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
	public static String SHORTEST_PATH_DIR="D:\\zjt\\ODѰ�����\\";
	public static int water=0;
	////////////////////////////////////////////////////////////
	public static String CYSNEARESTROADLINK="D:\\zjt\\·������zjt\\·�����˽��\\��������·�������̾���.csv";  //����������
	////////////////////////////////////////////////////////////
	public static String HIGHWAY_START_END="E:\\�ٶ���ͬ����\\����ʦ��ߵ���\\·�����˻���201604\\���ٱ��������ֹ·��.csv";
	public static String ROADLINK_HIGHWAY_ID="D:\\zjt\\·������zjt\\·�����˽��\\·������"+DF.format(new Date())+".csv";
	public static String HIGHWAY_ID_LONLAT="D:\\zjt\\·������zjt\\·�����˽��\\���ٹ�·ID���侭γ��"+DF.format(new Date())+".csv";
	///////////////////////////////////////////////////////////////
	public static String SUB_OPTIMAL_PATH_DIR="D:\\zjt\\OD���·���ʹζ�·��\\";
	public static String SHORTEST_PATH_LENGTH_IN="D:\\zjt\\·������zjt\\·�����˽��\\�շ�վ��̾���20160513174938.csv";
	public static String ROADLINK_MIDDLE_FILE_SUB_OPT="D:\\zjt\\·������zjt\\·�����˽��\\·������20160510112502.csv";
	public static double TOLERANCE_MULTIPLE=1.2;   //����·����ֵ����Ϊ  ���·�����Դ����ӡ�
	public static double CHONGQING_G5001=180;   //�����ƳǸ��ٵ�·�����ȡ�
	public static double TOLERANCE_SIMILARITY=0.1;   //����·�����������С�ڴ�ֵ������Ϊ���ơ�
	public static int COUNT_SUB_OPT_PATH=10;         //�������·����������������Ŀ��ֻ����̵�ǰxxx��
	public static String ROADLINK_EACH_DIR="G:\\zjt\\OD·����·������(ֻ����·��ID)\\";
	////////////////////////////////////////////////////////////////////////
	public static int NUMBERS_OF_ROADLINK=13771;
	public static String OD_DIJKSTRA_DIR="D:\\zjt\\OD���·��\\";
}
