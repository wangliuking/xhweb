package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.WebLogBean;

public interface WebLogMapper {
	/**
	 * 记录系统日志
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public void insertLog(WebLogBean bean)throws Exception;
	
	/**
	 * 查询日志记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<WebLogBean> logInfo(Map<String, Object> map)throws Exception;
	/**
	 * 查询日志记录总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int logInfoCount(Map<String, Object> map)throws Exception;

}
