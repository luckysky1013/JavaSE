package cn.itcast.xuliehua;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//序列化和反序列化的实现；
public class XuLieHua {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		//write();
		read();
	}
	private static void write() throws IOException{
		//创建序列化
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("oos.txt"));
		//创建需要序列化的对象；
		Person p = new Person("林青霞",21);
		//实现序列化；
		oos.writeObject(p);
		//释放资源；
		oos.close();
	}
	private static void read() throws IOException, ClassNotFoundException{
		//创建序列化
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("oos.txt"));
		//创建需要序列化的对象；
		//Person p = new Person("林青霞",21);
		//实现序列化；
		Object obj = ois.readObject();
		//释放资源；
		ois.close();
		System.out.println(obj);
	}
}
