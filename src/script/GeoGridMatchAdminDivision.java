package script;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;








import dao.GeographicGrid;
import dao.LonLat;
import util.Config;
import util.FileUtil;


public class GeoGridMatchAdminDivision {
	public static void main(String[] args){
		try {
			BufferedReader grid6_file=new BufferedReader(new FileReader(Config.BEIJING_GRID));
			String line;
			List<GeographicGrid> gridList=new ArrayList<GeographicGrid>(22000);
			while((line=grid6_file.readLine())!=null){
				String[] array=line.split(",");
				gridList.addAll(GeographicGrid.getGridListFrom6Code(array[2]));
			}
			grid6_file.close();
			
			List<String> boundary_file_list=FileUtil.getAbsolutePathFromDIR(Config.BEIJING_BOUNDARY_DIR);
			HashMap<String, List<LonLat>> name_LonLatList_hash=new HashMap<String, List<LonLat>>();
			for(String path : boundary_file_list){
				name_LonLatList_hash.put(path,LonLat.getLonLatListFromFile(path,0,1));
			}
			BufferedWriter writer=new BufferedWriter(new FileWriter(Config.BEIJING_MATCH));
			for(GeographicGrid grid : gridList){
				for(Iterator<String> iterator=name_LonLatList_hash.keySet().iterator();iterator.hasNext();){
					String path=iterator.next();
					if(LonLat.PointInPolygon(grid.lonLat, name_LonLatList_hash.get(path))){
						writer.write(grid.toStringLonLatGeographicCode()+","+path+"\r\n");
						break;
					}
				}
			}
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
