package view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.ControlDemo;
import domin.Products;

//这里的代码实现界面显示；
public class ViewDemo {
	public void getView() throws SQLException{
		Scanner sc = new Scanner(System.in);
		while(true){
			ControlDemo cd = new ControlDemo();
			System.out.println("请输入您的选择：");
			System.out.println("C.创建   U.更新   D.删除   DA.删除所有     I.通过id查询      SA.查询所有    Q.退出");
			String choose = sc.next(); 
			switch(choose){
			//添加数据
			case "C":
				System.out.println("请输入商品名");
				String name =sc.next();
				System.out.println("请输入商品价格：");
				int price = sc.nextInt();
				boolean create = cd.create(name,price);
				if(create){
					System.out.println("添加成功！");
				}
				else{
					System.out.println("添加失败！");
				}
				break;
				//修改数据
			case "U":
				System.out.println("请输入要修改的商品id：");
				int id =sc.nextInt();
				boolean bool = cd.checkId(id);
				if(bool){
					System.out.println("请输入修改的商品名：");
					String changeName = sc.next();
					System.out.println("请输入要修改的价格：");
					int changePrice =sc.nextInt();
					boolean change =cd.changeMessage(id,changeName,changePrice);
					if(change){
						System.out.println("修改成功！");
					}
					else{
						System.out.println("修改失败！");
					}
				}else{
					System.out.println("不存在该id!");
				}
				break;
				//用于删除数据：
			case "D":
				System.out.println("请输入要删除的商品id：");
				int deleId =sc.nextInt();
				boolean checkDelete = cd.checkId(deleId);
				if(checkDelete){
					boolean delete =cd.deleteMessage(deleId);
					if(delete){
						System.out.println("删除成功！");
					}
					else{
						System.out.println("删除失败！");
					}
				}else{
					System.out.println("不存在该id!");
				}
				break;
				//删除所有数据
			case "DA":
				boolean delAll =cd.deleteAll();
				if(delAll) 
					System.out.println("删除成功");
				else 
					System.out.println("删除失败");
				break;
				//通过id来查询
			case "I":
				System.out.println("请输入要查询的商品id：");
				int selectId =sc.nextInt();
				boolean checkId = cd.checkId(selectId);
				if(checkId){
				Products products = cd.selectById(selectId);
				
				System.out.println(products);
				}else{
					System.out.println("不存在该id!");
				}
				break;
				//查询所有的信息
			case "SA":
				List<Products> list =cd.selectAll();
				for (Products products2 : list) {
					System.out.println(products2);
				}
				break;
				//退出程序
			case "Q":
				System.out.println("谢谢使用，下次再见！");
				System.exit(0);
				break;
			default:
				System.out.println("您的输入有误，请重新输入！");
				break;
			}
		}
	}
}
