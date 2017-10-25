package cn.itcast.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//对单层文件夹进行复制；
public class CopyFileDemo {

	public static void main(String[] args) throws IOException {
		File srcFile = new File("demo\\aa");
		File desFile = new File("demo2\\bb");
		//如果目的文件夹不存在则创建；
		if(!desFile.exists()){
			desFile.mkdirs();
		}
		//获取到文件的数组；
		File[] file = srcFile.listFiles();
		//遍历数组获取到目录下的文件
		for(File f : file){
			String name = f.getName();
			File newFile = new File(desFile, name);
			copyFile(f,newFile);
		}

	}
	public static void copyFile(File file,File newFile) throws IOException{
		BufferedInputStream  bis = new BufferedInputStream(new FileInputStream(file));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
		byte[] by = new byte[1024];
		int len= 0;
		while((len = bis.read(by)) != -1){
			bos.write(by,0,len);
		}
		bis.close();
		bos.close();
	}

}
