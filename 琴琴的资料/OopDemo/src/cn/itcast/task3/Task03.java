package cn.itcast.task3;

import java.util.ArrayList;

/*���������У� ��������Student���󲢴���method(ArrayList<Student> stu)������
method������Ҫ�� �÷������жԵȼ������жϵķ��������ΪA�ȼ����ӡ��ѧ��������������һ��С�컨~~~~
*/
public class Task03 {
	public static void main(String[] args) {
		Student s1 = new Student("����",22,100);
		Student s2 = new Student("����",21,60);
		Student s3 = new Student("����",22,80);
		ArrayList<Student> list = new ArrayList<>();
		list.add(s1);
		list.add(s2);
		list.add(s3);
		method(list);
	}
	public static void method(ArrayList<Student> list){
		for(Student s : list){
			if(s.getScoreClass()=='A'){
				System.out.println("����"+s.getName()+"һ��С�컨~~~");
			}
		}
	}
}
