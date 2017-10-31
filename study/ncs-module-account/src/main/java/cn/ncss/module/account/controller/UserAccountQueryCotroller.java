package cn.ncss.module.account.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cn.ncss.commom.ShiroUser;
import cn.ncss.module.account.domain.UserAccount;
import cn.ncss.module.account.repository.AccountUsersRepository;
import cn.ncss.module.account.utils.EmailUtil;

@Transactional(readOnly = true)
@RestController
public class UserAccountQueryCotroller {
	@Autowired
	private AccountUsersRepository userAccountRepository;

	/**
	 * 获取用户信息
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "/userByUserId")
	public ResponseEntity<UserAccount> getOne() {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		UserAccount userAccount = userAccountRepository.findOne(userId);
		return ResponseEntity.ok(userAccount);
	}

	@GetMapping(value = "/userByUserId/{userId}")
	public ResponseEntity<UserAccount> getByUserId(@PathVariable String userId) {
		UserAccount userAccount = userAccountRepository.findOne(userId);
		return ResponseEntity.ok(userAccount);
	}

	/**
	 * 获取用户信息
	 * @param username
	 * @return
	 */
	@GetMapping(value = "/userAccount")
	public ResponseEntity<UserAccount> getUserAccount(String username) {
		UserAccount user = null;
		if (username.matches(EmailUtil.REGEXP_EMAIL)) {
			user = userAccountRepository.findByEmail(username);
		} else if (username.matches(EmailUtil.REGEXP_MOBILEPHONE)) {
			user = userAccountRepository.findByMobilePhone(username);
		}
		return ResponseEntity.ok(user);
	}
}
