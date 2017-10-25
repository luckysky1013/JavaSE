package cn.itcast.super_test;
/*定义老手机类，有品牌属性，且属性私有化，提供相应的getXxx与setXxx方法，
 * 提供无返回值的带一个String类型参数的打电话的方法，内容为：“正在给xxx打电话...”*/
public class OldPhone {
	private String brand;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void call(String name){
		System.out.println("正在给"+name+"打电话！");
	}
}
