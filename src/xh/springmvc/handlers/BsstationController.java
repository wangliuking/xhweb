package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.AssetInfoBean;
import xh.mybatis.bean.BsElectricityBean;
import xh.mybatis.bean.BsMachineRoomBean;
import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.ChartBean;
import xh.mybatis.bean.ExcelBsInfoBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.bsLinkConfigBean;
import xh.mybatis.bean.bscConfigBean;
import xh.mybatis.bean.bsrConfigBean;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.CallListServices;
import xh.mybatis.service.WebLogService;
import xh.org.listeners.SingLoginListener;
@Controller
@RequestMapping(value="/bs")
public class BsstationController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(BsstationController.class);
	private FlexJSON json=new FlexJSON();
	private BsstationBean bsstationBean=new BsstationBean();
	private WebLogBean webLogBean=new WebLogBean();
	
	/**
	 * 查询基站信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void bsInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String bsId=request.getParameter("bsId");
		String name=request.getParameter("name");
		int type=Integer.parseInt(request.getParameter("type"));
		int level=Integer.parseInt(request.getParameter("level"));
		String zone=request.getParameter("zone");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("bsId", bsId);
		map.put("name", name);
		map.put("type", type);
		map.put("level", level);
		map.put("zone", zone);
		map.put("start", start);
		map.put("limit", limit);
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",BsstationService.bsCount(map));
		result.put("items", BsstationService.bsInfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value="/search_more_bs",method = RequestMethod.GET)
	public void search_more_bs(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String zone=request.getParameter("zone");
		String str=request.getParameter("name");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("zone", zone);
		map.put("str", str);
		
		HashMap result = new HashMap();
		result.put("items",BsstationService.search_more_bs(map));
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
	 * 查询基站信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/bsInfolimit",method = RequestMethod.GET)
	public void bsInfolimit(HttpServletRequest request, HttpServletResponse response){
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",BsstationService.bsInfolimit().size());
		result.put("items", BsstationService.bsInfolimit());
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
	 * 根据基站ID查找基站相邻小区
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/neighborByBsId",method = RequestMethod.GET)
	public void neighborByBsId(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int bsId=Integer.parseInt(request.getParameter("bsId"));		
		HashMap result = new HashMap();
		result.put("totals",BsstationService.neighborByBsId(bsId).size());
		result.put("items", BsstationService.neighborByBsId(bsId));
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
	 * 根据基站ID查找基站切换参数
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/handoverByBsId",method = RequestMethod.GET)
	public void handoverByBsId(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int bsId=Integer.parseInt(request.getParameter("bsId"));		
		HashMap result = new HashMap();
		result.put("totals",BsstationService.handoverByBsId(bsId).size());
		result.put("items", BsstationService.handoverByBsId(bsId));
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
	 * 根据基站ID查找基站BSR配置信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/bsrconfigByBsId",method = RequestMethod.GET)
	public void bsrconfigByBsId(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int bsId=Integer.parseInt(request.getParameter("bsId"));		
		HashMap result = new HashMap();
		result.put("totals",BsstationService.bsrconfigByBsId(bsId).size());
		result.put("items", BsstationService.bsrconfigByBsId(bsId));
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
	 * 根据基站ID查找基站BSC配置信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/bscconfigByBsId",method = RequestMethod.GET)
	public void bscconfigByBsId(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int bsId=Integer.parseInt(request.getParameter("bsId"));		
		HashMap result = new HashMap();
		result.put("totals",BsstationService.bscconfigByBsId(bsId).size());
		result.put("items", BsstationService.bscconfigByBsId(bsId));
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
	 * 根据基站ID查找基站传输配置信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/linkconfigByBsId",method = RequestMethod.GET)
	public void linkconfigByBsId(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int bsId=Integer.parseInt(request.getParameter("bsId"));		
		HashMap result = new HashMap();
		result.put("totals",BsstationService.linkconfigByBsId(bsId).size());
		result.put("items", BsstationService.linkconfigByBsId(bsId));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value="/alarmList",method = RequestMethod.GET)
	public void bsOfflineList(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		List<HashMap<String,Object>> list=BsstationService.bsOfflineList();
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",list.size());
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
	/**
	 * 查询所有基站信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/allBsInfo",method = RequestMethod.GET)
	public void allBsInfo(HttpServletRequest request, HttpServletResponse response){
		int type=Integer.parseInt(request.getParameter("type"));
		String zone=request.getParameter("zone");
		int link=Integer.parseInt(request.getParameter("link"));
		int status=Integer.parseInt(request.getParameter("status"));
		String usergroup=request.getParameter("usergroup");
		String bsId=request.getParameter("bsId");
		List<String> bslist=new ArrayList<String>();
		Map<String,Object> usermap=SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
		String vpnId=usermap.get("vpnId").toString();
		int size=0;
		if(bsId!=""){
			String[] bsIds=bsId.split(",");
			for (String string : bsIds) {
				bslist.add(string);
			}
			size=bslist.size();
		}
		
		Map<String,Object> paramMap=new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("zone", zone);
		paramMap.put("link", link);
		paramMap.put("status",status);
		paramMap.put("usergroup",usergroup);
		paramMap.put("bslist",bslist);
		paramMap.put("size",size);
		paramMap.put("vpnId",vpnId);
		
		
		List<HashMap<String, Object>> list=	 BsstationService.allBsInfo(paramMap);
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
	@RequestMapping(value="/select_bs_by_bsid",method = RequestMethod.GET)
	public void select_bs_by_bsid(
			@RequestParam("bsId") int bsId,
			HttpServletRequest request, 
			HttpServletResponse response){
		HashMap result = new HashMap();
		result.put("items", BsstationService.select_bs_by_bsid(bsId));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value="/bs_business_info",method = RequestMethod.GET)
	public void bs_business_info(HttpServletRequest request, HttpServletResponse response){
		/*int type=Integer.parseInt(request.getParameter("type"));*/
		String zone=request.getParameter("zone");
		String sort_type=request.getParameter("sort_type");
		String sort_field=request.getParameter("sort_field");
		/*int link=Integer.parseInt(request.getParameter("link"));
		int status=Integer.parseInt(request.getParameter("status"));*/
		/*String usergroup=request.getParameter("usergroup");*/
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		String bsId=request.getParameter("bsId");
		List<String> bslist=new ArrayList<String>();
		Map<String,Object> usermap=SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
		String vpnId=usermap.get("vpnId").toString();
		int size=0;
		if(bsId!=""){
			String[] bsIds=bsId.split(",");
			for (String string : bsIds) {
				bslist.add(string);
			}
			size=bslist.size();
		}
		
		Map<String,Object> paramMap=new HashMap<String, Object>();
		/*paramMap.put("type", type);*/
		paramMap.put("zone", zone);
		paramMap.put("sort_type", sort_type);
		paramMap.put("sort_field",sort_field);
		/*paramMap.put("usergroup",usergroup);*/
		paramMap.put("start", start);
		paramMap.put("limit", limit);
		paramMap.put("bslist",bslist);
		paramMap.put("size",size);
		paramMap.put("vpnId",vpnId);
		
		
		List<HashMap<String, Object>> list=	 BsstationService.bs_business_info(paramMap);
		HashMap result = new HashMap();
		result.put("totals", BsstationService.bs_business_info_count(paramMap));
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
	/**
	 * 基站限制列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/bslimitList",method = RequestMethod.GET)
	public void bslimitList(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",BsstationService.bslimitListCount());
		result.put("items", BsstationService.bslimitList(map));
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
	 * 基站限制列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/addBsLimit",method = RequestMethod.POST)
	public void addBsLimit(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String[] bsIds=request.getParameter("bsIds").split(",");
		List<String> list=new ArrayList<String>();
		for (String string : bsIds) {
			list.add(string);
		}
		BsstationService.deleteBsLimit(list);
        int resultCode=BsstationService.addBsLimit(list);
        if(resultCode>0){
        	this.success=true;
        	this.message="添加成功";
        }else{
        	this.message="添加失败";
        	this.success=false;
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
	 * 基站限制列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteBsLimit",method = RequestMethod.POST)
	public void deleteBsLimit(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String[] bsIds=request.getParameter("bsIds").split(",");
		List<String> list=new ArrayList<String>();
		for (String string : bsIds) {
			list.add(string);
		}
        int resultCode=BsstationService.deleteBsLimit(list);
        if(resultCode>0){
        	this.success=true;
        	this.message="删除成功";
        }else{
        	this.message="删除失败";
        	this.success=false;
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
	/**
	 * 修改基站切换参数
	 * @param request
	 * @param response
	 */
	
	@RequestMapping(value="/updateBsHandover",method = RequestMethod.POST)
	public void  updateBsHandover(HttpServletRequest request, HttpServletResponse response){
		int bsId=Integer.parseInt(request.getParameter("bsId"));
		String minVol =request.getParameter("minVol");
		String slowReelectThreshold=request.getParameter("slowReelectThreshold");
		String fastReelectThreshold=request.getParameter("fastReelectThreshold");
		String slowReelectDelay=request.getParameter("slowReelectDelay");
		String fastReelectDelay=request.getParameter("fastReelectDelay");
		Map<String,Object> paramMap=new HashMap<String, Object>();
		paramMap.put("bsId", bsId);
		paramMap.put("minVol", minVol);
		paramMap.put("slowReelectThreshold", slowReelectThreshold);
		paramMap.put("fastReelectThreshold", fastReelectThreshold);
		paramMap.put("slowReelectDelay",slowReelectDelay);
		paramMap.put("fastReelectDelay",fastReelectDelay);
		
		int rslt=0;
		
		List<Map<String,Object>> list=BsstationService.handoverByBsId(bsId);
		
		if(list.size()>0){
			rslt=BsstationService.updateBsHandover(paramMap);
		}else{
			rslt=BsstationService.addBsHandover(paramMap);
		}
		if(rslt==1){
			this.message="基站切换参数修改成功";
			this.success=true;
		}else{
			this.message="修改失败";
			this.success=true;
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
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
	 * 添加基站
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ResponseBody
	public void addBs(HttpServletRequest request, HttpServletResponse response){
		String jsonData=request.getParameter("formData");
        BsstationBean bean=GsonUtil.json2Object(jsonData, BsstationBean.class);
        BsElectricityBean bean2=GsonUtil.json2Object(jsonData, BsElectricityBean.class);
        BsMachineRoomBean bean3=GsonUtil.json2Object(jsonData, BsMachineRoomBean.class);
        /*if (bean.getOpenen()==0) {
			bean.setStatus(2);
		}*/
        log.info("data1==>"+bean);
        log.info("data2==>"+bean2);
        log.info("data3==>"+bean3);
		int count=BsstationService.insertBs(bean, bean3, bean2);
		
		if (count>0) {
			this.success=true;
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("新增基站，bsId="+bean.getBsId()+";name="+bean.getName());
			WebLogService.writeLog(webLogBean);
		}else{
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result",count);
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
	 * 添加基站相邻小区
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/addBsNeighbor",method = RequestMethod.POST)
	@ResponseBody
	public void addBsNeighbor(HttpServletRequest request, HttpServletResponse response){
		int bsId=funUtil.StringToInt(request.getParameter("bsId"));
		int adjacentCellId=funUtil.StringToInt(request.getParameter("adjacentCellId"));
		Map<String,Object> paramterMap=new HashMap<String, Object>();
		paramterMap.put("bsId", bsId);
		paramterMap.put("adjacentCellId", adjacentCellId);
		int rslt=-1;
		if (BsstationService.neighborExists(paramterMap)==0) {
			rslt=BsstationService.addBsNeighbor(paramterMap);
			if(rslt==1){
				webLogBean.setOperator(funUtil.loginUser(request));
				webLogBean.setOperatorIp(funUtil.getIpAddr(request));
				webLogBean.setStyle(1);
				webLogBean.setContent("新增基站相邻小区，bsId="+bsId+";adjacentCellId="+adjacentCellId);
				WebLogService.writeLog(webLogBean);
				this.success=true;
				this.message="相邻小区添加成功";
			}else{
				this.message="添加失败";
				this.success=false;
			}
			
		}else{
			this.message="改临近小区已经存在";
			this.success=false;
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
	 * 删除相邻小区
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/delBsNeighbor",method = RequestMethod.POST)
	@ResponseBody
	public void delBsNeighbor(HttpServletRequest request, HttpServletResponse response){
		int bsId=funUtil.StringToInt(request.getParameter("bsId"));
		int adjacentCellId=funUtil.StringToInt(request.getParameter("adjacentCellId"));
		Map<String,Object> paramterMap=new HashMap<String, Object>();
		paramterMap.put("bsId", bsId);
		paramterMap.put("adjacentCellId", adjacentCellId);

		int rslt=BsstationService.delBsNeighbor(paramterMap);
		if (rslt==1) {
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(3);
			webLogBean.setContent("删除基站相邻小区，bsId="+bsId+";adjacentCellId="+adjacentCellId);
			WebLogService.writeLog(webLogBean);
			this.message="删除成功";
			this.success=true;
		}else{
			this.message="删除失败";
			this.success=false;
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
	 *  新增基站传输
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/addLinkconfig",method = RequestMethod.POST)
	@ResponseBody
	public void addLinkconfig(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		bsLinkConfigBean bean=GsonUtil.json2Object(formData, bsLinkConfigBean.class);
		
		
		
		
		Map<String,Object> paramterMap=new HashMap<String, Object>();
		paramterMap.put("bsId", bean.getBsId());
		paramterMap.put("regulation_number", bean.getRegulation_number());
		int rslt=-1;
		if (BsstationService.linkconfigExists(paramterMap)==0) {
			rslt=BsstationService.addLinkconfig(bean);
			if(rslt==1){
				webLogBean.setOperator(funUtil.loginUser(request));
				webLogBean.setOperatorIp(funUtil.getIpAddr(request));
				webLogBean.setStyle(1);
				webLogBean.setContent("新增基站传输配置，bsId="+bean.getBsId());
				WebLogService.writeLog(webLogBean);
				this.success=true;
				this.message="基站传输配置添加成功";
			}else{
				this.message="添加失败";
				this.success=false;
			}
			
		}else{
			this.message="该基站传输配置已经存在";
			this.success=false;
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
	 * 删除基站传输
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/delLinkconfig",method = RequestMethod.POST)
	@ResponseBody
	public void delLinkconfig(HttpServletRequest request, HttpServletResponse response){
		int id=funUtil.StringToInt(request.getParameter("id"));

		int rslt=BsstationService.delLinkconfig(id);
		if (rslt==1) {
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(3);
			webLogBean.setContent(" 删除基站传输，id="+id);
			WebLogService.writeLog(webLogBean);
			this.message="删除成功";
			this.success=true;
		}else{
			this.message="删除失败";
			this.success=false;
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
	 *  新增基站bsr
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/addBsrconfig",method = RequestMethod.POST)
	@ResponseBody
	public void addBsrconfig(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		bsrConfigBean bean=GsonUtil.json2Object(formData, bsrConfigBean.class);
				Map<String,Object> paramterMap=new HashMap<String, Object>();
		paramterMap.put("bsId", bean.getBsId());
		paramterMap.put("bsrId", bean.getBsrId());
		int rslt=-1;
		if (BsstationService.bsrconfigExists(paramterMap)==0) {
			rslt=BsstationService.addBsrconfig(bean);
			if(rslt==1){
				webLogBean.setOperator(funUtil.loginUser(request));
				webLogBean.setOperatorIp(funUtil.getIpAddr(request));
				webLogBean.setStyle(1);
				webLogBean.setContent("新增基站bsr配置");
				WebLogService.writeLog(webLogBean);
				this.success=true;
				this.message="基站bsr配置添加成功";
			}else{
				this.message="添加失败";
				this.success=false;
			}
			
		}else{
			this.message="该基站bsr配置已经存在";
			this.success=false;
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
	 *  新增基站bsc
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/addBscconfig",method = RequestMethod.POST)
	@ResponseBody
	public void addBscconfig(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		bscConfigBean bean=GsonUtil.json2Object(formData, bscConfigBean.class);
				Map<String,Object> paramterMap=new HashMap<String, Object>();
		paramterMap.put("bsId", bean.getBsId());
		paramterMap.put("bscId", bean.getBscId());
		int rslt=-1;
		if (BsstationService.bscconfigExists(paramterMap)==0) {
			rslt=BsstationService.addBscconfig(bean);
			if(rslt==1){
				webLogBean.setOperator(funUtil.loginUser(request));
				webLogBean.setOperatorIp(funUtil.getIpAddr(request));
				webLogBean.setStyle(1);
				webLogBean.setContent("新增基站bsc配置");
				WebLogService.writeLog(webLogBean);
				this.success=true;
				this.message="基站bsc配置添加成功";
			}else{
				this.message="添加失败";
				this.success=false;
			}
			
		}else{
			this.message="该基站bsc配置已经存在";
			this.success=false;
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
	 * 删除基站bsr
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/delBsrconfig",method = RequestMethod.POST)
	@ResponseBody
	public void delBsrconfig(HttpServletRequest request, HttpServletResponse response){
		int id=funUtil.StringToInt(request.getParameter("id"));

		int rslt=BsstationService.delBsrconfig(id);
		if (rslt==1) {
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(3);
			webLogBean.setContent(" 删除基站bsr，id="+id);
			WebLogService.writeLog(webLogBean);
			this.message="删除成功";
			this.success=true;
		}else{
			this.message="删除失败";
			this.success=false;
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
	 * 删除基站bsc
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/delBscconfig",method = RequestMethod.POST)
	@ResponseBody
	public void delBscconfig(HttpServletRequest request, HttpServletResponse response){
		int id=funUtil.StringToInt(request.getParameter("id"));

		int rslt=BsstationService.delBscconfig(id);
		if (rslt==1) {
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(3);
			webLogBean.setContent(" 删除基站bsc，id="+id);
			WebLogService.writeLog(webLogBean);
			this.message="删除成功";
			this.success=true;
		}else{
			this.message="删除失败";
			this.success=false;
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
	 * 修改基站
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public void updateBs(HttpServletRequest request, HttpServletResponse response){
		String jsonData=request.getParameter("formData");
		BsstationBean bean=GsonUtil.json2Object(jsonData, BsstationBean.class);
	    BsElectricityBean bean2=GsonUtil.json2Object(jsonData, BsElectricityBean.class);
	    BsMachineRoomBean bean3=GsonUtil.json2Object(jsonData, BsMachineRoomBean.class);
	        log.info("data1==>"+bean);
	        log.info("data2==>"+bean2);
	        log.info("data3==>"+bean3);
		int count=BsstationService.updateBs(bean,bean3,bean2);
		if (count>0) {
			this.success=true;
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改基站，bsId="+bean.getBsId()+";name="+bean.getName());
			WebLogService.writeLog(webLogBean);
		}else{
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result",count);
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
	 * 删除基站
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/del",method = RequestMethod.POST)
	public void deleteBs(HttpServletRequest request, HttpServletResponse response){
		String bsId=request.getParameter("bsId");
		List<String> list = new ArrayList<String>();
		String[] ids=bsId.split(",");
		for (String str : ids) {
			list.add(str);
		}
		BsstationService.deleteBsByBsId(list);
		webLogBean.setOperator(funUtil.loginUser(request));
		webLogBean.setOperatorIp(funUtil.getIpAddr(request));
		webLogBean.setStyle(3);
		webLogBean.setContent("删除基站，bsId="+bsId);
		WebLogService.writeLog(webLogBean);
		HashMap result = new HashMap();
		this.success=true;
		result.put("success", success);
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
	 * 根据所选区域查询所有基站信息
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/bsByArea")
	@ResponseBody
	public void bsByArea(HttpServletRequest request, HttpServletResponse response){	
		try {
			HashMap map = new HashMap();
			BsstationService bsStationService = new BsstationService();
			String temp = request.getParameter("zone");			
			//byte[] b=temp.getBytes("ISO-8859-1");
			//String test=new String(b,"utf-8");
			String[] zonetemp = temp.split(",");
			List<String> zone = Arrays.asList(zonetemp);
			List<HashMap<String, String>> listMap = bsStationService.bsByArea(zone);
			map.put("items", listMap);
			String dataMap = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据所选级别查询所有基站信息
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/bsByLevel")
	@ResponseBody
	public void bsByLevel(HttpServletRequest request, HttpServletResponse response){	
		try {
			HashMap map = new HashMap();
			BsstationService bsStationService = new BsstationService();
			String temp = request.getParameter("level");
			byte[] b=temp.getBytes("ISO-8859-1");
			String level=new String(b,"utf-8");
			List<HashMap<String, String>> listMap = bsStationService.bsByLevel(level);
			map.put("items", listMap);
			String dataMap = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询基站区域信息
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/area")
	@ResponseBody
	public void selectAllArea(HttpServletRequest request, HttpServletResponse response){
		HashMap map = new HashMap();
		BsstationService bsStationService = new BsstationService();
		try {
			List<HashMap<String, String>> listMap = bsStationService.selectAllArea();
			map.put("items", listMap);
			String dataMap = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询基站级别信息
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/level")
	@ResponseBody
	public void selectLevel(HttpServletRequest request, HttpServletResponse response){
		HashMap map = new HashMap();
		BsstationService bsStationService = new BsstationService();
		try {
			List<HashMap<String, String>> listMap = bsStationService.selectLevel();
			map.put("items", listMap);
			String dataMap = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询所有基站信息，用于首页显示
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/map/data",method=RequestMethod.GET)
	@ResponseBody
	public void selectAllBsStation(HttpServletRequest request, HttpServletResponse response){
		HashMap map = new HashMap();
		BsstationService bsStationService = new BsstationService();
		try {
			List<HashMap<String, String>> listMap = bsStationService.selectAllBsStation();
			map.put("items", listMap);
			String dataMap = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 根据ID查询选中基站的信息，用于首页模态框显示
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/map/dataById",method=RequestMethod.GET)
	@ResponseBody
	public void selectBsStationById(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,List<HashMap<String, String>>> map = new HashMap<String,List<HashMap<String, String>>>();
		BsstationService bsStationService = new BsstationService();
		String str = request.getParameter("bsId");
		int bsId = Integer.parseInt(str);
		try {
			List<HashMap<String, String>> bsStationBean = bsStationService.selectBsStationById(bsId);
			map.put("items", bsStationBean);
			String dataById = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataById);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询单个基站话务量及其他业务
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/top5Calllist")
	@ResponseBody
	public void selectCalllist(HttpServletRequest request, HttpServletResponse response){	
		try {
			Calendar cal = Calendar.getInstance();
			int temp = cal.get(Calendar.MONTH)+1;
			String currentMonth;
			if(temp<10){
				currentMonth="0"+temp;
			}else{
				currentMonth=Integer.toString(temp);
			}
			currentMonth="xhgmnet_calllist"+currentMonth;
			HashMap map = new HashMap();
			BsstationService bsStationService = new BsstationService();
			List<HashMap<String, String>> listMap = bsStationService.selectCalllistById(currentMonth);
			map.put("items", listMap);
			String dataMap = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询单个基站排队数及其他业务
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/top5Channel")
	@ResponseBody
	public void selectChannel(HttpServletRequest request, HttpServletResponse response){	
		try {
			HashMap map = new HashMap();
			BsstationService bsStationService = new BsstationService();
			List<HashMap<String, String>> listMap = bsStationService.selectChannelById();
			map.put("items", listMap);
			String dataMap = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * 查询路测数据
	 * @author wlk
	 * @param request
	 * @param response
	 */
	@RequestMapping("/map/roadtest")
	@ResponseBody
	public void selectRoadTest(HttpServletRequest request, HttpServletResponse response){	
		try {
			HashMap map = new HashMap();
			BsstationService bsStationService = new BsstationService();
			List<HashMap<String, String>> listMap = bsStationService.selectRoadTest();
			map.put("items", listMap);
			String dataMap = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 圈选功能查询
	 */
	@RequestMapping("/rectangle")
	@ResponseBody
	public void rectangle(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String params=request.getParameter("params");
		String [] strArray= params.split(",");
		List<String> groupData = Arrays.asList(strArray);
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("groupData", groupData);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",BsstationService.rectangleCount(map));
		result.put("items", BsstationService.rectangle(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value = "/excel_bs_info", method = RequestMethod.GET)
	public void excel_bs_info(@RequestParam("period") int period,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String time=request.getParameter("time");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		map.put("period", period);
		
		excel_bs_info_period(time,0,request);
		excel_bs_info_period(time,3,request);
		excel_bs_info_period(time,4,request);
		
		
		String rootDir = request.getSession().getServletContext().getRealPath("/upload/checksource");
		String sourceFilePath=rootDir + "/基站信息表["+time+"].xls";
		
		String destDir3=request.getSession().getServletContext().getRealPath("/upload/checksource");
		destDir3=destDir3+"/"+time.split("-")[0]+"/"+time.split("-")[1]+"/3";
		String destDir4=request.getSession().getServletContext().getRealPath("/upload/checksource");
		destDir4=destDir4+"/"+time.split("-")[0]+"/"+time.split("-")[1]+"/4";
		File Path3 = new File(destDir3);
		if (!Path3.exists()) {
			Path3.mkdirs();
		}
		File Path4 = new File(destDir4);
		if (!Path4.exists()) {
			Path4.mkdirs();
		}
		File file3 = new File(destDir3+"/基站信息表.xls");
		File file4 = new File(destDir4+"/基站信息表.xls");
		File srcFile3 = new File(rootDir + "/基站信息表["+time+"](三期).xls");
		File srcFile4 = new File(rootDir + "/基站信息表["+time+"](四期).xls");
		FunUtil.copyFile(srcFile3, file3);
		FunUtil.copyFile(srcFile4, file4);

		 HashMap<String, Object> result = new HashMap<String, Object>();
		 result.put("success", true);
		 if(period==0){
			 result.put("pathName", sourceFilePath);
		 }else if(period==3){
			 result.put("pathName", rootDir + "/基站信息表["+time+"](三期).xls");
		 }else{
			 result.put("pathName", rootDir + "/基站信息表["+time+"](四期).xls");
		 }
		 
		 response.setContentType("application/json;charset=utf-8"); 
		 String jsonstr = json.Encode(result); 
		 response.getWriter().write(jsonstr);
	}
	public void excel_bs_info_period(String time,int period,HttpServletRequest request) throws Exception{
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		try {
			String saveDir = request.getSession().getServletContext().getRealPath("/upload/checksource");			
			String pathname = "";
			if(period==0){
				pathname = saveDir + "/基站信息表["+time+"].xls";
			}else{
				pathname = saveDir + "/基站信息表["+time+"]("+(period==3?'三':'四')+"期).xls";
			}
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);			
			WritableWorkbook book = Workbook.createWorkbook(file);

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(true);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					9, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体
			WritableCellFormat fontFormat_Content = new WritableCellFormat(font_Content);
			// 设置其他样式
			fontFormat_Content.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行
		
			WritableSheet sheet0 = book.createSheet("基站信息表", 0);
			WritableSheet sheet1 = book.createSheet("基本信息考核表", 1);
			WritableSheet sheet2 = book.createSheet("基站信息表-传输链路", 2);
			WritableSheet sheet3 = book.createSheet("基站信息表-供配电", 3);
			WritableSheet sheet4 = book.createSheet("基站信息表-机房配套", 4);
			
			List<ExcelBsInfoBean> list = BsstationService.excel_bs_info(period);
			
			excel_bs_info(sheet0,list,fontFormat_h,fontFormat_Content);
			excel_bs_check(sheet1,list,fontFormat_h,fontFormat_Content);
			excel_bs_transfer(sheet2,list,fontFormat_h,fontFormat_Content);
			excel_bs_elect(sheet3,list,fontFormat_h,fontFormat_Content);
			excel_bs_room(sheet4,list,fontFormat_h,fontFormat_Content);
			
			
			book.write();
			book.close();
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public  void excel_bs_info(WritableSheet sheet,List<ExcelBsInfoBean> list,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content) {
		try {
			sheet.addCell(new Label(0,0,"基站信息表",fontFormat_h));
			sheet.mergeCells(0, 0, 10, 0);
			sheet.addCell(new Label(0, 1, "基站ID", fontFormat_h));
			sheet.addCell(new Label(1, 1, "基站名称", fontFormat_h));
			sheet.addCell(new Label(2, 1, "基站分级", fontFormat_h));
			sheet.addCell(new Label(3, 1, "建设周期", fontFormat_h));
			sheet.addCell(new Label(4, 1, "设备类型", fontFormat_h));
			sheet.addCell(new Label(5, 1, "区域", fontFormat_h));
			sheet.addCell(new Label(6, 1, "基站地址", fontFormat_h));
			sheet.addCell(new Label(7, 1, "运营商机房名", fontFormat_h));
			sheet.addCell(new Label(8, 1, "经度", fontFormat_h));
			sheet.addCell(new Label(9, 1, "纬度", fontFormat_h));
			sheet.addCell(new Label(10, 1, "海拔/米", fontFormat_h));
			sheet.addCell(new Label(11, 1, "是否与移动基站共址", fontFormat_h));
			sheet.addCell(new Label(12, 1, "机房类型", fontFormat_h));
			sheet.addCell(new Label(13, 1, "机柜类型", fontFormat_h));
			sheet.addCell(new Label(14, 1, "基站设备型号", fontFormat_h));
			sheet.addCell(new Label(15, 1, "基站设备厂家", fontFormat_h));
			sheet.addCell(new Label(16, 1, "基站状态", fontFormat_h));
			sheet.addCell(new Label(17, 1, "规划载频数", fontFormat_h));
			sheet.addCell(new Label(18, 1, "开启载频数", fontFormat_h));
			sheet.addCell(new Label(19, 1, "基站发射功率/dBm", fontFormat_h));
			
			
			
			sheet.addCell(new Label(20, 1, "基础频率1", fontFormat_h));
			sheet.addCell(new Label(21, 1, "基础频率2", fontFormat_h));
			sheet.addCell(new Label(22, 1, "基础频率3", fontFormat_h));
			sheet.addCell(new Label(23, 1, "基础频率4", fontFormat_h));
			sheet.addCell(new Label(24, 1, "扩容频率1", fontFormat_h));
			sheet.addCell(new Label(25, 1, "扩容频率2", fontFormat_h));
			sheet.addCell(new Label(26, 1, "扩容频率3", fontFormat_h));
			sheet.addCell(new Label(27, 1, "扩容频率4", fontFormat_h));
			
			sheet.addCell(new Label(28, 1, "天线型号", fontFormat_h));
			sheet.addCell(new Label(29, 1, "天线增益/dBi", fontFormat_h));
			sheet.addCell(new Label(30, 1, "天线安装方式", fontFormat_h));
			sheet.addCell(new Label(31, 1, "天线挂高(米）", fontFormat_h));
			sheet.addCell(new Label(32, 1, "基站IP地址", fontFormat_h));
			sheet.addCell(new Label(33, 1, "开通模式", fontFormat_h));
			sheet.addCell(new Label(34, 1, "传输方式", fontFormat_h));
			sheet.addCell(new Label(35, 1, "是否同一运营商", fontFormat_h));
			sheet.addCell(new Label(36, 1, "是否同网元", fontFormat_h));
			sheet.addCell(new Label(37, 1, "第一传输开通情况", fontFormat_h));
			sheet.addCell(new Label(38, 1, "第一传输运营商", fontFormat_h));
			sheet.addCell(new Label(39, 1, "第一传输设备型号", fontFormat_h));
			sheet.addCell(new Label(40, 1, "第一传输基站网元", fontFormat_h));
			sheet.addCell(new Label(41, 1, "第一传输基站端口", fontFormat_h));
			sheet.addCell(new Label(42, 1, "第一传输交换中心网元", fontFormat_h));
			sheet.addCell(new Label(43, 1, "第一传输交换中心网元端口", fontFormat_h));
			sheet.addCell(new Label(44, 1, "第一传输电路调单号", fontFormat_h));
			
			sheet.addCell(new Label(45, 1, "第二传输开通情况", fontFormat_h));
			sheet.addCell(new Label(46, 1, "第二传输运营商", fontFormat_h));
			sheet.addCell(new Label(47, 1, "第二传输设备型号", fontFormat_h));
			sheet.addCell(new Label(48, 1, "第二传输基站网元", fontFormat_h));
			sheet.addCell(new Label(49, 1, "第二传输基站端口", fontFormat_h));
			sheet.addCell(new Label(50, 1, "第二传输交换中心网元", fontFormat_h));
			sheet.addCell(new Label(51, 1, "第二传输交换中心网元端口", fontFormat_h));
			sheet.addCell(new Label(52, 1, "第二传输电路调单号", fontFormat_h));
			
			sheet.addCell(new Label(53, 1, "基站主设备供电类型", fontFormat_h));
			sheet.addCell(new Label(54, 1, "基站主设备电源下电方式", fontFormat_h));
			sheet.addCell(new Label(55, 1, "传输设备供电类型", fontFormat_h));
			sheet.addCell(new Label(56, 1, "传输设备电源下电方式", fontFormat_h));
			sheet.addCell(new Label(57, 1, "环控设备供电类型", fontFormat_h));
			sheet.addCell(new Label(58, 1, "环控设备电源下电方式", fontFormat_h));
			sheet.addCell(new Label(59, 1, "是否具有备用电源", fontFormat_h));
			sheet.addCell(new Label(60, 1, "后备电源说明", fontFormat_h));
			sheet.addCell(new Label(61, 1, "基站主设备要求持续续航时间（小时）", fontFormat_h));
			sheet.addCell(new Label(62, 1, "基站主设备实际持续续航时间（小时）", fontFormat_h));
			sheet.addCell(new Label(63, 1, "传输设备要求持续续航时间（小时）", fontFormat_h));
			sheet.addCell(new Label(64, 1, "传输设备实际持续续航时间（小时）", fontFormat_h));
			sheet.addCell(new Label(65, 1, "环控设备要求持续续航时间（小时）", fontFormat_h));
			sheet.addCell(new Label(66, 1, "环控设备实际持续续航时间（小时）", fontFormat_h));
			sheet.addCell(new Label(67, 1, "是否配备发电油机", fontFormat_h));
			sheet.addCell(new Label(68, 1, "配备油机说明", fontFormat_h));
			sheet.addCell(new Label(69, 1, "是否允许临时性发电", fontFormat_h));
			sheet.addCell(new Label(70, 1, "发电时间", fontFormat_h));
			sheet.addCell(new Label(71, 1, "上站时间", fontFormat_h));
			sheet.addCell(new Label(72, 1, "上站发电所需时间", fontFormat_h));
			
			
			
			sheet.addCell(new Label(73, 1, "环控通断", fontFormat_h));
			sheet.addCell(new Label(74, 1, "环控通断说明", fontFormat_h));
			sheet.addCell(new Label(75, 1, "门磁", fontFormat_h));
			sheet.addCell(new Label(76, 1, "摄像头", fontFormat_h));
			sheet.addCell(new Label(77, 1, "温湿度传感器", fontFormat_h));
			sheet.addCell(new Label(78, 1, "烟雾传感器", fontFormat_h));
			sheet.addCell(new Label(79, 1, "直流电流电压", fontFormat_h));
			sheet.addCell(new Label(80, 1, "交流电流电压", fontFormat_h));
			sheet.addCell(new Label(81, 1, "漏水检测", fontFormat_h));
			sheet.addCell(new Label(82, 1, "消防", fontFormat_h));
			sheet.addCell(new Label(83, 1, "消防说明", fontFormat_h));
			sheet.addCell(new Label(84, 1, "空调", fontFormat_h));
			sheet.addCell(new Label(85, 1, "空调说明", fontFormat_h));
			sheet.addCell(new Label(86, 1, "防雷接地", fontFormat_h));
			sheet.addCell(new Label(87, 1, "备注", fontFormat_h));
			sheet.setRowView(0, 500);
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			
			
			for(int i=2;i<6;i++){
				sheet.setColumnView(i, 10);
			}			
			sheet.setColumnView(6, 30);
			for(int i=7;i<28;i++){
				sheet.setColumnView(i, 10);
			}
			sheet.setColumnView(28, 20);
			for(int i=29;i<68;i++){
				sheet.setColumnView(i, 10);
			}
			sheet.setColumnView(68, 30);
			for(int i=69;i<83;i++){
				sheet.setColumnView(i, 10);
			}
			sheet.setColumnView(84, 20);
			sheet.setColumnView(85, 10);
			sheet.setColumnView(86, 20);
			sheet.setColumnView(87, 20);
						
			for (int i = 0; i < list.size(); i++) {			
				ExcelBsInfoBean bean =list.get(i);
				sheet.addCell(new jxl.write.Number(0, 2+i, bean.getBsId(), fontFormat_Content));
				sheet.addCell(new Label(1, 2+i, bean.getName(), fontFormat_Content));
				sheet.addCell(new Label(2, 2+i, bean.getLevel(), fontFormat_Content));
				sheet.addCell(new Label(3, 2+i, bean.getPeriod(), fontFormat_Content));
				if(bean.getType()!=null && bean.getType()!=""){
					String str="";
					if(bean.getType().equals("0")){
						str="固定基站";
					}else if(bean.getType().equals("1")){
						str="室分信源";
					}else if(bean.getType().equals("2")){
						str="应急站";
					}else{
						
					}
					sheet.addCell(new Label(4, 2+i, str, fontFormat_Content));
				}else{
					sheet.addCell(new Label(4, 2+i, "", fontFormat_Content));
				}
				sheet.addCell(new Label(5, 2+i, bean.getZone(), fontFormat_Content));
				sheet.addCell(new Label(6, 2+i, bean.getAddress(), fontFormat_Content));
				sheet.addCell(new Label(7, 2+i, bean.getCarrierName(), fontFormat_Content));
				sheet.addCell(new Label(8, 2+i, bean.getLng(), fontFormat_Content));
				sheet.addCell(new Label(9, 2+i, bean.getLat(), fontFormat_Content));
				sheet.addCell(new Label(10, 2+i, bean.getHeight(), fontFormat_Content));
				sheet.addCell(new Label(11, 2+i, bean.getIs_same_address(), fontFormat_Content));
				sheet.addCell(new Label(12, 2+i, bean.getHometype(), fontFormat_Content));
				sheet.addCell(new Label(13, 2+i, bean.getCabinet_type(), fontFormat_Content));
				sheet.addCell(new Label(14, 2+i, bean.getDeviceType(), fontFormat_Content));
				sheet.addCell(new Label(15, 2+i, bean.getProductor(), fontFormat_Content));
				if(bean.getStatus()!=null && bean.getStatus()!=""){
					sheet.addCell(new Label(16, 2+i, bean.getStatus().equals("1")?"启用":"未启用", fontFormat_Content));
				}else{
					sheet.addCell(new Label(16, 2+i, "", fontFormat_Content));
				}
				
				sheet.addCell(new Label(17, 2+i, bean.getChnumber(), fontFormat_Content));
				sheet.addCell(new Label(18, 2+i, bean.getNumber(), fontFormat_Content));
				sheet.addCell(new Label(19, 2+i, bean.getPower(), fontFormat_Content));
				
				sheet.addCell(new Label(20, 2+i, bean.getFreg1(), fontFormat_Content));
				sheet.addCell(new Label(21, 2+i, bean.getFreg2(), fontFormat_Content));
				sheet.addCell(new Label(22, 2+i, bean.getFreg3(), fontFormat_Content));
				sheet.addCell(new Label(23, 2+i, bean.getFreg4(), fontFormat_Content));
				sheet.addCell(new Label(24, 2+i, bean.getFreg5(), fontFormat_Content));
				sheet.addCell(new Label(25, 2+i, bean.getFreg6(), fontFormat_Content));
				sheet.addCell(new Label(26, 2+i, bean.getFreg7(), fontFormat_Content));
				sheet.addCell(new Label(27, 2+i, bean.getFreg8(), fontFormat_Content));
				
				
				
				
				sheet.addCell(new Label(28, 2+i, bean.getLine_model(), fontFormat_Content));
				sheet.addCell(new Label(29, 2+i, bean.getLine_dbi(), fontFormat_Content));
				sheet.addCell(new Label(30, 2+i, bean.getLineInstallType(), fontFormat_Content));
				sheet.addCell(new Label(31, 2+i, bean.getLineHeight(), fontFormat_Content));
				sheet.addCell(new Label(32, 2+i, bean.getIp(), fontFormat_Content));
				sheet.addCell(new Label(33, 2+i, bean.getTransfer_open_model(), fontFormat_Content));
				sheet.addCell(new Label(34, 2+i, bean.getTransfer_type(), fontFormat_Content));
				sheet.addCell(new Label(35, 2+i, bean.getIs_same_carrieroperator(), fontFormat_Content));
				sheet.addCell(new Label(36, 2+i, bean.getIs_same_net(), fontFormat_Content));
				
				//第一传输
				sheet.addCell(new Label(37, 2+i, bean.getIs_open(), fontFormat_Content));
				sheet.addCell(new Label(38, 2+i, bean.getOperator(), fontFormat_Content));
				sheet.addCell(new Label(39, 2+i, bean.getEquipment_model1(), fontFormat_Content));
				sheet.addCell(new Label(40, 2+i, bean.getBs_net(), fontFormat_Content));
				sheet.addCell(new Label(41, 2+i, bean.getBs_net_port(), fontFormat_Content));
				sheet.addCell(new Label(42, 2+i, bean.getMsc_net(), fontFormat_Content));
				sheet.addCell(new Label(43, 2+i, bean.getMsc_net_port(), fontFormat_Content));
				sheet.addCell(new Label(44, 2+i, bean.getRegulation_number(), fontFormat_Content));
				//第二传输
				sheet.addCell(new Label(45, 2+i, bean.getIs_open1(), fontFormat_Content));
				sheet.addCell(new Label(46, 2+i, bean.getOperator1(), fontFormat_Content));
				sheet.addCell(new Label(47, 2+i, bean.getEquipment_model2(), fontFormat_Content));
				sheet.addCell(new Label(48, 2+i, bean.getBs_net1(), fontFormat_Content));
				sheet.addCell(new Label(49, 2+i, bean.getBs_net_port1(), fontFormat_Content));
				sheet.addCell(new Label(50, 2+i, bean.getMsc_net1(), fontFormat_Content));
				sheet.addCell(new Label(51, 2+i, bean.getMsc_net_port1(), fontFormat_Content));
				sheet.addCell(new Label(52, 2+i, bean.getRegulation_number1(), fontFormat_Content));
				
				sheet.addCell(new Label(53, 2+i, bean.getBs_supply_electricity_type(), fontFormat_Content));
				sheet.addCell(new Label(54, 2+i, bean.getBs_power_down_type(), fontFormat_Content));
				sheet.addCell(new Label(55, 2+i, bean.getTransfer_supply_electricity_type(), fontFormat_Content));
				sheet.addCell(new Label(56, 2+i, bean.getTransfer_power_down_type(), fontFormat_Content));
				sheet.addCell(new Label(57, 2+i, bean.getEmh_supply_electricity_type(), fontFormat_Content));
				sheet.addCell(new Label(58, 2+i, bean.getEmh_power_down_type(), fontFormat_Content));
				sheet.addCell(new Label(59, 2+i, bean.getHas_spare_power(), fontFormat_Content));
				sheet.addCell(new Label(60, 2+i, bean.getSpare_power_info(), fontFormat_Content));
				sheet.addCell(new Label(61, 2+i, bean.getBs_xh_require_time(), fontFormat_Content));
				sheet.addCell(new Label(62, 2+i, bean.getBs_xh_fact_time(), fontFormat_Content));
				sheet.addCell(new Label(63, 2+i, bean.getTransfer_require_time(), fontFormat_Content));
				sheet.addCell(new Label(64, 2+i, bean.getTransfer_fact_time(), fontFormat_Content));
				sheet.addCell(new Label(65, 2+i, bean.getEmh_require_time(), fontFormat_Content));
				sheet.addCell(new Label(66, 2+i, bean.getEmh_fact_time(), fontFormat_Content));
				sheet.addCell(new Label(67, 2+i, bean.getGeneratorConfig(), fontFormat_Content));
				sheet.addCell(new Label(68, 2+i, bean.getNot_config_generator(), fontFormat_Content));
				sheet.addCell(new Label(69, 2+i, bean.getIs_allow_generation(), fontFormat_Content));
				sheet.addCell(new Label(70, 2+i, bean.getGeneration_date(), fontFormat_Content));
				sheet.addCell(new Label(71, 2+i, bean.getTo_bs_date(), fontFormat_Content));
				sheet.addCell(new Label(72, 2+i, bean.getGeneration_to_bs_date(), fontFormat_Content));
				
				
				sheet.addCell(new Label(73, 2+i, bean.getEmh_on_off(), fontFormat_Content));
				sheet.addCell(new Label(74, 2+i, bean.getEmh_on_off_info(), fontFormat_Content));
				sheet.addCell(new Label(75, 2+i, bean.getDoor(), fontFormat_Content));
				sheet.addCell(new Label(76, 2+i, bean.getCamera(), fontFormat_Content));
				sheet.addCell(new Label(77, 2+i, bean.getHumiture(), fontFormat_Content));
				sheet.addCell(new Label(78, 2+i, bean.getSmoke(), fontFormat_Content));
				sheet.addCell(new Label(79, 2+i, bean.getUps_z(), fontFormat_Content));
				sheet.addCell(new Label(80, 2+i, bean.getUps_j(), fontFormat_Content));
				sheet.addCell(new Label(81, 2+i, bean.getWater(), fontFormat_Content));
				sheet.addCell(new Label(82, 2+i, bean.getFire(), fontFormat_Content));
				sheet.addCell(new Label(83, 2+i, bean.getFire_info(), fontFormat_Content));
				sheet.addCell(new Label(84, 2+i, bean.getAir_conditioning(), fontFormat_Content));
				sheet.addCell(new Label(85, 2+i, bean.getAir_conditioning_info(), fontFormat_Content));
				sheet.addCell(new Label(86, 2+i, bean.getLightning(), fontFormat_Content));
				sheet.addCell(new Label(87, 2+i, bean.getComment(), fontFormat_Content));
				
				
			}

			
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	public  void excel_bs_check(WritableSheet sheet,List<ExcelBsInfoBean> list,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content) {
		try {
			sheet.addCell(new Label(0,0,"基本信息考核表",fontFormat_h));
			sheet.mergeCells(0, 0, 10, 0);
			sheet.addCell(new Label(0, 1, "基站ID", fontFormat_h));
			sheet.addCell(new Label(1, 1, "基站名称", fontFormat_h));
			sheet.addCell(new Label(2, 1, "基站分级", fontFormat_h));
			sheet.addCell(new Label(3, 1, "建设周期", fontFormat_h));
			sheet.addCell(new Label(4, 1, "区域", fontFormat_h));
			sheet.addCell(new Label(5, 1, "机房类型", fontFormat_h));

			sheet.addCell(new Label(6, 1, "传输链路", fontFormat_h));
			sheet.addCell(new Label(7, 1, "供配电", fontFormat_h));
			sheet.addCell(new Label(8, 1, "油机", fontFormat_h));

			sheet.addCell(new Label(9, 1, "动环监控", fontFormat_h));
			sheet.addCell(new Label(10, 1, "消防", fontFormat_h));
			sheet.addCell(new Label(11, 1, "空调", fontFormat_h));
			sheet.addCell(new Label(12, 1, "防雷接地", fontFormat_h));
			sheet.setRowView(0, 500);
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			
			
			for(int i=2;i<13;i++){
				sheet.setColumnView(i, 10);
			}					
			for (int i = 0; i < list.size(); i++) {			
				ExcelBsInfoBean bean =list.get(i);
				sheet.addCell(new jxl.write.Number(0, 2+i, bean.getBsId(), fontFormat_Content));
				sheet.addCell(new Label(1, 2+i, bean.getName(), fontFormat_Content));
				sheet.addCell(new Label(2, 2+i, bean.getLevel(), fontFormat_Content));
				sheet.addCell(new Label(3, 2+i, bean.getPeriod(), fontFormat_Content));
				sheet.addCell(new Label(4, 2+i, bean.getZone(), fontFormat_Content));
				sheet.addCell(new Label(5, 2+i, bean.getHometype(), fontFormat_Content));
				
				sheet.addCell(new Label(6, 2+i, "", fontFormat_Content));
				if(bean.getHas_spare_power()!=null && bean.getHas_spare_power()!=""){
					if(bean.getHas_spare_power().equals("是")){
						sheet.addCell(new Label(7, 2+i, "是", fontFormat_Content));
					}else{
						sheet.addCell(new Label(7, 2+i, "否", fontFormat_Content));
					}
				}else{
					sheet.addCell(new Label(7, 2+i, "否", fontFormat_Content));
				}
				
				sheet.addCell(new Label(8, 2+i, bean.getGeneratorConfig(), fontFormat_Content));

				if(bean.getEnvMonitor()!=null && bean.getEnvMonitor()!=""){
					if(!bean.getEnvMonitor().equals("无")){
						sheet.addCell(new Label(9, 2+i, "是", fontFormat_Content));
					}else{
						sheet.addCell(new Label(9, 2+i,"否", fontFormat_Content));
					}
				}else{
					sheet.addCell(new Label(9, 2+i,"", fontFormat_Content));
				}
				
				sheet.addCell(new Label(10, 2+i, bean.getFire(), fontFormat_Content));
				sheet.addCell(new Label(11, 2+i, bean.getAir_conditioning(), fontFormat_Content));
				sheet.addCell(new Label(12, 2+i, bean.getLightning(), fontFormat_Content));
				
				
			}

			
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	public  void excel_bs_transfer(WritableSheet sheet,List<ExcelBsInfoBean> list,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content) {
		try {
			sheet.addCell(new Label(0,0,"站信息表-传输链路",fontFormat_h));
			sheet.mergeCells(0, 0, 10, 0);
			sheet.addCell(new Label(0, 1, "基站ID", fontFormat_h));
			sheet.addCell(new Label(1, 1, "基站名称", fontFormat_h));
			sheet.addCell(new Label(2, 1, "基站分级", fontFormat_h));
			sheet.addCell(new Label(3, 1, "建设周期", fontFormat_h));
			
			
			sheet.addCell(new Label(4, 1, "第一传输开通情况", fontFormat_h));
			sheet.addCell(new Label(5, 1, "第一传输运营商", fontFormat_h));
			sheet.addCell(new Label(6, 1, "第一传输设备型号", fontFormat_h));
			sheet.addCell(new Label(7, 1, "第一传输基站网元", fontFormat_h));
			sheet.addCell(new Label(8, 1, "第一传输基站端口", fontFormat_h));
			sheet.addCell(new Label(9, 1, "第一传输交换中心网元", fontFormat_h));
			sheet.addCell(new Label(10, 1, "第一传输交换中心网元端口", fontFormat_h));
			sheet.addCell(new Label(11, 1, "第一传输电路调单号", fontFormat_h));
			
			sheet.addCell(new Label(12, 1, "第二传输开通情况", fontFormat_h));
			sheet.addCell(new Label(13, 1, "第二传输运营商", fontFormat_h));
			sheet.addCell(new Label(14, 1, "第二传输设备型号", fontFormat_h));
			sheet.addCell(new Label(15, 1, "第二传输基站网元", fontFormat_h));
			sheet.addCell(new Label(16, 1, "第二传输基站端口", fontFormat_h));
			sheet.addCell(new Label(17, 1, "第二传输交换中心网元", fontFormat_h));
			sheet.addCell(new Label(18, 1, "第二传输交换中心网元端口", fontFormat_h));
			sheet.addCell(new Label(19, 1, "第二传输电路调单号", fontFormat_h));
			
			sheet.setRowView(0, 500);
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);

			for(int i=2;i<20;i++){
				sheet.setColumnView(i, 10);
			}			
			
			for (int i = 0; i < list.size(); i++) {			
				ExcelBsInfoBean bean =list.get(i);
				sheet.addCell(new jxl.write.Number(0, 2+i, bean.getBsId(), fontFormat_Content));
				sheet.addCell(new Label(1, 2+i, bean.getName(), fontFormat_Content));
				sheet.addCell(new Label(2, 2+i, bean.getLevel(), fontFormat_Content));
				sheet.addCell(new Label(3, 2+i, bean.getPeriod(), fontFormat_Content));
				
				
				//第一传输
				sheet.addCell(new Label(4, 2+i, bean.getIs_open(), fontFormat_Content));
				sheet.addCell(new Label(5, 2+i, bean.getOperator(), fontFormat_Content));
				sheet.addCell(new Label(6, 2+i, bean.getEquipment_model1(), fontFormat_Content));
				sheet.addCell(new Label(7, 2+i, bean.getBs_net(), fontFormat_Content));
				sheet.addCell(new Label(8, 2+i, bean.getBs_net_port(), fontFormat_Content));
				sheet.addCell(new Label(9, 2+i, bean.getMsc_net(), fontFormat_Content));
				sheet.addCell(new Label(10, 2+i, bean.getMsc_net_port(), fontFormat_Content));
				sheet.addCell(new Label(11, 2+i, bean.getRegulation_number(), fontFormat_Content));
				//第二传输
				sheet.addCell(new Label(12, 2+i, bean.getIs_open1(), fontFormat_Content));
				sheet.addCell(new Label(13, 2+i, bean.getOperator1(), fontFormat_Content));
				sheet.addCell(new Label(14, 2+i, bean.getEquipment_model2(), fontFormat_Content));
				sheet.addCell(new Label(15, 2+i, bean.getBs_net1(), fontFormat_Content));
				sheet.addCell(new Label(16, 2+i, bean.getBs_net_port1(), fontFormat_Content));
				sheet.addCell(new Label(17, 2+i, bean.getMsc_net1(), fontFormat_Content));
				sheet.addCell(new Label(18, 2+i, bean.getMsc_net_port1(), fontFormat_Content));
				sheet.addCell(new Label(19, 2+i, bean.getRegulation_number1(), fontFormat_Content));
				
				
				
				
			}

			
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public  void excel_bs_elect(WritableSheet sheet,List<ExcelBsInfoBean> list,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content) {
		try {
			sheet.addCell(new Label(0,0,"基站信息表-供配电",fontFormat_h));
			sheet.mergeCells(0, 0, 10, 0);
			sheet.addCell(new Label(0, 1, "基站ID", fontFormat_h));
			sheet.addCell(new Label(1, 1, "基站名称", fontFormat_h));
			sheet.addCell(new Label(2, 1, "基站分级", fontFormat_h));
			sheet.addCell(new Label(3, 1, "建设周期", fontFormat_h));
			sheet.addCell(new Label(4, 1, "区域", fontFormat_h));
			sheet.addCell(new Label(5, 1, "是否与移动基站共址", fontFormat_h));
			sheet.addCell(new Label(6, 1, "机房类型", fontFormat_h));
			
			
			sheet.addCell(new Label(7, 1, "基站主设备供电类型", fontFormat_h));
			sheet.addCell(new Label(8, 1, "基站主设备电源下电方式", fontFormat_h));
			sheet.addCell(new Label(9, 1, "传输设备供电类型", fontFormat_h));
			sheet.addCell(new Label(10, 1, "传输设备电源下电方式", fontFormat_h));
			sheet.addCell(new Label(11, 1, "环控设备供电类型", fontFormat_h));
			sheet.addCell(new Label(12, 1, "环控设备电源下电方式", fontFormat_h));
			sheet.addCell(new Label(13, 1, "是否具有备用电源", fontFormat_h));
			sheet.addCell(new Label(14, 1, "后备电源说明", fontFormat_h));
			sheet.addCell(new Label(15, 1, "基站主设备要求持续续航时间（小时）", fontFormat_h));
			sheet.addCell(new Label(16, 1, "基站主设备实际持续续航时间（小时）", fontFormat_h));
			sheet.addCell(new Label(17, 1, "传输设备要求持续续航时间（小时）", fontFormat_h));
			sheet.addCell(new Label(18, 1, "传输设备实际持续续航时间（小时）", fontFormat_h));
			sheet.addCell(new Label(19, 1, "环控设备要求持续续航时间（小时）", fontFormat_h));
			sheet.addCell(new Label(20, 1, "环控设备实际持续续航时间（小时）", fontFormat_h));
			sheet.addCell(new Label(21, 1, "是否配备发电油机", fontFormat_h));
			sheet.addCell(new Label(22, 1, "配备油机说明", fontFormat_h));
			sheet.addCell(new Label(23, 1, "整改措施", fontFormat_h));
			sheet.addCell(new Label(24, 1, "是否允许临时性发电", fontFormat_h));
			sheet.addCell(new Label(25, 1, "发电时间", fontFormat_h));
			sheet.addCell(new Label(26, 1, "上站时间", fontFormat_h));
			sheet.addCell(new Label(27, 1, "上站发电所需时间", fontFormat_h));
			sheet.addCell(new Label(28, 1, "供配电是否达招标要求", fontFormat_h));
			sheet.addCell(new Label(29, 1, "未达到招标要求原因", fontFormat_h));
			sheet.addCell(new Label(30, 1, "整改措施", fontFormat_h));
			
			sheet.setRowView(0, 500);
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			
			
			for(int i=2;i<22;i++){
				sheet.setColumnView(i, 10);
			}			
			sheet.setColumnView(21, 30);
			sheet.setColumnView(22, 30);

			for(int i=23;i<29;i++){
				sheet.setColumnView(i, 10);
			}
			sheet.setColumnView(29, 30);
			sheet.setColumnView(30, 30);		
			for (int i = 0; i < list.size(); i++) {			
				ExcelBsInfoBean bean =list.get(i);
				sheet.addCell(new jxl.write.Number(0, 2+i, bean.getBsId(), fontFormat_Content));
				sheet.addCell(new Label(1, 2+i, bean.getName(), fontFormat_Content));
				sheet.addCell(new Label(2, 2+i, bean.getLevel(), fontFormat_Content));
				sheet.addCell(new Label(3, 2+i, bean.getPeriod(), fontFormat_Content));
				sheet.addCell(new Label(4, 2+i, bean.getZone(), fontFormat_Content));
				sheet.addCell(new Label(5, 2+i, bean.getIs_same_address(), fontFormat_Content));
				sheet.addCell(new Label(6, 2+i, bean.getHometype(), fontFormat_Content));
				
				
				sheet.addCell(new Label(7, 2+i, bean.getBs_supply_electricity_type(), fontFormat_Content));
				sheet.addCell(new Label(8, 2+i, bean.getBs_power_down_type(), fontFormat_Content));
				sheet.addCell(new Label(9, 2+i, bean.getTransfer_supply_electricity_type(), fontFormat_Content));
				sheet.addCell(new Label(10, 2+i, bean.getTransfer_power_down_type(), fontFormat_Content));
				sheet.addCell(new Label(11, 2+i, bean.getEmh_supply_electricity_type(), fontFormat_Content));
				sheet.addCell(new Label(12, 2+i, bean.getEmh_power_down_type(), fontFormat_Content));
				sheet.addCell(new Label(13, 2+i, bean.getHas_spare_power(), fontFormat_Content));
				sheet.addCell(new Label(14, 2+i, bean.getSpare_power_info(), fontFormat_Content));
				sheet.addCell(new Label(15, 2+i, bean.getBs_xh_require_time(), fontFormat_Content));
				sheet.addCell(new Label(16, 2+i, bean.getBs_xh_fact_time(), fontFormat_Content));
				sheet.addCell(new Label(17, 2+i, bean.getTransfer_require_time(), fontFormat_Content));
				sheet.addCell(new Label(18, 2+i, bean.getTransfer_fact_time(), fontFormat_Content));
				sheet.addCell(new Label(19, 2+i, bean.getEmh_require_time(), fontFormat_Content));
				sheet.addCell(new Label(20, 2+i, bean.getEmh_fact_time(), fontFormat_Content));
				sheet.addCell(new Label(21, 2+i, bean.getGeneratorConfig(), fontFormat_Content));
				sheet.addCell(new Label(22, 2+i, bean.getNot_config_generator(), fontFormat_Content));
				sheet.addCell(new Label(23, 2+i, bean.getRectification_measures(), fontFormat_Content));
				sheet.addCell(new Label(24, 2+i, bean.getIs_allow_generation(), fontFormat_Content));
				sheet.addCell(new Label(25, 2+i, bean.getGeneration_date(), fontFormat_Content));
				sheet.addCell(new Label(26, 2+i, bean.getTo_bs_date(), fontFormat_Content));
				sheet.addCell(new Label(27, 2+i, bean.getGeneration_to_bs_date(), fontFormat_Content));
				sheet.addCell(new Label(28, 2+i, bean.getIs_require(), fontFormat_Content));
				sheet.addCell(new Label(29, 2+i, bean.getNot_require_reason(), fontFormat_Content));
				sheet.addCell(new Label(30, 2+i, bean.getRectification_measures2(), fontFormat_Content));

				
			}

			
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
    
	public  void excel_bs_room(WritableSheet sheet,List<ExcelBsInfoBean> list,WritableCellFormat fontFormat_h,WritableCellFormat fontFormat_Content) {
		try {
			sheet.addCell(new Label(0,0,"基站信息表-机房配套",fontFormat_h));
			sheet.mergeCells(0, 0, 10, 0);
			sheet.addCell(new Label(0, 1, "基站ID", fontFormat_h));
			sheet.addCell(new Label(1, 1, "基站名称", fontFormat_h));
			sheet.addCell(new Label(2, 1, "基站分级", fontFormat_h));
			sheet.addCell(new Label(3, 1, "建设周期", fontFormat_h));
			sheet.addCell(new Label(4, 1, "设备类型", fontFormat_h));
			sheet.addCell(new Label(5, 1, "区域", fontFormat_h));
			sheet.addCell(new Label(6, 1, "是否与移动基站共址", fontFormat_h));
			sheet.addCell(new Label(7, 1, "机房类型", fontFormat_h));
			
			sheet.addCell(new Label(8, 1, "环控通断", fontFormat_h));
			sheet.addCell(new Label(9, 1, "门磁", fontFormat_h));
			sheet.addCell(new Label(10, 1, "摄像头", fontFormat_h));
			sheet.addCell(new Label(11, 1, "温湿度传感器", fontFormat_h));
			sheet.addCell(new Label(12, 1, "烟雾传感器", fontFormat_h));
			sheet.addCell(new Label(13, 1, "直流电流电压", fontFormat_h));
			sheet.addCell(new Label(14, 1, "交流电流电压", fontFormat_h));
			sheet.addCell(new Label(15, 1, "漏水检测", fontFormat_h));
			sheet.addCell(new Label(16, 1, "消防", fontFormat_h));
			sheet.addCell(new Label(17, 1, "空调", fontFormat_h));
			sheet.addCell(new Label(18, 1, "防雷接地", fontFormat_h));
			sheet.setRowView(0, 500);
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			
			
			for(int i=2;i<19;i++){
				sheet.setColumnView(i, 10);
			}			
			for (int i = 0; i < list.size(); i++) {			
				ExcelBsInfoBean bean =list.get(i);
				sheet.addCell(new jxl.write.Number(0, 2+i, bean.getBsId(), fontFormat_Content));
				sheet.addCell(new Label(1, 2+i, bean.getName(), fontFormat_Content));
				sheet.addCell(new Label(2, 2+i, bean.getLevel(), fontFormat_Content));
				sheet.addCell(new Label(3, 2+i, bean.getPeriod(), fontFormat_Content));
				if(bean.getType()!=null && bean.getType()!=""){
					String str="";
					if(bean.getType().equals("0")){
						str="固定基站";
					}else if(bean.getType().equals("1")){
						str="室分信源";
					}else if(bean.getType().equals("2")){
						str="应急站";
					}else{
						
					}
					sheet.addCell(new Label(4, 2+i, str, fontFormat_Content));
				}else{
					sheet.addCell(new Label(4, 2+i, "", fontFormat_Content));
				}
				sheet.addCell(new Label(5, 2+i, bean.getZone(), fontFormat_Content));
				sheet.addCell(new Label(6, 2+i, bean.getIs_same_address(), fontFormat_Content));
				sheet.addCell(new Label(7, 2+i, bean.getHometype(), fontFormat_Content));
				
				
				sheet.addCell(new Label(8, 2+i, bean.getEmh_on_off(), fontFormat_Content));
				sheet.addCell(new Label(9, 2+i, bean.getDoor(), fontFormat_Content));
				sheet.addCell(new Label(10, 2+i, bean.getCamera(), fontFormat_Content));
				sheet.addCell(new Label(11, 2+i, bean.getHumiture(), fontFormat_Content));
				sheet.addCell(new Label(12, 2+i, bean.getSmoke(), fontFormat_Content));
				sheet.addCell(new Label(13, 2+i, bean.getUps_z(), fontFormat_Content));
				sheet.addCell(new Label(14, 2+i, bean.getUps_j(), fontFormat_Content));
				sheet.addCell(new Label(15, 2+i, bean.getWater(), fontFormat_Content));
				sheet.addCell(new Label(16, 2+i, bean.getFire(), fontFormat_Content));
				sheet.addCell(new Label(17, 2+i, bean.getAir_conditioning(), fontFormat_Content));
				sheet.addCell(new Label(18, 2+i, bean.getLightning(), fontFormat_Content));

			}

			
		} catch (Exception e) {
			e.printStackTrace();

		}	
		
	}
}
