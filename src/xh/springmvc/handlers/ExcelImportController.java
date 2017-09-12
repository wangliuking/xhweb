package xh.springmvc.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import xh.func.plugin.FlexJSON;
import xh.mybatis.service.ExcelService;


@Controller  
@RequestMapping(value="/excel")
public class ExcelImportController {
		private FlexJSON json=new FlexJSON();
	    private ExcelService excelService = new ExcelService();  
	      
	    @RequestMapping(value="/upload",method = RequestMethod.POST)  
	    @ResponseBody  
	    public void upload(@RequestParam(value="file",required = false)MultipartFile file,HttpServletRequest request, HttpServletResponse response){  
	        String result = excelService.readExcelFile(file);  
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
