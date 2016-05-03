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
	public static String ROADLINK_OUTPUT="D:\\zjt\\路网拓扑zjt\\roadlink"; //路链的输出情况
	public static String OLD_TOPOLOGY="D:\\zjt\\路网拓扑0629.csv";  //常兵做的老拓扑，但是其中的测速仪数据是能用的
	public static int HIGHWAY_COUNT=14000;    //从之前的数据了解到，高速公路路链总共是14000条左右
	public static double EARTH_RADIUS=6371393;    //地球半径 米
	public static double TOLERANCE=500; //米。预设的误差值，用于计算收费站、测速仪经纬度与路链、路段经纬度序列之间的距离，小于此值说明在一个路上
	public static String START_ROADLINK_ID="16777580";//预设的起始路链号
	/////////////////////////////////////////////////
	public static String ROADLINK_MIDDLE_FILE="D:\\zjt\\路网拓扑zjt\\路网拓扑结果\\路链拓扑program.csv";  //生成的中间文件
	public static String STATION_SHORTEST_PATH_LENGTH="D:\\zjt\\路网拓扑zjt\\路网拓扑结果\\收费站最短距离"+DF.format(new Date())+".csv";  //生成的中间文件
	public static String SHORTEST_PATH_DIR;
	////////////////////////////////////////////////////////////
	public static String CYSNEARESTROADLINK="D:\\zjt\\路网拓扑zjt\\路网拓扑结果\\测速仪与路链相距最短距离.csv";  //给金玉辉算的
}
