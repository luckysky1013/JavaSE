package cn.itcast.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//实现多层文件夹的内容复制；
public class CopyFileDemo2 {

	public static void main(String[] args) throws IOException {
		File srcFile = new File("demo");
		File desFile = new File("demo2");
		getAllFile(srcFile,desFile);
	}
	//获取到源文件里面的文件信息，并进行操作；
	public static void  getAllFile(File srcFile,File desFile) throws IOException{
		if(!srcFile.isDirectory()){
			String fileName = srcFile.getName();
			File newFile = new File(desFile, fileName);
			copyFile(srcFile, newFile);
		}else{
			String name = srcFile.getName();
			
			desFile=getDirectory(desFile, name);
			File[] file = srcFile.listFiles();
			for(File f : file){
				getAllFile(f,desFile);
			}
			
		}
		
	}
	//将源文件的目录复制到目的文件
	public static File getDirectory(File desFile,String name){
		File addFile = new File(desFile, name);
		if(!addFile.exists()){
			addFile.mkdirs();
		}
		return addFile;
	}
	//将源文件的目录下的文件复制到目的文件夹下面
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
