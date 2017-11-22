package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DaoFactory;
import domin.Products;

//用来执行查询操作；
public class SelectDemo {
	DaoFactory dao = new DaoFactory();
	public boolean selectId(int id) throws SQLException {
		String sql = "select * from products where pid =?";
		List<Products> selectByID = dao.select(sql,id);
		if(selectByID.get(0)!=null){
			return true;
		}else{
			return false;
		}
	}
	//用于查询，通过id
	public Products getMessageById(int selectId) throws SQLException {
		String sql = "select * from products where pid =?";
		List<Products> selectByID = dao.select(sql,selectId);
		return selectByID.get(0);
	}
	//查询所有的信息；
	public List<Products> slectAllMessage() throws SQLException {
		String sql ="select * from products";
		return dao.select(sql);
	}

}
