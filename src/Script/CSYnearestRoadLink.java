package Script;
import java.io.*;
import java.util.*;

import com.sun.jndi.ldap.LdapPoolManager;

import dao.LonLat;
import dao.RoadLink;
import dao.Tachometer;
import util.Config;

public class CSYnearestRoadLink {
	public static void main(String[] args){
		//暂时先写在这里吧，目前没仔细想如何函数化代码
		try{
			BufferedReader file_mid = new BufferedReader(new FileReader(Config.MAP_MID));
			BufferedReader file_mif = new BufferedReader(new FileReader(Config.MAP_MIF));
			BufferedReader file_tachometer = new BufferedReader(new FileReader(Config.TACHYMETER));
			BufferedWriter file_tachometer_nearest_Roadlink=new BufferedWriter(new FileWriter(Config.CYSNEARESTROADLINK));
			int line=0,mif_kuai=0;
			List<String> lonLat_array=new ArrayList<String>();
			List<Integer> highway_lines=new ArrayList<Integer>(Config.HIGHWAY_COUNT);
			HashMap<String,RoadLink> id_RoadLink=new HashMap<String,RoadLink>();
			HashMap<Integer,RoadLink> line_RoadLink=new HashMap<Integer,RoadLink>();
			HashMap<String,Tachometer> id_Tachometer=new HashMap<String,Tachometer>();
			String s;
			while((s=file_tachometer.readLine())!=null){//读测速仪基础信息文件
				String[] s_array=s.split("	");
				if(s_array[8].equals("null")||s_array[9].equals("null"))
					continue;
				Tachometer temp_Tachometer=new Tachometer(s_array[1],s_array[0],s_array[8],s_array[9]);
				id_Tachometer.put(temp_Tachometer.id,temp_Tachometer);
			}
			while((s=file_mid.readLine())!=null){//读MID文件
				line++;
				String[] s_array=s.split("\",\"|\"");
				//判断这一行是高速公路，即前两个字段是00
				if(s_array[4].charAt(0)=='0'&&s_array[4].charAt(1)=='0'){
					highway_lines.add(line);
					RoadLink temp_RoadLink=new RoadLink(s_array);
					temp_RoadLink.line=line;
					id_RoadLink.put(s_array[2],temp_RoadLink);
					line_RoadLink.put(line,temp_RoadLink);
				}
			}
			while((s=file_mif.readLine())!=null){//mif文件
				if(s.contains("Line")||s.contains("Pline")){         
					mif_kuai++;
					lonLat_array.clear();
					lonLat_array.add(s);
				}
				else if(s.contains("Pen")){
					if(highway_lines.contains(mif_kuai)){//读到了高速公路的经纬度序列
						List<LonLat> lonLat_list=new ArrayList<LonLat>();
						int count=0;
						String lon=null,lat=null;
						for(String x : lonLat_array){
							String[] array_x=x.split(" ");
							for(String str : array_x){
								if(str.contains(".")){
									count++;
									if(count%2!=0){
										lon=str;
									}
									else{
										lat=str;
										lonLat_list.add(new LonLat(lon,lat));
									}
								}
							}
						}
						RoadLink temp_RoadLink=line_RoadLink.get(mif_kuai);
						temp_RoadLink.lonlat_list=lonLat_list;
						line_RoadLink.put(mif_kuai,temp_RoadLink);
						id_RoadLink.put(temp_RoadLink.ID,temp_RoadLink);
					}
				}
				else{
					lonLat_array.add(s);
				}
			}
			for(Iterator<Tachometer> iterator1=id_Tachometer.values().iterator();iterator1.hasNext();){
				Tachometer loop_tachometer=iterator1.next();
				double nearest_length=Config.EARTH_RADIUS;  //先定义为地球半径，一个不能更大的值
				String nearest_RoadLink_id="";
				System.out.println(loop_tachometer.id);
				for(Iterator<RoadLink> iterator2=id_RoadLink.values().iterator();iterator2.hasNext();){
					RoadLink loop_RoadLink=iterator2.next();
					if(!loop_RoadLink.isRamp){
						for(LonLat loop_roadlink_lonlat : loop_RoadLink.lonlat_list){
							double loop_temp_length=LonLat.GetDistance(loop_roadlink_lonlat,loop_tachometer.lonLat);
							if(loop_temp_length<nearest_length){
								nearest_length=loop_temp_length;
								nearest_RoadLink_id=loop_RoadLink.ID;
							}
						}
					}
				}
				StringBuilder output_line=new StringBuilder(loop_tachometer.id);
				output_line.append(",").append(nearest_RoadLink_id).append(",")
				.append(String.valueOf(nearest_length)).append("\r\n");
				file_tachometer_nearest_Roadlink.write(output_line.toString());
			}
			file_mid.close();
			file_mif.close();
			file_tachometer.close();
			file_tachometer_nearest_Roadlink.close();
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		finally{
			
		}
	}
	
}