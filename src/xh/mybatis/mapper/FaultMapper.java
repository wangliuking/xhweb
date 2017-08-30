package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.FaultBean;


public interface FaultMapper {
	/**
	 * 查询所有故障申请记录
	 * @return
	 * @throws Exception
	 */
	public List<FaultBean> selectAll(Map<String, Object> map)throws Exception;
	
	/**
	 * 申请进度查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> applyProgress(int id)throws Exception;
	/**
	 * 总数
	 * @return
	 * @throws Exception
	 */
	public int dataCount(Map<String, Object> map)throws Exception;
	/**
	 * 故障申请
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int insertFault(FaultBean bean)throws Exception;
	/**
	 * 主管部门审核
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedOne(FaultBean bean)throws Exception;
	/**
	 * 经办人确认
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedTwo(FaultBean bean)throws Exception;
	
	/**
	 * 用户确认
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int sureFile(FaultBean bean)throws Exception;
}
