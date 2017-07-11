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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BusinessVpnService;


@Controller
@RequestMapping("/vpn")
public class BusinessVpnController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(BusinessVpnController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	/**
	 * 查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void vpnInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", BusinessVpnService.assetInfo());
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
	 * 删除
	 */
	@RequestMapping("/deleteByVpnId")
	public void deleteByVpnId(HttpServletRequest request, HttpServletResponse response){
		String vpnId = request.getParameter("vpnId");
		this.success=true;
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", BusinessVpnService.deleteByVpnId(vpnId));
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新
	 */
	@RequestMapping("/updateByVpnId")
	public void updateByVpnId(HttpServletRequest request, HttpServletResponse response){
		try {
			request.setCharacterEncoding("utf-8");
			String vpnId = request.getParameter("vpnId");
			String name = request.getParameter("name");
			this.success=true;
			HashMap result = new HashMap();
			result.put("success", success);
			result.put("items", BusinessVpnService.updateByVpnId(vpnId, name));
			String jsonstr = json.Encode(result);
			try {
				response.getWriter().write(jsonstr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	


}
