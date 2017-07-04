package xh.springmvc.handlers;

import java.io.IOException;
import java.util.HashMap;
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
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.JoinNetService;
import xh.mybatis.service.WebLogService;

@Controller
@RequestMapping(value = "/net")
public class JoinNetController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(JoinNetController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	
	/**
	 * 查询所有流程
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET)
	public void selectAll(HttpServletRequest request,HttpServletResponse response) {
		this.success = true;
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items",JoinNetService.selectAll(map));
		result.put("totals",JoinNetService.dataCount());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		log.debug(jsonstr);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 入网申请
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertNet", method = RequestMethod.POST)
	public void insertNet(HttpServletRequest request,HttpServletResponse response) {
		this.success = true;
		
		String jsonData=request.getParameter("formData");
		JoinNetBean bean=GsonUtil.json2Object(jsonData, JoinNetBean.class);
		bean.setUserName(funUtil.loginUser(request));
		log.info("data==>"+bean.toString());
		
		int rst=JoinNetService.insertNet(bean);
		if (rst==1) {
			this.message="入网申请信息已经成功提交";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("入网申请信息，data="+bean.toString());
			WebLogService.writeLog(webLogBean);
		}else {
			this.message="入网申请信息提交失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result",rst);
		result.put("message",message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		log.debug(jsonstr);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}
