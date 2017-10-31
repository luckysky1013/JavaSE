package cn.ncss.module.account.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.ncss.commom.ShiroUser;
import cn.ncss.common.utils.IpUtils;
import cn.ncss.module.account.domain.ThirdPartyAccount;
import cn.ncss.module.account.domain.UserAccount;
import cn.ncss.module.account.domain.UserActionLog;
import cn.ncss.module.account.domain.UserActionLog.ActionType;
import cn.ncss.module.account.handler.UserActionLogHandler;
import cn.ncss.module.account.repository.AccountUsersRepository;
import cn.ncss.module.account.repository.ThirdPartyAccountRepository;

/**
 * 第三方登录
 * @author LiYang
 *
 */
@Transactional(readOnly = false)
@RestController
public class ThirdPartyAccountController {
	private static final Logger logger = LoggerFactory.getLogger(ThirdPartyAccountController.class);
	@Autowired
	private ThirdPartyAccountRepository thirdPartyAccountRepository;
	@Autowired
	private AccountUsersRepository userAccountRepository;
	@Autowired
	private UserActionLogHandler userActionLogHandler;

	/**
	 * 绑定第三方账号
	 * @param acc
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/thirdParty")
	public ResponseEntity<String> save(@RequestBody ThirdPartyAccount acc, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			logger.info(bindingResult.getFieldError().getField() + "-----------"
					+ bindingResult.getFieldError().getDefaultMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
		}
		ThirdPartyAccount tp = null;
		try {
			tp = thirdPartyAccountRepository.save(acc);
			/*String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				//解绑原来的第三方账号
				thirdPartyAccountRepository.updateUserId(userId, acc.getType().name());
				//绑定新的第三方账号
				thirdPartyAccountRepository.updateUserId(acc.getOpenId(), acc.getType().name(), userId, new Date());
			}*/
			userActionLogHandler
					.saveLog(new UserActionLog(acc.getUserAccount() == null ? null : acc.getUserAccount().toString(),
							null, ActionType.THIRDPARTY_CREATE, "绑定第三方账号：[id=" + tp.getId() + "]",
							IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.CREATED).cacheControl(CacheControl.noCache()).body(tp.getId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("绑定第三方账号失败" + e);
		}
		userActionLogHandler.saveLog(new UserActionLog(
				acc.getUserAccount() == null ? null : acc.getUserAccount().toString(), null,
				ActionType.THIRDPARTY_CREATE, "绑定第三方账号：[id=" + tp.getId() + "]", IpUtils.getIpAddr(request), "false"));
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");

	}

	@PutMapping(value = "/thirdParty")
	public ResponseEntity<String> update(String openid, String type, String userId, HttpServletRequest request) {
		try {
			UserAccount userAccount = userAccountRepository.findOne(userId);
			thirdPartyAccountRepository.updateUserAccount(openid, type, userAccount, new Date());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");

	}

	@PutMapping(value = "/thirdPartyChange")
	public ResponseEntity<String> thirdPartyChange(String openid, String type, String userId,
			HttpServletRequest request) {
		try {
			//解绑原来的第三方账号
			thirdPartyAccountRepository.updateUserId(userId, type);
			//绑定新的第三方账号
			thirdPartyAccountRepository.updateUserId(openid, type, userId, new Date());
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.THIRDPARTY_UPDATE,
					"更新第三方账号：[userId=" + userId + ",type=" + type + "]", IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("解绑原来的绑定新第三方账号失败" + e);
		}
		userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.THIRDPARTY_UPDATE,
				"更新第三方账号：[userId=" + userId + ",type=" + type + "]", IpUtils.getIpAddr(request), "false"));
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");

	}

	/**
	 * 解绑第三方账号
	 * @param thirdPartyId
	 * @return
	 */
	@DeleteMapping(value = "/unbindthirdParty")
	public ResponseEntity<String> unbindthirdParty(String type, HttpServletRequest request) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		if (!StringUtils.hasText(type.toString())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		try {
			thirdPartyAccountRepository.deleteByUserAccountUserIdAndType(userId, type);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.THIRDPARTY_DELETE,
					"解绑第三方账号：[type=" + type + "]", IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("解绑第三方账号失败" + e);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.THIRDPARTY_DELETE,
					"解绑第三方账号：[type=" + type + "]", IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}
}
