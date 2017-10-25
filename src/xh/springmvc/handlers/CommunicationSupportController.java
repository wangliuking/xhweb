package xh.springmvc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
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
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("user", user);
		map.put("roleId", roleId);

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
			sendNotify(bean.getUser_MainManager(), "保障申请信息已经成功提交,请审核。。。", request);
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
		String note1 = request.getParameter("note1");
		String user = request.getParameter("user");
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
			sendNotify(user, "保障申请信息审核，请管理部门领导审核并移交经办人，上传。。。", request);
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
		String user = request.getParameter("user");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName1(fileName);
		bean.setFilePath1(path);
		bean.setId(id);
		bean.setChecked(2);
		int rst = CommunicationSupportService.checkedTwo(bean);
		if (rst == 1) {
			this.message = "确认信息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(保障申请)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			//----发送通知邮件
			sendNotify(user, "保障申请信息审核。。。", request);
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
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		bean.setChecked(3);
		bean.setTime2(funUtil.nowDate());
		bean.setNote2(note2);
		int rst = CommunicationSupportService.checkedThree(bean);
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
		String user = request.getParameter("user");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName2(fileName);
		bean.setFilePath2(path);
		bean.setId(id);
		bean.setChecked(4);
		int rst = CommunicationSupportService.checkedFour(bean);
		if (rst == 1) {
			this.message = "确认信息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(保障申请)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotify(user, "保障申请信息审核。。。", request);
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
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		String note3 = request.getParameter("note3");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		if(checked == 5){
			bean.setChecked(5);
		}else if(checked == 3) {
			bean.setChecked(3);
		}
		bean.setTime3(funUtil.nowDate());
		bean.setNote3(note3);
		int rst = CommunicationSupportService.checkedFive(bean);
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
		String user = request.getParameter("user");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName3(fileName);
		bean.setFilePath3(path);
		bean.setId(id);
		bean.setChecked(6);
		int rst = CommunicationSupportService.checkedSix(bean);
		if (rst == 1) {
			this.message = "确认信息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(保障申请)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotify(user, "保障申请信息审核。。。", request);
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
	@RequestMapping(value = "/checkedSeven", method = RequestMethod.POST)
	public void checkedSeven(HttpServletRequest request,
							 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		String note4 = request.getParameter("note4");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		if(checked == 7) {
			bean.setChecked(7);
		}else if(checked == 5){
			bean.setChecked(5);
		}
		bean.setTime4(funUtil.nowDate());
		bean.setNote4(note4);
		int rst = CommunicationSupportService.checkedSeven(bean);
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
	 * 管理方上传
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedEight", method = RequestMethod.POST)
	public void checkedEight(HttpServletRequest request,
						   HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("user");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName4(fileName);
		bean.setFilePath4(path);
		bean.setId(id);
		bean.setChecked(8);
		int rst = CommunicationSupportService.checkedEight(bean);
		if (rst == 1) {
			this.message = "确认信息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(保障申请)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotify(user, "保障申请信息审核。。。", request);
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
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		String note5 = request.getParameter("note5");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		bean.setChecked(9);
		bean.setTime5(funUtil.nowDate());
		bean.setNote5(note5);
		int rst = CommunicationSupportService.checkedNine(bean);
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
	 * 管理方上传
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedTen", method = RequestMethod.POST)
	public void checkedTen(HttpServletRequest request,
						   HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("user");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName5(fileName);
		bean.setFilePath5(path);
		bean.setId(id);
		bean.setChecked(10);
		int rst = CommunicationSupportService.checkedTen(bean);
		if (rst == 1) {
			this.message = "确认信息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(保障申请)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotify(user, "保障申请信息审核。。。", request);
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
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		if(checked == 11){
			bean.setChecked(11);
		}else if(checked == 9){
			bean.setChecked(9);
		}
		bean.setTime6(funUtil.nowDate());
		bean.setNote6(note6);
		int rst = CommunicationSupportService.checkedEvelen(bean);
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
	 * 管理方上传
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedTwelve", method = RequestMethod.POST)
	public void checkedTwelve(HttpServletRequest request,
						   HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("user");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName6(fileName);
		bean.setFilePath6(path);
		bean.setId(id);
		bean.setChecked(12);
		int rst = CommunicationSupportService.checkedTwelve(bean);
		if (rst == 1) {
			this.message = "确认信息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(保障申请)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotify(user, "保障申请信息审核。。。", request);
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
	@RequestMapping(value = "/checkedThirteen", method = RequestMethod.POST)
	public void checkedThirteen(HttpServletRequest request,
							 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		String note7 = request.getParameter("note7");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		if(checked == 13){
			bean.setChecked(13);
		}else if(checked == 11){
			bean.setChecked(11);
		}
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
	 * 管理方上传
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedFourteen", method = RequestMethod.POST)
	public void checkedFourteen(HttpServletRequest request,
						   HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("user");
		CommunicationSupportBean bean = new CommunicationSupportBean();
		String fileName = request.getParameter("fileName");
		String path = request.getParameter("path");
		bean.setFileName7(fileName);
		bean.setFilePath7(path);
		bean.setId(id);
		bean.setChecked(14);
		int rst = CommunicationSupportService.checkedFourteen(bean);
		if (rst == 1) {
			this.message = "确认信息发送成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(保障申请)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotify(user, "保障申请信息审核。。。", request);
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
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		if(checked == 15) {
			bean.setChecked(15);
		}else if(checked == 13){
			bean.setChecked(13);
		}
		bean.setTime8(funUtil.nowDate());
		bean.setNote8(note8);
		int rst = CommunicationSupportService.checkedFifteen(bean);
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
		CommunicationSupportBean bean = new CommunicationSupportBean();
		bean.setId(id);
		//bean.setTime5(funUtil.nowDate());
		bean.setChecked(16);
		bean.setTime(funUtil.nowDate());
		bean.setNote(note);
		int rst = CommunicationSupportService.sureFile(bean);
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
	 * 上传文件
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestParam("filePath") MultipartFile file,
					   HttpServletRequest request,HttpServletResponse response) {
		String path = request.getSession().getServletContext()
				.getRealPath("")+"/Resources/upload";
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

		} catch (Exception e) {
			e.printStackTrace();
			this.message="文件上传失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", fileName);
		result.put("filePath", path+"/"+fileName);
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
	 * 发送邮件
	 * @param recvUser	邮件接收者
	 * @param content	邮件内容
	 * @param request
	 */
	public void sendNotify(String recvUser,String content,HttpServletRequest request){
		//----发送通知邮件
		EmailBean emailBean = new EmailBean();
		emailBean.setTitle("保障申请");
		emailBean.setRecvUser(recvUser);
		emailBean.setSendUser(funUtil.loginUser(request));
		emailBean.setContent(content);
		emailBean.setTime(funUtil.nowDate());
		EmailService.insertEmail(emailBean);
		//----END
	}

}
