package script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import com.sun.corba.se.spi.orb.StringPair;

import dao.GeographicGrid;
import dao.LonLat;
import util.Config;
import util.FileUtil;

public class NationalLonLatGridID {
	public static void main(String[] args){
		List<String> paths=FileUtil.getAbsolutePathFromDIR(Config.NATIONAL_BOUNDARY_DIR);
		try {
			for(String path : paths){
				BufferedReader reader=new BufferedReader(new FileReader(path));
				BufferedWriter writer=new BufferedWriter(new FileWriter(path.replace(Config.NATIONAL_BOUNDARY_DIR, Config.NATIONAL_BOUNDARY_OUTPUT)));
				String line;
				while((line=reader.readLine())!=null){
					String[] line_array=line.split(";");   //此处需要注意，具体的分割符是什么
					for(String lonlat_str : line_array){
						LonLat lonLat=new LonLat(lonlat_str.split("，"));  //此处需要注意，具体的分割符是什么
						lonLat.baiduTOgcj();
						GeographicGrid grid=new GeographicGrid(lonLat);
						writer.write(grid.toStringLonLatGeographicCode()+"\r\n");
					}
				}
				reader.close();
				writer.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
