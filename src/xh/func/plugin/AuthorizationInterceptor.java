package xh.func.plugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.util.Log;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.WebUserServices;
import xh.redis.server.UserRedis;

public class AuthorizationInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("操作完成");
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("操作中");
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
/*		System.out.println("操作前");
		System.out.println("操作前:"+arg0.getMethod());
		System.out.println("操作前:"+arg0.getRequestURI());
		System.out.println("操作前:"+arg0.getRequestedSessionId());*/
		
		String path=arg0.getRequestURI();
		/*if(url.indexOf("login")>-1){
			return true;
		}*/
		if (path.indexOf("/login") > -1) {
			return true;
		}else if (path.indexOf("/Views/business/sign") > -1) {
			return true;
		}else if (path.indexOf("/Views/business/sign") > -1) {
			return true;
		}else if (path.indexOf("/wav") > -1) {
			return true;
		}
		else if (path.indexOf("/Views/jsp") > -1) {
			return true;
		}
		else if (path.indexOf("/doc") > -1) {
			return true;
		}else if(path.indexOf("/Resources") > -1){
			return true;
		}else if(path.indexOf("/lib") > -1){
			return true;
		}
		else{
			HttpSession session=arg0.getSession();
			String sessionId = session.getId();
			//根据sessionId查询是否有登录用户
			String userId = UserRedis.searchUserInRedisOne(sessionId);
			if(userId!=null && isLock(arg0,userId)==1){
				return true;
			}else{
				// 获取到项目名，以便下面进行重定向
	            String homeUrl = arg0.getContextPath();
	            if(path.indexOf("/webapp/") > -1){
					arg0.getRequestDispatcher("webapp/pages/login/index.html").forward(arg0, arg1);
				}else{
					arg0.getRequestDispatcher("Views/login.html").forward(arg0, arg1);
				}
	            return false; 
			}
		}
		
	}
	public int isLock(HttpServletRequest request,String userId){
		//WebUserBean bean=WebUserServices.selectUserByUser(funUtil.loginUser(request));
		WebUserBean bean=WebUserServices.selectUserByUser(userId);
		if(bean!=null){
			return bean.getStatus();
		}else{
			return 0;
		}	
	}

}
