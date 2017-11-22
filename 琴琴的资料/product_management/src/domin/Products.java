package domin;
//创建一个products的实体类；
public class Products {
	private int pid;
	private String Pname;
	private int price;
	private String flag;
	private String category_id;
	public Products() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Products(int pid, String pname, int price, String flag, String category_id) {
		super();
		this.pid = pid;
		Pname = pname;
		this.price = price;
		this.flag = flag;
		this.category_id = category_id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPname() {
		return Pname;
	}
	public void setPname(String pname) {
		Pname = pname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	@Override
	public String toString() {
		return "Products [pid=" + pid + ", Pname=" + Pname + ", price=" + price + ", flag=" + flag + ", category_id="
				+ category_id + "]";
	}
	
}
