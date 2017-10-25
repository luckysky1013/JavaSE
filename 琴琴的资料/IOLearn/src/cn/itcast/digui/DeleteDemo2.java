package cn.itcast.digui;

import java.io.File;

//使用递归来删除文件
public class DeleteDemo2 {

	public static void main(String[] args) {
		//创建一个File对象；
		File srcFile = new File("demo\\bb\\c.txt");
		srcFile.delete();
		System.out.println(srcFile);
		//deleteAll(srcFile);

	}
	//写一个递归删除文件的方法；
	public static void deleteAll(File srcFile){
		//首先判断是不是目录
		if(!srcFile.isDirectory()){
			System.out.println(srcFile.getName()+"---"+srcFile.delete());

		}
			//获取文件路径的数组；
			File[] file = srcFile.listFiles();
			if(file!=null){
			for(File f : file){
				if(f.isDirectory()){
					deleteAll(f);
				}
				else{
					System.out.println(f.getName()+"---"+f.delete());
				}
			}
		}
		System.out.println(srcFile.getName() + "---" + srcFile.delete());
	}

}
