package xh.springmvc.handlers;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.mybatis.bean.BsAlarmBean;
import xh.mybatis.service.BsAlarmService;


@Controller
public class BsAlarmController {
	
	@RequestMapping(value="/index/ajax",method=RequestMethod.GET)
	@ResponseBody
	public void selectAllBsAlarm(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, List<BsAlarmBean>> map = new HashMap<String, List<BsAlarmBean>>();
		BsAlarmService bsAlarmService = new BsAlarmService();
		try {
			List<BsAlarmBean> list = bsAlarmService.selectAllBsAlarm();
			map.put("data", list);
			String data = FlexJSON.Encode(map);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.write(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
