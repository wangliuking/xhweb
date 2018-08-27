package xh.springmvc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;

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
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.AssetAddApplayInfoBean;
import xh.mybatis.bean.AssetAddApplyBean;
import xh.mybatis.bean.AssetInfoBean;
import xh.mybatis.bean.AssetScrapApplayInfoBean;
import xh.mybatis.bean.AssetScrapApplyBean;
import xh.mybatis.bean.AssetScrapInfoBean;
import xh.mybatis.bean.AssetTransferBean;
import xh.mybatis.bean.AssetUpdateStatusApplyBean;
import xh.mybatis.bean.AssetUpdateStatusAttrBean;
import xh.mybatis.bean.AssetUpdateStatusInfoBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BusinessService;
import xh.mybatis.service.WebLogService;

@Controller
@RequestMapping("/business")
public class BusinessController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	protected final Log log = LogFactory.getLog(BusinessController.class);
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	/**
	 * 查询资产记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/assetList", method = RequestMethod.GET)
	public void assetInfo(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int type = funUtil.StringToInt(request.getParameter("type"));
		int isLock = funUtil.StringToInt(request.getParameter("isLock"));
		int tag = funUtil.StringToInt(request.getParameter("tag"));
		String name = request.getParameter("name");
		String model = request.getParameter("model");
		String serialNumber = request.getParameter("serialNumber");
		String applyTag = request.getParameter("applyTag");
		String user = request.getParameter("user") == null ? FunUtil
				.loginUser(request) : request.getParameter("user");
		int from = funUtil.StringToInt(request.getParameter("from"));
		int status = funUtil.StringToInt(request.getParameter("status"));
		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("name", name);
		map.put("model", model);
		map.put("serialNumber", serialNumber);
		map.put("from", from);
		map.put("isLock", isLock);
		map.put("tag", tag);
		map.put("applyTag", applyTag);
		map.put("user", user);
		map.put("status", status);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", BusinessService.assetInfoCount(map));
		result.put("items", BusinessService.assetInfo(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 资产报废申请明细 */
	@RequestMapping(value = "/asset_scrap_info", method = RequestMethod.GET)
	public void asset_scrap_info(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int isLock = funUtil.StringToInt(request.getParameter("isLock"));
		int tag = funUtil.StringToInt(request.getParameter("tag"));
		String applyTag = request.getParameter("applyTag");
		String user = request.getParameter("user") == null ? FunUtil
				.loginUser(request) : request.getParameter("user");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isLock", isLock);
		map.put("tag", tag);
		map.put("applyTag", applyTag);
		map.put("user", user);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", BusinessService.asset_scrap_info(map).size());
		result.put("items", BusinessService.asset_scrap_info(map));
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
	 * 按资产状态统计
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/allAssetStatus", method = RequestMethod.GET)
	public void allAssetStatus(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		HashMap result = new HashMap();
		List<HashMap<String, Integer>> list = BusinessService.allAssetStatus();
		List<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>();
		int totals = 0;
		for(int i=1;i<8;i++){
			int tag=0;
			for (HashMap<String, Integer> hashMap : list) {
				if(hashMap.get("status")==i){
					tag=1;
				}
			}
			if(tag==0){
				HashMap<String, Integer> map = new HashMap<String,Integer>();
				map.put("status", i);
				map.put("number",0);
				list.add(map);
			}else{
				tag=0;
			}
		}
		
		for (HashMap<String, Integer> hashMap : list) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			String name = "";
			if (hashMap.get("status") == 1) {
				name = "外借";
			} else if (hashMap.get("status") == 2) {
				name = "报废";
			} else if (hashMap.get("status") == 3) {
				name = "维修";
			} else if (hashMap.get("status") == 4) {
				name = "在库";
			} else if (hashMap.get("status") == 5) {
				name = "在用";
			} else if (hashMap.get("status") == 6) {
				name = "待报废";
			} else if (hashMap.get("status") == 7) {
				name = "遗失";
			} else {
				name = "未知";
			}
			map.put("name", name);
			map.put("value", hashMap.get("number"));
			list2.add(map);
		}
		
		
		
		
		result.put("success", success);
		result.put("items", list2);
		result.put("totals", 10);
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
	 * 按资产名称统计
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/allAssetNameCount", method = RequestMethod.GET)
	public void allAssetNameCount(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", BusinessService.allAssetNameCount());
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
	 * 按资产类型统计
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/allAssetType", method = RequestMethod.GET)
	public void allAssetType(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		/*
		 * int type=funUtil.StringToInt(request.getParameter("type")); String
		 * name=request.getParameter("name"); String
		 * model=request.getParameter("model"); String
		 * serialNumber=request.getParameter("serialNumber"); int
		 * from=funUtil.StringToInt(request.getParameter("from")); int
		 * status=funUtil.StringToInt(request.getParameter("status")); int
		 * start=funUtil.StringToInt(request.getParameter("start")); int
		 * limit=funUtil.StringToInt(request.getParameter("limit")); Map<String,
		 * Object> map=new HashMap<String, Object>(); map.put("type", type);
		 * map.put("name", name); map.put("model",model );
		 * map.put("serialNumber",serialNumber ); map.put("from",from );
		 * map.put("status",status ); map.put("start", start); map.put("limit",
		 * limit);
		 */
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", BusinessService.allAssetType());
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
	 * 添加资产
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertAsset", method = RequestMethod.POST)
	public void insertAsset(HttpServletRequest request,
			HttpServletResponse response) {
		String jsonData = request.getParameter("formData");
		AssetInfoBean bean = GsonUtil
				.json2Object(jsonData, AssetInfoBean.class);
		bean.setCreateTime(funUtil.nowDate());
		log.info("data==>" + bean.toString());

		if (BusinessService.assetInfoByserialNumberExists(bean
				.getSerialNumber()) > 0) {
			this.message = "资产编号已经存在，请重新填写";
			this.success = false;

		} else {
			int rlt = BusinessService.insertAsset(bean);
			if (rlt == 1) {
				this.message = "添加资产成功";
				this.success = true;
				webLogBean.setOperator(funUtil.loginUser(request));
				webLogBean.setOperatorIp(funUtil.getIpAddr(request));
				webLogBean.setStyle(1);
				webLogBean.setContent("新增资产，data=" + bean.toString());
				WebLogService.writeLog(webLogBean);
			} else {
				this.message = "添加资产失败";
				this.success = false;
			}
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
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
	 * 修改资产记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateAsset", method = RequestMethod.POST)
	public void updateAsset(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		String jsonData = request.getParameter("formData");
		AssetInfoBean bean = GsonUtil
				.json2Object(jsonData, AssetInfoBean.class);
		log.info("data==>" + bean.toString());
		int rlt = BusinessService.updateAsset(bean);
		if (rlt == 1) {
			this.message = "修改资产记录成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改资产记录，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

		} else {
			this.message = "修改资产记录失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("result", rlt);
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
	 * 删除资产记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteAsset", method = RequestMethod.POST)
	public void deleteAsset(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("deleteIds");
		List<String> list = new ArrayList<String>();
		String[] ids = id.split(",");
		for (String str : ids) {
			list.add(str);
		}
		// log.info("data==>"+bean.toString());
		int rlt = BusinessService.deleteAsset(list);
		if (rlt == 1) {
			this.message = "删除记录成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(3);
			webLogBean.setContent("删除资产记录，data=" + id);
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "删除资产记录失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("result", rlt);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/deleteScrapAsset", method = RequestMethod.POST)
	public void deleteScrapAsset(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("deleteIds");
		List<String> list = new ArrayList<String>();
		String[] ids = id.split(",");
		for (String str : ids) {
			list.add(str);
		}
		// log.info("data==>"+bean.toString());
		int rlt = BusinessService.deleteScrapAsset(list);
		if (rlt == 1) {
			this.success = true;
			this.message = "成功";
		} else {
			this.message = "删除记录失败";
			this.success = false;
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("result", rlt);
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
	 * 导出数据到excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/excelName", method = RequestMethod.POST)
	public void excelName(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String saveDir = request.getSession().getServletContext()
					.getRealPath("/upload");
			String pathname = saveDir + "/资产状况.xls";
			File Path = new File(saveDir);
			if (!Path.exists()) {
				Path.mkdirs();
			}
			File file = new File(pathname);
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 12, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.PINK);// 背景颜色
			fontFormat.setBorder(Border.ALL, BorderLineStyle.NONE,
					Colour.DARK_GREEN);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.RED);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.YELLOW);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体
			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);
			// 设置其他样式
			fontFormat_Content.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(false);// 不自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(
					nf); // 设置表单格式
			WritableSheet sheet = book.createSheet("第1页", 0);
			// Label title=new Label(0,0,"gps信息",fontFormat);

			sheet.setRowView(0, 500);
			for (int i = 0; i <= 9; i++) {
				sheet.setColumnView(i, 17);
			}
			sheet.addCell(new Label(0, 0, "类型", fontFormat_h));
			sheet.addCell(new Label(1, 0, "名称", fontFormat_h));
			sheet.addCell(new Label(2, 0, "总数", fontFormat_h));
			sheet.addCell(new Label(3, 0, "外借", fontFormat_h));
			sheet.addCell(new Label(4, 0, "报废", fontFormat_h));
			sheet.addCell(new Label(5, 0, "维修", fontFormat_h));
			sheet.addCell(new Label(6, 0, "在库", fontFormat_h));
			sheet.addCell(new Label(7, 0, "在用", fontFormat_h));
			sheet.addCell(new Label(8, 0, "待报废", fontFormat_h));
			sheet.addCell(new Label(9, 0, "遗失", fontFormat_h));
			int row = 1; // 行
			boolean flg = false;
			int num = 0;// 工作表个数;
			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			list = BusinessService.allAssetNameCount();
			if (list.size() > 0) {
				while (row < list.size()) {
					if (row % 50000 == 0) {
						num++;
						flg = true;
						sheet = book.createSheet("第" + (num + 1) + "页", num);
						sheet.setRowView(0, 500);
						for (int i = 0; i <= 9; i++) {
							sheet.setColumnView(i, 17);
						}
						// 创建表头
						sheet.addCell(new Label(0, 0, "类型", fontFormat_h));
						sheet.addCell(new Label(1, 0, "名称", fontFormat_h));
						sheet.addCell(new Label(2, 0, "总数", fontFormat_h));
						sheet.addCell(new Label(3, 0, "外借", fontFormat_h));
						sheet.addCell(new Label(4, 0, "报废", fontFormat_h));
						sheet.addCell(new Label(5, 0, "维修", fontFormat_h));
						sheet.addCell(new Label(6, 0, "在库", fontFormat_h));
						sheet.addCell(new Label(7, 0, "在用", fontFormat_h));
						sheet.addCell(new Label(8, 0, "待报废", fontFormat_h));
						sheet.addCell(new Label(9, 0, "遗失", fontFormat_h));

					}
					// 将数据填充到excel
					HashMap<String, Object> map = list.get(row - 1);
					if (flg) {
						sheet.addCell(new Label(0, row + 1 - num * 50000, map
								.get("type").toString(), fontFormat_Content));
						sheet.addCell(new Label(1, row + 1 - num * 50000, map
								.get("name").toString(), fontFormat_Content));
						sheet.addCell(new Label(2, row + 1 - num * 50000,
								String.valueOf(map.get("total")),
								fontFormat_Content));
						sheet.addCell(new Label(3, row + 1 - num * 50000,
								String.valueOf(map.get("status1")),
								fontFormat_Content));
						sheet.addCell(new Label(4, row + 1 - num * 50000,
								String.valueOf(map.get("status2")),
								fontFormat_Content));
						sheet.addCell(new Label(5, row + 1 - num * 50000,
								String.valueOf(map.get("status3")),
								fontFormat_Content));
						sheet.addCell(new Label(6, row + 1 - num * 50000,
								String.valueOf(map.get("status4")),
								fontFormat_Content));
						sheet.addCell(new Label(7, row + 1 - num * 50000,
								String.valueOf(map.get("status5")),
								fontFormat_Content));
						sheet.addCell(new Label(8, row + 1 - num * 50000,
								String.valueOf(map.get("status6")),
								fontFormat_Content));
						sheet.addCell(new Label(9, row + 1 - num * 50000,
								String.valueOf(map.get("status7")),
								fontFormat_Content));
					} else {

						sheet.addCell(new Label(0, row, map.get("type")
								.toString(), fontFormat_Content));
						sheet.addCell(new Label(1, row, map.get("name")
								.toString(), fontFormat_Content));
						sheet.addCell(new Label(2, row, String.valueOf(map
								.get("total")), fontFormat_Content));
						sheet.addCell(new Label(3, row, String.valueOf(map
								.get("status1")), fontFormat_Content));
						sheet.addCell(new Label(4, row, String.valueOf(map
								.get("status2")), fontFormat_Content));
						sheet.addCell(new Label(5, row, String.valueOf(map
								.get("status3")), fontFormat_Content));
						sheet.addCell(new Label(6, row, String.valueOf(map
								.get("status4")), fontFormat_Content));
						sheet.addCell(new Label(7, row, String.valueOf(map
								.get("status5")), fontFormat_Content));
						sheet.addCell(new Label(8, row, String.valueOf(map
								.get("status6")), fontFormat_Content));
						sheet.addCell(new Label(9, row, String.valueOf(map
								.get("status7")), fontFormat_Content));
					}
					row++;

				}
			}
			book.write();
			book.close();
			this.message = "导出成功";
			this.success = true;
		} catch (Exception e) {
			e.printStackTrace();
			this.message = "导出失败";

		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
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
	 * 下载文件
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext()
				.getRealPath("/upload");
		String fileName = request.getParameter("fileName");
		/* fileName = new String(fileName.getBytes("gb2312"),"ISO8859-1"); */
		String downPath = path + "/" + fileName;
		log.info(downPath);
		File file = new File(downPath);
		if (!file.exists()) {
			this.success = false;
			this.message = "文件不存在";
		}
		// 设置响应头和客户端保存文件名
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ fileName);
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

	/**
	 * 根据序列号查询详细信息 wlk
	 */
	@RequestMapping(value = "/selectbynum", method = RequestMethod.POST)
	public void selectbynum(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		String serialNumber = request.getParameter("serialNumber");
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", BusinessService.selectbynum(serialNumber));
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
	 * 查询资产移交记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/assetTransferList", method = RequestMethod.GET)
	public void assetTransfer(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int type = funUtil.StringToInt(request.getParameter("type"));
		String name = request.getParameter("name");
		String model = request.getParameter("model");
		String serialNumber = request.getParameter("serialNumber");
		int from = funUtil.StringToInt(request.getParameter("from"));
		int status = funUtil.StringToInt(request.getParameter("status"));
		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("name", name);
		map.put("model", model);
		map.put("serialNumber", serialNumber);
		map.put("from", from);
		map.put("status", status);
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", BusinessService.assetTransferCount(map));
		// System.out.println("<--------------->"+BusinessService.assetTransferCount(map));
		result.put("items", BusinessService.assetTransfer(map));
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
	 * 添加移交资产
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertAssetTransfer", method = RequestMethod.POST)
	public void insertAssetTransfer(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		String jsonData = request.getParameter("formData");
		AssetTransferBean bean = GsonUtil.json2Object(jsonData,
				AssetTransferBean.class);
		bean.setCreateTime(funUtil.nowDate());
		log.info("data==>" + bean.toString());
		int rlt = BusinessService.insertAssetTransfer(bean);
		int rlt_1 = BusinessService.updateAssetTransfer2(bean);
		System.out.println("<---------------->" + bean.toString());
		if (rlt == 1 && rlt_1 == 1) {
			this.message = "添加资产移交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("新增资产，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "添加资产移交失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("result", rlt);
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
	 * 修改资产记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateAssetTransfer", method = RequestMethod.POST)
	public void updateAssetTransfer(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		String jsonData = request.getParameter("formData");
		AssetTransferBean bean = GsonUtil.json2Object(jsonData,
				AssetTransferBean.class);
		log.info("data==>" + bean.toString());
		int rlt = BusinessService.updateAssetTransfer(bean);
		int rlt_1 = BusinessService.updateAssetTransfer2(bean);
		if (rlt == 1 && rlt_1 == 1) {
			this.message = "修改资产记录成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改资产记录，data=" + bean.toString());
			WebLogService.writeLog(webLogBean);

		} else {
			this.message = "修改资产记录失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("result", rlt);
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
	 * 删除资产移交记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteAssetTransfer", method = RequestMethod.POST)
	public void deleteAssetTransfer(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("deleteIds");
		List<String> list = new ArrayList<String>();
		String[] ids = id.split(",");
		for (String str : ids) {
			list.add(str);
		}
		// log.info("data==>"+bean.toString());
		int rlt = BusinessService.deleteAssetTransfer(list);
		if (rlt == 1) {
			this.message = "删除资产记录成功";
			// this.message="添加资产成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(3);
			webLogBean.setContent("删除资产记录，data=" + id);
			WebLogService.writeLog(webLogBean);
		} else {
			this.message = "删除资产记录失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("result", rlt);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** 新增资产申请列表 */
	@RequestMapping(value = "/add_apply_list", method = RequestMethod.GET)
	public void add_apply_list(HttpServletRequest request,
			HttpServletResponse response) {

		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleType", FunUtil.loginUserInfo(request).get("roleType"));
		map.put("user", FunUtil.loginUserInfo(request).get("user"));
		map.put("power",
				FunUtil.loginUserPower(request).get("o_check_add_asset"));
		map.put("start", start);
		map.put("limit", limit);

		System.out.println(map);

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", BusinessService.add_apply_list_count(map));
		result.put("items", BusinessService.add_apply_list(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/asset_applay_create", method = RequestMethod.POST)
	public void asset_applay_create(HttpServletRequest request,
			HttpServletResponse response) {
		String jsonData = request.getParameter("formData");
		AssetInfoBean bean = GsonUtil
				.json2Object(jsonData, AssetInfoBean.class);
		bean.setCreateTime(funUtil.nowDate());
		bean.setIsLock(1);
		bean.setAddUser(FunUtil.loginUser(request));

		log.info("data==>" + bean.toString());

		if (BusinessService.assetInfoByserialNumberExists(bean
				.getSerialNumber()) > 0) {
			this.message = "资产编号已经存在，请重新填写";
			this.success = false;

		} else {
			int rlt = BusinessService.insertAsset(bean);
			if (rlt == 1) {
				this.message = "添加资产成功";
				this.success = true;
			} else {
				this.message = "添加资产失败";
				this.success = false;
			}
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
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
	 * 上传文件
	 * 
	 * @param file
	 * @param request
	 */
	@RequestMapping(value = "/upload/asset/attachment", method = RequestMethod.POST)
	public void upload(@RequestParam("filePath") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {

		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
		String data = funUtil.MD5(sdf.format(d));

		String path = request.getSession().getServletContext().getRealPath("")
				+ "/Resources/upload/asset/";
		String fileName = file.getOriginalFilename();

		/*
		 * File de=new File(fileName); file.renameTo(new File(name+".jpg"));
		 * //改名
		 */

		// String fileName = new Date().getTime()+".jpg";
		log.info("path==>" + path);
		log.info("fileName==>" + fileName);
		System.out.println("fileName==>" + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();

		}
		// 保存
		try {
			file.transferTo(targetFile);
			this.success = true;
			this.message = "文件上传成功";
			fileName = data + fileName.substring(fileName.indexOf("."));
			File rename = new File(path, fileName);
			targetFile.renameTo(rename);

		} catch (Exception e) {
			e.printStackTrace();
			this.message = "文件上传失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("fileName", fileName);
		result.put("filePath", path + "/" + fileName);
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

	@RequestMapping("/asset/add_apply")
	public void add_apply(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = FunUtil.loginUserInfo(request).get("userId").toString();
		String user = FunUtil.loginUserInfo(request).get("user").toString();
		String userName = FunUtil.loginUserInfo(request).get("userName")
				.toString();
		String tel = FunUtil.loginUserInfo(request).get("tel").toString();
		String comment = request.getParameter("comment");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");
		AssetAddApplyBean bean = new AssetAddApplyBean();
		bean.setUserId(FunUtil.StringToInt(userId));
		bean.setUser(user);
		bean.setUserName(userName);
		bean.setTel(tel);
		bean.setAttachmentName(fileName);
		bean.setAttachmentPath(filePath);
		bean.setComment(comment);
		bean.setCreateTime(FunUtil.nowDate());
		bean.setApplyTag(FunUtil.RandomAlphanumeric(15));

		System.out.println(bean);
		int rsl = BusinessService.add_apply(bean);

		if (rsl == 1) {
			this.success = true;
			this.message = "新增资产申请已发送，请耐心等到领导审核";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(4);
			webLogBean.setContent("新增资产申请");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToUserByPower(
					"o_check_add_asset",
					FunUtil.StringToInt(FunUtil.loginUserInfo(request)
							.get("roleType").toString()), "新增资产",
					"资产管理员申请新增资产，请审核相关信息。", request);

		} else {
			this.success = false;
			this.message = "失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rsl);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("/asset/add_apply_check1")
	public void add_apply_check1(HttpServletRequest request,
			HttpServletResponse response) {
		int id = FunUtil.StringToInt(request.getParameter("id"));
		int checked = FunUtil.StringToInt(request.getParameter("checked"));
		String user = request.getParameter("user");

		String comment = request.getParameter("comment");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");
		AssetAddApplyBean bean = new AssetAddApplyBean();
		int status = 1;
		if (checked == 0) {
			status = -1;
		}
		bean.setId(id);
		bean.setStatus(status);
		bean.setNote1(comment);
		bean.setTime1(FunUtil.nowDateNoTime());
		bean.setCheckUser(FunUtil.loginUser(request));

		System.out.println(bean);
		int rsl = BusinessService.add_apply_check1(bean);

		if (rsl == 1) {
			this.success = true;
			this.message = "审核信息完成";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核新增资产申请");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToOneUser(user, "新增资产", "领导已经审核你提交的申请", request);

		} else {
			this.success = false;
			this.message = "失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rsl);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/asset/add_apply_check2", method = RequestMethod.POST)
	public void add_apply_check2(HttpServletRequest request,
			HttpServletResponse response) {
		int id = FunUtil.StringToInt(request.getParameter("id"));
		String workNote = request.getParameter("workNote");
		String user = request.getParameter("user");
		String checkUser = request.getParameter("checkUser");
		AssetAddApplyBean bean = new AssetAddApplyBean();
		int status = 2;
		bean.setId(id);
		bean.setStatus(status);
		bean.setTime2(FunUtil.nowDateNoTime());
		bean.setUser(user);
		bean.setCheckUser(checkUser);
		AssetAddApplayInfoBean infobean = new AssetAddApplayInfoBean();
		infobean.setApplyId(id);
		infobean.setCreateTime(FunUtil.nowDateNoTime());
		infobean.setWorkNote(workNote);

		System.out.println("bean->" + bean);
		System.out.println("bean2->" + infobean);

		int rsl = BusinessService.add_apply_check2(bean, infobean);

		if (rsl == 1) {
			this.success = true;
			this.message = "资产入库成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("资产入库成功");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToOneUser(checkUser, "新增资产",
					"资产管理员已经将资产录入系统中，请点击确认", request);

		} else {
			this.success = false;
			this.message = "失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", 1);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/asset/add_apply_check3", method = RequestMethod.POST)
	public void add_apply_check3(HttpServletRequest request,
			HttpServletResponse response) {
		int id = FunUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("user");
		String checkUser = request.getParameter("checkUser");
		AssetAddApplyBean bean = new AssetAddApplyBean();
		int status = 3;
		bean.setId(id);
		bean.setStatus(status);
		bean.setTime3(FunUtil.nowDateNoTime());
		bean.setUser(user);
		bean.setCheckUser(checkUser);
		int rsl = BusinessService.add_apply_check3(bean);

		if (rsl == 1) {
			this.success = true;
			this.message = "资产入库确认成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("资产入库确认成功");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToOneUser(user, "新增资产", "管理员已经确认资产入库，流程结束", request);

		} else {
			this.success = false;
			this.message = "失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", 1);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("/asset/excel")
	@ResponseBody
	public void excel(@RequestParam("pathName") CommonsMultipartFile file,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/Resources/upload/asset/";
		String name = file.getOriginalFilename();
		String fileName = file.getOriginalFilename();
		List<AssetInfoBean> list = new ArrayList<AssetInfoBean>();

		File targetFile = new File(path, fileName);
		int r = 0, count = 0;
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);

			list = getDataByExcel(path + fileName, request);
			count = list.size();

			System.out.println("文件路径szie-----" + list.size());
			System.out
					.println("文件路径rrr-----" + Arrays.toString(list.toArray()));
			for (AssetInfoBean assetInfoBean : list) {
				
				
				if (BusinessService.assetInfoByserialNumberExists(assetInfoBean
						.getSerialNumber()) < 1) {
					r += BusinessService.insertAsset(assetInfoBean);
				}

			}
			// r=BusinessService.insertManyAsset(list);
			System.out.println("文件路径r-----" + r);

		} catch (Exception e) {
			e.printStackTrace();

		}
		if (r > 0) {
			success = true;
		} else {
			success = false;
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", count);
		result.put("successCount", r);
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

	public List<AssetInfoBean> getDataByExcel(String path,
			HttpServletRequest request) {
		List<AssetInfoBean> list = new ArrayList<AssetInfoBean>();
		try {
			Workbook rwb = Workbook.getWorkbook(new File(path));
			File file = new File(path);
			if (!file.exists()) {
				System.out.println("文件路径rdddd-----文件不存在存在");
			} else {
				System.out.println("文件路径rdddd-----文件存在");
			}

			// 取得第一个sheet
			int sheet = rwb.getSheets().length;
			for (int a = 0; a < sheet; a++) {

				Sheet rs = rwb.getSheet(a);

				int clos = 9;// 得到所有的列
				int rows = rs.getRows();// 得到所有的行

				System.out.println("文件解析clos-----" + clos);
				System.out.println("文件解析rows-----" + rows);

				for (int i = 1; i < rows; i++) {
					Cell[] cell = rs.getRow(i);
					int cellLen = cell.length;
					if (cellLen > 1) {
						for (int j = 0; j < clos; j++) {
							// getCell(列，行)
							// out.print(sheet.getCell(j, i).getContents());
							AssetInfoBean bean = new AssetInfoBean();
							bean.setSerialNumber(rs.getCell(j++, i)
									.getContents());

							bean.setType(asset_type(rs.getCell(j++, i)
									.getContents()));
							bean.setName(rs.getCell(j++, i).getContents());
							bean.setModel(rs.getCell(j++, i).getContents());
							bean.setPriceType(rs.getCell(j++, i).getContents());
							bean.setPrice(rs.getCell(j++, i).getContents());
							bean.setProName(rs.getCell(j++, i).getContents());
							
							bean.setFrom(asset_from(rs.getCell(j++, i)
									.getContents()));
							bean.setBuyTime(rs.getCell(j++, i).getContents());
							
							bean.setStatus(asset_status(rs.getCell(j++, i)
									.getContents()));
							bean.setNote(rs.getCell(j++, i).getContents());
							bean.setCreateTime(FunUtil.nowDateNoTime());
							bean.setIsLock(1);
							bean.setAddUser(FunUtil.loginUser(request));
							list.add(bean);
							/*System.out.println(bean);*/
						}

					}

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("导入excel出错");
			e.printStackTrace();
		}
		return list;

	}

	/** 报废资产申请列表 */
	@RequestMapping(value = "/scrap_apply_list", method = RequestMethod.GET)
	public void scrap_apply_list(HttpServletRequest request,
			HttpServletResponse response) {

		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleType", FunUtil.loginUserInfo(request).get("roleType"));
		map.put("user", FunUtil.loginUserInfo(request).get("user"));
		map.put("power",
				FunUtil.loginUserPower(request).get("o_check_scrap_asset"));
		map.put("start", start);
		map.put("limit", limit);

		System.out.println(map);

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", BusinessService.scrap_apply_list_count(map));
		result.put("items", BusinessService.scrap_apply_list(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 增加报废资产 */
	@RequestMapping(value = "/insertScrapAsset", method = RequestMethod.POST)
	public void insertScrapAsset(HttpServletRequest request,
			HttpServletResponse response) {
		String jsonData = request.getParameter("formData");
		AssetScrapInfoBean bean = GsonUtil.json2Object(jsonData,
				AssetScrapInfoBean.class);
		bean.setCreateTime(funUtil.nowDate());
		bean.setIsLock(1);
		bean.setAddUser(FunUtil.loginUser(request));
		log.info("data==>" + bean.toString());

		if (BusinessService.scrapAssetInfoByserialNumberExists(bean
				.getSerialNumber()) > 0) {
			this.message = "资产编号已经存在，请重新填写";
			this.success = false;

		} else {
			int rlt = BusinessService.insertScrapAsset(bean);
			if (rlt == 1) {
				this.message = "录入报废资产成功";
				this.success = true;
				webLogBean.setOperator(funUtil.loginUser(request));
				webLogBean.setOperatorIp(funUtil.getIpAddr(request));
				webLogBean.setStyle(1);
				webLogBean.setContent("录入报废资产，data=" + bean.toString());
				WebLogService.writeLog(webLogBean);
			} else {
				this.message = "录入报废资产失败";
				this.success = false;
			}
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** 报废资产申请 */
	@RequestMapping("/asset/scrap_apply")
	public void scrap_apply(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = FunUtil.loginUserInfo(request).get("userId").toString();
		String user = FunUtil.loginUserInfo(request).get("user").toString();
		String userName = FunUtil.loginUserInfo(request).get("userName")
				.toString();
		String tel = FunUtil.loginUserInfo(request).get("tel").toString();
		String comment = request.getParameter("comment");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");
		AssetScrapApplyBean bean = new AssetScrapApplyBean();
		bean.setUserId(FunUtil.StringToInt(userId));
		bean.setUser(user);
		bean.setUserName(userName);
		bean.setTel(tel);
		bean.setAttachmentName(fileName);
		bean.setAttachmentPath(filePath);
		bean.setComment(comment);
		bean.setCreateTime(FunUtil.nowDate());
		bean.setApplyTag(FunUtil.RandomAlphanumeric(15));

		System.out.println(bean);
		int rsl = BusinessService.scrap_apply(bean);

		if (rsl == 1) {
			this.success = true;
			this.message = "报废资产申请已发送，请耐心等到领导审核";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(4);
			webLogBean.setContent("报废资产申请");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToUserByPower(
					"o_check_add_asset",
					FunUtil.StringToInt(FunUtil.loginUserInfo(request)
							.get("roleType").toString()), "报废资产",
					"资产管理员申请报废资产，请审核相关信息。", request);

		} else {
			this.success = false;
			this.message = "失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rsl);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** 审核报废清单 */
	@RequestMapping("/asset/scrap_apply_check1")
	public void scrap_apply_check1(HttpServletRequest request,
			HttpServletResponse response) {
		int id = FunUtil.StringToInt(request.getParameter("id"));
		int checked = FunUtil.StringToInt(request.getParameter("checked"));
		String user = request.getParameter("user");

		String comment = request.getParameter("comment");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");
		AssetScrapApplyBean bean = new AssetScrapApplyBean();
		int status = 1;
		if (checked == 0) {
			status = -1;
		}
		bean.setId(id);
		bean.setStatus(status);
		bean.setNote1(comment);
		bean.setTime1(FunUtil.nowDateNoTime());
		bean.setCheckUser(FunUtil.loginUser(request));

		System.out.println(bean);
		int rsl = BusinessService.scrap_apply_check1(bean);

		if (rsl == 1) {
			this.success = true;
			this.message = "审核信息完成";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核报废资产申请");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToOneUser(user, "报废资产", "领导已经审核你提交的申请", request);

		} else {
			this.success = false;
			this.message = "失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rsl);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/asset/scrap_apply_check2", method = RequestMethod.POST)
	public void scrap_apply_check2(HttpServletRequest request,
			HttpServletResponse response) {
		int id = FunUtil.StringToInt(request.getParameter("id"));
		String workNote = request.getParameter("workNote");
		String user = request.getParameter("user");
		String checkUser = request.getParameter("checkUser");
		String applyTag = request.getParameter("applyTag");
		AssetScrapApplyBean bean = new AssetScrapApplyBean();
		int status = 2;
		bean.setId(id);
		bean.setStatus(status);
		bean.setTime2(FunUtil.nowDateNoTime());
		bean.setUser(user);
		bean.setCheckUser(checkUser);
		bean.setApplyTag(applyTag);
		AssetScrapApplayInfoBean infobean = new AssetScrapApplayInfoBean();
		infobean.setApplyId(id);
		infobean.setCreateTime(FunUtil.nowDateNoTime());
		infobean.setWorkNote(workNote);

		System.out.println("bean->" + bean);
		System.out.println("bean2->" + infobean);

		int rsl = BusinessService.scrap_apply_check2(bean, infobean);

		if (rsl == 1) {
			this.success = true;
			this.message = "报废资产入库成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("报废入库成功");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToOneUser(checkUser, "报废资产",
					"资产管理员已经将报废资产录入系统中，请点击确认", request);

		} else {
			this.success = false;
			this.message = "失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rsl);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<AssetScrapInfoBean> getScrapDataByExcel(String path,
			HttpServletRequest request) {
		List<AssetScrapInfoBean> list = new ArrayList<AssetScrapInfoBean>();
		try {
			Workbook rwb = Workbook.getWorkbook(new File(path));
			File file = new File(path);
			if (!file.exists()) {
				System.out.println("文件路径rdddd-----文件不存在存在");
			} else {
				System.out.println("文件路径rdddd-----文件存在");
			}

			// 取得第一个sheet
			int sheet = rwb.getSheets().length;
			for (int a = 0; a < sheet; a++) {

				Sheet rs = rwb.getSheet(a);

				int clos = rs.getColumns();// 得到所有的列
				int rows = rs.getRows();// 得到所有的行

				System.out.println("文件解析clos-----" + clos);
				System.out.println("文件解析rows-----" + rows);

				for (int i = 1; i < rows; i++) {
					Cell[] cell = rs.getRow(i);
					int cellLen = cell.length;
					if (cellLen > 1) {
						for (int j = 0; j < clos; j++) {
							// getCell(列，行)

							// out.print(sheet.getCell(j, i).getContents());
							AssetScrapInfoBean bean = new AssetScrapInfoBean();
							bean.setSerialNumber(rs.getCell(j++, i)
									.getContents());

							bean.setType(asset_type(rs.getCell(j++, i)
									.getContents()));
							bean.setName(rs.getCell(j++, i).getContents());
							bean.setModel(rs.getCell(j++, i).getContents());
							bean.setPriceType(rs.getCell(j++, i).getContents());
							bean.setPrice(rs.getCell(j++, i).getContents());
							bean.setProName(rs.getCell(j++, i).getContents());
							bean.setFrom(asset_from(rs.getCell(j++, i)
									.getContents()));
							bean.setBuyTime(rs.getCell(j++, i).getContents());
							bean.setStatus(asset_status(rs.getCell(j++, i)
									.getContents()) == 2 ? 2 : 2);
							bean.setNote(rs.getCell(j++, i).getContents());
							bean.setCreateTime(FunUtil.nowDateNoTime());
							bean.setIsLock(1);
							bean.setAddUser(FunUtil.loginUser(request));
							list.add(bean);
						}

					}

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("导入excel出错");
			e.printStackTrace();
		}
		return list;

	}

	@RequestMapping("/asset/excel/scrap")
	@ResponseBody
	public void excel_scrap(
			@RequestParam("pathName") CommonsMultipartFile file,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/Resources/upload/asset/";
		String name = file.getOriginalFilename();
		String fileName = file.getOriginalFilename();
		List<AssetScrapInfoBean> list = new ArrayList<AssetScrapInfoBean>();

		File targetFile = new File(path, fileName);
		int r = 0, count = 0;
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);

			list = getScrapDataByExcel(path + fileName, request);
			count = list.size();

			System.out.println("文件路径szie-----" + list.size());
			System.out
					.println("文件路径rrr-----" + Arrays.toString(list.toArray()));
			for (AssetScrapInfoBean assetInfoBean : list) {
				if (BusinessService
						.scrapAssetInfoByserialNumberExists(assetInfoBean
								.getSerialNumber()) < 1) {
					r += BusinessService.insertScrapAsset(assetInfoBean);
				}

			}
			// r=BusinessService.insertManyAsset(list);
			System.out.println("文件路径r-----" + r);

		} catch (Exception e) {
			e.printStackTrace();

		}
		if (r > 0) {
			success = true;
		} else {
			success = false;
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", count);
		result.put("successCount", r);
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

	@RequestMapping(value = "/asset/scrap_apply_check3", method = RequestMethod.POST)
	public void scrap_apply_check3(HttpServletRequest request,
			HttpServletResponse response) {
		int id = FunUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("user");
		String checkUser = request.getParameter("checkUser");
		AssetScrapApplyBean bean = new AssetScrapApplyBean();
		int status = 3;
		bean.setId(id);
		bean.setStatus(status);
		bean.setTime3(FunUtil.nowDateNoTime());
		bean.setUser(user);
		bean.setCheckUser(checkUser);
		int rsl = BusinessService.scrap_apply_check3(bean);

		if (rsl == 1) {
			this.success = true;
			this.message = "报废资产入库确认成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("报废资产入库确认成功");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToOneUser(user, "报废资产", "管理员已经确认报废资产入库，流程结束",
					request);

		} else {
			this.success = false;
			this.message = "失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rsl);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 资产类别
	public int asset_type(String str) {
		if (str.equals("交换控制中心（MSO）设备")) {
			return 1;
		} else if (str.equals("远端调度台设备")) {
			return 2;
		} else if (str.equals("基站设备")) {
			return 3;
		} else if (str.equals("用户台设备")) {
			return 4;
		} else if (str.equals("备件")) {
			return 5;
		} else if (str.equals("附件")) {
			return 6;
		} else {
			return 7;
		}
	}

	// 资产来源
	public int asset_from(String str) {
		if (str.equals("采购")) {
			return 1;
		} else if (str.equals("赠送")) {
			return 2;
		} else {
			return 3;
		}
	}

	// 资产来源
	public int asset_status(String str) {
		if(str==null || str==""){
			return 4;
		}
		if (str.equals("外借")) {
			return 1;
		} else if (str.equals("报废")) {
			return 2;
		} else if (str.equals("维修")) {
			return 3;
		} else if (str.equals("在库")) {
			return 4;
		} else if (str.equals("在用")) {
			return 5;
		} else if (str.equals("待报废")) {
			return 6;
		} else if (str.equals("遗失")) {
			return 7;
		} else {
			return 8;
		}
	}

	@RequestMapping(value = "/update_status_apply_list", method = RequestMethod.GET)
	public void update_status_apply_list(HttpServletRequest request,
			HttpServletResponse response) {

		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleType", FunUtil.loginUserInfo(request).get("roleType"));
		map.put("user", FunUtil.loginUserInfo(request).get("user"));
		map.put("power",
				FunUtil.loginUserPower(request).get("o_check_scrap_asset"));
		map.put("start", start);
		map.put("limit", limit);

		System.out.println(map);

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", BusinessService.update_status_apply_count(map));
		result.put("items", BusinessService.update_status_apply_list(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/add_update_status", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add_update_status(HttpServletRequest request,
			HttpServletResponse response) {

		int status = funUtil.StringToInt(request.getParameter("status"));
		String data = request.getParameter("data");

		JSONArray array = JSONArray.fromObject(data);
		List<AssetUpdateStatusInfoBean> list = JSONArray.toList(array,
				AssetUpdateStatusInfoBean.class);// 过时方法

		for (AssetUpdateStatusInfoBean assetUpdateStatusInfoBean : list) {
			assetUpdateStatusInfoBean.setAfterStatus(status);
			assetUpdateStatusInfoBean.setCreateTime(FunUtil.nowDateNoTime());
			assetUpdateStatusInfoBean.setAddUser(FunUtil.loginUser(request));
			assetUpdateStatusInfoBean.setIsLock(1);
		}
		System.out.println(list);
		int rst = BusinessService.add_update_status(list);
		if (rst > 0) {
			this.message = "添加数据成功";
			this.success = true;
		} else {
			this.message = "添加数据失败";
			this.success = false;
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		return result;
	}

	@RequestMapping(value = "/update_status_info", method = RequestMethod.GET)
	public void update_status_info(HttpServletRequest request,
			HttpServletResponse response) {
		this.success = true;
		int isLock = funUtil.StringToInt(request.getParameter("isLock"));
		int tag = funUtil.StringToInt(request.getParameter("tag"));
		String applyTag = request.getParameter("applyTag");
		String user = request.getParameter("user") == null ? FunUtil
				.loginUser(request) : request.getParameter("user");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isLock", isLock);
		map.put("tag", tag);
		map.put("applyTag", applyTag);
		map.put("user", user);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals", BusinessService.update_status_info(map).size());
		result.put("items", BusinessService.update_status_info(map));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteUpdateStatusAsset", method = RequestMethod.POST)
	public void deleteUpdateStatusAsset(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("deleteIds");
		List<String> list = new ArrayList<String>();
		String[] ids = id.split(",");
		for (String str : ids) {
			list.add(str);
		}
		// log.info("data==>"+bean.toString());
		int rlt = BusinessService.deleteUpdateStatusAsset(list);
		if (rlt == 1) {
			this.success = true;
			this.message = "成功";
		} else {
			this.message = "删除记录失败";
			this.success = false;
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message", message);
		result.put("result", rlt);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("/asset/add_update_status_apply")
	public void add_update_status_apply(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = FunUtil.loginUserInfo(request).get("userId").toString();
		String user = FunUtil.loginUserInfo(request).get("user").toString();
		String userName = FunUtil.loginUserInfo(request).get("userName")
				.toString();
		String tel = FunUtil.loginUserInfo(request).get("tel").toString();
		String comment = request.getParameter("comment");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");
		AssetUpdateStatusApplyBean bean = new AssetUpdateStatusApplyBean();
		bean.setUserId(FunUtil.StringToInt(userId));
		bean.setUser(user);
		bean.setUserName(userName);
		bean.setTel(tel);
		bean.setAttachmentName(fileName);
		bean.setAttachmentPath(filePath);
		bean.setComment(comment);
		bean.setCreateTime(FunUtil.nowDate());
		bean.setApplyTag(FunUtil.RandomAlphanumeric(15));

		System.out.println(bean);
		int rsl = BusinessService.add_update_status_apply(bean);

		if (rsl == 1) {
			this.success = true;
			this.message = "资产状态变更申请已发送，请耐心等到领导审核";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(4);
			webLogBean.setContent("资产状态变更申请");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToUserByPower(
					"o_check_add_asset",
					FunUtil.StringToInt(FunUtil.loginUserInfo(request)
							.get("roleType").toString()), "资产状态变更",
					"资产管理员申请资产资产状态变更，请审核相关信息。", request);

		} else {
			this.success = false;
			this.message = "失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rsl);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping("/asset/update_status_check1")
	public void update_status_check1(HttpServletRequest request,
			HttpServletResponse response) {
		int id = FunUtil.StringToInt(request.getParameter("id"));
		int checked = FunUtil.StringToInt(request.getParameter("checked"));
		String user = request.getParameter("user");

		String comment = request.getParameter("comment");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");
		AssetUpdateStatusApplyBean bean = new AssetUpdateStatusApplyBean();
		int status = 1;
		if (checked == 0) {
			status = -1;
		}
		bean.setId(id);
		bean.setStatus(status);
		bean.setNote1(comment);
		bean.setTime1(FunUtil.nowDateNoTime());
		bean.setCheckUser(FunUtil.loginUser(request));

		System.out.println(bean);
		int rsl = BusinessService.update_status_check1(bean);

		if (rsl == 1) {
			this.success = true;
			this.message = "审核信息完成";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("审核资产状态变更申请");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToOneUser(user, "资产状态变更", "领导已经审核你提交的申请", request);

		} else {
			this.success = false;
			this.message = "失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rsl);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/asset/update_status_check2", method = RequestMethod.POST)
	public void update_status_check2(HttpServletRequest request,
			HttpServletResponse response) {
		int id = FunUtil.StringToInt(request.getParameter("id"));
		String workNote = request.getParameter("workNote");
		String user = request.getParameter("user");
		String checkUser = request.getParameter("checkUser");
		String applyTag = request.getParameter("applyTag");
		AssetUpdateStatusApplyBean bean = new AssetUpdateStatusApplyBean();
		int status = 2;
		bean.setId(id);
		bean.setStatus(status);
		bean.setTime2(FunUtil.nowDateNoTime());
		bean.setUser(user);
		bean.setCheckUser(checkUser);
		bean.setApplyTag(applyTag);
		AssetUpdateStatusAttrBean infobean = new AssetUpdateStatusAttrBean();
		infobean.setApplyId(id);
		infobean.setCreateTime(FunUtil.nowDateNoTime());
		infobean.setWorkNote(workNote);

		System.out.println("bean->" + bean);
		System.out.println("bean2->" + infobean);

		int rsl = BusinessService.update_status_check2(bean, infobean);

		if (rsl == 1) {
			this.success = true;
			this.message = "报废资产入库成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("报废入库成功");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToOneUser(checkUser, "资产状态变更",
					"资产管理员已经将状态变更资产录入系统中，请点击确认", request);

		} else {
			this.success = false;
			this.message = "失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rsl);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value = "/asset/update_status_check3", method = RequestMethod.POST)
	public void update_status_check3(HttpServletRequest request,
			HttpServletResponse response) {
		int id = FunUtil.StringToInt(request.getParameter("id"));
		String user = request.getParameter("user");
		String checkUser = request.getParameter("checkUser");
		AssetUpdateStatusApplyBean bean = new AssetUpdateStatusApplyBean();
		int status = 3;
		bean.setId(id);
		bean.setStatus(status);
		bean.setTime3(FunUtil.nowDateNoTime());
		bean.setUser(user);
		bean.setCheckUser(checkUser);
		int rsl = BusinessService.update_status_check3(bean);

		if (rsl == 1) {
			this.success = true;
			this.message = "报废资产入库确认成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(5);
			webLogBean.setContent("报废资产入库确认成功");
			WebLogService.writeLog(webLogBean);
			FunUtil.sendMsgToOneUser(user, "资产状态变更", "管理员已经确认报废资产入库，流程结束",
					request);

		} else {
			this.success = false;
			this.message = "失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("result", rsl);
		result.put("message", message);
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
