package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.WorkContactBean;
import xh.mybatis.mapper.WorkContactMapper;
import xh.mybatis.tools.MoreDbTools;

public class WorkContactService {
	
	public static List<WorkContactBean> list(Map<String, Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		List<WorkContactBean> list = new ArrayList<WorkContactBean>();
		try {
			list = mapper.list(map);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					WorkContactBean bean=new WorkContactBean();
					bean=list.get(i);
					bean.setFiles(searchFile(bean.getTaskId()));
					bean.setHandle_files(searchHandleFile(bean.getTaskId()));
					bean.setSummary_files(searchSummaryFile(bean.getTaskId()));
					list.set(i, bean);
					
				}
			}
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static Map<String,Object> data_by_taskId(String taskId)  {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			map= mapper.data_by_taskId(taskId);
			List<Map<String,Object>> l=new ArrayList<Map<String,Object>>();
			l=searchFile(taskId);
			map.put("files", l);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	public static int list_count(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.list_count(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int codeNum(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.codeNum(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int add(WorkContactBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.add(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int update(WorkContactBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.update(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int addFile(List<Map<String,Object>> list) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.addFile(list);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int addHandleFile(List<Map<String,Object>> list) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.addHandleFile(list);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int addSummaryFile(List<Map<String,Object>> list) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.addSummaryFile(list);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int sign(WorkContactBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.sign(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int handle(WorkContactBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.handle(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int summary(WorkContactBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.summary(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int check(WorkContactBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.check(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int cancel(int id) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.cancel(id);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int delFile(int id) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.delFile(id);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int isFileExistis(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.isFileExistis(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int del(List<String> list) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		int count=0;
		try {
			count=mapper.del(list);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static List<Map<String,Object>> searchFile(String taskId) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		try {
			list=mapper.searchFile(taskId);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<Map<String,Object>> searchHandleFile(String taskId) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		try {
			list=mapper.searchHandleFile(taskId);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<Map<String,Object>> searchSummaryFile(String taskId) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WorkContactMapper mapper = sqlSession.getMapper(WorkContactMapper.class);
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		try {
			list=mapper.searchSummaryFile(taskId);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
}
