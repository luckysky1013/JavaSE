package view;

import java.sql.SQLException;
import java.util.List;

import controller.AdminControl;
import domain.Book;
import domain.BorrowDetail;

//这里是完成管理员工作的判断代码
public class AdminOperate {
	// 查看所有的书籍
	AdminControl admin = new AdminControl();

	public void selectAllBook() throws SQLException {
		// 获取控制层返回来的值，返回的是书籍的信息，用一个List集合接收，并打印；
		List<Book> book = admin.selectAllBook();
		// 输出book;
		// 判断有没有查出信息
		if (book != null) {
			for (Book book2 : book) {
				System.out.println(book2);
			}
		} else {
			System.out.println("没有查询到数据");
		}
	}

	// 通过书籍名称进行查找的处理；
	public void selectBookById(String bookName) throws SQLException {
		// 获取控制层返回来的值，返回的是书籍的信息，用一个Book对象接收，并打印；
		Book book = admin.selectBookByName(bookName);
		if (book != null) {
			System.out.println(book);
		} else {
			System.out.println("您要查询的书籍不存在！");
		}
	}

	// 处理删除的书籍的结果；
	public void deleteBook(String delete) throws SQLException {
		// 获取处理的结果；
		boolean flag = admin.deleteBook(delete);
		if (flag) {
			System.out.println("删除成功！");
		} else {
			System.out.println("删除失败");
		}

	}

	// 处理添加书籍的返回结果；
	public void addBook(Book book) throws SQLException {
		// 获取添加影响的行数，如果row等于true则添加成功，否则，添加失败；
		boolean row = admin.addBook(book);
		if (row) {
			System.out.println("添加成功！");
		} else {
			System.out.println("添加失败！");
		}
	}

	//// 处理修改书籍的返回结果；
	public void updateBook(String oldBook, Book newBook) throws SQLException {
		boolean flag = admin.updateBook(oldBook, newBook);
		if (flag) {
			System.out.println("修改成功！");
		} else {
			System.out.println("修改失败！");
		}
	}

	// 查看借书信息
	public void borrowMessage() throws SQLException {
		// 获取控制层返回来的值，返回的是书籍的信息，用一个List集合接收，并打印；
		List<BorrowDetail> borrow = admin.borrowMessage();
		// 输出book;
		// 判断有没有查出信息
		if (borrow != null) {
			for (BorrowDetail borrow2 : borrow) {
				System.out.println(borrow2);
			}
		} else {
			System.out.println("没有查询到数据");
		}
	}

}
