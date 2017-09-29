package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.FaultBean;
import xh.mybatis.mapper.FaultMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.mybatis.tools.MoreDbTools.DataSourceEnvironment;

public class FaultService {
	/**
	 * 故障申请记录
	 * @return
	 */
	public static List<FaultBean> selectAll(Map<String, Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		FaultMapper mapper = sqlSession.getMapper(FaultMapper.class);
		List<FaultBean> list=new ArrayList<FaultBean>();
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
		FaultMapper mapper = sqlSession.getMapper(FaultMapper.class);
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
		FaultMapper mapper = sqlSession.getMapper(FaultMapper.class);
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
	public static int insertFault(FaultBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		FaultMapper mapper = sqlSession.getMapper(FaultMapper.class);
		int result=0;
		try {
			result=mapper.insertFault(bean);
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
	public static int checkedOne(FaultBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		FaultMapper mapper = sqlSession.getMapper(FaultMapper.class);
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
	public static int checkedTwo(FaultBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		FaultMapper mapper = sqlSession.getMapper(FaultMapper.class);
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
	public static int checkedThree(FaultBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		FaultMapper mapper = sqlSession.getMapper(FaultMapper.class);
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
	 * 管理方入库信息
	 * @param bean
	 * @return
	 */
	public static int checkedFour(FaultBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		FaultMapper mapper = sqlSession.getMapper(FaultMapper.class);
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
	public static int checkedFive(FaultBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		FaultMapper mapper = sqlSession.getMapper(FaultMapper.class);
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
	public static int checkedSix(FaultBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		FaultMapper mapper = sqlSession.getMapper(FaultMapper.class);
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
	 * 用户确认
	 * @param bean
	 * @return
	 */
	public static int sureFile(FaultBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		FaultMapper mapper = sqlSession.getMapper(FaultMapper.class);
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
