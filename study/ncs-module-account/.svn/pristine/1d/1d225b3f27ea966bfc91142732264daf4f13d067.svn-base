package cn.ncss.module.account.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ncss.module.account.domain.ThirdPartyAccount;
import cn.ncss.module.account.domain.UserAccount;
import cn.ncss.module.account.domain.manager.ThirdPartyAccountResult;
import cn.ncss.module.account.repository.AccountUsersRepository;
import cn.ncss.module.account.repository.ThirdPartyAccountRepository;

/**
 * 第三方登录
 * @author LiYang
 *
 */
@Transactional(readOnly = true)
@RestController
public class ThirdPartyAccountQueryController {
	private static final Logger logger = LoggerFactory.getLogger(ThirdPartyAccountQueryController.class);
	@Autowired
	private ThirdPartyAccountRepository thirdPartyAccountRepository;
	@Autowired
	private AccountUsersRepository userAccountRepository;

	/**
	 * 
	 * @param openid 第三方用户唯一标识
	 * @param type 类型
	 * @return
	 */
	@GetMapping(value = "/thirdPartyId")
	public ResponseEntity<ThirdPartyAccount> getOneById(String thirdPartyId) {
		ThirdPartyAccount tpAccount = thirdPartyAccountRepository.findOneById(thirdPartyId);
		return ResponseEntity.ok(tpAccount);
	}

	@GetMapping(value = "/thirdPartyAT")
	public ResponseEntity<ThirdPartyAccount> getOneByAccountAndType(String userId, String type) {
		UserAccount userAccount = userAccountRepository.findOne(userId);
		ThirdPartyAccount other = thirdPartyAccountRepository.findOneByUserAccountAndType(userAccount, type);
		return ResponseEntity.ok(other);
	}

	@GetMapping(value = "/thirdParty")
	public ResponseEntity<ThirdPartyAccount> getOne(String openid, String type) {
		ThirdPartyAccount tpAccount = thirdPartyAccountRepository.findOneByOpenIdAndType(openid, type);
		return ResponseEntity.ok(tpAccount);
	}

	/**
	 * 获取列表
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "/thirdpartList")
	public ResponseEntity<ThirdPartyAccountResult> getList(String userId) {
		ThirdPartyAccountResult result = new ThirdPartyAccountResult();
		List<ThirdPartyAccount> list = thirdPartyAccountRepository.findByUserAccountUserId(userId);
		result.setThirdPartyAccounts(list);
		return ResponseEntity.ok(result);
	}

}
