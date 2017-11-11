package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import xh.mybatis.bean.DispatchBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.DispatchStatusService;
import xh.mybatis.service.ServerStatusService;
@Controller
@RequestMapping(value="/status")
public class DispatchStatusController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(DispatchStatusController.class);
	private WebLogBean webLogBean=new WebLogBean();
	
	@RequestMapping(value="/dispatch",method = RequestMethod.GET)
	public void dispatch(HttpServletRequest request, HttpServletResponse response){
		this.success=true;		
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("success", success);
		result.put("totals",DispatchStatusService.dispatchstatus().size());
		result.put("items", DispatchStatusService.dispatchstatus());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 添加调度台
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/addDispatch",method = RequestMethod.POST)
	public void addDispatch(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		
		DispatchBean bean=GsonUtil.json2Object(formData, DispatchBean.class);
		
		log.info("调度台="+bean.toString());
		int rsl=0;
		if(DispatchStatusService.dispatchExists(bean.getDstId())==0){
			rsl=DispatchStatusService.addDispatch(bean);
			if(rsl==1){
				this.message="调度台添加成功";
				this.success=true;
			}else{
				this.message="调度台添加失败";
				this.success=false;
			}
		}else{
			this.message="调度台已经存在";
			this.success=false;
		}
				
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("success", success);
		result.put("result",rsl);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 修改调度台
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateDispatch",method = RequestMethod.POST)
	public void updateDispatch(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		
		DispatchBean bean=GsonUtil.json2Object(formData, DispatchBean.class);
		
		log.info(bean.toString());
		int rsl=DispatchStatusService.updateDispatch(bean);
		if(rsl==1){
			this.message="修改调度台成功";
			this.success=true;
		}else{
			this.message="修改调度台失败";
			this.success=false;
		}
				
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("success", success);
		result.put("result",rsl);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 删除调度台
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteDispatch",method = RequestMethod.POST)
	public void deleteDispatch(HttpServletRequest request, HttpServletResponse response){
		String dstIds[]=request.getParameter("dstId").split(",");
		List<String> list=new ArrayList<String>();
		for(int i=0;i<dstIds.length;i++){
			list.add(dstIds[i]);
		}		
		int rsl=DispatchStatusService.deleteDispatch(list);
		if(rsl==1){
			this.message="删除调度台成功";
			this.success=true;
		}else{
			this.message="删除调度台失败";
			this.success=false;
		}
				
		HashMap<String,Object> result = new HashMap<String,Object>();
		result.put("success", success);
		result.put("result",rsl);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
