package domain;
//这里对应数据库里面的user表
public class User {
	private int id;
	private String name;
	private String password;
	private int age;
	private String gender;
	private int isadmin;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int id, String name, String password, int age, String gender, int isadmin) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.age = age;
		this.gender = gender;
		this.isadmin = isadmin;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public int getAge() {
		return age;
	}
	public String getGender() {
		return gender;
	}
	public int getIsadmin() {
		return isadmin;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setIsadmin(int isadmin) {
		this.isadmin = isadmin;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", age=" + age + ", gender=" + gender
				+ ", isadmin=" + isadmin + "]";
	}
	
	
}
