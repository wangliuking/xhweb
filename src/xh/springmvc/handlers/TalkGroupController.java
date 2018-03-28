package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
import xh.mybatis.bean.TalkGroupBean;
import xh.mybatis.service.RadioUserSeriaService;
import xh.mybatis.service.RadioUserService;
import xh.mybatis.service.TalkGroupService;
import xh.mybatis.service.UcmService;
import xh.org.listeners.SingLoginListener;
import xh.org.socket.TalkGroupStruct;
import xh.org.socket.TcpKeepAliveClient;

@Controller
@RequestMapping(value="/talkgroup")
public class TalkGroupController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(TalkGroupController.class);
	private FlexJSON json=new FlexJSON();
	private static Map<String,Object> ucmMap=new HashMap<String, Object>();
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void info(HttpServletRequest request, HttpServletResponse response){
		this.success=true;	
		//获取用户的vpnId	
		HashMap tempMap = (HashMap) SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
		String vpnId = tempMap.get("vpnId").toString();	
		
		String talkgroupid=request.getParameter("talkgroupid");
		String eName=request.getParameter("eName");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("vpnId", vpnId);
		map.put("talkgroupid", talkgroupid);
		map.put("eName", eName);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",TalkGroupService.Count(map));
		result.put("items", TalkGroupService.radioUserBusinessInfo(map));
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
	 * 查询无线用户互联属性
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/mscList",method = RequestMethod.GET)
	public void vpnList(HttpServletRequest request, HttpServletResponse response){
		this.success=true;	
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",TalkGroupService.vpnList().size());
		result.put("items", TalkGroupService.vpnList());
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
	 * 查询无线用户互联属性
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/vpnList",method = RequestMethod.GET)
	public void vpnList2(HttpServletRequest request, HttpServletResponse response){
		this.success=true;	
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",TalkGroupService.vpnList2().size());
		result.put("items", TalkGroupService.vpnList2());
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
	 * 添加组
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public synchronized void insertTalkGroup(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int count = 0;
		int resultCode=0;
		int status=-1;
		
		String formData=request.getParameter("formData");
		TalkGroupBean groupbean=GsonUtil.json2Object(formData, TalkGroupBean.class);
		
		groupbean.setTime(funUtil.nowDate());
		int timeout=0;
		if(groupbean.getTalkgroupIDE()==0 || groupbean.getTalkgroupIDS()==0){
			if(TalkGroupService.isExists(groupbean.getTalkgroupID())>0){
				this.success=true;
				this.message="改通话组已经存在";
			}else{
				//resultCode=TalkGroupService.insertTalkGroup(groupbean);
				TalkGroupStruct setTalkGroupData = new TalkGroupStruct();
				setTalkGroupData.setOperation(1);
				setTalkGroupData.setId(groupbean.getTalkgroupID());
				setTalkGroupData.setName(groupbean.getE_name());
				setTalkGroupData.setAlias(groupbean.getE_alias());
				setTalkGroupData.setMscId(groupbean.getE_mscId());
				setTalkGroupData.setVpnId(groupbean.getE_vpnId());
				setTalkGroupData.setSaId(groupbean.getE_said());
				setTalkGroupData.setIaId(groupbean.getE_iaid());
				setTalkGroupData.setVaId(groupbean.getE_vaid());
				setTalkGroupData.setPreempt(groupbean.getE_preempt());
				setTalkGroupData.setRadioType(FunUtil.StringToInt(groupbean.getE_radioType()));
				setTalkGroupData.setRegroupAble(FunUtil.StringToInt(groupbean.getE_regroupable()));
				setTalkGroupData.setEnabled(groupbean.getE_enabled());
				setTalkGroupData.setDirectDial(groupbean.getE_directDial());
				UcmService.sendTalkGroupData(setTalkGroupData);
				
				 tag:for(;;){
			          try {
						Thread.sleep(1000);
						timeout++;
						if(TcpKeepAliveClient.getUcmGroupMap().get(String.valueOf(groupbean.getTalkgroupID()))!=null){
							Map<String,Object> resultMap=(Map<String, Object>) TcpKeepAliveClient.getUcmGroupMap().get(String.valueOf(groupbean.getTalkgroupID()));
							status=FunUtil.StringToInt(resultMap.get("status").toString());
							if(status==1){
								resultCode=TalkGroupService.insertTalkGroup(groupbean);
							}
								
							if(resultCode>0){
								this.success=true;
								this.message="通话组添加成功";
							}else{
								this.success=false;
								this.message=resultMap.get("message").toString();
							}
							TcpKeepAliveClient.getUcmGroupMap().remove(String.valueOf(groupbean.getTalkgroupID()));
							break tag;
						}else{
							if(timeout>=50){
								this.success=false;
								this.message="三方服务器响应超时";
								break tag;
							}
							
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			       }
				
				
				/*if(resultCode>0){
					this.success=true;
					this.message="通话组添加成功";
				}else{
					this.success=false;
					this.message="添加失败，请检查参数是否合法";
				}*/
			}
		}else{
			String name=groupbean.getE_name();
			for(int i=groupbean.getTalkgroupIDS();i<groupbean.getTalkgroupIDE();i++){
				groupbean.setTalkgroupID(i);
				groupbean.setE_name(name+i);
				resultCode=TalkGroupService.insertTalkGroup(groupbean);
				TalkGroupStruct setTalkGroupData = new TalkGroupStruct();
				setTalkGroupData.setOperation(1);
				setTalkGroupData.setId(groupbean.getTalkgroupID());
				setTalkGroupData.setName(groupbean.getE_name());
				setTalkGroupData.setAlias(groupbean.getE_alias());
				setTalkGroupData.setMscId(groupbean.getE_mscId());
				setTalkGroupData.setVpnId(groupbean.getE_vpnId());
				setTalkGroupData.setSaId(groupbean.getE_said());
				setTalkGroupData.setIaId(groupbean.getE_iaid());
				setTalkGroupData.setVaId(groupbean.getE_vaid());
				setTalkGroupData.setPreempt(groupbean.getE_preempt());
				setTalkGroupData.setRadioType(FunUtil.StringToInt(groupbean.getE_radioType()));
				setTalkGroupData.setRegroupAble(FunUtil.StringToInt(groupbean.getE_regroupable()));
				setTalkGroupData.setEnabled(groupbean.getE_enabled());
				setTalkGroupData.setDirectDial(groupbean.getE_directDial());
				UcmService.sendTalkGroupData(setTalkGroupData);
				tag:for(;;){
			          try {
						Thread.sleep(1000);
						timeout++;
						if(TcpKeepAliveClient.getUcmGroupMap().get(String.valueOf(groupbean.getTalkgroupID()))!=null){
							Map<String,Object> resultMap=(Map<String, Object>) TcpKeepAliveClient.getUcmGroupMap().get(String.valueOf(groupbean.getTalkgroupID()));
							status=FunUtil.StringToInt(resultMap.get("status").toString());
							if(status==1){
								resultCode=TalkGroupService.insertTalkGroup(groupbean);
							}
							if(resultCode>0){
								this.success=true;
								this.message="通话组添加成功";
							}else{
								this.success=false;
								this.message=resultMap.get("message").toString();
							}
							TcpKeepAliveClient.getUcmGroupMap().remove(String.valueOf(groupbean.getTalkgroupID()));
							timeout=0;
							break tag;
						}else{
							if(timeout>=50){
								this.success=false;
								this.message="三方服务器响应超时";
								break tag;
							}
							
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			       }
			}
			
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
	 * 更新通话组
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public void updateByRadioUserId(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		HashMap<String,Object> map = new HashMap<String,Object>();
		Enumeration rnames=request.getParameterNames();
		for (Enumeration e = rnames ; e.hasMoreElements() ;) {
		         String thisName=e.nextElement().toString();
		        String thisValue=request.getParameter(thisName);
		        map.put(thisName, thisValue);
		}
		int count=TalkGroupService.update(map);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result",count);
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 删除通话组
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/del",method = RequestMethod.POST)
	public void deleteBsByRadioUserId(HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("id");
		List<String> list = new ArrayList<String>();
		String[] ids=id.split(",");
		for (String str : ids) {
			list.add(str);
		}
		TalkGroupService.delete(list);
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

	public static Map<String, Object> getUcmMap() {
		return ucmMap;
	}

	public static void setUcmMap(Map<String, Object> ucmMap) {
		TalkGroupController.ucmMap = ucmMap;
	}
	
	
}
