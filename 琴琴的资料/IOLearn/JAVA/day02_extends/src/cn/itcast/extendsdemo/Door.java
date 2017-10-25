package cn.itcast.extendsdemo;
/*包含3个属性：宽度width 和 高度height ，颜色color
	  包含2个方法：开门和关门
			开门方法：输出“门已经打开，请进！”
			关门方法：输出“门已经关闭，禁止进入！”
			
*/
public class Door {
	private double width;
	private double height;
	private String color;
	public Door() {
	}
	public Door(double width, double height, String color) {
		super();
		this.width = width;
		this.height = height;
		this.color = color;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void openDoor(){
		System.out.println("门已经打开，请进！");
	}
	public void closeDoor(){
		System.out.println("门已经关闭，禁止进入！");
	}
}
