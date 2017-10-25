package cn.itcast.copy;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//使用BufferedOutputStream来实现高效缓冲流输出；
public class BufferedOutDemo {
	public static void main(String[] args) throws IOException {
		//创建缓冲字节流对象；
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("bos.txt"));
		//通过字节流来进行写入；
		String s = "hello";
		bos.write(s.getBytes());
		bos.close();
	}
}
