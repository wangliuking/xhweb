package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.LendBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.JoinNetService;
import xh.mybatis.service.LendService;
import xh.mybatis.service.WebLogService;
@Controller
@RequestMapping(value="/business")
public class LendController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(LendController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	/**
	 * 申请租借列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/lend/list",method = RequestMethod.GET)
	public void lendList(HttpServletRequest request, HttpServletResponse response){
		this.success=true;		
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",LendService.lendlistCount());
		result.put("items", LendService.lendlist(map));
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
	 * 租借设备清单
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/lend/lendInfoList",method = RequestMethod.GET)
	public void lendInfoList(HttpServletRequest request, HttpServletResponse response){
		this.success=true;	
		int lendId=funUtil.StringToInt(request.getParameter("lendId"));
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",LendService.lendInfoList(lendId).size());
		result.put("items", LendService.lendInfoList(lendId));
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
	 * 租借
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/lend/add",method = RequestMethod.POST)
	public void lend(HttpServletRequest request, HttpServletResponse response){
		this.success = true;
		String jsonData = request.getParameter("formData");
		LendBean bean=GsonUtil.json2Object(jsonData, LendBean.class);
		bean.setUser(funUtil.loginUser(request));
		bean.setTime(funUtil.nowDate());
		int rst=LendService.lend(bean);
		if(rst==1){
			this.message = "申请已经发出，耐心等待审核";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("租借设备，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
		}else{
			this.message = "申请失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("message", message);
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
	/**
	 * 管理方审核
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/lend/checkedOne",method = RequestMethod.POST)
	public void checkedOne(HttpServletRequest request, HttpServletResponse response){
		this.success = true;
		String jsonData = request.getParameter("formData");
		LendBean bean=GsonUtil.json2Object(jsonData, LendBean.class);
		bean.setUser1(funUtil.loginUser(request));
		bean.setTime1(funUtil.nowDate());
		int rst=LendService.checkedOne(bean);
		if(rst==1){
			this.message = "审核成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("租借设备审核成功，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
		}else{
			this.message = "审核失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("message", message);
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
	/**
	 * 添加设备清单
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/lend/addOrder",method = RequestMethod.POST)
	public void addOrder(HttpServletRequest request, HttpServletResponse response){
		this.success = true;
		String jsonData = request.getParameter("toData");
		int id=funUtil.StringToInt(request.getParameter("id"));
		List<Map<String,Object>> list=GsonUtil.json2Object(jsonData, ArrayList.class);
		List<Map<String,Object>> list2=new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=new HashMap<String, Object>();
			Map<String,Object> map2=new HashMap<String, Object>();
			map.put("lendId", id);
			map.put("serialNumber", list.get(i).get("serialNumber").toString());
			
			map2.put("lendId", id);
			map2.put("id", list.get(i).get("id").toString());
			map2.put("type", list.get(i).get("type").toString());
			map2.put("name", list.get(i).get("name").toString());
			map2.put("model", list.get(i).get("model").toString());
			map2.put("serialNumber", list.get(i).get("serialNumber").toString());
			
			if(LendService.isExtisSerialNumberInfo(map)==0){
				list2.add(map2);
			}
		}

		int rst=-1;
		if(list2.size()>0){
			rst=LendService.addOrder(list2);
		}
		if(rst==1){
			this.message = "审核成功";
			LendService.updateAssetStatus(list2);
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("添加租借设备清单，lendId="+id);
			WebLogService.writeLog(webLogBean);
			
		}else if(rst==0){
			this.message = "添加失败";
		}else{
			this.message = "没有需要添加的数据";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("message", message);
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
	/**
	 * 删除清单中的设备
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/lend/deleteLendOrderE",method = RequestMethod.POST)
	public void deleteLendOrderE(HttpServletRequest request, HttpServletResponse response){
		this.success = true;
		String lendId = request.getParameter("lendId");
		String serialNumber = request.getParameter("serialNumber");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("lendId", lendId);
		map.put("serialNumber", serialNumber);
		int rst=LendService.deleteLendOrderE(map);
		if(rst==1){
			this.message = "删除成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("删除清单中的记录，data=" +lendId);
			WebLogService.writeLog(webLogBean);
		}else{
			this.message = "删除失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("message", message);
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
	/**
	 * 提交至领导审核租借清单
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/lend/checkedTwo", method = RequestMethod.POST)
	public void checkedTwo(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("lendId"));
		LendBean bean=new LendBean();
		bean.setId(id);
		bean.setChecked(2);
		bean.setTime2(funUtil.nowDate());

		int rst = LendService.checkedTwo(bean);
		if (rst == 1) {
			this.message = "提交至领导审核租借清单成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("提交至领导审核租借清单，data=" + id);
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "提交至领导审核租借清单失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("message", message);
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
	/**
	 * 领导审核租借清单
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/lend/checkedOrder", method = RequestMethod.POST)
	public void checkedOrder(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("lendId"));
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		String note2=request.getParameter("note2");
		LendBean bean=new LendBean();
		bean.setId(id);
		bean.setChecked(checked);
		bean.setTime3(funUtil.nowDate());
		bean.setNote2(note2);

		int rst = LendService.checkedOrder(bean);
		if (rst == 1) {
			this.message = "审核租借清单完成";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核租借清单，data=" + id);
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "审核租借清单失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("message", message);
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
	/**
	 * 用户确认租借清单
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/lend/sureOrder", method = RequestMethod.POST)
	public void sureOrder(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("lendId"));
		int checked = 4;
		LendBean bean=new LendBean();
		bean.setId(id);
		bean.setChecked(checked);
		bean.setTime4(funUtil.nowDate());

		int rst = LendService.sureOrder(bean);
		if (rst == 1) {
			this.message = "确认租借清单完成";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认租借清单，data=" + id);
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "确认租借清单失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("message", message);
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
