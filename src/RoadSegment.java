import java.util.List;

public class RoadSegment {
	String id;
	List<String> next_ID;
	double length;
	LonLat lonLat_start;
	public RoadSegment(String roadSegmentID) {
		id=roadSegmentID;
	}
	@Override
	public String toString() {
		StringBuilder next_ID_str=new StringBuilder("");
		StringBuilder return_str=new StringBuilder("");
		for(String str : next_ID)
			next_ID_str.append(str);
		return_str.append(id).append(next_ID_str.toString()).append(String.valueOf(length))
			.append(lonLat_start.toString());
		return return_str.toString();
	}
	
}
