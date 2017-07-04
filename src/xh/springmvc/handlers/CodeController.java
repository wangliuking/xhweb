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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CodeController {

	private int width = 90;// 验证码宽度
	private int height = 30;// 验证码高度
	private int codeCount = 4;// 验证码个数
	private int lineCount = 19;// 混淆线个数
	
	
	
	private static Random random=new Random();

	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

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
		// 定义随机数类
		Random r = new Random();
		// 定义存储验证码的类
		StringBuilder builderCode = new StringBuilder();
		// 定义画布
		BufferedImage buffImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 得到画笔
		Graphics g = buffImg.getGraphics();
		// 1.设置颜色,画边框 1149031036 
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, width, height);
		//g.drawRect(0, 0, width, height);
		// 2.设置颜色,填充内部
		
		g.setColor(Color.WHITE);
		g.fillRect(1, 1, width - 2, height - 2);
		// 3.设置干扰线
		g.setColor(Color.gray);
		for (int i = 0; i < lineCount; i++) {
			g.drawLine(r.nextInt(width), r.nextInt(width), r.nextInt(width),
					r.nextInt(width));
		}
		 //4.添加噪点  
		
        float yawpRate = 0.1f;// 噪声率  
        int area = (int) (yawpRate * width * height);  
        for (int i = 0; i < area; i++) {  
            int x = random.nextInt(width);  
            int y = random.nextInt(height);  
            int rgb = getRandomIntColor();  
            buffImg.setRGB(x, y, rgb);  
        }  
		// 4.设置验证码
		g.setColor(Color.blue);
		// 4.1设置验证码字体
		g.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 18));
		for (int i = 0; i < codeCount; i++) {
			char c = codeSequence[r.nextInt(codeSequence.length)];
			builderCode.append(c);
			g.drawString(c + "", 18 * (i + 1), 18);
		}
		// 5.保存到session中
		HttpSession session = request.getSession();
		session.setAttribute("codeValidate", builderCode.toString());
		// 6.输出到屏幕
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(buffImg, "png", sos);
		
		
		// 7.禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/png");
		// 8.关闭sos
		sos.close();
	}
	private static int getRandomIntColor() {  
        int[] rgb = getRandomRgb();  
        int color = 0;  
        for (int c : rgb) {  
            color = color << 8;  
            color = color | c;  
        }  
        return color;  
    } 
	 private static int[] getRandomRgb() { 
	        int[] rgb = new int[3];  
	        for (int i = 0; i < 3; i++) {  
	            rgb[i] = random.nextInt(255);  
	        }  
	        return rgb;  
	    }  
	 

}
