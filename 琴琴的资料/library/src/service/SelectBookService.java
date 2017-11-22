package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.JDBCUtils;
import dao.SelectDao;
import domain.Book;
import domain.BorrowDetail;

//这里对所有的查找Book的方法进行封装
public class SelectBookService {
	//进行查找所有的书籍的业务处理；
	SelectDao sd = new SelectDao();
	public List<Book> SelectAllBook() throws SQLException {
		List<Book> all = sd.selectAll();
		return all;
	}
	//通过书名进行查找书籍
	public Book selectBookByName(String bookName) throws SQLException {
		Book book = sd.selectBookByName(bookName);
		return book;
	}
	//结束信息
	public List<BorrowDetail> borrowMessage() throws SQLException {
		//这里是用来处理借书信息，要使用事务；
		Connection conn = JDBCUtils.getConnection();
		try {
			//开启事物
			conn.setAutoCommit(false);
			List<BorrowDetail> borrow =sd.borrowMessage();
			conn.commit();
			return borrow;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.setAutoCommit(true);
			conn.close();
		}
		return null;
	}
	//这里是用户查找自己的借书信息，无需开启事务；
	public List<BorrowDetail> ownBorrowMessage(String userName) throws SQLException {
		return new SelectDao().ownBorrowMessage(userName);
	}

}
