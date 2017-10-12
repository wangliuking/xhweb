package xh.springmvc.handlers;

import net.sf.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.AssetInfoBean;
import xh.mybatis.bean.AssetTransferBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.BusinessService;
import xh.mybatis.service.WebLogService;

@Controller
@RequestMapping("/business")
public class BusinessController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(BusinessController.class);
	private FlexJSON json=new FlexJSON();
	private WebLogBean webLogBean=new WebLogBean();
	/**
	 * 查询资产记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/assetList",method = RequestMethod.GET)
	public void assetInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int type=funUtil.StringToInt(request.getParameter("type"));
		String name=request.getParameter("name");
		String model=request.getParameter("model");
		String serialNumber=request.getParameter("serialNumber");
		int from=funUtil.StringToInt(request.getParameter("from"));
		int status=funUtil.StringToInt(request.getParameter("status"));
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("type", type);
		map.put("name", name);
		map.put("model",model );
		map.put("serialNumber",serialNumber );
		map.put("from",from );
		map.put("status",status );
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",BusinessService.assetInfoCount(map));
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
	/**
	 * 按资产状态统计
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/allAssetStatus",method = RequestMethod.GET)
	public void allAssetStatus(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		HashMap result = new HashMap();
		List<HashMap<String,Integer>> list=BusinessService.allAssetStatus();
		List<HashMap<String,Object>> list2=new ArrayList<HashMap<String,Object>>();
		int totals=0;
		for (HashMap<String, Integer> hashMap : list) {
			HashMap<String, Object> map=new HashMap<String, Object>();
			String name="";
			if(hashMap.get("status")==1){name="外借";}
			else if(hashMap.get("status")==2){name="报废";}
			else if(hashMap.get("status")==3){name="维修";}
			else if(hashMap.get("status")==4){name="在库";}
			else if(hashMap.get("status")==5){name="在用";}
			else if(hashMap.get("status")==6){name="待报废";}
			else if(hashMap.get("status")==7){name="遗失";}
			else{name="未知";}
			map.put("name", name);
			map.put("value", hashMap.get("number"));
			/*totals+=hashMap.get("number");*/
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
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/allAssetNameCount",method = RequestMethod.GET)
	public void allAssetNameCount(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
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
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/allAssetType",method = RequestMethod.GET)
	public void allAssetType(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
	/*	int type=funUtil.StringToInt(request.getParameter("type"));
		String name=request.getParameter("name");
		String model=request.getParameter("model");
		String serialNumber=request.getParameter("serialNumber");
		int from=funUtil.StringToInt(request.getParameter("from"));
		int status=funUtil.StringToInt(request.getParameter("status"));
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));*/
	/*	Map<String, Object> map=new HashMap<String, Object>();
		map.put("type", type);
		map.put("name", name);
		map.put("model",model );
		map.put("serialNumber",serialNumber );
		map.put("from",from );
		map.put("status",status );
		map.put("start", start);
		map.put("limit", limit);*/
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
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/insertAsset",method = RequestMethod.POST)
	public void insertAsset(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String jsonData=request.getParameter("formData");
        AssetInfoBean bean=GsonUtil.json2Object(jsonData, AssetInfoBean.class);
        bean.setCreateTime(funUtil.nowDate());
		log.info("data==>"+bean.toString());
		int rlt=BusinessService.insertAsset(bean);
		if (rlt==1) {
			this.message="添加资产成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("新增资产，data="+bean.toString());
			WebLogService.writeLog(webLogBean);
		}else {
			this.message="添加资产失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("result",rlt);
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
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateAsset",method = RequestMethod.POST)
	public void updateAsset(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String jsonData=request.getParameter("formData");
        AssetInfoBean bean=GsonUtil.json2Object(jsonData, AssetInfoBean.class);
		log.info("data==>"+bean.toString());
		int rlt=BusinessService.updateAsset(bean);
		if (rlt==1) {
			this.message="修改资产记录成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改资产记录，data="+bean.toString());
			WebLogService.writeLog(webLogBean);
			
		}else {
			this.message="修改资产记录失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("result",rlt);
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
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteAsset",method = RequestMethod.POST)
	public void deleteAsset(HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("deleteIds");
		List<String> list = new ArrayList<String>();
		String[] ids=id.split(",");
		for (String str : ids) {
			list.add(str);
		}
		//log.info("data==>"+bean.toString());
		int rlt=BusinessService.deleteAsset(list);
		if (rlt==1) {
			this.message="删除资产记录成功";
			this.message="添加资产成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(3);
			webLogBean.setContent("删除资产记录，data="+id);
			WebLogService.writeLog(webLogBean);
		}else {
			this.message="删除资产记录失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("result",rlt);
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
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/excelName",method = RequestMethod.POST)
	public void excelName(HttpServletRequest request, HttpServletResponse response){
		try{
		String saveDir =  request.getSession().getServletContext().getRealPath("/upload");
		String pathname=saveDir+"/资产状况.xls";
		File Path=new File(saveDir);
		if(!Path.exists()){Path.mkdirs();}
		File file=new File(pathname);
		WritableWorkbook book=Workbook.createWorkbook(file);			
		WritableFont font=new WritableFont(WritableFont.createFont("微软雅黑"), 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
		WritableCellFormat fontFormat=new WritableCellFormat(font);
		fontFormat.setAlignment(Alignment.CENTRE);   //水平居中
		fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);//垂直居中
		fontFormat.setWrap(true);  //自动换行
		fontFormat.setBackground(Colour.PINK);//背景颜色
		fontFormat.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.DARK_GREEN);
		fontFormat.setOrientation(Orientation.HORIZONTAL);//文字方向

		//设置头部字体格式
        WritableFont font_header = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, 
                false, UnderlineStyle.NO_UNDERLINE, Colour.RED);
        //应用字体
        WritableCellFormat fontFormat_h = new WritableCellFormat(font_header);
        //设置其他样式
        fontFormat_h.setAlignment(Alignment.CENTRE);//水平对齐
        fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
        fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);//边框
        fontFormat_h.setBackground(Colour.YELLOW);//背景色
        fontFormat_h.setWrap(false);//不自动换行
        

		//设置主题内容字体格式
        WritableFont font_Content = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD, 
                false, UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
        //应用字体
        WritableCellFormat fontFormat_Content = new WritableCellFormat(font_Content);
        //设置其他样式
        fontFormat_Content.setAlignment(Alignment.CENTRE);//水平对齐
        fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);//垂直对齐
        fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);//边框
        fontFormat_Content.setBackground(Colour.WHITE);//背景色
        fontFormat_Content.setWrap(false);//不自动换行
        
        //设置数字格式
        jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");    //设置数字格式
		jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); //设置表单格式    
		WritableSheet sheet=book.createSheet("第1页", 0); 
//		Label title=new Label(0,0,"gps信息",fontFormat);

		sheet.setRowView(0,500);
		for(int i=0;i<=9;i++){
			sheet.setColumnView(i,17);
		}
		sheet.addCell(new Label(0,0,"类型",fontFormat_h));
		sheet.addCell(new Label(1,0,"名称",fontFormat_h));
		sheet.addCell(new Label(2,0,"总数",fontFormat_h));
		sheet.addCell(new Label(3,0,"外借",fontFormat_h));
		sheet.addCell(new Label(4,0,"报废",fontFormat_h));
		sheet.addCell(new Label(5,0,"维修",fontFormat_h));
		sheet.addCell(new Label(6,0,"在库",fontFormat_h));
		sheet.addCell(new Label(7,0,"在用",fontFormat_h));
		sheet.addCell(new Label(8,0,"待报废",fontFormat_h));
		sheet.addCell(new Label(9,0,"遗失",fontFormat_h));
		int row=1; //行
		boolean flg=false;
		int num=0;//工作表个数;
		List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
		list=BusinessService.allAssetNameCount();
		if(list.size()>0){
			while(row<list.size()){
			if(row%50000==0){
				num++;
				flg=true;
				sheet=book.createSheet("第"+(num+1)+"页", num); 
				sheet.setRowView(0,500);
				for(int i=0;i<=9;i++){
					sheet.setColumnView(i,17);
				}
				//创建表头
				sheet.addCell(new Label(0,0,"类型",fontFormat_h));
				sheet.addCell(new Label(1,0,"名称",fontFormat_h));
				sheet.addCell(new Label(2,0,"总数",fontFormat_h));
				sheet.addCell(new Label(3,0,"外借",fontFormat_h));
				sheet.addCell(new Label(4,0,"报废",fontFormat_h));
				sheet.addCell(new Label(5,0,"维修",fontFormat_h));
				sheet.addCell(new Label(6,0,"在库",fontFormat_h));
				sheet.addCell(new Label(7,0,"在用",fontFormat_h));
				sheet.addCell(new Label(8,0,"待报废",fontFormat_h));
				sheet.addCell(new Label(9,0,"遗失",fontFormat_h));
				
			}
			//将数据填充到excel
			HashMap<String,Object> map=list.get(row-1);
			if(flg){
				sheet.addCell(new Label(0,row+1-num * 50000,map.get("type").toString(),fontFormat_Content));
				sheet.addCell(new Label(1,row+1-num * 50000,map.get("name").toString(),fontFormat_Content));
				sheet.addCell(new Label(2,row+1-num * 50000,String.valueOf(map.get("total")),fontFormat_Content));
				sheet.addCell(new Label(3,row+1-num * 50000,String.valueOf(map.get("status1")),fontFormat_Content));
				sheet.addCell(new Label(4,row+1-num * 50000,String.valueOf(map.get("status2")),fontFormat_Content));
				sheet.addCell(new Label(5,row+1-num * 50000,String.valueOf(map.get("status3")),fontFormat_Content));
				sheet.addCell(new Label(6,row+1-num * 50000,String.valueOf(map.get("status4")),fontFormat_Content));
				sheet.addCell(new Label(7,row+1-num * 50000,String.valueOf(map.get("status5")),fontFormat_Content));
				sheet.addCell(new Label(8,row+1-num * 50000,String.valueOf(map.get("status6")),fontFormat_Content));
				sheet.addCell(new Label(9,row+1-num * 50000,String.valueOf(map.get("status7")),fontFormat_Content));
			}
			else
			{
				
				sheet.addCell(new Label(0,row,map.get("type").toString(),fontFormat_Content));
				sheet.addCell(new Label(1,row,map.get("name").toString(),fontFormat_Content));
				sheet.addCell(new Label(2,row,String.valueOf(map.get("total")),fontFormat_Content));
				sheet.addCell(new Label(3,row,String.valueOf(map.get("status1")),fontFormat_Content));
				sheet.addCell(new Label(4,row,String.valueOf(map.get("status2")),fontFormat_Content));
				sheet.addCell(new Label(5,row,String.valueOf(map.get("status3")),fontFormat_Content));
				sheet.addCell(new Label(6,row,String.valueOf(map.get("status4")),fontFormat_Content));
				sheet.addCell(new Label(7,row,String.valueOf(map.get("status5")),fontFormat_Content));
				sheet.addCell(new Label(8,row,String.valueOf(map.get("status6")),fontFormat_Content));
				sheet.addCell(new Label(9,row,String.valueOf(map.get("status7")),fontFormat_Content));
			}
			row++;
			
		}
		}
		book.write();
		book.close();
		this.message="导出成功";
		this.success=true;
	}catch(Exception e){
		e.printStackTrace();
		this.message="导出失败";

	}
		
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
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
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String path = request.getSession().getServletContext().getRealPath("/upload");
		String fileName=request.getParameter("fileName");
		/*fileName = new String(fileName.getBytes("gb2312"),"ISO8859-1");*/
		String downPath=path+"/"+fileName;
		log.info(downPath);
		 File file = new File(downPath);
		 if(!file.exists()){
			 this.success=false;
			 this.message="文件不存在";
		 }
		    //设置响应头和客户端保存文件名
		    response.setCharacterEncoding("utf-8");
		    response.setContentType("multipart/form-data");
		    response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		    //用于记录以完成的下载的数据量，单位是byte
		    long downloadedLength = 0l;
		    try {
		        //打开本地文件流
		        InputStream inputStream = new FileInputStream(downPath);
		        //激活下载操作
		        OutputStream os = response.getOutputStream();

		        //循环写入输出流
		        byte[] b = new byte[2048];
		        int length;
		        while ((length = inputStream.read(b)) > 0) {
		            os.write(b, 0, length);
		            downloadedLength += b.length;
		        }

		        // 这里主要关闭。
		        os.close();
		        inputStream.close();
		    } catch (Exception e){
		        throw e;
		    }
		    //存储记录
	}
	/**
	 * 根据序列号查询详细信息
	 * wlk
	 */
	@RequestMapping(value="/selectbynum",method = RequestMethod.POST)
	public void selectbynum(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String serialNumber=request.getParameter("serialNumber");
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
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/assetTransferList",method = RequestMethod.GET)
	public void assetTransfer(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		int type=funUtil.StringToInt(request.getParameter("type"));
		String name=request.getParameter("name");
		String model=request.getParameter("model");
		String serialNumber=request.getParameter("serialNumber");
		int from=funUtil.StringToInt(request.getParameter("from"));
		int status=funUtil.StringToInt(request.getParameter("status"));
		int start=funUtil.StringToInt(request.getParameter("start"));
		int limit=funUtil.StringToInt(request.getParameter("limit"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("type", type);
		map.put("name", name);
		map.put("model",model );
		map.put("serialNumber",serialNumber );
		map.put("from",from );
		map.put("status",status );
		map.put("start", start);
		map.put("limit", limit);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("totals",BusinessService.assetTransferCount(map));
//		System.out.println("<--------------->"+BusinessService.assetTransferCount(map));
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
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/insertAssetTransfer",method = RequestMethod.POST)
	public void insertAssetTransfer(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String jsonData=request.getParameter("formData");
        AssetTransferBean bean=GsonUtil.json2Object(jsonData, AssetTransferBean.class);
        bean.setCreateTime(funUtil.nowDate());
		log.info("data==>"+bean.toString());
		int rlt=BusinessService.insertAssetTransfer(bean);
		int rlt_1=BusinessService.updateAssetTransfer2(bean);
		System.out.println("<---------------->"+bean.toString());
		if (rlt==1&&rlt_1==1) {
			this.message="添加资产移交成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(1);
			webLogBean.setContent("新增资产，data="+bean.toString());
			WebLogService.writeLog(webLogBean);
		}else {
			this.message="添加资产移交失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("result",rlt);
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
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/updateAssetTransfer",method = RequestMethod.POST)
	public void updateAssetTransfer(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String jsonData=request.getParameter("formData");
        AssetTransferBean bean=GsonUtil.json2Object(jsonData, AssetTransferBean.class);
		log.info("data==>"+bean.toString());
		int rlt=BusinessService.updateAssetTransfer(bean);
		int rlt_1=BusinessService.updateAssetTransfer2(bean);
		if (rlt==1&&rlt_1==1) {
			this.message="修改资产记录成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(2);
			webLogBean.setContent("修改资产记录，data="+bean.toString());
			WebLogService.writeLog(webLogBean);
			
		}else {
			this.message="修改资产记录失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("result",rlt);
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
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/deleteAssetTransfer",method = RequestMethod.POST)
	public void deleteAssetTransfer(HttpServletRequest request, HttpServletResponse response){
		String id=request.getParameter("deleteIds");
		List<String> list = new ArrayList<String>();
		String[] ids=id.split(",");
		for (String str : ids) {
			list.add(str);
		}
		//log.info("data==>"+bean.toString());
		int rlt=BusinessService.deleteAssetTransfer(list);
		if (rlt==1) {
			this.message="删除资产记录成功";
//			this.message="添加资产成功";
			webLogBean.setOperator(funUtil.loginUser(request));
			webLogBean.setOperatorIp(funUtil.getIpAddr(request));
			webLogBean.setStyle(3);
			webLogBean.setContent("删除资产记录，data="+id);
			WebLogService.writeLog(webLogBean);
		}else {
			this.message="删除资产记录失败";
		}

		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		result.put("result",rlt);
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


