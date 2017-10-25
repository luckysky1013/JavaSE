package cn.itcast.digui;
//求1到n的和(n>=100 && n<=200)
public class SumDemo {
	public static void main(String[] args) {
		
		int sum = getSum(150);
		System.out.println(sum);

	}
	public static int getSum(int n){
		if(n==1){
			return n=1;
		}
		return getSum(n-1)+n;
	}

}
