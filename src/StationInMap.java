import java.util.List;

public class StationInMap {
	String poi_ID,name,roadlink_ID;
	LonLat lonLat;
	public StationInMap() {
		// TODO Auto-generated constructor stub
	}
	public StationInMap(String poi_ID,String name) {
		// TODO Auto-generated constructor stub
		this.poi_ID=poi_ID;
		this.name=name;
	}
	@Override
	public String toString() {
		StringBuilder str=new StringBuilder(poi_ID);
		str.append(",").append(name).append(",").append(lonLat.toString());
		return str.toString();
	}
	public static String listAddSeparator(List<String> list){
		StringBuilder str=new StringBuilder();
		int length=list.size();
		for(int i=0;i<length-1;i++){
			str.append(list.get(i)).append("|");
		}
		str.append(list.get(length-1));
		return str.toString();
	}
}
