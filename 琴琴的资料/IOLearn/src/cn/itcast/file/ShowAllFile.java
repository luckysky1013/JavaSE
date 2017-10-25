package cn.itcast.file;

import java.io.File;
import java.io.FileFilter;

//递归遍历将指定文件夹的所有文件(包括所有子文件夹的文件)的全路径输出在控制台。
public class ShowAllFile {

	public static void main(String[] args) {
		File  file = new File("demo2");
		getAllFile(file);
	}
	public static void getAllFile(File file){
			MyFile m = new MyFile();
			System.out.println(m.accept(file));
			File[] files = file.listFiles(new MyFile());
			//判断文件是不是空的；
			if(files.length==0){
				System.out.println(file);
			}
			for (File file2 : files) {
				
				if(file2.isDirectory()){
					getAllFile(file2);
				
				}else{
					System.out.println(file2);
					//return;
				}
				
			}
			
//			System.out.println(file);
		}
		
	
}
class MyFile implements FileFilter{
	public boolean accept(File pathname){
		return pathname.getName().toLowerCase().endsWith(".txt");
		}
}
