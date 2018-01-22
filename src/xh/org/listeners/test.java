package xh.org.listeners;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*SimpleDateFormat timeF = new SimpleDateFormat("mm");
		timeF.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String time = timeF.format(new Date());*/
		
		String[] s = new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9","0", "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z","a","b","c","d","e","f","g","h","i","j"
				,"k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		
		for(int i=0;i<50;i++){
			System.out.println(RandomWord(8));
		}
		
		
		
	}
	
	public static String RandomWord(int num) {
		String[] beforeShuffle = new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9","0", "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z","a","b","c","d","e","f","g","h","i","j"
				,"k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		List list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		Random  random=new Random();
		for(int i=0;i<num;i++){
			 int s = random.nextInt(list.size()-1);
			 sb.append(list.get(s));
		}
		String afterShuffle = sb.toString();
		
	    
		
		
		/*for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(1, 9);*/
		return afterShuffle;
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
