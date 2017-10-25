package cn.itcast.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Set;

//创建文件读入流，查找文件里面有jianjian的，将jianjian修改为liujian；
public class SearchDemo {

	public static void main(String[] args) throws IOException {
		//创建文件输入流；
		InputStream is = new FileInputStream("read.txt");
		//创建集合；
		Properties p = new Properties();
		//读取；
		p.load(is);
		//获取到集合的键值；
		Set<String> set =p.stringPropertyNames();
		//遍历集合获取到值为jianjian
		for(String key: set){
			String value = p.getProperty(key);
			if(value.equals("jianjian")){
				value = "liujian";
				p.setProperty(key, value);
			}
		}
		//创建输出流来更新文件
		OutputStream os = new FileOutputStream("read.txt");
		p.store(os, "new property");
		is.close();
		os.close();
	}

}
