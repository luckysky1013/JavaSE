package cn.ncss.module.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.ncss.module.account.domain.Profile;

/**
 * 学生信息持久层
 * @author liuyang
 *
 */
public interface ProfileRepository extends JpaRepository<Profile, String>, JpaSpecificationExecutor<Profile> {

	@Override
	public Profile save(Profile profile);

	@Override
	public void delete(String userId);

	@Modifying
	@Query(value = "UPDATE Profile SET commore = :commore where userId = :userId")
	public int updateCommore(@Param("userId") String userId, @Param("commore") String commore);

	@Modifying
	@Query(value = "UPDATE Profile SET company_Name = ?2 where related_Id = ?1", nativeQuery = true)
	public int updateCompanyName(@Param("relatedId") String relatedId, @Param("companyName") String companyName);

	/**
	 * 过期审核成功时,修改符合条件的用户审核通过
	 * @param relatedId
	 * @param checkValidDate
	 * @param updateDate
	 * @return
	 */
	@Modifying
	@Query(value = "UPDATE Profile SET checkValidDate = :checkValidDate , updateDate = :updateDate where relatedId = :relatedId "
			+ " and (authedStatus='3' or authedStatus='4' or authedStatus='5' or authedStatus='9') ")
	public int updateCheckValidDate(@Param("relatedId") String relatedId, @Param("checkValidDate") Date checkValidDate,
			@Param("updateDate") Date updateDate);

	@Override
	public Profile findOne(String userId);

	@Override
	public boolean exists(String userId);

	/**
	 * 查询用户列表
	 * @param relatedId 公司id
	 * @param authedStatus 用户状态
	 * @return
	 */
	@Query(value = "select p from Profile p where p.relatedId=nvl(?1,p.relatedId) and  p.authedStatus=nvl(?2,p.authedStatus)  ")
	public List<Profile> findByRelatedId(String relatedId, String authedStatus);

	@Query(value = "select p from Profile p where p.relatedId=nvl(?1,p.relatedId) and p.uploadTag=nvl(?2,p.uploadTag)  ")
	public List<Profile> findByRelatedIdAndUploadTag(String relatedId, String uploadTag);

	@Query(value = "select p from Profile p where p.relatedId=nvl(?1,p.relatedId) and  p.authedStatus in(?2)  ")
	public List<Profile> findByAuthedStatusList(String relatedId, List<String> authedStatus);

	@Query(value = "select count(p.userId) from Profile p where  p.information.idCard = ?1 ")
	public int findByInformationIdCard(String idCard);

	/**
	 * 修改企业用户权限
	 * @param userId 用户ID
	 * @param authedStatus 认证状态
	 * @return
	 */
	@Modifying
	@Query(value = "UPDATE Profile SET authedStatus = :authedStatus , updateDate = :updateDate WHERE userId = :userId")
	public int updateAuthed(@Param("userId") String userId, @Param("authedStatus") String authedStatus,
			@Param("updateDate") Date updateDate);

	/**
	 * 修改状态【用户想认证管理员或普通用户(认证管理员（2）、认证普通用户标识（1）)】
	 * @param userId
	 * @param uploadTag
	 * @return
	 */
	@Modifying
	@Query(value = "UPDATE Profile SET uploadTag = :uploadTag ,authedStatus = :authedStatus, updateDate = :updateDate WHERE userId = :userId")
	public int uploadTag(@Param("userId") String userId, @Param("uploadTag") String uploadTag,
			@Param("authedStatus") String authedStatus, @Param("updateDate") Date updateDate);

	/**
	 * 后台manager审核用户未通过
	 * @param userId 
	 * @param authedStatus
	 * @param checkReason
	 * @param description
	 * @param checkDate
	 * @return
	 */
	@Modifying
	@Query(value = "UPDATE Profile SET authedStatus = :authedStatus ,checkReason = :checkReason , "
			+ "description = :description ,checkDate = :checkDate, updateDate = :updateDate WHERE userId = :userId")
	public int updateAuthedFail(@Param("userId") String userId, @Param("authedStatus") String authedStatus,
			@Param("checkReason") String checkReason, @Param("description") String description,
			@Param("checkDate") Date checkDate, @Param("updateDate") Date updateDate);

	/**
	 * 后台manager审核用户通过
	 * @param userId
	 * @param authedStatus
	 * @param checkValidDate
	 * @param checkDate
	 * @return
	 */
	@Modifying
	@Query(value = "UPDATE Profile SET authedStatus = :authedStatus , checkValidDate =:checkValidDate,"
			+ " checkDate = :checkDate, updateDate = :updateDate , checkReason = '' ,description = ''"
			+ " WHERE userId = :userId")
	public int updateAuthedSucc(@Param("userId") String userId, @Param("authedStatus") String authedStatus,
			@Param("checkValidDate") Date checkValidDate, @Param("checkDate") Date checkDate,
			@Param("updateDate") Date updateDate);

	/**
	 * 更新邮箱
	 * @param userId
	 * @param email
	 * @return
	 */
	@Modifying
	@Query(value = "update Profile u set u.information.email=?2 ,u.updateDate=?3 where u.userId=?1")
	public int updateEmail(String userId, String email, Date updateDate);

	@Modifying
	@Query(value = "update Profile u set u.information.mobilePhone=?2,u.updateDate=?3 where u.userId=?1")
	public int updateMobilePhone(String userId, String mobilePhone, Date updateDate);

	@Modifying
	@Query(value = "update Profile u set u.relatedId=?2 , u.uploadTag=?3, u.updateDate=?4 where u.userId=?1")
	public int updateRelatedId(String userId, String comId, String uploadTag, Date updateDate);

	//后台
	/**
	 * 按天数查询统计待审核企业数
	 * @return
	 */
	//@Query(value = " SELECT d as date,COALESCE(a.count,0 ) as count FROM generate_series(?1\\:\\:timestamp,?2, '24 hours') d LEFT JOIN (SELECT date_trunc('day', t.wait_check_date ) date, count(t.user_id) count FROM profile t WHERE t.upload_tag='0' and t.authed_status!='5' and t.wait_check_date>=?1 and t.wait_check_date<=?2  GROUP BY  date_trunc('day', t.wait_check_date ) order by date_trunc('day', t.wait_check_date ) asc ) as a ON a.date=d ", nativeQuery = true)
	@Query(value = "SELECT d AS wait_check_date, COALESCE (A .COUNT,0) AS COUNT FROM (select trunc(sysdate - n + 1, 'dd') as d from table(ncss_generate_series(1, ?3))) "
			+ "LEFT JOIN (SELECT trunc(wait_check_date,'dd') as wait_check_date, COUNT(user_id) as count FROM profile WHERE "
			+ "(authed_status='04' or authed_status='1' or authed_status='2' or authed_status='7' or authed_status='9') "
			+ "and trunc(wait_check_date,'dd')>=to_date(?1,'yyyy-mm-dd') and trunc(wait_check_date,'dd')<=to_date(?2,'yyyy-mm-dd') "
			+ "GROUP BY  trunc(wait_check_date,'dd') ORDER BY trunc(wait_check_date,'dd') ASC ) A ON A.wait_check_date = d ORDER BY trunc(wait_check_date,'dd') ASC", nativeQuery = true)
	public List<Object> findListCorpByDay(String startDate, String endDate, int num);

	/**
	 * 按月数查询统计待审核企业数
	 * @return
	 */

	//@Query(value = " SELECT d as date,COALESCE(a.count,0 ) as count FROM generate_series(?1\\:\\:timestamp,?2, '1 month') d LEFT JOIN (SELECT date_trunc('month', t.wait_check_date ) date, count(t.user_id) count FROM profile t WHERE t.upload_tag='0' and t.authed_status!='5' and date_trunc('month', t.wait_check_date)>=?1 and date_trunc('month', t.wait_check_date)<=?2  GROUP BY  date_trunc('month', t.wait_check_date ) order by date_trunc('month', t.wait_check_date ) asc ) as a ON a.date=d ", nativeQuery = true)
	@Query(value = "SELECT d AS wait_check_date, COALESCE (A .COUNT,0) AS COUNT FROM (select add_months(trunc(sysdate,'mm'), -n + 1) as d from table(ncss_generate_series(1, ?3)))"
			+ " LEFT JOIN (SELECT trunc(wait_check_date,'mm') wait_check_date, COUNT(user_id) count FROM profile WHERE "
			+ "(authed_status='04' or authed_status='1' or authed_status='2' or authed_status='7' or authed_status='9') and "
			+ "trunc(wait_check_date,'mm')>=to_date(?1,'yyyy-mm-dd') and trunc(wait_check_date,'mm')<=to_date(?2,'yyyy-mm-dd') "
			+ "GROUP BY  trunc(wait_check_date,'mm') ORDER BY trunc(wait_check_date,'mm') ASC ) A ON A.wait_check_date = d ORDER BY trunc(wait_check_date,'dd') ASC", nativeQuery = true)
	public List<Object> findListCorpByMonth(String startDate, String endDate, int num);

	//@Query(value = " SELECT COALESCE(a.count,0 ) as count FROM generate_series(?1\\:\\:timestamp,?2, '24 hours') d LEFT JOIN (SELECT date_trunc('day', t.wait_check_date ) date, count(t.user_id) count FROM profile t WHERE t.upload_tag='0' and t.authed_status!='5' and t.wait_check_date>=?1 and t.wait_check_date<=?2  GROUP BY  date_trunc('day', t.wait_check_date ) order by date_trunc('day', t.wait_check_date ) asc ) as a ON a.date=d ", nativeQuery = true)
	@Query(value = "select count(*) from profile where  to_char(wait_check_date,'YYYY-MM-DD')=nvl(?1,to_char(wait_check_date,'YYYY-MM-DD')) ", nativeQuery = true)
	public int findcount(String date);

	public void deleteByUserId(String userId);

}
