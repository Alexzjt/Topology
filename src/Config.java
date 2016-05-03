import java.text.SimpleDateFormat;
import java.util.Date;

public class Config {
	public static SimpleDateFormat DF=new SimpleDateFormat("yyyyMMddHHmmss");
	public static int SPEED_UPBOUND=250;   //�������Լ��
	public static int SPEED_LOWBOUND=0;    //�������Լ��
	public static String MAP_MIF="D:\\zjt\\�����ͼ\\Rchongqing.mif";	   //.mif�ļ��Ĵ洢·��
	public static String MAP_MID="D:\\zjt\\�����ͼ\\Rchongqing.txt";	   //.mid�ļ��Ĵ洢·��
	public static String STATION="D:\\zjt\\station.csv";	   //�շ�վ�ļ��Ĵ洢·��
	public static String TACHYMETER="D:\\zjt\\CSY.txt";	   //�������ļ��Ĵ洢·��
	public static String ROADLINK_OUTPUT="D:\\zjt\\·������zjt\\roadlink"; //·����������
	public static String OLD_TOPOLOGY="D:\\zjt\\·������0629.csv";  //�������������ˣ��������еĲ��������������õ�
	public static int HIGHWAY_COUNT=14000;    //��֮ǰ�������˽⵽�����ٹ�··���ܹ���14000������
	public static double EARTH_RADIUS=6371393;    //����뾶 ��
	public static double TOLERANCE=500; //�ס�Ԥ������ֵ�����ڼ����շ�վ�������Ǿ�γ����·����·�ξ�γ������֮��ľ��룬С�ڴ�ֵ˵����һ��·��
	public static String START_ROADLINK_ID="16777580";//Ԥ�����ʼ·����
	/////////////////////////////////////////////////
	public static String ROADLINK_MIDDLE_FILE="D:\\zjt\\·������zjt\\·�����˽��\\·������program.csv";  //���ɵ��м��ļ�
	public static String STATION_SHORTEST_PATH_LENGTH="D:\\zjt\\·������zjt\\·�����˽��\\�շ�վ��̾���"+DF.format(new Date())+".csv";  //���ɵ��м��ļ�
	public static String SHORTEST_PATH_DIR;
	////////////////////////////////////////////////////////////
	public static String CYSNEARESTROADLINK="D:\\zjt\\·������zjt\\·�����˽��\\��������·�������̾���.csv";  //����������
}
