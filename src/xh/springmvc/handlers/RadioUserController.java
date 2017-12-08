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
import xh.mybatis.service.WebLogService;
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
		String C_ID=request.getParameter("C_ID");
		String E_name=request.getParameter("E_name");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("C_ID", C_ID);
		map.put("E_name", E_name);
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
	public void insertRadioUser(HttpServletRequest request, HttpServletResponse response){
		
		String formData=request.getParameter("formData");
		int resultCode=0;
		
		RadioUserBean userbean=GsonUtil.json2Object(formData, RadioUserBean.class);
		
		userbean.setTime(funUtil.nowDate());
		if(userbean.getC_IDE()==0 || userbean.getC_IDS()==0){
			if(RadioUserService.radioUserIsExists(userbean.getC_ID())>0){
				this.success=false;
				this.message="该用户已经存在";
			}else{
				resultCode=RadioUserService.insertRadioUser(userbean);
				if(resultCode>0){
					this.success=true;
					this.message="用户添加";
					JoinNetBean bean = new JoinNetBean();
					bean.setId(userbean.getId_JoinNet());
					bean.setChecked(9);
					JoinNetService.updateCheckById(bean);
				}else{
					this.success=false;
					this.message="添加失败，请检查填写的参数是否合法";
				}
				
			}
		}else{
			String name=userbean.getE_name();
			for(int i=userbean.getC_IDS();i<=userbean.getC_IDE();i++){
				if(RadioUserService.radioUserIsExists(i)<1){
					userbean.setC_ID(i);
					userbean.setE_name(name+i);
					resultCode=RadioUserService.insertRadioUser(userbean);
					if(resultCode>0){
						this.success=true;
						this.message="用户添加成功";
						JoinNetBean bean = new JoinNetBean();
						bean.setId(userbean.getId_JoinNet());
						bean.setChecked(9);
						JoinNetService.updateCheckById(bean);
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
	public void updateByRadioUserId(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		HashMap<String,Object> map = new HashMap<String,Object>();
		Enumeration rnames=request.getParameterNames();
		for (Enumeration e = rnames ; e.hasMoreElements() ;) {
		         String thisName=e.nextElement().toString();
		        String thisValue=request.getParameter(thisName);
		        map.put(thisName, thisValue);
		}
		int count=RadioUserService.updateByRadioUserId(map);
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
	 * 删除无线用户
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
		RadioUserService.deleteBsByRadioUserId(list);
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
