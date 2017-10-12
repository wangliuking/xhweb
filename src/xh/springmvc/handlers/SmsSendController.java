package xh.springmvc.handlers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xalan.xsltc.runtime.MessageHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.smsBean;
import xh.mybatis.service.SmsSendService;
import xh.org.socket.MessageStruct;
import xh.org.socket.SendData;
import xh.org.socket.TcpKeepAliveClient;
@Controller
@RequestMapping(value="/sms-send")
public class SmsSendController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(SmsSendController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	
	/**
	 * 查询短信信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void recInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String srcId=request.getParameter("srcId");
		String dstId=request.getParameter("dstId");
		String writeTime=request.getParameter("writeTime");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("srcId", srcId);
		map.put("dstId", dstId);
		map.put("writeTime", writeTime);
		map.put("start", start);
		map.put("limit", limit);
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",SmsSendService.recCount(map));
		result.put("items", SmsSendService.recInfo(map));
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
	 * 发送短信
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/writesms",method = RequestMethod.POST)
	public void writesms(HttpServletRequest request, HttpServletResponse response){
		String jsonData=request.getParameter("formData");
		smsBean bean=GsonUtil.json2Object(jsonData, smsBean.class);
		log.info(bean.toString());
		
		//数据包头
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)164);
		header.setCommandId((short)102);
		header.setSrcDevice((byte)4);
		header.setDstDevice((byte)4);
		
		//数据内容
		
		bean.setConsume(0);
		try {
			bean.setLen(bean.getContent().getBytes("UTF-8").length);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		if(TcpKeepAliveClient.getSocket().isConnected()){
			SendData.sendSms(header, bean);
			this.message="短信已发送";
			this.success=true;
		}else{
			this.message="后台服务连接失败！发送短信失败";
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
	@RequestMapping(value="/del",method = RequestMethod.POST)
	public void deleteById(HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("id");
		List<String> list = new ArrayList<String>();
		String[] ids=id.split(",");
		for (String str : ids) {
			list.add(str);
		}
		SmsSendService.deleteById(list);
		HashMap result = new HashMap();
		this.success=true;
		result.put("success", success);
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
