package com.itheima.dao;

import com.itheima.domain.Customer;

public interface ICustomerDao {

	Customer findByName(String name);

	void update(Customer customer);

}
