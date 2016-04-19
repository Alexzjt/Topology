import java.util.List;

public class RoadLink {
	int speed_upbound,speed_lowbound,lane,direction,line;
	double length,stake_mark;
	boolean isRamp;
	char road_attribute;
	String ID,highway_ID,SnodeID,EnodeID;
	List<LonLat> lonlat_list;
	RoadLink(String line){
		String[] line_array=line.split("\",\"|\"");
		int[] speed=RoadLink.speed_judge(Integer.valueOf(line_array[5]));
		speed_lowbound=speed[0];
		speed_upbound=speed[1];
		lane=RoadLink.lane_num(line_array[26],line_array[27]);
		length=Double.valueOf(line_array[13]);
		ID=line_array[2];
		SnodeID=line_array[10];
		EnodeID=line_array[11];
		isRamp=RoadLink.judge_Ramp(line_array[4]);
		road_attribute=RoadLink.judge_Road_attribute(line_array[4]);
	}
	RoadLink(String[] line_array){
		int[] speed=RoadLink.speed_judge(Integer.valueOf(line_array[5]));
		speed_lowbound=speed[0];
		speed_upbound=speed[1];
		lane=RoadLink.lane_num(line_array[26],line_array[27]);
		length=Double.valueOf(line_array[13]);
		ID=line_array[2];
		SnodeID=line_array[10];
		EnodeID=line_array[11];
		isRamp=RoadLink.judge_Ramp(line_array[4]);
		road_attribute=RoadLink.judge_Road_attribute(line_array[4]);
	}
	public static boolean judge_Ramp(String kind){
		String[] kind_array=kind.split("");
		if(kind_array[3].equals("4")||kind_array[3].equals("5")||
			kind_array[3].equals("3")||kind_array[3].equals("7")||
			kind_array[3].equals("6")||kind_array[3].equals("a")||
			kind_array[3].equals("0")){
			return true;
		}
		if(kind.length()>5){
			if(kind_array[8].equals("4")||kind_array[8].equals("5")||
			kind_array[8].equals("3")||kind_array[8].equals("7")||
			kind_array[8].equals("6")||kind_array[8].equals("a")||
			kind_array[8].equals("0")){
				return true;
			}
		}
		if(kind.length()>10){
			if(kind_array[14].equals("4")||kind_array[14].equals("5")||
			kind_array[14].equals("3")||kind_array[14].equals("7")||
			kind_array[14].equals("6")||kind_array[14].equals("a")||
			kind_array[14].equals("0")){
				return true;
			}
		}
		return false;
	}
	public static char judge_Road_attribute(String kind){
		String[] kind_array=kind.split("");
		if(kind.length()<5){
			return RoadLink.judge_Road_char(kind_array[3]);
		}
		else if(kind.length()>=5&&kind.length()<10){
			return RoadLink.judge_Road_char(kind_array[3],kind_array[8]);
		}
		else if(kind.length()>=10&&kind.length()<15){
			return RoadLink.judge_Road_char(kind_array[3],kind_array[8],kind_array[14]);
		}
		return 'G';
	}
	public static char judge_Road_char(String str){
		if(str.equals("3")||str.equals("5")||str.equals("4"))
			return 'J';
		else if(str.equals("6"))
			return 'P';
		else if(str.equals("7"))
			return 'F';
		else if(str.equals("f"))
			return 'T';
		return 'G';
	}
	public static char judge_Road_char(String str1,String str2){
		char ch1=RoadLink.judge_Road_char(str1),ch2=RoadLink.judge_Road_char(str2);
		if(ch1!='G'&&ch1!='T')
			return ch1;
		if(ch2!='G'&&ch2!='T')
			return ch2;
		if(ch1=='T'||ch2=='T')
			return 'T';
		return 'G';
	}
	public static char judge_Road_char(String str1,String str2,String str3){
		char ch1=RoadLink.judge_Road_char(str1),ch2=RoadLink.judge_Road_char(str2);
		char ch3=RoadLink.judge_Road_char(str3);
		if(ch1!='G'&&ch1!='T')
			return ch1;
		if(ch2!='G'&&ch2!='T')
			return ch2;
		if(ch3!='G'&&ch3!='T')
			return ch3;
		if(ch1=='T'||ch2=='T'||ch3=='T')
			return 'T';
		return 'G';
	}
	public static int lane_num(String s2e,String e2s){
		if(s2e.equals(""))
			return Integer.valueOf(e2s);
		else if(e2s.equals(""))
			return Integer.valueOf(s2e);
		else
			return 0;
	}
	public static int[] speed_judge(int speedClass){
		int[] speed=new int[2];
		switch(speedClass){
			case 1:
				speed[0]=130;
				speed[1]=Config.SPEED_UPBOUND;
				break;
			case 2:
				speed[0]=100;
				speed[1]=130;
				break;
			case 3:
				speed[0]=90;
				speed[1]=100;
				break;
			case 4:
				speed[0]=70;
				speed[1]=90;
				break;
			case 5:
				speed[0]=50;
				speed[1]=70;
				break;
			case 6:
				speed[0]=30;
				speed[1]=50;
				break;
			case 7:
				speed[0]=11;
				speed[1]=30;
				break;
			case 8:
				speed[0]=Config.SPEED_LOWBOUND;
				speed[1]=11;
				break;
		}
		return speed;
	}
}
