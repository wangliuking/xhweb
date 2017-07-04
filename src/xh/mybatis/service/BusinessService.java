package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.AssetInfoBean;
import xh.mybatis.mapper.AssetInfoMapper;
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

}
