package domain;
//借书的表
public class Borrow {
	private int id;
	private int bid;
	private int uid;
	private int num;
	public Borrow() {
	}
	public Borrow(int id, int bid, int uid, int num) {
		super();
		this.id = id;
		this.bid = bid;
		this.uid = uid;
		this.num = num;
	}
	public int getId() {
		return id;
	}
	public int getBid() {
		return bid;
	}
	public int getUid() {
		return uid;
	}
	public int getNum() {
		return num;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "Borrow [id=" + id + ", bid=" + bid + ", uid=" + uid + ", num=" + num + "]";
	}
	
}
