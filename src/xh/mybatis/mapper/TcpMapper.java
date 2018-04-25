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
	 * 更新派单状态为处理中
	 */
	public int updateUserStatus(String serialNum)throws Exception;
	
	/**
	 * 查询派单处理情况
	 */
	public Map<String,String> selectOrderStatus(String serialNum)throws Exception;
	
	/**
	 * 插入移动基站巡检表数据
	 */
	public int insertMoveBsTable(List<String> list)throws Exception;
	
	/**
	 * 派单完成前删除原派单信息
	 */
	public int deleteBySerialNum(String serialNum)throws Exception;
	
	/**
	 * 插入故障派单
	 */
	public int insertFaultOrder(List<String> list)throws Exception;
	
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
	public List<Map<String,String>> selectForGpsDst()throws Exception;

}
