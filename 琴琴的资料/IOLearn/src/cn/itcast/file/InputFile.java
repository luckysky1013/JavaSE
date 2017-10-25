package cn.itcast.file;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/*键盘录入一个文件路径，根据文件路径创建File对象
如果输入的文件路径对应的文件不存在，则创建该文件。
如果输入的文件路径对应的文件已经存在了，则获得文件大小并输出。
*/
public class InputFile {

	public static void main(String[] args) {
		System.out.println("请输入一个文件路径:");
		Scanner sc =new Scanner(System.in);
		String filePath = sc.nextLine();
		//创建文件；
		File file = new File(filePath);
		//判断是否存在
		if(file.exists()){
			System.out.println(file.length());
		}else{
			//不存在，判断是不是文件夹；
			if(file.isDirectory()){
				//是，直接创建；
			boolean flag = file.mkdirs();
			if(flag){
				System.out.println("创建成功！");
			//输入有误的话就不创建；
			}else{
				System.out.println("输入有误！");
			}
			}
			else{
				//不是文件夹，直接，先创建文件夹，在创建文件；
				int index = filePath.lastIndexOf("\\");
				//判断是否是输入的合法的路径
				if(index!=-1){
					//是的话说明是可能是文件，先创建文件目录，在创建文件
				String dir = filePath.substring(0, index-1);
				File newFile = new File(dir);
				String theFile = filePath.substring(index+1);
				System.out.println(theFile);
				boolean flag = newFile.mkdirs();
				if(flag){
					try {
						file.createNewFile();
					} catch (IOException e) {
						System.out.println("你输入的文件有误！");
						//e.printStackTrace();
					}
				}
				else{
					System.out.println("输入有误！");
				}
				}else{
					System.out.println("你的输入有误！");
				}
				
			}
		}

	}

}
