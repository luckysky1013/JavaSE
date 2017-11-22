package dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import domain.User;

//这个页面是用来处理所有的查询相关的操作；
public class RegisterLoginDao {
	public User checkLogin(String userName,String passWord) throws SQLException{
		//创建DBUtils的对象
		QueryRunner qr = new QueryRunner(new ComboPooledDataSource());
		//sql语句
		String sql ="select * from user where name=? and password =?";
		//执行查询；
		User user = qr.query(sql, new BeanHandler<User>(User.class), userName,passWord);
		//返回查询的结果；
		return user;
		
	}
	//执行注册
	public int register(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(new ComboPooledDataSource());
		String sql ="insert into user(name,password,age,gender) values(?,?,?,?)";
		//执行更新，获取到影响的行数；
		int row = qr.update(sql, user.getName(),user.getPassword(),user.getAge(),user.getGender());
		return row;
	}
}
