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

}
