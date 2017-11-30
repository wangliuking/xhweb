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
import xh.mybatis.bean.DgnaBean;
import xh.mybatis.service.DgnaServices;
import xh.mybatis.service.TalkGroupService;
import xh.mybatis.service.UcmService;
import xh.mybatis.service.WebUserServices;
import xh.org.socket.KillStruct;
import xh.org.socket.SendData;
import xh.org.socket.TcpKeepAliveClient;
import xh.org.socket.addDgnaStruct;

@Controller
@RequestMapping(value="/ucm")
public class UcmController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(UcmController.class);
	private FlexJSON json=new FlexJSON();
	
	/**
	 * 动态重组
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/dgna",method=RequestMethod.POST)
	public void dgna(HttpServletRequest request, HttpServletResponse response){
		int operation=FunUtil.StringToInt(request.getParameter("operation"));
		int groupId=FunUtil.StringToInt(request.getParameter("groupId"));
		int cou=FunUtil.StringToInt(request.getParameter("cou"));
		int attached=FunUtil.StringToInt(request.getParameter("attached"));
		String groupName=DgnaServices.groupNameById(groupId);
		
		String[] data=request.getParameter("data").split(",");
		if(TcpKeepAliveClient.getSocket().isConnected()){
		for(int i=0;i<data.length;i++){
			DgnaBean bean=new DgnaBean();
			
			addDgnaStruct struct = new addDgnaStruct();
			struct.setOpra(2);
			struct.setMscId(1);
			struct.setIssi(Integer.parseInt(data[i]));
			struct.setGssi(groupId);
			struct.setGroupname(groupName);
			struct.setAttached(attached);
			struct.setCou(cou);
			struct.setOperation(operation);
			struct.setStatus(0);
			UcmService.sendDgna(struct);
			this.message="数据已发送";
			this.success=true;
		}
		}else{
			this.message="服务未连接，请检查IP配置";
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
	 * 手台遥启
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/radioOpen",method=RequestMethod.POST)
	public void open(HttpServletRequest request, HttpServletResponse response){
		/*String formData=request.getParameter("data");*/
		String[] data=request.getParameter("data").split(",");
		if(TcpKeepAliveClient.getSocket().isConnected()){
			for(int i=0;i<data.length;i++){
				KillStruct kill = new KillStruct();
				kill.setOperation(1);
				kill.setUserId(Integer.parseInt(data[i]));
				kill.setMsId(1);
				kill.setKillCmd(0);
				UcmService.sendKilledData(kill);
				this.message="数据已发送";
				this.success=true;
			}
			}else{
				this.message="服务未连接，请检查IP配置";
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
	 * 手台遥晕
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/kill",method=RequestMethod.POST)
	public void kill(HttpServletRequest request, HttpServletResponse response){
		/*String formData=request.getParameter("data");*/
		String[] data=request.getParameter("data").split(",");
		if(TcpKeepAliveClient.getSocket().isConnected()){
			for(int i=0;i<data.length;i++){
				KillStruct kill = new KillStruct();
				kill.setOperation(1);
				kill.setUserId(Integer.parseInt(data[i]));
				kill.setMsId(1);
				kill.setKillCmd(1);
				UcmService.sendKilledData(kill);
				this.message="数据已发送";
				this.success=true;
			}
			}else{
				this.message="服务未连接，请检查IP配置";
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

}
