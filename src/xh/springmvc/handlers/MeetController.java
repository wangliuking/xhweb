package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordreader.DataRegion;
import com.zhuozhengsoft.pageoffice.wordreader.WordDocument;

import xh.constant.ConstantLog;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.MeetBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.MeetServices;
import xh.mybatis.service.PageOfficeService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.service.WorkServices;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping("/meet")
public class MeetController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(MeetController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();
	
	@RequestMapping("/meetlist")
	@ResponseBody
	public HashMap meetlist(HttpServletRequest request, HttpServletResponse response){
		String time=request.getParameter("time");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		int roleType=(Integer) FunUtil.loginUserInfo(request).get("roleType");
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		map.put("start", start);
		map.put("limit", limit);
		map.put("roleType", roleType);

		HashMap result = new HashMap();
		result.put("totals",MeetServices.meetcount(map));
		result.put("items",MeetServices.meetlist(map));
		return result;		
	}
	@RequestMapping("/save_data")
	public void savedata(HttpServletRequest request,
			HttpServletResponse response) {
		
		WordDocument doc = new WordDocument(request, response);
		//获取提交的数值
		DataRegion a = doc.openDataRegion("PO_name");
		DataRegion b = doc.openDataRegion("PO_time");
		DataRegion c = doc.openDataRegion("PO_address");
		DataRegion d = doc.openDataRegion("PO_person");
		DataRegion e = doc.openDataRegion("PO_content");
		System.out.println("data->"+a.getValue());
		System.out.println("data->"+e.getValue());
		MeetBean bean=new MeetBean();
		bean.setName(a.getValue());
		bean.setStart_time(FunUtil.nowDate());
		//bean.setEnd_time(b.getValue());
		bean.setAddress(c.getValue());
		bean.setPerson(d.getValue().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		bean.setPerson(d.getValue().replaceAll(" ", "&nbsp;"));
		bean.setContent(e.getValue());
		System.out.println("bb->"+bean.toString());
		int rs=MeetServices.add(bean);
		if(rs>0){
			this.message="添加会议纪要成功";
			this.success=true;
			FunUtil.WriteLog(request, ConstantLog.ADD, "添加会议纪要："+bean.getStart_time());
		}else{
			this.message="添加失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		
		doc.close();
		
	}
	@RequestMapping("/save_page")
	public void savepage(HttpServletRequest request,
			HttpServletResponse response) {		
		String path=request.getParameter("path");
		System.out.println("path->"+path);
		FileSaver fs = new FileSaver(request, response);
		fs.saveToFile(path);
		fs.close();
		
		System.out.println("file->"+fs.getFileName());
	}
	@RequestMapping("/add")
	@ResponseBody
	public HashMap add(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		MeetBean bean=GsonUtil.json2Object(formData, MeetBean.class);
		MeetBean bean2=GsonUtil.json2Object(formData, MeetBean.class);
		
		int type=Integer.parseInt(FunUtil.loginUserInfo(request).get("roleType").toString());
		
		bean.setStart_time(FunUtil.nowDate());
		bean.setType(Integer.parseInt(FunUtil.loginUserInfo(request).get("roleType").toString()));
		bean.setPerson(bean.getPerson().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		bean.setPerson(bean.getPerson().replaceAll(" ", "&nbsp;"));
		bean.setContent(bean.getPerson().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		bean.setContent(bean.getPerson().replaceAll(" ", "&nbsp;"));
		
		String savePath="doc/meet/"+FunUtil.nowDateNotTime().split("-")[0];	
		String fileName=FunUtil.MD5(String.valueOf(new Date().getTime()))+".doc";
		String filePath=savePath+"/"+fileName;
		bean.setFile_name(fileName);
		bean.setFile_path(filePath);
		
		bean2.setFile_name(fileName);
		bean2.setFile_path(filePath);
		
		bean.setSend_user(FunUtil.loginUser(request));
		int rs=MeetServices.add(bean);
		if(rs>0){
			this.message="添加会议纪要成功";
			this.success=true;
			FunUtil.WriteLog(request, ConstantLog.ADD, "添加会议纪要："+bean.getStart_time());
			createFile(bean2,request);
			if(type==2){
				FunUtil.sendMsgToUserByPower("o_check_report", 3, "会议纪要", "你有新的会议纪要需要处理", request);
			}else{
				FunUtil.sendMsgToUserByPower("o_check_report", 2, "会议纪要", "你有新的会议纪要需要处理", request);
			}
			
		}else{
			this.message="添加失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		result.put("bean",bean2);
		return result;		
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public HashMap update(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		MeetBean bean=GsonUtil.json2Object(formData, MeetBean.class);
		MeetBean bean2=GsonUtil.json2Object(formData, MeetBean.class);
		bean.setPerson(bean.getPerson().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		bean.setPerson(bean.getPerson().replaceAll(" ", "&nbsp;"));
		bean.setContent(bean.getPerson().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		bean.setContent(bean.getPerson().replaceAll(" ", "&nbsp;"));
		int rs=MeetServices.update(bean);
		if(rs>0){
			this.message="修改会议纪要成功";
			this.success=true;
			FunUtil.WriteLog(request, ConstantLog.UPDATE, "修改会议纪要："+bean.getStart_time());
		}else{
			this.message="修改失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		result.put("bean",bean2);
		return result;		
	}
	@RequestMapping("/check")
	@ResponseBody
	public HashMap check(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		MeetBean bean=GsonUtil.json2Object(formData, MeetBean.class);
		bean.setCheck_time(FunUtil.nowDate());
		bean.setCheck_user1(FunUtil.loginUser(request));
		int rs=MeetServices.check(bean);
		if(rs>0){
			this.message="审核成功";
			this.success=true;
			FunUtil.WriteLog(request, ConstantLog.UPDATE, "审核会议纪要："+bean.getStart_time());
			if(bean.getState()==-1){
				FunUtil.sendMsgToOneUser(bean.getSend_user(), "会议纪要", "会议纪要被拒绝："+bean.getNote(), request);
			}else{
				String roleType=FunUtil.loginUserInfo(request).get("roleType").toString();
				if(roleType.equals("2")){
					FunUtil.sendMsgToUserByPower("o_check_report", 2, "会议纪要", "你有新的会议纪要，请签章", request);
				}else{
					FunUtil.sendMsgToUserByPower("o_check_report", 3, "会议纪要", "你有新的会议纪要，请签章", request);
				}
				
			}
		}else{
			this.message="审核失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		return result;		
	}
	@RequestMapping("/check2")
	@ResponseBody
	public HashMap check2(HttpServletRequest request, HttpServletResponse response){
		String formData=request.getParameter("formData");
		MeetBean bean=GsonUtil.json2Object(formData, MeetBean.class);
		bean.setCheck_time2(FunUtil.nowDate());
		bean.setCheck_user2(FunUtil.loginUser(request));
		int rs=MeetServices.check2(bean);
		if(rs>0){
			this.message="签字成功";
			this.success=true;
			FunUtil.WriteLog(request, ConstantLog.UPDATE, "签字会议纪要："+bean.getStart_time());
			FunUtil.sendMsgToOneUser(bean.getSend_user(), "会议纪要", "服务提供方已签字会议纪要", request);
			FunUtil.sendMsgToOneUser(bean.getSend_user(), "会议纪要", "服务提供方已签字会议纪要", request);
		}else{
			this.message="签字失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		return result;		
	}
	@RequestMapping("/del")
	@ResponseBody
	public HashMap del(HttpServletRequest request, HttpServletResponse response){
		String[] ids=request.getParameter("id").split(",");
		List<String> list=new ArrayList<String>();
		for (String string : ids) {
			list.add(string);
		}
		int rs=MeetServices.del(list);
		if(rs>0){
			this.message="删除会议纪要成功";
			this.success=true;
			FunUtil.WriteLog(request, ConstantLog.DELETE, "删除会议纪要："+ids);
		}else{
			this.message="删除失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
		return result;		
	}
/*	@RequestMapping("/seal")
	public ModelAndView seal(HttpServletRequest request,
			HttpServletResponse response) {
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		String path=request.getParameter("path");
		poCtrl.setServerPage(request.getContextPath() +"/poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
		poCtrl.addCustomToolButton("拒绝会议纪要", "check()", 1); // 添加自定义按钮
		poCtrl.addCustomToolButton("签字", "Seal()", 2);
		poCtrl.addCustomToolButton("删除签字", "DeleteSeal()", 21);
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setJsFunction_AfterDocumentOpened("IsFullScreen()");
		poCtrl.setTitlebar(false); //隐藏标题栏
		String name="";
		try {
			name=URLEncoder.encode(path,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		poCtrl.setSaveFilePage(request.getContextPath() +"/office/save_page?path="+name);
		poCtrl.webOpen(request.getSession().getServletContext().getRealPath(path), OpenModeType.docNormalEdit, "");
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/meet");
		mv.addObject("sealName",WebUserServices.sealName(FunUtil.loginUser(request),"2"));
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}*/
	@RequestMapping("/seal")
	public ModelAndView seal(HttpServletRequest request,
			HttpServletResponse response) {
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		String path=request.getParameter("path");
		int type=-1;
		if(request.getParameter("type")!=null){
			type=Integer.parseInt(request.getParameter("type"));
		}
		if(path.indexOf("/")==0){
			path=path.substring(1);
		}
		poCtrl.setServerPage(request.getContextPath() +"/poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
		poCtrl.addCustomToolButton("签字", "Seal1()", 2);
		poCtrl.addCustomToolButton("签章", "Seal2()", 2);
		/*poCtrl.addCustomToolButton("删除签字", "DeleteSeal1()", 21);
		poCtrl.addCustomToolButton("删除签章", "DeleteSeal2()", 21);*/
		
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		poCtrl.setJsFunction_AfterDocumentOpened("IsFullScreen()");
		poCtrl.setTitlebar(false); //隐藏标题栏
		String name="";
		try {
			name=URLEncoder.encode(path,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		poCtrl.setSaveFilePage(request.getContextPath() +"/office/save_page?path="+name);
		poCtrl.webOpen(request.getSession().getServletContext().getRealPath("/")+"/"+path, OpenModeType.docNormalEdit, "");
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/meet");
		mv.addObject("sealName1",WebUserServices.sealName(FunUtil.loginUser(request),"2"));
		mv.addObject("sealName2",WebUserServices.sealName(FunUtil.loginUser(request),"1"));
		mv.addObject("type",type);
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}
	public void createFile(MeetBean bean,HttpServletRequest request) {
		String path=request.getSession().getServletContext().getRealPath("")+"/doc/template/meet.doc";
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
