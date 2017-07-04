package xh.mybatis.mapper;

import java.util.List;

import xh.mybatis.bean.BsStatusBean;

public interface BsStatusMapper {
	/**
	 * 查询所有告警信息
	 */
	public List<BsStatusBean> selectAllBsStatus() throws Exception;

}
