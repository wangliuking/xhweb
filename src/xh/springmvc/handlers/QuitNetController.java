package xh.springmvc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.inject.Inject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import net.sf.json.JSONObject;
import xh.func.plugin.DownLoadUtils;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.QuitNetBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.JoinNetService;
import xh.mybatis.service.QuitNetService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;

@Controller
@RequestMapping(value = "/quitnet")
public class QuitNetController {

	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(QuitNetController.class);
	private FlexJSON json = new FlexJSON();
	private String unit;
	/**
	 * 查询所有流程
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET)
	public void selectAll(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));
		String user=funUtil.loginUser(request);
		WebUserBean userbean=WebUserServices.selectUserByUser(user);
		int roleId=userbean.getRoleId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("user", user);
		map.put("roleId", roleId);
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", QuitNetService.selectAll(map));
		result.put("totals", QuitNetService.dataCount(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/applyProgress", method = RequestMethod.GET)
	public void applyProgress(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", QuitNetService.applyProgress(id));
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

//	@RequestMapping(value = "/demo", method = RequestMethod.GET)
//	public List<Integer> selectquitNumber(@RequestBody UserFormBean userFormBean) {
//		this.success = true;
//		String userName = request.getParameter("userName");
//		List<Integer> ids =  new ArrayList<>();
//		return quitNetService.selectquitNumber(userName);
//	}

	/**
	 * 退网申请
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/quitNet", method = RequestMethod.POST)
	public void quitNet(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		String jsonData = request.getParameter("formData");
		QuitNetBean bean = GsonUtil.json2Object(jsonData, QuitNetBean.class);
		bean.setUserName(funUtil.loginUser(request));
		unit = funUtil.loginUser(request);
		bean.setQuitTime(funUtil.nowDate());
		log.info("data==>" + bean.toString());

		int rst = QuitNetService.quitNet(bean);
		WebLogBean webLogBean = new WebLogBean();
		if (rst == 1) {
			this.message = "退网申请信息已经成功提交";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("退网申请信息，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
//			//----发送通知邮件
//			sendNotify(bean.getUser_MainManager(), "退网申请信息已经成功提交,请审核。。。", request);
//			//----END
		} else {
			this.message = "退网申请信息提交失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("message", message);
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
	 * 主管部门审核
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedOne", method = RequestMethod.POST)
	public void checkedOne(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int quit = funUtil.StringToInt(request.getParameter("quit"));
		String user = request.getParameter("user");
		QuitNetBean bean = new QuitNetBean();
		bean.setId(id);
		if(quit == 1){
			bean.setQuit(1);
		}else if(quit ==-1){
			bean.setQuit(-1);
		}
		bean.setTime1(funUtil.nowDate());
		log.info("data==>" + bean.toString());
		WebLogBean webLogBean = new WebLogBean();
		int rst = QuitNetService.checkedOne(bean);
		if (rst == 1){
			this.message = "审核提交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核退网申请，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			//----发送通知邮件
			sendNotify(user, "退网申请信息审核，请管理人审核。。。", request);
			//----END
		} else {
			this.message = "审核提交失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("message", message);
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

	@RequestMapping(value = "/checkedTwo", method = RequestMethod.POST)
	public void checkedTwo(HttpServletRequest request,
						   HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String note1 = request.getParameter("note1");
		String user = request.getParameter("user");
		QuitNetBean bean = new QuitNetBean();
		bean.setId(id);
		bean.setNote1(note1);
		bean.setQuit(2);
		bean.setTime2(funUtil.nowDate());
		log.info("data==>" + bean.toString());
		WebLogBean webLogBean = new WebLogBean();
		int rst = QuitNetService.checkedTwo(bean);
		if (rst == 1){
			this.message = "审核提交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核退网申请，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotify(user, "退网申请信息审核，请管理人审核。。。", request);
			//----END
		} else {
			this.message = "审核提交失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("message", message);
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
	 * 用户确认
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sureFile", method = RequestMethod.POST)
	public void sureFile(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int quit = funUtil.StringToInt(request.getParameter("quit"));
		String note = request.getParameter("note");
		QuitNetBean bean = new QuitNetBean();
		bean.setId(id);
		if(quit == 3){
			bean.setQuit(3);
		}else if(quit == 1){
			bean.setQuit(1);
		}
		bean.setTime3(funUtil.nowDate());
		bean.setNote(note);
		WebLogBean webLogBean = new WebLogBean();

		int rst = QuitNetService.sureFile(bean);
		if (rst == 1) {
			this.message = "确认成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			//----发送通知邮件
			//sendNotify(bean.getUser_MainManager(), "入网申请信息已经成功提交,请审核。。。", request);
			//----END
		} else {
			this.message = "确认失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("message", message);
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
	 * 发送邮件
	 * @param recvUser	邮件接收者
	 * @param content	邮件内容
	 * @param request
	 */
	public void sendNotify(String recvUser,String content,HttpServletRequest request){
		//----发送通知邮件
		EmailBean emailBean = new EmailBean();
		emailBean.setTitle("退网申请");
		emailBean.setRecvUser(recvUser);
		emailBean.setSendUser(funUtil.loginUser(request));
		emailBean.setContent(content);
		emailBean.setTime(funUtil.nowDate());
		EmailService.insertEmail(emailBean);
		//----END
	}

}
