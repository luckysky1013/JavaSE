package cn.itcast.super_test;
/*、定义新手机类，继承老手机类，重写父类的打电话的方法，
 * 内容为2句话：“语音拨号中...”、“正在给xxx打电话...”要求打印“正在给xxx打电话...”
 * 这一句调用父类的方法实现，不能在子类的方法中直接打印；
 * 提供无返回值的无参数的手机介绍的方法，
 * 内容为：“品牌为：xxx的手机很好用...”
*/
public class NewPhone extends OldPhone {
	public void call(String name){
		System.out.println("语音拨号中...");
		super.call(name);
	}
	public void introduce(){
		System.out.println("品牌为："+super.getBrand()+"的手机很好用...");
	}
}
