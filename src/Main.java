import java.io.*;
import java.util.*;

import com.sun.jndi.ldap.LdapPoolManager;

public class Main {
	public static void main(String[] args){
		//��ʱ��д������ɣ�Ŀǰû��ϸ����κ���������
		try{
			Scanner in = new Scanner(System.in);
			BufferedReader file_mid = new BufferedReader(new FileReader(Config.MAP_MID));
			BufferedReader file_mif = new BufferedReader(new FileReader(Config.MAP_MIF));
			BufferedReader file_station = new BufferedReader(new FileReader(Config.STATION));
			BufferedReader file_tachometer = new BufferedReader(new FileReader(Config.TACHYMETER));
			int line=0,mif_kuai=0;
			List<String> lonLat_array=new ArrayList<String>();
			List<Integer> highway_lines=new ArrayList<Integer>(Config.HIGHWAY_COUNT);
			HashMap<String,RoadLink> id_RoadLink=new HashMap<String,RoadLink>();
			HashMap<String,List<String>> seNodeID_IDArray=new HashMap<String,List<String>>();
			HashMap<Integer,RoadLink> line_RoadLink=new HashMap<Integer,RoadLink>();
			HashMap<String,List<String>> s2E_hash=new HashMap<String,List<String>>();
			HashMap<String,List<String>> e2S_hash=new HashMap<String,List<String>>();
			HashMap<String,Station> id_Station=new HashMap<String,Station>();
			HashMap<String,Tachometer> id_Tachometer=new HashMap<String,Tachometer>();
			String s;
			while((s=file_station.readLine())!=null){//���շ�վ������Ϣ�ļ�
				String[] s_array=s.split(",");
				Station temp_station=new Station(s_array[2],s_array[23],s_array[0],s_array[1],s_array[5]);
				id_Station.put(temp_station.id,temp_station);
			}
			while((s=file_tachometer.readLine())!=null){//�������ǻ�����Ϣ�ļ�
				String[] s_array=s.split("	");
				if(s_array[8].equals("null")||s_array[9].equals("null"))
					continue;
				Tachometer temp_Tachometer=new Tachometer(s_array[11],s_array[0],s_array[8],s_array[9]);
				id_Tachometer.put(temp_Tachometer.id,temp_Tachometer);
			}
			while((s=file_mid.readLine())!=null){//��MID�ļ�
				line++;
				String[] s_array=s.split("\",\"|\"");
				//�ж���һ���Ǹ��ٹ�·����ǰ�����ֶ���00
				if(s_array[4].charAt(0)=='0'&&s_array[4].charAt(1)=='0'){
					highway_lines.add(line);
					RoadLink temp_RoadLink=new RoadLink(s_array);
					temp_RoadLink.line=line;
					id_RoadLink.put(s_array[2],temp_RoadLink);
					//�˴���Ҫ�жϵ�·�ķ���2�Ǵ�SNode��ENode��3�Ǵ�ENode��SNode��һ������¾�������ѡ��
					/*String nodeID,end_nodeID;
					if(s_array[6].equals("2")){//˳��
						nodeID=s_array[10];
						end_nodeID=s_array[11];
					}
					else{//����
						nodeID=s_array[11];
						end_nodeID=s_array[10];
					}*/
					if(seNodeID_IDArray.containsKey(temp_RoadLink.SnodeID)){
						List<String> temp_array=seNodeID_IDArray.get(temp_RoadLink.SnodeID);
						temp_array.add(s_array[2]);
						seNodeID_IDArray.put(temp_RoadLink.SnodeID,temp_array);
					}
					else{
						//�˴���Ϊĳ��Node��Ӧ��·�����2������һ��·����2��·��
						List<String> temp_array=new ArrayList<String>(2);
						temp_array.add(s_array[2]);
						seNodeID_IDArray.put(temp_RoadLink.SnodeID,temp_array);
					}
					line_RoadLink.put(line,temp_RoadLink);
					if(s2E_hash.containsKey(temp_RoadLink.SnodeID)){
						List<String> temp_array=s2E_hash.get(temp_RoadLink.SnodeID);
						temp_array.add(temp_RoadLink.EnodeID);
						s2E_hash.put(temp_RoadLink.SnodeID,temp_array);
					}
					else {
						List<String> temp_array=new ArrayList<String>(2);
						temp_array.add(temp_RoadLink.EnodeID);
						s2E_hash.put(temp_RoadLink.SnodeID,temp_array);
					}
					if(e2S_hash.containsKey(temp_RoadLink.EnodeID)){
						List<String> temp_array=e2S_hash.get(temp_RoadLink.EnodeID);
						temp_array.add(temp_RoadLink.SnodeID);
						e2S_hash.put(temp_RoadLink.EnodeID,temp_array);
					}
					else {
						List<String> temp_array=new ArrayList<String>(2);
						temp_array.add(temp_RoadLink.SnodeID);
						e2S_hash.put(temp_RoadLink.EnodeID,temp_array);
					}
				}
			}
			while((s=file_mif.readLine())!=null){//mif�ļ�
				if(s.contains("Line")||s.contains("Pline")){         
					mif_kuai++;
					lonLat_array.clear();
					lonLat_array.add(s);
				}
				else if(s.contains("Pen")){
					if(highway_lines.contains(mif_kuai)){//�����˸��ٹ�·�ľ�γ������
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
						//�˴���ɶԾ�γ�����еĶ�ȡ���������������·�������С�
						Station station_on_RoadLink=null;
						Tachometer tachometer_on_RoadLink=null;
						for(LonLat loop_LonLat : lonLat_list){
							//������û���շ�վ���������γ��������
							for(Iterator<Station> iterator=id_Station.values().iterator();iterator.hasNext();){
								Station loop_station=iterator.next();
								if(LonLat.GetDistance(loop_LonLat,loop_station.lonLat)<=Config.EARTH_RADIUS){
									station_on_RoadLink=loop_station;
									break;
								}
							}
							//������û�в��������������γ��������
							for(Iterator<Tachometer> iterator=id_Tachometer.values().iterator();iterator.hasNext();){
								Tachometer loop_Tachometer=iterator.next();
								if(LonLat.GetDistance(loop_LonLat,loop_Tachometer.lonLat)<=Config.EARTH_RADIUS){
									tachometer_on_RoadLink=loop_Tachometer;
									break;
								}
							}
						}
						RoadLink temp_RoadLink=line_RoadLink.get(mif_kuai);
						if(station_on_RoadLink!=null){
							temp_RoadLink.station=station_on_RoadLink.id;
							temp_RoadLink.highway_ID=station_on_RoadLink.highway_ID;
							temp_RoadLink.station_stake=station_on_RoadLink.stake;
						}
						if(tachometer_on_RoadLink!=null){
							temp_RoadLink.tachometer=tachometer_on_RoadLink.id;
							temp_RoadLink.highway_ID=tachometer_on_RoadLink.highway_ID;
							temp_RoadLink.tachometer_stake=tachometer_on_RoadLink.stake;
						}
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
			/*System.out.println("�������׸�·����ID");
			String input_RoadLink_ID=in.next();
			System.out.println("��������ٹ�·��ţ���G42");
			String highway_number=in.next();
			char highway_type='G';
			RoadLink loop_RoadLink=id_RoadLink.get(input_RoadLink_ID);
			String loop_Node_ID=loop_RoadLink.direction==2?loop_RoadLink.SnodeID:loop_RoadLink.EnodeID;
			double loop_length=0;
			List<LonLat> loop_lonLat_list=new ArrayList<LonLat>();
			HashMap<String,RoadSegment> id_RoadSegment=new HashMap<String,RoadSegment>();
			HashMap<String,List<String>> id_nextID_List=new HashMap<String,List<String>>();
			
			while(loop_Node_ID!=null){
				List<String> temp_ID_list=seNodeID_IDArray.get(loop_Node_ID);
				if(temp_ID_list.size()==1){
					loop_RoadLink=id_RoadLink.get(temp_ID_list.get(0));
					if(highway_type==loop_RoadLink.road_attribute){
						loop_length+=loop_RoadLink.length;
						loop_lonLat_list.addAll(loop_RoadLink.lonlat_list);
					}
					else{
						
					}
				}
				else{
					
				}
			}
			*/
			
			//��������Ϊÿ��·������next���ԡ����ù����ַ�BFS��
			BufferedWriter file_roadLink=new BufferedWriter(new FileWriter(Config.ROADLINK_OUTPUT));
			file_roadLink.write("·��ID,��һ·��ID,����,�Ƿ��ѵ�,·������,����,γ��,������,�ٶ�����,�ٶ�����,MID���к�,������,������׮��,�շ�վ,�շ�վ׮��,���׮��,�յ�׮��,����");
			ArrayDeque<RoadLink> roadLink_queue=new ArrayDeque<RoadLink>();
			roadLink_queue.add(id_RoadLink.get(Config.START_ROADLINK_ID));
			while(roadLink_queue.size()!=0){
				RoadLink loop_RoadLink=roadLink_queue.poll();
				if(!loop_RoadLink.visit){
					System.out.println(loop_RoadLink.ID+","+loop_RoadLink.EnodeID);
					loop_RoadLink.visit=true;
					loop_RoadLink.next_ID=seNodeID_IDArray.get(loop_RoadLink.EnodeID);
					if(loop_RoadLink.next_ID==null){//������ʦ˵��˵���������ݴ��ж�
						String change_EnodeID="";
						if(loop_RoadLink.EnodeID.charAt(0)=='2'){
							change_EnodeID="1"+loop_RoadLink.EnodeID.substring(1,loop_RoadLink.EnodeID.length());
						}
						else if(loop_RoadLink.EnodeID.charAt(0)=='1'){
							change_EnodeID="2"+loop_RoadLink.EnodeID.substring(1,loop_RoadLink.EnodeID.length());
						}
						loop_RoadLink.next_ID=seNodeID_IDArray.get(change_EnodeID);
					}
					file_roadLink.write(loop_RoadLink.toString()+"\r\n");
					id_RoadLink.put(loop_RoadLink.ID,loop_RoadLink);
					if(loop_RoadLink.next_ID!=null){
						for(String str_id : loop_RoadLink.next_ID){
							RoadLink temp_RoadLink_judge=id_RoadLink.get(str_id);
							if(temp_RoadLink_judge!=null)
								roadLink_queue.add(temp_RoadLink_judge);
						}
					}
				}
			}
			file_roadLink.close();
			file_mid.close();
			file_mif.close();
			file_station.close();
			file_tachometer.close();
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		finally{
			
		}
	}
	
}
