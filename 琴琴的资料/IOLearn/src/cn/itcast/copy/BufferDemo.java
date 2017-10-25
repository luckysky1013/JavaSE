package cn.itcast.copy;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

//使用BufferredInputStream来读取文件；
public class BufferDemo {
	public static void main(String[] args) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream("a.txt"));
		int len = 0;
		while((len = bis.read())!=-1){
			System.out.print((char)len);
		}
		bis.close();
	}
}
