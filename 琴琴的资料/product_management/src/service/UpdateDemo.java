package service;

import java.sql.SQLException;

import dao.DaoFactory;

//本类用来执行数据库更新操作；
public class UpdateDemo {
	DaoFactory dao = new DaoFactory();
	//用来添加数据
	public boolean createProduct(String name, int price) throws SQLException {
		String sql ="insert into products(pname,price) values(?,?)";
		int row =dao.updateMassage(sql,name,price);
		//执行更新操作返回的值是1则证明更新成功！
		if(row==1){
			return true;
		}else{
			
			return false;
		}
	}
	//用于执行更新数据
	public boolean change(int id, String changeName, int changePrice) throws SQLException {
		String sql ="update products set pname=?,price=? where pid =?";
		int row =dao.updateMassage(sql,changeName,changePrice,id);
		//执行更新操作返回的值是1则证明更新成功！
		if(row==1){
			return true;
		}else{
			
			return false;
		}
	}
	
	//执行删除操作
	public boolean delete(int deleId) throws SQLException {
	
		String sql ="delete from products sewhere pid =?";
		int row =dao.updateMassage(sql,deleId);
		//执行更新操作返回的值是1则证明删除成功！
		if(row==1){
			return true;
		}else{
			return false;
		}
	}
	public boolean deletAllMessage() throws SQLException {
		String sql ="delete from products";
		int row =dao.updateMassage(sql);
		//执行删除操作返回的值不是0则证明删除成功！
		if(row != 0){
			return true;
		}else{
			return false;
		}
	}

}
