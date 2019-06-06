package xh.springmvc.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.RecordNetBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.RecordNetService;


@Controller
@RequestMapping("/recordnet")
public class RecordNetController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(RecordNetController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	@RequestMapping(value="/data_all", method = RequestMethod.GET)
	@ResponseBody
	public HashMap data_all(HttpServletRequest request, HttpServletResponse response) {
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		String time=request.getParameter("time");
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time",time);
		HashMap result = new HashMap();
		result.put("items",RecordNetService.data_all(map));
		result.put("totals", RecordNetService.data_all_count(map));		
		return result;
	}
	@RequestMapping(value="/add", method = RequestMethod.POST)
	@ResponseBody
	public HashMap add(@RequestBody RecordNetBean bean) {
		
		System.out.println("bean-->"+bean.toString());
		int rs=RecordNetService.add(bean);
		if(rs>0){
			this.message="添加成功";
			this.success=true;
		}else{
			this.message="添加失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);		
		return result;
	}
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public HashMap update(@RequestBody RecordNetBean bean) {
		int rs=RecordNetService.update(bean);
		if(rs>0){
			this.message="修改成功";
			this.success=true;
		}else{
			this.message="失败";
			this.success=false;
		}
		
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);		
		return result;
	}
	@RequestMapping(value="/del", method = RequestMethod.POST)
	@ResponseBody
	public HashMap del(HttpServletRequest request, HttpServletResponse response) {
		String[] id=request.getParameter("id").split(",");
		List<String> list=new ArrayList<String>();
		for (String string : id) {
			list.add(string);
		}
		
		int rs=RecordNetService.del(list);
		if(rs>0){
			this.message="删除成功";
			this.success=true;
		}else{
			this.message="失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);		
		return result;
	}

	
}
