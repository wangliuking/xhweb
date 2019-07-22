package xh.func.plugin;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import xh.mybatis.service.CheckCutService;

import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oreilly.servlet.Base64Encoder;

public class WordTest {

    private Configuration configuration = null;
    public static String globalPath = "C:\\";

    public WordTest(){
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
    }

    public static void main(String[] args) throws Exception {
        //test();
        /*Map<String,Object> dataMap = new HashMap<String,Object>();
        String fileName1 = dataMap.get("fileName1")==null?"":dataMap.get("fileName1")+"";
        String fileName2 = dataMap.get("fileName2")==null?"":dataMap.get("fileName2")+"";
        String fileName3 = dataMap.get("fileName3")==null?"":dataMap.get("fileName3")+"";
        String fileNames = fileName1 + fileName2 +fileName3;
        String[] fileArr = fileNames.split(";");
        System.out.println(fileName1);
        System.out.println(fileName2);
        System.out.println(fileName3);
        System.out.println(fileNames);
        System.out.println(fileArr[0]);*/
        //WordTest wordTest = new WordTest();
        //List<Integer> list = wordTest.getImgWidth("bfc25dce54fb13756183cad4adad8c78-test1.jpg");
        //System.out.println(list);
        System.out.println(dateFormat("2019-07-19 16:04:55"));
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
        String fileName1 = dataMap.get("fileName1")==null?"":dataMap.get("fileName1")+"";
        String fileName2 = dataMap.get("fileName2")==null?"":dataMap.get("fileName2")+"";
        String fileName3 = dataMap.get("fileName3")==null?"":dataMap.get("fileName3")+"";
        String bsIdElec = dataMap.get("bsIdElec")==null?"":dataMap.get("bsIdElec")+"";
        String fileNames = fileName1 + fileName2 +fileName3;
        //初始化图片参数
        for(int i=1;i<13;i++){
            String key = "img"+i;
            String val = "";
            dataMap.put(key,val);
            String a = "img"+i+"width";
            int b = 0;
            dataMap.put(a,b);
            String x = "img"+i+"height";
            int y = 0;
            dataMap.put(x,y);
        }
        if(!"".equals(fileNames)){
            String[] fileArr = fileNames.split(";");
            for(int i=0;i<fileArr.length;i++){
                int tempEnd = i+1;
                String key = "img"+tempEnd;
                String val = getImgsStr(fileArr[i],dataMap);
                dataMap.put(key,val);
                List<Integer> list = getImgWidth(fileArr[i]);
                String a = "img"+tempEnd+"width";
                String b = "img"+tempEnd+"height";
                int w = list.get(0);
                int h = list.get(1);
                if(w>410){
                    h = h/(w/410);
                    if(h>670){
                        h=670;
                    }
                    dataMap.put(a,410);
                    dataMap.put(b,h);
                }else{
                    if(h>670){
                        h=670;
                    }
                    dataMap.put(a,w);
                    dataMap.put(b,h);
                }
            }
        }
        if("1".equals(checkCutType)){
            if(!"".equals(bsIdElec)){
                //有发电记录
                ftlName = "checkcutElec.ftl";
            }else{
                //无发电记录
                ftlName = "checkcut.ftl";
                System.out.println("===========================");
                System.out.println("使用了该模板");
                System.out.println("===========================");
            }

        }else if("2".equals(checkCutType)){
            if(!"".equals(bsIdElec)){
                //有发电记录
                ftlName = "checkcutDispatchElec.ftl";
            }else{
                //无发电记录
                ftlName = "checkcutDispatch.ftl";
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
        String tempReason = dataMap.get("reason")+"";
        if(!"".equals(tempReason) && !"null".equals(tempReason)){
            String[] tempReasonArr = tempReason.split(";");
            String temp4 = getImgsStr(tempReasonArr[0],dataMap);
            //System.out.println("temp3 : "+temp3);
            dataMap.put("reason",temp4);
        }else{
            dataMap.put("reason","");
        }
        String to_bs_time = dataMap.get("to_bs_time")==null?"":dataMap.get("to_bs_time")+"";
        String to_bs_level = dataMap.get("to_bs_level")==null?"":dataMap.get("to_bs_level")+"";
        String isPowerElec = dataMap.get("isPowerElec")==null?"":dataMap.get("isPowerElec")+"";
        String power_time = dataMap.get("power_time")==null?"":dataMap.get("power_time")+"";
        String suggestElec = dataMap.get("suggestElec")==null?"":dataMap.get("suggestElec")+"";
        dataMap.put("to_bs_time",to_bs_time);
        dataMap.put("to_bs_level",to_bs_level);
        dataMap.put("isPowerElec",isPowerElec);
        dataMap.put("power_time",power_time);
        dataMap.put("suggestElec",suggestElec);

        //判断是否发电
        String isPower = dataMap.get("isPower")+"";
        String isPowerTime = dataMap.get("isPowerTime")+"";
        String maintainTime = dataMap.get("maintainTime")+"";
        if("基站允许发电".equals(isPower)){
            isPower = isPower+"，"+isPowerTime+"，维护上站时间"+maintainTime;
            dataMap.put("isPower",isPower);
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

        //处理时间
        String date1 = dataMap.get("breakTime")+"";
        String date2 = dataMap.get("restoreTime")+"";
        dataMap.put("breakTime",dateFormat(date1));
        dataMap.put("restoreTime",dateFormat(date2));

        File outFile = new File(globalPath+"down\\"+dataMap.get("bsPeriod")+dataMap.get("name")+breakTime+".doc"); //导出文件
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

    public static List<String> searchPeriodFile(String param){
        File file = new File(globalPath+"down");
        File[] files = file.listFiles();
        List<String> list = new LinkedList<String>();
        for(int i=0;i<files.length;i++){
            String fileName = files[i].getName();
            String period = fileName.substring(0,1);
            if(period.equals(param)){
                list.add(files[i].getPath());
                System.out.println(files[i].getName()+"==="+files[i].getPath());
            }
        }
        return list;
    }

    public static List<String> searchPeriodFileName(String param){
        File file = new File(globalPath+"down");
        File[] files = file.listFiles();
        List<String> list = new LinkedList<String>();
        for(int i=0;i<files.length;i++){
            String fileName = files[i].getName();
            String period = fileName.substring(0,1);
            if(period.equals(param)){
                list.add(files[i].getName());
                //System.out.println(files[i].getName()+"==="+files[i].getPath());
            }
        }
        return list;
    }

    public String getImgsStr(String person,Map<String,Object> dataMap){
        if(person == null || "".equals(person)){
            return "";
        }
        String path = this.getClass().getResource("/").getPath();
        String[] temp = path.split("WEB-INF");
       /* System.out.println("===================");
        System.out.println("dataMap ->: "+dataMap);
        System.out.println("person ->: "+person);
        System.out.println("===================");*/

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

    /**
     * 获取图片宽高
     * @return 宽高
     */
    public List<Integer> getImgWidth(String person) {
        String path = this.getClass().getResource("/").getPath();
        String[] temp = path.split("WEB-INF");
        /*System.out.println("===================");
        System.out.println("person ->: "+person);
        System.out.println("===================");*/

        String imgFile = temp[0]+"Resources/upload/CheckCut/"+person;

        InputStream is = null;
        BufferedImage src = null;
        int width = -1;
        int height = -1;
        try {
            is = new FileInputStream(imgFile);
            src = javax.imageio.ImageIO.read(is);
            width = src.getWidth(null); // 得到源图宽
            height = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Integer> list = new LinkedList<Integer>();
        list.add(width);
        list.add(height);
        return list;
    }

    /**
     * 时间格式化
     * @return
     */
    public static String dateFormat(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(time);
        }catch (Exception e){
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);					//获取年份
        int month = calendar.get(Calendar.MONTH)+1;					//获取月份
        int day = calendar.get(Calendar.DATE);					//获取日
        int hour = calendar.get(Calendar.HOUR_OF_DAY);				//时（24小时制）
        int minute = calendar.get(Calendar.MINUTE);					//分
        int second = calendar.get(Calendar.SECOND);					//秒
        String min = minute<10?"0"+minute:""+minute;
        return year+"年"+month+"月"+day+"日 "+hour+":"+min;
    }

}
