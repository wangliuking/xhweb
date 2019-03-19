package xh.func.plugin;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import xh.mybatis.service.CheckCutService;

import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordTest {

    private Configuration configuration = null;

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
        createDir("D:\\down");

        for(int i=0;i<list.size();i++){
            Map<String,Object> temp = list.get(i);
            createWord(temp);
        }

        List<String> finalList = searchFile();
        return finalList;

    }

    public void createWord(Map<String,Object> dataMap){

        Template t=null;
        try {
            configuration.setDirectoryForTemplateLoading(new File("D:\\"));
            t = configuration.getTemplate("checkcut.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String tempBreakTime = dataMap.get("breakTime")+"";
        //去除时间中的特殊符号
        //可以在中括号内加上任何想要替换的字符
        String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        //这里是将特殊字符换为aa字符串,""代表直接去掉
        String aa = "";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(tempBreakTime);//这里把想要替换的字符串传进来
        String breakTime = m.replaceAll(aa).trim();

        File outFile = new File("D:\\down\\"+dataMap.get("name")+breakTime+".doc"); //导出文件
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        } catch (FileNotFoundException e1) {
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
        File file = new File("D:\\down");
        File[] files = file.listFiles();
        List<String> list = new LinkedList<String>();
        for(int i=0;i<files.length;i++){
            list.add(files[i].getPath());
            System.out.println(files[i].getName()+"==="+files[i].getPath());
        }
        return list;
    }

}
