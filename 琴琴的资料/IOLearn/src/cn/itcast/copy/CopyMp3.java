package cn.itcast.copy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//复制MP3文件；
public class CopyMp3 {
	public static void main(String[] args) throws IOException {
		//创建输入流
		
		FileInputStream fis = new FileInputStream("郝蕾 - 氧气.mp3");
		//创建输出流；
		FileOutputStream fos = new FileOutputStream("氧气.mp3");
		//创建读取用的字节数组；
		long start = System.currentTimeMillis();
		byte[] b = new byte[1024];
		int len = 0;
		while((len = fis.read(b)) != -1){
			fos.write(b,0,len);
		}
		long end = System.currentTimeMillis();
		System.out.println("普通耗时："+(end-start));
		long start2 = System.currentTimeMillis();
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream("郝蕾 - 氧气.mp3"));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("氧气(2).mp3"));
		byte[] by = new byte[1024];
		int len2 = 0;
		while((len2 = bis.read(by))!= -1){
			bos.write(by, 0, len2);
		}
		long end2 = System.currentTimeMillis();
		System.out.println("高效流的时间："+(end2-start2));
		fis.close();
		fos.close();
	}
}
