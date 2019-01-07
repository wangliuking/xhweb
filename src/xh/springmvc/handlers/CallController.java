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

import javax.management.StringValueExp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.bean.EastBsCallDataBean;
import xh.mybatis.bean.EastMscDayBean;
import xh.mybatis.bean.EastVpnCallBean;
import xh.mybatis.service.BsStatusService;
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
		if(bsId!=null && bsId!=""){
			int a=Integer.parseInt(bsId);
			if(a>200){
				bsId=String.valueOf((a+1000));
			}
			
		}
		
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
	
	@RequestMapping(value = "/chart_msc_call", method = RequestMethod.GET)
	public void chart_msc_call(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		String endtime=request.getParameter("endtime");
		int type=Integer.parseInt(request.getParameter("type"));
		Map<String,Object> map=new HashMap<String, Object>();
		if(type==4){
			String a=time.split(" ")[1];
			if(a.length()==1){
				time=time.split(" ")[0]+" 0"+time.split(" ")[1];
			}
		}
		map.put("time", time);
		map.put("type", type);
		map.put("endtime", endtime);
		EastMscDayBean bean =EastComService.chart_month_msc(map);
		
		HashMap result = new HashMap();
		result.put("totals", bean==null?0:1);
		result.put("items", bean);
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
		String endtime=request.getParameter("endtime");
		int type=Integer.parseInt(request.getParameter("type"));
		Map<String,Object> map=new HashMap<String, Object>();
		if(type==4){
			String a=time.split(" ")[1];
			if(a.length()==1){
				time=time.split(" ")[0]+" 0"+time.split(" ")[1];
			}
		}
		map.put("time", time);
		map.put("type", type);
		map.put("endtime", endtime);
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
		String endtime=request.getParameter("endtime");
		int type=Integer.parseInt(request.getParameter("type"));
		Map<String,Object> map=new HashMap<String, Object>();
		if(type==4){
			String a=time.split(" ")[1];
			if(a.length()==1){
				time=time.split(" ")[0]+" 0"+time.split(" ")[1];
			}
		}
		map.put("time", time);
		map.put("type", type);
		map.put("endtime", endtime);
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
		String endtime=request.getParameter("endtime");
		int type=Integer.parseInt(request.getParameter("type"));
		Map<String,Object> map=new HashMap<String, Object>();
		if(type==4){
			String a=time.split(" ")[1];
			if(a.length()==1){
				time=time.split(" ")[0]+" 0"+time.split(" ")[1];
			}
		}
		map.put("time", time);
		map.put("type", type);
		map.put("endtime", endtime);
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
		String endtime=request.getParameter("endtime");
		int type=Integer.parseInt(request.getParameter("type"));
		Map<String,Object> map=new HashMap<String, Object>();
		if(type==4){
			String a=time.split(" ")[1];
			if(a.length()==1){
				time=time.split(" ")[0]+" 0"+time.split(" ")[1];
			}
		}
		map.put("time", time);
		map.put("type", type);
		map.put("endtime", endtime);
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
		String endtime=request.getParameter("endtime");
		int type=Integer.parseInt(request.getParameter("type"));
		Map<String,Object> map=new HashMap<String, Object>();
		if(type==4){
			String a=time.split(" ")[1];
			if(a.length()==1){
				time=time.split(" ")[0]+" 0"+time.split(" ")[1];
			}
		}
		map.put("time", time);
		map.put("type", type);
		map.put("endtime", endtime);
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
		String endtime=request.getParameter("endtime");
		int type=Integer.parseInt(request.getParameter("type"));
		Map<String,Object> map=new HashMap<String, Object>();
		if(type==4){
			String a=time.split(" ")[1];
			if(a.length()==1){
				time=time.split(" ")[0]+" 0"+time.split(" ")[1];
			}
		}
		map.put("time", time);
		map.put("type", type);
		map.put("endtime", endtime);
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
		String endtime=request.getParameter("endtime");
		int type=Integer.parseInt(request.getParameter("type"));
		Map<String,Object> map=new HashMap<String, Object>();
		if(type==4){
			String a=time.split(" ")[1];
			if(a.length()==1){
				time=time.split(" ")[0]+" 0"+time.split(" ")[1];
			}
		}
		map.put("time", time);
		map.put("type", type);
		map.put("endtime", endtime);
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
		String endtime=request.getParameter("endtime");
		int type=Integer.parseInt(request.getParameter("type"));
		Map<String,Object> map=new HashMap<String, Object>();
		if(type==4){
			String a=time.split(" ")[1];
			if(a.length()==1){
				time=time.split(" ")[0]+" 0"+time.split(" ")[1];
			}
		}
		map.put("time", time);
		map.put("type", type);
		map.put("endtime", endtime);
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
		String endtime=request.getParameter("endtime");
		int type=Integer.parseInt(request.getParameter("type"));
		Map<String,Object> map=new HashMap<String, Object>();
		if(type==4){
			String a=time.split(" ")[1];
			if(a.length()==1){
				time=time.split(" ")[0]+" 0"+time.split(" ")[1];
			}
		}
		map.put("time", time);
		map.put("type", type);
		map.put("endtime", endtime);
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
	@RequestMapping(value = "/excel_call", method = RequestMethod.GET)
	public void excel_call(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		String endtime=request.getParameter("endtime");
		int type=Integer.parseInt(request.getParameter("type"));
		Map<String,Object> map=new HashMap<String, Object>();
		if(type==4){
			String a=time.split(" ")[1];
			if(a.length()==1){
				time=time.split(" ")[0]+" 0"+time.split(" ")[1];
			}
		}
		map.put("time", time);
		map.put("type", type);
		map.put("endtime", endtime);
		try {
			String saveDir = request.getSession().getServletContext().getRealPath("/upload/call");
			String pathname = saveDir + "/话务统计-"+time+".xls";
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);			
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(WritableFont.createFont("微软雅黑"), 15, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			
			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES,13,
								WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
								Colour.BLACK);
			
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat.setBorder(Border.ALL, BorderLineStyle.THIN,
					Colour.DARK_GREEN);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.LIGHT_GREEN);// 背景色
			fontFormat_h.setWrap(true);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体
			WritableCellFormat fontFormat_Content = new WritableCellFormat(font_Content);
			// 设置其他样式
			fontFormat_Content.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行
		
			
			//总计内容字体格式
			WritableFont total_font = new WritableFont(WritableFont.COURIER,
					13, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.RED);
			// 应用字体
			WritableCellFormat total_fontFormat = new WritableCellFormat(total_font);
			total_fontFormat.setAlignment(Alignment.CENTRE);// 水平对齐
			total_fontFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			total_fontFormat.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			total_fontFormat.setBackground(Colour.WHITE);// 背景色
			total_fontFormat.setWrap(true);// 自动换行
			
			
			
			
			
			

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("0.00%"); // 设置数字格式
			jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); // 设置表单格式
			wcfN.setAlignment(Alignment.CENTRE);// 水平对齐
			wcfN.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcfN.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcfN.setBackground(Colour.WHITE);// 背景色
			wcfN.setFont(font_Content);
			wcfN.setWrap(true);// 自动换行
			

			WritableSheet sheet1 = book.createSheet("交换中心话务统计", 0);
			WritableSheet sheet2 = book.createSheet("基站话务统计", 1);
			WritableSheet sheet3 = book.createSheet("虚拟专网话务统计", 2);
			WritableSheet sheet4 = book.createSheet("按级别区域分", 3);
			WritableSheet sheet5 = book.createSheet("按行政区域分", 4);
			WritableSheet sheet6 = book.createSheet("行政区域话务TOP10", 5);
			WritableSheet sheet7 = book.createSheet("各数据前十", 6);
			WritableSheet sheet8 = book.createSheet("虚拟专网用户数据前十", 7);
			excel_msc_call(map,sheet1,fontFormat,fontFormat_h,fontFormat_Content,wcfN);
			excel_bs_call(map,sheet2,fontFormat,fontFormat_h,fontFormat_Content,wcfN);
			excel_vpn_call(map,sheet3,fontFormat,fontFormat_h,fontFormat_Content,wcfN);
			excel_bs_level_area_call(map,sheet4,fontFormat,fontFormat_h,fontFormat_Content,wcfN);
			excel_bs_zone_call(map,sheet5,fontFormat,fontFormat_h,fontFormat_Content,wcfN);
			excel_bs_zone_top10_call(map,sheet6,fontFormat,fontFormat_h,fontFormat_Content,wcfN);
			excel_bs_top10_call(map,sheet7,fontFormat,fontFormat_h,fontFormat_Content,wcfN);
			excel_vpn_top10_call(map,sheet8,fontFormat,fontFormat_h,fontFormat_Content,wcfN);
		
			
			

			book.write();
			book.close();
			log.info(time+"-基站话务统计");
			/*DownExcelFile(response, pathname);*/
			 this.success=true;
			 HashMap<String, Object> result = new HashMap<String, Object>();
			 result.put("success", success);
			 result.put("pathName", pathname);
			 response.setContentType("application/json;charset=utf-8"); 
			 String jsonstr = json.Encode(result); 
			 response.getWriter().write(jsonstr);
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	
	public  void  excel_msc_call(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,
			WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content,WritableCellFormat wcfN){
		String time=map.get("time").toString();
		try {
		sheet.addCell(new Label(0, 0, time+"-交换中心话务统计"+time, fontFormat));
		sheet.mergeCells(0,0,11,0);
		sheet.setRowView(0, 600);
		sheet.setColumnView(0, 20);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		sheet.setColumnView(6, 20);
		sheet.setColumnView(7, 20);
		sheet.setColumnView(8, 20);
		sheet.setColumnView(9, 20);
		sheet.setColumnView(10, 20);
		
		sheet.addCell(new Label(0, 1, "活动呼叫总数", fontFormat_h));
		sheet.addCell(new Label(1, 1, "活动呼叫总持续时间", fontFormat_h));
		sheet.addCell(new Label(2, 1, "平均呼叫持续时间", fontFormat_h));
		sheet.addCell(new Label(3, 1, "总PTT数", fontFormat_h));
		sheet.addCell(new Label(4, 1, "呼叫总数", fontFormat_h));
		sheet.addCell(new Label(5, 1, "呼损总数", fontFormat_h));
		sheet.addCell(new Label(6, 1, "呼损率", fontFormat_h));
		sheet.addCell(new Label(7, 1, "未成功呼叫总数", fontFormat_h));
		sheet.addCell(new Label(8, 1, "最大用户注册数",fontFormat_h));
		sheet.addCell(new Label(9, 1, "排队数量", fontFormat_h));
		sheet.addCell(new Label(10, 1, "排队持续时间", fontFormat_h));
		sheet.addCell(new Label(11, 1, "最大组注册", fontFormat_h));
		
		
		
		EastMscDayBean bean=EastComService.chart_month_msc(map);
		sheet.setRowView(2, 400);
		sheet.addCell(new jxl.write.Number(0, 2, bean.getTotalActiveCall(), fontFormat_Content));
		sheet.addCell(new Label(1, 2, funUtil.second_time((int) bean.getTotalActiveCallDuration()), fontFormat_Content));
		sheet.addCell(new Label(2, 2, funUtil.second_time((int) bean.getAverageCallDuration()), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(3, 2, bean.getTotalPTTs(), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(4, 2, bean.getTotalCalls(), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(5, 2, bean.getTotalFailedCalls(), fontFormat_Content));
		
		
		
		sheet.addCell(new jxl.write.Number(6, 2, (float)bean.getFailedPercentage()/100, wcfN));
		sheet.addCell(new jxl.write.Number(7, 2, bean.getNoEffectCalls(), fontFormat_Content));
		
		sheet.addCell(new jxl.write.Number(8, 2, bean.getTotalMaxReg(), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(9, 2, bean.getTotalQueueCount(), fontFormat_Content));
		sheet.addCell(new Label(10, 2, funUtil.second_time(bean.getTotalQueueDuration()), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(11, 2, bean.getMaxRegGroup(), fontFormat_Content));
		
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	
	public  void  excel_vpn_call(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,
			WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content,WritableCellFormat wcfN){
		String time=map.get("time").toString();
		try {
		sheet.addCell(new Label(0, 0, time+"-虚拟专网话务统计", fontFormat));
		sheet.mergeCells(0,0,8,0);
		sheet.setRowView(0, 600);
		sheet.setColumnView(0, 10);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		sheet.setColumnView(6, 20);
		sheet.setColumnView(7, 20);
		sheet.setColumnView(8, 20);
		
		sheet.addCell(new Label(0, 1, "专网名称", fontFormat_h));
		sheet.addCell(new Label(1, 1, "虚拟专网", fontFormat_h));
		sheet.addCell(new Label(2, 1, "活动呼叫总数", fontFormat_h));
		sheet.addCell(new Label(3, 1, "活动呼叫总持续时间", fontFormat_h));
		sheet.addCell(new Label(4, 1, "平均呼叫持续时间", fontFormat_h));
		sheet.addCell(new Label(5, 1, "呼叫总数", fontFormat_h));
		sheet.addCell(new Label(6, 1, "呼损总数", fontFormat_h));
		sheet.addCell(new Label(7, 1, "呼损率",fontFormat_h));
		sheet.addCell(new Label(8, 1, "未成功呼叫总数", fontFormat_h));
		
		
		
		List<EastVpnCallBean> list=EastComService.chart_vpn_call(map);
		for (int i = 0; i < list.size(); i++) {
			EastVpnCallBean bean = (EastVpnCallBean) list.get(i);
			sheet.addCell(new jxl.write.Number(0, i + 2, bean.getVpnid(), fontFormat_Content));
			sheet.addCell(new Label(1, i + 2, String.valueOf(bean.getName()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(2, i + 2,bean.getTotalActiveCalls(), fontFormat_Content));
			sheet.addCell(new Label(3, i + 2, FunUtil.second_time(bean.getTotalActiveCallDuration()), fontFormat_Content));
			sheet.addCell(new Label(4, i + 2, FunUtil.second_time(bean.getAverageCallDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(5, i + 2,bean.getDexTotalCalls(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(6, i + 2,bean.getTotalFailedCalls(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(7, i + 2, (float)bean.getFailedPercentage()/100, wcfN));
			sheet.addCell(new jxl.write.Number(8, i + 2,bean.getNoEffectCalls(), fontFormat_Content));
		}
		int a=0,b=0,c=0,d=0,e=0,f=0,g=0;
		for(int i=0,len=list.size();i<len;i++){
			EastVpnCallBean bean = (EastVpnCallBean) list.get(i);
			a+=bean.getTotalActiveCalls();
			b+=bean.getTotalActiveCallDuration();
			c+=bean.getAverageCallDuration();
			d+=bean.getDexTotalCalls();
			e+=bean.getTotalFailedCalls();
			f+=bean.getFailedPercentage();
			g+=bean.getNoEffectCalls();
		}
		
		//总计
		int len=list.size()+2;
		sheet.setRowView(len, 600);
		sheet.addCell(new Label(0, len, String.valueOf("总计"), fontFormat_Content));
		sheet.addCell(new Label(1, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(2, len, a, fontFormat_Content));
		sheet.addCell(new Label(3, len, FunUtil.second_time(b), fontFormat_Content));
		sheet.addCell(new Label(4, len, FunUtil.second_time(c), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(5, len, d, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(6, len, e, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(7, len, (float)f/100, wcfN));
		sheet.addCell(new jxl.write.Number(8, len, g, fontFormat_Content));
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void  excel_bs_call(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,
			WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content,WritableCellFormat wcfN){
		String time=map.get("time").toString();
		try {
			WritableFont sort_font = new WritableFont(WritableFont.TIMES, 9,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.RED);
			// 应用字体
			WritableCellFormat sort = new WritableCellFormat(sort_font);
			// 设置其他样式
			sort.setAlignment(Alignment.CENTRE);// 水平对齐
			sort.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			sort.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			sort.setBackground(Colour.WHITE);// 背景色
			sort.setWrap(true);// 不自动换行
		sheet.addCell(new Label(0, 0, time+"-基站话务统计", fontFormat));
		sheet.mergeCells(0,0,14,0);
		sheet.setRowView(0, 600);
		sheet.setColumnView(0, 10);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		sheet.setColumnView(6, 20);
		sheet.setColumnView(7, 20);
		sheet.setColumnView(8, 20);
		sheet.setColumnView(9, 20);
		sheet.setColumnView(10, 20);
		sheet.setColumnView(11, 20);
		sheet.setColumnView(12, 20);
		sheet.setColumnView(13, 20);
		sheet.setColumnView(14, 20);
		sheet.addCell(new Label(0, 1, "话务排名", fontFormat_h));
		sheet.addCell(new Label(1, 1, "注册用户排名", fontFormat_h));
		sheet.addCell(new Label(2, 1, "排队数排名", fontFormat_h));
		sheet.addCell(new Label(3, 1, "基站ID", fontFormat_h));
		sheet.addCell(new Label(4, 1, "基站名称", fontFormat_h));
		sheet.addCell(new Label(5, 1, "基站分级", fontFormat_h));
		sheet.addCell(new Label(6, 1, "区域", fontFormat_h));
		sheet.addCell(new Label(7, 1, "活动呼叫总数", fontFormat_h));
		sheet.addCell(new Label(8, 1, "活动呼叫总持续时间", fontFormat_h));
		sheet.addCell(new Label(9, 1, "平均呼叫持续时间", fontFormat_h));
		sheet.addCell(new Label(10, 1, "总PPT数", fontFormat_h));
		sheet.addCell(new Label(11, 1, "排队数量", fontFormat_h));
		sheet.addCell(new Label(12, 1, "排队持续时间",fontFormat_h));
		sheet.addCell(new Label(13, 1, "最大用户注册数", fontFormat_h));
		sheet.addCell(new Label(14, 1, "最大组注册数", fontFormat_h));
		List<EastBsCallDataBean> list=EastComService.chart_bs_call(map);
		for (int i = 0; i < list.size(); i++) {
			EastBsCallDataBean bean = (EastBsCallDataBean) list.get(i);
			sheet.setRowView(i + 2, 400);
			sheet.addCell(new jxl.write.Number(0, i + 2,bean.getCall_sort(), sort));
			sheet.addCell(new jxl.write.Number(1, i + 2, bean.getUser_sort(), sort));
			sheet.addCell(new jxl.write.Number(2, i + 2, bean.getQueue_sort(), sort));
			sheet.addCell(new jxl.write.Number(3, i + 2, bean.getBsid(), fontFormat_Content));
			sheet.addCell(new Label(4, i + 2, String.valueOf(bean.getName()), fontFormat_Content));
			if(bean.getLevel()==null || bean.getLevel()==""){
				sheet.addCell(new Label(5, i + 2, bean.getLevel(), fontFormat_Content));
			}else{
				sheet.addCell(new jxl.write.Number(5, i + 2, Integer.parseInt(bean.getLevel()), fontFormat_Content));
			}
			sheet.addCell(new Label(6, i + 2, String.valueOf(bean.getArea()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(7, i + 2, bean.getTotalActiveCalls(), fontFormat_Content));
			sheet.addCell(new Label(8, i + 2, FunUtil.second_time(bean.getTotalActiveCallDuration()), fontFormat_Content));
			sheet.addCell(new Label(9, i + 2, FunUtil.second_time(bean.getAverageCallDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(10, i + 2, bean.getPTTCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(11, i + 2, bean.getQueueCount(), fontFormat_Content));
			sheet.addCell(new Label(12, i + 2, FunUtil.second_time(bean.getQueueDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(13, i + 2, bean.getMaxUserRegCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(14, i + 2, bean.getMaxGroupRegCount(), fontFormat_Content));
		}
		int a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0;
		for(int i=0,len=list.size();i<len;i++){
			EastBsCallDataBean bean = (EastBsCallDataBean) list.get(i);
			a+=bean.getTotalActiveCalls();
			b+=bean.getTotalActiveCallDuration();
			c+=bean.getAverageCallDuration();
			d+=bean.getPTTCount();
			e+=bean.getQueueCount();
			f+=bean.getQueueDuration();
			if(bean.getMaxUserRegCount()>g){
				g=bean.getMaxUserRegCount();
			}
			if(bean.getMaxGroupRegCount()>h){
				h=bean.getMaxGroupRegCount();
			}
		}
		
		//总计
		int len=list.size()+2;
		sheet.setRowView(len, 600);
		sheet.addCell(new Label(0, len, String.valueOf("总计"), fontFormat_Content));
		sheet.addCell(new Label(1, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(2, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(3, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(4, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(5, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(6, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(7, len,a, fontFormat_Content));
		sheet.addCell(new Label(8, len, FunUtil.second_time(b), fontFormat_Content));
		sheet.addCell(new Label(9, len, FunUtil.second_time(c), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(10, len, d, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(11, len, e, fontFormat_Content));
		sheet.addCell(new Label(12, len, FunUtil.second_time(f), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(13, len, g, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(14, len, h, fontFormat_Content));
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void  excel_bs_level_area_call(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,
			WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content,WritableCellFormat wcfN){
		try {
	    List<EastBsCallDataBean> level_list=EastComService.chart_bs_level_call(map);
	    
	    /*mergeCells(a,b,c,d) 单元格合并函数
		a 单元格的列号
		b 单元格的行号
		c 从单元格[a,b]起，向下合并到c列
		d 从单元格[a,b]起，向下合并到d行*/
	    sheet.addCell(new Label(0, 1, "基站级别", fontFormat_Content));
	    sheet.mergeCells(0,1,0,level_list.size()+1);
		/*sheet.setRowView(0, 600);*/
		sheet.setColumnView(0, 10);
		sheet.setColumnView(1, 10);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 10);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		sheet.setColumnView(6, 20);
		sheet.setColumnView(7, 20);
		sheet.setColumnView(8, 20);
		sheet.setColumnView(9, 20);
		sheet.setColumnView(10, 20);
		sheet.addCell(new Label(1, 1, "基站分级", fontFormat_h));
		sheet.addCell(new Label(2, 1, "基站数量", fontFormat_h));
		sheet.addCell(new Label(3, 1, "活动呼叫总数", fontFormat_h));
		sheet.addCell(new Label(4, 1, "活动呼叫总持续时间", fontFormat_h));
		sheet.addCell(new Label(5, 1, "平均呼叫持续时间", fontFormat_h));
		sheet.addCell(new Label(6, 1, "总PPT数", fontFormat_h));
		sheet.addCell(new Label(7, 1, "排队数量", fontFormat_h));
		sheet.addCell(new Label(8, 1, "排队持续时间",fontFormat_h));
		sheet.addCell(new Label(9, 1, "最大用户注册数", fontFormat_h));
		sheet.addCell(new Label(10, 1, "最大组注册数", fontFormat_h));
		
		for (int i = 0; i < level_list.size(); i++) {
			EastBsCallDataBean bean = (EastBsCallDataBean) level_list.get(i);
			sheet.setRowView(i + 2, 400);
			sheet.addCell(new jxl.write.Number(1, i + 2, Integer.parseInt(bean.getLevel()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(2, i + 2, bean.getBsTotals(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(3, i + 2, bean.getTotalActiveCalls(), fontFormat_Content));
			sheet.addCell(new Label(4, i + 2, FunUtil.second_time(bean.getTotalActiveCallDuration()), fontFormat_Content));
			sheet.addCell(new Label(5, i + 2, FunUtil.second_time(bean.getAverageCallDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(6, i + 2,bean.getPTTCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(7, i + 2, bean.getQueueCount(), fontFormat_Content));
			sheet.addCell(new Label(8, i + 2, FunUtil.second_time(bean.getQueueDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(9, i + 2,bean.getMaxUserRegCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(10, i + 2, bean.getMaxGroupRegCount(), fontFormat_Content));
		}
		int a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0,x=0;
		for(int i=0,len=level_list.size();i<len;i++){
			EastBsCallDataBean bean = (EastBsCallDataBean) level_list.get(i);
			a+=bean.getTotalActiveCalls();
			b+=bean.getTotalActiveCallDuration();
			c+=bean.getAverageCallDuration();
			d+=bean.getPTTCount();
			e+=bean.getQueueCount();
			f+=bean.getQueueDuration();
			
			if(bean.getMaxUserRegCount()>g){
				g=bean.getMaxUserRegCount();
			}
			if(bean.getMaxGroupRegCount()>h){
				h=bean.getMaxGroupRegCount();
			}
			
			x+=bean.getBsTotals();
		}
		
		//总计
		int len=level_list.size()+2;
		sheet.setRowView(len, 600);
		sheet.addCell(new Label(1, len, String.valueOf("总计"), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(2, len, x, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(3, len, a, fontFormat_Content));
		sheet.addCell(new Label(4, len, FunUtil.second_time(b), fontFormat_Content));
		sheet.addCell(new Label(5, len, FunUtil.second_time(c), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(6, len, d, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(7, len, e, fontFormat_Content));
		sheet.addCell(new Label(8, len, FunUtil.second_time(f), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(9, len, g, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(10, len, h, fontFormat_Content));
		
		
		//基站区域
		List<EastBsCallDataBean> area_list=EastComService.chart_bs_area_call(map);
		/*mergeCells(a,b,c,d) 单元格合并函数
		a 单元格的列号
		b 单元格的行号
		c 从单元格[a,b]起，向下合并到c列
		d 从单元格[a,b]起，向下合并到d行*/
	
		sheet.addCell(new Label(0, len+3, "绕城内外", fontFormat_Content));
		sheet.mergeCells(0,len+3,0,area_list.size()+level_list.size()+6);
		
		sheet.addCell(new Label(1, len+3, "基站区域", fontFormat_h));
		sheet.addCell(new Label(2, len+3, "基站数量", fontFormat_h));
		sheet.addCell(new Label(3, len+3, "活动呼叫总数", fontFormat_h));
		sheet.addCell(new Label(4, len+3, "活动呼叫总持续时间", fontFormat_h));
		sheet.addCell(new Label(5, len+3, "平均呼叫持续时间", fontFormat_h));
		sheet.addCell(new Label(6, len+3, "总PPT数", fontFormat_h));
		sheet.addCell(new Label(7, len+3, "排队数量", fontFormat_h));
		sheet.addCell(new Label(8, len+3, "排队持续时间",fontFormat_h));
		sheet.addCell(new Label(9, len+3, "最大用户注册数", fontFormat_h));
		sheet.addCell(new Label(10, len+3, "最大组注册数", fontFormat_h));
		
		for (int i = 0; i < area_list.size(); i++) {
			EastBsCallDataBean bean = (EastBsCallDataBean) area_list.get(i);
			sheet.setRowView(i + 2, 400);
			sheet.addCell(new Label(1, i + len+4, String.valueOf(bean.getArea()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(2, i + len+4, bean.getBsTotals(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(3, i + len+4, bean.getTotalActiveCalls(), fontFormat_Content));
			sheet.addCell(new Label(4, i + len+4, FunUtil.second_time(bean.getTotalActiveCallDuration()), fontFormat_Content));
			sheet.addCell(new Label(5, i + len+4, FunUtil.second_time(bean.getAverageCallDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(6, i + len+4, bean.getPTTCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(7, i + len+4, bean.getQueueCount(), fontFormat_Content));
			sheet.addCell(new Label(8, i + len+4, FunUtil.second_time(bean.getQueueDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(9, i + len+4, bean.getMaxUserRegCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(10, i + len+4, bean.getMaxGroupRegCount(), fontFormat_Content));
		}
		int a1=0,b1=0,c1=0,d1=0,e1=0,f1=0,g1=0,h1=0,x1=0;
		//int len1=level_list.size()+2;
		for(int i=0,len1=area_list.size();i<len1;i++){
			EastBsCallDataBean bean = (EastBsCallDataBean) area_list.get(i);
			a1+=bean.getTotalActiveCalls();
			b1+=bean.getTotalActiveCallDuration();
			c1+=bean.getAverageCallDuration();
			d1+=bean.getPTTCount();
			e1+=bean.getQueueCount();
			f1+=bean.getQueueDuration();
			if(bean.getMaxUserRegCount()>g1){
				g1=bean.getMaxUserRegCount();
			}
			if(bean.getMaxGroupRegCount()>h1){
				h1=bean.getMaxGroupRegCount();
			}
			x1+=bean.getBsTotals();
		}
		
		//总计
		int len2=area_list.size()+2;
		sheet.setRowView(len, 600);
		sheet.addCell(new Label(1, len2+len+2, String.valueOf("总计"), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(2, len2+len+2, x1, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(3, len2+len+2, a1, fontFormat_Content));
		sheet.addCell(new Label(4, len2+len+2, FunUtil.second_time(b1), fontFormat_Content));
		sheet.addCell(new Label(5, len2+len+2, FunUtil.second_time(c1), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(6, len2+len+2, d1, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(7, len2+len+2, e1, fontFormat_Content));
		sheet.addCell(new Label(8, len2+len+2, FunUtil.second_time(f1), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(9, len2+len+2,g1, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(10, len2+len+2, h1, fontFormat_Content));
		
		
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void  excel_bs_zone_call(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,
			WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content,WritableCellFormat wcfN){
		String time=map.get("time").toString();
		
		try {
		sheet.setColumnView(0, 10);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 10);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		sheet.setColumnView(6, 20);
		sheet.setColumnView(7, 20);
		sheet.setColumnView(8, 20);
		sheet.setColumnView(9, 20);
		sheet.setColumnView(10, 20);
		sheet.setColumnView(11, 20);
		sheet.addCell(new Label(0, 1, "序号", fontFormat_h));
		sheet.addCell(new Label(1, 1, "行政区域", fontFormat_h));
		sheet.addCell(new Label(2, 1, "基站数量", fontFormat_h));
		sheet.addCell(new Label(3, 1, "活动呼叫总数", fontFormat_h));
		sheet.addCell(new Label(4, 1, "活动呼叫总持续时间", fontFormat_h));
		sheet.addCell(new Label(5, 1, "平均呼叫持续时间", fontFormat_h));
		sheet.addCell(new Label(6, 1, "总PPT数", fontFormat_h));
		sheet.addCell(new Label(7, 1, "排队数量", fontFormat_h));
		sheet.addCell(new Label(8, 1, "排队持续时间",fontFormat_h));
		sheet.addCell(new Label(9, 1, "最大用户注册数", fontFormat_h));
		sheet.addCell(new Label(10, 1, "最大组注册数", fontFormat_h));
		List<EastBsCallDataBean> list=EastComService.chart_bs_zone_call(map);
		for (int i = 0; i < list.size(); i++) {
			EastBsCallDataBean bean = (EastBsCallDataBean) list.get(i);
			sheet.setRowView(i + 2, 400);
			sheet.addCell(new jxl.write.Number(0, i + 2, (i+1), fontFormat_Content));
			sheet.addCell(new Label(1, i + 2, String.valueOf(bean.getZone()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(2, i + 2, bean.getBsTotals(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(3, i + 2, bean.getTotalActiveCalls(), fontFormat_Content));
			sheet.addCell(new Label(4, i + 2, FunUtil.second_time(bean.getTotalActiveCallDuration()), fontFormat_Content));
			sheet.addCell(new Label(5, i + 2, FunUtil.second_time(bean.getAverageCallDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(6, i + 2, bean.getPTTCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(7, i + 2,bean.getQueueCount(), fontFormat_Content));
			sheet.addCell(new Label(8, i + 2, FunUtil.second_time(bean.getQueueDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(9, i + 2, bean.getMaxUserRegCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(10, i + 2, bean.getMaxGroupRegCount(), fontFormat_Content));
		}
		int a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0,x=0;
		for(int i=0,len=list.size();i<len;i++){
			EastBsCallDataBean bean = (EastBsCallDataBean) list.get(i);
			a+=bean.getTotalActiveCalls();
			b+=bean.getTotalActiveCallDuration();
			c+=bean.getAverageCallDuration();
			d+=bean.getPTTCount();
			e+=bean.getQueueCount();
			f+=bean.getQueueDuration();
			if(bean.getMaxUserRegCount()>g){
				g=bean.getMaxUserRegCount();
			}
			if(bean.getMaxGroupRegCount()>h){
				h=bean.getMaxGroupRegCount();
			}
			x+=bean.getBsTotals();
		}
		
		//总计
		int len=list.size()+2;
		sheet.setRowView(len, 600);
		sheet.addCell(new Label(0, len, String.valueOf("总计"), fontFormat_Content));
		sheet.addCell(new Label(1, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(2, len, x, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(3, len, a, fontFormat_Content));
		sheet.addCell(new Label(4, len, FunUtil.second_time(b), fontFormat_Content));
		sheet.addCell(new Label(5, len, FunUtil.second_time(c), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(6, len, d, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(7, len, e, fontFormat_Content));
		sheet.addCell(new Label(8, len, FunUtil.second_time(f), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(9, len, g, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(10, len, h, fontFormat_Content));
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}	
	public  void  excel_bs_zone_top10_call(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,
			WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content,WritableCellFormat wcfN){
		String time=map.get("time").toString();
		
		try {
	    
		sheet.setColumnView(0, 10);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 10);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		sheet.setColumnView(6, 20);
		sheet.setColumnView(7, 20);
		sheet.setColumnView(8, 20);
		sheet.setColumnView(9, 20);
		sheet.setColumnView(10, 20);
		sheet.setColumnView(11, 20);
		sheet.addCell(new Label(0, 1, "序号", fontFormat_h));
		sheet.addCell(new Label(1, 1, "行政区域", fontFormat_h));
		sheet.addCell(new Label(2, 1, "基站数量", fontFormat_h));
		sheet.addCell(new Label(3, 1, "活动呼叫总数", fontFormat_h));
		sheet.addCell(new Label(4, 1, "活动呼叫总持续时间", fontFormat_h));
		sheet.addCell(new Label(5, 1, "平均呼叫持续时间", fontFormat_h));
		sheet.addCell(new Label(6, 1, "总PPT数", fontFormat_h));
		sheet.addCell(new Label(7, 1, "排队数量", fontFormat_h));
		sheet.addCell(new Label(8, 1, "排队持续时间",fontFormat_h));
		sheet.addCell(new Label(9, 1, "最大用户注册数", fontFormat_h));
		sheet.addCell(new Label(10, 1, "最大组注册数", fontFormat_h));
		sheet.addCell(new Label(11, 1, "占总数百分比", fontFormat_h));
		List<EastBsCallDataBean> list=EastComService.chart_bs_zone_top10_call(map);
		for (int i = 0; i < list.size(); i++) {
			EastBsCallDataBean bean = (EastBsCallDataBean) list.get(i);
			sheet.setRowView(i + 2, 400);
			sheet.addCell(new jxl.write.Number(0, i + 2, (i+1), fontFormat_Content));
			sheet.addCell(new Label(1, i + 2, String.valueOf(bean.getZone()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(2, i + 2, bean.getBsTotals(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(3, i + 2, bean.getTotalActiveCalls(), fontFormat_Content));
			sheet.addCell(new Label(4, i + 2, FunUtil.second_time(bean.getTotalActiveCallDuration()), fontFormat_Content));
			sheet.addCell(new Label(5, i + 2, FunUtil.second_time(bean.getAverageCallDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(6, i + 2, bean.getPTTCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(7, i + 2, bean.getQueueCount(), fontFormat_Content));
			sheet.addCell(new Label(8, i + 2, FunUtil.second_time(bean.getQueueDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(9, i + 2, bean.getMaxUserRegCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(10, i + 2, bean.getMaxGroupRegCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(11, i + 2, (float)bean.getPercent()/100, wcfN));
		}
		int a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0,x=0,y=0;
		for(int i=0,len=list.size();i<len;i++){
			EastBsCallDataBean bean = (EastBsCallDataBean) list.get(i);
			a+=bean.getTotalActiveCalls();
			b+=bean.getTotalActiveCallDuration();
			c+=bean.getAverageCallDuration();
			d+=bean.getPTTCount();
			e+=bean.getQueueCount();
			f+=bean.getQueueDuration();
			if(bean.getMaxUserRegCount()>g){
				g=bean.getMaxUserRegCount();
			}
			if(bean.getMaxGroupRegCount()>h){
				h=bean.getMaxGroupRegCount();
			}
			x+=bean.getBsTotals();
			y+=bean.getPercent();
		}
		
		//总计
		int len=list.size()+2;
		sheet.setRowView(len, 600);
		sheet.addCell(new Label(0, len, String.valueOf("总计"), fontFormat_Content));
		sheet.addCell(new Label(1, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(2, len, x, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(3, len, a, fontFormat_Content));
		sheet.addCell(new Label(4, len, FunUtil.second_time(b), fontFormat_Content));
		sheet.addCell(new Label(5, len, FunUtil.second_time(c), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(6, len, d, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(7, len, e, fontFormat_Content));
		sheet.addCell(new Label(8, len, FunUtil.second_time(f), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(9, len, g, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(10, len, h, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(11, len, (float)y/100, wcfN));
		System.out.println("Y::="+y);
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void  excel_bs_top10_call(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,
			WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content,WritableCellFormat wcfN){
		String time=map.get("time").toString();
		try {
			//基站话务前十
		sheet.addCell(new Label(0, 0,"基站话务前十", fontFormat));
		sheet.mergeCells(0,0,10,0);
		sheet.setRowView(0, 600);
		sheet.setColumnView(0, 10);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		sheet.setColumnView(6, 20);
		sheet.setColumnView(7, 20);
		sheet.setColumnView(8, 20);
		sheet.setColumnView(9, 20);
		sheet.setColumnView(10, 20);
		sheet.setColumnView(11, 20);
		sheet.setColumnView(12, 20);
		sheet.setColumnView(13, 20);
		sheet.setColumnView(14, 20);
		sheet.addCell(new Label(0, 1, "序号", fontFormat_h));
		sheet.addCell(new Label(1, 1, "基站ID", fontFormat_h));
		sheet.addCell(new Label(2, 1, "基站名称", fontFormat_h));
		sheet.addCell(new Label(3, 1, "行政区域", fontFormat_h));
		sheet.addCell(new Label(4, 1, "基站分级", fontFormat_h));
		sheet.addCell(new Label(5, 1, "区域", fontFormat_h));
		sheet.addCell(new Label(6, 1, "活动呼叫总数", fontFormat_h));
		sheet.addCell(new Label(7, 1, "活动呼叫总持续时间", fontFormat_h));
		sheet.addCell(new Label(8, 1, "平均呼叫持续时间", fontFormat_h));
		sheet.addCell(new Label(9, 1, "总PPT数", fontFormat_h));
		sheet.addCell(new Label(10, 1, "排队数量", fontFormat_h));
		sheet.addCell(new Label(11, 1, "排队持续时间",fontFormat_h));
		sheet.addCell(new Label(12, 1, "最大用户注册数", fontFormat_h));
		sheet.addCell(new Label(13, 1, "最大组注册数", fontFormat_h));
		sheet.addCell(new Label(14, 1, "活动呼叫占百分比", fontFormat_h));
		List<EastBsCallDataBean> list=EastComService.chart_bs_call_top10(map);
		for (int i = 0; i < list.size(); i++) {
			EastBsCallDataBean bean = (EastBsCallDataBean) list.get(i);
			sheet.setRowView(i + 2, 400);
			sheet.addCell(new jxl.write.Number(0, i + 2, (i+1), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(1, i + 2, bean.getBsid(), fontFormat_Content));
			sheet.addCell(new Label(2, i + 2, String.valueOf(bean.getName()), fontFormat_Content));
			sheet.addCell(new Label(3, i + 2, String.valueOf(bean.getZone()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(4, i + 2, Integer.parseInt(bean.getLevel()), fontFormat_Content));
			sheet.addCell(new Label(5, i + 2, String.valueOf(bean.getArea()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(6, i + 2, bean.getTotalActiveCalls(), fontFormat_Content));
			sheet.addCell(new Label(7, i + 2, FunUtil.second_time(bean.getTotalActiveCallDuration()), fontFormat_Content));
			sheet.addCell(new Label(8, i + 2, FunUtil.second_time(bean.getAverageCallDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(9, i + 2, bean.getPTTCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(10, i + 2, bean.getQueueCount(), fontFormat_Content));
			sheet.addCell(new Label(11, i + 2, FunUtil.second_time(bean.getQueueDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(12, i + 2, bean.getMaxUserRegCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(13, i + 2, bean.getMaxGroupRegCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(14, i + 2, (float)bean.getPercent()/100, wcfN));
		}
		int a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0,x=0;
		for(int i=0,len=list.size();i<len;i++){
			EastBsCallDataBean bean = (EastBsCallDataBean) list.get(i);
			a+=bean.getTotalActiveCalls();
			b+=bean.getTotalActiveCallDuration();
			c+=bean.getAverageCallDuration();
			d+=bean.getPTTCount();
			e+=bean.getQueueCount();
			f+=bean.getQueueDuration();
			if(bean.getMaxUserRegCount()>g){
				g=bean.getMaxUserRegCount();
			}
			if(bean.getMaxGroupRegCount()>h){
				h=bean.getMaxGroupRegCount();
			}
			x+=bean.getPercent();
		}
		
		//总计
		int len=list.size()+2;
		sheet.setRowView(len, 600);
		sheet.addCell(new Label(0, len, String.valueOf("总计"), fontFormat_Content));
		sheet.addCell(new Label(1, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(2, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(3, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(4, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(5, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(6, len,a, fontFormat_Content));
		sheet.addCell(new Label(7, len, FunUtil.second_time(b), fontFormat_Content));
		sheet.addCell(new Label(8, len, FunUtil.second_time(c), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(9, len, d, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(10, len, e, fontFormat_Content));
		sheet.addCell(new Label(11, len, FunUtil.second_time(f), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(12, len,g, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(13, len, h, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(14, len, (float)x/100, wcfN));
		
		
		//最大用户前十
		int start2=len+3;
		sheet.addCell(new Label(0, start2,"最大注册用户前十", fontFormat));
		sheet.mergeCells(0,start2,12,start2);
		
		sheet.setRowView(0, 600);
		sheet.addCell(new Label(0, start2+1, "序号", fontFormat_h));
		sheet.addCell(new Label(1, start2+1, "基站ID", fontFormat_h));
		sheet.addCell(new Label(2, start2+1, "基站名称", fontFormat_h));
		sheet.addCell(new Label(3, start2+1, "行政区域", fontFormat_h));
		sheet.addCell(new Label(4, start2+1, "基站分级", fontFormat_h));
		sheet.addCell(new Label(5, start2+1, "区域", fontFormat_h));
		sheet.addCell(new Label(6, start2+1, "活动呼叫总数", fontFormat_h));
		sheet.addCell(new Label(7, start2+1, "活动呼叫总持续时间", fontFormat_h));
		sheet.addCell(new Label(8, start2+1, "平均呼叫持续时间", fontFormat_h));
		sheet.addCell(new Label(9, start2+1, "总PPT数", fontFormat_h));
		sheet.addCell(new Label(10, start2+1, "排队数量", fontFormat_h));
		sheet.addCell(new Label(11, start2+1, "排队持续时间",fontFormat_h));
		sheet.addCell(new Label(12, start2+1, "最大用户注册数", fontFormat_h));
		sheet.addCell(new Label(13, start2+1, "最大组注册数", fontFormat_h));
		List<EastBsCallDataBean> list2=EastComService.chart_bs_userreg_top10(map);
		for (int i = 0; i < list2.size(); i++) {
			EastBsCallDataBean bean = (EastBsCallDataBean) list2.get(i);
			sheet.setRowView(i +start2+2, 400);
			sheet.addCell(new jxl.write.Number(0, i +start2+ 2, (i+1), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(1, i +start2+ 2, bean.getBsid(), fontFormat_Content));
			sheet.addCell(new Label(2, i +start2+ 2, String.valueOf(bean.getName()), fontFormat_Content));
			sheet.addCell(new Label(3, i +start2+ 2, String.valueOf(bean.getZone()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(4, i +start2+ 2, Integer.parseInt(bean.getLevel()), fontFormat_Content));
			sheet.addCell(new Label(5, i +start2+ 2, String.valueOf(bean.getArea()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(6, i +start2+ 2, bean.getTotalActiveCalls(), fontFormat_Content));
			sheet.addCell(new Label(7, i +start2+ 2, FunUtil.second_time(bean.getTotalActiveCallDuration()), fontFormat_Content));
			sheet.addCell(new Label(8, i +start2+ 2, FunUtil.second_time(bean.getAverageCallDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(9, i +start2+ 2, bean.getPTTCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(10, i +start2+ 2, bean.getQueueCount(), fontFormat_Content));
			sheet.addCell(new Label(11, i +start2+ 2, FunUtil.second_time(bean.getQueueDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(12, i +start2+ 2, bean.getMaxUserRegCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(13, i +start2+ 2, bean.getMaxGroupRegCount(), fontFormat_Content));
		}
		int a2=0,b2=0,c2=0,d2=0,e2=0,f2=0,g2=0,h2=0,x2=0;
		for(int i=0,len1=list2.size();i<len1;i++){
			EastBsCallDataBean bean = (EastBsCallDataBean) list2.get(i);
			a2+=bean.getTotalActiveCalls();
			b2+=bean.getTotalActiveCallDuration();
			c2+=bean.getAverageCallDuration();
			d2+=bean.getPTTCount();
			e2+=bean.getQueueCount();
			f2+=bean.getQueueDuration();
			if(bean.getMaxUserRegCount()>g2){
				g2=bean.getMaxUserRegCount();
			}
			if(bean.getMaxGroupRegCount()>h2){
				h2=bean.getMaxGroupRegCount();
			}
		}
		
		//总计
		int len2=start2+list2.size()+2;
		sheet.setRowView(len2, 600);
		sheet.addCell(new Label(0, len2, String.valueOf("总计"), fontFormat_Content));
		sheet.addCell(new Label(1, len2, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(2, len2, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(3, len2, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(4, len2, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(5, len2, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(6, len2, a2, fontFormat_Content));
		sheet.addCell(new Label(7, len2, FunUtil.second_time(b2), fontFormat_Content));
		sheet.addCell(new Label(8, len2, FunUtil.second_time(c2), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(9, len2, d2, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(10, len2, e2, fontFormat_Content));
		sheet.addCell(new Label(11, len2, FunUtil.second_time(f2), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(12, len2, g2, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(13, len2, h2, fontFormat_Content));
		//排队前十
		
		int start3=len2+3;
		sheet.addCell(new Label(0, start3,"排队前十", fontFormat));
		sheet.mergeCells(0,start3,13,start3);
		sheet.setRowView(0, 600);
		
		sheet.addCell(new Label(0, start3+1, "序号", fontFormat_h));
		sheet.addCell(new Label(1, start3+1, "基站ID", fontFormat_h));
		sheet.addCell(new Label(2, start3+1, "基站名称", fontFormat_h));
		sheet.addCell(new Label(3, start3+1, "行政区域", fontFormat_h));
		sheet.addCell(new Label(4, start3+1, "基站分级", fontFormat_h));
		sheet.addCell(new Label(5, start3+1, "区域", fontFormat_h));
		sheet.addCell(new Label(6, start3+1, "活动呼叫总数", fontFormat_h));
		sheet.addCell(new Label(7, start3+1, "活动呼叫总持续时间", fontFormat_h));
		sheet.addCell(new Label(8, start3+1, "平均呼叫持续时间", fontFormat_h));
		sheet.addCell(new Label(9, start3+1, "总PPT数", fontFormat_h));
		sheet.addCell(new Label(10, start3+1, "排队数量", fontFormat_h));
		sheet.addCell(new Label(11, start3+1, "排队持续时间",fontFormat_h));
		sheet.addCell(new Label(12, start3+1, "最大用户注册数", fontFormat_h));
		sheet.addCell(new Label(13, start3+1, "最大组注册数", fontFormat_h));
		sheet.addCell(new Label(14, start3+1, "活动呼叫占百分比", fontFormat_h));
		List<EastBsCallDataBean> list3=EastComService.chart_bs_queue_top10(map);
		for (int i = 0; i < list3.size(); i++) {
			EastBsCallDataBean bean = (EastBsCallDataBean) list3.get(i);
			sheet.setRowView(i +start3+ 2, 400);
			sheet.addCell(new jxl.write.Number(0, i +start3+ 2,(i+1), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(1, i +start3+ 2, bean.getBsid(), fontFormat_Content));
			sheet.addCell(new Label(2, i +start3+ 2, String.valueOf(bean.getName()), fontFormat_Content));
			sheet.addCell(new Label(3, i +start3+ 2, String.valueOf(bean.getZone()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(4, i +start3+ 2, Integer.parseInt(bean.getLevel()), fontFormat_Content));
			sheet.addCell(new Label(5, i +start3+ 2, String.valueOf(bean.getArea()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(6, i +start3+ 2, bean.getTotalActiveCalls(), fontFormat_Content));
			sheet.addCell(new Label(7, i +start3+ 2, FunUtil.second_time(bean.getTotalActiveCallDuration()), fontFormat_Content));
			sheet.addCell(new Label(8, i +start3+ 2, FunUtil.second_time(bean.getAverageCallDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(9, i +start3+ 2, bean.getPTTCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(10, i +start3+ 2, bean.getQueueCount(), fontFormat_Content));
			sheet.addCell(new Label(11, i +start3+ 2, FunUtil.second_time(bean.getQueueDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(12, i +start3+ 2, bean.getMaxUserRegCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(13, i +start3+ 2, bean.getMaxGroupRegCount(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(14, i +start3+ 2, (float)bean.getPercent()/100, wcfN));
		}
		int a3=0,b3=0,c3=0,d3=0,e3=0,f3=0,g3=0,h3=0,x3=0;
		for(int i=0,len3=list3.size();i<len3;i++){
			EastBsCallDataBean bean = (EastBsCallDataBean) list3.get(i);
			a3+=bean.getTotalActiveCalls();
			b3+=bean.getTotalActiveCallDuration();
			c3+=bean.getAverageCallDuration();
			d3+=bean.getPTTCount();
			e3+=bean.getQueueCount();
			f3+=bean.getQueueDuration();
			if(bean.getMaxUserRegCount()>g3){
				g3=bean.getMaxUserRegCount();
			}
			if(bean.getMaxGroupRegCount()>h3){
				h3=bean.getMaxGroupRegCount();
			}
			x3+=bean.getPercent();
		}
		
		//总计
		int len4=start3+list3.size()+2;
		sheet.setRowView(len4, 600);
		sheet.addCell(new Label(0, len4, String.valueOf("总计"), fontFormat_Content));
		sheet.addCell(new Label(1, len4, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(2, len4, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(3, len4, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(4, len4, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(5, len4, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(6, len4, a3, fontFormat_Content));
		sheet.addCell(new Label(7, len4, FunUtil.second_time(b3), fontFormat_Content));
		sheet.addCell(new Label(8, len4, FunUtil.second_time(c3), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(9, len4, d3, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(10, len4, e3, fontFormat_Content));
		sheet.addCell(new Label(11, len4, FunUtil.second_time(f3), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(12, len4, g3, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(13, len4, h3, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(14, len4, (float)x3/100, wcfN));
		
		
		
		
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void  excel_vpn_top10_call(Map<String,Object> map,WritableSheet sheet,WritableCellFormat fontFormat,
			WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content,WritableCellFormat wcfN){
		String time=map.get("time").toString();
		try {
		sheet.mergeCells(0,0,10,0);
		sheet.setColumnView(0, 10);
		sheet.setColumnView(1, 20);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 20);
		sheet.setColumnView(5, 20);
		sheet.setColumnView(6, 20);
		sheet.setColumnView(7, 20);
		sheet.setColumnView(8, 20);
		sheet.setColumnView(9, 20);
		sheet.setColumnView(10, 20);
		
		sheet.addCell(new Label(0, 1, "序号", fontFormat_h));
		sheet.addCell(new Label(1, 1, "专网名称", fontFormat_h));
		sheet.addCell(new Label(2, 1, "虚拟专网", fontFormat_h));
		sheet.addCell(new Label(3, 1, "活动呼叫总数", fontFormat_h));
		sheet.addCell(new Label(4, 1, "活动呼叫总持续时间", fontFormat_h));
		sheet.addCell(new Label(5, 1, "平均呼叫持续时间", fontFormat_h));
		sheet.addCell(new Label(6, 1, "呼叫总数", fontFormat_h));
		sheet.addCell(new Label(7, 1, "呼损总数", fontFormat_h));
		sheet.addCell(new Label(8, 1, "呼损率",fontFormat_h));
		sheet.addCell(new Label(9, 1, "未成功呼叫总数", fontFormat_h));
		sheet.addCell(new Label(10, 1, "呼叫总数占百分比", fontFormat_h));
		
		
		
		List<EastVpnCallBean> list=EastComService.chart_vpn_call_top10(map);
		for (int i = 0; i < list.size(); i++) {
			EastVpnCallBean bean = (EastVpnCallBean) list.get(i);
			sheet.setRowView(i + 2, 400);
			sheet.addCell(new jxl.write.Number(0, i + 2, (i+1), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(1, i + 2, bean.getVpnid(), fontFormat_Content));
			sheet.addCell(new Label(2, i + 2, String.valueOf(bean.getName()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(3, i + 2, bean.getTotalActiveCalls(), fontFormat_Content));
			sheet.addCell(new Label(4, i + 2, FunUtil.second_time(bean.getTotalActiveCallDuration()), fontFormat_Content));
			sheet.addCell(new Label(5, i + 2, FunUtil.second_time(bean.getAverageCallDuration()), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(6, i + 2, bean.getDexTotalCalls(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(7, i + 2, bean.getTotalFailedCalls(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(8, i + 2, (float)bean.getFailedPercentage()/100, wcfN));
			sheet.addCell(new jxl.write.Number(9, i + 2, bean.getNoEffectCalls(), fontFormat_Content));
			sheet.addCell(new jxl.write.Number(10, i + 2, (float)bean.getPercent()/100, wcfN));
		}
		int a=0,b=0,c=0,d=0,e=0,f=0,g=0,x=0;
		for(int i=0,len=list.size();i<len;i++){
			EastVpnCallBean bean = (EastVpnCallBean) list.get(i);
			a+=bean.getTotalActiveCalls();
			b+=bean.getTotalActiveCallDuration();
			c+=bean.getAverageCallDuration();
			d+=bean.getDexTotalCalls();
			e+=bean.getTotalFailedCalls();
			f+=bean.getFailedPercentage();
			g+=bean.getNoEffectCalls();
			x+=bean.getPercent();
			}
		
		//总计
		int len=list.size()+2;
		sheet.setRowView(len, 600);
		sheet.addCell(new Label(0, len, String.valueOf("总计"), fontFormat_Content));
		sheet.addCell(new Label(1, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new Label(2, len, String.valueOf("--"), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(3, len, a, fontFormat_Content));
		sheet.addCell(new Label(4, len, FunUtil.second_time(b), fontFormat_Content));
		sheet.addCell(new Label(5, len, FunUtil.second_time(c), fontFormat_Content));
		sheet.addCell(new jxl.write.Number(6, len, d, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(7, len, e, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(8, len, (float)f/100, wcfN));
		sheet.addCell(new jxl.write.Number(9, len, g, fontFormat_Content));
		sheet.addCell(new jxl.write.Number(10, len, (float)x/100, wcfN));
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
}
