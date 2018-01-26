package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.BsAlarmBean;
import xh.mybatis.bean.BsAlarmExcelBean;

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
	
	public List<Map<String,Object>> selectTop5() throws Exception;
	/*实时添加基站断站记录*/
	public int addBsFault(BsAlarmExcelBean bean) throws Exception;
	/*判断基站断站记录是否存在*/
	public int bsFaultIsHave(int bsId) throws Exception;
	
	/**
	 *  告警总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int BsAlarmCount(Map<String,Object> map) throws Exception;
	
	/**
	 * 警告等级统计查询
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> bsAlarmLevelChart(Map<String,Object> map) throws Exception;
	
	/**
	 * 警告类型统计查询
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> bsAlarmTypeChart(Map<String,Object> map) throws Exception;

	/**
	 * 确认告警信息
	 * @return
	 */
	public void identifyBsAlarmById(String id);
}
