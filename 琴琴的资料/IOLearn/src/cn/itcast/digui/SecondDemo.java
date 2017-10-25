package cn.itcast.digui;

import java.io.File;

//若指定的目录有子目录，那么把子目录中的文件路径也打印出来
public class SecondDemo {
	public static void main(String[] args) {
		File file = new File("demo2");
		getAllFiles(file);
	}
	public static void getAllFiles(File file){
		//首先在盘符下查找，如果不是存在的目录，则认为是文件将文件打印；
		if(!file.isDirectory()){
			System.out.println(file);
		}
		//否则就递归查询该目录下的的文件夹或者文件，是文件夹则继续递归，是文件则直接输出；
		else{
		File[] files = file.listFiles();
		//遍历文件数组获取到每一个路径；
		for(File f : files){
			//判断当前遍历到的是否为目录
			if(f.isDirectory()){
				//是则继续遍历，递归调用；
				getAllFiles(f);
			}
			else{
				//如果不是目录则为文件，进行输出；
				System.out.println(f);
			}
		}
		}
	}
}
