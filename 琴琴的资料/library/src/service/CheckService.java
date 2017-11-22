package service;

import java.sql.SQLException;

import dao.RegisterLoginDao;
import domain.User;

//这个类是用来处理验证用户登录的页面；
public class CheckService {

	public int check(String userName, String passWord) throws SQLException {
		RegisterLoginDao sd =new RegisterLoginDao();
		//接收查询出来的user对象；
		User user =sd.checkLogin(userName, passWord);
		//创建返回的变量,初始化为登录失败
		int result = 3;
		//做简单的校验；
		//如果user为空则证明登录失败；
		if(user==null){
			result=3;
		}else if(user.getIsadmin()==1){
			result=1;
		}
		else if(user.getIsadmin()!=1){
			result=2;
		}
		return result;
	}
	//用来处理执行注册是否成功的操作
	public boolean checkRigister(User user) throws SQLException {
		RegisterLoginDao sd =new RegisterLoginDao();
		int row = sd.register(user);
		//如果影响的行数为1，则证明注册成功；
		if(row==1){
			return true;
		}
		//否则返回false，注册失败
		return false;
	}

}
