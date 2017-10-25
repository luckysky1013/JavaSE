package cn.itcast.digui;
//1-5之间数字的递归；
public class FirstDemo {
	public static void main(String[] args) {
		int sum = getSum(1);
		System.out.println(sum);
	}
	public static int getSum(int n){
		if(n==5){
			return 1;
		}
		return n+getSum(n+1);
	}
}
