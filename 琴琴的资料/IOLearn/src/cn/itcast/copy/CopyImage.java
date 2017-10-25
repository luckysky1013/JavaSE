package cn.itcast.copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//FileInputStream和FileOutputStream复制图像；
public class CopyImage {
	public static void main(String[] args) throws IOException {
		FileInputStream fis = new FileInputStream("程序员个人规划.jpg"); 
		FileOutputStream fos = new FileOutputStream("CopyImage.jpg");
		byte[] b = new byte[1024];
		int len = 0;
		while((len = fis.read(b))!=-1){
			fos.write(b,0,len);
		}
		fis.close();
		fos.close();
		
	}
}
