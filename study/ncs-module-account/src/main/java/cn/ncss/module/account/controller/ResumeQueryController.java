package cn.ncss.module.account.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.ncss.commom.ShiroUser;
import cn.ncss.module.account.domain.EducationHistory;
import cn.ncss.module.account.domain.ItemMeta;
import cn.ncss.module.account.domain.Resume;
import cn.ncss.module.account.repository.EducationHistoryRepository;
import cn.ncss.module.account.repository.ResumeRepository;

@Transactional(readOnly = true)
@RestController
public class ResumeQueryController {
	private static final Logger logger = LoggerFactory.getLogger(ResumeQueryController.class);
	@Autowired
	private ResumeRepository resumeRepository;
	@Autowired
	private EducationHistoryRepository educationHistoryRepository;

	/**
	 * 当前用户获取简历信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/resume")
	public ResponseEntity<Resume> getResumeByUserId() {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		Resume resume = resumeRepository.findByProfileUserId(userId);
		if (resume != null && resume.getItemMeta() != null) {
			//itemMeta排序
			Collections.sort(resume.getItemMeta(), new ComparatorItem());
			//教育背景排序
			Collections.sort(resume.getEducations(), new ComparatorEducation());
		}
		return ResponseEntity.ok(resume);
	}

	/**
	 * 根据用户ID获取简历信息
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/resumeDetail")
	public ResponseEntity<Resume> getResumeDetail(String userId) {
		Resume resume = resumeRepository.findByProfileUserId(userId);
		if (resume != null && resume.getItemMeta() != null) {
			//itemMeta排序
			Collections.sort(resume.getItemMeta(), new ComparatorItem());
			//教育背景排序
			Collections.sort(resume.getEducations(), new ComparatorEducation());
		}
		return ResponseEntity.ok(resume);
	}

	/**
	 * 根据简历ID获取简历信息
	 * @param resumeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/resume/{resumeId}")
	public ResponseEntity<Resume> getResume(@PathVariable String resumeId) {
		if (!resumeRepository.exists(resumeId)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		Resume resume = resumeRepository.findOne(resumeId);
		//itemMeta排序
		Collections.sort(resume.getItemMeta(), new ComparatorItem());
		//教育背景排序
		Collections.sort(resume.getEducations(), new ComparatorEducation());
		return ResponseEntity.ok(resume);
	}

	@SuppressWarnings("rawtypes")
	public class ComparatorItem implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			ItemMeta item0 = (ItemMeta) o1;
			ItemMeta item1 = (ItemMeta) o2;
			return item0.getSn().compareTo(item1.getSn());
		}

	}

	@SuppressWarnings("rawtypes")
	public class ComparatorEducation implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			EducationHistory edu0 = (EducationHistory) o1;
			EducationHistory edu1 = (EducationHistory) o2;
			return Integer.valueOf(edu0.getEduLevel()).compareTo(Integer.valueOf(edu1.getEduLevel()));
		}

	}

	/**
	 * 通过名字查询外国学校列表
	 * @param  
	 * @return
	 */
	@GetMapping(value = "/name")
	public ResponseEntity<List<Map<String, Object>>> getSchoolsList(String name) {
		List<Map<String, Object>> schools = educationHistoryRepository.findByNameContaining(name);
		return ResponseEntity.ok(schools);
	}

	/**
	 * 通过名字查询院系列表
	 * @param  
	 * @return
	 */
	@GetMapping(value = "/departmentList")
	public ResponseEntity<List<String>> getDepartmentList(String department, String schoolName,
			@RequestParam(defaultValue = "false") String isForeign) {
		List<String> departments = educationHistoryRepository.findByDepartmentAndSchoolName(department, schoolName,
				isForeign);
		return ResponseEntity.ok(departments);
	}

	/**
	 * 通过名字查询专业列表
	 * @param  
	 * @return
	 */
	@GetMapping(value = "/majorNameList")
	public ResponseEntity<List<String>> getMajorNameList(String majorName) {
		List<String> majorNames = educationHistoryRepository.findByMajorName(majorName);
		return ResponseEntity.ok(majorNames);
	}
}
