package cn.itcast.copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//ʹ��FileInputStream��FileOutputStream�������ı��ĵ�
public class CopyTxt {
	public static void main(String[] args) throws IOException {
		FileInputStream fis = new FileInputStream("a.txt");
		FileOutputStream fos = new FileOutputStream("aCopy.txt");
		int by = 0;
		while((by = fis.read())!=-1){
			fos.write((char)by);
		}
		fis.close();
		fos.close();
	}
}
