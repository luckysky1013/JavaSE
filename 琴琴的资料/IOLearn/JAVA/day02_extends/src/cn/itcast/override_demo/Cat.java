package cn.itcast.override_demo;
/*定义猫类，继承动物类，重写父类中的吃饭方法，内容为：“猫吃鱼...”*/
public class Cat extends Animal{
	public void eat(){
		System.out.println("猫吃鱼...");
	}
}
