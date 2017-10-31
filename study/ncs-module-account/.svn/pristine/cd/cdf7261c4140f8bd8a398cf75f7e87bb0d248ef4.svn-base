package cn.ncss.module.account.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.ncss.module.account.domain.Profile;

/**
 * 学生用户查询列
 * @author liyang
 *
 */
@CacheConfig(cacheNames = "studentusers")
public interface AccountUsersQueryRepository extends PagingAndSortingRepository<Profile, String> {

	//后台 搜索学生用户list truesql
	@Query(value = "SELECT q.user_Id,q.mobile_Phone,q.email,q.real_Name,q.edu_Level,q.school_Id,q.school_Name,q.middle_major_code,"
			+ "q.middle_major_name,q.end_Date,q.division_Code,q.authed_Status  from (SELECT rownum rn, s1.* from (SELECT u.user_Id,u.mobile_Phone,u.email,p.real_Name,p.edu_Level,p.school_Id,p.school_Name,p.middle_major_code,"
			+ "p.middle_major_name,p.end_Date,p.division_Code,p.authed_Status FROM user_account u LEFT JOIN profile p ON u.user_Id = p.user_Id  "
			+ "WHERE u.user_Type = 'STUDENT' AND P .user_Id is not null AND nvl(p.edu_Level,0) like ?1% AND p.authed_Status=nvl(?4,p.authed_Status) "
			+ "AND nvl( p.division_Code,0) like ?2% AND nvl(p.end_Date,0) like ?3% AND  nvl(p.id_Card,0) like %?5% AND  nvl(p.school_Name,0) like %?6% AND (nvl(p.middle_major_name,0) like %?7% or nvl(p.small_major_name,0) like %?7% )"
			+ "AND (nvl(p.real_Name,0) like %?8% or nvl(u.mobile_Phone,0) like %?8% or nvl(u.email,0) like %?8%) order by p.update_date desc  ) s1 where rownum<=?10) q where rn>=?9 ", nativeQuery = true)
	public List<Object[]> findList(String eduLevel, String divisionCode, String endDate, String authedStatus,
			String idCard, String schoolName, String majorName, String realName, int pageSize, int offset);

	//搜索学生用户count truesql
	@Cacheable(value = "count", key = "'count'+#p0+#p1+#p2+#p3+#p4+#p5+#p6+#p7")
	@Query(value = "SELECT count(*) FROM user_account u LEFT JOIN profile p ON u.user_Id = p.user_Id  "
			+ "WHERE u.user_Type = 'STUDENT' AND P .user_Id is not null AND nvl(p.edu_Level,0) like ?1%  AND p.authed_Status=nvl(?4,p.authed_Status) "
			+ "AND nvl( p.division_Code,0) like ?2% AND nvl(p.end_Date,0) like ?3% AND  nvl(p.id_Card,0) like %?5% AND  nvl(p.school_Name,0) like %?6% AND (nvl(p.middle_major_name,0) like %?7% or nvl(p.small_major_name,0) like %?7% )"
			+ "AND (nvl(p.real_Name,0) like %?8% or nvl(u.mobile_Phone,0) like %?8% or nvl(u.email,0) like %?8%)", nativeQuery = true)
	public int count(String eduLevel, String divisionCode, String graduationTime, String authedStatus, String idCard,
			String schoolName, String majorName, String realName);

	//后台 搜索学生用户无信息list truesql
	@Query(value = "SELECT q.user_Id,q.mobile_Phone,q.email from(SELECT rownum rn, s1.* from (SELECT u.user_Id,u.mobile_Phone,u.email "
			+ "FROM user_account u LEFT JOIN profile p ON u.user_Id = p.user_Id WHERE u.user_Type = 'STUDENT' AND p.user_Id is null "
			+ "AND (nvl(u.mobile_Phone,0) like %?1% OR nvl(u.email,0) like %?1%)  order by u.reg_date desc) s1 where rownum<=?3) q where rn>=?2 ", nativeQuery = true)
	public List<Object[]> findListNoInformation(String realName, int startNum, int endNum);

	//truesql
	@Cacheable(value = "countNoInformation", key = "'countNoInformation'+#p0")
	@Query(value = "SELECT count(*) FROM user_account u LEFT JOIN profile p ON u.user_Id = p.user_Id  "
			+ "WHERE u.user_Type = 'STUDENT' AND p.user_Id is null AND (nvl(u.mobile_Phone,0) like %?1% OR nvl(u.email,0) like %?1%) ", nativeQuery = true)
	public int countNoInformation(String realName);

	/**
	 * 按天数查询统计注册数
	 * @return
	 */
	//@Query(value = " SELECT d as date,COALESCE(a.count,0 ) as count FROM generate_series(?1\\:\\:timestamp,?2, '24 hours') d LEFT JOIN (SELECT date_trunc('day', t.reg_date ) date, count(t.user_id) count FROM User_Account t WHERE  t.reg_date>=?1 and t.reg_date<=?2  GROUP BY  date_trunc('day', t.reg_date ) order by date_trunc('day', t.reg_date ) asc ) as a ON a.date=d ", nativeQuery = true)
	@Query(value = "SELECT d AS reg_date, COALESCE (A .COUNT,0) AS COUNT FROM (select trunc(sysdate - n + 1, 'dd') as d from table(ncss_generate_series(1, ?3))) "
			+ "LEFT JOIN (SELECT trunc(reg_date,'dd') as reg_date, COUNT(user_id) as count FROM User_Account WHERE trunc(reg_date,'dd')>=to_date(?1,'yyyy-mm-dd') and trunc(reg_date,'dd')<=to_date(?2,'yyyy-mm-dd') "
			+ "GROUP BY  trunc(reg_date,'dd') ORDER BY trunc(reg_date,'dd') ASC ) A ON A.reg_date = d  ORDER BY trunc(reg_date,'dd') ASC", nativeQuery = true)
	public List<Object> findListByDay(String startDate, String endDate, int num);

	/**
	 * 按月数查询统计注册数
	 * @return
	 */
	//@Query(value = " SELECT d as date,COALESCE(a.count,0 ) as count FROM generate_series(?1\\:\\:timestamp,?2, '1 month') d LEFT JOIN (SELECT date_trunc('month', t.reg_date ) date, count(t.user_id) count FROM User_Account t WHERE date_trunc('month', t.reg_date)>=?1 and date_trunc('month', t.reg_date)<=?2  GROUP BY  date_trunc('month', t.reg_date ) order by date_trunc('month', t.reg_date ) asc ) as a ON a.date=d ", nativeQuery = true)
	@Query(value = "SELECT d as reg_date, COALESCE(a.log_count, 0) as count FROM (select add_months(trunc(sysdate,'mm'), -n + 1) as d from table(ncss_generate_series(1, ?3))) "
			+ "LEFT JOIN (SELECT trunc(t.reg_date, 'mm') as reg_date, count(t.user_Id) as log_count FROM User_Account t WHERE trunc(t.reg_date,'mm')>=to_date(?1, 'yyyy-mm-dd') "
			+ "and trunc(t.reg_date,'mm') <= to_date(?2, 'yyyy-mm-dd') GROUP BY trunc(t.reg_date, 'mm') "
			+ "order by trunc(t.reg_date, 'mm') asc) a ON d = a.reg_date ORDER BY trunc(reg_date,'dd') ASC", nativeQuery = true)
	public List<Object> findListByMonth(String startDate, String endDate, int num);

	@Cacheable(value = "findcount", key = "'findcount'+#p0+#p1")
	//@Query(value = " SELECT COALESCE(a.count,0 ) as count FROM generate_series(?1\\:\\:timestamp,?2, '24 hours') d LEFT JOIN (SELECT date_trunc('day', t.reg_date ) date, count(t.user_id) count FROM User_Account t WHERE  t.reg_date>=?1 and t.reg_date<=?2  GROUP BY  date_trunc('day', t.reg_date ) order by date_trunc('day', t.reg_date ) asc ) as a ON a.date=d ", nativeQuery = true)
	@Query(value = "select count(*) from User_Account where  to_char(reg_date,'YYYY-MM-DD')=nvl(?1,to_char(reg_date,'YYYY-MM-DD')) ", nativeQuery = true)
	public int findcount(String createDate);

}
