package dao;

public class Station {
	public String id,highway_ID;
	public LonLat lonLat;
	public double stake;
	public Station(String id,String highway_ID,String lon,String lat,String stake) {
		this.id=id;
		this.highway_ID=highway_ID;
		this.lonLat=new LonLat(lon,lat);
		this.stake=Double.valueOf(stake);
	}
}
