package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.BorrowBookDao;
import dao.JDBCUtils;
import dao.SelectDao;
import domain.Book;

//这里是处理借书和还书的
public class BorrowBookServer {
	// 在这里处理借书的信息，需要开启事务，防止借书的时候
	public int borrowBook(String userName,String borrowName, int amount) throws SQLException {
		// 获取连接；
		Connection conn = JDBCUtils.getConnection();
		// 开启事物；
		try {
			conn.setAutoCommit(false);
			//获取到查询出来的结果；
			SelectDao sd = new SelectDao();
			Book book= sd.selectBookByName(borrowName);
			//如果没有查询到书籍，返回-1
			if(book==null) {
				return -1;
			}
			//查询到结果，执行更新；更新失败则返回0，库存不足，回滚事务；
			BorrowBookDao borrowDao = new BorrowBookDao();
			//更新书籍信息；
			int row = borrowDao.updateBook(borrowName,amount);
			//更新结束表；
			int row2 =borrowDao.updateBorrow(userName,borrowName,amount);
			//都成功了将借书信息添加到借书表，提交事务
			if(row==1&&row2==1){
				//更新都成功了，就执行提交，
				conn.commit();
				return 1;
			}
			else{
				//否则回滚
				conn.rollback();
				//库存不足
				return -1;
			}
			
			
		} catch (Exception e) {
			//发生异常，回滚事务；
			conn.rollback();
			e.printStackTrace();
		}finally{
			conn.setAutoCommit(true);
		}
		return 0;
	}
	//处理在还书数据库中执行操作；
	public boolean returnBookMessage(String userName, String returnBook) throws SQLException {
		//获取连接
		Connection conn = JDBCUtils.getConnection();
		//开启事务；
		try {
			conn.setAutoCommit(false);
			//获取到查询出来的结果；
			SelectDao sd = new SelectDao();
			Book book= sd.selectBookByName(returnBook);
			//如果没有查询到书籍，返回-1
			if(book==null) {
				return false;
			}
			//查询到结果，执行更新；更新失败则返回0，库存不足，回滚事务；
			BorrowBookDao borrowDao = new BorrowBookDao();
			//更新书籍信息,将归还的书籍归还给book表；
			int row = borrowDao.returnBook(userName,returnBook);
			//删除该条借书记录；
			int row2 =borrowDao.deleteBorrow(userName,returnBook);
			//都成功了将借书信息添加到借书表，提交事务
			if(row==1&&row2==1){
				//更新都成功了，就执行提交，还书成功，
				conn.commit();
				return true;
			}
			else{
				//否则回滚
				conn.rollback();
				//还书失败
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.setAutoCommit(true);
		}
		
		return false;
	}

}
