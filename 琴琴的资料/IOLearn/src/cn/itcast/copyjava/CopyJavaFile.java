package cn.itcast.copyjava;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/*控制台获取输入的文件目录然后将该目录(包含子目录)下的.java文件复制到D:/java文件夹中,并统计java文件的个数.
提示:如果有相同的名称的文件,如果两个Test01.java,则拷贝到目标文件夹时只能有一个Test01.java,
另一个Test01.java要修改为另一个名称:该名称可随机生成,只要不重复即可.
*/
public class CopyJavaFile {

	public static void main(String[] args) throws IOException {
	/*	Scanner sc = new Scanner(System.in);
		System.out.println("请输入目录：");
		String path = sc.nextLine();*/
		File srcFile = new File("D:\\eclipseWorkplace\\day02_extends");
		File desFile = new File("jAVA");
		int count = copyJavaFile(srcFile,desFile);
		System.out.println(count);

	}
	public static int copyJavaFile(File srcFile,File desFile) throws IOException{
		int count = 0;
		Random rand = new Random();
		HashSet<String> set = new HashSet<String>();
		if(!srcFile.isDirectory()){
			if(srcFile.getName().endsWith(".java")){
				count++;
				boolean flag = set.add(srcFile.getName());
				if(!flag){
					String name = srcFile.getName().substring(0, srcFile.getName().length()-5)+rand.nextInt(100)+".java";
					desFile = getDirectory(desFile, name);
					copyFile(srcFile,desFile);
				}
				copyFile(srcFile,desFile);
			}
		}else{
			File[] files = srcFile.listFiles();
			for (File file : files) {
				if(file.isDirectory()){
					copyJavaFile(srcFile,desFile);
				}
				else{
					if(file.getName().endsWith(".java")){
						count++;
						boolean flag = set.add(file.getName());
						if(!flag){
							String name = file.getName().substring(0, file.getName().length()-5)+rand.nextInt(100)+".java";
							desFile = getDirectory(desFile, name);
							copyFile(srcFile,desFile);
						}
						copyFile(file,desFile);
					}
				}
			}
		}
		
		return count++;
		
	}
	public static File getDirectory(File desFile,String name){
		File addFile = new File(desFile, name);
		if(!addFile.exists()){
			addFile.mkdirs();
		}
		return addFile;
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
