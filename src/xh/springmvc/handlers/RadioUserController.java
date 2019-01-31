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
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.CallListServices;
import xh.mybatis.service.JoinNetService;
import xh.mybatis.service.RadioUserService;
import xh.mybatis.service.TalkGroupService;
import xh.mybatis.service.UcmService;
import xh.mybatis.service.WebLogService;
import xh.org.listeners.SingLoginListener;
import xh.org.socket.RadioUserStruct;
import xh.org.socket.TcpKeepAliveClient;
@Controller
@RequestMapping(value="/radiouser")
public class RadioUserController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(RadioUserController.class);
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
		String E_name=request.getParameter("E_name");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		int is_moto=funUtil.StringToInt(request.getParameter("is_moto"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("C_ID", C_ID);
		map.put("E_name", E_name);
		map.put("vpnId", vpnId);
		map.put("is_moto", is_moto);
		map.put("start", start);
		map.put("limit", limit);
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",RadioUserService.radiouserCount(map));
		result.put("items", RadioUserService.radiouserById(map));
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
	 * 添加无线用户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public synchronized void insertRadioUser(HttpServletRequest request, HttpServletResponse response){
		
		String formData=request.getParameter("formData");
		int resultCode=0;
		int timeout=0;
		int status=0;
		
		RadioUserBean userbean=GsonUtil.json2Object(formData, RadioUserBean.class);
		
		userbean.setTime(funUtil.nowDate());
		if(userbean.getC_IDE()==0 || userbean.getC_IDS()==0){
			if(RadioUserService.radioUserIsExists(userbean.getC_ID())>0){
				this.success=false;
				this.message="该用户已经存在";
			}else{
				
				RadioUserStruct setRadioUser = new RadioUserStruct();
				setRadioUser.setOperation(1);

				setRadioUser.setId(userbean.getC_ID());
				setRadioUser.setName(userbean.getE_name());
				setRadioUser.setAlias(userbean.getE_alias());
				setRadioUser.setMscId(userbean.getE_mscId());
				setRadioUser.setVpnId(userbean.getE_vpnId());
				setRadioUser.setSn(userbean.getE_sn());
				setRadioUser.setCompany(userbean.getE_company());
				setRadioUser.setType(userbean.getE_type());
				setRadioUser.setEnabled(userbean.getE_enabled());
				setRadioUser.setShortData(userbean.getE_shortData());
				setRadioUser.setFullDuple(userbean.getE_fullDuple());
				setRadioUser.setRadioType(userbean.getE_radioType());
				setRadioUser.setAnycall(userbean.getE_anycall());
				setRadioUser.setSaId(userbean.getE_saId());
				setRadioUser.setIaId(userbean.getE_iaId());
				setRadioUser.setVaId(userbean.getE_vaId());
				setRadioUser.setRugId(userbean.getE_rutgId());
				setRadioUser.setPacketData(userbean.getE_packetData());
				setRadioUser.setIp(userbean.getE_ip());
				setRadioUser.setPrimaryTGId(userbean.getE_PrimaryTGId());
				setRadioUser.setAmbienceMonitoring(userbean.getE_ambienceMonitoring());
				setRadioUser.setAmbienceInitiation(userbean.getE_ambienceInitiation());
				setRadioUser.setDirectDial(userbean.getE_directDial());
				setRadioUser.setPstnAccess(userbean.getE_PSTNAccess());
				setRadioUser.setPabxAccess(userbean.getE_pabxAccess());
				setRadioUser.setClir(userbean.getE_clir());
				setRadioUser.setClirOverride(userbean.getE_clirOverride());
				setRadioUser.setKilled(userbean.getE_killed());
				setRadioUser.setMsType(userbean.getE_msType());
				UcmService.sendRadioUser(setRadioUser);
				tag:for(;;){
			          try {
						Thread.sleep(1000);
						timeout++;
						if(TcpKeepAliveClient.getUcmRadioUserMap().get("user_"+userbean.getC_ID())!=null){
							Map<String,Object> resultMap=(Map<String, Object>) TcpKeepAliveClient.getUcmRadioUserMap().get("user_"+userbean.getC_ID());
							status=FunUtil.StringToInt(resultMap.get("status").toString());
							if(status==1){
								resultCode=RadioUserService.insertRadioUser(userbean);
							}
							if(resultCode>0){
								this.success=true;
								this.message="用户添加";
								JoinNetBean bean = new JoinNetBean();
								bean.setId(userbean.getId_JoinNet());
								bean.setChecked(9);
								JoinNetService.updateCheckById(bean);
							}else{
								this.success=false;
								this.message=resultMap.get("message").toString();
							}
							TcpKeepAliveClient.getUcmRadioUserMap().remove("user_"+userbean.getC_ID());
							timeout=0;
							break tag;
						}else{
							if(timeout>=20){
								this.success=false;
								this.message="三方服务器响应超时";
								timeout=0;
								break tag;
							}
							
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			       }
			
				
			}
		}else{
			String name=userbean.getE_name();
			int j=0;
			for(int i=userbean.getC_IDS();i<=userbean.getC_IDE();i++){
				j++;
				if(RadioUserService.radioUserIsExists(i)<1){
					userbean.setC_ID(i);
					userbean.setE_name(name+j);
					RadioUserStruct setRadioUser = new RadioUserStruct();
					setRadioUser.setOperation(1);

					setRadioUser.setId(userbean.getC_ID());
					setRadioUser.setName(userbean.getE_name());
					setRadioUser.setAlias(userbean.getE_alias());
					setRadioUser.setMscId(userbean.getE_mscId());
					setRadioUser.setVpnId(userbean.getE_vpnId());
					setRadioUser.setSn(userbean.getE_sn());
					setRadioUser.setCompany(userbean.getE_company());
					setRadioUser.setType(userbean.getE_type());
					setRadioUser.setEnabled(userbean.getE_enabled());
					setRadioUser.setShortData(userbean.getE_shortData());
					setRadioUser.setFullDuple(userbean.getE_fullDuple());
					setRadioUser.setRadioType(userbean.getE_radioType());
					setRadioUser.setAnycall(userbean.getE_anycall());
					setRadioUser.setSaId(userbean.getE_saId());
					setRadioUser.setIaId(userbean.getE_iaId());
					setRadioUser.setVaId(userbean.getE_vaId());
					setRadioUser.setRugId(userbean.getE_rutgId());
					setRadioUser.setPacketData(userbean.getE_packetData());
					setRadioUser.setIp(userbean.getE_ip());
					setRadioUser.setPrimaryTGId(userbean.getE_PrimaryTGId());
					setRadioUser.setAmbienceMonitoring(userbean.getE_ambienceMonitoring());
					setRadioUser.setAmbienceInitiation(userbean.getE_ambienceInitiation());
					setRadioUser.setDirectDial(userbean.getE_directDial());
					setRadioUser.setPstnAccess(userbean.getE_PSTNAccess());
					setRadioUser.setPabxAccess(userbean.getE_pabxAccess());
					setRadioUser.setClir(userbean.getE_clir());
					setRadioUser.setClirOverride(userbean.getE_clirOverride());
					setRadioUser.setKilled(userbean.getE_killed());
					setRadioUser.setMsType(userbean.getE_msType());
					UcmService.sendRadioUser(setRadioUser);
					tag:for(;;){
				          try {
							Thread.sleep(1000);
							timeout++;
							if(TcpKeepAliveClient.getUcmRadioUserMap().get("user_"+userbean.getC_ID())!=null){
								Map<String,Object> resultMap=(Map<String, Object>) TcpKeepAliveClient.getUcmRadioUserMap().get("user_"+userbean.getC_ID());
								status=FunUtil.StringToInt(resultMap.get("status").toString());
								if(status==1){
									resultCode=RadioUserService.insertRadioUser(userbean);
								}
								if(resultCode>0){
									this.success=true;
									this.message="用户添加";
									JoinNetBean bean = new JoinNetBean();
									bean.setId(userbean.getId_JoinNet());
									bean.setChecked(9);
									JoinNetService.updateCheckById(bean);
								}else{
									this.success=false;
									this.message=resultMap.get("message").toString();
								}
								TcpKeepAliveClient.getUcmRadioUserMap().remove("user_"+userbean.getC_ID());
								timeout=0;
								break tag;
							}else{
								if(timeout>=50){
									this.success=false;
									this.message="三方服务器响应超时";
									timeout=0;
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
	 * 修改无线用户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public synchronized void updateByRadioUserId(HttpServletRequest request, HttpServletResponse response){
		HashMap<String,Object> map = new HashMap<String,Object>();
		Enumeration rnames=request.getParameterNames();
		for (Enumeration e = rnames ; e.hasMoreElements() ;) {
		         String thisName=e.nextElement().toString();
		        String thisValue=request.getParameter(thisName);
		        map.put(thisName, thisValue);
		}
		String param = json.Encode(map);
		RadioUserBean userbean=GsonUtil.json2Object(param, RadioUserBean.class);
		int resultCode=0;
		int timeout=0;
		int status=0;
		RadioUserStruct setRadioUser = new RadioUserStruct();
		setRadioUser.setOperation(2);

		setRadioUser.setId(userbean.getC_ID());
		setRadioUser.setName(userbean.getE_name());
		setRadioUser.setAlias(userbean.getE_alias());
		setRadioUser.setMscId(userbean.getE_mscId());
		setRadioUser.setVpnId(userbean.getE_vpnId());
		setRadioUser.setSn(userbean.getE_sn());
		setRadioUser.setCompany(userbean.getE_company());
		setRadioUser.setType(userbean.getE_type());
		setRadioUser.setEnabled(userbean.getE_enabled());
		setRadioUser.setShortData(userbean.getE_shortData());
		setRadioUser.setFullDuple(userbean.getE_fullDuple());
		setRadioUser.setRadioType(userbean.getE_radioType());
		setRadioUser.setAnycall(userbean.getE_anycall());
		setRadioUser.setSaId(userbean.getE_saId());
		setRadioUser.setIaId(userbean.getE_iaId());
		setRadioUser.setVaId(userbean.getE_vaId());
		setRadioUser.setRugId(userbean.getE_rutgId());
		setRadioUser.setPacketData(userbean.getE_packetData());
		setRadioUser.setIp(userbean.getE_ip());
		setRadioUser.setPrimaryTGId(userbean.getE_PrimaryTGId());
		setRadioUser.setAmbienceMonitoring(userbean.getE_ambienceMonitoring());
		setRadioUser.setAmbienceInitiation(userbean.getE_ambienceInitiation());
		setRadioUser.setDirectDial(userbean.getE_directDial());
		setRadioUser.setPstnAccess(userbean.getE_PSTNAccess());
		setRadioUser.setPabxAccess(userbean.getE_pabxAccess());
		setRadioUser.setClir(userbean.getE_clir());
		setRadioUser.setClirOverride(userbean.getE_clirOverride());
		setRadioUser.setKilled(userbean.getE_killed());
		setRadioUser.setMsType(userbean.getE_msType());
		UcmService.sendRadioUser(setRadioUser);
		tag:for(;;){
	          try {
				Thread.sleep(1000);
				timeout++;
				if(TcpKeepAliveClient.getUcmRadioUserMap().get("user_"+userbean.getC_ID())!=null){
					Map<String,Object> resultMap=(Map<String, Object>) TcpKeepAliveClient.getUcmRadioUserMap().get("user_"+userbean.getC_ID());
					status=FunUtil.StringToInt(resultMap.get("status").toString());
					if(status==1){
						resultCode=RadioUserService.updateByRadioUserId(map);
					}
					if(resultCode>0){
						this.success=true;
						this.message="修改成功";
						JoinNetBean bean = new JoinNetBean();
						bean.setId(userbean.getId_JoinNet());
						bean.setChecked(9);
						JoinNetService.updateCheckById(bean);
					}else{
						this.success=false;
						this.message=resultMap.get("message").toString();
					}
					TcpKeepAliveClient.getUcmRadioUserMap().remove("user_"+userbean.getC_ID());
					timeout=0;
					break tag;
				}else{
					if(timeout>=20){
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
		
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 删除无线用户
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/del",method = RequestMethod.POST)
	public synchronized void deleteBsByRadioUserId(HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("id");
		List<String> list = new ArrayList<String>();
		int resultCode=0;
		int timeout=0;
		int status=0;
		String[] ids=id.split(",");
		for (String str : ids) {
			list.add(str);
			
		}
		for(int i=0;i<list.size();i++){
				RadioUserStruct setRadioUser = new RadioUserStruct();
				setRadioUser.setOperation(3);

				setRadioUser.setId(funUtil.StringToInt(list.get(i).toString()));
				setRadioUser.setName("0");
				setRadioUser.setAlias("0");
				setRadioUser.setMscId(1);
				setRadioUser.setVpnId(0);
				setRadioUser.setSn("0");
				setRadioUser.setCompany("0");
				setRadioUser.setType("0");
				setRadioUser.setEnabled(0);
				setRadioUser.setShortData(0);
				setRadioUser.setFullDuple(0);
				setRadioUser.setRadioType(0);
				setRadioUser.setAnycall(0);
				setRadioUser.setSaId(0);
				setRadioUser.setIaId(0);
				setRadioUser.setVaId(0);
				setRadioUser.setRugId(0);
				setRadioUser.setPacketData("0");
				setRadioUser.setIp("0");
				setRadioUser.setPrimaryTGId(0);
				setRadioUser.setAmbienceMonitoring("0");
				setRadioUser.setAmbienceInitiation("0");
				setRadioUser.setDirectDial("0");
				setRadioUser.setPstnAccess("0");
				setRadioUser.setPabxAccess("0");
				setRadioUser.setClir("0");
				setRadioUser.setClirOverride("0");
				setRadioUser.setKilled("0");
				setRadioUser.setMsType("0");
				
				UcmService.sendRadioUser(setRadioUser);
				tag:for(;;){
			          try {
						Thread.sleep(1000);
						timeout++;
						if(TcpKeepAliveClient.getUcmRadioUserMap().get("user_"+list.get(i).toString())!=null){
							Map<String,Object> resultMap=(Map<String, Object>) TcpKeepAliveClient.getUcmRadioUserMap().get("user_"+list.get(i).toString());
							status=FunUtil.StringToInt(resultMap.get("status").toString());
							if(status==1){
								List<String> list2 = new ArrayList<String>();
								list2.add(list.get(i).toString());
								resultCode=RadioUserService.deleteBsByRadioUserId(list2);
								if(resultCode>0){
									this.message="删除成功";
									this.success=true;
								}
							}
						
							TcpKeepAliveClient.getUcmRadioUserMap().remove("user_"+list.get(i).toString());
							timeout=0;
							break tag;
						}else{
							if(timeout>=20){
								this.success=false;
								this.message="三方服务器响应超时";
								timeout=0;
								break tag;
							}
							
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			       }
		
		}
	
		
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
	/**
	 * 标记为moto手台
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/mark_moto",method = RequestMethod.POST)
	public synchronized void mark_moto(HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("id");
		int is_moto=Integer.parseInt(request.getParameter("is_moto"));
		List<String> list = new ArrayList<String>();
		String[] ids=id.split(",");
		for (String str : ids) {
			list.add(str);
			
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("is_moto", is_moto);
		map.put("list", list);
		int rs=RadioUserService.update_moto(map);
		if(rs>0){
			success=true;
		}
		
		HashMap result = new HashMap();
		result.put("success", success);
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 根据vpnId查询无线用户信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/vpnid",method = RequestMethod.GET)
	public void allByVpnId(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String vpnId=request.getParameter("vpnId");
		String C_ID=request.getParameter("C_ID");
		String E_name=request.getParameter("E_name");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("C_ID", C_ID);
		map.put("E_name", E_name);
		map.put("start", start);
		map.put("limit", limit);
		map.put("vpnId", vpnId);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",RadioUserService.CountByVpnId(map));
		result.put("items", RadioUserService.allByVpnId(map));
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
