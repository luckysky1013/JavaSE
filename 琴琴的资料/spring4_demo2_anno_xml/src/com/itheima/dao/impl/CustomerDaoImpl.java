package com.itheima.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.itheima.dao.ICustomerDao;
import com.itheima.domain.Customer;
@Repository("customerDao")
public class CustomerDaoImpl implements ICustomerDao {
	@Autowired
	private JdbcTemplate jp ;
	@Override
	public Customer findByName(String name) {
		String sql="select cust_id,cust_name,money from customer where cust_name=?";
		//获取结果
		List<Customer> list = jp.query(sql, new CustomerRowMapper(), name);
		//返回结果
		return list.isEmpty()?null:list.get(0);
	}
	//执行转账操作；
	@Override
	public void update(Customer customer) {
		String sql = "update customer set money=? where cust_id=?";
		//执行更新；
		int update = jp.update(sql, customer.getMoney(),customer.getCustId());
		System.out.println(update);
	}
	
}
//手动包装实体类
class CustomerRowMapper implements RowMapper<Customer>{
	@Override
	public Customer mapRow(ResultSet rs, int index) throws SQLException {
		Customer c = new Customer();
		c.setCustId(rs.getLong("cust_id"));
//		c.setCustAddress(rs.getString("cust_address"));
//		c.setCustIndustry(rs.getString("cust_industry"));
//		c.setCustLevel(rs.getString("cust_level"));
		c.setCustName(rs.getString("cust_name"));
//		c.setCustPhone(rs.getString("cust_phone"));
//		c.setCustSource(rs.getString("cust_source"));
		c.setMoney(rs.getInt("money"));
		return c;
	}
	
}
