import sun.security.action.GetBooleanAction;

public class LonLat {
	public double longitude,latitude;
	LonLat(String str1,String str2){
		longitude=Double.valueOf(str1);
		latitude=Double.valueOf(str2);
	}
	LonLat(String str){
		String[] array=str.split(" ");
		longitude=Double.valueOf(array[0]);
		latitude=Double.valueOf(array[1]);
	}
	private static double rad(double d)
	{
	   return d * Math.PI / 180.0;
	}
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
	{
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);

	   double s = 2.0 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2.0),2.0) +
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2.0),2.0)));
	   s = s * Config.EARTH_RADIUS;
	   s = Math.round(s * 10000.0) / 10000.0;
	   return s;
	}
	public static double GetDistance(LonLat obj1,LonLat obj2){
		return GetDistance(obj1.latitude,obj1.latitude,obj2.latitude,obj2.longitude);
	}
	@Override
	public String toString() {
		StringBuilder return_str=new StringBuilder(String.valueOf(longitude));
		return_str.append(",").append(String.valueOf(latitude));
		return return_str.toString();
	}
}
