package dao;


public class GeographicGrid {
	public LonLat lonLat;
	public int lonCode,latCode,x1,y1,x2,y2;
	public String geographicCode;
	
	/**
	 * 无参构造函数
	 */
	public GeographicGrid(){
		
	}
	
	/**
	 * 传入GCJ经纬度的构造函数
	 */
	public GeographicGrid(LonLat lonLatGCJ){
		lonLat=lonLatGCJ;
		double lonCode_Double=lonLat.latitude-60;
		double latCode_Double=lonLat.latitude*1.5;
		lonCode=(int)lonCode_Double;
		latCode=(int)latCode_Double;
		double x1_Double=(lonCode_Double-lonCode)*8;
		double y1_Double=(latCode_Double-latCode)*8;
		x1=(int)x1_Double;
		y1=(int)y1_Double;
		x2=(int)((x1_Double-x1)*8);
		y2=(int)((y1_Double-y1)*8);
		StringBuilder stringBuilder=new StringBuilder(latCode);
		stringBuilder.append(lonCode).append(y1).append(x1).append(y2).append(x2);
		geographicCode=stringBuilder.toString();
	}
	
	
}
