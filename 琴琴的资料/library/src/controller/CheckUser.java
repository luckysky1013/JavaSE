package controller;

import java.sql.SQLException;

import domain.User;
import service.CheckService;

//这个类是用来处理用户登陆和注册的功能；
public class CheckUser {
	// 登录功能的任务分发；
	public int check(String userName, String passWord) throws SQLException {

		return new CheckService().check(userName, passWord);
	}

	// 注册功能的任务分发；
	public boolean register(User user) throws SQLException {

		return new CheckService().checkRigister(user);
	}

}
