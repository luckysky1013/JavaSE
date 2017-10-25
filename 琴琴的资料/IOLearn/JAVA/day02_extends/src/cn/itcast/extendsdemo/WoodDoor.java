package cn.itcast.extendsdemo;
/*重写父类开门方法：输出门的高度  宽度  颜色  +“门已经打开，请进！”
重写父类关门方法：输出门的高度  宽度  颜色  +“门已经关闭，禁止进入！”
*/
public class WoodDoor extends Door {
	
	public WoodDoor() {
		super();
	}
	public WoodDoor(double width, double height, String color) {
		super(width, height, color);
	}
	public void show(){
		System.out.print("门的高度："+super.getHeight()+" 宽度： "+super.getWidth()+" 颜色："+super.getColor()+",");
	}
	public void openDoor(){
		show();
		super.openDoor();
	}
	public void closeDoor(){
		show();
		super.closeDoor();
	}
}
