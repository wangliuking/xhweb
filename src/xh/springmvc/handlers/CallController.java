package xh.springmvc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.EastBsCallDataBean;
import xh.mybatis.bean.EastVpnCallBean;
import xh.mybatis.service.CallListServices;
import xh.mybatis.service.EastComService;
import xh.org.listeners.SingLoginListener;
@Controller
@RequestMapping(value="/call")
public class CallController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(CallController.class);
	private FlexJSON json=new FlexJSON();
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public void CallList(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String caller=request.getParameter("caller");
		String called=request.getParameter("called");
		String bsId=request.getParameter("bsId");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		
		Map<String,Object> usermap=SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
		String vpnId=usermap.get("vpnId").toString();
		
		/*log.info("user->"+usermap.get("user"));
		log.info("vpnId->"+usermap.get("vpnId"));*/
		
		String[] time1=starttime.split("-");
		String[] time2=endtime.split("-");
		int a=Integer.parseInt(time1[1]);
		int b=Integer.parseInt(time2[1]);
		Map<String, Object> map=new HashMap<String, Object>();
		
		if(a==b){
			map.put("dbname1","xhgmnet_calllist"+time1[1]);
			map.put("dbname2","");
		}else{
			map.put("dbname1","xhgmnet_calllist"+time1[1]);
			map.put("dbname2","xhgmnet_calllist"+time2[1]);
		}
		map.put("caller", caller);
		map.put("called", called);
		map.put("bsId", bsId);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("vpnId", vpnId);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", CallListServices.CallListCount(map));
		result.put("items", CallListServices.selectCallList(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 下载文件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String filePath=request.getParameter("filePath");
		String fileName=request.getParameter("fileName");
		String path = request.getSession().getServletContext().getRealPath(filePath);
		
		String downPath=path;
		log.info(downPath);
		 File file = new File(downPath);
		 if(!file.exists()){
			 this.success=false;
			 this.message="文件不存在";
		 }
		    //设置响应头和客户端保存文件名
		    response.setCharacterEncoding("utf-8");
		    response.setContentType("multipart/form-data");
		    response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		    //用于记录以完成的下载的数据量，单位是byte
		    long downloadedLength = 0l;
		    try {
		        //打开本地文件流
		        InputStream inputStream = new FileInputStream(downPath);
		        //激活下载操作
		        OutputStream os = response.getOutputStream();

		        //循环写入输出流
		        byte[] b = new byte[2048];
		        int length;
		        while ((length = inputStream.read(b)) > 0) {
		            os.write(b, 0, length);
		            downloadedLength += b.length;
		        }

		        // 这里主要关闭。
		        os.close();
		        inputStream.close();
		    } catch (Exception e){
		        throw e;
		    }
		    //存储记录
	}
	/**
	 * 播放录音
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/play", method = RequestMethod.GET)
	public void playFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String path = request.getSession().getServletContext().getRealPath("/Resources/audio");
		String fileName=request.getParameter("playName");
		String downPath=path+"/"+fileName;
		log.info(downPath);
		File file = new File(downPath);
		 
	}
	/**
	 * 通话统计分析
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/chart", method = RequestMethod.POST)
	public void chart(HttpServletRequest request,HttpServletResponse response) throws Exception{
		int bsId=Integer.parseInt(request.getParameter("bsId"));
		String type=request.getParameter("type");
		String time = request.getParameter("time");
		
		String[] date=time.split("-");
		String dbname="xhgmnet_calllist"+date[1];
		Map<String,Object> usermap=SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
		String vpnId=usermap.get("vpnId").toString();
	
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("dbname", dbname);
		map.put("time", time);
		map.put("type", type);
		map.put("bsId",bsId);
		map.put("vpnId",vpnId);
		
		List<Map<String, Object>> resultList=new  ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listParse=new  ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listParse2=new  ArrayList<Map<String,Object>>();
		resultList=CallListServices.callChartt(map);
		
		Map<String, Object> mapA=new HashMap<String, Object>();
		Map<String, Object> mapB=new HashMap<String, Object>();
		DecimalFormat df=new DecimalFormat("#.##"); 

		for (Map<String, Object> map2 : resultList) {
			mapA.put(map2.get("date").toString(), map2.get("usetime"));
			mapB.put(map2.get("date").toString(), map2.get("num"));
		}
		
		if(type.equals("day")){
			int days=FunUtil.getDaysOfMonth(time);
			
			for(int i=1;i<=days;i++){
				Map<String, Object> mapDay=new HashMap<String, Object>();
				Map<String, Object> mapDay2=new HashMap<String, Object>();
				String key="d-"+i;
				double a=mapA.get(key)==null?0:Double.parseDouble(mapA.get(key).toString())/60;
				mapDay.put("label", i);
				mapDay.put("time", df.format(a));
				listParse.add(mapDay);	
				
				mapDay2.put("label", i);
				mapDay2.put("num", mapB.get(key)==null?0:mapB.get(key));
				listParse2.add(mapDay2);					
			}		
		}
		if(type.equals("hour")){
			int hour=24;
			
			for(int i=0;i<hour;i++){
				Map<String, Object> mapHour=new HashMap<String, Object>();
				Map<String, Object> mapHour2=new HashMap<String, Object>();
				String key="h-"+i,key1="";
				/*if(i<10){
					key1="0"+i+":00";
					key="h-0"+i;
				}else{
					key1=i+":00";
					key="h-"+i;
				}*/
				if(i<10){
					key1="0"+i;
					key="h-0"+i;
				}else{
					key1=String.valueOf(i);
					key="h-"+i;
				}
				
				double a=mapA.get(key)==null?0:Double.parseDouble(mapA.get(key).toString())/60;
				 
				 
				mapHour.put("label", key1);
				mapHour.put("time",df.format(a));
				listParse.add(mapHour);	
				
				mapHour2.put("label", key1);
				mapHour2.put("num", mapB.get(key)==null?0:mapB.get(key));
				listParse2.add(mapHour2);	
			}		
		}
		
		
		HashMap result = new HashMap();
		result.put("totals", listParse.size());
		result.put("time", listParse);
		result.put("num", listParse2);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
  
	/**
	 * 今日通话统计分析
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/chart_call_hour_now", method = RequestMethod.POST)
	public void chart_call_hour_now(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		List<Map<String, Object>> resultList=new  ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listParse=new  ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listParse2=new  ArrayList<Map<String,Object>>();
		resultList=CallListServices.chart_call_hour_now();
		
		Map<String, Object> mapA=new HashMap<String, Object>();
		Map<String, Object> mapB=new HashMap<String, Object>();
		DecimalFormat df=new DecimalFormat("#.##"); 

		for (Map<String, Object> map2 : resultList) {
			mapA.put(map2.get("date").toString(), map2.get("usetime"));
			mapB.put(map2.get("date").toString(), map2.get("num"));
		}
		
		int hour=24;
		
		for(int i=0;i<hour;i++){
			Map<String, Object> mapHour=new HashMap<String, Object>();
			Map<String, Object> mapHour2=new HashMap<String, Object>();
			String key="h-"+i,key1="";
			if(i<10){
				key1="0"+i;
				key="h-0"+i;
			}else{
				key1=String.valueOf(i);
				key="h-"+i;
			}
			
			double a=mapA.get(key)==null?0:Double.parseDouble(mapA.get(key).toString())/60;			 			 
			mapHour.put("label", key1);
			mapHour.put("time",df.format(a));
			listParse.add(mapHour);	
			
			mapHour2.put("label", key1);
			mapHour2.put("num", mapB.get(key)==null?0:mapB.get(key));
			listParse2.add(mapHour2);	
		}
		
		
		HashMap result = new HashMap();
		result.put("totals", listParse.size());
		result.put("time", listParse);
		result.put("num", listParse2);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	
	

	@RequestMapping(value = "/chart_bs_call", method = RequestMethod.GET)
	public void chart_bs_call(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		List<EastBsCallDataBean> list=EastComService.chart_bs_call(map);
		
		HashMap result = new HashMap();
		result.put("totals", list.size());
		result.put("items", list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	@RequestMapping(value = "/chart_vpn_call", method = RequestMethod.GET)
	public void chart_vpn_call(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		List<EastVpnCallBean> list=EastComService.chart_vpn_call(map);
		
		HashMap result = new HashMap();
		result.put("totals", list.size());
		result.put("items", list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	@RequestMapping(value = "/chart_bs_level_area_call", method = RequestMethod.GET)
	public void chart_bs_level_area_call(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		List<EastBsCallDataBean> level_list=EastComService.chart_bs_level_call(map);
		List<EastBsCallDataBean> area_list=EastComService.chart_bs_area_call(map);
		
		HashMap result = new HashMap();
		result.put("totals", level_list.size());
		result.put("areaitems",area_list);
		result.put("levelitems",level_list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	@RequestMapping(value = "/chart_bs_zone_call", method = RequestMethod.GET)
	public void chart_bs_zone_call(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		List<EastBsCallDataBean> list=EastComService.chart_bs_zone_call(map);
		
		HashMap result = new HashMap();
		result.put("totals", list.size());
		result.put("items", list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	@RequestMapping(value = "/chart_bs_zone_top10_call", method = RequestMethod.GET)
	public void chart_bs_zone_top10_call(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		List<EastBsCallDataBean> list=EastComService.chart_bs_zone_top10_call(map);
		
		HashMap result = new HashMap();
		result.put("totals", list.size());
		result.put("items", list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}

	@RequestMapping(value = "/chart_bs_call_top10", method = RequestMethod.GET)
	public void chart_bs_call_top10(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		List<EastBsCallDataBean> list=EastComService.chart_bs_call_top10(map);
		
		HashMap result = new HashMap();
		result.put("totals", list.size());
		result.put("items", list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	@RequestMapping(value = "/chart_bs_userreg_top10", method = RequestMethod.GET)
	public void chart_bs_userreg_top10(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		List<EastBsCallDataBean> list=EastComService.chart_bs_userreg_top10(map);
		
		HashMap result = new HashMap();
		result.put("totals", list.size());
		result.put("items", list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	@RequestMapping(value = "/chart_bs_queue_top10", method = RequestMethod.GET)
	public void chart_bs_queue_top10(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		List<EastBsCallDataBean> list=EastComService.chart_bs_queue_top10(map);
		
		HashMap result = new HashMap();
		result.put("totals", list.size());
		result.put("items", list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	@RequestMapping(value = "/chart_vpn_call_top10", method = RequestMethod.GET)
	public void chart_vpn_call_top10(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		List<EastVpnCallBean> list=EastComService.chart_vpn_call_top10(map);
		
		HashMap result = new HashMap();
		result.put("totals", list.size());
		result.put("items", list);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
	
	
}
