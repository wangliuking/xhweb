package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.AssetInfoBean;

public interface AssetInfoMapper {
	/**
	 * 查询资产记录
	 * @return
	 * @throws Exception
	 */
	public List<AssetInfoBean> assetInfo(Map<String,Object> map)throws Exception;
	
	/**
	 * 按资产状态统计
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,Integer>>allAssetStatus()throws Exception;
	/**
	 * 按资产类型统计
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,Integer>>allAssetType()throws Exception;
	/**
	 * 查询资产记录总数
	 * @return
	 * @throws Exception
	 */
	public int assetInfoCount(Map<String,Object> map)throws Exception;
	/**
	 * 添加资产
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int insertAsset(AssetInfoBean bean)throws Exception;
	/**
	 *修改资产记录 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int updateAsset(AssetInfoBean bean)throws Exception;
	/**
	 * 删除记录
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int deleteAsset(List<String> list)throws Exception;
	/**
	 * 根据序列号查询是否存在
	 * wlk
	 */
	public int count(String serialNumber)throws Exception;
	/**
	 * 根据序列号查询详细信息
	 * wlk
	 */
	public AssetInfoBean selectbynum(String serialNumber)throws Exception;
	/**
	 * 根据序列号更新记录表中的状态
	 * wlk
	 */
	public int updateStatusByNum(Map<String,Object> map)throws Exception;

}
