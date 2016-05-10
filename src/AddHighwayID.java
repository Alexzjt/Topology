import java.io.*;
import java.util.*;

public class AddHighwayID {
	public static void main(String[] args) {
		try {
			BufferedReader file_highway_SE = new BufferedReader(new FileReader(Config.HIGHWAY_START_END));
			BufferedReader file_RoadLink = new BufferedReader(new FileReader(Config.ROADLINK_MIDDLE_FILE));
			BufferedWriter file_RoadLink_HighwayID = new BufferedWriter(new FileWriter(Config.ROADLINK_HIGHWAY_ID));
			HashMap<String, RoadLink> id_RoadLink = new HashMap<String, RoadLink>();
			String s;
			while ((s = file_RoadLink.readLine()) != null) {
				String[] loop_array = s.split(",");
				id_RoadLink.put(loop_array[0], new RoadLink(loop_array, true));
			}
			while ((s = file_highway_SE.readLine()) != null) {
				int count=0;  //进度可视化
				String[] loop_array = s.split(",");
				ArrayDeque<String> queue = new ArrayDeque<String>();
				queue.add(loop_array[0]);
				while (!queue.isEmpty()) {
					String loop_id = queue.poll();
					if (loop_id.equals(loop_array[1]))
						break;
					RoadLink loop_RoadLink = id_RoadLink.get(loop_id);
					if (!loop_RoadLink.isRamp) {
						count++;
						loop_RoadLink.highway_ID = loop_array[2];
						id_RoadLink.put(loop_RoadLink.ID, loop_RoadLink);
						for(String next : loop_RoadLink.next_ID){
							queue.add(next);
						}
					}
				}
				System.out.println(loop_array[2]+"  count "+count);
			}
			for(Iterator<RoadLink> iterator=id_RoadLink.values().iterator();iterator.hasNext();){
				RoadLink loop_RoadLink=iterator.next();
				file_RoadLink_HighwayID.write(loop_RoadLink.toString()+"\r\n");
			}
			file_highway_SE.close();
			file_RoadLink.close();
			file_RoadLink_HighwayID.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
