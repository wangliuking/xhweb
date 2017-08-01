package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import xh.mybatis.bean.EmhBean;

public interface EmhMapper {
	/**
	 * 查询基站环境监控实时状态
	 * @return
	 * @throws Exception
	 */
	public List<EmhBean> oneBsEmh(String code)throws Exception;
	
	/**
	 * 基站环境监控实时告警列表
	 * @return
	 * @throws Exception
	 */
	public List<HashedMap<String, Object>> bsEmhNowStatus(Map<String,Object> map)throws Exception;
	/**
	 * 基站环境监控告警列表条目
	 * @return
	 * @throws Exception
	 */
	public int bsEmhNowStatusCount()throws Exception;
	/**
	 * 基站环境监控历史告警列表
	 * @return
	 * @throws Exception
	 */
	public List<HashedMap<String, Object>> bsEmhOldStatus(Map<String,Object> map)throws Exception;
	/**
	 * 基站环境监控历史告警列表条目
	 * @return
	 * @throws Exception
	 */
	public int bsEmhOldStatusCount()throws Exception;

}
