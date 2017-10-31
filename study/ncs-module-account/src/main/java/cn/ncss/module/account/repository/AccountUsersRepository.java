package cn.ncss.module.account.repository;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import cn.ncss.module.account.domain.UserAccount;

/**
 * 
 * 登录账户信息操作
 * 在使用 { @Modifying } 的时候，不加 { @Transaction }的话，会报错“
 * 
 * javax.persistence.TransactionRequiredException: Executing an update/delete query
 *
 * 
 * @author kyrin,liuyang
 *
 */
public interface AccountUsersRepository extends Repository<UserAccount, String> {

	public UserAccount save(UserAccount userAccount);

	public UserAccount findOne(String userId);

	public UserAccount findByEmail(String email);

	public UserAccount findByMobilePhone(String mobilePhone);

	public int deleteByUserId(String userId);

	@Modifying
	@Query(value = "update UserAccount u set u.isValidEmail=?2 where u.userId=?1")
	public int updateIsValidEmail(String userId, String isValid);

	@Modifying
	@Query(value = "update UserAccount u set u.isValidPhone=?2 where u.userId=?1")
	public int updateIsValidPhone(String userId, String isValid);

	@Modifying
	@Query(value = "update UserAccount u set u.password=?2 where u.userId=?1 ")
	public int updatePasswordByUserId(String userId, String newPassword);

	@Modifying
	@Query(value = "update UserAccount u set u.password=?2 where u.email=?1")
	public int updatePasswordByEmail(String email, String password);

	@Modifying
	@Query(value = "update UserAccount u set u.password=?2 where u.mobilePhone=?1")
	public int updatePasswordByMobilePhone(String mobilePhone, String password);

	@Modifying
	@Query(value = "update UserAccount u set u.loginFailedCount=u.loginFailedCount+1 where u.userId=?1")
	public int updateLoginFailedCountIncrementOneById(String userId);

	@Modifying
	@Query(value = "update UserAccount u set u.loginFailedCount=0 where u.userId=?1")
	public int updateLoginFailedCountMakeZeroById(String userId);

	@Modifying
	@Query(value = "update UserAccount u set u.loginFailedCount=0 where u.email=?1")
	public int updateLoginFailedCountMakeZeroByEmail(String email);

	@Modifying
	@Query(value = "update UserAccount u set u.loginFailedCount=0 where u.mobilePhone=?1")
	public int updateLoginFailedCountMakeZeroByMobilePhone(String email);

	@Modifying
	@Query(value = "update UserAccount u set u.unlockDate=?3 where u.userId=?1 and u.loginFailedCount>?2")
	public int updateUnlockDateById(String userId, int loginFailedCount, Date unlockDate);

	@Modifying
	@Query(value = "update UserAccount u set u.mobilePhone=?2 , u.isValidPhone = ?3 where u.userId=?1")
	public int updateMobilePhone(String userId, String mobilePhone, String isValidPhone);

	@Modifying
	@Query(value = "update UserAccount u set u.email=?2 , u.isValidEmail=?3 where u.userId=?1")
	public int updateEmail(String userId, String email, String isValidEmail);

	/**
	 * 锁死
	 * @param userId
	 * @param unlockDate
	 * @return
	 */
	@Transactional
	@Modifying
	@Query(value = "update UserAccount u set u.unlockDate=?2 where u.userId=?1 ")
	public int updatelockDate(String userId, Date unlockDate);

	/**
	 * 解锁
	 * @param userId
	 * @param unlockDate
	 * @param loginFailedCount 失败次数
	 * @return
	 */
	@Transactional
	@Modifying
	@Query(value = "update UserAccount u set u.unlockDate=?2 ,u.loginFailedCount=?3  where u.userId=?1 ")
	public int updateunlockDate(String userId, Date unlockDate, int loginFailedCount);

	@Transactional
	@Modifying
	@Query(value = "update UserAccount u set u.nfnType=?2 where u.userId=?1 ")
	public int updateNfnType(String userId, String nfnType);

	@Transactional
	@Modifying
	@Query(value = "update UserAccount u set u.popTag=?2 where u.userId=?1 ")
	public int updatePopTag(String userId, String popTag);

	@Query(value = "select count(c) from UserAccount c where  c.email = ?1 and c.userId != ?2  ")
	public int countByEmail(String email, String userId);

	@Query(value = "select count(c) from UserAccount c where  c.mobilePhone = ?1 and c.userId != ?2  ")
	public int countByMobilePhone(String mobilePhone, String userId);

	@Modifying
	@Query(value = "update UserAccount u set u.beReportedCount=u.beReportedCount+1 where u.userId=?1")
	public int updateBeReportedCountById(String userId);

	@Modifying
	@Query(value = "update UserAccount u set u.beReportedCountBefore=u.beReportedCountBefore+u.beReportedCount ,u.beReportedCount=0 where u.userId=?1")
	public int updateBeReportedCountBeforeById(String userId);

}
