package xh.mybatis.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.web.multipart.MultipartFile;

import xh.func.plugin.ReadExcel;
import xh.mybatis.bean.ImpExcelBean;
import xh.mybatis.mapper.BsstationMapper;
import xh.mybatis.mapper.ExcelImportMapper;
import xh.mybatis.tools.MoreDbTools;

public class ExcelService {
	/** 
     * 读取excel中的数据,生成list 
     */  
    public String readExcelFile(MultipartFile file){
    	String result ="";  
        //创建处理EXCEL的类  
        ReadExcel readExcel=new ReadExcel();  
        //解析excel，获取上传的事件单  
        List<ImpExcelBean> excelList = readExcel.getExcelInfo(file);  
        System.out.println(excelList);
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
        //和你具体业务有关,这里不做具体的示范  
        if(excelList != null && !excelList.isEmpty()){  
            result = "上传成功";  
        }else{  
            result = "上传失败";  
        }  
        return result;
    }
}
