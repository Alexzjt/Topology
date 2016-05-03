import java.io.*;
import java.util.*;

public class BFSForShortestPathTest {
	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			BufferedReader file_roadlink = new BufferedReader(new FileReader(Config.ROADLINK_MIDDLE_FILE));
			//BufferedWriter file_SPResult = new BufferedWriter(new FileWriter(Config.STATION_SHORTEST_PATH_LENGTH));
			HashMap<String, RoadLinkForSP> id_RoadLinkForSP_hash = new HashMap<String, RoadLinkForSP>();
			HashMap<String, List<String>> stationID_RoadLinkForSPID_hash = new HashMap<String, List<String>>();
			String line;
			while ((line = file_roadlink.readLine()) != null) {
				// System.out.println(line);
				String[] array_line = line.split(",");
				RoadLinkForSP loop_roadlink = new RoadLinkForSP(array_line);
				id_RoadLinkForSP_hash.put(loop_roadlink.ID, loop_roadlink);
				if (loop_roadlink.station != null) {
					if (stationID_RoadLinkForSPID_hash.containsKey(loop_roadlink.station)) {
						List<String> temp_str_list = stationID_RoadLinkForSPID_hash.get(loop_roadlink.station);
						temp_str_list.add(loop_roadlink.ID);
						stationID_RoadLinkForSPID_hash.put(loop_roadlink.station, temp_str_list);
					} else {
						List<String> temp_str_list = new ArrayList<String>(6);
						temp_str_list.add(loop_roadlink.ID);
						stationID_RoadLinkForSPID_hash.put(loop_roadlink.station, temp_str_list);
					}
				}
			}

			double minCost = 0;
			String origin = in.next(), destination = in.next();
			ArrayDeque<StatusForShortestPath> queue = new ArrayDeque<StatusForShortestPath>();
			queue.add(new StatusForShortestPath(id_RoadLinkForSP_hash.get(origin)));
			while (!queue.isEmpty()) {
				// System.out.println(queue.size());
				StatusForShortestPath loop_status = queue.poll();
				if (!loop_status.roadLink.visit) {
					System.out.println(loop_status.roadLink.ID);
					loop_status.roadLink.visit = true;
					if (loop_status.roadLink.ID.equals(destination)) {
						if (minCost == 0) {
							minCost = loop_status.cost;
						} else if (minCost > loop_status.cost) {
							minCost = loop_status.cost;
						}
						queue.clear();
						break;
					}
					if (loop_status.roadLink.next_ID == null)
						continue;
					for (String next_RoadLinkForSP_id : loop_status.roadLink.next_ID) {
						//System.out.println(next_RoadLinkForSP_id);
						RoadLinkForSP temp_roadlink = id_RoadLinkForSP_hash.get(next_RoadLinkForSP_id);
						// System.out.println(loop_status.roadLink.ID);
						double temp_cost = loop_status.cost + temp_roadlink.length;
						// List<String>
						// temp_list=loop_status.path;
						// temp_list.add(temp_roadlink.ID);
						queue.add(new StatusForShortestPath(temp_roadlink, temp_cost));
					}
					id_RoadLinkForSP_hash.put(loop_status.roadLink.ID, loop_status.roadLink);
				}
			}
			for (Iterator<String> iterator2 = id_RoadLinkForSP_hash.keySet().iterator(); iterator2.hasNext();) {
				String reset_ID = iterator2.next();
				RoadLinkForSP reset_RoadLink = id_RoadLinkForSP_hash.get(reset_ID);
				reset_RoadLink.visit = false;
				id_RoadLinkForSP_hash.put(reset_ID, reset_RoadLink);
			}
			file_roadlink.close();
			//file_SPResult.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
