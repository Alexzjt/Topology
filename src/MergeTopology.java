import java.io.*;
import java.util.*;

public class MergeTopology {
	public static void main(String[] args) {
		String topology1 = "D:\\zjt\\四维图新\\29省路网拓扑\\guangdong1.csv";
		String topology2 = "D:\\zjt\\四维图新\\29省路网拓扑\\guangdong2.csv";
		String topology3 = "D:\\zjt\\四维图新\\29省路网拓扑\\guangzhou.csv";
		String mid = "D:\\zjt\\四维图新\\level2\\level2\\guangdong1\\road\\RguangdongHighway.MID";
		String mif = "D:\\zjt\\四维图新\\level2\\level2\\guangdong1\\road\\RguangdongHighway.MIF";
		String output = "C:\\Users\\lirun\\Desktop\\svn\\ZY1421226张景涛\\29省路网拓扑\\guangdong.csv";
		try {
			BufferedReader reader1 = new BufferedReader(new FileReader(topology1));
			BufferedReader reader2 = new BufferedReader(new FileReader(topology2));
			BufferedReader reader3 = new BufferedReader(new FileReader(topology3));
			BufferedWriter writer=new BufferedWriter(new FileWriter(output));
			HashMap<String, RoadLink> id_Roadlink1=new HashMap<String, RoadLink>();
			HashMap<String, RoadLink> id_Roadlink2=new HashMap<String, RoadLink>();
			HashMap<String, RoadLink> id_Roadlink3=new HashMap<String, RoadLink>();
			HashMap<String, RoadLink> id_Roadlink=Main.getTopology_ID_RoadLink(mid, mif);
			String s;
			while ((s = reader1.readLine()) != null) {
				String[] s_array=s.split(",");
				id_Roadlink1.put(s_array[0], new RoadLink(s_array,true));
			}
			reader1.close();
			while ((s = reader2.readLine()) != null) {
				String[] s_array=s.split(",");
				id_Roadlink2.put(s_array[0], new RoadLink(s_array,true));
			}
			reader2.close();
			while ((s = reader3.readLine()) != null) {
				String[] s_array=s.split(",");
				id_Roadlink3.put(s_array[0], new RoadLink(s_array,true));
			}
			reader3.close();
			for(Iterator<RoadLink> iterator=id_Roadlink.values().iterator();iterator.hasNext();){
				RoadLink loop_RoadLink=iterator.next();
				if(id_Roadlink1.containsKey(loop_RoadLink.ID)){
					RoadLink temp_RoadLink=id_Roadlink1.get(loop_RoadLink.ID);
					loop_RoadLink.highway_ID=temp_RoadLink.highway_ID;
					loop_RoadLink.station=temp_RoadLink.station;
				}
				else if(id_Roadlink2.containsKey(loop_RoadLink.ID)){
					RoadLink temp_RoadLink=id_Roadlink2.get(loop_RoadLink.ID);
					loop_RoadLink.highway_ID=temp_RoadLink.highway_ID;
					loop_RoadLink.station=temp_RoadLink.station;
				}
				else if(id_Roadlink3.containsKey(loop_RoadLink.ID)){
					RoadLink temp_RoadLink=id_Roadlink3.get(loop_RoadLink.ID);
					loop_RoadLink.highway_ID=temp_RoadLink.highway_ID;
					loop_RoadLink.station=temp_RoadLink.station;
				}
				writer.write(loop_RoadLink.toString()+"\r\n");
			}
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
