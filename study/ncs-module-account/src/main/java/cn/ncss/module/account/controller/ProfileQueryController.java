package cn.ncss.module.account.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ncss.commom.ShiroUser;
import cn.ncss.module.account.domain.Profile;
import cn.ncss.module.account.repository.ProfileRepository;

@Transactional(readOnly = true)
@RestController
public class ProfileQueryController {
	private static final Logger logger = LoggerFactory.getLogger(ProfileQueryController.class);
	@Autowired
	private ProfileRepository profileRepository;

	/**
	 * 获取用户基本信息(已登录)
	 * @return
	 */
	@GetMapping(value = "/profile")
	public ResponseEntity<Profile> getProfile() {
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		Profile profile = profileRepository.findOne(userId);
		return ResponseEntity.ok(profile);
	}

	/**
	 * 获取用户基本信息(未登录)
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "/profileByUserId")
	public ResponseEntity<Profile> getProfileByUserId(String userId) {
		Profile profile = profileRepository.findOne(userId);
		return ResponseEntity.ok(profile);
	}

	/**
	 * 查找同事
	 * @param relatedId 公司ID
	 * @param authedStatus 认证状态（企业）
	 * @return
	 */
	@GetMapping(value = "/profileList")
	public ResponseEntity<List<Profile>> getByRelatedId(String relatedId, String authedStatus) {
		List<Profile> profiles = profileRepository.findByRelatedId(relatedId, authedStatus);
		return ResponseEntity.ok(profiles);
	}

	/**
	 * 查找用户个数
	 * @param relatedId
	 * @param uploadTag
	 * @return
	 */
	@GetMapping(value = "/profileByUploadTag")
	public ResponseEntity<List<Profile>> getByRelatedIdAndUploadTag(String relatedId, String uploadTag) {
		List<Profile> profiles = profileRepository.findByRelatedIdAndUploadTag(relatedId, uploadTag);
		return ResponseEntity.ok(profiles);
	}

	@GetMapping(value = "/profileListByList")
	public ResponseEntity<List<Profile>> getByAuthedStatusList(String relatedId, String authedStatus) {
		String[] autheds = authedStatus.split(",");
		List<String> asList = Arrays.asList(autheds);
		List<Profile> profiles = profileRepository.findByAuthedStatusList(relatedId, asList);
		return ResponseEntity.ok(profiles);
	}

	/**
	 * 候选人中查发布人信息
	 * @param relatedId
	 * @param authedStatus
	 * @return
	 */
	@GetMapping(value = "/getReleaseUser")
	public ResponseEntity<Map<String, String>> getReleaseUser() {
		String authedStatus = "3,4,5,9";
		String[] autheds = authedStatus.split(",");
		List<String> asList = Arrays.asList(autheds);
		String userId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getUserId();
		Profile profile = profileRepository.findOne(userId);
		List<Profile> profiles = profileRepository.findByAuthedStatusList(profile.getRelatedId(), asList);
		Map<String, String> map = new HashMap<String, String>();
		for (Profile p : profiles) {
			map.put(p.getUserId(), p.getRealName());
		}
		return ResponseEntity.ok(map);
	}

}
