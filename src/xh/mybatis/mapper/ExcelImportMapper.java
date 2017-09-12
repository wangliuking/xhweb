package xh.mybatis.mapper;


import java.util.List;
import xh.mybatis.bean.ImpExcelBean;

public interface ExcelImportMapper {
	/**
	 * 添加excel到数据库
	 * @return
	 * @throws Exception
	 */
	public int insertExcel(List<ImpExcelBean> list)throws Exception;

	
}
