package xh.mybatis.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.MeetBean;

import com.zhuozhengsoft.pageoffice.DocumentOpenType;
import com.zhuozhengsoft.pageoffice.FileMakerCtrl;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordwriter.DataRegion;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;

public class PageOfficeService {
	
	public static void meet(MeetBean bean,HttpServletRequest request) {
		String path=request.getSession().getServletContext().getRealPath("")+"/doc/meet.doc";
		String savePath=request.getSession().getServletContext().getRealPath("doc/meet/");
		savePath +=FunUtil.nowDateNotTime().split("-")[0];	;
		
		System.out.println("地址："+savePath);
		String filePath=savePath+"/"+bean.getFile_name();
		File file=new File(savePath);
		if(!file.exists()){
			file.mkdirs();
		}
		File source=new File(path);
		File dst=new File(filePath);
		try {
			FunUtil.copyFile(source, dst);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
