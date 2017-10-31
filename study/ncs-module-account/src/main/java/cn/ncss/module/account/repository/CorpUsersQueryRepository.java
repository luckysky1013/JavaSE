package cn.ncss.module.account.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.ncss.module.account.domain.Profile;

/**
 * 企业用户查询列
 * @author liyang
 *
 */
@CacheConfig(cacheNames = "corpusers")
public interface CorpUsersQueryRepository extends PagingAndSortingRepository<Profile, String> {
	//搜索企业用户list（带时间） truesql  AND (u.unlock_date is null or u.unlock_date <=nvl2(?8,sysdate,u.unlock_date)) AND u.unlock_date >=nvl2(?9,sysdate,u.unlock_date)
	@Query(value = "SELECT q.user_Id,q.real_name,q.company_Name,q.department,q.job_Title,q.jobphone,q.jobfax,q.email,q.mobile_Phone,q.update_Date,q.authed_Status,q.be_Reported_Count,"
			+ "q.unlock_date,q.related_Id,q.check_Date,q.be_Reported_Count_before from (SELECT rownum rn, s1.* from (SELECT u.user_Id,P.real_name,P.company_Name,P.department,P.job_Title,P.jobphone,P.jobfax,"
			+ "u.email,u.mobile_Phone,P.update_Date,P.authed_Status,u.be_Reported_Count,u.unlock_date,P.related_Id,P.check_Date,u.be_Reported_Count_before FROM user_account u LEFT JOIN profile P ON u.user_id = P.user_id WHERE u.user_type='COMPANY' "
			+ "AND P .user_Id is not null AND P.authed_Status in(?1) AND to_char(P.update_Date,'YYYY-MM-DD') >= nvl(?4,to_char(P.update_Date,'YYYY-MM-DD')) AND to_char(P.update_Date,'YYYY-MM-DD') <= nvl(?5,to_char(P.update_Date,'YYYY-MM-DD'))  AND "
			+ "nvl(P.company_Name,0) LIKE %?2% AND (nvl(p.real_Name,0) like %?3% or nvl(u.mobile_Phone,0) like %?3% or nvl(u.email,0) like %?3%) AND (?8=' ' or (u.unlock_date<=sysdate or u.unlock_date is null )) AND (?9=' ' or u.unlock_date>=sysdate) order by P.update_Date desc ) s1 where rownum<=?7) q where rn>=?6 ", nativeQuery = true)
	public List<Object[]> findList(List<String> authedStatus, String companyName, String realName, String startDate,
			String endDate, int pageSize, int offset, String unlock, String lock);

	//搜索企业用户count（带时间）truesql  AND (u.unlock_date is null or u.unlock_date <=nvl2(?6,sysdate,u.unlock_date)) AND u.unlock_date >=nvl2(?7,sysdate,u.unlock_date)
	@Cacheable(value = "count", key = "'count'+#p0+#p1+#p2+#p3+#p4+#p5+#p6")
	@Query(value = "select count(*) FROM user_account u LEFT JOIN profile P ON u.user_id = P.user_id WHERE u.user_type='COMPANY' AND P .user_Id is not null AND P.authed_Status in(?1) "
			+ "AND to_char(P.update_Date,'YYYY-MM-DD') >= nvl(?4,to_char(P.update_Date,'YYYY-MM-DD')) AND to_char(P.update_Date,'YYYY-MM-DD') <= nvl(?5,to_char(P.update_Date,'YYYY-MM-DD'))  AND "
			+ "nvl(P.company_Name,0) LIKE %?2% AND (nvl(p.real_Name,0) like %?3% or nvl(u.mobile_Phone,0) like %?3% or nvl(u.email,0) like %?3%) AND (?6=' ' or (u.unlock_date<=sysdate or u.unlock_date is null )) AND (?7=' ' or u.unlock_date>=sysdate)", nativeQuery = true)
	public int count(List<String> authedStatus, String companyName, String realName, String startDate, String endDate,
			String unlock, String lock);

	//无信息 truesql
	@Query(value = "SELECT q.user_Id,q.email,q.mobile_Phone,q.reg_date,q.unlock_date,q.be_Reported_Count,q.be_Reported_Count_before from (SELECT rownum rn, s1.* from (SELECT u.user_Id ,u.email , u.mobile_Phone , u.reg_date , "
			+ "u.unlock_date,u.be_Reported_Count,u.be_Reported_Count_before FROM user_account u LEFT JOIN profile P ON u.user_id = P.user_id WHERE u.user_type='COMPANY' AND P .user_Id is null AND to_char(u.reg_date,'YYYY-MM-DD') >= nvl(?2,to_char(u.reg_date,'YYYY-MM-DD'))"
			+ "AND to_char(u.reg_date,'YYYY-MM-DD') <= nvl(?3,to_char(u.reg_date,'YYYY-MM-DD')) AND ( nvl(u.mobile_Phone,0) like %?1% or nvl(u.email,0) like %?1%) AND (?6=' ' or (u.unlock_date<=sysdate or u.unlock_date is null )) AND (?7=' ' or u.unlock_date>=sysdate) order by u.reg_date desc) s1 where rownum<=?5) q where rn>=?4 ", nativeQuery = true)
	public List<Object[]> findListNoInformation(String realName, String startDate, String endDate, int pageSize,
			int offset, String unlock, String lock);

	//truesql
	@Cacheable(value = "countNoInformation", key = "'countNoInformation'+#p0+#p1+#p2+#p3+#p4")
	@Query(value = "select count(*) FROM user_account u LEFT JOIN profile P ON u.user_id = P.user_id WHERE u.user_type='COMPANY' AND P .user_Id is null AND  "
			+ "to_char(u.reg_date,'YYYY-MM-DD') >= nvl(?2,to_char(u.reg_date,'YYYY-MM-DD')) AND to_char(u.reg_date,'YYYY-MM-DD') <= nvl(?3,to_char(u.reg_date,'YYYY-MM-DD')) AND "
			+ "( nvl(u.mobile_Phone,0) like %?1% or nvl(u.email,0) like %?1%) AND (?4=' ' or (u.unlock_date<=sysdate or u.unlock_date is null )) AND (?5=' ' or u.unlock_date>=sysdate)", nativeQuery = true)
	public int countNoInformation(String realName, String startDate, String endDate, String unlock, String lock);

	//搜索一个单位下所有用户  truesql
	@Query(value = "SELECT q.user_Id , q.real_name, q.company_Name , q.department , q.job_Title , q.jobphone ,q.jobfax , q.email, q.mobile_Phone, q.update_Date, q.authed_Status ,q.check_Date , "
			+ "q.unlock_date , q.related_Id ,q.upload_Tag,q.be_Reported_Count_before from (SELECT rownum rn, s1.* from (SELECT u.user_Id , P.real_name, P.company_Name , P.department , P.job_Title , P.jobphone ,"
			+ "P.jobfax , u.email, u.mobile_Phone, P.update_Date, P .authed_Status ,P .check_Date ,u.unlock_date , P.related_Id ,P.upload_Tag,u.be_Reported_Count_before FROM user_account u LEFT JOIN profile P ON u.user_id = P.user_id WHERE u.user_type='COMPANY' "
			+ "AND P .user_Id is not null AND P.related_Id=nvl(?1,P.related_Id) AND  nvl(P.real_Name,0) like %?2%  "
			+ "AND nvl(to_char(P.check_Date, 'YYYY-MM-DD'), sysdate) >= nvl(?3,  nvl(to_char(P.check_Date, 'YYYY-MM-DD'), sysdate)) AND nvl(to_char(P.check_Date, 'YYYY-MM-DD'), sysdate) <= nvl(?4,  nvl(to_char(P.check_Date, 'YYYY-MM-DD'), sysdate)) ) s1 where rownum<=?6) q where rn>=?5 ", nativeQuery = true)
	public List<Object[]> findListByComId(String comId, String realName, String startDate, String endDate, int pageSize,
			int offset);

	//搜索一个单位下所有用户  truesql
	@Query(value = "SELECT count(*) FROM user_account u LEFT JOIN profile P ON u.user_id = P.user_id WHERE u.user_type='COMPANY' "
			+ "AND P .user_Id is not null AND  P.related_Id=nvl(?1,P.related_Id) AND  nvl(P.real_Name,0) like %?2%  AND nvl(to_char(P.check_Date, 'YYYY-MM-DD'), sysdate) >= nvl(?3,  nvl(to_char(P.check_Date, 'YYYY-MM-DD'), sysdate)) AND nvl(to_char(P.check_Date, 'YYYY-MM-DD'), sysdate) <= nvl(?4,  nvl(to_char(P.check_Date, 'YYYY-MM-DD'), sysdate))  ", nativeQuery = true)
	public long countByComId(String comId, String realName, String startDate, String endDate);

}
