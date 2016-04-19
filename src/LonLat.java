
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
	@Override
	public String toString() {
		return String.valueOf(longitude)+","+String.valueOf(latitude);
	}
	
}
