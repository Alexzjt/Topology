package Script;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import path.SubOptimalPath;
import util.Config;

public class AnalysisTollData {
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Config.TOLL_DATA));
			BufferedWriter writer = new BufferedWriter(new FileWriter(Config.TOLL_DATA_ANALYSIS));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] line_array = line.split(",");
				String origin = line_array[9], destination = line_array[0], vehicle_ID = line_array[21].trim();
				File examine = new File(Config.OD_DIJKSTRA_DIR_SHORT + origin + "\\" + destination);
				if (!examine.exists())
					continue;
				Date inDate = Config.DF_TOLL.parse(line_array[10]), outDate = Config.DF_TOLL.parse(line_array[2]);
				long difference_time = outDate.getTime() - inDate.getTime();
				if (difference_time <= 0)
					continue;
				BufferedReader od_File = new BufferedReader(new FileReader(
						SubOptimalPath.append_file_path(Config.OD_DIJKSTRA_DIR_SHORT, origin, destination)));
				String s = null;
				double length = 0, used_length = 0;
				List<String> od_File_lines = new ArrayList<String>();
				while ((s = od_File.readLine()) != null) {
					od_File_lines.add(s);
					String[] s_array = s.split(",");
					length += Double.valueOf(s_array[1]);
				}
				od_File.close();
				for (int i = 0; i < od_File_lines.size() - 1; i++) {
					s = od_File_lines.get(i);
					String[] s_array = s.split(",");
					double bias = (double) difference_time * used_length / length;
					String string = Config.DF_TOLL.format(new Date(inDate.getTime() + (long) bias));
					writer.write(s.trim() + "," + vehicle_ID + "," + string + "\r\n");
					used_length += Double.valueOf(s_array[1]);
				}
				writer.write(od_File_lines.get(od_File_lines.size() - 1).trim() + "," + vehicle_ID + ","
						+ Config.DF_TOLL.format(outDate) + "\r\n");
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
