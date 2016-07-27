package script;

import generateTopology.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Set;

import util.Config;

public class GeoGridMatchAdminDivisionProvince {
	public static void main(String[] args) throws Exception {
		BufferedWriter writer=new BufferedWriter(new FileWriter(Config.GEOGRID_ADMIN_PROVINCE_MATCH_OUTPUT));
		File dir = new File(Config.NAVINFO_PRE);
		String[] file_name_array = dir.list();
		for (String province : file_name_array) {
			System.out.println(province);
			Set<String> numberSet=Main.getMapSheetNumberSet(Config.NAVINFO_PRE + province
							+ "\\road\\R" + province + ".mid");
			for(Iterator<String> iterator=numberSet.iterator();iterator.hasNext();){
				String mapsheet=iterator.next();
				writer.write(mapsheet+","+province+"\r\n");
			}
		}
		writer.close();
	}
}
