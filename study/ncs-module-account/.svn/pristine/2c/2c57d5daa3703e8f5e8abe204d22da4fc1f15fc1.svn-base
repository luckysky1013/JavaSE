package cn.ncss.module.account.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ncss.commom.ShiroUser;
import cn.ncss.common.utils.CommonUtils;
import cn.ncss.common.utils.IpUtils;
import cn.ncss.module.account.domain.Profile;
import cn.ncss.module.account.domain.UserAccount;
import cn.ncss.module.account.domain.UserActionLog;
import cn.ncss.module.account.domain.UserActionLog.ActionType;
import cn.ncss.module.account.handler.UserActionLogHandler;
import cn.ncss.module.account.repository.AccountUsersRepository;
import cn.ncss.module.account.repository.ProfileRepository;
import cn.ncss.module.account.utils.EmailUtil;

@Transactional(readOnly = false)
@RestController
public class UserAccountCommandCotroller {
	private static final Logger logger = LoggerFactory.getLogger(UserAccountCommandCotroller.class);

	public static final String RESET_PWD_USERNAME = "reset_pwd_username";
	@Autowired
	private AccountUsersRepository userAccountRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private UserActionLogHandler userActionLogHandler;

	/**
	 * 修改密码
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param rePassword
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/password")
	public ResponseEntity<Map<String, String>> changePassword(String oldPassword, String newPassword, String rePassword,
			HttpServletRequest request) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		Map<String, String> result = new HashedMap();
		if (!StringUtils.hasText(oldPassword) || !StringUtils.hasText(newPassword) || !newPassword.equals(rePassword)) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "密码不能为空");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		UserAccount user = userAccountRepository.findOne(userId);
		logger.info("修改密码为" + new Md5Hash(oldPassword, user.getSalt()).toString());
		if (!user.getPassword().equals(new Md5Hash(oldPassword, user.getSalt()).toString())) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "原密码错误");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		UserAccount userAccount = null;
		try {
			//user.setPassword(newPassword);
			//密码加密
			if (!StringUtils.hasText(user.getSalt())) {
				user.setSalt(new SecureRandomNumberGenerator().nextBytes().toHex());
			}
			user.setPasswordLevel(CommonUtils.getLevel(newPassword));
			user.setPassword(new Md5Hash(newPassword, user.getSalt()).toString());
			userAccount = userAccountRepository.save(user);
			((ShiroUser) SecurityUtils.getSubject().getPrincipal()).setPasswordLevel(userAccount.getPasswordLevel());
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.runAs(currentUser.getPrincipals());
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
					"修改用户密码：[id=" + userAccount.getUserId() + ",newPassword=" + newPassword + "]",
					IpUtils.getIpAddr(request), "true"));
			result.put("result", "true");
			result.put("code", "");
			result.put("message", "");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("修改密码失败" + e);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
					"修改用户密码：[id=" + userAccount.getUserId() + ",newPassword=" + newPassword + "]",
					IpUtils.getIpAddr(request), "false"));
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "密码修改失败");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
	}

	/**
	 * 管理员重置用户密码
	 * @param newPassword
	 * @param rePassword
	 * @return
	 */
	@PostMapping(value = "/managerRePassword")
	public ResponseEntity<String> rePassword(String userId, String newPassword, String rePassword,
			HttpServletRequest request) {
		if (!StringUtils.hasText(userId) || !StringUtils.hasText(newPassword) || !newPassword.equals(rePassword)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}
		UserAccount user = userAccountRepository.findOne(userId);
		try {
			//密码加密
			if (!StringUtils.hasText(user.getSalt())) {
				user.setSalt(new SecureRandomNumberGenerator().nextBytes().toHex());
			}
			user.setPasswordLevel(CommonUtils.getLevel(newPassword));
			user.setPassword(new Md5Hash(newPassword, user.getSalt()).toString());
			/*user.setUnlockDate(null);//清空解锁日期*/
			userAccountRepository.save(user);
			userActionLogHandler.saveLog(new UserActionLog("管理员", userId, ActionType.USER_UPDATE,
					"管理员重置用户密码：[id=" + userId + ",newPassword=" + newPassword + "]", IpUtils.getIpAddr(request),
					"true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("管理员重置用户密码失败" + e);
			userActionLogHandler.saveLog(new UserActionLog("管理员", userId, ActionType.USER_UPDATE,
					"管理员重置用户密码：[id=" + userId + ",newPassword=" + newPassword + "]", IpUtils.getIpAddr(request),
					"false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	/**
	 * 重置密码(手机号或者邮箱)
	 * @param password 密码
	 * @param reword 重复密码
	 * @return
	 */
	@PostMapping(value = "/repassword")
	public ResponseEntity<String> resetPassword(String password, String username, HttpServletRequest request) {
		if (!StringUtils.hasText(password) || !StringUtils.hasText(username)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}
		try {
			UserAccount user = null;
			if (username.matches(EmailUtil.REGEXP_EMAIL)) {
				user = userAccountRepository.findByEmail(username);
				//password = new Md5Hash(password, user.getSalt()).toString();
				//userAccountRepository.updatePasswordByEmail(username, password);
				userAccountRepository.updateLoginFailedCountMakeZeroByEmail(username);
			} else if (username.matches(EmailUtil.REGEXP_MOBILEPHONE)) {
				user = userAccountRepository.findByMobilePhone(username);
				//password = new Md5Hash(password, user.getSalt()).toString();
				//userAccountRepository.updatePasswordByMobilePhone(username, password);
				userAccountRepository.updateLoginFailedCountMakeZeroByMobilePhone(username);
			}
			//user.setPassword(password);
			//密码加密
			if (!StringUtils.hasText(user.getSalt())) {
				user.setSalt(new SecureRandomNumberGenerator().nextBytes().toHex());
			}
			user.setPasswordLevel(CommonUtils.getLevel(password));
			user.setPassword(new Md5Hash(password, user.getSalt()).toString());
			userAccountRepository.save(user);
			userActionLogHandler.saveLog(new UserActionLog(username, null, ActionType.USER_UPDATE,
					"用户重置用户密码：[username=" + username + ",password=" + password + "]", IpUtils.getIpAddr(request),
					"true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("重置密码(手机号或者邮箱)失败" + e);
			userActionLogHandler.saveLog(new UserActionLog(username, null, ActionType.USER_UPDATE,
					"用户重置用户密码：[username=" + username + ",password=" + password + "]", IpUtils.getIpAddr(request),
					"false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}

	}

	public UserAccount getByUsername(String username) {
		if (username != null) {
			if (username.matches(EmailUtil.REGEXP_EMAIL)) {
				return userAccountRepository.findByEmail(username);
			} else if (username.matches(EmailUtil.REGEXP_MOBILEPHONE)) {
				return userAccountRepository.findByMobilePhone(username);
			}
		}
		logger.warn("username=[{}] is not correct format", username);
		return null;
	}

	/**
	 * 绑定并验证邮箱
	 * @param email 邮箱
	 * @param authcode 验证码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/bindEmail")
	public ResponseEntity<Map<String, String>> bindEmail(String email, HttpServletRequest request) {
		Map<String, String> result = new HashedMap();
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		if (email != null && email.matches(EmailUtil.REGEXP_EMAIL)) {
			//绑定邮箱并验证
			if (userAccountRepository.findByEmail(email) != null) {
				result.put("result", "false");
				result.put("code", "error");
				result.put("message", "邮箱已存在");
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			}
			//&& userAccountRepository.updateIsValidEmail(userId, true) == 1
			if (StringUtils.hasText(email)) {
				if (!CommonUtils.checkEmail(email)) {
					result.put("result", "false");
					result.put("code", "error");
					result.put("message", "邮箱格式不正确");
					return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
				}
			}
			if (userAccountRepository.updateEmail(userId, email, "true") == 1) {
				try {
					Profile profile = profileRepository.findOne(userId);
					if (profile != null) {
						profileRepository.updateEmail(userId, email, new Date());
					}
					result.put("result", "true");
					result.put("code", "");
					result.put("message", "");
					userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
							"绑定并验证邮箱：[email=" + email + "]", IpUtils.getIpAddr(request), "false"));
					return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
				} catch (Exception e) {
					logger.warn("绑定并验证邮箱失败" + e);
				}
			}
		}
		result.put("result", "false");
		result.put("code", "error");
		result.put("message", "绑定邮箱失败");
		userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
				"绑定并验证邮箱：[email=" + email + "]", IpUtils.getIpAddr(request), "false"));
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);

	}

	/**
	 * 解绑邮箱
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/removeEmail")
	public ResponseEntity<Map<String, String>> removeEmail(HttpServletRequest request) {
		Map<String, String> result = new HashedMap();
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		String mobile = userAccountRepository.findOne(userId).getMobilePhone();
		if (!StringUtils.hasText(mobile)) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "手机为空，不能解绑邮箱");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		if (userAccountRepository.updateEmail(userId, null, "false") == 1) {
			try {
				Profile profile = profileRepository.findOne(userId);
				if (profile != null) {
					profileRepository.updateEmail(userId, "", new Date());
				}

				result.put("result", "true");
				result.put("code", "");
				result.put("message", "");
				userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE, "解绑邮箱",
						IpUtils.getIpAddr(request), "true"));
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			} catch (Exception e) {
				logger.warn("解绑邮箱失败" + e);
			}
		}
		result.put("result", "false");
		result.put("code", "error");
		result.put("message", "解绑邮箱失败");
		userActionLogHandler.saveLog(
				new UserActionLog(userId, null, ActionType.USER_UPDATE, "解绑邮箱", IpUtils.getIpAddr(request), "false"));
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);

	}

	/**
	 * 修改邮箱
	 * @param newemail
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/changeEmail")
	public ResponseEntity<Map<String, String>> changeEmail(String newemail, HttpServletRequest request) {
		Map<String, String> result = new HashedMap();
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		if (newemail != null && newemail.matches(EmailUtil.REGEXP_EMAIL)) {
			//绑定邮箱并验证
			if (userAccountRepository.findByEmail(newemail) != null) {
				result.put("result", "false");
				result.put("code", "error");
				result.put("message", "邮箱已存在");
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			}
			if (StringUtils.hasText(newemail)) {
				if (!CommonUtils.checkEmail(newemail)) {
					result.put("result", "false");
					result.put("code", "error");
					result.put("message", "邮箱格式不正确");
					return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
				}
			}
			//&& userAccountRepository.updateIsValidEmail(userId, true) == 1
			if (userAccountRepository.updateEmail(userId, newemail, "true") == 1) {
				try {
					Profile profile = profileRepository.findOne(userId);
					if (profile != null) {
						profileRepository.updateEmail(userId, newemail, new Date());
					}
					result.put("result", "true");
					result.put("code", "");
					result.put("message", "");
					userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
							"修改邮箱[newemail=" + newemail + "]", IpUtils.getIpAddr(request), "true"));
					return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
				} catch (Exception e) {
					logger.warn("修改邮箱失败" + e);
				}
			}
		}
		result.put("result", "false");
		result.put("code", "error");
		result.put("message", "修改邮箱失败");
		userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
				"修改邮箱[newemail=" + newemail + "]", IpUtils.getIpAddr(request), "false"));
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);

	}

	/**
	 * 绑定并验证手机号
	 * @param mobilephone
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/bindMobilePhone")
	public ResponseEntity<Map<String, String>> bindMobilePhone(String mobilephone, HttpServletRequest request) {
		Map<String, String> result = new HashedMap();
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		if (mobilephone != null && mobilephone.matches(EmailUtil.REGEXP_MOBILEPHONE)) {
			//绑定手机号同时验证
			if (userAccountRepository.findByMobilePhone(mobilephone) != null) {
				result.put("result", "false");
				result.put("code", "error");
				result.put("message", "手机号已存在");
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			}
			if (StringUtils.hasText(mobilephone)) {
				if (!CommonUtils.checkPhone(mobilephone)) {
					result.put("result", "false");
					result.put("code", "error");
					result.put("message", "手机号格式不正确");
					return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
				}

			}
			if (userAccountRepository.updateMobilePhone(userId, mobilephone, "false") == 1
					&& userAccountRepository.updateIsValidPhone(userId, "true") == 1) {
				try {
					Profile profile = profileRepository.findOne(userId);
					if (profile != null) {
						profileRepository.updateMobilePhone(userId, mobilephone, new Date());
					}
					result.put("result", "true");
					result.put("code", "");
					result.put("message", "");
					userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
							"绑定并验证手机号：[mobilephone=" + mobilephone + "]", IpUtils.getIpAddr(request), "true"));
					return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
				} catch (Exception e) {
					logger.warn("绑定手机号失败" + e);
				}
			}
		}
		result.put("result", "false");
		result.put("code", "error");
		result.put("message", "绑定手机号失败");
		userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
				"绑定并验证手机号：[mobilephone=" + mobilephone + "]", IpUtils.getIpAddr(request), "false"));
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);

	}

	/**
	 * 修改手机号
	 * @param newphone
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/changeMobilePhone")
	public ResponseEntity<Map<String, String>> changeMobilePhone(String newphone, HttpServletRequest request) {
		Map<String, String> result = new HashedMap();
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		if (newphone != null && newphone.matches(EmailUtil.REGEXP_MOBILEPHONE)) {
			//修改手机号同时验证
			if (userAccountRepository.findByMobilePhone(newphone) != null) {
				result.put("result", "false");
				result.put("code", "error");
				result.put("message", "手机号已存在");
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			}
			if (StringUtils.hasText(newphone)) {
				if (!CommonUtils.checkPhone(newphone)) {
					result.put("result", "false");
					result.put("code", "error");
					result.put("message", "手机号格式不正确");
					return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
				}
			}
			if (userAccountRepository.updateMobilePhone(userId, newphone, "false") == 1
					&& userAccountRepository.updateIsValidPhone(userId, "true") == 1) {
				try {
					Profile profile = profileRepository.findOne(userId);
					if (profile != null) {
						profileRepository.updateMobilePhone(userId, newphone, new Date());
					}
					result.put("result", "true");
					result.put("code", "");
					result.put("message", "");
					userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
							"修改手机号[newphone=" + newphone + "]", IpUtils.getIpAddr(request), "true"));
					return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
				} catch (Exception e) {
					logger.warn("修改手机号失败" + e);
				}
			}

		}
		result.put("result", "false");
		result.put("code", "error");
		result.put("message", "修改手机号失败");
		userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
				"修改手机号[newphone=" + newphone + "]", IpUtils.getIpAddr(request), "false"));
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);

	}

	/**
	 * 解绑手机
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/removeMobilePhone")
	public ResponseEntity<Map<String, String>> removeMobilePhone(HttpServletRequest request) {
		Map<String, String> result = new HashedMap();
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		String email = userAccountRepository.findOne(userId).getEmail();
		if (!StringUtils.hasText(email)) {
			//TODO 抛出邮箱为空，不能解绑手机异常
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "邮箱为空，不能解绑手机");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		if (userAccountRepository.updateMobilePhone(userId, null, "false") == 1) {
			try {
				Profile profile = profileRepository.findOne(userId);
				if (profile != null) {
					profileRepository.updateMobilePhone(userId, "", new Date());
				}
				result.put("result", "true");
				result.put("code", "");
				result.put("message", "");
				userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE, "解绑手机号",
						IpUtils.getIpAddr(request), "true"));
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			} catch (Exception e) {
				logger.warn("解绑手机失败" + e);
			}

		}
		result.put("result", "false");
		result.put("code", "error");
		result.put("message", "解绑手机失败");
		userActionLogHandler.saveLog(
				new UserActionLog(userId, null, ActionType.USER_UPDATE, "解绑手机号", IpUtils.getIpAddr(request), "false"));
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);

	}

	/**
	 * 登录失败次数置空
	 * @param userId
	 * @return
	 */
	@PutMapping(value = "/loginErrorConut")
	public ResponseEntity<String> loginErrorCountMakeZero(String userId, HttpServletRequest request) {
		//String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		try {
			userAccountRepository.updateLoginFailedCountMakeZeroById(userId);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE, "登录失败次数置空",
					IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			logger.warn("登录失败次数置空失败" + e);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE, "登录失败次数置空",
					IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}

	}

	/**
	 * 登录失败，错误次数加1,如果大于5次，锁定半小时
	 * @param userId
	 */
	@PutMapping(value = "/loginFailed")
	public ResponseEntity<String> proccessLoginFailed(String userId, HttpServletRequest request) {
		try {
			userAccountRepository.updateLoginFailedCountIncrementOneById(userId);
			userAccountRepository.updateUnlockDateById(userId, 5,
					new Date(System.currentTimeMillis() + 30 * 60 * 1000));
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
					"登录失败，错误次数加1,如果大于5次，锁定半小时", IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
					"登录失败，错误次数加1,如果大于5次，锁定半小时", IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}

	}

	/**
	 * 修改通知类型
	 * @param nfnType
	 * @return
	 */
	@PutMapping(value = "/updateNfnType")
	public ResponseEntity<String> updateNfnType(String nfnType, HttpServletRequest request) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		try {
			userAccountRepository.updateNfnType(userId, nfnType);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
					"修改通知类型[nfnType=" + nfnType + "]", IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
					"修改通知类型[nfnType=" + nfnType + "]", IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}

	}

	//修改管理员删除用户时弹窗标识
	@PutMapping(value = "/updatePopTag")
	public ResponseEntity<String> updatePopTag(String userId, String popTag, HttpServletRequest request) {
		try {
			userAccountRepository.updatePopTag(userId, popTag);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
					"修改弹窗标识[popTag=" + popTag + "]", IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
					"修改弹窗标识[popTag=" + popTag + "]", IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}

	}

	/**
	 * 修改最后登录
	 * @param userId
	 * @param popTag
	 * @return
	 */
	@PutMapping(value = "/updateLastLoginDate")
	public ResponseEntity<String> updateLastLoginDate(String userId, HttpServletRequest request) {
		try {
			UserAccount userAccount = userAccountRepository.findOne(userId);
			userAccount.setLastLoginDateShow(
					userAccount.getLastLoginDate() == null ? new Date() : userAccount.getLastLoginDate());
			userAccount.setLastLoginDate(new Date());
			userAccountRepository.save(userAccount);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
					"修改最后登录时间[lastLoginDate=" + new Date() + "]", IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE,
					"修改最后登录时间[lastLoginDate=" + new Date() + "]", IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}

	}

	/**
	 * 职位被举报次数加一
	 * @param userId
	 * @return
	 */
	@PutMapping(value = "/updateBeReportedCount")
	public ResponseEntity<String> updateBeReportedCountById(String userId, HttpServletRequest request) {
		try {
			userAccountRepository.updateBeReportedCountById(userId);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE, "职位被举报次数加1",
					IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE, "职位被举报次数加1",
					IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}

	}

	//用户累计被举报数更新
	@PutMapping(value = "/updateBeReportedCountBefore")
	public ResponseEntity<String> updateBeReportedCountBeforeById(String userId, HttpServletRequest request) {
		try {
			userAccountRepository.updateBeReportedCountBeforeById(userId);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE, "用户累计被举报数更新",
					IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.USER_UPDATE, "用户累计被举报数更新",
					IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}

	}

}
