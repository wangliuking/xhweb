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
import xh.mybatis.bean.RadioStatusBean;
import xh.mybatis.service.RadioStatusService;


@Controller
public class RadioStatusController {
	
	@RequestMapping(value="/index/ajax_table3",method=RequestMethod.GET)
	@ResponseBody
	public void selectAllRadioStatus(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, List<RadioStatusBean>> map = new HashMap<String, List<RadioStatusBean>>();
		RadioStatusService RadioStatusService = new RadioStatusService();
		try {
			List<RadioStatusBean> list = RadioStatusService.selectAllRadioStatus();
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
