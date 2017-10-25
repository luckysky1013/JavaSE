package cn.itcast.properties;

import java.util.Properties;
import java.util.Set;

//properties集合的基本使用；
public class BasisUse {
	public static void main(String[] args) {
		//创建集合；
		Properties p = new Properties();
		//使用集合添加数据；
		p.setProperty("001", "jianjian");
		p.setProperty("002", "qinqin");
		//获取所有键的集合
		Set<String> set = p.stringPropertyNames();
		//遍历集合来获取键，通过键值来获取值；
		for(String key : set){
			//获取值；
			String value = p.getProperty(key);
			System.out.println(key+"---"+value);
		}
	}
}
