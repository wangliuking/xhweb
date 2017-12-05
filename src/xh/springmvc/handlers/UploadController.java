package xh.springmvc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import xh.func.plugin.DownLoadUtils;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.ProgressBean;
import xh.org.listeners.FileUploadProgressListener;

@Controller
@RequestMapping(value = "/uploadFile")
public class UploadController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(UploadController.class);
	private FlexJSON json = new FlexJSON();

	@RequestMapping(value = "/fileExists", method = RequestMethod.POST)
	public void fileExists(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String filePath = request.getParameter("filePath");
		String path = request.getSession().getServletContext()
				.getRealPath(filePath);

		File file = new File(path);
		if (!file.exists()) {
			this.success = false;
			this.message = "文件不存在";
		} else {
			this.success = true;
		}
		HashMap result = new HashMap();
		result.put("success", success);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("/upload")
	@ResponseBody
	public void fileUpload(@RequestParam("pathName") CommonsMultipartFile file,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		

		String path = request.getSession().getServletContext().getRealPath("")
				+ "/Resources/upload/";
		String name = file.getOriginalFilename();
		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String[] data = sdf.format(d).split(" ")[0].split("-");
		path += data[0] + "/" + data[1] + "/" + data[2];
		String savePath = "/Resources/upload/" + data[0] + "/" + data[1] + "/"+ data[2];

		
	
		if(uploadFile(request,file,path)){
			this.success = true;
			this.message = "文件上传成功";
		}else{
			this.success = false;
			this.message = "文件上传失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", name);
		result.put("filePath", savePath + "/" + name);
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

	/**
	 * 下载文件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String filePath = request.getParameter("filePath");
		String fileName = request.getParameter("fileName");
		String path = request.getSession().getServletContext()
				.getRealPath(filePath);

		String downPath = path;
		log.info("下载连接："+path);

		File file = new File(downPath);
		if (!file.exists()) {
			this.success = false;
			this.message = "文件不存在";
		}
		// 设置响应头和客户端保存文件名
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ new String(fileName.getBytes("gbk"), "iso-8859-1"));
		// 用于记录以完成的下载的数据量，单位是byte
		long downloadedLength = 0l;
		try {
			// 打开本地文件流
			InputStream inputStream = new FileInputStream(downPath);
			// 激活下载操作
			OutputStream os = response.getOutputStream();

			// 循环写入输出流
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
				downloadedLength += b.length;
			}

			// 这里主要关闭。
			os.close();
			inputStream.close();
		} catch (Exception e) {
			throw e;
		}
		// 存储记录
	}
/*	public void downFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String filePath = request.getParameter("filePath");
		String fileName = request.getParameter("fileName");
		String path = request
				.getSession()
				.getServletContext()
				.getRealPath(
						new String(filePath.getBytes("iso-8859-1"), "utf-8"));

		String downPath = path;

		File file = new File(downPath);
		if (!file.exists()) {
			this.success = false;
			this.message = "文件不存在";
		}
		// 设置响应头和客户端保存文件名
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader(
				"Content-Disposition",
				"attachment;fileName="
						+ DownLoadUtils.getName(
								request.getHeader("user-agent"), new String(
										fileName.getBytes("iso-8859-1"),
										"utf-8")));
		// 用于记录以完成的下载的数据量，单位是byte
		long downloadedLength = 0l;
		try {
			// 打开本地文件流
			InputStream inputStream = new FileInputStream(downPath);
			// 激活下载操作
			OutputStream os = response.getOutputStream();

			// 循环写入输出流
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
				downloadedLength += b.length;
			}

			// 这里主要关闭。
			os.close();
			inputStream.close();
		} catch (Exception e) {
			throw e;
		}
		// 存储记录
	}*/

	@RequestMapping(value = "/downfile", method = RequestMethod.GET)
	public void downFile2(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String filePath = request.getParameter("filePath");
		String fileName = request.getParameter("fileName");
		String path = request.getSession().getServletContext()
				.getRealPath(filePath);

		String downPath = path;

		File file = new File(downPath);
		if (!file.exists()) {
			this.success = false;
			this.message = "文件不存在";
		}
		// 设置响应头和客户端保存文件名
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ new String(fileName.getBytes("gbk"), "iso-8859-1"));
		// 用于记录以完成的下载的数据量，单位是byte
		long downloadedLength = 0l;
		try {
			// 打开本地文件流
			InputStream inputStream = new FileInputStream(downPath);
			// 激活下载操作
			OutputStream os = response.getOutputStream();

			// 循环写入输出流
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
				downloadedLength += b.length;
			}

			// 这里主要关闭。
			os.close();
			inputStream.close();
		} catch (Exception e) {
			throw e;
		}
		// 存储记录
	}

	@RequestMapping(value = "/progress", method = RequestMethod.GET)
	public void progress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		ProgressBean bean = (ProgressBean) session.getAttribute("status");
		log.info("文件上传进度：" + bean.toString());
		HashMap result = new HashMap();
		result.put("pBytesRead", bean.getBytesRead());
		result.put("pContentLength", bean.getContentLength());
		result.put("pItems", bean.getItems());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Boolean uploadFile(HttpServletRequest request, MultipartFile file,String filePath) {
		String fileName = file.getOriginalFilename();
		File targetFile = new File(filePath, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
