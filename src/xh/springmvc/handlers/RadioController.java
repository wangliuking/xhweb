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
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.RadioBean;
import xh.mybatis.service.RadioDispatchService;
import xh.mybatis.service.RadioService;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping(value="/radio")
public class RadioController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(RadioController.class);
	private RadioService radio_s=new RadioService();
	private FlexJSON json=new FlexJSON();
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	@ResponseBody
	public HashMap info(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String formdata=request.getParameter("formData");
		RadioBean bean=GsonUtil.json2Object(formdata, RadioBean.class);
		
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("totals",radio_s.count());
		result.put("items", radio_s.list(map));
		return result;
	}
	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ResponseBody
	public HashMap add(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String formdata=request.getParameter("formData");
		RadioBean bean=GsonUtil.json2Object(formdata, RadioBean.class);
		int rs=radio_s.add(bean);
		if(rs>0){
			this.message="添加数据成功";
			this.success=true;
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("data", bean);
		return result;
	}
	@RequestMapping(value="/update",method = RequestMethod.POST)
	@ResponseBody
	public HashMap update(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String formdata=request.getParameter("formData");
		RadioBean bean=GsonUtil.json2Object(formdata, RadioBean.class);
		int rs=radio_s.update(bean);
		if(rs>0){
			this.message="修改数据成功";
			this.success=true;
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("data", bean);
		return result;
	}
	@RequestMapping(value="/delete",method = RequestMethod.POST)
	@ResponseBody
	public HashMap delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String[] ids=request.getParameter("ids").split(",");
		List<String> list=new ArrayList<String>();
		
		for (String string : ids) {
			list.add(string);
		}
		
		
		int rs=radio_s.delete(list);
		if(rs>0){
			this.message="删除数据成功";
			this.success=true;
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("data", ids);
		return result;
	}
}
