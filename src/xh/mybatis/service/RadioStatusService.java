package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.RadioStatusBean;
import xh.mybatis.mapper.RadioStatusMapper;
import xh.mybatis.tools.MoreDbTools;

public class RadioStatusService {
	/**
	 * 查询基站下的注册终端列表
	 * @param map
	 * @return
	 */
	public static List<HashMap<String,Object>>oneBsRadio(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioStatusMapper mapper=sqlSession.getMapper(RadioStatusMapper.class);
		 List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
		 try {
			list=mapper.oneBsRadio(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return list;
	}
	/**
	 * 查询基站下的注册组列表
	 * @param map
	 * @return
	 */
	public static List<HashMap<String,Object>>oneBsGroup(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioStatusMapper mapper=sqlSession.getMapper(RadioStatusMapper.class);
		 List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
		 try {
			list=mapper.oneBsGroup(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return list;
	}
	/**
	 * 查询基站下的注册终端总数
	 * @param bsId
	 * @return
	 */
	public static int oneBsRadioCount(int bsId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioStatusMapper mapper=sqlSession.getMapper(RadioStatusMapper.class);
		int count=0;
		 try {
			count=mapper.oneBsRadioCount(bsId);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return count;
	}
	/**
	 * 查询基站下的注册组总数
	 * @param bsId
	 * @return
	 */
	public static int oneBsGroupCount(int bsId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioStatusMapper mapper=sqlSession.getMapper(RadioStatusMapper.class);
		int count=0;
		 try {
			count=mapper.oneBsGroupCount(bsId);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return count;
	}

}
