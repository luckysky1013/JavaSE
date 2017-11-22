package domain;
//这里是book的javaBean，对应数据库的book表；
public class Book {
	private int id;
	private String bookname;
	private String author;
	private int price;
	private int amount;
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book(int id, String bookname, String author, int price, int amount) {
		super();
		this.id = id;
		this.bookname = bookname;
		this.author = author;
		this.price = price;
		this.amount = amount;
	}
	public int getId() {
		return id;
	}
	public String getBookname() {
		return bookname;
	}
	public String getAuthor() {
		return author;
	}
	public int getPrice() {
		return price;
	}
	public int getAmount() {
		return amount;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "book [id=" + id + ", bookname=" + bookname + ", author=" + author + ", price=" + price + ", amount="
				+ amount + "]";
	}
	
	
}
