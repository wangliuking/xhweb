package xh.springmvc.handlers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import xh.func.plugin.CaptchaUtil;

@Controller
public class CodeController {

	protected final Log log=LogFactory.getLog(CodeController.class);

	/**
	 * 具体获取验证码的方法
	 * 
	 * @param time
	 *            time为时戳,这样的话可以避免浏览器缓存验证码
	 * @throws IOException
	 */
	@RequestMapping(value = "/code/{time}", method = RequestMethod.GET)
	public void getCode(@PathVariable("time") Float time,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {		
		
		// 通知浏览器不要缓存  
        response.setHeader("Expires", "-1");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setHeader("Pragma", "-1");  
        CaptchaUtil util = CaptchaUtil.Instance();  
        // 将验证码输入到session中，用来验证  
        String code = util.getString();  
        log.info("验证码："+code);
        request.getSession().setAttribute("code", code);  
        // 输出打web页面  
        ImageIO.write(util.getImage(), "png", response.getOutputStream());  
		
	}

}
