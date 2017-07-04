package xh.springmvc.handlers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.Monthreport;
import xh.mybatis.service.MonthreportService;

@Controller
@RequestMapping(value = "/monthreport")
public class MonthreportController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(MonthreportController.class);
	private FlexJSON json = new FlexJSON();

	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void info(HttpServletRequest request, HttpServletResponse response) {
		this.success = true;
		String filename = request.getParameter("filename");
		String contact = request.getParameter("contact");
		String status = request.getParameter("status");
		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("filename", filename);
		map.put("contact", contact);
		map.put("status", status);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", MonthreportService.Count(map));
		result.put("items", MonthreportService.radioUserBusinessInfo(map));
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
	 * ajax文件上传
	 * 
	 * @param file
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public String fileUpload(@RequestParam("pathName") MultipartFile file,
			HttpSession session, HttpServletRequest request) throws IOException {
		String name = file.getOriginalFilename();
		String temp = null;
		String dateNowStr = null;
		try {
			// 获取当前时间
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dateNowStr = sdf.format(d);
			// 获取项目路径
			String str = this.getClass().getClassLoader().getResource("")
					.toURI().getPath();
			temp = str.substring(1, str.length() - 17) + "/Resources/data/"
					+ dateNowStr;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 判断是否有相同日期下的文件夹
		File filetemp = new File(temp);
		File filename = new File(temp + "/" + name);
		// 如果文件夹不存在则创建
		if (!filetemp.exists() && !filetemp.isDirectory()) {
			System.out.println("//不存在");
			filetemp.mkdir();
		} else {
			if (filename.exists()) {
				return "0";
			}
		}
		String fileName = imgsUpload(file, session, "/Resources/data/"
				+ dateNowStr);
		return name;
	}

	/**
	 * 文件上传公共方法
	 * 
	 * @param file
	 * @param session
	 * @return
	 * @throws IOException
	 */
	private String imgsUpload(MultipartFile file, HttpSession session,
			String savePath) throws IOException {
		// 获取文件在服务器的存储路径
		String path = session.getServletContext().getRealPath(savePath);
		// 获取上传文件的名称
		String fileName = file.getOriginalFilename();
		// 进行文件存储
		file.transferTo(new File(path, fileName));
		return savePath + fileName;
	}

	/**
	 * 文件下载方法
	 */
	@RequestMapping("/download")
	public void download(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String temp = request.getParameter("path");
			String str1=temp;
			String path=new String(str1.getBytes("ISO-8859-1"),"utf-8"); //转码UTF8
			String str = this.getClass().getClassLoader().getResource("")
					.toURI().getPath();
			// 截取字符串
			String strTemp = str.substring(0, str.length() - 17);
			String filePath = strTemp + path; // 文件在项目中的路径
			File outfile = new File(filePath);
			String filename = outfile.getName();// 获取文件名称
			InputStream fis = new BufferedInputStream(new FileInputStream(
					filePath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer); // 读取文件流
			fis.close();
			response.reset(); // 重置结果集
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(
							filename.replaceAll(" ", "").getBytes("utf-8"),
							"iso8859-1")); // 返回头 文件名
			response.addHeader("Content-Length", "" + outfile.length()); // 返回头
																			// 文件大小
			response.setContentType("application/octet-stream"); // 设置数据种类
			// 获取返回体输出权
			OutputStream os = new BufferedOutputStream(
					response.getOutputStream());
			os.write(buffer); // 输出文件
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加值班信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/add")
	public void insertRadioUser(Monthreport record, HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		String name = record.getFilepath();
		if(name!=null){
			if(name.length()==0){
				record.setFilepath("null");
			}else{
				record.setFilepath("/Resources/data/" + dateNowStr + "/" + name);
			}		
		}else if(name==null || name==""){
			record.setFilepath("null");
		}
		record.setStatus(0);
		int count = MonthreportService.insert(record);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", count);
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
