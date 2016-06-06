import java.io.*;
import java.util.*;
public class DijkstraForEachRoadLink {
	static HashMap<String, RoadLink> id_RoadLink_hash;
	static List<String> roadlink_out_ID_List,roadLink_in_ID_List;
	public static void main(String[] args){
		try{
			BufferedReader file_roadlink = new BufferedReader(new FileReader(Config.ROADLINK_MIDDLE_FILE_SUB_OPT));//
			id_RoadLink_hash = new HashMap<String, RoadLink>();
			roadlink_out_ID_List=new ArrayList<String>(2000);
			roadLink_in_ID_List=new ArrayList<String>(2000);
			String line;
			while ((line = file_roadlink.readLine()) != null) {
				String[] array_line = line.split(",");
				RoadLink loop_roadlink = new RoadLink(array_line, true);
				id_RoadLink_hash.put(loop_roadlink.ID, loop_roadlink);
			}
			file_roadlink.close();
			for(Iterator<RoadLink> iterator=id_RoadLink_hash.values().iterator();iterator.hasNext();){
				RoadLink loop_roadlink=iterator.next();
				if(loop_roadlink.next_ID!=null&&loop_roadlink.next_ID.size()>1){
					boolean judge=true;
					for(String next_RoadLink_id : loop_roadlink.next_ID){
						if(!id_RoadLink_hash.get(next_RoadLink_id).is_MainLine()){
							judge=false;
							break;
						}
					}
					if(judge)
					roadlink_out_ID_List.addAll(loop_roadlink.next_ID);
				}
					
				if(loop_roadlink.pre_ID!=null&&loop_roadlink.pre_ID.size()>1){
					boolean judge=true;
					for(String pre_RoadLink_id : loop_roadlink.pre_ID){
						if(!id_RoadLink_hash.get(pre_RoadLink_id).is_MainLine()){
							judge=false;
							break;
						}
					}
					if(judge)
					roadLink_in_ID_List.add(loop_roadlink.ID);
				}
					
			}
			for(int i=0;i<roadlink_out_ID_List.size();i++){
				HashMap<String, StatusForShortestPath> hash=dijkstra(roadlink_out_ID_List.get(i));
				for(int i1=0;i1<roadLink_in_ID_List.size();i1++){
					if(roadlink_out_ID_List.get(i).equals(roadLink_in_ID_List.get(i1)))
						continue;
					StatusForShortestPath result=hash.get(roadLink_in_ID_List.get(i1));
					if(result!=null){
						identify_Status(result,Config.ROADLINK_EACH_DIR+roadlink_out_ID_List.get(i)+"\\"+roadLink_in_ID_List.get(i1));
					}
				}
			}
		}
		catch(Exception e){
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
	
	public static void identify_Status(StatusForShortestPath status, String path) {
		try {
			File loop_file=new File(path);
			if(!loop_file.exists())
				loop_file.mkdirs();
			BufferedWriter file = new BufferedWriter(
					new FileWriter(path + "\\" + (int) status.cost + "_" + (Config.water++) + ".csv"));
			for (String str : status.path) {
				file.write(str + "\r\n");
			}
			file.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
