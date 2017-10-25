package cn.itcast.filestream;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

/*使用集合存储10个1-50(含50)的随机偶数元素,要求数字不能重复,
 * 按指定格式输出到C盘的num.txt中,例如: 48,44,40,38,34,30,26...... */
public class JiHeDemo {

	public static void main(String[] args) throws IOException {
		Random rand = new Random();
		HashSet<Integer> hs = new HashSet<Integer>();
		while(hs.size()<=10){
				int random = rand.nextInt(50)+1;
				hs.add(random);
			
		}
		String str = "";
		int index = 0 ;
		for (Integer integer : hs) {
			if(index == hs.size()-1){
				str += integer; 
			}else{
				str += integer+","; 
				index++;
			}
		}
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("random.txt"));
		bos.write(str.getBytes());
		bos.close();
	}

}
