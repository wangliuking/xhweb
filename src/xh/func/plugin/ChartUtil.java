package xh.func.plugin;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

import xh.mybatis.service.ToWorkFileServices;

public class ChartUtil {
	public static void main(String[] args) throws Exception {
		TimeSeries timeSeries=new TimeSeries("A");
		// 添加数据
	    String time="2019-01";
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
						Integer.parseInt(map.get("yearstr").toString())),Double.valueOf(map.get("TotalCalls").toString()));
			}
	      
	        for (Map<String, Object> map : list2) {
				timeSeries2.add(new Month(Integer.parseInt(map.get("monthstr").toString()),
						Integer.parseInt(map.get("yearstr").toString())),Double.valueOf(map.get("TotalCalls").toString()));
			}
	        
	        ChartUtil.ThreeLineImg("系统总通话次数",timeSeries1, timeSeries2, timeSeries3);
	}

	/**
	 * 饼图
	 * @param name
	 * @param dcd
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public static void PieImg(String name,DefaultPieDataset pds,int tag) throws Exception {

		ChartFactory.setChartTheme(getFont());
		 // 分别是:显示图表的标题、需要提供对应图表的DateSet对象、是否显示图例、是否生成贴士以及是否生成URL链接
        JFreeChart chart = ChartFactory.createPieChart(name, pds, true, false, true);
        // 如果不使用Font,中文将显示不出来
        Font font = new Font("宋体", Font.BOLD, 12);
        // 设置图片标题的字体
        chart.getTitle().setFont(font);
        // 得到图块,准备设置标签的字体
        PiePlot plot = (PiePlot) chart.getPlot();
        // 设置标签字体
        plot.setLabelFont(font);
        plot.setStartAngle(new Float(3.14f / 2f));
        // 设置plot的前景色透明度
        plot.setForegroundAlpha(0.7f);
        // 设置plot的背景色透明度
        plot.setBackgroundAlpha(0.0f);
        // 设置标签生成器(默认{0})
        // {0}:key {1}:value {2}:百分比 {3}:sum
        if(tag==0){
         plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2})"));
        }else if(tag==1){
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1}占{2})"));
           }else if(tag==2){
               plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1}占{2})"));
           }
        
        // 将内存中的图片写到本地硬盘
		File pieChart = new File("D:/chart.png");
		ChartUtilities.saveChartAsPNG(pieChart, chart, 550, 350);
	}
	/**
	 * 单柱状图
	 * @param name
	 * @param dpd
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	public static void Bar3DImg(String name,DefaultCategoryDataset  cdt) throws Exception {

		ChartFactory.setChartTheme(getFont());
		 Font font = new Font("宋体", Font.BOLD, 12);
		JFreeChart chart = ChartFactory.createBarChart3D(name,"","",cdt,PlotOrientation.VERTICAL,true,false,false);
		 CategoryPlot categoryPlot = chart.getCategoryPlot();
		// 取得X轴
        CategoryAxis categoryAxis = (CategoryAxis) categoryPlot.getDomainAxis();
        // 横轴上的 Lable 45度倾斜
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		File pieChart = new File("D:/chart.png");
		ChartUtilities.saveChartAsPNG(pieChart, chart, 500, 400);
	}
	public static void BarImg(String name,DefaultCategoryDataset  cdt) throws Exception {

		ChartFactory.setChartTheme(getFont());
		 Font font = new Font("宋体", Font.BOLD, 12);
		JFreeChart chart = ChartFactory.createBarChart(name,"","",cdt,PlotOrientation.VERTICAL,true,false,false);
		 CategoryPlot categoryPlot = chart.getCategoryPlot();
		// 取得X轴
        CategoryAxis categoryAxis = (CategoryAxis) categoryPlot.getDomainAxis();
        // 横轴上的 Lable 45度倾斜
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		File pieChart = new File("D:/chart.png");
		ChartUtilities.saveChartAsPNG(pieChart, chart, 500, 400);
	}
	public static void ThreeLineImg(String name,TimeSeries timeSeries1,TimeSeries timeSeries2,TimeSeries timeSeries3) throws Exception {
		//timeSeries.add(new Month(1,2013), 100);
		ChartFactory.setChartTheme(getFont());
		// 定义时间序列的集合
		TimeSeriesCollection lineDataset=new TimeSeriesCollection();
		lineDataset.addSeries(timeSeries1);

		lineDataset.addSeries(timeSeries2);
		lineDataset.addSeries(timeSeries3);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(name, "", "", lineDataset, true, true, true);
		// 设置主标题
		chart.setTitle(new TextTitle(name, new Font("隶书", Font.ITALIC, 13)));
		/*// 设置子标题
		TextTitle subtitle = new TextTitle("2", new Font("黑体", Font.BOLD, 12));
		chart.addSubtitle(subtitle);*/
		chart.setAntiAlias(true);
 
		// 设置时间轴的范围。
		XYPlot plot = (XYPlot) chart.getPlot();
		DateAxis dateaxis = (DateAxis) plot.getDomainAxis();
		dateaxis.setDateFormatOverride(new java.text.SimpleDateFormat("M月"));
		dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1));
		
		// 背景色 透明度
		plot.setBackgroundAlpha(0.5f);
				// 前景色 透明度
		plot.setForegroundAlpha(0.5f);
 
		// 设置曲线是否显示数据点
		XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer) plot.getRenderer();
		xylinerenderer.setBaseShapesVisible(true);

		// 设置曲线显示各数据点的值
		XYItemRenderer xyitem = plot.getRenderer();
		xyitem.setBaseItemLabelsVisible(true);
		xyitem.setBasePositiveItemLabelPosition(
				new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
		xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 12));
		plot.setRenderer(xyitem);
        File pieChart = new File("D:/chart.png");
        ChartUtilities.saveChartAsPNG(pieChart, chart, 500, 400);
    }
	public static void LineImg(String name,TimeSeries timeSeries1) throws Exception {
		//timeSeries.add(new Month(1,2013), 100);
		ChartFactory.setChartTheme(getFont());
		// 定义时间序列的集合
		TimeSeriesCollection lineDataset=new TimeSeriesCollection();
		lineDataset.addSeries(timeSeries1);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(name, "", "", lineDataset, true, true, true);
		// 设置主标题
		chart.setTitle(new TextTitle(name, new Font("隶书", Font.ITALIC, 13)));
		/*// 设置子标题
		TextTitle subtitle = new TextTitle("2", new Font("黑体", Font.BOLD, 12));
		chart.addSubtitle(subtitle);*/
		chart.setAntiAlias(true);
 
		// 设置时间轴的范围。
		XYPlot plot = (XYPlot) chart.getPlot();
		DateAxis dateaxis = (DateAxis) plot.getDomainAxis();
		dateaxis.setDateFormatOverride(new java.text.SimpleDateFormat("M月"));
		dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, 1));
		
		// 背景色 透明度
		//plot.setBackgroundAlpha(0.5f);
		// 前景色 透明度
		//plot.setForegroundAlpha(0.5f);
		 plot.setBackgroundPaint(Color.WHITE);
		 plot.setRangeGridlinePaint(Color.black);//背景底部横虚线
		 plot.setRangeZeroBaselinePaint(Color.GREEN);
		
		
 
		// 设置曲线是否显示数据点
		XYLineAndShapeRenderer xylinerenderer = (XYLineAndShapeRenderer) plot.getRenderer();
		xylinerenderer.setBaseShapesVisible(true);
		xylinerenderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		xylinerenderer.setItemLabelFont(new Font("宋体", Font.PLAIN, 12));// 设置数字的字体大小
		//xylinerenderer.setItemLabelAnchorOffset(2D);
		// // 设置颜色-----------------------------------------------------------
		xylinerenderer.setSeriesStroke(0, new BasicStroke(1.0F));//设置折线大小
		xylinerenderer.setSeriesPaint(0, Color.blue);//绿色
		plot.setRenderer(xylinerenderer);// 使用我们设计的效果F
		// 设置曲线显示各数据点的值
		XYItemRenderer xyitem = plot.getRenderer();
		xyitem.setBaseItemLabelsVisible(true);
		xyitem.setBasePositiveItemLabelPosition(
				new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
		xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 10));
		plot.setRenderer(xyitem);
        File pieChart = new File("D:/chart.png");
        ChartUtilities.saveChartAsPNG(pieChart, chart, 500, 350);
    }
	public static void LineImg(String name,DefaultCategoryDataset dcd) throws Exception {
		//timeSeries.add(new Month(1,2013), 100);
		ChartFactory.setChartTheme(getFont());

		JFreeChart chart = ChartFactory.createLineChart(name, "", "", dcd, PlotOrientation.VERTICAL, true, true, false);
		// 设置主标题
		chart.setTitle(new TextTitle(name, new Font("隶书", Font.ITALIC, 13)));
		/*// 设置子标题
		TextTitle subtitle = new TextTitle("2", new Font("黑体", Font.BOLD, 12));
		chart.addSubtitle(subtitle);*/
		chart.setAntiAlias(true);
 
		 CategoryPlot mPlot = (CategoryPlot)chart.getPlot();
		    mPlot.setBackgroundPaint(Color.WHITE);
		    mPlot.setRangeGridlinePaint(Color.gray);//背景底部横虚线
		    //mPlot.setOutlinePaint(Color.RED);//边界线
 
	

        File pieChart = new File("D:/chart.png");
        ChartUtilities.saveChartAsPNG(pieChart, chart, 500, 400);
    }
	public static void createLineChart(DefaultCategoryDataset ds,String name) {
        try {
            // 创建柱状图.标题,X坐标,Y坐标,数据集合,orientation,是否显示legend,是否显示tooltip,是否使用url链接
            JFreeChart chart = ChartFactory.createLineChart(name, "", "", ds, PlotOrientation.VERTICAL,false, true, true);
            chart.setBackgroundPaint(Color.WHITE);
            Font font = new Font("宋体", Font.BOLD, 12);
            chart.getTitle().setFont(font);
            chart.setBackgroundPaint(Color.WHITE);
            // 配置字体（解决中文乱码的通用方法）
            Font xfont = new Font("仿宋", Font.BOLD, 12); // X轴
            Font yfont = new Font("宋体", Font.BOLD, 12); // Y轴
            Font titleFont = new Font("宋体", Font.BOLD, 12); // 图片标题
            CategoryPlot categoryPlot = chart.getCategoryPlot();
            categoryPlot.getDomainAxis().setLabelFont(xfont);
            categoryPlot.getDomainAxis().setLabelFont(xfont);
            categoryPlot.getRangeAxis().setLabelFont(yfont);
            chart.getTitle().setFont(titleFont);
            categoryPlot.setBackgroundPaint(Color.WHITE);
            // x轴 // 分类轴网格是否可见
            categoryPlot.setDomainGridlinesVisible(true);
            // y轴 //数据轴网格是否可见
            categoryPlot.setRangeGridlinesVisible(true);
            // 设置网格竖线颜色
            categoryPlot.setDomainGridlinePaint(Color.LIGHT_GRAY);
            // 设置网格横线颜色
            categoryPlot.setRangeGridlinePaint(Color.LIGHT_GRAY);
            // 没有数据时显示的文字说明
            categoryPlot.setNoDataMessage("没有数据显示");
            // 设置曲线图与xy轴的距离
            categoryPlot.setAxisOffset(new RectangleInsets(0d, 0d, 0d, 0d));
            // 设置面板字体
            Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
            // 取得Y轴
            NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            rangeAxis.setAutoRangeIncludesZero(true);
            rangeAxis.setUpperMargin(0.20);
            rangeAxis.setLabelAngle(Math.PI / 2.0);
            // 取得X轴
            CategoryAxis categoryAxis = (CategoryAxis) categoryPlot.getDomainAxis();
            // 设置X轴坐标上的文字
            categoryAxis.setTickLabelFont(labelFont);
            // 设置X轴的标题文字
            categoryAxis.setLabelFont(labelFont);
            // 横轴上的 Lable 45度倾斜
            categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            // 设置距离图片左端距离
            categoryAxis.setLowerMargin(0.0);
            // 设置距离图片右端距离
            categoryAxis.setUpperMargin(0.0);
            // 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
            LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
            // 是否显示折点
            lineandshaperenderer.setBaseShapesVisible(true);
            // 是否显示折线
            lineandshaperenderer.setBaseLinesVisible(true);
            // series 点（即数据点）间有连线可见 显示折点数据
            lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
            lineandshaperenderer.setBaseItemLabelsVisible(true);
            ChartUtilities.saveChartAsPNG(new File("D:/chart.png"), chart, 1207, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public static StandardChartTheme getFont() throws Exception {
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		standardChartTheme.setExtraLargeFont(new Font("微软雅黑", Font.BOLD, 10));
		standardChartTheme.setRegularFont(new Font("微软雅黑", Font.PLAIN, 9));
		standardChartTheme.setLargeFont(new Font("微软雅黑", Font.PLAIN, 9));
		return standardChartTheme;
	}

	/**
	 * 删除图片
	 * 
	 * @param imgName
	 */
	public static void deleteImg(String imgName) {
		File img = null;
		try {
			img = new File(imgName);
			boolean b = img.exists();
			if (b) {
				img.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
