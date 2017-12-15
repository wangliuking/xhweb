package xh.mybatis.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.web.multipart.MultipartFile;

import xh.func.plugin.ReadExcel;
import xh.func.plugin.ReadExcel1;
import xh.mybatis.bean.ImpExcelBean;
import xh.mybatis.bean.OtherBean;
import xh.mybatis.bean.TempBean;
import xh.mybatis.mapper.BsstationMapper;
import xh.mybatis.mapper.ExcelImportMapper;
import xh.mybatis.tools.MoreDbTools;

public class ExcelService {
	/** 
     * 读取excel中的数据,生成list 
     */  
    public String readExcelFile(MultipartFile file,String time,String bsId){
    	/**
    	 * 路测录入
    	 */
    	String result ="";  
    	 //创建处理EXCEL的类 
    	ReadExcel readExcel=new ReadExcel();
        List<ImpExcelBean> excelList = readExcel.getExcelInfo(file);
        for(int i=0;i<excelList.size();i++){
        	excelList.get(i).setTime(time);
        	excelList.get(i).setBsId(bsId);
        }
      //至此已经将excel中的数据转换到list里面了,接下来就可以操作list,可以进行保存到数据库,或者其他操作 
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        ExcelImportMapper mapper = sqlSession.getMapper(ExcelImportMapper.class);
		try {
			mapper.insertExcel(excelList);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
        if(excelList != null && !excelList.isEmpty()){  
            result = "success!";  
        }else{  
            result = "failure!";  
        }  
        return result;
    }
    
    
    /**
     * 其他数据录入
     */
    public String readExcelFileOther(MultipartFile file){
    	String result ="";  
    	 //创建处理EXCEL的类 
    	ReadExcel1 readExcel=new ReadExcel1();
        List excelList = readExcel.getExcelInfo(file);
        String finalStr = "";
        for(int i=0;i<excelList.size();i++){
        	List l = (List) excelList.get(i);
        	String a = (String) l.get(0);
        	String c = (String) l.get(1);
        	//String b = "'" + c +"'";
        	String b = "";
        	if("移动/正常".equals(c) || "自备/正常".equals(c) || "自备/正常".equals(c) || "移动/异常".equals(c) || "自备/正常".equals(c)){
        		b="1";
        	}else{
        		b="null";
        	}
        	/*String b = "";
        	if("楼顶铁塔".equals(c)){
        		b = "0";
        	}else if("抱杆".equals(c)){
        		b = "1";
        	}else if("地面铁塔".equals(c)){
        		b = "2";
        	}*/
        	
        	finalStr = finalStr+" WHEN "+a+" THEN "+b;
        }
        writeStringToFile(finalStr);        
        return result;
    }
    
    /**
     * 输出到txt
     * @param filePath
     */
    public void writeStringToFile(String str) {
        try {
        	//中文输出
            FileOutputStream fos = new FileOutputStream("D://test.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            osw.write(str);
            osw.flush();
            osw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
