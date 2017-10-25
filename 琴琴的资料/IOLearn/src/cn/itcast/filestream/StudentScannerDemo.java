package cn.itcast.filestream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/*从控制台循环接收用户录入的学生信息，输入格式为：学号-学生名字
将学生信息保存到C盘下面的stu.txt文件中，一个学生信息占据一行数据。
当用户输入end时停止输入。
* 要求使用字节输出流写出数据
*/
public class StudentScannerDemo {

	public static void main(String[] args) throws IOException {
		/*Scanner sc = new Scanner(System.in);
		System.out.println("请输入学生信息，输入格式为：学号-学生名字（end结束）");
		String message = "";
		ArrayList<String> list = new ArrayList<String>();
		while(true){
			message = sc.nextLine();
			if(message.equals("end")){
				break;             
			}
			list.add(message);
		}
		writeMessage(list);*/
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream("stu.txt"));
		int len = -1;
		byte[] by = new byte[1024];
		String s = "";
		while((len = bis.read(by)) != -1){
			s +=new String(by,0,len);
		}
		String regex = "\r\n";
		String[] arr = s.split(regex);
		//System.out.println(arr[1]);
		
		HashMap<String,String> hm = new HashMap<String,String>();
		for (int i = 0; i < arr.length; i++) {
			String[] str = arr[i].split("-");
			hm.put(str[0], str[1]);
		}
		System.out.println(hm);
		
	}

	private static void writeMessage(ArrayList<String> list) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("stu.txt"));		
		//int len = -1;
		for (String stu : list) {
			bos.write((stu+"\r\n").getBytes());
			
		}
		bos.close();
	}
	

}
