package cn.itcast.filestream;

import java.io.FileInputStream;
import java.io.IOException;

//一次读取一个字节；
public class FileInputStreamDemo {

	public static void main(String[] args) throws IOException {
		//创建字节输入流；
		FileInputStream fis = new FileInputStream("demo\\java.txt");
		int len = -1;
		while((len = fis.read()) != -1){
			System.out.println((char)len);
		}
		fis.close();
	}

}
