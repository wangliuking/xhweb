package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import xh.mybatis.bean.Lose;
import xh.mybatis.mapper.LoseMapper;
import xh.mybatis.tools.MoreDbTools;

public class BusinessLoseService {
	/**
	 * 查询
	 * @param root
	 * @return
	 */
	public static List<Lose> assetInfo(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		LoseMapper mapper=sqlSession.getMapper(LoseMapper.class);
		List<Lose> list=new ArrayList<Lose>();
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
		LoseMapper mapper=sqlSession.getMapper(LoseMapper.class);
		List<Lose> list=new ArrayList<Lose>();
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
	public static int insertAsset(Lose bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		LoseMapper mapper=sqlSession.getMapper(LoseMapper.class);
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
	public static int updateAsset(Lose bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		LoseMapper mapper=sqlSession.getMapper(LoseMapper.class);
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
	 * 删除
	 * @param list
	 * @return
	 */
	public static int deleteAsset(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		LoseMapper mapper=sqlSession.getMapper(LoseMapper.class);
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
