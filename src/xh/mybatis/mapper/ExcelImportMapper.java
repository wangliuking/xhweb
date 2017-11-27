package xh.mybatis.mapper;


import java.util.List;
import java.util.Map;

import xh.mybatis.bean.ImpExcelBean;
import xh.mybatis.bean.OtherBean;
import xh.mybatis.bean.TempBean;

public interface ExcelImportMapper {
	/**
	 * 添加excel到数据库
	 * @return
	 * @throws Exception
	 */
	public int insertExcel(List<ImpExcelBean> list)throws Exception;
	
	/**
	 * 其他数据录入
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int insertExcelOther(List<OtherBean> list)throws Exception;
	
	public int updateExcel(TempBean tempBean)throws Exception;

	
}
