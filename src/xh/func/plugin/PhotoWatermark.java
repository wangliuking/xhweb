package xh.func.plugin;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.zhuozhengsoft.pageoffice.DocumentOpenType;
import com.zhuozhengsoft.pageoffice.FileMakerCtrl;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.FileNotFoundException;

@Controller
@RequestMapping(value = "/officetest")
public class PhotoWatermark{

    public static void main(String[] args) throws Exception{
        PhotoWatermark pw=new PhotoWatermark();
        //添加logo  原图片路径  logo路径 生成合成图片路径 x,y
        PhotoWatermark.addImageLogo("d:/测试2.jpg","d:/seal.png","d:/x.jpg",20,20);

        //源文件路径 水印文字信息 生成后的路径
        /*pw.addTextLogo("d:/1.png","www.baidu.com","d:/logo.png",
                new Font("Arial Black",Font.PLAIN,48),20,40);*/
    }

    /**
     *@param photopath : 原图存放的路径
     *@param logopath : logo图像存放的路径
     *@param savepath : 目标输出保存的路径
     *@param x : logo图像在合并到原图中的显示位置x座标
     *@param y : logo图像在合并到原图中的显示位置y座标
     */
    public static void addImageLogo(String photopath,String logopath,String savepath,int x,int y)
            throws IOException,FileNotFoundException{
        System.out.println(photopath+"====="+logopath);
        Image image=ImageIO.read(new File(photopath));
        int pwidth=image.getWidth(null);
        int pheight=image.getHeight(null);

        BufferedImage buffimage=new BufferedImage(pwidth,pheight,BufferedImage.TYPE_INT_BGR);
        Graphics g=buffimage.createGraphics();
        g.drawImage(image,0,0,pwidth,pheight,null);

        Image logo=ImageIO.read(new File(logopath));
        int lwidth=logo.getWidth(null);
        int lheight=logo.getHeight(null);
        g.drawImage(logo,x,y,lwidth,lheight,null);

        g.dispose();
        FileOutputStream os=new FileOutputStream(savepath);
        JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(os);
        encoder.encode(buffimage);
        os.close();
    }

    /**
     53     *@param photopath : 原图存放的路径
     54     *@param logopath : 文本logo内容
     55     *@param savepath : 目标输出保存的路径
     56     *@param font : 文本logo的字体设置
     57     *@param x : 文本logo在合并到原图中的显示位置x座标
     58     *@param y : 文本logo在合并到原图中的显示位置y座标
     59     */
    public void addTextLogo(String photopath,String logotext,String savepath,
                            java.awt.Font font,int x,int y) throws Exception{

        Image image=ImageIO.read(new File(photopath));
        int pwidth=image.getWidth(null);
        int pheight=image.getHeight(null);

        BufferedImage buffimage=new BufferedImage(pwidth,pheight,BufferedImage.TYPE_INT_BGR);
        Graphics g=buffimage.createGraphics();
        g.drawImage(image,0,0,pwidth,pheight,null);

        g.setFont(font);
        g.drawString(logotext,x,y);

        g.dispose();
        FileOutputStream os=new FileOutputStream(savepath);
        JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(os);
        encoder.encode(buffimage);
        os.close();
    }
}
