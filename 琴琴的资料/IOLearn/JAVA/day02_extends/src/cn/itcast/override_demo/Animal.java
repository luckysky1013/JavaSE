package cn.itcast.override_demo;
/*定义动物类，有名称和年龄两个属性，且属性私有化，
 * 提供相应的getXxx与setXxx方法，提供无参数的无返回值的吃饭方法，内容为：“吃饭...”；*/
public class Animal {
	private String name;
	private int age;
	public Animal() {}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void eat(){
		System.out.println("吃饭！");
	}
	
}
