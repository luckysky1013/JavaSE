package cn.itcast.digui;
//不死神兔的递归实现；
public class ThirdDemo {
	public static void main(String[] args) {
		int sum=0;
		sum=fib(20);
		System.out.println(sum);
	}
	public static int fib(int n){
		if (n == 1 || n == 2) {
			return 1;
		} else {
			return fib(n - 1) + fib(n - 2);
		}
	}
}
