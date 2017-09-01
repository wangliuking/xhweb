package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

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
	
}
