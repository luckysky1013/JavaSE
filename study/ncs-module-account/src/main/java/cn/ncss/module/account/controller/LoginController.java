package cn.ncss.module.account.controller;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ncss.commom.ShiroUser;
import cn.ncss.module.account.domain.UserAccount;
import cn.ncss.module.account.repository.AccountUsersRepository;

/**
 * 登录
 * @author LiYang
 *
 */
@RestController
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	public final static String UPDATE_3RD_PATTY_BIND = "update_3rd_party_bind";

	@Autowired
	private AccountUsersRepository userAccountRepository;

	/**
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param vcode 验证码
	 * @param isRememberMe 是否自动登录
	 * @param flag 密码登录或验证码登录
	 * @param referer
	 * @return
	 */
	@PostMapping(value = "/signin")
	public ResponseEntity<String> login(String username, String password, boolean isRememberMe) {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			AuthenticationToken token = new UsernamePasswordToken(username, password, isRememberMe);
			currentUser.login(token);
			String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
			UserAccount userAccount = userAccountRepository.findOne(userId);
			userAccount.setLastLoginDate(new Date());
			userAccountRepository.save(userAccount);
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			logger.warn("登录异常" + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}

	}
}
