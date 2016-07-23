package Script;
import java.io.*;

import dao.RoadLink;
import util.Config;

public class ExtractShortRoadLinkInSameRoad {
	public static void main(String[] args) {
		try {
			File dir = new File(Config.OD_DIJKSTRA_DIR);
			String[] file_name_array = dir.list();
			for (String origin : file_name_array) {
				for (String destination : file_name_array) {
					String input_file_path = append_file_path(Config.OD_DIJKSTRA_DIR, origin, destination);
					String line = null;
					String[] line_array = null;
					RoadLink loop_roadlink = null;
					if (input_file_path == null)
						continue;
					File output_file=new File(Config.OD_DIJKSTRA_DIR_SHORT + origin + "\\" + destination);
					output_file.mkdirs();
					BufferedReader reader = new BufferedReader(new FileReader(
							Config.OD_DIJKSTRA_DIR + origin + "\\" + destination + "\\" + input_file_path));
					BufferedWriter writer = new BufferedWriter(new FileWriter(
							Config.OD_DIJKSTRA_DIR_SHORT + origin + "\\" + destination + "\\" + input_file_path));
					while ((line = reader.readLine()) != null) {
						line_array = line.split(",");
						if (loop_roadlink == null) {
							loop_roadlink = new RoadLink(line_array, true);
						} else {
							if (loop_roadlink.highway_ID.equals(line_array[6]) || line_array[6].equals("null")) {
								loop_roadlink.length += Double.valueOf(line_array[3]);
							} else {
								writer.write(loop_roadlink.toString_Short() + "\r\n");
								loop_roadlink = new RoadLink(line_array, true);
							}
						}
					}
					loop_roadlink.length-=Double.valueOf(line_array[3]);
					writer.write(loop_roadlink.toString_Short() + "\r\n");
					loop_roadlink = new RoadLink(line_array, true);
					writer.write(loop_roadlink.toString_Short() + "\r\n");
					reader.close();
					writer.close();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static String append_file_path(String dir, String origin, String destination) {
		StringBuilder str = new StringBuilder(dir);
		str.append(origin).append("\\").append(destination);
		File path = new File(str.toString());
		if (path.isDirectory()) {
			String[] s = path.list();
			if (s.length != 0) {
				for (String name : s) {
					if (name.contains("csv"))
						return name;
				}
			}
		}
		return null;
	}
}
