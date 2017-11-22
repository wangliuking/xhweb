package xh.springmvc.handlers;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.MenuService;

@Controller
@RequestMapping("/sqlserver")
public class SqlServerController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(SqlServerController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	
	@RequestMapping(value="/bsmonitorList", method = RequestMethod.GET)
	public void bsmonitorList(HttpServletRequest request, HttpServletResponse response) {
		int bsId=funUtil.StringToInt(request.getParameter("bsId"));
		String table="tb_Dev";
		if(bsId<10){table=table+"000"+bsId;}
		else if( bsId>=10 &&  bsId<100){table=table+"00"+bsId;}
		else{table=table+"0"+bsId;}
		
		
		

		/*HashMap result = new HashMap();
		result.put("items", MenuService.menuChild(roleId,0));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

}
