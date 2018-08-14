package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.AssetAddApplayInfoBean;
import xh.mybatis.bean.AssetAddApplyBean;
import xh.mybatis.bean.AssetInfoBean;
import xh.mybatis.bean.AssetScrapApplayInfoBean;
import xh.mybatis.bean.AssetScrapApplyBean;
import xh.mybatis.bean.AssetScrapInfoBean;

public interface AssetInfoMapper {
	/**
	 * 查询资产记录
	 * @return
	 * @throws Exception
	 */
	public List<AssetInfoBean> assetInfo(Map<String,Object> map)throws Exception;
	
	public int assetInfoByserialNumberExists(String serialNumber)throws Exception;
	
	/**
	 * 按资产状态统计
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,Integer>>allAssetStatus()throws Exception;
	/**
	 * 按资产名称统计
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,Object>>allAssetNameCount()throws Exception;
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
	
	public int insertManyAsset(List<AssetInfoBean> list)throws Exception;
	/**
	 *修改资产记录 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int updateAsset(AssetInfoBean bean)throws Exception;
	
	public int updateStatus(Map<String,Object> map)throws Exception;
	
	/**
	 * 核查资产
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int checkAsset(Map<String,Object> map)throws Exception;
	/**
	 * 删除记录
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int deleteAsset(List<String> list)throws Exception;
	
	public int deleteScrapAsset(List<String> list)throws Exception;
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
	/**
	 * 根据序列号批量更新记录表中的状态
	 * durant
	 */
	public int updateStatusByNumAsList(Map<String,Object> map)throws Exception;
	
	/** 新增资产申请列表*/
	public List<AssetAddApplyBean> add_apply_list(Map<String,Object> map)throws Exception;
	
	public int add_apply_list_count(Map<String,Object> map)throws Exception;
	
	public int add_apply(AssetAddApplyBean bean)throws Exception;
	
	public int add_apply_check1(AssetAddApplyBean bean)throws Exception;
	
	public int add_apply_info(AssetAddApplayInfoBean bean)throws Exception;
	
	public int add_apply_check2(AssetAddApplyBean bean)throws Exception;
	
	public int update_asset_isLock(String user)throws Exception;
	
	public int update_asset_applyTag(Map<String,Object> map)throws Exception;
	
	public int add_apply_check3(AssetAddApplyBean bean)throws Exception;

	/** 资产报废申请明细*/
	public List<AssetScrapInfoBean> asset_scrap_info(Map<String,Object> map)throws Exception;
	
	/** 录入报废资产*/
	public int insertScrapAsset(AssetScrapInfoBean bean)throws Exception;
	
	/** 判断报废资产是否存在*/
	public int scrapAssetInfoByserialNumberExists(String serialNumber)throws Exception;
	
	/** 报废资产申请*/
	public int scrap_apply(AssetScrapApplyBean bean)throws Exception;
	
	/** 更新报废资产tag*/
	public int update_scrap_asset_applyTag(Map<String,Object> map)throws Exception;
	
	/** 报废资产申请列表*/
	public List<AssetScrapApplyBean> scrap_apply_list(Map<String,Object> map)throws Exception;
	
	/** 报废资产申请列表总数*/
	public int scrap_apply_list_count(Map<String,Object> map)throws Exception;
	
	/**  审核报废清单*/
	public int scrap_apply_check1(AssetScrapApplyBean bean)throws Exception;
	
	public int scrap_apply_check2(AssetScrapApplyBean bean)throws Exception;
	
	public int update_scrap_asset_isLock(String user)throws Exception;
	
	public int scrap_apply_info(AssetScrapApplayInfoBean bean)throws Exception;
	
	public int scrap_apply_check3(AssetScrapApplyBean bean)throws Exception;

}
