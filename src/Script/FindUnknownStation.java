package Script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.corba.se.spi.orb.StringPair;

import util.*;
import dao.*;

/*
1��401�һ����շ�վ��312�����շ�վ����γ�ȡ�׮�ŵ���Ϣ��ȫһ�£�����֤Ϊͬһ�շ�վ��
2��3003�����շ�վ��3019�����շ�վ�ѳ�����
3��2402ǭ�����շ�վ��2403G5515ǭ���շ�վ�ڴ˰汾��άͼ�µ�ͼ�в����ڡ�����·��������·������ͨ��
4��4806ʰ���շ�վ���������ڴ˰汾��άͼ�µ�ͼ�в����ڡ�
5��4705��ʯ�����շ�վ�����Ϻ���ά��ͼ�ж��鲻������û�о�γ����Ϣ��
*/


public class FindUnknownStation {
	public static void main(String[] args) {
		try {
			BufferedReader topology_file = new BufferedReader(new FileReader(Config.ROADLINK_MIDDLE_FILE_SUB_OPT));
			Set<String> station_set=new HashSet<String>(300);
			station_set.add("401");  //�˴�ר������д������Ϊ401��312�����������Ӧ���շ�վ��һ����ȴ���벻ͬ��
			String s;
			while((s=topology_file.readLine())!=null){
				String[] s_array=s.split(",");
				station_set.add(s_array[15]);
			}
			topology_file.close();
			
			BufferedReader toll_file=new BufferedReader(new FileReader(Config.TOLL_DATA));
			BufferedWriter unknown_file=new BufferedWriter(new FileWriter(Config.TOLL_DATA_UNKNOWN));
			while((s=toll_file.readLine())!=null){
				String[] s_array=s.split(",");
				if(!station_set.contains(s_array[0])||!station_set.contains(s_array[9])){
					unknown_file.write(s+"\r\n");
				}
			}
			toll_file.close();
			unknown_file.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
