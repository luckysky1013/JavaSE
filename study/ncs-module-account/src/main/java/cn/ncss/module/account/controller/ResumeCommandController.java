package cn.ncss.module.account.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.map.HashedMap;
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
import cn.ncss.module.account.domain.EducationHistory;
import cn.ncss.module.account.domain.Information;
import cn.ncss.module.account.domain.ItemMeta;
import cn.ncss.module.account.domain.Profile;
import cn.ncss.module.account.domain.Resume;
import cn.ncss.module.account.domain.Target;
import cn.ncss.module.account.domain.UserActionLog;
import cn.ncss.module.account.domain.UserActionLog.ActionType;
import cn.ncss.module.account.handler.UserActionLogHandler;
import cn.ncss.module.account.repository.EducationHistoryRepository;
import cn.ncss.module.account.repository.ItemMetaRepository;
import cn.ncss.module.account.repository.ProfileRepository;
import cn.ncss.module.account.repository.ResumeRepository;
import cn.ncss.module.account.repository.TargetRepository;

/**
 * 简历控制层
 * @author liyang
 *
 */
@Transactional(readOnly = false)
@RestController
public class ResumeCommandController {
	private static final Logger logger = LoggerFactory.getLogger(ResumeCommandController.class);
	@Autowired
	private TargetRepository targetRepository;
	@Autowired
	private ResumeRepository resumeRepository;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ItemMetaRepository itemMetaRepository;
	@Autowired
	private UserActionLogHandler userActionLogHandler;
	@Autowired
	private EducationHistoryRepository educationHistoryRepository;

	//简历微调更新简历(求职期望、简历项)
	@PutMapping(value = "/updateResume")
	public ResponseEntity<String> updateResume(@RequestBody @Valid Resume resume, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}
		if (resume == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
		}
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		Resume resumeOld = resumeRepository.findByProfileUserId(userId);
		try {
			//resumeRepository.save(resume);
			Target target = resume.getTarget();
			if (!StringUtils.hasText(target.getTargrtId()) || targetRepository.findOne(target.getTargrtId()) == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
			}
			target.setResume(resumeOld);
			targetRepository.save(target);

			List<ItemMeta> items = resume.getItemMeta();
			for (ItemMeta item : items) {
				if (!StringUtils.hasText(item.getId()) || itemMetaRepository.findOne(item.getId()) == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).cacheControl(CacheControl.noCache()).body("");
				}
				item.setResume(resumeOld);
				itemMetaRepository.save(item);
			}
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.RESUME_UPDATE,
					"更新简历：[id=" + resume.getId() + "]", IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("简历微调更新简历(求职期望、简历项)失败" + e);
		}
		userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.RESUME_UPDATE,
				"更新简历：[id=" + resume.getId() + "]", IpUtils.getIpAddr(request), "false"));
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
	}

	/**
	 * 添加教育背景
	 * @param userId
	 * @param education
	 * @param isForeignUniversity 是否为外国大学
	 * @param bindingResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/education")
	public ResponseEntity<Map<String, String>> addEducation(@RequestBody @Valid EducationHistory education,
			BindingResult bindingResult, HttpServletRequest request) {
		Map<String, String> result = new HashedMap();
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		if (bindingResult.hasErrors()) {
			result.put("result", "false");
			result.put("code", bindingResult.getFieldError().getField());
			result.put("message", bindingResult.getFieldError().getDefaultMessage());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		Resume resume = resumeRepository.findByProfileUserId(userId);
		if (resume == null) {
			logger.info("add education error: could not find an entity according to the userId[{}].", userId);
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "未找到该简历");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		if (resume.getEducations().size() > 9) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "教育背景最多允许添加十条");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		EducationHistory edu = null;
		try {
			if ("true".equals(education.getIsForeignUniversity())) {
				education.setSchoolId(EducationHistory.FOREIGN_SCHOOLID);
				education.setMiddleMajorCode("");
				education.setMiddleMinorCode("");
				education.setSmallMajorCode("");
				education.setSmallMinorCode("");
			}
			education.setResume(resume);
			edu = educationHistoryRepository.save(education);
			result.put("result", "true");
			result.put("code", "");
			result.put("message", "");
			result.put("id", edu.getId());
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.EDUCATION_CREATE,
					"添加教育背景：[id=" + edu.getId() + "]", IpUtils.getIpAddr(request), "true"));
		} catch (Exception e) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "操作失败");
			logger.warn("add education error: the entity is null." + e);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.EDUCATION_CREATE,
					"添加教育背景：[id=" + edu.getId() + "]", IpUtils.getIpAddr(request), "false"));
		}
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);

	}

	/**
	 * 修改教育背景
	 * @param userId
	 * @param education
	 * @param bindingResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PutMapping(value = "/education")
	public ResponseEntity<Map<String, String>> updateEducation(@RequestBody @Valid EducationHistory education,
			BindingResult bindingResult, HttpServletRequest request) {
		Map<String, String> result = new HashedMap();
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		if (bindingResult.hasErrors()) {
			result.put("result", "false");
			result.put("code", bindingResult.getFieldError().getField());
			result.put("message", bindingResult.getFieldError().getDefaultMessage());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		Resume resume = resumeRepository.findByProfileUserId(userId);
		if (resume == null) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "未找到该简历");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		EducationHistory edu = educationHistoryRepository.findOne(education.getId());
		if (!StringUtils.hasText(education.getId()) || edu == null) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "未找到该教育背景");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		//学籍验证通过不允许修改
		if (edu.getIsAuthed().equals("true")) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "不能修改");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		//学籍验证未通过且是基本信息冗余过来的数据，学校名称和学历层次不允许修改，其他数据修改时更新profile表
		if (edu.getIsAuthed().equals("false") && edu.getIsKey().equals("true")) {
			education.setSchoolId(edu.getSchoolId());
			education.setSchoolName(edu.getSchoolName());
			education.setEduLevel(edu.getEduLevel());
			education.setIsKey(edu.getIsKey());
			education.setIsAuthed(edu.getIsAuthed());
			education.setIsForeignUniversity(edu.getIsForeignUniversity());
			//更新profile表
			if ("true".equals(education.getIsForeignUniversity())) {
				education.setMiddleMajorCode("");
				education.setMiddleMinorCode("");
				education.setSmallMajorCode("");
				education.setSmallMinorCode("");
			}
			Profile profile = resume.getProfile();
			Information info = profile.getInformation();
			info.setStartDate(education.getStartDate());
			info.setEndDate(education.getEndDate());
			info.setDepartment(education.getDepartment());
			info.setMiddleMajorCode(education.getMiddleMajorCode());
			info.setMiddleMajorName(education.getMiddleMajorName());
			info.setMiddleMinorCode(education.getMiddleMinorCode());
			info.setMiddleMinorName(education.getMiddleMinorName());
			info.setSmallMajorCode(education.getSmallMajorCode());
			info.setSmallMajorName(education.getSmallMajorName());
			info.setSmallMinorCode(education.getSmallMinorCode());
			info.setSmallMinorName(education.getSmallMinorName());
			info.setIsForeignUniversity(education.getIsForeignUniversity());
			profile.setInformation(info);
			profile.setUpdateDate(new Date());
			profileRepository.save(profile);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int endDate = Integer.parseInt(profile.getInformation().getEndDate().substring(0, 4));
			if (year == endDate) {//当前的用户是否是当前年毕业 
				((ShiroUser) SecurityUtils.getSubject().getPrincipal()).setGraduationTag("true");
			} else {
				((ShiroUser) SecurityUtils.getSubject().getPrincipal()).setGraduationTag("false");
			}
			SecurityUtils.getSubject().runAs(SecurityUtils.getSubject().getPrincipals());
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.PROFILE_UPDATE,
					"修改教育背景冗余数据时，同时修改profile中的数据：[id=" + profile.getUserId() + "]", IpUtils.getIpAddr(request),
					"true"));

		}
		//不是profile冗余的数据，判断是否选外国大学
		if (edu.getIsAuthed().equals("false") && "true".equals(education.getIsForeignUniversity())
				&& edu.getIsKey().equals("false")) {
			education.setSchoolId(EducationHistory.FOREIGN_SCHOOLID);
			education.setMiddleMajorCode("");
			education.setMiddleMinorCode("");
			education.setSmallMajorCode("");
			education.setSmallMinorCode("");
		}
		education.setResume(resume);
		try {
			educationHistoryRepository.save(education);
			result.put("result", "true");
			result.put("code", "");
			result.put("message", "");
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.EDUCATION_UPDATE,
					"修改教育背景：[id=" + edu.getId() + "]", IpUtils.getIpAddr(request), "true"));
		} catch (Exception e) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "操作失败");
			e.printStackTrace();
			logger.warn("修改教育背景失败" + e);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.EDUCATION_UPDATE,
					"修改教育背景：[id=" + edu.getId() + "]", IpUtils.getIpAddr(request), "false"));
		}
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
	}

	/**
	 * 删除教育背景
	 * @param educationId
	 * @return
	 */
	@DeleteMapping(value = "/education")
	public ResponseEntity<String> deleteEducation(String educationId, HttpServletRequest request) {
		if (!StringUtils.hasText(educationId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		EducationHistory edu = educationHistoryRepository.findOne(educationId);
		if ("true".equals(edu.getIsKey())) {
			//不允许删除
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		try {
			educationHistoryRepository.delete(educationId);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.EDUCATION_DELETE,
					"删除教育背景：[id=" + educationId + "]", IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("delete education error:   EducationHistory[id={}].", educationId);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.EDUCATION_DELETE,
					"删除教育背景：[id=" + educationId + "]", IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	/**
	 * 添加求职期望
	 * @param userId
	 * @param target
	 * @param bindingResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/target")
	public ResponseEntity<Map<String, String>> addTarget(@RequestBody @Valid Target target, BindingResult bindingResult,
			HttpServletRequest request) {
		Map<String, String> result = new HashedMap();
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		if (bindingResult.hasErrors()) {
			result.put("result", "false");
			result.put("code", bindingResult.getFieldError().getField());
			result.put("message", bindingResult.getFieldError().getDefaultMessage());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		Resume resume = resumeRepository.findByProfileUserId(userId);
		if (resume == null) {
			logger.info("add target error: could not find an entity according to the userId[{}].", userId);
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "未找到该简历");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		if (resume.getTarget() != null) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "求职期望已存在");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		target.setResume(resume);
		Target tar = null;
		try {
			tar = targetRepository.save(target);
			result.put("id", tar.getTargrtId());
			result.put("result", "true");
			result.put("code", "");
			result.put("message", "");
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.TARGET_CREATE,
					"添加求职期望：[id=" + tar.getTargrtId() + "]", IpUtils.getIpAddr(request), "true"));
		} catch (Exception e) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "操作失败");
			e.printStackTrace();
			logger.warn("添加求职期望失败" + e);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.TARGET_CREATE,
					"添加求职期望：[id=" + tar.getTargrtId() + "]", IpUtils.getIpAddr(request), "false"));
		}
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
	}

	/**
	 * 修改求职期望
	 * @param userId
	 * @param target
	 * @param bindingResult
	 * @return
	 */
	@PutMapping(value = "/target")
	public ResponseEntity<Map<String, String>> updateTarget(@RequestBody @Valid Target target,
			BindingResult bindingResult, HttpServletRequest request) {
		Map<String, String> result = new HashedMap();
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		if (bindingResult.hasErrors()) {
			result.put("result", "false");
			result.put("code", bindingResult.getFieldError().getField());
			result.put("message", bindingResult.getFieldError().getDefaultMessage());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		Resume resume = resumeRepository.findByProfileUserId(userId);
		if (resume == null) {
			logger.info("add target error: could not find an entity according to the userId[{}].", userId);
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "未找到该简历");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		if (!StringUtils.hasText(target.getTargrtId()) || targetRepository.findOne(target.getTargrtId()) == null) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "未找到该求职期望");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		target.setResume(resume);
		try {
			targetRepository.save(target);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.TARGET_UPDATE,
					"修改求职期望：[id=" + target.getTargrtId() + "]", IpUtils.getIpAddr(request), "true"));
			result.put("result", "true");
			result.put("code", "");
			result.put("message", "");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("修改求职期望失败" + e);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.TARGET_UPDATE,
					"修改求职期望：[id=" + target.getTargrtId() + "]", IpUtils.getIpAddr(request), "false"));
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "操作失败");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
	}

	/**
	 * 添加简历项
	 * @param userId
	 * @param target
	 * @param bindingResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/itemMeta")
	public ResponseEntity<Map<String, String>> addItemMeta(@RequestBody @Valid ItemMeta itemMeta,
			BindingResult bindingResult, HttpServletRequest request) {
		Map<String, String> result = new HashedMap();
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		if (bindingResult.hasErrors()) {
			result.put("result", "false");
			result.put("code", bindingResult.getFieldError().getField());
			result.put("message", bindingResult.getFieldError().getDefaultMessage());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		Resume resume = resumeRepository.findByProfileUserId(userId);
		if (resume == null) {
			logger.info("add target error: could not find an entity according to the userId[{}].", userId);
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "简历不存在");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		for (ItemMeta item : resume.getItemMeta()) {
			if (item.getLabel().equals(itemMeta.getLabel())) {
				result.put("result", "false");
				result.put("code", "error");
				result.put("message", itemMeta.getLabel() + "已存在");
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
			}
		}
		ItemMeta it = null;
		try {
			itemMeta.setResume(resume);
			it = itemMetaRepository.save(itemMeta);
			result.put("result", "true");
			result.put("code", "");
			result.put("message", "");
			result.put("id", it.getId());
			result.put("sn", it.getSn().toString());
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.ITEMMETA_CREATE,
					"添加简历项：[id=" + it.getId() + "]", IpUtils.getIpAddr(request), "true"));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "操作失败");
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.ITEMMETA_CREATE,
					"添加简历项：[id=" + it.getId() + "]", IpUtils.getIpAddr(request), "false"));
			logger.warn("添加简历项失败" + e);
		}
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
	}

	/**
	 * 修改简历项
	 * @param userId
	 * @param itemMeta
	 * @param bindingResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PutMapping(value = "/itemMeta")
	public ResponseEntity<Map<String, String>> updateItemMeta(@RequestBody @Valid ItemMeta itemMeta,
			BindingResult bindingResult, HttpServletRequest request) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		Map<String, String> result = new HashedMap();
		if (bindingResult.hasErrors()) {
			result.put("result", "false");
			result.put("code", bindingResult.getFieldError().getField());
			result.put("message", bindingResult.getFieldError().getDefaultMessage());
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		Resume resume = resumeRepository.findByProfileUserId(userId);
		if (resume == null) {
			logger.info("add target error: could not find an entity according to the userId[{}].", userId);
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "简历不存在");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		if (!StringUtils.hasText(itemMeta.getId()) || itemMetaRepository.findOne(itemMeta.getId()) == null) {
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "简历项不存在");
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
		}
		try {
			itemMeta.setResume(resume);
			itemMetaRepository.save(itemMeta);
			result.put("result", "true");
			result.put("code", "");
			result.put("message", "");
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.ITEMMETA_UPDATE,
					"修改简历项：[id=" + itemMeta.getId() + "]", IpUtils.getIpAddr(request), "true"));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "false");
			result.put("code", "error");
			result.put("message", "操作失败");
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.ITEMMETA_UPDATE,
					"修改简历项：[id=" + itemMeta.getId() + "]", IpUtils.getIpAddr(request), "false"));
			logger.warn("修改简历项失败" + e);
		}
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body(result);
	}

	/**
	 * 更新简历项顺序
	 * @param id1
	 * @param sn1
	 * @param id2
	 * @param sn2
	 * @return
	 */
	@PutMapping(value = "/itemMetaSN")
	public ResponseEntity<String> updateItemMetaSn(String id1, String sn1, String id2, String sn2,
			HttpServletRequest request) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		Resume resume = resumeRepository.findByProfileUserId(userId);
		if (resume == null) {
			logger.info("add target error: could not find an entity according to the userId[{}].", userId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到该用户简历信息");
		}
		if (!StringUtils.hasText(id1) || itemMetaRepository.findOne(id1) == null || !StringUtils.hasText(id2)
				|| itemMetaRepository.findOne(id2) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到该条简历项");
		}
		try {
			itemMetaRepository.updateSn(id1, Integer.parseInt(sn1));
			itemMetaRepository.updateSn(id2, Integer.parseInt(sn2));
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.ITEMMETA_UPDATE,
					"修改简历项顺序：[id1=" + id1 + ",sn1=" + sn1 + ",id2=" + id2 + ",sn2=" + sn2 + "]",
					IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("更新简历项顺序失败" + e);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.ITEMMETA_UPDATE,
					"修改简历项顺序：[id1=" + id1 + ",sn1=" + sn1 + ",id2=" + id2 + ",sn2=" + sn2 + "]",
					IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	/**
	 * 删除简历项
	 * @param educationId
	 * @return
	 */
	@DeleteMapping(value = "/itemMeta")
	public ResponseEntity<String> deleteItemMeta(String itemMetaId, HttpServletRequest request) {
		if (!StringUtils.hasText(itemMetaId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		try {
			itemMetaRepository.delete(itemMetaId);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.ITEMMETA_DELETE,
					"删除简历项：[id=" + itemMetaId + "]", IpUtils.getIpAddr(request), "true"));
			return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("delete ItemMeta error:   ItemMeta[id={}].", itemMetaId);
			userActionLogHandler.saveLog(new UserActionLog(userId, null, ActionType.ITEMMETA_DELETE,
					"删除简历项：[id=" + itemMetaId + "]", IpUtils.getIpAddr(request), "false"));
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
		}
	}

	/**
	 * 添加或修改简历其他项
	 * @param userId
	 * @param name
	 * @param content
	 * @return
	 */
	/*@PostMapping(value = "/item")
	public ResponseEntity<String> addOrUpdateItem(String name, String content) {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		Resume resume = resumeRepository.findByProfileUserId(userId);
		if (resume == null) {
			logger.info("add resume error: could not find an entity according to the userId[{}].", userId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		if (StringUtils.hasText(name) && StringUtils.hasText(name)) {
			Map<String, String> item = new HashMap<String, String>();
			item.putAll(resume.getOtherItems()); // 先将原来的简历项添加进去
			item.put(name, content);
			resume.setOtherItems(item); // 设置
			try {
				resumeRepository.save(resume);
				return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache())
						.body("");
			}
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
	}
	
	*//**
		* 删除简历其他项
		* @param userId
		* @param key
		* @return
		*/

	/*
	@DeleteMapping(value = "/{key}")
	public ResponseEntity<String> deleteItem(@PathVariable String key) {
	String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
	if (!StringUtils.hasText(key)) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	Resume resume = resumeRepository.findByProfileUserId(userId);
	if (resume == null) {
		logger.info("add resume error: could not find an entity according to the userId[{}].", userId);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	try {
		Map<String, String> map = resume.getOtherItems();
		map.remove(key);// 根据key删除对应的项
		resume.setOtherItems(map);
		resumeRepository.save(resume);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
	} catch (Exception e) {
		e.printStackTrace();
		logger.error("delete item error:   item [key={}].", key);
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
	}
	}
	
	*//**
		* 添加修改简历单个字段
		* @param userId
		* @param property
		* @param value
		* @return
		* @throws SecurityException 
		* @throws NoSuchMethodException 
		* @throws InvocationTargetException 
		* @throws IllegalArgumentException 
		* @throws IllegalAccessException 
		* @throws NumberFormatException 
		*/
	/*
	@PostMapping(value = "/resume")
	public ResponseEntity<String> addOrUpdateResume(@RequestParam("property") String property,
		@RequestParam String value) throws NoSuchMethodException, SecurityException, NumberFormatException,
		IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
	Resume resume = resumeRepository.findByProfileUserId(userId);
	if (resume == null) {
		logger.info("add resume error: could not find an entity according to the userId[{}].", userId);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	String res = validateValue(property, value);
	if (res != null) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	List<String> list = null;
	//获得对应参数的setter方法
	Method method = getMethod(resume, property, value, list);
	//通过反射执行setter方法，为resume赋值
	resume = invoke(resume, property, value, list, method);
	try {
		resumeRepository.save(resume);
		return ResponseEntity.status(HttpStatus.OK).cacheControl(CacheControl.noCache()).body("");
	} catch (Exception e) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).cacheControl(CacheControl.noCache()).body("");
	}
	
	}
	
	//反射获取method
	private Method getMethod(Resume resume, String property, String value, List<String> list)
		throws NoSuchMethodException, SecurityException {
	Method method = resume.getClass().getMethod(
			"set" + property.substring(0, 1).toUpperCase() + property.substring(1, property.length()),
			list == null ? value.getClass() : List.class);
	return method;
	}
	
	//反射执行setter方法
	private Resume invoke(Resume resume, String property, String value, List<String> list, Method method)
		throws NumberFormatException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	method.invoke(resume, list == null ? value : list);
	return resume;
	}
	
	private String validateValue(String property, String value) {
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	Set<ConstraintViolation<Class<Resume>>> sets = validator.validateProperty(Resume.class, property);
	if (!sets.isEmpty()) {
		return sets.iterator().next().getMessage();
	}
	Set<ConstraintViolation<Resume>> res = validator.validateValue(Resume.class, property, value);
	if (!res.isEmpty()) {
		return res.iterator().next().getMessage();
	}
	return null;
	}*/

}
