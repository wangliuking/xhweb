package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.BsFlashBean;
import xh.mybatis.bean.BsRunStatusBean;
import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.bean.EmhBean;

public interface BsStatusMapper {
	
	/*tera系统告警基站部分,获取当前的断站列表*/
	public List<BsAlarmExcelBean> bsFaultInfo(Map<String,Object> map) throws Exception;
	public List<BsAlarmExcelBean> bsRestoreInfo(Map<String,Object> map) throws Exception;
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
	
	public int bsOfflineCount() throws Exception;
	
	public List<Map<String,Object>> bsGroupTop5() throws Exception;
	
	public List<Map<String,Object>> bsRadioTop5() throws Exception;
	
	/**
	 * 基站闪断列表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> bsflash(Map<String,Object> map) throws Exception;
	
	public List<BsFlashBean> excelToBsflash(Map<String,Object> map) throws Exception;
	
	/**
	 * 基站闪断列表总数
	 * @return
	 * @throws Exception
	 */
	public int bsflashCount(Map<String,Object> map) throws Exception;
	
	/**
	 * 导出现网基站的运行状态
	 * @return
	 * @throws Exception
	 */
	public List<BsRunStatusBean> excelToBsRunStatus() throws Exception;
	
	/**
	 * 基站下的环控状态
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public List<EmhBean> bsEmh(String fsuId) throws Exception;
	
	/**
	 * 基站下的环控告警
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public List<EmhBean> bsEmhAlarm(String fsuId) throws Exception;
	
	/**
	 * 4期所有基站环控告警
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> fourEmhAlarmList(Map<String,Object> map) throws Exception;
	/**
	 * 4期所有基站环控告警数目
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public int fourEmhAlarmListCount() throws Exception;
	
	/*<!--查询没有核减的基站 -->*/
	public int not_check_bs() throws Exception;
	/*<!--查询没有派单的基站 -->*/
	public int not_order_bs() throws Exception;
	
	
	
	/**
	 * 基站下的环控fsuId 
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	public String fsuIdBySiteId(int siteId) throws Exception;
	
	/**
	 * 基站下的bsc状态
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> bsc(Map<String,Object> map) throws Exception;
	
	/**
	 * 基站断站告警
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> bsOffList() throws Exception;
	
	/**
	 * 基站区域列表告警
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> bsZoneAlarm(List<String> list) throws Exception;
	/**
	 * 基站下的bs状态
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> bsr(Map<String,Object> map) throws Exception;
	/**
	 * 基站下的dpx状态
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> dpx(Map<String,Object> map) throws Exception;
	/**
	 * 基站下的psm状态
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> psm(Map<String,Object> map) throws Exception;
	
	/**
	 * 基站断站声音告警数目 
	 * @return
	 * @throws Exception
	 */
	public int bsOffVoiceCount() throws Exception;
	public Map<String,Object> emhVoiceCount() throws Exception;
	
	/**
	 * 基站断站定时更新
	 * @return
	 * @throws Exception
	 */
	public int bsOffVoiceChange(Map<String,Object> map) throws Exception;
	
	/**
	 * 更新基站断站告警状态
	 * @return
	 * @throws Exception
	 */
	public int updateAlarmStatus() throws Exception;
	
	/**
	 * 基站异常统计
	 * @return
	 * @throws Exception
	 */
	public int MapBsOfflineCount() throws Exception;
	/**
	 * 交换中心异常统计
	 * @return
	 * @throws Exception
	 */
	public int MapMscAlarmCount() throws Exception;
	/**
	 * 调度台异常统计
	 * @return
	 * @throws Exception
	 */
	public int MapDispatchAlarmCount() throws Exception;
	
	/**
	 * tera系统告警基站部分  导出excel
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<BsAlarmExcelBean> bsAlarmExcel(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> bsFaultList(Map<String,Object> map) throws Exception;
	
	public int bsFaultListCount(Map<String,Object> map) throws Exception;
	
	 /*更新基站故障表*/
	public int editBsFault(BsAlarmExcelBean bean) throws Exception;
	
	public List<Map<String,Object>> linkStatus() throws Exception;

}
