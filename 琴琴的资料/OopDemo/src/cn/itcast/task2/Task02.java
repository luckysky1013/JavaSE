package cn.itcast.task2;
/*��ʹ���������˼�����������ࣨ���������䣬���ʣ��·ݣ�
Ҫ�������Ա�����ͳ�Ա���������췽�����޲Σ����Σ�
��������Ҫ�����method(����1������2)����ʹ���������󴫲Σ�
method������Ҫ�������λ���˵�ǰ�·ݵĹ��ʺͣ������δ�ӡ
*/
public class Task02 {
	public static void main(String[] args) {
		int sumSalary = method(new Worker("����",22,2000,12),new Worker("����",21,3000,12));
		System.out.println("��λ���˵�ǰ�·ݵĹ��ʺ�"+sumSalary);
	}
	public static int method(Worker w1,Worker w2){
		return w1.getSalary()+w2.getSalary();
	}
}
