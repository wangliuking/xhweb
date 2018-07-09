package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TcpMapper {
	
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
	 * 更新派单状态
	 */
	public int updateUserStatus(Map<String,String> map)throws Exception;
	
	/**
	 * 查询派单处理情况
	 */
	public Map<String,String> selectOrderStatus(String serialNum)throws Exception;
	
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
	public List<Map<String,String>> selectForGpsDst(Map<String,Object> map)throws Exception;
	
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
	public List<Map<String,String>> selectForAllBsList()throws Exception;
	
	/**
	 * 插入用户上传的位置信息
	 */
	public int appInsertGpsInfoUp(Map<String,String> map)throws Exception;
	
	/**
	 * 插入用户上传的位置信息
	 */
	public List<Map<String,String>> selectForAllAppLocation(List<String> list)throws Exception;

}
