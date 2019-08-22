package xh.springmvc.handlers;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.reflect.TypeToken;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.CheckMoneyBean;
import xh.mybatis.bean.MeetBean;
import xh.mybatis.bean.MoneyBean;
import xh.mybatis.bean.OperationsCheckBean;
import xh.mybatis.bean.OperationsCheckDetailBean;
import xh.mybatis.bean.OperationsCheckScoreBean;
import xh.mybatis.bean.ScoreBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.OperationsCheckService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.service.WorkContactService;


@Controller
@RequestMapping("/check")
public class OperationsCheckController {
	private boolean success;
	private String message;
	protected final Log log = LogFactory.getLog(OperationsCheckController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public void data(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		String time=request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time", time);
		map.put("user", FunUtil.loginUser(request));
	    map.put("roleType",FunUtil.loginUserInfo(request).get("roleType"));
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",OperationsCheckService.count(map));
		result.put("items", OperationsCheckService.dataList(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch blocksearchFile
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/searchFile", method = RequestMethod.GET)
	public void searchFile(@RequestParam("applyId") String applyId,
			HttpServletRequest request, HttpServletResponse response) {
		HashMap result = new HashMap();
		result.put("items", OperationsCheckService.searchFile(applyId));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch blocksearchFile
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/search_checkcut_count", method = RequestMethod.GET)
	public void search_checkcut_count(
			@RequestParam("month") String month,
			@RequestParam("period") int period,
			HttpServletRequest request, 
			HttpServletResponse response) {
		this.success = true;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("month", month);
		map.put("period", period);
		HashMap result = new HashMap();
		result.put("count", OperationsCheckService.search_checkcut_count(map));
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/searchDetail", method = RequestMethod.GET)
	public void searchDetail(HttpServletRequest request, HttpServletResponse response) {
		String time=request.getParameter("time");
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("time", time);
		map3.put("period", 3);		
		List<CheckMoneyBean> list3=OperationsCheckService.searchDetail(map3);
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("time", time);
		map4.put("period", 4);		
		List<CheckMoneyBean> list4=OperationsCheckService.searchDetail(map4);
		float sum3=0,sum4=0;
		HashMap<String,Object> rs3=new HashMap<String, Object>();
		HashMap<String,Object> rs4=new HashMap<String, Object>();
		for(int i=0;i<list3.size();i++){
			CheckMoneyBean bean=list3.get(i);
			log.info("bean:"+bean);
			rs3.put("m_"+bean.getCheck_tag(), bean.getMoney());
			rs3.put("n_"+bean.getCheck_tag(), bean.getCheck_note());
			sum3+=bean.getMoney();
		}
		for(int i=0;i<list4.size();i++){
			CheckMoneyBean bean=list4.get(i);
			log.info("bean:"+bean);
			rs4.put("m_"+bean.getCheck_tag(), bean.getMoney());
			rs4.put("n_"+bean.getCheck_tag(), bean.getCheck_note());
			sum4+=bean.getMoney();
		}
		
		HashMap result = new HashMap();
		result.put("items3",rs3);
		result.put("sum3",sum3);
		result.put("items4",rs4);
		result.put("sum4",sum4);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/show_money_detail", method = RequestMethod.GET)
	public void show_money_detail(@RequestParam("period") int period,HttpServletRequest request, HttpServletResponse response) {
		String time=request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		List<CheckMoneyBean> list=OperationsCheckService.show_money_detail(time,period);
		float sum=0;
		HashMap<String,Object> rs=new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			CheckMoneyBean bean=list.get(i);
			log.info("bean:"+bean);
			rs.put("m_"+bean.getCheck_tag(), bean.getMoney());
			rs.put("n_"+bean.getCheck_tag(), bean.getCheck_note());
			sum+=bean.getMoney();
		}
		if(period==4){
			rs.put("n_a1", "交换中心本月运行正常");
			rs.put("n_m1", "Moto交换中心本月运行正常");
			rs.put("n_n1", "公网集群本月运行正常");
			rs.put("n_a2", "本月新增超时0小时");
			rs.put("n_a3", "本月新增超时0小时");
			rs.put("n_a4", "本月简阳片区新增超时0小时，其他片区新增超时0小时");
			rs.put("n_b1", "本月未发生重大故障");
			rs.put("n_b2", "本月未发生特别重大故障");
			rs.put("n_c1", "本月未发生特别重大故障");
			rs.put("n_c2", "本月未发生重大故障");
			rs.put("n_c3", "本月故障通报时间未超时");
			rs.put("n_d1", "本月用户服务响应未超时");
			rs.put("n_e1", "本月无用户培训服务");
			rs.put("n_f1", "本月应急通信保障正常实施");
			rs.put("n_g1", "本月应急通信保障演练0次");
			rs.put("n_h1", "本月系统应急处置演练0次");
			rs.put("n_i1", "本月运维资料齐全");
			rs.put("n_j1", "本月无安全保密事故");
		}else{
			rs.put("n_a1", "交换中心本月运行正常");
			rs.put("n_m1", "Moto交换中心本月运行正常");
			rs.put("n_n1", "公网集群本月运行正常");
			rs.put("n_a2", "本月新增超时0小时");
			rs.put("n_a3", "本月新增超时0小时");
			rs.put("n_a4", "本月新增超时0小时");
			rs.put("n_b1", "本月未发生重大故障");
			rs.put("n_b2", "本月未发生特别重大故障");
			rs.put("n_c1", "本月未发生特别重大故障");
			rs.put("n_c2", "本月未发生重大故障");
			rs.put("n_c3", "本月故障通报时间未超时");
			rs.put("n_d1", "本月用户服务响应未超时");
			rs.put("n_e1", "本月无用户培训服务");
			rs.put("n_f1", "本月应急通信保障正常实施");
			rs.put("n_g1", "本月应急通信保障演练0次");
			rs.put("n_h1", "本月系统应急处置演练0次");
			rs.put("n_i1", "本月运维资料齐全");
			rs.put("n_j1", "本月无安全保密事故");
		}
		
		Map<String, Object> map_year = new HashMap<String, Object>();
		Map<String, Object> map_month = new HashMap<String, Object>();
		String start_time="",end_time="";
		if(period==3){
			int month=FunUtil.StringToInt(time.split("-")[1]);
			if(month<=7){
				start_time=(FunUtil.StringToInt(time.split("-")[0])-1)+"-08";
				end_time=time.split("-")[0]+"-07";
			}else{
				start_time=time.split("-")[0]+"-08";
				end_time=(FunUtil.StringToInt(time.split("-")[0])+1)+"-07";
				
			}
		}
		//5-4
		if(period==4){
			int month=FunUtil.StringToInt(time.split("-")[1]);
			if(month<=5){
				start_time=(FunUtil.StringToInt(time.split("-")[0])-1)+"-05";
				end_time=time.split("-")[0]+"-04";
			}else{
				start_time=time.split("-")[0]+"-05";
				end_time=(FunUtil.StringToInt(time.split("-")[0])+1)+"-04";
				
			}
		}
		map_month.put("start_time", start_time);
		map_month.put("end_time", end_time);
		map_month.put("time", time);
		map_month.put("period", period);
		List<Map<String,Object>> search_month_bs_break=OperationsCheckService.search_month_bs_break(map_month);
		int a2=0,a3=0,a4=0;
		for (Map<String, Object> map2 : search_month_bs_break) {
			if(map2.get("level").toString().equals("1")){
				int x1=FunUtil.StringToInt(map2.get("break_time_total"));
				int x2=FunUtil.StringToInt(map2.get("break_time"));
				int y1=FunUtil.StringToInt(map2.get("checkcut_time_total"));
				int y2=FunUtil.StringToInt(map2.get("checkcut_time"));
				
				int total1=x1-y1;
				int total2=x2-y2;
				
				
				
				int a=((total1-total2)%60+total2)%60==0?((total1-total2)%60+total2-60)/60:(((total1-total2)%60+total2-60)/60+1);
				if((total1-total2)%60>0){
					a-=1;
				}
				if(a>0){
					if(map2.get("period").toString().equals(3)){
						a2+=a*300;
					}else{
						a2+=a*1000;
					}
					
					
				}
			}
			if(map2.get("level").toString().equals("2")){
				int x1=FunUtil.StringToInt(map2.get("break_time_total"));
				int x2=FunUtil.StringToInt(map2.get("break_time"));
				int y1=FunUtil.StringToInt(map2.get("checkcut_time_total"));
				int y2=FunUtil.StringToInt(map2.get("checkcut_time"));
				
				int total1=x1-y1;
				int total2=x2-y2;
				
				int a=((total1-total2)%60+total2)%60==0?((total1-total2)%60+total2-60*5)/60:(((total1-total2)%60+total2-60*5)/60+1);
				if((total1-total2)%60>0){
					a-=1;
				}
				if(a>0){
					if(map2.get("period").toString().equals(3)){
						a3+=a*300;
					}else{
						a3+=a*1000;
					}
					
				}
			}
			
			if(map2.get("level").toString().equals("3")){
				int x1=FunUtil.StringToInt(map2.get("break_time_total"));
				int x2=FunUtil.StringToInt(map2.get("break_time"));
				int y1=FunUtil.StringToInt(map2.get("checkcut_time_total"));
				int y2=FunUtil.StringToInt(map2.get("checkcut_time"));
				
				int total1=x1-y1;
				int total2=x2-y2;
				
				int a=((total1-total2)%60+total2)%60==0?((total1-total2)%60+total2-60*9)/60:(((total1-total2)%60+total2-60*9)/60+1);
				if((total1-total2)%60>0){
					a-=1;
				}
				if(a>0){
					if(map2.get("period").toString().equals(3)){
						a4+=a*300;
					}else{
						a4+=a*1000;
					}
					
					
				}
			}	
		}
		rs.put("m_a2", a2);
		rs.put("m_a3", a3);
		rs.put("m_a4", a4);
		sum+=(a2+a3+a4);
		
		
		
		
		HashMap result = new HashMap();
		result.put("items",rs);
		result.put("sum",sum);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/searchScore", method = RequestMethod.GET)
	public void searchScore(HttpServletRequest request, HttpServletResponse response) {
		String time=request.getParameter("time");
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("time", time);
		map3.put("period", 3);	
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("time", time);
		map4.put("period", 4);	
		List<OperationsCheckScoreBean> list3=OperationsCheckService.searchScore(map3);
		List<OperationsCheckScoreBean> list4=OperationsCheckService.searchScore(map4);
		
		float sum3=0,sum4=0;
		HashMap<String,Object> rs3=new HashMap<String, Object>();
		HashMap<String,Object> rs4=new HashMap<String, Object>();
		for(int i=0;i<list3.size();i++){
			OperationsCheckScoreBean bean=list3.get(i);
			rs3.put("s_"+bean.getCheck_tag(), bean.getScore());
			rs3.put("n_"+bean.getCheck_tag(), bean.getCheck_note());
			sum3+=bean.getScore();
		}
		for(int i=0;i<list4.size();i++){
			OperationsCheckScoreBean bean=list4.get(i);
			rs4.put("s_"+bean.getCheck_tag(), bean.getScore());
			rs4.put("n_"+bean.getCheck_tag(), bean.getCheck_note());
			sum4+=bean.getScore();
		}
		HashMap result = new HashMap();
		result.put("items3",rs3);
		result.put("sum3",sum3);
		result.put("items4",rs4);
		result.put("sum4",sum4);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/show_score_detail", method = RequestMethod.GET)
	public void show_score_detail(@RequestParam("period") int period,HttpServletRequest request, HttpServletResponse response) {
		String time=request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		List<OperationsCheckScoreBean> list=OperationsCheckService.show_score_detail(time,period);
		log.info("list:"+list);
		float sum=0;
		HashMap<String,Object> rs=new HashMap<String, Object>();
		rs.put("s_a1", 20);
		rs.put("s_b1", 3);
		rs.put("s_b2", 1);
		rs.put("s_b3", 9);
		rs.put("s_b4", 2);
		rs.put("s_c1", 10);
		rs.put("s_c2", 10);
		rs.put("s_d1", 25);
		rs.put("s_d2", 5);
		rs.put("s_e1", 5);
		rs.put("s_f1", 3);
		rs.put("s_f2", 2);
		rs.put("s_g1", 5);
		
		for(int i=0;i<list.size();i++){
			OperationsCheckScoreBean bean=list.get(i);
			log.info("bean:"+bean);
			//(rs.get("s_"+bean.getCheck_tag()).toString())-bean.getScore())
			int ss=FunUtil.StringToInt(rs.get("s_"+bean.getCheck_tag()));
			String sx=String.format("%.1f",(ss-bean.getScore()));
			rs.put("s_"+bean.getCheck_tag(),Float.parseFloat(String.format("%.1f",(ss-bean.getScore()))));
			
		}
		Iterator it=rs.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, Object> entry=(Entry<String, Object>) it.next();
			Object ket=entry.getKey();
			Object value=entry.getValue();
			sum+=Float.parseFloat(value.toString());
		}
		rs.put("n_d1", "故障控制：特别重大故障0起无扣分，重大故障0起无扣分，故障处理：一般故障中一级基站0起超时0小时扣0分，二级基站0起超时0小时扣0分，三级基站0起超时0小时扣0分，应扣0分，实扣0分。故障情况详见《2019年7月运维故障统计》");
		
		HashMap result = new HashMap();
		result.put("items",rs);
		result.put("sum",sum);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response) {
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setApplyId(FunUtil.RandomAlphanumeric(15));
		checkBean.setCreateTime(FunUtil.nowDateNoTime());
		checkBean.setUser(FunUtil.loginUserInfo(request).get("user").toString());
		checkBean.setUserName(FunUtil.loginUserInfo(request).get("userName").toString());
		checkBean.setTel(FunUtil.loginUserInfo(request).get("tel").toString());
		checkBean.setUserId(FunUtil.StringToInt(FunUtil.loginUserInfo(request).get("userId").toString()));
		
		String month=request.getParameter("time");
		int typestr=Integer.parseInt(request.getParameter("type"));
		String files=request.getParameter("files");
		
		
		
		
		List<Map<String,Object>> filelist=new ArrayList<Map<String,Object>>();
		Type type = new TypeToken<ArrayList<Map<String,Object>>>() {}.getType(); 
		filelist=GsonUtil.json2Object(files, type);
		if(filelist.size()>0){
			for(int i=0;i<filelist.size();i++){
				Map<String,Object> map=filelist.get(i);
				map.put("applyId", checkBean.getApplyId());
				filelist.set(i, map);				
				String srcDir=request.getSession().getServletContext().getRealPath("/upload/checksource");
				srcDir=srcDir+"/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+typestr;
				String destDir1=request.getSession().getServletContext()
						.getRealPath("/upload/check");
				destDir1=destDir1+"/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+typestr;
				File Path1 = new File(destDir1);
				if (!Path1.exists()) {
					Path1.mkdirs();
				}
				File src=new File(srcDir+"/"+map.get("fileName"));
				File file1 = new File(destDir1+"/"+map.get("fileName"));
				try {
					if(!file1.exists()){
						FunUtil.copyFile(src, file1);
					}else{
						file1.delete();
						FunUtil.copyFile(src, file1);
						//log.info("文件："+map.get("fileName")+"已经存在考核目录中，禁止覆盖");
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			OperationsCheckService.addFile(filelist);
		}


		checkBean.setCheckMonth(month);
		checkBean.setType(typestr);
		
		String fileName="会议纪要("+checkBean.getType()+")-"+month+".doc";
		checkBean.setFileName(fileName);
		checkBean.setFilePath("doc/check/"+month.split("-")[0]+"/"+fileName);

		int rst=OperationsCheckService.add(checkBean);
		if(rst>=1){
			this.success=true;
			this.message="提交文件成功";
			/*OperationsCheckService.addScore(score3);
			OperationsCheckService.addScore(score4);
			OperationsCheckService.addDetail(money3);
			OperationsCheckService.addDetail(money4);*/
			webLogBean.setOperator(FunUtil.loginUser(request));
			webLogBean.setOperatorIp(FunUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("运维考核，data=" + checkBean.getApplyId());
			WebLogService.writeLog(webLogBean);
			createMeetFile(checkBean, request);
			FunUtil.sendMsgToUserByPower("o_check_operations_check", 3, "运维考核", "请审核运维考核文件，并签章", request);	
		}else{
			this.success=false;
			this.message="提交文件失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/addFile", method = RequestMethod.POST)
	public void addFile(@RequestParam("applyId") String applyId,HttpServletRequest request, HttpServletResponse response) {
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setApplyId(applyId);
		
		String month=request.getParameter("time");
		int typestr=Integer.parseInt(request.getParameter("type"));
		String files=request.getParameter("files");
		List<Map<String,Object>> filelist=new ArrayList<Map<String,Object>>();
		Type type = new TypeToken<ArrayList<Map<String,Object>>>() {}.getType(); 
		filelist=GsonUtil.json2Object(files, type);
		if(filelist.size()>0){
			for(int i=0;i<filelist.size();i++){
				Map<String,Object> map=filelist.get(i);
				map.put("applyId", checkBean.getApplyId());
				filelist.set(i, map);				
				String srcDir=request.getSession().getServletContext().getRealPath("/upload/checksource");
				srcDir=srcDir+"/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+typestr;
				String destDir1=request.getSession().getServletContext()
						.getRealPath("/upload/check");
				destDir1=destDir1+"/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+typestr;
				File Path1 = new File(destDir1);
				if (!Path1.exists()) {
					Path1.mkdirs();
				}
				File src=new File(srcDir+"/"+map.get("fileName"));
				File file1 = new File(destDir1+"/"+map.get("fileName"));
				try {
					if(!file1.exists()){
						FunUtil.copyFile(src, file1);
					}else{
						log.info("文件："+map.get("fileName")+"已经存在考核目录中，禁止覆盖");
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(OperationsCheckService.addFile(filelist)>0){
				this.success=true;
				this.message="提交文件成功";
				webLogBean.setOperator(FunUtil.loginUser(request));
				webLogBean.setOperatorIp(FunUtil.getIpAddr(request));
				webLogBean.setStyle(1);
				webLogBean.setContent("运维考核，data=" + checkBean.getApplyId());
				WebLogService.writeLog(webLogBean);
				FunUtil.sendMsgToUserByPower("o_check_operations_check", 3, "运维考核", "考核月份("+month+")新增了考核文件", request);	
			}else{
				this.success=false;
				this.message="没有新增文件";
			}
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/record_score_m_4", method = RequestMethod.POST)
	public void record_score_m_4(HttpServletRequest request, HttpServletResponse response) {
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setApplyId(FunUtil.RandomAlphanumeric(15));
		checkBean.setCreateTime(FunUtil.nowDateNoTime());
		checkBean.setUser(FunUtil.loginUserInfo(request).get("user").toString());
		checkBean.setUserName(FunUtil.loginUserInfo(request).get("userName").toString());
		
		String month=request.getParameter("time");
		String applyId=request.getParameter("applyId");
		String checkUser=request.getParameter("checkUser");
        Float score_total=(float) 0,money_total=(float) 0;
		if(request.getParameter("score_total")!=null){
			score_total=Float.parseFloat(request.getParameter("score_total"));
		}
		if(request.getParameter("money_total")!=null){
			money_total=Float.parseFloat(request.getParameter("money_total"));
		}
		
		
		String score4Data = request.getParameter("score4Data");
		String money4Data = request.getParameter("money4Data");
		ScoreBean score4=GsonUtil.json2Object(score4Data, ScoreBean.class);
		MoneyBean money4=GsonUtil.json2Object(money4Data, MoneyBean.class);
		score4.setTime(month);
		score4.setPeriod(4);
		money4.setTime(month);
		money4.setPeriod(4);
		score4.setScore_total(score_total);
		money4.setMoney_total(money_total);

		int a=OperationsCheckService.addScore(score4);
		int b=OperationsCheckService.addDetail(money4);
		String savePath="doc/check/"+FunUtil.nowDateNotTime().split("-")[0];	
		String fileName="扣分-"+month+".doc";
		String filePath=savePath+"/"+fileName;
		score4.setFileName(fileName);
		score4.setFilePath(filePath);
		
		money4.setFileName("扣款-"+month+".doc");
		money4.setFilePath(savePath+"/"+"扣款-"+month+".doc");
		
		if(a>=1 && b>=1){
			this.success=true;
			this.message="成功";		
			createFile(request);
			FunUtil.sendMsgToUserByPower("o_check_operations_check", 3, "运维考核", "请审核运维考核文件，并签章", request);
			FunUtil.sendMsgToOneUser(checkUser, "运维考核", "服务提供方提交了扣分，扣款明细，请确认", request);
		}else{
			this.success=false;
			this.message="失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("score",score4);
		result.put("money",money4);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/record_score_4", method = RequestMethod.POST)
	public void record_score_4(HttpServletRequest request, HttpServletResponse response) {
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setApplyId(FunUtil.RandomAlphanumeric(15));
		checkBean.setCreateTime(FunUtil.nowDateNoTime());
		checkBean.setUser(FunUtil.loginUserInfo(request).get("user").toString());
		checkBean.setUserName(FunUtil.loginUserInfo(request).get("userName").toString());
		
		String month=request.getParameter("time");
		String applyId=request.getParameter("applyId");
		
		Float score_total=(float) 0;
		if(request.getParameter("score_total")!=null){
			score_total=Float.parseFloat(request.getParameter("score_total"));
		}
		
		String score4Data = request.getParameter("score4Data");
		ScoreBean score4=GsonUtil.json2Object(score4Data, ScoreBean.class);
		score4.setTime(month);
		score4.setPeriod(4);
		score4.setScore_total(score_total);
		//score4.setDoc_name(doc_name);
		//score4.setDoc_name(score4.getDoc_name().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));

		int a=OperationsCheckService.addScore(score4);
		String savePath="doc/check/"+month.split("-")[0];	
		String fileName="扣分(四期)-"+month+".doc";
		String filePath=savePath+"/"+fileName;
		score4.setFileName(fileName);
		score4.setFilePath(filePath);
		
		if(a>=1){
			this.success=true;
			this.message="成功";		
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("applyId", applyId);
			map.put("fileNamestr", "score4_fileName");
			map.put("filePathstr", "score4_filePath");
			map.put("docNamestr", "score_header");
			map.put("docName", score4.getDoc_name());
			map.put("fileName", fileName);
			map.put("filePath", filePath);
			OperationsCheckService.update_file_info(map);
			createFile(request);
		}else{
			this.success=false;
			this.message="失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("score",score4);
		FunUtil.CreateSession(request, request.getSession().getId()+"score4", score4);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/record_score_3", method = RequestMethod.POST)
	public void record_score_3(HttpServletRequest request, HttpServletResponse response) {
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setApplyId(FunUtil.RandomAlphanumeric(15));
		checkBean.setCreateTime(FunUtil.nowDateNoTime());
		checkBean.setUser(FunUtil.loginUserInfo(request).get("user").toString());
		checkBean.setUserName(FunUtil.loginUserInfo(request).get("userName").toString());
		
		String month=request.getParameter("time");
		String applyId=request.getParameter("applyId");
		String doc_name=request.getParameter("doc_name");
		Float score_total=(float) 0;
		if(request.getParameter("score_total")!=null){
			score_total=Float.parseFloat(request.getParameter("score_total"));
		}
		
		String score3Data = request.getParameter("score3Data");
		ScoreBean score3=GsonUtil.json2Object(score3Data, ScoreBean.class);
		score3.setTime(month);
		score3.setPeriod(3);
		score3.setScore_total(score_total);
		score3.setDoc_name(doc_name);

		int a=OperationsCheckService.addScore(score3);
		String savePath="doc/check/"+month.split("-")[0];	
		String fileName="扣分(三期)-"+month+".doc";
		String filePath=savePath+"/"+fileName;
		score3.setFileName(fileName);
		score3.setFilePath(filePath);
		
		if(a>=1){
			this.success=true;
			this.message="成功";	
			
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("applyId", applyId);
			map.put("fileNamestr", "score3_fileName");
			map.put("filePathstr", "score3_filePath");
			map.put("docNamestr", "score_header");
			map.put("docName", doc_name);
			map.put("fileName", fileName);
			map.put("filePath", filePath);
			OperationsCheckService.update_file_info(map);
			createFile(request);
		}else{
			this.success=false;
			this.message="失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("score",score3);
		FunUtil.CreateSession(request, request.getSession().getId()+"score3", score3);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/record_money_3", method = RequestMethod.POST)
	public void record_money_3(HttpServletRequest request, HttpServletResponse response) {
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setApplyId(FunUtil.RandomAlphanumeric(15));
		checkBean.setCreateTime(FunUtil.nowDateNoTime());
		checkBean.setUser(FunUtil.loginUserInfo(request).get("user").toString());
		checkBean.setUserName(FunUtil.loginUserInfo(request).get("userName").toString());
		
		String month=request.getParameter("time");
		String applyId=request.getParameter("applyId");
		String doc_name=request.getParameter("doc_name");
		Float money_total=(float) 0;
		if(request.getParameter("money_total")!=null){
			money_total=Float.parseFloat(request.getParameter("money_total"));
		}
		
		
		String money3Data = request.getParameter("money3Data");
		MoneyBean money3=GsonUtil.json2Object(money3Data, MoneyBean.class);
		money3.setTime(month);
		money3.setPeriod(3);
		money3.setMoney_total(money_total);
		money3.setDoc_name(doc_name);

		int b=OperationsCheckService.addDetail(money3);
		String savePath="doc/check/"+month.split("-")[0];
		String fileName="扣款(三期)-"+month+".doc";
		String filePath=savePath+"/"+fileName;
		money3.setFileName(fileName);
		money3.setFilePath(filePath);
		
		if(b>=1){
			this.success=true;
			this.message="成功";	
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("applyId", applyId);
			map.put("fileNamestr", "money3_fileName");
			map.put("filePathstr", "money3_filePath");
			map.put("docNamestr", "money_header");
			map.put("docName", doc_name);
			map.put("fileName", fileName);
			map.put("filePath", filePath);
			OperationsCheckService.update_file_info(map);
			createFile(request);
		}else{
			this.success=false;
			this.message="失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("money",money3);
		FunUtil.CreateSession(request, request.getSession().getId()+"money3", money3);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/record_money_4", method = RequestMethod.POST)
	public void record_money_4(HttpServletRequest request, HttpServletResponse response) {
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setApplyId(FunUtil.RandomAlphanumeric(15));
		checkBean.setCreateTime(FunUtil.nowDateNoTime());
		checkBean.setUser(FunUtil.loginUserInfo(request).get("user").toString());
		checkBean.setUserName(FunUtil.loginUserInfo(request).get("userName").toString());
		
		String month=request.getParameter("time");
		String applyId=request.getParameter("applyId");
		String doc_name=request.getParameter("doc_name");
		Float money_total=(float) 0;
		if(request.getParameter("money_total")!=null){
			money_total=Float.parseFloat(request.getParameter("money_total"));
		}
		
		
		String money4Data = request.getParameter("money4Data");
		MoneyBean money4=GsonUtil.json2Object(money4Data, MoneyBean.class);
		money4.setTime(month);
		money4.setPeriod(4);
		money4.setMoney_total(money_total);
		money4.setDoc_name(doc_name);

		int b=OperationsCheckService.addDetail(money4);
		String savePath="doc/check/"+month.split("-")[0];
		String fileName="扣款(四期)-"+month+".doc";
		String filePath=savePath+"/"+fileName;
		money4.setFileName(fileName);
		money4.setFilePath(filePath);
		
		if(b>=1){
			this.success=true;
			this.message="成功";	
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("applyId", applyId);
			map.put("fileNamestr", "money4_fileName");
			map.put("filePathstr", "money4_filePath");
			map.put("docNamestr", "money_header");
			map.put("docName", doc_name);
			map.put("fileName", fileName);
			map.put("filePath", filePath);
			OperationsCheckService.update_file_info(map);
			createFile(request);
		}else{
			this.success=false;
			this.message="失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("money",money4);
		FunUtil.CreateSession(request, request.getSession().getId()+"money4", money4);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/up_score_money", method = RequestMethod.POST)
	public void up_score_money(HttpServletRequest request, HttpServletResponse response) {
		String applyId=request.getParameter("applyId");
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setApplyId(applyId);
		checkBean.setCheckUser4(FunUtil.loginUser(request));
		checkBean.setCheckTime5(FunUtil.nowDate());
		checkBean.setStatus(5);


		int b=OperationsCheckService.up_score_money(checkBean);		
		if(b>=1){
			this.success=true;
			this.message="成功";	
			//FunUtil.sendMsgToUserByPower("o_check_operations_check", 2, "运维考核", "服务提供方提交了考核扣分扣款信息，请确认", request);	
		}else{
			this.success=false;
			this.message="失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/sure_score_money", method = RequestMethod.POST)
	public void sure_score_money(HttpServletRequest request, HttpServletResponse response) {
		String applyId=request.getParameter("applyId");
		String user=request.getParameter("user");
		String checkUser=request.getParameter("checkUser");
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setApplyId(applyId);
		checkBean.setCheckUser5(FunUtil.loginUser(request));
		checkBean.setCheckTime6(FunUtil.nowDate());
		checkBean.setStatus(6);


		int b=OperationsCheckService.sure_score_money(checkBean);		
		if(b>=1){
			this.success=true;
			this.message="成功";	
			FunUtil.sendMsgToOneUser(user, "运维考核", "管理方确认了扣分扣款消息，等待出会议纪要", request);
			FunUtil.sendMsgToOneUser(checkUser, "运维考核", "管理方确认了扣分扣款消息，等待出会议纪要", request);
		}else{
			this.success=false;
			this.message="失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/allcheckfile", method = RequestMethod.GET)
	public void allcheckfile(@RequestParam("month") String month,
			@RequestParam("period") String period,
			HttpServletRequest request, HttpServletResponse response) {
		
		String rootDir = request.getSession().getServletContext().getRealPath("/upload/checksource");
		String hz="/upload/checksource/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+period;
		rootDir=rootDir+"/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+period;
		File file=new File(rootDir);
		System.out.println(rootDir);
		File[] files=file.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if(pathname.isFile()){
					return true;
				}
				return false;
			}
		});
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for (File file2 : files) {
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("fileName", file2.getName());
			map.put("filePath", hz+"/"+file2.getName());
			map.put("doc", file2.getName().substring(file2.getName().indexOf(".")+1));
			list.add(map);
			
		}
		
		HashMap result = new HashMap();
		result.put("files", list);
		result.put("totals", list.size());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/bscheckfile", method = RequestMethod.GET)
	public void bscheckfile(@RequestParam("month") String month,
			@RequestParam("period") String period,
			HttpServletRequest request, HttpServletResponse response) {
		
		String rootDir = request.getSession().getServletContext().getRealPath("/upload/check");
		String hz="/upload/check/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+period+"/故障核减申请书";
		rootDir=rootDir+"/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+period+"/故障核减申请书";
		File file=new File(rootDir);
		File[] files=file.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if(pathname.isFile()){
					return true;
				}
				return false;
			}
		});
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(files!=null){
			for (File file2 : files) {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("fileName", file2.getName());
				map.put("filePath", hz+"/"+file2.getName());
				list.add(map);				
			}
		}
		HashMap result = new HashMap();
		result.put("files", list);
		result.put("totals", list.size());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/bs_ensure_file", method = RequestMethod.GET)
	public void bs_ensure_file(@RequestParam("month") String month,
			@RequestParam("period") String period,
			HttpServletRequest request, HttpServletResponse response) {
		
		String rootDir = request.getSession().getServletContext().getRealPath("/upload/check");
		String hz="/upload/check/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+period+"/通信保障报告";
		rootDir=rootDir+"/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+period+"/通信保障报告";
		File file=new File(rootDir);
		File[] files=file.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				if(pathname.isFile()){
					return true;
				}
				return false;
			}
		});
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(files!=null){
			for (File file2 : files) {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("fileName", file2.getName());
				map.put("filePath", hz+"/"+file2.getName());
				list.add(map);				
			}
		}
		HashMap result = new HashMap();
		result.put("files", list);
		result.put("totals", list.size());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/bs_app_file", method = RequestMethod.GET)
	public void bs_app_file(@RequestParam("month") String month,
			@RequestParam("period") String period,
			HttpServletRequest request, HttpServletResponse response) {
		
		String rootDir = request.getSession().getServletContext().getRealPath("/upload/check");
		String hz="/upload/check/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+period+"/基站月度巡检表";
		rootDir=rootDir+"/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+period+"/基站月度巡检表";
		File file=new File(rootDir);
		File[] files=file.listFiles();
		/*Collections.sort(files, new Comparator<File>() {
		    @Override
		    public int compare(File o1, File o2) {
		        if (o1.isDirectory() && o2.isFile())
		            return -1;
		        if (o1.isFile() && o2.isDirectory())
		            return 1;
		        return o2.getName().compareTo(o1.getName());
		    }
		});

		Arrays.sort(files, new Comparator<File>() {
			 
			@Override
			public int compare(File f1, File f2) {
				return f1.compareTo(f2);
			}
 
		});*/
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(files!=null){
			for (File file2 : files) {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("fileName", file2.getName());
				map.put("filePath", hz+"/"+file2.getName());
				
				//System.out.println("文件-》"+file2.getName());
				
				list.add(map);				
			}
		}
		HashMap result = new HashMap();
		result.put("files", list);
		result.put("totals", list.size());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/checkFiles", method = RequestMethod.GET)
	public void checkFiles(@RequestParam("month") String month,
			@RequestParam("period") String period,
			HttpServletRequest request, HttpServletResponse response) {
		
		String rootDir = request.getSession().getServletContext().getRealPath("/upload/check");
		String hz="/upload/check/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+period;
		rootDir=rootDir+"/"+month.split("-")[0]+"/"+month.split("-")[1]+"/"+period;

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		list=OperationsCheckService.readFileTree(rootDir);
		HashMap result = new HashMap();
		result.put("files", list);
		result.put("totals", list.size());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@RequestMapping(value = "/check2", method = RequestMethod.POST)
	public void check2(HttpServletRequest request, HttpServletResponse response) {
		//String data=request.getParameter("data");
		//OperationsCheckDetailBean detailBean=GsonUtil.json2Object(data, OperationsCheckDetailBean.class);
		int id=FunUtil.StringToInt(request.getParameter("id"));
		int check=FunUtil.StringToInt(request.getParameter("check"));
		String user=request.getParameter("user");
		String note1=request.getParameter("comment");
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setId(id);
		checkBean.setCheckTime(FunUtil.nowDateNoTime());
		checkBean.setCheckUser(FunUtil.loginUser(request));
		checkBean.setStatus(check);
		checkBean.setNote1(note1);
		int rst=OperationsCheckService.check2(checkBean);
		if(rst>=1){
			this.success=true;
			this.message="审核信息成功";
			if(check==1){
				FunUtil.sendMsgToOneUser(user, "运维考核", "管理部门已经审核通过了你提交的考核信息", request);
			}else{
				FunUtil.sendMsgToOneUser(user, "运维考核", "管理部门拒绝了你的考核申请信息："+note1, request);
			}
		}else{
			this.success=false;
			this.message="审核信息失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public void check(HttpServletRequest request, HttpServletResponse response) {
		//String data=request.getParameter("data");
		//OperationsCheckDetailBean detailBean=GsonUtil.json2Object(data, OperationsCheckDetailBean.class);
		int id=FunUtil.StringToInt(request.getParameter("id"));
		int check=FunUtil.StringToInt(request.getParameter("check"));
		String user=request.getParameter("user");
		String note1=request.getParameter("note1");
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setId(id);
		checkBean.setCheckTime(FunUtil.nowDateNoTime());
		checkBean.setCheckUser(FunUtil.loginUser(request));
		checkBean.setStatus(check);
		checkBean.setNote1(note1);
		int rst=OperationsCheckService.check2(checkBean);
		if(rst>=1){
			this.success=true;
			this.message="审核信息成功";
			if(check==1){
				FunUtil.sendMsgToOneUser(user, "运维考核", "领导审核通过了你提交的考核文件，等待领导签章", request);
			}else{
				FunUtil.sendMsgToOneUser(user, "运维考核", "领导拒绝了你提交的考核文件："+note1, request);
			}
		}else{
			this.success=false;
			this.message="审核信息失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*签章*/
	@RequestMapping(value = "/sealComplete", method = RequestMethod.POST)
	public void sealComplete(HttpServletRequest request, HttpServletResponse response) {
		//String data=request.getParameter("data");
		//OperationsCheckDetailBean detailBean=GsonUtil.json2Object(data, OperationsCheckDetailBean.class);
		int id=FunUtil.StringToInt(request.getParameter("id"));
		String user=request.getParameter("user");
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setId(id);
		checkBean.setCheckTime2(FunUtil.nowDateNoTime());
		checkBean.setStatus(2);
		int rst=OperationsCheckService.check3(checkBean);
		if(rst>=1){
			this.success=true;
			this.message="成功";
			FunUtil.sendMsgToUserByPower("o_check_operations_check", 2, "运维考核", "服务提供方提交了考核文件，请查阅", request);
			
		}else{
			this.success=false;
			this.message="失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*签章*/
	@RequestMapping(value = "/sealScoreMoneyComplete", method = RequestMethod.POST)
	public void sealScoreMoneyComplete(HttpServletRequest request, HttpServletResponse response) {
		//String data=request.getParameter("data");
		//OperationsCheckDetailBean detailBean=GsonUtil.json2Object(data, OperationsCheckDetailBean.class);
		int id=FunUtil.StringToInt(request.getParameter("id"));
		String user=request.getParameter("user");
		String checkUser=request.getParameter("checkUser");
		String checkUser2=request.getParameter("checkUser2");
		OperationsCheckBean checkBean=new OperationsCheckBean();
		int type=Integer.parseInt(FunUtil.loginUserInfo(request).get("roleType").toString());
		checkBean.setId(id);
		checkBean.setType(type);
		if(type==0 || type==3){
			checkBean.setCheckTime7(FunUtil.nowDate());
			checkBean.setStatus(7);
		}else if(type==2){
			checkBean.setCheckTime8(FunUtil.nowDate());
			checkBean.setStatus(8);
		}
		int rst=OperationsCheckService.sealScoreMoneyComplete(checkBean);
		if(rst>=1){
			this.success=true;
			this.message="成功";
			if(type==2){
				FunUtil.sendMsgToOneUser(user, "运维考核", "管理方已经对扣分扣款文件签章", request);
				FunUtil.sendMsgToOneUser(checkUser, "运维考核", "管理方已经对扣分扣款文件签章", request);
			}else{
				FunUtil.sendMsgToOneUser(checkUser2, "运维考核", "服务提供方已经对扣分扣款文件签章，请对文件签章", request);
			}
			
		}else{
			this.success=false;
			this.message="失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/sealFile", method = RequestMethod.POST)
	public void sealFile(HttpServletRequest request, HttpServletResponse response) {
		int id=Integer.parseInt(request.getParameter("id"));
		int rs=OperationsCheckService.sealFile(id);
		if(rs>0){
			this.success=true;
		}
		
		HashMap result = new HashMap();
		result.put("success",success);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/sureFile", method = RequestMethod.POST)
	public void sureFile(HttpServletRequest request, HttpServletResponse response) {
		//String data=request.getParameter("data");
		//OperationsCheckDetailBean detailBean=GsonUtil.json2Object(data, OperationsCheckDetailBean.class);
		int id=FunUtil.StringToInt(request.getParameter("id"));
		String user=request.getParameter("user");
		String checkUser=request.getParameter("checkUser");
		String note=request.getParameter("note");
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setId(id);
		checkBean.setCheckTime3(FunUtil.nowDateNoTime());
		checkBean.setStatus(3);
		checkBean.setComment(note);
		checkBean.setCheckUser2(FunUtil.loginUser(request));
		int rst=OperationsCheckService.check4(checkBean);
		if(rst>=1){
			this.success=true;
			this.message="成功";			
			FunUtil.sendMsgToOneUser(user, "运维考核", "管理方已经确认考核文件："+note, request);
			FunUtil.sendMsgToOneUser(checkUser, "运维考核", "管理方已经确认考核文件："+note, request);
		}else{
			this.success=false;
			this.message="失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/sureMeet", method = RequestMethod.POST)
	public void sureMeet(HttpServletRequest request, HttpServletResponse response) {
		//String data=request.getParameter("data");
		//OperationsCheckDetailBean detailBean=GsonUtil.json2Object(data, OperationsCheckDetailBean.class);
		int id=FunUtil.StringToInt(request.getParameter("id"));
		String checkUser2=request.getParameter("checkUser2");
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setId(id);
		checkBean.setCheckTime4(FunUtil.nowDateNoTime());
		checkBean.setCheckUser3(FunUtil.loginUser(request));
		checkBean.setStatus(4);
		int rst=OperationsCheckService.check5(checkBean);
		if(rst>=1){
			this.success=true;
			this.message="成功";			
			FunUtil.sendMsgToOneUser(checkUser2, "运维考核", "服务提供方收到通知，准时参加会议", request);
		}else{
			this.success=false;
			this.message="失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/checkreport", method = RequestMethod.POST)
	public void checkreport(HttpServletRequest request, HttpServletResponse response) {
		String data=request.getParameter("data");
		OperationsCheckDetailBean detailBean=GsonUtil.json2Object(data, OperationsCheckDetailBean.class);
		int id=FunUtil.StringToInt(request.getParameter("id"));
		String time=request.getParameter("month");
		String user=request.getParameter("user");
		String applyId=request.getParameter("applyId");
		
		detailBean.setTime(time);
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setId(id);
		checkBean.setCheckTime2(FunUtil.nowDateNoTime());
		checkBean.setStatus(2);
		checkBean.setApplyId(applyId);
		checkBean.setCheckMonth(time);
		//int rst=OperationsCheckService.check3(checkBean,detailBean);
		int rst=1;
		if(rst>=1){
			this.success=true;
			this.message="填写记录成功";
			FunUtil.sendMsgToOneUser(user, "运维考核", "管理部门已经填写了考核记录，请确认", request);
		}else{
			this.success=false;
			this.message="填写记录失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/check4", method = RequestMethod.POST)
	public void check4(HttpServletRequest request, HttpServletResponse response) {
		int id=FunUtil.StringToInt(request.getParameter("id"));
		String user=request.getParameter("user");
		OperationsCheckBean checkBean=new OperationsCheckBean();
		checkBean.setId(id);
		checkBean.setCheckTime3(FunUtil.nowDateNoTime());
		checkBean.setStatus(3);
		int rst=OperationsCheckService.check4(checkBean);
		if(rst>=1){
			this.success=true;
			this.message="确认成功";
			FunUtil.sendMsgToOneUser(user, "运维考核", "服务方确认了考核结果", request);
		}else{
			this.success=false;
			this.message="确认失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
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
	 * 上传文件
	 * 
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/upload/file", method = RequestMethod.POST)
	public void upload(@RequestParam("pathName") MultipartFile file,@RequestParam("type") int type,
			HttpServletRequest request, HttpServletResponse response) {
		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String date=sdf.format(d);
		
		String savePath="/upload/check/"+date.split("-")[0]+"/"+date.split("-")[1]+"/"+type;

		String path = request.getSession().getServletContext().getRealPath("")
				+ "/upload/check/"+date.split("-")[0]+"/"+date.split("-")[1]+"/"+type;
		String fileName = file.getOriginalFilename();
		/*
		 * File de=new File(fileName); file.renameTo(new File(name+".jpg"));
		 * //改名
		 */
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();

		}
		// 保存
		try {
			file.transferTo(targetFile);
			this.success = true;
			this.message = "文件上传成功";
			/*File rename = new File(path, fileName);
			targetFile.renameTo(rename);*/

		} catch (Exception e) {
			e.printStackTrace();
			this.message = "文件上传失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", fileName);
		result.put("filePath", savePath + "/" + fileName);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		log.debug(jsonstr);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/upload/batch/file", method = RequestMethod.POST)
	public void batch_upload(
			@RequestParam("pathName") MultipartFile[] file,
			@RequestParam("type") int type,
			@RequestParam("month") String month,
			@RequestParam("files") String files,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("month:"+month);
		List<Map<String,Object>> filelist=new ArrayList<Map<String,Object>>();
		// 获取当前时间
		/*Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");*/
		String date=month;
		String savePath="/upload/checksource/"+date.split("-")[0]+"/"+date.split("-")[1]+"/"+type;
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/upload/checksource/"+date.split("-")[0]+"/"+date.split("-")[1]+"/"+type;
		List<Map<String,Object>> rs=new ArrayList<Map<String,Object>>();
		String mastUploadFiles="'运维人员通讯录','运维资源配置表',"
				+ "'本月计划维护作业完成情况','下月计划维护作业','系统运行维护服务月报',"
				+ "'基站信息表','运维故障统计','通信保障报告','备品备件表','定期维护报告-交换中心月维护',"
				+ "'定期维护报告-基站月维护','系统日常维护表','巡检汇总表','故障处理报告','年度健康检查报告'";
		String error1="";
		String error2="";
		if(!(file.length==0)){
			
			
			for(int i=0;i<file.length;i++){
				boolean x=false;
				String fileName = file[i].getOriginalFilename();
				String[] srcFiles=files.split(",");
				if(srcFiles.length>0){
					/*for (String string : srcFiles) {
						if(string.split("\\.")[0].equals(fileName.split("\\.")[0])){
							x=true;
							log.info("文件存在不上传");
						}
					}*/
					System.out.println(fileName);
					if(!mastUploadFiles.contains(fileName.split("\\.")[0])){
						x=true;
						error1+=fileName+";";
						log.info("该文件不在必传列表中");
					}
				}
				File isExists1=new File(path+"/"+fileName.split("\\.")[0]+".xls");
				File isExists2=new File(path+"/"+fileName.split("\\.")[0]+".xlsx");
				if(isExists1.exists() || isExists2.exists()){
					
					if(isExists1.exists()){
						isExists1.delete();
					}
					if(isExists2.exists()){
						isExists2.delete();
					}
					
					/*if(!new File(path+"/"+fileName).exists()){
						x=true;
						error2+=fileName+";";
						
						log.info("包含相同文件名,且文件格式不一致的文件存在");
					}else{
						log.info("包含相同文件名,且文件格式一致的文件存在");
					}	*/									
				}				
				if(!x){
					File targetFile = new File(path, fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();

					}
					// 保存
					try {
						file[i].transferTo(targetFile);
						this.success = true;
						this.message = "文件上传成功";
						Map<String,Object> map=new HashMap<String, Object>();
						map.put("fileName", fileName);
						map.put("filePath", savePath + "/" + fileName);
						map.put("index", i+1);
						map.put("size", file[i].getSize());
						rs.add(map);

					} catch (Exception e) {
						e.printStackTrace();
						this.message = "文件上传失败";
					}
				}

				
			}
			
			
		}
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("error1", error1);
		result.put("error2", error2);
		result.put("rs", rs);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		log.debug(jsonstr);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search_score_detail", method = RequestMethod.GET)
	public void search_score_detail(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		String time=request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time", time);
		HashMap result = new HashMap();
		result.put("items",OperationsCheckService.search_score_detail(map));
		result.put("totals", OperationsCheckService.search_score_detail_count(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search_money_detail", method = RequestMethod.GET)
	public void search_money_detail(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		String time=request.getParameter("time");
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time", time);
		HashMap result = new HashMap();
		result.put("items",OperationsCheckService.search_money_detail(map));
		result.put("money",OperationsCheckService.error_money_total(time));
		result.put("totals", OperationsCheckService.search_money_detail_count(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/signMeet", method = RequestMethod.POST)
	public void signMeet(HttpServletRequest request, HttpServletResponse response) {
		int id = FunUtil.StringToInt(request.getParameter("id"));
		int status = FunUtil.StringToInt(request.getParameter("status"));
		String user=request.getParameter("user");
		String checkUser=request.getParameter("checkUser");
		String checkUser2=request.getParameter("checkUser2");
		String checkUser3=request.getParameter("checkUser3");
		OperationsCheckBean bean=new OperationsCheckBean();
		int roleType=Integer.parseInt(FunUtil.loginUserInfo(request).get("roleType").toString());
		bean.setId(id);
		bean.setStatus(status);
		bean.setWriteMeetRoleType(roleType);
		bean.setWriteMeetUser(FunUtil.loginUser(request));
		if(status==9){
			bean.setIsMeetDocSign(1);
		}else{
			bean.setIsMeetDocSign(2);
		}
		
		int rs=OperationsCheckService.signMeet(bean);
		if(rs>0){
			this.message="会议纪要提交成功";
			this.success=true;
			if(status==9){
				
				
				if(roleType==2){
					FunUtil.sendMsgToOneUser(user, "运维考核", "管理方提交了考核会议纪要", request);
					FunUtil.sendMsgToOneUser(checkUser, "运维考核", "管理方提交了考核会议纪要，请签字", request);
				}else{
					FunUtil.sendMsgToOneUser(checkUser2, "运维考核", "服务提供方提交了考核会议纪要，请签字", request);
				}
				
			}else if(status==10){
				if(roleType==2){				
					FunUtil.sendMsgToOneUser(user, "运维考核", "服务提供方已经对会议纪要签字了，本次考核结束", request);
				}else{
					FunUtil.sendMsgToOneUser(checkUser3, "运维考核", "服务提供方已经对会议纪要签字了，本次考核结束", request);
				}			
			}
			
		}else{
			this.success=false;
			this.message="失败";
		}
		
		HashMap result = new HashMap();
		result.put("message",message);
		result.put("success",success);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping("/editMeetWord")
	public ModelAndView editMeetWord(HttpServletRequest request,
			HttpServletResponse response) {
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		String path=request.getParameter("path");
		String user=FunUtil.loginUser(request);
		int type=request.getParameter("type")==null?0:Integer.parseInt(request.getParameter("type"));
		poCtrl.setServerPage(request.getContextPath() +"/poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
		poCtrl.addCustomToolButton("签字", "sign()", 2);
		poCtrl.addCustomToolButton("加盖印章", "Seal()", 2);
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setTitlebar(false); //隐藏标题栏
		poCtrl.setOfficeToolbars(true);//隐藏Office工具条
		poCtrl.setCustomToolbar(true);//隐藏自定义工具栏
		poCtrl.setJsFunction_AfterDocumentOpened("IsFullScreen()");
		String name="";
		try {
			name=URLEncoder.encode(path,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(path.indexOf("/")==0){
			path=path.substring(1);
		}
		poCtrl.setSaveFilePage(request.getContextPath() +"/office/save_page?path="+name);
		//设置页面的显示标题
		//poCtrl.setCaption("");
		//设置并发控制时间
		poCtrl.setTimeSlice(40);
		
		poCtrl.webOpen(request.getSession().getServletContext().getRealPath("/")+"/"+path, OpenModeType.docNormalEdit,FunUtil.loginUserInfo(request).get("userName").toString());
		poCtrl.setTagId("PageOfficeCtrl1");
		
		ModelAndView mv = new ModelAndView("Views/jsp/edit_meet_word");
		mv.addObject("sealName1",WebUserServices.sealName(FunUtil.loginUser(request),"1"));
		mv.addObject("sealName2",WebUserServices.sealName(FunUtil.loginUser(request),"2"));
		mv.addObject("type",type);
		mv.addObject("userName",FunUtil.loginUser(request));
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}
	public void createFile(HttpServletRequest request) {
		String path=request.getSession().getServletContext().getRealPath("")+"/doc/template/扣分.doc";
		String savePath=request.getSession().getServletContext().getRealPath("doc/check/");
		savePath +=FunUtil.nowDateNotTime().split("-")[0];
		
		System.out.println("地址："+savePath);
		File file=new File(savePath);
		if(!file.exists()){
			file.mkdirs();
		}
		
	}
	public void createMeetFile(OperationsCheckBean bean,HttpServletRequest request) {
		String path=request.getSession().getServletContext().getRealPath("")+"/doc/template/meet2.doc";
		String savePath=request.getSession().getServletContext().getRealPath("")+"/doc/check/";
		savePath +=FunUtil.nowDateNotTime().split("-")[0];
		
		System.out.println("地址："+savePath);
		String filePath=savePath+"/"+bean.getFileName();
		File file=new File(savePath);
		if(!file.exists()){
			file.mkdirs();
		}
		File source=new File(path);
		File dst=new File(filePath);
		try {
			FunUtil.copyFile(source, dst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
