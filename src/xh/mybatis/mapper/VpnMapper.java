package xh.mybatis.mapper;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.VpnBean;


public interface VpnMapper {
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> selectAllName()throws Exception;
	/**
	 * 总数
	 * @return
	 * @throws Exception
	 */
	
	/**
	 * 添加
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int insertByVpnId(VpnBean bean)throws Exception;
	/**
	 *修改
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int updateByVpnId(Map<String,String> map)throws Exception;
	/**
	 * 删除
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int deleteByVpnId(String vpnId)throws Exception;
	/**
	 * 根据vpnId判断是否有重复的
	 */
	public int countByVpnId(String vpnId)throws Exception;
}
