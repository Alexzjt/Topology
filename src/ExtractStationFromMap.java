import java.io.*;
import java.util.*;

public class ExtractStationFromMap {
	public static void main(String[] args) {
		File dir = new File(Config.NAVINFO_PRE);
		String[] file_name_array = dir.list();
		for (String province : file_name_array) {
			System.out.println(province);
			HashMap<String, StationInMap> poi_StationInMap_hash = new HashMap<String, StationInMap>(); // poi的ID和名字对应
			HashMap<String, List<String>> id_route_id = new HashMap<String, List<String>>();
			HashMap<String, List<String>> route_id_name = new HashMap<String, List<String>>();
			HashMap<String, RoadLink> id_Roadlink = Main.getTopology_ID_RoadLink(
					Config.NAVINFO_PRE + province + "\\road\\R" + province + ".mid",
					Config.NAVINFO_PRE + province + "\\road\\R" + province + ".mif");
			// List<String> poi_station_array = new ArrayList<String>(300); //
			// poi_name_hash的key的集合，为了方便不用iterator
			// List<String> roadlink_id_array = new ArrayList<String>(300);
			// List<String> route_id_array = new ArrayList<String>(300);
			try {
				BufferedReader pname = new BufferedReader(
						new FileReader(Config.NAVINFO_PRE + province + "\\other\\PName" + province + ".mid"));
				String line = null;
				while ((line = pname.readLine()) != null) {
					String[] line_array = line.split("\",\"|\"");
					if (line_array[3].endsWith("收费站")) {
						poi_StationInMap_hash.put(line_array[1], new StationInMap(line_array[1], line_array[3])); // 查找到了是收费站的POI。一一对应。
						// poi_station_array.add(line_array[1]);
					}
				}
				pname.close();
				BufferedReader poi = new BufferedReader(
						new FileReader(Config.NAVINFO_PRE + province + "\\index\\POI" + province + ".mid"));
				while ((line = poi.readLine()) != null) {
					String[] line_array = line.split("\",\"|\"");
					if (poi_StationInMap_hash.containsKey(line_array[8])) {
						StationInMap loop_station = poi_StationInMap_hash.get(line_array[8]);
						loop_station.lonLat = new LonLat(line_array[6], line_array[7]);
						loop_station.roadlink_ID = line_array[13];
						RoadLink station_RoadLink = id_Roadlink.get(loop_station.roadlink_ID);
						if(station_RoadLink!=null&&!station_RoadLink.isRamp){
							loop_station.in_out="主";
						}
						else {
							ArrayDeque<RoadLink> queue = new ArrayDeque<RoadLink>();
							ArrayList<String> history = new ArrayList<String>();
							if (id_Roadlink != null && id_Roadlink.containsKey(loop_station.roadlink_ID)) {
								queue.add(id_Roadlink.get(loop_station.roadlink_ID));
								while (!queue.isEmpty()) {
									RoadLink loop_roadlink = queue.poll();
									history.add(loop_roadlink.ID);
									if (!loop_roadlink.isRamp) {
										loop_station.in_out="入";
										break;
									}
									if (loop_roadlink.next_ID != null) {
										for (String str_id : loop_roadlink.next_ID) {
											RoadLink temp_RoadLink_judge = id_Roadlink.get(str_id);
											if (temp_RoadLink_judge != null && !history.contains(str_id))
												queue.add(temp_RoadLink_judge);
										}
									}
								}
								if(queue.isEmpty()){
									history.clear();
									queue.add(id_Roadlink.get(loop_station.roadlink_ID));
									while (!queue.isEmpty()) {
										RoadLink loop_roadlink = queue.poll();
										history.add(loop_roadlink.ID);
										if (!loop_roadlink.isRamp) {
											loop_station.in_out="出";
											break;
										}
										if (loop_roadlink.pre_ID != null) {
											for (String str_id : loop_roadlink.pre_ID) {
												RoadLink temp_RoadLink_judge = id_Roadlink.get(str_id);
												if (temp_RoadLink_judge != null && !history.contains(str_id))
													queue.add(temp_RoadLink_judge);
											}
										}
									}
								}
							}
						}
						poi_StationInMap_hash.put(loop_station.poi_ID, loop_station);
						// roadlink_id_array.add(line_array[13]);
					}
				}
				poi.close();
				BufferedReader rlname = new BufferedReader(
						new FileReader(Config.NAVINFO_PRE + province + "\\road\\R_LName" + province + ".mid"));
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
				BufferedReader rname = new BufferedReader(
						new FileReader(Config.NAVINFO_PRE + province + "\\road\\R_Name" + province + ".mid"));
				while ((line = rname.readLine()) != null) {
					String[] line_array = line.split("\",\"|\"");
					if (line_array[14].equals("1")) { // 如果此处是中文路名
						if (route_id_name.containsKey(line_array[1])) {
							List<String> loop_name = route_id_name.get(line_array[1]);
							loop_name.add(line_array[3]);
							route_id_name.put(line_array[1], loop_name);
						} else {
							List<String> loop_name = new ArrayList<String>(5);
							loop_name.add(line_array[3]);
							route_id_name.put(line_array[1], loop_name);
						}
					}
				}
				rname.close();
				BufferedWriter output = new BufferedWriter(
						new FileWriter(Config.NAVINFO_STATION_OUTPUT + province + ".csv"));
				for (Iterator<StationInMap> iterator = poi_StationInMap_hash.values().iterator(); iterator.hasNext();) {
					StationInMap loop_station = iterator.next();
					String loop_roadlink_id = loop_station.roadlink_ID;
					ArrayDeque<RoadLink> queue = new ArrayDeque<RoadLink>();
					ArrayList<String> history = new ArrayList<String>();
					if (id_Roadlink != null && id_Roadlink.containsKey(loop_roadlink_id)) {
						queue.add(id_Roadlink.get(loop_roadlink_id));
						while (!queue.isEmpty()) {
							RoadLink loop_roadlink = queue.poll();
							history.add(loop_roadlink.ID);
							if (id_route_id.containsKey(loop_roadlink.ID)) {
								loop_roadlink_id = loop_roadlink.ID;
								break;
							}
							if (loop_roadlink.next_ID != null) {
								for (String str_id : loop_roadlink.next_ID) {
									RoadLink temp_RoadLink_judge = id_Roadlink.get(str_id);
									if (temp_RoadLink_judge != null && !history.contains(str_id))
										queue.add(temp_RoadLink_judge);
								}
							}
							if (loop_roadlink.pre_ID != null) {
								for (String str_id : loop_roadlink.pre_ID) {
									RoadLink temp_RoadLink_judge = id_Roadlink.get(str_id);
									if (temp_RoadLink_judge != null && !history.contains(str_id))
										queue.add(temp_RoadLink_judge);
								}
							}
						}
					}
					if (id_route_id.containsKey(loop_roadlink_id)) {
						List<String> roadlink_name = new ArrayList<String>(10);
						for (String route_ID : id_route_id.get(loop_roadlink_id)) {
							roadlink_name.addAll(route_id_name.get(route_ID));
						}
						output.write(
								loop_station.toString() + "," + StationInMap.listAddSeparator(roadlink_name) + "\r\n");
					} else {
						output.write(loop_station.toString() + ",\r\n");
					}
				}
				output.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
