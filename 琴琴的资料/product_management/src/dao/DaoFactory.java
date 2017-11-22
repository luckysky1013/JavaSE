package dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import domin.Products;

//用这个类来获取数据库的内容，进行操作；
public class DaoFactory {

	public int updateMassage(String sql,Object...args) throws SQLException {
		//从JDBCUtils里面获取连接对象；
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		//用于执行有可变参数的sql;
		int update = qr.update(sql, args);
		return update;
	}
	//用来查找数据；
	public List<Products> select(String sql, Object...args) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		 List<Products> query = qr.query(sql, new BeanListHandler<Products>(Products.class), args);
		return query;
	}
	
}
