package xh.springmvc.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.MenuService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.org.listeners.SingLoginListener;
import xh.redis.server.UserRedis;

@Controller
public class LoginController {
	private String username;
	private String password;
	private String code;
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(LoginController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	@RequestMapping(value = "/web/login", method = RequestMethod.POST)
	@ResponseBody
	public void Login(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {
		this.username = request.getParameter("username");
		/*this.password = EncryptUtil.aesDecrypt(request.getParameter("password"), FunUtil.readXml("web", "key"));*/
		this.password = request.getParameter("password");
		String codeVar="a5fg";
		String codeSession="a5fg";
		
		System.out.println("用户:"+username);
		System.out.println("密码:"+password);
		/*try{
		codeSession=funUtil.getSession(request, "code");
		}catch(NullPointerException e){
			
		}*/
		String toSign = request.getParameter("ToSign");
		
		
		String signedData = request.getParameter("Signature");
		
		
		Map<String,Object> map=new HashMap<String, Object>();
		map = WebUserServices.selectUserByRootAndPass(username, funUtil.MD5(password));

		// 初始化接口地址
		String ip = funUtil.readXml("ca", "ip");
		int port = funUtil.StringToInt(funUtil.readXml("ca", "port"));
		// 验证
		String projectId = "yjyypt";
		String reqId = "1";
		String code="200";
		String url="../index.html";
		//!username.equals("admin")
		
		/*if(username.indexOf("admin")>-1 || username.indexOf("test")>-1){
			code="200";
			log.info("超级账号免key登录");
			
		}else{
			SccaGwSDK.init("http://192.168.120.152:8080/sign-gw");
	        String rs = SccaGwSDK.certLogin(projectId, toSign, signedData,reqId);	
			int startPos = rs.indexOf("code");
			code = rs.substring(startPos + 6 ,startPos + 9);
			Map<String,Object> camap=GsonUtil.json2Object(rs, Map.class);
			log.info("登陆签名验证返回数据如下:");
			log.info("camap->"+camap);
		}*/
		
		if(!codeVar.isEmpty()){
			if(!codeVar.toLowerCase().equals(codeSession.toLowerCase())){
				code="001";//验证码错误
			}
		}else{
			code="002";
		}

		
		
		if ( code.equals("200") ) {
			if (map!=null) {
				if (map.get("status").toString().equals("1")) {
					this.success = true;
					this.message = "登录系统成功";
					SingLoginListener.isLogin(session, username,password);
					
					String role=SingLoginListener.getLogUserInfoMap().get(request.getSession().getId()).get("roleId").toString();
					int roleId=Integer.parseInt(role);
					Map<String,Object> menuMap=MenuService.menuList(roleId);
					
					if(menuMap!=null){
						if(menuMap.get("m_5")!=null){
							url=Integer.parseInt(menuMap.get("m_5").toString())==0?"../main.html":url;
						}
					}

					webLogBean.setOperator(funUtil.loginUser(request));
					webLogBean.setOperatorIp(funUtil.getIpAddr(request));
					webLogBean.setStyle(4);
					webLogBean.setContent("登录系统");
					WebLogService.writeLog(webLogBean);
				} else {
					this.message = "该账号已经被禁用，请联系管理员";
					this.success = false;
				}

			} else {
				this.success = false;
				this.message = "用户名或者密码错误!";

			}
		}else if(code.equals("001")){
			this.message = "验证码输入错误请重新输入!";
			this.success = false;
		}else if(code.equals("002")){
			this.message = "验证码不能为空";
			this.success = false;
		}else{
			this.message = "证书登录验证失败!";
			this.success = false;
		}
	
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("url", url);
		result.put("code", code);
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
	 * 退出登录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/web/loginOut")
	public String LoginOut(HttpServletRequest request,
			HttpServletResponse response) {
		webLogBean.setOperator(funUtil.loginUser(request));
		webLogBean.setOperatorIp(funUtil.getIpAddr(request));
		webLogBean.setStyle(4);
		webLogBean.setContent("登录系统");
		WebLogService.writeLog(webLogBean);
		SingLoginListener.getLogUserMap().remove(request.getSession().getId());
		SingLoginListener.getLoginUserPowerMap().remove(request.getSession().getId());
		//删除redis1中的session
		UserRedis.delSession(request.getSession().getId());
		return "redirect:/Views/login.html";

	}
	//会话标识
	@RequestMapping(value = "/web/clearSession")
	public void ClearSession(HttpServletRequest request,
			HttpServletResponse response) {
		
		SingLoginListener.getLogUserMap().remove(request.getSession().getId());
		SingLoginListener.getLoginUserPowerMap().remove(request.getSession().getId());
		request.getSession(true).invalidate();//清空session
		Cookie cookie=request.getCookies()[0];
		
		cookie.setMaxAge(0);
		
		/*return "redirect:/Views/login.html";*/

	}

	@RequestMapping(value = "/web/loginUserInfo")
	@ResponseBody
	public void LoginUserInfo(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String,Object> result = new HashMap<String,Object>();
		if (SingLoginListener.isOnline(request.getSession())) {
			result=(HashMap<String, Object>) SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
		}
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/web/loginUserPower")
	@ResponseBody
	public void LoginUserPower(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String,Object> result = new HashMap<String,Object>();
		if (SingLoginListener.isOnline(request.getSession())) {
			result=(HashMap<String, Object>) SingLoginListener.getLoginUserPowerMap().get(request.getSession().getId());
		}
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
