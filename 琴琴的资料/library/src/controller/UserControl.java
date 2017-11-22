package controller;

import java.sql.SQLException;
import java.util.List;

import domain.Book;
import domain.BorrowDetail;
import service.BorrowBookServer;
import service.SelectBookService;

//这里对用户的操作进行任务分发；
public class UserControl {

	// 分发查找所有书籍信息的操作；
	public List<Book> selectAllBook() throws SQLException {
		return new SelectBookService().SelectAllBook();
	}

	// 分发用户通过书名查找书籍信息的功能；
	public Book selectBookByName(String bookName) throws SQLException {
		return new SelectBookService().selectBookByName(bookName);
	}

	// 这里是看自己的借书信息，开始分发信息；；
	public List<BorrowDetail> ownBorrowMessage(String userName) throws SQLException {

		return new SelectBookService().ownBorrowMessage(userName);
	}

	// 这里是执行借书的任务分发；
	public int borrowBook(String userName, String borrowName, int amount) throws SQLException {
		return new BorrowBookServer().borrowBook(userName, borrowName, amount);
	}

	// 分发还书的任务
	public boolean returnBookMessage(String userName, String returnBook) throws SQLException {
		
		return new BorrowBookServer().returnBookMessage(userName,returnBook);
	}

}
