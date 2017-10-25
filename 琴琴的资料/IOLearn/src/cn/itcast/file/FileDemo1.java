package cn.itcast.file;

import java.io.File;

//file的构造方法；File(File parent, String child) 
//根据 parent 抽象路径名和 child 路径名字符串创建一个新 File 实例。
public class FileDemo1 {
	public static void main(String[] args) {
		File file1 = new File("d:\\demo");
		System.out.println("mkdir:"+file1.mkdir());
	}
}
