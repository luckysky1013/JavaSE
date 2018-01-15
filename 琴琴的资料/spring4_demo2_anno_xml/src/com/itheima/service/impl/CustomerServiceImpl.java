package com.itheima.service.impl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.dao.ICustomerDao;
import com.itheima.domain.Customer;
import com.itheima.service.ICustomerService;
@Service("customerService")
//@Scope("prototype")
@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
public class CustomerServiceImpl implements ICustomerService{
	@Resource(name="customerDao")
	private ICustomerDao dao;

	/**
	 * 实现转账案例
	 * */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void transfor(String targetName, String sourceName, int money) {
		//通过sourceName来转账
		Customer target = dao.findByName(targetName);
		Customer source = dao.findByName(sourceName);
		//通过targetName收账
		//如果有一方不存在则不转账
		if(target==null||source==null){
			return;
		}
		//如果转账一方的钱不够转账的钱，直接返回，不进行转账
		if(target.getMoney()-money<0){
			return;
		}
		//可以执行转账
		target.setMoney(target.getMoney()-money);
		source.setMoney(source.getMoney()+money);
		//执行更新操作；
		dao.update(target);
		int i = 1/0;
		dao.update(source);
		
	}

}
