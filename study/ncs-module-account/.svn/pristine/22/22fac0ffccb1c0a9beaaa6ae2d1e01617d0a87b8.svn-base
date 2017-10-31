package cn.ncss.module.account.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.ncss.commom.PubCodeFactory;
import cn.ncss.common.utils.CommonUtils;
import cn.ncss.module.account.domain.EducationHistory;
import cn.ncss.module.account.domain.Information;
import cn.ncss.module.account.domain.Profile;
import cn.ncss.module.account.domain.Resume;
import cn.ncss.module.account.domain.manager.AccountUsers;
import cn.ncss.module.account.domain.manager.AccountUsersSearchResult;
import cn.ncss.module.account.repository.AccountUsersQueryRepository;
import cn.ncss.module.account.repository.AccountUsersRepository;
import cn.ncss.module.account.repository.EducationHistoryRepository;
import cn.ncss.module.account.repository.ProfileRepository;
import cn.ncss.module.account.repository.ResumeRepository;

/**
 * 后台manager管理学生用户
 * @author liyang
 *
 */
@RestController
public class ManagerStudentController {
	private static final Logger logger = LoggerFactory.getLogger(ManagerStudentController.class);
	@Autowired
	private AccountUsersQueryRepository accountUsersQueryRepository;

	@Autowired
	private AccountUsersRepository userAccountRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private EducationHistoryRepository educationRepository;

	@Autowired
	private ResumeRepository resumeRepository;

	/**
	 * 获取学生用户列表
	 * @param eduLevel
	 * @param province
	 * @param city
	 * @param graduationTime
	 * @param authedStatus
	 * @param idCard
	 * @param schoolName
	 * @param majorCode
	 * @param realName 姓名、手机号或邮箱
	 * @param pageSize
	 * @param offset
	 * @return
	 */
	@Transactional(readOnly = true)
	@GetMapping(value = "/accountUserlist")
	public ResponseEntity<AccountUsersSearchResult> search(String eduLevel, String divisionCode, String graduationTime,
			String authedStatus, String idCard, String schoolName, @RequestParam(defaultValue = "") String majorName,
			String realName, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "0") int offset) {
		List<Object[]> users = accountUsersQueryRepository.findList(eduLevel, divisionCode, graduationTime,
				authedStatus, idCard, schoolName, majorName, realName, offset * pageSize + 1, pageSize * (offset + 1));
		long total = accountUsersQueryRepository.count(eduLevel, divisionCode, graduationTime, authedStatus, idCard,
				schoolName, majorName, realName);
		List<AccountUsers> accountUsers = new ArrayList<AccountUsers>();
		for (Object[] user : users) {
			AccountUsers acc = new AccountUsers();
			acc.setUserId(user[0] + "");
			acc.setMobilePhone(user[1] + "");
			acc.setEmail(user[2] + "");
			acc.setRealName(user[3] + "");
			acc.setEduLevel(PubCodeFactory.getInstance().getEdulevelCode().getProperty(user[4] + ""));
			acc.setSchoolId(user[5] + "");
			acc.setSchoolName(user[6] + "");
			acc.setMajorCode(user[7] + "");
			acc.setMajorName(user[8] + "");
			acc.setEndDate(user[9] + "");
			acc.setProvince(user[10] + "");
			//acc.setCity(user[11] + "");
			acc.setAuthedStatus(user[11] + "");
			accountUsers.add(acc);
		}
		AccountUsersSearchResult accResult = new AccountUsersSearchResult();
		accResult.setAccountUsers(accountUsers);
		accResult.setTotal(total);
		accResult.setLimit(pageSize);
		accResult.setOffset(offset);
		return ResponseEntity.ok(accResult);
	}

	/**
	 * 无信息列表
	 * @param realName
	 * @param pageSize
	 * @param offset
	 * @return
	 */
	@Transactional(readOnly = true)
	@GetMapping(value = "/accountUserlistNo")
	public ResponseEntity<AccountUsersSearchResult> searchNo(String realName,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "0") int offset) {
		List<Object[]> users = accountUsersQueryRepository.findListNoInformation(realName, offset * pageSize + 1,
				pageSize * (offset + 1));
		long total = accountUsersQueryRepository.countNoInformation(realName);
		List<AccountUsers> accountUsers = new ArrayList<AccountUsers>();
		for (Object[] user : users) {
			AccountUsers acc = new AccountUsers();
			acc.setUserId(user[0] + "");
			acc.setMobilePhone(user[1] + "");
			acc.setEmail(user[2] + "");
			accountUsers.add(acc);
		}
		AccountUsersSearchResult accResult = new AccountUsersSearchResult();
		accResult.setAccountUsers(accountUsers);
		accResult.setTotal(total);
		accResult.setLimit(pageSize);
		accResult.setOffset(offset);
		return ResponseEntity.ok(accResult);
	}

	/**
	 * 删除、批量删除用户(只注册无数据的用户)
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false)
	@DeleteMapping(value = "/deleteuser")
	public ResponseEntity<String> deleteuser(String userIds) {
		try {
			String[] userId = userIds.replace("，", ",").split(",");
			if (userId.length == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
			}
			for (int i = 0; i < userId.length; i++) {
				Profile pro = profileRepository.findOne(userId[i]);
				if (pro != null) {
					continue;
					//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
				}
				userAccountRepository.deleteByUserId(userId[i]);
			}
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("后台manager删除学生用户异常" + e);
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	//修改后台管理学生用户基本信息 updateStudentUser
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	@PutMapping(value = "/updateStudentUser")
	public ResponseEntity<Map<String, String>> updateStudentUser(@RequestBody @Valid Profile profile,
			BindingResult bindingResult) {
		Map<String, String> result = new HashedMap();
		try {
			if (bindingResult.hasErrors()) {
				result.put("result", "false");
				result.put("code", bindingResult.getFieldError().getField());
				result.put("message", bindingResult.getFieldError().getDefaultMessage());
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			}
			if (!profileRepository.exists(profile.getUserId())) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			String email = profile.getInformation().getEmail() == "" ? null : profile.getInformation().getEmail();
			String mobile = profile.getInformation().getMobilePhone() == "" ? null
					: profile.getInformation().getMobilePhone();
			if (!StringUtils.hasText(email) && !StringUtils.hasText(mobile)) {
				result.put("result", "false");
				result.put("code", "error");
				result.put("message", "手机号和邮箱都不存在");
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
			Profile old = profileRepository.findOne(profile.getUserId());
			Information inf = old.getInformation();
			//old.setRealName(profile.getRealName());
			old.setGender(profile.getGender());
			//inf.setIdCard(profile.getInformation().getIdCard());
			//inf.setIdType(profile.getInformation().getIdType());
			inf.setBirth(profile.getInformation().getBirth());
			inf.setNation(profile.getInformation().getNation());
			inf.setDivisionCode(profile.getInformation().getDivisionCode());
			inf.setIdentity(profile.getInformation().getIdentity());
			inf.setMobilePhone(profile.getInformation().getMobilePhone());
			inf.setEmail(profile.getInformation().getEmail());
			old.setInformation(inf);
			old.setUpdateDate(new Date());
			profileRepository.save(old);
			//手机号邮箱若修改，同步到accountuser表
			String isValidEmail = "false";
			String isValidPhone = "false";
			if (StringUtils.hasText(email)) {
				isValidEmail = "true";
			}
			if (StringUtils.hasText(mobile)) {
				isValidPhone = "true";
			}
			userAccountRepository.updateEmail(old.getUserId(), old.getInformation().getEmail(), isValidEmail);
			userAccountRepository.updateMobilePhone(old.getUserId(), old.getInformation().getMobilePhone(),
					isValidPhone);
			result.put("result", "true");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("修改后台管理学生用户基本信息异常" + e);
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "修改失败");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
	}

	//后台修改学生用户教育信息
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	@PutMapping(value = "/updateEducation")
	public ResponseEntity<Map<String, String>> updateEducation(@RequestBody @Valid EducationHistory edu,
			BindingResult bindingResult) {
		Map<String, String> result = new HashedMap();
		if (bindingResult.hasErrors()) {
			result.put("result", "false");
			result.put("code", bindingResult.getFieldError().getField());
			result.put("message", bindingResult.getFieldError().getDefaultMessage());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		try {
			if (!educationRepository.exists(edu.getId())) {
				result.put("result", "false");
				result.put("code", "error");
				result.put("message", "教育背景不存在");
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			}
			EducationHistory old = educationRepository.findOne(edu.getId());
			edu.setCreateDate(old.getCreateDate());
			edu.setUpdateDate(new Date());
			edu.setIsAuthed(old.getIsAuthed());
			edu.setIsKey(old.getIsKey());
			edu.setResume(old.getResume());
			//已认证学籍的不能修改学历层次和学校//已认证的不会出现外国大学
			if (edu.getIsAuthed().equals("true")) {
				edu.setEduLevel(old.getEduLevel());
				edu.setSchoolName(old.getSchoolName());
				edu.setSchoolId(old.getSchoolId());
			}
			//未认证外国大学
			if (edu.getIsAuthed().equals("false") && "true".equals(edu.getIsForeignUniversity())) {
				edu.setSchoolId(EducationHistory.FOREIGN_SCHOOLID);
				edu.setMiddleMajorCode("");
				edu.setMiddleMinorCode("");
				edu.setSmallMajorCode("");
				edu.setSmallMinorCode("");
			}
			educationRepository.save(edu);
			if ("true".equals(edu.getIsKey())) {
				//修改基本信息冗余学籍数据
				Profile profile = profileRepository.findOne(old.getResume().getProfile().getUserId());
				Information info = profile.getInformation();
				info.setEduLevel(edu.getEduLevel());
				info.setSchoolId(edu.getSchoolId());
				info.setSchoolName(edu.getSchoolName());
				info.setStartDate(edu.getStartDate());
				info.setEndDate(edu.getEndDate());
				info.setDepartment(edu.getDepartment());
				info.setMiddleMajorCode(edu.getMiddleMajorCode());
				info.setMiddleMajorName(edu.getMiddleMajorName());
				info.setMiddleMinorCode(edu.getMiddleMinorCode());
				info.setMiddleMinorName(edu.getMiddleMinorName());
				info.setSmallMajorCode(edu.getSmallMajorCode());
				info.setSmallMajorName(edu.getSmallMajorName());
				info.setSmallMinorCode(edu.getSmallMinorCode());
				info.setSmallMinorName(edu.getSmallMinorName());
				profile.setInformation(info);
				profile.setUpdateDate(new Date());
				profileRepository.save(profile);
			}
			result.put("result", "true");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("后台修改学生用户教育信息异常" + e);
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "修改失败");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
	}

	//学籍验证通过
	@Transactional(readOnly = false)
	@PutMapping(value = "/passStatus")
	public ResponseEntity<String> passStatus(String userId, HttpServletRequest request) {
		Resume resume = resumeRepository.findByProfileUserId(userId);
		if (resume == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}
		try {
			EducationHistory edu = educationRepository.findByIsKeyAndResumeId("true", resume.getId());
			edu.setIsAuthed("true");
			educationRepository.save(edu);
			Profile pro = profileRepository.findOne(userId);
			pro.setAuthedStatus(Profile.STUDENT_AS_PASS_01);
			profileRepository.save(pro);
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("后台学籍验证通过异常" + e);
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
	}

	@Transactional(readOnly = false)
	@DeleteMapping(value = "/deleteProfile/{userId}")
	public ResponseEntity<String> deleteProfile(@PathVariable String userId) {
		if (!StringUtils.hasText(userId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		try {
			Resume resume = resumeRepository.findByProfileUserId(userId);
			if (resume != null) {
				resumeRepository.delete(resume.getId());
			}
			profileRepository.deleteByUserId(userId);
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

}
