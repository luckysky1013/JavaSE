package cn.itcast.file;

import java.io.File;
import java.text.SimpleDateFormat;

/*
 * 获取功能：
 * public String getAbsolutePath()：获取绝对路径
 * public String getPath():获取相对路径
 * public String getName():获取名称
 * public long length():获取长度。字节数
 * public long lastModified():获取最后一次的修改时间，毫秒值
 */
public class FileDemo2 {
	public static void main(String[] args) {
		File file = new File("demo\\bb\\a.txt");
		System.out.println("getAbsolutePath():"+file.getAbsolutePath());
		System.out.println("getPath:"+file.getPath());
		System.out.println("name:"+file.getName());
		System.out.println("长度："+file.length());
		SimpleDateFormat date = new SimpleDateFormat("yy-MM--dd hh:mm:ss");
		String s = date.format(file.lastModified());
		System.out.println(s);
		System.out.println(file.lastModified());
	}
}
