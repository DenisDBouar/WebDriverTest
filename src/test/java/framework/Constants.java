package framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants {
	
	public Properties mapObjectsPaths;
	public Properties mapOutputData;
	
	 public void setObjectsPaths(String path){
		 mapObjectsPaths = readProperty(path);
	 }
	
	 public void setOutputData(String path){
		 mapOutputData = readProperty(path);
	 }
	 
	 public Properties readProperty(String path){
		 Properties prop = new Properties();
			InputStream input = null;
			try {
				input = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\" + path);
				prop.load(input);
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return prop;
		}
}
