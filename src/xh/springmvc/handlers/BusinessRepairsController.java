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
import xh.mybatis.bean.AssetInfoBean;
import xh.mybatis.bean.Repairs;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BusinessRepairsService;
import xh.mybatis.service.BusinessService;
import xh.mybatis.service.WebLogService;

@Controller
@RequestMapping("/businessrepairs")
public class BusinessRepairsController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(BusinessRepairsController.class);
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
		result.put("totals",BusinessRepairsService.assetInfoCount(map));
		result.put("items", BusinessRepairsService.assetInfo(map));
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
		HashMap result = new HashMap();
		String serialNumber = request.getParameter("serialNumber");
		String note = request.getParameter("note");
		if(BusinessService.count(serialNumber)==0 || BusinessService.count(serialNumber)>1){
			result.put("result",0);
		}else if(BusinessRepairsService.countByNum(serialNumber)>0){
			result.put("result",2);
		}else{
			AssetInfoBean bean = BusinessService.selectbynum(serialNumber);
			bean.setNote(note);
			int rlt=BusinessRepairsService.insertAsset(bean);
			//添加成功后修改记录表设备属性
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("serialNumber", serialNumber);
			map.put("status", 3);
			BusinessService.updateStatusByNum(map);
			result.put("success", success);
			result.put("message",message);
			result.put("result",rlt);
		}
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
		String noterepairs=request.getParameter("noterepairs");
		String serialNumbertemp=request.getParameter("serialNumbertemp");
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("noterepairs", noterepairs);
		map.put("serialNumbertemp", serialNumbertemp);
		int rlt=BusinessRepairsService.updateByNum(map);
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
		int rlt=BusinessRepairsService.deleteAsset(list);
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
