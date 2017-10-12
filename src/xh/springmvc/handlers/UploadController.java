package xh.springmvc.handlers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;

@Controller
@RequestMapping(value = "/uploadFile")
public class UploadController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(UploadController.class);
	private FlexJSON json = new FlexJSON();

	@RequestMapping("/upload")
	@ResponseBody
	public void fileUpload(@RequestParam("pathName") MultipartFile file,
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
		String savePath="/Resources/upload/"+data[0] + "/" + data[1] + "/" + data[2];
		// 判断是否有相同日期下的文件夹

		File targetFile = new File(path, name);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			this.success = true;
			this.message = "文件上传成功";

		} catch (Exception e) {
			e.printStackTrace();
			this.success=false;
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

}
