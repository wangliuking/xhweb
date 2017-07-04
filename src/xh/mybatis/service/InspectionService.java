package xh.mybatis.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.Inspection;
import xh.mybatis.mapper.InspectionMapper;
import xh.mybatis.tools.MoreDbTools;

public class InspectionService {
	/**
	 * 查询终端用户历史信息
	 * 
	 * @param root
	 * @return
	 */
	public static List<HashMap<String,String>> radioUserBusinessInfo(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		InspectionMapper mapper = sqlSession.getMapper(InspectionMapper.class);
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		try {
			list = mapper.ById(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		InspectionMapper mapper = sqlSession.getMapper(InspectionMapper.class);
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
	
	
	public static int insert(Inspection record) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		InspectionMapper mapper = sqlSession.getMapper(InspectionMapper.class);
		int count = 0;
		try {
			//count = mapper.selectByBsId(bean.getBsId());
			if (count == 0) {
				mapper.insert(record);
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
