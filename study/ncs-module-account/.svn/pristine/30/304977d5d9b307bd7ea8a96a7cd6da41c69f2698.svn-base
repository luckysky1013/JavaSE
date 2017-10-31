package cn.ncss.module.account.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import cn.ncss.module.account.domain.EducationHistory;
import cn.ncss.module.account.domain.Information;
import cn.ncss.module.account.domain.Profile;
import cn.ncss.module.account.domain.Resume;
import cn.ncss.module.account.domain.UserActionLog;
import cn.ncss.module.account.domain.UserActionLog.ActionType;
import cn.ncss.module.account.handler.UserActionLogHandler;
import cn.ncss.module.account.repository.AccountUsersRepository;
import cn.ncss.module.account.repository.EducationHistoryRepository;
import cn.ncss.module.account.repository.ProfileRepository;
import cn.ncss.module.account.repository.ResumeRepository;

/**
 * 基本信息
 * @author LiYang
 *
 */
@RestController
public class ProfileCommandController {
	private static final Logger logger = LoggerFactory.getLogger(ProfileCommandController.class);
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ResumeRepository resumeRepository;
	@Autowired
	private UserActionLogHandler userActionLogHandler;
	@Autowired
	private EducationHistoryRepository educationHistoryRepository;
	@Autowired
	private AccountUsersRepository userAccountRepository;
	/*@Value("${corp.upload.path}")
	private String corpUploadPath;*/

	/**
	 * 完善基本信息
	 * @param userId
	 * @param profile
	 * @param bindingResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	@PostMapping(value = "/proBasic")
	public ResponseEntity<Map<String, String>> addProfile(@RequestBody @Valid Profile profile,
			BindingResult bindingResult, HttpServletRequest request) {
		Map<String, String> result = new HashedMap();
		if (bindingResult.hasErrors()) {
			result.put("result", "false");
			result.put("code", bindingResult.getFieldError().getField());
			result.put("message", bindingResult.getFieldError().getDefaultMessage());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String userId = shiroUser.getUserId();
		try {
			profile.setUserId(userId);
			if ("STUDENT".equals(shiroUser.getUserType())) {
				int tag = profileRepository.findByInformationIdCard(profile.getInformation().getIdCard());
				if (tag != 0) {
					result.put("result", "false");
					result.put("code", "idCard");
					result.put("message", "证件号码已存在");
					return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
				}
			}
			profileRepository.save(profile);
			((ShiroUser) SecurityUtils.getSubject().getPrincipal()).setRealName(profile.getRealName());
			((ShiroUser) SecurityUtils.getSubject().getPrincipal()).setAvatorUrl(profile.getAvatorUrl());
			SecurityUtils.getSubject().runAs(SecurityUtils.getSubject().getPrincipals());
			result.put("result", "true");
			result.put("code", "");
			result.put("message", "");
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_CREATE,
					"添加基本信息[userId=" + userId + ",realName=" + profile.getRealName() + "]", IpUtils.getIpAddr(request),
					"true"));
		} catch (Exception e) {
			logger.warn("完善基本信息失败：" + e);
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "完善信息失败");
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_CREATE,
					"添加基本信息[userId=" + userId + ",realName=" + profile.getRealName() + "]", IpUtils.getIpAddr(request),
					"false"));
		}
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
	}

	/**
	 * 修改基本信息
	 * @param userId
	 * @param province 生源地省份
	 * @param city 城市
	 * @param politial 政治面貌
	 * @return
	 */
	@Transactional(readOnly = false)
	@PutMapping(value = "/updateProfile")
	public ResponseEntity<String> updateProfile(@RequestBody @Valid Profile profile, HttpServletRequest request) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		try {
			if (profile != null) {
				profileRepository.save(profile);
				userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_UPDATE,
						"修改基本信息[userId=" + userId + "]", IpUtils.getIpAddr(request), "true"));
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
			}
		} catch (Exception e) {
			logger.warn("修改基本信息异常" + e);
		}
		userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_UPDATE,
				"修改基本信息[userId=" + userId + "]", IpUtils.getIpAddr(request), "false"));
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
	}

	/**
	 * profile里添加企业用户Id
	 * @param profile
	 * @return
	 */
	@Transactional(readOnly = false)
	@PutMapping(value = "/updateRelatedId")
	public ResponseEntity<String> updateRelatedId(String userId, String comId, String uploadTag,
			HttpServletRequest request) {
		try {
			if ("0".equals(uploadTag)) {
				//更新waitCheckDate
				Profile profile = profileRepository.findOne(userId);
				profile.setWaitCheckDate(new Date());
				profileRepository.save(profile);
			}
			profileRepository.updateRelatedId(userId, comId, uploadTag, new Date());
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_UPDATE,
					"profile里添加企业用户Id[userId=" + userId + ",comId=" + comId + ",uploadTag=" + uploadTag + "]",
					IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("true");
		} catch (Exception e) {
			logger.warn("profile里添加企业用户Id失败：" + e);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_UPDATE,
					"profile里添加企业用户Id[userId=" + userId + ",comId=" + comId + ",uploadTag=" + uploadTag + "]",
					IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache())
					.body("false");
		}

	}

	/**
	 * 完善基本信息上传头像
	 * @param userId
	 * @param avatorUrl（userId + "." + imgType）
	 * @return
	 */
	@Transactional(readOnly = false)
	@PostMapping(value = "/avatorUrl")
	public ResponseEntity<String> uploadAvator(String imgType, HttpServletRequest request) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		try {
			Profile profile = profileRepository.findOne(userId);
			if (profile == null) {
				Profile pf = new Profile();
				pf.setUserId(userId);
				pf.setAvatorUrl(userId + "." + imgType);
				profileRepository.save(pf);
			} else {
				profile.setAvatorUrl(userId + "." + imgType);
				profileRepository.save(profile);
			}
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_UPDATE, "上传头像",
					IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			logger.warn("完善基本信息上传头像失败：" + e);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_UPDATE, "上传头像",
					IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}

	}

	/**
	 * 完善学历信息
	 * @param schoolCode 学校代码
	 * @param department 院系名称
	 * @param eduLevel 学历
	 * @param zydm 专业代码
	 * @param majorCode 专业类别
	 * @param majorName 专业类别名称
	 * @param startSchoolDate 开始时间
	 * @param endSchoolDate 结束时间
	 * @param minorCode 辅修专业代码
	 * @param minorName 辅修专业名
	 * @param authedStatus 学籍校验状态(0 未认证、 1 当前学籍通过认证、 2 学籍校验失败)
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/proEducation")
	@Transactional(readOnly = false, rollbackFor = { Exception.class })
	public ResponseEntity<Map<String, String>> addProfileeducation(String schoolId, String schoolName,
			String department, String eduLevel, String startschool, String endschool, String avatorUrl,
			String middleMajorCode, String middleMajorName, String middleMinorCode, String middleMinorName,
			String smallMajorCode, String smallMajorName, String smallMinorCode, String smallMinorName,
			String isForeignUniversity, HttpServletRequest request) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		Profile profile = profileRepository.findOne(userId);
		Map<String, String> result = new HashedMap();
		if (profile != null) {
			try {
				Information information = profile.getInformation();
				if ("true".equals(isForeignUniversity)) {
					schoolId = EducationHistory.FOREIGN_SCHOOLID;
					middleMajorCode = "";
					middleMinorCode = "";
					smallMajorCode = "";
					smallMinorCode = "";
				}
				information.setIsForeignUniversity(isForeignUniversity);
				information.setSchoolId(schoolId);
				information.setSchoolName(schoolName);
				information.setDepartment(department);
				information.setEduLevel(eduLevel);
				information.setStartDate(startschool);
				information.setEndDate(endschool);
				information.setMiddleMajorCode(middleMajorCode);
				information.setMiddleMajorName(middleMajorName);
				information.setMiddleMinorCode(middleMinorCode);
				information.setMiddleMinorName(middleMinorName);
				information.setSmallMajorCode(smallMajorCode);
				information.setSmallMajorName(smallMajorName);
				information.setSmallMinorCode(smallMinorCode);
				information.setSmallMinorName(smallMinorName);
				profile.setInformation(information);
				profile.setAvatorUrl(avatorUrl);
				profile.setUpdateDate(new Date());
				//同时修改profile中的authedStatus
				//profile.setAuthedStatus(authedStatus);
				profileRepository.save(profile);
				if (resumeRepository.findByProfileUserId(userId) == null) {
					//添加简历
					Resume resume = new Resume();
					resume.setProfile(profile);
					resumeRepository.save(resume);
					//添加教育背景
					EducationHistory education = new EducationHistory();
					education.setIsForeignUniversity(isForeignUniversity);
					education.setStartDate(startschool);
					education.setEndDate(endschool);
					education.setSchoolId(schoolId);
					education.setSchoolName(schoolName);
					education.setEduLevel(eduLevel);
					education.setDepartment(department);
					education.setMiddleMajorCode(middleMajorCode);
					education.setMiddleMajorName(middleMajorName);
					education.setMiddleMinorCode(middleMinorCode);
					education.setMiddleMinorName(middleMinorName);
					education.setSmallMajorCode(smallMajorCode);
					education.setSmallMajorName(smallMajorName);
					education.setSmallMinorCode(smallMinorCode);
					education.setSmallMinorName(smallMinorName);
					education.setIsKey("true");
					if (Profile.STUDENT_AS_PASS_01.equals(profile.getAuthedStatus())) {
						education.setIsAuthed("true");
					}
					education.setResume(resume);
					educationHistoryRepository.save(education);
					((ShiroUser) SecurityUtils.getSubject().getPrincipal()).setOrgId(schoolId);
					((ShiroUser) SecurityUtils.getSubject().getPrincipal()).setOrgName(schoolName);
					((ShiroUser) SecurityUtils.getSubject().getPrincipal()).setAvatorUrl(avatorUrl);
					/*int year = Calendar.getInstance().get(Calendar.YEAR);
					int endDate = Integer.parseInt(profile.getInformation().getEndDate().substring(0, 4));
					if ("1".equals(profile.getAuthedStatus()) && year == endDate) {//当前的用户是否是 学籍验证通过的当前年毕业 
						((ShiroUser) SecurityUtils.getSubject().getPrincipal()).setGraduationTag("true");
					}*/
					int year = Calendar.getInstance().get(Calendar.YEAR);
					int endDate = Integer.parseInt(profile.getInformation().getEndDate().substring(0, 4));
					if (year == endDate) {//当前的用户是否是当前年毕业 
						((ShiroUser) SecurityUtils.getSubject().getPrincipal()).setGraduationTag("true");
					}
					SecurityUtils.getSubject().runAs(SecurityUtils.getSubject().getPrincipals());
					userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_UPDATE,
							"完善学历信息[schoolId=" + schoolId + ",schoolName=" + schoolName + ",department=" + department
									+ ",eduLevel=" + eduLevel + ",middleMajorCode=" + middleMajorCode
									+ ",smallMajorCode=" + smallMajorCode + ",startschool=" + startschool
									+ ",endschool=" + endschool + ",middleMinorCode=" + middleMinorCode
									+ ",smallMinorCode=" + smallMinorCode + "]",
							IpUtils.getIpAddr(request), "true"));
				}
				result.put("result", "true");
				result.put("code", "");
				result.put("message", "");
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			} catch (Exception e) {
				logger.warn("完善学历信息异常" + e);
			}

		}
		result.put("result", "false");
		result.put("code", "error");
		result.put("message", "添加失败");
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);

	}

	/**
	 * 管理员转交权限（只有一个管理员时可以移交权限）
	 * @param generalUserId 被转交用户ID
	 * @return
	 */
	@Transactional(readOnly = false)
	@PostMapping(value = "/changePermission")
	public ResponseEntity<String> changePermission(String generalUserId, HttpServletRequest request) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		try {
			Profile profile = profileRepository.findOne(userId);
			//管理员转交权限（只有一个管理员时可以移交权限）
			if (profileRepository.findByRelatedId(profile.getRelatedId(), "5").size() == 1) {
				profileRepository.updateAuthed(userId, "3", new Date());//当前用户变为普通用户
				profileRepository.updateAuthed(generalUserId, "5", new Date());//变为管理员用户
				userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_UPDATE,
						"管理员转交权限,被转交的用户[userId=" + generalUserId + ",authedStatus=5]", IpUtils.getIpAddr(request),
						"true"));
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
			}
		} catch (Exception e) {
			logger.warn("管理员转交权限（只有一个管理员时可以移交权限）" + e);
		}
		userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_UPDATE,
				"管理员转交权限,被转交的[userId=" + generalUserId + ",authedStatus=5]", IpUtils.getIpAddr(request), "false"));
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
	}

	/**
	 * 修改用户权限
	 * @param userId 用户ID
	 * @param authedStatus 认证状态
	 * @return
	 */
	@Transactional(readOnly = false)
	@PostMapping(value = "/updateAuthed")
	public ResponseEntity<String> updateAuthed(String userId, String authedStatus, String message,
			HttpServletRequest request) {
		String adminuserId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		try {
			profileRepository.updateAuthed(userId, authedStatus, new Date());
			userActionLogHandler.saveLog(new UserActionLog(adminuserId, null, ActionType.PROFILE_UPDATE,
					"修改用户权限[message=" + message + ",userId=" + userId + ",authedStatus=" + authedStatus + "]",
					IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			logger.warn("修改用户权限异常" + e);
			userActionLogHandler.saveLog(new UserActionLog(adminuserId, null, ActionType.PROFILE_UPDATE,
					"修改用户权限[message=" + message + ",userId=" + userId + ",authedStatus=" + authedStatus + "]",
					IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}

	}

	@Transactional(readOnly = false)
	@PutMapping(value = "/updateMemberAuthed")
	public ResponseEntity<String> updateMemberAuthed(String userId, String authedStatus, Date checkValidDate,
			HttpServletRequest request) {
		String adminuserId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		try {
			Profile profile = profileRepository.findOne(userId);
			profile.setAuthedStatus(authedStatus);
			profile.setUpdateDate(new Date());
			profile.setCheckDate(new Date());
			checkValidDate = new Date(checkValidDate.getTime() + 1 * 24 * 3599 * 1000);
			profile.setCheckValidDate(checkValidDate);
			profileRepository.save(profile);
			userActionLogHandler.saveLog(new UserActionLog(adminuserId, null, ActionType.PROFILE_UPDATE,
					"企业管理员审核公司同事为普通用户[userId=" + userId + ",authedStatus=" + authedStatus + "]",
					IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			logger.warn("企业管理员审核公司同事为普通用户异常" + e);
			userActionLogHandler.saveLog(new UserActionLog(adminuserId, null, ActionType.PROFILE_UPDATE,
					"企业管理员审核公司同事为普通用户[userId=" + userId + ",authedStatus=" + authedStatus + "]",
					IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}

	}

	/**
	 * 普通用户申请成为管理员
	 * @param authedStatus
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = false)
	@PostMapping(value = "/changeToAdmin")
	public ResponseEntity<String> changeToAdmin(String authedStatus, HttpServletRequest request) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		try {
			profileRepository.updateAuthed(userId, authedStatus, new Date());
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_UPDATE,
					"普通用户申请成为管理员[userId=" + userId + ",authedStatus=" + authedStatus + "]", IpUtils.getIpAddr(request),
					"true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_UPDATE,
					"普通用户申请成为管理员[userId=" + userId + ",authedStatus=" + authedStatus + "]", IpUtils.getIpAddr(request),
					"false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}

	}

	/**
	 * 修改状态【用户上传材料想认证管理员或普通用户(认证管理员（2）、认证普通用户标识（1）)】
	 * @param uploadTag
	 * @return
	 */
	//TODO 暂时弃用
	@Transactional(readOnly = false)
	@PostMapping(value = "/uploadTag")
	public ResponseEntity<String> uploadTag(String uploadTag, String authedStatus, HttpServletRequest request) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		try {
			profileRepository.uploadTag(userId, uploadTag, authedStatus, new Date());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}

	}

	/**
	 * 企业管理员删除普通用户（解绑）/企业管理员审核不通过/用户自己解绑
	 * @param generalUserId 被删除的普通用户ID
	 * @return
	 */
	@Transactional(readOnly = false)
	@PostMapping(value = "/unbundlgeneraluser")
	public ResponseEntity<String> unbundlgeneraluser(String generalUserId, String popTag, HttpServletRequest request) {
		String adminuserId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		String content = "";
		try {
			profileRepository.delete(generalUserId);
			//用户累计被举报数更新
			userAccountRepository.updateBeReportedCountBeforeById(generalUserId);
			//删除上传得认证图片,暂不删除图片 TODO
			//String delPath = corpUploadPath + generalUserId.substring(0, 2) + "/" + generalUserId.substring(2, 4) + "/";
			//ImageUtil.deleteFolder(delPath);
			if ("del".equals(popTag)) {
				content = "企业管理员删除普通用户（解绑）";
			} else if ("nopass".equals(popTag)) {
				content = "企业管理员审核不通过";
			}
			userActionLogHandler.saveLog(new UserActionLog(adminuserId, null, ActionType.PROFILE_DELETE,
					content + "[userId=" + generalUserId + ", popTag=" + popTag + "]", IpUtils.getIpAddr(request),
					"true"));
			userActionLogHandler.saveLog(new UserActionLog(generalUserId, null, ActionType.USER_UPDATE, "用户累计被举报数更新",
					IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			userActionLogHandler.saveLog(new UserActionLog(adminuserId, null, ActionType.PROFILE_DELETE,
					content + "[userId=" + generalUserId + ", popTag=" + popTag + "]", IpUtils.getIpAddr(request),
					"false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	/**
	 * 普通企业用户与公司解绑
	 * 多个管理员时，管理员可以与公司解绑
	 * @return
	 */
	@Transactional(readOnly = false)
	@PostMapping(value = "/unbundlOwn")
	public ResponseEntity<String> unbundlOwn(HttpServletRequest request) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		Profile profile = profileRepository.findOne(userId);
		try {
			//多个管理员时，管理员可以与公司解绑 Profile.CORP_ADMINUSER=5
			if (Profile.CORP_ADMINUSER_5.equals(profile.getAuthedStatus())
					&& profileRepository.findByRelatedId(profile.getRelatedId(), Profile.CORP_ADMINUSER_5).size() < 2) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
			}
			profileRepository.delete(userId);
			Subject currentUser = SecurityUtils.getSubject();
			ShiroUser user = ((ShiroUser) SecurityUtils.getSubject().getPrincipal());
			user.setAvatorUrl("");
			user.setOrgId("");
			user.setOrgName("");
			user.setRealName("");
			currentUser.runAs(currentUser.getPrincipals());
			//删除上传得认证图片,暂不删除图片 TODO
			//String delPath = corpUploadPath + userId.substring(0, 2) + "/" + userId.substring(2, 4) + "/";
			//ImageUtil.deleteFolder(delPath);
			//用户累计被举报数更新
			userAccountRepository.updateBeReportedCountBeforeById(userId);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_DELETE,
					"企业用户与公司解绑[userId=" + userId + "]", IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_DELETE,
					"企业用户与公司解绑[userId=" + userId + "]", IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}

	}

	//manager删除公司，同时删除公司下的所有用户
	@Transactional(readOnly = false)
	@DeleteMapping(value = "/unbundlCorpUsers")
	public ResponseEntity<String> unbundlCorpUsers(String comId, HttpServletRequest request) {
		try {
			String[] id = comId.replace("，", ",").split(",");
			if (id.length == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
			}
			for (int i = 0; i < id.length; i++) {
				List<Profile> profiles = profileRepository.findByRelatedId(id[i], "");
				for (Profile p : profiles) {
					profileRepository.delete(p.getUserId());
					//删除用户头像,暂不删除图片 TODO
				}
			}
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	/**
	 * 修改commore值
	 * @param userId
	 * @param commore 是否上传认证其他图片标识
	 * @return
	 */
	@Transactional(readOnly = false)
	@PutMapping(value = "/commore")
	public ResponseEntity<String> updateCommore(String userId, String commore) {
		if (!StringUtils.hasText(userId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}
		try {
			profileRepository.updateCommore(userId, commore);
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	/**
	 * 
	 * @param relatedId 公司ID
	 * @param companyName 公司Name
	 * @return
	 */
	@Transactional(readOnly = false)
	@PutMapping(value = "/updateCompanyName")
	public ResponseEntity<String> updateCompanyName(String relatedId, String companyName) {
		if (!StringUtils.hasText(relatedId) || !StringUtils.hasText(companyName)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}
		try {
			profileRepository.updateCompanyName(relatedId, companyName);
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

}
