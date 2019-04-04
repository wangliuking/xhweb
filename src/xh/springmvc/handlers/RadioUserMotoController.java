package xh.springmvc.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.constant.ConstantLog;
import xh.constant.ConstantMap;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.ChartBean;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.RadioBean;
import xh.protobuf.RadioUserBean;
import xh.mybatis.bean.RadioUserMotoBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.CallListServices;
import xh.mybatis.service.JoinNetService;
import xh.mybatis.service.RadioUserMotoService;
import xh.mybatis.service.RadioUserService;
import xh.mybatis.service.TalkGroupService;
import xh.mybatis.service.UcmService;
import xh.mybatis.service.WebLogService;
import xh.org.listeners.SingLoginListener;
import xh.org.socket.MotoTcpClient;
import xh.org.socket.RadioUserStruct;
import xh.org.socket.SendData;
import xh.org.socket.TcpKeepAliveClient;
@Controller
@RequestMapping(value="/usermoto")
public class RadioUserMotoController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(RadioUserMotoController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	
	/**
	 * 查询无线用户信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void radiouserById(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		//获取用户的vpnId
		HashMap tempMap = (HashMap) SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
		String vpnId = tempMap.get("vpnId").toString();
		
		String C_ID=request.getParameter("C_ID");
		String type=request.getParameter("type");
		
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("C_ID", C_ID);
		map.put("type", type);
		map.put("start", start);
		map.put("limit", limit);
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",RadioUserMotoService.count(map));
		result.put("items", RadioUserMotoService.radiouserById(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping(value="/radioUserMotoGet",method = RequestMethod.GET)
	@ResponseBody
	public HashMap radioUserMotoGet(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String radioUserAlias=request.getParameter("radioUserAlias");
		
		/*int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);*/
		RadioUserMotoBean bean2=new RadioUserMotoBean();
		if(MotoTcpClient.getSocket().isConnected()){
			RadioUserBean bean=new RadioUserBean();
			bean.setRadioUserAlias(radioUserAlias);
			bean.setCallId(FunUtil.RandomAlphanumeric(8));
			SendData.RadioUserGetReq(bean);
			long nowtime=System.currentTimeMillis();
			tag:for(;;){			
				long tt=System.currentTimeMillis();
				if(ConstantMap.getMotoResultMap().containsKey(bean.getCallId())){
					this.success=true;
					if(ConstantMap.getMotoResultMap().get(bean.getCallId()).equals("0")){
						this.message="没有查询到相关数据";
						this.success=false;
					}else{
						bean2=(RadioUserMotoBean) ConstantMap.getMotoResultMap().get(bean.getCallId()+"map");
						RadioUserMotoService.vAdd(bean2);
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
		result.put("data",bean2);
		return result;
	}

	@RequestMapping(value="/radioList",method = RequestMethod.GET)
	@ResponseBody
	public HashMap radioList(HttpServletRequest request, HttpServletResponse response){
		HashMap result = new HashMap();
		result.put("items", RadioUserMotoService.radioList());
		return result;		
	}
	@RequestMapping(value="/securityGroupList",method = RequestMethod.GET)
	@ResponseBody
	public HashMap securityGroupList(HttpServletRequest request, HttpServletResponse response){
		HashMap result = new HashMap();
		result.put("items", RadioUserMotoService.securityGroupList());
		return result;		
	}
	/**
	 * 添加无线用户
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String,Object> insertRadioUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String formData=request.getParameter("formData");	
		RadioUserMotoBean userbean=GsonUtil.json2Object(formData, RadioUserMotoBean.class);	
		if(userbean.getRadioUserAlias()==null){
			userbean.setRadioUserAlias(String.valueOf(userbean.getC_ID()));
		}
		if(RadioUserMotoService.radioUserIsExists(userbean.getC_ID())==0){
			if(RadioUserMotoService.radio_one(String.valueOf(userbean.getC_ID()))!=null ){
				userbean.setRadioID(String.valueOf(userbean.getC_ID()));
				userbean.setSecurityGroup(RadioUserMotoService.radio_one(String.valueOf(userbean.getC_ID())).getSecurityGroup());
				if(userbean.getRadioUserCapabilityProfileAlias()==null){
					userbean.setRadioUserCapabilityProfileAlias(userbean.getSecurityGroup());
				}
				xh.protobuf.RadioUserBean bean=new xh.protobuf.RadioUserBean();
				bean.setRadioID(String.valueOf(userbean.getC_ID()));
				bean.setRadioUserAlias(String.valueOf(userbean.getC_ID()));
				bean.setSecurityGroup(userbean.getSecurityGroup());
				bean.setRadioUserCapabilityProfileAlias(userbean.getRadioUserCapabilityProfileAlias());
				bean.setUserEnabled(userbean.getUserEnabled().equals("1")?"Y":"N");
				bean.setInterconnectEnabled(userbean.getInterconnectEnabled().equals("1")?"Y":"N");
				bean.setPacketDataEnabled(userbean.getPacketDataEnabled().equals("1")?"Y":"N");
				bean.setShortDataEnabled(userbean.getShortDataEnabled().equals("1")?"Y":"N");
				bean.setFullDuplexEnabled(userbean.getFullDuplexEnabled().equals("1")?"Y":"N");
				bean.setCallId(FunUtil.RandomAlphanumeric(8));
				Map<String,Object> map=RadioUserMotoService.insertRadioUser(userbean,bean);
				if(Integer.parseInt(map.get("rs").toString())>0){
					this.success=true;
					this.message="添加用户成功";
					FunUtil.WriteLog(request, ConstantLog.ADD, "添加moto用户："+bean.getRadioID());
				}else{
					this.message="用户添加失败："+map.get("errMsg")==null?"":map.get("errMsg").toString();
					this.success=false;
				}
			}else{
				this.message="终端ID:["+userbean.getC_ID()+"]不存在，请先添加终端ID";
				this.success=false;
			}
		}else{
			this.message="用户已经存在";
			this.success=false;
		}
		Map<String,Object> rmap=new HashMap<String, Object>();
		rmap.put("success", success);
		rmap.put("message", message);
		return rmap;
		}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String,Object> updateByRadioUserId(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String formData=request.getParameter("formData");		
		RadioUserMotoBean userbean=GsonUtil.json2Object(formData, RadioUserMotoBean.class);	
		xh.protobuf.RadioUserBean bean=new xh.protobuf.RadioUserBean();
		bean.setRadioID(String.valueOf(userbean.getC_ID()));
		bean.setRadioUserAlias(String.valueOf(userbean.getC_ID()));
		bean.setSecurityGroup(userbean.getSecurityGroup());
		bean.setRadioUserCapabilityProfileAlias(userbean.getRadioUserCapabilityProfileAlias());
		bean.setUserEnabled(userbean.getUserEnabled().equals("1")?"Y":"N");
		bean.setInterconnectEnabled(userbean.getInterconnectEnabled().equals("1")?"Y":"N");
		bean.setPacketDataEnabled(userbean.getPacketDataEnabled().equals("1")?"Y":"N");
		bean.setShortDataEnabled(userbean.getShortDataEnabled().equals("1")?"Y":"N");
		bean.setFullDuplexEnabled(userbean.getFullDuplexEnabled().equals("1")?"Y":"N");
		bean.setCallId(FunUtil.RandomAlphanumeric(8));
		Map<String,Object> map=RadioUserMotoService.updateByRadioUserId(userbean,bean);
		if(Integer.parseInt(map.get("rs").toString())>0){
			this.success=true;
			this.message="修改用户成功";
			FunUtil.WriteLog(request, ConstantLog.UPDATE, "修改moto用户："+bean.getRadioID());
		}else{
			this.message="修改数据失败："+map.get("errMsg")==null?"":map.get("errMsg").toString();
			this.success=false;
			this.success=false;
		}
		
		
		Map<String,Object> rmap=new HashMap<String, Object>();
		rmap.put("success", success);
		rmap.put("message", message);
		return rmap;
		}
	
	@RequestMapping(value="/deleteByRadioUserId",method = RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String,Object> deleteByRadioUserId(HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String formData=request.getParameter("formData");		
		RadioUserMotoBean userbean=GsonUtil.json2Object(formData, RadioUserMotoBean.class);	
		
		System.out.println("dd->"+userbean.toString());
		xh.protobuf.RadioUserBean bean=new xh.protobuf.RadioUserBean();
		bean.setRadioID(String.valueOf(userbean.getC_ID()));
		bean.setRadioUserAlias(String.valueOf(userbean.getC_ID()));
		bean.setSecurityGroup(userbean.getSecurityGroup());
		bean.setRadioUserCapabilityProfileAlias(userbean.getRadioUserCapabilityProfileAlias());
		bean.setUserEnabled(userbean.getUserEnabled().equals("1")?"Y":"N");
		bean.setInterconnectEnabled(userbean.getInterconnectEnabled().equals("1")?"Y":"N");
		bean.setPacketDataEnabled(userbean.getPacketDataEnabled().equals("1")?"Y":"N");
		bean.setShortDataEnabled(userbean.getShortDataEnabled().equals("1")?"Y":"N");
		bean.setFullDuplexEnabled(userbean.getFullDuplexEnabled().equals("1")?"Y":"N");
		bean.setCallId(FunUtil.RandomAlphanumeric(8));
		Map<String,Object> map=RadioUserMotoService.deleteByRadioUserId(bean);
		if(Integer.parseInt(map.get("rs").toString())>0){
			this.success=true;
			this.message="删除用户成功";
			FunUtil.WriteLog(request, ConstantLog.DELETE, "删除moto用户："+bean.getRadioID());
		}else{
			this.message="删除数据失败："+map.get("errMsg")==null?"":map.get("errMsg").toString();
			this.success=false;
		}
		Map<String,Object> rmap=new HashMap<String, Object>();
		rmap.put("success", success);
		rmap.put("message", message);
		return rmap;
		}
	

}
