import java.util.ArrayList;
import java.util.List;

public class StatusForShortestPath {
	public RoadLinkForSP roadLink;
	public double cost;
	//public List<String> path;
	public StatusForShortestPath(RoadLinkForSP r) {
		// TODO Auto-generated constructor stub
		roadLink=r;
		cost=0;
		//path=new ArrayList<String>(100);
	}
	public StatusForShortestPath(RoadLinkForSP r,double c) {
		// TODO Auto-generated constructor stub
		roadLink=r;
		cost=c;
		//path=p;
	}
	public StatusForShortestPath(RoadLinkForSP r,double c,List<String> p) {
		// TODO Auto-generated constructor stub
		roadLink=r;
		cost=c;
		//path=p;
	}
}
