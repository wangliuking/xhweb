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
import xh.mybatis.bean.MeetBean;
import xh.mybatis.bean.RecordCommunicationBean;
import xh.mybatis.bean.UserNeedBean;
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
import com.zhuozhengsoft.pageoffice.wordwriter.WdLineStyle;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;

@Controller
@RequestMapping(value = "/word")
public class ToWordFileController {

	@RequestMapping("/showReport")
	public ModelAndView meet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String time=request.getParameter("time");
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
		
		w_4_5(wordDoc,request,time);
		w_4_8(wordDoc,request,time);

		poCtrl.setWriter(wordDoc);
		String nowYear=FunUtil.nowDateNotTime().split("-")[0];
		String nowMonth=FunUtil.nowDateNotTime().split("-")[1];
		String descPath=request.getSession().getServletContext().getRealPath("/doc/monthReport")+"/"+nowYear;
		String fileName="系统运行维护服务月报-"+nowYear+"年"+nowMonth+"月-三期.docx";
		createFile(fileName, request);
		String path="/doc/monthReport/"+nowYear+"/"+fileName;
		
		

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
		poCtrl.webOpen(descPath+"/"+fileName,OpenModeType.docNormalEdit, "");
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
		a1 = Double.valueOf(map_last_year_call==null || map_last_year_call.get("gpsTotals") == null ? "0"
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
			table5.openCellRC(index, 2).setValue(String.valueOf(eastBsCallDataBean.getQueueCount()));
			table5.openCellRC(index, 3).setValue(String
					.valueOf(eastBsCallDataBean.getBsid()));
			table5.openCellRC(index, 4).setValue(eastBsCallDataBean.getName());
			table5.openCellRC(index, 5).setValue(eastBsCallDataBean.getLevel());
			table5.openCellRC(index, 6).setValue(eastBsCallDataBean.getZone());
			table5.openCellRC(index, 7).setValue(String
					.valueOf(eastBsCallDataBean.getTotalActiveCalls()));
			table5.openCellRC(index, 8).setValue(FunUtil
					.second_time(eastBsCallDataBean
							.getTotalActiveCallDuration()));
			table5.openCellRC(index, 9).setValue(FunUtil
					.second_time(eastBsCallDataBean.getAverageCallDuration()));
			table5.openCellRC(index, 10).setValue(FunUtil
					.second_time(eastBsCallDataBean.getQueueDuration()));
			table5.openCellRC(index, 11).setValue(String
					.valueOf(eastBsCallDataBean.getMaxUserRegCount()));
			table5.openCellRC(index, 12).setValue(String
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
	
	public WordDocument w_4_5(WordDocument doc,HttpServletRequest request,String time) {	
		List<UserNeedBean> rs=ToWorkFileServices.user_need(time);
		int a1=0;
		if(rs!=null){
			a1=rs.size();
		}
		doc.openDataRegion("PO_w_4_5_1").setValue(String.valueOf(a1));
		com.zhuozhengsoft.pageoffice.wordwriter.Table table = doc.openDataRegion("PO_w_4_5_t_001").openTable(1);
		int z=0;
		if(a1>0){			
			for(int i=1;i<=rs.size()*8;i++){
				table.insertRowAfter(table.openCellRC(1, 3));
				//table.openCellRC(i, 2).mergeTo(2, 3);
			}
		} 
		int rows=rs.size()*8;
		
		/*for(int i=1;i<=rows;i++){
			if(i%8==0){
				System.out.println("row"+i);
				table.openCellRC(i, 1).mergeTo(1, 3);				
				table.openCellRC(i, 1).getBorder().setLineStyle(WdLineStyle.wdLineStyleDashDot);
			}
		}*/
		
		z=0;
	    for(int i=0;i<rs.size();i++){
	    	UserNeedBean bean=rs.get(i);
	    	table.openCellRC(1+z, 1).setValue("时间/联系人");
	    	table.openCellRC(1+z, 2).setValue(bean.getTime());
	    	table.openCellRC(1+z, 3).setValue(bean.getContact_person());
	    	table.openCellRC(2+z, 1).setValue("地点");
	    	table.openCellRC(2+z, 2).mergeTo(2+z, 3);
	    	table.openCellRC(2+z, 2).setValue(bean.getAddress());
	    	
	    	table.openCellRC(3+z, 1).setValue("现象描述");
	    	table.openCellRC(3+z, 2).mergeTo(3+z, 3);
	    	table.openCellRC(3+z, 2).setValue(bean.getDescription());
	    	
	    	table.openCellRC(4+z, 1).setValue("接单响应");
	    	table.openCellRC(4+z, 2).mergeTo(4+z, 3);
	    	table.openCellRC(4+z, 2).setValue(bean.getResponse());
	    	
	    	table.openCellRC(5+z, 1).setValue("处理跟踪");
	    	table.openCellRC(5+z, 2).mergeTo(5+z, 3);
	    	table.openCellRC(5+z, 2).setValue(bean.getHandle());
	    	
	    	table.openCellRC(6+z, 1).setValue("处理结果");
	    	table.openCellRC(6+z, 2).mergeTo(6+z, 3);
	    	table.openCellRC(6+z, 2).setValue(bean.getResult());
	    	
	    	table.openCellRC(7+z, 1).setValue("备注");
	    	table.openCellRC(7+z, 2).mergeTo(7+z, 3);
	    	table.openCellRC(7+z, 2).setValue(bean.getNote());
	    	
	    	table.openCellRC(8+z, 1).mergeTo(8+z, 3);
	    	table.openCellRC(8+z, 1).getBorder().setLineStyle(WdLineStyle.wdLineStyleDashDot);
	    	z+=8;
	    }
		return doc;
	}
	public WordDocument w_4_8(WordDocument doc,HttpServletRequest request,String time) {	
		List<RecordCommunicationBean> rs=ToWorkFileServices.RecordCommunication(time);
		int a1=0,a2=0;
		if(rs!=null){
			a1=rs.size();
			a2=rs.size();
		}
		doc.openDataRegion("PO_w_8_1").setValue(String.valueOf(a1));
		doc.openDataRegion("PO_w_8_2").setValue(String.valueOf(a2));
		com.zhuozhengsoft.pageoffice.wordwriter.Table table = doc.openDataRegion("PO_w_8_t_001").openTable(1);
		// 扩充表格
	    for (int j = 1; j < rs.size(); j++)
	    {
	        table.insertRowAfter(table.openCellRC(2, 4));  //在第1行的最后一个单元格下插入新行
	    }
	   
	    
	    for(int i=0;i<rs.size();i++){
	    	RecordCommunicationBean bean=rs.get(i);	
	    	String times=bean.getStart_time().split("-")[0];
	    	times=times+"年"+bean.getStart_time().split("-")[1]+"月"+bean.getStart_time().split("-")[2]+"日";
	    	times=times+" - "+bean.getEnd_time().split("-")[1]+"月"+bean.getEnd_time().split("-")[2]+"日";
	    	table.openCellRC(i+2, 1).setValue(bean.getName());
	    	table.openCellRC(i+2, 2).setValue(times);
	    	table.openCellRC(i+2, 3).setValue(bean.getZone());
	    	table.openCellRC(i+2, 4).setValue(bean.getLevel());
	    }
	    
		
		return doc;
	}
	public void createFile(String fileName,HttpServletRequest request) {
		String path=request.getSession().getServletContext().getRealPath("")+"/doc/template/月报.docx";
		String savePath=request.getSession().getServletContext().getRealPath("doc/monthReport/");
		savePath +=FunUtil.nowDateNotTime().split("-")[0];
		
		System.out.println("地址："+savePath);
		String filePath=savePath+"/"+fileName;
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
