package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.AssetCheckBean;
import xh.mybatis.bean.WorkBean;
import xh.mybatis.mapper.AssetCheckMapper;
import xh.mybatis.mapper.WorkMapper;
import xh.mybatis.tools.MoreDbTools;

public class AssetCheckServices {
	
	/**
	 * 资产核查申请列表
	 * @param map
	 * @return
	 */
	public static List<Map<String,Object>> assetCheckList(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetCheckMapper mapper=sqlSession.getMapper(AssetCheckMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.assetCheckList(map);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 资产核查申请列表总数
	 * @param map
	 * @return
	 */
	public static int count(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AssetCheckMapper  mapper=sqlSession.getMapper(AssetCheckMapper.class);
		int count=0;
		try {
			count=mapper.count();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;
	}
	//申请核查资产
	public static int apply(AssetCheckBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetCheckMapper mapper=sqlSession.getMapper(AssetCheckMapper.class);
		int result=0;
		try {
			result=mapper.apply(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	/**
	 * 管理部门领导审核
	 * @param bean
	 * @return
	 */
	public static int checkOne(AssetCheckBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetCheckMapper mapper=sqlSession.getMapper(AssetCheckMapper.class);
		int result=0;
		try {
			result=mapper.checkOne(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	/**
	 * 资产管理员确认完成
	 * @param map
	 * @return
	 */
	public static int checkTwo(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AssetCheckMapper mapper=sqlSession.getMapper(AssetCheckMapper.class);
		int result=0;
		try {
			result=mapper.checkTwo(map);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	
	

}
