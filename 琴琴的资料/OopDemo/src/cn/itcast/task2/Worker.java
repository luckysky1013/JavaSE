package cn.itcast.task2;
/*姓名，年龄，工资，月份*/
public class Worker {
	private String name;
	private int age;
	private int salary;
	private int month;
	public Worker() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Worker(String name, int age, int salary, int month) {
		super();
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.month = month;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}

}
