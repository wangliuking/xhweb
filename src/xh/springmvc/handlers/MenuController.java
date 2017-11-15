package xh.springmvc.handlers;

import java.io.IOException;
import java.util.ArrayList;
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
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.MenuService;
import xh.mybatis.service.WebUserServices;

@Controller
@RequestMapping("/web")
public class MenuController {

	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(MenuController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	@RequestMapping(value="/menu", method = RequestMethod.GET)
	public void menu(HttpServletRequest request, HttpServletResponse response) {
		int roleId=funUtil.StringToInt(request.getParameter("roleId"));

		HashMap result = new HashMap();
		result.put("items", MenuService.menuChild(roleId));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("/updateMenu")
	public void updateMenu(HttpServletRequest request,
			HttpServletResponse response) {
		String formData = request.getParameter("formData");
		String roleId = request.getParameter("roleId");
		ArrayList list = GsonUtil.json2Object(formData, ArrayList.class);

		List<Map<String, Object>> paraList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = GsonUtil.json2Object(list.get(i).toString(), Map.class);

			/*Map<String, Object> paraMap = new HashMap<String, Object>();
			int rslt = (int) Math.floor(Float.parseFloat(map.get("id")
					.toString()));
			boolean checked = (Boolean) map.get("checked");
			paraMap.put("id", rslt);
			paraMap.put("checked", checked == true ? 1 : 0);*/
			MenuService.updateMenu(map);

		}

		HashMap result = new HashMap();
		/* result.put("items", ); */
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
