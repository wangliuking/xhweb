package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.Map;

import xh.mybatis.bean.InspectionBean;

public interface InspectionMapper {
	
	/**
	 * 运维巡检记录表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Map<String,Object>>list(Map<String,Object> map)throws Exception;
	/**
	 * 运维巡检记录表总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int count(Map<String,Object> map)throws Exception;
	
	/**
	 * 新增运维巡检记录表
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int add(InspectionBean bean)throws Exception;
	
	/**
	 * 签收运维巡检记录表
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int sign(Map<String,Object> map)throws Exception;
	
	/**
	 * 填写巡检记录相关信息，汇总上报项目负责人
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int check2(InspectionBean bean)throws Exception;
	
	/**
	 * 抢修组将抢修情况汇总记录到平台，并发送消息通知巡检组
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int check3(InspectionBean bean)throws Exception;
	
	/**
	 * 巡检组整理填写巡检记录相关信息，汇总上报项目负责人，流程结束
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int check4(InspectionBean bean)throws Exception;

}
