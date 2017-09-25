package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.CommunicationSupportBean;
import xh.mybatis.mapper.CommunicationSupportMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.mybatis.tools.MoreDbTools.DataSourceEnvironment;

public class CommunicationSupportService{
	/**
	 * 保障申请记录
	 * @return
	 */
	public static List<CommunicationSupportBean> selectAll(Map<String, Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		List<CommunicationSupportBean> list=new ArrayList<CommunicationSupportBean>();
		try {
			list = mapper.selectAll(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 申请进度查询
	 * @param id
	 * @return
	 */
	public static Map<String,Object> applyProgress(int id){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map = mapper.applyProgress(id);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 总数
	 * @return
	 */
	public static int dataCount(Map<String, Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int count=0;
		try {
			count=mapper.dataCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 故障申请
	 * @param bean
	 * @return
	 */
	public static int insertSupport(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.insertSupport(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 管理方审核
	 * @param bean
	 * @return
	 */
	public static int checkedOne(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedOne(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedTwo(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedTwo(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedThree(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedThree(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedFour(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedFour(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedFive(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedFive(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedSix(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedSix(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedSeven(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedSeven(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedEight(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedEight(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedNine(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedNine(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedTen(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedTen(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedEvelen(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedEvelen(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedTwelve(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedTwelve(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedThirteen(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedThirteen(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedFourteen(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedFourteen(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 *
	 * @param bean
	 * @return
	 */
	public static int checkedFifteen(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.checkedFifteen(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 用户确认
	 * @param bean
	 * @return
	 */
	public static int sureFile(CommunicationSupportBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		CommunicationSupportMapper mapper = sqlSession.getMapper(CommunicationSupportMapper.class);
		int result=0;
		try {
			result=mapper.sureFile(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
