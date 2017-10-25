package cn.itcast.filestream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*1.文件的续写和换行输出
3.2． 训练描述
利用字节输出流对象往C盘下c.txt文件输出5句：”i love java”
要求：
1.不能覆盖文件中原有的内容
2.每一句输出各占一行
*/
public class FileStreamDemo {

	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream("demo\\java.txt",true);
		for (int i = 0; i < 5; i++) {
			fos.write("i love java\r\n".getBytes());
		}
		fos.close();

	}

}
