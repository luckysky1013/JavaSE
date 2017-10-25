package cn.itcast.override_demo;
/*定义测试类，分别创建猫对象和狗对象，并分别给父类对象中的名称和年龄属性赋值；
5、分别使用猫对象和狗对象获取名称和年龄的属性值并打印在控制台上；
6、分别使用猫对象和狗对象调用吃饭方法；
*/
public class MainTest {

	public static void main(String[] args) {
		Cat c  = new Cat();
		Dog d = new Dog();
		c.setName("汤姆猫");
		c.setAge(3);
		d.setName("八公");
		d.setAge(8);
		System.out.println("猫的名字为："+c.getName()+" 年龄为："+c.getAge());
		System.out.println("狗的名字为："+d.getName()+" 年龄为："+d.getAge());
		c.eat();
		d.eat();

	}

}
