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

import xh.constant.ConstantLog;
import xh.constant.ConstantMap;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.RadioBean;
import xh.mybatis.service.RadioDispatchService;
import xh.mybatis.service.RadioService;
import xh.org.listeners.SingLoginListener;
import xh.org.socket.MotoTcpClient;
import xh.org.socket.SendData;

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
		String id=request.getParameter("id");
		/*RadioBean bean=GsonUtil.json2Object(formdata, RadioBean.class);*/
		
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("id", id);
		HashMap result = new HashMap();
		result.put("totals",radio_s.count(map));
		result.put("items", radio_s.list(map));
		return result;
	}
	@RequestMapping(value="/radioGet",method = RequestMethod.GET)
	@ResponseBody
	public HashMap radioGet(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String radioId=request.getParameter("radioId");
		
		/*int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);*/
		RadioBean bean=new RadioBean();
		if(MotoTcpClient.getSocket().isConnected()){
			
			bean.setRadioID(radioId);
			bean.setCallId(FunUtil.RandomAlphanumeric(8));
			bean.setRadioReferenceID("0");
			bean.setRadioSerialNumber("0");
			bean.setSecurityGroup("0");
			SendData.RadioGetReq(bean);
			long nowtime=System.currentTimeMillis();
			tag:for(;;){			
				long tt=System.currentTimeMillis();
				if(ConstantMap.getMotoResultMap().containsKey(bean.getCallId())){
					this.success=true;
					if(ConstantMap.getMotoResultMap().get(bean.getCallId()).equals("0")){
						this.message="没有查询到相关数据";
						this.success=false;
					}else{
						bean=(RadioBean) ConstantMap.getMotoResultMap().get(bean.getCallId()+"map");
						radio_s.vAdd(bean);
						this.message="获取数据成功";
					}	
					ConstantMap.getMotoResultMap().remove(bean.getCallId());
					ConstantMap.getMotoResultMap().remove(bean.getCallId()+"map");
					break tag;
				}else{
					if(tt-nowtime>10000){
						this.success=false;
						this.message="查询数据超时";
						break tag;
					}
				}
			}			
		}else{
			this.success=false;
			this.message="连接服务失败";
		}
		
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		result.put("data",bean);
		return result;
	}
	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ResponseBody
	public HashMap add(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String formdata=request.getParameter("formData");
		RadioBean bean=GsonUtil.json2Object(formdata, RadioBean.class);
		bean.setCallId(FunUtil.RandomAlphanumeric(8));
		Map<String,Object> map=radio_s.add(bean);
		if(Integer.parseInt(map.get("rs").toString())>0){
			this.message="添加数据成功";
			FunUtil.WriteLog(request, ConstantLog.ADD, "添加moto终端："+bean.getRadioID());
			this.success=true;
		}else if(Integer.parseInt(map.get("rs").toString())==-1){
			this.message="终端ID 或则序列号已经存在";
			this.success=false;
		}else{
			this.message="添加数据失败:"+map.get("errMsg")==null?"":map.get("errMsg").toString();
			this.success=false;
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
		bean.setCallId(FunUtil.RandomAlphanumeric(8));
		Map<String,Object> map=radio_s.update(bean);
		if(Integer.parseInt(map.get("rs").toString())>0){
			this.message="修改数据成功";
			FunUtil.WriteLog(request, ConstantLog.UPDATE, "修改moto终端："+bean.getRadioID());
			this.success=true;
		}else{
			this.message="修改数据失败："+map.get("errMsg")==null?"":map.get("errMsg").toString();
			this.success=false;
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
		String formdata=request.getParameter("formData");
		RadioBean bean=GsonUtil.json2Object(formdata, RadioBean.class);
		bean.setCallId(FunUtil.RandomAlphanumeric(8));
		
		Map<String,Object> map=radio_s.delete(bean);
		if(Integer.parseInt(map.get("rs").toString())>0){
			this.message="删除数据成功";
			FunUtil.WriteLog(request, ConstantLog.DELETE, "删除moto终端："+bean.getRadioID());
			this.success=true;
		}else{
			this.message="删除数据失败："+map.get("errMsg")==null?"":map.get("errMsg").toString();
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("data", bean.getRadioID());
		return result;
	}
}
