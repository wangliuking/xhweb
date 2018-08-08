package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.AssetAddApplayInfoBean;
import xh.mybatis.bean.AssetAddApplyBean;
import xh.mybatis.bean.AssetInfoBean;
import xh.mybatis.bean.AssetTransferBean;
import xh.mybatis.mapper.AssetInfoMapper;
import xh.mybatis.mapper.AssetTransferMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class BusinessService {
	/**
	 * 查询资产记录
	 * @param root
	 * @return
	 */
	public static List<AssetInfoBean> assetInfo(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		List<AssetInfoBean> list=new ArrayList<AssetInfoBean>();
		try {
			list=mapper.assetInfo(map);
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}

	public static int assetInfoByserialNumberExists(String v){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int count=0;
		try {
			count=mapper.assetInfoByserialNumberExists(v);
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;
	}
	/**
	 * 按资产状态统计
	 * @return
	 */
	public static List<HashMap<String,Integer>>allAssetStatus(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		List<HashMap<String,Integer>> list=new ArrayList<HashMap<String,Integer>>();
		try {
			list=mapper.allAssetStatus();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 按资产名称统计
	 * @return
	 */
	public static List<HashMap<String, Object>> allAssetNameCount(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
		try {
			list=mapper.allAssetNameCount();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 按资产类型统计
	 * @return
	 */
	public static List<HashMap<String,Integer>>allAssetType(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		List<HashMap<String,Integer>> list=new ArrayList<HashMap<String,Integer>>();
		try {
			list=mapper.allAssetType();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 查询资产记录总数
	 * @param root
	 * @return
	 */
	public static int assetInfoCount(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		List<AssetInfoBean> list=new ArrayList<AssetInfoBean>();
		int count=0;
		try {
			count=mapper.assetInfoCount(map);
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;
	}
	/**
	 * 添加资产
	 * @param bean
	 * @return
	 */
	public static int insertAsset(AssetInfoBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.insertAsset(bean);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	public static int insertManyAsset(List<AssetInfoBean> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.insertManyAsset(list);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	/**
	 * 修改资产记录
	 * @param bean
	 * @return
	 */
	public static int updateAsset(AssetInfoBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.updateAsset(bean);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	/**
	 * 核查资产
	 * @param map
	 * @return
	 */
	public static int checkAsset(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.checkAsset(map);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	/**
	 * 删除资产记录
	 * @param list
	 * @return
	 */
	public static int deleteAsset(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.deleteAsset(list);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	
	/**
	 * 根据序列号查询是否存在
	 * wlk
	 */
	public static int count(String serialNumber){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result = mapper.count(serialNumber);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据序列号查询详细信息
	 * wlk
	 */
	public static AssetInfoBean selectbynum(String serialNumber){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		AssetInfoBean bean = null;
		try {
			bean = mapper.selectbynum(serialNumber);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 根据序列号修改记录表的状态
	 * wlk
	 */
	public static int updateStatusByNum(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int count = 0;
		try {
			count = mapper.updateStatusByNum(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 根据序列号批量修改记录表的状态
	 * durant
	 */
	public static int updateStatusByNumAsList(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int count = 0;
		try {
			count = mapper.updateStatusByNumAsList(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 查询资产移交记录
	 * @param root
	 * @return
	 */
	public static List<AssetTransferBean> assetTransfer(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetTransferMapper mapper=sqlSession.getMapper(AssetTransferMapper.class);
		List<AssetTransferBean> list=new ArrayList<AssetTransferBean>();
		try {
			list=mapper.assetTransfer(map);
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 查询资产移交记录的总数
	 * @param root
	 * @return
	 */
	public static int assetTransferCount(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetTransferMapper mapper=sqlSession.getMapper(AssetTransferMapper.class);
		List<AssetInfoBean> list=new ArrayList<AssetInfoBean>();
		int count=0;
		try {
			count=mapper.assetTransferCount(map);
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;
	}
	/**
	 * 添加资产移交
	 * @param bean
	 * @return
	 */
	public static int insertAssetTransfer(AssetTransferBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetTransferMapper mapper=sqlSession.getMapper(AssetTransferMapper.class);
		int result=0;
		try {
			result=mapper.insertAssetTransfer(bean);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	/**
	 * 修改资产记录
	 * @param bean
	 * @return
	 */
	public static int updateAssetTransfer(AssetTransferBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetTransferMapper mapper=sqlSession.getMapper(AssetTransferMapper.class);
		int result=0;
		try {
			result=mapper.updateAssetTransfer(bean);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	public static int updateAssetTransfer2(AssetTransferBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetTransferMapper mapper=sqlSession.getMapper(AssetTransferMapper.class);
		int result=0;
		try {
			result=mapper.updateAssetTransfer2(bean);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	/**
	 * 删除资产移交记录
	 * @param list
	 * @return
	 */
	public static int deleteAssetTransfer(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetTransferMapper mapper=sqlSession.getMapper(AssetTransferMapper.class);
		int result=0;
		try {
			result=mapper.deleteAssetTransfer(list);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	public static List<AssetAddApplyBean> add_apply_list(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		List<AssetAddApplyBean> list=new ArrayList<AssetAddApplyBean>();
		try {
			list=mapper.add_apply_list(map);
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	public static int add_apply_list_count(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.add_apply_list_count(map);
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	public static int add_apply(AssetAddApplyBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.add_apply(bean);
			sqlSession.commit();
			if(result>0){
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("applyTag", bean.getApplyTag());
				map.put("user",bean.getUser());
				int r=update_asset_applyTag(map);
				
				if(r==0){
					sqlSession.rollback();
				}
			}
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	
	public static int add_apply_check1(AssetAddApplyBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.add_apply_check1(bean);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	public static int add_apply_info(AssetAddApplayInfoBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.add_apply_info(bean);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	public static int add_apply_check2(AssetAddApplyBean bean,AssetAddApplayInfoBean infobean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.add_apply_check2(bean);
			sqlSession.commit();
			if(result>0){
				if(update_asset_isLock(bean.getUser())>0){
					add_apply_info(infobean);
				}else{
					sqlSession.rollback();
				}
			}
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	public static int update_asset_isLock(String user){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.update_asset_isLock(user);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	public static int update_asset_applyTag(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.update_asset_applyTag(map);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	public static int add_apply_check3(AssetAddApplyBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetInfoMapper mapper=sqlSession.getMapper(AssetInfoMapper.class);
		int result=0;
		try {
			result=mapper.add_apply_check3(bean);
			sqlSession.commit();
			
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
}
