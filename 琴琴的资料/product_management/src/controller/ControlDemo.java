package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domin.Products;
import service.SelectDemo;
import service.UpdateDemo;

//这里用来给功能进行分配；
public class ControlDemo {
	UpdateDemo  ud = new UpdateDemo();
	SelectDemo sd = new SelectDemo();
	//用于添加数据；
	public boolean create(String name, int price) throws SQLException {
//		 ud.createProduct(name,price);
		return ud.createProduct(name,price);
		
		
	}
	//用于检查id是否存在；
	public boolean checkId(int id) throws SQLException {
		return sd.selectId(id);
	}
	//用于修改商品数据；
	public boolean changeMessage(int id, String changeName, int changePrice) throws SQLException {
	
		return ud.change(id,changeName,changePrice);
	}
	//用于删除数据
	public boolean deleteMessage(int deleId) throws SQLException {
		UpdateDemo  ud = new UpdateDemo();
		return ud.delete(deleId);
	}
	//用来删除出所有的信息；
	public boolean deleteAll() throws SQLException {
		
		return ud.deletAllMessage();
	}
	//用于通过id来查询数据
	public Products selectById(int selectId) throws SQLException {
		
		return sd.getMessageById(selectId);
	}
	//用于查询所有的信息
	public List<Products> selectAll() throws SQLException {
		
		return sd.slectAllMessage();
	}

	
}
