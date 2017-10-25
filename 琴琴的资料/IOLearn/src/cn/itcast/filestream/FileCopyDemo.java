package cn.itcast.filestream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// 要求所有txt的文件内容都复制到同一个文件中
public class FileCopyDemo {

	public static void main(String[] args) throws IOException {
		//创建一个字节输出流，可以续写，用来存储所有的文件内容；
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("all.txt",true));
		//创建读取文件
		File file = new File("Copy");
		copyFile(bos,file);
		bos.close();
	}

	private static void copyFile(BufferedOutputStream bos, File file) throws IOException {
		if(!file.isDirectory()){
			writeToFile(bos,file);
		}else{
			File[] files = file.listFiles();
			for (File file2 : files) {
				if(file2.isDirectory()){
					copyFile(bos,file2);
				}else{
					writeToFile(bos,file2);
				}
			}
		}
		
	}

	private static void writeToFile(BufferedOutputStream bos, File file) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file.getName()));                                                                                                                                                                                                                                                                                        
		int len = -1 ;
		byte[] by = new byte[1024];
		while((len = bis.read(by))!=-1){
			bos.write(by, 0, len);
		}
		bis.close();
		
	}

}
