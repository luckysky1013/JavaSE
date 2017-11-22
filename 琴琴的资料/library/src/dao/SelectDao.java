package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import domain.Book;
import domain.BorrowDetail;

//这里是执行查询的dao层
public class SelectDao {
	//创建数据库连接池对象；
	private static QueryRunner qr = new QueryRunner(new ComboPooledDataSource());
	// 执行查询所有书的操作
	public List<Book> selectAll() throws SQLException {
		//执行查询的语句；
		String sql = "select * from book";
		List<Book> query = qr.query(sql, new BeanListHandler<Book>(Book.class));
		return query;
		
	}
	//通过书名进行查找；
	public Book selectBookByName(String bookName) throws SQLException {
		//sql语句；
		String sql = "select * from book where bookname =?";
		Book book = qr.query(sql, new BeanHandler<Book>(Book.class), bookName);
		return book;
	}
	//管理员查看借书信息
	public List<BorrowDetail> borrowMessage() throws SQLException {
		QueryRunner qrunner = new QueryRunner();
		Connection conn = JDBCUtils.getConnection();
		String sql  ="select u.name,b.bookname,num from user u,book b,borrow where u.id=borrow.uid and b.id=borrow.bid";
		List<BorrowDetail> query = qrunner.query(conn,sql, new BeanListHandler<BorrowDetail>(BorrowDetail.class));
		return query;
	}
	//用户查看自己的借书信息；
	public List<BorrowDetail> ownBorrowMessage(String userName) throws SQLException {
		String sql = "select u.name,b.bookname,num from user u,book b,borrow where u.id=borrow.uid and b.id=borrow.bid and u.name=?";
		//执行查询；
		List<BorrowDetail> query = qr.query(sql, new BeanListHandler<BorrowDetail>(BorrowDetail.class),userName);
		return query;
	}

}
