import java.io.*;
import java.util.*;

import com.sun.xml.internal.bind.v2.runtime.Name;

public class SubOptimalPath {
	static HashMap<String, RoadLink> id_RoadLink_hash;
	static HashMap<String, List<String>> stationID_RoadLinkID_hash;
	static HashMap<String, Double> SE_length;
	static List<String> stationID;
	static ArrayList<List<String>> used_out_in_list=new ArrayList<List<String>>();
	static Comparator<StatusForShortestPath> order = new Comparator<StatusForShortestPath>() {
		public int compare(StatusForShortestPath obj1, StatusForShortestPath obj2) {
			if (obj1.cost < obj2.cost)
				return -1;
			else if (obj1.cost == obj2.cost)
				return 0;
			else {
				return 1;
			}
		}
	};

	public static void main(String[] args) {
		try {
			BufferedReader file_roadlink = new BufferedReader(new FileReader(Config.ROADLINK_MIDDLE_FILE_SUB_OPT));// 读路链拓扑文件
			BufferedReader file_station_SP = new BufferedReader(new FileReader(Config.SHORTEST_PATH_LENGTH_IN));
			id_RoadLink_hash = new HashMap<String, RoadLink>();
			stationID_RoadLinkID_hash = new HashMap<String, List<String>>();
			SE_length = new HashMap<String, Double>();
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
			while ((line = file_station_SP.readLine()) != null) {
				String[] line_array = line.split(",");
				SE_length.put(line_array[0] + "_" + line_array[1], Double.valueOf(line_array[2]));
			}
			file_roadlink.close();
			file_station_SP.close();
			for (String origin_station : stationID) {
				for (String destination_station : stationID) {
					if (origin_station.equals(destination_station))
						continue;
					String file_sp_path = append_file_path(Config.SUB_OPTIMAL_PATH_DIR, origin_station,
							destination_station);
					BufferedReader file_sp = new BufferedReader(new FileReader(file_sp_path));
					used_out_in_list.clear();
					Queue<StatusForShortestPath> priorityQueue = new PriorityQueue<StatusForShortestPath>(10, order);
					ArrayList<String> sp_path = new ArrayList<String>(100);
					while ((line = file_sp.readLine()) != null) {
						String[] array_line = line.split(",");
						sp_path.add(array_line[0]);
					}
					file_sp.close();
					ArrayList<ArrayList<String>> record_all_path = new ArrayList<ArrayList<String>>();
					record_all_path.add(sp_path);
					priorityQueue = find_Kth_Sub_Optimal_Path(priorityQueue, sp_path);
					while (!priorityQueue.isEmpty()) {
						StatusForShortestPath sub_optimal_status = priorityQueue.poll();
						if (sub_optimal_status.cost < SE_length.get(origin_station + "_" + destination_station)
								* Config.TOLERANCE_MULTIPLE && !compare_similarity_in_Path_List(record_all_path,sub_optimal_status.path)) {
							record_all_path.add(sub_optimal_status.path);
							identify_Status(sub_optimal_status,
									Config.SUB_OPTIMAL_PATH_DIR + origin_station + "\\" + destination_station);
							System.out.println(origin_station + "_" + destination_station+" "+sub_optimal_status.cost);
							priorityQueue = find_Kth_Sub_Optimal_Path(priorityQueue, sub_optimal_status.path);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static boolean compare_similarity(ArrayList<String> list1,ArrayList<String> list2){
		//此函数是为了比较两个路径是否相似，因为在前一版本中，出现了在服务区这种地方，匝道特别多，排列组合下来之后产生了特别多的相似次优路径。
		//本函数是比较两个List是否相似。List1为原始路径，List2为待比较路径，顺序不能用错。
		int length1=list1.size(),length2=list2.size();
		ArrayList<String> l1=new ArrayList<String>(list1),l2=new ArrayList<String>(list2);
		for(String str : l1){
			l2.remove(str);
		}
		return (double)l2.size()/(double)length2<=Config.TOLERANCE_SIMILARITY;
	}
	
	public static boolean compare_similarity_in_Path_List(ArrayList<ArrayList<String>> list1,ArrayList<String> list2){
		//和上面那个函数不一样的地方在于，这个函数是在list1中查找是否有路径和list2相似。即List1是路径的集合，list2是路径。
		for(ArrayList<String> l1 : list1){
			if(compare_similarity(l1, list2)){
				return true;
			}
		}
		return false;
	}

	public static Queue<StatusForShortestPath> find_Kth_Sub_Optimal_Path(Queue<StatusForShortestPath> priorityQueue,
			List<String> sp_path) {
		for (int i = 0; i < sp_path.size(); i++) {
			RoadLink out_Roadlink = id_RoadLink_hash.get(sp_path.get(i));
			if (out_Roadlink.next_ID != null && out_Roadlink.next_ID.size() > 1) { // 如果这个路链的下个路链是分叉的。就说明有第二条路可以走，可以从此离开最短路径。
				for (int j = i + 1; j < sp_path.size(); j++) {
					RoadLink in_Roadlink = id_RoadLink_hash.get(sp_path.get(j));
					if (in_Roadlink.pre_ID != null && in_Roadlink.pre_ID.size() > 1) { // 说明可以从此进入最短路径中
						for (String out_ID : out_Roadlink.next_ID) {
							List<String> out_in_pair=Arrays.asList(out_ID,sp_path.get(j));
							if (!out_ID.equals(sp_path.get(i + 1))&&!used_out_in_list.contains(out_in_pair)) { // 这个出口必须不能和最短路径走一条路
								used_out_in_list.add(out_in_pair);
								StatusForShortestPath mid_status = BFS(out_ID, sp_path.get(j));
								if (mid_status != null) {
									StatusForShortestPath record_status = new StatusForShortestPath();
									for (int i1 = 0; i1 <= i; i1++) {
										record_status.add_RoadLink(id_RoadLink_hash.get(sp_path.get(i1)));
									}
									record_status.append_Status_Path_Cost(mid_status);
									for (int i1 = j + 1; i1 < sp_path.size(); i1++) {
										record_status.add_RoadLink(id_RoadLink_hash.get(sp_path.get(i1)));
									}
									priorityQueue.add(record_status);
								}
							}
						}
					}
				}
			}
		}
		return priorityQueue;
	}

	public static StatusForShortestPath BFS(String origin_id, String destination_id) {
		double minCost = 0;
		// List<String> minCostPath = new ArrayList<String>(200);
		StatusForShortestPath final_status = null;
		ArrayDeque<StatusForShortestPath> queue = new ArrayDeque<StatusForShortestPath>();
		List<String> init_path = new ArrayList<String>(200);
		init_path.add(origin_id);
		queue.add(new StatusForShortestPath(id_RoadLink_hash.get(origin_id), 0, init_path));
		while (!queue.isEmpty()) {
			StatusForShortestPath loop_status = queue.poll();
			if (!loop_status.roadLink.visit) {
				loop_status.roadLink.visit = true;
				if (loop_status.roadLink.ID.equals(destination_id)) {
					if (minCost == 0) {
						minCost = loop_status.cost;
						// minCostPath = loop_status.path;
						final_status = loop_status;
					} else if (minCost > loop_status.cost) {
						minCost = loop_status.cost;
						// minCostPath = loop_status.path;
						final_status = loop_status;
					}
					break;
				}
				if (loop_status.roadLink.next_ID == null)
					continue;
				for (String next_RoadLink_id : loop_status.roadLink.next_ID) {
					RoadLink temp_roadlink = id_RoadLink_hash.get(next_RoadLink_id);
					// System.out.println(loop_status.roadLink.ID);
					double temp_cost = loop_status.cost + temp_roadlink.length;
					List<String> temp_list = new ArrayList<String>(loop_status.path);
					temp_list.add(temp_roadlink.ID);
					queue.add(new StatusForShortestPath(temp_roadlink, temp_cost, temp_list));
				}
				id_RoadLink_hash.put(loop_status.roadLink.ID, loop_status.roadLink);
			}
		}
		set_visit_false();
		return final_status;
	}

	public static void set_visit_false() {
		for (Iterator<String> iterator2 = id_RoadLink_hash.keySet().iterator(); iterator2.hasNext();) {
			String reset_ID = iterator2.next();
			RoadLink reset_RoadLink = id_RoadLink_hash.get(reset_ID);
			reset_RoadLink.visit = false;
			id_RoadLink_hash.put(reset_ID, reset_RoadLink);
		}
	}

	public static String append_file_path(String dir, String origin, String destination) {
		StringBuilder str = new StringBuilder(dir);
		str.append(origin).append("\\").append(destination);
		File path = new File(str.toString());
		if (path.isDirectory()) {
			String[] s = path.list();
			if (s.length != 0) {
				for(String name : s){
					if(name.contains("csv"))
						return str.append("\\").append(name).toString();
				}
			}
		}
		return null;
	}

	public static void identify_Status(StatusForShortestPath status, String path) {
		try {
			BufferedWriter file = new BufferedWriter(
					new FileWriter(path + "\\" + (int) status.cost + "次优" + (Config.water++) + ".csv"));
			for (String str : status.path) {
				file.write(id_RoadLink_hash.get(str).toString() + "\r\n");
			}
			file.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
