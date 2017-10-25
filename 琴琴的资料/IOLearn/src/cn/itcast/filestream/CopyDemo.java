package cn.itcast.filestream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*用字节流将C盘下的a.png图片复制到D盘下(文件名保存一致)
要求：一次读写一个字节数组的方式进行复制
*/
public class CopyDemo {

	public static void main(String[] args) throws IOException {
		BufferedInputStream bis =new BufferedInputStream(new FileInputStream("氧气.mp3"));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("demo\\氧气.mp3"));
		int len = -1 ;
		while((len = bis.read()) != -1){
			bos.write(len);
		}
		bis.close();
		bos.close();
	}

}
