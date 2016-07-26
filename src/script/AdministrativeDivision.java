package script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;

import java.io.FileWriter;

import util.Config;

public class AdministrativeDivision {
	public static void main(String[] args) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					Config.ADMINISTRARIVE_DIVISION_FILE_PATH));
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					Config.ADMINISTRARIVE_DIVISION_FILE_OUTPUT));
			String line = null, province = null, city = null;
			String before="",now="";
			while ((line = reader.readLine()) != null) {
				String[] line_array = line.split("\\s+");
				before=now;
				now=line_array[0];
				if(!before.endsWith("0000")&&before.endsWith("00")&&now.endsWith("00"))
					System.out.println(before);
				if (line_array[1].equals("市辖区") || line_array[1].equals("县")
						|| line_array[1].equals("省直辖县级行政区划")
						|| line_array[1].equals("自治区直辖县级行政区划")){
					if(line_array[0].endsWith("00"))
						city=null;
					continue;
				}
				if (line_array[0].endsWith("0000")) {
					province = line_array[1];
					city=null;
				} else if (line_array[0].endsWith("00")) {
					city = line_array[1];
				} else {
					StringBuilder stringBuilder = new StringBuilder(
							line_array[0]);
					stringBuilder.append(",").append(province==null?"":province).append(city==null?"":city)
							.append(line_array[1]).append("\r\n");
					writer.write(stringBuilder.toString());
				}
			}
			reader.close();
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
