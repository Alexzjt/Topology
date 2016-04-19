import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args){
		//��ʱ��д������ɣ�Ŀǰû��ϸ����κ���������
		try{
			Scanner in = new Scanner(System.in);
			BufferedReader file_mid = new BufferedReader(new FileReader(Config.MAP_MID));
			BufferedReader file_mif = new BufferedReader(new FileReader(Config.MAP_MIF));
			int line=0,mif_kuai=0;
			List<String> lonLat_array=new ArrayList<String>();
			List<Integer> highway_lines=new ArrayList<Integer>(Config.HIGHWAY_COUNT);
			HashMap<String,RoadLink> id_RoadLink=new HashMap<String,RoadLink>();
			HashMap<String,List<String>> seNodeID_IDArray=new HashMap<String,List<String>>();
			HashMap<Integer,RoadLink> line_RoadLink=new HashMap<Integer,RoadLink>();
			HashMap<String,List<String>> s2E_hash=new HashMap<String,List<String>>();
			HashMap<String,List<String>> e2S_hash=new HashMap<String,List<String>>();
			String s;
			while((s=file_mid.readLine())!=null){
				line++;
				String[] s_array=s.split("\",\"|\"");
				//�ж���һ���Ǹ��ٹ�·����ǰ�����ֶ���00
				if(s_array[4].charAt(0)=='0'&&s_array[4].charAt(1)=='0'){
					highway_lines.add(line);
					RoadLink temp_RoadLink=new RoadLink(s_array);
					id_RoadLink.put(s_array[2],temp_RoadLink);
					//�˴���Ҫ�жϵ�·�ķ���2�Ǵ�SNode��ENode��3�Ǵ�ENode��SNode��һ������¾�������ѡ��
					String nodeID,end_nodeID;
					if(s_array[6].equals("2")){//˳��
						nodeID=s_array[10];
						end_nodeID=s_array[11];
					}
					else{//����
						nodeID=s_array[11];
						end_nodeID=s_array[10];
					}
					if(seNodeID_IDArray.containsKey(nodeID)){
						List<String> temp_array=seNodeID_IDArray.get(nodeID);
						temp_array.add(s_array[2]);
						seNodeID_IDArray.put(nodeID,temp_array);
					}
					else{
						//�˴���Ϊĳ��Node��Ӧ��·�����2������һ��·����2��·��
						List<String> temp_array=new ArrayList<String>(2);
						temp_array.add(s_array[2]);
						seNodeID_IDArray.put(nodeID,temp_array);
					}
					line_RoadLink.put(line,temp_RoadLink);
					if(s2E_hash.containsKey(nodeID)){
						List<String> temp_array=s2E_hash.get(nodeID);
						temp_array.add(end_nodeID);
						s2E_hash.put(nodeID,temp_array);
					}
					else {
						List<String> temp_array=new ArrayList<String>(2);
						temp_array.add(end_nodeID);
						s2E_hash.put(nodeID,temp_array);
					}
					if(e2S_hash.containsKey(end_nodeID)){
						List<String> temp_array=e2S_hash.get(end_nodeID);
						temp_array.add(nodeID);
						e2S_hash.put(end_nodeID,temp_array);
					}
					else {
						List<String> temp_array=new ArrayList<String>(2);
						temp_array.add(nodeID);
						e2S_hash.put(end_nodeID,temp_array);
					}
				}
			}
			while((s=file_mif.readLine())!=null){
				if(s.contains("Line")||s.contains("Pline")){         
					mif_kuai++;
					lonLat_array.clear();
					lonLat_array.add(s);
				}
				else if(s.contains("Pen")){
					if(highway_lines.contains(mif_kuai)){
						List<LonLat> lonLat_list=new ArrayList<LonLat>();
						int count=0;
						String lon=null,lat=null;
						for(String x : lonLat_array){
							String[] array_x=x.split(" ");
							for(String str : array_x){
								if(str.contains(".")){
									count++;
									if(count%2!=0){
										lon=str;
									}
									else{
										lat=str;
										lonLat_list.add(new LonLat(lon,lat));
									}
								}
							}
						}
						RoadLink temp_RoadLink=line_RoadLink.get(mif_kuai);
						temp_RoadLink.lonlat_list=lonLat_list;
						line_RoadLink.put(mif_kuai,temp_RoadLink);
						id_RoadLink.put(temp_RoadLink.ID,temp_RoadLink);
					}
				}
				else{
					lonLat_array.add(s);
				}
			}
			//
			System.out.println("�������׸�·����ID");
			
			
			file_mid.close();
			file_mif.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
		}
	}
	
}
