package cn.itcast.task3;

import java.util.ArrayList;

/*创建student类，包含年龄，姓名，成绩属性，
 * 本类中有对学生成绩打等级的方法，并返回该等级（A,B,C）*/
public class Student {
	private String name;
	private int age;
	private double score;
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(String name, int age, double score) {
		super();
		this.name = name;
		this.age = age;
		this.score = score;
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
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public char getScoreClass(){
		char c;
		if(score>100||score<0){
			c='F';
		}
		if(score>=80){
			c='A';
		}
		else if(score<=60){
			c='B';
		}
		else{
			c='C';
		}
		return c;
	}
}
