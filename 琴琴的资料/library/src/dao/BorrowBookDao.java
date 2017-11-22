package dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

//这里是执行借书的数据库操作代码；
public class BorrowBookDao {
	QueryRunner qr =new QueryRunner();
	public int updateBook(String borrowName, int amount) throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		String sql ="update book set amount=amount-? where bookname=? and amount>=?";
		int update = qr.update(conn, sql, amount,borrowName,amount);
		return update;
	}
	//更新借书表的信息
	public int updateBorrow(String userName, String borrowName, int amount) throws SQLException {
		String sql = "insert into borrow(bid,uid,num)VALUES((select id from book where bookname=?),(select id from user where name=?),?);";
		//获取连接，并执行更新；
		Connection conn = JDBCUtils.getConnection();
		int update = qr.update(conn, sql, borrowName,userName,amount);
		return update;
	}
	//还书操作，这里是更新书的数量
	public int returnBook(String userName, String returnBook) throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		QueryRunner qr2 =new QueryRunner(new ComboPooledDataSource());
		//获取书的id
		String sql ="select id from book where bookname =?";
		Object[] bid =qr2.query(sql, new ArrayHandler(), returnBook);
		//获取用户id；
		String sql2 ="select id from user where name =?";
		Object[] uid =qr2.query(sql2, new ArrayHandler(), userName);
		//执行更新；
		String sql3="update book set amount=amount+(select num from borrow where bid=? and uid=?) where bookname=?";
		int update = qr.update(conn, sql3, bid[0],uid[0],returnBook);
		return update;
	}
	//删除该条结束记录；
	public int deleteBorrow(String userName, String returnBook) throws SQLException {
		Connection conn = JDBCUtils.getConnection();
		QueryRunner qr2 =new QueryRunner(new ComboPooledDataSource());
		//获取书的id
		String sql ="select id from book where bookname =?";
		Object[] bid =qr2.query(sql, new ArrayHandler(), returnBook);
		//获取用户id；
		String sql2 ="select id from user where name =?";
		Object[] uid =qr2.query(sql2, new ArrayHandler(), userName);
		//执行更新；
		String sql3="delete from borrow where bid=? and uid=?";
		int delete = qr.update(conn, sql3, bid[0],uid[0]);
		return delete;
	}

}
