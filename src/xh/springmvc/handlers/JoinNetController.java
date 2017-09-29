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
import xh.func.plugin.FormToWord;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.JoinNet_registerFormBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.JoinNetService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;

@Controller
@RequestMapping(value = "/net")
public class JoinNetController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(JoinNetController.class);
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
		result.put("items", JoinNetService.selectAll(map));
		result.put("totals", JoinNetService.dataCount(map));
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
		result.put("items", JoinNetService.applyProgress(id));
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
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertNet", method = RequestMethod.POST)
	public void insertNet(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		String jsonData = request.getParameter("formData");
		
		JoinNetBean bean = GsonUtil.json2Object(jsonData, JoinNetBean.class);
		bean.setUserName(funUtil.loginUser(request));
		bean.setTime(funUtil.nowDate());
		if(bean.getServiceType()=="有线接入"){
			bean.setChecked(-1);
		}
		else if(bean.getServiceType()=="无线接入"){
			bean.setChecked(0);
		}
		log.info("data==>" + bean.toString());

		int rst = JoinNetService.insertNet(bean); 
		if (rst == 1) {
			this.message = "入网申请信息已经成功提交";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("入网申请信息，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			//----发送通知邮件
			sendNotify(bean.getUser_MainManager(), "入网申请信息已经成功提交,请审核。。。", request);
			//----END
		} else {
			this.message = "入网申请信息提交失败";
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
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		String note1 = request.getParameter("note1");
		String user = request.getParameter("user");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setChecked(checked);
		bean.setUser1(funUtil.loginUser(request));
		bean.setUser2(user);
		bean.setTime1(funUtil.nowDate());
		bean.setNote1(note1);
		log.info("data==>" + bean.toString());
		int rst = JoinNetService.checkedOne(bean);
		if (rst == 1) {
			this.message = "审核提交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核入网申请，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			//----发送通知邮件
			sendNotify(user, "入网申请信息审核，请管理部门领导审核并移交经办人，上传编组方案。。。", request);
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
	 * 管理方审核
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkedTwo", method = RequestMethod.POST)
	public void checkedTwo(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String note2 = request.getParameter("note2");
		String user3 = request.getParameter("user");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setChecked(2);
		bean.setUser2(funUtil.loginUser(request));
		bean.setUser3(user3);
		bean.setTime2(funUtil.nowDate());
		bean.setNote2(note2);

		int rst = JoinNetService.checkedTwo(bean);
		if (rst == 1) {
			this.message = "通知经办人处理成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(入网申请)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			//----发送通知邮件
			sendNotify(user3, "入网申请信息审核，请上传编组方案。。。", request);
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

	// 上传编组方案
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int type = funUtil.StringToInt(request.getParameter("type"));
		String note3 = request.getParameter("note3");
		String user4 = request.getParameter("user");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("path");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setChecked(3);
		bean.setTime3(funUtil.nowDate());
		bean.setNote3(note3);
		bean.setUser4(user4);
		bean.setFileName(fileName);
		bean.setFilePath(filePath);
		
		if(type == 1){
			bean.setChecked(4);
		}

		int rst = JoinNetService.uploadFile(bean);
		if (rst == 1) {
			this.message = "上传编组方案成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("上传编组方案，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			//----发送通知邮件
			sendNotify(user4, "入网申请信息审核，编组方案已上传，内审资源配置技术方案。。。", request);
			//----END
		} else {
			this.message = "上传编组方案失败";
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
	 * 管理方审核编组方案
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkFile", method = RequestMethod.POST)
	public void checkFile(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		int type = funUtil.StringToInt(request.getParameter("type"));
		String loginUser = funUtil.loginUser(request);
		
		String note4 = request.getParameter("note4");
		String backUser = request.getParameter("backUser");
		String sendUser = request.getParameter("sendUser");
		String managerUser = request.getParameter("managerUser");
		
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setTime4(funUtil.nowDate());
		bean.setNote4(note4);
		bean.setNote5(note4);
		if(type == 0){
			if(checked==1){
				bean.setChecked(4);
			}else{
				bean.setChecked(2);
			}
		}else{
			if(!loginUser.equals(sendUser)){
				if(checked==1){
					bean.setChecked(5);
				}else{
					bean.setChecked(3);
					}
			}else{
				if(checked==1){
					bean.setChecked(6);
					sendUser = managerUser;
				}else{
					bean.setChecked(3);
				}
			}
		}

		int rst = JoinNetService.checkFile(bean);
		if (rst == 1) {
			this.message = "审核编组方案成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核编组方案，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			if(checked==1){
				sendNotify(sendUser, "入网申请信息审核，编组方案已审核，确认资源配置技术方案。" + bean.getNote4(), request);
			}else{
				sendNotify(backUser, bean.getNote4(), request);
			}
			//----发送通知邮件
			//----END
		} else {
			this.message = "审核编组方案失败";
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
	 * 用户确认编组编组方案
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sureFile", method = RequestMethod.POST)
	public void sureFile(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checked = funUtil.StringToInt(request.getParameter("userBZ_checked"));
		String note5 = request.getParameter("note5");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);	
		bean.setTime5(funUtil.nowDate());
		bean.setNote5(note5);
		if(checked == 1){
			bean.setChecked(5);
		} else{
			bean.setChecked(-2);
		}
		int rst = JoinNetService.sureFile(bean);
		if (rst == 1) {
			this.message = "确认编组方案成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认编组方案，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			//----发送通知邮件
			//sendNotify(bean.getUser_MainManager(), "入网申请信息已经成功提交,请审核。。。", request);
			//----END
		} else {
			this.message = "确认编组方案失败";
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
	 * 审核样机入网送检申请（合同附件）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sureHT", method = RequestMethod.POST)
	public void sureHT(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checked = funUtil.StringToInt(request.getParameter("userHT_checked"));
		String note6 = request.getParameter("note6");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);	
		bean.setTime6(funUtil.nowDate());
		bean.setNote6(note6);
		if(checked == 1){
			bean.setChecked(7);
		} else{
			bean.setChecked(-3);
		}
		int rst = JoinNetService.sureHT(bean);
		if (rst == 1) {
			this.message = "审核样机入网送检申请（合同附件）成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认编组方案，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			//----发送通知邮件
			//sendNotify(bean.getUser_MainManager(), "入网申请信息已经成功提交,请审核。。。", request);
			//----END
		} else {
			this.message = "确认编组方案失败";
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
	 * 填写入网登记表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/registNet", method = RequestMethod.POST)
	public void registNet(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		String jsonData = request.getParameter("formData");
		JoinNet_registerFormBean bean1 = GsonUtil.json2Object(jsonData, JoinNet_registerFormBean.class);
		bean1.setDocNum("201708081001");
		FormToWord toWord = new FormToWord();
		String fileName = toWord.fillWord(bean1, request, "register.ftl");
		String filePath = request.getSession().getServletContext()
				.getRealPath("")+"/Resources/outputDoc/";
		JoinNetBean bean2 = new JoinNetBean();
		bean2.setId(bean1.getId());
		bean2.setChecked(13);
		bean2.setFileNameDoc(fileName);
		bean2.setFilePathDoc(filePath);
		System.out.println("ID :" + bean1.getId());
		System.out.println("Name :" + fileName);
		System.out.println("Path :" + filePath);
		int rst = 0;
		if(fileName != "false"){
			this.message = "入网登记表填写成功";
			//将生成的模板路径保存到数据库中
			rst = JoinNetService.uploadFileDoc(bean2);
		}else{
			this.message = "入网登记表填写失败";
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
		System.out.println("fileName==>"+fileName);
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
	 * 上传公函
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/uploadGH", method = RequestMethod.POST)
	public void uploadGH(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("path");
		int type = funUtil.StringToInt(request.getParameter("type"));
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setFileNameGH(fileName);
		bean.setFilePathGH(filePath);
		bean.setChecked(0);
		System.out.println("保存公函:" + fileName);
		
		int rst = JoinNetService.uploadFileGh(bean);
		if (rst == 1) {
			this.message = "上传成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("上传公函，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "上传公函失败";
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
	 * 上传通知函
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/uploadNote", method = RequestMethod.POST)
	public void uploadNote(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String managerId = request.getParameter("managerId");
		int type = funUtil.StringToInt(request.getParameter("type"));
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("path");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setFileNameNote(fileName);
		bean.setFilePathNote(filePath);
		if(type == 1){
			bean.setChecked(2);
			//----发送通知邮件
			sendNotify(managerId, "评估意见已上报,请审核。。。", request);
			//----END
		}else{
			bean.setChecked(1);
		}
		
		System.out.println("保存通知函:" + fileName);
		
		int rst = JoinNetService.uploadFileNote(bean);
		if (rst == 1) {
			this.message = "上传成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("上传文件，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "上传失败";
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
	 * 上传合同
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/uploadHT", method = RequestMethod.POST)
	public void uploadHT(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int type = funUtil.StringToInt(request.getParameter("type"));
		String sendUser = request.getParameter("sendUser");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("path");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setFileNameHT(fileName);
		bean.setFilePathHT(filePath);
		if(type == 1){
			bean.setChecked(7);
		}else{
			bean.setChecked(6);
		}
		System.out.println("保存合同:" + fileName + filePath);
		
		int rst = JoinNetService.uploadFileHT(bean);
		if (rst == 1) {
			this.message = "上传合同成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("上传合同，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			if(type == 1){
				sendNotify(sendUser, "协议已上传。", request);
			}
		} else {
			this.message = "上传合同失败";
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
	 * 上传采购设备信息
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/uploadCG", method = RequestMethod.POST)
	public void uploadCG(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("path");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setFileNameCG(fileName);
		bean.setFilePathCG(filePath);
		bean.setChecked(8);
		System.out.println("保存采购设备信息:" + fileName + filePath);
		
		int rst = JoinNetService.uploadFileCG(bean);
		if (rst == 1) {
			this.message = "上传采购设备信息成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("上传采购设备信息，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "上传采购设备信息失败";
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
	 * 更新流程
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/updateCheckById", method = RequestMethod.POST)
	public void deliveryTerminal(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checkNum = funUtil.StringToInt(request.getParameter("checkNum"));
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setChecked(checkNum);
		System.out.println("------根据ID:" + id + "，将checked更新为：" + checkNum);
		
		int rst = JoinNetService.updateCheckById(bean);
		if (rst == 1) {
			if(checkNum == 9){
				this.message = "终端交付成功";
			}
			else if(checkNum == 10){
				this.message = "终端接受确认成功";
			}
			else if(checkNum == 11){
				this.message = "用户使用培训完成";
			}
			else if(checkNum == 12){
				this.message = "培训确认完成";
			}
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent(this.message + "，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "信息更新失败";
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
	 * 管理方审核
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/YXcheckOne", method = RequestMethod.POST)
	public void YXcheckOne(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		String note2 = request.getParameter("note2");
		String operator = request.getParameter("operator");
		String proposer = request.getParameter("proposer");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setChecked(checked);
		bean.setTime2(funUtil.nowDate());
		bean.setNote2(note2);
		

		int rst = JoinNetService.YXcheckedOne(bean);
		if (rst == 1) {
			this.message = "处理成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(入网申请)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			if(checked == 3){
				sendNotify(operator, "入网申请信息审核，请上传资源配置技术方案。。。", request);
			}else{
				sendNotify(proposer, bean.getNote2(), request);
			}
			//----发送通知邮件
			//sendNotify(reciver, "入网申请信息审核，请上传编组方案。。。", request);
			//----END
		} else {
			this.message = "处理失败";
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
	 * 安排应用接入
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/applicationAccess", method = RequestMethod.POST)
	public void applicationAccess(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int loginUserRoleId = funUtil.StringToInt(request.getParameter("loginUserRoleId"));
		int checkId = funUtil.StringToInt(request.getParameter("checkId"));
		int verifyId = funUtil.StringToInt(request.getParameter("checked"));
		String sendUser = request.getParameter("sendUser");
		String managerUser = request.getParameter("managerUser");
		String note6 = request.getParameter("note6");
		String note6A = request.getParameter("note6A");
		String note6B = request.getParameter("note6B");
		String resultNote = request.getParameter("result");
		String resultNoteA = request.getParameter("resultA");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setNote6(note6);
		bean.setResult(resultNote);
		if(loginUserRoleId == 10002 && checkId == 7){
			bean.setChecked(8);
		}else if(loginUserRoleId == 10003 && checkId == 8){
			bean.setNote6(note6A);
			bean.setChecked(9);
		}else if(loginUserRoleId == 10002 && checkId == 9){
			bean.setResult(resultNoteA);
			bean.setNote6(note6A);
			if(verifyId == 1){
				bean.setChecked(10);
			}else if(verifyId == 0){
				bean.setChecked(8);
				bean.setNote6(note6B);
			}
		}

		int rst = JoinNetService.applicationAccess(bean);
		if (rst == 1) {
			this.message = "处理成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("处理(入网申请)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			
			if(loginUserRoleId == 10003){
				sendNotify(sendUser, "应用已接入，请审核", request);
			}
			if(loginUserRoleId == 10002 && checkId == 9){
				sendNotify(managerUser, "有线入网接入完成", request);
			}
			//----发送通知邮件
			//sendNotify(user3, "入网申请信息审核，请上传编组方案。。。", request);
			//----END
		} else {
			this.message = "处理失败";
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
	 * 发送邮件--入网申请
	 * @param recvUser	邮件接收者
	 * @param content	邮件内容
	 * @param request
	 */
	public void sendNotify(String recvUser,String content,HttpServletRequest request){
		//----发送通知邮件
		EmailBean emailBean = new EmailBean();
		emailBean.setTitle("入网申请");
		emailBean.setRecvUser(recvUser);
		emailBean.setSendUser(funUtil.loginUser(request));
		emailBean.setContent(content);
		emailBean.setTime(funUtil.nowDate());
		EmailService.insertEmail(emailBean);
		//----END
	}

}
