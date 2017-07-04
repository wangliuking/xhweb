package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.mapper.JoinNetMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.mybatis.tools.MoreDbTools.DataSourceEnvironment;

public class JoinNetService {
	/**
	 * 入网申请记录
	 * @return
	 */
	public static List<JoinNetBean> selectAll(Map<String, Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		List<JoinNetBean> list=new ArrayList<JoinNetBean>();
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
	 * 总数
	 * @return
	 */
	public static int dataCount(){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int count=0;
		try {
			count=mapper.dataCount();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 入网申请
	 * @param bean
	 * @return
	 */
	public static int insertNet(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.insertNet(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
