package cn.ncss.module.account.controller;

import javax.validation.Valid;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.ncss.module.account.domain.UserAccount;
import cn.ncss.module.account.repository.AccountUsersRepository;

/**
 * 注册
 * @author LiYang
 *
 */

@RestController
public class RegisterController {
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	@Autowired
	private AccountUsersRepository userAccountRepository;

	/**
	 * 
	 * @param usertype 用户类型
	 * @param username 用户名
	 * @param password 密码
	 * @param regType 注册（电子邮箱或手机）
	 * @param vcode 图片验证码
	 * @param authcode 短信验证码
	 * @param request
	 * @param referer
	 * @return
	 */
	@Transactional(readOnly = false)
	@PostMapping(value = "/register")
	public ResponseEntity<String> register(@RequestBody @Valid UserAccount userAccount, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					bindingResult.getFieldError().getField() + "&" + bindingResult.getFieldError().getDefaultMessage());
		}
		try {
			//密码加密
			if (!StringUtils.hasText(userAccount.getSalt())) {
				userAccount.setSalt(new SecureRandomNumberGenerator().nextBytes().toHex());
			}
			userAccount.setPassword(new Md5Hash(userAccount.getPassword(), userAccount.getSalt()).toString());
			userAccountRepository.save(userAccount);
			return ResponseEntity.status(HttpStatus.CREATED).cacheControl(CacheControl.noCache()).body("");
		} catch (DataIntegrityViolationException p) {
			p.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache())
					.body("error");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Add userAccount error .The account's name is {}",
					userAccount.getEmail() + "、" + userAccount.getMobilePhone() + e);
			//创建失败
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache())
					.body("error");
		}

	}
}
