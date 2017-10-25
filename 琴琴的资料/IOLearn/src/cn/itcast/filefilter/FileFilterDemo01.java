package cn.itcast.filefilter;

import java.io.File;
import java.io.FilenameFilter;

/*使用文件过滤器筛选将指定文件夹下的小于200K的小文件获取并打印(包括所有子文件夹的文件)。*/
public class FileFilterDemo01 {
	public static void main(String[] args) {
		File file = new File("bb");
		File[] files = file.listFiles();
		for (File file2 : files) {
			String[] newFiles = file2.list(new FilenameFilter(){

				@Override
				public boolean accept(File dir, String name) {
					if(dir.isFile()&&dir.length()<200){
						return true;
					}else{
					return false;
					}
				}
				
			});
			if(newFiles.length != 0){
				for (int i = 0; i < newFiles.length; i++) {
					System.out.println(newFiles[i]);
				}
			}
			
		}
		
	}
}
