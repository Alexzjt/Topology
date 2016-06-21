import java.io.*;
import java.util.*;

public class AddHighwayID {
	public static void main(String[] args) {
		try {
			BufferedReader file_highway_SE = new BufferedReader(new FileReader(Config.HIGHWAY_START_END));
			BufferedReader file_RoadLink = new BufferedReader(new FileReader(Config.ROADLINK_MIDDLE_FILE));
			BufferedWriter file_RoadLink_HighwayID = new BufferedWriter(new FileWriter(Config.ROADLINK_HIGHWAY_ID));
			BufferedWriter file_HighwayID_LonLat=new BufferedWriter(new FileWriter(Config.HIGHWAY_ID_LONLAT));
			HashMap<String, RoadLink> id_RoadLink = new HashMap<String, RoadLink>();
			String s;
			while ((s = file_RoadLink.readLine()) != null) {  //读取路链文件，建立路链ID与路链的哈希表
				String[] loop_array = s.split(",");
				id_RoadLink.put(loop_array[0], new RoadLink(loop_array, true));
			}
			while ((s = file_highway_SE.readLine()) != null) {  //读取包含高速公路起止路链的文件
				int count = 0; // 进度可视化
				String[] loop_array = s.split(",");
				String loop_id = loop_array[0];
				ArrayList<String> path=new ArrayList<String>(200);
				while (!loop_id.equals(loop_array[1])) {
					if(path.contains(loop_id)){
						System.out.println(loop_id+" "+loop_array);
					}
					path.add(loop_id);
					RoadLink loop_RoadLink = id_RoadLink.get(loop_id);
					//此处输出文件是为了验证结果是否正确，输出经纬度点以及所对应的高速公路编号，方便可视化验证结果
					file_HighwayID_LonLat.write(loop_RoadLink.lonlat_list.get(0).toString()+","+loop_array[2]+"\r\n");
					count++;
					loop_RoadLink.highway_ID = loop_array[2];
					id_RoadLink.put(loop_RoadLink.ID, loop_RoadLink);
					if (loop_RoadLink.next_ID.size() == 1) {
						loop_id = loop_RoadLink.next_ID.get(0);
					} else {
						for (String next : loop_RoadLink.next_ID) {
							if (!id_RoadLink.get(next).isRamp) {  //在当前路链的下一路链中，只取干线不取匝道，只对干线进行编号，不对匝道进行编号
								loop_id = next;
								break;
							}
						}
					}
				}
				System.out.println(loop_array[2] + "  count " + count);
			}
			for (Iterator<RoadLink> iterator = id_RoadLink.values().iterator(); iterator.hasNext();) {
				RoadLink loop_RoadLink = iterator.next();
				file_RoadLink_HighwayID.write(loop_RoadLink.toString() + "\r\n");
			}
			file_highway_SE.close();
			file_RoadLink.close();
			file_RoadLink_HighwayID.close();
			file_HighwayID_LonLat.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
