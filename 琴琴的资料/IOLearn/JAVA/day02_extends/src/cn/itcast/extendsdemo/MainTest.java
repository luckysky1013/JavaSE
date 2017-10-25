package cn.itcast.extendsdemo;
/*	分别创建 门对象  和 木头门对象， 为创建的木头门对象属性赋值, 调用开门和关门两个方法。*/
public class MainTest {

	public static void main(String[] args) {
		Door d = new Door();
		WoodDoor wd = new WoodDoor(1.8,1.5,"red");
	/*	wd.setHeight(1.8);
		wd.setWidth(1.5);
		wd.setColor("black");*/
		d.openDoor();
		d.closeDoor();
		wd.openDoor();
		wd.closeDoor();

	}

}
