package controller;

import java.sql.SQLException;
import java.util.List;

import domain.Book;
import domain.BorrowDetail;
import service.SelectBookService;
import service.UpdateService;

//这里是对管理员的工作进行任务分发；
public class AdminControl {
	// 分发查找所有书籍信息的操作；
	public List<Book> selectAllBook() throws SQLException {
		return new SelectBookService().SelectAllBook();
	}

	// 分发通过书名进行查找的书籍信息；
	public Book selectBookByName(String bookName) throws SQLException {
		return new SelectBookService().selectBookByName(bookName);
	}

	// 管理员删除书籍信息的操作
	public boolean deleteBook(String deleteName) throws SQLException {

		return new UpdateService().deleteByName(deleteName);
	}

	// 管理员添加书籍信息的操作
	public boolean addBook(Book book) throws SQLException {
		return new UpdateService().addBook(book);
	}

	// 管理员修改书籍信息的操作
	public boolean updateBook(String oldBook, Book newBook) throws SQLException {
		return new UpdateService().updateBook(oldBook, newBook);
	}

	// 查看借书信息
	public List<BorrowDetail> borrowMessage() throws SQLException {
		return new SelectBookService().borrowMessage();
	}

}
