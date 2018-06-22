package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.OndutyBean;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.OndutyService;
import xh.mybatis.service.WebLogService;

@Controller
@RequestMapping("/onduty")
public class OndutyController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(OndutyController.class);
	private FlexJSON json=new FlexJSON();
	
	
	@RequestMapping(value="/dutyinfo",method = RequestMethod.GET)
	public void dutyinfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		HashMap result = new HashMap();
		result.put("leader",FunUtil.readXml("duty","one"));
		result.put("tel", FunUtil.readXml("duty","three"));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@SuppressWarnings({ "static-access", "unchecked" })
	@RequestMapping(value="/updutyinfo",method = RequestMethod.POST)
	public void updutyinfo(HttpServletRequest request, HttpServletResponse response){
		String leader=request.getParameter("leader");
		String tel=request.getParameter("tel");
		try {
			funUtil.updateXML("duty","one", leader);
			funUtil.updateXML("duty","three", tel);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HashMap result = new HashMap();
		result.put("success",true);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * 值班记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void duty_list(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String starttime=request.getParameter("starttime");
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("time", starttime);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",OndutyService.count(map));
		result.put("items", OndutyService.duty_list(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@RequestMapping(value="/onduty",method = RequestMethod.GET)
	public void onduty(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		Map<String, Object> map=OndutyService.onduty();
		if(map==null){
			map=new HashMap<String, Object>();
			map.put("one",FunUtil.readXml("duty", "one"));
			map.put("two", "");
			map.put("three", FunUtil.readXml("duty", "three"));
		}else{
			String time=FunUtil.nowDate().split(" ")[1];
			int day=FunUtil.StringToInt(time.split(":")[0]);
			
			if(day>=9 && day<=17){
				map.put("two", map.get("onePerson").toString());
			}else{
				map.put("two",map.get("twoPerson").toString());
			}
			
			map.put("one",FunUtil.readXml("duty", "one"));
			
			map.put("three", FunUtil.readXml("duty", "three"));
		}
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(map);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping("/excel")
	@ResponseBody
	public void excel(@RequestParam("pathName") CommonsMultipartFile file,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/Resources/upload/duty/";
		String name = file.getOriginalFilename();
		String fileName = file.getOriginalFilename();
		List<OndutyBean> list=new ArrayList<OndutyBean>();
		File targetFile = new File(path, fileName);
		
		
		
		
		int r=0;
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			
			list=getDataByExcel(path+fileName);
			System.out.println("文件路径szie-----"+list.size());
			System.out.println("文件路径rrr-----"+Arrays.toString(list.toArray()));
			r=OndutyService.write_data(list);
			System.out.println("文件路径r-----"+r);
			
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		if(r>0){
			success=true;
		}else{
			success=false;
		}

		HashMap result = new HashMap();
		result.put("success", success);
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
	 public  List<OndutyBean> getDataByExcel(String  path){
	        List<OndutyBean> list=new ArrayList<OndutyBean>();
	        try {
	            Workbook rwb=Workbook.getWorkbook(new File(path));
	            File file = new File(path);
	            if (!file.exists()) {
	            	System.out.println("文件路径rdddd-----文件不存在存在");
	    		} else {
	    			System.out.println("文件路径rdddd-----文件存在");
	    		}
	            
	            
	          //取得第一个sheet  
	            int sheet=rwb.getSheets().length;	            
	            for(int a=0;a<sheet;a++){	            	
	          
	            Sheet rs=rwb.getSheet(a); 
	            
	            int clos=rs.getColumns();//得到所有的列
	            int rows=rs.getRows();//得到所有的行
	            
	            System.out.println("文件解析clos-----"+clos);
	            System.out.println("文件解析rows-----"+rows);
	            
	            for(int i = 1; i < rows; i++) {  
	                Cell [] cell = rs.getRow(i);  
	                int cellLen=cell.length;
	                if(cellLen>1){
	                	for(int j=0; j<3; j++) {  
	                        //getCell(列，行)  
	                        //out.print(sheet.getCell(j, i).getContents());  
	                		OndutyBean bean=new OndutyBean();
	                		bean.setDutyDate(rs.getCell(j++, i).getContents());
	                		bean.setOnePerson(rs.getCell(j++, i).getContents());
	                		bean.setTwoPerson(rs.getCell(j++, i).getContents());
	                		list.add(bean);
	                    }
	                	
	                }
	                 
	            }  
	            }
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            //e.printStackTrace();
	        	System.out.println("导入excel出错");
	        	e.printStackTrace();
	        } 
	        return list;
	        
	    }

}
