package cn.itcast.xuliehua;

import java.io.Serializable;

//建立一个person类来操作反序列化和序列化；
//要实现Serializable接口
public class Person implements Serializable{
	/**
	 * 
	 */
	//生成序列版本号，改动成员变量时，之前写入的信息依然能够正确读取；
	private static final long serialVersionUID = -3480080293632537979L;
	private String name;
	private int age;
	public Person() {
	}
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
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
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
	
}
