package cn.ncss.module.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.ncss.module.account.domain.Resume;

/**
 * 简历持久层
 * @author LiYang
 *
 */
public interface ResumeRepository extends JpaRepository<Resume, String> {

	@Override
	public Resume save(Resume resume);

	@Override
	public Resume findOne(String id);

	@Override
	public boolean exists(String id);

	/**
	 * 根据id查询对应的简历
	 * @param id 简历的id
	 * @return
	 */
	public Resume findById(String id);

	/**
	 * 根据用户id查询到对应的简历
	 * @param id 用户id,同样也是UserProfile的id
	 * @return
	 */
	public Resume findByProfileUserId(String userId);

}
