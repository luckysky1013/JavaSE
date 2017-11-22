package view;

import java.sql.SQLException;
import java.util.Scanner;

import controller.CheckUser;
import domain.Book;
import domain.User;

//这里是用来写整个页面的界面；
public class screen {
	static Scanner sc = new Scanner(System.in);

	public static void start() {
		while (true) {
			// 系统入口
			System.out.println("-------欢迎光临图书管理系统-------");
			System.out.println("请输入你的选择：a.登录 b.注册  c.退出");
			String firChoose = sc.next();
			switch (firChoose.toLowerCase()) {
			// 登录
			case "a":
				login();

				break;
			case "b":
				register();

				break;
			case "c":
				break;
			default:
				System.out.println("您的输入有误！");
			}
		}
	}

	private static void register() {
		System.out.println("请输入您的姓名：");
		String username = sc.next();
		System.out.println("请输入您的密码：");
		String password = sc.next();
		System.out.println("请输入您的年龄：");
		int age = sc.nextInt();
		System.out.println("请输入您的性别：");
		String gender = sc.next();
		// 将输入的信息封装成user；
		User user = new User();
		user.setName(username);
		user.setPassword(password);
		user.setAge(age);
		user.setGender(gender);
		// 获取CheckUser的对象，用来调用注册的方法
		CheckUser cu = new CheckUser();
		boolean register;
		try {
			// 获取注册的结果
			register = cu.register(user);
			// 判断是否注册成功
			if (register) {
				// 注册成功的操作
				System.out.println("1.登录 2.退出");
				String secChoose = sc.next();
				// 选择1直接登录
				if ("1".equals(secChoose)) {
					login();
				}
				// 选择2直接退出
				else if ("2".equals(secChoose)) {
					System.exit(0);
				}
				// 输入有误
				else {
					System.out.println("您的输入有误！");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 如果注册成功，则可以选择登录，或者退出；

	}

	public static void main(String[] args) {
		start();
	}

	// 用户登录的判断；
	private static void login() {
		System.out.println("请输入您的用户名：");
		String userName = sc.next();
		System.out.println("请输入您的密码：");
		String passWord = sc.next();
		CheckUser cu = new CheckUser();
		//// 这里是用来判断用户登录是否成功，返回1代表是管理员，返回2代表是普通用户，返回3代表登录失败；
		// 设置初始化的结果为登录失败
		int result = 3;
		try {
			result = cu.check(userName, passWord);
			// 编写管理员的工作代码
			if (result == 1) {
				System.out.println("欢迎您！管理员。");
				while (true) {
					System.out.println("请输入您的选择：1、查看所有书的信息 2、书名查找书籍信息 3、删除书籍  4、增加书籍  5、修改书的信息 6、查看借书信息 7 退出");
					String adminChoose = sc.next();
					// 管理员选择的代码
					AdminOperate admin = new AdminOperate();
					switch (adminChoose) {
					// 查看所有的书籍；
					case "1":
						admin.selectAllBook();
						break;
					// 通过书名查询信息；
					case "2":
						System.out.println("请输入您要查找的书名");
						String bookName = sc.next();
						admin.selectBookById(bookName);
						break;
					// 删除书籍
					case "3":
						System.out.println("请输入您要删除的书名:");
						String delete = sc.next();
						System.out.println("您要删除的书籍的信息：");
						admin.selectBookById(delete);
						System.out.println("1 确定 2取消");
						String confirm = sc.next();
						if ("1".equals(confirm)) {
							admin.deleteBook(delete);
						} else {
							System.out.println("取消删除");
						}
						break;
					// 增加书籍
					case "4":
						System.out.println("请输入书名");
						String name = sc.next();
						System.out.println("请输入作者");
						String author = sc.next();
						System.out.println("请输入价格");
						int price = sc.nextInt();
						System.out.println("请输入数量");
						int amount = sc.nextInt();
						// 将增加的信息封装到Book里面
						Book book = new Book();
						book.setBookname(name);
						book.setAuthor(author);
						book.setPrice(price);
						book.setAmount(amount);
						admin.addBook(book);
						break;
					// 5、修改书的信息
					case "5":
						System.out.println("请输入你要修改的书籍名称");
						String oldBook = sc.next();
						System.out.println("请新的书名");
						String newName = sc.next();
						System.out.println("请新的作者");
						String newAuthor = sc.next();
						System.out.println("请输入新的价格");
						int newPrice = sc.nextInt();
						System.out.println("请输入新的数量");
						int newAmount = sc.nextInt();
						// 将增加的信息封装到Book里面
						Book newBook = new Book();
						newBook.setBookname(newName);
						newBook.setAuthor(newAuthor);
						newBook.setPrice(newPrice);
						newBook.setAmount(newAmount);
						admin.updateBook(oldBook, newBook);
						break;
					// 查看借书信息
					case "6":
						admin.borrowMessage();
						break;
					case "7":
						System.out.println("退出管理系统");
						return;
					default:
						System.out.println("您的输入有误！");
						break;
					}
				}
			} else if (result == 2) {// 这里是普通用户登陆后的代码
				System.out.println("欢迎您！" + userName);
				while (true) {
					System.out.println("请输入您的选择：1、查看所有书的信息 2、书名查找书籍信息  3、查看借书信息  4、通过书名借书  5、还书  6、退出");
					String publicChoose = sc.next();
					// 普通用户选择的代码
					UserOperate userOperate = new UserOperate();
					switch (publicChoose) {
					// 查看所有的书籍；
					case "1":
						userOperate.selectAllBook();
						break;
					case "2":
						System.out.println("请输入您要查找的书名");
						String bookName = sc.next();
						userOperate.selectBookById(bookName);
						break;
					// 3、查看借书信息 ,这里查看的是自己的借书信息
					case "3":
						userOperate.showOwnBorrow(userName);
						break;
					// 通过书名借书
					case "4":
						// 首先输入要查找的书；
						System.out.println("请输入您要借阅的书籍：");
						String borrowName = sc.next();
						System.out.println("请输入你要借阅的数量：");
						int amount = sc.nextInt();
						// 直接查找；
						userOperate.borrowBook(userName, borrowName, amount);
						break;
					// 还书；
					case "5":
						System.out.println("请输入你要归还的书名：");
						String returnBook = sc.next();
						userOperate.returnBookMessage(userName,returnBook);
						break;
					case "6":
						break;
					default:
						break;
					}
				}
			} else if (result == 3) {
				System.out.println("用户名或者密码不对！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
