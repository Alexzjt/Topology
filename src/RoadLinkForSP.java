import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoadLinkForSP {
	String ID,station;
	List<String> next_ID;
	double length;
	boolean visit=false;
	public RoadLinkForSP(String[] line_array) {
		// TODO Auto-generated constructor stub
		//�����Ϊ�м��ļ�RoadLink��Ƶ����toString������Ƶ��溯��
		ID=line_array[0];
		next_ID=line_array[1].equals("")?null:Arrays.asList(line_array[1].split("#"));
		length=Double.valueOf(line_array[3]);
		station=line_array[15].equals("null")?null:line_array[15];
	}
}
