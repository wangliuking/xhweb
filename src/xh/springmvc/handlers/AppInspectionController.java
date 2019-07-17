package xh.springmvc.handlers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.print.attribute.standard.PageRanges;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.InspectionBean;
import xh.mybatis.bean.InspectionDispatchBean;
import xh.mybatis.bean.InspectionMbsBean;
import xh.mybatis.bean.InspectionMscBean;
import xh.mybatis.bean.InspectionNetBean;
import xh.mybatis.bean.InspectionRoomBean;
import xh.mybatis.bean.InspectionSbsBean;
import xh.mybatis.bean.InspectionStarBean;
import xh.mybatis.bean.InspectionVerticalBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.AppInspectionServer;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.BsstationService;

@Controller
@RequestMapping("/app")
public class AppInspectionController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(AppInspectionController.class);
	private WebLogBean webLogBean = new WebLogBean();
	private FlexJSON json = new FlexJSON();
	private AppInspectionExcelController app=new AppInspectionExcelController();
	
	@RequestMapping(value = "/repeater_list", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> repeater_list(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("items", AppInspectionServer.repeater_list());
		return result;
	}
	@RequestMapping(value = "/room_list", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> room_list(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("items", AppInspectionServer.room_list());
		return result;
	}
	@RequestMapping(value = "/portable_list", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> portable_list(HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("items", AppInspectionServer.portable_list());
		return result;
	}
	

	/**
	 * 删除巡检基站记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/del_sbs", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> del_sbs(HttpServletRequest request,
			HttpServletResponse response) {
		int id = FunUtil.StringToInt(request.getParameter("id"));
		int rs = AppInspectionServer.del_sbs(id);
		if (rs > 0) {
			this.success = true;
			this.message = "删除基站巡检记录成功";
		} else {
			this.success = false;
			this.message = "删除失败";
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		return result;

	}

	@RequestMapping(value = "/del_net", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> del_net(HttpServletRequest request,
			HttpServletResponse response) {
		String[] id = request.getParameter("id").split(",");
		List<String> list = new ArrayList<String>();

		for (String str : id) {
			list.add(str);
		}
		int rs = AppInspectionServer.del_net(list);
		if (rs > 0) {
			this.success = true;
			this.message = "删除网管巡检记录成功";
		} else {
			this.success = false;
			this.message = "删除失败";
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		return result;

	}

	@RequestMapping(value = "/del_msc", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> del_msc(HttpServletRequest request,
			HttpServletResponse response) {
		String[] id = request.getParameter("id").split(",");
		List<String> list = new ArrayList<String>();

		for (String str : id) {
			list.add(str);
		}
		int rs = AppInspectionServer.del_msc(list);
		if (rs > 0) {
			this.success = true;
			this.message = "删除交换中心巡检记录成功";
		} else {
			this.success = false;
			this.message = "删除失败";
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		return result;

	}

	@RequestMapping(value = "/del_dispatch", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> del_dispatch(HttpServletRequest request,
			HttpServletResponse response) {
		String[] id = request.getParameter("id").split(",");
		List<String> list = new ArrayList<String>();

		for (String str : id) {
			list.add(str);
		}
		int rs = AppInspectionServer.del_dispatch(list);
		if (rs > 0) {
			this.success = true;
			this.message = "删除调度台巡检记录成功";
		} else {
			this.success = false;
			this.message = "删除失败";
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		return result;

	}

	@RequestMapping(value = "/del_vertical", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> del_vertical(HttpServletRequest request,
			HttpServletResponse response) {
		String[] id = request.getParameter("id").split(",");
		List<String> list = new ArrayList<String>();

		for (String str : id) {
			list.add(str);
		}
		int rs = AppInspectionServer.del_vertical(list);
		if (rs > 0) {
			this.success = true;
			this.message = "删除直放站巡检记录成功";
		} else {
			this.success = false;
			this.message = "删除失败";
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		return result;

	}
	@RequestMapping(value = "/del_room", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> del_room(HttpServletRequest request,
			HttpServletResponse response) {
		String[] id = request.getParameter("id").split(",");
		List<String> list = new ArrayList<String>();

		for (String str : id) {
			list.add(str);
		}
		int rs = AppInspectionServer.del_room(list);
		if (rs > 0) {
			this.success = true;
			this.message = "删除室内覆盖站巡检记录成功";
		} else {
			this.success = false;
			this.message = "删除失败";
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		return result;

	}
	@RequestMapping(value = "/del_star", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> del_star(HttpServletRequest request,
			HttpServletResponse response) {
		String[] id = request.getParameter("id").split(",");
		List<String> list = new ArrayList<String>();

		for (String str : id) {
			list.add(str);
		}
		int rs = AppInspectionServer.del_star(list);
		if (rs > 0) {
			this.success = true;
			this.message = "删除卫星通信车载便携站巡检记录成功";
		} else {
			this.success = false;
			this.message = "删除失败";
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		return result;

	}

	/* <!--查询800M移动基站巡检表--> */
	@RequestMapping(value = "/mbsinfo", method = RequestMethod.GET)
	public void mbsinfo(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("totals", AppInspectionServer.mbsinfoCount());
		result.put("items", AppInspectionServer.mbsinfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* <!--自建基站巡检表--> */
	@RequestMapping(value = "/sbsinfo", method = RequestMethod.GET)
	public void sbsinfo(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		int period=Integer.parseInt(request.getParameter("period"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("starttime", starttime);
		map.put("endtime", endtime);
		map.put("period", period);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("totals", AppInspectionServer.sbsinfoCount(map));
		result.put("bstotals", BsstationService.select_bs_by_type(0));
		result.put("items", AppInspectionServer.sbsinfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* <!--网管巡检表--> */
	@RequestMapping(value = "/netinfo", method = RequestMethod.GET)
	public void netinfo(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		String time = request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time", time);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("totals", AppInspectionServer.netinfoCount(map));
		result.put("items", AppInspectionServer.netinfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/verticalinfo", method = RequestMethod.GET)
	public void verticalinfo(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		String time = request.getParameter("time");
		int period=Integer.parseInt(request.getParameter("period"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time", time);
		map.put("period", period);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("totals", AppInspectionServer.verticalinfoCount(map));
		result.put("items", AppInspectionServer.verticalinfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/roominfo", method = RequestMethod.GET)
	public void roominfo(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		String time = request.getParameter("time");
		int period=Integer.parseInt(request.getParameter("period"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time", time);
		map.put("period", period);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("totals", AppInspectionServer.roominfoCount(map));
		result.put("items", AppInspectionServer.roominfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/starinfo", method = RequestMethod.GET)
	public void starinfo(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		String time = request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time", time);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("totals", AppInspectionServer.starinfoCount(map));
		result.put("items", AppInspectionServer.starinfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* <!--调度台巡检表--> */
	@RequestMapping(value = "/dispatchinfo", method = RequestMethod.GET)
	public void dispatchinfo(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		String time = request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time", time);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("totals", AppInspectionServer.dispatchinfoCount(map));
		result.put("items", AppInspectionServer.dispatchinfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* <!--交换中心巡检表--> */
	@RequestMapping(value = "/mscinfo", method = RequestMethod.GET)
	public void mscinfo(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		int start = FunUtil.StringToInt(request.getParameter("start"));
		int limit = FunUtil.StringToInt(request.getParameter("limit"));
		String time = request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time", time);
		map.put("year", 1);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("totals", AppInspectionServer.mscCount(map));
		result.put("items", AppInspectionServer.mscinfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/dispatch_add", method = RequestMethod.POST)
	public void dispatch_add(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionDispatchBean bean = GsonUtil.json2Object(data,
				InspectionDispatchBean.class);
		bean.setUserid(FunUtil.loginUser(request));

		System.out.println(bean);
		int rst = AppInspectionServer.dispatch_add(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "添加调度台巡检记录成功！";
		} else {
			this.success = false;
			this.message = "添加调度台巡检记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/dispatch_edit", method = RequestMethod.POST)
	public void dispatch_edit(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionDispatchBean bean = GsonUtil.json2Object(data,
				InspectionDispatchBean.class);

		System.out.println(bean);
		int rst = AppInspectionServer.dispatch_edit(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "修改交调度台巡检记录成功！";
		} else {
			this.success = false;
			this.message = "修改调度台巡检记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/net_add", method = RequestMethod.POST)
	public void net_add(HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionNetBean bean = GsonUtil.json2Object(data,
				InspectionNetBean.class);
		bean.setUserid(FunUtil.loginUser(request));

		System.out.println(bean);
		int rst = AppInspectionServer.net_add(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "添加网管巡检记录成功！";
		} else {
			this.success = false;
			this.message = "添加网管巡检记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/net_edit", method = RequestMethod.POST)
	public void net_edit(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionNetBean bean = GsonUtil.json2Object(data,
				InspectionNetBean.class);

		System.out.println(bean);
		int rst = AppInspectionServer.net_edit(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "修改交网管巡检记录成功！";
		} else {
			this.success = false;
			this.message = "修改网管巡检记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/vertical_add", method = RequestMethod.POST)
	public void vertical_add(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionVerticalBean bean = GsonUtil.json2Object(data,
				InspectionVerticalBean.class);
		bean.setUserid(FunUtil.loginUser(request));
		bean.setSerialnumber(FunUtil.RandomAlphanumeric(8));

		System.out.println(bean);
		int rst = AppInspectionServer.vertical_add(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "添加直放站检记录成功！";
		} else {
			this.success = false;
			this.message = "添加记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/vertical_edit", method = RequestMethod.POST)
	public void vertical_edit(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionVerticalBean bean = GsonUtil.json2Object(data,
				InspectionVerticalBean.class);

		System.out.println(bean);
		int rst = AppInspectionServer.vertical_edit(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "修改直放站巡检记录成功！";
		} else {
			this.success = false;
			this.message = "修改记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	@RequestMapping(value = "/room_add", method = RequestMethod.POST)
	public void room_add(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionRoomBean bean = GsonUtil.json2Object(data,
				InspectionRoomBean.class);
		bean.setUserid(FunUtil.loginUser(request));
		bean.setSerialnumber(FunUtil.RandomAlphanumeric(8));

		System.out.println(bean);
		int rst = AppInspectionServer.room_add(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "添加室内覆盖站巡检记录成功！";
		} else {
			this.success = false;
			this.message = "添加记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/room_edit", method = RequestMethod.POST)
	public void room_edit(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionRoomBean bean = GsonUtil.json2Object(data,
				InspectionRoomBean.class);

		System.out.println(bean);
		int rst = AppInspectionServer.room_edit(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "修改室内覆盖站巡检记录成功！";
		} else {
			this.success = false;
			this.message = "修改记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/star_add", method = RequestMethod.POST)
	public void star_add(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionStarBean bean = GsonUtil.json2Object(data,
				InspectionStarBean.class);
		bean.setUserid(FunUtil.loginUser(request));
		bean.setSerialnumber(FunUtil.RandomAlphanumeric(8));

		System.out.println(bean);
		int rst = AppInspectionServer.star_add(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "添加卫星通信车载便携站巡检记录成功！";
		} else {
			this.success = false;
			this.message = "添加记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/star_edit", method = RequestMethod.POST)
	public void star_edit(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionStarBean bean = GsonUtil.json2Object(data,
				InspectionStarBean.class);
		
		
		int rst = AppInspectionServer.star_edit(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "修改卫星通信车载便携站巡检记录成功！";
		} else {
			this.success = false;
			this.message = "修改记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/msc_add", method = RequestMethod.POST)
	public void msc_add(HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionMscBean bean = GsonUtil.json2Object(data,
				InspectionMscBean.class);
		bean.setUser(FunUtil.loginUser(request));

		System.out.println(bean);
		int rst = AppInspectionServer.msc_add(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "添加交换中心巡检记录成功！";
		} else {
			this.success = false;
			this.message = "添加交换中心巡检记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/msc_edit", method = RequestMethod.POST)
	public void msc_edit(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionMscBean bean = GsonUtil.json2Object(data,
				InspectionMscBean.class);

		System.out.println(bean);
		int rst = AppInspectionServer.msc_edit(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "修改交换中心巡检记录成功！";
		} else {
			this.success = false;
			this.message = "修改交换中心巡检记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/mbs_add", method = RequestMethod.POST)
	public void mbs_add(HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionMbsBean bean = GsonUtil.json2Object(data,
				InspectionMbsBean.class);
		bean.setSerialnumber(FunUtil.RandomAlphanumeric(8));
		bean.setUserid(FunUtil.loginUser(request));
		Map<String, Object> bs = BsstationService.select_bs_by_bsid(bean
				.getBsid());
		bean.setBslevel(Integer.parseInt(bs.get("level").toString()));
		bean.setBsname(bs.get("name").toString());
		bean.setBstype(bs.get("hometype").toString());

		System.out.println(bean);
		int rst = AppInspectionServer.mbs_add(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "添加移动基站巡检记录成功！";
		} else {
			this.success = false;
			this.message = "添加移动基站巡检记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/mbs_edit", method = RequestMethod.POST)
	public void mbs_edit(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionMbsBean bean = GsonUtil.json2Object(data,
				InspectionMbsBean.class);
		Map<String, Object> bs = BsstationService.select_bs_by_bsid(bean
				.getBsid());
		bean.setBslevel(Integer.parseInt(bs.get("level").toString()));
		bean.setBsname(bs.get("name").toString());
		bean.setBstype(bs.get("hometype").toString());

		System.out.println(bean);
		int rst = AppInspectionServer.mbs_edit(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "修改移动基站巡检记录成功！";
		} else {
			this.success = false;
			this.message = "修改移动基站巡检记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/sbs_add", method = RequestMethod.POST)
	public void sbs_add(HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionSbsBean bean = GsonUtil.json2Object(data,
				InspectionSbsBean.class);
		bean.setSerialnumber(FunUtil.RandomAlphanumeric(8));
		bean.setUserid(FunUtil.loginUser(request));
		Map<String, Object> bs = BsstationService.select_bs_by_bsid(bean
				.getBsid());
		bean.setBslevel(bs.get("level").toString());
		bean.setBsname(bs.get("name").toString());
		bean.setBstype(bs.get("hometype").toString());

		System.out.println(bean);
		int rst = AppInspectionServer.sbs_add(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "添加自建基站巡检记录成功！";
		} else {
			this.success = false;
			this.message = "添加自建基站巡检记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/sbs_edit", method = RequestMethod.POST)
	public void sbs_edit(HttpServletRequest request,
			HttpServletResponse response) {
		String data = request.getParameter("data");
		InspectionSbsBean bean = GsonUtil.json2Object(data,
				InspectionSbsBean.class);
		/*
		 * Map<String, Object> bs = BsstationService.select_bs_by_bsid(bean
		 * .getBsid());
		 */
		/*
		 * bean.setBslevel(bs.get("level").toString());
		 * bean.setBsname(bs.get("name").toString());
		 * bean.setBstype(bs.get("hometype").toString());
		 * 
		 * System.out.println(bean);
		 */
		int rst = AppInspectionServer.sbs_edit(bean);
		if (rst > 0) {
			this.success = true;
			this.message = "修改自建基站巡检记录成功！";
		} else {
			this.success = false;
			this.message = "修改自建基站巡检记录失败！";
		}

		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = FlexJSON.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 导出交换中心巡检记录
	/*@RequestMapping(value = "/excel_msc", method = RequestMethod.POST)
	public void app_msc(HttpServletRequest request, HttpServletResponse response) {
		String time = request.getParameter("time");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", 0);
		map.put("limit", 1);
		map.put("time", time);
		List<InspectionMscBean> list = AppInspectionServer.mscinfo(map);
		InspectionMscBean bean = new InspectionMscBean();
		if (list.size() > 0) {
			bean = list.get(0);
		}
		System.out.println(bean);

		try {
			String saveDir = request.getSession().getServletContext()
					.getRealPath("/app");
			String pathname = saveDir + "/交换中心月度维护" + time + ".xls";
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 16, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 9,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.BRIGHT_GREEN);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体

			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义

			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);

			// 设置其他样式

			wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
			wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcf_title.setBackground(Colour.WHITE);// 背景色
			wcf_title.setWrap(false);// 不自动换行

			
			 * fontFormat_Content.setAlignment(Alignment.CENTRE);// 水平对齐
			 * fontFormat_Content
			 * .setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			 fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			WritableSheet sheet = book.createSheet("交换中心月度维护", 0);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			// 第1行
			Label title = new Label(0, 0, "交换中心月度维护", fontFormat);
			sheet.mergeCells(0, 0, 4, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高

			sheet.setColumnView(0, 5);
			sheet.setColumnView(1, 50);
			sheet.setColumnView(2, 30);
			sheet.setColumnView(3, 30);
			sheet.setColumnView(4, 30);

			for (int i = 1; i < 19; i++) {
				sheet.setRowView(i, 400, false);
			}

			sheet.addCell(new Label(0, 1, "系統名称：成都市应急指挥调度无线通信网",
					fontFormat_Content));
			sheet.mergeCells(0, 1, 1, 1);
			sheet.addCell(new Label(2, 1, "地点：亚光高新产业园", fontFormat_Content));
			sheet.addCell(new Label(3, 1, "检查日期：" + bean.getCheckDate(),
					fontFormat_Content));
			sheet.addCell(new Label(4, 1, "检查人：" + bean.getCheckPersion(),
					fontFormat_Content));

			sheet.addCell(new Label(0, 2, "序号", fontFormat_Content));
			sheet.addCell(new Label(1, 2, "检查内容", fontFormat_Content));
			sheet.addCell(new Label(2, 2, "检查情况", fontFormat_Content));
			sheet.addCell(new Label(3, 2, "问题描述", fontFormat_Content));
			sheet.addCell(new Label(4, 2, "处理情况及遗留问题", fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 3, 1, fontFormat_Content));
			sheet.addCell(new Label(1, 3, "机房门窗地面墙壁等是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 3, checkbox(bean.getA1()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 3, bean.getB1(), fontFormat_Content));
			sheet.addCell(new Label(4, 3, bean.getC1(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 4, 2, fontFormat_Content));
			sheet.addCell(new Label(1, 4, "机房照明、电源插座是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 4, checkbox(bean.getA2()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 4, bean.getB2(), fontFormat_Content));
			sheet.addCell(new Label(4, 4, bean.getC2(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 5, 3, fontFormat_Content));
			sheet.addCell(new Label(1, 5, "设备灰尘及滤网是否清洁(传输3500等)",
					fontFormat_Content));
			sheet.addCell(new Label(2, 5, checkbox(bean.getA3()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 5, bean.getB3(), fontFormat_Content));
			sheet.addCell(new Label(4, 5, bean.getC3(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 6, 4, fontFormat_Content));
			sheet.addCell(new Label(1, 6, "机房是否清洁、是否没有杂物或易燃物品",
					fontFormat_Content));
			sheet.addCell(new Label(2, 6, checkbox(bean.getA4()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 6, bean.getB4(), fontFormat_Content));
			sheet.addCell(new Label(4, 6, bean.getC4(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 7, 5, fontFormat_Content));
			sheet.addCell(new Label(1, 7, "消防设备是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 7, checkbox(bean.getA5()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 7, bean.getB5(), fontFormat_Content));
			sheet.addCell(new Label(4, 7, bean.getC5(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 8, 6, fontFormat_Content));
			sheet.addCell(new Label(1, 8, "UPS是否正常（断电测试）", fontFormat_Content));
			sheet.addCell(new Label(2, 8, checkbox(bean.getA6()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 8, bean.getB6(), fontFormat_Content));
			sheet.addCell(new Label(4, 8, bean.getC6(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 9, 7, fontFormat_Content));
			sheet.addCell(new Label(1, 9, "蓄电池是否正常（发电测试）", fontFormat_Content));
			sheet.addCell(new Label(2, 9, checkbox(bean.getA7()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 9, bean.getB7(), fontFormat_Content));
			sheet.addCell(new Label(4, 9, bean.getC7(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 10, 8, fontFormat_Content));
			sheet.addCell(new Label(1, 10, "发电机是否正常（包括汽油发电机、柴油发电机）",
					fontFormat_Content));
			sheet.addCell(new Label(2, 10, checkbox(bean.getA8()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 10, bean.getB8(), fontFormat_Content));
			sheet.addCell(new Label(4, 10, bean.getC8(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 11, 9, fontFormat_Content));
			sheet.addCell(new Label(1, 11, "空调是否正常工作、滤网是否清洁",
					fontFormat_Content));
			sheet.addCell(new Label(2, 11, checkbox(bean.getA9()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 11, bean.getB9(), fontFormat_Content));
			sheet.addCell(new Label(4, 11, bean.getC9(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 12, 10, fontFormat_Content));
			sheet.addCell(new Label(1, 12, "环境监控系统是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 12, checkbox(bean.getA10()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 12, bean.getB10(), fontFormat_Content));
			sheet.addCell(new Label(4, 12, bean.getC10(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 13, 11, fontFormat_Content));
			sheet.addCell(new Label(1, 13, "电源线是否老化", fontFormat_Content));
			sheet.addCell(new Label(2, 13, checkbox(bean.getA11()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 13, bean.getB11(), fontFormat_Content));
			sheet.addCell(new Label(4, 13, bean.getC11(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 14, 12, fontFormat_Content));
			sheet.addCell(new Label(1, 14, "设备标签是否规范完、完整、走线是否正常",
					fontFormat_Content));
			sheet.addCell(new Label(2, 14, checkbox(bean.getA12()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 14, bean.getB12(), fontFormat_Content));
			sheet.addCell(new Label(4, 14, bean.getC12(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 15, 13, fontFormat_Content));
			sheet.addCell(new Label(1, 15, "服务器是否运行正常", fontFormat_Content));
			sheet.addCell(new Label(2, 15, checkbox(bean.getA13()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 15, bean.getB13(), fontFormat_Content));
			sheet.addCell(new Label(4, 15, bean.getC13(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 16, 14, fontFormat_Content));
			sheet.addCell(new Label(1, 16, "设备接地是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 16, checkbox(bean.getA14()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 16, bean.getB14(), fontFormat_Content));
			sheet.addCell(new Label(4, 16, bean.getC14(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 17, 15, fontFormat_Content));
			sheet.addCell(new Label(1, 17, "服务器、单板、模块安装是否牢固可靠",
					fontFormat_Content));
			sheet.addCell(new Label(2, 17, checkbox(bean.getA15()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 17, bean.getB15(), fontFormat_Content));
			sheet.addCell(new Label(4, 17, bean.getC15(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 18, 16, fontFormat_Content));
			sheet.addCell(new Label(1, 18, "楼顶吸盘天线是否加固，接头是否正常",
					fontFormat_Content));
			sheet.addCell(new Label(2, 18, checkbox(bean.getA16()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 18, bean.getB16(), fontFormat_Content));
			sheet.addCell(new Label(4, 18, bean.getC16(), fontFormat_Content));

			sheet.addCell(new jxl.write.Number(0, 19, 17, fontFormat_Content));
			sheet.addCell(new Label(1, 19, "服务器数据及配置是否备份", fontFormat_Content));
			sheet.addCell(new Label(2, 19, checkbox(bean.getA17()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 19, bean.getB17(), fontFormat_Content));
			sheet.addCell(new Label(4, 19, bean.getC17(), fontFormat_Content));

			book.write();
			book.close();
			// DownExcelFile(response, pathname);
			this.success = true;
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("success", success);
			result.put("pathName", pathname);
			response.setContentType("application/json;charset=utf-8");
			String jsonstr = json.Encode(result);
			response.getWriter().write(jsonstr);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}*/

	// 导出网管巡检记录
	@RequestMapping(value = "/excel_net", method = RequestMethod.POST)
	public void app_net(HttpServletRequest request, HttpServletResponse response) {
		String excelData = request.getParameter("excelData");

		InspectionNetBean bean = GsonUtil.json2Object(excelData,
				InspectionNetBean.class);

		try {
			String saveDir = request.getSession().getServletContext()
					.getRealPath("/app");
			String pathname = saveDir + "/网管巡检表.xls";
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 16, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体

			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义

			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);

			// 设置其他样式

			wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
			wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcf_title.setBackground(Colour.WHITE);// 背景色
			wcf_title.setWrap(false);// 不自动换行

			fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			WritableSheet sheet = book.createSheet("网管巡检表", 0);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			// 第1行
			Label title = new Label(0, 0, "网管巡检表", fontFormat);
			sheet.mergeCells(0, 0, 7, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE, PaperSize.A4, 0.5d, 0.5d);
			sheet.getSettings().setLeftMargin(0.50);
			sheet.getSettings().setRightMargin(0.50);
			sheet.getSettings().setTopMargin(0.5);
			sheet.getSettings().setBottomMargin(0.5);

			// 第2行
			Label label_1_0 = new Label(0, 1, "网管名称", fontFormat_h);// 创建单元格
			Label label_1_1 = new Label(1, 1, bean.getManagername(),
					fontFormat_Content);// 创建单元格
			Label label_1_2 = new Label(2, 1, "地点", fontFormat_h);// 创建单元格
			Label label_1_3 = new Label(3, 1, bean.getManagerplace(),
					fontFormat_Content);// 创建单元格
			Label label_1_4 = new Label(4, 1, "巡检时间", fontFormat_h);// 创建单元格
			Label label_1_5 = new Label(5, 1, bean.getCommitdate().split(" ")[0],
					fontFormat_Content);// 创建单元格
			Label label_1_6 = new Label(6, 1, "巡检人", fontFormat_h);// 创建单元格
			Label label_1_7 = new Label(7, 1, bean.getCheckman(),
					fontFormat_Content);// 创建单元格

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 25);
			sheet.setColumnView(2, 10);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 10);
			sheet.setColumnView(7, 20);

			sheet.addCell(label_1_0);
			sheet.addCell(label_1_1);
			sheet.addCell(label_1_2);
			sheet.addCell(label_1_3);
			sheet.addCell(label_1_4);
			sheet.addCell(label_1_5);
			sheet.addCell(label_1_6);
			sheet.addCell(label_1_7);

			// 第3行
			Label label_2_0 = new Label(0, 2, "序号", fontFormat_Content);// 创建单元格
			Label label_2_1 = new Label(1, 2, "检查类容", fontFormat_Content);// 创建单元格
			Label label_2_2 = new Label(2, 2, "备注", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 2, 3, 2);
			Label label_2_3 = new Label(4, 2, "检查情况", fontFormat_Content);// 创建单元格
			Label label_2_4 = new Label(5, 2, "问题描述", fontFormat_Content);// 创建单元格
			Label label_2_5 = new Label(6, 2, "处理情况及遗留问题", fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 2, 7, 2);
			sheet.addCell(label_2_0);
			sheet.addCell(label_2_1);
			sheet.addCell(label_2_2);
			sheet.addCell(label_2_3);
			sheet.addCell(label_2_4);
			sheet.addCell(label_2_5);

			// 第4行
			Label label_3_0 = new Label(0, 3, "1", fontFormat_Content);// 创建单元格
			Label label_3_1 = new Label(1, 3, "网管安装环境是否完成正常",
					fontFormat_Content);// 创建单元格
			Label label_3_2 = new Label(2, 3, bean.getComment1(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 3, 3, 3);
			Label label_3_3 = new Label(4, 3, checkbox(bean.getD1()),
					fontFormat_Content);// 创建单元格
			Label label_3_4 = new Label(5, 3, bean.getP1(), fontFormat_Content);// 创建单元格
			Label label_3_5 = new Label(6, 3, bean.getR1(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 3, 7, 3);
			sheet.addCell(label_3_0);
			sheet.addCell(label_3_1);
			sheet.addCell(label_3_2);
			sheet.addCell(label_3_3);
			sheet.addCell(label_3_4);
			sheet.addCell(label_3_5);
			// 第5行
			Label label_4_0 = new Label(0, 4, "2", fontFormat_Content);// 创建单元格
			Label label_4_1 = new Label(1, 4, "网管电源是否正常开启", fontFormat_Content);// 创建单元格
			Label label_4_2 = new Label(2, 4, bean.getComment2(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 4, 3, 4);
			Label label_4_3 = new Label(4, 4, checkbox(bean.getD2()),
					fontFormat_Content);// 创建单元格
			Label label_4_4 = new Label(5, 4, bean.getP2(), fontFormat_Content);// 创建单元格
			Label label_4_5 = new Label(6, 4, bean.getR2(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 4, 7, 4);
			sheet.addCell(label_4_0);
			sheet.addCell(label_4_1);
			sheet.addCell(label_4_2);
			sheet.addCell(label_4_3);
			sheet.addCell(label_4_4);
			sheet.addCell(label_4_5);
			// 第6行
			Label label_5_0 = new Label(0, 5, "3", fontFormat_Content);// 创建单元格
			Label label_5_1 = new Label(1, 5, "网管是否正常登录", fontFormat_Content);// 创建单元格
			Label label_5_2 = new Label(2, 5, bean.getComment3(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 5, 3, 5);
			Label label_5_3 = new Label(4, 5, checkbox(bean.getD3()),
					fontFormat_Content);// 创建单元格
			Label label_5_4 = new Label(5, 5, bean.getP3(), fontFormat_Content);// 创建单元格
			Label label_5_5 = new Label(6, 5, bean.getR3(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 5, 7, 5);
			sheet.addCell(label_5_0);
			sheet.addCell(label_5_1);
			sheet.addCell(label_5_2);
			sheet.addCell(label_5_3);
			sheet.addCell(label_5_4);
			sheet.addCell(label_5_5);
			// 第7行
			Label label_6_0 = new Label(0, 6, "4", fontFormat_Content);// 创建单元格
			Label label_6_1 = new Label(1, 6, "配置管理查看是否正常", fontFormat_Content);// 创建单元格
			Label label_6_2 = new Label(2, 6, bean.getComment4(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 6, 3, 6);
			Label label_6_3 = new Label(4, 6, checkbox(bean.getD4()),
					fontFormat_Content);// 创建单元格
			Label label_6_4 = new Label(5, 6, bean.getP4(), fontFormat_Content);// 创建单元格
			Label label_6_5 = new Label(6, 6, bean.getR4(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 6, 7, 6);
			sheet.addCell(label_6_0);
			sheet.addCell(label_6_1);
			sheet.addCell(label_6_2);
			sheet.addCell(label_6_3);
			sheet.addCell(label_6_4);
			sheet.addCell(label_6_5);
			// 第8行
			Label label_7_0 = new Label(0, 7, "5", fontFormat_Content);// 创建单元格
			Label label_7_1 = new Label(1, 7, "用户管理查看是否正常", fontFormat_Content);// 创建单元格
			Label label_7_2 = new Label(2, 7, bean.getComment5(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 7, 3, 7);
			Label label_7_3 = new Label(4, 7, checkbox(bean.getD5()),
					fontFormat_Content);// 创建单元格
			Label label_7_4 = new Label(5, 7, bean.getP5(), fontFormat_Content);// 创建单元格
			Label label_7_5 = new Label(6, 7, bean.getR5(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 7, 7, 7);
			sheet.addCell(label_7_0);
			sheet.addCell(label_7_1);
			sheet.addCell(label_7_2);
			sheet.addCell(label_7_3);
			sheet.addCell(label_7_4);
			sheet.addCell(label_7_5);
			// 第9行
			Label label_8_0 = new Label(0, 8, "6", fontFormat_Content);// 创建单元格
			Label label_8_1 = new Label(1, 8, "故障管理查看是否正常", fontFormat_Content);// 创建单元格
			Label label_8_2 = new Label(2, 8, bean.getComment6(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 8, 3, 8);
			Label label_8_3 = new Label(4, 8, checkbox(bean.getD6()),
					fontFormat_Content);// 创建单元格
			Label label_8_4 = new Label(5, 8, bean.getP6(), fontFormat_Content);// 创建单元格
			Label label_8_5 = new Label(6, 8, bean.getR6(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 8, 7, 8);
			sheet.addCell(label_8_0);
			sheet.addCell(label_8_1);
			sheet.addCell(label_8_2);
			sheet.addCell(label_8_3);
			sheet.addCell(label_8_4);
			sheet.addCell(label_8_5);
			// 第10行
			Label label_9_0 = new Label(0, 9, "7", fontFormat_Content);// 创建单元格
			Label label_9_1 = new Label(1, 9, "安全管理查看是否正常", fontFormat_Content);// 创建单元格
			Label label_9_2 = new Label(2, 9, bean.getComment7(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 9, 3, 9);
			Label label_9_3 = new Label(4, 9, checkbox(bean.getD7()),
					fontFormat_Content);// 创建单元格
			Label label_9_4 = new Label(5, 9, bean.getP7(), fontFormat_Content);// 创建单元格
			Label label_9_5 = new Label(6, 9, bean.getR7(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 9, 7, 9);
			sheet.addCell(label_9_0);
			sheet.addCell(label_9_1);
			sheet.addCell(label_9_2);
			sheet.addCell(label_9_3);
			sheet.addCell(label_9_4);
			sheet.addCell(label_9_5);
			// 第11行
			Label label_10_0 = new Label(0, 10, "8", fontFormat_Content);// 创建单元格
			Label label_10_1 = new Label(1, 10, "辅助管理查看是否正常",
					fontFormat_Content);// 创建单元格
			Label label_10_2 = new Label(2, 10, bean.getComment8(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 10, 3, 10);
			Label label_10_3 = new Label(4, 10, checkbox(bean.getD8()),
					fontFormat_Content);// 创建单元格
			Label label_10_4 = new Label(5, 10, bean.getP8(),
					fontFormat_Content);// 创建单元格
			Label label_10_5 = new Label(6, 10, bean.getR8(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 10, 7, 10);
			sheet.addCell(label_10_0);
			sheet.addCell(label_10_1);
			sheet.addCell(label_10_2);
			sheet.addCell(label_10_3);
			sheet.addCell(label_10_4);
			sheet.addCell(label_10_5);
			// 第12行
			Label label_11_0 = new Label(0, 11, "9", fontFormat_Content);// 创建单元格
			Label label_11_1 = new Label(1, 11, "性能管理查看是否正常",
					fontFormat_Content);// 创建单元格
			Label label_11_2 = new Label(2, 11, bean.getComment9(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 11, 3, 11);
			Label label_11_3 = new Label(4, 11, checkbox(bean.getD9()),
					fontFormat_Content);// 创建单元格
			Label label_11_4 = new Label(5, 11, bean.getP9(),
					fontFormat_Content);// 创建单元格
			Label label_11_5 = new Label(6, 11, bean.getR9(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 11, 7, 11);
			sheet.addCell(label_11_0);
			sheet.addCell(label_11_1);
			sheet.addCell(label_11_2);
			sheet.addCell(label_11_3);
			sheet.addCell(label_11_4);
			sheet.addCell(label_11_5);
			// 第13行
			Label label_12_0 = new Label(0, 12, "10", fontFormat_Content);// 创建单元格
			Label label_12_1 = new Label(1, 12, "拓扑管理查看是否正常",
					fontFormat_Content);// 创建单元格
			Label label_12_2 = new Label(2, 12, bean.getComment10(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 12, 3, 12);
			Label label_12_3 = new Label(4, 12, checkbox(bean.getD10()),
					fontFormat_Content);// 创建单元格
			Label label_12_4 = new Label(5, 12, bean.getP10(),
					fontFormat_Content);// 创建单元格
			Label label_12_5 = new Label(6, 12, bean.getR10(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 12, 7, 12);
			sheet.addCell(label_12_0);
			sheet.addCell(label_12_1);
			sheet.addCell(label_12_2);
			sheet.addCell(label_12_3);
			sheet.addCell(label_12_4);
			sheet.addCell(label_12_5);
			// 第14行
			Label label_13_0 = new Label(0, 13, "11", fontFormat_Content);// 创建单元格
			Label label_13_1 = new Label(1, 13, "环境温度是否工作正常",
					fontFormat_Content);// 创建单元格
			Label label_13_2 = new Label(2, 13, bean.getComment11(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 13, 3, 13);
			Label label_13_3 = new Label(4, 13, checkbox(bean.getD11()),
					fontFormat_Content);// 创建单元格
			Label label_13_4 = new Label(5, 13, bean.getP11(),
					fontFormat_Content);// 创建单元格
			Label label_13_5 = new Label(6, 13, bean.getR11(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 13, 7, 13);
			sheet.addCell(label_13_0);
			sheet.addCell(label_13_1);
			sheet.addCell(label_13_2);
			sheet.addCell(label_13_3);
			sheet.addCell(label_13_4);
			sheet.addCell(label_13_5);

			book.write();
			book.close();
			// DownExcelFile(response, pathname);
			this.success = true;
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

	@RequestMapping(value = "/excel_vertical", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> excel_vertical(HttpServletRequest request,
			HttpServletResponse response) {
		String excelData = request.getParameter("excelData");

		InspectionVerticalBean bean = GsonUtil.json2Object(excelData,
				InspectionVerticalBean.class);

		try {
			String saveDir = request.getSession().getServletContext()
					.getRealPath("/app");
			String pathname = saveDir + "/直放站巡检表.xls";
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
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES,11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体

			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义

			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);

			// 设置其他样式

			wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
			wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcf_title.setBackground(Colour.WHITE);// 背景色
			wcf_title.setWrap(false);// 不自动换行

			fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			WritableSheet sheet = book.createSheet("直放站巡检表", 0);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE, PaperSize.A4, 0.5d, 0.5d);
			/*sheet.getSettings().setLeftMargin(0.50);
			sheet.getSettings().setRightMargin(0.50);
			sheet.getSettings().setTopMargin(0.5);
			sheet.getSettings().setBottomMargin(0.5);*/

			// 第1行
			Label title = new Label(0, 0, "直放站巡检表", fontFormat);
			sheet.mergeCells(0, 0, 4, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高

			// 第2行

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 25);
			sheet.setColumnView(2, 40);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 30);
			

			sheet.addCell(new Label(0, 1, "基站名称", fontFormat_Content));
			sheet.addCell(new Label(1, 1, bean.getName(), fontFormat_Content));
			sheet.addCell(new Label(2, 1, "巡检人", fontFormat_Content));
			sheet.addCell(new Label(3, 1, bean.getCheckman(),
					fontFormat_Content));
			sheet.mergeCells(3, 1, 4, 1);

			sheet.addCell(new Label(0, 2, "巡检时间", fontFormat_Content));
			sheet.addCell(new Label(1, 2, bean.getCommitdate().split(" ")[0],
					fontFormat_Content));
			sheet.addCell(new Label(2, 2, "电表", fontFormat_Content));
			sheet.addCell(new Label(3, 2, bean.getAmmeternumber(),
					fontFormat_Content));
			sheet.mergeCells(3, 2, 4, 2);

			// 第3行
			// sheet.mergeCells(6, 2, 7, 2);

			sheet.addCell(new Label(0, 3, "巡检项目", fontFormat_h));
			sheet.addCell(new Label(1, 3, "具体项目", fontFormat_h));
			sheet.addCell(new Label(2, 3, "具体操作", fontFormat_h));
			sheet.addCell(new Label(3, 3, "执行情况", fontFormat_h));
			sheet.addCell(new Label(4, 3, "备注", fontFormat_h));

			// 第4行
			sheet.addCell(new Label(0, 4, "基站清洁", fontFormat_Content));
			sheet.mergeCells(0, 4, 0, 5);
			sheet.addCell(new Label(1, 4, "机房清洁", fontFormat_Content));
			sheet.addCell(new Label(2, 4, "清洁机房内门窗、地面、墙面卫生", fontFormat_Content));
			sheet.addCell(new Label(3, 4, checkbox(bean.getD1()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 4, bean.getComment1(),
					fontFormat_Content));
			// 第5行
			sheet.addCell(new Label(1, 5, "设备除尘", fontFormat_Content));
			sheet.addCell(new Label(2, 5, "清洁设备表面及内部", fontFormat_Content));
			sheet.addCell(new Label(3, 5, checkbox(bean.getD2()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 5, bean.getComment2(),
					fontFormat_Content));
			// 第6行
			sheet.addCell(new Label(0, 6, "基站环境", fontFormat_Content));
			sheet.mergeCells(0, 6, 0, 11);
			sheet.addCell(new Label(1, 6, "基站安全", fontFormat_Content));
			sheet.mergeCells(1, 6, 1, 8);
			sheet.addCell(new Label(2, 6, "门锁、门窗是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 6, checkbox(bean.getD3()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 6, bean.getComment3(),
					fontFormat_Content));
			// 第7行

			sheet.addCell(new Label(2, 7, "清理可燃物", fontFormat_Content));
			sheet.addCell(new Label(3, 7, checkbox(bean.getD4()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 7, bean.getComment4(),
					fontFormat_Content));
			// 第8行
			sheet.addCell(new Label(2, 8, "机房是否存在裂缝、渗水、漏水等情况	",
					fontFormat_Content));
			sheet.addCell(new Label(3, 8, checkbox(bean.getD5()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 8, bean.getComment5(),
					fontFormat_Content));
			// 第9行
			sheet.addCell(new Label(1, 9, "机房配套", fontFormat_Content));
			sheet.mergeCells(1, 9, 1, 11);
			sheet.addCell(new Label(2, 9, "机房照明设施是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 9, checkbox(bean.getD6()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 9, bean.getComment6(),
					fontFormat_Content));
			// 第10行
			sheet.addCell(new Label(2, 10, "机房插座是否有电", fontFormat_Content));
			sheet.addCell(new Label(3, 10, checkbox(bean.getD7()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 10, bean.getComment7(),
					fontFormat_Content));
			// 第11行
			sheet.addCell(new Label(2, 10, "灭火设备是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 10, checkbox(bean.getD8()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 10, bean.getComment8(),
					fontFormat_Content));
			// 第12行
			sheet.addCell(new Label(0, 11, "主体设备", fontFormat_Content));
			sheet.mergeCells(0, 11, 0, 16);
			sheet.addCell(new Label(1, 11, "基站运行状态", fontFormat_Content));
			sheet.addCell(new Label(2, 11, "基站设备是否正常运行，供电是否正常，设备有无明显告警",
					fontFormat_Content));
			sheet.addCell(new Label(3, 11, checkbox(bean.getD9()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 11, bean.getComment9(),
					fontFormat_Content));
			// 第13行
			sheet.addCell(new Label(1, 12, "告警确认", fontFormat_Content));
			sheet.addCell(new Label(2, 12, "通过与监控后台联系确认设备运行是否正常",
					fontFormat_Content));
			sheet.addCell(new Label(3, 12, checkbox(bean.getD10()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 12, bean.getComment10(),
					fontFormat_Content));
			// 第14行
			sheet.addCell(new Label(1, 13, "风扇运行检查", fontFormat_Content));
			sheet.addCell(new Label(2, 13, "检查风扇是否正常运转、无告警", fontFormat_Content));
			sheet.addCell(new Label(3, 13, checkbox(bean.getD11()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 13, bean.getComment11(),
					fontFormat_Content));
			// 第15行
			sheet.addCell(new Label(1, 14, "检查各连线状态", fontFormat_Content));
			sheet.addCell(new Label(2, 14,
					"电源/信号/射频/地线/网线是否分开，机架内各连线是否接触良好并正确无误", fontFormat_Content));
			sheet.addCell(new Label(3, 14, checkbox(bean.getD12()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 14, bean.getComment12(),
					fontFormat_Content));
			// 第16行
			sheet.addCell(new Label(1, 15, "设备加固", fontFormat_Content));
			sheet.addCell(new Label(2, 15, "各设备是否安装牢固", fontFormat_Content));
			sheet.addCell(new Label(3, 15, checkbox(bean.getD13()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 15, bean.getComment13(),
					fontFormat_Content));
			// 第17行
			sheet.addCell(new Label(1, 16, "设备温度", fontFormat_Content));
			sheet.addCell(new Label(2, 16, "各设备是否有高温、异味", fontFormat_Content));
			sheet.addCell(new Label(3, 16, checkbox(bean.getD14()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 16, bean.getComment14(),
					fontFormat_Content));
			// 第18行
			sheet.addCell(new Label(0, 17, "空调", fontFormat_Content));
			sheet.mergeCells(0, 17, 0, 18);
			sheet.addCell(new Label(1, 17, "制冷效果", fontFormat_Content));
			sheet.addCell(new Label(2, 17, "空调制冷效果是否正常", fontFormat_Content));
			sheet.addCell(new Label(3, 17, checkbox(bean.getD15()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 17, bean.getComment16(),
					fontFormat_Content));
			// 第19行
			sheet.addCell(new Label(1, 18, "温度设置", fontFormat_Content));
			sheet.addCell(new Label(2, 18, "机房温度是否正常（建议空调设置25℃，最终情况以机房环境为主）",
					fontFormat_Content));
			sheet.addCell(new Label(3, 18, checkbox(bean.getD16()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 18, bean.getComment16(),
					fontFormat_Content));
			// 第20行
			sheet.addCell(new Label(0, 19, "功能", fontFormat_Content));
			sheet.addCell(new Label(1, 19, "呼叫功能", fontFormat_Content));
			sheet.addCell(new Label(2, 19, "沿途呼叫测试", fontFormat_Content));
			sheet.addCell(new Label(3, 19, checkbox(bean.getD17()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 19, bean.getComment17(),
					fontFormat_Content));
			// 第20行
			sheet.addCell(new Label(0, 20, "遗留问题", fontFormat_Content));
			sheet.addCell(new Label(1, 20, bean.getQuestion(),
					fontFormat_Content));
			sheet.mergeCells(1, 20, 4, 20);
			sheet.setRowView(20, 600);
			book.write();
			book.close();
			// DownExcelFile(response, pathname);
			this.success = true;
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("success", success);
			result.put("pathName", pathname);
			return result;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}
	@RequestMapping(value = "/excel_room", method = RequestMethod.POST)
	public void excel_room(HttpServletRequest request,
			HttpServletResponse response) {
		String excelData = request.getParameter("excelData");

		InspectionRoomBean bean = GsonUtil.json2Object(excelData,
				InspectionRoomBean.class);

		try {
			String saveDir = request.getSession().getServletContext()
					.getRealPath("/app");
			String pathname = saveDir + "/室内覆盖站巡检表.xls";
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
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体

			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义

			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);

			// 设置其他样式

			wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
			wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcf_title.setBackground(Colour.WHITE);// 背景色
			wcf_title.setWrap(false);// 不自动换行

			fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			WritableSheet sheet = book.createSheet("室内覆盖站巡检表", 0);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			// 第1行
			Label title = new Label(0, 0, "室内覆盖站巡检表", fontFormat);
			sheet.mergeCells(0, 0, 4, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE, PaperSize.A4, 0.5d, 0.5d);

			// 第2行

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 25);
			sheet.setColumnView(2, 40);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 30);

			sheet.addCell(new Label(0, 1, "基站名称", fontFormat_Content));
			sheet.addCell(new Label(1, 1, bean.getName(), fontFormat_Content));
			sheet.addCell(new Label(2, 1, "巡检人", fontFormat_Content));
			sheet.addCell(new Label(3, 1, bean.getCheckman(),
					fontFormat_Content));
			sheet.mergeCells(3, 1, 4, 1);

			sheet.addCell(new Label(0, 2, "巡检时间", fontFormat_Content));
			sheet.addCell(new Label(1, 2, bean.getCommitdate().split(" ")[0],
					fontFormat_Content));
			sheet.addCell(new Label(2, 2, "电表", fontFormat_Content));
			sheet.addCell(new Label(3, 2, bean.getAmmeternumber(),
					fontFormat_Content));
			sheet.mergeCells(3, 2, 4, 2);

			// 第3行
			// sheet.mergeCells(6, 2, 7, 2);

			sheet.addCell(new Label(0, 3, "巡检项目", fontFormat_h));
			sheet.addCell(new Label(1, 3, "具体项目", fontFormat_h));
			sheet.addCell(new Label(2, 3, "具体操作", fontFormat_h));
			sheet.addCell(new Label(3, 3, "执行情况", fontFormat_h));
			sheet.addCell(new Label(4, 3, "备注", fontFormat_h));

			// 第4行
			sheet.addCell(new Label(0, 4, "基站清洁", fontFormat_Content));
			sheet.mergeCells(0, 4, 0, 5);
			sheet.addCell(new Label(1, 4, "机房清洁", fontFormat_Content));
			sheet.addCell(new Label(2, 4, "清洁机房内门窗、地面、墙面卫生", fontFormat_Content));
			sheet.addCell(new Label(3, 4, checkbox(bean.getD1()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 4, bean.getComment1(),
					fontFormat_Content));
			// 第5行
			sheet.addCell(new Label(1, 5, "设备除尘", fontFormat_Content));
			sheet.addCell(new Label(2, 5, "清洁设备表面及内部", fontFormat_Content));
			sheet.addCell(new Label(3, 5, checkbox(bean.getD2()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 5, bean.getComment2(),
					fontFormat_Content));
			// 第6行
			sheet.addCell(new Label(0, 6, "基站环境", fontFormat_Content));
			sheet.mergeCells(0, 6, 0, 11);
			sheet.addCell(new Label(1, 6, "基站安全", fontFormat_Content));
			sheet.mergeCells(1, 6, 1, 8);
			sheet.addCell(new Label(2, 6, "门锁、门窗是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 6, checkbox(bean.getD3()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 6, bean.getComment3(),
					fontFormat_Content));
			// 第7行

			sheet.addCell(new Label(2, 7, "清理可燃物", fontFormat_Content));
			sheet.addCell(new Label(3, 7, checkbox(bean.getD4()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 7, bean.getComment4(),
					fontFormat_Content));
			// 第8行
			sheet.addCell(new Label(2, 8, "机房是否存在裂缝、渗水、漏水等情况	",
					fontFormat_Content));
			sheet.addCell(new Label(3, 8, checkbox(bean.getD5()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 8, bean.getComment5(),
					fontFormat_Content));
			// 第9行
			sheet.addCell(new Label(1, 9, "机房配套", fontFormat_Content));
			sheet.mergeCells(1, 9, 1, 11);
			sheet.addCell(new Label(2, 9, "机房照明设施是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 9, checkbox(bean.getD6()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 9, bean.getComment6(),
					fontFormat_Content));
			// 第10行
			sheet.addCell(new Label(2, 10, "机房插座是否有电", fontFormat_Content));
			sheet.addCell(new Label(3, 10, checkbox(bean.getD7()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 10, bean.getComment7(),
					fontFormat_Content));
			// 第11行
			sheet.addCell(new Label(2, 10, "灭火设备是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 10, checkbox(bean.getD8()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 10, bean.getComment8(),
					fontFormat_Content));
			// 第12行
			sheet.addCell(new Label(0, 11, "主体设备", fontFormat_Content));
			sheet.mergeCells(0, 11, 0, 16);
			sheet.addCell(new Label(1, 11, "基站运行状态", fontFormat_Content));
			sheet.addCell(new Label(2, 11, "基站设备是否正常运行，供电是否正常，设备有无明显告警",
					fontFormat_Content));
			sheet.addCell(new Label(3, 11, checkbox(bean.getD9()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 11, bean.getComment9(),
					fontFormat_Content));
			// 第13行
			sheet.addCell(new Label(1, 12, "告警确认", fontFormat_Content));
			sheet.addCell(new Label(2, 12, "通过与监控后台联系确认设备运行是否正常",
					fontFormat_Content));
			sheet.addCell(new Label(3, 12, checkbox(bean.getD10()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 12, bean.getComment10(),
					fontFormat_Content));
			// 第14行
			sheet.addCell(new Label(1, 13, "风扇运行检查", fontFormat_Content));
			sheet.addCell(new Label(2, 13, "检查风扇是否正常运转、无告警", fontFormat_Content));
			sheet.addCell(new Label(3, 13, checkbox(bean.getD11()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 13, bean.getComment11(),
					fontFormat_Content));
			// 第15行
			sheet.addCell(new Label(1, 14, "检查各连线状态", fontFormat_Content));
			sheet.addCell(new Label(2, 14,
					"电源/信号/射频/地线/网线是否分开，机架内各连线是否接触良好并正确无误", fontFormat_Content));
			sheet.addCell(new Label(3, 14, checkbox(bean.getD12()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 14, bean.getComment12(),
					fontFormat_Content));
			// 第16行
			sheet.addCell(new Label(1, 15, "设备加固", fontFormat_Content));
			sheet.addCell(new Label(2, 15, "各设备是否安装牢固", fontFormat_Content));
			sheet.addCell(new Label(3, 15, checkbox(bean.getD13()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 15, bean.getComment13(),
					fontFormat_Content));
			// 第17行
			sheet.addCell(new Label(1, 16, "设备温度", fontFormat_Content));
			sheet.addCell(new Label(2, 16, "各设备是否有高温、异味", fontFormat_Content));
			sheet.addCell(new Label(3, 16, checkbox(bean.getD14()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 16, bean.getComment14(),
					fontFormat_Content));
			// 第18行
			sheet.addCell(new Label(0, 17, "空调", fontFormat_Content));
			sheet.mergeCells(0, 17, 0, 18);
			sheet.addCell(new Label(1, 17, "制冷效果", fontFormat_Content));
			sheet.addCell(new Label(2, 17, "空调制冷效果是否正常", fontFormat_Content));
			sheet.addCell(new Label(3, 17, checkbox(bean.getD15()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 17, bean.getComment16(),
					fontFormat_Content));
			// 第19行
			sheet.addCell(new Label(1, 18, "温度设置", fontFormat_Content));
			sheet.addCell(new Label(2, 18, "机房温度是否正常（建议空调设置25℃，最终情况以机房环境为主）",
					fontFormat_Content));
			sheet.addCell(new Label(3, 18, checkbox(bean.getD16()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 18, bean.getComment16(),
					fontFormat_Content));
			// 第20行
			sheet.addCell(new Label(0, 19, "功能", fontFormat_Content));
			sheet.addCell(new Label(1, 19, "呼叫功能", fontFormat_Content));
			sheet.addCell(new Label(2, 19, "沿途呼叫测试", fontFormat_Content));
			sheet.addCell(new Label(3, 19, checkbox(bean.getD17()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 19, bean.getComment17(),
					fontFormat_Content));
			// 第20行
			sheet.addCell(new Label(0, 20, "遗留问题", fontFormat_Content));
			sheet.addCell(new Label(1, 20, bean.getQuestion(),
					fontFormat_Content));
			sheet.mergeCells(1, 20, 4, 20);
			sheet.setRowView(20, 600);
			book.write();
			book.close();
			// DownExcelFile(response, pathname);
			this.success = true;
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

	@RequestMapping(value = "/excel_msc", method = RequestMethod.POST)
	public void excel_msc(HttpServletRequest request,
			HttpServletResponse response) {
		String excelData = request.getParameter("excelData");

		InspectionMscBean bean = GsonUtil.json2Object(excelData,InspectionMscBean.class);
		String time=bean.getCheckDate();

		try {
			String saveDir = request.getSession().getServletContext().getRealPath("/upload/checksource");
			String pathname = saveDir + "/定期维护报告-交换中心月维护.xls";
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
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体

			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义

			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);

			// 设置其他样式

			wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
			wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcf_title.setBackground(Colour.WHITE);// 背景色
			wcf_title.setWrap(false);// 不自动换行

			fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			WritableSheet sheet = book.createSheet("网管巡检表", 0);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			// 第1行
			Label title = new Label(0, 0, "交换中心月度维护", fontFormat);
			sheet.mergeCells(0, 0, 6, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE, PaperSize.A4, 0.5d, 0.5d);
			sheet.getSettings().setLeftMargin(0.50);
			sheet.getSettings().setRightMargin(0.50);
			sheet.getSettings().setTopMargin(0.5);
			sheet.getSettings().setBottomMargin(0.5);

			// 第2行

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 30);
			sheet.setColumnView(2, 25);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 15);
			sheet.setColumnView(5, 15);
			sheet.setColumnView(6, 15);

			sheet.addCell(new Label(0, 1, "系統名称：成都市应急指挥调度无线通信网",
					fontFormat_Content));
			sheet.mergeCells(0, 1, 1, 1);
			sheet.addCell(new Label(2, 1, "地点：亚光高新产业园", fontFormat_Content));
			sheet.addCell(new Label(3, 1, "巡检时间", fontFormat_Content));
			sheet.addCell(new Label(4, 1, bean.getCheckDate(),
					fontFormat_Content));
			sheet.addCell(new Label(5, 1, "巡检人", fontFormat_Content));
			sheet.addCell(new Label(6, 1, bean.getCheckPersion(),
					fontFormat_Content));
			// 第3行
			// sheet.mergeCells(6, 2, 7, 2);

			sheet.addCell(new Label(0, 2, "序号", fontFormat_h));
			sheet.addCell(new Label(1, 2, "检查类容", fontFormat_h));
			sheet.addCell(new Label(2, 2, "检查情况", fontFormat_h));
			sheet.addCell(new Label(3, 2, "问题描述", fontFormat_h));
			sheet.mergeCells(3, 2, 4, 2);
			sheet.addCell(new Label(5, 2, "处理情况及遗留问题", fontFormat_Content));
			sheet.mergeCells(5, 2, 6, 2);

			// 第4行
			sheet.addCell(new Label(0, 3, "1", fontFormat_Content));
			sheet.addCell(new Label(1, 3, "机房门窗地面墙壁等是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 3, checkbox(bean.getA1()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 3, bean.getB1(), fontFormat_Content));
			sheet.mergeCells(3, 3, 4, 3);
			sheet.addCell(new Label(5, 3, bean.getC1(), fontFormat_Content));
			sheet.mergeCells(5, 3, 6, 3);
			// 第5行
			sheet.addCell(new Label(0, 4, "2", fontFormat_Content));
			sheet.addCell(new Label(1, 4, "机房照明、电源插座是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 4, checkbox(bean.getA2()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 4, bean.getB2(), fontFormat_Content));
			sheet.mergeCells(3, 4, 4, 4);
			sheet.addCell(new Label(5, 4, bean.getC2(), fontFormat_Content));
			sheet.mergeCells(5, 4, 6, 4);
			// 第6行
			sheet.addCell(new Label(0, 5, "3", fontFormat_Content));
			sheet.addCell(new Label(1, 5, "设备灰尘及滤网是否清洁(传输3500等)", fontFormat_Content));
			sheet.addCell(new Label(2, 5, checkbox(bean.getA3()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 5, bean.getB3(), fontFormat_Content));
			sheet.mergeCells(3, 5, 4, 5);
			sheet.addCell(new Label(5, 5, bean.getC3(), fontFormat_Content));
			sheet.mergeCells(5, 5, 6, 5);
			// 第5行
			sheet.addCell(new Label(0, 6, "4", fontFormat_Content));
			sheet.addCell(new Label(1, 6, "机房是否清洁、是否没有杂物或易燃物品", fontFormat_Content));
			sheet.addCell(new Label(2, 6, checkbox(bean.getA4()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 6, bean.getB4(), fontFormat_Content));
			sheet.mergeCells(3, 6, 4, 6);
			sheet.addCell(new Label(5, 6, bean.getC4(), fontFormat_Content));
			sheet.mergeCells(5, 6, 6, 6);
			// 第5行
			sheet.addCell(new Label(0, 7, "5", fontFormat_Content));
			sheet.addCell(new Label(1, 7, "消防设备是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 7, checkbox(bean.getA5()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 7, bean.getB5(), fontFormat_Content));
			sheet.mergeCells(3, 7, 4, 7);
			sheet.addCell(new Label(5, 7, bean.getC5(), fontFormat_Content));
			sheet.mergeCells(5, 7, 6, 7);
			// 第5行
			sheet.addCell(new Label(0, 8, "6", fontFormat_Content));
			sheet.addCell(new Label(1, 8, "UPS是否正常（断电测试）", fontFormat_Content));
			sheet.addCell(new Label(2, 8, checkbox(bean.getA6()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 8, bean.getB6(), fontFormat_Content));
			sheet.mergeCells(3, 8, 4, 8);
			sheet.addCell(new Label(5, 8, bean.getC6(), fontFormat_Content));
			sheet.mergeCells(5, 8, 6, 8);
			// 第5行
			sheet.addCell(new Label(0, 9, "7", fontFormat_Content));
			sheet.addCell(new Label(1, 9, "蓄电池是否正常（发电测试）", fontFormat_Content));
			sheet.addCell(new Label(2, 9, checkbox(bean.getA7()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 9, bean.getB7(), fontFormat_Content));
			sheet.mergeCells(3, 9, 4, 9);
			sheet.addCell(new Label(5, 9, bean.getC7(), fontFormat_Content));
			sheet.mergeCells(5, 9, 6, 9);
			// 第5行
			sheet.addCell(new Label(0, 10, "8", fontFormat_Content));
			sheet.addCell(new Label(1, 10, "发电机是否正常（包括汽油发电机、柴油发电机）", fontFormat_Content));
			sheet.addCell(new Label(2, 10, checkbox(bean.getA8()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 10, bean.getB8(), fontFormat_Content));
			sheet.mergeCells(3, 10, 4, 10);
			sheet.addCell(new Label(5, 10, bean.getC8(), fontFormat_Content));
			sheet.mergeCells(5, 10, 6, 10);
			// 第5行
			sheet.addCell(new Label(0, 11, "9", fontFormat_Content));
			sheet.addCell(new Label(1, 11, "空调是否正常工作、滤网是否清洁", fontFormat_Content));
			sheet.addCell(new Label(2, 11, checkbox(bean.getA9()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 11, bean.getB9(), fontFormat_Content));
			sheet.mergeCells(3, 11, 4, 11);
			sheet.addCell(new Label(5, 11, bean.getC9(), fontFormat_Content));
			sheet.mergeCells(5, 11, 6, 11);
			// 第5行
			sheet.addCell(new Label(0, 12, "10", fontFormat_Content));
			sheet.addCell(new Label(1, 12, "环境监控系统是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 12, checkbox(bean.getA10()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 12, bean.getB10(), fontFormat_Content));
			sheet.mergeCells(3, 12, 4, 12);
			sheet.addCell(new Label(5, 12, bean.getC10(), fontFormat_Content));
			sheet.mergeCells(5, 12, 6, 12);
			// 第5行
			sheet.addCell(new Label(0, 13, "11", fontFormat_Content));
			sheet.addCell(new Label(1, 13, "电源线是否老化", fontFormat_Content));
			sheet.addCell(new Label(2, 13, checkbox(bean.getA11()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 13, bean.getB11(), fontFormat_Content));
			sheet.mergeCells(3, 13, 4, 13);
			sheet.addCell(new Label(5, 13, bean.getC11(), fontFormat_Content));
			sheet.mergeCells(5, 13, 6, 13);
			// 第5行
			sheet.addCell(new Label(0, 14, "12", fontFormat_Content));
			sheet.addCell(new Label(1, 14, "设备标签是否规范完、完整、走线是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 14, checkbox(bean.getA12()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 14, bean.getB12(), fontFormat_Content));
			sheet.mergeCells(3, 14, 4, 14);
			sheet.addCell(new Label(5, 14, bean.getC12(), fontFormat_Content));
			sheet.mergeCells(5, 14, 6, 14);
			// 第5行
			sheet.addCell(new Label(0, 15, "13", fontFormat_Content));
			sheet.addCell(new Label(1, 15, "服务器是否运行正常", fontFormat_Content));
			sheet.addCell(new Label(2, 15, checkbox(bean.getA13()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 15, bean.getB13(), fontFormat_Content));
			sheet.mergeCells(3, 15, 4, 15);
			sheet.addCell(new Label(5, 15, bean.getC13(), fontFormat_Content));
			sheet.mergeCells(5, 15, 6, 15);
			// 第5行
			sheet.addCell(new Label(0, 16, "14", fontFormat_Content));
			sheet.addCell(new Label(1, 16, "设备接地是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 16, checkbox(bean.getA14()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 16, bean.getB14(), fontFormat_Content));
			sheet.mergeCells(3, 16, 4, 16);
			sheet.addCell(new Label(5, 16, bean.getC14(), fontFormat_Content));
			sheet.mergeCells(5, 16, 6, 16);
			// 第5行
			sheet.addCell(new Label(0, 17, "15", fontFormat_Content));
			sheet.addCell(new Label(1,17, "服务器、单板、模块安装是否牢固可靠", fontFormat_Content));
			sheet.addCell(new Label(2, 17, checkbox(bean.getA15()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 17, bean.getB15(), fontFormat_Content));
			sheet.mergeCells(3, 17, 4, 17);
			sheet.addCell(new Label(5, 17, bean.getC15(), fontFormat_Content));
			sheet.mergeCells(5, 17, 6, 17);
			// 第5行
			sheet.addCell(new Label(0, 18, "16", fontFormat_Content));
			sheet.addCell(new Label(1, 18, "楼顶吸盘天线是否加固，接头是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 18, checkbox(bean.getA16()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 18, bean.getB16(), fontFormat_Content));
			sheet.mergeCells(3, 18, 4, 18);
			sheet.addCell(new Label(5, 18, bean.getC16(), fontFormat_Content));
			sheet.mergeCells(5, 18, 6, 18);
			// 第5行
			sheet.addCell(new Label(0, 19, "17", fontFormat_Content));
			sheet.addCell(new Label(1, 19, "服务器数据及配置是否备份", fontFormat_Content));
			sheet.addCell(new Label(2, 19, checkbox(bean.getA17()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 19, bean.getB17(), fontFormat_Content));
			sheet.mergeCells(3, 19, 4, 19);
			sheet.addCell(new Label(5, 19, bean.getC17(), fontFormat_Content));
			sheet.mergeCells(5, 19, 6, 19);

			book.write();
			book.close();
			// DownExcelFile(response, pathname);
			String destDir1=request.getSession().getServletContext().getRealPath("/upload/checksource");
			destDir1=destDir1+"/"+time.split("-")[0]+"/"+time.split("-")[1]+"/3";
			File Path1 = new File(destDir1);
			if (!Path1.exists()) {
				Path1.mkdirs();
			}
			
			String destDir2=request.getSession().getServletContext()
					.getRealPath("/upload/checksource");
			destDir2=destDir2+"/"+time.split("-")[0]+"/"+time.split("-")[1]+"/4";
			File Path2 = new File(destDir2);
			if (!Path2.exists()) {
				Path2.mkdirs();
			}
			File file1 = new File(destDir1+"/定期维护报告-交换中心月维护.xls");
			File file2 = new File(destDir2+"/定期维护报告-交换中心月维护.xls");
			FunUtil.copyFile(file, file1);
			FunUtil.copyFile(file, file2);
			
			//file.delete();
			
			this.success = true;
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

	// 导出调度台巡检记录
	@RequestMapping(value = "/excel_dispatch", method = RequestMethod.POST)
	public void app_dispatch(HttpServletRequest request,
			HttpServletResponse response) {
		String excelData = request.getParameter("excelData");

		InspectionDispatchBean bean = GsonUtil.json2Object(excelData,
				InspectionDispatchBean.class);

		log.info("map-->" + bean.toString());
		try {
			String saveDir = request.getSession().getServletContext()
					.getRealPath("/app");
			String pathname = saveDir + "/调度台巡检表.xls";
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 16, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体

			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义

			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);

			// 设置其他样式

			wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
			wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcf_title.setBackground(Colour.WHITE);// 背景色
			wcf_title.setWrap(false);// 不自动换行

			fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			 fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			WritableSheet sheet = book.createSheet("调度台巡检表", 0);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			// 第1行
			Label title = new Label(0, 0, "调度台巡检表", fontFormat);
			sheet.mergeCells(0, 0, 7, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE, PaperSize.A4, 0.5d, 0.5d);
			sheet.getSettings().setLeftMargin(0.50);
			sheet.getSettings().setRightMargin(0.50);
			sheet.getSettings().setTopMargin(0.5);
			sheet.getSettings().setBottomMargin(0.5);

			// 第2行
			Label label_1_0 = new Label(0, 1, "调度台名称", fontFormat_Content);// 创建单元格
			Label label_1_1 = new Label(1, 1, bean.getDispatchname(),
					fontFormat_Content);// 创建单元格
			Label label_1_2 = new Label(2, 1, "地点", fontFormat_Content);// 创建单元格
			Label label_1_3 = new Label(3, 1, bean.getDispatchplace(),
					fontFormat_Content);// 创建单元格
			Label label_1_4 = new Label(4, 1, "巡检时间", fontFormat_Content);// 创建单元格
			Label label_1_5 = new Label(5, 1, bean.getCommitdate().split(" ")[0],
					fontFormat_Content);// 创建单元格
			Label label_1_6 = new Label(6, 1, "巡检人", fontFormat_Content);// 创建单元格
			Label label_1_7 = new Label(7, 1, bean.getCheckman(),
					fontFormat_Content);// 创建单元格

			sheet.setColumnView(0, 13);
			sheet.setColumnView(1, 28);
			sheet.setColumnView(2, 10);
			sheet.setColumnView(3, 14);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 30);
			sheet.setColumnView(6, 10);
			sheet.setColumnView(7, 10);

			sheet.addCell(label_1_0);
			sheet.addCell(label_1_1);
			sheet.addCell(label_1_2);
			sheet.addCell(label_1_3);
			sheet.addCell(label_1_4);
			sheet.addCell(label_1_5);
			sheet.addCell(label_1_6);
			sheet.addCell(label_1_7);

			// 第3行
			Label label_2_0 = new Label(0, 2, "序号", fontFormat_h);// 创建单元格
			Label label_2_1 = new Label(1, 2, "检查类容", fontFormat_h);// 创建单元格
			Label label_2_2 = new Label(2, 2, "备注", fontFormat_h);// 创建单元格
			sheet.mergeCells(2, 2, 3, 2);
			Label label_2_3 = new Label(4, 2, "检查情况", fontFormat_h);// 创建单元格
			Label label_2_4 = new Label(5, 2, "问题描述", fontFormat_h);// 创建单元格
			Label label_2_5 = new Label(6, 2, "处理情况及遗留问题", fontFormat_h);// 创建单元格
			sheet.mergeCells(6, 2, 7, 2);
			sheet.addCell(label_2_0);
			sheet.addCell(label_2_1);
			sheet.addCell(label_2_2);
			sheet.addCell(label_2_3);
			sheet.addCell(label_2_4);
			sheet.addCell(label_2_5);

			// 第4行
			Label label_3_0 = new Label(0, 3, "1", fontFormat_Content);// 创建单元格
			Label label_3_1 = new Label(1, 3, "调度台安装环境是否完成正常",
					fontFormat_Content);// 创建单元格
			Label label_3_2 = new Label(2, 3, bean.getComment1(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 3, 3, 3);
			Label label_3_3 = new Label(4, 3, checkbox(bean.getD1()),
					fontFormat_Content);// 创建单元格
			Label label_3_4 = new Label(5, 3, bean.getP1(), fontFormat_Content);// 创建单元格
			Label label_3_5 = new Label(6, 3, bean.getR1(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 3, 7, 3);
			sheet.addCell(label_3_0);
			sheet.addCell(label_3_1);
			sheet.addCell(label_3_2);
			sheet.addCell(label_3_3);
			sheet.addCell(label_3_4);
			sheet.addCell(label_3_5);
			// 第5行
			Label label_4_0 = new Label(0, 4, "2", fontFormat_Content);// 创建单元格
			Label label_4_1 = new Label(1, 4, "调度台电源是否正常开启", fontFormat_Content);// 创建单元格
			Label label_4_2 = new Label(2, 4, bean.getComment2(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 4, 3, 4);
			Label label_4_3 = new Label(4, 4, checkbox(bean.getD2()),
					fontFormat_Content);// 创建单元格
			Label label_4_4 = new Label(5, 4, bean.getP2(), fontFormat_Content);// 创建单元格
			Label label_4_5 = new Label(6, 4, bean.getR2(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 4, 7, 4);
			sheet.addCell(label_4_0);
			sheet.addCell(label_4_1);
			sheet.addCell(label_4_2);
			sheet.addCell(label_4_3);
			sheet.addCell(label_4_4);
			sheet.addCell(label_4_5);
			// 第6行
			Label label_5_0 = new Label(0, 5, "3", fontFormat_Content);// 创建单元格
			Label label_5_1 = new Label(1, 5, "调度台是否正常登录", fontFormat_Content);// 创建单元格
			Label label_5_2 = new Label(2, 5, bean.getComment3(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 5, 3, 5);
			Label label_5_3 = new Label(4, 5, checkbox(bean.getD3()),
					fontFormat_Content);// 创建单元格
			Label label_5_4 = new Label(5, 5, bean.getP3(), fontFormat_Content);// 创建单元格
			Label label_5_5 = new Label(6, 5, bean.getR3(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 5, 7, 5);
			sheet.addCell(label_5_0);
			sheet.addCell(label_5_1);
			sheet.addCell(label_5_2);
			sheet.addCell(label_5_3);
			sheet.addCell(label_5_4);
			sheet.addCell(label_5_5);
			// 第7行
			Label label_6_0 = new Label(0, 6, "4", fontFormat_Content);// 创建单元格
			Label label_6_1 = new Label(1, 6, "调度台配置是否正常", fontFormat_Content);// 创建单元格
			Label label_6_2 = new Label(2, 6, bean.getComment4(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 6, 3, 6);
			Label label_6_3 = new Label(4, 6, checkbox(bean.getD4()),
					fontFormat_Content);// 创建单元格
			Label label_6_4 = new Label(5, 6, bean.getP4(), fontFormat_Content);// 创建单元格
			Label label_6_5 = new Label(6, 6, bean.getR4(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 6, 7, 6);
			sheet.addCell(label_6_0);
			sheet.addCell(label_6_1);
			sheet.addCell(label_6_2);
			sheet.addCell(label_6_3);
			sheet.addCell(label_6_4);
			sheet.addCell(label_6_5);
			// 第8行
			Label label_7_0 = new Label(0, 7, "5", fontFormat_Content);// 创建单元格
			Label label_7_1 = new Label(1, 7, "调度台录音是否正常", fontFormat_Content);// 创建单元格
			Label label_7_2 = new Label(2, 7, bean.getComment5(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 7, 3, 7);
			Label label_7_3 = new Label(4, 7, checkbox(bean.getD5()),
					fontFormat_Content);// 创建单元格
			Label label_7_4 = new Label(5, 7, bean.getP5(), fontFormat_Content);// 创建单元格
			Label label_7_5 = new Label(6, 7, bean.getR5(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 7, 7, 7);
			sheet.addCell(label_7_0);
			sheet.addCell(label_7_1);
			sheet.addCell(label_7_2);
			sheet.addCell(label_7_3);
			sheet.addCell(label_7_4);
			sheet.addCell(label_7_5);
			// 第9行
			Label label_8_0 = new Label(0, 8, "6", fontFormat_Content);// 创建单元格
			Label label_8_1 = new Label(1, 8, "语音、短信业务测试是否正常",
					fontFormat_Content);// 创建单元格
			Label label_8_2 = new Label(2, 8, bean.getComment6(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 8, 3, 8);
			Label label_8_3 = new Label(4, 8, checkbox(bean.getD6()),
					fontFormat_Content);// 创建单元格
			Label label_8_4 = new Label(5, 8, bean.getP6(), fontFormat_Content);// 创建单元格
			Label label_8_5 = new Label(6, 8, bean.getR6(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 8, 7, 8);
			sheet.addCell(label_8_0);
			sheet.addCell(label_8_1);
			sheet.addCell(label_8_2);
			sheet.addCell(label_8_3);
			sheet.addCell(label_8_4);
			sheet.addCell(label_8_5);
			// 第10行
			Label label_9_0 = new Label(0, 9, "7", fontFormat_Content);// 创建单元格
			Label label_9_1 = new Label(1, 9, "调度业务测试是否正常", fontFormat_Content);// 创建单元格
			Label label_9_2 = new Label(2, 9, bean.getComment7(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 9, 3, 9);
			Label label_9_3 = new Label(4, 9, checkbox(bean.getD7()),
					fontFormat_Content);// 创建单元格
			Label label_9_4 = new Label(5, 9, bean.getP7(), fontFormat_Content);// 创建单元格
			Label label_9_5 = new Label(6, 9, bean.getR7(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 9, 7, 9);
			sheet.addCell(label_9_0);
			sheet.addCell(label_9_1);
			sheet.addCell(label_9_2);
			sheet.addCell(label_9_3);
			sheet.addCell(label_9_4);
			sheet.addCell(label_9_5);
			// 第11行
			Label label_10_0 = new Label(0, 10, "8", fontFormat_Content);// 创建单元格
			Label label_10_1 = new Label(1, 10, "环境温度是否工作正常",
					fontFormat_Content);// 创建单元格
			Label label_10_2 = new Label(2, 10, bean.getComment8(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 10, 3, 10);
			Label label_10_3 = new Label(4, 10, checkbox(bean.getD8()),
					fontFormat_Content);// 创建单元格
			Label label_10_4 = new Label(5, 10, bean.getP8(),
					fontFormat_Content);// 创建单元格
			Label label_10_5 = new Label(6, 10, bean.getR8(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 10, 7, 10);
			sheet.addCell(label_10_0);
			sheet.addCell(label_10_1);
			sheet.addCell(label_10_2);
			sheet.addCell(label_10_3);
			sheet.addCell(label_10_4);
			sheet.addCell(label_10_5);

			book.write();
			book.close();
			log.info("移动基站巡检表");
			// DownExcelFile(response, pathname);
			this.success = true;
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

	public void excel_sbs(InspectionSbsBean bean, WritableSheet sheet,
			WritableCellFormat fontFormat, WritableCellFormat fontFormat_h,
			WritableCellFormat fontFormat_Content)
			throws RowsExceededException, WriteException {

		// 第1行
		Label title = new Label(0, 0, "800M基站巡检作业表", fontFormat);
		sheet.mergeCells(0, 0, 7, 0);
		sheet.addCell(title);
		sheet.setRowView(0, 600); // 设置行高
		
		

		// 第2行
		sheet.setColumnView(0, 10);
		sheet.setColumnView(1, 15);
		sheet.setColumnView(2, 10);
		sheet.setColumnView(3, 10);
		sheet.setColumnView(4, 15);
		sheet.setColumnView(5, 25);
		sheet.setColumnView(6, 22);
		sheet.setColumnView(7, 30);
		
		

		StringBuilder str = new StringBuilder();
		str.append("基站名称: ");
		str.append(bean.getBsname());
		str.append("   基站ID: ");
		str.append(bean.getBsid()>2000?"JY"+String.valueOf(bean.getBsid()).substring(2):bean.getBsid());
		str.append("   基站级别: ");
		str.append(bean.getLevel());
		str.append("   巡检人： ");
		str.append(bean.getCheckman());

		sheet.addCell(new Label(0, 1, "建设周期", fontFormat_Content));
		sheet.addCell(new Label(1, 1, bean.getPeriod(), fontFormat_Content));
		sheet.addCell(new Label(2, 1, str.toString(), fontFormat_Content));
		sheet.mergeCells(2, 1, 7, 1);
		// 第3行
		String hometype = bean.getHometype();
		String time = "";
		if (hometype.equals("移动") || hometype.equals("电信")
				|| hometype.equals("铁塔")) {
			hometype = "运营商机房";
		} else if (hometype.equals("自建")) {
			hometype = "自建机房";
		} else if (hometype.equals("租赁")) {
			hometype = "租赁机房";
		}
		if (bean.getCommitdate() != null && bean.getCommitdate() != "") {
			time = bean.getCommitdate().split(" ")[0].split("-")[0] + "年";
			time += bean.getCommitdate().split(" ")[0].split("-")[1] + "月";
			time += bean.getCommitdate().split(" ")[0].split("-")[2] + "日";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("机房类型:");
		sb.append(checkbox(bean.getHometype()));
		sb.append("          巡检时间:");
		sb.append(time);
		sb.append("           自能电表:");
		sb.append(bean.getAmmeternumber());

		Label label_2_0 = new Label(0, 2, sb.toString(), fontFormat_Content);// 创建单元格
		/*
		 * Label label_2_1 = new Label(1, 2,
		 * checkbox(bean.getHometype()),fontFormat_Content);// 创建单元格 Label
		 * label_2_2 = new Label(4, 2, "巡检时间", fontFormat_Content);// 创建单元格
		 * Label label_2_3 = new Label(5, 2,
		 * bean.getCommitdate(),fontFormat_Content);// 创建单元格
		 * 
		 * Label label_2_4 = new Label(6, 2, "自能电表", fontFormat_Content);//
		 * 创建单元格 Label label_2_5 = new Label(7, 2, bean.getAmmeternumber(),
		 * fontFormat_Content);// 创建单元格
		 */sheet.addCell(label_2_0);
		sheet.mergeCells(0, 2, 7, 2);
		/*
		 * sheet.addCell(label_2_1); sheet.addCell(label_2_2);
		 * sheet.addCell(label_2_3); sheet.addCell(label_2_4);
		 * sheet.addCell(label_2_5);
		 */
		// 第4行
		Label label_3_0 = new Label(0, 3, "巡检项目", fontFormat_h);// 创建单元格
		Label label_3_1 = new Label(1, 3, "具体项目", fontFormat_h);// 创建单元格
		Label label_3_2 = new Label(2, 3, "具体操作", fontFormat_h);// 创建单元格
		sheet.mergeCells(2, 3, 5, 3);
		Label label_3_3 = new Label(6, 3, "执行情况", fontFormat_h);// 创建单元格
		Label label_3_4 = new Label(7, 3, "备注", fontFormat_h);// 创建单元格
		sheet.addCell(label_3_0);
		sheet.addCell(label_3_1);
		sheet.addCell(label_3_2);
		sheet.addCell(label_3_3);
		sheet.addCell(label_3_4);

		// 第4-5行
		Label label_4_0 = new Label(0, 4, "天面", fontFormat_Content);// 创建单元格
		sheet.mergeCells(0, 4, 0, 5);
		Label label_4_1 = new Label(1, 4, "防倾斜", fontFormat_Content);// 创建单元格
		Label label_4_2 = new Label(2, 4, "观测铁塔、抱杆、天线是否有明显倾斜",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 4, 5, 4);

		sheet.addCell(label_4_0);
		sheet.addCell(label_4_1);
		sheet.addCell(label_4_2);
		sheet.addCell(new Label(6, 4, checkbox(bean.getD1()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 4, bean.getC1(), fontFormat_Content));

		Label label_5_1 = new Label(1, 5, "防雷情况", fontFormat_Content);// 创建单元格
		Label label_5_2 = new Label(2, 5,
				"避雷针是否正常，接地线焊点是否锈蚀，天线支架、走线架是否接地，馈线是否三点接地", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 5, 5, 5);

		sheet.addCell(label_5_1);
		sheet.addCell(label_5_2);
		sheet.addCell(new Label(6, 5, checkbox(bean.getD2()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 5, bean.getC2(), fontFormat_Content));

		// 第6行
		Label label_6_0 = new Label(0, 6, "防雷情况", fontFormat_Content);// 创建单元格
		sheet.mergeCells(0, 6, 0, 12);
		Label label_6_1 = new Label(1, 6, "机房安全", fontFormat_Content);// 创建单元格
		sheet.mergeCells(1, 6, 1, 9);
		Label label_6_2 = new Label(2, 6, "机房有无灭火设备", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 6, 5, 6);

		sheet.addCell(label_6_0);
		sheet.addCell(label_6_1);
		sheet.addCell(label_6_2);
		sheet.addCell(new Label(6, 6, checkbox(bean.getD3()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 6, bean.getC3(), fontFormat_Content));
		// 第7行
		Label label_7_2 = new Label(2, 7, "门、窗、锁是否完好，密封是否严实",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 7, 5, 7);

		sheet.addCell(label_7_2);
		sheet.addCell(new Label(6, 7, checkbox(bean.getD4()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 7, bean.getC4(), fontFormat_Content));
		// 第8行
		Label label_8_2 = new Label(2, 8, "门窗、馈线孔等缝隙密封是否严实", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 8, 5, 8);

		sheet.addCell(label_8_2);
		sheet.addCell(new Label(6, 8, checkbox(bean.getD5()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 8, bean.getC5(), fontFormat_Content));
		// 第9行
		Label label_9_2 = new Label(2, 9, "机房走线架及室内、室外铜牌安装是否规范",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 9, 5, 9);

		sheet.addCell(label_9_2);
		sheet.addCell(new Label(6, 9, checkbox(bean.getD6()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 9, bean.getC6(), fontFormat_Content));
		// 第10行

		Label label_10_1 = new Label(1, 10, "机房温度", fontFormat_Content);// 创建单元格
		Label label_10_2 = new Label(2, 10, "空调制冷效果、温度设置及运行状态是否正常",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 10, 5, 10);

		sheet.addCell(label_10_1);
		sheet.addCell(label_10_2);
		sheet.addCell(new Label(6, 10, checkbox(bean.getD7()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 10, bean.getC7(), fontFormat_Content));
		// 第11行
		Label label_11_1 = new Label(1, 11, "积水情况", fontFormat_Content);// 创建单元格
		sheet.mergeCells(1, 11, 1, 12);
		Label label_11_2 = new Label(2, 11, "空调有无漏水情况", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 11, 5, 11);

		sheet.addCell(label_11_1);
		sheet.addCell(label_11_2);
		sheet.addCell(new Label(6, 11, checkbox(bean.getD8()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 11, bean.getC8(), fontFormat_Content));
		// 第12行
		Label label_12_2 = new Label(2, 12, "机房有无裂缝、渗水、漏水等情况",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 12, 5, 12);

		sheet.addCell(label_12_2);
		sheet.addCell(new Label(6, 12, checkbox(bean.getD9()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 12, bean.getC9(), fontFormat_Content));
		// 第13行
		Label label_13_0 = new Label(0, 13, "电源配套", fontFormat_Content);// 创建单元格
		sheet.mergeCells(0, 13, 0, 19);
		Label label_13_1 = new Label(1, 13, "油机", fontFormat_Content);// 创建单元格
		Label label_13_2 = new Label(2, 13, "油机是否能正常运行、油机及油机线缆有无安全隐患",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 13, 5, 13);

		sheet.addCell(label_13_0);
		sheet.addCell(label_13_1);
		sheet.addCell(label_13_2);
		sheet.addCell(new Label(6, 13, checkbox(bean.getD10()),
				fontFormat_Content));
		sheet.mergeCells(6, 13, 6, 13);
		sheet.addCell(new Label(7, 13, bean.getC10(), fontFormat_Content));
		sheet.mergeCells(7, 13, 7, 13);
		// 第14行
		/*
		 * Label label_14_2 = new Label(2, 14, "", fontFormat_Content);// 创建单元格
		 * sheet.mergeCells(2, 14, 5, 14);
		 * 
		 * 
		 * sheet.addCell(label_14_2); sheet.addCell(new Label(6, 14, ""));
		 * sheet.addCell(new Label(7, 14, ""));
		 */
		// 第15行
		Label label_15_1 = new Label(1, 14, "后备电源", fontFormat_Content);// 创建单元格
		sheet.mergeCells(1, 14, 1, 16);
		Label label_15_2 = new Label(2, 14, "电源线连接是否正常，有无松动、老化、发热等情况",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 14, 5, 14);

		sheet.addCell(label_15_1);
		sheet.addCell(label_15_2);
		sheet.addCell(new Label(6, 14, checkbox(bean.getD11()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 14, bean.getC11(), fontFormat_Content));
		// 第16行

		Label label_16_2 = new Label(2, 15, "电池壳体有无渗漏和变形，极柱、安全阀周围是否有酸雾酸液逸出",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 15, 5, 15);

		sheet.addCell(label_16_2);
		sheet.addCell(new Label(6, 15, checkbox(bean.getD12()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 15, bean.getC12(), fontFormat_Content));
		// 第17行
		Label label_17_2 = new Label(2, 16, "查看EPS/UPS/逆变器是否正常工作",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 16, 5, 16);

		sheet.addCell(label_17_2);
		sheet.addCell(new Label(6, 16, checkbox(bean.getD13()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 16, bean.getC13(), fontFormat_Content));

		// 第18行
		Label label_18_1 = new Label(1, 17, "配电箱", fontFormat_Content);// 创建单元格
		Label label_18_2 = new Label(2, 17, "交流配电柜电压是否符合供电需求，空开有无跳闸隐患",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 17, 5, 17);

		sheet.addCell(label_18_1);
		sheet.addCell(label_18_2);
		sheet.addCell(new Label(6, 17, checkbox(bean.getD14()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 17, bean.getC14(), fontFormat_Content));
		// 第19行
		Label label_19_1 = new Label(1, 18, "照明", fontFormat_Content);// 创建单元格
		/* sheet.mergeCells(1,19,1,9); */
		Label label_19_2 = new Label(2, 18, "机房照明设施是否完好", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 18, 5, 18);

		sheet.addCell(label_19_1);
		sheet.addCell(label_19_2);
		sheet.addCell(new Label(6, 18, checkbox(bean.getD15()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 18, bean.getC15(), fontFormat_Content));
		// 第20行
		Label label_20_1 = new Label(1, 19, "插座", fontFormat_Content);// 创建单元格
		Label label_20_2 = new Label(2, 19, "机房插座是否有电", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 19, 5, 19);

		sheet.addCell(label_20_1);
		sheet.addCell(label_20_2);
		sheet.addCell(new Label(6, 19, checkbox(bean.getD16()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 19, bean.getC16(), fontFormat_Content));
		// 第21行
		Label label_21_0 = new Label(0, 20, "主体设备", fontFormat_Content);// 创建单元格
		sheet.mergeCells(0, 20, 0, 24);
		Label label_21_1 = new Label(1, 20, "单板状态", fontFormat_Content);// 创建单元格
		Label label_21_2 = new Label(2, 20, "检查各单板运行状态、供电情况是否正常，设备是否有明显告警",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 20, 5, 20);

		sheet.addCell(label_21_0);
		sheet.addCell(label_21_1);
		sheet.addCell(label_21_2);
		sheet.addCell(new Label(6, 20, checkbox(bean.getD17()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 20, bean.getC17(), fontFormat_Content));
		// 第22行
		Label label_22_1 = new Label(1, 21, "告警确认", fontFormat_Content);// 创建单元格
		Label label_22_2 = new Label(2, 21, "通过与监控后台联系确认设备有无告警",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 21, 5, 21);

		sheet.addCell(label_22_1);
		sheet.addCell(label_22_2);
		sheet.addCell(new Label(6, 21, checkbox(bean.getD18()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 21, bean.getC18(), fontFormat_Content));
		// 第23行
		Label label_23_1 = new Label(1, 22, "连线状态", fontFormat_Content);// 创建单元格
		Label label_23_2 = new Label(2, 22,
				"电源/信号/射频/地线/网线是否分开，机架内各连线是否接触良好并正确无误", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 22, 5, 22);

		sheet.addCell(label_23_1);
		sheet.addCell(label_23_2);
		sheet.addCell(new Label(6, 22, checkbox(bean.getD19()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 22, bean.getC19(), fontFormat_Content));
		// 第24行
		/*
		 * Label label_24_0 = new Label(0, 24, "动环监控", fontFormat_Content);//
		 * 创建单元格 sheet.mergeCells(0,24,0,26);
		 */
		Label label_24_1 = new Label(1, 23, "设备加固", fontFormat_Content);// 创建单元格
		Label label_24_2 = new Label(2, 23, "设备安装是否牢固", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 23, 5, 23);

		sheet.addCell(label_24_1);
		sheet.addCell(label_24_2);
		sheet.addCell(new Label(6, 23, checkbox(bean.getD20()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 23, bean.getC20(), fontFormat_Content));
		// 第25行
		Label label_25_1 = new Label(1, 24, "设备温度", fontFormat_Content);// 创建单元格
		Label label_25_2 = new Label(2, 24, "设备是否有高温、异味", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 24, 5, 24);

		sheet.addCell(label_25_1);
		sheet.addCell(label_25_2);
		sheet.addCell(new Label(6, 24, checkbox(bean.getD21()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 24, bean.getC21(), fontFormat_Content));
		// 第26行
		Label label_26_0 = new Label(0, 25, "动环监控", fontFormat_Content);// 创建单元格
		sheet.mergeCells(0, 25, 0, 28);
		Label label_26_1 = new Label(1, 25, "环境监测", fontFormat_Content);// 创建单元格
		Label label_26_2 = new Label(2, 25, "查看温湿度、漏水是否正常(需值班人员配合完成)",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 25, 5, 25);

		sheet.addCell(label_26_0);
		sheet.addCell(label_26_1);
		sheet.addCell(label_26_2);
		sheet.addCell(new Label(6, 25, checkbox(bean.getD22()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 25, bean.getC22(), fontFormat_Content));
		// 第27行
		Label label_27_1 = new Label(1, 26, "安全监测", fontFormat_Content);// 创建单元格
		Label label_27_2 = new Label(2, 26, "门磁、红外传感器、烟感、摄像头是否正常(需值班人员配合完成)",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 26, 5, 26);

		sheet.addCell(label_27_1);
		sheet.addCell(label_27_2);
		sheet.addCell(new Label(6, 26, checkbox(bean.getD23()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 26, bean.getC23(), fontFormat_Content));
		// 第28行
		Label label_28_1 = new Label(1, 27, "交直流电流电压", fontFormat_Content);// 创建单元格
		Label label_28_2 = new Label(2, 27, "查看交直流是否正常(需值班人员配合完成)",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 27, 5, 27);

		sheet.addCell(label_28_1);
		sheet.addCell(label_28_2);
		sheet.addCell(new Label(6, 27, checkbox(bean.getD24()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 27, bean.getC24(), fontFormat_Content));
		// 第29行
		Label label_29_1 = new Label(1, 28, "EPS/UPS", fontFormat_Content);// 创建单元格
		Label label_29_2 = new Label(2, 28, "查看EPS/UPS输入、输出及电池组状态(需值班人员配合完成)",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 28, 5, 28);

		sheet.addCell(label_29_1);
		sheet.addCell(label_29_2);
		sheet.addCell(new Label(6, 28, checkbox(bean.getD25()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 28, bean.getC25(), fontFormat_Content));
		// 第30行
		Label label_30_0 = new Label(0, 29, "清洁", fontFormat_Content);// 创建单元格
		sheet.mergeCells(0, 29, 0, 30);
		Label label_30_1 = new Label(1, 29, "机房清洁", fontFormat_Content);// 创建单元格
		Label label_30_2 = new Label(2, 29, "清洁机房内门窗、地面、墙面卫生",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 29, 5, 29);

		sheet.addCell(label_30_0);
		sheet.addCell(label_30_1);
		sheet.addCell(label_30_2);
		sheet.addCell(new Label(6, 29, checkbox(bean.getD26()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 29, bean.getC26(), fontFormat_Content));
		// 第31行
		Label label_31_1 = new Label(1, 30, "设备除尘", fontFormat_Content);// 创建单元格
		Label label_31_2 = new Label(2, 30, "清洁设备表面、内部、主体设备滤网",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 30, 5, 30);

		sheet.addCell(label_31_1);
		sheet.addCell(label_31_2);
		sheet.addCell(new Label(6, 30, checkbox(bean.getD27()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 30, bean.getC27(), fontFormat_Content));
		// 第32行
		Label label_32_0 = new Label(0, 31, "功能", fontFormat_Content);// 创建单元格
		Label label_32_1 = new Label(1, 31, "呼叫功能", fontFormat_Content);// 创建单元格
		Label label_32_2 = new Label(2, 31, "沿途呼叫测试", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 31, 5, 31);

		sheet.addCell(label_32_0);
		sheet.addCell(label_32_1);
		sheet.addCell(label_32_2);
		sheet.addCell(new Label(6, 31, checkbox(bean.getD28()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 31, bean.getC28(), fontFormat_Content));
		// 第33行
		Label label_33_0 = new Label(0, 32, "遗留问题", fontFormat_Content);// 创建单元格
		Label label_33_1 = new Label(1, 32, bean.getRemainwork(),
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(1, 32, 7, 33);
		sheet.mergeCells(0, 32, 0, 33);
		sheet.addCell(label_33_0);
		sheet.addCell(label_33_1);
	
		sheet.setRowView(5, 514);
		sheet.setRowView(22, 514);
	

	}

	@RequestMapping(value = "/excel_batch", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> excel_batch(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String time = request.getParameter("time");
		String data = request.getParameter("data");
		int type=Integer.parseInt(request.getParameter("type"));
		String saveDir = request.getSession().getServletContext().getRealPath("/app");
		HashMap<String, Object> rs=new HashMap<String, Object>();
		// 设置下载的压缩包名
				String zipName = time + "-基站月度巡检.zip";
				StringBuilder sb = new StringBuilder();
				ArrayList<String> fileNames = new ArrayList<String>();
				if(type==1){
					zipName = time + "-基站月度巡检.zip";
					List<InspectionSbsBean> sbsList=GsonUtil.json2Object(data, new TypeToken<List<InspectionSbsBean>>(){}.getType());
					rs=app_bs(sbsList,saveDir,time,zipName);
				}else if(type==2){
					zipName = time + "-直放站月度巡检.zip";
					List<InspectionVerticalBean> sbsList=GsonUtil.json2Object(data, new TypeToken<List<InspectionVerticalBean>>(){}.getType());					
					String pathname = "";
					for (InspectionVerticalBean bean : sbsList) {
						String fileName = time + "-直放站-[" + bean.getName()+"]巡检表.xls";
						sb.append(fileName);
						sb.append(",");
						fileNames.add(fileName);

						pathname = saveDir + "/" + fileName;
						File Path = new File(saveDir);
						if (!Path.exists()) {
							Path.mkdirs();
						}
						File file = new File(pathname);
						app.excel_vertical(file, bean);
						
					}
					rs.put("success", true);
					rs.put("fileName",sb.deleteCharAt(sb.length() - 1).toString());
					rs.put("zipName", zipName);	
									
				}else if(type==3){
					zipName = time + "-室内覆盖站月度巡检.zip";
					List<InspectionRoomBean> list=GsonUtil.json2Object(data, new TypeToken<List<InspectionRoomBean>>(){}.getType());					
					String pathname = "";
					for (InspectionRoomBean bean : list) {
						String fileName = time + "-室内覆盖站-[" + bean.getName()+"]巡检表.xls";
						sb.append(fileName);
						sb.append(",");
						fileNames.add(fileName);

						pathname = saveDir + "/" + fileName;
						File Path = new File(saveDir);
						if (!Path.exists()) {
							Path.mkdirs();
						}
						File file = new File(pathname);
						app.excel_room(file, bean);
					}
					rs.put("success", true);
					rs.put("fileName",sb.deleteCharAt(sb.length() - 1).toString());
					rs.put("zipName", zipName);	
				}else if(type==4){
					zipName = time + "-卫星通信车载便携站月度巡检.zip";
					List<InspectionStarBean> list=GsonUtil.json2Object(data, new TypeToken<List<InspectionStarBean>>(){}.getType());					
					String pathname = "";
					for (InspectionStarBean bean : list) {
						String fileName = time + "-卫星通信车载便携站-[" + bean.getName()+"]巡检表.xls";
						sb.append(fileName);
						sb.append(",");
						fileNames.add(fileName);

						pathname = saveDir + "/" + fileName;
						File Path = new File(saveDir);
						if (!Path.exists()) {
							Path.mkdirs();
						}
						File file = new File(pathname);
						//app.excel_net(file, bean);
						app.excel_star(file, bean);
					}
					rs.put("success", true);
					rs.put("fileName",sb.deleteCharAt(sb.length() - 1).toString());
					rs.put("zipName", zipName);	
				}else if(type==5){
					zipName = time + "-网管月度巡检.zip";
					List<InspectionNetBean> list=GsonUtil.json2Object(data, new TypeToken<List<InspectionNetBean>>(){}.getType());					
					String pathname = "";
					for (InspectionNetBean bean : list) {
						String fileName = time + "-网管-[" + bean.getManagername()+"]巡检表.xls";
						sb.append(fileName);
						sb.append(",");
						fileNames.add(fileName);

						pathname = saveDir + "/" + fileName;
						File Path = new File(saveDir);
						if (!Path.exists()) {
							Path.mkdirs();
						}
						File file = new File(pathname);
						app.excel_net(file, bean);
					}
					rs.put("success", true);
					rs.put("fileName",sb.deleteCharAt(sb.length() - 1).toString());
					rs.put("zipName", zipName);	
				}else if(type==6){
					zipName = time + "-调度台月度巡检.zip";
					List<InspectionDispatchBean> list=GsonUtil.json2Object(data, new TypeToken<List<InspectionDispatchBean>>(){}.getType());					
					String pathname = "";
					for (InspectionDispatchBean bean : list) {
						String fileName = time + "-调度台-[" + bean.getDispatchname()+"]巡检表.xls";
						sb.append(fileName);
						sb.append(",");
						fileNames.add(fileName);

						pathname = saveDir + "/" + fileName;
						File Path = new File(saveDir);
						if (!Path.exists()) {
							Path.mkdirs();
						}
						File file = new File(pathname);
						app.excel_dispatch(file, bean);
					}
					rs.put("success", true);
					rs.put("fileName",sb.deleteCharAt(sb.length() - 1).toString());
					rs.put("zipName", zipName);	
				}else if(type==7){
					zipName = time + "-交换中心月度巡检.zip";
					List<InspectionMscBean> list=GsonUtil.json2Object(data, new TypeToken<List<InspectionMscBean>>(){}.getType());					
					String pathname = "";
					for (InspectionMscBean bean : list) {
						String fileName = time + "-交换中心-[" + bean.getName()+"]巡检表.xls";
						sb.append(fileName);
						sb.append(",");
						fileNames.add(fileName);

						pathname = saveDir + "/" + fileName;
						File Path = new File(saveDir);
						if (!Path.exists()) {
							Path.mkdirs();
						}
						File file = new File(pathname);
						app.excel_msc(file, bean);
					}
					rs.put("success", true);
					rs.put("fileName",sb.deleteCharAt(sb.length() - 1).toString());
					rs.put("zipName", zipName);	
		     }	
				
				
				return rs;
				
			
	
	}
	public HashMap<String, Object> app_bs(List<InspectionSbsBean> sbsList,String saveDir,String time,String zipName)
			throws Exception {
		String pathname = "";
		
		ArrayList<String> fileNames = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		try {

			for (InspectionSbsBean bean : sbsList) {
				String fileName=(bean.getBsid()>2000?"JY"+String.valueOf(bean.getBsid()).substring(2):bean.getBsid())+"-";
				fileName+=bean.getBsname() +"-"+time+"-基站巡检表.xls";
				/*String fileName = time + "-基站-[" + bean.getBsname() + "("
						+ (bean.getBsid()>2000?"JY"+String.valueOf(bean.getBsid()).substring(2):bean.getBsid()) + ")]巡检表.xls";*/
				sb.append(fileName);
				sb.append(",");
				fileNames.add(fileName);

				pathname = saveDir + "/" + fileName;
				File Path = new File(saveDir);
				if (!Path.exists()) {
					Path.mkdirs();
				}
				File file = new File(pathname);
				WritableWorkbook book = Workbook.createWorkbook(file);
				WritableFont font = new WritableFont(
						WritableFont.createFont("微软雅黑"), 15,
						WritableFont.NO_BOLD, false,
						UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
				WritableCellFormat fontFormat = new WritableCellFormat(font);
				fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
				fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
				fontFormat.setWrap(true); // 自动换行
				fontFormat.setBackground(Colour.WHITE);// 背景颜色
				fontFormat.setBorder(Border.ALL, BorderLineStyle.NONE,
						Colour.BLACK);
				fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

				// 设置头部字体格式
				WritableFont font_header = new WritableFont(WritableFont.TIMES,
						11, WritableFont.NO_BOLD, false,
						UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
				// 应用字体
				WritableCellFormat fontFormat_h = new WritableCellFormat(
						font_header);
				// 设置其他样式
				fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
				fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
				fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
				//fontFormat_h.setBackground(Colour.BRIGHT_GREEN);// 背景色
				fontFormat_h.setWrap(false);// 不自动换行

				// 设置主题内容字体格式
				WritableFont font_Content = new WritableFont(
						WritableFont.TIMES, 11, WritableFont.NO_BOLD, false,
						UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
				// 应用字体

				WritableFont wf_title = new WritableFont(WritableFont.ARIAL,
						11, WritableFont.NO_BOLD, false,
						UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK); // 定义格式
																				// 字体
																				// 下划线
																				// 斜体
																				// 粗体
																				// 颜色

				WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义

				WritableCellFormat fontFormat_Content = new WritableCellFormat(
						font_Content);

				// 设置其他样式

				wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
				wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
				wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
				wcf_title.setBackground(Colour.WHITE);// 背景色
				wcf_title.setWrap(false);// 不自动换行

				fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
				fontFormat_Content
						.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
				fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
				fontFormat_Content.setBackground(Colour.WHITE);// 背景色
				fontFormat_Content.setWrap(true);// 自动换行

				WritableSheet sheet = book.createSheet("基站巡检表", 0);
				sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE, PaperSize.A4, 0.5d, 0.5d);
				sheet.getSettings().setLeftMargin(0.50);
				sheet.getSettings().setRightMargin(0.50);
				sheet.getSettings().setTopMargin(0.1);
				sheet.getSettings().setBottomMargin(0.1);
				excel_sbs(bean, sheet, fontFormat, fontFormat_h,
						fontFormat_Content);

				book.write();
				book.close();
				log.info("自建基站巡检表");
			}

			this.success = true;
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("success", success);
			result.put("fileName", sb.deleteCharAt(sb.length() - 1).toString());
			result.put("zipName", zipName);
			result.put("pathName", pathname);	
			return result;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
		
	}

	// 基站故障记录登记表
	@RequestMapping(value = "/excel_bs_one", method = RequestMethod.POST)
	public void app_bs_one(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String time = request.getParameter("time");
		int id = Integer.parseInt(request.getParameter("id"));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", 0);
		map.put("limit", 50000);
		map.put("time", time);
		map.put("id", id);
		// List<InspectionMbsBean> mbsList=AppInspectionServer.mbsinfo(map);
		List<InspectionSbsBean> sbsList = AppInspectionServer.sbsinfo(map);
		String saveDir = request.getSession().getServletContext()
				.getRealPath("/app");
		String pathname = "";
		ArrayList<String> fileNames = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();

		InspectionSbsBean bean = sbsList.get(0);

		try {
			String fileName=(bean.getBsid()>2000?"JY"+String.valueOf(bean.getBsid()).substring(2):bean.getBsid())+"-";
			fileName+=bean.getBsname() +"-"+time+"-基站巡检表.xls";
			/*String fileName = time + "-基站-[" + bean.getBsname() + "("
					+ (bean.getBsid()>2000?"JY"+String.valueOf(bean.getBsid()).substring(2):bean.getBsid()) + ")]巡检表.xls";*/
			sb.append(fileName);
			sb.append(",");
			fileNames.add(fileName);
			pathname = saveDir + "/" + fileName;
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
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体

			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);

		    fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行
			

			WritableSheet sheet = book.createSheet("基站巡检表", 0);
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE, PaperSize.A4, 0.5d, 0.5d);
			sheet.getSettings().setLeftMargin(0.50);
			sheet.getSettings().setRightMargin(0.50);
			sheet.getSettings().setTopMargin(0.1);
			sheet.getSettings().setBottomMargin(0.1);
			/*sheet.getSettings().setBottomMargin(0.5);*/
			/*sheet.getSettings().setLeftMargin(0.1);*/

			excel_sbs(bean, sheet, fontFormat, fontFormat_h, fontFormat_Content);
			book.write();
			book.close();
			log.info("基站巡检表");
			this.success = true;
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("success", success);
			result.put("fileName", sb.deleteCharAt(sb.length() - 1).toString());
			result.put("pathName", pathname);
			response.setContentType("application/json;charset=utf-8");
			String jsonstr = json.Encode(result);
			response.getWriter().write(jsonstr);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	@RequestMapping(value = "/excel_star", method = RequestMethod.POST)
	public void app_star(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String excelData = request.getParameter("excelData");
		String time = request.getParameter("time");
		InspectionStarBean bean=GsonUtil.json2Object(excelData, InspectionStarBean.class);
		String saveDir = request.getSession().getServletContext()
				.getRealPath("/app");
		String pathname = "";
		ArrayList<String> fileNames = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();

		try {
			String fileName = time + "-卫星通信车载便携站-[" + bean.getName()+"]巡检表.xls";
			/*String fileName = time + "-基站-[" + bean.getBsname() + "("
					+ (bean.getBsid()>2000?"JY"+String.valueOf(bean.getBsid()).substring(2):bean.getBsid()) + ")]巡检表.xls";*/
			sb.append(fileName);
			sb.append(",");
			fileNames.add(fileName);
			pathname = saveDir + "/" + fileName;
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);
			AppInspectionExcelController app=new AppInspectionExcelController();
			app.excel_star(file, bean);
			log.info("卫星通信车载便携站");
			this.success = true;
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("success", success);
			result.put("fileName", sb.deleteCharAt(sb.length() - 1).toString());
			result.put("pathName", pathname);
			response.setContentType("application/json;charset=utf-8");
			String jsonstr = json.Encode(result);
			response.getWriter().write(jsonstr);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void excel_mbs(InspectionMbsBean bean, WritableSheet sheet,
			WritableCellFormat fontFormat, WritableCellFormat fontFormat_h,
			WritableCellFormat fontFormat_Content) throws Exception,
			WriteException {

		// 第1行
		Label title = new Label(0, 0, "800M移动基站巡检作业表", fontFormat);
		sheet.mergeCells(0, 0, 7, 0);
		sheet.addCell(title);
		sheet.setRowView(0, 600, false); // 设置行高

		sheet.setColumnView(0, 10);
		sheet.setColumnView(1, 10);
		sheet.setColumnView(2, 10);
		sheet.setColumnView(3, 20);
		sheet.setColumnView(4, 10);
		sheet.setColumnView(5, 40);
		sheet.setColumnView(6, 20);
		sheet.setColumnView(7, 40);

		sheet.addCell(new Label(0, 1, "基站名称", fontFormat_Content));
		sheet.mergeCells(1, 1, 3, 1);
		sheet.addCell(new Label(1, 1, bean.getBsname(), fontFormat_Content));
		sheet.addCell(new Label(4, 1, "基站ID", fontFormat_Content));
		sheet.addCell(new jxl.write.Number(5, 1, bean.getBsid(),
				fontFormat_Content));
		sheet.addCell(new Label(6, 1, "基站级别", fontFormat_Content));
		sheet.addCell(new jxl.write.Number(7, 1, bean.getBslevel(),
				fontFormat_Content));

		// 第3行
		sheet.addCell(new Label(0, 2, "机房类型", fontFormat_Content));
		sheet.addCell(new Label(1, 2, bean.getBstype(), fontFormat_Content));
		sheet.addCell(new Label(2, 2, "巡检时间", fontFormat_Content));
		sheet.addCell(new Label(3, 2, bean.getCommitdate(), fontFormat_Content));
		sheet.mergeCells(3, 2, 5, 2);
		sheet.addCell(new Label(6, 2, "巡检人", fontFormat_Content));
		sheet.addCell(new Label(7, 2, bean.getCheckman(), fontFormat_Content));

		// 第4行
		sheet.addCell(new Label(0, 3, "巡检项目", fontFormat_Content));
		sheet.addCell(new Label(1, 3, "具体项目", fontFormat_Content));
		sheet.addCell(new Label(2, 3, "具体操作", fontFormat_Content));
		sheet.mergeCells(2, 3, 5, 3);
		sheet.addCell(new Label(6, 3, "执行情况", fontFormat_Content));
		sheet.addCell(new Label(7, 3, "备注", fontFormat_Content));

		// 第4-5行
		sheet.addCell(new Label(0, 4, "天面", fontFormat_Content));
		sheet.mergeCells(0, 4, 0, 5);
		sheet.addCell(new Label(1, 4, "防倾斜", fontFormat_Content));
		sheet.addCell(new Label(2, 4, "观测铁塔、抱杆、天线是否有明显倾斜", fontFormat_Content));
		sheet.mergeCells(2, 4, 5, 4);
		sheet.addCell(new Label(6, 4, checkbox(bean.getD1()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 4, bean.getC1(), fontFormat_Content));

		sheet.addCell(new Label(1, 5, "防雷情况", fontFormat_Content));
		sheet.addCell(new Label(2, 5,
				"避雷针是否正常，接地线焊点是否锈蚀，天线支架、走线架是否接地，馈线是否三点接地", fontFormat_Content));
		sheet.mergeCells(2, 5, 5, 5);
		sheet.addCell(new Label(6, 5, checkbox(bean.getD2()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 5, bean.getC2(), fontFormat_Content));

		// 第6行
		sheet.addCell(new Label(0, 6, "防雷情况", fontFormat_Content));
		sheet.mergeCells(0, 6, 0, 12);
		sheet.addCell(new Label(1, 6, "机房安全", fontFormat_Content));
		sheet.mergeCells(1, 6, 1, 9);
		sheet.addCell(new Label(2, 6, "灭火设备是否完好", fontFormat_Content));
		sheet.mergeCells(2, 6, 5, 6);
		sheet.addCell(new Label(6, 6, checkbox(bean.getD3()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 6, bean.getC3(), fontFormat_Content));
		// 第7行
		sheet.addCell(new Label(2, 7, "门、窗、锁是否完好", fontFormat_Content));
		sheet.mergeCells(2, 7, 5, 7);
		sheet.addCell(new Label(6, 7, checkbox(bean.getD4()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 7, bean.getC4(), fontFormat_Content));
		// 第8行
		sheet.addCell(new Label(2, 8, "门窗、馈线孔等缝隙密封是否严实", fontFormat_Content));
		sheet.mergeCells(2, 8, 5, 8);
		sheet.addCell(new Label(6, 8, checkbox(bean.getD5()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 8, bean.getC5(), fontFormat_Content));
		// 第9行
		sheet.addCell(new Label(2, 9, "机房走线架及室内、室外铜牌安装是否规范", fontFormat_Content));
		sheet.mergeCells(2, 9, 5, 9);
		sheet.addCell(new Label(6, 9, checkbox(bean.getD6()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 9, bean.getC6(), fontFormat_Content));
		// 第10行
		sheet.addCell(new Label(1, 10, "机房温度", fontFormat_Content));
		sheet.addCell(new Label(2, 10, "空调制冷效果、温度设置及运行状态是否正常",
				fontFormat_Content));
		sheet.mergeCells(2, 10, 5, 10);
		sheet.addCell(new Label(6, 10, checkbox(bean.getD7()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 10, bean.getC7(), fontFormat_Content));
		// 第11行
		sheet.addCell(new Label(1, 11, "积水情况", fontFormat_Content));
		sheet.mergeCells(1, 11, 1, 12);
		sheet.addCell(new Label(2, 11, "灭火设备是否完好", fontFormat_Content));
		sheet.mergeCells(2, 11, 5, 11);
		sheet.addCell(new Label(6, 11, checkbox(bean.getD8()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 11, bean.getC8(), fontFormat_Content));
		// 第12行
		sheet.addCell(new Label(2, 12, "机房有无裂缝、渗水、漏水等情况", fontFormat_Content));
		sheet.mergeCells(2, 12, 5, 12);
		sheet.addCell(new Label(6, 12, checkbox(bean.getD9()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 12, bean.getC9(), fontFormat_Content));
		// 第13行
		sheet.addCell(new Label(0, 13, "电源配套", fontFormat_Content));
		sheet.mergeCells(0, 13, 0, 18);
		sheet.addCell(new Label(1, 13, "逆变器", fontFormat_Content));
		sheet.addCell(new Label(2, 13, "逆变器运行是否正常，有无挂死隐患", fontFormat_Content));
		sheet.mergeCells(2, 13, 5, 13);
		sheet.addCell(new Label(6, 13, checkbox(bean.getD10()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 13, bean.getC10(), fontFormat_Content));
		// 第14行
		sheet.addCell(new Label(1, 14, "电源线", fontFormat_Content));
		sheet.addCell(new Label(2, 14, "电源线是否老化，连接是否正常，有无短路隐患",
				fontFormat_Content));
		sheet.mergeCells(2, 14, 5, 14);
		sheet.addCell(new Label(6, 14, checkbox(bean.getD11()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 14, bean.getC11(), fontFormat_Content));
		// 第15行
		sheet.addCell(new Label(1, 15, "配电箱", fontFormat_Content));
		sheet.mergeCells(1, 15, 1, 16);
		sheet.addCell(new Label(2, 15, "直流配电柜运行是否正常，空开有无跳闸隐患，电压是否稳定",
				fontFormat_Content));
		sheet.mergeCells(2, 15, 5, 15);
		sheet.addCell(new Label(6, 15, checkbox(bean.getD12()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 15, bean.getC12(), fontFormat_Content));
		// 第16行

		sheet.addCell(new Label(2, 16, "交流配电柜运行是否正常，空开有无跳闸隐患，电压是否稳定",
				fontFormat_Content));
		sheet.mergeCells(2, 16, 5, 16);
		sheet.addCell(new Label(6, 16, checkbox(bean.getD13()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 16, bean.getC13(), fontFormat_Content));
		// 第17行
		sheet.addCell(new Label(1, 17, "照明", fontFormat_Content));
		sheet.addCell(new Label(2, 17, "机房照明设施是否完好", fontFormat_Content));
		sheet.mergeCells(2, 17, 5, 17);
		sheet.addCell(new Label(6, 17, checkbox(bean.getD14()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 17, bean.getC14(), fontFormat_Content));
		// 第18行
		sheet.addCell(new Label(1, 18, "插座", fontFormat_Content));
		sheet.addCell(new Label(2, 18, "机房插座是否有电", fontFormat_Content));
		sheet.mergeCells(2, 18, 5, 18);
		sheet.addCell(new Label(6, 18, checkbox(bean.getD15()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 18, bean.getC15(), fontFormat_Content));
		// 第19行
		sheet.addCell(new Label(0, 19, "主体设备", fontFormat_Content));
		sheet.mergeCells(0, 19, 0, 23);
		sheet.addCell(new Label(1, 19, "单板状态", fontFormat_Content));
		sheet.addCell(new Label(2, 19, "检查各单板运行状态、供电情况是否正常，设备是否有明显告警",
				fontFormat_Content));
		sheet.mergeCells(2, 19, 5, 19);
		sheet.addCell(new Label(6, 19, checkbox(bean.getD16()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 19, bean.getC16(), fontFormat_Content));
		// 第20行
		Label label_20_1 = new Label(1, 20, "告警确认", fontFormat_Content);// 创建单元格
		Label label_20_2 = new Label(2, 20, "通过与监控后台联系确认设备有无告警",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 20, 5, 20);

		sheet.addCell(label_20_1);
		sheet.addCell(label_20_2);
		sheet.addCell(new Label(6, 20, checkbox(bean.getD17()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 20, bean.getC17(), fontFormat_Content));
		// 第21行
		Label label_21_1 = new Label(1, 21, "连线状态", fontFormat_Content);// 创建单元格
		Label label_21_2 = new Label(2, 21,
				"电源/信号/射频/地线/网线是否分开，机架内各连线是否接触良好并正确无误", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 21, 5, 21);

		sheet.addCell(label_21_1);
		sheet.addCell(label_21_2);
		sheet.addCell(new Label(6, 21, checkbox(bean.getD18()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 21, bean.getC18(), fontFormat_Content));
		// 第22行
		Label label_22_1 = new Label(1, 22, "设备加固", fontFormat_Content);// 创建单元格
		Label label_22_2 = new Label(2, 22, "设备安装是否牢固", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 22, 5, 22);
		sheet.addCell(label_22_1);
		sheet.addCell(label_22_2);
		sheet.addCell(new Label(6, 22, checkbox(bean.getD19()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 22, bean.getC19(), fontFormat_Content));
		// 第23行
		Label label_23_1 = new Label(1, 23, "设备温度", fontFormat_Content);// 创建单元格
		Label label_23_2 = new Label(2, 23, "设备是否有高温、异味", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 23, 5, 23);

		sheet.addCell(label_23_1);
		sheet.addCell(label_23_2);
		sheet.addCell(new Label(6, 23, checkbox(bean.getD20()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 23, bean.getC20(), fontFormat_Content));
		// 第24行
		Label label_24_0 = new Label(0, 24, "动环监控", fontFormat_Content);// 创建单元格
		sheet.mergeCells(0, 24, 0, 26);
		Label label_24_1 = new Label(1, 24, "环境监测", fontFormat_Content);// 创建单元格
		Label label_24_2 = new Label(2, 24, "查看温湿度、漏水是否正常(需值班人员配合完成)",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 24, 5, 24);

		sheet.addCell(label_24_0);
		sheet.addCell(label_24_1);
		sheet.addCell(label_24_2);
		sheet.addCell(new Label(6, 24, checkbox(bean.getD21()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 24, bean.getC21(), fontFormat_Content));
		// 第25行
		Label label_25_1 = new Label(1, 25, "安全监测", fontFormat_Content);// 创建单元格
		Label label_25_2 = new Label(2, 25, "查看门磁、烟感、摄像头是否正常(需值班人员配合完成)",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 25, 5, 25);

		sheet.addCell(label_25_1);
		sheet.addCell(label_25_2);
		sheet.addCell(new Label(6, 25, checkbox(bean.getD22()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 25, bean.getC22(), fontFormat_Content));
		// 第26行
		Label label_26_1 = new Label(1, 26, "交直流电流电压", fontFormat_Content);// 创建单元格
		Label label_26_2 = new Label(2, 26, "查看交直流是否正常(需值班人员配合完成)",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 26, 5, 26);

		sheet.addCell(label_26_1);
		sheet.addCell(label_26_2);
		sheet.addCell(new Label(6, 26, checkbox(bean.getD23()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 26, bean.getC23(), fontFormat_Content));
		// 第27行
		Label label_27_0 = new Label(0, 27, "清洁", fontFormat_Content);// 创建单元格
		sheet.mergeCells(0, 27, 0, 28);
		Label label_27_1 = new Label(1, 27, "机房清洁", fontFormat_Content);// 创建单元格
		Label label_27_2 = new Label(2, 27, "清洁机房内门窗、地面、墙面卫生",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 27, 5, 27);

		sheet.addCell(label_27_0);
		sheet.addCell(label_27_1);
		sheet.addCell(label_27_2);
		sheet.addCell(new Label(6, 27, checkbox(bean.getD24()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 27, bean.getC24(), fontFormat_Content));
		// 第28行
		Label label_28_1 = new Label(1, 28, "设备除尘", fontFormat_Content);// 创建单元格
		Label label_28_2 = new Label(2, 28, "清洁设备表面、内部、主体设备滤网",
				fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 28, 5, 28);

		sheet.addCell(label_28_1);
		sheet.addCell(label_28_2);
		sheet.addCell(new Label(6, 28, checkbox(bean.getD25()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 28, bean.getC25(), fontFormat_Content));
		// 第29行
		Label label_29_0 = new Label(0, 29, "功能", fontFormat_Content);// 创建单元格
		Label label_29_1 = new Label(1, 29, "呼叫功能", fontFormat_Content);// 创建单元格
		Label label_29_2 = new Label(2, 29, "沿途呼叫测试", fontFormat_Content);// 创建单元格
		sheet.mergeCells(2, 29, 5, 29);

		sheet.addCell(label_29_0);
		sheet.addCell(label_29_1);
		sheet.addCell(label_29_2);
		sheet.addCell(new Label(6, 29, checkbox(bean.getD26()),
				fontFormat_Content));
		sheet.addCell(new Label(7, 29, bean.getC26(), fontFormat_Content));
		// 第30行
		Label label_30_0 = new Label(0, 30, "遗留问题", fontFormat_Content);// 创建单元格
		sheet.mergeCells(0, 30, 0, 31);
		sheet.mergeCells(1, 30, 7, 31);
		sheet.addCell(label_30_0);
		sheet.addCell(new Label(1, 30, bean.getRemainwork(), fontFormat_Content));

	}

	public void DownExcelFile(HttpServletResponse response, String filePath)
			throws Exception {
		File file = new File(filePath);
		String fileName = "";
		response.setContentType("text/plain;charset=utf-8");
		if (file.exists()) {
			try {
				// 要用servlet 来打开一个 EXCEL 文档，需要将 response 对象中 header 的
				// contentType 设置成"application/x-msexcel"。
				response.setContentType("application/x-msexcel");
				// 保存文件名称
				fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
				fileName = new String(fileName.getBytes("GB2312"), "iso8859_1");
				// servlet中，要在 header中设置下载方式
				response.setHeader("Content-Disposition",
						"attachment; filename=" + fileName);
				// 缓冲流(BufferedStream)可以一次读写一批数据，,缓冲流(Buffered
				// Stream)大大提高了I/O的性能。
				BufferedInputStream bis = new BufferedInputStream(
						new FileInputStream(file));
				// OutputStream输出流
				OutputStream bos = response.getOutputStream();
				byte[] buff = new byte[1024];
				int readCount = 0;
				// 每次从文件流中读1024个字节到缓冲里。
				readCount = bis.read(buff);
				while (readCount != -1) {
					// 把缓冲里的数据写入浏览器
					bos.write(buff, 0, readCount);
					readCount = bis.read(buff);
				}
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
				response.setStatus(HttpServletResponse.SC_OK);
				response.flushBuffer();
				response.getWriter().close();
			} catch (Exception e) {

			}
		}
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public void downFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileNames[] = request.getParameter("fileName").split(",");
		String zipName = request.getParameter("zipName");
		response.setContentType("application/x-msdownload");
		response.setHeader("content-disposition", "attachment;filename="
				+ URLEncoder.encode(zipName, "utf-8"));

		String saveDir = request.getSession().getServletContext()
				.getRealPath("/app");
		
		
		ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
		
		List<String> tempList = new ArrayList<String>();
        for(String i : fileNames){
            if(!tempList.contains(i)){
                tempList.add(i);
            }
        }
        System.out.println("dddd->"+Arrays.toString(tempList.toArray()));
		
		for (String name : tempList) {
			File file = new File(saveDir + "/" + name);
			zos.putNextEntry(new ZipEntry(name));
			FileInputStream fis = new FileInputStream(file);
			byte b[] = new byte[1024];
			int n = 0;
			while ((n = fis.read(b)) != -1) {
				zos.write(b, 0, n);
			}
			zos.flush();
			fis.close();
		}
		zos.flush();
		zos.close();

		// 存储记录
	}

	public String checkbox(String str) {
		if (str == null) {
			return "";
		} else {
			if (str.equals("有")) {
				return "☑有  口无";
			} else if (str.equals("无")) {
				return "口有   ☑无";
			}

			else if (str.equals("是")) {
				return "☑是  口否";
			} else if (str.equals("否")) {
				return "口是   ☑ 否";
			}

			else if (str.equals("正常")) {
				return "☑正常  口异常";
			} else if (str.equals("异常")) {
				return "口正常   ☑异常";
			}

			else if (str.equals("安全")) {
				return "☑安全  口有隐患";
			} else if (str.equals("有隐患")) {
				return "口安全   ☑ 有隐患";
			} else if (str.equals("执行")) {
				return "☑ 执行  口未执行";
			} else if (str.equals("未执行")) {
				return "口执行   ☑未执行";
			}

			else if (str.equals("已备份")) {
				return "☑ 已备份  口未备份";
			} else if (str.equals("未备份")) {
				return "口已备份   ☑未备份";
			}

			else if (str.equals("移动") || str.equals("电信") || str.equals("铁塔")) {
				// return "☑已备份  口未备份";
				return " 租凭机房口       运营商机房☑          自建机房□";
			} else if (str.equals("自建")) {
				return " 租凭机房口       运营商机房口        自建机房☑";
			} else if (str.equals("租赁")) {
				return " 租凭机房☑        运营商机房口        自建机房口 ";
			}

			else if (str.equals("已执行")) {
				return "☑已执行  口未执行";
			} else if (str.equals("未执行")) {
				return "口已执行   ☑未执行";
			} else {
				return "";
			}
		}

	}
}
