package xh.func.plugin;

import java.awt.Color;  
import java.awt.Font;  
import java.awt.Graphics;  
import java.awt.image.BufferedImage;  
import java.util.Random;  
/** 
 * 验证码生成工具 
 * @author HXL 
 * 
 */  
public class CaptchaUtil {  
    private BufferedImage image;// 图像  
    private String str;// 验证码 
    private int width=90;
    private int height=35;
    private static char code[] = "abcdefgABCDEFG123456789".toCharArray();  
  
    public static final String SESSION_CODE_NAME="code";  
      
    private CaptchaUtil() {  
        init();// 初始化属性  
    }  
  
    /* 
     * 取得RandomNumUtil实例 
     */  
    public static CaptchaUtil Instance() {  
        return new CaptchaUtil();  
    }  
  
    /* 
     * 取得验证码图片 
     */  
    public BufferedImage getImage() {  
        return this.image;  
    }  
  
    /* 
     * 取得图片的验证码 
     */  
    public String getString() {  
        return this.str;  
    }  
  
    private void init() {  
        // 在内存中创建图象   
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        //创建画笔
        Graphics g = image.getGraphics();  
        // 生成随机类  
        Random random = new Random();  
        // 设定背景色  
        g.setColor(getRandColor(200, 250));  
        g.fillRect(0, 0, width, height);  
        
        // 设定字体  
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));  
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到  
        for (int i = 0; i < 155; i++) {  
            int x = random.nextInt(width);  
            int y = random.nextInt(height);  
            int xl = random.nextInt(12);  
            int yl = random.nextInt(12);  
            g.drawLine(x, y, x + xl, y + yl);  
        } 
        //设置噪点
        float yawpRate = 0.1f;// 噪声率  
        int area = (int) (yawpRate * width * height);  
        for (int i = 0; i < area; i++) {  
            int x = random.nextInt(width);  
            int y = random.nextInt(height);  
            int rgb = getRandomIntColor();  
            image.setRGB(x, y, rgb);  
        } 
        // 创建字体，字体的大小应该根据图片的高度来定。
	    Font font = new Font("Fixedsys", Font.BOLD, 20);
		// 设置字体。
		g.setFont(font);
		
        // 取随机产生的认证码(5位数字)  
        String sRand = "";  
        //居中显示  
         
        int c=random.nextInt(150)+1;
        for (int i = 0; i < 4; i++) {  
            String rand = String.valueOf(code[random.nextInt(code.length)]);  
            sRand += rand;  
            // 将认证码显示到图象中  
            g.setColor(Color.BLUE); 
            g.drawString(rand + "", 20 *i, 26);
        } 
        g.setColor(new Color(random.nextInt(150)+1)); 
        
        
        
        
        
        // 赋值验证码  
        this.str = sRand;  
  
        // 图象生效  
        g.dispose();  
        this.image = image;/* 赋值图像 */  
    }  
  
    /* 
     * 给定范围获得随机颜色 
     */  
    private Color getRandColor(int fc, int bc) {  
        Random random = new Random();  
        if (fc > 255)  
            fc = 255;  
        if (bc > 255)  
            bc = 255;  
        int r = fc + random.nextInt(bc - fc);  
        int g = fc + random.nextInt(bc - fc);  
        int b = fc + random.nextInt(bc - fc);  
        return new Color(r, g, b);  
    }  
    private  int getRandomIntColor() {  
        int[] rgb = getRandomRgb();  
        int color = 0;  
        for (int c : rgb) {  
            color = color << 8;  
            color = color | c;  
        }  
        return color;  
    } 
	 private int[] getRandomRgb() { 
		    Random random=new Random();
	        int[] rgb = new int[3];  
	        for (int i = 0; i < 3; i++) {  
	            rgb[i] = random.nextInt(255);  
	        }  
	        return rgb;  
	} 
	 public static void MyDrawString(String str,int x,int y,double rate,Graphics g){  
	        String tempStr=new String();  
	        int orgStringWight=g.getFontMetrics().stringWidth(str);  
	        int orgStringLength=str.length();  
	        int tempx=x;  
	        int tempy=y;  
	        while(str.length()>0)  
	        {  
	            tempStr=str.substring(0, 1);  
	            str=str.substring(1, str.length());  
	            g.drawString(tempStr, tempx, tempy);  
	            tempx=(int)(tempx+(double)orgStringWight/(double)orgStringLength*rate);  
	        }  
	    }  
}  