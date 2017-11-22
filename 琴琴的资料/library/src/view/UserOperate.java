package view;

import java.sql.SQLException;
import java.util.List;

import controller.UserControl;
import domain.Book;
import domain.BorrowDetail;

//这里是对用户的选择信息进行处理；
public class UserOperate {
	// 这里是对用户的查看所有书籍进行处理的代码
	// 分发查找所有书籍信息的操作；
	// 查看所有的书籍
	UserControl userContrl = new UserControl();

	public void selectAllBook() throws SQLException {
		// 获取控制层返回来的值，返回的是书籍的信息，用一个Book对象接收，并打印；
		List<Book> book = userContrl.selectAllBook();
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

	// 通过书名进行查找书籍；
	public void selectBookById(String bookName) throws SQLException {
		// 获取控制层返回来的值，返回的是书籍的信息，用一个Book对象接收，并打印；
		Book book = userContrl.selectBookByName(bookName);
		if (book != null) {
			System.out.println(book);
		} else {
			System.out.println("您要查询的书籍不存在！");
		}
	}

	// 用户查看自己的借书信息
	public void showOwnBorrow(String userName) throws SQLException {
		// 查找出来的是借书的的信息的集合；
		List<BorrowDetail> borrow = userContrl.ownBorrowMessage(userName);
		if (borrow != null) {
			// 打印；
			System.out.println("您的借书信息如下：");
			for (BorrowDetail detail : borrow) {
				System.out.println(detail);

			}
		} else {
			System.out.println("您还没有借书！");
		}
	}

	// 解析借书的结果，反馈给用户；
	public void borrowBook(String userName,String borrowName,int amount) throws SQLException {
		//返回的值为1，代表借书成功，
		//返回的值为0，代表书籍库存不足；
		//返回值为-1，代表查找的书籍不存在；
		if(amount<=0){
			System.out.println("借阅的书籍数量有误！");
			return;
		}
		int result = userContrl.borrowBook(userName,borrowName,amount);
		if(result==1){
			System.out.println("借书成功！");
		}else if(result==0){
			System.out.println("书籍库存不足！");
		}else if(result==-1){
			System.out.println("您查找的书籍不存在！");
		}
	}
	//还书返回的结果；
	public void returnBookMessage(String userName,String returnBook) throws SQLException {
		//获得还书的结果；
		boolean flag = userContrl.returnBookMessage(userName,returnBook);
		if(flag){
			System.out.println("还书成功！");
		}else{
			System.out.println("还书失败，可能的结果是书名错误！");
		}
	}

}
