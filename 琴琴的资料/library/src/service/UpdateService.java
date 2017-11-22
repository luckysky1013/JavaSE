package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.JDBCUtils;
import dao.UpdateDao;
import domain.Book;
import domain.BorrowDetail;

//这个类是用来对更新数据操作的层
public class UpdateService {
	// 用来处理执行删除书籍信息的业务逻辑处理
	UpdateDao ud = new UpdateDao();

	public boolean deleteByName(String deleteName) throws SQLException {

		// 如果删除影响的行数为1，则执行删除成功，否则删除失败；
		int row = ud.deleteByBookName(deleteName);
		if (row == 1) {
			return true;
		}

		return false;
	}

	// 来处理执行增加书籍信息的业务逻辑处理
	public boolean addBook(Book book) throws SQLException {
		// 如果添加影响的行数为1，则证明添加成功，否则添加失败
		int row = ud.addBook(book);
		if (row == 1) {
			return true;
		}
		return false;
	}

	// 来处理执行修改书籍信息的业务逻辑处理
	public boolean updateBook(String oldBook, Book newBook) throws SQLException {
		// 如果修改影响的行数为1，则证明添加成功，否则添加失败
		int row = ud.updateBook(oldBook, newBook);
		if (row == 1) {
			return true;
		}
		return false;
	}
	

}
