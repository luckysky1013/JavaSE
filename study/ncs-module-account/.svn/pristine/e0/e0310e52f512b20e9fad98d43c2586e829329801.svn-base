package cn.ncss.module.account.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cn.ncss.module.account.domain.ItemMeta;

/**
 * 教育背景持久层
 * @author LiYang
 */
public interface ItemMetaRepository extends CrudRepository<ItemMeta, String> {

	/**
	 * 更新简历项顺序
	 * @param id 
	 * @param sn 顺序
	 * @return
	 */
	@Modifying
	@Query(value = "UPDATE ItemMeta SET sn = :sn WHERE id = :id")
	public int updateSn(@Param("id") String id, @Param("sn") Integer sn);

	public ItemMeta findById(String id);

}
