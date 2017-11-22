package dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import domain.Book;

//用来处理数据更新的，与数据库直接交互；
public class UpdateDao {
	// 管理员删除数据
	QueryRunner qr = new QueryRunner(new ComboPooledDataSource());

	public int deleteByBookName(String deleteName) throws SQLException {
		String sql = "delete from book where bookname =? ";
		// 获取到执行删除影响的行数
		int update = qr.update(sql, deleteName);
		return update;
	}

	// 管理员添加书籍
	public int addBook(Book book) throws SQLException {
		// 要执行的sql语句；
		String sql = "insert into book (bookname,author,price,amount) values(?,?,?,?)";
		int add = qr.update(sql, book.getBookname(), book.getAuthor(), book.getPrice(), book.getAmount());
		return add;
	}

	// 管理员修改书籍
	public int updateBook(String oldBook, Book newBook) throws SQLException {
		// 要执行的sql语句；
		String sql = "update book set bookname=?,author=?,price=?,amount=? where bookname=?";
		int update = qr.update(sql, newBook.getBookname(), newBook.getAuthor(), newBook.getPrice(), newBook.getAmount(),
				oldBook);
		return update;
	}

}
