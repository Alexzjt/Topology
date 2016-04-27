import java.io.*;
import java.util.*;
public class BFSforShortestPath {
	public static void main(String[] args){
		try {
			BufferedReader file_roadlink=new BufferedReader(new FileReader(Config.ROADLINK_MID_FILE));
			HashMap<String,RoadLink> id_RoadLink_hash=new HashMap<String,RoadLink>();
			HashMap<String,List<String>> stationID_RoadLinkID_hash=new HashMap<String,List<String>>();
			String line;
			while((line=file_roadlink.readLine())!=null){
				String[] array_line=line.split(",");
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
