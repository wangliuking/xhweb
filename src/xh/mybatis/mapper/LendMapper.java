package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.LendBean;

public interface LendMapper {
	/**
	 * 申请租借设备列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> lendlist(Map<String,Object> map)throws Exception;
	/**
	 * 租借列表总数
	 * @return
	 * @throws Exception
	 */
	public int lendlistCount()throws Exception;
	/**
	 * 租借
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int lend(LendBean bean)throws Exception;
	/**
	 * 管理方审核租借
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedOne(LendBean bean)throws Exception;
	/**
	 * 提交至领导审核租借清单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedTwo(LendBean bean)throws Exception;
	/**
	 * 领导审核租借清单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedOrder(LendBean bean)throws Exception;
	/**
	 * 用户确认租借清单
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int sureOrder(LendBean bean)throws Exception;
	/**
	 * 判断设备清单中是否存在该条记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int isExtisSerialNumberInfo(Map<String,Object> map)throws Exception;
	/**
	 * 添加设备清单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int addOrder(List<Map<String,Object>> list)throws Exception;
	/**
	 * 更新资产租借状态
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int updateAssetStatus(List<Map<String,Object>> list)throws Exception;
	/**
	 * 删除租借设备清单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int deleteLendOrderE(Map<String,Object> map)throws Exception;
	/**
	 * 租借清单列表
	 * @param lendId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> lendInfoList(int lendId)throws Exception;


}
