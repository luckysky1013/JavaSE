package com.itheima.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itheima.service.ICustomerService;

//用来测试方法的类
public class Client {
	public static void main(String[] args) {
		//获取Spring容器
		ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
		//获取ICustomerService对象
		ICustomerService service = (ICustomerService) ac.getBean("customerService");
		//执行更新
		service.transfor("孙行者", "行者孙", 100);
	}
}
