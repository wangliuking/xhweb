package xh.mybatis.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.web.multipart.MultipartFile;

import xh.func.plugin.ReadExcel;
import xh.func.plugin.ReadExcel1;
import xh.mybatis.bean.ImpExcelBean;
import xh.mybatis.bean.TempBean;
import xh.mybatis.mapper.BsstationMapper;
import xh.mybatis.mapper.ExcelImportMapper;
import xh.mybatis.tools.MoreDbTools;

public class ExcelService {
	/** 
     * 读取excel中的数据,生成list 
     */  
    public String readExcelFile(MultipartFile file,String time,String bsId){
    	String result ="";  
        //创建处理EXCEL的类  
        ReadExcel readExcel=new ReadExcel();  
    	//ReadExcel1 readExcel=new ReadExcel1();
        //解析excel，获取上传的事件单  
        //List<TempBean> excelList = readExcel.getExcelInfo(file);
        List<ImpExcelBean> excelList = readExcel.getExcelInfo(file);
        for(int i=0;i<excelList.size();i++){
        	excelList.get(i).setTime(time);
        	excelList.get(i).setBsId(bsId);
        }
        //System.out.println(excelList);
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
}
