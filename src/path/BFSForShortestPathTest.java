package path;
import java.io.*;
import java.util.*;

import dao.RoadLink;
import dao.StatusForShortestPath;
import util.Config;

public class BFSForShortestPathTest {
	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			BufferedReader file_roadlink = new BufferedReader(new FileReader(Config.ROADLINK_MIDDLE_FILE_SUB_OPT));// 读路链拓扑文件
			BufferedReader file_station_SP = new BufferedReader(new FileReader(Config.SHORTEST_PATH_LENGTH_IN));
			HashMap<String, RoadLink> id_RoadLink_hash = new HashMap<String, RoadLink>();
			HashMap<String, List<String>> stationID_RoadLinkID_hash = new HashMap<String, List<String>>();
			HashMap<String, Double> SE_length = new HashMap<String, Double>();
			List<String> stationID = new ArrayList<String>(220);
			String line;
			while ((line = file_roadlink.readLine()) != null) {
				// System.out.println(line);
				String[] array_line = line.split(",");
				RoadLink loop_roadlink = new RoadLink(array_line, true);
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
					if (!stationID.contains(loop_roadlink.station)) {
						stationID.add(loop_roadlink.station);
					}
				}
			}
			while ((line = file_station_SP.readLine()) != null) {
				String[] line_array = line.split(",");
				SE_length.put(line_array[0] + "_" + line_array[1], Double.valueOf(line_array[2]));
			}
			file_roadlink.close();
			file_station_SP.close();
			BufferedWriter file_debug=new BufferedWriter(new FileWriter("D:\\zjt\\路网拓扑zjt\\中间文件\\bug.csv"));
			double upbound = 400;
			String loop_roadlink_id = in.next(), loop_roadlink_id1 = in.next();
			ArrayDeque<StatusForShortestPath> queue = new ArrayDeque<StatusForShortestPath>();
			List<String> init_path = new ArrayList<String>(200);
			init_path.add(loop_roadlink_id);
			queue.add(new StatusForShortestPath(id_RoadLink_hash.get(loop_roadlink_id), 0, init_path));
			while (!queue.isEmpty()) {
				StatusForShortestPath loop_status = queue.poll();
				file_debug.write(loop_status.roadLink.toString()+"\r\n");
				//System.out.println(loop_status.roadLink.lonlat_list.get(0).toString());
				if (loop_status.cost < upbound) {
					if (loop_status.roadLink.ID.equals(loop_roadlink_id1)) {
						System.out.println("success");
						file_debug.close();
						break;
					}
					if (loop_status.roadLink.next_ID == null)
						continue;
					for (String next_RoadLink_id : loop_status.roadLink.next_ID) {
						if (!loop_status.path.contains(next_RoadLink_id)) {
							RoadLink temp_roadlink = id_RoadLink_hash.get(next_RoadLink_id);
							// System.out.println(loop_status.roadLink.ID);
							double temp_cost = loop_status.cost + temp_roadlink.length;
							List<String> temp_list = new ArrayList<String>(loop_status.path);
							temp_list.add(temp_roadlink.ID);
							queue.add(new StatusForShortestPath(temp_roadlink, temp_cost, temp_list));
						}
					}
					//id_RoadLink_hash.put(loop_status.roadLink.ID, loop_status.roadLink);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
