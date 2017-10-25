package cn.itcast.filestream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/*在d盘目录下有一个加密文件a.txt（文件里只有英文和数字），密码是“heima”
当密码输入正确时才能读取文件里的数据。现要求用代码来模拟读取文件的过程，并统计文件里各个字母出现的次数，
并把统计结果按照如下格式输出到d盘的count.txt中*/


public class CountCharDemo2 {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入文件的密码：");
		String pass = sc.nextLine();
		if(pass.equals("123456")){
		countNum("count.txt");
		//System.out.println(countNum);
		}else{
			System.out.println("密码错误！");
		}
	}
	public static void countNum(String srcFile) throws IOException{
		BufferedInputStream bis =new BufferedInputStream(new FileInputStream(srcFile));
		HashMap<Character,Integer> hm = new HashMap<Character,Integer>();    
		HashSet<Character> set = new HashSet<Character>();
		//定义变量用来统计所有的次数
		int count = 0;
		//将文件的内容到字节数组，在将字节数组拼接成字符串；
		byte[] by = new byte[1024];
		int len = -1 ;
		String s = "";
		while((len = bis.read(by)) != -1){
			s += new String(by,0,len);
			//count += sum(s,c);
			
		}
		char[] charArray = s.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			set.add(charArray[i]);
		}
		//遍历set集合；
		for (char c : charArray) {
			count = sum(s, c);
			hm.put(c, count);
		}
		//System.out.println(s);
		System.out.println(hm);
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
