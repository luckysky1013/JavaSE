package cn.itcast.login;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/*模拟用户登录和注册功能实现。
接收用户输入的用户名和密码，然后和文件中存储的用户名、密码匹配。
如果文件已经存在用户名并且密码也是正确的，提示登录成功，否则提示登录失败。
如果文件中不存在该用户名，则使用该用户名和密码注册一个新的账号，并提示注册成功。
*/
public class LoginDemo {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入姓名：");
		String name = sc.nextLine();
		System.out.println("请输入密码");
		String password = sc.nextLine();
		if(check(name,password)){
			System.out.println("登陆成功！");
		}else{
			System.out.println("用户名或者密码不对!");
		}

	}
	//判断输入的学生是否存在；
	public static boolean check(String name,String password) throws IOException{
		HashMap<String,String> map = getAll();
		//boolean flag = false;
		Set<String> set = map.keySet();
		for (String key : set) {
			if(key.equals(name)&&map.get(key).equals(password)){
				return true;
			}
		}
		return false;
	} 
	//用来获取到所有的学生数据；
	public static HashMap<String,String> getAll() throws IOException{
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream("login.txt"));
		int len = -1;
		byte[] by = new byte[1024];
		String s = "";
		while((len = bis.read(by)) != -1){
			s +=new String(by,0,len);
		}
		String regex = "\r\n";
		String[] arr = s.split(regex);
		//System.out.println(arr[1]);
		
		HashMap<String,String> hm = new HashMap<String,String>();
		for (int i = 0; i < arr.length; i++) {
			String[] str = arr[i].split("-");
			hm.put(str[0], str[1]);
		}
		return hm;
	}

}
