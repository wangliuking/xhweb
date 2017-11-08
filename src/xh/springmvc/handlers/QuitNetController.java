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
			
			//----发送通知邮件
			sendNotifytoGroup("b_check_quitnet", "退网申请信息已经成功提交,请审核。。。", request);
			//----END
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
		String note1 = request.getParameter("note1");
		String user = request.getParameter("user");
		QuitNetBean bean = new QuitNetBean();
		bean.setId(id);
		bean.setUser1(funUtil.loginUser(request));
		bean.setNote1(note1);
		bean.setQuit(1);
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
			sendNotifytoSingle(user, "退网申请信息审核，请审核。。。", request);
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
		String note2 = request.getParameter("note2");
		String user = request.getParameter("user");
		QuitNetBean bean = new QuitNetBean();
		bean.setUser2(funUtil.loginUser(request));
		bean.setId(id);
		bean.setNote2(note2);
		bean.setQuit(2);
		bean.setCheckUser(user);
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
			sendNotifytoSingle(user, "退网申请信息审核，请管理方领导指派人员处理。。。", request);
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

	@RequestMapping(value = "/checkedThree", method = RequestMethod.POST)
	public void checkedThree(HttpServletRequest request,
						   HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("user");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("path");
		QuitNetBean bean = new QuitNetBean();
		bean.setUser3(funUtil.loginUser(request));
		bean.setId(id);
		bean.setQuit(3);
		bean.setFileName1(fileName);
		bean.setFilePath1(filePath);
		bean.setTime3(funUtil.nowDate());
		log.info("data==>" + bean.toString());
		WebLogBean webLogBean = new WebLogBean();
		int rst = QuitNetService.checkedThree(bean);
		if (rst == 1){
			this.message = "处理方案提交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核处理方案，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "处理方案审核，请服务提供方处理。。。", request);
			//----END
		} else {
			this.message = "处理方案上传失败";
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

	@RequestMapping(value = "/checkedFour", method = RequestMethod.POST)
	public void checkedFour(HttpServletRequest request,
							 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String note4 = request.getParameter("note4");
		String user = request.getParameter("user");
		QuitNetBean bean = new QuitNetBean();
		bean.setUser4(funUtil.loginUser(request));
		bean.setId(id);
		bean.setQuit(4);
		bean.setNote4(note4);
		bean.setTime4(funUtil.nowDate());
		log.info("data==>" + bean.toString());
		WebLogBean webLogBean = new WebLogBean();
		int rst = QuitNetService.checkedFour(bean);
		if (rst == 1){
			this.message = "处理方案审核通过";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核处理方案，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "处理方案审核通过，请服务提供方上传实施报告。。。", request);
			//----END
		} else {
			this.message = "处理方案审核失败";
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

	@RequestMapping(value = "/checkedFive", method = RequestMethod.POST)
	public void checkedFive(HttpServletRequest request,
							 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("user");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("path");
		QuitNetBean bean = new QuitNetBean();
		bean.setUser5(funUtil.loginUser(request));
		bean.setId(id);
		bean.setQuit(5);
		bean.setCheckUser(user);
		bean.setFileName2(fileName);
		bean.setFilePath2(filePath);
		bean.setTime5(funUtil.nowDate());
		log.info("data==>" + bean.toString());
		WebLogBean webLogBean = new WebLogBean();
		int rst = QuitNetService.checkedFive(bean);
		if (rst == 1){
			this.message = "实施方案上传成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核实施方案，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "实施方案上传成功，请管理方人员审核。。。", request);
			//----END
		} else {
			this.message = "实施方案上传失败";
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

	@RequestMapping(value = "/checkedSix", method = RequestMethod.POST)
	public void checkedSix(HttpServletRequest request,
							HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String note6 = request.getParameter("note6");
		String user = request.getParameter("user");
		QuitNetBean bean = new QuitNetBean();
		bean.setUser6(funUtil.loginUser(request));
		bean.setId(id);
		bean.setQuit(6);
		bean.setNote6(note6);
		bean.setTime6(funUtil.nowDate());
		log.info("data==>" + bean.toString());
		WebLogBean webLogBean = new WebLogBean();
		int rst = QuitNetService.checkedSix(bean);
		if (rst == 1){
			this.message = "实施方案审核成功,流程结束";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核实施方案，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
//			sendNotifytoSingle(user, "实施方案审核成功，流程结束。。。", request);
			//----END
		} else {
			this.message = "实施方案审核失败";
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

	@RequestMapping(value = "/checkedSeven", method = RequestMethod.POST)
	public void checkedSeven(HttpServletRequest request,
							 HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("user");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("path");
		QuitNetBean bean = new QuitNetBean();
		bean.setUser7(funUtil.loginUser(request));
		bean.setId(id);
		bean.setQuit(3);
		bean.setCheckUser(user);
		bean.setFileName3(fileName);
		bean.setFilePath3(filePath);
		bean.setTime7(funUtil.nowDate());
		log.info("data==>" + bean.toString());
		WebLogBean webLogBean = new WebLogBean();
		int rst = QuitNetService.checkedSeven(bean);
		if (rst == 1){
			this.message = "处理方案提交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核处理方案，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "处理方案审核，请服务提供方审核。。。", request);
			//----END
		} else {
			this.message = "处理方案提交失败";
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

	@RequestMapping(value = "/checkedEight", method = RequestMethod.POST)
	public void checkedEight(HttpServletRequest request,
							HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String note8 = request.getParameter("note8");
		String user = request.getParameter("user");
		QuitNetBean bean = new QuitNetBean();
		bean.setUser8(funUtil.loginUser(request));
		bean.setId(id);
		bean.setQuit(4);
		bean.setNote8(note8);
		bean.setTime8(funUtil.nowDate());
		log.info("data==>" + bean.toString());
		WebLogBean webLogBean = new WebLogBean();
		int rst = QuitNetService.checkedEight(bean);
		if (rst == 1){
			this.message = "审核提交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核处理方案，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			//----发送通知邮件
			sendNotifytoSingle(user, "处理方案审核通过，请服务提供方上传实施报告。。。", request);
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
		log.info("fileName==>"+fileName);;
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
		int type = funUtil.StringToInt(request.getParameter("type"));
		String path = "";
		if(type == 3){
			path = request.getSession().getServletContext().getRealPath("/Resources/outputDoc");
		}
		else{
			path = request.getSession().getServletContext().getRealPath("/Resources/upload");
		}
		String fileName=request.getParameter("fileName");
		fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
		String downPath=path+"/"+fileName;
		log.info(downPath);
		System.out.println(downPath);
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
	 * 发送邮件(指定收件人)--保障申请
	 * @param recvUser	邮件接收者
	 * @param content	邮件内容
	 * @param request
	 */
	public void sendNotifytoSingle(String recvUser,String content,HttpServletRequest request){
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
	/**
	 * 发送邮件(指定权限)--保障申请
	 * @param
	 * @param content	邮件内容
	 * @param request
	 */
	public void sendNotifytoGroup(String powerstr,String content,HttpServletRequest request){
		List<Map<String,Object>> list = WebUserServices.userlistByPower(powerstr);
		for(Map<String,Object> map : list){
			//----发送通知邮件
			EmailBean emailBean = new EmailBean();
			emailBean.setTitle("退网申请");
			emailBean.setRecvUser(map.get("user").toString());
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent(content);
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			//----END
		}
	}

}
