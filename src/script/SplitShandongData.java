package script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import util.FileUtil;

public class SplitShandongData {
	public static void main(String[] args) {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		List<String> paths = FileUtil.getAbsolutePathFromDIR("C:\\Users\\lirun\\Desktop\\四月消费流水");
		for (String path : paths) {
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"GBK"));
				File file = new File(path);
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\lirun\\Desktop\\山东省内拆分结果\\" + file.getName()),"UTF-8"));
				String line;
				while ((line = reader.readLine()) != null) {
					String[] array = line.split(",");
					StringBuilder sb = new StringBuilder();
					if (array[50].contains(" ") && !array[50].contains("|")) {
						sb.append(array[0]).append(",").append(array[3]).append(",").append(array[12]).append(",")
								.append(array[28]).append(",");
						String origin = "", destination = "", time1 = "", time2 = "", overlimit = "0", weight = "0";

						origin = array[50].substring(array[50].indexOf(" ") + 1, array[50].indexOf("入"));
						origin = origin.replace("主线", "");
						origin = origin.replace("站", "收费站");
						destination = array[50].substring(array[50].indexOf(" ", array[50].indexOf("入")) + 1,
								array[50].indexOf("出"));
						destination = destination.replace("主线", "");
						destination = destination.replace("站", "收费站");
						time1 = array[50].substring(0, array[50].indexOf(" "));
						time2 = array[50].substring(array[50].indexOf("，") + 1,
								array[50].indexOf(" ", array[50].indexOf("，")));
						if(array[50].indexOf(" 总重")>=0){
							weight = array[50].substring(array[50].indexOf(" 总重") + 3, array[50].indexOf("吨"));
						}
						if(array[50].indexOf(" 超限率")>=0){
							overlimit = array[50].substring(array[50].indexOf(" 超限率") + 4, array[50].indexOf("％"));
						}
						sb.append(time1).append(",").append(origin).append(",").append(time2).append(",")
								.append(destination).append(",").append(weight).append(",").append(overlimit)
								.append(",");
						String[] cars = array[52].split("\\|");
						sb.append(cars[1]).append(",").append(cars[2]).append(",").append(cars[3]).append("\r\n");
					}
					if(sb.length()!=0){
						//writer.write(new String(sb.toString().getBytes(),"UTF-8"));
						writer.write(sb.toString());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
