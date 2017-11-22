package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
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
import xh.mybatis.service.TalkGroupService;
import xh.org.socket.SendData;
import xh.org.socket.TcpKeepAliveClient;

@Controller
@RequestMapping(value="/tools")
public class DgnaController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(DgnaController.class);
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
		
		String[] data=request.getParameter("data").split(",");
		for(int i=0;i<data.length;i++){
			DgnaBean bean=new DgnaBean();
			bean.setOpra(2);
			bean.setMscId(1);
			bean.setIssi(Integer.parseInt(data[i]));
			bean.setGssi(groupId);
			bean.setGroupname(TalkGroupService.GroupNameById(groupId));
			bean.setAttached(attached);
			bean.setCou(cou);
			bean.setOperation(operation);
			bean.setStatus(0);
			/*if(TcpKeepAliveClient.getSocket().isConnected()){
				try {
					//SendData.DGNA(bean);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			
			
		}	
	}
	public void DgnaHander(){
		
	}
	/**
	 * 手台遥启
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/open",method=RequestMethod.POST)
	public void open(HttpServletRequest request, HttpServletResponse response){
		/*String formData=request.getParameter("data");*/
		String[] data=request.getParameter("data").split(",");
		for(int i=0;i<data.length;i++){
			
		}	
	}
	/**
	 * 手台遥毙
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/kill",method=RequestMethod.POST)
	public void kill(HttpServletRequest request, HttpServletResponse response){
		/*String formData=request.getParameter("data");*/
		String[] data=request.getParameter("data").split(",");
		for(int i=0;i<data.length;i++){
			
		}	
	}

}
