package cn.ncss.module.account.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.ncss.common.utils.CommonUtils;
import cn.ncss.module.account.domain.Information;
import cn.ncss.module.account.domain.Profile;
import cn.ncss.module.account.domain.manager.CorpUsers;
import cn.ncss.module.account.domain.manager.CorpUsersSearchResult;
import cn.ncss.module.account.repository.AccountUsersRepository;
import cn.ncss.module.account.repository.CorpUsersQueryRepository;
import cn.ncss.module.account.repository.ProfileRepository;

/**
 * 后台manager管理企业用户
 * @author liyang
 *
 */
@RestController
public class ManagerCorpUserController {
	private static final Logger logger = LoggerFactory.getLogger(ManagerCorpUserController.class);
	@Autowired
	private CorpUsersQueryRepository corpUsersQueryRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private AccountUsersRepository userAccountRepository;

	/**
	 *  获取企业用户列表
	 * @param authedStatus 
	 * @param operator 操作者
	 * @param companyName
	 * @param realName 姓名、手机号或邮箱
	 * @param startDate
	 * @param endDate
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//@DateTimeFormat(pattern = "yyyy-MM-dd") Date
	@Transactional(readOnly = true)
	@GetMapping(value = "/corpUserlist")
	public ResponseEntity<CorpUsersSearchResult> search(String authedStatus, String companyName, String realName,
			String startDate, String endDate, String unlockDate, @RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "10") int pageSize) throws ParseException {
		//给authedStatus赋值
		List<Object[]> users = null;
		long total = 0;
		String unlock = " ";
		String lock = " ";
		if ("true".equals(unlockDate)) {//正常的数据
			unlock = "true";
			lock = " ";
		} else if ("false".equals(unlockDate)) {//禁用的数据
			lock = "true";
			unlock = " ";
		}
		List<CorpUsers> corpUsers = new ArrayList<CorpUsers>();
		if (!"无".equals(authedStatus)) {
			String[] nfnTypes = authedStatus.split(",");
			List asList = Arrays.asList(nfnTypes);
			users = corpUsersQueryRepository.findList(asList, companyName, realName, startDate, endDate,
					offset * pageSize + 1, pageSize * (offset + 1), unlock, lock);
			total = corpUsersQueryRepository.count(asList, companyName, realName, startDate, endDate, unlock, lock);
			for (Object[] user : users) {
				CorpUsers acc = new CorpUsers();
				acc.setUserId(user[0] + "");
				acc.setRealName(user[1] + "");
				acc.setCompanyName(user[2] + "");
				acc.setDepartment(user[3] + "");
				acc.setJobTitle(user[4] + "");
				acc.setJobphone(user[5] + "");
				acc.setJobfax(user[6] + "");
				acc.setEmail(user[7] + "");
				acc.setMobilePhone(user[8] + "");
				acc.setUpdateDate((Date) user[9]);
				acc.setAuthedStatus(user[10] + "");
				acc.setReportNum(user[11] + "");
				acc.setUnlockDate((Date) user[12]);
				acc.setCompanyId(user[13] + "");
				acc.setCheckDate((Date) user[14]);
				acc.setReportNumBefore(user[15] + "");
				corpUsers.add(acc);
			}
		} else {
			//搜索无信息数据
			users = corpUsersQueryRepository.findListNoInformation(realName, startDate, endDate, offset * pageSize + 1,
					pageSize * (offset + 1), unlock, lock);
			total = corpUsersQueryRepository.countNoInformation(realName, startDate, endDate, unlock, lock);
			for (Object[] user : users) {
				CorpUsers acc = new CorpUsers();
				acc.setUserId(user[0] + "");
				acc.setEmail(user[1] + "");
				acc.setMobilePhone(user[2] + "");
				acc.setUpdateDate((Date) user[3]);
				acc.setUnlockDate((Date) user[4]);
				acc.setReportNum(user[5] + "");
				acc.setReportNumBefore(user[6] + "");
				acc.setRealName("");
				acc.setAuthedStatus("null");
				corpUsers.add(acc);
			}
		}
		CorpUsersSearchResult proResult = new CorpUsersSearchResult();
		proResult.setCorpUsers(corpUsers);
		proResult.setTotal(total);
		proResult.setLimit(pageSize);
		proResult.setOffset(offset);
		return ResponseEntity.ok(proResult);
	}

	/**
	 *  根据公司ID获取企业用户列表
	 * @param comId 公司id
	 * @param realName 真实姓名
	 * @param startDate 审核开始时间
	 * @param endDate 审核结束时间
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws ParseException 
	 */
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Transactional(readOnly = true)
	@GetMapping(value = "/corpInfoUserList")
	public ResponseEntity<CorpUsersSearchResult> search(String comId, String realName, String startDate, String endDate,
			@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int pageSize)
			throws ParseException {
		List<Object[]> users = corpUsersQueryRepository.findListByComId(comId, realName, startDate, endDate,
				offset * pageSize + 1, pageSize * (offset + 1));
		long total = corpUsersQueryRepository.countByComId(comId, realName, startDate, endDate);
		/*	Page<Object[]> page = queryRepository.paginateCorpuserListByName(comId, realName, startDate, endDate, pageSize,
					offset);*/
		List<CorpUsers> corpUsers = new ArrayList<CorpUsers>();
		for (Object[] user : users) {
			CorpUsers acc = new CorpUsers();
			acc.setUserId(user[0] + "");
			acc.setRealName(user[1] + "");
			acc.setCompanyName(user[2] + "");
			acc.setDepartment(user[3] + "");
			acc.setJobTitle(user[4] + "");
			acc.setJobphone(user[5] + "");
			acc.setJobfax(user[6] + "");
			acc.setEmail(user[7] + "");
			acc.setMobilePhone(user[8] + "");
			acc.setUpdateDate((Date) user[9]);
			acc.setAuthedStatus(user[10] + "");
			acc.setCheckDate((Date) user[11]);
			acc.setUnlockDate((Date) user[12]);
			acc.setCompanyId(user[13] + "");
			acc.setUploadTag(user[14] + "");
			acc.setReportNumBefore(user[15] + "");
			corpUsers.add(acc);
		}
		CorpUsersSearchResult proResult = new CorpUsersSearchResult();
		proResult.setCorpUsers(corpUsers);
		proResult.setTotal(total);
		proResult.setLimit(pageSize);
		proResult.setOffset(offset);
		return ResponseEntity.ok(proResult);
	}

	/**
	 * 后台管理解绑
	 * @param userIds
	 * @return
	 */
	/*@Transactional(readOnly = false)
	@DeleteMapping(value = "/managerUnbundl")
	public ResponseEntity<String> unbundl(String userIds) {
		try {
			String[] userId = userIds.replace("，", ",").split(",");
			if (userId.length == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
			}
			for (int i = 0; i < userId.length; i++) {
				profileRepository.delete(userId[i]);
				//删除上传得认证图片
				String delPath = corpUploadPath + userId + "/";
				ImageUtil.deleteFolder(delPath);
			}
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			logger.warn("后台管理解绑异常" + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}*/

	/**
	 * 企业用户管理修改个人信息
	 * @param userId
	 * @param property
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	@PutMapping(value = "/updateCorpUser")
	public ResponseEntity<Map<String, String>> managerUpdateProfile(@RequestBody @Valid Profile profile,
			BindingResult bindingResult) {
		Map<String, String> result = new HashedMap();
		if (bindingResult.hasErrors()) {
			result.put("result", "false");
			result.put("code", bindingResult.getFieldError().getField());
			result.put("message", bindingResult.getFieldError().getDefaultMessage());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		if (!profileRepository.exists(profile.getUserId())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		try {
			String email = profile.getInformation().getEmail() == "" ? null : profile.getInformation().getEmail();
			String mobile = profile.getInformation().getMobilePhone() == "" ? null
					: profile.getInformation().getMobilePhone();
			if (!StringUtils.hasText(email) && !StringUtils.hasText(mobile)) {
				result.put("result", "false");
				result.put("code", "error");
				result.put("message", "手机号和邮箱至少填写一个");
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			}
			if (StringUtils.hasText(email) && !CommonUtils.checkEmail(email)) {
				result.put("result", "false");
				result.put("code", "email");
				result.put("message", "邮箱格式不正确");
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			}
			if (StringUtils.hasText(mobile) && !CommonUtils.checkPhone(mobile)) {
				result.put("result", "false");
				result.put("code", "mobilePhone");
				result.put("message", "手机号格式不正确");
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			}
			if (userAccountRepository.countByEmail(email, profile.getUserId()) > 0 ? true : false) {
				result.put("result", "false");
				result.put("code", "email");
				result.put("message", "该邮箱已被其他用户注册");
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			}
			if (userAccountRepository.countByMobilePhone(mobile, profile.getUserId()) > 0 ? true : false) {
				result.put("result", "false");
				result.put("code", "mobilePhone");
				result.put("message", "该手机号已被其他用户注册");
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			}
			Profile oldProfile = profileRepository.findOne(profile.getUserId());
			Information oldInfo = oldProfile.getInformation();
			oldInfo.setJobfax(profile.getInformation().getJobfax());
			oldInfo.setJobphone(profile.getInformation().getJobphone());
			oldInfo.setDepartment(profile.getInformation().getDepartment());
			oldInfo.setJobTitle(profile.getInformation().getJobTitle());
			oldInfo.setMobilePhone(profile.getInformation().getMobilePhone());
			oldInfo.setEmail(profile.getInformation().getEmail());
			oldProfile.setInformation(oldInfo);
			oldProfile.setGender(profile.getGender());
			oldProfile.setRealName(profile.getRealName());
			oldProfile.setUpdateDate(new Date());
			profileRepository.save(oldProfile);
			//手机号邮箱若修改，同步到accountuser表
			String isValidEmail = "false";
			String isValidPhone = "false";
			if (!StringUtils.hasText(email)) {
				isValidEmail = "true";
			}
			if (!StringUtils.hasText(mobile)) {
				isValidPhone = "true";
			}
			userAccountRepository.updateEmail(profile.getUserId(), email, isValidEmail);
			userAccountRepository.updateMobilePhone(profile.getUserId(), mobile, isValidPhone);
			result.put("result", "true");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("企业用户管理修改个人信息异常" + e);
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "修改失败");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
	}

	/**
	 * 禁用、批量禁用
	 * @param userId 用户ID
	 * @param unlockDate 锁住时间
	 * @param tag 是否永久封号
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = false)
	@PutMapping(value = "/locked")
	public ResponseEntity<String> locked(String userId, String unlockDate) {
		try {
			if (!StringUtils.hasText(userId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
			Date unlockDateNew = null;
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
			if (StringUtils.hasText(unlockDate)) {
				if (!"3000-01-01".equals(unlockDate)) {
					unlockDate = unlockDate + " " + df.format(date).toString();
					unlockDateNew = new Date(sdf.parse(unlockDate).getTime());
				} else {
					unlockDateNew = new Date(ymd.parse(unlockDate).getTime());
				}
			}
			String[] ids = userId.split(",");
			for (int i = 0; i < ids.length; i++) {
				if (StringUtils.hasText(ids[i])) {
					userAccountRepository.updatelockDate(ids[i], unlockDateNew);
				}
			}
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("禁用、批量禁用异常" + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	/**
	 * 解禁、批量解禁
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	@PutMapping(value = "/unlocked")
	public ResponseEntity<String> unlocked(String userId) {
		try {
			if (!StringUtils.hasText(userId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			String[] ids = userId.split(",");
			for (int i = 0; i < ids.length; i++) {
				if (StringUtils.hasText(ids[i])) {
					userAccountRepository.updateunlockDate(ids[i], null, 0);
				}
			}
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("解禁、批量解禁异常" + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	/**
	 * 解绑
	 * @param userId
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = false)
	@PutMapping(value = "/unbundled")
	public ResponseEntity<String> unbundled(String userId) {
		try {
			if (!StringUtils.hasText(userId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			profileRepository.delete(userId);
			//用户累计被举报数更新
			userAccountRepository.updateBeReportedCountBeforeById(userId);
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("解绑异常" + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	/**
	 * 删除、批量删除企业用户(只有无信息用户可以删除)
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	@PutMapping(value = "/deleteCorpUser")
	public ResponseEntity<String> deleteCorpUser(String userId) {
		try {
			if (!StringUtils.hasText(userId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			Profile pro = profileRepository.findOne(userId);
			if (pro != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			userAccountRepository.deleteByUserId(userId);
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("删除corpuser无信息用户异常" + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	/**
	 * 更新符合条件同事的checkValidDate
	 * @param checkValidDate
	 * @param companyId
	 * @return
	 */
	@Transactional(readOnly = false)
	@PutMapping(value = "/updateCheckValidDate")
	public ResponseEntity<String> updateCheckValidDate(Date checkValidDate, String companyId) {
		try {
			if (!StringUtils.hasText(companyId)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			//更新审核通过的学生的审核时间 3 4 5 9 
			checkValidDate = new Date(checkValidDate.getTime() + 1 * 24 * 3599 * 1000);
			profileRepository.updateCheckValidDate(companyId, checkValidDate, new Date());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("updateCheckValidDate异常" + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	/**
	 * 后台manager审核用户未通过
	 * @param userId
	 * @param authedStatus
	 * @param checkReason
	 * @param description
	 * @param checkDate
	 * @return
	 */
	@Transactional(readOnly = false)
	@PutMapping(value = "/updateAuthedFail")
	public ResponseEntity<String> updateAuthedFail(String userId, String authedStatus, String checkReason,
			String description) {
		try {
			profileRepository.updateAuthedFail(userId, authedStatus, checkReason, description, new Date(), new Date());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			logger.warn("后台manager审核用户未通过异常updateAuthedFail" + e);
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
	}

	/**
	 * 后台manager审核用户通过
	 * @param userId
	 * @param authedStatus
	 * @param checkValidDate
	 * @param checkDate
	 * @return
	 */
	@Transactional(readOnly = false)
	@PutMapping(value = "/updateAuthedSucc")
	public ResponseEntity<String> updateAuthedSucc(String userId, String authedStatus, Date checkValidDate) {
		try {
			checkValidDate = new Date(checkValidDate.getTime() + 1 * 24 * 3599 * 1000);
			profileRepository.updateAuthedSucc(userId, authedStatus, checkValidDate, new Date(), new Date());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			logger.warn("后台manager审核用户通过异常" + e);
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
	}

}
