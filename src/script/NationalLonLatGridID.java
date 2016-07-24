package script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import util.Config;
import util.FileUtil;

public class NationalLonLatGridID {
	public static void main(String[] args){
		List<String> paths=FileUtil.getAbsolutePathFromDIR(Config.NATIONAL_BOUNDARY_DIR);
		try {
			for(String path : paths){
				BufferedReader reader=new BufferedReader(new FileReader(path));
				
				reader.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
