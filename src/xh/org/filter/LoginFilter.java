package xh.org.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.WebUserServices;
import xh.org.listeners.SingLoginListener;

public class LoginFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L;
	protected final Log log = LogFactory.getLog(LoginFilter.class);
	private FilterConfig filterConfig;
	private FunUtil funUtil = new FunUtil();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session=servletRequest.getSession();
		/*String pass = funUtil.getCookie(servletRequest,"");
		String root = funUtil.getCookie(servletRequest,"");*/
		// 获得用户请求的URI
		String path = servletRequest.getRequestURI();
		//陆页面无需过滤
		try {
			if (path.indexOf("/login.html") > -1) {
				chain.doFilter(servletRequest, servletResponse);
				return;
			} else if (path.indexOf("/loginout.jsp") > -1) {
				chain.doFilter(servletRequest, servletResponse);
				return;
			} else if (path.indexOf("/loadData.js") > -1) {
				chain.doFilter(servletRequest, servletResponse);
				return;
			} else if (path.indexOf("/loadData.html") > -1) {
				chain.doFilter(servletRequest, servletResponse);
				return;
			}
			else {
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (SingLoginListener.isOnline(session) && isLock(servletRequest)==1) {
				//log.info("已经登录系统，可以正常使用");
				chain.doFilter(request, response);
			}else {
				log.info("未登录系统，跳转至登录页面");
				servletResponse.sendRedirect(servletRequest.getContextPath()
						+ "/Views/login.html");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
	public int isLock(HttpServletRequest request){
		WebUserBean bean=WebUserServices.selectUserByUser(funUtil.loginUser(request));
		return bean.getStatus();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = arg0;
	}

}
