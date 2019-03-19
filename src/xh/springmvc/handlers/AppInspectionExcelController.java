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
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import xh.mybatis.bean.InspectionSbsBean;
import xh.mybatis.bean.InspectionVerticalBean;

public class AppInspectionExcelController {
	public HashMap<String, Object> app_bs(List<InspectionSbsBean> sbsList,String saveDir,String time,String zipName)
			throws Exception {
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
	
	public void excel_vertical(File file,InspectionVerticalBean bean) {
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
			WritableFont font_header = new WritableFont(WritableFont.TIMES, 9,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK);
			// 应用字体
			WritableCellFormat fontFormat_h = new WritableCellFormat(
					font_header);
			// 设置其他样式
			fontFormat_h.setAlignment(Alignment.CENTRE);// 水平对齐
			fontFormat_h.setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			fontFormat_h.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
			fontFormat_h.setBackground(Colour.BRIGHT_GREEN);// 背景色
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

			/*
			 * fontFormat_Content.setAlignment(Alignment.CENTRE);// 水平对齐
			 * fontFormat_Content
			 * .setVerticalAlignment(VerticalAlignment.CENTRE);// 垂直对齐
			 */fontFormat_Content.setBorder(Border.ALL, BorderLineStyle.THIN);// 边框
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

			// 第2行

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 25);
			sheet.setColumnView(2, 50);
			sheet.setColumnView(3, 30);
			sheet.setColumnView(4, 30);

			sheet.addCell(new Label(0, 1, "基站名称", fontFormat_Content));
			sheet.addCell(new Label(1, 1, bean.getName(), fontFormat_Content));
			sheet.addCell(new Label(2, 1, "巡检人", fontFormat_Content));
			sheet.addCell(new Label(3, 1, bean.getCheckman(),
					fontFormat_Content));
			sheet.mergeCells(3, 1, 4, 1);

			sheet.addCell(new Label(0, 2, "巡检时间", fontFormat_Content));
			sheet.addCell(new Label(1, 2, bean.getCommitdate(),
					fontFormat_Content));
			sheet.addCell(new Label(2, 2, "电表", fontFormat_Content));
			sheet.addCell(new Label(3, 2, bean.getAmmeternumber(),
					fontFormat_Content));
			sheet.mergeCells(3, 2, 4, 2);

			// 第3行
			// sheet.mergeCells(6, 2, 7, 2);

			sheet.addCell(new Label(0, 3, "巡检项目", fontFormat_Content));
			sheet.addCell(new Label(1, 3, "具体项目", fontFormat_Content));
			sheet.addCell(new Label(2, 3, "具体操作", fontFormat_Content));
			sheet.addCell(new Label(3, 3, "执行情况", fontFormat_Content));
			sheet.addCell(new Label(4, 3, "备注", fontFormat_Content));

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
				return "☑  有  口 无";
			} else if (str.equals("无")) {
				return "口 有   ☑ 无";
			}

			else if (str.equals("是")) {
				return "☑ 是  口 否";
			} else if (str.equals("否")) {
				return "口  是   ☑ 否";
			}

			else if (str.equals("正常")) {
				return "☑ 正常  口 异常";
			} else if (str.equals("异常")) {
				return "口  正常   ☑ 异常";
			}

			else if (str.equals("安全")) {
				return "☑ 安全  口 有隐患";
			} else if (str.equals("有隐患")) {
				return "口  安全   ☑ 有隐患";
			} else if (str.equals("执行")) {
				return "☑ 执行  口 未执行";
			} else if (str.equals("未执行")) {
				return "口  执行   ☑ 未执行";
			}

			else if (str.equals("已备份")) {
				return "☑ 已备份  口 未备份";
			} else if (str.equals("未备份")) {
				return "口  已备份   ☑ 未备份";
			}

			else if (str.equals("移动") || str.equals("电信") || str.equals("铁塔")) {
				// return "☑已备份  口未备份";
				return " 租凭机房口       运营商机房☑          自建机房□";
			} else if (str.equals("自建")) {
				return " 租凭机房口       运营商机房口        自建机房☑";
			} else if (str.equals("租赁")) {
				return " 租凭机房☑        运营商机房口        自建机房口 ";
			}

			else if (str.equals("已执行")) {
				return "☑ 已执行  口  未执行";
			} else if (str.equals("未执行")) {
				return "口  已执行   ☑ 未执行";
			} else {
				return "";
			}
		}

	}

}
