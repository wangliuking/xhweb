package xh.mybatis.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.RadioUserMapper;
import xh.mybatis.mapper.TalkGroupMapper;
import xh.mybatis.tools.MoreDbTools;

public class TalkGroupService {
	/**
	 * 查询通话组信息
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> radioUserBusinessInfo(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.ById(map);
			sqlSession.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	/**
	 * 总数
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int Count(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
		int count = 0;
		try {
			count = mapper.Count(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 根据ID获取组名称
	 * @param id
	 * @return
	 */
	public static String GroupNameById(int id) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
		String groupName="";
		try {
			groupName = mapper.GroupNameById(id);
			sqlSession.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupName;
	}
	
	/**
	 * 添加组
	 * 
	 * @return
	 * @throws Exception
	 */
	public static int insertTalkGroup(HashMap<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TalkGroupMapper mapper = sqlSession.getMapper(TalkGroupMapper.class);
		int count = 0;
		try {
			if (count == 0) {
				mapper.insertTalkGroup(map);
			}
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
