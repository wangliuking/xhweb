package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import org.springframework.web.multipart.MultipartFile;

import com.google.gson.reflect.TypeToken;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.RecordTrainBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.OperationsCheckService;
import xh.mybatis.service.RecordTrainService;


@Controller
@RequestMapping("/recordtrain")
public class RecordTrainController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(RecordTrainController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	@RequestMapping(value="/data_all", method = RequestMethod.GET)
	@ResponseBody
	public HashMap data_all(HttpServletRequest request, HttpServletResponse response) {
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		String time=request.getParameter("time");
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time",time);
		HashMap result = new HashMap();
		result.put("items",RecordTrainService.data_all(map));
		result.put("totals", RecordTrainService.data_all_count(map));		
		return result;
	}
	@RequestMapping(value="/add", method = RequestMethod.POST)
	@ResponseBody
	public HashMap add(@RequestBody RecordTrainBean bean) {
		
		System.out.println("bean-->"+bean.toString());
		int rs=RecordTrainService.add(bean);
		if(rs>0){
			this.message="添加成功";
			this.success=true;
		}else{
			this.message="添加失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);		
		return result;
	}
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public HashMap update(@RequestBody RecordTrainBean bean) {
		int rs=RecordTrainService.update(bean);
		if(rs>0){
			this.message="修改成功";
			this.success=true;
		}else{
			this.message="失败";
			this.success=false;
		}
		
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);		
		return result;
	}
	@RequestMapping(value="/del", method = RequestMethod.POST)
	@ResponseBody
	public HashMap del(HttpServletRequest request, HttpServletResponse response) {
		String[] id=request.getParameter("id").split(",");
		List<String> list=new ArrayList<String>();
		for (String string : id) {
			list.add(string);
		}
		
		int rs=RecordTrainService.del(list);
		if(rs>0){
			this.message="删除成功";
			this.success=true;
		}else{
			this.message="失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);		
		return result;
	}
	@RequestMapping(value="/addfile", method = RequestMethod.POST)
	@ResponseBody
	public HashMap addfile(HttpServletRequest request, HttpServletResponse response) {
		int id=Integer.parseInt(request.getParameter("id"));
		String files=request.getParameter("files");
		List<Map<String,Object>> filelist=new ArrayList<Map<String,Object>>();
		Type type = new TypeToken<ArrayList<Map<String,Object>>>() {}.getType(); 
		filelist=GsonUtil.json2Object(files, type);
		int rs=0;
		if(filelist.size()>0){
			for(int i=0;i<filelist.size();i++){
				Map<String,Object> map=filelist.get(i);
				map.put("id", id);
				filelist.set(i, map);
			}
			rs=RecordTrainService.addFile(filelist);
		}
		if(rs>0){
			this.message="成功";
			this.success=true;
		}else{
			this.message="失败";
			this.success=false;
		}
		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message", message);		
		return result;
	}
	@RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
	public void batch_upload(@RequestParam("pathName") MultipartFile[] file,
			HttpServletRequest request, HttpServletResponse response) {
		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String date=sdf.format(d);
		String savePath="/upload/train/"+date.split("-")[0];
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/upload/train/"+date.split("-")[0];
		List<Map<String,Object>> rs=new ArrayList<Map<String,Object>>();
		if(!(file.length==0)){
			
			for(int i=0;i<file.length;i++){
				String fileName = file[i].getOriginalFilename();
				System.out.println("文件名："+fileName);
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();

				}
				// 保存
				try {
					file[i].transferTo(targetFile);
					long time=new Date().getTime();
					String newName=time+fileName.substring(fileName.lastIndexOf("."));
					FunUtil.renameFile(path, fileName, newName);
					this.success = true;
					this.message = "文件上传成功";
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("fileName", fileName);
					map.put("filePath", savePath + "/" + newName);
					map.put("index", i+1);
					map.put("size", file[i].getSize());
					rs.add(map);

				} catch (Exception e) {
					e.printStackTrace();
					this.message = "文件上传失败";
				}
			}
			
			
		}
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("rs", rs);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		log.debug(jsonstr);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
