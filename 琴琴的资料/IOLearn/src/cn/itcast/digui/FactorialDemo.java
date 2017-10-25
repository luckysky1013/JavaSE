package cn.itcast.digui;
//求整数n的阶乘(n <=10)
public class FactorialDemo {
	public static void main(String[] args) {
		int num = factorial(10);
		System.out.println(num);
	}
	public static int factorial(int n){
		if(n==1){
			return 1;
		}
		return n*factorial(n-1);
	}

}
