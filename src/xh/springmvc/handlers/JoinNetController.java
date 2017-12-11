package xh.springmvc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
import xh.func.plugin.FormToWord;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.JoinNetBean_programingTemplate;
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
		String user = funUtil.loginUser(request);
		WebUserBean userbean = WebUserServices.selectUserByUser(user);
		int roleId = userbean.getRoleId();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("user", user);
		map.put("roleId", roleId);

		HashMap result = new HashMap();
		List<JoinNetBean> selectall_rst = JoinNetService.selectAll(map);
		result.put("success", success);
		result.put("items", selectall_rst);
		result.put("totals", selectall_rst.size());
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

		int rst = 0;
		int shoubanNum = 0;
		int chezaitaiNum = 0;
		int MAXID = 0;
		String jsonData = request.getParameter("formData");
		JoinNetBean bean = GsonUtil.json2Object(jsonData, JoinNetBean.class);
		bean.setUserName(funUtil.loginUser(request));
		bean.setNetTime(funUtil.nowDate());
		if (bean.getApply2() != 0 || bean.getApply3() != 0) {
			bean.setChecked(-1);
			bean.setServiceType("有线接入");
			shoubanNum = bean.getApply0();
			chezaitaiNum = bean.getApply1();
			bean.setApply0(0);
			bean.setApply1(0);
			log.info("data有线==>" + bean.toString());
			rst = JoinNetService.insertNet(bean) + 1;
			MAXID = JoinNetService.YXMAXID();
			bean.setApply2(0);
			bean.setApply3(0);
			bean.setApply0(shoubanNum);
			bean.setApply1(chezaitaiNum);
			log.info("data有线==>rst=" + rst);
			if (rst == 2) {
				// ----发送通知邮件
				sendNotifytoGroup("b_check_joinnet", 10001,
						"有线入网申请信息已经成功提交,请审核。。。", request);
				// ----END
			}
			this.success = true;
		}
		if (bean.getApply0() != 0 && bean.getApply0() != 0) {
			bean.setServiceType("无线接入");
			bean.setChecked(0);
			rst += JoinNetService.insertNet(bean);
			log.info(rst + "data无线==>" + bean.toString());
			if (rst != 0) {
				// ----发送通知邮件
				sendNotifytoGroup("b_check_joinnet", 10001,
						"无线入网申请信息已经成功提交,请审核。。。", request);
				// ----END
			}
			this.success = true;
		}
		if (rst >= 1) {
			this.message = "无线入网申请信息已经成功提交";
			if (rst == 2) {
				this.message = "有线入网申请信息已经成功提交";
			} else if (rst == 3) {
				this.message = "有线/无线入网申请信息已经成功提交";
			}

			System.out.println("-----MAXID:" + MAXID);
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("入网申请信息，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

		} else {
			this.message = "入网申请信息提交失败";
			this.success = false;
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rst);
		result.put("maxID", MAXID);
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
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setChecked(checked);
		bean.setUser1(funUtil.loginUser(request));
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

			// ----发送通知邮件
			sendNotifytoGroup("b_check_joinnet", 10002,
					"入网申请信息审核，请管理部门指定并移交经办人办理。", request);
			// ----END
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

			// ----发送通知邮件
			sendNotifytoSingle(user3, "入网申请信息审核，请上传编组方案。。。", request);
			// ----END
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
		String backUser = request.getParameter("backUser");
		String sendUser = request.getParameter("sendUser");
		String user4 = request.getParameter("user");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("path");
		String recvUser= request.getParameter("recvUser");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setChecked(3);
		bean.setTime3(funUtil.nowDate());
		bean.setNote3(note3);
		bean.setUser4(user4);
		bean.setFileName(fileName);
		bean.setFilePath(filePath);
		
		
		int rst=0;

		if (type == 1) {
			bean.setChecked(4);
			bean.setTime5(funUtil.nowDate());
			bean.setNote4(note3);
			rst = JoinNetService.uploadFile4(bean);
			
			if (rst == 1) {
				this.message = "上传资源配置方案成功";
				webLogBean.setOperator(funUtil.loginUser(request));
				webLogBean.setOperatorIp(funUtil.getIpAddr(request));
				webLogBean.setStyle(5);
				webLogBean.setContent("上传资源配置方案，data=" + bean.toString());
				WebLogService.writeLog(webLogBean);

				// ----发送通知邮件
				sendNotifytoSingle(recvUser, "请审核资源配置方案", request);
				// ----END
			} else {
				this.message = "上传资源配置方案失败";
			}
		}else{
			rst = JoinNetService.uploadFile(bean);
			if (rst == 1) {
				this.message = "上传编组方案成功";
				webLogBean.setOperator(funUtil.loginUser(request));
				webLogBean.setOperatorIp(funUtil.getIpAddr(request));
				webLogBean.setStyle(5);
				webLogBean.setContent("上传编组方案，data=" + bean.toString());
				WebLogService.writeLog(webLogBean);

				// ----发送通知邮件
				if (backUser != null) {
					sendNotifytoSingle(backUser, "入网申请信息审核，编组方案已上传", request);
				} else if (sendUser != null) {
					sendNotifytoSingle(sendUser, "入网申请信息审核，编组方案已上传", request);
				}
				// ----END
			} else {
				this.message = "上传编组方案失败";
			}
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
	 * 
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
		int rst=0;
		if (type == 0) {
			if (checked == 1) {
				bean.setChecked(4);
			} else {
				bean.setChecked(2);
			}
			rst = JoinNetService.checkFile(bean);
		} else {
			if (!loginUser.equals(sendUser)) {
				if (checked == 1) {
					bean.setChecked(5);
				} else {
					bean.setChecked(3);
				}
				bean.setTime6(funUtil.nowDate());
				rst = JoinNetService.checkFile5(bean);
			} else {
				if (checked == 1) {
					bean.setChecked(7);
					sendUser = managerUser;
				} else {
					bean.setChecked(3);
				}
				bean.setTime7(funUtil.nowDate());
				rst = JoinNetService.checkFile7(bean);
			}
		}

		
		if (rst == 1) {
			if(type==0){
				if (checked == 1) {
					sendNotifytoSingle(sendUser, "入网申请信息审核，编组方案已审核，请确认"
							+ bean.getNote4(), request);
				} else {
					sendNotifytoSingle(backUser, bean.getNote4(), request);
				}
				this.message = "审核编组方案成功";
				webLogBean.setOperator(funUtil.loginUser(request));
				webLogBean.setOperatorIp(funUtil.getIpAddr(request));
				webLogBean.setStyle(5);
				webLogBean.setContent("审核编组方案");
				WebLogService.writeLog(webLogBean);
			}else{
				
				if(bean.getChecked()==5){
					sendNotifytoSingle(backUser, "领导已经审核资源配置文件，等待用户确认", request);
					sendNotifytoSingle(sendUser, "领导已经审核资源配置文件，等待用户确认", request);
				}else if(bean.getChecked()==7){
					sendNotifytoSingle(backUser, "用户已经确认资源配置文件", request);
				}else{
					sendNotifytoSingle(backUser, "资源配置文件审核不通过", request);
				}
				
				
				this.message = "确认资源配置文件成功";
			}

			
			// ----发送通知邮件
			// ----END
		} else {
			if(type==0){
				this.message = "审核编组方案失败";
			}else{
				this.message = "确认资源配置文件失败";
			}
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
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sureFile", method = RequestMethod.POST)
	public void sureFile(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String sendUser = request.getParameter("sendUser");
		String note5 = request.getParameter("note5");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setTime5(funUtil.nowDate());
		bean.setNote5(note5);
		bean.setChecked(6);

		int rst = JoinNetService.sureFile(bean);
		if (rst == 1) {
			this.message = "请于三个工作日内将入网样机送至软件中心测试";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认编组方案，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			// ----发送通知邮件
			sendNotifytoSingle(sendUser, "用户将于三个工作日内将入网样机送至软件中心测试，请注意查收。",
					request);
			// ----END
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
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sureHT", method = RequestMethod.POST)
	public void sureHT(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checked = funUtil.StringToInt(request
				.getParameter("userHT_checked"));
		String sendUser = request.getParameter("sendUser");
		String note6 = request.getParameter("note6");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setTime6(funUtil.nowDate());
		bean.setNote6(note6);
		if (checked == 1) {
			bean.setChecked(7);
		} else {
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

			// ----发送通知邮件
			sendNotifytoSingle(sendUser, "审核样机入网送检申请（合同附件）成功,请用户上传采购设备信息。",
					request);
			// ----END
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
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/registNet", method = RequestMethod.POST)
	public void registNet(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		String jsonData = request.getParameter("formData");
		JoinNet_registerFormBean bean1 = GsonUtil.json2Object(jsonData,
				JoinNet_registerFormBean.class);
		bean1.setDocNum("201708081001");
		FormToWord toWord = new FormToWord();
		String fileName = toWord.fillWord(bean1, request, "register.ftl");
		String filePath = request.getSession().getServletContext()
				.getRealPath("")
				+ "/Resources/outputDoc/";
		JoinNetBean bean2 = new JoinNetBean();
		bean2.setId(bean1.getId());
		bean2.setChecked(13);
		bean2.setFileNameDoc(fileName);
		bean2.setFilePathDoc(filePath);
		System.out.println("ID :" + bean1.getId());
		System.out.println("Name :" + fileName);
		System.out.println("Path :" + filePath);
		int rst = 0;
		if (fileName != "false") {
			this.message = "入网登记表填写成功";
			// 将生成的模板路径保存到数据库中
			rst = JoinNetService.uploadFileDoc(bean2);
		} else {
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
	 * 
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestParam("filePath") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {

		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
		String data = funUtil.MD5(sdf.format(d));

		
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/Resources/upload/net/";
		String fileName = file.getOriginalFilename();
		
		/*File de=new File(fileName);
		file.renameTo(new   File(name+".jpg"));   //改名  
*/		
		
		// String fileName = new Date().getTime()+".jpg";
		log.info("path==>" + path);
		log.info("fileName==>" + fileName);
		System.out.println("fileName==>" + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		
		}
		// 保存
		try {
			file.transferTo(targetFile);
			this.success = true;
			this.message = "文件上传成功";
			fileName=data+"-"+fileName;
			File rename = new File(path,fileName);
			targetFile.renameTo(rename);
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
			this.message = "文件上传失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", fileName);
		result.put("filePath", path + "/" + fileName);
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
	 * 
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/upload1", method = RequestMethod.POST)
	public void uploadfiles(@RequestParam("files") MultipartFile[] files,
			HttpServletRequest request, HttpServletResponse response) {
		int id = funUtil.StringToInt(request.getParameter("joinNetId"));
		int isSuccess = 1;
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/Resources/upload/net/template/";
		String fileName = files[0].getOriginalFilename();
		log.info("path==>" + path);
		log.info("fileName==>" + fileName);
		log.info("文件--id---"+id);
		List<Integer> list = JoinNetService.getUserCIDByID(id);
		
		
		log.info("编程底板：=>"+Arrays.toString(list.toArray()));
		
		
		
		
		
		if (funUtil.isInteger(fileName.split("\\.")[0])) {
			
			log.info("编程底板123：=>"+fileName.split("\\.")[0]);
			
			
			
			if (list.contains(Integer.parseInt(fileName.split("\\.")[0]))) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id_JoinNet", request.getParameter("joinNetId"));
				map.put("fileName", fileName);
				map.put("filePath", path);
				map.put("insertTime", funUtil.nowDate());
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 保存
				try {
					files[0].transferTo(targetFile);
					int rst = JoinNetService.insertProgramingTemplate(map);
					log.info("文件map--->"+map);
					this.success = true;
					this.message = "文件上传成功";
				} catch (Exception e) {
					e.printStackTrace();
					this.message = "文件上传失败";
				}
			}
		} else {
			this.success = true;
			isSuccess = 0;
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("isSuccess", isSuccess);
		result.put("message", message);
		result.put("fileName", fileName);
		result.put("filePath", path + "/" + fileName);
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
	 * 
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
		// int type = funUtil.StringToInt(request.getParameter("type"));
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setFileNameGH(fileName);
		bean.setFilePathGH(filePath);
		bean.setChecked(0);
		System.out.println("保存文件:" + fileName);

		int rst = JoinNetService.uploadFileGh(bean);
		if (rst == 1) {
			this.message = "上传成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("上传公函，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "上传文件失败";
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
	 * 
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/uploadNote", method = RequestMethod.POST)
	public void uploadNote(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String user2 = request.getParameter("loginUser");
		String note2_suggest = request.getParameter("note2_suggest");
		String managerId = request.getParameter("managerId");
		int type = funUtil.StringToInt(request.getParameter("type"));
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("path");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setFileNameNote(fileName);
		bean.setFilePathNote(filePath);
		int rst=0;
		if (type == 1) {
			bean.setChecked(2);
			bean.setUser2(user2);
			bean.setTime2(funUtil.nowDate());
			bean.setNote2_suggest(note2_suggest);
			rst = JoinNetService.uploadFile100(bean);
			// ----发送通知邮件
			sendNotifytoSingle(managerId, "评估意见已上报,请审核。。。", request);
			// ----END
		} else {
			bean.setChecked(1);
			rst = JoinNetService.uploadFileNote(bean);
		}

		System.out.println("保存通知函:" + fileName);

		
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
	 * 
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
		bean.setChecked(11);
		System.out.println("保存合同:" + fileName + filePath);

		int rst = JoinNetService.uploadFileHT(bean);
		if (rst == 1) {
			this.message = "上传合同成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("上传合同，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			if (type == 1) {
				sendNotifytoSingle(sendUser, "协议已上传。", request);
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
	 * 
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
		String sendUser = request.getParameter("sendUser");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setFileNameCG(fileName);
		bean.setFilePathCG(filePath);
		bean.setTime7(funUtil.nowDate());
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

			sendNotifytoSingle(sendUser, "上传采购设备信息成功。", request);
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
	 * 
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/updateCheckById", method = RequestMethod.POST)
	public void deliveryTerminal(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		int checkNum = funUtil.StringToInt(request.getParameter("checkNum"));
		String sendUser = request.getParameter("sendUser");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setChecked(checkNum);
		System.out.println("------根据ID:" + id + "，将checked更新为：" + checkNum);

		int rst = JoinNetService.updateCheckById(bean);
		if (rst == 1) {
			if (checkNum == 9) {
				this.message = "用户添加成功";
			} else if (checkNum == 10) {
				this.message = "终端交付成功";
				bean.setTime8(funUtil.nowDate());
				JoinNetService.updateCheck10(bean);
				sendNotifytoSingle(sendUser, "终端交付成功,请用户确认", request);
			} else if (checkNum == 11) {
				this.message = "终端接受确认成功";
				bean.setTime9(funUtil.nowDate());
				JoinNetService.updateCheck11(bean);
				sendNotifytoSingle(sendUser, "终端接受确认成功，请经办人办理培训", request);
			} else if (checkNum == 12) {
				this.message = "用户使用培训完成";
				bean.setTime10(funUtil.nowDate());
				JoinNetService.updateCheck12(bean);
				sendNotifytoSingle(sendUser, "用户使用培训完成，请用户确认。", request);
			} else if (checkNum == 13) {
				this.message = "培训确认完成";
				bean.setTime11(funUtil.nowDate());
				JoinNetService.updateCheck13(bean);
				sendNotifytoSingle(sendUser, "培训确认完成，请经办人上传附件报备", request);
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
		bean.setTime3(funUtil.nowDate());
		bean.setNote2(note2);

		int rst = JoinNetService.YXcheckedOne(bean);
		if (rst == 1) {
			this.message = "处理成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("主管部门领导审核评估技术方案");
			WebLogService.writeLog(webLogBean);
			if (checked == 3) {
				sendNotifytoSingle(operator, "有线申请评估技术方案审核通过，请上传资源配置技术方案。。。", request);
			} else {
				sendNotifytoSingle(proposer, bean.getNote2(), request);
			}
			// ----发送通知邮件
			// sendNotify(reciver, "入网申请信息审核，请上传编组方案。。。", request);
			// ----END
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
		int loginUserRoleId = funUtil.StringToInt(request
				.getParameter("loginUserRoleId"));
		int checkId = funUtil.StringToInt(request.getParameter("checkId"));
		int verifyId = funUtil.StringToInt(request.getParameter("checked"));
		
		String user3=request.getParameter("user3");
		String userName=request.getParameter("userName");
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
		int rst=0;
		if (loginUserRoleId == 10002 && checkId == 7) {
			bean.setChecked(8);
			bean.setTime8(funUtil.nowDate());
			rst = JoinNetService.applicationAccess8(bean);
		} else if (loginUserRoleId == 10003 && checkId == 8) {
			bean.setUser4(funUtil.loginUser(request));
			bean.setNote7(note6A);
			bean.setChecked(9);
			bean.setTime9(funUtil.nowDate());
			rst = JoinNetService.applicationAccess9(bean);
		} else if (loginUserRoleId == 10002 && checkId == 9) {
			bean.setResult(resultNoteA);
			bean.setNote6(note6A);
			if (verifyId == 1) {
				bean.setChecked(10);
			} else if (verifyId == 0) {
				bean.setChecked(8);
				bean.setNote8(note6B);
			}
			bean.setTime10(funUtil.nowDate());
			rst = JoinNetService.applicationAccess10(bean);
		}

		
		if (rst == 1) {
			this.message = "处理成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("处理(入网申请)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

			if (loginUserRoleId == 10002 && checkId == 7) {
				sendNotifytoGroup("b_check_joinnet", 10003,"请根据相关手续安排用户应用接入",request);
			}
			if (loginUserRoleId == 10003 && checkId == 8) {
				sendNotifytoSingle(user3, "应用已接入完成，请审核", request);
			}
			if (loginUserRoleId == 10002 && checkId == 9) {
				sendNotifytoSingle(userName, "有线入网接入申请已经通过，应用接入已经完成，请知悉", request);
				sendNotifytoSingle(userName, "有线入网接入申请已经通过，应用接入已经完成，请知悉", request);
			}
			// ----发送通知邮件
			// sendNotify(user3, "入网申请信息审核，请上传编组方案。。。", request);
			// ----END
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
	@RequestMapping(value = "/training", method = RequestMethod.POST)
	public void training(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String managerUser = request.getParameter("managerUser");
		String pxTime = request.getParameter("pxTime");
		String pxAddress = request.getParameter("pxAddress");
		int pxNumOfPeople = funUtil.StringToInt(request
				.getParameter("pxNumOfPeople"));
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setPxAddress(pxAddress);
		bean.setPxNumOfPeople(pxNumOfPeople);
		bean.setPxTime(pxTime);

		int rst = JoinNetService.training(bean);
		if (rst == 1) {
			this.message = "处理成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("处理(入网申请)，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			// ----发送通知邮件
			sendNotifytoSingle(
					managerUser,
					"培训通知\n培训时间:" + bean.getPxTime() + "\n培训地点:"
							+ bean.getPxAddress() + "\n培训人数:"
							+ bean.getPxNumOfPeople(), request);
			// ----END
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
	 * 领导通知经办人上传资源配置方案
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checked100", method = RequestMethod.POST)
	public void checked100(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("id"));
		String note3 = request.getParameter("note3");
		String user3 = request.getParameter("user3");
		JoinNetBean bean = new JoinNetBean();
		bean.setId(id);
		bean.setChecked(100);
		bean.setUser3(user3);
		bean.setTime4(funUtil.nowDate());
		bean.setNote3(note3);

		int rst = JoinNetService.checked100(bean);
		if (rst == 1) {
			this.message = "通知经办人处理成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("通知经办人处理(领导通知经办人上传资源配置方案)");
			WebLogService.writeLog(webLogBean);

			// ----发送通知邮件
			sendNotifytoSingle(user3, "请上传资源配置方案", request);
			// ----END
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
	 * 下载文件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int type = funUtil.StringToInt(request.getParameter("type"));
		String path = "";
		if (type == 3) {
			path = request.getSession().getServletContext()
					.getRealPath("/Resources/outputDoc");
		} else {
			path = request.getSession().getServletContext()
					.getRealPath("/Resources/upload");
		}
		String fileName = request.getParameter("fileName");
		fileName = new String(fileName.getBytes("ISO-8859-1"), "utf-8");
		String downPath = path + "/" + fileName;
		log.info(downPath);
		System.out.println(downPath);
		File file = new File(downPath);
		if (!file.exists()) {
			this.success = false;
			this.message = "文件不存在";
		}
		// 设置响应头和客户端保存文件名
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ fileName);
		// 用于记录以完成的下载的数据量，单位是byte
		long downloadedLength = 0l;
		try {
			// 打开本地文件流
			InputStream inputStream = new FileInputStream(downPath);
			// 激活下载操作
			OutputStream os = response.getOutputStream();

			// 循环写入输出流
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
				downloadedLength += b.length;
			}

			// 这里主要关闭。
			os.close();
			inputStream.close();
		} catch (Exception e) {
			throw e;
		}
		// 存储记录
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
		emailBean.setTitle("入网申请");
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
			emailBean.setTitle("入网申请");
			emailBean.setRecvUser(item.get("user").toString());
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent(content);
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			// ----END
		}
	}

}
