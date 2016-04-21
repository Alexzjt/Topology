import java.text.SimpleDateFormat;
import java.util.Date;

public class Config {
	public static SimpleDateFormat DF=new SimpleDateFormat("yyyyMMddHHmmss");
	public static int SPEED_UPBOUND=250;   //最高限速约定
	public static int SPEED_LOWBOUND=0;    //最低限速约定
	public static String MAP_MIF="D:\\zjt\\重庆地图\\Rchongqing.mif";	   //.mif文件的存储路径
	public static String MAP_MID="D:\\zjt\\重庆地图\\Rchongqing.txt";	   //.mid文件的存储路径
	public static String STATION="D:\\zjt\\station.csv";	   //收费站文件的存储路径
	public static String TACHYMETER="D:\\zjt\\CSY.txt";	   //测速仪文件的存储路径
	public static String ROADLINK_OUTPUT="D:\\zjt\\路网拓扑zjt\\roadlink"+DF.format(new Date())+".csv"; //路链的输出情况
	public static int HIGHWAY_COUNT=14000;    //从之前的数据了解到，高速公路路链总共是14000条左右
	public static double EARTH_RADIUS=6371393;    //地球半径 米
	public static double TOLERANCE=500; //米。预设的误差值，用于计算收费站、测速仪经纬度与路链、路段经纬度序列之间的距离，小于此值说明在一个路上
	public static String START_ROADLINK_ID="16777580";  //预设的起始路链号
}
