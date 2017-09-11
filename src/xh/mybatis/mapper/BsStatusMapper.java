package xh.mybatis.mapper;

import java.util.List;

import xh.mybatis.bean.BsStatusBean;

public interface BsStatusMapper {
	/**
	 * 查询所有告警信息
	 */
	public List<BsStatusBean> selectAllBsStatus() throws Exception;
	
	/**
	 * 导出现网基站的运行记录
	 * @return
	 * @throws Exception
	 */
	public List<BsStatusBean> excelToBsStatus() throws Exception;

}
