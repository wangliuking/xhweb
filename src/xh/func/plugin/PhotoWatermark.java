package xh.func.plugin;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/officetest")
public class PhotoWatermark{

    public static void main(String[] args) throws Exception{
        PhotoWatermark pw=new PhotoWatermark();
        //添加logo  原图片路径  logo路径 生成合成图片路径 x,y
        //PhotoWatermark.addImageLogo("d:/测试2.jpg","d:/seal.png","d:/x.jpg",20,20);

        //源文件路径 水印文字信息 生成后的路径
        /*pw.addTextLogo("d:/1.png","www.baidu.com","d:/logo.png",
                new Font("Arial Black",Font.PLAIN,48),20,40);*/
        /*String fileName = "2019.9.0.docx";
        String nameNoSuffix = fileName.substring(0,fileName.lastIndexOf("."));
        System.out.println(nameNoSuffix);*/
        //pw.pdf2Image("/D:/www.pdf","www");
        pw.pdfToImage("/D:/www.pdf","www");
    }

    public String parseSuffix(String fileName) throws Exception {
        PhotoWatermark pw = new PhotoWatermark();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        String nameNoSuffix = fileName.substring(0, fileName.lastIndexOf("."));
        if ("jpg".equals(suffix) || "png".equals(suffix)) {
            //图片类型
            return fileName;
        }
        if ("doc".equals(suffix) || "docx".equals(suffix)) {
            //文档类型
            String classPath = this.getClass().getResource("/").getPath();
            String[] temp = classPath.split("WEB-INF");
            String docPath = temp[0] + "Resources/upload/CheckCut/" + fileName;
            String pdfPath = temp[0] + "Resources/upload/CheckCut/" + nameNoSuffix + ".pdf";
            //word转pdf
            pw.word2Pdf(docPath, pdfPath);
            //pdf转图片
            String s = pw.pdfToImage(pdfPath,nameNoSuffix);
            return s.substring(0,s.length()-1);
        }
        if("pdf".equals(suffix)){
            //pdf类型
            String classPath = this.getClass().getResource("/").getPath();
            String[] temp = classPath.split("WEB-INF");
            String pdfPath = temp[0] + "Resources/upload/CheckCut/" + nameNoSuffix + ".pdf";
            //pdf转图片
            String s = pw.pdfToImage(pdfPath,nameNoSuffix);
            return s.substring(0,s.length()-1);
        }
        return "";
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
        /*FileOutputStream os=new FileOutputStream(savepath);
        JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(os);
        encoder.encode(buffimage);
        os.close();*/
        ImageIO.write(buffimage, "jpeg" , new File(savepath));
        image.flush();
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
        /*FileOutputStream os=new FileOutputStream(savepath);
        JPEGImageEncoder encoder= JPEGCodec.createJPEGEncoder(os);
        encoder.encode(buffimage);
        os.close();*/
        ImageIO.write(buffimage, "jpeg" , new File(savepath));
        image.flush();
    }

    /***
     * PDF文件转图片(删除原pdf)
     */
    public String pdfToImage(String pdfPath, String nameNoSuffix) {
        String pathNoSuffix = pdfPath.substring(0,pdfPath.lastIndexOf("."));
        String fileNames = "";
        File file = new File(pdfPath.substring(1,pdfPath.length()));
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 296);
                //BufferedImage image = renderer.renderImage(i, 2.5f);
                ImageIO.write(image, "PNG", new File(pathNoSuffix+i+".png"));
                fileNames +=nameNoSuffix+i+".png;";
            }
            doc.close();
            Thread.sleep(200);
            //删除原pdf
            File delFile = new File(pdfPath.substring(1,pdfPath.length()));
            delFile.delete();
            return fileNames;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String pdf2Image(String pdfPath,String nameNoSuffix) throws Exception {
        String pathNoSuffix = pdfPath.substring(0,pdfPath.lastIndexOf("."));
        String fileNames = "";
        //加载PDF文件
        PdfDocument doc = new PdfDocument();
        System.out.println("===========================");
        System.out.println(pdfPath);
        System.out.println("===========================");
        System.out.println(nameNoSuffix);
        System.out.println("===========================");
        doc.loadFromFile(pdfPath.substring(1,pdfPath.length()), FileFormat.PDF);

        /*File f = new File(pdfPath.substring(1,pdfPath.length()));
        InputStream in = new FileInputStream(f);
        InputStreamReader input = new InputStreamReader(in, "UTF-8");
        input
        doc.loadFromStream(input);*/

        //保存PDF的每一页到图片
        BufferedImage image;
        for (int i = 0; i < doc.getPages().getCount(); i++) {
            image = doc.saveAsImage(i);
            File file = new File(pathNoSuffix+i+".png");
            ImageIO.write(image, "PNG", file);
            fileNames +=nameNoSuffix+i+".png;";
        }
        doc.close();
        //删除原pdf
        File delFile = new File(pdfPath.substring(1,pdfPath.length()));
        delFile.delete();
        //end
        return fileNames;
    }

    /***
     * word文件转Pdf(删除原word)
     */
    public  void word2Pdf(String docPath,String pdfPath) throws Exception {
        System.out.println("+++++++++++++++++++++++++++");
        System.out.println(docPath);
        System.out.println(pdfPath);
        try {
            String s = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
            ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
            License license = new License();
            license.setLicense(is);
            com.aspose.words.Document document = new com.aspose.words.Document(docPath.substring(1,docPath.length()));
            document.save(new FileOutputStream(new File(pdfPath.substring(1,pdfPath.length()))), SaveFormat.PDF);
            //删除原word
            File delFile = new File(docPath.substring(1,docPath.length()));
            delFile.delete();
            //end
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
