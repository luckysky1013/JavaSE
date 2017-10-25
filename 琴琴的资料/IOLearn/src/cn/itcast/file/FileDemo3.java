package cn.itcast.file;

import java.io.File;

//1.获取文件信息：文件名，文件大小，文件的绝对路径，文件的父路径
public class FileDemo3 {

	public static void main(String[] args) {
		File file = new File("G:\\API\\jdk api 1.8_google");
		//文件名
		System.out.println(file.getName());
		//文件大小
		System.out.println(file.length());
		//文件的绝对路径
		System.out.println(file.getAbsolutePath());
		//文件的父路径；
		System.out.println(file.getParent());

	}

}
