package generateTopology;
import java.io.*;
import java.util.*;

import dao.LonLat;
import dao.RoadLink;
import dao.StationInMap;
import util.Config;

public class Main {
	public static void main(String[] args) {
		File dir = new File(Config.NAVINFO_PRE);
		String[] file_name_array = dir.list();
		for (String province : file_name_array) {
			System.out.println(province);
			HashMap<String, RoadLink> id_Roadlink = Main.getTopology_ID_RoadLink_RoadName_Station(
					Config.NAVINFO_PRE + province + "\\road\\R" + province + ".mid",
					Config.NAVINFO_PRE + province + "\\road\\R" + province + ".mif",
					Config.NAVINFO_PRE + province + "\\other\\PName" + province + ".mid",
					Config.NAVINFO_PRE + province + "\\index\\POI" + province + ".mid",
					Config.NAVINFO_PRE + province + "\\road\\R_LName" + province + ".mid",
					Config.NAVINFO_PRE + province + "\\road\\R_Name" + province + ".mid");
			try {
				BufferedWriter writer=new BufferedWriter(new FileWriter(Config.ROADLINK_TOPOLOGY_29+province+".csv"));
				for(Iterator<RoadLink> iterator=id_Roadlink.values().iterator();iterator.hasNext();){
					RoadLink loop_roadlink=iterator.next();
					writer.write(loop_roadlink.toString()+"\r\n");
				}
				writer.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		// // ��ʱ��д������ɣ�Ŀǰû��ϸ����κ���������
		// try {
		// BufferedReader file_mid = new BufferedReader(new
		// FileReader(Config.MAP_MID));
		// BufferedReader file_mif = new BufferedReader(new
		// FileReader(Config.MAP_MIF));
		// BufferedReader file_station = new BufferedReader(new
		// FileReader(Config.STATION));
		// BufferedReader file_tachometer = new BufferedReader(new
		// FileReader(Config.TACHYMETER));
		// BufferedReader file_old_topology = new BufferedReader(new
		// FileReader(Config.OLD_TOPOLOGY));
		// int line = 0, mif_kuai = 0;
		// List<String> lonLat_array = new ArrayList<String>();
		// List<Integer> highway_lines = new
		// ArrayList<Integer>(Config.HIGHWAY_COUNT);
		// HashMap<String, RoadLink> id_RoadLink = new HashMap<String,
		// RoadLink>();
		// HashMap<String, List<String>> sNodeID_IDArray = new HashMap<String,
		// List<String>>();
		// HashMap<String, List<String>> eNodeID_IDArray = new HashMap<String,
		// List<String>>();
		// HashMap<Integer, RoadLink> line_RoadLink = new HashMap<Integer,
		// RoadLink>();
		// // HashMap<String,List<String>> s2E_hash=new
		// // HashMap<String,List<String>>();
		// // HashMap<String,List<String>> e2S_hash=new
		// // HashMap<String,List<String>>();
		// HashMap<String, Station> id_Station = new HashMap<String, Station>();
		// HashMap<String, Tachometer> id_Tachometer = new HashMap<String,
		// Tachometer>();
		// HashMap<String, String> id_Tachometer_stake = new HashMap<String,
		// String>();
		// String s;
		// while ((s = file_station.readLine()) != null) {// ���շ�վ������Ϣ�ļ�
		// String[] s_array = s.split(",");
		// Station temp_station = new Station(s_array[2], s_array[23],
		// s_array[0], s_array[1], s_array[5]);
		// id_Station.put(temp_station.id, temp_station);
		// }
		// while ((s = file_tachometer.readLine()) != null) {// �������ǻ�����Ϣ�ļ�
		// String[] s_array = s.split(" ");
		// if (s_array[8].equals("null") || s_array[9].equals("null"))
		// continue;
		// Tachometer temp_Tachometer = new Tachometer(s_array[11], s_array[0],
		// s_array[8], s_array[9]);
		// id_Tachometer.put(temp_Tachometer.id, temp_Tachometer);
		// }
		// while ((s = file_old_topology.readLine()) != null) { // ���������ļ�
		// // ��ȡ����׮�š�
		// String[] s_array = s.split(",");
		// if (!s_array[8].equals("null")) {
		// id_Tachometer_stake.put(s_array[8], s_array[9]);
		// }
		// }
		// while ((s = file_mid.readLine()) != null) {// ��MID�ļ�
		// line++;
		// String[] s_array = s.split("\",\"|\"");
		// // �ж���һ���Ǹ��ٹ�·����ǰ�����ֶ���00
		// if (s_array[4].charAt(0) == '0' && s_array[4].charAt(1) == '0') {
		// highway_lines.add(line);
		// RoadLink temp_RoadLink = new RoadLink(s_array);
		// temp_RoadLink.line = line;
		// id_RoadLink.put(s_array[2], temp_RoadLink);
		// // �˴���Ҫ�жϵ�·�ķ���2�Ǵ�SNode��ENode��3�Ǵ�ENode��SNode��һ������¾�������ѡ��
		// /*
		// * String nodeID,end_nodeID; if(s_array[6].equals("2")){//˳��
		// * nodeID=s_array[10]; end_nodeID=s_array[11]; } else{//����
		// * nodeID=s_array[11]; end_nodeID=s_array[10]; }
		// */
		// if (sNodeID_IDArray.containsKey(temp_RoadLink.SnodeID)) {
		// List<String> temp_array = sNodeID_IDArray.get(temp_RoadLink.SnodeID);
		// temp_array.add(s_array[2]);
		// sNodeID_IDArray.put(temp_RoadLink.SnodeID, temp_array);
		// } else {
		// // �˴���Ϊĳ��Node��Ӧ��·�����2������һ��·����2��·��
		// List<String> temp_array = new ArrayList<String>(2);
		// temp_array.add(s_array[2]);
		// sNodeID_IDArray.put(temp_RoadLink.SnodeID, temp_array);
		// }
		// if (eNodeID_IDArray.containsKey(temp_RoadLink.EnodeID)) {
		// List<String> temp_array = eNodeID_IDArray.get(temp_RoadLink.EnodeID);
		// temp_array.add(s_array[2]);
		// eNodeID_IDArray.put(temp_RoadLink.EnodeID, temp_array);
		// } else {
		// // �˴���Ϊĳ��Node��Ӧ��·�����2������һ��·����2��·��
		// List<String> temp_array = new ArrayList<String>(2);
		// temp_array.add(s_array[2]);
		// eNodeID_IDArray.put(temp_RoadLink.EnodeID, temp_array);
		// }
		// line_RoadLink.put(line, temp_RoadLink);
		// /*
		// * if(s2E_hash.containsKey(temp_RoadLink.SnodeID)){
		// * List<String>
		// * temp_array=s2E_hash.get(temp_RoadLink.SnodeID);
		// * temp_array.add(temp_RoadLink.EnodeID);
		// * s2E_hash.put(temp_RoadLink.SnodeID,temp_array); } else {
		// * List<String> temp_array=new ArrayList<String>(2);
		// * temp_array.add(temp_RoadLink.EnodeID);
		// * s2E_hash.put(temp_RoadLink.SnodeID,temp_array); }
		// * if(e2S_hash.containsKey(temp_RoadLink.EnodeID)){
		// * List<String>
		// * temp_array=e2S_hash.get(temp_RoadLink.EnodeID);
		// * temp_array.add(temp_RoadLink.SnodeID);
		// * e2S_hash.put(temp_RoadLink.EnodeID,temp_array); } else {
		// * List<String> temp_array=new ArrayList<String>(2);
		// * temp_array.add(temp_RoadLink.SnodeID);
		// * e2S_hash.put(temp_RoadLink.EnodeID,temp_array); }
		// */
		// }
		// }
		// while ((s = file_mif.readLine()) != null) {// mif�ļ�
		// if (s.contains("Line") || s.contains("Pline")) {
		// mif_kuai++;
		// lonLat_array.clear();
		// lonLat_array.add(s);
		// } else if (s.contains("Pen")) {
		// if (highway_lines.contains(mif_kuai)) {// �����˸��ٹ�·�ľ�γ������
		// List<LonLat> lonLat_list = new ArrayList<LonLat>();
		// int count = 0;
		// String lon = null, lat = null;
		// for (String x : lonLat_array) {
		// String[] array_x = x.split(" ");
		// for (String str : array_x) {
		// if (str.contains(".")) {
		// count++;
		// if (count % 2 != 0) {
		// lon = str;
		// } else {
		// lat = str;
		// lonLat_list.add(new LonLat(lon, lat));
		// }
		// }
		// }
		// }
		// // �˴���ɶԾ�γ�����еĶ�ȡ���������������·�������С�
		// RoadLink temp_RoadLink = line_RoadLink.get(mif_kuai);
		// /*
		// * Station station_on_RoadLink=null; Tachometer
		// * tachometer_on_RoadLink=null; for(LonLat loop_LonLat :
		// * lonLat_list){ //������û���շ�վ���������γ��������
		// * for(Iterator<Station>
		// * iterator=id_Station.values().iterator();iterator.
		// * hasNext();){ Station loop_station=iterator.next();
		// * if(LonLat.GetDistance(loop_LonLat,loop_station.lonLat
		// * )<=Config.TOLERANCE){
		// * station_on_RoadLink=loop_station; break; } }
		// * //������û�в��������������γ�������� for(Iterator<Tachometer>
		// * iterator=id_Tachometer.values().iterator();iterator.
		// * hasNext();){ Tachometer
		// * loop_Tachometer=iterator.next();
		// * if(LonLat.GetDistance(loop_LonLat,loop_Tachometer.
		// * lonLat)<=Config.TOLERANCE&&!temp_RoadLink.isRamp){
		// * tachometer_on_RoadLink=loop_Tachometer; break; } } }
		// * if(station_on_RoadLink!=null){
		// * temp_RoadLink.station=station_on_RoadLink.id;
		// * temp_RoadLink.highway_ID=station_on_RoadLink.
		// * highway_ID;
		// * temp_RoadLink.station_stake=station_on_RoadLink.
		// * stake; } if(tachometer_on_RoadLink!=null){
		// * temp_RoadLink.tachometer=tachometer_on_RoadLink.id;
		// * temp_RoadLink.highway_ID=tachometer_on_RoadLink.
		// * highway_ID;
		// * //System.out.println(id_Tachometer_stake.get(
		// * tachometer_on_RoadLink.id)); String
		// * tachometer_stake=id_Tachometer_stake.get(
		// * tachometer_on_RoadLink.id);
		// * temp_RoadLink.tachometer_stake=tachometer_stake==null
		// * ?tachometer_on_RoadLink.stake:Double.valueOf(
		// * tachometer_stake); }
		// */
		// temp_RoadLink.lonlat_list = lonLat_list;
		// line_RoadLink.put(mif_kuai, temp_RoadLink);
		// id_RoadLink.put(temp_RoadLink.ID, temp_RoadLink);
		// }
		// } else {
		// lonLat_array.add(s);
		// }
		// }
		// //
		// /*
		// * System.out.println("�������׸�·����ID"); String
		// * input_RoadLink_ID=in.next();
		// * System.out.println("��������ٹ�·��ţ���G42"); String
		// * highway_number=in.next(); char highway_type='G'; RoadLink
		// * loop_RoadLink=id_RoadLink.get(input_RoadLink_ID); String
		// * loop_Node_ID=loop_RoadLink.direction==2?loop_RoadLink.SnodeID:
		// * loop_RoadLink.EnodeID; double loop_length=0; List<LonLat>
		// * loop_lonLat_list=new ArrayList<LonLat>();
		// * HashMap<String,RoadSegment> id_RoadSegment=new
		// * HashMap<String,RoadSegment>(); HashMap<String,List<String>>
		// * id_nextID_List=new HashMap<String,List<String>>();
		// *
		// * while(loop_Node_ID!=null){ List<String>
		// * temp_ID_list=seNodeID_IDArray.get(loop_Node_ID);
		// * if(temp_ID_list.size()==1){
		// * loop_RoadLink=id_RoadLink.get(temp_ID_list.get(0));
		// * if(highway_type==loop_RoadLink.road_attribute){
		// * loop_length+=loop_RoadLink.length;
		// * loop_lonLat_list.addAll(loop_RoadLink.lonlat_list); } else{
		// *
		// * } } else{
		// *
		// * } }
		// */
		//
		// // ��������Ϊÿ��·������next���ԡ����ù����ַ�BFS��
		// BufferedWriter file_roadLink = new BufferedWriter(new
		// FileWriter(Config.ROADLINK_OUTPUT));
		// file_roadLink.write(
		// "·��ID,��һ·��ID,��һ·��ID,����,�Ƿ��ѵ�,·������,���ٱ��,����,γ��,������,�ٶ�����,�ٶ�����,MID���к�,������,������׮��,�շ�վ,�շ�վ׮��,���׮��,�յ�׮��,����\r\n");
		// ArrayDeque<RoadLink> roadLink_queue = new ArrayDeque<RoadLink>();
		// roadLink_queue.add(id_RoadLink.get(Config.START_ROADLINK_ID));
		// while (roadLink_queue.size() != 0) {
		// RoadLink loop_RoadLink = roadLink_queue.poll();
		// if (!loop_RoadLink.visit) {
		// // System.out.println(loop_RoadLink.ID+","+loop_RoadLink.EnodeID);
		// loop_RoadLink.visit = true;
		// loop_RoadLink.next_ID = sNodeID_IDArray.get(loop_RoadLink.EnodeID);
		// loop_RoadLink.pre_ID = eNodeID_IDArray.get(loop_RoadLink.SnodeID);
		// if (loop_RoadLink.next_ID == null) {// ������ʦ˵��˵���������ݴ��ж�
		// String change_EnodeID = "";
		// if (loop_RoadLink.EnodeID.charAt(0) == '2') {
		// change_EnodeID = "1" + loop_RoadLink.EnodeID.substring(1,
		// loop_RoadLink.EnodeID.length());
		// } else if (loop_RoadLink.EnodeID.charAt(0) == '1') {
		// change_EnodeID = "2" + loop_RoadLink.EnodeID.substring(1,
		// loop_RoadLink.EnodeID.length());
		// }
		// loop_RoadLink.next_ID = sNodeID_IDArray.get(change_EnodeID);
		// }
		// if (loop_RoadLink.pre_ID == null) {// ������ʦ˵��˵���������ݴ��ж�
		// String change_SnodeID = "";
		// if (loop_RoadLink.SnodeID.charAt(0) == '2') {
		// change_SnodeID = "1" + loop_RoadLink.SnodeID.substring(1,
		// loop_RoadLink.SnodeID.length());
		// } else if (loop_RoadLink.SnodeID.charAt(0) == '1') {
		// change_SnodeID = "2" + loop_RoadLink.SnodeID.substring(1,
		// loop_RoadLink.SnodeID.length());
		// }
		// loop_RoadLink.pre_ID = eNodeID_IDArray.get(change_SnodeID);
		// }
		// file_roadLink.write(loop_RoadLink.toString() + "\r\n");
		// id_RoadLink.put(loop_RoadLink.ID, loop_RoadLink);
		// if (loop_RoadLink.next_ID != null) {
		// for (String str_id : loop_RoadLink.next_ID) {
		// RoadLink temp_RoadLink_judge = id_RoadLink.get(str_id);
		// if (temp_RoadLink_judge != null)
		// roadLink_queue.add(temp_RoadLink_judge);
		// }
		// }
		// if (loop_RoadLink.pre_ID != null) {
		// for (String str_id : loop_RoadLink.pre_ID) {
		// RoadLink temp_RoadLink_judge = id_RoadLink.get(str_id);
		// if (temp_RoadLink_judge != null)
		// roadLink_queue.add(temp_RoadLink_judge);
		// }
		// }
		// }
		// }
		// file_roadLink.close();
		// file_mid.close();
		// file_mif.close();
		// file_station.close();
		// file_tachometer.close();
		// file_old_topology.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		//
		// } finally {
		//
		// }
	}

	public static HashMap<String, RoadLink> getTopology_ID_RoadLink(String mid_file_path, String mif_file_path) {
		try {
			BufferedReader file_mid = new BufferedReader(new FileReader(mid_file_path));
			BufferedReader file_mif = new BufferedReader(new FileReader(mif_file_path));
			int line = 0, mif_kuai = 0;
			List<String> lonLat_array = new ArrayList<String>();
			List<Integer> highway_lines = new ArrayList<Integer>(Config.HIGHWAY_COUNT);
			List<String> highway_roadlink_id = new ArrayList<String>(Config.HIGHWAY_COUNT);
			HashMap<String, RoadLink> id_RoadLink = new HashMap<String, RoadLink>();
			HashMap<String, List<String>> sNodeID_IDArray = new HashMap<String, List<String>>();
			HashMap<String, List<String>> eNodeID_IDArray = new HashMap<String, List<String>>();
			HashMap<Integer, RoadLink> line_RoadLink = new HashMap<Integer, RoadLink>();
			String s;
			while ((s = file_mid.readLine()) != null) {// ��MID�ļ�
				line++;
				String[] s_array = s.split("\",\"|\""); // ������ָ�����
				// �ж���һ���Ǹ��ٹ�·����ǰ�����ֶ���00
				if (s_array[4].charAt(0) == '0' && s_array[4].charAt(1) == '0') {
					highway_roadlink_id.add(s_array[2]);
					highway_lines.add(line);
					RoadLink temp_RoadLink = new RoadLink(s_array);
					temp_RoadLink.line = line;
					id_RoadLink.put(s_array[2], temp_RoadLink);
					if (sNodeID_IDArray.containsKey(temp_RoadLink.SnodeID)) {
						List<String> temp_array = sNodeID_IDArray.get(temp_RoadLink.SnodeID);
						temp_array.add(s_array[2]);
						sNodeID_IDArray.put(temp_RoadLink.SnodeID, temp_array);
					} else {
						// �˴���Ϊĳ��Node��Ӧ��·�����2������һ��·����2��·��
						List<String> temp_array = new ArrayList<String>(2);
						temp_array.add(s_array[2]);
						sNodeID_IDArray.put(temp_RoadLink.SnodeID, temp_array);
					}
					if (eNodeID_IDArray.containsKey(temp_RoadLink.EnodeID)) {
						List<String> temp_array = eNodeID_IDArray.get(temp_RoadLink.EnodeID);
						temp_array.add(s_array[2]);
						eNodeID_IDArray.put(temp_RoadLink.EnodeID, temp_array);
					} else {
						// �˴���Ϊĳ��Node��Ӧ��·�����2������һ��·����2��·��
						List<String> temp_array = new ArrayList<String>(2);
						temp_array.add(s_array[2]);
						eNodeID_IDArray.put(temp_RoadLink.EnodeID, temp_array);
					}
					line_RoadLink.put(line, temp_RoadLink);

				}
			}
			while ((s = file_mif.readLine()) != null) {// mif�ļ�
				if (s.contains("Line") || s.contains("Pline")) {
					mif_kuai++;
					lonLat_array.clear();
					lonLat_array.add(s);
				} else if (s.contains("Pen")) {
					if (highway_lines.contains(mif_kuai)) {// �����˸��ٹ�·�ľ�γ������
						List<LonLat> lonLat_list = new ArrayList<LonLat>();
						int count = 0;
						String lon = null, lat = null;
						for (String x : lonLat_array) {
							String[] array_x = x.split(" ");
							for (String str : array_x) {
								if (str.contains(".")) {
									count++;
									if (count % 2 != 0) {
										lon = str;
									} else {
										lat = str;
										lonLat_list.add(new LonLat(lon, lat));
									}
								}
							}
						}
						// �˴���ɶԾ�γ�����еĶ�ȡ���������������·�������С�
						RoadLink temp_RoadLink = line_RoadLink.get(mif_kuai);

						temp_RoadLink.lonlat_list = lonLat_list;
						line_RoadLink.put(mif_kuai, temp_RoadLink);
						id_RoadLink.put(temp_RoadLink.ID, temp_RoadLink);
					}
				} else {
					lonLat_array.add(s);
				}
			}
			// ��������Ϊÿ��·������next���ԡ����ù����ַ�BFS��
			ArrayDeque<RoadLink> roadLink_queue = new ArrayDeque<RoadLink>();
			while (!highway_roadlink_id.isEmpty()) {
				roadLink_queue.add(id_RoadLink.get(highway_roadlink_id.get(0)));
				while (roadLink_queue.size() != 0) {
					RoadLink loop_RoadLink = roadLink_queue.poll();
					highway_roadlink_id.remove(loop_RoadLink.ID);
					if (!loop_RoadLink.visit) {
						// System.out.println(loop_RoadLink.ID+","+loop_RoadLink.EnodeID);
						loop_RoadLink.visit = true;
						loop_RoadLink.next_ID = sNodeID_IDArray.get(loop_RoadLink.EnodeID);
						loop_RoadLink.pre_ID = eNodeID_IDArray.get(loop_RoadLink.SnodeID);
						if (loop_RoadLink.next_ID == null) {// ������ʦ˵��˵���������ݴ��ж�
							String change_EnodeID = "";
							if (loop_RoadLink.EnodeID.charAt(0) == '2') {
								change_EnodeID = "1"
										+ loop_RoadLink.EnodeID.substring(1, loop_RoadLink.EnodeID.length());
							} else if (loop_RoadLink.EnodeID.charAt(0) == '1') {
								change_EnodeID = "2"
										+ loop_RoadLink.EnodeID.substring(1, loop_RoadLink.EnodeID.length());
							}
							loop_RoadLink.next_ID = sNodeID_IDArray.get(change_EnodeID);
						}
						if (loop_RoadLink.pre_ID == null) {// ������ʦ˵��˵���������ݴ��ж�
							String change_SnodeID = "";
							if (loop_RoadLink.SnodeID.charAt(0) == '2') {
								change_SnodeID = "1"
										+ loop_RoadLink.SnodeID.substring(1, loop_RoadLink.SnodeID.length());
							} else if (loop_RoadLink.SnodeID.charAt(0) == '1') {
								change_SnodeID = "2"
										+ loop_RoadLink.SnodeID.substring(1, loop_RoadLink.SnodeID.length());
							}
							loop_RoadLink.pre_ID = eNodeID_IDArray.get(change_SnodeID);
						}
						id_RoadLink.put(loop_RoadLink.ID, loop_RoadLink);
						if (loop_RoadLink.next_ID != null) {
							for (String str_id : loop_RoadLink.next_ID) {
								RoadLink temp_RoadLink_judge = id_RoadLink.get(str_id);
								if (temp_RoadLink_judge != null)
									roadLink_queue.add(temp_RoadLink_judge);
							}
						}
						if (loop_RoadLink.pre_ID != null) {
							for (String str_id : loop_RoadLink.pre_ID) {
								RoadLink temp_RoadLink_judge = id_RoadLink.get(str_id);
								if (temp_RoadLink_judge != null)
									roadLink_queue.add(temp_RoadLink_judge);
							}
						}
					}
				}
			}
			file_mid.close();
			file_mif.close();
			return id_RoadLink;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static HashMap<String, RoadLink> getTopology_ID_RoadLink_RoadName_Station(String mid_file_path,
			String mif_file_path, String pname_file_path, String poi_file_path, String rlname_file_path,
			String rname_file_path) {
		HashMap<String, StationInMap> poi_StationInMap_hash = new HashMap<String, StationInMap>(); // poi��ID�����ֶ�Ӧ
		HashMap<String, List<String>> id_route_id = new HashMap<String, List<String>>();
		HashMap<String, List<String>> route_id_name = new HashMap<String, List<String>>();
		HashMap<String, RoadLink> id_Roadlink = Main.getTopology_ID_RoadLink(mid_file_path, mif_file_path);
		// List<String> poi_station_array = new ArrayList<String>(300); //
		// poi_name_hash��key�ļ��ϣ�Ϊ�˷��㲻��iterator
		// List<String> roadlink_id_array = new ArrayList<String>(300);
		// List<String> route_id_array = new ArrayList<String>(300);
		try {
			BufferedReader pname = new BufferedReader(new FileReader(pname_file_path));
			String line = null;
			while ((line = pname.readLine()) != null) {
				String[] line_array = line.split("\",\"|\"");
				if (line_array[3].endsWith("�շ�վ")) { // ���շ�վ��β
					poi_StationInMap_hash.put(line_array[1], new StationInMap(line_array[1], line_array[3])); // ���ҵ������շ�վ��POI��һһ��Ӧ��
					// poi_station_array.add(line_array[1]);
				}
			}
			pname.close();
			BufferedReader poi = new BufferedReader(new FileReader(poi_file_path));
			while ((line = poi.readLine()) != null) {
				String[] line_array = line.split("\",\"|\"");
				if (poi_StationInMap_hash.containsKey(line_array[8])) {
					StationInMap loop_station = poi_StationInMap_hash.get(line_array[8]);
					loop_station.lonLat = new LonLat(line_array[6], line_array[7]);
					loop_station.roadlink_ID = line_array[13];
					poi_StationInMap_hash.put(loop_station.poi_ID, loop_station);
					RoadLink station_RoadLink = id_Roadlink.get(loop_station.roadlink_ID);
					if(station_RoadLink!=null){
						station_RoadLink.station = loop_station.name;
						id_Roadlink.put(station_RoadLink.ID, station_RoadLink);
					}
				}
			}
			poi.close();
			BufferedReader rlname = new BufferedReader(new FileReader(rlname_file_path));
			while ((line = rlname.readLine()) != null) {
				String[] line_array = line.split("\",\"|\"");
				// if (roadlink_id_array.contains(line_array[2])) {
				// route_id_array.add(line_array[3]);
				// }
				if (id_route_id.containsKey(line_array[2])) {
					List<String> loop_route_id = id_route_id.get(line_array[2]);
					loop_route_id.add(line_array[3]);
					id_route_id.put(line_array[2], loop_route_id);
				} else {
					List<String> loop_route_id = new ArrayList<String>(5);
					loop_route_id.add(line_array[3]);
					id_route_id.put(line_array[2], loop_route_id);
				}
			}
			rlname.close();
			BufferedReader rname = new BufferedReader(new FileReader(rname_file_path));
			while ((line = rname.readLine()) != null) {
				String[] line_array = line.split("\",\"|\"");
				if (line_array[14].equals("1")) { // ����˴�������·��
					if (route_id_name.containsKey(line_array[1])) {
						List<String> loop_name = route_id_name.get(line_array[1]);
						loop_name.add(fullWidth2halfWidth(line_array[3]));
						route_id_name.put(line_array[1], loop_name);
					} else {
						List<String> loop_name = new ArrayList<String>(5);
						loop_name.add(fullWidth2halfWidth(line_array[3]));
						route_id_name.put(line_array[1], loop_name);
					}
				}
			}
			rname.close();
			for (Iterator<RoadLink> iterator = id_Roadlink.values().iterator(); iterator.hasNext();) {
				RoadLink loop_roadlink = iterator.next();
				if (id_route_id.containsKey(loop_roadlink.ID)) {
					List<String> roadlink_name = new ArrayList<String>(10);
					for (String route_ID : id_route_id.get(loop_roadlink.ID)) {
						roadlink_name.addAll(route_id_name.get(route_ID));
					}
					loop_roadlink.highway_ID = StationInMap.listAddSeparator(roadlink_name);
				}
				id_Roadlink.put(loop_roadlink.ID, loop_roadlink);
			}
			return id_Roadlink;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ȫ�ǰ��ת��
	 * @param fullWidthStr
	 * @return
	 */
	public static String fullWidth2halfWidth(String fullWidthStr) {
		if (null == fullWidthStr || fullWidthStr.length() <= 0) {
			return "";
		}
		char[] charArray = fullWidthStr.toCharArray();
		// ��ȫ���ַ�ת����char�������
		for (int i = 0; i < charArray.length; ++i) {
			int charIntValue = (int) charArray[i];
			// �������ת����ϵ,����Ӧ�±�֮�����ƫ����65248;����ǿո�Ļ�,ֱ����ת��
			if (charIntValue >= 65281 && charIntValue <= 65374) {
				charArray[i] = (char) (charIntValue - 65248);
			} else if (charIntValue == 12288) {
				charArray[i] = (char) 32;
			}
		}
		return new String(charArray);
	}
	
	/**
	 * ������ά��ͼ��mid�ļ����õ����ļ��ڵ�����ͼ���ţ���Set��ʽ���
	 * @param mid_file_path
	 * @return
	 */
	public static Set<String> getMapSheetNumberSet(String mid_file_path){
		try {
			Set<String> MapSheetNumberSet=new HashSet<String>();
			BufferedReader reader=new BufferedReader(new FileReader(mid_file_path));
			String line;
			while((line=reader.readLine())!=null){
				String[] line_array = line.split("\",\"|\"");
				MapSheetNumberSet.add(line_array[1]);
			}
			reader.close();
			return MapSheetNumberSet;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}