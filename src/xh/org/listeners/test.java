package xh.org.listeners;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import xh.func.plugin.FunUtil;
import xh.mybatis.service.DispatchStatusService;
import xh.mybatis.service.SqlServerService;

public class test {

	
	 public static void main(String[] args) {   
        /* ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                 new ArrayBlockingQueue<Runnable>(5));
 
         for(int i=0;i<150;i++){
             MyTask myTask = new MyTask(i);
             executor.execute(myTask);
             System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
             executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
         }
         executor.shutdown();*/
		 String d="119指挥中心，空调监测，空调关机,报警。";
		 
		System.out.println(d.split("，")[2]);
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
class MyTask implements Runnable {
    private int taskNum;
 
    public MyTask(int num) {
        this.taskNum = num;
    }
 
    @Override
    public void run() {
        System.out.println("正在执行task "+taskNum);
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task "+taskNum+"执行完毕");
    }
}
