package xh.springmvc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import net.sf.json.JSONObject;
import xh.func.plugin.DownLoadUtils;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.CommunicationSupportBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.CommunicationSupportService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping(value = "/support")
public class CommunicationSupportController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(CommunicationSupportController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
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
		Map<String,Object> power = SingLoginListener.getLoginUserPowerMap().get(request.getSession().getId());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("user", user);
		map.put("power", power.get("b_check_communicationsupport"));
		map.put("roleId", roleId);
		map.put("roleType", userbean.getRoleType());

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", CommunicationSupportService.selectAll(map));

		result.put("totals", CommunicationSupportService.dataCount(map));
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
		result.put("items", CommunicationSupportService.applyProgress(id));
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
	 * 保障申请
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertSupport", method = RequestMethod.POST)
	public void insertSupport(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		String jsonData = request.getParameter("formData");
		CommunicationSupportBean bean = GsonUtil.json2Object(jsonData, CommunicationSupportBean.class);
		bean.setUserName(funUtil.loginUser(request));
		bean.setTime(funUtil.nowDate());
		log.info("data==>" + bean.toString());
		int rst = CommunicationSupportService.insertSupport(bean);
		if (rst == 1) {
			this.message = "保障申请信息已经成功提交";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("保障申请信息，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			//----发送通知邮件
			sendNotifytoGroup("b_check_communicationsupport",10001, "保障申请信息", request);
			//----END
		} else {
			this.message = "保障申请信息提交失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("message", message);
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
		String note1 = request.getParameter("note1");
		/*String user = request.getParameter("user");*/
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		bean.setChecked(1);
		bean.setUser1(funUtil.loginUser(request));
		bean.setTime1(funUtil.nowDate());
		bean.setNote1(note1);
		log.info("data==>" + bean.toString());

		int rst = CommunicationSupportService.checkedOne(bean);
		if (rst == 1) {
			this.message = "审核提交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核保障申请，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			//----发送通知邮件
			sendNotifytoGroup("b_check_communicationsupport",10002, "保障申请信息审核，请管理部门领导审核并移交经办人", request);
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
	 * 管理方领导指派管理方处理
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedSeventeen", method = RequestMethod.POST)
	public void checkedSeventeen(HttpServletRequest request,
							  HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		String user = request.getParameter("user");
		String user1 = request.getParameter("user1");
		String userName=request.getParameter("userName");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		bean.setChecked(checked);
		bean.setCheckUser(user);
		bean.setTime2(funUtil.nowDate());
		int rst = CommunicationSupportService.checkedSeventeen(bean);
		if (rst == 1) {
			this.message = "管理方领导指派经办人处理";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			if(checked==-1){
				sendNotifytoSingle(userName, "你提交的通讯保障申请被拒绝了", request);
				sendNotifytoSingle(user1, "本次保障任务被管理部门拒绝了", request);
			}else{
				sendNotifytoSingle(user, "用户提交了通信保障任务，请处理相关事宜", request);
			}
			
			//----END
		} else {
			this.message = "通知管理方处理失败";
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
	 * 管理方向用户单位发送通信保障需求确认消息（该请求消息可包含上传附件的功能）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedTwo", method = RequestMethod.POST)
	public void checkedTwo(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("userName");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName1(fileName);
		bean.setFilePath1(path);
		bean.setId(id);
		bean.setChecked(3);
		bean.setTime3(funUtil.nowDate());
		int rst = CommunicationSupportService.checkedTwo(bean);
		if (rst == 1) {
			this.message = "保障需求确认消息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知用户处理(保障需求确认消息)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			//----发送通知邮件
			sendNotifytoSingle(user, "管理方经办人上传了通信保障确认文件，请确认", request);
			//----END
		} else {
			this.message = "通知经办人处理失败";
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

	/**用户单位核准该消息
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedThree", method = RequestMethod.POST)
	public void checkedThree(HttpServletRequest request,
							 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String note2 = request.getParameter("note2");
		String user = request.getParameter("checkUser");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		bean.setChecked(4);
		bean.setTime4(funUtil.nowDate());
		bean.setNote4(note2);
		bean.setUser4(funUtil.loginUser(request));
		int rst = CommunicationSupportService.checkedThree(bean);
		if (rst == 1) {
			this.message = "确认成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "用户已经确认通信保障文件", request);
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
	 * 管理方根据通信保障类型发送消息通知服务提供方(该请求消息可包含上传附件的功能)
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedFour", method = RequestMethod.POST)
	public void checkedFour(HttpServletRequest request,
						   HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		/*String user = request.getParameter("user");*/
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName2(fileName);
		bean.setFilePath2(path);
		bean.setId(id);
		bean.setChecked(5);
		bean.setTime5(funUtil.nowDate());
		int rst = CommunicationSupportService.checkedFour(bean);
		if (rst == 1) {
			this.message = "保障类型发送消息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知服务提供商（通信保障），data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoGroup("b_check_communicationsupport",10003, "通信保障任务", request);
			//----END
		} else {
			this.message = "通知经办人处理失败";
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
	 *服务提供方审核信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedFive", method = RequestMethod.POST)
	public void checkedFive(HttpServletRequest request,
							 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String note3 = request.getParameter("note3");
		String user = request.getParameter("checkUser");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		bean.setChecked(6);
		bean.setTime6(funUtil.nowDate());
		bean.setNote6(note3);
		bean.setUser6(funUtil.loginUser(request));
		int rst = CommunicationSupportService.checkedFive(bean);
		if (rst == 1) {
			this.message = "确认成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "服务提供方已确认消息", request);
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
	 * 服务提供方发送审核请求消息给管理方(该请求消息可包含上传附件的功能)
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedSix", method = RequestMethod.POST)
	public void checkedSix(HttpServletRequest request,
						   HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("checkUser");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName3(fileName);
		bean.setFilePath3(path);
		bean.setId(id);
		bean.setChecked(7);
		bean.setTime7(funUtil.nowDate());
		int rst = CommunicationSupportService.checkedSix(bean);
		if (rst == 1) {
			this.message = "审核请求消息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(审核请求消息)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "保障审核请求消息", request);
			//----END
		} else {
			this.message = "通知管理方处理失败";
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

	/**管理方审核
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedSeven", method = RequestMethod.POST)
	public void checkedSeven(HttpServletRequest request,
							 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		String note4 = request.getParameter("note4");
		String user = request.getParameter("user6");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		if(checked == 8) {
			bean.setChecked(8);
		}else if(checked == 6){
			bean.setChecked(6);
		}
		bean.setTime8(funUtil.nowDate());
		bean.setNote8(note4);
		bean.setUser8(funUtil.loginUser(request));
		int rst = CommunicationSupportService.checkedSeven(bean);
		if (rst == 1) {
			this.message = "确认成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			if(checked==8){
				sendNotifytoSingle(user, "保障方案已审核,审核通过", request);
			}else{
				sendNotifytoSingle(user, "保障方案已审核,未通过审核", request);
			}
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
	 * 管理方通知用户
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedEight", method = RequestMethod.POST)
	public void checkedEight(HttpServletRequest request,
						   HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("userName");
		String user6 = request.getParameter("user6");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName4(fileName);
		bean.setFilePath4(path);
		bean.setId(id);
		bean.setChecked(10);
		bean.setTime9(funUtil.nowDate());
		int rst = CommunicationSupportService.checkedEight(bean);
		if (rst == 1) {
			this.message = "确认信息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(确认信息)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "服务提供方已经上传了保障方案，请查看", request);
			sendNotifytoSingle(user6, "已通知用户查看保障方案，请近期将保障准备情况写一个说明文档，并提交上来", request);
			//----END
		} else {
			this.message = "通知用户处理失败";
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

	/**用户确认
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedNine", method = RequestMethod.POST)
	public void checkedNine(HttpServletRequest request,
							 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		/*int checked = funUtil.StringToInt(request.getParameter("checked"));*/
		String note5 = request.getParameter("note5");
		String user = request.getParameter("checkUser");
		String user6 = request.getParameter("user6");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		bean.setChecked(14);
		bean.setTime10(funUtil.nowDate());
		bean.setNote10(note5);
		int rst = CommunicationSupportService.checkedNine(bean);
		if (rst == 1) {
			this.message = "确认成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "用户已经确认保障准备情况说明文档", request);
			sendNotifytoSingle(user6, "用户已经确认保障准备情况说明文档，", request);
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
	 * 服务提供方发送保障准备信息给管理方
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedTen", method = RequestMethod.POST)
	public void checkedTen(HttpServletRequest request,
						   HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("checkUser");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName5(fileName);
		bean.setFilePath5(path);
		bean.setId(id);
		bean.setChecked(11);
		bean.setTime11(funUtil.nowDate());
		int rst = CommunicationSupportService.checkedTen(bean);
		if (rst == 1) {
			this.message = "保障准备信息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(保障准备信息)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "服务提供方上传了保障准备说明文档", request);
			//----END
		} else {
			this.message = "通知经办人处理失败";
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
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedEleven", method = RequestMethod.POST)
	public void checkedEvelen(HttpServletRequest request,
							 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		String note6 = request.getParameter("note6");
		String user6 = request.getParameter("user6");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		if(checked == 12){
			bean.setChecked(12);
		}else if(checked == 10){
			bean.setChecked(10);
		}
		bean.setTime12(funUtil.nowDate());
		bean.setNote12(note6);
		int rst = CommunicationSupportService.checkedEvelen(bean);
		if (rst == 1) {
			this.message = "确认成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			if(checked==12){
				sendNotifytoSingle(user6, "通信保障准备情况说明文件已审核通过", request);
			}else{
				sendNotifytoSingle(user6, "通信保障准备情况说明文件审核未通过！！", request);
			}
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
	 * 管理方发送保障准备信息给用户单位
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedTwelve", method = RequestMethod.POST)
	public void checkedTwelve(HttpServletRequest request,
						   HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("userName");
		String user6 = request.getParameter("user6");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName6(fileName);
		bean.setFilePath6(path);
		bean.setId(id);
		bean.setChecked(14);
		bean.setTime13(funUtil.nowDate());
		int rst = CommunicationSupportService.checkedTwelve(bean);
		if (rst == 1) {
			this.message = "保障准备信息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知用户处理(保障准备信息)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "通信保障准备信息通知，请知悉", request);
			sendNotifytoSingle(user6, "已通知用户，了解保障准备情况，请尽快完成保障任务，并上传保障完成情况相关文档", request);
			//----END
		} else {
			this.message = "通知用户处理失败";
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
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedThirteen", method = RequestMethod.POST)
	public void checkedThirteen(HttpServletRequest request,
							 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String note7 = request.getParameter("note7");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		bean.setChecked(14);
		bean.setTime7(funUtil.nowDate());
		bean.setNote7(note7);
		int rst = CommunicationSupportService.checkedThirteen(bean);
		if (rst == 1) {
			this.message = "确认成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			//sendNotify(bean.getUser_MainManager(), "保障申请信息已经成功提交,请审核。。。", request);
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
	 * 服务提供方发送保障完成信息给管理方
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedFourteen", method = RequestMethod.POST)
	public void checkedFourteen(HttpServletRequest request,
						   HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("checkUser");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName7(fileName);
		bean.setFilePath7(path);
		bean.setId(id);
		bean.setChecked(15);
		bean.setTime15(funUtil.nowDate());
		int rst = CommunicationSupportService.checkedFourteen(bean);
		if (rst == 1) {
			this.message = "保障完成信息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(保障完成信息)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "保障完成信息审核。。。", request);
			//----END
		} else {
			this.message = "通知经办人处理失败";
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
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedFifteen", method = RequestMethod.POST)
	public void checkedFifteen(HttpServletRequest request,
							 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		String note8 = request.getParameter("note8");
		String user = request.getParameter("userName");
		String user6 = request.getParameter("user6");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		if(checked == 16) {
			bean.setChecked(16);
		}else if(checked == 14){
			bean.setChecked(14);
		}
		bean.setTime16(funUtil.nowDate());
		bean.setNote16(note8);
		int rst = CommunicationSupportService.checkedFifteen(bean);
		if (rst == 1) {
			this.message = "确认成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			
			if(checked==16){
				sendNotifytoSingle(user6, "保障完成信息已经审核通过", request);
				sendNotifytoSingle(user, "保障已完成，请给我保障服务质量做评价", request);
			}else{
				sendNotifytoSingle(user6, "保障完成信息已经审核未通过，请重新处理", request);
			}
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
	 * 用户确认
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sureFile", method = RequestMethod.POST)
	public void sureFile(HttpServletRequest request,
						 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String note = request.getParameter("note");
		String user=request.getParameter("checkUser");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		bean.setChecked(17);
		bean.setTime17(funUtil.nowDate());
		bean.setNote17(note);
		int rst = CommunicationSupportService.sureFile(bean);
		if (rst == 1) {
			this.message = "确认成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "用户已评价本次通信保障任务", request);
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
	 * 上传文件
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestParam("filePath") MultipartFile file,
					   HttpServletRequest request,HttpServletResponse response) {
		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
		String data = funUtil.MD5(sdf.format(d));
		
		String path = request.getSession().getServletContext()
				.getRealPath("")+"/Resources/upload/communicationsupport";
		String fileName = file.getOriginalFilename();
		//String fileName = new Date().getTime()+".jpg";
		log.info("path==>"+path);
		log.info("fileName==>"+fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			this.success=true;
			this.message="文件上传成功";
			fileName=data+"-"+fileName;
			File rename = new File(path,fileName);
			targetFile.renameTo(rename);

		} catch (Exception e) {
			e.printStackTrace();
			this.message="文件上传失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", fileName);
		result.put("filePath", "/Resources/upload/communicationsupport/"+fileName);
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
	 * 下载文件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String path = request.getSession().getServletContext().getRealPath("/Resources/upload");
		String fileName=request.getParameter("fileName");
		fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
		String downPath=path+"/"+fileName;
		log.info(downPath);
		 File file = new File(downPath);
		 if(!file.exists()){
			 this.success=false;
			 this.message="文件不存在";
		 }
		    //设置响应头和客户端保存文件名
		    response.setCharacterEncoding("utf-8");
		    response.setContentType("multipart/form-data");
		    response.setHeader("Content-Disposition", "attachment;fileName=" + DownLoadUtils.getName(request.getHeader("user-agent"), fileName));
		    //用于记录以完成的下载的数据量，单位是byte
		    long downloadedLength = 0l;
		    try {
		        //打开本地文件流
		        InputStream inputStream = new FileInputStream(downPath);
		        //激活下载操作
		        OutputStream os = response.getOutputStream();

		        //循环写入输出流
		        byte[] b = new byte[2048];
		        int length;
		        while ((length = inputStream.read(b)) > 0) {
		            os.write(b, 0, length);
		            downloadedLength += b.length;
		        }

		        // 这里主要关闭。
		        os.close();
		        inputStream.close();
		    } catch (Exception e){
		        throw e;
		    }
		    //存储记录
	}


	/**
	 * 发送邮件(指定收件人)--入网申请
	 * 
	 * @param recvUser
	 *            邮件接收者
	 * @param content
	 *            邮件内容
	 * @param request
	 */
	public void sendNotifytoSingle(String recvUser, String content,
			HttpServletRequest request) {
		// ----发送通知邮件
		EmailBean emailBean = new EmailBean();
		emailBean.setTitle("通信保障申请");
		emailBean.setRecvUser(recvUser);
		emailBean.setSendUser(funUtil.loginUser(request));
		emailBean.setContent(content);
		emailBean.setTime(funUtil.nowDate());
		EmailService.insertEmail(emailBean);
		// ----END
	}

	/**
	 * 发送邮件(指定权限)--入网申请
	 * 
	 * @param recvUser
	 *            邮件接收者
	 * @param content
	 *            邮件内容
	 * @param request
	 */
	public void sendNotifytoGroup(String powerstr, int roleId, String content,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("powerstr", powerstr);
		map.put("roleId", roleId);
		List<Map<String, Object>> items = WebUserServices
				.userlistByPowerAndRoleId(map);
		log.info("邮件发送：" + items);
		for (Map<String, Object> item : items) {
			// ----发送通知邮件
			EmailBean emailBean = new EmailBean();
			emailBean.setTitle("通信保障申请");
			emailBean.setRecvUser(item.get("user").toString());
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent(content);
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			// ----END
		}
	}

}
