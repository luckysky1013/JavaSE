package cn.itcast.practice6;

import java.util.ArrayList;

/*定义一个ArrayList集合,泛型为String类型。统计整个集合中所有字符（注意，不是字符串）的个数。例如：	
		集合中有”abc”、”bcd”两个元素
		程序最终输出结果为:
		a = 1
		b = 2
		c = 2 
		d = 1
*/
public class Task13 {
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		list.add("abcww");
		list.add("bcdasd");
		//遍历集合获取总共有多少种字符；
		ArrayList<String> list2 = new ArrayList<>();
		for(String s : list){
			for (int i = 0; i < s.length(); i++) {
				String ch=s.charAt(i)+"";
				if(!list2.contains(ch)){
					list2.add(ch);
				}
			}
		}
		//获取个数；
		for (int i = 0; i < list2.size(); i++) {
			String checkStr =list2.get(i);
			int before = 0;
			int after = 0;
			for(String ss : list){
				before +=ss.length();
				String newStr = ss.replace(checkStr, "");
				after +=newStr.length();
				
			}
			int count = before-after;
			System.out.println("字符"+checkStr+"出现了"+count+"次");
		}
	}
}
