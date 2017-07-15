package xh.springmvc.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


/*@Controller*/
public class UncaughtExceptionController implements HandlerExceptionResolver{
	protected final Log log = LogFactory.getLog(UncaughtExceptionController.class);

	@Override
	@RequestMapping(value="/exception")
	public ModelAndView resolveException(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception e) {
		// TODO Auto-generated method stub
		 String viewName = ClassUtils.getShortName(e.getClass());
		 
		 log.error("program warning=>"+e);
		 log.error("program warning=>"+arg2);
		 log.error("program warning=>"+e.getStackTrace());
		return null;
	}
	

}
