package cn.ncss.module.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import cn.ncss.module.account.domain.ThirdPartyAccount;
import cn.ncss.module.account.domain.UserAccount;

/**
 * 第三方账户信息操作
 * @author kyrin
 *
 */
public interface ThirdPartyAccountRepository extends Repository<ThirdPartyAccount, String> {

	public ThirdPartyAccount save(ThirdPartyAccount thirdPartyAccount);

	public void deleteById(String id);

	public void deleteByUserAccountUserIdAndType(String userId, String type);

	@Modifying
	@Query(value = "update ThirdPartyAccount tpa  set tpa.accessToken=?2 where tpa.id=?1 ")
	public void updateAccessToken(String id, String accessToken);

	@Modifying
	@Query(value = "update ThirdPartyAccount tpa  set tpa.refreshToken=?2 where tpa.id=?1 ")
	public void updateRefreshToken(String id, String refreshToken);

	@Modifying
	@Query(value = "update ThirdPartyAccount tpa  set tpa.expireIn=?2  where tpa.id=?1 ")
	public void updateExpireIn(String id, Date expireIn);

	@Modifying
	@Query(value = "update ThirdPartyAccount tpa set tpa.accessToken=?2 , tpa.refreshToken=?3 , tpa.expireIn=?4 where tpa.id=?1 ")
	public void update(String id, String accessToken, String refreshToken, Date expireIn);

	@Modifying
	@Query(value = "update ThirdPartyAccount tpa set tpa.userAccount=?3,tpa.bindDate=?4 where tpa.openId=?1 and tpa.type=?2")
	public void updateUserAccount(String openid, String type, UserAccount userAccount, Date bindDate);

	@Modifying
	@Query(value = "update third_party_account set user_id=?3,bind_date=?4 where openid=?1 and type=?2", nativeQuery = true)
	public void updateUserId(String openid, String type, String userId, Date bindDate);

	@Modifying
	@Query(value = "update third_party_account set user_id=NULL where user_id=?1 and type=?2", nativeQuery = true)
	public void updateUserId(String userId, String type);

	public ThirdPartyAccount findOneById(String id);

	@EntityGraph(attributePaths = { "userAccount" })
	public ThirdPartyAccount findOneByOpenIdAndType(String openid, String type);

	public ThirdPartyAccount findOneByUserAccountAndType(UserAccount userAccount, String type);

	public List<ThirdPartyAccount> findByUserAccountUserId(String userId);
}
