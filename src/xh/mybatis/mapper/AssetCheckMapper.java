package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.AssetCheckBean;

public interface AssetCheckMapper {
	
	/**
	 * 资产核查申请列表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> assetCheckList(Map<String,Object> map) throws Exception;
	
	/**
	 * 资产核查申请列表数量
	 * @return
	 * @throws Exception
	 */
	public int count() throws Exception;
	
	/**
	 * 申请核查资料
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int apply(AssetCheckBean bean) throws Exception;
	
	/**
	 * 管理部门领导审核
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkOne(AssetCheckBean bean) throws Exception;
	
	/**
	 * 资产管理员确认完成
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkTwo(Map<String,Object> map) throws Exception;

}
