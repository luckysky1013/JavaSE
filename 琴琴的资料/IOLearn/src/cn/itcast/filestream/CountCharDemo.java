package cn.itcast.filestream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//定义一个方法统计test.txt文件中指定字符出现的次数。
//比如a字符在文件中出现了10次则调用方法传入a后，方法内部输出：a出现10次  

public class CountCharDemo {

	public static void main(String[] args) throws IOException {
		int countNum = countNum("count.txt",'2');
		System.out.println(countNum);
	}
	public static int countNum(String srcFile,char c) throws IOException{
		BufferedInputStream bis =new BufferedInputStream(new FileInputStream(srcFile));

		//定义变量用来统计所有的次数
		int count = 0;
		//将文件的内容到字节数组，在将字节数组拼接成字符串；
		byte[] by = new byte[1024];
		int len = -1 ;
		String s = "";
		while((len = bis.read(by)) != -1){
			s = new String(by,0,len);
			count += sum(s,c);
		}
		//System.out.println(s);
		return count;
	}
	//用来统计指定支付在字符串中出现的次数；
	public static int sum(String s,char c){
		int sum = 0;
		char[] array = s.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if(array[i] == c){
				sum++;
			}
		}
		return sum;
	}
	

}
