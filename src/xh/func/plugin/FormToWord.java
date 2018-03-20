package xh.func.plugin;
 
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FormToWord {
	private Configuration configuration = null;
	
	public FormToWord(){
		configuration = new Configuration();  
        configuration.setDefaultEncoding("UTF-8"); 
	}
	
	public String fillWord(Object obj,HttpServletRequest request,String templateName){  
        Map<String,Object> dataMap= getData(obj);
        //configuration.setClassForTemplateLoading(this.getClass(), request.getSession().getServletContext().getRealPath("/Resources/template"));//模板文件所在路径
        configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/Resources/template");
        Template t=null;  
        try {  
            t = configuration.getTemplate(templateName); //获取模板文件
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        String outFilePath = request.getSession().getServletContext()
				.getRealPath("")+"/Resources/outputDoc/";
        String outputFileName = Math.random()*10000+".doc";
        File outFile = new File(outFilePath + outputFileName); //导出文件
        Writer out = null;  
        try {  
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));  
        } catch (FileNotFoundException e1) {  
            e1.printStackTrace();  
        }  
           
        try {
        	//将填充数据填入模板文件并输出到目标文件 
            t.process(dataMap, out); 
            return outputFileName;
        } catch (TemplateException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        return "false";
    }  
  
    private Map<String, Object> getData(Object obj) {  
    	if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
    }  
}
