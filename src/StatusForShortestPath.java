import java.util.ArrayList;
import java.util.List;

public class StatusForShortestPath {
	public RoadLink roadLink;
	public double cost;
	public List<String> path;
	public StatusForShortestPath(RoadLink r) {
		// TODO Auto-generated constructor stub
		roadLink=r;
		cost=0;
		path=new ArrayList<String>(200);
	}
	public StatusForShortestPath(RoadLink r,double c) {
		// TODO Auto-generated constructor stub
		roadLink=r;
		cost=c;
		//path=p;
	}
	public StatusForShortestPath(RoadLink r,double c,List<String> p) {
		// TODO Auto-generated constructor stub
		roadLink=r;
		cost=c;
		path=p;
	}
}
