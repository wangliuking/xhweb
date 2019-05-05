package xh.org.listeners;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;

import xh.func.plugin.DocConverter;
import xh.func.plugin.FunUtil;
import xh.org.socket.MotoTcpClient;
import xh.org.socket.TcpKeepAliveClient;

public class test {

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1); //凌晨1点
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date=calendar.getTime(); //第一次执行定时任务的时间
        
        SimpleDateFormat fTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   
     
     int ab=2003;
     System.out.print(fTime.format(date));
    
	}

	public synchronized static void handleList(List<String> data, int threadNum) {
		int length = data.size();
		int tl = length % threadNum == 0 ? length / threadNum : (length
				/ threadNum + 1);

		for (int i = 0; i < threadNum; i++) {
			int end = (i + 1) * tl;
			ListThread thread = new ListThread("线程[" + (i + 1) + "] ", data, i
					* tl, end > length ? length : end);
			thread.start();
		}
	}

	public static String DataTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");

		String now = df.format(new Date());
		int m = Integer.parseInt(now.split(" ")[1].split(":")[1]) % 5;
		Calendar c = Calendar.getInstance();
		int x = m + 20;
		// 获取20分钟以前的时间
		c.add(Calendar.MINUTE, -x);
		Date d = c.getTime();
		return df.format(d);
	}

	public static String RandomWord(int num) {
		String[] beforeShuffle = new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "0", "A", "B", "C", "D", "E", "F", "G", "H",
				"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
				"U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f",
				"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
				"s", "t", "u", "v", "w", "x", "y", "z" };
		List list = Arrays.asList(beforeShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < num; i++) {
			int s = random.nextInt(list.size() - 1);
			sb.append(list.get(s));
		}
		String afterShuffle = sb.toString();

		/*
		 * for (int i = 0; i < list.size(); i++) { sb.append(list.get(i)); }
		 * String afterShuffle = sb.toString(); String result =
		 * afterShuffle.substring(1, 9);
		 */
		return afterShuffle;
	}

	public static boolean isInteger(String str) {
		/* Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$"); */
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();

	}

	public static String ss() {

		SimpleDateFormat timeF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		timeF.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		Date d = new Date();
		String dateStr = timeF.format(d.getTime() - 10 * 60 * 1000);
		String[] time = dateStr.split(" ")[1].split(":");
		String rtime = dateStr.split(" ")[0];
		int a = Integer.parseInt(time[1]);
		int c = a % 5;
		int x = a - c;
		if (x >= 10) {
			rtime += " " + time[0] + ":" + x + ":00";
		} else {
			rtime += " " + time[0] + ":0" + x + ":00";
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
		System.out.println("正在执行task " + taskNum);
		try {
			Thread.currentThread().sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("task " + taskNum + "执行完毕");
	}
}
