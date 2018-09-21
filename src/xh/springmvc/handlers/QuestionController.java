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

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.QuestionService;

@Controller
@RequestMapping("/question")
public class QuestionController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(QuestionController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response){
		String question=request.getParameter("question");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("question", question);
		map.put("time", FunUtil.nowDateNoTime());
		map.put("user", FunUtil.loginUser(request));
		try {
			int rst=QuestionService.add(map);
			if(rst>0){
				this.message="提交遗留问题成功";
				this.success=true;
			}else{
				this.message="提交遗留问题失败";
				this.success=false;
			}
			HashMap result = new HashMap();
			result.put("success", success);
			result.put("message", message);
			response.setContentType("application/json;charset=utf-8");
			String jsonstr = json.Encode(result);
			response.getWriter().write(jsonstr);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
	}
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response){
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		try {
			List<Map<String,Object>> list=QuestionService.list(map);
			int count=QuestionService.count(map);

			HashMap result = new HashMap();
			result.put("items", list);
			result.put("totals", count);
			response.setContentType("application/json;charset=utf-8");
			String jsonstr = json.Encode(result);
			response.getWriter().write(jsonstr);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
	}

}
