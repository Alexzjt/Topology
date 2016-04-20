import java.util.List;

public class RoadSegment {
	public String id;
	public List<String> next_ID;
	public double length;
	public LonLat lonLat_start;
	public RoadSegment(String roadSegmentID) {
		id=roadSegmentID;
	}
	@Override
	public String toString() {
		StringBuilder next_ID_str=new StringBuilder("");
		StringBuilder return_str=new StringBuilder("");
		for(int i=0;i<next_ID.size()-1;i++){
			next_ID_str.append(next_ID.get(i)).append("#");
		}
		next_ID_str.append(next_ID.get(next_ID.size()-1));
		return_str.append(id).append(",").append(next_ID_str.toString()).append(",")
		.append(String.valueOf(length)).append(",")
			.append(lonLat_start.toString());
		return return_str.toString();
	}
	
}
