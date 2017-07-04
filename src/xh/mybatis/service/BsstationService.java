package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.ChartBean;
import xh.mybatis.mapper.BsstationMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class BsstationService {
	/**
	 * 查询基站信息
	 * 
	 * @param root
	 * @return
	 */
	public static List<BsstationBean> bsInfo(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsstationMapper mapper = sqlSession.getMapper(BsstationMapper.class);
		List<BsstationBean> list = new ArrayList<BsstationBean>();
		try {
			list = mapper.bsInfo(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 基站总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int bsCount(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsstationMapper mapper = sqlSession.getMapper(BsstationMapper.class);
		int count = 0;
		try {
			count = mapper.bsCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 添加基站
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int insertBs(BsstationBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsstationMapper mapper = sqlSession.getMapper(BsstationMapper.class);
		int count = 0;
		try {
			count = mapper.selectByBsId(bean.getBsId());
			if (count == 0) {
				mapper.insertBs(bean);
			}
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 修改基站
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int updateBs(BsstationBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsstationMapper mapper = sqlSession.getMapper(BsstationMapper.class);
		int count = 0;
		try {
			count = mapper.updateByBsId(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 删除基站
	 * 
	 * @return
	 * @throws Exception
	 */
	public static void deleteBsByBsId(List<String> list) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsstationMapper mapper = sqlSession.getMapper(BsstationMapper.class);
		try {
			mapper.deleteBsByBsId(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 查询所有基站
	 * @return
	 */
	public static List<HashMap> allBsInfo() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsstationMapper mapper = sqlSession.getMapper(BsstationMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list=mapper.allBsInfo();
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询所有基站信息，用于首页展示
	 * @author wlk
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> selectAllBsStation() throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsstationMapper mapper=session.getMapper(BsstationMapper.class);
		List<HashMap<String, String>> BsStation=mapper.selectAllBsStation();
	        session.commit();  
	        session.close();
	        return BsStation;   
	}
	/**
	 * 根据选中的基站id进行查询
	 * @author wlk
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> selectBsStationById(int bsId) throws Exception{
		SqlSession session =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsstationMapper mapper=session.getMapper(BsstationMapper.class);
		List<HashMap<String, String>> bsStation = mapper.selectBsStationById(bsId);
		session.commit();
		session.close();
		return bsStation;
	}
	/**
	 * 根据选中的基站id查询对应的动环信息
	 * @author wlk
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> selectAllEMHById(String bsId) throws Exception{
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.emh);
		BsstationMapper mapper=session.getMapper(BsstationMapper.class);
		List<HashMap<String, String>> bsStation = mapper.selectAllEMHById(bsId);
		session.commit();
		session.close();
		return bsStation;
	}

}
