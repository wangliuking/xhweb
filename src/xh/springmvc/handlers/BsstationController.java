package xh.springmvc.handlers;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.AssetInfoBean;
import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.ChartBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.bsLinkConfigBean;
import xh.mybatis.bean.bscConfigBean;
import xh.mybatis.bean.bsrConfigBean;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.CallListServices;
import xh.mybatis.service.WebLogService;
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
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("bsId", bsId);
		map.put("name", name);
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
		String bsId=request.getParameter("bsIds");
		List<String> bslist=new ArrayList<String>();
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
		
		
		List<HashMap> list=	 BsstationService.allBsInfo(paramMap);
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
		this.success=true;
		String jsonData=request.getParameter("formData");
        BsstationBean bean=GsonUtil.json2Object(jsonData, BsstationBean.class);
        /*if (bean.getOpenen()==0) {
			bean.setStatus(2);
		}*/
        log.info("data==>"+bean.toString());
		int count=BsstationService.insertBs(bean);
		if (count==0) {
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("新增基站，bsId="+bean.getBsId()+";name="+bean.getName());
			WebLogService.writeLog(webLogBean);
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
		paramterMap.put("transferNumber", bean.getTransferNumber());
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
		this.success=true;
		String jsonData=request.getParameter("formData");
        BsstationBean bean=GsonUtil.json2Object(jsonData, BsstationBean.class);
       /* if (bean.getOpenen()==0) {
			bean.setStatus(2);
		}*/
        log.info("data==>"+bean.toString());
		int count=BsstationService.updateBs(bean);
		if (count==1) {
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改基站，bsId="+bean.getBsId()+";name="+bean.getName());
			WebLogService.writeLog(webLogBean);
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
}
