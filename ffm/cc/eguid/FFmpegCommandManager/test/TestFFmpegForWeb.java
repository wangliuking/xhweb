package cc.eguid.FFmpegCommandManager.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import xh.org.listeners.SingLoginListener;
import cc.eguid.FFmpegCommandManager.FFmpegManager;
import cc.eguid.FFmpegCommandManager.FFmpegManagerImpl;
import cc.eguid.FFmpegCommandManager.entity.TaskEntity;
/**
 * 测试
 * @author eguid
 * @since jdk1.7
 * @version 2017年10月13日
 */
public class TestFFmpegForWeb {
	private static Map<String,String> hashMap = new HashMap<String,String>();
	private static FFmpegManager manager = new FFmpegManagerImpl();
	public static List<Map<String,String>> userByBsIdForWebSocketList = new LinkedList<Map<String,String>>();	
	public static void main(String[] args) throws InterruptedException {
		//test1();
		//test2();
		//test3();
		//test4();
	}
	
	//根据userId删除userByBsIdForWebSocketList中的连接
	public static void deleleByUserId(String userId,String bsId){
		for(int i=0;i<userByBsIdForWebSocketList.size();i++){
			Map<String,String> map = userByBsIdForWebSocketList.get(i);
			if(userId.equals(map.get("userId")) && bsId.equals(map.get("bsId"))){
				userByBsIdForWebSocketList.remove(i);
				continue;
			}
		}
	}
	
	/**
	 * 命令组装器测试
	 * @throws InterruptedException
	 */
	public static void test1(Map<String,Object> cameraMap) throws InterruptedException{
		//获取此websocket的userId，同此基站进行绑定,放入全局list
		String userId = "";
		Iterator iter = SingLoginListener.getLogUserMap().entrySet().iterator();
		 while (iter.hasNext()) {
			 Map.Entry entry = (Map.Entry) iter.next();  
             Object key = entry.getKey();  
             Object val = entry.getValue();
             userId = val+"";
		 }
		 Map<String,String> userByBsIdForWebSocketMap = new HashMap<String,String>();
		 userByBsIdForWebSocketMap.put("userId", userId);
		 userByBsIdForWebSocketMap.put("bsId", cameraMap.get("bsId")+"");
		 userByBsIdForWebSocketList.add(userByBsIdForWebSocketMap);
		 //end
		Map<String,String> map = new HashMap<String,String>();
		String input = "rtsp://" + cameraMap.get("loginName") + ":" + cameraMap.get("password") + "@" + cameraMap.get("deviceIP") + ":554/h264/ch" + cameraMap.get("ch") +"/main/av_stream";		
		int bsId = (Integer) cameraMap.get("bsId");
		String tempWindow = cameraMap.get("window").toString();
		String[] strs = tempWindow.split("[.]");
		String window = strs[0]+"x360";
		System.out.println("window大小为："+window);
		String cameraId = "camera"+bsId;
		map.put("appName", cameraId);
		map.put("input", input);
		map.put("output", "rtmp://localhost:7790/oflaDemo/");
		map.put("codec", "h264");
		map.put("fmt", "flv");
		map.put("fps", "25");
		map.put("rs", window);
		map.put("twoPart", "1");
		
		//执行任务前查询所有推流进程，如果存在则不需要进行推流
		Collection<TaskEntity> infoList = manager.queryAll();
		System.out.println("infoList为："+infoList);
		int status = 0;
		for(TaskEntity task : infoList){
			if(cameraId.equals(task.getId())){
				//infoList存在此推流，故不需要进行推送，将标志位置为1
				status = 1;
			}
		}
		//标志位0-需进行推流 标志位1-不用进行推流
		if(status==0){
			// 执行任务，id就是appName，如果执行失败返回为null
			String str = new String();
			str = manager.start(map);
			hashMap.put(str, str);
		}
		System.out.println("userByBsIdForWebSocketList : "+userByBsIdForWebSocketList);
	}
	/**
	 * 停止视频流进程
	 */
	public static void stop(String bsId) throws InterruptedException{
		String cameraId = hashMap.get("camera"+bsId);
		// 停止id对应的任务
		manager.stop(cameraId);
	}
	/**
	 * 默认方式，rtsp->rtmp转流单个命令测试
	 * @throws InterruptedException
	 */
	public static void test2() throws InterruptedException{
		FFmpegManager manager = new FFmpegManagerImpl();
		// -rtsp_transport tcp 
		//测试多个任何同时执行和停止情况
		//默认方式发布任务
		manager.start("tomcat", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat");
		
		Thread.sleep(30000);
		// 停止全部任务
		manager.stopAll();
	}
	/**
	 * 完整ffmpeg路径测试
	 * @throws InterruptedException
	 */
	public static void test4() throws InterruptedException{
		FFmpegManager manager = new FFmpegManagerImpl();
		// -rtsp_transport tcp 
		//测试多个任何同时执行和停止情况
		//默认方式发布任务
		manager.start("tomcat", "D:/TestWorkspaces/FFmpegCommandHandler/src/cc/eguid/FFmpegCommandManager/ffmpeg/ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat",true);
		
		Thread.sleep(30000);
		// 停止全部任务
		manager.stopAll();
	}
	
	/**
	 * rtsp-rtmp转流多任务测试
	 * @throws InterruptedException
	 */
	public static void test3() throws InterruptedException{
		FFmpegManager manager = new FFmpegManagerImpl();
		// -rtsp_transport tcp 
		//测试多个任何同时执行和停止情况
		//false表示使用配置文件中的ffmpeg路径，true表示本条命令已经包含ffmpeg所在的完整路径
		manager.start("tomcat", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat",false);
		manager.start("tomcat1", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat1",false);
		manager.start("tomcat2", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat2",false);
		manager.start("tomcat3", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat3",false);
		manager.start("tomcat4", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat4",false);
		manager.start("tomcat5", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat5",false);
		manager.start("tomcat6", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat6",false);
		manager.start("tomcat7", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat7",false);
		manager.start("tomcat8", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat8",false);
		manager.start("tomcat9", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat9",false);
		
		Thread.sleep(30000);
		// 停止全部任务
		manager.stopAll();
	}
	
}
