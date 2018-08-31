package xh.func.plugin;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;



public class GlobalExceptionHandler{
	protected final Log log=LogFactory.getLog(GlobalExceptionHandler.class.getName());
	
	@ExceptionHandler(value=Exception.class)	
	@ResponseBody
	public Map<String,Object> PrintException(HttpServletRequest request,Exception ex){
		log.error("--------------------------程序异常开始----------------------");
		log.error("程序异常->描述="+ex.getLocalizedMessage());
		log.error("程序异常->error类型="+ex.getClass());
		log.error("程序异常->请求URL="+request.getRequestURL());
		log.error("程序异常->message="+ex.getMessage());
		log.error("--------------------------程序异常结束----------------------");
		Map map=new HashMap();
		map.put("success", false);
		map.put("class", ex.getClass());
		map.put("url", request.getRequestURL().toString());
		map.put("message",ex.getMessage());
		//String json=FlexJSON.Encode(map);
		return map;
		
	}

	
	/*public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		Map<String, Object> model = new HashMap<String, Object>();  
        model.put("ex", ex); 
      
       
        
        
        if(handler instanceof HandlerMethod){
        	  
            HandlerMethod handlerMethod = (HandlerMethod) handler;  
            String className = handlerMethod.getBeanType().getName();  
            String methodName = handlerMethod.getMethod().getName();  
            log.error("--------------------------程序异常开始----------------------");
    		log.error("程序异常->描述="+ex.getLocalizedMessage());
    		log.error("程序异常->描述="+ex.getStackTrace());
    		ex.printStackTrace();
    		log.error("程序异常->error类型="+ex.getClass());
    		log.error("程序异常->请求URL="+request.getRequestURL());
    		log.error("程序异常->message="+className);
    		log.error("程序异常->message="+methodName);
    		log.error("--------------------------程序异常结束----------------------");
            
        	
        }
		return null;
	}*/
	 

}
