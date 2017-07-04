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
import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.service.BsStatusService;


@Controller
public class BsStatusController {
	
	@RequestMapping(value="/index/ajax_table2",method=RequestMethod.GET)
	@ResponseBody
	public void selectAllBsStatus(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, List<BsStatusBean>> map = new HashMap<String, List<BsStatusBean>>();
		BsStatusService bsStatusService = new BsStatusService();
		try {
			List<BsStatusBean> list = bsStatusService.selectAllBsStatus();
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
