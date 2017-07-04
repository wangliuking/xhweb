package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.AssetInfoBean;
import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.BusinessService;
import xh.mybatis.service.WebLogService;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping("/business")
public class BusinessController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(BusinessController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	/**
	 * 查询资产记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/assetList",method = RequestMethod.GET)
	public void assetInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int type=funUtil.StringToInt(request.getParameter("type"));
		String name=request.getParameter("name");
		String model=request.getParameter("model");
		String serialNumber=request.getParameter("serialNumber");
		int from=funUtil.StringToInt(request.getParameter("from"));
		int status=funUtil.StringToInt(request.getParameter("status"));
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("type", type);
		map.put("name", name);
		map.put("model",model );
		map.put("serialNumber",serialNumber );
		map.put("from",from );
		map.put("status",status );
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",BusinessService.assetInfoCount(map));
		result.put("items", BusinessService.assetInfo(map));
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
	 * 添加资产
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/insertAsset",method = RequestMethod.POST)
	public void insertAsset(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String jsonData=request.getParameter("formData");
        AssetInfoBean bean=GsonUtil.json2Object(jsonData, AssetInfoBean.class);
		log.info("data==>"+bean.toString());
		int rlt=BusinessService.insertAsset(bean);
		if (rlt==1) {
			this.message="添加资产成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("新增资产，data="+bean.toString());
			WebLogService.writeLog(webLogBean);
		}else {
			this.message="添加资产失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("result",rlt);
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
	 * 修改资产记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateAsset",method = RequestMethod.POST)
	public void updateAsset(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String jsonData=request.getParameter("formData");
        AssetInfoBean bean=GsonUtil.json2Object(jsonData, AssetInfoBean.class);
		log.info("data==>"+bean.toString());
		int rlt=BusinessService.updateAsset(bean);
		if (rlt==1) {
			this.message="修改资产记录成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改资产记录，data="+bean.toString());
			WebLogService.writeLog(webLogBean);
			
		}else {
			this.message="修改资产记录失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("result",rlt);
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
	 * 删除资产记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteAsset",method = RequestMethod.POST)
	public void deleteAsset(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String id=request.getParameter("deleteIds");
		List<String> list = new ArrayList<String>();
		String[] ids=id.split(",");
		for (String str : ids) {
			list.add(str);
		}
		//log.info("data==>"+bean.toString());
		int rlt=BusinessService.deleteAsset(list);
		if (rlt==1) {
			this.message="删除资产记录成功";
			this.message="添加资产成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(3);
			webLogBean.setContent("删除资产记录，data="+id);
			WebLogService.writeLog(webLogBean);
		}else {
			this.message="删除资产记录失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("result",rlt);
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
