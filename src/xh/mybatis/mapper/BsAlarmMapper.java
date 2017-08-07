package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.BsAlarmBean;

public interface BsAlarmMapper {
	/**
	 * 查询所有告警信息
	 */
	public List<BsAlarmBean> selectAllBsAlarm() throws Exception;
	
	/**
	 * 条件查询告警信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<BsAlarmBean> selectBsAlarmList(Map<String,Object> map) throws Exception;
	
	/**
	 * 警告等级统计查询
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> bsAlarmLevelChart() throws Exception;
	
	/**
	 * 警告类型统计查询
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> bsAlarmTypeChart() throws Exception;

	/**
	 * 确认告警信息
	 * @return
	 */
	public void identifyBsAlarmById(String id);
}
