package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.spi.orb.StringPair;

public class FileUtil {
	public static List<String> getAbsolutePathFromDIR(String dir_path){
		File dir=new File(dir_path);
		if(!dir.exists())
			return null;
		File[] files=dir.listFiles();
		List<String> paths=new ArrayList<String>();
		for(File file : files){
			if(file.isDirectory()){
				paths.addAll(getAbsolutePathFromDIR(file.getAbsolutePath()));
			}
			else{
				paths.add(file.getAbsolutePath());
			}
		}
		return paths;
	}
	
	
	/**
	 * ≤‚ ‘”√
	 * @param args
	 */
	public static void main(String[] args){
		for(String str : getAbsolutePathFromDIR("C:\\Users\\lirun\\Desktop\\svn")){
			System.out.println(str);
		}
	}
}
