package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.AssetInfoBean;
import xh.mybatis.bean.Repairs;
import xh.mybatis.mapper.RepairsMapper;
import xh.mybatis.tools.MoreDbTools;

public class BusinessRepairsService {
	/**
	 * 查询
	 * @param root
	 * @return
	 */
	public static List<Repairs> assetInfo(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RepairsMapper mapper=sqlSession.getMapper(RepairsMapper.class);
		List<Repairs> list=new ArrayList<Repairs>();
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
	 * 总数
	 * @param root
	 * @return
	 */
	public static int assetInfoCount(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RepairsMapper mapper=sqlSession.getMapper(RepairsMapper.class);
		List<Repairs> list=new ArrayList<Repairs>();
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
	 * 添加
	 * @param bean
	 * @return
	 */
	public static int insertAsset(AssetInfoBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RepairsMapper mapper=sqlSession.getMapper(RepairsMapper.class);
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
	 * 修改
	 * @param bean
	 * @return
	 */
	public static int updateAsset(Repairs bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RepairsMapper mapper=sqlSession.getMapper(RepairsMapper.class);
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
	 * 根据序列号修改备注信息
	 */
	public static int updateByNum(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RepairsMapper mapper=sqlSession.getMapper(RepairsMapper.class);
		int result=0;
		try {
			result=mapper.updateByNum(map);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  result;
	}
	
	/**
	 * 删除
	 * @param list
	 * @return
	 */
	public static int deleteAsset(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RepairsMapper mapper=sqlSession.getMapper(RepairsMapper.class);
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
	 * 根据序列号查询是否存在此信息
	 */
	public static int countByNum(String serialNumber){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RepairsMapper mapper=sqlSession.getMapper(RepairsMapper.class);
		int count=0;
		try {
			count=mapper.countByNum(serialNumber);
			sqlSession.close();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  count;
	}

}
