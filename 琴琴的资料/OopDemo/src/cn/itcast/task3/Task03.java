package cn.itcast.task3;

import java.util.ArrayList;

/*在主方法中： 创建三个Student对象并传入method(ArrayList<Student> stu)方法中
method方法内要求： 该方法内有对等级进行判断的方法，如果为A等级则打印该学生姓名，并奖励一朵小红花~~~~
*/
public class Task03 {
	public static void main(String[] args) {
		Student s1 = new Student("张三",22,100);
		Student s2 = new Student("李四",21,60);
		Student s3 = new Student("王五",22,80);
		ArrayList<Student> list = new ArrayList<>();
		list.add(s1);
		list.add(s2);
		list.add(s3);
		method(list);
	}
	public static void method(ArrayList<Student> list){
		for(Student s : list){
			if(s.getScoreClass()=='A'){
				System.out.println("奖励"+s.getName()+"一朵小红花~~~");
			}
		}
	}
}
