package cn.itcast.file;

import java.io.File;

//file�Ĺ��췽����File(File parent, String child) 
//���� parent ����·������ child ·�����ַ�������һ���� File ʵ����
public class FileDemo1 {
	public static void main(String[] args) {
		File file1 = new File("d:\\demo");
		System.out.println("mkdir:"+file1.mkdir());
	}
}
