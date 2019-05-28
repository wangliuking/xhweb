package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.OrderMapper;
import xh.mybatis.mapper.WebAppMapper;
import xh.mybatis.tools.MoreDbTools;

public class WebAppService implements WebAppMapper {

	@Override
	public int app_bs_gd_count() throws Exception {
		// TODO Auto-generated method stub
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebAppMapper mapper=sqlSession.getMapper(WebAppMapper.class);
		int count=0;
		//List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			count=mapper.app_bs_gd_count();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int app_verticalbs_count() throws Exception {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebAppMapper mapper=sqlSession.getMapper(WebAppMapper.class);
		int count=0;
		try{
			count=mapper.app_verticalbs_count();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Map<String, Object> app_room() throws Exception {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebAppMapper mapper=sqlSession.getMapper(WebAppMapper.class);
		Map<String,Object> map=new HashMap<String, Object>();
		try{
			map=mapper.app_room();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> app_portable() throws Exception {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebAppMapper mapper=sqlSession.getMapper(WebAppMapper.class);
		Map<String,Object> map=new HashMap<String, Object>();
		try{
			map=mapper.app_portable();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> app_movebus() throws Exception {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebAppMapper mapper=sqlSession.getMapper(WebAppMapper.class);
		Map<String,Object> map=new HashMap<String, Object>();
		try{
			map=mapper.app_movebus();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> app_subway() throws Exception {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebAppMapper mapper=sqlSession.getMapper(WebAppMapper.class);
		Map<String,Object> map=new HashMap<String, Object>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			list=mapper.app_subway();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int app_dispatch_count() throws Exception {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebAppMapper mapper=sqlSession.getMapper(WebAppMapper.class);
		int count=0;
		try{
			count=mapper.app_dispatch_count();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int app_access_count() throws Exception {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebAppMapper mapper=sqlSession.getMapper(WebAppMapper.class);
		int count=0;
		try{
			count=mapper.app_access_count();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int app_vpn_count() throws Exception {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebAppMapper mapper=sqlSession.getMapper(WebAppMapper.class);
		int count=0;
		try{
			count=mapper.app_vpn_count();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}
