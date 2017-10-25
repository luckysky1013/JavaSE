package cn.itcast.digui;

import java.io.File;

//使用递归来进行删除文件
public class DeleteDemo {
	public static void main(String[] args) {
		// 封装目录
		File srcFolder = new File("demo\\bb\\c.txt");
		// 递归实现
		deleteFolder(srcFolder);
	}

	private static void deleteFolder(File srcFolder) {
		// 获取该目录下的所有文件或者文件夹的File数组
		File[] fileArray = srcFolder.listFiles();

		if (fileArray != null) {
			// 遍历该File数组，得到每一个File对象
			for (File file : fileArray) {
				// 判断该File对象是否是文件夹
				if (file.isDirectory()) {
					deleteFolder(file);
				} else {
					System.out.println(file.getName() + "---" + file.delete());
					
				}
			}

			
		}
		System.out.println(srcFolder.getName() + "---" + srcFolder.delete());
	}
}
