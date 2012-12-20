package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemOpenTime {
	/**
	* @param args
	*/
	public static void main(String[] args) throws Exception{
	   System.out.println(SystemOpenTime.readSystemStartTime());
	  
	}
	public static String readSystemStartTime() throws IOException, 
	    InterruptedException { 
	        Process process = Runtime.getRuntime().exec( 
	            "cmd /c net statistics workstation"); 
	        String startUpTime = ""; 
	        BufferedReader bufferedReader = new BufferedReader( 
	            new InputStreamReader(process.getInputStream())); 
	        int i = 0; 
	        String timeWith = ""; 
	        while ((timeWith = bufferedReader.readLine()) != null) { 
	           if (i == 3) { 
	               System.out.println(timeWith); 
	               startUpTime = timeWith; 
	         } 
	        i++; 
	       } 
	        process.waitFor(); 
	        //it x = startUpTime.indexOf(ch)
	        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        try {
				Date d = sdf.parse(startUpTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			InetAddress addr = InetAddress.getLocalHost();			
			String IP = addr.getHostAddress().toString();
			
			System.out.println(IP);

	        
	        return startUpTime; 
	} 

}
