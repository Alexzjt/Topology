import java.io.*;
import java.util.*;
public class SubOptimalPath {
	public static void main(String[] args){
		try {
			BufferedReader file_roadlink = new BufferedReader(new FileReader(Config.ROADLINK_MIDDLE_FILE_SUB_OPT));//¶ÁÂ·Á´ÍØÆËÎÄ¼þ
			BufferedReader file_station_SP = new BufferedReader(new FileReader(Config.SHORTEST_PATH_LENGTH_IN));
			HashMap<String, RoadLink> id_RoadLink_hash = new HashMap<String, RoadLink>();
			HashMap<String, List<String>> stationID_RoadLinkID_hash = new HashMap<String, List<String>>();
			HashMap<String, Double> SE_length=new HashMap<String, Double>();
			List<String> stationID=new ArrayList<String>(220);
			String line;
			while ((line = file_roadlink.readLine()) != null) {
				// System.out.println(line);
				String[] array_line = line.split(",");
				RoadLink loop_roadlink = new RoadLink(array_line,true);
				id_RoadLink_hash.put(loop_roadlink.ID, loop_roadlink);
				if (loop_roadlink.station != null) {
					if (stationID_RoadLinkID_hash.containsKey(loop_roadlink.station)) {
						List<String> temp_str_list = stationID_RoadLinkID_hash.get(loop_roadlink.station);
						temp_str_list.add(loop_roadlink.ID);
						stationID_RoadLinkID_hash.put(loop_roadlink.station, temp_str_list);
					} else {
						List<String> temp_str_list = new ArrayList<String>(6);
						temp_str_list.add(loop_roadlink.ID);
						stationID_RoadLinkID_hash.put(loop_roadlink.station, temp_str_list);
					}
					if(!stationID.contains(loop_roadlink.station)){
						stationID.add(loop_roadlink.station);
					}
				}
			}
			while ((line = file_station_SP.readLine()) != null) {
				String[] line_array=line.split(",");
				SE_length.put(line_array[0]+"_"+line_array[1],Double.valueOf(line_array[2]));
			}
			newDir(stationID,Config.SUB_OPTIMAL_PATH_DIR);
			for (Iterator<String> iterator = stationID_RoadLinkID_hash.keySet().iterator(); iterator.hasNext();) {
				String loop_station = iterator.next();
				System.out.println(loop_station);
				for (Iterator<String> iterator1 = stationID_RoadLinkID_hash.keySet().iterator(); iterator1
						.hasNext();) {
					String loop_station1 = iterator1.next();
					if (loop_station.equals(loop_station1)) {
						continue;
					}
					double upbound = SE_length.get(loop_station+"_"+loop_station1)+Config.TOLERANCE_SUB_OPT;
					//List<String> minCostPath=new ArrayList<String>(200);
					for (String loop_roadlink_id : stationID_RoadLinkID_hash.get(loop_station)) {
						for (String loop_roadlink_id1 : stationID_RoadLinkID_hash.get(loop_station1)) {
							ArrayDeque<StatusForShortestPath> queue = new ArrayDeque<StatusForShortestPath>();
							List<String> init_path=new ArrayList<String>(200);
							init_path.add(loop_roadlink_id);
							queue.add(new StatusForShortestPath(id_RoadLink_hash.get(loop_roadlink_id),0,init_path));
							while (!queue.isEmpty()) {
								StatusForShortestPath loop_status = queue.poll();
								if (loop_status.cost<upbound) {
									if (loop_status.roadLink.ID.equals(loop_roadlink_id1)) {
										BufferedWriter loop_file=new BufferedWriter(new FileWriter(Config.SUB_OPTIMAL_PATH_DIR+
												loop_station+"\\"+loop_station1+"\\"+String.valueOf((int)loop_status.cost)+"_"
												+String.valueOf(Config.water++)+".csv"));
										for(String loop_path_roadlink_id : loop_status.path){
											loop_file.write(id_RoadLink_hash.get(loop_path_roadlink_id).toString()+"\r\n");
										}
										loop_file.close();
									}
									if (loop_status.roadLink.next_ID == null)
										continue;
									for (String next_RoadLink_id : loop_status.roadLink.next_ID) {
										RoadLink temp_roadlink = id_RoadLink_hash.get(next_RoadLink_id);
										// System.out.println(loop_status.roadLink.ID);
										double temp_cost = loop_status.cost + temp_roadlink.length;
										List<String> temp_list=new ArrayList<String>(loop_status.path);
										temp_list.add(temp_roadlink.ID);
										queue.add(new StatusForShortestPath(temp_roadlink, temp_cost,temp_list));
									}
									id_RoadLink_hash.put(loop_status.roadLink.ID,loop_status.roadLink);
								}
							}
						}
					}
				}
			}
			file_roadlink.close();
			file_station_SP.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void newDir(List<String> array,String path){
		int length=array.size();
		for(int i=0;i<length;i++){
			for(int i1=0;i1<length;i1++){
				File loop_file=new File(path+array.get(i)+"\\"+array.get(i1));
				if(!loop_file.exists())
					loop_file.mkdirs();
			}
		}
	}
}
