package cn.itcast.properties;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

//将io和Properties集合结合使用；
/* void load(Reader reader)  按简单的面向行的格式从输入字符流中读取属性列表（键和元素对）。 
 * void load(InputStream inStream)    从输入流中读取属性列表（键和元素对）。 
 * void store(OutputStream out, String comments)   以适合使用 load(InputStream) 方法加载到 Properties 表中的格式，将此 Properties 表中的属性列表（键和元素对）写入输出流。 
 * void store(Writer writer, String comments)  以适合使用 load(Reader) 方法的格式，将此 Properties 表中的属性列表（键和元素对）写入输出字符。 
//可以用来加载和存储单机游戏的进度；
 * */
public class IoAndProperties {

	public static void main(String[] args) throws IOException {
		//myRead();
		myWrite();
		
	}
	public static void myRead() throws IOException{
		//创建集合；
				Properties p = new Properties();
				//创建输入的对象
				//文件必须是键值对的形式；
				Reader rd = new FileReader("read.txt");
				p.load(rd);
				System.out.println(p);
				rd.close();
	}
	public static void myWrite() throws IOException{
		//创建集合；
		Properties p = new Properties();
		//添加数据；
		p.setProperty("001", "jianjian");
		p.setProperty("002", "qinqin");
		//创建输出对象；
		Writer wr = new FileWriter("write.txt");
		//将集合的信息输出到文件；
		p.store(wr, "this is my demo.");
	}

}
