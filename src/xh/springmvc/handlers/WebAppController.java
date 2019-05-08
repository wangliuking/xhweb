package xh.springmvc.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.RadioUserService;
import xh.mybatis.service.UserStatusService;
import xh.mybatis.service.WebAppService;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping("/webapp")
public class WebAppController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(WebAppController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	private WebAppService app=new WebAppService();
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> DocPreview(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		Map<String,Object> usermap=SingLoginListener.getLogUserInfoMap().get(request.getSession().getId());
		String vpnId=usermap.get("vpnId")==null?"":usermap.get("vpnId").toString();
		Map<String, Object> user=new HashMap<String, Object>();
		user.put("vpnId",vpnId);
		//在线用户
		int useOnlineCount=UserStatusService.userOnline(user);
		
		//固定基站数量
		int bs_gd_count=app.app_bs_gd_count();
		//直放站数量
		int bs_vertical_count=app.app_verticalbs_count();
		//室内覆盖
		Map<String,Object> room=app.app_room();
		//便携
		Map<String,Object> bx=app.app_portable();
		//应急通信车
		Map<String,Object> embus=app.app_movebus();
		//接入单位
		int vpn=app.app_vpn_count();
		//终端数量		
		int userCount=RadioUserService.radiouserCount(user);
		//接入应用
		int access=app.app_access_count();
		//调度台
		int dispatch=app.app_dispatch_count();
		//地铁
		List<Map<String,Object>> subwaylist=app.app_subway();
		Map<String,Object> subway=new HashMap<String, Object>();
		int a=0;
		subway.put("total", a);
		
		if(subwaylist!=null){
			for(int i=0;i<subwaylist.size();i++){
				Map<String,Object> m=subwaylist.get(i);
				a+=Integer.parseInt(m.get("num").toString());
				subway.put("line-"+m.get("line"), m.get("num"));
			}
			subway.put("total", a);
		}
		
		
		
		
		HashMap result = new HashMap();
		result.put("useOnlineCount", useOnlineCount);
		result.put("bs_gd_count",bs_gd_count );
		result.put("bs_vertical_count",bs_vertical_count);
		result.put("room",room );
		result.put("bx",bx );
		result.put("embus",embus );
		result.put("vpn",vpn );
		result.put("userCount", userCount);
		result.put("access", access);
		result.put("dispatch", dispatch);
		result.put("subway",subway);
		return result;
		
	}

}
