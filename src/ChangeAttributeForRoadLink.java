import java.io.*;
import java.util.*;

public class ChangeAttributeForRoadLink {
	public static void main(String[] args){
		try{
			BufferedReader file_roadlink = new BufferedReader(new FileReader(Config.ROADLINK_MIDDLE_FILE_SUB_OPT));//
			HashMap<String, RoadLink> id_RoadLink_old = new HashMap<String, RoadLink>();
			String line;
			while ((line = file_roadlink.readLine()) != null) {
				String[] array_line = line.split(",");
				RoadLink loop_roadlink = new RoadLink(array_line, true);
				id_RoadLink_old.put(loop_roadlink.ID, loop_roadlink);
			}
			file_roadlink.close();
			HashMap<String, RoadLink> id_RoadLink_new=Main.getTopology_ID_RoadLink(Config.MAP_MID,Config.MAP_MIF);
			BufferedWriter writer=new BufferedWriter(new FileWriter(Config.ROADLINK_HIGHWAY_ID));
			for(Iterator<RoadLink> iterator=id_RoadLink_old.values().iterator();iterator.hasNext();){
				RoadLink loop_roadLink=iterator.next();
//				loop_roadLink.road_attribute=id_RoadLink_new.get(loop_roadLink.ID).road_attribute;
//				loop_roadLink.isRamp=id_RoadLink_new.get(loop_roadLink.ID).isRamp;
				loop_roadLink.lonlat_list=id_RoadLink_new.get(loop_roadLink.ID).lonlat_list;
				writer.write(loop_roadLink.toString()+"\r\n");
			}
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
