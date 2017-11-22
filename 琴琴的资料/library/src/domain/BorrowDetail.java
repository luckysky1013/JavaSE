package domain;
//这里是一个前台javaBean
public class BorrowDetail {
	private String name;
	private String bookname;
	private int num;
	public BorrowDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BorrowDetail(String name, String bookname, int num) {
		super();
		this.name = name;
		this.bookname = bookname;
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "BorrowDetail [name=" + name + ", bookname=" + bookname + ", num=" + num + "]";
	}
	
	
}
