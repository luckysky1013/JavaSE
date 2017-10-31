package cn.ncss.module.account.repository;

import org.springframework.data.repository.CrudRepository;

import cn.ncss.module.account.domain.Target;

/**
 * 求职期望持久层
 * @author LiYang
 */
public interface TargetRepository extends CrudRepository<Target, String> {

}
