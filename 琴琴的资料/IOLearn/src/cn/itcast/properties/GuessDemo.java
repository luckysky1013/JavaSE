package cn.itcast.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

//猜数字小游戏，用IO实现，如果猜的次数超过五次，则不能继续输入，请付费；
public class GuessDemo {
	
	public static void main(String[] args) throws IOException {
		Properties prop = new Properties();
		InputStream is = new FileInputStream("guess.txt");
		prop.load(is);
		is.close();
		String num = prop.getProperty("count");
		int number = Integer.parseInt(num);
		if(number>5){
			System.out.println("免费体验已经结束，请付费！");
		}
		else{
			number++;
			//将int类型的数据转换成String类型的；
			prop.setProperty("count", String.valueOf(number));
			//写入文件；
			OutputStream os = new FileOutputStream("guess.txt");
			prop.store(os, null);
			os.close();
			GuessNumber.start();
		}
	}
}
