package cn.itcast.task2;
/*请使用面向对象思想描述工人类（姓名，年龄，工资，月份）
要求包含成员变量和成员方法，构造方法（无参，满参）
主方法中要求调用method(工人1，工人2)方法使用匿名对象传参；
method方法内要求计算两位工人当前月份的工资和，并返参打印
*/
public class Task02 {
	public static void main(String[] args) {
		int sumSalary = method(new Worker("张三",22,2000,12),new Worker("李四",21,3000,12));
		System.out.println("两位工人当前月份的工资和"+sumSalary);
	}
	public static int method(Worker w1,Worker w2){
		return w1.getSalary()+w2.getSalary();
	}
}
