import java.io.*;
import java.util.*;
public class BFSforShortestPath {
	public static void main(String[] args){
		try {
			BufferedReader file_roadlink=new BufferedReader(new FileReader(Config.ROADLINK_MID_FILE));
			BufferedWriter file_SPResult=new BufferedWriter(new FileWriter(Config.STATION_SHORTEST_PATH_LENGTH));
			HashMap<String,RoadLink> id_RoadLink_hash=new HashMap<String,RoadLink>();
			HashMap<String,List<String>> stationID_RoadLinkID_hash=new HashMap<String,List<String>>();
			String line;
			while((line=file_roadlink.readLine())!=null){
				//System.out.println(line);
				String[] array_line=line.split(",");
				RoadLink loop_roadlink=new RoadLink(array_line);
				id_RoadLink_hash.put(loop_roadlink.ID,loop_roadlink);
				if(loop_roadlink.station!=null){
					if(stationID_RoadLinkID_hash.containsKey(loop_roadlink.station)){
						List<String> temp_str_list=stationID_RoadLinkID_hash.get(loop_roadlink.station);
						temp_str_list.add(loop_roadlink.ID);
						stationID_RoadLinkID_hash.put(loop_roadlink.station,temp_str_list);
					}
					else {
						List<String> temp_str_list=new ArrayList<String>(6);
						temp_str_list.add(loop_roadlink.ID);
						stationID_RoadLinkID_hash.put(loop_roadlink.station,temp_str_list);
					}
				}
			}
			for(Iterator<String> iterator=stationID_RoadLinkID_hash.keySet().iterator();iterator.hasNext();){
				String loop_station=iterator.next();
				for(Iterator<String> iterator1=stationID_RoadLinkID_hash.keySet().iterator();iterator1.hasNext();){
					String loop_station1=iterator1.next();
					if(loop_station.equals(loop_station1)){
						System.out.println(loop_station+","+loop_station1+","+String.valueOf(0));
						file_SPResult.write(loop_station+","+loop_station1+","+String.valueOf(0));
						continue;
					}
					double minCost=0;
					for(String loop_roadlink_id : stationID_RoadLinkID_hash.get(loop_station)){
						for(String loop_roadlink_id1 : stationID_RoadLinkID_hash.get(loop_station1)){
							ArrayDeque<StatusForShortestPath> queue=new ArrayDeque<StatusForShortestPath>();
							queue.add(new StatusForShortestPath(id_RoadLink_hash.get(loop_roadlink_id)));
							while(!queue.isEmpty()){
								StatusForShortestPath loop_status=queue.poll();
								if(loop_status.roadLink.next_ID==null)
									continue;
								if(loop_status.roadLink.ID.equals(loop_roadlink_id1)){
									if(minCost==0){
										minCost=loop_status.cost;
									}
									else if(minCost>loop_status.cost){
										minCost=loop_status.cost;
									}
									queue.clear();
									break;
								}
								for(String next_RoadLink_id : loop_status.roadLink.next_ID){
									RoadLink temp_roadlink=id_RoadLink_hash.get(next_RoadLink_id);
									//System.out.println(loop_status.roadLink.ID);
									double temp_cost=loop_status.cost+temp_roadlink.length;
									//List<String> temp_list=loop_status.path;
									//temp_list.add(temp_roadlink.ID);
									queue.add(new StatusForShortestPath(temp_roadlink,temp_cost));
								}
							}
						}
					}
					System.out.println(loop_station+","+loop_station1+","+String.valueOf(minCost));
					file_SPResult.write(loop_station+","+loop_station1+","+String.valueOf(minCost));
				}
			}
			file_roadlink.close();
			file_SPResult.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
