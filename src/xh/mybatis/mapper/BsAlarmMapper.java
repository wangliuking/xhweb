package xh.mybatis.mapper;

import java.util.List;

import xh.mybatis.bean.BsAlarmBean;

public interface BsAlarmMapper {
	/**
	 * 查询所有告警信息
	 */
	public List<BsAlarmBean> selectAllBsAlarm() throws Exception;

}
