package xh.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HelloWord {
	private static int i=0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	@RequestMapping("/hello")
	public String Hello(){
		System.out.println("spring mvc:=="+(i++));
		return "success";
	}

}
