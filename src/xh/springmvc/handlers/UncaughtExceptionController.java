package xh.springmvc.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class UncaughtExceptionController implements HandlerExceptionResolver{

	@Override
	@RequestMapping(value="/exception")
	public ModelAndView resolveException(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception e) {
		// TODO Auto-generated method stub
		 String viewName = ClassUtils.getShortName(e.getClass());
		 
		 System.out.println("我是异常=>"+e);
		 System.out.println("我是异常=>"+viewName);
		return null;
	}
	

}
