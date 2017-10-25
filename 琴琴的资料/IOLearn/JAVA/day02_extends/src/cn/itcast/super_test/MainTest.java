package cn.itcast.super_test;
/*3、定义测试类，创建新手机对象，并使用该对象，对父类中的品牌属性赋值；
4、使用新手机对象调用手机介绍的方法；
5、使用新手机对象调用打电话的方法；
*/
public class MainTest {

	public static void main(String[] args) {
		NewPhone nw = new NewPhone();
		nw.setBrand("诺基亚");
		nw.introduce();
		nw.call("sam");

	}

}
