package xh.mybatis.mapper;

import com.tcpBean.GenTable;
import com.tcpBean.GetOrderInfoAck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TcpMapper {
	/**
	 * 查询当月已巡基站
	 */
	public List<Map<String,Object>> selectInspectionBsList(Map<String,Object> param)throws Exception;

	/**
	 * 查询当月未巡基站
	 */
	public List<Map<String,Object>> selectNotInspectionBsList(Map<String,Object> param)throws Exception;

	/**
	 * 查询当前所有断站
	 */
	public List<Map<String,Object>> selectBreakBsInfo(List<String> list)throws Exception;
	/**
	 * 根据基站id查询移动基站最近五条巡检
	 */
	public List<Map<String,Object>> selectInspectListForMoveBs(String bsId)throws Exception;

	/**
	 * 根据基站id查询自建基站最近五条巡检
	 */
	public List<Map<String,Object>> selectInspectListForSelfBs(String bsId)throws Exception;
	
	/**
	 * app根据用户id查询用户名称
	 */
	public Map<String,String> selectUserName(String userId)throws Exception;
	
	/**
	 * app根据基站id查询基站基本信息
	 * 
	 */
	public Map<String,Object> selectByBsId(String bsId)throws Exception;

	/**
	 * app根据基站id返回基站相关业务和状态信息
	 */
	public Map<String,Object> selectInfoByBsId(String bsId)throws Exception;

	/**
	 * app根据基站id返回基站相关传输状态
	 */
	public List<Map<String,Object>> selectBsTransfer(String znename)throws Exception;

	/**
	 * 根据基站id查询市电告警状态
	 */
	public Map<String,Object> selectBsPowerOff(String bsId)throws Exception;

	/**
	 * 根据基站id获取摄像头IP
	 */
	public String selectCameraIP(String bsId)throws Exception;

	/**
	 * 根据基站id查询电池续航时长
	 */
	public String selectBatteryTime(String bsId)throws Exception;

	/**
	 * app根据基站id查询基站基本信息(new)
	 *
	 */
	public Map<String,Object> selectByBsIdNew(String bsId)throws Exception;
	
	/**
	 * 更新派单状态
	 */
	public int updateUserStatus(Map<String,String> map)throws Exception;

	/**
	 * 更新发电状态
	 */
	public int updateElecStatus(Map<String,Object> map)throws Exception;
	
	/**
	 * 查询派单处理情况
	 */
	public Map<String,String> selectOrderStatus(String serialNum)throws Exception;

	/**
	 * 插入基站巡检表
	 */
	public int insertBsTable(List<String> list)throws Exception;
	
	/**
	 * 插入移动基站巡检表数据
	 */
	public int insertMoveBsTable(List<String> list)throws Exception;
	
	/**
	 * 更新最终的故障完结单
	 */
	public int updateFaultOrder(Map<String,String> map)throws Exception;
	
	/**
	 * 插入自建基站巡检表数据
	 */
	public int insertOwnBsTable(List<String> list)throws Exception;
	
	/**
	 * 插入网管巡检作业表
	 */
	public int insertNetTable(List<String> list)throws Exception;
	
	/**
	 * 插入调度台作业表
	 */
	public int insertDispatchTable(List<String> list)throws Exception;
	
	/**
	 * 查询最近五分钟手台gps信息
	 */
	public List<Map<String,Object>> selectForGpsDst(Map<String,Object> map)throws Exception;
	
	/**
	 * 查询所有需要显示的srcId
	 */
	public List<String> selectForAllVisable()throws Exception;
	
	/**
	 * 查询所有手台显示情况
	 */
	public List<Map<String,String>> selectForAllVisableStatus()throws Exception;
	
	/**
	 * 更新终端显示情况
	 */
	public int saveForAllVisable(Map<String,Object> map)throws Exception;

	/**
	 * 批量删除终端
	 */
	public int deleteRadioId(Map<String,Object> map)throws Exception;
	
	/**
	 * 更新用户初始化信息
	 */
	public int updateForMapInitByUser(Map<String,Object> map)throws Exception;
	
	/**
	 * 根据用户查询GIS初始化信息
	 */
	public String selectForMapInitByUser(String userId)throws Exception;
	
	/**
	 * 查询所有基站信息
	 */
	public List<Map<String,String>> selectForAllBsList(List<String> list)throws Exception;
	
	/**
	 * 插入用户上传的位置信息
	 */
	public int appInsertGpsInfoUp(Map<String,String> map)throws Exception;
	
	/**
	 * 插入用户上传的位置信息
	 */
	public List<Map<String,String>> selectForAllAppLocation(List<String> list)throws Exception;

	/**
	 * 更新发电派单状态
	 */
	public int updateGenTableStatus(Map<String,Object> map)throws Exception;

	/**
	 * 查询发电电压和电流
	 */
	public List<Map<String,Object>> selectForGenVI(String bsId)throws  Exception;

	/**
	 * 查询市电恢复时间
	 */
	public List<Map<String,Object>> selectForPowerOnTime(String serialnumber)throws  Exception;

	/**
	 * 查询停止发电时间
	 */
	public List<Map<String,Object>> selectForGenOffTime(String bsId)throws  Exception;

	/**
	 * 更新发电单号
	 */
	public int updateForGenTable(GenTable genTable)throws Exception;
	
	/**
	 * 模糊查询基站名称
	 */
	public List<Map<String,String>> selectBsListByName(String name)throws  Exception;
	
	/**
	 * 新增终端号
	 */
	public int addRadioId(Map<String,Object> param)throws Exception;

	/**
	 * 查询终端号
	 */
	public int selectRadioId(String radioId)throws  Exception;

	/**
	 * 查询用户所在区域
	 */
	public List<String> selectUserZone(String userId)throws  Exception;

	/**
	 * 根据订单号查询接单人，用于发电接单后的推送
	 */
	public String searchReceverElec(String serialnumber)throws  Exception;

	/**
	 * 根据订单号查询接单人，用于故障接单后的推送
	 */
	public String searchReceverFault(String serialnumber)throws  Exception;

	/**
	 * 根据订单号更新发电接单人
	 */
	public int updateReceverElec(Map<String,Object> param)throws  Exception;

	/**
	 * 根据订单号查询全部信息
	 */
	public Map<String,Object> searchAllInfo(String serialnumber)throws  Exception;

	/**
	 * 根据订单号更新故障接单人
	 */
	public int updateReceverFault(Map<String,String> param)throws  Exception;

	/**
	 * 根据订单号查询全部故障信息
	 */
	public Map<String,Object> searchAllFaultInfo(String serialnumber)throws  Exception;

}
