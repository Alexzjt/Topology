import java.io.*;
import java.util.*;

public class DijkstraStation {
	static HashMap<String, RoadLink> id_RoadLink_hash;
	static HashMap<String, List<String>> stationID_RoadLinkID_hash;
	static List<String> stationID;

	public static void main(String[] args) {
		try {
			BufferedReader file_roadlink = new BufferedReader(new FileReader(Config.ROADLINK_MIDDLE_FILE_SUB_OPT));// ¶ÁÂ·Á´ÍØÆËÎÄ¼þ
			id_RoadLink_hash = new HashMap<String, RoadLink>();
			stationID_RoadLinkID_hash = new HashMap<String, List<String>>();
			stationID = new ArrayList<String>(220);
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
			file_roadlink.close();
			map_toFile("377751",dijkstra("377751"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static HashMap<String, StatusForShortestPath> dijkstra(String roadlink_ID) {
		HashMap<String, StatusForShortestPath> id_Status = new HashMap<String, StatusForShortestPath>(
				Config.NUMBERS_OF_ROADLINK / 2);
		Queue<StatusForShortestPath> priorityQueue = new PriorityQueue<StatusForShortestPath>(10, SubOptimalPath.order);
		List<String> init_path = new ArrayList<String>(200);
		init_path.add(roadlink_ID);
		priorityQueue.add(new StatusForShortestPath(id_RoadLink_hash.get(roadlink_ID),
				id_RoadLink_hash.get(roadlink_ID).length, init_path));
		while (!priorityQueue.isEmpty()) {
			StatusForShortestPath loop_Status = priorityQueue.poll();
			id_Status.put(loop_Status.roadLink.ID, loop_Status);
			if(loop_Status.roadLink.next_ID==null)
				continue;
			for (String str : loop_Status.roadLink.next_ID) {
				RoadLink loop_RoadLink = id_RoadLink_hash.get(str);
				if ((!id_Status.containsKey(str))||(id_Status.containsKey(str) && id_Status.get(str).cost > loop_Status.cost + loop_RoadLink.length)) {
					StatusForShortestPath next_Status = new StatusForShortestPath(loop_Status);
					next_Status.add_RoadLink(loop_RoadLink);
					//id_Status.put(str, next_Status);
					priorityQueue.add(next_Status);
				}
			}
		}
		return id_Status;
	}
	
	public static void map_toFile(String origin_Roadlink_ID,HashMap<String, StatusForShortestPath> id_Status){
		for(Iterator<StatusForShortestPath> iterator=id_Status.values().iterator();iterator.hasNext();){
			StatusForShortestPath loop_status=iterator.next();
			try {
				BufferedWriter writer=new BufferedWriter(new FileWriter(Config.OD_DIJKSTRA_DIR+loop_status.roadLink.ID+"_"+(int)loop_status.cost+"_"+(Config.water++)+".csv"));
				for(String string : loop_status.path){
					writer.write(id_RoadLink_hash.get(string).toString()+"\r\n");
				}
				writer.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
