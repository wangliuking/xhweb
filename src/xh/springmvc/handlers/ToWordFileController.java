package xh.springmvc.handlers;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import jxl.write.Label;

import org.apache.commons.lang.WordUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xh.func.plugin.ChartUtil;
import xh.func.plugin.FreeChartUtil;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.EastBsCallDataBean;
import xh.mybatis.bean.EastVpnCallBean;
import xh.mybatis.service.ReportDayService;
import xh.mybatis.service.ToWorkFileServices;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfParagraphStyle;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordwriter.DataRegion;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;

@Controller
@RequestMapping(value = "/word")
public class ToWordFileController {
	/**
	 * 页眉
	 */
	public String header = "";

	/**
	 * 文档字体大小，页脚页眉最好和文本大小一致
	 */
	public int presentFontSize = 12;

	/**
	 * 文档页面大小，最好前面传入，否则默认为A4纸张
	 */
	public static Rectangle pageSize = PageSize.A4;

	public static void main(String[] args) {
		String saveDir = "D:/";
		String time = "2019-03";
		String pathname = saveDir + "/系统运行维护服务月报  -" + time + ".doc";
		try {
			to_word(time, 3, pathname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("/meet")
	public ModelAndView meet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String time="2019-04";
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		WordDocument wordDoc = new WordDocument();
		wordDoc.setEnableAllDataRegionsEditing(true);

		wordDoc.openDataRegion("PO_Month").setValue(time);
		w_1(wordDoc,time);
		
		w_3_1(wordDoc,time,request);
		w_3_2(wordDoc,time,request);
		
		w_4_2(wordDoc,time);
		w_4_3(wordDoc,request,time);
		w_4_3_1(wordDoc,request,time);

		poCtrl.setWriter(wordDoc);
		String path = "doc/template/月报.docx";
		/*
		 * String path=request.getSession().getServletContext().getRealPath("")+
		 * "/doc/meet.doc"; String
		 * savePath=request.getSession().getServletContext
		 * ().getRealPath("doc/meet/"); Date d = new Date(); SimpleDateFormat
		 * sdf = new SimpleDateFormat("yyyy-MM-dd"); String[] data =
		 * sdf.format(d).split(" ")[0].split("-"); savePath +=data[0];
		 * 
		 * System.out.println("地址："+savePath); //path=data[0] + "/" + data[1] +
		 * "/"+ data[2]; String
		 * filePath=savePath+"/"+FunUtil.MD5(String.valueOf(new
		 * Date().getTime()))+".doc"; File file=new File(savePath);
		 * if(!file.exists()){ file.mkdirs(); } File source=new File(path); File
		 * dst=new File(filePath); try { FunUtil.copyFile(source, dst); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		poCtrl.setServerPage("../poserver.zz");// 设置授权程序servlet
		poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
		poCtrl.addCustomToolButton("打印", "PrintFile()", 6);
		poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
		poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
		String name = "";
		try {
			name = URLEncoder.encode(path, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		poCtrl.setSaveFilePage(request.getContextPath()+ "/office/save_page?path=" + name);
		poCtrl.setJsFunction_AfterDocumentOpened("IsFullScreen()");
		poCtrl.webOpen(request.getSession().getServletContext().getRealPath(path),OpenModeType.docNormalEdit, "");
		poCtrl.setTagId("PageOfficeCtrl1");
		ModelAndView mv = new ModelAndView("Views/jsp/month_report");
		mv.addObject("poCtrl", poCtrl);
		mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		return mv;
	}

	public WordDocument w_1(WordDocument doc,String time) {
		doc.openDataRegion("PO_w_1_1").setValue("191");
		doc.openDataRegion("PO_w_1_2").setValue("188");
		doc.openDataRegion("PO_w_1_3").setValue("3");

		return doc;
	}

	/*系统运行状态*/
	public WordDocument w_2(WordDocument doc) {
		com.zhuozhengsoft.pageoffice.wordwriter.Table table1 = doc.openDataRegion("PO_w_2_t_001").openTable(1);
		 
	   /*// table1.openCellRC(1,1).setValue("PageOffice组件");
		int dataRowCount = 5;//需要插入数据的行数
		int oldRowCount = 3;//表格中原有的行数
		// 扩充表格
	    for (int j = 0; j < dataRowCount - oldRowCount; j++)
	    {
	        table1.insertRowAfter(table1.openCellRC(2, 5));  //在第2行的最后一个单元格下插入新行
	    }
		// 填充数据
	    int i = 1;
	    while (i <= dataRowCount)
	    {   
	        table1.openCellRC(i, 2).setValue("AA" + String.valueOf(i));
	        table1.openCellRC(i, 3).setValue("BB" + String.valueOf(i));
	        table1.openCellRC(i, 4).setValue("CC" + String.valueOf(i));
	        table1.openCellRC(i, 5).setValue("DD" + String.valueOf(i));
	        i++;
	    }*/
		return doc;
	}

	/*3.话务统计*/
	public WordDocument w_3_1(WordDocument doc,String time,HttpServletRequest request) throws Exception {
		String time1 = "", time2 = time, time3 = "";
		time1 = (Integer.parseInt(time2.split("-")[0]) - 1) + "-"
				+ time2.split("-")[1];
		if (time2.split("-")[1].equals("01")) {
			time3 = (Integer.parseInt(time2.split("-")[0]) - 1) + "-12";
		} else {
			int y = Integer.parseInt(time2.split("-")[1]) - 1;
			time3 = time2.split("-")[0] + "-" + (y < 10 ? ("0" + y) : y);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("time", time1);
		paraMap.put("period", 3);
		Map<String, Object> map_last_year_call = ToWorkFileServices
				.system_call(paraMap);
		paraMap.put("time", time2);
		paraMap.put("period", 3);
		Map<String, Object> map_last_month_call = ToWorkFileServices
				.system_call(paraMap);
		paraMap.put("time", time3);
		paraMap.put("period", 3);
		Map<String, Object> map_now_call = ToWorkFileServices.system_call(paraMap);
		doc.openDataRegion("PO_w_3_1_1").setValue(String.valueOf(Double.valueOf(map_now_call.get("TotalActiveCall").toString()) / 10000));
		Double a1 = (double) 0;
		Double a2 = (double) 0;
		a1 = Double.valueOf(map_last_year_call == null ? "0"
				: map_last_year_call.get("TotalActiveCall").toString());
		a2 = Double.valueOf(map_now_call.get("TotalActiveCall").toString());
		doc.openDataRegion("PO_w_3_1_2").setValue((a1 < a2 ? "增加"
							+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
							: "减少"
									+ String.format("%.2f", (a1 - a2) / a1
											* 100) + "%"));
		a1 = Double.valueOf(map_last_month_call == null ? "0"
				: map_last_month_call.get("TotalActiveCall").toString());
		a2 = Double.valueOf(map_now_call.get("TotalActiveCall").toString());
		doc.openDataRegion("PO_w_3_1_3").setValue(a1 < a2 ? "增加"
				+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
				: "减少"
						+ String.format("%.2f", (a1 - a2) / a1
								* 100) + "%");
		doc.openDataRegion("PO_w_3_1_4").setValue(String.valueOf(map_now_call.get("TotalActiveCallDuration")));
		a1 = Double.valueOf(map_last_year_call == null ? "0"
				: map_last_year_call.get("TotalActiveCallDuration")
						.toString());
		a2 = Double.valueOf(map_now_call.get("TotalActiveCallDuration")
				.toString());
		doc.openDataRegion("PO_w_3_1_5").setValue(a1 < a2 ? "增加"
				+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
				: "减少"
						+ String.format("%.2f", (a1 - a2) / a1
								* 100) + "%");
		a1 = Double.valueOf(map_last_month_call == null ? "0"
				: map_last_month_call.get("TotalActiveCallDuration")
						.toString());
		a2 = Double.valueOf(map_now_call.get("TotalActiveCallDuration")
				.toString());
		doc.openDataRegion("PO_w_3_1_6").setValue(a1 < a2 ? "增加"
				+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
				: "减少"
						+ String.format("%.2f", (a1 - a2) / a1
								* 100) + "%");
		doc.openDataRegion("PO_w_3_1_7").setValue(map_now_call.get("AverageCallDuration").toString());
		doc.openDataRegion("PO_w_3_1_8").setValue(map_now_call.get("FailedPercentage").toString());
		doc.openDataRegion("PO_w_3_1_9").setValue(map_now_call.get("totalQueueCount").toString());
		a1 = Double.valueOf(map_last_year_call == null ? "0"
				: map_last_year_call.get("AverageCallDuration").toString());
		a2 = Double.valueOf(map_now_call.get("AverageCallDuration")
				.toString());
		doc.openDataRegion("PO_w_3_1_10").setValue(a1 < a2 ? "增加"
				+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
				: "减少"
						+ String.format("%.2f", (a1 - a2) / a1
								* 100) + "%");
		a1 = Double
				.valueOf(map_last_month_call == null ? "0"
						: map_last_month_call.get("AverageCallDuration")
								.toString());
		a2 = Double.valueOf(map_now_call.get("AverageCallDuration")
				.toString());
		doc.openDataRegion("PO_w_3_1_11").setValue(a1 < a2 ? "增加"
				+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
				: "减少"
						+ String.format("%.2f", (a1 - a2) / a1
								* 100) + "%");
		
		doc.openDataRegion("PO_w_3_1_12").setValue(map_now_call.get("totalMaxReg").toString());
		a1 = Double.valueOf(map_last_year_call == null ? "0"
				: map_last_year_call.get("totalMaxReg").toString());
		a2 = Double.valueOf(map_now_call.get("totalMaxReg").toString());
		doc.openDataRegion("PO_w_3_1_13").setValue(a1 < a2 ? "增加"
				+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
				: "减少"
						+ String.format("%.2f", (a1 - a2) / a1
								* 100) + "%");
		a1 = Double.valueOf(map_last_month_call == null ? "0"
				: map_last_month_call.get("totalMaxReg").toString());
		a2 = Double.valueOf(map_now_call.get("totalMaxReg").toString());
		doc.openDataRegion("PO_w_3_1_14").setValue(a1 < a2 ? "增加"
				+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
				: "减少"
						+ String.format("%.2f", (a1 - a2) / a1
								* 100) + "%");
		doc.openDataRegion("PO_w_3_1_15").setValue(String.valueOf(Double.valueOf(map_now_call.get("gpsTotals").toString()) / 10000));
		a1 = Double.valueOf(map_last_year_call == null ? "0"
				: map_last_year_call.get("gpsTotals").toString());
		a2 = Double.valueOf(map_now_call.get("gpsTotals").toString());
		doc.openDataRegion("PO_w_3_1_16").setValue(a1 < a2 ? "增加"
				+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
				: "减少"
						+ String.format("%.2f", (a1 - a2) / a1
								* 100) + "%");
		a1 = Double.valueOf(map_last_month_call == null ? "0"
				: map_last_month_call.get("gpsTotals").toString());
		a2 = Double.valueOf(map_now_call.get("gpsTotals").toString());
		doc.openDataRegion("PO_w_3_1_17").setValue(a1 < a2 ? "增加"
				+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
				: "减少"
						+ String.format("%.2f", (a1 - a2) / a1
								* 100) + "%");
		//记录表
		com.zhuozhengsoft.pageoffice.wordwriter.Table table1 = doc.openDataRegion("PO_w_3_1_t_001").openTable(1);
		table1.insertRowAfter(table1.openCellRC(1, 12));  //在第1行的最后一个单元格下插入新行
		table1.openCellRC(2, 1).setValue(map_now_call.get("bsTotals").toString());
        table1.openCellRC(2, 2).setValue(map_now_call.get("TotalCalls").toString());
        table1.openCellRC(2, 3).setValue(map_now_call.get("TotalActiveCall").toString());
        table1.openCellRC(2, 4).setValue(map_now_call.get("TotalActiveCallDuration").toString());
        table1.openCellRC(2, 5).setValue(FunUtil.second_time(Double.valueOf(map_now_call.get("AverageCallDuration").toString()).intValue()));
        table1.openCellRC(2, 6).setValue(map_now_call.get("TotalFailedCalls").toString());
        table1.openCellRC(2, 7).setValue(map_now_call.get("NoEffectCalls").toString());
        table1.openCellRC(2, 8).setValue(map_now_call.get("FailedPercentage").toString());
        table1.openCellRC(2, 9).setValue(map_now_call.get("totalQueueCount").toString());
        table1.openCellRC(2, 10).setValue(map_now_call.get("totalMaxReg").toString());
        table1.openCellRC(2, 11).setValue(map_now_call.get("maxRegGroup").toString());
        table1.openCellRC(2, 12).setValue(map_now_call.get("gpsTotals").toString());
        
        //chart
        List<Map<String, Object>> list1 = ToWorkFileServices
				.system_call_year(String.valueOf(Integer.parseInt(time
						.split("-")[0]) - 2));
		List<Map<String, Object>> list2 = ToWorkFileServices
				.system_call_year(String.valueOf(Integer.parseInt(time
						.split("-")[0]) - 1));
		List<Map<String, Object>> list3 = ToWorkFileServices
				.system_call_year(String.valueOf(Integer.parseInt(time
						.split("-")[0])));
		TimeSeries timeSeries1 = new TimeSeries(Integer.parseInt(time
				.split("-")[0]) - 2 + "年系统总通话次数");
		TimeSeries timeSeries2 = new TimeSeries(Integer.parseInt(time
				.split("-")[0]) - 1 + "年系统总通话次数");
		TimeSeries timeSeries3 = new TimeSeries(Integer.parseInt(time
				.split("-")[0]) + "年系统总通话次数");
		for (Map<String, Object> map : list1) {
			timeSeries1.add(
					new Month(Integer.parseInt(map.get("monthstr")
							.toString()),
							Integer.parseInt(time.split("-")[0])), Double
							.valueOf(map.get("TotalCalls").toString()));
		}
		for (Map<String, Object> map : list2) {
			timeSeries2.add(
					new Month(Integer.parseInt(map.get("monthstr")
							.toString()),
							Integer.parseInt(time.split("-")[0])), Double
							.valueOf(map.get("TotalCalls").toString()));
		}
		for (Map<String, Object> map : list3) {
			timeSeries3.add(
					new Month(Integer.parseInt(map.get("monthstr")
							.toString()),
							Integer.parseInt(time.split("-")[0])), Double
							.valueOf(map.get("TotalCalls").toString()));
		}
		FreeChartUtil.ThreeLineImg("系统总通话次数", timeSeries1, timeSeries2,
				timeSeries3,request,"PO_w_3_1_img_001.png");
		doc.openDataRegion("PO_w_3_1_img_001").setValue("[image]../pic/PO_w_3_1_img_001.png[/image]");
		
		list1 = ToWorkFileServices.system_gps_year(String.valueOf(Integer
				.parseInt(time.split("-")[0]) - 2));
		list2 = ToWorkFileServices.system_gps_year(String.valueOf(Integer
				.parseInt(time.split("-")[0]) - 1));
		list3 = ToWorkFileServices.system_gps_year(String.valueOf(Integer
				.parseInt(time.split("-")[0])));
		timeSeries1 = new TimeSeries(Integer.parseInt(time.split("-")[0])
				- 2 + "年GPS上报");
		timeSeries2 = new TimeSeries(Integer.parseInt(time.split("-")[0])
				- 1 + "年GPS上报");
		timeSeries3 = new TimeSeries(Integer.parseInt(time.split("-")[0])
				+ "年GPS上报");

		for (Map<String, Object> map : list1) {
			timeSeries1.add(
					new Month(Integer.parseInt(map.get("monthstr")
							.toString()),
							Integer.parseInt(time.split("-")[0])), Double
							.valueOf(map.get("totals").toString()));
		}
		for (Map<String, Object> map : list2) {
			timeSeries2.add(
					new Month(Integer.parseInt(map.get("monthstr")
							.toString()),
							Integer.parseInt(time.split("-")[0])), Double
							.valueOf(map.get("totals").toString()));
		}
		for (Map<String, Object> map : list3) {
			timeSeries3.add(
					new Month(Integer.parseInt(map.get("monthstr")
							.toString()),
							Integer.parseInt(time.split("-")[0])), Double
							.valueOf(map.get("totals").toString()));
		}
		FreeChartUtil.ThreeLineImg("短数据上报数量", timeSeries1, timeSeries2,
				timeSeries3,request,"PO_w_3_1_img_002.png");
		
		doc.openDataRegion("PO_w_3_1_img_002").setValue("[image]../pic/PO_w_3_1_img_001.png[/image]");
		
		return doc;
	}
	public WordDocument w_3_2(WordDocument doc,String time,HttpServletRequest request) throws Exception {
		/*一、二、三级基站话务统计*/
		w_3_2_1(doc,time,request);
		/*绕城内外基站话务统计*/	
		w_3_2_2(doc,time,request);
		/*各行政区域基站话务*/
		w_3_2_3(doc,time,request);	
		/*基站话务统计TOP10*/
		w_3_2_4(doc,time,request);
		/*基站排队话务统计TOP10*/
		w_3_2_5(doc,time,request);
		/*用户单位话务统计TOP10*/
		w_3_2_6(doc,time,request);
		/*基站最大注册用户数前十*/
		w_3_2_7(doc,time,request);
		return doc;
	}
	
	/*一、二、三级基站话务统计*/
	public WordDocument w_3_2_1(WordDocument doc,String time,HttpServletRequest request){
		EastBsCallDataBean bean11 = new EastBsCallDataBean();
		EastBsCallDataBean bean12 = new EastBsCallDataBean();
		EastBsCallDataBean bean13 = new EastBsCallDataBean();

		EastBsCallDataBean bean21 = new EastBsCallDataBean();
		EastBsCallDataBean bean22 = new EastBsCallDataBean();
		EastBsCallDataBean bean23 = new EastBsCallDataBean();

		String time2 = "", time3 = time;
		time2 = (Integer.parseInt(time3.split("-")[0]) - 1) + "-"
				+ time3.split("-")[1];
		if (time3.split("-")[1].equals("01")) {
			time2 = (Integer.parseInt(time3.split("-")[0]) - 1) + "-12";
		} else {
			int y = Integer.parseInt(time3.split("-")[1]) - 1;
			time2 = time3.split("-")[0] + "-" + (y < 10 ? ("0" + y) : y);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("time", time2);
		map.put("period", 4);
		List<EastBsCallDataBean> list2 = ToWorkFileServices
				.system_call_level(map);
		map.put("time", time3);
		map.put("period", 4);
		List<EastBsCallDataBean> list3 = ToWorkFileServices
				.system_call_level(map);
		for (EastBsCallDataBean eastBsCallDataBean : list2) {
			if (eastBsCallDataBean.getLevel().equals("1")) {
				bean11 = eastBsCallDataBean;
			} else if (eastBsCallDataBean.getLevel().equals("2")) {
				bean12 = eastBsCallDataBean;
			} else if (eastBsCallDataBean.getLevel().equals("3")) {
				bean13 = eastBsCallDataBean;
			}
		}
		for (EastBsCallDataBean eastBsCallDataBean : list3) {
			if (eastBsCallDataBean.getLevel().equals("1")) {
				bean21 = eastBsCallDataBean;
			} else if (eastBsCallDataBean.getLevel().equals("2")) {
				bean22 = eastBsCallDataBean;
			} else if (eastBsCallDataBean.getLevel().equals("3")) {
				bean23 = eastBsCallDataBean;
			}
		}
		Double a1 = (double) 0;
		Double a2 = (double) 0;

		doc.openDataRegion("PO_w_3_2_1").setValue(String.valueOf(Double.valueOf(bean21.getTotalActiveCalls()) / 10000));
		a1 = Double.valueOf(bean11.getTotalActiveCalls());
		a2 = Double.valueOf(bean21.getTotalActiveCalls());
		doc.openDataRegion("PO_w_3_2_2").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		doc.openDataRegion("PO_w_3_2_3").setValue(String.valueOf(bean21.getTotalActiveCallDuration()));
		a1 = Double.valueOf(bean11.getTotalActiveCallDuration());
		a2 = Double.valueOf(bean21.getTotalActiveCallDuration());
		doc.openDataRegion("PO_w_3_2_4").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		doc.openDataRegion("PO_w_3_2_5").setValue(String.valueOf(bean21.getAverageCallDuration()));
		doc.openDataRegion("PO_w_3_2_6").setValue(String.valueOf(bean21.getQueueCount()));
		a1 = Double.valueOf(bean11.getQueueCount());
		a2 = Double.valueOf(bean21.getQueueCount());
		doc.openDataRegion("PO_w_3_2_7").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		doc.openDataRegion("PO_w_3_2_8").setValue(String.valueOf(Double.valueOf(bean22.getTotalActiveCalls()) / 10000));
		a1 = Double.valueOf(bean12.getTotalActiveCalls());
		a2 = Double.valueOf(bean22.getTotalActiveCalls());
		doc.openDataRegion("PO_w_3_2_9").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		doc.openDataRegion("PO_w_3_2_10").setValue(String.valueOf(bean22.getTotalActiveCallDuration()));
		a1 = Double.valueOf(bean12.getTotalActiveCallDuration());
		a2 = Double.valueOf(bean22.getTotalActiveCallDuration());
		doc.openDataRegion("PO_w_3_2_11").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		doc.openDataRegion("PO_w_3_2_12").setValue(String.valueOf(bean22.getAverageCallDuration()));
		doc.openDataRegion("PO_w_3_2_13").setValue(String.valueOf(bean22.getQueueCount()));
		a1 = Double.valueOf(bean12.getQueueCount());
		a2 = Double.valueOf(bean22.getQueueCount());
		doc.openDataRegion("PO_w_3_2_14").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		doc.openDataRegion("PO_w_3_2_15").setValue(String.valueOf(Double.valueOf(bean23.getTotalActiveCalls()) / 10000));
		a1 = Double.valueOf(bean13.getTotalActiveCalls());
		a2 = Double.valueOf(bean23.getTotalActiveCalls());
		doc.openDataRegion("PO_w_3_2_16").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		doc.openDataRegion("PO_w_3_2_17").setValue(String.valueOf(bean23.getTotalActiveCallDuration()));
		a1 = Double.valueOf(bean13.getTotalActiveCallDuration());
		a2 = Double.valueOf(bean23.getTotalActiveCallDuration());
		doc.openDataRegion("PO_w_3_2_18").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		doc.openDataRegion("PO_w_3_2_19").setValue(String.valueOf(bean23.getAverageCallDuration()));
		doc.openDataRegion("PO_w_3_2_20").setValue(String.valueOf(bean23.getQueueCount()));
		a1 = Double.valueOf(bean13.getQueueCount());
		a2 = Double.valueOf(bean23.getQueueCount());
		doc.openDataRegion("PO_w_3_2_21").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		
		
		com.zhuozhengsoft.pageoffice.wordwriter.Table table1 = doc.openDataRegion("PO_w_3_2_t_001").openTable(1);
		table1.insertRowAfter(table1.openCellRC(1, 9));  //在第1行的最后一个单元格下插入新行
		table1.openCellRC(2, 1).setValue("一级基站");
        table1.openCellRC(2, 2).setValue(String.valueOf(bean21
				.getBsTotals()));
        table1.openCellRC(2, 3).setValue(String.valueOf(bean21
				.getTotalActiveCalls()));
        table1.openCellRC(2, 4).setValue(String.valueOf(Math.round(Double.valueOf(bean21
				.getTotalActiveCalls()) / bean21.getBsTotals())));
        table1.openCellRC(2, 5).setValue(String.valueOf(bean21
				.getTotalActiveCallDuration()));
        table1.openCellRC(2, 6).setValue(String.valueOf(Math.round(Double
				.valueOf(bean21.getTotalActiveCallDuration()))
				/ bean21.getBsTotals()));
        table1.openCellRC(2, 7).setValue(String.valueOf(bean21
				.getQueueCount()));
        table1.openCellRC(2, 8).setValue(String.valueOf(bean21
				.getMaxUserRegCount()));
        table1.openCellRC(2, 9).setValue(String.valueOf(bean21
				.getMaxGroupRegCount()));
        
        
        table1.insertRowAfter(table1.openCellRC(2, 9));  //在第1行的最后一个单元格下插入新行
        table1.openCellRC(3, 1).setValue("二级基站");
        table1.openCellRC(3, 2).setValue(String.valueOf(bean22
				.getBsTotals()));
        table1.openCellRC(3, 3).setValue(String.valueOf(bean22
				.getTotalActiveCalls()));
        table1.openCellRC(3, 4).setValue(String.valueOf(Math.round(Double.valueOf(bean22
				.getTotalActiveCalls()) / bean22.getBsTotals())));
        table1.openCellRC(3, 5).setValue(String.valueOf(bean22
				.getTotalActiveCallDuration()));
        table1.openCellRC(3, 6).setValue(String.valueOf(Math.round(Double
				.valueOf(bean22.getTotalActiveCallDuration()))
				/ bean22.getBsTotals()));
        table1.openCellRC(3, 7).setValue(String.valueOf(bean22
				.getQueueCount()));
        table1.openCellRC(3, 8).setValue(String.valueOf(bean22
				.getMaxUserRegCount()));
        table1.openCellRC(3, 9).setValue(String.valueOf(bean22
				.getMaxGroupRegCount()));
        
        table1.insertRowAfter(table1.openCellRC(3, 9));  //在第1行的最后一个单元格下插入新行
        table1.openCellRC(4, 1).setValue("三级基站");
        table1.openCellRC(4, 2).setValue(String.valueOf(bean23
				.getBsTotals()));
        table1.openCellRC(4, 3).setValue(String.valueOf(bean23
				.getTotalActiveCalls()));
        table1.openCellRC(4, 4).setValue(String.valueOf(Math.round(Double.valueOf(bean23
				.getTotalActiveCalls()) / bean23.getBsTotals())));
        table1.openCellRC(4, 5).setValue(String.valueOf(bean23
				.getTotalActiveCallDuration()));
        table1.openCellRC(4, 6).setValue(String.valueOf(Math.round(Double
				.valueOf(bean23.getTotalActiveCallDuration()))
				/ bean23.getBsTotals()));
        table1.openCellRC(4, 7).setValue(String.valueOf(bean23
				.getQueueCount()));
        table1.openCellRC(4, 8).setValue(String.valueOf(bean23
				.getMaxUserRegCount()));
        table1.openCellRC(4, 9).setValue(String.valueOf(bean23
				.getMaxGroupRegCount()));
        
        if (Integer.parseInt(time.split("-")[1]) > 6) {
			time2 = time.split("-")[0] + "-07";
			time3 = (Integer.parseInt(time.split("-")[0]) + 1) + "-06";

		} else {
			time2 = (Integer.parseInt(time.split("-")[0]) - 1) + "-07";
			time3 = time.split("-")[0] + "-06";
		}
		map.put("starttime", time2);
		map.put("endtime", time3);
		list2 = ToWorkFileServices.system_call_year_level(map);

		DefaultCategoryDataset dcd = new DefaultCategoryDataset();

		for (EastBsCallDataBean bean : list2) {
			if (bean.getLevel().equals("1")) {
				dcd.addValue(
						Math.round(Double.valueOf(bean.getTotalActiveCalls())
								/ bean.getBsTotals()), "一级基站平均呼叫次数",
						bean.getStarttime());
			} else if (bean.getLevel().equals("2")) {
				dcd.addValue(
						Math.round(Double.valueOf(bean.getTotalActiveCalls())
								/ bean.getBsTotals()), "二级基站平均呼叫次数",
						bean.getStarttime());
			} else if (bean.getLevel().equals("3")) {
				dcd.addValue(
						Math.round(Double.valueOf(bean.getTotalActiveCalls())
								/ bean.getBsTotals()), "三级基站平均呼叫次数",
						bean.getStarttime());
			}

		}

		try {
			FreeChartUtil.LineImg("一、二、三级基站平均呼叫次数趋势图", dcd,request,"PO_w_3_2_img_001.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.openDataRegion("PO_w_3_2_img_001").setValue("[image]../pic/PO_w_3_2_img_001.png[/image]");
		return doc;
	}
	/*绕城内外基站话务统计*/
	public WordDocument w_3_2_2(WordDocument doc,String time,HttpServletRequest request){
		EastBsCallDataBean bean11 = new EastBsCallDataBean();
		EastBsCallDataBean bean12 = new EastBsCallDataBean();

		EastBsCallDataBean bean21 = new EastBsCallDataBean();
		EastBsCallDataBean bean22 = new EastBsCallDataBean();

		String time2 = "", time3 = time;
		time2 = (Integer.parseInt(time3.split("-")[0]) - 1) + "-"
				+ time3.split("-")[1];
		if (time3.split("-")[1].equals("01")) {
			time2 = (Integer.parseInt(time3.split("-")[0]) - 1) + "-12";
		} else {
			int y = Integer.parseInt(time3.split("-")[1]) - 1;
			time2 = time3.split("-")[0] + "-" + (y < 10 ? ("0" + y) : y);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("time", time2);
		map.put("period", 4);
		List<EastBsCallDataBean> list2 = ToWorkFileServices
				.system_call_area(map);
		map.put("time", time3);
		map.put("period", 4);
		List<EastBsCallDataBean> list3 = ToWorkFileServices
				.system_call_area(map);
		for (EastBsCallDataBean eastBsCallDataBean : list2) {
			if (eastBsCallDataBean.getArea().equals("绕城内")) {
				bean11 = eastBsCallDataBean;
			} else if (eastBsCallDataBean.getArea().equals("绕城外")) {
				bean12 = eastBsCallDataBean;
			}
		}
		for (EastBsCallDataBean eastBsCallDataBean : list3) {
			if (eastBsCallDataBean.getArea().equals("绕城内")) {
				bean21 = eastBsCallDataBean;
			} else if (eastBsCallDataBean.getArea().equals("绕城外")) {
				bean22 = eastBsCallDataBean;
			}
		}
		Double a1 = (double) 0;
		Double a2 = (double) 0;

		com.zhuozhengsoft.pageoffice.wordwriter.Table table2 = doc.openDataRegion("PO_w_3_2_t_002").openTable(1);
		table2.insertRowAfter(table2.openCellRC(1, 9));  //在第1行的最后一个单元格下插入新行
		table2.openCellRC(2, 1).setValue("绕城内");
		table2.openCellRC(2, 2).setValue(String.valueOf(bean21.getBsTotals()));
		table2.openCellRC(2, 3).setValue(String.valueOf(bean21.getTotalActiveCalls()));
		table2.openCellRC(2, 4).setValue(String.valueOf(Math.round(Double.valueOf(bean21.getTotalActiveCalls()) / bean21.getBsTotals())));
		table2.openCellRC(2, 5).setValue(String.valueOf(bean21.getTotalActiveCallDuration()));
		table2.openCellRC(2, 6).setValue(String.valueOf(Math.round(Double.valueOf(bean21.getTotalActiveCallDuration())/ bean21.getBsTotals())));
		table2.openCellRC(2, 7).setValue(String.valueOf(bean21.getQueueCount()));
		table2.openCellRC(2, 8).setValue(String.valueOf(bean21.getMaxUserRegCount()));
		table2.openCellRC(2, 9).setValue(String.valueOf(bean21.getMaxGroupRegCount()));
		
		table2.insertRowAfter(table2.openCellRC(2, 9));  //在第1行的最后一个单元格下插入新行
		table2.openCellRC(3, 1).setValue("绕城外");
		table2.openCellRC(3, 2).setValue(String.valueOf(bean22.getBsTotals()));
		table2.openCellRC(3, 3).setValue(String.valueOf(bean22.getTotalActiveCalls()));
		table2.openCellRC(3, 4).setValue(String.valueOf(Math.round(Double.valueOf(bean22.getTotalActiveCalls()) / bean22.getBsTotals())));
		table2.openCellRC(3, 5).setValue(String.valueOf(bean22.getTotalActiveCallDuration()));
		table2.openCellRC(3, 6).setValue(String.valueOf(Math.round(Double.valueOf(bean22.getTotalActiveCallDuration())/ bean22.getBsTotals())));
		table2.openCellRC(3, 7).setValue(String.valueOf(bean22.getQueueCount()));
		table2.openCellRC(3, 8).setValue(String.valueOf(bean22.getMaxUserRegCount()));
		table2.openCellRC(3, 9).setValue(String.valueOf(bean22.getMaxGroupRegCount()));
		
		if (Integer.parseInt(time.split("-")[1]) > 6) {
			time2 = time.split("-")[0] + "-07";
			time3 = (Integer.parseInt(time.split("-")[0]) + 1) + "-06";

		} else {
			time2 = (Integer.parseInt(time.split("-")[0]) - 1) + "-07";
			time3 = time.split("-")[0] + "-06";
		}
		map.put("starttime", time2);
		map.put("endtime", time3);
		list2 = ToWorkFileServices.system_call_year_area(map);

		DefaultCategoryDataset dcd = new DefaultCategoryDataset();

		for (EastBsCallDataBean bean : list2) {
			if (bean.getArea().equals("绕城内")) {
				dcd.addValue(
						Math.round(Double.valueOf(bean.getTotalActiveCalls())
								/ bean.getBsTotals()), "绕城内基站平均呼叫次数",
						bean.getStarttime());
			} else if (bean.getArea().equals("绕城外")) {
				dcd.addValue(
						Math.round(Double.valueOf(bean.getTotalActiveCalls())
								/ bean.getBsTotals()), "绕城外基站平均呼叫次数",
						bean.getStarttime());
			}

		}

		try {
			FreeChartUtil.LineImg("绕城内/外基站平均呼叫次数趋势图", dcd,request,"PO_w_3_2_img_002.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.openDataRegion("PO_w_3_2_img_002").setValue("[image]../pic/PO_w_3_2_img_002.png[/image]");
		
		
		doc.openDataRegion("PO_w_3_2_30").setValue(String.valueOf(Double.valueOf(bean21.getTotalActiveCalls()) / 10000));
		a1 = Double.valueOf(bean11.getTotalActiveCalls());
		a2 = Double.valueOf(bean21.getTotalActiveCalls());
		doc.openDataRegion("PO_w_3_2_31").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		
		doc.openDataRegion("PO_w_3_2_32").setValue(String.valueOf(bean21.getTotalActiveCallDuration()));
		a1 = Double.valueOf(bean11.getTotalActiveCallDuration());
		a2 = Double.valueOf(bean21.getTotalActiveCallDuration());
		doc.openDataRegion("PO_w_3_2_33").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		doc.openDataRegion("PO_w_3_2_34").setValue(String.valueOf(bean21.getAverageCallDuration()));
		doc.openDataRegion("PO_w_3_2_35").setValue(String.valueOf(bean21.getQueueCount() ));
		a1 = Double.valueOf(bean11.getQueueCount());
		a2 = Double.valueOf(bean21.getQueueCount());
		doc.openDataRegion("PO_w_3_2_36").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		doc.openDataRegion("PO_w_3_2_37").setValue(String.valueOf(Double.valueOf(bean22.getTotalActiveCalls()) / 10000));
		a1 = Double.valueOf(bean12.getTotalActiveCalls());
		a2 = Double.valueOf(bean22.getTotalActiveCalls());
		doc.openDataRegion("PO_w_3_2_38").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		doc.openDataRegion("PO_w_3_2_39").setValue(String.valueOf(bean22.getTotalActiveCallDuration()));
		a1 = Double.valueOf(bean12.getTotalActiveCallDuration());
		a2 = Double.valueOf(bean22.getTotalActiveCallDuration());
		doc.openDataRegion("PO_w_3_2_40").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		doc.openDataRegion("PO_w_3_2_41").setValue(String.valueOf(bean22.getAverageCallDuration()));
		doc.openDataRegion("PO_w_3_2_42").setValue(String.valueOf(bean22.getQueueCount()));
		a1 = Double.valueOf(bean12.getQueueCount());
		a2 = Double.valueOf(bean22.getQueueCount());
		doc.openDataRegion("PO_w_3_2_43").setValue(a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
				+ "%" : "减少"
				+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%");
		return doc;
	}
	/*各行政区域基站话务*/
	public WordDocument w_3_2_3(WordDocument doc,String time,HttpServletRequest request){
		EastBsCallDataBean bean = new EastBsCallDataBean();

		List<EastBsCallDataBean> list = ToWorkFileServices
				.system_call_zone_top10(time);
		DefaultPieDataset dpd = new DefaultPieDataset();
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			dpd.setValue(eastBsCallDataBean.getZone(),
					eastBsCallDataBean.getTotalActiveCalls());
		}
		try {
			FreeChartUtil.PieImg("各行政区域基站话务占比", dpd, 2,request,"PO_w_3_2_img_003.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.openDataRegion("PO_w_3_2_img_003").setValue("[image]../pic/PO_w_3_2_img_003.png[/image]");
		/*行政区域话务占比*/
		com.zhuozhengsoft.pageoffice.wordwriter.Table table3 = doc.openDataRegion("PO_w_3_2_t_003").openTable(1);
		
		int dataRowCount=list.size();
		for(int i=0;i<dataRowCount;i++){
			table3.insertRowAfter(table3.openCellRC(1, 9));  //在第1行的最后一个单元格下插入新行	
		}
		
		int i = 1;
		int index=2;
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			table3.openCellRC(index, 1).setValue(String.valueOf(i++));
			table3.openCellRC(index, 2).setValue(eastBsCallDataBean.getZone());
			table3.openCellRC(index, 3).setValue(String.valueOf(eastBsCallDataBean.getBsTotals()));
			table3.openCellRC(index, 4).setValue(String.valueOf(eastBsCallDataBean.getTotalActiveCalls()));
			table3.openCellRC(index, 5).setValue(String.valueOf(eastBsCallDataBean.getAverageCallDuration()));
			table3.openCellRC(index, 6).setValue(String.valueOf(Math
					.round(Double.valueOf(eastBsCallDataBean
							.getTotalActiveCalls())
							/ eastBsCallDataBean.getBsTotals())));
			table3.openCellRC(index, 7).setValue(String.valueOf(eastBsCallDataBean.getQueueCount()));
			table3.openCellRC(index, 8).setValue(String.valueOf(eastBsCallDataBean.getMaxUserRegCount()));
			table3.openCellRC(index, 9).setValue(String.valueOf(eastBsCallDataBean.getMaxGroupRegCount()));
		    index++;
		}
		return doc;
	}
	/*基站话务统计TOP10*/
	public WordDocument w_3_2_4(WordDocument doc,String time,HttpServletRequest request) throws Exception{
		EastBsCallDataBean bean = new EastBsCallDataBean();

		List<EastBsCallDataBean> list = ToWorkFileServices
				.system_call_bs_top10(time);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("time", time);
		paraMap.put("period", 4);
		Map<String, Object> all_call = ToWorkFileServices.system_bs_call(time);
		com.zhuozhengsoft.pageoffice.wordwriter.Table table4 = doc.openDataRegion("PO_w_3_2_t_004").openTable(1);
		
		int dataRowCount=list.size();
		for(int i=0;i<dataRowCount;i++){
			table4.insertRowAfter(table4.openCellRC(1, 11));  //在第1行的最后一个单元格下插入新行	
		}
		int i = 1;
		int index=2;
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			table4.openCellRC(index, 1).setValue(String.valueOf(i++));
			table4.openCellRC(index, 2).setValue(String.valueOf(eastBsCallDataBean.getBsid()));
			table4.openCellRC(index, 3).setValue(eastBsCallDataBean.getName());
			table4.openCellRC(index, 4).setValue(eastBsCallDataBean.getLevel());
			table4.openCellRC(index, 5).setValue(eastBsCallDataBean.getZone());
			table4.openCellRC(index, 6).setValue(String.valueOf(eastBsCallDataBean.getTotalActiveCalls()));
			table4.openCellRC(index, 7).setValue(FunUtil.second_time(eastBsCallDataBean
							.getTotalActiveCallDuration()));
			table4.openCellRC(index, 8).setValue(FunUtil
					.second_time(eastBsCallDataBean.getAverageCallDuration()));
			table4.openCellRC(index, 9).setValue(String
					.valueOf(eastBsCallDataBean.getQueueCount()));
			table4.openCellRC(index, 10).setValue(String
					.valueOf(eastBsCallDataBean.getMaxUserRegCount()));
			table4.openCellRC(index, 11).setValue(String
					.valueOf(eastBsCallDataBean.getMaxGroupRegCount()));
			index++;
		}
		DefaultPieDataset dpd = new DefaultPieDataset();
		StringBuilder str = new StringBuilder();
		int a = 0;
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			dpd.setValue(eastBsCallDataBean.getName(),
					eastBsCallDataBean.getTotalActiveCalls());
			str.append(eastBsCallDataBean.getBsid() + "-"
					+ eastBsCallDataBean.getName() + "、");
			a += eastBsCallDataBean.getTotalActiveCalls();

		}
		FreeChartUtil.PieImg("基站话务占比 TOP10", dpd, 2,request,"PO_w_3_2_img_004.png");
		doc.openDataRegion("PO_w_3_2_img_004").setValue("[image]../pic/PO_w_3_2_img_004.png[/image]");
		doc.openDataRegion("PO_w_3_2_50").setValue(str.toString());
		doc.openDataRegion("PO_w_3_2_51").setValue(String.valueOf(a / 10000));
		doc.openDataRegion("PO_w_3_2_52").setValue(String.format(
				"%.2f",(Double.valueOf(a)/ Integer.parseInt(all_call.get("TotalCalls").toString()) * 100)));
	return doc;
}
	/*基站排队话务统计TOP10*/
	public WordDocument w_3_2_5(WordDocument doc,String time,HttpServletRequest request) throws Exception{
		EastBsCallDataBean bean = new EastBsCallDataBean();

		List<EastBsCallDataBean> list = ToWorkFileServices
				.system_call_queue_top10(time);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("time", time);
		paraMap.put("period", 4);
		Map<String, Object> all_call = ToWorkFileServices.system_bs_call(time);
		com.zhuozhengsoft.pageoffice.wordwriter.Table table5 = doc.openDataRegion("PO_w_3_2_t_005").openTable(1);
		
		int dataRowCount=list.size();
		for(int i=0;i<dataRowCount;i++){
			table5.insertRowAfter(table5.openCellRC(1, 12));  //在第1行的最后一个单元格下插入新行		
		}
		int i = 1;
		int index=2;
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			table5.openCellRC(index, 1).setValue(String.valueOf(i++));
			table5.openCellRC(index, 2).setValue(String
					.valueOf(eastBsCallDataBean.getBsid()));
			table5.openCellRC(index, 3).setValue(eastBsCallDataBean.getName());
			table5.openCellRC(index, 4).setValue(eastBsCallDataBean.getLevel());
			table5.openCellRC(index, 5).setValue(eastBsCallDataBean.getZone());
			table5.openCellRC(index, 6).setValue(String
					.valueOf(eastBsCallDataBean.getTotalActiveCalls()));
			table5.openCellRC(index, 7).setValue(FunUtil
					.second_time(eastBsCallDataBean
							.getTotalActiveCallDuration()));
			table5.openCellRC(index, 8).setValue(FunUtil
					.second_time(eastBsCallDataBean.getAverageCallDuration()));
			table5.openCellRC(index, 9).setValue(FunUtil
					.second_time(eastBsCallDataBean.getQueueDuration()));
			table5.openCellRC(index, 10).setValue(String
					.valueOf(eastBsCallDataBean.getMaxUserRegCount()));
			table5.openCellRC(index, 11).setValue(String
					.valueOf(eastBsCallDataBean.getMaxGroupRegCount()));
			index++;
		}
		DefaultPieDataset dpd = new DefaultPieDataset();
		StringBuilder str = new StringBuilder();
		int a = 0;
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			dpd.setValue(eastBsCallDataBean.getName(),
					eastBsCallDataBean.getTotalActiveCalls());
			str.append(eastBsCallDataBean.getBsid() + "-"
					+ eastBsCallDataBean.getName() + "、");
			a += eastBsCallDataBean.getQueueCount();

		}
		FreeChartUtil.PieImg("基站排队占比 TOP10", dpd, 2,request,"PO_w_3_2_img_005.png");
		doc.openDataRegion("PO_w_3_2_img_005").setValue("[image]../pic/PO_w_3_2_img_005.png[/image]");
		doc.openDataRegion("PO_w_3_2_img_004").setValue("[image]../pic/PO_w_3_2_img_004.png[/image]");
		doc.openDataRegion("PO_w_3_2_60").setValue(str.toString());
		doc.openDataRegion("PO_w_3_2_61").setValue(String.format("%.2f",
				(Double.valueOf(a)/ Integer.parseInt(all_call.get("QueueCount")
								.toString()) * 100)) + "%");
	return doc;
}
	/*用户单位话务统计TOP10*/
	public WordDocument w_3_2_6(WordDocument doc,String time,HttpServletRequest request) throws Exception{
		EastBsCallDataBean bean = new EastBsCallDataBean();

		List<EastVpnCallBean> list = ToWorkFileServices
				.system_call_vpn_top10(time);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("time", time);
		paraMap.put("period", 4);
		Map<String, Object> all_call = ToWorkFileServices.system_call(paraMap);
		com.zhuozhengsoft.pageoffice.wordwriter.Table table6 = doc.openDataRegion("PO_w_3_2_t_006").openTable(1);
		
		int dataRowCount=list.size();
		for(int i=0;i<dataRowCount;i++){
			table6.insertRowAfter(table6.openCellRC(1, 6));  //在第1行的最后一个单元格下插入新行			
		}
		int i = 1;
		int index=2;
		for (EastVpnCallBean eastBsCallDataBean : list) {
			table6.openCellRC(index, 1).setValue(String.valueOf(i++));
			table6.openCellRC(index, 2).setValue(eastBsCallDataBean.getName());
			table6.openCellRC(index, 3).setValue(String
					.valueOf(eastBsCallDataBean.getTotalActiveCalls()));
			table6.openCellRC(index, 4).setValue(FunUtil
					.second_time(eastBsCallDataBean
							.getTotalActiveCallDuration()));
			table6.openCellRC(index, 5).setValue(FunUtil
					.second_time(eastBsCallDataBean.getAverageCallDuration()));
			table6.openCellRC(index, 6).setValue(String
					.valueOf(eastBsCallDataBean.getPercent() + "%"));
			index++;
		}
		DefaultPieDataset dpd = new DefaultPieDataset();
		StringBuilder str = new StringBuilder();
		int a = 0;
		for (EastVpnCallBean eastBsCallDataBean : list) {
			dpd.setValue(eastBsCallDataBean.getName(),
					eastBsCallDataBean.getTotalActiveCalls());
			str.append(eastBsCallDataBean.getName() + "、");
			a += eastBsCallDataBean.getTotalActiveCalls();

		}
		FreeChartUtil.PieImg("用户单位话务统计 TOP10", dpd, 0,request,"PO_w_3_2_img_006.png");
		doc.openDataRegion("PO_w_3_2_img_006").setValue("[image]../pic/PO_w_3_2_img_006.png[/image]");
		
		String time1 = "";
		if (time.split("-")[1].equals("01")) {
			time1 = (Integer.parseInt(time.split("-")[0]) - 1) + "-12";
		} else {
			int y = Integer.parseInt(time.split("-")[1]) - 1;
			time1 = time.split("-")[0] + "-" + (y < 10 ? ("0" + y) : y);
		}
		List<EastVpnCallBean> list2 = ToWorkFileServices
				.system_call_vpn_top10(time1);
		DefaultCategoryDataset dct = new DefaultCategoryDataset();
		for (EastVpnCallBean eastVpnCallBean : list2) {
			dct.addValue(eastVpnCallBean.getTotalActiveCalls(), time1,
					eastVpnCallBean.getName());
		}
		for (EastVpnCallBean eastVpnCallBean : list2) {
			dct.addValue(eastVpnCallBean.getTotalActiveCalls(), time,
					eastVpnCallBean.getName());
		}
		try {
			FreeChartUtil.BarImg("用户单位话务统计 TOP10", dct,request,"PO_w_3_2_img_007.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.openDataRegion("PO_w_3_2_img_007").setValue("[image]../pic/PO_w_3_2_img_007.png[/image]");
		
		doc.openDataRegion("PO_w_3_2_70").setValue(str.toString());
		doc.openDataRegion("PO_w_3_2_71").setValue(String.format("%.2f",
				(Double.valueOf(a)
						/ Integer.parseInt(all_call.get("TotalCalls")
								.toString()) * 100)) + "%");
		
	return doc;
}
	/*基站最大注册用户数前十*/
	public WordDocument w_3_2_7(WordDocument doc,String time,HttpServletRequest request) throws Exception{
	EastBsCallDataBean bean = new EastBsCallDataBean();

	List<EastBsCallDataBean> list = ToWorkFileServices
			.chart_bs_userreg_top10(time);
	Map<String, Object> paraMap = new HashMap<String, Object>();
	paraMap.put("time", time);
	paraMap.put("period", 4);
	Map<String, Object> all_call = ToWorkFileServices.system_bs_call(time);

	DefaultCategoryDataset dpd = new DefaultCategoryDataset();
	StringBuilder str = new StringBuilder();
	int a = 0;
	for (EastBsCallDataBean eastBsCallDataBean : list) {
		dpd.setValue(eastBsCallDataBean.getMaxUserRegCount(),
				eastBsCallDataBean.getStarttime(),
				eastBsCallDataBean.getName());

	}
	if (list.size() > 0) {
		doc.openDataRegion("PO_w_3_2_72").setValue(String.valueOf(list.get(0).getMaxUserRegCount()));
		doc.openDataRegion("PO_w_3_2_73").setValue(list.get(0).getBsid() + "-"
				+ list.get(0).getName());
	}
	FreeChartUtil.BarImg("基站最大注册用户数前十", dpd,request,"PO_w_3_2_img_008.png");
	doc.openDataRegion("PO_w_3_2_img_008").setValue("[image]../pic/PO_w_3_2_img_008.png[/image]");
	return doc;
}
	
	public WordDocument w_4_1(WordDocument doc) {
		com.zhuozhengsoft.pageoffice.wordwriter.Table table1 = doc.openDataRegion("PO_w_2_t_001").openTable(1);
		 
	   /*// table1.openCellRC(1,1).setValue("PageOffice组件");
		int dataRowCount = 5;//需要插入数据的行数
		int oldRowCount = 3;//表格中原有的行数
		// 扩充表格
	    for (int j = 0; j < dataRowCount - oldRowCount; j++)
	    {
	        table1.insertRowAfter(table1.openCellRC(2, 5));  //在第2行的最后一个单元格下插入新行
	    }
		// 填充数据
	    int i = 1;
	    while (i <= dataRowCount)
	    {   
	        table1.openCellRC(i, 2).setValue("AA" + String.valueOf(i));
	        table1.openCellRC(i, 3).setValue("BB" + String.valueOf(i));
	        table1.openCellRC(i, 4).setValue("CC" + String.valueOf(i));
	        table1.openCellRC(i, 5).setValue("DD" + String.valueOf(i));
	        i++;
	    }*/
		return doc;
	}
	/*巡检*/
	public WordDocument w_4_2(WordDocument doc,String time) {
		Map<String, Object> map = new HashMap<String, Object>();
		map = ToWorkFileServices.xj_bs_all_type_num(4);
		doc.openDataRegion("PO_w_4_2_1").setValue(map.get("bs") == null ? String.valueOf(0) : map.get("bs")
				.toString());
		doc.openDataRegion("PO_w_4_2_2").setValue(map.get("room") == null ? "0" : map.get("room").toString());
		doc.openDataRegion("PO_w_4_2_3").setValue(map.get("vertical") == null ? "0" : map.get("vertical").toString());
		doc.openDataRegion("PO_w_4_2_7").setValue(map.get("bus") == null ? "0" : map.get("bus").toString());
		doc.openDataRegion("PO_w_4_2_4").setValue(map.get("total") == null ? "0" : map.get("total").toString());
		/*doc.openDataRegion("PO_w_4_2_5").setValue();
		doc.openDataRegion("PO_w_4_2_5").setValue();
		
		doc.openDataRegion("PO_w_4_2_5").setValue();*/
		com.zhuozhengsoft.pageoffice.wordwriter.Table table1 = doc.openDataRegion("PO_w_4_2_t_001").openTable(1);
		for(int i=0;i<5;i++){
			  table1.insertRowAfter(table1.openCellRC(1, 9));
		}
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> mapPara = new HashMap<String, Object>();
		mapPara.put("time", time);
		mapPara.put("period", 3);
		map2 = ToWorkFileServices.xj_bs_num(mapPara);
		Map<String, Object> bs=ToWorkFileServices.xj_bs(mapPara);
		Map<String, Object> vertical=ToWorkFileServices.xj_vertical(mapPara);
		Map<String, Object> net=ToWorkFileServices.xj_net(mapPara);
		Map<String, Object> dispatch=ToWorkFileServices.xj_dispatch(mapPara);
		Map<String, Object> room=ToWorkFileServices.xj_room(mapPara);
		
		
		table1.openCellRC(2, 1).setValue("固定基站");
		table1.openCellRC(2, 2).setValue(bs.get("count1") == null ? String.valueOf(0) : bs.get("count1").toString());
        table1.openCellRC(2, 3).setValue(bs.get("count1") == null ? String.valueOf(0) : bs.get("count1").toString());
        table1.openCellRC(2, 4).setValue(bs.get("count2") == null ? String.valueOf(0) : bs.get("count2").toString());
        /*table1.openCellRC(2, 5).setValue();
        table1.openCellRC(2, 6).setValue();*/
        table1.openCellRC(2, 7).setValue(bs.get("count2") == null ? String.valueOf(0) : bs.get("count2").toString());
        table1.openCellRC(2, 8).setValue(bs.get("count2") == null ? String.valueOf(0) : bs.get("count2").toString());
        /*table1.openCellRC(2, 9).setValue();*/
        
        table1.openCellRC(3, 1).setValue("室内覆盖");
        table1.openCellRC(3, 2).setValue(room.get("count1") == null ? String.valueOf(0) : room.get("count1").toString());
        table1.openCellRC(3, 3).setValue(room.get("count1") == null ? String.valueOf(0) : room.get("count1").toString());
        table1.openCellRC(3, 4).setValue(room.get("count2") == null ? String.valueOf(0) : room.get("count2").toString());
        /*table1.openCellRC(2, 5).setValue();
        table1.openCellRC(2, 6).setValue();*/
        table1.openCellRC(3, 7).setValue(room.get("count2") == null ? String.valueOf(0) : room.get("count2").toString());
        table1.openCellRC(3, 8).setValue(room.get("count2") == null ? String.valueOf(0) : room.get("count2").toString());
        /*table1.openCellRC(2, 9).setValue();*/
        
        table1.openCellRC(4, 1).setValue("直放站");
        table1.openCellRC(4, 2).setValue(vertical.get("count1") == null ? String.valueOf(0) : vertical.get("count1").toString());
        table1.openCellRC(4, 3).setValue(vertical.get("count1") == null ? String.valueOf(0) : vertical.get("count1").toString());
        table1.openCellRC(4, 4).setValue(vertical.get("count2") == null ? String.valueOf(0) : vertical.get("count2").toString());
        /*table1.openCellRC(2, 5).setValue();
        table1.openCellRC(2, 6).setValue();*/
        table1.openCellRC(4, 7).setValue(vertical.get("count2") == null ? String.valueOf(0) : vertical.get("count2").toString());
        table1.openCellRC(4, 8).setValue(vertical.get("count2") == null ? String.valueOf(0) : vertical.get("count2").toString());
        /*table1.openCellRC(2, 9).setValue();*/
        
        table1.openCellRC(5, 1).setValue("网管");
        table1.openCellRC(5, 2).setValue(net.get("count1") == null ? String.valueOf(0) : net.get("count1").toString());
        table1.openCellRC(5, 3).setValue(net.get("count1") == null ? String.valueOf(0) : net.get("count1").toString());
        table1.openCellRC(5, 4).setValue(net.get("count2") == null ? String.valueOf(0) : net.get("count2").toString());
        /*table1.openCellRC(2, 5).setValue();
        table1.openCellRC(2, 6).setValue();*/
        table1.openCellRC(5, 7).setValue(net.get("count2") == null ? String.valueOf(0) : net.get("count2").toString());
        table1.openCellRC(5, 8).setValue(net.get("count2") == null ? String.valueOf(0) : net.get("count2").toString());
        /*table1.openCellRC(2, 9).setValue();*/
        
        table1.openCellRC(6, 1).setValue("调度台");
        table1.openCellRC(6, 2).setValue(dispatch.get("count1") == null ? String.valueOf(0) : dispatch.get("count1").toString());
        table1.openCellRC(6, 3).setValue(dispatch.get("count1") == null ? String.valueOf(0) : dispatch.get("count1").toString());
        table1.openCellRC(6, 4).setValue(dispatch.get("count2") == null ? String.valueOf(0) : dispatch.get("count2").toString());
        /*table1.openCellRC(2, 5).setValue();
        table1.openCellRC(2, 6).setValue();*/
        table1.openCellRC(6, 7).setValue(dispatch.get("count2") == null ? String.valueOf(0) : dispatch.get("count2").toString());
        table1.openCellRC(6, 8).setValue(dispatch.get("count2") == null ? String.valueOf(0) : dispatch.get("count2").toString());
        /*table1.openCellRC(2, 9).setValue();*/
        int a=0,b=0;
        if(bs.get("count1")!=null){
        	a+=Integer.parseInt(bs.get("count1").toString());
        }
        if(net.get("count1")!=null){
        	a+=Integer.parseInt(net.get("count1").toString());
        }
        if(dispatch.get("count1")!=null){
        	a+=Integer.parseInt(dispatch.get("count1").toString());
        }
        if(room.get("count1")!=null){
        	a+=Integer.parseInt(room.get("count1").toString());
        }
        if(vertical.get("count1")!=null){
        	a+=Integer.parseInt(vertical.get("count1").toString());
        }
        
        
        if(bs.get("count2")!=null){
        	b+=Integer.parseInt(bs.get("count2").toString());
        }
        if(net.get("count2")!=null){
        	b+=Integer.parseInt(net.get("count2").toString());
        }
        if(dispatch.get("count2")!=null){
        	b+=Integer.parseInt(dispatch.get("count2").toString());
        }
        if(room.get("count2")!=null){
        	b+=Integer.parseInt(room.get("count2").toString());
        }
        if(vertical.get("count2")!=null){
        	b+=Integer.parseInt(vertical.get("count2").toString());
        }
        
        table1.openCellRC(7, 1).setValue("合计");
        table1.openCellRC(7, 2).setValue(String.valueOf(a));
        table1.openCellRC(7, 3).setValue(String.valueOf(a));
        table1.openCellRC(7, 4).setValue(String.valueOf(b));
        /*table1.openCellRC(2, 5).setValue();
        table1.openCellRC(2, 6).setValue();*/
        table1.openCellRC(7, 7).setValue(String.valueOf(b));
        table1.openCellRC(7, 8).setValue(String.valueOf(b));
        /*table1.openCellRC(2, 9).setValue();*/
		return doc;
	}
	public WordDocument w_4_3(WordDocument doc,HttpServletRequest request,String time) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapPara = new HashMap<String, Object>();
		mapPara.put("time", time);
		mapPara.put("period", 3);
		map = ToWorkFileServices.fault_num(mapPara);
		int a=map.get("one") == null ? 0 : Integer.parseInt(map.get("one").toString());
		int b=map.get("two") == null ? 0 : Integer.parseInt(map.get("two").toString());
		int c=map.get("three") == null ? 0 : Integer.parseInt(map.get("three").toString());
		int check=map.get("checked") == null ? 0 : Integer.parseInt(map.get("checked").toString());
		
		System.out.println("4——3map->"+map);
		System.out.println("4——3map->"+check);
		
		
		
		doc.openDataRegion("PO_w_4_3_1").setValue(map.get("total") == null ? String.valueOf(0) : map.get(
				"total").toString());
		doc.openDataRegion("PO_w_4_3_2").setValue(map.get("one") == null ? String.valueOf(0) : map.get("one")
				.toString());
		doc.openDataRegion("PO_w_4_3_3").setValue(map.get("two") == null ? String.valueOf(0) : map.get("two")
				.toString());
		doc.openDataRegion("PO_w_4_3_4").setValue(map.get("three") == null ? String.valueOf(0) : map.get(
				"three").toString());
		doc.openDataRegion("PO_w_4_3_5").setValue("");
		
		doc.openDataRegion("PO_w_4_3_6").setValue(map.get("four") == null ? String.valueOf(0) : map
				.get("four").toString());
		
		com.zhuozhengsoft.pageoffice.wordwriter.Table table1 = doc.openDataRegion("PO_w_4_3_t_001").openTable(1);
		// 扩充表格
	    for (int j = 0; j < 4; j++)
	    {
	        table1.insertRowAfter(table1.openCellRC(2, 5));  //在第2行的最后一个单元格下插入新行
	    }
	    table1.openCellRC(2, 1).setValue("特别重大故障");
	    table1.openCellRC(2, 2).setValue(map.get("one") == null ? String.valueOf(0) : map.get("one").toString());
	    table1.openCellRC(2, 3).setValue("0");
	    table1.openCellRC(2, 4).setValue("0");
	    table1.openCellRC(2, 5).setValue("");
	    
	    table1.openCellRC(3, 1).setValue("重大故障");
	    table1.openCellRC(3, 2).setValue(map.get("two") == null ? String.valueOf(0) : map.get("two").toString());
	    table1.openCellRC(3, 3).setValue("0");
	    table1.openCellRC(3, 4).setValue("0");
	    table1.openCellRC(3, 5).setValue("");
	    
	    
	    
	    table1.openCellRC(4, 1).setValue("一般故障");
	    table1.openCellRC(4, 2).setValue(map.get("three") == null ? String.valueOf(0) : map.get("three").toString());
	    table1.openCellRC(4, 3).setValue("0");
	    table1.openCellRC(4, 4).setValue("0");
	    table1.openCellRC(4, 5).setValue("");
	    
	    table1.openCellRC(5, 1).setValue("合计");
	    table1.openCellRC(5, 2).setValue(map.get("total") == null ? String.valueOf(0) : map.get("total").toString());
	    table1.openCellRC(5, 3).setValue(String.valueOf(check));
	    table1.openCellRC(5, 4).setValue(String.valueOf(c-check));
	    table1.openCellRC(5, 5).setValue("");
	    
	    DefaultCategoryDataset dcd = new DefaultCategoryDataset();

		TimeSeries timeSeries = new TimeSeries(
				Integer.parseInt(time.split("-")[0]) + "系统故障趋势图");

		for (int i = 1; i <= 12; i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("time", time.split("-")[0] + "-" + (i < 10 ? "0" + i : i));
			Map<String, Object> m2 = ToWorkFileServices.fault_num(m);
			dcd.addValue(
					(Number) Math.round(Double.valueOf(m2.get("total") == null ? String
							.valueOf(0) : m2.get("total").toString())), time
							.split("-")[0], (i < 10 ? "0" + i : i));
			timeSeries.add(new Month(i, Integer.parseInt(time.split("-")[0])),
					Double.valueOf(m2.get("total") == null ? String.valueOf(0)
							: m2.get("total").toString()));
		}
		try {
			FreeChartUtil.LineImg("系统故障趋势图", timeSeries,request,"PO_w_4_3_img_001.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.openDataRegion("PO_w_4_3_img_001").setValue("[image]../pic/PO_w_4_3_img_001.png[/image]");
	    
	    return doc;
	}
	public WordDocument w_4_3_1(WordDocument doc,HttpServletRequest request,String time) {
		Map<String, Object> mapPara = new HashMap<String, Object>();
		mapPara.put("time", time);
		mapPara.put("period", 4);

		List<Map<String, Object>> list = ToWorkFileServices
				.fault_level(mapPara);
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		int total = 0, checked = 0, afterCheck = 0;
		for (Map<String, Object> map : list) {

			/* System.out.println("mmmmmm->"+map); */
			total += Integer.parseInt(map.get("total").toString());
			checked += Integer.parseInt(map.get("checked").toString());
			afterCheck += Integer.parseInt(map.get("afterCheck").toString());

			if (map.get("level").toString().equals("1")) {
				map1.put("total", map.get("total"));
				map1.put("checked", map.get("checked"));
				map1.put("afterCheck", map.get("afterCheck"));
			} else if (map.get("level").toString().equals("2")) {
				map2.put("total", map.get("total"));
				map2.put("checked", map.get("checked"));
				map2.put("afterCheck", map.get("afterCheck"));
			} else if (map.get("level").toString().equals("3")) {
				map3.put("total", map.get("total"));
				map3.put("checked", map.get("checked"));
				map3.put("afterCheck", map.get("afterCheck"));
			}
		}
		System.out.println("map1"+map1);
		System.out.println("map2"+map2);
		System.out.println("map3"+map3);
		doc.openDataRegion("PO_w_4_3_1_1").setValue(map1.get("total") == null ? "0" : map1.get(
				"total").toString());
		doc.openDataRegion("PO_w_4_3_1_2").setValue(map1.get(
				"checked") == null ? "0" : map1.get(
				"checked").toString());		
		doc.openDataRegion("PO_w_4_3_1_5").setValue(map2.get(
				"total") == null ? "0" : map2.get(
				"total").toString());
		doc.openDataRegion("PO_w_4_3_1_6").setValue( map2.get(
				"checked") == null ? "0" : map2.get(
				"checked").toString());	
		doc.openDataRegion("PO_w_4_3_1_9").setValue(map3.get(
				"total") == null ? "0" : map3.get(
				"total").toString());
		doc.openDataRegion("PO_w_4_3_1_10").setValue(map3.get(
				"checked") == null ? "0" : map3.get(
				"checked").toString());
		
		com.zhuozhengsoft.pageoffice.wordwriter.Table table1 = doc.openDataRegion("PO_w_4_3_t_002").openTable(1);
		
		table1.openCellRC(2, 2).setValue(map1.get("total") == null ? "0" : map1.get("total").toString());
		table1.openCellRC(2, 3).setValue(map1.get("checked") == null ? "0" : map1.get("checked").toString());
		table1.openCellRC(2, 4).setValue(map1.get("afterCheck") == null ? "0" : map1.get("afterCheck").toString());
		
		table1.openCellRC(3, 2).setValue(map2.get("total") == null ? "0" : map2.get("total").toString());
		table1.openCellRC(3, 3).setValue(map2.get("checked") == null ? "0" : map2.get("checked").toString());
		table1.openCellRC(3, 4).setValue(map2.get("afterCheck") == null ? "0" : map2.get("afterCheck").toString());
		
		table1.openCellRC(4, 2).setValue(map3.get("total").toString() == null ? "0" : map3.get("total").toString());
		table1.openCellRC(4, 3).setValue(map3.get("checked") == null ? "0" : map3.get("checked").toString());
		table1.openCellRC(4, 4).setValue(map3.get("afterCheck") == null ? "0" : map3.get("afterCheck").toString());
		
		int a= (map1.get("total") == null ? 0 :Integer.parseInt(map1.get("total").toString()))+
               (map2.get("total") == null ? 0 :Integer.parseInt(map2.get("total").toString()))+
               (map3.get("total") == null ? 0 :Integer.parseInt(map3.get("total").toString()));
		int b= (map1.get("checked") == null ? 0 :Integer.parseInt(map1.get("checked").toString()))+
	           (map2.get("checked") == null ? 0 :Integer.parseInt(map2.get("checked").toString()))+
	           (map3.get("checked") == null ? 0 :Integer.parseInt(map3.get("checked").toString()));
		int c= (map1.get("afterCheck") == null ? 0 :Integer.parseInt(map1.get("afterCheck").toString()))+
	           (map2.get("afterCheck") == null ? 0 :Integer.parseInt(map2.get("afterCheck").toString()))+
	           (map3.get("afterCheck")== null ? 0 :Integer.parseInt(map3.get("afterCheck").toString()));
		
		
		
		table1.openCellRC(5, 2).setValue(String.valueOf(a));
		table1.openCellRC(5, 3).setValue(String.valueOf(b));
		table1.openCellRC(5, 4).setValue(String.valueOf(c));
		
		DefaultPieDataset dpd = new DefaultPieDataset();
		for (Map<String, Object> map : list) {

			if (map.get("level").toString().equals("1")) {
				dpd.setValue("一级基站", (Number) map.get("total"));
			} else if (map.get("level").toString().equals("2")) {
				dpd.setValue("二级基站", (Number) map.get("total"));
			} else if (map.get("level").toString().equals("3")) {
				dpd.setValue("三级基站", (Number) map.get("total"));
			}
		}

		try {
			FreeChartUtil.PieImg("一般故障基站等级占比", dpd, 0,request,"PO_w_4_3_img_002.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.openDataRegion("PO_w_4_3_img_002").setValue("[image]../pic/PO_w_4_3_img_002.png[/image]");
		 
	   /*// table1.openCellRC(1,1).setValue("PageOffice组件");
		int dataRowCount = 5;//需要插入数据的行数
		int oldRowCount = 3;//表格中原有的行数
		// 扩充表格
	    for (int j = 0; j < dataRowCount - oldRowCount; j++)
	    {
	        table1.insertRowAfter(table1.openCellRC(2, 5));  //在第2行的最后一个单元格下插入新行
	    }
		// 填充数据
	    int i = 1;
	    while (i <= dataRowCount)
	    {   
	        table1.openCellRC(i, 2).setValue("AA" + String.valueOf(i));
	        table1.openCellRC(i, 3).setValue("BB" + String.valueOf(i));
	        table1.openCellRC(i, 4).setValue("CC" + String.valueOf(i));
	        table1.openCellRC(i, 5).setValue("DD" + String.valueOf(i));
	        i++;
	    }*/
		
		return doc;
	}
	
	@RequestMapping(value = "/monthword", method = RequestMethod.POST)
	@ResponseBody
public Map<String, Object> monthword(HttpServletRequest request,
			HttpServletResponse response) {
		String saveDir = request.getSession().getServletContext()
				.getRealPath("/download");
		String time = request.getParameter("time");
		String pathname = saveDir + "/系统运行维护服务月报  -" + time + ".doc";
		try {
			to_word("2019-01", 3, pathname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("pathName", pathname);
		return result;
	}

	public static void to_word(String time, int period, String path)
			throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		// 页脚，页眉
		Font hfFont = new Font(bfChinese, 9, Font.NORMAL);
		// 标题字体风格
		Font titleFont = new Font(bfChinese, 12, Font.BOLD);
		Font titleBaoFont = new Font(bfChinese, 20, Font.BOLD);
		Font underLineFont = new Font(bfChinese, 12, Font.UNDERLINE);
		// 正文字体风格
		Font contextFont = new Font(bfChinese, 12, Font.NORMAL);
		// 正文标题1 风格
		Font contextTitle1Font = new Font(bfChinese, 12, Font.NORMAL);
		// 表格标题
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		// 设置加粗字体
		Font boldFont = new Font(bfChinese, 10, Font.BOLD);
		// 设置纸张的大小
		Document document = new Document(pageSize);

		/* 设置标题1格式 */
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1;
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt1.setStyle(Font.BOLD);
		rtfGsBt1.setSize(14);
		/* 设置标题2格式 */
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2;
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt2.setStyle(Font.NORMAL);
		rtfGsBt2.setSize(12);

		try {
			// 创建word文档
			RtfWriter2.getInstance(document, new FileOutputStream(path));
			// 打开文档
			document.open();

			// 添加页眉
			HeaderFooter header = new HeaderFooter(
					new Phrase(
							"系统运行维护服务月报                                                                                2018年6月",
							hfFont), false);
			header.setAlignment(Rectangle.ALIGN_LEFT);
			header.setBorder(2);
			header.setPageNumber(2);
			header.setBottom(20);
			header.setBorderWidthBottom(5);

			document.setHeader(header);
			// 添加页脚
			HeaderFooter footer = new HeaderFooter(new Phrase(
					"成都亚光电子股份有限公司\n成都市应急指挥调度无线通信网四期工程项目部\n", hfFont), true);
			footer.setAlignment(Rectangle.ALIGN_CENTER);
			document.setFooter(footer);

			/* 首页 */
			// 创建段落
			Paragraph p1 = new Paragraph("成都市应急指挥调度无线通信网三期工程", new Font(
					bfChinese, 20, Font.BOLD));
			Paragraph p2 = new Paragraph("系统运行维护服务月报", new Font(bfChinese, 19,
					Font.BOLD));
			Paragraph p3 = new Paragraph(time, new Font(bfChinese, 18,
					Font.BOLD));

			// 设置段落为居中对齐
			p1.setAlignment(Paragraph.ALIGN_CENTER);
			p1.setSpacingBefore(150);

			p2.setAlignment(Paragraph.ALIGN_CENTER);
			p2.setSpacingBefore(30);
			p3.setAlignment(Paragraph.ALIGN_CENTER);
			p3.setSpacingAfter(200);
			// 写入段落
			document.add(p1);
			document.add(p2);
			document.add(p3);
			p1 = new Paragraph("拟制_______________", new Font(bfChinese, 18,
					Font.BOLD));
			p1.setAlignment(Paragraph.ALIGN_CENTER);
			p2 = new Paragraph("审核_______________", new Font(bfChinese, 18,
					Font.BOLD));
			p2.setAlignment(Paragraph.ALIGN_CENTER);
			p2.setSpacingAfter(500);
			document.add(p1);
			document.add(p2);
			Paragraph title = new Paragraph("一 运维内容");
			// 设置标题格式对齐方式
			title.setAlignment(Element.ALIGN_LEFT);
			title.setFont(rtfGsBt1);
			title.setSpacingAfter(6);
			document.add(title);

			Paragraph c = new Paragraph();
			Paragraph tn = new Paragraph();
			tn.setAlignment(Element.ALIGN_CENTER);
			tn.setFont(tableTitleFont);
			tn.setSpacingAfter(3);
			// 设置第一行空的列数
			c.setSpacingBefore(3);
			c.setFirstLineIndent(20);
			c.setFont(contextFont);
			c.setExtraParagraphSpace(20);
			c.add("191个固定基站：188个在用，3个备用，详见《基站信息表》；\n4个室内覆盖站点，4个在用，分别位于新会展中心会议中心、火车东站");
			c.add("东广场、火车东站西广场、西博城室内；详见《基站信息表》；\n");
			c.add("15个直放站，均在用，分别位于清泉1号隧道、清泉2号隧道、人和隧道、万兴隧道、丹景1号隧道、丹景2号隧道、");
			c.add("火车东站隧道、郫县老成灌隧道、龙泉山隧道、龙泉山1号隧道、龙泉山2号隧道、龙泉山3号隧道、龙泉山4号隧道、紫坪铺隧道；");
			c.add("通信车5辆，均可用，停放于交换中心；\n");
			c.add("应急便携站4套，均可用，存放于交换中心库房；\n");
			title = new Paragraph("二 系统运行情况");
			// 设置标题格式对齐方式
			title.setAlignment(Element.ALIGN_LEFT);
			title.setFont(rtfGsBt1);
			title.setSpacingAfter(6);
			document.add(c);
			document.add(title);
			c.clear();
			c.add("系统主要服务器及核心网络设备运行状态正常。");
			document.add(c);
			tn.add("表一 系统核心设备运行状态");
			document.add(tn);

			// 设置Table表格,创建一个三列的表格
			Table table = new Table(6);
			int width[] = { 25, 25, 25, 30, 50, 20 };// 设置每列宽度比例
			// table.setWidths(width);
			table.setWidth(100);// 占页面宽度比例
			table.setAlignment(1);
			table.setAlignment(Element.ALIGN_CENTER);// 居中
			table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
			table.setAutoFillEmptyCells(true);// 自动填满
			table.setBorderWidth(1);// 边框宽度

			table.addCell(new Cell(new Paragraph("设备", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("CPU占用率", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("内存使用率", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("硬盘已用/可用(G)", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("设备运行时长", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("备注", tableheaderFont)));

			List<Map<String, Object>> list = ReportDayService.chart_server();
			List<Map<String, Object>> list_one = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> list_two = new ArrayList<Map<String, Object>>();
			for (int i = 0, j = list.size(); i < j; i++) {
				Map<String, Object> mapa = list.get(i);
				if (mapa.get("ID").equals(1)) {
					list_one.add(mapa);
				} else {
					list_two.add(mapa);
				}
			}

			for (int i = 0; i < list_one.size(); i++) {
				Map bean = list_one.get(i);
				table.addCell(new Cell(new Paragraph(bean.get("name")
						.toString(), tableheaderFont)));
				table.addCell(new Cell(new Paragraph(bean.get("cpuLoad") + "%",
						tableheaderFont)));
				table.addCell(new Cell(new Paragraph(bean.get("memPercent")
						+ "%", tableheaderFont)));
				table.addCell(new Cell(new Paragraph(bean.get("diskUsed")
						.toString() + "/" + bean.get("diskSize").toString(),
						tableheaderFont)));
				table.addCell(new Cell(new Paragraph("")));
				table.addCell(new Cell(new Paragraph("")));
			}

			document.add(table);

			title = new Paragraph("三 话务统计");
			title.setAlignment(Element.ALIGN_LEFT);
			title.setFont(rtfGsBt1);
			title.setSpacingAfter(6);
			document.add(title);
			title = new Paragraph("1、系统话务情况");
			title.setAlignment(Element.ALIGN_LEFT);
			title.setFont(rtfGsBt2);
			title.setSpacingAfter(6);
			document.add(title);

			String time1 = "", time2 = time, time3 = "";
			time1 = (Integer.parseInt(time2.split("-")[0]) - 1) + "-"
					+ time2.split("-")[1];
			if (time2.split("-")[1].equals("01")) {
				time3 = (Integer.parseInt(time2.split("-")[0]) - 1) + "-12";
			} else {
				int y = Integer.parseInt(time2.split("-")[1]) - 1;
				time3 = time2.split("-")[0] + "-" + (y < 10 ? ("0" + y) : y);
			}
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("time", time1);
			paraMap.put("period", period);
			Map<String, Object> map_last_year_call = ToWorkFileServices
					.system_call(paraMap);
			paraMap.put("time", time2);
			paraMap.put("period", period);
			Map<String, Object> map_last_month_call = ToWorkFileServices
					.system_call(paraMap);
			paraMap.put("time", time3);
			paraMap.put("period", period);
			Map<String, Object> map_now_call = ToWorkFileServices
					.system_call(paraMap);

			c.clear();
			Double a1 = (double) 0;
			Double a2 = (double) 0;
			c.add("本月系统有效呼叫总数"
					+ (Double.valueOf(map_now_call.get("TotalActiveCall")
							.toString()) / 10000) + "万次，");

			a1 = Double.valueOf(map_last_year_call == null ? "0"
					: map_last_year_call.get("TotalActiveCall").toString());
			a2 = Double.valueOf(map_now_call.get("TotalActiveCall").toString());
			c.add("同比"
					+ (a1 < a2 ? "增加"
							+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
							: "减少"
									+ String.format("%.2f", (a1 - a2) / a1
											* 100) + "%") + ",");

			a1 = Double.valueOf(map_last_month_call == null ? "0"
					: map_last_month_call.get("TotalActiveCall").toString());
			a2 = Double.valueOf(map_now_call.get("TotalActiveCall").toString());
			c.add("环比"
					+ (a1 < a2 ? "增加"
							+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
							: "减少"
									+ String.format("%.2f", (a1 - a2) / a1
											* 100) + "%") + ",");
			c.add("通话总时长" + map_now_call.get("TotalActiveCallDuration") + "分钟，");

			a1 = Double.valueOf(map_last_year_call == null ? "0"
					: map_last_year_call.get("TotalActiveCallDuration")
							.toString());
			a2 = Double.valueOf(map_now_call.get("TotalActiveCallDuration")
					.toString());
			c.add("同比"
					+ (a1 < a2 ? "增加"
							+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
							: "减少"
									+ String.format("%.2f", (a1 - a2) / a1
											* 100) + "%") + ",");

			a1 = Double.valueOf(map_last_month_call == null ? "0"
					: map_last_month_call.get("TotalActiveCallDuration")
							.toString());
			a2 = Double.valueOf(map_now_call.get("TotalActiveCallDuration")
					.toString());
			c.add("环比"
					+ (a1 < a2 ? "增加"
							+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
							: "减少"
									+ String.format("%.2f", (a1 - a2) / a1
											* 100) + "%") + ",");
			c.add("平均通话时长" + map_now_call.get("AverageCallDuration")
					+ "秒；呼叫成功率" + map_now_call.get("FailedPercentage") + "；");
			c.add("排队数量" + map_now_call.get("totalQueueCount") + "个，");

			a1 = Double.valueOf(map_last_year_call == null ? "0"
					: map_last_year_call.get("AverageCallDuration").toString());
			a2 = Double.valueOf(map_now_call.get("AverageCallDuration")
					.toString());
			c.add("同比"
					+ (a1 < a2 ? "增加"
							+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
							: "减少"
									+ String.format("%.2f", (a1 - a2) / a1
											* 100) + "%") + ",");

			a1 = Double
					.valueOf(map_last_month_call == null ? "0"
							: map_last_month_call.get("AverageCallDuration")
									.toString());
			a2 = Double.valueOf(map_now_call.get("AverageCallDuration")
					.toString());
			c.add("环比"
					+ (a1 < a2 ? "增加"
							+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
							: "减少"
									+ String.format("%.2f", (a1 - a2) / a1
											* 100) + "%") + ",");

			c.add("最大用户注册数" + map_now_call.get("totalMaxReg") + "个，");

			a1 = Double.valueOf(map_last_year_call == null ? "0"
					: map_last_year_call.get("totalMaxReg").toString());
			a2 = Double.valueOf(map_now_call.get("totalMaxReg").toString());
			c.add("同比"
					+ (a1 < a2 ? "增加"
							+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
							: "减少"
									+ String.format("%.2f", (a1 - a2) / a1
											* 100) + "%") + ",");

			a1 = Double.valueOf(map_last_month_call == null ? "0"
					: map_last_month_call.get("totalMaxReg").toString());
			a2 = Double.valueOf(map_now_call.get("totalMaxReg").toString());
			c.add("环比"
					+ (a1 < a2 ? "增加"
							+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
							: "减少"
									+ String.format("%.2f", (a1 - a2) / a1
											* 100) + "%") + ",");

			c.add("GPS数据"
					+ (Double.valueOf(map_now_call.get("gpsTotals").toString()) / 10000)
					+ "万条，");
			a1 = Double.valueOf(map_last_year_call == null ? "0"
					: map_last_year_call.get("gpsTotals").toString());
			a2 = Double.valueOf(map_now_call.get("gpsTotals").toString());
			c.add("同比"
					+ (a1 < a2 ? "增加"
							+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
							: "减少"
									+ String.format("%.2f", (a1 - a2) / a1
											* 100) + "%") + ",");

			a1 = Double.valueOf(map_last_month_call == null ? "0"
					: map_last_month_call.get("gpsTotals").toString());
			a2 = Double.valueOf(map_now_call.get("gpsTotals").toString());
			c.add("环比"
					+ (a1 < a2 ? "增加"
							+ String.format("%.2f", (a2 - a1) / a1 * 100) + "%"
							: "减少"
									+ String.format("%.2f", (a1 - a2) / a1
											* 100) + "%") + ",");

			document.add(c);
			tn.clear();
			tn.add("表二 系统话务统计");
			document.add(tn);

			table = new Table(12);
			table.setWidth(100);// 占页面宽度比例
			table.setAlignment(1);
			table.setAlignment(Element.ALIGN_CENTER);// 居中
			table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
			table.setAutoFillEmptyCells(true);// 自动填满
			table.setBorderWidth(1);// 边框宽度
			table.addCell(new Cell(new Paragraph("基站总数", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("系统呼叫总数", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("系统有效呼叫总数", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("系统有效呼叫持续时间（分钟）",
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph("系统平均呼叫持续时间（秒）",
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph("系统未成功呼叫总数", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("系统呼损总数", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("系统呼叫成功率", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("系统排队次数", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("系统最大用户注册数", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("系统最大组注册数", tableheaderFont)));
			table.addCell(new Cell(new Paragraph("系统GPS数据", tableheaderFont)));

			table.addCell(new Cell(new Paragraph(map_now_call.get("bsTotals")
					.toString(), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(map_now_call.get("TotalCalls")
					.toString(), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(map_now_call.get(
					"TotalActiveCall").toString(), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(map_now_call.get(
					"TotalActiveCallDuration").toString(), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(FunUtil
					.second_time(Double.valueOf(
							map_now_call.get("AverageCallDuration").toString())
							.intValue()), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(map_now_call.get(
					"TotalFailedCalls").toString(), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(map_now_call.get(
					"NoEffectCalls").toString(), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(map_now_call.get(
					"FailedPercentage").toString(), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(map_now_call.get(
					"totalQueueCount").toString(), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(map_now_call
					.get("totalMaxReg").toString(), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(map_now_call
					.get("maxRegGroup").toString(), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(map_now_call.get("gpsTotals")
					.toString(), tableheaderFont)));

			document.add(table);
			List<Map<String, Object>> list1 = ToWorkFileServices
					.system_call_year(String.valueOf(Integer.parseInt(time
							.split("-")[0]) - 2));
			List<Map<String, Object>> list2 = ToWorkFileServices
					.system_call_year(String.valueOf(Integer.parseInt(time
							.split("-")[0]) - 1));
			List<Map<String, Object>> list3 = ToWorkFileServices
					.system_call_year(String.valueOf(Integer.parseInt(time
							.split("-")[0])));
			TimeSeries timeSeries1 = new TimeSeries(Integer.parseInt(time
					.split("-")[0]) - 2 + "年系统总通话次数");
			TimeSeries timeSeries2 = new TimeSeries(Integer.parseInt(time
					.split("-")[0]) - 1 + "年系统总通话次数");
			TimeSeries timeSeries3 = new TimeSeries(Integer.parseInt(time
					.split("-")[0]) + "年系统总通话次数");
			for (Map<String, Object> map : list1) {
				timeSeries1.add(
						new Month(Integer.parseInt(map.get("monthstr")
								.toString()),
								Integer.parseInt(time.split("-")[0])), Double
								.valueOf(map.get("TotalCalls").toString()));
			}
			for (Map<String, Object> map : list2) {
				timeSeries2.add(
						new Month(Integer.parseInt(map.get("monthstr")
								.toString()),
								Integer.parseInt(time.split("-")[0])), Double
								.valueOf(map.get("TotalCalls").toString()));
			}
			for (Map<String, Object> map : list3) {
				timeSeries3.add(
						new Month(Integer.parseInt(map.get("monthstr")
								.toString()),
								Integer.parseInt(time.split("-")[0])), Double
								.valueOf(map.get("TotalCalls").toString()));
			}
			ChartUtil.ThreeLineImg("系统总通话次数", timeSeries1, timeSeries2,
					timeSeries3);
			Image img = Image.getInstance("D:/chart.png");
			document.add(img);

			TimeSeries timeSeries4 = new TimeSeries(Integer.parseInt(time
					.split("-")[0]) - 2 + "年系统总通话时长");
			TimeSeries timeSeries5 = new TimeSeries(Integer.parseInt(time
					.split("-")[0]) - 1 + "年系统总通话时长");
			TimeSeries timeSeries6 = new TimeSeries(Integer.parseInt(time
					.split("-")[0]) + "年系统总通话时长");
			for (Map<String, Object> map : list1) {
				timeSeries4.add(
						new Month(Integer.parseInt(map.get("monthstr")
								.toString()),
								Integer.parseInt(time.split("-")[0])), Double
								.valueOf(map.get("TotalActiveCallDuration")
										.toString()));
			}
			for (Map<String, Object> map : list2) {
				timeSeries5.add(
						new Month(Integer.parseInt(map.get("monthstr")
								.toString()),
								Integer.parseInt(time.split("-")[0])), Double
								.valueOf(map.get("TotalActiveCallDuration")
										.toString()));
			}
			for (Map<String, Object> map : list3) {
				timeSeries6.add(
						new Month(Integer.parseInt(map.get("monthstr")
								.toString()),
								Integer.parseInt(time.split("-")[0])), Double
								.valueOf(map.get("TotalActiveCallDuration")
										.toString()));
			}
			ChartUtil.ThreeLineImg("系统总通话时长", timeSeries4, timeSeries5,
					timeSeries6);
			document.add(img);

			TimeSeries timeSeries7 = new TimeSeries(Integer.parseInt(time
					.split("-")[0]) - 2 + "年系统排队总次数");
			TimeSeries timeSeries8 = new TimeSeries(Integer.parseInt(time
					.split("-")[0]) - 1 + "年系统排队总次数");
			TimeSeries timeSeries9 = new TimeSeries(Integer.parseInt(time
					.split("-")[0]) + "年系统排队总次数");
			for (Map<String, Object> map : list1) {
				timeSeries7
						.add(new Month(Integer.parseInt(map.get("monthstr")
								.toString()),
								Integer.parseInt(time.split("-")[0])), Double
								.valueOf(map.get("totalQueueCount").toString()));
			}
			for (Map<String, Object> map : list2) {
				timeSeries8
						.add(new Month(Integer.parseInt(map.get("monthstr")
								.toString()),
								Integer.parseInt(time.split("-")[0])), Double
								.valueOf(map.get("totalQueueCount").toString()));
			}
			for (Map<String, Object> map : list3) {
				timeSeries9
						.add(new Month(Integer.parseInt(map.get("monthstr")
								.toString()),
								Integer.parseInt(time.split("-")[0])), Double
								.valueOf(map.get("totalQueueCount").toString()));
			}
			ChartUtil.ThreeLineImg("系统排队总次数", timeSeries7, timeSeries8,
					timeSeries9);
			document.add(img);

			list1 = ToWorkFileServices.system_gps_year(String.valueOf(Integer
					.parseInt(time.split("-")[0]) - 2));
			list2 = ToWorkFileServices.system_gps_year(String.valueOf(Integer
					.parseInt(time.split("-")[0]) - 1));
			list3 = ToWorkFileServices.system_gps_year(String.valueOf(Integer
					.parseInt(time.split("-")[0])));
			timeSeries1 = new TimeSeries(Integer.parseInt(time.split("-")[0])
					- 2 + "年GPS上报");
			timeSeries2 = new TimeSeries(Integer.parseInt(time.split("-")[0])
					- 1 + "年GPS上报");
			timeSeries3 = new TimeSeries(Integer.parseInt(time.split("-")[0])
					+ "年GPS上报");

			for (Map<String, Object> map : list1) {
				timeSeries1.add(
						new Month(Integer.parseInt(map.get("monthstr")
								.toString()),
								Integer.parseInt(time.split("-")[0])), Double
								.valueOf(map.get("totals").toString()));
			}
			for (Map<String, Object> map : list2) {
				timeSeries2.add(
						new Month(Integer.parseInt(map.get("monthstr")
								.toString()),
								Integer.parseInt(time.split("-")[0])), Double
								.valueOf(map.get("totals").toString()));
			}
			for (Map<String, Object> map : list3) {
				timeSeries3.add(
						new Month(Integer.parseInt(map.get("monthstr")
								.toString()),
								Integer.parseInt(time.split("-")[0])), Double
								.valueOf(map.get("totals").toString()));
			}
			ChartUtil.ThreeLineImg("短数据上报数量", timeSeries1, timeSeries2,
					timeSeries3);
			document.add(img);

			/* 基站话务-级别 */
			c.clear();
			word_5(time, document, c, rtfGsBt2, title);
			/* 基站话务-区域 */
			c.clear();
			word_6(time, document, c, rtfGsBt2, title);
			/* 基站话务-区域top10 */
			c.clear();
			word_7(time, document, c, rtfGsBt2, title);
			c.clear();
			word_8(time, document, c, rtfGsBt2, title);
			c.clear();
			word_9(time, document, c, rtfGsBt2, title);
			/* 用户单位top10 */
			c.clear();
			word_10(time, document, c, rtfGsBt2, title);
			c.clear();
			word_11(time, document, c, rtfGsBt2, title);
			c.clear();
			word_12(time, document, c, rtfGsBt2, title);
			c.clear();
			word_13(time, document, c, rtfGsBt2, title);
			/* 4.2巡检 */
			c.clear();
			word_14(time, document, c, rtfGsBt2, title);
			/* 4.3故障处理与分析 */
			c.clear();
			word_15(time, document, c, rtfGsBt2, title);
			/* 4.3故障处理与分析3.1、特别重大故障 */
			c.clear();
			word_16(time, document, c, rtfGsBt2, title);
			/* 4.3故障处理与分析3.2、重大故障 */
			c.clear();
			word_17(time, document, c, rtfGsBt2, title);
			/* 4.3故障处理与分析3.3、一般故障 */
			c.clear();
			word_18(time, document, c, rtfGsBt2, title);

			// 关流
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();

		}

	}

	/* 基站话务-级别 */
	public static void word_5(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title)
			throws DocumentException, IOException {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);
		title = new Paragraph("2、基站话务情况");
		title.setAlignment(Element.ALIGN_LEFT);
		title.setFont(rtfGsBt);
		title.setSpacingAfter(6);
		document.add(title);

		EastBsCallDataBean bean11 = new EastBsCallDataBean();
		EastBsCallDataBean bean12 = new EastBsCallDataBean();
		EastBsCallDataBean bean13 = new EastBsCallDataBean();

		EastBsCallDataBean bean21 = new EastBsCallDataBean();
		EastBsCallDataBean bean22 = new EastBsCallDataBean();
		EastBsCallDataBean bean23 = new EastBsCallDataBean();

		String time2 = "", time3 = time;
		time2 = (Integer.parseInt(time3.split("-")[0]) - 1) + "-"
				+ time3.split("-")[1];
		if (time3.split("-")[1].equals("01")) {
			time2 = (Integer.parseInt(time3.split("-")[0]) - 1) + "-12";
		} else {
			int y = Integer.parseInt(time3.split("-")[1]) - 1;
			time2 = time3.split("-")[0] + "-" + (y < 10 ? ("0" + y) : y);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("time", time2);
		map.put("period", 4);
		List<EastBsCallDataBean> list2 = ToWorkFileServices
				.system_call_level(map);
		map.put("time", time3);
		map.put("period", 4);
		List<EastBsCallDataBean> list3 = ToWorkFileServices
				.system_call_level(map);
		for (EastBsCallDataBean eastBsCallDataBean : list2) {
			if (eastBsCallDataBean.getLevel().equals("1")) {
				bean11 = eastBsCallDataBean;
			} else if (eastBsCallDataBean.getLevel().equals("2")) {
				bean12 = eastBsCallDataBean;
			} else if (eastBsCallDataBean.getLevel().equals("3")) {
				bean13 = eastBsCallDataBean;
			}
		}
		for (EastBsCallDataBean eastBsCallDataBean : list3) {
			if (eastBsCallDataBean.getLevel().equals("1")) {
				bean21 = eastBsCallDataBean;
			} else if (eastBsCallDataBean.getLevel().equals("2")) {
				bean22 = eastBsCallDataBean;
			} else if (eastBsCallDataBean.getLevel().equals("3")) {
				bean23 = eastBsCallDataBean;
			}
		}
		Double a1 = (double) 0;
		Double a2 = (double) 0;

		c.add("一级基站本月总通话"
				+ (Double.valueOf(bean21.getTotalActiveCalls()) / 10000)
				+ "万次，");
		a1 = Double.valueOf(bean11.getTotalActiveCalls());
		a2 = Double.valueOf(bean21.getTotalActiveCalls());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ",");

		c.add("通话总时长" + bean21.getTotalActiveCallDuration() + "分钟，");
		a1 = Double.valueOf(bean11.getTotalActiveCallDuration());
		a2 = Double.valueOf(bean21.getTotalActiveCallDuration());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";");

		c.add("平均通话时长" + bean21.getAverageCallDuration() + "秒；");

		c.add("排队数量" + bean21.getQueueCount() + "个，");
		a1 = Double.valueOf(bean11.getQueueCount());
		a2 = Double.valueOf(bean21.getQueueCount());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";\n");

		c.add("二级基站本月总通话"
				+ (Double.valueOf(bean22.getTotalActiveCalls()) / 10000)
				+ "万次，");
		a1 = Double.valueOf(bean12.getTotalActiveCalls());
		a2 = Double.valueOf(bean22.getTotalActiveCalls());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";");

		c.add("通话总时长" + bean22.getTotalActiveCallDuration() + "分钟，");
		a1 = Double.valueOf(bean12.getTotalActiveCallDuration());
		a2 = Double.valueOf(bean22.getTotalActiveCallDuration());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";");

		c.add("平均通话时长" + bean22.getAverageCallDuration() + "秒；排队数量"
				+ bean22.getQueueCount() + "个，");
		a1 = Double.valueOf(bean12.getQueueCount());
		a2 = Double.valueOf(bean22.getQueueCount());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";\n");

		c.add("三级基站本月总通话"
				+ (Double.valueOf(bean23.getTotalActiveCalls()) / 10000)
				+ "万次，");
		a1 = Double.valueOf(bean13.getTotalActiveCalls());
		a2 = Double.valueOf(bean23.getTotalActiveCalls());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";");

		c.add("通话总时长" + bean23.getTotalActiveCallDuration() + "分钟，");
		a1 = Double.valueOf(bean13.getTotalActiveCallDuration());
		a2 = Double.valueOf(bean23.getTotalActiveCallDuration());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";");

		c.add("平均通话时长" + bean23.getAverageCallDuration() + "秒；排队数量"
				+ bean23.getQueueCount() + "个,");
		a1 = Double.valueOf(bean13.getQueueCount());
		a2 = Double.valueOf(bean23.getQueueCount());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";\n");

		document.add(c);
		// 设置Table表格,创建一个三列的表格
		Paragraph tn = new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表三  一、二、三级基站话务统计");
		document.add(tn);

		Table table = new Table(9);
		int width[] = { 25, 25, 25, 30, 50, 20 };// 设置每列宽度比例
		// table.setWidths(width);
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.addCell(new Cell(new Paragraph("基站等级", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站总呼叫次数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站月平均呼叫次数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站有效呼叫持续时间（分钟）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站月平均通话时长（分钟）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站用户排队数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站最大用户注册数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站最大组注册数", tableheaderFont)));

		table.addCell(new Cell(new Paragraph("一级基站", tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean21
				.getBsTotals()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean21
				.getTotalActiveCalls()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(
				String.valueOf(Math.round(Double.valueOf(bean21
						.getTotalActiveCalls()) / bean21.getBsTotals())),
				tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean21
				.getTotalActiveCallDuration()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double
				.valueOf(bean21.getTotalActiveCallDuration()))
				/ bean21.getBsTotals()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean21
				.getQueueCount()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean21
				.getMaxUserRegCount()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean21
				.getMaxGroupRegCount()), tableheaderFont)));

		table.addCell(new Cell(new Paragraph("二级基站", tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean22
				.getBsTotals()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(
				String.valueOf(Math.round(Double.valueOf(bean22
						.getTotalActiveCalls()) / bean22.getBsTotals())),
				tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean22
				.getTotalActiveCallDuration()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double
				.valueOf(bean22.getTotalActiveCallDuration()))
				/ bean22.getBsTotals()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean22
				.getAverageCallDuration() / bean21.getBsTotals()),
				tableheaderFont)));

		table.addCell(new Cell(new Paragraph(String.valueOf(bean22
				.getQueueCount()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean22
				.getMaxUserRegCount()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean22
				.getMaxGroupRegCount()), tableheaderFont)));

		table.addCell(new Cell(new Paragraph("三级基站", tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean23
				.getBsTotals()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean23
				.getTotalActiveCalls()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(
				String.valueOf(Math.round(Double.valueOf(bean23
						.getTotalActiveCalls()) / bean23.getBsTotals())),
				tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean23
				.getTotalActiveCallDuration()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double
				.valueOf(bean23.getTotalActiveCallDuration()))
				/ bean23.getBsTotals()), tableheaderFont)));

		table.addCell(new Cell(new Paragraph(String.valueOf(bean23
				.getQueueCount()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean23
				.getMaxUserRegCount()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean23
				.getMaxGroupRegCount()), tableheaderFont)));

		document.add(table);

		if (Integer.parseInt(time.split("-")[1]) > 6) {
			time2 = time.split("-")[0] + "-07";
			time3 = (Integer.parseInt(time.split("-")[0]) + 1) + "-06";

		} else {
			time2 = (Integer.parseInt(time.split("-")[0]) - 1) + "-07";
			time3 = time.split("-")[0] + "-06";
		}
		map.put("starttime", time2);
		map.put("endtime", time3);
		list2 = ToWorkFileServices.system_call_year_level(map);

		DefaultCategoryDataset dcd = new DefaultCategoryDataset();

		for (EastBsCallDataBean bean : list2) {
			if (bean.getLevel().equals("1")) {
				dcd.addValue(
						Math.round(Double.valueOf(bean.getTotalActiveCalls())
								/ bean.getBsTotals()), "一级基站平均呼叫次数",
						bean.getStarttime());
			} else if (bean.getLevel().equals("2")) {
				dcd.addValue(
						Math.round(Double.valueOf(bean.getTotalActiveCalls())
								/ bean.getBsTotals()), "二级基站平均呼叫次数",
						bean.getStarttime());
			} else if (bean.getLevel().equals("3")) {
				dcd.addValue(
						Math.round(Double.valueOf(bean.getTotalActiveCalls())
								/ bean.getBsTotals()), "三级基站平均呼叫次数",
						bean.getStarttime());
			}

		}

		try {
			ChartUtil.LineImg("一、二、三级基站平均呼叫次数趋势图", dcd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image img = Image.getInstance("D:/chart.png");
		document.add(img);

	}

	/* 基站话务-区域 */
	public static void word_6(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title)
			throws DocumentException, IOException {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		EastBsCallDataBean bean11 = new EastBsCallDataBean();
		EastBsCallDataBean bean12 = new EastBsCallDataBean();

		EastBsCallDataBean bean21 = new EastBsCallDataBean();
		EastBsCallDataBean bean22 = new EastBsCallDataBean();

		String time2 = "", time3 = time;
		time2 = (Integer.parseInt(time3.split("-")[0]) - 1) + "-"
				+ time3.split("-")[1];
		if (time3.split("-")[1].equals("01")) {
			time2 = (Integer.parseInt(time3.split("-")[0]) - 1) + "-12";
		} else {
			int y = Integer.parseInt(time3.split("-")[1]) - 1;
			time2 = time3.split("-")[0] + "-" + (y < 10 ? ("0" + y) : y);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("time", time2);
		map.put("period", 4);
		List<EastBsCallDataBean> list2 = ToWorkFileServices
				.system_call_area(map);
		map.put("time", time3);
		map.put("period", 4);
		List<EastBsCallDataBean> list3 = ToWorkFileServices
				.system_call_area(map);
		for (EastBsCallDataBean eastBsCallDataBean : list2) {
			if (eastBsCallDataBean.getArea().equals("绕城内")) {
				bean11 = eastBsCallDataBean;
			} else if (eastBsCallDataBean.getArea().equals("绕城外")) {
				bean12 = eastBsCallDataBean;
			}
		}
		for (EastBsCallDataBean eastBsCallDataBean : list3) {
			if (eastBsCallDataBean.getArea().equals("绕城内")) {
				bean21 = eastBsCallDataBean;
			} else if (eastBsCallDataBean.getArea().equals("绕城外")) {
				bean22 = eastBsCallDataBean;
			}
		}
		Double a1 = (double) 0;
		Double a2 = (double) 0;

		// 设置Table表格,创建一个三列的表格
		Paragraph tn = new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表四 绕城内外基站话务统计");
		document.add(tn);

		Table table = new Table(9);
		int width[] = { 25, 25, 25, 30, 50, 20 };// 设置每列宽度比例
		// table.setWidths(width);
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.addCell(new Cell(new Paragraph("基站等级", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站总呼叫次数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站月平均呼叫次数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站有效呼叫持续时间（分钟）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站月平均通话时长（分钟）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站用户排队数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站最大用户注册数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站最大组注册数", tableheaderFont)));

		table.addCell(new Cell(new Paragraph("绕城内", tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean21
				.getBsTotals()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean21
				.getTotalActiveCalls()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(
				String.valueOf(Math.round(Double.valueOf(bean21
						.getTotalActiveCalls()) / bean21.getBsTotals())),
				tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean21
				.getTotalActiveCallDuration()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double
				.valueOf(bean21.getTotalActiveCallDuration())
				/ bean21.getBsTotals())), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean21
				.getQueueCount()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean21
				.getMaxUserRegCount()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean21
				.getMaxGroupRegCount()), tableheaderFont)));

		table.addCell(new Cell(new Paragraph("绕城外", tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean22
				.getBsTotals()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean22
				.getTotalActiveCalls()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(
				String.valueOf(Math.round(Double.valueOf(bean22
						.getTotalActiveCalls()) / bean22.getBsTotals())),
				tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean22
				.getTotalActiveCallDuration()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double
				.valueOf(bean22.getTotalActiveCallDuration())
				/ bean22.getBsTotals())), tableheaderFont)));

		table.addCell(new Cell(new Paragraph(String.valueOf(bean22
				.getQueueCount()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean22
				.getMaxUserRegCount()), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(bean22
				.getMaxGroupRegCount()), tableheaderFont)));

		document.add(table);

		if (Integer.parseInt(time.split("-")[1]) > 6) {
			time2 = time.split("-")[0] + "-07";
			time3 = (Integer.parseInt(time.split("-")[0]) + 1) + "-06";

		} else {
			time2 = (Integer.parseInt(time.split("-")[0]) - 1) + "-07";
			time3 = time.split("-")[0] + "-06";
		}
		map.put("starttime", time2);
		map.put("endtime", time3);
		list2 = ToWorkFileServices.system_call_year_area(map);

		DefaultCategoryDataset dcd = new DefaultCategoryDataset();

		for (EastBsCallDataBean bean : list2) {
			if (bean.getArea().equals("绕城内")) {
				dcd.addValue(
						Math.round(Double.valueOf(bean.getTotalActiveCalls())
								/ bean.getBsTotals()), "绕城内基站平均呼叫次数",
						bean.getStarttime());
			} else if (bean.getArea().equals("绕城外")) {
				dcd.addValue(
						Math.round(Double.valueOf(bean.getTotalActiveCalls())
								/ bean.getBsTotals()), "绕城外基站平均呼叫次数",
						bean.getStarttime());
			}

		}

		try {
			ChartUtil.LineImg("绕城内/外基站平均呼叫次数趋势图", dcd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image img = Image.getInstance("D:/chart.png");
		document.add(img);

		c.add("绕城内基站本月总通话"
				+ (Double.valueOf(bean21.getTotalActiveCalls()) / 10000)
				+ "万次，");
		a1 = Double.valueOf(bean11.getTotalActiveCalls());
		a2 = Double.valueOf(bean21.getTotalActiveCalls());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ",");

		c.add("通话总时长" + bean21.getTotalActiveCallDuration() + "分钟，");
		a1 = Double.valueOf(bean11.getTotalActiveCallDuration());
		a2 = Double.valueOf(bean21.getTotalActiveCallDuration());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";");

		c.add("平均通话时长" + bean21.getAverageCallDuration() + "秒；");

		c.add("排队数量" + bean21.getQueueCount() + "个，");
		a1 = Double.valueOf(bean11.getQueueCount());
		a2 = Double.valueOf(bean21.getQueueCount());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";\n");

		c.add("绕城外基站本月总通话"
				+ (Double.valueOf(bean22.getTotalActiveCalls()) / 10000)
				+ "万次，");
		a1 = Double.valueOf(bean12.getTotalActiveCalls());
		a2 = Double.valueOf(bean22.getTotalActiveCalls());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";");

		c.add("通话总时长" + bean22.getTotalActiveCallDuration() + "分钟，");
		a1 = Double.valueOf(bean12.getTotalActiveCallDuration());
		a2 = Double.valueOf(bean22.getTotalActiveCallDuration());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";");

		c.add("平均通话时长" + bean22.getAverageCallDuration() + "秒；排队数量"
				+ bean22.getQueueCount() + "个，");
		a1 = Double.valueOf(bean12.getQueueCount());
		a2 = Double.valueOf(bean22.getQueueCount());
		c.add("环比"
				+ (a1 < a2 ? "增加" + String.format("%.2f", (a2 - a1) / a1 * 100)
						+ "%" : "减少"
						+ String.format("%.2f", (a1 - a2) / a1 * 100) + "%")
				+ ";\n");

		document.add(c);

	}

	/* 基站话务-区域-top10 */
	public static void word_7(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		EastBsCallDataBean bean = new EastBsCallDataBean();

		List<EastBsCallDataBean> list = ToWorkFileServices
				.system_call_zone_top10(time);
		DefaultPieDataset dpd = new DefaultPieDataset();
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			dpd.setValue(eastBsCallDataBean.getZone(),
					eastBsCallDataBean.getTotalActiveCalls());
		}
		ChartUtil.PieImg("各行政区域基站话务占比", dpd, 2);
		Image img = Image.getInstance("D:/chart.png");
		document.add(img);

		// 设置Table表格,创建一个三列的表格
		Paragraph tn = new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表五 各行政区域基站话务统计TOP10");
		document.add(tn);

		Table table = new Table(9);
		int width[] = { 25, 25, 25, 30, 50, 20 };// 设置每列宽度比例
		// table.setWidths(width);
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.addCell(new Cell(new Paragraph("话务量排名", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("行政区域", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站总呼叫次数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站有效呼叫持续时间（分钟）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站月平均通话时长（分钟）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站用户排队数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站最大用户注册数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站最大组注册数", tableheaderFont)));
		int i = 1;
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			table.addCell(new Cell(new Paragraph(String.valueOf(i++),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getZone(),
					tableheaderFont)));
			table.addCell(new Cell(
					new Paragraph(String.valueOf(eastBsCallDataBean
							.getBsTotals()), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getTotalActiveCalls()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getAverageCallDuration()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String.valueOf(Math
					.round(Double.valueOf(eastBsCallDataBean
							.getTotalActiveCalls())
							/ eastBsCallDataBean.getBsTotals())),
					tableheaderFont)));

			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getQueueCount()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getMaxUserRegCount()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getMaxGroupRegCount()),
					tableheaderFont)));
		}
		document.add(table);

	}

	/* 基站话务-基站-top10 */
	public static void word_8(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		EastBsCallDataBean bean = new EastBsCallDataBean();

		List<EastBsCallDataBean> list = ToWorkFileServices
				.system_call_bs_top10(time);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("time", time);
		paraMap.put("period", 4);
		Map<String, Object> all_call = ToWorkFileServices.system_bs_call(time);
		// 设置Table表格,创建一个三列的表格
		Paragraph tn = new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表六 基站话务统计TOP10");
		document.add(tn);

		Table table = new Table(11);
		int width[] = { 25, 25, 25, 30, 50, 20 };// 设置每列宽度比例
		// table.setWidths(width);
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.addCell(new Cell(new Paragraph("基站话务量排序", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站ID", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站名称", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站分级", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("行政区域", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站总呼叫次数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站呼叫持续时间（分钟）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站平均呼叫持续时间", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站用户排队数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站最大用户注册数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站最大组注册数", tableheaderFont)));
		int i = 1;
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			table.addCell(new Cell(new Paragraph(String.valueOf(i++),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getBsid()), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getName(),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getLevel(),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getZone(),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getTotalActiveCalls()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(FunUtil
					.second_time(eastBsCallDataBean
							.getTotalActiveCallDuration()), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(FunUtil
					.second_time(eastBsCallDataBean.getAverageCallDuration()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getQueueCount()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getMaxUserRegCount()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getMaxGroupRegCount()),
					tableheaderFont)));
		}
		document.add(table);

		DefaultPieDataset dpd = new DefaultPieDataset();
		StringBuilder str = new StringBuilder();
		int a = 0;
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			dpd.setValue(eastBsCallDataBean.getName(),
					eastBsCallDataBean.getTotalActiveCalls());
			str.append(eastBsCallDataBean.getBsid() + "-"
					+ eastBsCallDataBean.getName() + "、");
			a += eastBsCallDataBean.getTotalActiveCalls();

		}
		ChartUtil.PieImg("基站话务占比 TOP10", dpd, 2);
		Image img = Image.getInstance("D:/chart.png");
		document.add(img);
		c.add("由上图可知，本月\n");
		c.add(str.toString());
		c.add("10个基站的基站有效呼叫总数超过");
		c.add(String.valueOf(a / 10000) + "万");
		c.add("次，占全网基站话务量的"
				+ String.format(
						"%.2f",
						(Double.valueOf(a)
								/ Integer.parseInt(all_call.get("TotalCalls")
										.toString()) * 100)) + "%。\n");
		document.add(c);
	}

	/* 基站话务-排队-top10 */
	public static void word_9(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		EastBsCallDataBean bean = new EastBsCallDataBean();

		List<EastBsCallDataBean> list = ToWorkFileServices
				.system_call_queue_top10(time);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("time", time);
		paraMap.put("period", 4);
		Map<String, Object> all_call = ToWorkFileServices.system_bs_call(time);
		// 设置Table表格,创建一个三列的表格
		Paragraph tn = new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表七 基站排队话务统计TOP10");
		document.add(tn);

		Table table = new Table(11);
		int width[] = { 25, 25, 25, 30, 50, 20 };// 设置每列宽度比例
		// table.setWidths(width);
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.addCell(new Cell(new Paragraph("基站排队排名", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站ID", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站名称", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站分级", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("行政区域", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站总呼叫次数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站呼叫持续时间（分钟）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站平均呼叫持续时间", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站持续排队时间", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站最大用户注册数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("基站最大组注册数", tableheaderFont)));
		int i = 1;
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			table.addCell(new Cell(new Paragraph(String.valueOf(i++),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getBsid()), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getName(),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getLevel(),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getZone(),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getTotalActiveCalls()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(FunUtil
					.second_time(eastBsCallDataBean
							.getTotalActiveCallDuration()), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(FunUtil
					.second_time(eastBsCallDataBean.getAverageCallDuration()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(FunUtil
					.second_time(eastBsCallDataBean.getQueueDuration()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getMaxUserRegCount()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getMaxGroupRegCount()),
					tableheaderFont)));
		}
		document.add(table);

		DefaultPieDataset dpd = new DefaultPieDataset();
		StringBuilder str = new StringBuilder();
		int a = 0;
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			dpd.setValue(eastBsCallDataBean.getName(),
					eastBsCallDataBean.getTotalActiveCalls());
			str.append(eastBsCallDataBean.getBsid() + "-"
					+ eastBsCallDataBean.getName() + "、");
			a += eastBsCallDataBean.getQueueCount();

		}
		ChartUtil.PieImg("基站排队占比 TOP10", dpd, 2);
		Image img = Image.getInstance("D:/chart.png");
		document.add(img);
		c.add("由上图可知，本月\n");
		c.add(str.toString());
		c.add("10个基站");
		c.add("占全网排队数量的"
				+ String.format(
						"%.2f",
						(Double.valueOf(a)
								/ Integer.parseInt(all_call.get("QueueCount")
										.toString()) * 100)) + "%。\n");
		document.add(c);
	}

	/* 基站话务-用户单位-top10 */
	public static void word_10(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		EastBsCallDataBean bean = new EastBsCallDataBean();

		List<EastVpnCallBean> list = ToWorkFileServices
				.system_call_vpn_top10(time);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("time", time);
		paraMap.put("period", 4);
		Map<String, Object> all_call = ToWorkFileServices.system_call(paraMap);
		// 设置Table表格,创建一个三列的表格
		Paragraph tn = new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表八 用户单位话务统计TOP10");
		document.add(tn);

		Table table = new Table(6);
		int width[] = { 25, 25, 25, 30, 50, 20 };// 设置每列宽度比例
		// table.setWidths(width);
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.addCell(new Cell(new Paragraph("序号", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("单位名称", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("用户单位有效呼叫总数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("用户单位有效呼叫总持续时间", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("用户单位平均呼叫持续时间", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("用户单位呼叫成功率", tableheaderFont)));
		int i = 1;
		for (EastVpnCallBean eastBsCallDataBean : list) {
			table.addCell(new Cell(new Paragraph(String.valueOf(i++),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getName(),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getTotalActiveCalls()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(FunUtil
					.second_time(eastBsCallDataBean
							.getTotalActiveCallDuration()), tableheaderFont)));
			table.addCell(new Cell(new Paragraph(FunUtil
					.second_time(eastBsCallDataBean.getAverageCallDuration()),
					tableheaderFont)));
			table.addCell(new Cell(new Paragraph(String
					.valueOf(eastBsCallDataBean.getPercent() + "%"),
					tableheaderFont)));
		}
		document.add(table);

		DefaultPieDataset dpd = new DefaultPieDataset();
		StringBuilder str = new StringBuilder();
		int a = 0;
		for (EastVpnCallBean eastBsCallDataBean : list) {
			dpd.setValue(eastBsCallDataBean.getName(),
					eastBsCallDataBean.getTotalActiveCalls());
			str.append(eastBsCallDataBean.getName() + "、");
			a += eastBsCallDataBean.getTotalActiveCalls();

		}
		ChartUtil.PieImg("用户单位话务统计 TOP10", dpd, 0);
		Image img = Image.getInstance("D:/chart.png");
		document.add(img);

		String time1 = "";
		if (time.split("-")[1].equals("01")) {
			time1 = (Integer.parseInt(time.split("-")[0]) - 1) + "-12";
		} else {
			int y = Integer.parseInt(time.split("-")[1]) - 1;
			time1 = time.split("-")[0] + "-" + (y < 10 ? ("0" + y) : y);
		}
		List<EastVpnCallBean> list2 = ToWorkFileServices
				.system_call_vpn_top10(time1);
		DefaultCategoryDataset dct = new DefaultCategoryDataset();
		for (EastVpnCallBean eastVpnCallBean : list2) {
			dct.addValue(eastVpnCallBean.getTotalActiveCalls(), time1,
					eastVpnCallBean.getName());
		}
		for (EastVpnCallBean eastVpnCallBean : list) {
			dct.addValue(eastVpnCallBean.getTotalActiveCalls(), time,
					eastVpnCallBean.getName());
		}
		ChartUtil.BarImg("用户单位话务统计 TOP10", dct);
		document.add(img);

		c.add("由上图可知，本月\n");
		c.add(str.toString());
		c.add("10家用户单位使用成都应急通信网的频率最高");
		c.add("话务量占全网的"
				+ String.format(
						"%.2f",
						(Double.valueOf(a)
								/ Integer.parseInt(all_call.get("TotalCalls")
										.toString()) * 100)) + "%。\n");
		document.add(c);
	}

	/* 基站话务-排队-top10 */
	public static void word_11(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		EastBsCallDataBean bean = new EastBsCallDataBean();

		List<EastBsCallDataBean> list = ToWorkFileServices
				.chart_bs_userreg_top10(time);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("time", time);
		paraMap.put("period", 4);
		Map<String, Object> all_call = ToWorkFileServices.system_bs_call(time);

		DefaultCategoryDataset dpd = new DefaultCategoryDataset();
		StringBuilder str = new StringBuilder();
		int a = 0;
		for (EastBsCallDataBean eastBsCallDataBean : list) {
			dpd.setValue(eastBsCallDataBean.getMaxUserRegCount(),
					eastBsCallDataBean.getStarttime(),
					eastBsCallDataBean.getName());

		}
		if (list.size() > 0) {
			str.append("基站最大用户注册数为" + (list.get(0).getMaxUserRegCount())
					+ "，出现在" + list.get(0).getBsid() + "-"
					+ list.get(0).getName());
		}
		ChartUtil.BarImg("基站最大注册用户数前十", dpd);
		Image img = Image.getInstance("D:/chart.png");
		document.add(img);
		c.add(str.toString());
		document.add(c);
	}

	/* 四 基础运维工作 */
	public static void word_12(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		/* 设置标题1格式 */
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1;
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt1.setStyle(Font.BOLD);
		rtfGsBt1.setSize(14);
		/* 设置标题2格式 */
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2;
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt2.setStyle(Font.NORMAL);
		rtfGsBt2.setSize(12);

		title = new Paragraph("四 基础运维工作");
		title.setAlignment(Element.ALIGN_LEFT);
		title.setFont(rtfGsBt1);
		title.setSpacingAfter(6);
		document.add(title);
		StringBuilder str = new StringBuilder();
		str.append("   项目部运维严格遵守《成都应急网运维管理制度》，其中包含《交换中心机房管理制度》、《值班与交接班制度》、《保密制度》、《维护作业计");
		str.append("划管理制度》、《仪器仪表及备品备件管理制度》、《通信故障管理制度》、《巡检制度》、《维护资项目部料管理制度》；切实按照《成都应急网应急通信保障预案》响应应急通信保障。");
		c.add(str.toString());
		document.add(c);
		c.clear();
		str = new StringBuilder();
		str.append("   系统基础运维工作主要包括交换中心监控，巡检，故障抢修，基站、交换中心或传输优化整改，"
				+ "用户投诉响应，培训与技术支持服务，应急演练，应急通信保障等。");
		c.add(str.toString());
		document.add(c);
	}

	/* 4.1交换中心 */
	public static void word_13(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		/* 设置标题1格式 */
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1;
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt1.setStyle(Font.BOLD);
		rtfGsBt1.setSize(14);
		/* 设置标题2格式 */
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2;
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt2.setStyle(Font.NORMAL);
		rtfGsBt2.setSize(12);

		title = new Paragraph("1 交换中心监控");
		title.setAlignment(Element.ALIGN_LEFT);
		title.setFont(rtfGsBt2);
		title.setSpacingAfter(6);
		document.add(title);

		StringBuilder str = new StringBuilder();
		str.append("  本月交换中心运行正常，完成7×24小时系统监控并填写了31份系统日常维护表。"
				+ "本月后台监控发现隐患25起，24起处理完成均为现场处理，" + "1起遗留至下月处理。详见3.4、隐患故障。");
		c.add(str.toString());
		document.add(c);

		// 设置Table表格,创建一个三列的表格
		Paragraph tn = new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表九 后台监控反馈表");
		document.add(tn);
		Table table = new Table(6);
		int width[] = { 25, 25, 25, 30, 50, 20 };// 设置每列宽度比例
		// table.setWidths(width);
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		Cell cell;
		cell = new Cell(new Paragraph("7×24小时系统监控", tableheaderFont));
		cell.setRowspan(2);
		table.addCell(cell);
		table.addCell(new Cell(new Paragraph("应填写系统日常维护表（份）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("实际填写系统日常维护表（份）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("后台监控发现隐患数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("隐患处理数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("备注", tableheaderFont)));
		document.add(table);
	}

	/* 4.2巡检 */
	public static void word_14(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		/* 设置标题1格式 */
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1;
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt1.setStyle(Font.BOLD);
		rtfGsBt1.setSize(14);
		/* 设置标题2格式 */
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2;
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt2.setStyle(Font.NORMAL);
		rtfGsBt2.setSize(12);

		title = new Paragraph("2 巡检");
		title.setAlignment(Element.ALIGN_LEFT);
		title.setFont(rtfGsBt2);
		title.setSpacingBefore(6);
		title.setSpacingAfter(6);
		document.add(title);

		Map<String, Object> map = new HashMap<String, Object>();
		map = ToWorkFileServices.xj_bs_all_type_num(4);

		StringBuilder str = new StringBuilder();
		str.append("全网"
				+ (map.get("bs") == null ? String.valueOf(0) : map.get("bs")
						.toString()) + "个固定基站、");
		str.append(map.get("room") == null ? 0 : map.get("room") + "个室内覆盖站、");
		str.append(map.get("vertical") == null ? 0 : map.get("vertical")
				+ "个直放站、");
		str.append(map.get("bus") == null ? 0 : map.get("bus") + "辆应急通信车，");
		str.append("实际完成巡检" + map.get("total") == null ? 0 : map.get("bus")
				+ "次");
		str.append("巡检过程中发现隐患    起，已处理   起，详见《巡检记录汇总表》。");
		c.add(str.toString());
		document.add(c);
		// 设置Table表格,创建一个三列的表格
		Paragraph tn = new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表十 巡检反馈表");
		document.add(tn);
		Table table = new Table(9);
		int width[] = { 25, 25, 25, 30, 50, 20 };// 设置每列宽度比例
		// table.setWidths(width);
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.addCell(new Cell(new Paragraph("资源类别", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("资源数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("应该巡检次数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("实际巡检数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("隐患数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("隐患处理数量", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("应填写系统巡检表（份）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("实际填写系统巡检表（份）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("备注", tableheaderFont)));

		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> mapPara = new HashMap<String, Object>();
		mapPara.put("time", time);
		mapPara.put("period", 4);
		map2 = ToWorkFileServices.xj_bs_num(mapPara);

		table.addCell(new Cell(new Paragraph("固定基站", tableheaderFont)), 1, 0);
		table.addCell(new Cell(new Paragraph(map.get("bs") == null ? String
				.valueOf(0) : map.get("bs").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map.get("bs") == null ? String
				.valueOf(0) : map.get("bs").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map2.get("bs") == null ? String
				.valueOf(0) : map2.get("bs").toString(), tableheaderFont)));
		table.addCell(
				new Cell(
						new Paragraph(map.get("bs") == null ? String.valueOf(0)
								: map.get("bs").toString(), tableheaderFont)),
				1, 6);
		table.addCell(
				new Cell(new Paragraph(map2.get("bs") == null ? String
						.valueOf(0) : map2.get("bs").toString(),
						tableheaderFont)), 1, 7);

		table.addCell(new Cell(new Paragraph("室内覆盖", tableheaderFont)), 2, 0);
		table.addCell(new Cell(new Paragraph(map.get("room") == null ? String
				.valueOf(0) : map.get("room").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map.get("room") == null ? String
				.valueOf(0) : map.get("room").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map2.get("room") == null ? String
				.valueOf(0) : map2.get("room").toString(), tableheaderFont)));
		table.addCell(
				new Cell(new Paragraph(map.get("room") == null ? String
						.valueOf(0) : map.get("room").toString(),
						tableheaderFont)), 2, 6);
		table.addCell(
				new Cell(new Paragraph(map2.get("room") == null ? String
						.valueOf(0) : map2.get("room").toString(),
						tableheaderFont)), 2, 7);

		table.addCell(new Cell(new Paragraph("直放站", tableheaderFont)), 3, 0);
		table.addCell(new Cell(new Paragraph(
				map.get("vertical") == null ? String.valueOf(0) : map.get(
						"vertical").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(
				map.get("vertical") == null ? String.valueOf(0) : map.get(
						"vertical").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(
				map2.get("vertical") == null ? String.valueOf(0) : map2.get(
						"vertical").toString(), tableheaderFont)));
		table.addCell(
				new Cell(new Paragraph(map.get("vertical") == null ? String
						.valueOf(0) : map.get("vertical").toString(),
						tableheaderFont)), 3, 6);
		table.addCell(
				new Cell(new Paragraph(map2.get("vertical") == null ? String
						.valueOf(0) : map2.get("vertical").toString(),
						tableheaderFont)), 3, 7);

		table.addCell(new Cell(new Paragraph("应急通信车", tableheaderFont)), 4, 0);
		table.addCell(new Cell(new Paragraph(map.get("bus") == null ? String
				.valueOf(0) : map.get("bus").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map.get("bus") == null ? String
				.valueOf(0) : map.get("bus").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map2.get("bus") == null ? String
				.valueOf(0) : map2.get("bus").toString(), tableheaderFont)));
		table.addCell(
				new Cell(new Paragraph(map.get("bus") == null ? String
						.valueOf(0) : map.get("bus").toString(),
						tableheaderFont)), 4, 6);
		table.addCell(
				new Cell(new Paragraph(map2.get("bus") == null ? String
						.valueOf(0) : map2.get("bus").toString(),
						tableheaderFont)), 4, 7);

		table.addCell(new Cell(new Paragraph("合计", tableheaderFont)), 5, 0);
		table.addCell(new Cell(new Paragraph(map.get("total") == null ? String
				.valueOf(0) : map.get("total").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map.get("total") == null ? String
				.valueOf(0) : map.get("total").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map2.get("total") == null ? String
				.valueOf(0) : map2.get("total").toString(), tableheaderFont)));
		table.addCell(
				new Cell(new Paragraph(map.get("total") == null ? String
						.valueOf(0) : map.get("total").toString(),
						tableheaderFont)), 5, 6);
		table.addCell(
				new Cell(new Paragraph(map2.get("total") == null ? String
						.valueOf(0) : map2.get("total").toString(),
						tableheaderFont)), 5, 7);

		document.add(table);
	}

	/* 4.3故障处理与分析 */
	public static void word_15(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		/* 设置标题1格式 */
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1;
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt1.setStyle(Font.BOLD);
		rtfGsBt1.setSize(14);
		/* 设置标题2格式 */
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2;
		/*
		 * rtfGsBt2.setAlignment(Element.ALIGN_LEFT);
		 * rtfGsBt2.setStyle(Font.NORMAL); rtfGsBt2.setSize(12);
		 */
		/* 设置标题3格式 */
		RtfParagraphStyle rtfGsBt3 = RtfParagraphStyle.STYLE_HEADING_3;
		/*
		 * rtfGsBt3.setAlignment(Element.ALIGN_LEFT);
		 * rtfGsBt3.setStyle(Font.NORMAL); rtfGsBt3.setSize(10);
		 */

		title = new Paragraph("3 故障处理与分析");
		title.setAlignment(Element.ALIGN_LEFT);
		title.setFont(rtfGsBt2);
		title.setSpacingBefore(6);
		title.setSpacingAfter(6);
		document.add(title);

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapPara = new HashMap<String, Object>();
		mapPara.put("time", time);
		map = ToWorkFileServices.fault_num(mapPara);

		StringBuilder str = new StringBuilder();
		str.append("本月系统发生故障"
				+ (map.get("total") == null ? String.valueOf(0) : map.get(
						"total").toString()) + "起， ");
		str.append("特别重大故障"
				+ (map.get("one") == null ? String.valueOf(0) : map.get("one")
						.toString()) + "，起");
		str.append("重大故障"
				+ (map.get("two") == null ? String.valueOf(0) : map.get("two")
						.toString()) + "起，");
		str.append("一般故障"
				+ (map.get("three") == null ? String.valueOf(0) : map.get(
						"three").toString()) + "起，");
		str.append("其中维护计划作业       起，");
		str.append("隐患故障"
				+ (map.get("four") == null ? String.valueOf(0) : map
						.get("four").toString()) + "起。");
		c.add(str.toString());
		document.add(c);
		// 设置Table表格,创建一个三列的表格
		/*
		 * Paragraph tn=new Paragraph(); tn.setAlignment(Element.ALIGN_CENTER);
		 * tn.setFont(tableTitleFont); tn.setSpacingAfter(1);
		 * tn.add("表十 巡检反馈表"); document.add(tn);
		 */
		Table table = new Table(5);
		int width[] = { 25, 25, 25, 30, 50, 20 };// 设置每列宽度比例
		// table.setWidths(width);
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.addCell(new Cell(new Paragraph("故障类别", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("故障次数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("申请核减", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("核减后", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("备注", tableheaderFont)));

		table.addCell(new Cell(new Paragraph("特别重大故障", tableheaderFont)), 1, 0);
		table.addCell(new Cell(new Paragraph(map.get("one") == null ? String
				.valueOf(0) : map.get("one").toString(), tableheaderFont)));
		table.addCell(new Cell(
				new Paragraph(String.valueOf(0), tableheaderFont)));
		table.addCell(new Cell(
				new Paragraph(String.valueOf(0), tableheaderFont)));

		table.addCell(new Cell(new Paragraph("重大故障", tableheaderFont)), 2, 0);
		table.addCell(new Cell(new Paragraph(map.get("two") == null ? String
				.valueOf(0) : map.get("two").toString(), tableheaderFont)));
		table.addCell(new Cell(
				new Paragraph(String.valueOf(0), tableheaderFont)));
		table.addCell(new Cell(
				new Paragraph(String.valueOf(0), tableheaderFont)));

		table.addCell(new Cell(new Paragraph("一般故障", tableheaderFont)), 3, 0);
		table.addCell(new Cell(new Paragraph(map.get("three") == null ? String
				.valueOf(0) : map.get("three").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(
				map.get("checked") == null ? String.valueOf(0) : map.get(
						"checked").toString(), tableheaderFont)));
		int num = Integer.parseInt(map.get("three") == null ? String.valueOf(0)
				: map.get("three").toString());
		int chk1 = Integer.parseInt(map.get("checked") == null ? String
				.valueOf(0) : map.get("checked").toString());
		table.addCell(new Cell(new Paragraph(String.valueOf(num - chk1),
				tableheaderFont)));

		table.addCell(new Cell(new Paragraph("合计", tableheaderFont)), 4, 0);
		table.addCell(new Cell(new Paragraph(map.get("total") == null ? String
				.valueOf(0) : map.get("total").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(
				map.get("checked") == null ? String.valueOf(0) : map.get(
						"checked").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(String.valueOf(num - chk1),
				tableheaderFont)));
		document.add(table);

		DefaultCategoryDataset dcd = new DefaultCategoryDataset();

		TimeSeries timeSeries = new TimeSeries(
				Integer.parseInt(time.split("-")[0]) + "系统故障趋势图");

		for (int i = 1; i <= 12; i++) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("time", time.split("-")[0] + "-" + (i < 10 ? "0" + i : i));
			Map<String, Object> m2 = ToWorkFileServices.fault_num(m);
			dcd.addValue(
					(Number) Math.round(Double.valueOf(m2.get("total") == null ? String
							.valueOf(0) : m2.get("total").toString())), time
							.split("-")[0], (i < 10 ? "0" + i : i));
			timeSeries.add(new Month(i, Integer.parseInt(time.split("-")[0])),
					Double.valueOf(m2.get("total") == null ? String.valueOf(0)
							: m2.get("total").toString()));
		}
		try {
			ChartUtil.LineImg("系统故障趋势图", timeSeries);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image img = Image.getInstance("D:/chart.png");
		document.add(img);

	}

	/* 4.3.1特别重大故障 */
	public static void word_16(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		/* 设置标题1格式 */
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1;
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt1.setStyle(Font.BOLD);
		rtfGsBt1.setSize(14);
		/* 设置标题2格式 */
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2;
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt2.setStyle(Font.NORMAL);
		rtfGsBt2.setSize(12);
		/* 设置标题3格式 */
		RtfParagraphStyle rtfGsBt3 = RtfParagraphStyle.STYLE_HEADING_3;
		/*
		 * rtfGsBt3.setAlignment(Element.ALIGN_LEFT);
		 * rtfGsBt3.setStyle(Font.NORMAL); rtfGsBt3.setSize(10);
		 */

		title = new Paragraph("3.1、特别重大故障");
		title.setAlignment(Element.ALIGN_LEFT);
		title.setFont(rtfGsBt3);
		title.setSpacingBefore(6);
		title.setSpacingAfter(6);
		document.add(title);

	}

	/* 4.3.2重大故障 */
	public static void word_17(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		/* 设置标题1格式 */
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1;
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt1.setStyle(Font.BOLD);
		rtfGsBt1.setSize(14);
		/* 设置标题2格式 */
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2;
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt2.setStyle(Font.NORMAL);
		rtfGsBt2.setSize(12);
		/* 设置标题3格式 */
		RtfParagraphStyle rtfGsBt3 = RtfParagraphStyle.STYLE_HEADING_3;
		rtfGsBt3.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt3.setStyle(Font.NORMAL);
		rtfGsBt3.setSize(10);

		title = new Paragraph("3.2、重大故障");
		title.setAlignment(Element.ALIGN_LEFT);
		title.setFont(rtfGsBt3);
		title.setSpacingBefore(6);
		title.setSpacingAfter(6);
		document.add(title);

	}

	/* 4.3..3、一般故障 */
	public static void word_18(String time, Document document, Paragraph c,
			RtfParagraphStyle rtfGsBt, Paragraph title) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

		/* 设置标题1格式 */
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1;
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt1.setStyle(Font.BOLD);
		rtfGsBt1.setSize(14);
		/* 设置标题2格式 */
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2;
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt2.setStyle(Font.NORMAL);
		rtfGsBt2.setSize(12);
		/* 设置标题3格式 */
		RtfParagraphStyle rtfGsBt3 = RtfParagraphStyle.STYLE_HEADING_3;
		rtfGsBt3.setAlignment(Element.ALIGN_LEFT);
		rtfGsBt3.setStyle(Font.NORMAL);
		rtfGsBt3.setSize(10);

		title = new Paragraph("3.3、一般故障");
		title.setAlignment(Element.ALIGN_LEFT);
		title.setFont(rtfGsBt3);
		title.setSpacingBefore(6);
		title.setSpacingAfter(6);
		document.add(title);

		Paragraph tn = new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表十一 一般故障分析（按基站等级分类）");
		document.add(tn);
		Table table = new Table(6);
		int width[] = { 25, 25, 25, 30, 50, 20 };// 设置每列宽度比例
		// table.setWidths(width);
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.setWidth(100);// 占页面宽度比例
		table.setAlignment(1);
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		table.addCell(new Cell(new Paragraph("基站类别", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("故障次数", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("申请核减", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("核减后断站", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("处理超时（次）", tableheaderFont)));
		table.addCell(new Cell(new Paragraph("超时时间（小时）", tableheaderFont)));

		Map<String, Object> mapPara = new HashMap<String, Object>();
		mapPara.put("time", time);
		mapPara.put("period", 4);

		List<Map<String, Object>> list = ToWorkFileServices
				.fault_level(mapPara);
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		int total = 0, checked = 0, afterCheck = 0;
		for (Map<String, Object> map : list) {

			/* System.out.println("mmmmmm->"+map); */
			total += Integer.parseInt(map.get("total").toString());
			checked += Integer.parseInt(map.get("checked").toString());
			afterCheck += Integer.parseInt(map.get("afterCheck").toString());

			if (map.get("level").toString().equals("1")) {
				map1.put("total", map.get("total"));
				map1.put("checked", map.get("checked"));
				map1.put("afterCheck", map.get("afterCheck"));
			} else if (map.get("level").toString().equals("2")) {
				map2.put("total", map.get("total"));
				map2.put("checked", map.get("checked"));
				map2.put("afterCheck", map.get("afterCheck"));
			} else if (map.get("level").toString().equals("3")) {
				map3.put("total", map.get("total"));
				map3.put("checked", map.get("checked"));
				map3.put("afterCheck", map.get("afterCheck"));
			}
		}
		table.addCell(new Cell(new Paragraph("一级基站", tableheaderFont)), 1, 0);
		table.addCell(new Cell(new Paragraph(map1 == null ? "0" : map1.get(
				"total").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map1 == null ? "0" : map1.get(
				"checked").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map1 == null ? "0" : map1.get(
				"afterCheck").toString(), tableheaderFont)));

		table.addCell(new Cell(new Paragraph("二级基站", tableheaderFont)), 2, 0);
		table.addCell(new Cell(new Paragraph(map2 == null ? "0" : map2.get(
				"total").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map2 == null ? "0" : map2.get(
				"checked").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map2 == null ? "0" : map2.get(
				"afterCheck").toString(), tableheaderFont)));

		table.addCell(new Cell(new Paragraph("三级基站", tableheaderFont)), 3, 0);
		table.addCell(new Cell(new Paragraph(map3 == null ? "0" : map3.get(
				"total").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map3 == null ? "0" : map3.get(
				"checked").toString(), tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map3 == null ? "0" : map3.get(
				"afterCheck").toString(), tableheaderFont)));

		table.addCell(new Cell(new Paragraph("合计", tableheaderFont)), 4, 0);
		table.addCell(new Cell(new Paragraph(String.valueOf(total),
				tableheaderFont)), 4, 1);
		table.addCell(new Cell(new Paragraph(String.valueOf(checked),
				tableheaderFont)), 4, 2);
		table.addCell(new Cell(new Paragraph(String.valueOf(afterCheck),
				tableheaderFont)), 4, 3);
		document.add(table);

		DefaultPieDataset dpd = new DefaultPieDataset();
		for (Map<String, Object> map : list) {

			if (map.get("level").toString().equals("1")) {
				dpd.setValue("一级基站", (Number) map.get("total"));
			} else if (map.get("level").toString().equals("2")) {
				dpd.setValue("二级基站", (Number) map.get("total"));
			} else if (map.get("level").toString().equals("3")) {
				dpd.setValue("三级基站", (Number) map.get("total"));
			}
		}

		ChartUtil.PieImg("一般故障基站等级占比", dpd, 0);
		Image img = Image.getInstance("D:/chart.png");
		document.add(img);

		list = new ArrayList<Map<String, Object>>();
		mapPara.clear();
		mapPara.put("time", time.split(",")[0]);
		mapPara.put("period", 4);
		list = ToWorkFileServices.fault_level_pie(mapPara);

		DefaultCategoryDataset dcd = new DefaultCategoryDataset();
		TimeSeries timeSeries = new TimeSeries(
				Integer.parseInt(time.split("-")[0]) + "系统故障趋势图");

		/*
		 * for(int i=1;i<=12;i++){ Map<String,Object> m=new HashMap<String,
		 * Object>(); Map<String,Object> m2=new HashMap<String, Object>(); for
		 * (Map<String, Object> map : list) { if(map.get("month")!=null &&
		 * Integer.parseInt(map.get("month").toString())==i){ m2=map; } }
		 * dcd.addValue((Number)
		 * Math.round(Double.valueOf(m2==null?"0":m2.get("total").toString())),
		 * time.split("-")[0],(i<10?"0"+i:i)); timeSeries.add(new
		 * Month(i,Integer
		 * .parseInt(time.split("-")[0])),Double.valueOf(m2==null?
		 * "0":m2.get("total").toString())); } try {
		 * ChartUtil.LineImg("一级基站故障次数", timeSeries); } catch (Exception e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * img=Image.getInstance("D:/chart.png"); document.add(img);
		 */

	}

}
