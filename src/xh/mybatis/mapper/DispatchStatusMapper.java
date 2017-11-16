package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.DispatchBean;

public interface DispatchStatusMapper {
	
	/**
	 *调度台运行状态
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> dispatchstatus() throws Exception;
	
	/**
	 * 已经安装的调度台
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> dispatchSetup() throws Exception;
	
	/**
	 * 调度用户是否存在
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int dispatchExists(int dstId) throws Exception;
	
	/**
	 * 修改调度台状态
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateDispatchStatus(Map<String,String> map) throws Exception;
	
	/**
	 * 添加调度台
	 * @param bean
	 * @return
	 * @throws Exception
	 */
    public int addDispatch(DispatchBean bean) throws Exception;
	
    /**
     * 修改调度台
     * @param bean
     * @return
     * @throws Exception
     */
	public int updateDispatch(DispatchBean bean) throws Exception;
	/**
	 * 删除调度台
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int deleteDispatch(List<String> list) throws Exception;

}
