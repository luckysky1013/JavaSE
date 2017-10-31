package cn.ncss.module.account.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cn.ncss.module.account.domain.EducationHistory;

/**
 * 教育背景持久层
 * @author LiYang
 */
public interface EducationHistoryRepository extends CrudRepository<EducationHistory, String> {

	/**
	 * 更新学籍认证结果
	 * @param id 教育背景id
	 * @param isAuthed 认证结果
	 * @return
	 */
	@Modifying
	@Query(value = "UPDATE EducationHistory SET isAuthed = :isAuthed WHERE id = :id and isKey = :isKey")
	public int updateAuthed(@Param("id") String id, @Param("isAuthed") String isAuthed, @Param("isKey") String isKey);

	/**
	 * 根据关键字查询匹配院系
	 * @param name 关键字
	 * @return
	 */
	@Query(value = "select distinct department as department  from EducationHistory  where schoolName = nvl(?2,schoolName) and isForeignUniversity=?3 and department like %?1% ")
	public List<String> findByDepartmentAndSchoolName(String department, String schoolName, String isForeign);

	@Query(value = "select distinct smallMajorName as smallMajorName  from EducationHistory  where smallMajorName like %?1% ")
	public List<String> findByMajorName(String smallMajorName);

	@Query(value = "select  e  from EducationHistory e where e.isKey = ?1 and e.resume.id = ?2 ")
	public EducationHistory findByIsKeyAndResumeId(String iskey, String resumeId);

	@Query(value = "select new map( schoolId as id,schoolName as text ) from EducationHistory where isForeignUniversity='true' and schoolName like %?1% ")
	public List<Map<String, Object>> findByNameContaining(String name);

}
