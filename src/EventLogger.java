import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.OutputStreamWriter;

public class EventLogger {
	
	private static EventLogger single_instance = null;
	private File file;
	private FileOutputStream fos;
	BufferedWriter bw;
	
	private EventLogger() {
		
		try {
			file = new File("Event Log.txt");
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
		} catch (FileNotFoundException e) {
			
			System.out.println("Writing Error : " + e.getLocalizedMessage());
		}
		
	}
	
	 public static EventLogger getEventLogger() 
	 {
	        if (single_instance == null)
	            single_instance = new EventLogger();
	 
	        return single_instance;
    }
	
	public  void logEvent(String log)  {
		try {
			bw.write(log);
			bw.newLine();
		}catch(Exception e) {
			System.out.println("Writing Error : " + e.getLocalizedMessage());
		}
	
	}
	
	public void stopLogging() {
		try {
			bw.close();
		}catch(Exception e) {
			System.out.println("Writing Error : " + e.getLocalizedMessage());
		}
	}
}
