import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.spi.ior.ObjectKey;

public class StatusForShortestPath {
	public RoadLink roadLink;
	public double cost;
	public List<String> path;
	public StatusForShortestPath(){
		roadLink=null;
		cost=0;
		path=new ArrayList<String>(200);
	}
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
		path=new ArrayList<String>(200);
	}
	public StatusForShortestPath(RoadLink r,double c,List<String> p) {
		// TODO Auto-generated constructor stub
		roadLink=r;
		cost=c;
		path=new ArrayList<String>(p);
	}
	public void append_Status_Path_Cost(StatusForShortestPath obj){
		if(path.get(path.size()-1).equals(obj.path.get(0))){
			path.remove(path.size()-1);
		}
		path.addAll(obj.path);
		roadLink=obj.roadLink;
		cost+=obj.cost;
	}
	public void add_RoadLink(RoadLink obj){
		roadLink=obj;
		cost+=obj.length;
		path.add(obj.ID);
	}
}
