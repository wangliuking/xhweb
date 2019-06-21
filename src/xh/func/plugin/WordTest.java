package xh.func.plugin;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import xh.mybatis.service.CheckCutService;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oreilly.servlet.Base64Encoder;

public class WordTest {

    private Configuration configuration = null;
    private static String globalPath = "C:\\";

    public WordTest(){
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
    }

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        WordTest wordTest = new WordTest();

        Map<String,Object> param = new HashMap<String,Object>();
        param.put("startTime","2019-03-15 15:00:36");
        param.put("endTime","2019-03-15 16:00:43");
        List<Map<String,Object>> list = CheckCutService.exportWordByTime(param);

        wordTest.run(list);
    }

    public List<String> run(List<Map<String,Object>> list) {
        createDir(globalPath+"down");

        for(int i=0;i<list.size();i++){
            Map<String,Object> temp = list.get(i);
            createWord(temp);
        }

        List<String> finalList = searchFile();
        return finalList;

    }

    public void createWord(Map<String,Object> dataMap){
        String ftlName = "";
        String checkCutType = dataMap.get("checkCutType")+"";
        if("1".equals(checkCutType)){
            String fileName1 = dataMap.get("fileName1")+"";
            String fileName2 = dataMap.get("fileName2")+"";
            String fileName3 = dataMap.get("fileName3")+"";
            if(fileName1 == null || "".equals(fileName1)){
                //无附件
                ftlName = "checkcut.ftl";
            }else{
                String fileNames = fileName1 + fileName2 +fileName3;
                String[] fileArr = fileNames.split(";");
                dataMap.put("img1","");
                dataMap.put("img2","");
                dataMap.put("img3","");
                dataMap.put("img4","");
                dataMap.put("img5","");
                dataMap.put("img6","");
                dataMap.put("img7","");
                dataMap.put("img8","");
                dataMap.put("img9","");
                dataMap.put("img10","");
                dataMap.put("img11","");
                dataMap.put("img12","");
                for(int i=0;i<fileArr.length;i++){
                    int tempEnd = i+1;
                    String key = "img"+tempEnd;
                    String val = getImgsStr(fileArr[i]);
                    dataMap.put(key,val);
                }
                if(fileArr.length>0 && fileArr.length<4){
                    ftlName = "checkcutimg1.ftl";
                }else if(fileArr.length>3 && fileArr.length<7){
                    ftlName = "checkcutimg2.ftl";
                }else if(fileArr.length>6 && fileArr.length<10){
                    ftlName = "checkcutimg3.ftl";
                }else if(fileArr.length>9 && fileArr.length<13){
                    ftlName = "checkcutimg4.ftl";
                }
            }

        }else if("2".equals(checkCutType)){
            String fileName1 = dataMap.get("fileName1")+"";
            String fileName2 = dataMap.get("fileName2")+"";
            String fileName3 = dataMap.get("fileName3")+"";
            if(fileName1 == null || "".equals(fileName1)){
                //无附件
                ftlName = "checkcutDispatch.ftl";
            }else{
                String fileNames = fileName1 + fileName2 +fileName3;
                String[] fileArr = fileNames.split(";");
                dataMap.put("img1","");
                dataMap.put("img2","");
                dataMap.put("img3","");
                dataMap.put("img4","");
                dataMap.put("img5","");
                dataMap.put("img6","");
                dataMap.put("img7","");
                dataMap.put("img8","");
                dataMap.put("img9","");
                dataMap.put("img10","");
                dataMap.put("img11","");
                dataMap.put("img12","");
                for(int i=0;i<fileArr.length;i++){
                    int tempEnd = i+1;
                    String key = "img"+tempEnd;
                    String val = getImgsStr(fileArr[i]);
                    dataMap.put(key,val);
                }
                if(fileArr.length>0 && fileArr.length<4){
                    ftlName = "checkcutDispatchimg1.ftl";
                }else if(fileArr.length>3 && fileArr.length<7){
                    ftlName = "checkcutDispatchimg2.ftl";
                }else if(fileArr.length>6 && fileArr.length<10){
                    ftlName = "checkcutDispatchimg3.ftl";
                }else if(fileArr.length>9 && fileArr.length<13){
                    ftlName = "checkcutDispatchimg4.ftl";
                }
            }

        }
        Template t=null;
        try {
            configuration.setDirectoryForTemplateLoading(new File(globalPath));
            t = configuration.getTemplate(ftlName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将img转为base64
        String temp1 = getImgStr(dataMap.get("persion1")+"");
        //System.out.println("temp1 : "+temp1);
        dataMap.put("persion1",temp1);
        String temp2 = getImgStr(dataMap.get("persion2")+"");
        //System.out.println("temp2 : "+temp2);
        dataMap.put("persion2",temp2);
        String temp3 = getImgStr(dataMap.get("persion3")+"");
        //System.out.println("temp3 : "+temp3);
        dataMap.put("persion3",temp3);

        String tempBreakTime = dataMap.get("breakTime")+"";
        //去除时间中的特殊符号
        //可以在中括号内加上任何想要替换的字符
        String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        //这里是将特殊字符换为aa字符串,""代表直接去掉
        String aa = "";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(tempBreakTime);//这里把想要替换的字符串传进来
        String breakTime = m.replaceAll(aa).trim();

        File outFile = new File(globalPath+"down\\"+dataMap.get("name")+breakTime+".doc"); //导出文件
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            t.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 迭代删除文件夹
     * @param dirPath 文件夹路径
     */
    public static void deleteDir(String dirPath)
    {
        File file = new File(dirPath);
        if(file.isFile())
        {
            file.delete();
        }else
        {
            File[] files = file.listFiles();
            if(files == null)
            {
                file.delete();
            }else
            {
                for (int i = 0; i < files.length; i++)
                {
                    deleteDir(files[i].getAbsolutePath());
                }
                file.delete();
            }
        }
    }

    // 判断文件夹是否存在
    public static void createDir(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            System.out.println("dir exists first delete then create");
            deleteDir(path);
            file.mkdir();
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }

    }

    private static List<String> searchFile(){
        File file = new File(globalPath+"down");
        File[] files = file.listFiles();
        List<String> list = new LinkedList<String>();
        for(int i=0;i<files.length;i++){
            list.add(files[i].getPath());
            System.out.println(files[i].getName()+"==="+files[i].getPath());
        }
        return list;
    }

    public String getImgsStr(String person){
        if(person == null || "".equals(person)){
            return "";
        }
        String path = this.getClass().getResource("/").getPath();
        String[] temp = path.split("WEB-INF");
        System.out.println(temp[0]);

        String imgFile = temp[0]+"Resources/upload/CheckCut/"+person;
        InputStream in = null;
        byte[] data = null;

        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



        return Base64Encoder.encode(data);
    }

    public String getImgStr(String person){
        if(person == null || "".equals(person)){
            return "";
        }
        String path = this.getClass().getResource("/").getPath();
        String[] temp = path.split("WEB-INF");
        System.out.println(temp[0]);

        String imgFile = temp[0]+"Views/business/"+person;
        InputStream in = null;
        byte[] data = null;

        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        
       
        return Base64Encoder.encode(data);
    }

}
