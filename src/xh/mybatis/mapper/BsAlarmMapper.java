package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.BsAlarmBean;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.BsJiFourBean;
import xh.mybatis.bean.Sf800mAlarmBean;

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
	public List<BsJiFourBean> bs_ji_four() throws Exception;
	public List<BsJiFourBean> bs_ji_four_compare(String fsuId) throws Exception;
	public List<Map<String,Object>> bs_ji_four_e4(Map<String,Object> map) throws Exception;
	public List<BsJiFourBean> bs_water_four() throws Exception;
	
	public int bs_emh_eps(BsJiFourBean bean) throws Exception;
	
	public int write_bs_emh_eps(BsJiFourBean bean) throws Exception;
	public int del_bs_emh_eps(BsJiFourBean bean) throws Exception;
	
	/*实时添加基站断站记录*/
	public int addBsFault(BsAlarmExcelBean bean) throws Exception;
	public int bsRestore(BsAlarmExcelBean bean) throws Exception;
	/*判断基站断站记录是否存在*/
	public int bsFaultIsHave(int bsId) throws Exception;
	
	public int dispatch_alarm() throws Exception;
	public int link_alarm() throws Exception;
	public int esight_alarm() throws Exception;
	public int control_alarm() throws Exception;
	
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
	
	
	
	public int clear_sf_alarm() throws Exception;
	
	public List<Sf800mAlarmBean> sf_800m_alarm() throws Exception;
	
	public List<Sf800mAlarmBean> sf_order_alarm() throws Exception;
	public int insert_sf_alarm(List<Sf800mAlarmBean> list) throws Exception;
	
	public int sureAlarm(List<String> list) throws Exception;
	
	public int updateCkeckTag(Map<String,Object> map) throws Exception;
	
}
