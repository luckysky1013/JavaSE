package cn.itcast.practice6;

import java.util.ArrayList;

/*����һ��ArrayList����,����ΪString���͡�ͳ�����������������ַ���ע�⣬�����ַ������ĸ��������磺	
		�������С�abc������bcd������Ԫ��
		��������������Ϊ:
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
		//�������ϻ�ȡ�ܹ��ж������ַ���
		ArrayList<String> list2 = new ArrayList<>();
		for(String s : list){
			for (int i = 0; i < s.length(); i++) {
				String ch=s.charAt(i)+"";
				if(!list2.contains(ch)){
					list2.add(ch);
				}
			}
		}
		//��ȡ������
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
			System.out.println("�ַ�"+checkStr+"������"+count+"��");
		}
	}
}
