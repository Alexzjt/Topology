package Script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.corba.se.spi.orb.StringPair;

import util.*;
import dao.*;

/*
1、401桃花街收费站和312长寿收费站，经纬度、桩号等信息完全一致，经查证为同一收费站。
2、3003土主收费站、3019西永收费站已撤销。
3、2402黔江北收费站、2403G5515黔江收费站在此版本四维图新地图中不存在。所在路链与其余路网不连通。
4、4806拾万收费站所在立交在此版本四维图新地图中不存在。
5、4705宝石主线收费站在网上和四维地图中都查不到。且没有经纬度信息。
*/


public class FindUnknownStation {
	public static void main(String[] args) {
		try {
			BufferedReader topology_file = new BufferedReader(new FileReader(Config.ROADLINK_MIDDLE_FILE_SUB_OPT));
			Set<String> station_set=new HashSet<String>(300);
			station_set.add("401");  //此处专门这样写，是因为401和312这两个号码对应的收费站是一个，却号码不同。
			String s;
			while((s=topology_file.readLine())!=null){
				String[] s_array=s.split(",");
				station_set.add(s_array[15]);
			}
			topology_file.close();
			
			BufferedReader toll_file=new BufferedReader(new FileReader(Config.TOLL_DATA));
			BufferedWriter unknown_file=new BufferedWriter(new FileWriter(Config.TOLL_DATA_UNKNOWN));
			while((s=toll_file.readLine())!=null){
				String[] s_array=s.split(",");
				if(!station_set.contains(s_array[0])||!station_set.contains(s_array[9])){
					unknown_file.write(s+"\r\n");
				}
			}
			toll_file.close();
			unknown_file.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
