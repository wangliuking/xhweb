package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
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
import xh.mybatis.bean.Lose;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BusinessLoseService;
import xh.mybatis.service.WebLogService;

@Controller
@RequestMapping("/businesslose")
public class BusinessLoseController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(BusinessLoseController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	/**
	 * 查询
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
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("type", type);
		map.put("name", name);
		map.put("model",model );
		map.put("serialNumber",serialNumber );
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",BusinessLoseService.assetInfoCount(map));
		result.put("items", BusinessLoseService.assetInfo(map));
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
	 * 添加
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/insertAsset",method = RequestMethod.POST)
	public void insertAsset(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String jsonData=request.getParameter("dataForm");
		System.out.println(jsonData);
        Lose bean=GsonUtil.json2Object(jsonData, Lose.class);
		int rlt=BusinessLoseService.insertAsset(bean);
		if (rlt==1) {
			this.message="添加遗失信息成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("新增遗失信息，data="+bean.toString());
			WebLogService.writeLog(webLogBean);
		}else {
			this.message="添加遗失信息失败";
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
	 * 修改
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateAsset",method = RequestMethod.POST)
	public void updateAsset(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String jsonData=request.getParameter("formData");
        Lose bean=GsonUtil.json2Object(jsonData, Lose.class);
		int rlt=BusinessLoseService.updateAsset(bean);
		if (rlt==1) {
			this.message="修改遗失信息成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改遗失信息，data="+bean.toString());
			WebLogService.writeLog(webLogBean);
			
		}else {
			this.message="修改遗失信息失败";
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
	 * 删除
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
		int rlt=BusinessLoseService.deleteAsset(list);
		if (rlt==1) {
			this.message="删除遗失信息成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(3);
			webLogBean.setContent("删除遗失信息，data="+id);
			WebLogService.writeLog(webLogBean);
		}else {
			this.message="删除遗失信息失败";
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
