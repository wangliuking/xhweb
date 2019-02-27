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

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.ChartBean;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.RadioUserBean;
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
import xh.org.socket.RadioUserStruct;
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
		String M_RadioUserAlias=request.getParameter("M_RadioUserAlias");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("C_ID", C_ID);
		map.put("M_RadioUserAlias", M_RadioUserAlias);
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
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String,Object> insertRadioUser(HttpServletRequest request, HttpServletResponse response){
		
		String formData=request.getParameter("formData");
		int resultCode=0;
		int timeout=0;
		int status=0;		
		RadioUserMotoBean userbean=GsonUtil.json2Object(formData, RadioUserMotoBean.class);	
		//userbean.setTime(funUtil.nowDate());
		if(userbean.getC_IDE()==0 || userbean.getC_IDS()==0){
			if(RadioUserMotoService.radioUserIsExists(userbean.getC_ID())>0){
				this.success=false;
				this.message="该用户已经存在";
			}else{
				int rs=RadioUserMotoService.insertRadioUser(userbean);
				if(rs>0){
					this.success=true;
					this.message="添加用户成功";
					webLogBean.setOperator(funUtil.loginUser(request));
					webLogBean.setOperatorIp(funUtil.getIpAddr(request));
					webLogBean.setStyle(1);
					webLogBean.setContent("新增moto用户用户，id="+userbean.getC_ID());
					webLogBean.setCreateTime(funUtil.nowDate());
					WebLogService.writeLog(webLogBean);
				}else{
					this.message="参数错误";
					this.success=false;
				}
				
			}
		}else{
			for(int i=userbean.getC_IDS();i<=userbean.getC_IDE();i++){
				userbean.setC_ID(i);
				userbean.setRadioUserAlias(String.valueOf(i));
				System.out.println("bean->"+RadioUserMotoService.radio_one(String.valueOf(i)));
				
				if(RadioUserMotoService.radioUserIsExists(userbean.getC_ID())==0){
					if(RadioUserMotoService.radio_one(String.valueOf(i))!=null ){
						userbean.setSecurityGroup(RadioUserMotoService.radio_one(String.valueOf(i)).getSecurityGroup());
						int rs=RadioUserMotoService.insertRadioUser(userbean);
						if(rs>0){
							this.success=true;
							this.message="添加用户成功";
							webLogBean.setOperator(funUtil.loginUser(request));
							webLogBean.setOperatorIp(funUtil.getIpAddr(request));
							webLogBean.setStyle(1);
							webLogBean.setContent("新增moto用户用户，id="+userbean.getC_ID());
							webLogBean.setCreateTime(funUtil.nowDate());
							WebLogService.writeLog(webLogBean);
						}else{
							this.message="参数错误";
							this.success=false;
						}
					}
						
					
				}
			}
		}
		Map<String,Object> rmap=new HashMap<String, Object>();
		rmap.put("success", success);
		rmap.put("message", message);
		return rmap;
		}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String,Object> updateByRadioUserId(HttpServletRequest request, HttpServletResponse response){
		
		String formData=request.getParameter("formData");
		int resultCode=0;
		int timeout=0;
		int status=0;		
		RadioUserMotoBean userbean=GsonUtil.json2Object(formData, RadioUserMotoBean.class);	
		int rs=RadioUserMotoService.updateByRadioUserId(userbean);
		if(rs>0){
			this.success=true;
			this.message="修改用户成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("修改moto用户用户，id="+userbean.getC_ID());
			webLogBean.setCreateTime(funUtil.nowDate());
			WebLogService.writeLog(webLogBean);
		}else{
			this.message="参数错误";
			this.success=false;
		}
		
		
		Map<String,Object> rmap=new HashMap<String, Object>();
		rmap.put("success", success);
		rmap.put("message", message);
		return rmap;
		}
	
	/**
	 * 添加无线用户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteByRadioUserId",method = RequestMethod.POST)
	@ResponseBody
	public synchronized Map<String,Object> deleteByRadioUserId(HttpServletRequest request, HttpServletResponse response){
		
		String[] ids=request.getParameter("ids").split(",");
		int resultCode=0;
		int timeout=0;
		int status=0;
		List<String> list=new ArrayList<String>();
		
		for (String str: ids) {
			list.add(str);
		}
		int rs=RadioUserMotoService.deleteByRadioUserId(list);
		if(rs>0){
			this.success=true;
			this.message="删除用户成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("删除moto用户用户，id="+ids);
			webLogBean.setCreateTime(funUtil.nowDate());
			WebLogService.writeLog(webLogBean);
		}else{
			this.message="删除失败";
			this.success=false;
		}
		Map<String,Object> rmap=new HashMap<String, Object>();
		rmap.put("success", success);
		rmap.put("message", message);
		return rmap;
		}
	

}
