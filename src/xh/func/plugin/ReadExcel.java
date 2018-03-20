package xh.func.plugin;
 
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import xh.mybatis.bean.ImpExcelBean;

/**
 * Excel处理函数
 * @author 12878
 *
 */
public class ReadExcel {  
    //总行数  
    private int totalRows = 0;    
    //总条数  
    private int totalCells = 0;   
    //错误信息接收器  
    private String errorMsg;  
    //构造方法  
    public ReadExcel(){}  
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
    public List<ImpExcelBean> getExcelInfo(MultipartFile mFile) {  
        String fileName = mFile.getOriginalFilename();//获取文件名  
        List<ImpExcelBean> impExcelBeanList = null;
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
    public List<ImpExcelBean> createExcel(InputStream is, boolean isExcel2003) {  
    	List<ImpExcelBean> impExcelBeanList = null;
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
    private List<ImpExcelBean> readExcelValue(Workbook wb) {  
        // 得到第一个shell  
        Sheet sheet = wb.getSheetAt(0);  
        // 得到Excel的行数  
        this.totalRows = sheet.getPhysicalNumberOfRows();  
        // 得到Excel的列数(前提是有行数)  
        if (totalRows > 1 && sheet.getRow(0) != null) {  
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();  
        }  
        List<ImpExcelBean> impExcelBeanList = new ArrayList<ImpExcelBean>();  
        // 循环Excel行数  
        for (int r = 1; r < totalRows; r++) {  
            Row row = sheet.getRow(r);  
            if (row == null){  
                continue;  
            }  
            ImpExcelBean impExcelBean = new ImpExcelBean();
            // 循环Excel的列  
            for (int c = 0; c < this.totalCells; c++) {  
                Cell cell = row.getCell(c);  
                if (null != cell) {  
                    if (c == 0) {  
                        //如果是纯数字,比如你写的是25,cell.getNumericCellValue()获得是25.0,通过截取字符串去掉.0获得25  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String positionArea = String.valueOf(cell.getNumericCellValue());  
                            impExcelBean.setPositionArea(positionArea.substring(0, positionArea.length()-2>0?positionArea.length()-2:1));
                        }else{  
                        	impExcelBean.setPositionArea(cell.getStringCellValue());//位置区 
                        }  
                    } else if (c == 1) {  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String db = String.valueOf(cell.getNumericCellValue());  
                            impExcelBean.setDb(db.substring(0, db.length()-2>0?db.length()-2:1));
                        }else{  
                        	impExcelBean.setDb(cell.getStringCellValue());//场强  
                        }  
                    } else if (c == 2){  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String lng = String.valueOf(cell.getNumericCellValue());  
                            impExcelBean.setLng(lng);
                        }else{  
                        	impExcelBean.setLng(cell.getStringCellValue());//经度 
                        }  
                    } else if (c == 3){  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String lat = String.valueOf(cell.getNumericCellValue());  
                            impExcelBean.setLat(lat);
                        }else{  
                        	impExcelBean.setLat(cell.getStringCellValue());//纬度
                        }  
                    } else if (c == 4){  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String nPositionArea = String.valueOf(cell.getNumericCellValue());  
                            impExcelBean.setnPositionArea(nPositionArea.substring(0, nPositionArea.length()-2>0?nPositionArea.length()-2:1));
                        }else{  
                        	impExcelBean.setnPositionArea(cell.getStringCellValue());//邻位置区 
                        }  
                    } else if (c == 5){  
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){  
                            String ndb = String.valueOf(cell.getNumericCellValue());  
                            impExcelBean.setNdb(ndb.substring(0, ndb.length()-2>0?ndb.length()-2:1));
                        }else{  
                        	impExcelBean.setNdb(cell.getStringCellValue());//邻场强
                        }  
                    }
                }  
            }  
            // 添加到list  
            impExcelBeanList.add(impExcelBean);  
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
