package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.WorkBean;

public interface WorkMapper {
	
	/**
	 * 工作记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Map<String,Object>> worklist(Map<String,Object> map)throws Exception;
	
	/**
	 * 工作记录总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int count(Map<String,Object> map)throws Exception;
	
	/**
	 * 新增工作记录
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int addwork(WorkBean bean)throws Exception;
	
	/**
	 * 签收工作记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int signWork(int id)throws Exception;

}
