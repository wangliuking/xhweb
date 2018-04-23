package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.LendBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.BusinessService;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.JoinNetService;
import xh.mybatis.service.LendService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping(value = "/business")
public class LendController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(LendController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	/**
	 * 申请租借列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/lend/list", method = RequestMethod.GET)
	public void lendList(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));
		String user = funUtil.loginUser(request);
		WebUserBean userbean=WebUserServices.selectUserByUser(user);
		int roleId=userbean.getRoleId();
		Map<String,Object> power = SingLoginListener.getLoginUserPowerMap().get(request.getSession().getId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("user", user.trim());
	    map.put("power", power.get("b_check_lend"));
	    map.put("roleId", roleId);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", LendService.lendlistCount(map));
		result.put("items", LendService.lendlist(map));
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
	 * 租借设备清单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/lend/lendInfoList", method = RequestMethod.GET)
	public void lendInfoList(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int lendId = funUtil.StringToInt(request.getParameter("lendId"));
		String status = request.getParameter("status");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lendId", lendId);
		map.put("status", status);

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", LendService.lendInfoList(map).size());
		result.put("items", LendService.lendInfoList(map));
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
	 * 租借
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/lend/add", method = RequestMethod.POST)
	public void lend(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		String jsonData = request.getParameter("formData");
		LendBean bean = GsonUtil.json2Object(jsonData, LendBean.class);
		bean.setChecked(0);
		bean.setUser(funUtil.loginUser(request));
		bean.setTime(funUtil.nowDate());
		int rst = LendService.lend(bean);
		if (rst == 1) {
			this.message = "申请完成，请选择租赁设备";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("租借设备，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			// ----发送通知邮件
			sendNotifytoGroup("b_check_lend",10002, "用户申请租借设备，请审核", request);
			// ----END
		} else {
			this.message = "申请失败";
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
	 * 将租借信息发送给用户
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/lend/checkedSend", method = RequestMethod.POST)
	public void lendSend(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("lendId"));
		String loginUser = request.getParameter("loginUser");
		String leaderUser = request.getParameter("leaderUser");
		LendBean bean = new LendBean();
		bean.setId(id);
		bean.setUser2(loginUser);
		bean.setChecked(2);
		bean.setTime2(funUtil.nowDate());
		
	

		int rst = LendService.checkedSend(bean);
		if (rst == 1) {
			this.message = "提交至领导审核租借清单成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("提交至领导审核租借清单，data=" + id);
			WebLogService.writeLog(webLogBean);
			// ----发送通知邮件
			sendNotifytoSingle(leaderUser, "设备租借清单，请领导审核", request);
			// ----END
		} else {
			this.message = "提交至用户审核租借清单失败";
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
	@RequestMapping(value = "/lend/checkedOne", method = RequestMethod.POST)
	public void checkedOne(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		String jsonData = request.getParameter("formData");
		LendBean bean = GsonUtil.json2Object(jsonData, LendBean.class);
		
		if(bean.getChecked()==-1){
			bean.setUser2("");
		}
		bean.setUser1(funUtil.loginUser(request));
		bean.setTime1(funUtil.nowDate());
		int rst = LendService.checkedOne(bean);
		if (rst == 1) {
			this.message = "审核成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("租借设备审核成功，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
			// ----发送通知邮件
			sendNotifytoSingle(bean.getUser2(), "用户提交了租借设备申请，请根据相应要求准备设备清单", request);
			// ----END
		} else {
			this.message = "审核失败";
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
	 * 添加设备清单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/lend/addOrder", method = RequestMethod.POST)
	public void addOrder(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		String jsonData = request.getParameter("toData");
		int id = funUtil.StringToInt(request.getParameter("id"));
		List<Map<String, Object>> list = GsonUtil.json2Object(jsonData, ArrayList.class);
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			//Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			//map.put("lendId", id);
			//map.put("serialNumber", list.get(i).get("serialNumber").toString());

			map2.put("lendId", id);
			//map2.put("id", list.get(i).get("id").toString());
			map2.put("type", list.get(i).get("type").toString());
			map2.put("name", list.get(i).get("name").toString());
			map2.put("model", list.get(i).get("model").toString());
			map2.put("serialNumber", list.get(i).get("serialNumber").toString());
			// if (LendService.isExtisSerialNumberInfo(map) == 0) {
			list2.add(map2);
			// }
		}

		int rst = -1;
		if (list2.size() > 0) {
			rst = LendService.addOrder(list2);
		}
		if (rst == list2.size()) {
			this.message = "设备添加成功";
			LendService.updateAssetStatus1(list2);
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("添加租借设备清单，lendId=" + id);
			WebLogService.writeLog(webLogBean);

		} else if (rst == 0) {
			this.message = "添加失败";
		} else {
			this.message = "没有需要添加的数据";
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
	 * 删除清单中的设备
	 * 
	 * @param request
	 * @param response
	 */
	
	 @RequestMapping(value="/lend/deleteLendOrderE",method = RequestMethod.POST)
	 public void deleteLendOrderE(HttpServletRequest request, HttpServletResponse response){ 
		 this.success = true; 
		 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		 String lendId = request.getParameter("lendId"); 
		 String serialNumber = request.getParameter("serialNumber"); 
		 Map<String,Object> map=new HashMap<String, Object>(); 
		 map.put("lendId", lendId);
		 map.put("status", 4);
		 map.put("serialNumber", serialNumber); 
		 System.out.println(lendId + "+ " + serialNumber);
		 int rst=LendService.deleteLendOrderE(map);
		 int rst2=LendService.updateAssetStatusBySerialNumber(map);
		 if(rst==1 && rst==1){ 
			 this.message = "删除成功";
			 webLogBean.setOperator(funUtil.loginUser(request));
			 webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			 webLogBean.setStyle(2); webLogBean.setContent("删除清单中的记录，data=" +lendId);
			 WebLogService.writeLog(webLogBean); 
		 }else{ 
			 this.message = "删除失败"; 
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
		 } 
		 catch (IOException e) { 
			 // TODOAuto-generated catch block 
			 e.printStackTrace(); 
		 } 
	 }

	/**
	 * 提交至领导审核租借清单
	 * 
	 * @param request
	 * @param response
	 */
	/*@RequestMapping(value = "/lend/checkedTwo", method = RequestMethod.POST)
	public void checkedTwo(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("lendId"));
		String manager = request.getParameter("manager");
		LendBean bean = new LendBean();
		bean.setId(id);
		bean.setChecked(2);
		bean.setTime2(funUtil.nowDate());

		int rst = LendService.checkedTwo(bean);
		if (rst == 1) {
			this.message = "提交至领导审核租借清单成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("提交至领导审核租借清单，data=" + id);
			WebLogService.writeLog(webLogBean);
			// ----发送通知邮件
			sendNotifytoSingle(manager, "设备租借清单，请领导审核", request);
			// ----END
		} else {
			this.message = "提交至领导审核租借清单失败";
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
*/
	/**
	 * 领导审核租借清单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/lend/checkedOrder", method = RequestMethod.POST)
	public void checkedOrder(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("lendId"));
		int checked = funUtil.StringToInt(request.getParameter("checked"));
		String note2 = request.getParameter("note2");
		String user = request.getParameter("user");
		String user2 = request.getParameter("user2");
		LendBean bean = new LendBean();
		bean.setId(id);
		bean.setChecked(checked);
		bean.setTime3(funUtil.nowDate());
		bean.setNote2(note2);

		Map<String, Object> mapforLend = new HashMap<String, Object>();
		//mapforLend.put("returnTime", funUtil.nowDate());
		mapforLend.put("lendId", id);
		mapforLend.put("status", 2);
		int rst =0;
		
		
		
		 if(checked==-1){
				bean.setChecked(-10);
				 rst = LendService.checkedOrder(bean);
					if (rst >= 1) {
						this.message = "审核租借清单完成";
						webLogBean.setOperator(funUtil.loginUser(request));
						webLogBean.setOperatorIp(funUtil.getIpAddr(request));
						webLogBean.setStyle(5);
						webLogBean.setContent("拒绝租借清单，data=" + id);
						WebLogService.writeLog(webLogBean);
						// ----发送通知邮件
						sendNotifytoSingle(user2, "租借清单被拒绝，请重新整理清单", request);
						// ----END
					} else {
						this.message = "审核租借清单失败";
					}
		 }
		 else{
			 rst += LendService.updateStatusByLendID(mapforLend);
				if (rst >= 1) {
					this.message = "审核租借清单完成";
					webLogBean.setOperator(funUtil.loginUser(request));
					webLogBean.setOperatorIp(funUtil.getIpAddr(request));
					webLogBean.setStyle(5);
					webLogBean.setContent("审核租借清单，data=" + id);
					WebLogService.writeLog(webLogBean);
					// ----发送通知邮件
					sendNotifytoSingle(user, "租借清单完成，请确认", request);
					sendNotifytoSingle(user2, "租借清单审核通过！等用户确认", request);
					// ----END
				} else {
					this.message = "审核租借清单失败";
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
	 * 用户确认租借清单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/lend/sureOrder", method = RequestMethod.POST)
	public void sureOrder(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("lendId"));
		String user1=request.getParameter("user1");
		String user2=request.getParameter("user2");
		int checked = 4;
		LendBean bean = new LendBean();
		bean.setId(id);
		bean.setChecked(checked);
		bean.setUser1(user1);
		bean.setUser2(user2);
		bean.setTime4(funUtil.nowDate());

		int rst = LendService.sureOrder(bean);
		if (rst == 1) {
			this.message = "确认租借清单完成";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("确认租借清单，data=" + id);
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "确认租借清单失败";
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
	 * 用户归还设备
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/lend/operation", method = RequestMethod.POST)
	public void operation(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int id = funUtil.StringToInt(request.getParameter("lendId"));
		int status = funUtil.StringToInt(request.getParameter("status"));
		String checkId = request.getParameter("checkId");
		List<String> checkIds = Arrays.asList(checkId.split(","));
		Map<String, Object> mapforLend = new HashMap<String, Object>();
		//mapforLend.put("returnTime", funUtil.nowDate());
		mapforLend.put("lendId", id);
		mapforLend.put("status", status);
		mapforLend.put("checkIds", checkIds);
		// -------------------将归还设备在资产信息表中修改状态----------------
		if(status == 0){
			 Map<String, Object> mapforBusiness=new HashMap<String, Object>();
			 mapforBusiness.put("status", 4);
			 mapforBusiness.put("checkIds", checkIds);
			 int rst2 = LendService.updateAssetStatusBySerialNumberList(mapforBusiness);
			 Map<String, Object> map2 = new HashMap<String, Object>();
			 map2.put("lendId", id);
			 int rst3 = LendService.checkReturnOrderCount(map2);
			 if(rst3 == 0){
				 LendBean bean = new LendBean();
				 bean.setId(id);
				 bean.setChecked(5);
				 bean.setTime5(funUtil.nowDate());
				 LendService.returnFinish(bean);
			 }
		}
		// -------------------------------------------------------------
		int rst = LendService.operation(mapforLend);
		// if (rst == 1 && rst2 == 1) {
		if (rst == checkIds.size()) {
			if (status == 1) {
				this.message = "验收设备完成";
			} else if(status == 3) {
				this.message = "设备归还待审核";
			} else if(status == 0){
				this.message = "设备已归还";
				// ----发送通知邮件
				//sendNotifytoSingle(manager, "归还设备，请审核", request);
				// ----END
			}
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("归还设备，data=" + id + ",serialNumber=" + checkId);
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "操作未成功，请联系管理员";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", true);
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
		emailBean.setTitle("租借设备");
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
			emailBean.setTitle("租借设备");
			emailBean.setRecvUser(item.get("user").toString());
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent(content);
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			// ----END
		}
	}

}
