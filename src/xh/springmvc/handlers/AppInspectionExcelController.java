package xh.springmvc.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.InspectionDispatchBean;
import xh.mybatis.bean.InspectionMscBean;
import xh.mybatis.bean.InspectionNetBean;
import xh.mybatis.bean.InspectionRoomBean;
import xh.mybatis.bean.InspectionSbsBean;
import xh.mybatis.bean.InspectionStarBean;
import xh.mybatis.bean.InspectionVerticalBean;

public class AppInspectionExcelController {
	public HashMap<String, Object> app_bs(List<InspectionSbsBean> sbsList,
			String saveDir, String time, String zipName) throws Exception {
		String pathname = "";

		ArrayList<String> fileNames = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		try {

			for (InspectionSbsBean bean : sbsList) {

				String fileName = time + "-基站-[" + bean.getBsname() + "("
						+ bean.getBsid() + ")]巡检表.xls";
				sb.append(fileName);
				sb.append(",");
				fileNames.add(fileName);

				pathname = saveDir + "/" + fileName;
				File Path = new File(saveDir);
				if (!Path.exists()) {
					Path.mkdirs();
				}
				File file = new File(pathname);

			}

			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("fileName", sb.deleteCharAt(sb.length() - 1).toString());
			result.put("zipName", zipName);
			result.put("pathName", pathname);
			return result;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;

	}

	public void excel_vertical(File file, InspectionVerticalBean bean) {
		try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 15, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体

			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义

			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);

			// 设置其他样式

			wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
			wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcf_title.setBackground(Colour.WHITE);// 背景色
			wcf_title.setWrap(false);// 不自动换行

			fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			WritableSheet sheet = book.createSheet("网管巡检表", 0);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			// 第1行
			Label title = new Label(0, 0, "直放站巡检表", fontFormat);
			sheet.mergeCells(0, 0, 4, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE,
					PaperSize.A4, 0.5d, 0.5d);

			// 第2行

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 25);
			sheet.setColumnView(2, 40);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 30);

			sheet.addCell(new Label(0, 1, "基站名称", fontFormat_Content));
			sheet.addCell(new Label(1, 1, bean.getName(), fontFormat_Content));
			sheet.addCell(new Label(2, 1, "巡检人", fontFormat_Content));
			sheet.addCell(new Label(3, 1, bean.getCheckman(),
					fontFormat_Content));
			sheet.mergeCells(3, 1, 4, 1);

			sheet.addCell(new Label(0, 2, "巡检时间", fontFormat_Content));
			sheet.addCell(new Label(1, 2, bean.getCommitdate().split(" ")[0],
					fontFormat_Content));
			sheet.addCell(new Label(2, 2, "电表", fontFormat_Content));
			sheet.addCell(new Label(3, 2, bean.getAmmeternumber(),
					fontFormat_Content));
			sheet.mergeCells(3, 2, 4, 2);

			// 第3行
			// sheet.mergeCells(6, 2, 7, 2);

			sheet.addCell(new Label(0, 3, "巡检项目", fontFormat_h));
			sheet.addCell(new Label(1, 3, "具体项目", fontFormat_h));
			sheet.addCell(new Label(2, 3, "具体操作", fontFormat_h));
			sheet.addCell(new Label(3, 3, "执行情况", fontFormat_h));
			sheet.addCell(new Label(4, 3, "备注", fontFormat_h));

			// 第4行
			sheet.addCell(new Label(0, 4, "基站清洁", fontFormat_Content));
			sheet.mergeCells(0, 4, 0, 5);
			sheet.addCell(new Label(1, 4, "机房清洁", fontFormat_Content));
			sheet.addCell(new Label(2, 4, "清洁机房内门窗、地面、墙面卫生", fontFormat_Content));
			sheet.addCell(new Label(3, 4, checkbox(bean.getD1()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 4, bean.getComment1(),
					fontFormat_Content));
			// 第5行
			sheet.addCell(new Label(1, 5, "设备除尘", fontFormat_Content));
			sheet.addCell(new Label(2, 5, "清洁设备表面及内部", fontFormat_Content));
			sheet.addCell(new Label(3, 5, checkbox(bean.getD2()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 5, bean.getComment2(),
					fontFormat_Content));
			// 第6行
			sheet.addCell(new Label(0, 6, "基站环境", fontFormat_Content));
			sheet.mergeCells(0, 6, 0, 11);
			sheet.addCell(new Label(1, 6, "基站安全", fontFormat_Content));
			sheet.mergeCells(1, 6, 1, 8);
			sheet.addCell(new Label(2, 6, "门锁、门窗是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 6, checkbox(bean.getD3()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 6, bean.getComment3(),
					fontFormat_Content));
			// 第7行

			sheet.addCell(new Label(2, 7, "清理可燃物", fontFormat_Content));
			sheet.addCell(new Label(3, 7, checkbox(bean.getD4()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 7, bean.getComment4(),
					fontFormat_Content));
			// 第8行
			sheet.addCell(new Label(2, 8, "机房是否存在裂缝、渗水、漏水等情况	",
					fontFormat_Content));
			sheet.addCell(new Label(3, 8, checkbox(bean.getD5()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 8, bean.getComment5(),
					fontFormat_Content));
			// 第9行
			sheet.addCell(new Label(1, 9, "机房配套", fontFormat_Content));
			sheet.mergeCells(1, 9, 1, 11);
			sheet.addCell(new Label(2, 9, "机房照明设施是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 9, checkbox(bean.getD6()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 9, bean.getComment6(),
					fontFormat_Content));
			// 第10行
			sheet.addCell(new Label(2, 10, "机房插座是否有电", fontFormat_Content));
			sheet.addCell(new Label(3, 10, checkbox(bean.getD7()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 10, bean.getComment7(),
					fontFormat_Content));
			// 第11行
			sheet.addCell(new Label(2, 10, "灭火设备是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 10, checkbox(bean.getD8()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 10, bean.getComment8(),
					fontFormat_Content));
			// 第12行
			sheet.addCell(new Label(0, 11, "主体设备", fontFormat_Content));
			sheet.mergeCells(0, 11, 0, 16);
			sheet.addCell(new Label(1, 11, "基站运行状态", fontFormat_Content));
			sheet.addCell(new Label(2, 11, "基站设备是否正常运行，供电是否正常，设备有无明显告警",
					fontFormat_Content));
			sheet.addCell(new Label(3, 11, checkbox(bean.getD9()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 11, bean.getComment9(),
					fontFormat_Content));
			// 第13行
			sheet.addCell(new Label(1, 12, "告警确认", fontFormat_Content));
			sheet.addCell(new Label(2, 12, "通过与监控后台联系确认设备运行是否正常",
					fontFormat_Content));
			sheet.addCell(new Label(3, 12, checkbox(bean.getD10()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 12, bean.getComment10(),
					fontFormat_Content));
			// 第14行
			sheet.addCell(new Label(1, 13, "风扇运行检查", fontFormat_Content));
			sheet.addCell(new Label(2, 13, "检查风扇是否正常运转、无告警", fontFormat_Content));
			sheet.addCell(new Label(3, 13, checkbox(bean.getD11()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 13, bean.getComment11(),
					fontFormat_Content));
			// 第15行
			sheet.addCell(new Label(1, 14, "检查各连线状态", fontFormat_Content));
			sheet.addCell(new Label(2, 14,
					"电源/信号/射频/地线/网线是否分开，机架内各连线是否接触良好并正确无误", fontFormat_Content));
			sheet.addCell(new Label(3, 14, checkbox(bean.getD12()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 14, bean.getComment12(),
					fontFormat_Content));
			// 第16行
			sheet.addCell(new Label(1, 15, "设备加固", fontFormat_Content));
			sheet.addCell(new Label(2, 15, "各设备是否安装牢固", fontFormat_Content));
			sheet.addCell(new Label(3, 15, checkbox(bean.getD13()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 15, bean.getComment13(),
					fontFormat_Content));
			// 第17行
			sheet.addCell(new Label(1, 16, "设备温度", fontFormat_Content));
			sheet.addCell(new Label(2, 16, "各设备是否有高温、异味", fontFormat_Content));
			sheet.addCell(new Label(3, 16, checkbox(bean.getD14()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 16, bean.getComment14(),
					fontFormat_Content));
			// 第18行
			sheet.addCell(new Label(0, 17, "空调", fontFormat_Content));
			sheet.mergeCells(0, 17, 0, 18);
			sheet.addCell(new Label(1, 17, "制冷效果", fontFormat_Content));
			sheet.addCell(new Label(2, 17, "空调制冷效果是否正常", fontFormat_Content));
			sheet.addCell(new Label(3, 17, checkbox(bean.getD15()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 17, bean.getComment16(),
					fontFormat_Content));
			// 第19行
			sheet.addCell(new Label(1, 18, "温度设置", fontFormat_Content));
			sheet.addCell(new Label(2, 18, "机房温度是否正常（建议空调设置25℃，最终情况以机房环境为主）",
					fontFormat_Content));
			sheet.addCell(new Label(3, 18, checkbox(bean.getD16()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 18, bean.getComment16(),
					fontFormat_Content));
			// 第20行
			sheet.addCell(new Label(0, 19, "功能", fontFormat_Content));
			sheet.addCell(new Label(1, 19, "呼叫功能", fontFormat_Content));
			sheet.addCell(new Label(2, 19, "沿途呼叫测试", fontFormat_Content));
			sheet.addCell(new Label(3, 19, checkbox(bean.getD17()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 19, bean.getComment17(),
					fontFormat_Content));
			// 第20行
			sheet.addCell(new Label(0, 20, "遗留问题", fontFormat_Content));
			sheet.addCell(new Label(1, 20, bean.getQuestion(),
					fontFormat_Content));
			sheet.mergeCells(1, 20, 4, 20);
			sheet.setRowView(20, 600);
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void excel_room(File file, InspectionRoomBean bean) {

		try {

			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 15, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体

			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义

			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);

			// 设置其他样式

			wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
			wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcf_title.setBackground(Colour.WHITE);// 背景色
			wcf_title.setWrap(false);// 不自动换行

			fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			WritableSheet sheet = book.createSheet("室内覆盖站巡检表", 0);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			// 第1行
			Label title = new Label(0, 0, "室内覆盖站巡检表", fontFormat);
			sheet.mergeCells(0, 0, 4, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE,
					PaperSize.A4, 0.5d, 0.5d);

			// 第2行

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 25);
			sheet.setColumnView(2, 40);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 30);

			sheet.addCell(new Label(0, 1, "基站名称", fontFormat_Content));
			sheet.addCell(new Label(1, 1, bean.getName(), fontFormat_Content));
			sheet.addCell(new Label(2, 1, "巡检人", fontFormat_Content));
			sheet.addCell(new Label(3, 1, bean.getCheckman(),
					fontFormat_Content));
			sheet.mergeCells(3, 1, 4, 1);

			sheet.addCell(new Label(0, 2, "巡检时间", fontFormat_Content));
			sheet.addCell(new Label(1, 2, bean.getCommitdate().split(" ")[0],
					fontFormat_Content));
			sheet.addCell(new Label(2, 2, "电表", fontFormat_Content));
			sheet.addCell(new Label(3, 2, bean.getAmmeternumber(),
					fontFormat_Content));
			sheet.mergeCells(3, 2, 4, 2);

			// 第3行
			// sheet.mergeCells(6, 2, 7, 2);

			sheet.addCell(new Label(0, 3, "巡检项目", fontFormat_h));
			sheet.addCell(new Label(1, 3, "具体项目", fontFormat_h));
			sheet.addCell(new Label(2, 3, "具体操作", fontFormat_h));
			sheet.addCell(new Label(3, 3, "执行情况", fontFormat_h));
			sheet.addCell(new Label(4, 3, "备注", fontFormat_h));

			// 第4行
			sheet.addCell(new Label(0, 4, "基站清洁", fontFormat_Content));
			sheet.mergeCells(0, 4, 0, 5);
			sheet.addCell(new Label(1, 4, "机房清洁", fontFormat_Content));
			sheet.addCell(new Label(2, 4, "清洁机房内门窗、地面、墙面卫生", fontFormat_Content));
			sheet.addCell(new Label(3, 4, checkbox(bean.getD1()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 4, bean.getComment1(),
					fontFormat_Content));
			// 第5行
			sheet.addCell(new Label(1, 5, "设备除尘", fontFormat_Content));
			sheet.addCell(new Label(2, 5, "清洁设备表面及内部", fontFormat_Content));
			sheet.addCell(new Label(3, 5, checkbox(bean.getD2()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 5, bean.getComment2(),
					fontFormat_Content));
			// 第6行
			sheet.addCell(new Label(0, 6, "基站环境", fontFormat_Content));
			sheet.mergeCells(0, 6, 0, 11);
			sheet.addCell(new Label(1, 6, "基站安全", fontFormat_Content));
			sheet.mergeCells(1, 6, 1, 8);
			sheet.addCell(new Label(2, 6, "门锁、门窗是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 6, checkbox(bean.getD3()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 6, bean.getComment3(),
					fontFormat_Content));
			// 第7行

			sheet.addCell(new Label(2, 7, "清理可燃物", fontFormat_Content));
			sheet.addCell(new Label(3, 7, checkbox(bean.getD4()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 7, bean.getComment4(),
					fontFormat_Content));
			// 第8行
			sheet.addCell(new Label(2, 8, "机房是否存在裂缝、渗水、漏水等情况	",
					fontFormat_Content));
			sheet.addCell(new Label(3, 8, checkbox(bean.getD5()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 8, bean.getComment5(),
					fontFormat_Content));
			// 第9行
			sheet.addCell(new Label(1, 9, "机房配套", fontFormat_Content));
			sheet.mergeCells(1, 9, 1, 11);
			sheet.addCell(new Label(2, 9, "机房照明设施是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 9, checkbox(bean.getD6()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 9, bean.getComment6(),
					fontFormat_Content));
			// 第10行
			sheet.addCell(new Label(2, 10, "机房插座是否有电", fontFormat_Content));
			sheet.addCell(new Label(3, 10, checkbox(bean.getD7()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 10, bean.getComment7(),
					fontFormat_Content));
			// 第11行
			sheet.addCell(new Label(2, 10, "灭火设备是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 10, checkbox(bean.getD8()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 10, bean.getComment8(),
					fontFormat_Content));
			// 第12行
			sheet.addCell(new Label(0, 11, "主体设备", fontFormat_Content));
			sheet.mergeCells(0, 11, 0, 16);
			sheet.addCell(new Label(1, 11, "基站运行状态", fontFormat_Content));
			sheet.addCell(new Label(2, 11, "基站设备是否正常运行，供电是否正常，设备有无明显告警",
					fontFormat_Content));
			sheet.addCell(new Label(3, 11, checkbox(bean.getD9()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 11, bean.getComment9(),
					fontFormat_Content));
			// 第13行
			sheet.addCell(new Label(1, 12, "告警确认", fontFormat_Content));
			sheet.addCell(new Label(2, 12, "通过与监控后台联系确认设备运行是否正常",
					fontFormat_Content));
			sheet.addCell(new Label(3, 12, checkbox(bean.getD10()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 12, bean.getComment10(),
					fontFormat_Content));
			// 第14行
			sheet.addCell(new Label(1, 13, "风扇运行检查", fontFormat_Content));
			sheet.addCell(new Label(2, 13, "检查风扇是否正常运转、无告警", fontFormat_Content));
			sheet.addCell(new Label(3, 13, checkbox(bean.getD11()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 13, bean.getComment11(),
					fontFormat_Content));
			// 第15行
			sheet.addCell(new Label(1, 14, "检查各连线状态", fontFormat_Content));
			sheet.addCell(new Label(2, 14,
					"电源/信号/射频/地线/网线是否分开，机架内各连线是否接触良好并正确无误", fontFormat_Content));
			sheet.addCell(new Label(3, 14, checkbox(bean.getD12()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 14, bean.getComment12(),
					fontFormat_Content));
			// 第16行
			sheet.addCell(new Label(1, 15, "设备加固", fontFormat_Content));
			sheet.addCell(new Label(2, 15, "各设备是否安装牢固", fontFormat_Content));
			sheet.addCell(new Label(3, 15, checkbox(bean.getD13()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 15, bean.getComment13(),
					fontFormat_Content));
			// 第17行
			sheet.addCell(new Label(1, 16, "设备温度", fontFormat_Content));
			sheet.addCell(new Label(2, 16, "各设备是否有高温、异味", fontFormat_Content));
			sheet.addCell(new Label(3, 16, checkbox(bean.getD14()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 16, bean.getComment14(),
					fontFormat_Content));
			// 第18行
			sheet.addCell(new Label(0, 17, "空调", fontFormat_Content));
			sheet.mergeCells(0, 17, 0, 18);
			sheet.addCell(new Label(1, 17, "制冷效果", fontFormat_Content));
			sheet.addCell(new Label(2, 17, "空调制冷效果是否正常", fontFormat_Content));
			sheet.addCell(new Label(3, 17, checkbox(bean.getD15()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 17, bean.getComment16(),
					fontFormat_Content));
			// 第19行
			sheet.addCell(new Label(1, 18, "温度设置", fontFormat_Content));
			sheet.addCell(new Label(2, 18, "机房温度是否正常（建议空调设置25℃，最终情况以机房环境为主）",
					fontFormat_Content));
			sheet.addCell(new Label(3, 18, checkbox(bean.getD16()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 18, bean.getComment16(),
					fontFormat_Content));
			// 第20行
			sheet.addCell(new Label(0, 19, "功能", fontFormat_Content));
			sheet.addCell(new Label(1, 19, "呼叫功能", fontFormat_Content));
			sheet.addCell(new Label(2, 19, "沿途呼叫测试", fontFormat_Content));
			sheet.addCell(new Label(3, 19, checkbox(bean.getD17()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 19, bean.getComment17(),
					fontFormat_Content));
			// 第20行
			sheet.addCell(new Label(0, 20, "遗留问题", fontFormat_Content));
			sheet.addCell(new Label(1, 20, bean.getQuestion(),
					fontFormat_Content));
			sheet.mergeCells(1, 20, 4, 20);
			sheet.setRowView(20, 600);
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void excel_net(File file, InspectionNetBean bean) {

		try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 15, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体

			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义

			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);

			// 设置其他样式

			wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
			wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcf_title.setBackground(Colour.WHITE);// 背景色
			wcf_title.setWrap(false);// 不自动换行

			fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			WritableSheet sheet = book.createSheet("网管巡检表", 0);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			// 第1行
			Label title = new Label(0, 0, "网管巡检表", fontFormat);
			sheet.mergeCells(0, 0, 7, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE,
					PaperSize.A4, 0.5d, 0.5d);
			sheet.getSettings().setLeftMargin(0.50);
			sheet.getSettings().setRightMargin(0.50);
			sheet.getSettings().setTopMargin(0.5);
			sheet.getSettings().setBottomMargin(0.5);

			// 第2行
			Label label_1_0 = new Label(0, 1, "网管名称", fontFormat_h);// 创建单元格
			Label label_1_1 = new Label(1, 1, bean.getManagername(),
					fontFormat_Content);// 创建单元格
			Label label_1_2 = new Label(2, 1, "地点", fontFormat_h);// 创建单元格
			Label label_1_3 = new Label(3, 1, bean.getManagerplace(),
					fontFormat_Content);// 创建单元格
			Label label_1_4 = new Label(4, 1, "巡检时间", fontFormat_h);// 创建单元格
			Label label_1_5 = new Label(5, 1, bean.getCommitdate().split(" ")[0],
					fontFormat_Content);// 创建单元格
			Label label_1_6 = new Label(6, 1, "巡检人", fontFormat_h);// 创建单元格
			Label label_1_7 = new Label(7, 1, bean.getCheckman(),
					fontFormat_Content);// 创建单元格

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 25);
			sheet.setColumnView(2, 10);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 10);
			sheet.setColumnView(7, 20);

			sheet.addCell(label_1_0);
			sheet.addCell(label_1_1);
			sheet.addCell(label_1_2);
			sheet.addCell(label_1_3);
			sheet.addCell(label_1_4);
			sheet.addCell(label_1_5);
			sheet.addCell(label_1_6);
			sheet.addCell(label_1_7);

			// 第3行
			Label label_2_0 = new Label(0, 2, "序号", fontFormat_Content);// 创建单元格
			Label label_2_1 = new Label(1, 2, "检查类容", fontFormat_Content);// 创建单元格
			Label label_2_2 = new Label(2, 2, "备注", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 2, 3, 2);
			Label label_2_3 = new Label(4, 2, "检查情况", fontFormat_Content);// 创建单元格
			Label label_2_4 = new Label(5, 2, "问题描述", fontFormat_Content);// 创建单元格
			Label label_2_5 = new Label(6, 2, "处理情况及遗留问题", fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 2, 7, 2);
			sheet.addCell(label_2_0);
			sheet.addCell(label_2_1);
			sheet.addCell(label_2_2);
			sheet.addCell(label_2_3);
			sheet.addCell(label_2_4);
			sheet.addCell(label_2_5);

			// 第4行
			Label label_3_0 = new Label(0, 3, "1", fontFormat_Content);// 创建单元格
			Label label_3_1 = new Label(1, 3, "网管安装环境是否完成正常",
					fontFormat_Content);// 创建单元格
			Label label_3_2 = new Label(2, 3, bean.getComment1(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 3, 3, 3);
			Label label_3_3 = new Label(4, 3, checkbox(bean.getD1()),
					fontFormat_Content);// 创建单元格
			Label label_3_4 = new Label(5, 3, bean.getP1(), fontFormat_Content);// 创建单元格
			Label label_3_5 = new Label(6, 3, bean.getR1(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 3, 7, 3);
			sheet.addCell(label_3_0);
			sheet.addCell(label_3_1);
			sheet.addCell(label_3_2);
			sheet.addCell(label_3_3);
			sheet.addCell(label_3_4);
			sheet.addCell(label_3_5);
			// 第5行
			Label label_4_0 = new Label(0, 4, "2", fontFormat_Content);// 创建单元格
			Label label_4_1 = new Label(1, 4, "网管电源是否正常开启", fontFormat_Content);// 创建单元格
			Label label_4_2 = new Label(2, 4, bean.getComment2(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 4, 3, 4);
			Label label_4_3 = new Label(4, 4, checkbox(bean.getD2()),
					fontFormat_Content);// 创建单元格
			Label label_4_4 = new Label(5, 4, bean.getP2(), fontFormat_Content);// 创建单元格
			Label label_4_5 = new Label(6, 4, bean.getR2(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 4, 7, 4);
			sheet.addCell(label_4_0);
			sheet.addCell(label_4_1);
			sheet.addCell(label_4_2);
			sheet.addCell(label_4_3);
			sheet.addCell(label_4_4);
			sheet.addCell(label_4_5);
			// 第6行
			Label label_5_0 = new Label(0, 5, "3", fontFormat_Content);// 创建单元格
			Label label_5_1 = new Label(1, 5, "网管是否正常登录", fontFormat_Content);// 创建单元格
			Label label_5_2 = new Label(2, 5, bean.getComment3(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 5, 3, 5);
			Label label_5_3 = new Label(4, 5, checkbox(bean.getD3()),
					fontFormat_Content);// 创建单元格
			Label label_5_4 = new Label(5, 5, bean.getP3(), fontFormat_Content);// 创建单元格
			Label label_5_5 = new Label(6, 5, bean.getR3(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 5, 7, 5);
			sheet.addCell(label_5_0);
			sheet.addCell(label_5_1);
			sheet.addCell(label_5_2);
			sheet.addCell(label_5_3);
			sheet.addCell(label_5_4);
			sheet.addCell(label_5_5);
			// 第7行
			Label label_6_0 = new Label(0, 6, "4", fontFormat_Content);// 创建单元格
			Label label_6_1 = new Label(1, 6, "配置管理查看是否正常", fontFormat_Content);// 创建单元格
			Label label_6_2 = new Label(2, 6, bean.getComment4(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 6, 3, 6);
			Label label_6_3 = new Label(4, 6, checkbox(bean.getD4()),
					fontFormat_Content);// 创建单元格
			Label label_6_4 = new Label(5, 6, bean.getP4(), fontFormat_Content);// 创建单元格
			Label label_6_5 = new Label(6, 6, bean.getR4(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 6, 7, 6);
			sheet.addCell(label_6_0);
			sheet.addCell(label_6_1);
			sheet.addCell(label_6_2);
			sheet.addCell(label_6_3);
			sheet.addCell(label_6_4);
			sheet.addCell(label_6_5);
			// 第8行
			Label label_7_0 = new Label(0, 7, "5", fontFormat_Content);// 创建单元格
			Label label_7_1 = new Label(1, 7, "用户管理查看是否正常", fontFormat_Content);// 创建单元格
			Label label_7_2 = new Label(2, 7, bean.getComment5(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 7, 3, 7);
			Label label_7_3 = new Label(4, 7, checkbox(bean.getD5()),
					fontFormat_Content);// 创建单元格
			Label label_7_4 = new Label(5, 7, bean.getP5(), fontFormat_Content);// 创建单元格
			Label label_7_5 = new Label(6, 7, bean.getR5(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 7, 7, 7);
			sheet.addCell(label_7_0);
			sheet.addCell(label_7_1);
			sheet.addCell(label_7_2);
			sheet.addCell(label_7_3);
			sheet.addCell(label_7_4);
			sheet.addCell(label_7_5);
			// 第9行
			Label label_8_0 = new Label(0, 8, "6", fontFormat_Content);// 创建单元格
			Label label_8_1 = new Label(1, 8, "故障管理查看是否正常", fontFormat_Content);// 创建单元格
			Label label_8_2 = new Label(2, 8, bean.getComment6(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 8, 3, 8);
			Label label_8_3 = new Label(4, 8, checkbox(bean.getD6()),
					fontFormat_Content);// 创建单元格
			Label label_8_4 = new Label(5, 8, bean.getP6(), fontFormat_Content);// 创建单元格
			Label label_8_5 = new Label(6, 8, bean.getR6(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 8, 7, 8);
			sheet.addCell(label_8_0);
			sheet.addCell(label_8_1);
			sheet.addCell(label_8_2);
			sheet.addCell(label_8_3);
			sheet.addCell(label_8_4);
			sheet.addCell(label_8_5);
			// 第10行
			Label label_9_0 = new Label(0, 9, "7", fontFormat_Content);// 创建单元格
			Label label_9_1 = new Label(1, 9, "安全管理查看是否正常", fontFormat_Content);// 创建单元格
			Label label_9_2 = new Label(2, 9, bean.getComment7(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 9, 3, 9);
			Label label_9_3 = new Label(4, 9, checkbox(bean.getD7()),
					fontFormat_Content);// 创建单元格
			Label label_9_4 = new Label(5, 9, bean.getP7(), fontFormat_Content);// 创建单元格
			Label label_9_5 = new Label(6, 9, bean.getR7(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 9, 7, 9);
			sheet.addCell(label_9_0);
			sheet.addCell(label_9_1);
			sheet.addCell(label_9_2);
			sheet.addCell(label_9_3);
			sheet.addCell(label_9_4);
			sheet.addCell(label_9_5);
			// 第11行
			Label label_10_0 = new Label(0, 10, "8", fontFormat_Content);// 创建单元格
			Label label_10_1 = new Label(1, 10, "辅助管理查看是否正常",
					fontFormat_Content);// 创建单元格
			Label label_10_2 = new Label(2, 10, bean.getComment8(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 10, 3, 10);
			Label label_10_3 = new Label(4, 10, checkbox(bean.getD8()),
					fontFormat_Content);// 创建单元格
			Label label_10_4 = new Label(5, 10, bean.getP8(),
					fontFormat_Content);// 创建单元格
			Label label_10_5 = new Label(6, 10, bean.getR8(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 10, 7, 10);
			sheet.addCell(label_10_0);
			sheet.addCell(label_10_1);
			sheet.addCell(label_10_2);
			sheet.addCell(label_10_3);
			sheet.addCell(label_10_4);
			sheet.addCell(label_10_5);
			// 第12行
			Label label_11_0 = new Label(0, 11, "9", fontFormat_Content);// 创建单元格
			Label label_11_1 = new Label(1, 11, "性能管理查看是否正常",
					fontFormat_Content);// 创建单元格
			Label label_11_2 = new Label(2, 11, bean.getComment9(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 11, 3, 11);
			Label label_11_3 = new Label(4, 11, checkbox(bean.getD9()),
					fontFormat_Content);// 创建单元格
			Label label_11_4 = new Label(5, 11, bean.getP9(),
					fontFormat_Content);// 创建单元格
			Label label_11_5 = new Label(6, 11, bean.getR9(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 11, 7, 11);
			sheet.addCell(label_11_0);
			sheet.addCell(label_11_1);
			sheet.addCell(label_11_2);
			sheet.addCell(label_11_3);
			sheet.addCell(label_11_4);
			sheet.addCell(label_11_5);
			// 第13行
			Label label_12_0 = new Label(0, 12, "10", fontFormat_Content);// 创建单元格
			Label label_12_1 = new Label(1, 12, "拓扑管理查看是否正常",
					fontFormat_Content);// 创建单元格
			Label label_12_2 = new Label(2, 12, bean.getComment10(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 12, 3, 12);
			Label label_12_3 = new Label(4, 12, checkbox(bean.getD10()),
					fontFormat_Content);// 创建单元格
			Label label_12_4 = new Label(5, 12, bean.getP10(),
					fontFormat_Content);// 创建单元格
			Label label_12_5 = new Label(6, 12, bean.getR10(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 12, 7, 12);
			sheet.addCell(label_12_0);
			sheet.addCell(label_12_1);
			sheet.addCell(label_12_2);
			sheet.addCell(label_12_3);
			sheet.addCell(label_12_4);
			sheet.addCell(label_12_5);
			// 第14行
			Label label_13_0 = new Label(0, 13, "11", fontFormat_Content);// 创建单元格
			Label label_13_1 = new Label(1, 13, "环境温度是否工作正常",
					fontFormat_Content);// 创建单元格
			Label label_13_2 = new Label(2, 13, bean.getComment11(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 13, 3, 13);
			Label label_13_3 = new Label(4, 13, checkbox(bean.getD11()),
					fontFormat_Content);// 创建单元格
			Label label_13_4 = new Label(5, 13, bean.getP11(),
					fontFormat_Content);// 创建单元格
			Label label_13_5 = new Label(6, 13, bean.getR11(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 13, 7, 13);
			sheet.addCell(label_13_0);
			sheet.addCell(label_13_1);
			sheet.addCell(label_13_2);
			sheet.addCell(label_13_3);
			sheet.addCell(label_13_4);
			sheet.addCell(label_13_5);

			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void excel_dispatch(File file, InspectionDispatchBean bean) {
		try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 16, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体

			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义

			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);

			// 设置其他样式

			wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
			wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcf_title.setBackground(Colour.WHITE);// 背景色
			wcf_title.setWrap(false);// 不自动换行

			fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
			 fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			 fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			WritableSheet sheet = book.createSheet("调度台巡检表", 0);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			// 第1行
			Label title = new Label(0, 0, "调度台巡检表", fontFormat);
			sheet.mergeCells(0, 0, 7, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE,
					PaperSize.A4, 0.5d, 0.5d);
			sheet.getSettings().setLeftMargin(0.50);
			sheet.getSettings().setRightMargin(0.50);
			sheet.getSettings().setTopMargin(0.5);
			sheet.getSettings().setBottomMargin(0.5);

			// 第2行
			Label label_1_0 = new Label(0, 1, "调度台名称", fontFormat_h);// 创建单元格
			Label label_1_1 = new Label(1, 1, bean.getDispatchname(),
					fontFormat_Content);// 创建单元格
			Label label_1_2 = new Label(2, 1, "地点", fontFormat_h);// 创建单元格
			Label label_1_3 = new Label(3, 1, bean.getDispatchplace(),
					fontFormat_Content);// 创建单元格
			Label label_1_4 = new Label(4, 1, "巡检时间", fontFormat_h);// 创建单元格
			Label label_1_5 = new Label(5, 1, bean.getCommitdate().split(" ")[0],
					fontFormat_Content);// 创建单元格
			Label label_1_6 = new Label(6, 1, "巡检人", fontFormat_h);// 创建单元格
			Label label_1_7 = new Label(7, 1, bean.getCheckman(),
					fontFormat_Content);// 创建单元格

			sheet.setColumnView(0, 13);
			sheet.setColumnView(1, 28);
			sheet.setColumnView(2, 10);
			sheet.setColumnView(3, 14);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 30);
			sheet.setColumnView(6, 10);
			sheet.setColumnView(7, 10);

			sheet.addCell(label_1_0);
			sheet.addCell(label_1_1);
			sheet.addCell(label_1_2);
			sheet.addCell(label_1_3);
			sheet.addCell(label_1_4);
			sheet.addCell(label_1_5);
			sheet.addCell(label_1_6);
			sheet.addCell(label_1_7);

			// 第3行
			Label label_2_0 = new Label(0, 2, "序号", fontFormat_Content);// 创建单元格
			Label label_2_1 = new Label(1, 2, "检查类容", fontFormat_Content);// 创建单元格
			Label label_2_2 = new Label(2, 2, "备注", fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 2, 3, 2);
			Label label_2_3 = new Label(4, 2, "检查情况", fontFormat_Content);// 创建单元格
			Label label_2_4 = new Label(5, 2, "问题描述", fontFormat_Content);// 创建单元格
			Label label_2_5 = new Label(6, 2, "处理情况及遗留问题", fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 2, 7, 2);
			sheet.addCell(label_2_0);
			sheet.addCell(label_2_1);
			sheet.addCell(label_2_2);
			sheet.addCell(label_2_3);
			sheet.addCell(label_2_4);
			sheet.addCell(label_2_5);

			// 第4行
			Label label_3_0 = new Label(0, 3, "1", fontFormat_Content);// 创建单元格
			Label label_3_1 = new Label(1, 3, "调度台安装环境是否完成正常",
					fontFormat_Content);// 创建单元格
			Label label_3_2 = new Label(2, 3, bean.getComment1(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 3, 3, 3);
			Label label_3_3 = new Label(4, 3, checkbox(bean.getD1()),
					fontFormat_Content);// 创建单元格
			Label label_3_4 = new Label(5, 3, bean.getP1(), fontFormat_Content);// 创建单元格
			Label label_3_5 = new Label(6, 3, bean.getR1(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 3, 7, 3);
			sheet.addCell(label_3_0);
			sheet.addCell(label_3_1);
			sheet.addCell(label_3_2);
			sheet.addCell(label_3_3);
			sheet.addCell(label_3_4);
			sheet.addCell(label_3_5);
			// 第5行
			Label label_4_0 = new Label(0, 4, "2", fontFormat_Content);// 创建单元格
			Label label_4_1 = new Label(1, 4, "调度台电源是否正常开启", fontFormat_Content);// 创建单元格
			Label label_4_2 = new Label(2, 4, bean.getComment2(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 4, 3, 4);
			Label label_4_3 = new Label(4, 4, checkbox(bean.getD2()),
					fontFormat_Content);// 创建单元格
			Label label_4_4 = new Label(5, 4, bean.getP2(), fontFormat_Content);// 创建单元格
			Label label_4_5 = new Label(6, 4, bean.getR2(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 4, 7, 4);
			sheet.addCell(label_4_0);
			sheet.addCell(label_4_1);
			sheet.addCell(label_4_2);
			sheet.addCell(label_4_3);
			sheet.addCell(label_4_4);
			sheet.addCell(label_4_5);
			// 第6行
			Label label_5_0 = new Label(0, 5, "3", fontFormat_Content);// 创建单元格
			Label label_5_1 = new Label(1, 5, "调度台是否正常登录", fontFormat_Content);// 创建单元格
			Label label_5_2 = new Label(2, 5, bean.getComment3(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 5, 3, 5);
			Label label_5_3 = new Label(4, 5, checkbox(bean.getD3()),
					fontFormat_Content);// 创建单元格
			Label label_5_4 = new Label(5, 5, bean.getP3(), fontFormat_Content);// 创建单元格
			Label label_5_5 = new Label(6, 5, bean.getR3(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 5, 7, 5);
			sheet.addCell(label_5_0);
			sheet.addCell(label_5_1);
			sheet.addCell(label_5_2);
			sheet.addCell(label_5_3);
			sheet.addCell(label_5_4);
			sheet.addCell(label_5_5);
			// 第7行
			Label label_6_0 = new Label(0, 6, "4", fontFormat_Content);// 创建单元格
			Label label_6_1 = new Label(1, 6, "调度台配置是否正常", fontFormat_Content);// 创建单元格
			Label label_6_2 = new Label(2, 6, bean.getComment4(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 6, 3, 6);
			Label label_6_3 = new Label(4, 6, checkbox(bean.getD4()),
					fontFormat_Content);// 创建单元格
			Label label_6_4 = new Label(5, 6, bean.getP4(), fontFormat_Content);// 创建单元格
			Label label_6_5 = new Label(6, 6, bean.getR4(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 6, 7, 6);
			sheet.addCell(label_6_0);
			sheet.addCell(label_6_1);
			sheet.addCell(label_6_2);
			sheet.addCell(label_6_3);
			sheet.addCell(label_6_4);
			sheet.addCell(label_6_5);
			// 第8行
			Label label_7_0 = new Label(0, 7, "5", fontFormat_Content);// 创建单元格
			Label label_7_1 = new Label(1, 7, "调度台录音是否正常", fontFormat_Content);// 创建单元格
			Label label_7_2 = new Label(2, 7, bean.getComment5(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 7, 3, 7);
			Label label_7_3 = new Label(4, 7, checkbox(bean.getD5()),
					fontFormat_Content);// 创建单元格
			Label label_7_4 = new Label(5, 7, bean.getP5(), fontFormat_Content);// 创建单元格
			Label label_7_5 = new Label(6, 7, bean.getR5(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 7, 7, 7);
			sheet.addCell(label_7_0);
			sheet.addCell(label_7_1);
			sheet.addCell(label_7_2);
			sheet.addCell(label_7_3);
			sheet.addCell(label_7_4);
			sheet.addCell(label_7_5);
			// 第9行
			Label label_8_0 = new Label(0, 8, "6", fontFormat_Content);// 创建单元格
			Label label_8_1 = new Label(1, 8, "语音、短信业务测试是否正常",
					fontFormat_Content);// 创建单元格
			Label label_8_2 = new Label(2, 8, bean.getComment6(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 8, 3, 8);
			Label label_8_3 = new Label(4, 8, checkbox(bean.getD6()),
					fontFormat_Content);// 创建单元格
			Label label_8_4 = new Label(5, 8, bean.getP6(), fontFormat_Content);// 创建单元格
			Label label_8_5 = new Label(6, 8, bean.getR6(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 8, 7, 8);
			sheet.addCell(label_8_0);
			sheet.addCell(label_8_1);
			sheet.addCell(label_8_2);
			sheet.addCell(label_8_3);
			sheet.addCell(label_8_4);
			sheet.addCell(label_8_5);
			// 第10行
			Label label_9_0 = new Label(0, 9, "7", fontFormat_Content);// 创建单元格
			Label label_9_1 = new Label(1, 9, "调度业务测试是否正常", fontFormat_Content);// 创建单元格
			Label label_9_2 = new Label(2, 9, bean.getComment7(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 9, 3, 9);
			Label label_9_3 = new Label(4, 9, checkbox(bean.getD7()),
					fontFormat_Content);// 创建单元格
			Label label_9_4 = new Label(5, 9, bean.getP7(), fontFormat_Content);// 创建单元格
			Label label_9_5 = new Label(6, 9, bean.getR7(), fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 9, 7, 9);
			sheet.addCell(label_9_0);
			sheet.addCell(label_9_1);
			sheet.addCell(label_9_2);
			sheet.addCell(label_9_3);
			sheet.addCell(label_9_4);
			sheet.addCell(label_9_5);
			// 第11行
			Label label_10_0 = new Label(0, 10, "8", fontFormat_Content);// 创建单元格
			Label label_10_1 = new Label(1, 10, "环境温度是否工作正常",
					fontFormat_Content);// 创建单元格
			Label label_10_2 = new Label(2, 10, bean.getComment8(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(2, 10, 3, 10);
			Label label_10_3 = new Label(4, 10, checkbox(bean.getD8()),
					fontFormat_Content);// 创建单元格
			Label label_10_4 = new Label(5, 10, bean.getP8(),
					fontFormat_Content);// 创建单元格
			Label label_10_5 = new Label(6, 10, bean.getR8(),
					fontFormat_Content);// 创建单元格
			sheet.mergeCells(6, 10, 7, 10);
			sheet.addCell(label_10_0);
			sheet.addCell(label_10_1);
			sheet.addCell(label_10_2);
			sheet.addCell(label_10_3);
			sheet.addCell(label_10_4);
			sheet.addCell(label_10_5);

			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void excel_msc(File file, InspectionMscBean bean) {

		try {
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 15, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体

			WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色

			WritableCellFormat wcf_title = new WritableCellFormat(wf_title); // 单元格定义

			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);

			// 设置其他样式

			wcf_title.setAlignment(Alignment.CENTRE);// 水平对齐
			wcf_title.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			wcf_title.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			wcf_title.setBackground(Colour.WHITE);// 背景色
			wcf_title.setWrap(false);// 不自动换行

			fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##"); // 设置数字格式
			WritableSheet sheet = book.createSheet("网管巡检表", 0);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			// 第1行
			Label title = new Label(0, 0, "交换中心月度维护", fontFormat);
			sheet.mergeCells(0, 0, 6, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE,
					PaperSize.A4, 0.5d, 0.5d);
			sheet.getSettings().setLeftMargin(0.50);
			sheet.getSettings().setRightMargin(0.50);
			sheet.getSettings().setTopMargin(0.5);
			sheet.getSettings().setBottomMargin(0.5);

			// 第2行

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 30);
			sheet.setColumnView(2, 25);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 15);
			sheet.setColumnView(5, 15);
			sheet.setColumnView(6, 15);

			sheet.addCell(new Label(0, 1, "系統名称：成都市应急指挥调度无线通信网",
					fontFormat_Content));
			sheet.mergeCells(0, 1, 1, 1);
			sheet.addCell(new Label(2, 1, "地点：亚光高新产业园", fontFormat_Content));
			sheet.addCell(new Label(3, 1, "巡检时间", fontFormat_Content));
			sheet.addCell(new Label(4, 1, bean.getCheckDate(),
					fontFormat_Content));
			sheet.addCell(new Label(5, 1, "巡检人", fontFormat_Content));
			sheet.addCell(new Label(6, 1, bean.getCheckPersion(),
					fontFormat_Content));
			// 第3行
			// sheet.mergeCells(6, 2, 7, 2);

			sheet.addCell(new Label(0, 2, "序号", fontFormat_h));
			sheet.addCell(new Label(1, 2, "检查类容", fontFormat_h));
			sheet.addCell(new Label(2, 2, "检查情况", fontFormat_h));
			sheet.addCell(new Label(3, 2, "问题描述", fontFormat_h));
			sheet.mergeCells(3, 2, 4, 2);
			sheet.addCell(new Label(5, 2, "处理情况及遗留问题", fontFormat_h));
			sheet.mergeCells(5, 2, 6, 2);

			// 第4行
			sheet.addCell(new Label(0, 3, "1", fontFormat_Content));
			sheet.addCell(new Label(1, 3, "机房门窗地面墙壁等是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 3, checkbox(bean.getA1()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 3, bean.getB1(), fontFormat_Content));
			sheet.mergeCells(3, 3, 4, 3);
			sheet.addCell(new Label(5, 3, bean.getC1(), fontFormat_Content));
			sheet.mergeCells(5, 3, 6, 3);
			// 第5行
			sheet.addCell(new Label(0, 4, "2", fontFormat_Content));
			sheet.addCell(new Label(1, 4, "机房照明、电源插座是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 4, checkbox(bean.getA2()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 4, bean.getB2(), fontFormat_Content));
			sheet.mergeCells(3, 4, 4, 4);
			sheet.addCell(new Label(5, 4, bean.getC2(), fontFormat_Content));
			sheet.mergeCells(5, 4, 6, 4);
			// 第6行
			sheet.addCell(new Label(0, 5, "3", fontFormat_Content));
			sheet.addCell(new Label(1, 5, "设备灰尘及滤网是否清洁(传输3500等)",
					fontFormat_Content));
			sheet.addCell(new Label(2, 5, checkbox(bean.getA3()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 5, bean.getB3(), fontFormat_Content));
			sheet.mergeCells(3, 5, 4, 5);
			sheet.addCell(new Label(5, 5, bean.getC3(), fontFormat_Content));
			sheet.mergeCells(5, 5, 6, 5);
			// 第5行
			sheet.addCell(new Label(0, 6, "4", fontFormat_Content));
			sheet.addCell(new Label(1, 6, "机房是否清洁、是否没有杂物或易燃物品",
					fontFormat_Content));
			sheet.addCell(new Label(2, 6, checkbox(bean.getA4()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 6, bean.getB4(), fontFormat_Content));
			sheet.mergeCells(3, 6, 4, 6);
			sheet.addCell(new Label(5, 6, bean.getC4(), fontFormat_Content));
			sheet.mergeCells(5, 6, 6, 6);
			// 第5行
			sheet.addCell(new Label(0, 7, "5", fontFormat_Content));
			sheet.addCell(new Label(1, 7, "消防设备是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 7, checkbox(bean.getA5()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 7, bean.getB5(), fontFormat_Content));
			sheet.mergeCells(3, 7, 4, 7);
			sheet.addCell(new Label(5, 7, bean.getC5(), fontFormat_Content));
			sheet.mergeCells(5, 7, 6, 7);
			// 第5行
			sheet.addCell(new Label(0, 8, "6", fontFormat_Content));
			sheet.addCell(new Label(1, 8, "UPS是否正常（断电测试）", fontFormat_Content));
			sheet.addCell(new Label(2, 8, checkbox(bean.getA6()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 8, bean.getB6(), fontFormat_Content));
			sheet.mergeCells(3, 8, 4, 8);
			sheet.addCell(new Label(5, 8, bean.getC6(), fontFormat_Content));
			sheet.mergeCells(5, 8, 6, 8);
			// 第5行
			sheet.addCell(new Label(0, 9, "7", fontFormat_Content));
			sheet.addCell(new Label(1, 9, "蓄电池是否正常（发电测试）", fontFormat_Content));
			sheet.addCell(new Label(2, 9, checkbox(bean.getA7()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 9, bean.getB7(), fontFormat_Content));
			sheet.mergeCells(3, 9, 4, 9);
			sheet.addCell(new Label(5, 9, bean.getC7(), fontFormat_Content));
			sheet.mergeCells(5, 9, 6, 9);
			// 第5行
			sheet.addCell(new Label(0, 10, "8", fontFormat_Content));
			sheet.addCell(new Label(1, 10, "发电机是否正常（包括汽油发电机、柴油发电机）",
					fontFormat_Content));
			sheet.addCell(new Label(2, 10, checkbox(bean.getA8()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 10, bean.getB8(), fontFormat_Content));
			sheet.mergeCells(3, 10, 4, 10);
			sheet.addCell(new Label(5, 10, bean.getC8(), fontFormat_Content));
			sheet.mergeCells(5, 10, 6, 10);
			// 第5行
			sheet.addCell(new Label(0, 11, "9", fontFormat_Content));
			sheet.addCell(new Label(1, 11, "空调是否正常工作、滤网是否清洁",
					fontFormat_Content));
			sheet.addCell(new Label(2, 11, checkbox(bean.getA9()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 11, bean.getB9(), fontFormat_Content));
			sheet.mergeCells(3, 11, 4, 11);
			sheet.addCell(new Label(5, 11, bean.getC9(), fontFormat_Content));
			sheet.mergeCells(5, 11, 6, 11);
			// 第5行
			sheet.addCell(new Label(0, 12, "10", fontFormat_Content));
			sheet.addCell(new Label(1, 12, "环境监控系统是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 12, checkbox(bean.getA10()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 12, bean.getB10(), fontFormat_Content));
			sheet.mergeCells(3, 12, 4, 12);
			sheet.addCell(new Label(5, 12, bean.getC10(), fontFormat_Content));
			sheet.mergeCells(5, 12, 6, 12);
			// 第5行
			sheet.addCell(new Label(0, 13, "11", fontFormat_Content));
			sheet.addCell(new Label(1, 13, "电源线是否老化", fontFormat_Content));
			sheet.addCell(new Label(2, 13, checkbox(bean.getA11()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 13, bean.getB11(), fontFormat_Content));
			sheet.mergeCells(3, 13, 4, 13);
			sheet.addCell(new Label(5, 13, bean.getC11(), fontFormat_Content));
			sheet.mergeCells(5, 13, 6, 13);
			// 第5行
			sheet.addCell(new Label(0, 14, "12", fontFormat_Content));
			sheet.addCell(new Label(1, 14, "设备标签是否规范完、完整、走线是否正常",
					fontFormat_Content));
			sheet.addCell(new Label(2, 14, checkbox(bean.getA12()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 14, bean.getB12(), fontFormat_Content));
			sheet.mergeCells(3, 14, 4, 14);
			sheet.addCell(new Label(5, 14, bean.getC12(), fontFormat_Content));
			sheet.mergeCells(5, 14, 6, 14);
			// 第5行
			sheet.addCell(new Label(0, 15, "13", fontFormat_Content));
			sheet.addCell(new Label(1, 15, "服务器是否运行正常", fontFormat_Content));
			sheet.addCell(new Label(2, 15, checkbox(bean.getA13()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 15, bean.getB13(), fontFormat_Content));
			sheet.mergeCells(3, 15, 4, 15);
			sheet.addCell(new Label(5, 15, bean.getC13(), fontFormat_Content));
			sheet.mergeCells(5, 15, 6, 15);
			// 第5行
			sheet.addCell(new Label(0, 16, "14", fontFormat_Content));
			sheet.addCell(new Label(1, 16, "设备接地是否正常", fontFormat_Content));
			sheet.addCell(new Label(2, 16, checkbox(bean.getA14()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 16, bean.getB14(), fontFormat_Content));
			sheet.mergeCells(3, 16, 4, 16);
			sheet.addCell(new Label(5, 16, bean.getC14(), fontFormat_Content));
			sheet.mergeCells(5, 16, 6, 16);
			// 第5行
			sheet.addCell(new Label(0, 17, "15", fontFormat_Content));
			sheet.addCell(new Label(1, 17, "服务器、单板、模块安装是否牢固可靠",
					fontFormat_Content));
			sheet.addCell(new Label(2, 17, checkbox(bean.getA15()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 17, bean.getB15(), fontFormat_Content));
			sheet.mergeCells(3, 17, 4, 17);
			sheet.addCell(new Label(5, 17, bean.getC15(), fontFormat_Content));
			sheet.mergeCells(5, 17, 6, 17);
			// 第5行
			sheet.addCell(new Label(0, 18, "16", fontFormat_Content));
			sheet.addCell(new Label(1, 18, "楼顶吸盘天线是否加固，接头是否正常",
					fontFormat_Content));
			sheet.addCell(new Label(2, 18, checkbox(bean.getA16()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 18, bean.getB16(), fontFormat_Content));
			sheet.mergeCells(3, 18, 4, 18);
			sheet.addCell(new Label(5, 18, bean.getC16(), fontFormat_Content));
			sheet.mergeCells(5, 18, 6, 18);
			// 第5行
			sheet.addCell(new Label(0, 19, "17", fontFormat_Content));
			sheet.addCell(new Label(1, 19, "服务器数据及配置是否备份", fontFormat_Content));
			sheet.addCell(new Label(2, 19, checkbox(bean.getA17()),
					fontFormat_Content));
			sheet.addCell(new Label(3, 19, bean.getB17(), fontFormat_Content));
			sheet.mergeCells(3, 19, 4, 19);
			sheet.addCell(new Label(5, 19, bean.getC17(), fontFormat_Content));
			sheet.mergeCells(5, 19, 6, 19);

			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void excel_star(File file, InspectionStarBean bean) {

		try {

			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableFont font = new WritableFont(
					WritableFont.createFont("微软雅黑"), 15, WritableFont.NO_BOLD,
					false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat fontFormat = new WritableCellFormat(font);
			fontFormat.setAlignment(Alignment.CENTRE); // 水平居中
			fontFormat.setVerticalAlignment(VerticalAlignment.JUSTIFY);// 垂直居中
			fontFormat.setWrap(true); // 自动换行
			fontFormat.setBackground(Colour.WHITE);// 背景颜色
			fontFormat
					.setBorder(Border.ALL, BorderLineStyle.NONE, Colour.BLACK);
			fontFormat.setOrientation(Orientation.HORIZONTAL);// 文字方向

			// 设置头部字体格式
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 11,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.WHITE);// 背景色
			fontFormat_h.setWrap(false);// 不自动换行

			// 设置主题内容字体格式
			WritableFont font_Content = new WritableFont(WritableFont.TIMES,
					11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, Colour.GRAY_80);
			// 应用字体
			WritableCellFormat fontFormat_Content = new WritableCellFormat(
					font_Content);
			fontFormat_Content.setAlignment(Alignment.LEFT);// 水平对齐
			fontFormat_Content.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_Content.setBackground(Colour.WHITE);// 背景色
			fontFormat_Content.setWrap(true);// 自动换行

			// 设置数字格式
			WritableSheet sheet = book.createSheet("卫星通信车载便携站巡检表", 0);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小

			// 第1行
			Label title = new Label(0, 0, "卫星通信车载便携站巡检表", fontFormat);
			sheet.mergeCells(0, 0, 4, 0);
			sheet.addCell(title);
			sheet.setRowView(0, 600, false); // 设置行高
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE, PaperSize.A4, 0.5d, 0.5d);
			sheet.getSettings().setLeftMargin(0.50);
			sheet.getSettings().setRightMargin(0.50);
			sheet.getSettings().setTopMargin(0.1);
			sheet.getSettings().setBottomMargin(0.1);

			// 第2行

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 28);
			sheet.setColumnView(2, 40);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 30);

			sheet.addCell(new Label(0, 1, "基站名称:" + bean.getName(),
					fontFormat_Content));
			sheet.mergeCells(0, 1, 1, 1);
			sheet.addCell(new Label(2, 1, "基站号:" + bean.getBsId(),
					fontFormat_Content));
			sheet.addCell(new Label(3, 1, "基站级别:" + bean.getLevel(),
					fontFormat_Content));
			sheet.addCell(new Label(4, 1, "巡检人:" + bean.getCheckman(),
					fontFormat_Content));

			sheet.addCell(new Label(0, 2, "基站类型:" + checkbox(bean.getType()),
					fontFormat_Content));
			sheet.mergeCells(0, 2, 1, 2);
			sheet.addCell(new Label(2, 2, "巡检时间:" + bean.getCommitdate().split(" ")[0],
					fontFormat_Content));
			sheet.mergeCells(2, 2, 4, 2);

			// 第3行
			// sheet.mergeCells(6, 2, 7, 2);

			sheet.addCell(new Label(0, 3, "巡检项目", fontFormat_h));
			sheet.addCell(new Label(1, 3, "具体项目", fontFormat_h));
			sheet.addCell(new Label(2, 3, "具体操作", fontFormat_h));
			sheet.addCell(new Label(3, 3, "执行情况", fontFormat_h));
			sheet.addCell(new Label(4, 3, "备注", fontFormat_h));

			// 第4行
			sheet.addCell(new Label(0, 4, "天    面", fontFormat_Content));
			sheet.addCell(new Label(1, 4, "防倾斜", fontFormat_Content));
			sheet.addCell(new Label(2, 4, "集群天线是否竖起正常", fontFormat_Content));
			sheet.addCell(new Label(3, 4, checkbox(bean.getD1()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 4, bean.getComment1(),
					fontFormat_Content));
			// 第5行
			sheet.addCell(new Label(0, 5, "卫星设备", fontFormat_Content));
			sheet.mergeCells(0, 5, 0, 7);
			sheet.addCell(new Label(1, 5, "天线", fontFormat_Content));
			sheet.addCell(new Label(2, 5, "卫星天线寻星是否正常", fontFormat_Content));
			sheet.addCell(new Label(3, 5, checkbox(bean.getD2()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 5, bean.getComment2(),
					fontFormat_Content));
			// 第6行
			sheet.addCell(new Label(1, 6, "天线控制器", fontFormat_Content));
			sheet.addCell(new Label(2, 6, "天线对星控制模式、数据是否正常", fontFormat_Content));
			sheet.addCell(new Label(3, 6, checkbox(bean.getD3()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 6, bean.getComment3(),
					fontFormat_Content));
			// 第7行
			sheet.addCell(new Label(1, 7, "卫星调制解调器", fontFormat_Content));
			sheet.addCell(new Label(2, 7, "卫星调制解调器是否正常", fontFormat_Content));
			sheet.addCell(new Label(3, 7, checkbox(bean.getD4()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 7, bean.getComment4(),
					fontFormat_Content));
			// 第8行
			sheet.addCell(new Label(0, 8, "主体设备", fontFormat_Content));
			sheet.mergeCells(0, 8, 0, 10);
			sheet.addCell(new Label(1, 8, "单板状态", fontFormat_Content));
			sheet.addCell(new Label(2, 8, "检查各单板运行状态、供电情况是否正常，设备是否有明显告警	",
					fontFormat_Content));
			sheet.addCell(new Label(3, 8, checkbox(bean.getD5()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 8, bean.getComment5(),
					fontFormat_Content));
			// 第9行
			sheet.addCell(new Label(1, 9, "设备加固", fontFormat_Content));
			sheet.addCell(new Label(2, 9, "设备安装是否牢固", fontFormat_Content));
			sheet.addCell(new Label(3, 9, checkbox(bean.getD6()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 9, bean.getComment6(),
					fontFormat_Content));
			// 第10行
			sheet.addCell(new Label(1, 10, "设备温度", fontFormat_Content));
			sheet.addCell(new Label(2, 10, "设备是否有高温、异味", fontFormat_Content));
			sheet.addCell(new Label(3, 10, checkbox(bean.getD7()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 10, bean.getComment7(),
					fontFormat_Content));
			// 第11行
			// 第12行
			sheet.addCell(new Label(0, 11, "电源配套", fontFormat_Content));
			sheet.mergeCells(0, 11, 0, 17);
			sheet.addCell(new Label(1, 11, "发电机", fontFormat_Content));
			sheet.mergeCells(1, 11, 1, 12);
			sheet.addCell(new Label(2, 11, "发电机是否能正常运行", fontFormat_Content));
			sheet.addCell(new Label(3, 11, checkbox(bean.getD8()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 11, bean.getComment8(),
					fontFormat_Content));
			// 第13行
			sheet.addCell(new Label(2, 12, "发电机及引电线缆有无老化、发热等情况",
					fontFormat_Content));
			sheet.addCell(new Label(3, 12, checkbox(bean.getD9()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 12, bean.getComment9(),
					fontFormat_Content));
			// 第14行
			sheet.addCell(new Label(1, 13, "UPS", fontFormat_Content));
			sheet.mergeCells(1, 13, 1, 15);
			sheet.addCell(new Label(2, 13, "电源连接线有无松动、老化、发热等情况",
					fontFormat_Content));
			sheet.addCell(new Label(3, 13, checkbox(bean.getD10()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 13, bean.getComment10(),
					fontFormat_Content));
			// 第15行
			sheet.addCell(new Label(2, 14, "电池壳体有无渗漏和变形，极柱、安全阀周围是否有酸雾、酸液逸出",
					fontFormat_Content));
			sheet.addCell(new Label(3, 14, checkbox(bean.getD11()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 14, bean.getComment11(),
					fontFormat_Content));
			
			
			
			// 第16行
			sheet.addCell(new Label(2, 15, "断开交流电及发电机电源输入，查看UPS输出是否正常",
					fontFormat_Content));
			sheet.addCell(new Label(3, 15, checkbox(bean.getD12()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 15, bean.getComment12(),
					fontFormat_Content));
			// 第17行
			sheet.addCell(new Label(1, 16, "照明", fontFormat_Content));
			sheet.addCell(new Label(2, 16, "车内照明设施是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 16, checkbox(bean.getD13()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 16, bean.getComment13(),
					fontFormat_Content));
			// 第18行
			sheet.addCell(new Label(1, 17, "插座", fontFormat_Content));
			sheet.addCell(new Label(2, 17, "车内插座是否有电", fontFormat_Content));
			sheet.addCell(new Label(3, 17, checkbox(bean.getD14()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 17, bean.getComment14(),
					fontFormat_Content));
			// 第19行
			sheet.addCell(new Label(0, 18, "隐患检查", fontFormat_Content));
			sheet.mergeCells(0, 18, 0, 23);
			sheet.addCell(new Label(1, 18, "车内安全", fontFormat_Content));
			sheet.mergeCells(1, 18, 1, 22);
			sheet.addCell(new Label(2, 18, "灭火设备是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 18, checkbox(bean.getD15()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 18, bean.getComment15(),
					fontFormat_Content));
			// 第20行
			sheet.addCell(new Label(2, 19, "车门、车窗、车锁是否完好", fontFormat_Content));
			sheet.addCell(new Label(3, 19, checkbox(bean.getD16()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 19, bean.getComment16(),
					fontFormat_Content));
			// 第21行
			sheet.addCell(new Label(2, 20, "车门、车窗、馈线孔等缝隙密封是否严实",
					fontFormat_Content));
			sheet.addCell(new Label(3, 20, checkbox(bean.getD17()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 20, bean.getComment17(),
					fontFormat_Content));
			// 第22行
			sheet.addCell(new Label(2, 21, "空调有无漏水情况", fontFormat_Content));
			sheet.addCell(new Label(3, 21, checkbox(bean.getD18()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 21, bean.getComment18(),
					fontFormat_Content));
			// 第23行
			sheet.addCell(new Label(2, 22, "车体有无裂缝、渗水、漏水等情况",
					fontFormat_Content));
			sheet.addCell(new Label(3, 22, checkbox(bean.getD19()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 22, bean.getComment19(),
					fontFormat_Content));
			// 第24行
			sheet.addCell(new Label(1, 23, "车内温度", fontFormat_Content));
			sheet.addCell(new Label(2, 23, "空调制冷效果及运行状态是否正常",
					fontFormat_Content));
			sheet.addCell(new Label(3, 23, checkbox(bean.getD20()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 23, bean.getComment20(),
					fontFormat_Content));
			// 第25行
			sheet.addCell(new Label(0, 24, "车    辆", fontFormat_Content));
			sheet.mergeCells(0, 24, 0, 28);
			sheet.addCell(new Label(1, 24, "外观", fontFormat_Content));
			sheet.addCell(new Label(2, 24, "车辆外观是否正常，有无擦挂、破损",
					fontFormat_Content));
			sheet.addCell(new Label(3, 24, checkbox(bean.getD21()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 24, bean.getComment21(),
					fontFormat_Content));
			// 第26行
			sheet.addCell(new Label(1, 25, "仪表盘", fontFormat_Content));
			sheet.addCell(new Label(2, 25, "显示是否正常", fontFormat_Content));
			sheet.addCell(new Label(3, 25, checkbox(bean.getD22()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 25, bean.getComment22(),
					fontFormat_Content));
			// 第27行
			sheet.addCell(new Label(1, 26, "指示灯", fontFormat_Content));
			sheet.addCell(new Label(2, 26, "各指示灯是否正常", fontFormat_Content));
			sheet.addCell(new Label(3, 26, checkbox(bean.getD23()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 26, bean.getComment23(),
					fontFormat_Content));
			// 第28行
			sheet.addCell(new Label(1, 27, "操作", fontFormat_Content));
			sheet.addCell(new Label(2, 27, "刹车、启动、运行是否正常", fontFormat_Content));
			sheet.addCell(new Label(3, 27, checkbox(bean.getD24()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 27, bean.getComment24(),
					fontFormat_Content));
			// 第29行
			sheet.addCell(new Label(1, 28, "顶起装置", fontFormat_Content));
			sheet.addCell(new Label(2, 28, "是否收放正常", fontFormat_Content));
			sheet.addCell(new Label(3, 28, checkbox(bean.getD25()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 28, bean.getComment25(),
					fontFormat_Content));
			// 第30行
			sheet.addCell(new Label(0, 29, "清    洁", fontFormat_Content));
			sheet.mergeCells(0, 29, 0, 30);
			sheet.addCell(new Label(1, 29, "车内清洁", fontFormat_Content));
			sheet.addCell(new Label(2, 29, "清洁车内门、窗、地面、桌椅卫生",
					fontFormat_Content));
			sheet.addCell(new Label(3, 29, checkbox(bean.getD26()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 29, bean.getComment26(),
					fontFormat_Content));
			// 第31行
			sheet.addCell(new Label(1, 30, "设备除尘", fontFormat_Content));
			sheet.addCell(new Label(2, 30, "清洁设备表面、内部", fontFormat_Content));
			sheet.addCell(new Label(3, 30, checkbox(bean.getD27()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 30, bean.getComment27(),
					fontFormat_Content));
			// 第32行
			sheet.addCell(new Label(0, 31, "功    能", fontFormat_Content));
			sheet.addCell(new Label(1, 31, "呼叫功能", fontFormat_Content));
			sheet.addCell(new Label(2, 31, "基站覆盖范围内呼叫测试", fontFormat_Content));
			sheet.addCell(new Label(3, 31, checkbox(bean.getD28()),
					fontFormat_Content));
			sheet.addCell(new Label(4, 31, bean.getComment28(),
					fontFormat_Content));
			// 第23行
			sheet.addCell(new Label(0, 32, "遗留问题", fontFormat_Content));
			sheet.addCell(new Label(1, 32, bean.getQuestion(),
					fontFormat_Content));
			sheet.mergeCells(1, 32, 4, 32);
			sheet.setRowView(32, 600);
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public String checkbox(String str) {
		if (str == null) {
			return "";
		} else {
			if (str.equals("有")) {
				return "☑有  口无";
			} else if (str.equals("无")) {
				return "口有   ☑ 无";
			}

			else if (str.equals("是")) {
				return "☑是  口否";
			} else if (str.equals("否")) {
				return "口是   ☑否";
			}

			else if (str.equals("正常")) {
				return "☑正常  口异常";
			} else if (str.equals("异常")) {
				return "口正常   ☑异常";
			}

			else if (str.equals("安全")) {
				return "☑安全  口有隐患";
			} else if (str.equals("有隐患")) {
				return "口安全   ☑有隐患";
			} else if (str.equals("执行")) {
				return "☑ 执行  口未执行";
			} else if (str.equals("未执行")) {
				return "口执行   ☑未执行";
			}

			else if (str.equals("已备份")) {
				return "☑已备份  口未备份";
			} else if (str.equals("未备份")) {
				return "口已备份   ☑未备份";
			}

			else if (str.equals("移动") || str.equals("电信") || str.equals("铁塔")) {
				// return "☑已备份  口未备份";
				return "租凭机房口       运营商机房☑          自建机房□";
			} else if (str.equals("自建")) {
				return "租凭机房口       运营商机房口        自建机房☑";
			} else if (str.equals("租赁")) {
				return " 租凭机房☑        运营商机房口        自建机房口 ";
			}

			else if (str.equals("已执行")) {
				return "☑已执行  口未执行";
			} else if (str.equals("未执行")) {
				return "口已执行   ☑未执行";
			} else if (str.equals("车载")) {
				return "☑车载   口便携";
			} else if (str.equals("便携")) {
				return "口车载   ☑ 便携";
			} else {
				return "";
			}
		}

	}

}
