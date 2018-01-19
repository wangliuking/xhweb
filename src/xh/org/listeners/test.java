package xh.org.listeners;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SimpleDateFormat timeF = new SimpleDateFormat("mm");
		timeF.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String time = timeF.format(new Date());
		System.out.println(ss());
		
		
		
	}
	
	public static boolean isInteger(String str) {  
        /*Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");*/  
		Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();  
        
  }
	
	public static String ss(){

		SimpleDateFormat timeF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timeF.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));		
		Date d=new Date();
		String dateStr = timeF.format(d.getTime()-10*60*1000);
		String[] time=dateStr.split(" ")[1].split(":");
		String rtime=dateStr.split(" ")[0];
		int a=Integer.parseInt(time[1]);
		int c=a%5;
		int x=a-c;
		if(x>=10){
			rtime+=" "+time[0]+":"+x+":00";
		}else{
			rtime+=" "+time[0]+":0"+x+":00";
		}
		return rtime;
	}
	

}
