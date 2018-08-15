package xh.springmvc.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.DocConverter;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WebLogBean;
@Controller
@RequestMapping("/web")
public class WebController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(WebController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	@RequestMapping("/preview")
	@ResponseBody
	public Map<String,Object> DocPreview(HttpServletRequest request, 
			HttpServletResponse response){
		String filePath = request.getParameter("filePath");
		String path = request.getSession().getServletContext().getRealPath(filePath);
		DocConverter d = new DocConverter(path); 
         if(d.conver()){
        	 this.success=true;
        	 this.message="文档转换成功";
         }else{
        	 this.success=false;
        	 this.message="转换失败";
         }
		HashMap result = new HashMap();
		result.put("success", success);
		return result;
		}
	}