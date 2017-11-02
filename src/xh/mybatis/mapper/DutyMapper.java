package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.Map;

import xh.mybatis.bean.WorkBean;

public interface DutyMapper {
	
	/**
	 * 运维值班情况表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Map<String,Object>>list(Map<String,Object> map)throws Exception;
	/**
	 * 运维值班情况表总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int count(Map<String,Object> map)throws Exception;
	
	/**
	 * 新增运维值班情况表
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int add(WorkBean bean)throws Exception;
	
	/**
	 * 签收运维值班情况表
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int sign(WorkBean bean)throws Exception;

}
