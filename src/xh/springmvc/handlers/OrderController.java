package xh.springmvc.handlers;

import java.io.IOException;
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

import com.tcpBean.ErrProTable;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.OrderService;


@Controller
@RequestMapping("/order")
public class OrderController {
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
		
		Map<String,Object> map=new HashMap<String, Object>();
		/*map.put("loginuser", funUtil.loginUser(request));*/
		map.put("start", start);
		map.put("limit", limit);
		map.put("type", -1);
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
	//派单处理未完成数量
	@RequestMapping(value="/orderNoComCount", method = RequestMethod.GET)
	public void orderNoComCount(HttpServletRequest request, HttpServletResponse response) {		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("type", 3);
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
	
	@RequestMapping(value="/writeOrder", method = RequestMethod.POST)
	public void writeOrder(HttpServletRequest request, HttpServletResponse response) {
		String fromData=request.getParameter("formData");
		ErrProTable bean=GsonUtil.json2Object(fromData, ErrProTable.class);
		bean.setSerialnumber(FunUtil.RandomWord(8));
		
		log.info("ErrProTab->"+bean.toString());
		this.success=true;

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

}
