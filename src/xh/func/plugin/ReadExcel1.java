package xh.func.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import xh.mybatis.bean.ImpExcelBean;
import xh.mybatis.bean.TempBean;

/**
 * Excel处理函数
 * @author 12878
 *
 */
public class ReadExcel1 {  
    //总行数  
    private int totalRows = 0;    
    //总条数  
    private int totalCells = 0;   
    //错误信息接收器  
    private String errorMsg;  
    //构造方法  
    public ReadExcel1(){}  
    //获取总行数  
    public int getTotalRows()  { return totalRows;}   
    //获取总列数  
    public int getTotalCells() {  return totalCells;}   
    //获取错误信息  
    public String getErrorInfo() { return errorMsg; }    
      
  /** 
   * 读EXCEL文件，获取信息集合 
   * @param fielName 
   * @return 
   */  
    public List<TempBean> getExcelInfo(MultipartFile mFile) {  
        String fileName = mFile.getOriginalFilename();//获取文件名  
        List<TempBean> impExcelBeanList = null;
        try {  
            if (!validateExcel(fileName)) {// 验证文件名是否合格  
                return null;  
            }  
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本  
            if (isExcel2007(fileName)) {  
                isExcel2003 = false;  
            }  
            impExcelBeanList = createExcel(mFile.getInputStream(), isExcel2003);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return impExcelBeanList;  
    }  
    
  /** 
   * 根据excel里面的内容读取客户信息 
   * @param is 输入流 
   * @param isExcel2003 excel是2003还是2007版本 
   * @return 
   * @throws IOException 
   */  
    public List<TempBean> createExcel(InputStream is, boolean isExcel2003) {  
    	List<TempBean> impExcelBeanList = null;
        try{  
            Workbook wb = null;  
            if (isExcel2003) {// 当excel是2003时,创建excel2003  
                wb = new HSSFWorkbook(is);  
            } else {// 当excel是2007时,创建excel2007  
                wb = new XSSFWorkbook(is);  
            }  
            impExcelBeanList = readExcelValue(wb);// 读取Excel里面客户的信息  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return impExcelBeanList;  
    }  
    
  /** 
   * 读取Excel里面客户的信息 
   * @param wb 
   * @return 
   */  
    private List<TempBean> readExcelValue(Workbook wb) {  
        // 得到第一个shell  
        Sheet sheet = wb.getSheetAt(0);  
        // 得到Excel的行数  
        this.totalRows = sheet.getPhysicalNumberOfRows();  
        // 得到Excel的列数(前提是有行数)  
        /*Row r0 =sheet.getRow(0);
        Row r1 =sheet.getRow(1);
        Row r2 =sheet.getRow(2);
        Row r3 =sheet.getRow(3);
        Row r4 =sheet.getRow(4);*/
        
        if (totalRows > 1 && sheet.getRow(2) != null) {  
            this.totalCells = sheet.getRow(2).getPhysicalNumberOfCells();  
        }  
        List<TempBean> impExcelBeanList = new ArrayList<TempBean>();  
        // 循环Excel行数  
        for (int r = 3; r < totalRows-4; r++) {  
            Row row = sheet.getRow(r);  
            if (row == null){  
                continue;  
            }  
            TempBean tempBean = new TempBean();
            // 循环Excel的列  
            for (int c = 0; c < this.totalCells; c++) {  
                Cell cell = row.getCell(c);  
                if (null != cell) {  
                    if (c == 2) {  
                        //如果是纯数字,比如你写的是25,cell.getNumericCellValue()获得是25.0,通过截取字符串去掉.0获得25  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String bsId = String.valueOf(cell.getNumericCellValue());  
                            tempBean.setBsId((bsId.substring(0, bsId.length()-2>0?bsId.length()-2:1)));
                        }else{  
                        	tempBean.setBsId(cell.getStringCellValue());
                        }  
                    } else if (c == 3) {  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String name = String.valueOf(cell.getNumericCellValue());  
                            tempBean.setName((name.substring(0, name.length()-2>0?name.length()-2:1)));
                        }else{  
                        	tempBean.setName(cell.getStringCellValue());
                        }  
                    } else if (c == 6) {  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String chnumber = String.valueOf(cell.getNumericCellValue());  
                            tempBean.setChnumber((chnumber.substring(0, chnumber.length()-2>0?chnumber.length()-2:1)));
                        }else{  
                        	tempBean.setChnumber(cell.getStringCellValue());
                        }  
                    }else if (c == 8) {  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String zone = String.valueOf(cell.getNumericCellValue());  
                            tempBean.setZone((zone.substring(0, zone.length()-2>0?zone.length()-2:1)));
                        }else{  
                        	tempBean.setZone(cell.getStringCellValue());
                        }  
                    }else if (c == 10) {  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String type = String.valueOf(cell.getNumericCellValue());  
                            tempBean.setType((type.substring(0, type.length()-2>0?type.length()-2:1)));
                        }else{  
                        	tempBean.setType(cell.getStringCellValue());
                        }  
                    }else if (c == 11) {  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String level = String.valueOf(cell.getNumericCellValue());  
                            tempBean.setLevel((level.substring(0, level.length()-2>0?level.length()-2:1)));
                        }else{  
                        	tempBean.setLevel(cell.getStringCellValue());
                        }  
                    }else if (c == 12) {  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String period = String.valueOf(cell.getNumericCellValue());  
                            tempBean.setPeriod((period.substring(0, period.length()-2>0?period.length()-2:1)));
                        }else{  
                        	tempBean.setPeriod(cell.getStringCellValue());
                        }  
                    }else if (c == 13) {  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String address = String.valueOf(cell.getNumericCellValue());  
                            tempBean.setAddress((address.substring(0, address.length()-2>0?address.length()-2:1)));
                        }else{  
                        	tempBean.setAddress(cell.getStringCellValue());
                        }  
                    }else if (c == 14) {  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String lng = String.valueOf(cell.getNumericCellValue());  
                            tempBean.setLng((lng.substring(0, lng.length()-2>0?lng.length()-2:1)));
                        }else{  
                        	tempBean.setLng(cell.getStringCellValue());
                        }  
                    }else if (c == 15) {  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String lat = String.valueOf(cell.getNumericCellValue());  
                            tempBean.setLat((lat.substring(0, lat.length()-2>0?lat.length()-2:1)));
                        }else{  
                        	tempBean.setLat(cell.getStringCellValue());
                        }  
                    }
                }  
            }  
            // 添加到list  
            impExcelBeanList.add(tempBean);  
        }  
        return impExcelBeanList;  
    }  
      
    /** 
     * 验证EXCEL文件 
     *  
     * @param filePath 
     * @return 
     */  
    public boolean validateExcel(String filePath) {  
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {  
            errorMsg = "文件名不是excel格式";  
            return false;  
        }  
        return true;  
    }  
      
    // @描述：是否是2003的excel，返回true是2003   
    public static boolean isExcel2003(String filePath)  {    
         return filePath.matches("^.+\\.(?i)(xls)$");    
     }    
     
    //@描述：是否是2007的excel，返回true是2007   
    public static boolean isExcel2007(String filePath)  {    
         return filePath.matches("^.+\\.(?i)(xlsx)$");    
     }    
}  
