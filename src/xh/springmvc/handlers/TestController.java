package xh.springmvc.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xh.org.socket.test;

@Controller
@RequestMapping("/test")
public class TestController {
	private test ss=new test();
	
	@RequestMapping(value="/a",method=RequestMethod.POST)
	public void a(HttpServletRequest request, HttpServletResponse response){
		ss.radioUser();
		
	}
	@RequestMapping(value="/b",method=RequestMethod.POST)
	public void b(HttpServletRequest request, HttpServletResponse response){
		ss.RadioUserAttr();
		
	}
	@RequestMapping(value="/c",method=RequestMethod.POST)
	public void c(HttpServletRequest request, HttpServletResponse response){
		ss.talkgroup();
		
	}
	@RequestMapping(value="/d",method=RequestMethod.POST)
	public void d(HttpServletRequest request, HttpServletResponse response){
		ss.talkGroupAttr();
		
	}
	@RequestMapping(value="/e",method=RequestMethod.POST)
	public void e(HttpServletRequest request, HttpServletResponse response){
		ss.MultiGroup();
		
	}
	@RequestMapping(value="/f",method=RequestMethod.POST)
	public void f(HttpServletRequest request, HttpServletResponse response){
		ss.dispatchUser();
		
	}
	@RequestMapping(value="/g",method=RequestMethod.POST)
	public void g(HttpServletRequest request, HttpServletResponse response){
		ss.DispatchUserIA();
		
	}

}
