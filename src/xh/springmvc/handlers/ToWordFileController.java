package xh.springmvc.handlers;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

import xh.func.plugin.ChartUtil;
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

@Controller
@RequestMapping(value="/word")
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
		String time="2019-03";
		String pathname = saveDir + "/系统运行维护服务月报  -"+time+".doc";
		try {
			to_word(time,3,pathname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(value="/monthword",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> monthword(HttpServletRequest request,HttpServletResponse response){
		String saveDir = request.getSession().getServletContext().getRealPath("/download");
		String time=request.getParameter("time");
		String pathname = saveDir + "/系统运行维护服务月报  -"+time+".doc";
		try {
			to_word("2019-01",3,pathname);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 HashMap<String, Object> result = new HashMap<String, Object>();
		 result.put("success", true);
		 result.put("pathName", pathname);
		 return result;
	}

	public static void to_word(String time,int period,String path) throws Exception {
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		//页脚，页眉
		Font hfFont = new Font(bfChinese,9, Font.NORMAL);
		// 标题字体风格
		Font titleFont = new Font(bfChinese, 12, Font.BOLD);
		Font titleBaoFont = new Font(bfChinese, 20, Font.BOLD);
		Font underLineFont = new Font(bfChinese, 12, Font.UNDERLINE);
		// 正文字体风格
		Font contextFont = new Font(bfChinese, 12, Font.NORMAL);
		// 正文标题1 风格
		Font contextTitle1Font = new Font(bfChinese, 12, Font.NORMAL);
		//表格标题
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		//设置加粗字体
		Font boldFont = new Font(bfChinese, 10, Font.BOLD);
		// 设置纸张的大小
		Document document = new Document(pageSize);
		
		/*	设置标题1格式	*/ 
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1; 
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt1.setStyle(Font.BOLD); 
		rtfGsBt1.setSize(14); 
		/*	设置标题2格式	*/ 
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
	        HeaderFooter header = new HeaderFooter(new Phrase("系统运行维护服务月报                                                                                2018年6月",hfFont), false);   
	        header.setAlignment(Rectangle.ALIGN_LEFT); 
	        header.setBorder(2);
	        header.setPageNumber(2);
	        header.setBottom(20);
	        header.setBorderWidthBottom(5);
	       
	        document.setHeader(header);
	        // 添加页脚   
	        HeaderFooter footer = new HeaderFooter(new Phrase("成都亚光电子股份有限公司\n成都市应急指挥调度无线通信网四期工程项目部\n",hfFont), true); 
	        footer.setAlignment(Rectangle.ALIGN_CENTER);   
	        document.setFooter(footer);   
	      
			/*首页  */
			// 创建段落
			Paragraph p1 = new Paragraph("成都市应急指挥调度无线通信网三期工程", new Font(bfChinese, 20, Font.BOLD));
			Paragraph p2 = new Paragraph("系统运行维护服务月报", new Font(bfChinese, 19, Font.BOLD));
			Paragraph p3 = new Paragraph(time, new Font(bfChinese, 18, Font.BOLD));
			
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
			p1=new Paragraph("拟制_______________", new Font(bfChinese, 18, Font.BOLD));
			p1.setAlignment(Paragraph.ALIGN_CENTER);
			p2=new Paragraph("审核_______________", new Font(bfChinese, 18, Font.BOLD));
			p2.setAlignment(Paragraph.ALIGN_CENTER);
			p2.setSpacingAfter(500);
			document.add(p1);
			document.add(p2);
			Paragraph title=new Paragraph("一 运维内容");
			 //设置标题格式对齐方式  
	        title.setAlignment(Element.ALIGN_LEFT);  
	        title.setFont(rtfGsBt1); 
	        title.setSpacingAfter(6);
	        document.add(title); 
			
			
			Paragraph c=new Paragraph();
			Paragraph tn=new Paragraph();
			tn.setAlignment(Element.ALIGN_CENTER);
			tn.setFont(tableTitleFont);
			tn.setSpacingAfter(3);
			//设置第一行空的列数  
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
			title=new Paragraph("二 系统运行情况");
			 //设置标题格式对齐方式  
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
		    
		    //设置Table表格,创建一个三列的表格  
	        Table table = new Table(6);
	        int width[] = {25,25,25,30,50,20};//设置每列宽度比例  
	        //table.setWidths(width);  
	        table.setWidth(100);//占页面宽度比例  
	        table.setAlignment(1);
	        table.setAlignment(Element.ALIGN_CENTER);//居中  
	        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
	        table.setAutoFillEmptyCells(true);//自动填满  
	        table.setBorderWidth(1);//边框宽度  
	        
	        table.addCell(new Cell(new Paragraph("设备",tableheaderFont)));  
	        table.addCell(new Cell(new Paragraph("CPU占用率",tableheaderFont)));  
	        table.addCell(new Cell(new Paragraph("内存使用率",tableheaderFont)));  
	        table.addCell(new Cell(new Paragraph("硬盘已用/可用(G)",tableheaderFont))); 
	        table.addCell(new Cell(new Paragraph("设备运行时长",tableheaderFont))); 
	        table.addCell(new Cell(new Paragraph("备注",tableheaderFont))); 
	        
	        List<Map<String,Object>> list=ReportDayService.chart_server();
			List<Map<String,Object>> list_one=new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> list_two=new ArrayList<Map<String,Object>>();
			for(int i=0,j=list.size();i<j;i++){
				Map<String,Object> mapa=list.get(i);
				if(mapa.get("ID").equals(1)){
					list_one.add(mapa);
				}else{
					list_two.add(mapa);
				}
			}
			
			for (int i = 0; i < list_one.size(); i++) {
				Map bean =list_one.get(i);
				table.addCell(new Cell(new Paragraph(bean.get("name").toString(),tableheaderFont)));
				table.addCell(new Cell(new Paragraph(bean.get("cpuLoad")+"%",tableheaderFont)));
				table.addCell(new Cell(new Paragraph(bean.get("memPercent")+"%",tableheaderFont)));
				table.addCell(new Cell(new Paragraph(bean.get("diskUsed").toString()+"/"+bean.get("diskSize").toString(),tableheaderFont)));
				table.addCell(new Cell(new Paragraph("")));
				table.addCell(new Cell(new Paragraph("")));
			}
	        
	          
	        document.add(table); 
	        
	        title=new Paragraph("三 话务统计");
	        title.setAlignment(Element.ALIGN_LEFT);  
	        title.setFont(rtfGsBt1); 
	        title.setSpacingAfter(6);
		    document.add(title); 
		    title=new Paragraph("1、系统话务情况");
	        title.setAlignment(Element.ALIGN_LEFT);  
	        title.setFont(rtfGsBt2); 
	        title.setSpacingAfter(6);
	        document.add(title); 
	        
	        String time1="",time2=time,time3="";
	        time1=(Integer.parseInt(time2.split("-")[0])-1)+"-"+time2.split("-")[1];
	        if(time2.split("-")[1].equals("01")){
	        	time3=(Integer.parseInt(time2.split("-")[0])-1)+"-12";	        	
	        }else{
	        	int y=Integer.parseInt(time2.split("-")[1])-1;
	        	time3=time2.split("-")[0]+"-"+(y<10?("0"+y):y);
	        }
	        Map<String,Object> paraMap=new HashMap<String, Object>();
	        paraMap.put("time", time1);paraMap.put("period", period);
	        Map<String,Object> map_last_year_call=ToWorkFileServices.system_call(paraMap);
	        paraMap.put("time", time2);paraMap.put("period", period);
	        Map<String,Object> map_last_month_call=ToWorkFileServices.system_call(paraMap);
	        paraMap.put("time", time3);paraMap.put("period", period);
	        Map<String,Object> map_now_call=ToWorkFileServices.system_call(paraMap);
	        
	        
	        
	        c.clear();
	        Double a1=(double) 0;
	        Double a2=(double) 0;
	        c.add("本月系统有效呼叫总数"+(Double.valueOf(map_now_call.get("TotalActiveCall").toString())/10000)+"万次，");
	       
	        a1=Double.valueOf(map_last_year_call==null?"0":map_last_year_call.get("TotalActiveCall").toString());
	        a2=Double.valueOf(map_now_call.get("TotalActiveCall").toString());
	        c.add("同比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+",");
	        
	        a1=Double.valueOf(map_last_month_call==null?"0":map_last_month_call.get("TotalActiveCall").toString());
	        a2=Double.valueOf(map_now_call.get("TotalActiveCall").toString());	        
	        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+",");
	        c.add("通话总时长"+map_now_call.get("TotalActiveCallDuration")+"分钟，");
	        
	        a1=Double.valueOf(map_last_year_call==null?"0":map_last_year_call.get("TotalActiveCallDuration").toString());
	        a2=Double.valueOf(map_now_call.get("TotalActiveCallDuration").toString());
	        c.add("同比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+",");
	        
	        a1=Double.valueOf(map_last_month_call==null?"0":map_last_month_call.get("TotalActiveCallDuration").toString());
	        a2=Double.valueOf(map_now_call.get("TotalActiveCallDuration").toString());	        
	        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+",");
	        c.add("平均通话时长"+map_now_call.get("AverageCallDuration")+"秒；呼叫成功率"+map_now_call.get("FailedPercentage")+"；");
	        c.add("排队数量"+map_now_call.get("totalQueueCount")+"个，");
	        
	        
	        a1=Double.valueOf(map_last_year_call==null?"0":map_last_year_call.get("AverageCallDuration").toString());
	        a2=Double.valueOf(map_now_call.get("AverageCallDuration").toString());
	        c.add("同比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+",");
	       
	        a1=Double.valueOf(map_last_month_call==null?"0":map_last_month_call.get("AverageCallDuration").toString());
	        a2=Double.valueOf(map_now_call.get("AverageCallDuration").toString());	        
	        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+",");
	        
	        c.add("最大用户注册数"+map_now_call.get("totalMaxReg")+"个，");
	        
	        a1=Double.valueOf(map_last_year_call==null?"0":map_last_year_call.get("totalMaxReg").toString());
	        a2=Double.valueOf(map_now_call.get("totalMaxReg").toString());
	        c.add("同比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+",");
	       
	        a1=Double.valueOf(map_last_month_call==null?"0":map_last_month_call.get("totalMaxReg").toString());
	        a2=Double.valueOf(map_now_call.get("totalMaxReg").toString());	        
	        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+",");
	        
	        c.add("GPS数据"+(Double.valueOf(map_now_call.get("gpsTotals").toString())/10000)+"万条，");
	        a1=Double.valueOf(map_last_year_call==null?"0":map_last_year_call.get("gpsTotals").toString());
	        a2=Double.valueOf(map_now_call.get("gpsTotals").toString());
	        c.add("同比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+",");
	       
	        a1=Double.valueOf(map_last_month_call==null?"0":map_last_month_call.get("gpsTotals").toString());
	        a2=Double.valueOf(map_now_call.get("gpsTotals").toString());	        
	        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+",");
	        
	        document.add(c);
	        tn.clear();
	        tn.add("表二 系统话务统计");
		    document.add(tn);
		    
		    table = new Table(12);
	        table.setWidth(100);//占页面宽度比例  
	        table.setAlignment(1);
	        table.setAlignment(Element.ALIGN_CENTER);//居中  
	        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
	        table.setAutoFillEmptyCells(true);//自动填满  
	        table.setBorderWidth(1);//边框宽度  
		    table.addCell(new Cell(new Paragraph("基站总数",tableheaderFont)));  
	        table.addCell(new Cell(new Paragraph("系统呼叫总数",tableheaderFont)));  
	        table.addCell(new Cell(new Paragraph("系统有效呼叫总数",tableheaderFont)));  
	        table.addCell(new Cell(new Paragraph("系统有效呼叫持续时间（分钟）",tableheaderFont))); 
	        table.addCell(new Cell(new Paragraph("系统平均呼叫持续时间（秒）",tableheaderFont))); 
	        table.addCell(new Cell(new Paragraph("系统未成功呼叫总数",tableheaderFont)));
	        table.addCell(new Cell(new Paragraph("系统呼损总数",tableheaderFont)));
	        table.addCell(new Cell(new Paragraph("系统呼叫成功率",tableheaderFont)));
	        table.addCell(new Cell(new Paragraph("系统排队次数",tableheaderFont)));
	        table.addCell(new Cell(new Paragraph("系统最大用户注册数",tableheaderFont)));
	        table.addCell(new Cell(new Paragraph("系统最大组注册数",tableheaderFont)));
	        table.addCell(new Cell(new Paragraph("系统GPS数据",tableheaderFont)));
	        
	        table.addCell(new Cell(new Paragraph(map_now_call.get("bsTotals").toString(),tableheaderFont)));
	        table.addCell(new Cell(new Paragraph(map_now_call.get("TotalCalls").toString(),tableheaderFont)));
	        table.addCell(new Cell(new Paragraph(map_now_call.get("TotalActiveCall").toString(),tableheaderFont)));
	        table.addCell(new Cell(new Paragraph(map_now_call.get("TotalActiveCallDuration").toString(),tableheaderFont)));
	        table.addCell(new Cell(new Paragraph(FunUtil.second_time(Double.valueOf(map_now_call.get("AverageCallDuration").toString()).intValue()),tableheaderFont)));
	        table.addCell(new Cell(new Paragraph(map_now_call.get("TotalFailedCalls").toString(),tableheaderFont)));
	        table.addCell(new Cell(new Paragraph(map_now_call.get("NoEffectCalls").toString(),tableheaderFont)));
	        table.addCell(new Cell(new Paragraph(map_now_call.get("FailedPercentage").toString(),tableheaderFont)));
	        table.addCell(new Cell(new Paragraph(map_now_call.get("totalQueueCount").toString(),tableheaderFont)));
	        table.addCell(new Cell(new Paragraph(map_now_call.get("totalMaxReg").toString(),tableheaderFont)));
	        table.addCell(new Cell(new Paragraph(map_now_call.get("maxRegGroup").toString(),tableheaderFont)));
	        table.addCell(new Cell(new Paragraph(map_now_call.get("gpsTotals").toString(),tableheaderFont)));
	        
	        document.add(table);
	        List<Map<String,Object>> list1=ToWorkFileServices.
	        		system_call_year(String.valueOf(Integer.parseInt(time.split("-")[0])-2));
	        List<Map<String,Object>> list2=ToWorkFileServices.
	        		system_call_year(String.valueOf(Integer.parseInt(time.split("-")[0])-1));
	        List<Map<String,Object>> list3=ToWorkFileServices.
	        		system_call_year(String.valueOf(Integer.parseInt(time.split("-")[0])));
	        TimeSeries timeSeries1=new TimeSeries(Integer.parseInt(time.split("-")[0])-2+"年系统总通话次数");
	        TimeSeries timeSeries2=new TimeSeries(Integer.parseInt(time.split("-")[0])-1+"年系统总通话次数");
	        TimeSeries timeSeries3=new TimeSeries(Integer.parseInt(time.split("-")[0])+"年系统总通话次数");
	        for (Map<String, Object> map : list1) {
				timeSeries1.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
						Integer.parseInt(time.split("-")[0])),Double.valueOf(map.get("TotalCalls").toString()));
			}
	        for (Map<String, Object> map : list2) {
				timeSeries2.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
						Integer.parseInt(time.split("-")[0])),Double.valueOf(map.get("TotalCalls").toString()));
			}
	        for (Map<String, Object> map : list3) {
				timeSeries3.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
						Integer.parseInt(time.split("-")[0])),Double.valueOf(map.get("TotalCalls").toString()));
			}
	        ChartUtil.ThreeLineImg("系统总通话次数",timeSeries1, timeSeries2, timeSeries3);
	        Image img=Image.getInstance("D:/chart.png");
	        document.add(img);
	        
	        TimeSeries timeSeries4=new TimeSeries(Integer.parseInt(time.split("-")[0])-2+"年系统总通话时长");
	        TimeSeries timeSeries5=new TimeSeries(Integer.parseInt(time.split("-")[0])-1+"年系统总通话时长");
	        TimeSeries timeSeries6=new TimeSeries(Integer.parseInt(time.split("-")[0])+"年系统总通话时长");
	        for (Map<String, Object> map : list1) {
				timeSeries4.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
						Integer.parseInt(time.split("-")[0])),Double.valueOf(map.get("TotalActiveCallDuration").toString()));
			}
	        for (Map<String, Object> map : list2) {
				timeSeries5.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
						Integer.parseInt(time.split("-")[0])),Double.valueOf(map.get("TotalActiveCallDuration").toString()));
			}
	        for (Map<String, Object> map : list3) {
				timeSeries6.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
						Integer.parseInt(time.split("-")[0])),Double.valueOf(map.get("TotalActiveCallDuration").toString()));
			}
	        ChartUtil.ThreeLineImg("系统总通话时长",timeSeries4, timeSeries5, timeSeries6);
		      document.add(img);
	        
	        TimeSeries timeSeries7=new TimeSeries(Integer.parseInt(time.split("-")[0])-2+"年系统排队总次数");
	        TimeSeries timeSeries8=new TimeSeries(Integer.parseInt(time.split("-")[0])-1+"年系统排队总次数");
	        TimeSeries timeSeries9=new TimeSeries(Integer.parseInt(time.split("-")[0])+"年系统排队总次数");
	        for (Map<String, Object> map : list1) {
				timeSeries7.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
						Integer.parseInt(time.split("-")[0])),Double.valueOf(map.get("totalQueueCount").toString()));
			}
	        for (Map<String, Object> map : list2) {
				timeSeries8.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
						Integer.parseInt(time.split("-")[0])),Double.valueOf(map.get("totalQueueCount").toString()));
			}
	        for (Map<String, Object> map : list3) {
				timeSeries9.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
						Integer.parseInt(time.split("-")[0])),Double.valueOf(map.get("totalQueueCount").toString()));
			} 
		      ChartUtil.ThreeLineImg("系统排队总次数", timeSeries7, timeSeries8, timeSeries9);
		      document.add(img);
		     
		      list1=ToWorkFileServices.system_gps_year(String.valueOf(Integer.parseInt(time.split("-")[0])-2));
		      list2=ToWorkFileServices.system_gps_year(String.valueOf(Integer.parseInt(time.split("-")[0])-1));
		      list3=ToWorkFileServices.system_gps_year(String.valueOf(Integer.parseInt(time.split("-")[0])));
		      timeSeries1=new TimeSeries(Integer.parseInt(time.split("-")[0])-2+"年GPS上报");
		      timeSeries2=new TimeSeries(Integer.parseInt(time.split("-")[0])-1+"年GPS上报");
		      timeSeries3=new TimeSeries(Integer.parseInt(time.split("-")[0])+"年GPS上报");
		      
		      for (Map<String, Object> map : list1) {
					timeSeries1.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
					Integer.parseInt(time.split("-")[0])),Double.valueOf(map.get("totals").toString()));
				}
		        for (Map<String, Object> map : list2) {
					timeSeries2.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
							Integer.parseInt(time.split("-")[0])),Double.valueOf(map.get("totals").toString()));
				}
		        for (Map<String, Object> map : list3) {
					timeSeries3.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
							Integer.parseInt(time.split("-")[0])),Double.valueOf(map.get("totals").toString()));
				} 
		        ChartUtil.ThreeLineImg("短数据上报数量",timeSeries1, timeSeries2, timeSeries3);
			      document.add(img);
			     
			      /*基站话务-级别*/
			      c.clear();
			      word_5(time,document,c,rtfGsBt2,title);
			      /*基站话务-区域*/
			      c.clear();
			      word_6(time,document,c,rtfGsBt2,title);
			      /*基站话务-区域top10*/
			      c.clear();
			      word_7(time,document,c,rtfGsBt2,title);
			      c.clear();
			      word_8(time,document,c,rtfGsBt2,title);
			      c.clear();
			      word_9(time,document,c,rtfGsBt2,title);
			      /*用户单位top10*/
			      c.clear();
			      word_10(time,document,c,rtfGsBt2,title);
			      c.clear();
			      word_11(time,document,c,rtfGsBt2,title);
			      c.clear();
			      word_12(time,document,c,rtfGsBt2,title);
			      c.clear();
			      word_13(time,document,c,rtfGsBt2,title);
			      /*4.2巡检*/
			      c.clear();
			      word_14(time,document,c,rtfGsBt2,title);
			      /*4.3故障处理与分析*/
			      c.clear();
			      word_15(time,document,c,rtfGsBt2,title);
			      /*4.3故障处理与分析3.1、特别重大故障*/
			      c.clear();
			      word_16(time,document,c,rtfGsBt2,title);
			      /*4.3故障处理与分析3.2、重大故障*/
			      c.clear();
			      word_17(time,document,c,rtfGsBt2,title);
			      /*4.3故障处理与分析3.3、一般故障*/
			      c.clear();
			      word_18(time,document,c,rtfGsBt2,title);
			      
		      
		    
		    
		   
			
			
			// 关流
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();

		}

	}
	/*基站话务-级别*/
	public static void word_5(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws DocumentException, IOException{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);
		title=new Paragraph("2、基站话务情况");
        title.setAlignment(Element.ALIGN_LEFT);  
        title.setFont(rtfGsBt); 
        title.setSpacingAfter(6);
        document.add(title); 
      
        
        EastBsCallDataBean bean11=new EastBsCallDataBean();
        EastBsCallDataBean bean12=new EastBsCallDataBean();
        EastBsCallDataBean bean13=new EastBsCallDataBean();
        
        EastBsCallDataBean bean21=new EastBsCallDataBean();
        EastBsCallDataBean bean22=new EastBsCallDataBean();
        EastBsCallDataBean bean23=new EastBsCallDataBean();
        
        
        String time2="",time3=time;
        time2=(Integer.parseInt(time3.split("-")[0])-1)+"-"+time3.split("-")[1];
        if(time3.split("-")[1].equals("01")){
        	time2=(Integer.parseInt(time3.split("-")[0])-1)+"-12";	        	
        }else{
        	int y=Integer.parseInt(time3.split("-")[1])-1;
        	time2=time3.split("-")[0]+"-"+(y<10?("0"+y):y);
        }
        
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("time", time2); map.put("period", 4);
        List<EastBsCallDataBean> list2=ToWorkFileServices.system_call_level(map);
        map.put("time", time3); map.put("period", 4);
        List<EastBsCallDataBean> list3=ToWorkFileServices.system_call_level(map);
        for (EastBsCallDataBean eastBsCallDataBean : list2) {
			if(eastBsCallDataBean.getLevel().equals("1")){
				bean11=eastBsCallDataBean;
			}else if(eastBsCallDataBean.getLevel().equals("2")){
				bean12=eastBsCallDataBean;
			}else if(eastBsCallDataBean.getLevel().equals("3")){
				bean13=eastBsCallDataBean;
			}
		} 
        for (EastBsCallDataBean eastBsCallDataBean : list3) {
			if(eastBsCallDataBean.getLevel().equals("1")){
				bean21=eastBsCallDataBean;
			}else if(eastBsCallDataBean.getLevel().equals("2")){
				bean22=eastBsCallDataBean;
			}else if(eastBsCallDataBean.getLevel().equals("3")){
				bean23=eastBsCallDataBean;
			}
		} 
        Double a1=(double) 0;
        Double a2=(double) 0;
        
        c.add("一级基站本月总通话"+(Double.valueOf(bean21.getTotalActiveCalls())/10000)+"万次，");
        a1=Double.valueOf(bean11.getTotalActiveCalls());
        a2=Double.valueOf(bean21.getTotalActiveCalls());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+",");

        c.add("通话总时长"+bean21.getTotalActiveCallDuration()+"分钟，");
        a1=Double.valueOf(bean11.getTotalActiveCallDuration());
        a2=Double.valueOf(bean21.getTotalActiveCallDuration());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";");

        c.add("平均通话时长"+bean21.getAverageCallDuration()+"秒；");
        
        c.add("排队数量"+bean21.getQueueCount()+"个，");
        a1=Double.valueOf(bean11.getQueueCount());
        a2=Double.valueOf(bean21.getQueueCount());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";\n");

        c.add("二级基站本月总通话"+(Double.valueOf(bean22.getTotalActiveCalls())/10000)+"万次，");
        a1=Double.valueOf(bean12.getTotalActiveCalls());
        a2=Double.valueOf(bean22.getTotalActiveCalls());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";");

        c.add("通话总时长"+bean22.getTotalActiveCallDuration()+"分钟，");
        a1=Double.valueOf(bean12.getTotalActiveCallDuration());
        a2=Double.valueOf(bean22.getTotalActiveCallDuration());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";");

        c.add("平均通话时长"+bean22.getAverageCallDuration()+"秒；排队数量"+bean22.getQueueCount()+"个，");
        a1=Double.valueOf(bean12.getQueueCount());
        a2=Double.valueOf(bean22.getQueueCount());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";\n");

        c.add("三级基站本月总通话"+(Double.valueOf(bean23.getTotalActiveCalls())/10000)+"万次，");
        a1=Double.valueOf(bean13.getTotalActiveCalls());
        a2=Double.valueOf(bean23.getTotalActiveCalls());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";");

        c.add("通话总时长"+bean23.getTotalActiveCallDuration()+"分钟，");
        a1=Double.valueOf(bean13.getTotalActiveCallDuration());
        a2=Double.valueOf(bean23.getTotalActiveCallDuration());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";");

        c.add("平均通话时长"+bean23.getAverageCallDuration()+"秒；排队数量"+bean23.getQueueCount()+"个,");
        a1=Double.valueOf(bean13.getQueueCount());
        a2=Double.valueOf(bean23.getQueueCount());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";\n");

        document.add(c);
        //设置Table表格,创建一个三列的表格  
        Paragraph tn=new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表三  一、二、三级基站话务统计");
	    document.add(tn);
		
        Table table = new Table(9);
        int width[] = {25,25,25,30,50,20};//设置每列宽度比例  
        //table.setWidths(width);  
        table.setWidth(100);//占页面宽度比例  
        table.setAlignment(1);
        table.setAlignment(Element.ALIGN_CENTER);//居中  
        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
        table.setAutoFillEmptyCells(true);//自动填满  
        table.setBorderWidth(1);//边框宽度 
        table.setWidth(100);//占页面宽度比例  
        table.setAlignment(1);
        table.setAlignment(Element.ALIGN_CENTER);//居中  
        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
        table.setAutoFillEmptyCells(true);//自动填满  
        table.setBorderWidth(1);//边框宽度  
	    table.addCell(new Cell(new Paragraph("基站等级",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站数量",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站总呼叫次数",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站月平均呼叫次数",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站有效呼叫持续时间（分钟）",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站月平均通话时长（分钟）",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站用户排队数量",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站最大用户注册数",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站最大组注册数",tableheaderFont)));
        
        table.addCell(new Cell(new Paragraph("一级基站",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(bean21.getBsTotals()),tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(bean21.getTotalActiveCalls()),tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double.valueOf(bean21.getTotalActiveCalls())/bean21.getBsTotals())),tableheaderFont))); 
        table.addCell(new Cell(new Paragraph(String.valueOf(bean21.getTotalActiveCallDuration()),tableheaderFont))); 
        table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double.valueOf(bean21.getTotalActiveCallDuration()))/bean21.getBsTotals()),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean21.getQueueCount()),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean21.getMaxUserRegCount()),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean21.getMaxGroupRegCount()),tableheaderFont)));
        
        table.addCell(new Cell(new Paragraph("二级基站",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(bean22.getBsTotals()),tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double.valueOf(bean22.getTotalActiveCalls())/bean22.getBsTotals())),tableheaderFont))); 
        table.addCell(new Cell(new Paragraph(String.valueOf(bean22.getTotalActiveCallDuration()),tableheaderFont))); 
        table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double.valueOf(bean22.getTotalActiveCallDuration()))/bean22.getBsTotals()),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean22.getAverageCallDuration()/bean21.getBsTotals()),tableheaderFont)));
        
        table.addCell(new Cell(new Paragraph(String.valueOf(bean22.getQueueCount()),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean22.getMaxUserRegCount()),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean22.getMaxGroupRegCount()),tableheaderFont)));
        
        table.addCell(new Cell(new Paragraph("三级基站",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(bean23.getBsTotals()),tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(bean23.getTotalActiveCalls()),tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double.valueOf(bean23.getTotalActiveCalls())/bean23.getBsTotals())),tableheaderFont))); 
        table.addCell(new Cell(new Paragraph(String.valueOf(bean23.getTotalActiveCallDuration()),tableheaderFont))); 
        table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double.valueOf(bean23.getTotalActiveCallDuration()))/bean23.getBsTotals()),tableheaderFont)));
        
        table.addCell(new Cell(new Paragraph(String.valueOf(bean23.getQueueCount()),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean23.getMaxUserRegCount()),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean23.getMaxGroupRegCount()),tableheaderFont)));
              
        document.add(table);
        
        if(Integer.parseInt(time.split("-")[1])>6){
        	time2=time.split("-")[0]+"-07";
        	time3=(Integer.parseInt(time.split("-")[0])+1)+"-06";
            
        }else{
        	time2=(Integer.parseInt(time.split("-")[0])-1)+"-07";
            time3=time.split("-")[0]+"-06";
        }
        map.put("starttime", time2);
        map.put("endtime", time3);
        list2=ToWorkFileServices.system_call_year_level(map);
        
        
        DefaultCategoryDataset dcd=new DefaultCategoryDataset();
        
        for (EastBsCallDataBean bean : list2) {
        	if(bean.getLevel().equals("1")){
        		dcd.addValue(Math.round(Double.valueOf(bean.getTotalActiveCalls())/bean.getBsTotals()), "一级基站平均呼叫次数", bean.getStarttime());
        	}else if(bean.getLevel().equals("2")){
        		dcd.addValue(Math.round(Double.valueOf(bean.getTotalActiveCalls())/bean.getBsTotals()), "二级基站平均呼叫次数", bean.getStarttime());
        	} else if(bean.getLevel().equals("3")){
        		dcd.addValue(Math.round(Double.valueOf(bean.getTotalActiveCalls())/bean.getBsTotals()), "三级基站平均呼叫次数", bean.getStarttime());
        	}
			
		}
	    
	        try {
				ChartUtil.LineImg("一、二、三级基站平均呼叫次数趋势图", dcd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Image img=Image.getInstance("D:/chart.png");
		    document.add(img);
        
        
        
        
	
	}
	/*基站话务-区域*/
	public static void word_6(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws DocumentException, IOException{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

        EastBsCallDataBean bean11=new EastBsCallDataBean();
        EastBsCallDataBean bean12=new EastBsCallDataBean();
        
        EastBsCallDataBean bean21=new EastBsCallDataBean();
        EastBsCallDataBean bean22=new EastBsCallDataBean();
        
        
        String time2="",time3=time;
        time2=(Integer.parseInt(time3.split("-")[0])-1)+"-"+time3.split("-")[1];
        if(time3.split("-")[1].equals("01")){
        	time2=(Integer.parseInt(time3.split("-")[0])-1)+"-12";	        	
        }else{
        	int y=Integer.parseInt(time3.split("-")[1])-1;
        	time2=time3.split("-")[0]+"-"+(y<10?("0"+y):y);
        }
        
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("time", time2); map.put("period", 4);
        List<EastBsCallDataBean> list2=ToWorkFileServices.system_call_area(map);
        map.put("time", time3); map.put("period", 4);
        List<EastBsCallDataBean> list3=ToWorkFileServices.system_call_area(map);
        for (EastBsCallDataBean eastBsCallDataBean : list2) {
			if(eastBsCallDataBean.getArea().equals("绕城内")){
				bean11=eastBsCallDataBean;
			}else if(eastBsCallDataBean.getArea().equals("绕城外")){
				bean12=eastBsCallDataBean;
			}
		} 
        for (EastBsCallDataBean eastBsCallDataBean : list3) {
			if(eastBsCallDataBean.getArea().equals("绕城内")){
				bean21=eastBsCallDataBean;
			}else if(eastBsCallDataBean.getArea().equals("绕城外")){
				bean22=eastBsCallDataBean;
			}
		} 
        Double a1=(double) 0;
        Double a2=(double) 0;
        
        
        //设置Table表格,创建一个三列的表格  
        Paragraph tn=new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表四 绕城内外基站话务统计");
	    document.add(tn);
		
        Table table = new Table(9);
        int width[] = {25,25,25,30,50,20};//设置每列宽度比例  
        //table.setWidths(width);  
        table.setWidth(100);//占页面宽度比例  
        table.setAlignment(1);
        table.setAlignment(Element.ALIGN_CENTER);//居中  
        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
        table.setAutoFillEmptyCells(true);//自动填满  
        table.setBorderWidth(1);//边框宽度 
        table.setWidth(100);//占页面宽度比例  
        table.setAlignment(1);
        table.setAlignment(Element.ALIGN_CENTER);//居中  
        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
        table.setAutoFillEmptyCells(true);//自动填满  
        table.setBorderWidth(1);//边框宽度  
	    table.addCell(new Cell(new Paragraph("基站等级",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站数量",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站总呼叫次数",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站月平均呼叫次数",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站有效呼叫持续时间（分钟）",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站月平均通话时长（分钟）",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站用户排队数量",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站最大用户注册数",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站最大组注册数",tableheaderFont)));
        
        table.addCell(new Cell(new Paragraph("绕城内",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(bean21.getBsTotals()),tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(bean21.getTotalActiveCalls()),tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double.valueOf(bean21.getTotalActiveCalls())/bean21.getBsTotals())),tableheaderFont)));         
        table.addCell(new Cell(new Paragraph(String.valueOf(bean21.getTotalActiveCallDuration()),tableheaderFont))); 
        table.addCell(new Cell(new Paragraph( String.valueOf(Math.round(Double.valueOf(bean21.getTotalActiveCallDuration())/bean21.getBsTotals())),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean21.getQueueCount()),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean21.getMaxUserRegCount()),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean21.getMaxGroupRegCount()),tableheaderFont)));
        
        table.addCell(new Cell(new Paragraph("绕城外",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(bean22.getBsTotals()),tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(bean22.getTotalActiveCalls()),tableheaderFont)));  
        table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double.valueOf(bean22.getTotalActiveCalls())/bean22.getBsTotals())),tableheaderFont)));         
        table.addCell(new Cell(new Paragraph(String.valueOf(bean22.getTotalActiveCallDuration()),tableheaderFont))); 
        table.addCell(new Cell(new Paragraph( String.valueOf(Math.round(Double.valueOf(bean22.getTotalActiveCallDuration())/bean22.getBsTotals())),tableheaderFont)));
        
        table.addCell(new Cell(new Paragraph(String.valueOf(bean22.getQueueCount()),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean22.getMaxUserRegCount()),tableheaderFont)));
        table.addCell(new Cell(new Paragraph(String.valueOf(bean22.getMaxGroupRegCount()),tableheaderFont)));
              
        document.add(table);
        
        
        if(Integer.parseInt(time.split("-")[1])>6){
        	time2=time.split("-")[0]+"-07";
        	time3=(Integer.parseInt(time.split("-")[0])+1)+"-06";
            
        }else{
        	time2=(Integer.parseInt(time.split("-")[0])-1)+"-07";
            time3=time.split("-")[0]+"-06";
        }
        map.put("starttime", time2);
        map.put("endtime", time3);
        list2=ToWorkFileServices.system_call_year_area(map);
        
        
        DefaultCategoryDataset dcd=new DefaultCategoryDataset();
        
        for (EastBsCallDataBean bean : list2) {
        	if(bean.getArea().equals("绕城内")){
        		dcd.addValue(Math.round(Double.valueOf(bean.getTotalActiveCalls())/bean.getBsTotals()), "绕城内基站平均呼叫次数", bean.getStarttime());
        	}else if(bean.getArea().equals("绕城外")){
        		dcd.addValue(Math.round(Double.valueOf(bean.getTotalActiveCalls())/bean.getBsTotals()), "绕城外基站平均呼叫次数", bean.getStarttime());
        	}
			
		}
	    
	        try {
				ChartUtil.LineImg("绕城内/外基站平均呼叫次数趋势图", dcd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Image img=Image.getInstance("D:/chart.png");
		    document.add(img);
        
        
        
        c.add("绕城内基站本月总通话"+(Double.valueOf(bean21.getTotalActiveCalls())/10000)+"万次，");
        a1=Double.valueOf(bean11.getTotalActiveCalls());
        a2=Double.valueOf(bean21.getTotalActiveCalls());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+",");

        c.add("通话总时长"+bean21.getTotalActiveCallDuration()+"分钟，");
        a1=Double.valueOf(bean11.getTotalActiveCallDuration());
        a2=Double.valueOf(bean21.getTotalActiveCallDuration());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";");

        c.add("平均通话时长"+bean21.getAverageCallDuration()+"秒；");
        
        c.add("排队数量"+bean21.getQueueCount()+"个，");
        a1=Double.valueOf(bean11.getQueueCount());
        a2=Double.valueOf(bean21.getQueueCount());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";\n");

        c.add("绕城外基站本月总通话"+(Double.valueOf(bean22.getTotalActiveCalls())/10000)+"万次，");
        a1=Double.valueOf(bean12.getTotalActiveCalls());
        a2=Double.valueOf(bean22.getTotalActiveCalls());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";");

        c.add("通话总时长"+bean22.getTotalActiveCallDuration()+"分钟，");
        a1=Double.valueOf(bean12.getTotalActiveCallDuration());
        a2=Double.valueOf(bean22.getTotalActiveCallDuration());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";");

        c.add("平均通话时长"+bean22.getAverageCallDuration()+"秒；排队数量"+bean22.getQueueCount()+"个，");
        a1=Double.valueOf(bean12.getQueueCount());
        a2=Double.valueOf(bean22.getQueueCount());
        c.add("环比"+(a1<a2?"增加"+String.format("%.2f", (a2-a1)/a1*100)+"%":"减少"+String.format("%.2f", (a1-a2)/a1*100)+"%")+";\n");

 
        document.add(c);
	
	}
	/*基站话务-区域-top10*/
	public static void word_7(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws Exception{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

        EastBsCallDataBean bean=new EastBsCallDataBean();

        List<EastBsCallDataBean> list=ToWorkFileServices.system_call_zone_top10(time);
        DefaultPieDataset dpd=new DefaultPieDataset();
       for (EastBsCallDataBean eastBsCallDataBean : list) {
    	   dpd.setValue(eastBsCallDataBean.getZone(), eastBsCallDataBean.getTotalActiveCalls());
	   }
       ChartUtil.PieImg("各行政区域基站话务占比", dpd,2);
       Image img=Image.getInstance("D:/chart.png");
       document.add(img);
       
        //设置Table表格,创建一个三列的表格  
        Paragraph tn=new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表五 各行政区域基站话务统计TOP10");
	    document.add(tn);
		
        Table table = new Table(9);
        int width[] = {25,25,25,30,50,20};//设置每列宽度比例  
        //table.setWidths(width);  
        table.setWidth(100);//占页面宽度比例  
        table.setAlignment(1);
        table.setAlignment(Element.ALIGN_CENTER);//居中  
        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
        table.setAutoFillEmptyCells(true);//自动填满  
        table.setBorderWidth(1);//边框宽度 
        table.setWidth(100);//占页面宽度比例  
        table.setAlignment(1);
        table.setAlignment(Element.ALIGN_CENTER);//居中  
        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
        table.setAutoFillEmptyCells(true);//自动填满  
        table.setBorderWidth(1);//边框宽度  
        table.addCell(new Cell(new Paragraph("话务量排名",tableheaderFont))); 
	    table.addCell(new Cell(new Paragraph("行政区域",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站数量",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站总呼叫次数",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站有效呼叫持续时间（分钟）",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站月平均通话时长（分钟）",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站用户排队数量",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站最大用户注册数",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站最大组注册数",tableheaderFont)));
        int i=1;
        for (EastBsCallDataBean eastBsCallDataBean : list) {
        	 table.addCell(new Cell(new Paragraph(String.valueOf(i++),tableheaderFont))); 
     	     table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getZone(),tableheaderFont)));  
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getBsTotals()),tableheaderFont)));  
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getTotalActiveCalls()),tableheaderFont))); 
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getAverageCallDuration()),tableheaderFont))); 
             table.addCell(new Cell(new Paragraph(String.valueOf(Math.round(Double.valueOf(eastBsCallDataBean.getTotalActiveCalls())/eastBsCallDataBean.getBsTotals())),tableheaderFont)));         
           
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getQueueCount()),tableheaderFont)));
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getMaxUserRegCount()),tableheaderFont)));
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getMaxGroupRegCount()),tableheaderFont)));
 	   }        
        document.add(table);
	
	}
	/*基站话务-基站-top10*/
	public static void word_8(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws Exception{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

        EastBsCallDataBean bean=new EastBsCallDataBean();

        List<EastBsCallDataBean> list=ToWorkFileServices.system_call_bs_top10(time);
        Map<String,Object> paraMap=new HashMap<String, Object>();
        paraMap.put("time", time);paraMap.put("period", 4);
        Map<String,Object> all_call=ToWorkFileServices.system_bs_call(time);
        //设置Table表格,创建一个三列的表格  
        Paragraph tn=new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表六 基站话务统计TOP10");
	    document.add(tn);
		
        Table table = new Table(11);
        int width[] = {25,25,25,30,50,20};//设置每列宽度比例  
        //table.setWidths(width);  
        table.setWidth(100);//占页面宽度比例  
        table.setAlignment(1);
        table.setAlignment(Element.ALIGN_CENTER);//居中  
        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
        table.setAutoFillEmptyCells(true);//自动填满  
        table.setBorderWidth(1);//边框宽度 
        table.setWidth(100);//占页面宽度比例  
        table.setAlignment(1);
        table.setAlignment(Element.ALIGN_CENTER);//居中  
        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
        table.setAutoFillEmptyCells(true);//自动填满  
        table.setBorderWidth(1);//边框宽度  
        table.addCell(new Cell(new Paragraph("基站话务量排序",tableheaderFont))); 
	    table.addCell(new Cell(new Paragraph("基站ID",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站名称",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站分级",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("行政区域",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站总呼叫次数",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站呼叫持续时间（分钟）",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站平均呼叫持续时间",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站用户排队数量",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站最大用户注册数",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站最大组注册数",tableheaderFont)));
        int i=1;
        for (EastBsCallDataBean eastBsCallDataBean : list) {
        	 table.addCell(new Cell(new Paragraph(String.valueOf(i++),tableheaderFont))); 
        	 table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getBsid()),tableheaderFont))); 
     	     table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getName(),tableheaderFont)));
     	     table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getLevel(),tableheaderFont)));
     	     table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getZone(),tableheaderFont)));  
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getTotalActiveCalls()),tableheaderFont))); 
             table.addCell(new Cell(new Paragraph(FunUtil.second_time(eastBsCallDataBean.getTotalActiveCallDuration()),tableheaderFont)));
             table.addCell(new Cell(new Paragraph(FunUtil.second_time(eastBsCallDataBean.getAverageCallDuration()),tableheaderFont)));
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getQueueCount()),tableheaderFont)));
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getMaxUserRegCount()),tableheaderFont)));
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getMaxGroupRegCount()),tableheaderFont)));
 	   }        
        document.add(table);
        
        
        DefaultPieDataset dpd=new DefaultPieDataset();
        StringBuilder str=new StringBuilder();
        int a=0;
       for (EastBsCallDataBean eastBsCallDataBean : list) {
    	   dpd.setValue(eastBsCallDataBean.getName(), eastBsCallDataBean.getTotalActiveCalls());
    	   str.append(eastBsCallDataBean.getBsid()+"-"+eastBsCallDataBean.getName()+"、");
    	   a+=eastBsCallDataBean.getTotalActiveCalls();
    	   
	   }
       ChartUtil.PieImg("基站话务占比 TOP10", dpd,2);
       Image img=Image.getInstance("D:/chart.png");
       document.add(img);
       c.add("由上图可知，本月\n");
       c.add(str.toString());
       c.add("10个基站的基站有效呼叫总数超过");
       c.add(String.valueOf(a/10000)+"万");
       c.add("次，占全网基站话务量的"+String.format("%.2f", (Double.valueOf(a)/Integer.parseInt(all_call.get("TotalCalls").toString())*100))+"%。\n");
      document.add(c);
	}
	
	/*基站话务-排队-top10*/
	public static void word_9(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws Exception{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

        EastBsCallDataBean bean=new EastBsCallDataBean();

        List<EastBsCallDataBean> list=ToWorkFileServices.system_call_queue_top10(time);
        Map<String,Object> paraMap=new HashMap<String, Object>();
        paraMap.put("time", time);paraMap.put("period", 4);
        Map<String,Object>all_call=ToWorkFileServices.system_bs_call(time);
        //设置Table表格,创建一个三列的表格  
        Paragraph tn=new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表七 基站排队话务统计TOP10");
	    document.add(tn);
		
        Table table = new Table(11);
        int width[] = {25,25,25,30,50,20};//设置每列宽度比例  
        //table.setWidths(width);  
        table.setWidth(100);//占页面宽度比例  
        table.setAlignment(1);
        table.setAlignment(Element.ALIGN_CENTER);//居中  
        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
        table.setAutoFillEmptyCells(true);//自动填满  
        table.setBorderWidth(1);//边框宽度 
        table.setWidth(100);//占页面宽度比例  
        table.setAlignment(1);
        table.setAlignment(Element.ALIGN_CENTER);//居中  
        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
        table.setAutoFillEmptyCells(true);//自动填满  
        table.setBorderWidth(1);//边框宽度  
        table.addCell(new Cell(new Paragraph("基站排队排名",tableheaderFont))); 
	    table.addCell(new Cell(new Paragraph("基站ID",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站名称",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站分级",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("行政区域",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站总呼叫次数",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("基站呼叫持续时间（分钟）",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站平均呼叫持续时间",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("基站持续排队时间",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站最大用户注册数",tableheaderFont)));
        table.addCell(new Cell(new Paragraph("基站最大组注册数",tableheaderFont)));
        int i=1;
        for (EastBsCallDataBean eastBsCallDataBean : list) {
        	 table.addCell(new Cell(new Paragraph(String.valueOf(i++),tableheaderFont))); 
        	 table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getBsid()),tableheaderFont))); 
     	     table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getName(),tableheaderFont)));
     	     table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getLevel(),tableheaderFont)));
     	     table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getZone(),tableheaderFont)));  
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getTotalActiveCalls()),tableheaderFont))); 
             table.addCell(new Cell(new Paragraph(FunUtil.second_time(eastBsCallDataBean.getTotalActiveCallDuration()),tableheaderFont)));
             table.addCell(new Cell(new Paragraph(FunUtil.second_time(eastBsCallDataBean.getAverageCallDuration()),tableheaderFont)));
             table.addCell(new Cell(new Paragraph(FunUtil.second_time(eastBsCallDataBean.getQueueDuration()),tableheaderFont)));
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getMaxUserRegCount()),tableheaderFont)));
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getMaxGroupRegCount()),tableheaderFont)));
 	   }        
        document.add(table);
        
        
        DefaultPieDataset dpd=new DefaultPieDataset();
        StringBuilder str=new StringBuilder();
        int a=0;
       for (EastBsCallDataBean eastBsCallDataBean : list) {
    	   dpd.setValue(eastBsCallDataBean.getName(), eastBsCallDataBean.getTotalActiveCalls());
    	   str.append(eastBsCallDataBean.getBsid()+"-"+eastBsCallDataBean.getName()+"、");
    	   a+=eastBsCallDataBean.getQueueCount();
    	   
	   }
       ChartUtil.PieImg("基站排队占比 TOP10", dpd,2);
       Image img=Image.getInstance("D:/chart.png");
       document.add(img);
       c.add("由上图可知，本月\n");
       c.add(str.toString());
       c.add("10个基站");
       c.add("占全网排队数量的"+String.format("%.2f", (Double.valueOf(a)/Integer.parseInt(all_call.get("QueueCount").toString())*100))+"%。\n");
      document.add(c);
	}
	/*基站话务-用户单位-top10*/
	public static void word_10(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws Exception{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

        EastBsCallDataBean bean=new EastBsCallDataBean();

        List<EastVpnCallBean> list=ToWorkFileServices.system_call_vpn_top10(time);
        Map<String,Object> paraMap=new HashMap<String, Object>();
        paraMap.put("time", time);paraMap.put("period", 4);
        Map<String,Object>all_call=ToWorkFileServices.system_call(paraMap);
        //设置Table表格,创建一个三列的表格  
        Paragraph tn=new Paragraph();
		tn.setAlignment(Element.ALIGN_CENTER);
		tn.setFont(tableTitleFont);
		tn.setSpacingAfter(1);
		tn.add("表八 用户单位话务统计TOP10");
	    document.add(tn);
		
        Table table = new Table(6);
        int width[] = {25,25,25,30,50,20};//设置每列宽度比例  
        //table.setWidths(width);  
        table.setWidth(100);//占页面宽度比例  
        table.setAlignment(1);
        table.setAlignment(Element.ALIGN_CENTER);//居中  
        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
        table.setAutoFillEmptyCells(true);//自动填满  
        table.setBorderWidth(1);//边框宽度 
        table.setWidth(100);//占页面宽度比例  
        table.setAlignment(1);
        table.setAlignment(Element.ALIGN_CENTER);//居中  
        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
        table.setAutoFillEmptyCells(true);//自动填满  
        table.setBorderWidth(1);//边框宽度  
        table.addCell(new Cell(new Paragraph("序号",tableheaderFont))); 
	    table.addCell(new Cell(new Paragraph("单位名称",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("用户单位有效呼叫总数",tableheaderFont)));  
        table.addCell(new Cell(new Paragraph("用户单位有效呼叫总持续时间",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("用户单位平均呼叫持续时间",tableheaderFont))); 
        table.addCell(new Cell(new Paragraph("用户单位呼叫成功率",tableheaderFont)));
        int i=1;
        for (EastVpnCallBean eastBsCallDataBean : list) {
        	 table.addCell(new Cell(new Paragraph(String.valueOf(i++),tableheaderFont))); 
     	     table.addCell(new Cell(new Paragraph(eastBsCallDataBean.getName(),tableheaderFont))); 
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getTotalActiveCalls()),tableheaderFont))); 
             table.addCell(new Cell(new Paragraph(FunUtil.second_time(eastBsCallDataBean.getTotalActiveCallDuration()),tableheaderFont)));
             table.addCell(new Cell(new Paragraph(FunUtil.second_time(eastBsCallDataBean.getAverageCallDuration()),tableheaderFont)));
             table.addCell(new Cell(new Paragraph(String.valueOf(eastBsCallDataBean.getPercent()+"%"),tableheaderFont)));
 	   }        
        document.add(table);
        
        
        DefaultPieDataset dpd=new DefaultPieDataset();
        StringBuilder str=new StringBuilder();
        int a=0;
       for (EastVpnCallBean eastBsCallDataBean : list) {
    	   dpd.setValue(eastBsCallDataBean.getName(), eastBsCallDataBean.getTotalActiveCalls());
    	   str.append(eastBsCallDataBean.getName()+"、");
    	   a+=eastBsCallDataBean.getTotalActiveCalls();
    	   
	   }
       ChartUtil.PieImg("用户单位话务统计 TOP10", dpd,0);
       Image img=Image.getInstance("D:/chart.png");
       document.add(img);
      
      String time1="";
      if(time.split("-")[1].equals("01")){
      	time1=(Integer.parseInt(time.split("-")[0])-1)+"-12";	        	
      }else{
      	int y=Integer.parseInt(time.split("-")[1])-1;
      	time1=time.split("-")[0]+"-"+(y<10?("0"+y):y);
      }
      List<EastVpnCallBean> list2=ToWorkFileServices.system_call_vpn_top10(time1);
      DefaultCategoryDataset dct=new DefaultCategoryDataset();
      for (EastVpnCallBean eastVpnCallBean : list2) {
		dct.addValue(eastVpnCallBean.getTotalActiveCalls(), time1,eastVpnCallBean.getName());
	  }
      for (EastVpnCallBean eastVpnCallBean : list) {
  		dct.addValue(eastVpnCallBean.getTotalActiveCalls(), time,eastVpnCallBean.getName());
  	}
      ChartUtil.BarImg("用户单位话务统计 TOP10", dct);
      document.add(img);

      c.add("由上图可知，本月\n");
      c.add(str.toString());
      c.add("10家用户单位使用成都应急通信网的频率最高");
      c.add("话务量占全网的"+String.format("%.2f", (Double.valueOf(a)/Integer.parseInt(all_call.get("TotalCalls").toString())*100))+"%。\n");
     document.add(c);
	}
	/*基站话务-排队-top10*/
	public static void word_11(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws Exception{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);

        EastBsCallDataBean bean=new EastBsCallDataBean();

        List<EastBsCallDataBean> list=ToWorkFileServices.chart_bs_userreg_top10(time);
        Map<String,Object> paraMap=new HashMap<String, Object>();
        paraMap.put("time", time);paraMap.put("period", 4);
        Map<String,Object>all_call=ToWorkFileServices.system_bs_call(time);
     
		
        
        DefaultCategoryDataset dpd=new DefaultCategoryDataset();
        StringBuilder str=new StringBuilder();
        int a=0;
       for (EastBsCallDataBean eastBsCallDataBean : list) {
    	   dpd.setValue(eastBsCallDataBean.getMaxUserRegCount(), eastBsCallDataBean.getStarttime(), eastBsCallDataBean.getName());
    	   
	   }
       if(list.size()>0){
    	   str.append("基站最大用户注册数为"+(list.get(0).getMaxUserRegCount())+"，出现在"+list.get(0).getBsid()+"-"+list.get(0).getName());
       }
       ChartUtil.BarImg("基站最大注册用户数前十", dpd);
       Image img=Image.getInstance("D:/chart.png");
       document.add(img);
       c.add(str.toString());
       document.add(c);
	}
	/*四 基础运维工作*/
	public static void word_12(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws Exception{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);
		
		/*	设置标题1格式	*/ 
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1; 
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt1.setStyle(Font.BOLD); 
		rtfGsBt1.setSize(14); 
		/*	设置标题2格式	*/ 
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2; 
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt2.setStyle(Font.NORMAL); 
		rtfGsBt2.setSize(12); 
		
		
		 title=new Paragraph("四 基础运维工作");
	     title.setAlignment(Element.ALIGN_LEFT);  
	     title.setFont(rtfGsBt1); 
	     title.setSpacingAfter(6);
		 document.add(title); 
		 StringBuilder str=new StringBuilder();
		 str.append("   项目部运维严格遵守《成都应急网运维管理制度》，其中包含《交换中心机房管理制度》、《值班与交接班制度》、《保密制度》、《维护作业计");
		 str.append("划管理制度》、《仪器仪表及备品备件管理制度》、《通信故障管理制度》、《巡检制度》、《维护资项目部料管理制度》；切实按照《成都应急网应急通信保障预案》响应应急通信保障。");
		 c.add(str.toString());
		 document.add(c);
		 c.clear();
		 str=new StringBuilder();
		 str.append("   系统基础运维工作主要包括交换中心监控，巡检，故障抢修，基站、交换中心或传输优化整改，"
		 		+ "用户投诉响应，培训与技术支持服务，应急演练，应急通信保障等。");
		 c.add(str.toString());
		 document.add(c);		 
	}
	/*4.1交换中心*/
	public static void word_13(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws Exception{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);
		
		/*	设置标题1格式	*/ 
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1; 
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt1.setStyle(Font.BOLD); 
		rtfGsBt1.setSize(14); 
		/*	设置标题2格式	*/ 
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2; 
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt2.setStyle(Font.NORMAL); 
		rtfGsBt2.setSize(12); 
		
		
		 title=new Paragraph("1 交换中心监控");
	     title.setAlignment(Element.ALIGN_LEFT);  
	     title.setFont(rtfGsBt2); 
	     title.setSpacingAfter(6);
		 document.add(title); 
		 
		 StringBuilder str=new StringBuilder();
		 str.append("  本月交换中心运行正常，完成7×24小时系统监控并填写了31份系统日常维护表。"
		 		+ "本月后台监控发现隐患25起，24起处理完成均为现场处理，"
		 		+ "1起遗留至下月处理。详见3.4、隐患故障。");
		 c.add(str.toString());
		 document.add(c);
		 
		 //设置Table表格,创建一个三列的表格  
	        Paragraph tn=new Paragraph();
			tn.setAlignment(Element.ALIGN_CENTER);
			tn.setFont(tableTitleFont);
			tn.setSpacingAfter(1);
			tn.add("表九 后台监控反馈表");
		    document.add(tn);
		    Table table = new Table(6);
	        int width[] = {25,25,25,30,50,20};//设置每列宽度比例  
	        //table.setWidths(width);  
	        table.setWidth(100);//占页面宽度比例  
	        table.setAlignment(1);
	        table.setAlignment(Element.ALIGN_CENTER);//居中  
	        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
	        table.setAutoFillEmptyCells(true);//自动填满  
	        table.setBorderWidth(1);//边框宽度 
	        table.setWidth(100);//占页面宽度比例  
	        table.setAlignment(1);
	        table.setAlignment(Element.ALIGN_CENTER);//居中  
	        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
	        table.setAutoFillEmptyCells(true);//自动填满  
	        table.setBorderWidth(1);//边框宽度  
	        Cell cell;
	        cell=new Cell(new Paragraph("7×24小时系统监控",tableheaderFont));
	        cell.setRowspan(2);
		    table.addCell(cell); 
		    table.addCell(new Cell(new Paragraph("应填写系统日常维护表（份）",tableheaderFont))); 
		    table.addCell(new Cell(new Paragraph("实际填写系统日常维护表（份）",tableheaderFont))); 
		    table.addCell(new Cell(new Paragraph("后台监控发现隐患数量",tableheaderFont))); 
		    table.addCell(new Cell(new Paragraph("隐患处理数量",tableheaderFont))); 
		    table.addCell(new Cell(new Paragraph("备注",tableheaderFont))); 
		    document.add(table);
	}
	/*4.2巡检*/
	public static void word_14(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws Exception{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);
		
		/*	设置标题1格式	*/ 
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1; 
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt1.setStyle(Font.BOLD); 
		rtfGsBt1.setSize(14); 
		/*	设置标题2格式	*/ 
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2; 
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt2.setStyle(Font.NORMAL); 
		rtfGsBt2.setSize(12); 
		
		
		 title=new Paragraph("2 巡检");
	     title.setAlignment(Element.ALIGN_LEFT);  
	     title.setFont(rtfGsBt2); 
	     title.setSpacingBefore(6);
	     title.setSpacingAfter(6);
		 document.add(title); 	
		 
		 Map<String,Object> map=new HashMap<String, Object>();
		 map=ToWorkFileServices.xj_bs_all_type_num(4);
		 
		 
		 StringBuilder str=new StringBuilder();
		 str.append("全网"+(map.get("bs")==null?String.valueOf(0):map.get("bs").toString())+"个固定基站、");
		 str.append(map.get("room")==null?0:map.get("room")+"个室内覆盖站、");
		 str.append(map.get("vertical")==null?0:map.get("vertical")+"个直放站、");
		 str.append(map.get("bus")==null?0:map.get("bus")+"辆应急通信车，");
		 str.append("实际完成巡检"+map.get("total")==null?0:map.get("bus")+"次");
		 str.append("巡检过程中发现隐患    起，已处理   起，详见《巡检记录汇总表》。");
		 c.add(str.toString());
		 document.add(c); 
		 //设置Table表格,创建一个三列的表格  
	        Paragraph tn=new Paragraph();
			tn.setAlignment(Element.ALIGN_CENTER);
			tn.setFont(tableTitleFont);
			tn.setSpacingAfter(1);
			tn.add("表十 巡检反馈表");
		    document.add(tn);
		    Table table = new Table(9);
	        int width[] = {25,25,25,30,50,20};//设置每列宽度比例  
	        //table.setWidths(width);  
	        table.setWidth(100);//占页面宽度比例  
	        table.setAlignment(1);
	        table.setAlignment(Element.ALIGN_CENTER);//居中  
	        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
	        table.setAutoFillEmptyCells(true);//自动填满  
	        table.setBorderWidth(1);//边框宽度 
	        table.setWidth(100);//占页面宽度比例  
	        table.setAlignment(1);
	        table.setAlignment(Element.ALIGN_CENTER);//居中  
	        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
	        table.setAutoFillEmptyCells(true);//自动填满  
	        table.setBorderWidth(1);//边框宽度   
		    table.addCell(new Cell(new Paragraph("资源类别",tableheaderFont)));
		    table.addCell(new Cell(new Paragraph("资源数量",tableheaderFont)));
		    table.addCell(new Cell(new Paragraph("应该巡检次数",tableheaderFont)));
		    table.addCell(new Cell(new Paragraph("实际巡检数量",tableheaderFont)));
		    table.addCell(new Cell(new Paragraph("隐患数量",tableheaderFont)));
		    table.addCell(new Cell(new Paragraph("隐患处理数量",tableheaderFont)));
		    table.addCell(new Cell(new Paragraph("应填写系统巡检表（份）",tableheaderFont)));
		    table.addCell(new Cell(new Paragraph("实际填写系统巡检表（份）",tableheaderFont)));
		    table.addCell(new Cell(new Paragraph("备注",tableheaderFont)));
		    
		    Map<String,Object> map2=new HashMap<String, Object>();
		    Map<String,Object> mapPara=new HashMap<String, Object>();
		    mapPara.put("time", time);
		    mapPara.put("period", 4);
		    map2=ToWorkFileServices.xj_bs_num(mapPara);
		    
		    table.addCell(new Cell(new Paragraph("固定基站",tableheaderFont)),1,0);
		    table.addCell(new Cell(new Paragraph(map.get("bs")==null?String.valueOf(0):map.get("bs").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map.get("bs")==null?String.valueOf(0):map.get("bs").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map2.get("bs")==null?String.valueOf(0):map2.get("bs").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map.get("bs")==null?String.valueOf(0):map.get("bs").toString(),tableheaderFont)),1,6);
		    table.addCell(new Cell(new Paragraph(map2.get("bs")==null?String.valueOf(0):map2.get("bs").toString(),tableheaderFont)),1,7);
		   
		    table.addCell(new Cell(new Paragraph("室内覆盖",tableheaderFont)),2,0);
		    table.addCell(new Cell(new Paragraph(map.get("room")==null?String.valueOf(0):map.get("room").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map.get("room")==null?String.valueOf(0):map.get("room").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map2.get("room")==null?String.valueOf(0):map2.get("room").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map.get("room")==null?String.valueOf(0):map.get("room").toString(),tableheaderFont)),2,6);
		    table.addCell(new Cell(new Paragraph(map2.get("room")==null?String.valueOf(0):map2.get("room").toString(),tableheaderFont)),2,7);
		   
		    
		    table.addCell(new Cell(new Paragraph("直放站",tableheaderFont)),3,0);
		    table.addCell(new Cell(new Paragraph(map.get("vertical")==null?String.valueOf(0):map.get("vertical").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map.get("vertical")==null?String.valueOf(0):map.get("vertical").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map2.get("vertical")==null?String.valueOf(0):map2.get("vertical").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map.get("vertical")==null?String.valueOf(0):map.get("vertical").toString(),tableheaderFont)),3,6);
		    table.addCell(new Cell(new Paragraph(map2.get("vertical")==null?String.valueOf(0):map2.get("vertical").toString(),tableheaderFont)),3,7);
		   
		    
		    table.addCell(new Cell(new Paragraph("应急通信车",tableheaderFont)),4,0);
		    table.addCell(new Cell(new Paragraph(map.get("bus")==null?String.valueOf(0):map.get("bus").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map.get("bus")==null?String.valueOf(0):map.get("bus").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map2.get("bus")==null?String.valueOf(0):map2.get("bus").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map.get("bus")==null?String.valueOf(0):map.get("bus").toString(),tableheaderFont)),4,6);
		    table.addCell(new Cell(new Paragraph(map2.get("bus")==null?String.valueOf(0):map2.get("bus").toString(),tableheaderFont)),4,7);
		   
		    
		    table.addCell(new Cell(new Paragraph("合计",tableheaderFont)),5,0);
		    table.addCell(new Cell(new Paragraph(map.get("total")==null?String.valueOf(0):map.get("total").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map.get("total")==null?String.valueOf(0):map.get("total").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map2.get("total")==null?String.valueOf(0):map2.get("total").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map.get("total")==null?String.valueOf(0):map.get("total").toString(),tableheaderFont)),5,6);
		    table.addCell(new Cell(new Paragraph(map2.get("total")==null?String.valueOf(0):map2.get("total").toString(),tableheaderFont)),5,7);
 
 
		    document.add(table);		 
	}
	/*4.3故障处理与分析*/
	public static void word_15(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws Exception{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);
		
		/*	设置标题1格式	*/ 
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1; 
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt1.setStyle(Font.BOLD); 
		rtfGsBt1.setSize(14); 
		/*	设置标题2格式	*/ 
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2; 
		/*rtfGsBt2.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt2.setStyle(Font.NORMAL); 
		rtfGsBt2.setSize(12);*/ 
		/*	设置标题3格式	*/ 
		RtfParagraphStyle rtfGsBt3 = RtfParagraphStyle.STYLE_HEADING_3; 
		/*rtfGsBt3.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt3.setStyle(Font.NORMAL); 
		rtfGsBt3.setSize(10); */
		
		
		 title=new Paragraph("3 故障处理与分析");
	     title.setAlignment(Element.ALIGN_LEFT);  
	     title.setFont(rtfGsBt2); 
	     title.setSpacingBefore(6);
	     title.setSpacingAfter(6);
		 document.add(title); 	
		 
		 Map<String,Object> map=new HashMap<String, Object>();
		 Map<String,Object> mapPara=new HashMap<String, Object>();
		 mapPara.put("time", time);
		 map=ToWorkFileServices.fault_num(mapPara);
		 
		 
		 StringBuilder str=new StringBuilder();
		 str.append("本月系统发生故障"+(map.get("total")==null?String.valueOf(0):map.get("total").toString())+"起， ");
		 str.append("特别重大故障"+(map.get("one")==null?String.valueOf(0):map.get("one").toString())+"，起");
		 str.append("重大故障"+(map.get("two")==null?String.valueOf(0):map.get("two").toString())+"起，");
		 str.append("一般故障"+(map.get("three")==null?String.valueOf(0):map.get("three").toString())+"起，");
		 str.append("其中维护计划作业       起，");
		 str.append("隐患故障"+(map.get("four")==null?String.valueOf(0):map.get("four").toString())+"起。");
		 c.add(str.toString());
		 document.add(c);
		 //设置Table表格,创建一个三列的表格  
	       /* Paragraph tn=new Paragraph();
			tn.setAlignment(Element.ALIGN_CENTER);
			tn.setFont(tableTitleFont);
			tn.setSpacingAfter(1);
			tn.add("表十 巡检反馈表");
		    document.add(tn);*/
		    Table table = new Table(5);
	        int width[] = {25,25,25,30,50,20};//设置每列宽度比例  
	        //table.setWidths(width);  
	        table.setWidth(100);//占页面宽度比例  
	        table.setAlignment(1);
	        table.setAlignment(Element.ALIGN_CENTER);//居中  
	        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
	        table.setAutoFillEmptyCells(true);//自动填满  
	        table.setBorderWidth(1);//边框宽度 
	        table.setWidth(100);//占页面宽度比例  
	        table.setAlignment(1);
	        table.setAlignment(Element.ALIGN_CENTER);//居中  
	        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
	        table.setAutoFillEmptyCells(true);//自动填满  
	        table.setBorderWidth(1);//边框宽度   
		    table.addCell(new Cell(new Paragraph("故障类别",tableheaderFont)));
		    table.addCell(new Cell(new Paragraph("故障次数",tableheaderFont)));
		    table.addCell(new Cell(new Paragraph("申请核减",tableheaderFont)));
		    table.addCell(new Cell(new Paragraph("核减后",tableheaderFont)));
		    table.addCell(new Cell(new Paragraph("备注",tableheaderFont)));
		    
		    
		    table.addCell(new Cell(new Paragraph("特别重大故障",tableheaderFont)),1,0);
		    table.addCell(new Cell(new Paragraph(map.get("one")==null?String.valueOf(0):map.get("one").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(String.valueOf(0),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(String.valueOf(0),tableheaderFont)));
		   
		    table.addCell(new Cell(new Paragraph("重大故障",tableheaderFont)),2,0);
		    table.addCell(new Cell(new Paragraph(map.get("two")==null?String.valueOf(0):map.get("two").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(String.valueOf(0),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(String.valueOf(0),tableheaderFont)));
		    
		    table.addCell(new Cell(new Paragraph("一般故障",tableheaderFont)),3,0);
		    table.addCell(new Cell(new Paragraph(map.get("three")==null?String.valueOf(0):map.get("three").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map.get("checked")==null?String.valueOf(0):map.get("checked").toString(),tableheaderFont)));
		    int num=Integer.parseInt(map.get("three")==null?String.valueOf(0):map.get("three").toString());
		    int chk1=Integer.parseInt(map.get("checked")==null?String.valueOf(0):map.get("checked").toString());
		    table.addCell(new Cell(new Paragraph(String.valueOf(num-chk1),tableheaderFont)));
		    
		    table.addCell(new Cell(new Paragraph("合计",tableheaderFont)),4,0);
		    table.addCell(new Cell(new Paragraph(map.get("total")==null?String.valueOf(0):map.get("total").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(map.get("checked")==null?String.valueOf(0):map.get("checked").toString(),tableheaderFont)));
		    table.addCell(new Cell(new Paragraph(String.valueOf(num-chk1),tableheaderFont)));
		    document.add(table);
		    
		    DefaultCategoryDataset dcd=new DefaultCategoryDataset();
		    
		    TimeSeries timeSeries=new TimeSeries(Integer.parseInt(time.split("-")[0])+"系统故障趋势图");
		    
			 for(int i=1;i<=12;i++){
				 Map<String,Object> m=new HashMap<String, Object>();		 
				 m.put("time", time.split("-")[0]+"-"+(i<10?"0"+i:i));
				 Map<String,Object> m2=ToWorkFileServices.fault_num(m);
				 dcd.addValue((Number)Math.round(Double.valueOf(m2.get("total")==null?
						 String.valueOf(0):m2.get("total").toString())), time.split("-")[0],(i<10?"0"+i:i));
				 timeSeries.add(new Month(i,Integer.parseInt(time.split("-")[0])),Double.valueOf(m2.get("total")==null?
						 String.valueOf(0):m2.get("total").toString()));
			 }
		        try {
					ChartUtil.LineImg("系统故障趋势图", timeSeries);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        Image img=Image.getInstance("D:/chart.png");
			    document.add(img);
	        
	}
	/*4.3.1特别重大故障*/
	public static void word_16(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws Exception{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);
		
		/*	设置标题1格式	*/ 
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1; 
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt1.setStyle(Font.BOLD); 
		rtfGsBt1.setSize(14); 
		/*	设置标题2格式	*/ 
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2; 
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt2.setStyle(Font.NORMAL); 
		rtfGsBt2.setSize(12); 
		/*	设置标题3格式	*/ 
		RtfParagraphStyle rtfGsBt3 = RtfParagraphStyle.STYLE_HEADING_3; 
		/*rtfGsBt3.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt3.setStyle(Font.NORMAL); 
		rtfGsBt3.setSize(10); */
		
		
		 title=new Paragraph("3.1、特别重大故障");
	     title.setAlignment(Element.ALIGN_LEFT);  
	     title.setFont(rtfGsBt3); 
	     title.setSpacingBefore(6);
	     title.setSpacingAfter(6);
		 document.add(title); 	
	
	}
	/*4.3.2重大故障*/
	public static void word_17(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws Exception{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);
		
		/*	设置标题1格式	*/ 
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1; 
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt1.setStyle(Font.BOLD); 
		rtfGsBt1.setSize(14); 
		/*	设置标题2格式	*/ 
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2; 
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt2.setStyle(Font.NORMAL); 
		rtfGsBt2.setSize(12); 
		/*	设置标题3格式	*/ 
		RtfParagraphStyle rtfGsBt3 = RtfParagraphStyle.STYLE_HEADING_3; 
		rtfGsBt3.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt3.setStyle(Font.NORMAL); 
		rtfGsBt3.setSize(10); 
		
		
		 title=new Paragraph("3.2、重大故障");
	     title.setAlignment(Element.ALIGN_LEFT);  
	     title.setFont(rtfGsBt3); 
	     title.setSpacingBefore(6);
	     title.setSpacingAfter(6);
		 document.add(title); 	
	
	}
	/*4.3..3、一般故障*/
	public static void word_18(String time,Document document,Paragraph c,RtfParagraphStyle rtfGsBt,Paragraph title) throws Exception{
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);	
		Font tableheaderFont = new Font(bfChinese, 10, Font.BOLD);
		Font tableTitleFont = new Font(bfChinese, 10, Font.NORMAL);
		
		/*	设置标题1格式	*/ 
		RtfParagraphStyle rtfGsBt1 = RtfParagraphStyle.STYLE_HEADING_1; 
		rtfGsBt1.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt1.setStyle(Font.BOLD); 
		rtfGsBt1.setSize(14); 
		/*	设置标题2格式	*/ 
		RtfParagraphStyle rtfGsBt2 = RtfParagraphStyle.STYLE_HEADING_2; 
		rtfGsBt2.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt2.setStyle(Font.NORMAL); 
		rtfGsBt2.setSize(12); 
		/*	设置标题3格式	*/ 
		RtfParagraphStyle rtfGsBt3 = RtfParagraphStyle.STYLE_HEADING_3; 
		rtfGsBt3.setAlignment(Element.ALIGN_LEFT); 
		rtfGsBt3.setStyle(Font.NORMAL); 
		rtfGsBt3.setSize(10); 
		
		
		 title=new Paragraph("3.3、一般故障");
	     title.setAlignment(Element.ALIGN_LEFT);  
	     title.setFont(rtfGsBt3); 
	     title.setSpacingBefore(6);
	     title.setSpacingAfter(6);
		 document.add(title); 

	        Paragraph tn=new Paragraph();
			tn.setAlignment(Element.ALIGN_CENTER);
			tn.setFont(tableTitleFont);
			tn.setSpacingAfter(1);
			tn.add("表十一 一般故障分析（按基站等级分类）");
		    document.add(tn);
		    Table table = new Table(6);
	        int width[] = {25,25,25,30,50,20};//设置每列宽度比例  
	        //table.setWidths(width);  
	        table.setWidth(100);//占页面宽度比例  
	        table.setAlignment(1);
	        table.setAlignment(Element.ALIGN_CENTER);//居中  
	        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
	        table.setAutoFillEmptyCells(true);//自动填满  
	        table.setBorderWidth(1);//边框宽度 
	        table.setWidth(100);//占页面宽度比例  
	        table.setAlignment(1);
	        table.setAlignment(Element.ALIGN_CENTER);//居中  
	        table.setAlignment(Element.ALIGN_MIDDLE);//垂直居中  
	        table.setAutoFillEmptyCells(true);//自动填满  
	        table.setBorderWidth(1);//边框宽度   
	        table.addCell(new Cell(new Paragraph("基站类别",tableheaderFont)));
	        table.addCell(new Cell(new Paragraph("故障次数",tableheaderFont)));
	        table.addCell(new Cell(new Paragraph("申请核减",tableheaderFont)));
	        table.addCell(new Cell(new Paragraph("核减后断站",tableheaderFont)));
	        table.addCell(new Cell(new Paragraph("处理超时（次）",tableheaderFont)));
	        table.addCell(new Cell(new Paragraph("超时时间（小时）",tableheaderFont)));
		 
		 Map<String,Object> mapPara=new HashMap<String, Object>();
		 mapPara.put("time", time);
		 mapPara.put("period", 4);
		 
		 List<Map<String,Object>> list=ToWorkFileServices.fault_level(mapPara);
		 Map<String, Object> map1=new HashMap<String, Object>();
		 Map<String, Object> map2=new HashMap<String, Object>();
		 Map<String, Object> map3=new HashMap<String, Object>();
		 int total=0,checked=0,afterCheck=0;
		 for (Map<String, Object> map : list) {
			 
			/* System.out.println("mmmmmm->"+map);*/
			 total+=Integer.parseInt(map.get("total").toString());
			 checked+=Integer.parseInt(map.get("checked").toString());
			 afterCheck+=Integer.parseInt(map.get("afterCheck").toString());
			 
			if(map.get("level").toString().equals("1")){
				map1.put("total", map.get("total"));
				map1.put("checked", map.get("checked"));
				map1.put("afterCheck", map.get("afterCheck"));
			}else if(map.get("level").toString().equals("2")){
				map2.put("total", map.get("total"));
				map2.put("checked", map.get("checked"));
				map2.put("afterCheck", map.get("afterCheck"));
			}else if(map.get("level").toString().equals("3")){
				map3.put("total", map.get("total"));
				map3.put("checked", map.get("checked"));
				map3.put("afterCheck", map.get("afterCheck"));
			}
		}
		table.addCell(new Cell(new Paragraph("一级基站",tableheaderFont)),1,0);
		table.addCell(new Cell(new Paragraph(map1==null?"0":map1.get("total").toString(),tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map1==null?"0":map1.get("checked").toString(),tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map1==null?"0":map1.get("afterCheck").toString(),tableheaderFont)));
		
		table.addCell(new Cell(new Paragraph("二级基站",tableheaderFont)),2,0);
		table.addCell(new Cell(new Paragraph(map2==null?"0":map2.get("total").toString(),tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map2==null?"0":map2.get("checked").toString(),tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map2==null?"0":map2.get("afterCheck").toString(),tableheaderFont)));
		
		table.addCell(new Cell(new Paragraph("三级基站",tableheaderFont)),3,0);
		table.addCell(new Cell(new Paragraph(map3==null?"0":map3.get("total").toString(),tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map3==null?"0":map3.get("checked").toString(),tableheaderFont)));
		table.addCell(new Cell(new Paragraph(map3==null?"0":map3.get("afterCheck").toString(),tableheaderFont)));
		
		table.addCell(new Cell(new Paragraph("合计",tableheaderFont)),4,0);
		table.addCell(new Cell(new Paragraph(String.valueOf(total),tableheaderFont)),4,1);
		table.addCell(new Cell(new Paragraph(String.valueOf(checked),tableheaderFont)),4,2);
		table.addCell(new Cell(new Paragraph(String.valueOf(afterCheck),tableheaderFont)),4,3);
		document.add(table);
		
		 DefaultPieDataset dpd=new DefaultPieDataset();
		 for (Map<String, Object> map : list) {
			
				 
				if(map.get("level").toString().equals("1")){
					 dpd.setValue("一级基站",(Number) map.get("total"));
				}else if(map.get("level").toString().equals("2")){
					dpd.setValue("二级基站",(Number) map.get("total"));
				}else if(map.get("level").toString().equals("3")){
					dpd.setValue("三级基站",(Number) map.get("total"));
				}
			}
	       
	       ChartUtil.PieImg("一般故障基站等级占比", dpd,0);
	       Image img=Image.getInstance("D:/chart.png");
	       document.add(img);
	       
	       list=new ArrayList<Map<String,Object>>();
	       mapPara.clear();
	       mapPara.put("time", time.split(",")[0]);
		   mapPara.put("period", 4);
	       list=ToWorkFileServices.fault_level_pie(mapPara);
	       
	       DefaultCategoryDataset dcd=new DefaultCategoryDataset();
		   TimeSeries timeSeries=new TimeSeries(Integer.parseInt(time.split("-")[0])+"系统故障趋势图");
		    
			/* for(int i=1;i<=12;i++){
				 Map<String,Object> m=new HashMap<String, Object>();
				 Map<String,Object> m2=new HashMap<String, Object>();
				 for (Map<String, Object> map : list) {
					if(map.get("month")!=null && Integer.parseInt(map.get("month").toString())==i){
						m2=map;
					}
				}
				 dcd.addValue((Number) Math.round(Double.valueOf(m2==null?"0":m2.get("total").toString())), time.split("-")[0],(i<10?"0"+i:i));
				 timeSeries.add(new Month(i,Integer.parseInt(time.split("-")[0])),Double.valueOf(m2==null?"0":m2.get("total").toString()));
			 }
		        try {
					ChartUtil.LineImg("一级基站故障次数", timeSeries);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        img=Image.getInstance("D:/chart.png");
			    document.add(img);*/
	       
	}
	
	
}
