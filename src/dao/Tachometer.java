package dao;

public class Tachometer {
	public String id,highway_ID;
	public LonLat lonLat;
	public double stake=0;
	public Tachometer(String id,String highway_ID,String lon,String lat){
		this.id=id;
		this.highway_ID=highway_ID;
		this.lonLat=new LonLat(lon,lat);
	}
}
