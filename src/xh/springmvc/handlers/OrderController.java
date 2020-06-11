package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spire.ms.System.Collections.ArrayList;
import com.tcpBean.ErrCheckAck;
import com.tcpBean.ErrProTable;
import com.tcpServer.ServerDemo;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.wordreader.DataRegion;
import com.zhuozhengsoft.pageoffice.wordreader.WordDocument;

import xh.constant.ConstantLog;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.func.plugin.MapRemoveNullUtil;
import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.bean.FaultThreeBean;
import xh.mybatis.bean.OrderDataBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.FaultLevelService;
import xh.mybatis.service.OrderService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.org.listeners.SingLoginListener;


@Controller
@RequestMapping("/order")
public class OrderController {
	private static final long serialVersionUID = -758686623642845302L;
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(OrderController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	
	
	//派单列表
	@RequestMapping(value="/orderlist", method = RequestMethod.GET)
	public void orderlist(HttpServletRequest request, HttpServletResponse response) {
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		String bs=request.getParameter("bs");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		String copy_user=request.getParameter("copy_user");
		String dispatchman=request.getParameter("dispatchman");
		String recv_user=request.getParameter("recv_user");
		String type=request.getParameter("type");
		String status=request.getParameter("status");
		
		String user = funUtil.loginUser(request);
		WebUserBean userbean = WebUserServices.selectUserByUser(user);
		int roleId = userbean.getRoleId();
	
		
		Map<String,Object> map=new HashMap<String, Object>();
		/*map.put("loginuser", funUtil.loginUser(request));*/
		map.put("start", start);
		map.put("limit", limit);
		map.put("user",user);
		map.put("roleId", roleId);
		map.put("bs", bs);
		map.put("copy_user", copy_user);
		map.put("dispatchman", dispatchman);
		map.put("recv_user", recv_user);
		map.put("errtype", type);
		map.put("status", status);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		
		
		map.put("type", 10);
		HashMap result = new HashMap();
		result.put("items",OrderService.orderList(map));
		result.put("totals", OrderService.orderListCount(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value="/del", method = RequestMethod.GET)
	public void del(
			@RequestParam("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		String[] ids=id.split(",");
		List<Integer> list=new ArrayList();
		for (String string : ids) {
			list.add(Integer.parseInt(string));
		}
		int rs = OrderService.del(list);
		if(rs>0){
			this.success=true;
			this.message="删除成功";
			FunUtil.WriteLog(request, ConstantLog.DELETE, "删除派单"+id);
		}else{
			this.success=false;
			this.message="删除失败";
		}
		Map<String,Object> result= new HashMap<String, Object>();
		result.put("success",this.success);
		result.put("message", this.message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//用户列表
	@RequestMapping(value="/userlist", method = RequestMethod.GET)
	public void userlist(HttpServletRequest request, HttpServletResponse response) {
		HashMap result = new HashMap();
		result.put("items",OrderService.userList());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//郊县维护组用户列表
		@RequestMapping(value="/jxuserlist", method = RequestMethod.GET)
		public void jxuserlist(HttpServletRequest request, HttpServletResponse response) {
			HashMap result = new HashMap();
			result.put("items",OrderService.jxuserList());
			response.setContentType("application/json;charset=utf-8");
			String jsonstr = json.Encode(result);
			try {
				response.getWriter().write(jsonstr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	@RequestMapping(value="/orderhtml", method = RequestMethod.GET)
	public ModelAndView orderhtml(HttpServletRequest request) {
		String bsId=request.getParameter("bsId");
		String name=request.getParameter("name");
		String errInfo=request.getParameter("errInfo");
		String errfoundtime=request.getParameter("errfoundtime");
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("bsId", bsId);
		map.put("name", name);
		map.put("errInfo", errInfo);
		map.put("errfoundtime", errfoundtime);
		System.out.println(map);
		ModelAndView mv = new ModelAndView("jsp/order");
		mv.addObject("nowDate",FunUtil.nowDate());
		mv.addObject("userlist", OrderService.userList());
		mv.addAllObjects(map);
		return mv;
	}
	//派单处理未完成数量
	@RequestMapping(value="/orderNoComCount", method = RequestMethod.GET)
	public void orderNoComCount(HttpServletRequest request, HttpServletResponse response) {		
		Map<String,Object> map=new HashMap<String, Object>();
		String user = funUtil.loginUser(request);
		WebUserBean userbean = WebUserServices.selectUserByUser(user);
		int roleId = userbean.getRoleId();
		map.put("type", 3);
		map.put("user",user);
		map.put("roleId", roleId);
		HashMap result = new HashMap();
		result.put("totals", OrderService.orderListCount(map));
		
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value="/updateOrder", method = RequestMethod.POST)
	public void updateOrder(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		int status = Integer.parseInt(request.getParameter("status"));
		int id=FunUtil.StringToInt(request.getParameter("id"));
		int alarmId=FunUtil.StringToInt(request.getParameter("alarmId"));
		int level=FunUtil.StringToInt(request.getParameter("level"));
		String dispatchtime=request.getParameter("dispatchtime");
		String recvTime=request.getParameter("recvTime");
		String from=request.getParameter("from");
		String bsid=request.getParameter("bsId");
		String zbdldm=request.getParameter("zbdldm");
		String serialnumber=request.getParameter("serialnumber");
		String userid=request.getParameter("userid");
		String recvUser=request.getParameter("recvUser");
		String copyUser=request.getParameter("copyUser");
		
		
		
		this.success=true;
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("status", status);
		map.put("time", FunUtil.nowDate());
		map.put("auditor", SingLoginListener.getLogUserMap().get(request.getSession().getId())+"");
		map.put("id", id);
		System.out.println(map);
		
		int code=OrderService.updateOrder(map);
		
		ServerDemo demo=new ServerDemo();
		ErrCheckAck errCheckAck = new ErrCheckAck();
		errCheckAck.setSerialnumber(serialnumber);
		errCheckAck.setUserid(userid);
		errCheckAck.setAuditor(SingLoginListener.getLogUserMap().get(request.getSession().getId())+"");
		errCheckAck.setResult(status==-1?String.valueOf(status):"0");

		demo.startMessageThread(userid, errCheckAck);

		if(code>0){
			if(recvUser!=null && recvUser!=""){
				String[] recvuser=recvUser.split(",");
				for(String user:recvuser){
					FunUtil.sendMsgToOneUser(user, "故障派单", "派单信息已经更新", request);
				}
			
			}
			if(copyUser!=null && copyUser!=""){
				String[] copyuser=copyUser.split(",");
				for(String user:copyuser){
					FunUtil.sendMsgToOneUser(user, "故障派单", "派单信息已经更新", request);
				}
			}
			/*if(status==3){
				FaultThreeBean faultBean=new FaultThreeBean();
				faultBean.setFault_id(alarmId);
				faultBean.setSend_order_time(dispatchtime);
				faultBean.setReceipt_order_time(recvTime);
				faultBean.setRecv_order_time(map.get("time").toString());
			    SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    //接单耗时
			    long r=0;
			    Date r1=sf.parse(dispatchtime);
			    Date r2=sf.parse(faultBean.getReceipt_order_time());
			    r=(r2.getTime()-r1.getTime())/60000;
			    faultBean.setRecv_order_use_time(r);
			    //接单超时
			    long j=0;
			    faultBean.setRecv_order_cs(0);
			    //处理耗时
			    long cs=0;
			    Date t1=sf.parse(faultBean.getReceipt_order_time());
			    Date t2=sf.parse(faultBean.getRecv_order_time());
			    cs=(t2.getTime()-t1.getTime())/60000;
			    faultBean.setHandle_order_user_time(cs);
			    //处理超时
			    //接单耗时超过规定时间（一般故障发生后，一级基站要求110分钟内恢复，
			    //二级基站要求170分钟内恢复，三级基站要求290分钟内恢复）则为超时
			    long cs_total=0;
			    if(level==1){
			    	if(cs>110){
			    		cs_total=cs-110;
			    	}
			    }else if(level==2){
			    	if(cs>170){
			    		cs_total=cs-170;
			    	}
			    }else if(level==3){
			    	if(cs>290){
			    		cs_total=cs-290;
			    	}
			    }
			    faultBean.setHandle_order_cs(cs_total>0?cs_total:0);
			    
			FaultLevelService.three_update_by_order(faultBean);
			}*/
			
			/*if(from.equals("数据分析")){
				ErrProTable bean=new ErrProTable();
				bean.setBsid(bsid);
				bean.setFrom(from);
				bean.setZbdldm(zbdldm);
				bean.setStatus("已处理");
				OrderService.updateSfOrder(bean);
			}
			*/
			this.success=true;
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("确认派单已解决");
			webLogBean.setCreateTime(funUtil.nowDate());
			WebLogService.writeLog(webLogBean);
		}else{
			this.success=false;
		}

		HashMap result = new HashMap();
		result.put("success",success);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value="/writeOrder", method = RequestMethod.POST)
	public void writeOrder(HttpServletRequest request, HttpServletResponse response) {
		String fromData=request.getParameter("formData");
		Map<String,Object> mm=GsonUtil.json2Object(fromData, Map.class);
		MapRemoveNullUtil.removeNullValue(mm);
		
		
		ErrProTable bean=new ErrProTable();
		try {
			BeanUtils.populate(bean, mm);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bean.setSerialnumber(FunUtil.RandomWord(8));
		bean.setOrderAccount(funUtil.loginUser(request));
		bean.setDispatchtime(FunUtil.nowDate());
		bean.setDispatchman(FunUtil.loginUserInfo(request).get("userName").toString());
		bean.setStatus("0");
		log.info("ErrProTab->"+bean.toString());
		
		int id=bean.getId();
		
		
		this.success=true;
		
		int code=OrderService.addOrder(bean);
		
		
		if(code>0){
			this.success=true;
			FaultThreeBean faultBean=new FaultThreeBean();
			faultBean.setFault_id(id);
			faultBean.setSend_order_time(bean.getDispatchtime());
			FaultLevelService.three_update_by_order(faultBean);
			
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("派单");
			webLogBean.setCreateTime(funUtil.nowDate());
			WebLogService.writeLog(webLogBean);
			
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("status", 1);
			map.put("orderId",bean.getSerialnumber());
			map.put("id", id);
			log.info("id->"+id);
			
			if(id>0){
				OrderService.updateBsFault(map);	
				
			}
			String recv_user=bean.getRecv_user();
			String copy_user=bean.getCopy_user();
			String recv_user_name=bean.getRecv_user_name();
			String copy_user_name=bean.getCopy_user_name();
			ServerDemo demo=new ServerDemo();
			//发送接单人
			String[] a1=recv_user.split(",");
			String[] a2=recv_user_name.split(";");
			if(recv_user!=null && !recv_user.equals("")){
				for(int i=0;i<a1.length;i++){
					bean.setUserid(a1[i]);
					/*bean.setWorkman(a2[i]);*/
					bean.setHandlepower("0");
					demo.startMessageThread(bean.getUserid(), bean);
					FunUtil.sendMsgToOneUser(bean.getUserid(), "故障派单", "你有新的故障派单信息", request);
					log.info("派单：接收》userId="+bean.getUserid()+";bean="+bean);
				}
			}
			
			//发送抄送人
			if(copy_user!=null && !copy_user.equals("")){
				String[] b1=copy_user.split(",");
				String[] b2=copy_user_name.split(";");
				for(int i=0;i<b1.length;i++){
					bean.setUserid(b1[i]);
					//bean.setWorkman(b2[i]);
					bean.setHandlepower("2");
					demo.startMessageThread(bean.getUserid(), bean);
					FunUtil.sendMsgToOneUser(bean.getUserid(), "故障派单", "你有新的故障派单信息", request);
					log.info("派单：抄送》userId="+bean.getUserid()+";bean="+bean);
				}
			}
			
			
		}else{
			this.success=false;
		}

		HashMap result = new HashMap();
		result.put("success",success);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value="/rewriteOrder", method = RequestMethod.POST)
	public void rewriteOrder(HttpServletRequest request, HttpServletResponse response) {
		String fromData=request.getParameter("formData");
		ErrProTable bean=GsonUtil.json2Object(fromData, ErrProTable.class);
		//bean.setSerialnumber(FunUtil.RandomWord(8));
		
		log.info("ErrProTab->"+bean.toString());
		ServerDemo demo=new ServerDemo();
		if(demo.getmThreadList().size()>0){
			demo.startMessageThread(bean.getUserid(), bean);
			this.message="发送成功";
			this.success=true;
		}else{
			this.message="没有找到用户";
			this.success=false;
		}
		
		
		this.success=true;

		HashMap result = new HashMap();
		result.put("success",success);
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
	@RequestMapping(value="/updateOrderData", method = RequestMethod.POST)
	public void updateOrderData(HttpServletRequest request, HttpServletResponse response) {
		String fromData=request.getParameter("formData");
		Map<String,Object> mm=GsonUtil.json2Object(fromData, Map.class);
		MapRemoveNullUtil.removeNullValue(mm);
		
		ErrProTable bean=new ErrProTable();
		try {
			BeanUtils.populate(bean, mm);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*bean.setSerialnumber(FunUtil.RandomWord(8));
		bean.setOrderAccount(funUtil.loginUser(request));
		bean.setDispatchtime(FunUtil.nowDate());
		bean.setDispatchman(FunUtil.loginUserInfo(request).get("userName").toString());*/
		log.info("ErrProTab->"+bean.toString());
		
		int id=bean.getId();
		
		
		this.success=true;
		
		int code=OrderService.updateOrderData(bean);
		
		
		if(code>0){
			this.success=true;
			FaultThreeBean faultBean=new FaultThreeBean();
			faultBean.setFault_id(id);
			faultBean.setSend_order_time(bean.getDispatchtime());
			FaultLevelService.three_update_by_order(faultBean);
			
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("派单");
			webLogBean.setCreateTime(funUtil.nowDate());
			WebLogService.writeLog(webLogBean);
			
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("status", 1);
			map.put("orderId",bean.getSerialnumber());
			map.put("id", id);
			log.info("id->"+id);
			
			if(id>0){
				OrderService.updateBsFault(map);	
				
			}
			String recv_user=bean.getRecv_user();
			String copy_user=bean.getCopy_user();
			String recv_user_name=bean.getRecv_user_name();
			String copy_user_name=bean.getCopy_user_name();
			ServerDemo demo=new ServerDemo();
			//发送接单人
			String[] a1=recv_user.split(",");
			String[] a2=recv_user_name.split(";");
			if(recv_user!=null && !recv_user.equals("")){
				for(int i=0;i<a1.length;i++){
					bean.setUserid(a1[i]);
					//bean.setWorkman(a2[i]);
					bean.setHandlepower("0");
					demo.startMessageThread(bean.getUserid(), bean);
					
					log.info("派单：接收》userId="+bean.getUserid()+";bean="+bean);
				}
			}
			
			//发送抄送人
			if(copy_user!=null && !copy_user.equals("")){
				String[] b1=copy_user.split(",");
				String[] b2=copy_user_name.split(";");
				for(int i=0;i<b1.length;i++){
					bean.setUserid(b1[i]);
					//bean.setWorkman(b2[i]);
					bean.setHandlepower("2");
					demo.startMessageThread(bean.getUserid(), bean);
					log.info("派单：抄送》userId="+bean.getUserid()+";bean="+bean);
				}
			}
			
			
		}else{
			this.success=false;
		}

		HashMap result = new HashMap();
		result.put("success",success);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value="/excel", method = RequestMethod.GET)
	public void excel(HttpServletRequest request, HttpServletResponse response) {
		String bs=request.getParameter("bs");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
	
		
		Map<String,Object> map=new HashMap<String, Object>();
		/*map.put("loginuser", funUtil.loginUser(request));*/
		map.put("bs", bs);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("type", 10);
		
		List<OrderDataBean> list=OrderService.orderList(map);
		try {
			String saveDir = request.getSession().getServletContext().getRealPath("/upload");
			String pathname = saveDir + "/派单记录表["+starttime+"-"+endtime+"].xls";
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);			
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 15, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat.setBorder(Border.ALL, BorderLineStyle.THIN,
					Colour.DARK_GREEN);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 13,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.LIGHT_GREEN);// 背景色
			fontFormat_h.setWrap(true);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					12, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体
			WritableCellFormat fontFormat_Content = new WritableCellFormat(font_Content);
			// 设置其他样式
			fontFormat_Content.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行
		
			
			//总计内容字体格式
			WritableFont total_font = new WritableFont(WritableFont.COURIER,
					13, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.RED);
			// 应用字体
			WritableCellFormat total_fontFormat = new WritableCellFormat(total_font);
			total_fontFormat.setAlignment(Alignment.CENTRE);// 水平对齐
			total_fontFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			total_fontFormat.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			total_fontFormat.setBackground(Colour.WHITE);// 背景色
			total_fontFormat.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); // 设置表单格式

			WritableSheet sheet = book.createSheet("总派单记录", 0);
		
			sheet.addCell(new Label(0, 0, "建设周期", fontFormat_h));
			sheet.addCell(new Label(1, 0, "基站编号", fontFormat_h));
			sheet.addCell(new Label(2, 0, "基站名称", fontFormat_h));
			sheet.addCell(new Label(3, 0, "派单时间", fontFormat_h));
			sheet.addCell(new Label(4, 0, "派单人", fontFormat_h));
			sheet.addCell(new Label(5, 0, "故障类型", fontFormat_h));
			sheet.addCell(new Label(6, 0, "故障等级", fontFormat_h));
			sheet.addCell(new Label(7, 0, "故障发现时间", fontFormat_h));
			sheet.addCell(new Label(8, 0, "恢复时间", fontFormat_h));
			sheet.addCell(new Label(9, 0, "处理进展", fontFormat_h));
			sheet.addCell(new Label(10, 0, "处理结果", fontFormat_h));
			sheet.addCell(new Label(11, 0, "接单人", fontFormat_h));
			sheet.addCell(new Label(12, 0, "审核人", fontFormat_h));
			sheet.setRowView(0, 300);
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 7);
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 30);
			sheet.setColumnView(4, 10);
			sheet.setColumnView(5, 10);
			sheet.setColumnView(6, 10);
			sheet.setColumnView(7, 30);
			sheet.setColumnView(8, 30);
			sheet.setColumnView(9, 50);
			sheet.setColumnView(10, 20);
			sheet.setColumnView(11, 20);
			sheet.setColumnView(12, 20);

			
			for (int i = 0; i < list.size(); i++) {
				OrderDataBean bean=list.get(i);
				Map<String,Object> m=FunUtil.BeanToMap(bean);
				
				sheet.addCell(new Label(0, i + 1, m.get("period")==null?"":m.get("period").toString(), fontFormat_Content));
				sheet.addCell(new Label(1, i + 1, m.get("bsid")==null?"":m.get("bsid").toString(), fontFormat_Content));
				sheet.addCell(new Label(2, i + 1, m.get("bsname")==null?"":m.get("bsname").toString(), fontFormat_Content));
				sheet.addCell(new Label(3, i + 1, m.get("dispatchtime")==null?"":m.get("dispatchtime").toString(), fontFormat_Content));
				sheet.addCell(new Label(4, i + 1, m.get("dispatchman")==null?"":m.get("dispatchman").toString(), fontFormat_Content));
				sheet.addCell(new Label(5, i + 1, m.get("errtype")==null?"":m.get("errtype").toString(), fontFormat_Content));
				sheet.addCell(new Label(6, i + 1, m.get("errlevel")==null?"":m.get("errlevel").toString(), fontFormat_Content));
				sheet.addCell(new Label(7, i + 1, m.get("errfoundtime")==null?"":m.get("errfoundtime").toString(), fontFormat_Content));
				sheet.addCell(new Label(8, i + 1, m.get("errslovetime")==null?"":m.get("errslovetime").toString(), fontFormat_Content));
				sheet.addCell(new Label(9, i + 1, m.get("progress")==null?"":m.get("progress").toString(), fontFormat_Content));
				sheet.addCell(new Label(10, i + 1, m.get("proresult")==null?"":m.get("proresult").toString(), fontFormat_Content));
				sheet.addCell(new Label(11, i + 1, m.get("workman")==null?"":m.get("workman").toString(), fontFormat_Content));
				sheet.addCell(new Label(12, i + 1, m.get("auditor")==null?"":m.get("auditor").toString(), fontFormat_Content));
				
			}
			
			book.write();
			book.close();
			/*DownExcelFile(response, pathname);*/
			 this.success=true;
			 HashMap<String, Object> result = new HashMap<String, Object>();
			 result.put("success", success);
			 result.put("pathName", pathname);
			 response.setContentType("application/json;charset=utf-8"); 
			 String jsonstr = json.Encode(result); 
			 response.getWriter().write(jsonstr);
			
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
