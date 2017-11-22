package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//这里是一个工具类，用来获取连接；
public class JDBCUtils {
	//创建连接池对象;
	private static ComboPooledDataSource ds = new ComboPooledDataSource();
	//获取一个ThreadLocal对象；
	 static ThreadLocal<Connection> local = new ThreadLocal<Connection>();
	 public static DataSource getDataSource(){
		 return ds;
	 }
	 //获取连接，每次从ThreadLocal里面取；
	 public static Connection getConnection() throws SQLException{
		 Connection conn = local.get();
		 if(conn==null){
			 conn=ds.getConnection();
			 local.set(conn);
		 }
		 return conn;
	 }
}
