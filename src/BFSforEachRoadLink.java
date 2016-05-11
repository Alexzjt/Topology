import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
public class BFSforEachRoadLink {
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
				if(loop_roadlink.next_ID!=null&&loop_roadlink.next_ID.size()>1)
					roadlink_out_ID_List.addAll(loop_roadlink.next_ID);
				if(loop_roadlink.pre_ID!=null&&loop_roadlink.pre_ID.size()>1)
					roadLink_in_ID_List.add(loop_roadlink.ID);
			}
			file_roadlink.close();
			for(int i=0;i<roadlink_out_ID_List.size();i++){
				for(int i1=0;i1<roadLink_in_ID_List.size();i1++){
					File loop_file=new File(Config.ROADLINK_EACH_DIR+roadlink_out_ID_List.get(i)+"\\"+roadLink_in_ID_List.get(i1));
					if(!loop_file.exists())
						loop_file.mkdirs();
					if(i==i1)
						continue;
					StatusForShortestPath result=BFS(roadlink_out_ID_List.get(i),roadLink_in_ID_List.get(i));
					if(result!=null){
						identify_Status(result,Config.ROADLINK_EACH_DIR+roadlink_out_ID_List.get(i)+"\\"+roadLink_in_ID_List.get(i));
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
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
			BufferedWriter file = new BufferedWriter(
					new FileWriter(path + "\\" + (int) status.cost + "╢нсе" + (Config.water++) + ".csv"));
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
