package cn.itcast.digui;

import java.io.FileWriter;
import java.io.IOException;

public class CreateDemo {
	public static void main(String[] args) throws IOException {
		FileWriter fw = new FileWriter("c.txt");
		//FileWriter fw2 = new FileWriter("demo\\aa\\cc");
		fw.write("hello");
		fw.flush();
		fw.close();
		//fw2.close();
	}
}
