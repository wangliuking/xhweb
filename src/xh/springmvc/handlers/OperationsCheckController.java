package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.CheckMoneyBean;
import xh.mybatis.bean.MoneyBean;
import xh.mybatis.bean.OperationsCheckBean;
import xh.mybatis.bean.OperationsCheckDetailBean;
import xh.mybatis.bean.OperationsCheckScoreBean;
import xh.mybatis.bean.ScoreBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.OperationsCheckService;


@Controller
@RequestMapping("/check")
public class OperationsCheckController {
	private boolean success;
	private String message;
	protected final Log log = LogFactory.getLog(LendController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public void data(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/searchDetail", method = RequestMethod.GET)
	public void searchDetail(HttpServletRequest request, HttpServletResponse response) {
		String time=request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		List<CheckMoneyBean> list=OperationsCheckService.searchDetail(time);
		log.info("list-->"+list);
		float sum=0;
		HashMap<String,Object> rs=new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			CheckMoneyBean bean=list.get(i);
			log.info("bean:"+bean);
			rs.put("m_"+bean.getCheck_tag(), bean.getMoney());
			rs.put("n_"+bean.getCheck_tag(), bean.getCheck_note());
			sum+=bean.getMoney();
		}
		
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
		Map<String, Object> map = new HashMap<String, Object>();
		List<OperationsCheckScoreBean> list=OperationsCheckService.searchScore(time);
		log.info("list:"+list);
		float sum=0;
		HashMap<String,Object> rs=new HashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			OperationsCheckScoreBean bean=list.get(i);
			log.info("bean:"+bean);
			rs.put("s_"+bean.getCheck_tag(), bean.getScore());
			rs.put("n_"+bean.getCheck_tag(), bean.getCheck_note());
			sum+=bean.getScore();
		}
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
		
		String comment = request.getParameter("comment");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");
		String month=request.getParameter("time");
		checkBean.setFileName(fileName);
		checkBean.setFilePath(filePath);
		checkBean.setComment(comment);
		checkBean.setCheckMonth(month);
		
		String scoreData = request.getParameter("scoreData");
		String moneyData = request.getParameter("moneyData");
		ScoreBean score=GsonUtil.json2Object(scoreData, ScoreBean.class);
		MoneyBean money=GsonUtil.json2Object(moneyData, MoneyBean.class);
		score.setTime(month);
		money.setTime(month);
		
		int rst=OperationsCheckService.add(checkBean);
		if(rst>=1){
			this.success=true;
			this.message="提交申请成功";
			OperationsCheckService.addScore(score);
			OperationsCheckService.addDetail(money);
		}else{
			this.success=false;
			this.message="提交申请失败";
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
			FunUtil.sendMsgToOneUser(user, "考核", "管理部门已经审核你提交的考核申请信息", request);
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
		int rst=OperationsCheckService.check3(checkBean,detailBean);
		if(rst>=1){
			this.success=true;
			this.message="填写记录成功";
			FunUtil.sendMsgToOneUser(user, "考核", "管理部门已经填写了考核记录，请确认", request);
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
			FunUtil.sendMsgToOneUser(user, "考核", "服务方确认了考核结果", request);
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
	public void upload(@RequestParam("filePath") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {

		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
		String data = FunUtil.MD5(sdf.format(d));

		String path = request.getSession().getServletContext().getRealPath("")
				+ "/Resources/upload/business/";
		String fileName = file.getOriginalFilename();

		/*
		 * File de=new File(fileName); file.renameTo(new File(name+".jpg"));
		 * //改名
		 */

		// String fileName = new Date().getTime()+".jpg";
		log.info("path==>" + path);
		log.info("fileName==>" + fileName);
		System.out.println("fileName==>" + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();

		}
		// 保存
		try {
			file.transferTo(targetFile);
			this.success = true;
			this.message = "文件上传成功";
			fileName = data + fileName;
			File rename = new File(path, fileName);
			targetFile.renameTo(rename);

		} catch (Exception e) {
			e.printStackTrace();
			this.message = "文件上传失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", fileName);
		result.put("filePath", path + "/" + fileName);
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
	

}
