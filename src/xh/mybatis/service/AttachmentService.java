package xh.mybatis.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.AttachmentBean;
import xh.mybatis.mapper.AttachmentMapper;
import xh.mybatis.tools.MoreDbTools;

public class AttachmentService {

	public static int add(AttachmentBean bean) throws Exception {	
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		int rs=mapper.add(bean);
		session.commit();
		session.close();
		return rs;
	}

	public static int update(AttachmentBean bean) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		int rs=mapper.update(bean);
		session.commit();
		session.close();
		
		return rs;
	}

	public static int del(List<String> list) throws Exception {
		
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		int rs=mapper.del(list);
		session.commit();
		session.close();
		return rs;
	}

	public static int count(Map<String, Object> map) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		int count=mapper.count(map);
		session.close();
		
		return count;
	}

	public static List<AttachmentBean> attachmentList(Map<String, Object> map) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		List<AttachmentBean> list=mapper.attachmentList(map);
		session.close();
		return list;
	}
	
	
	public static int add_config(AttachmentBean bean) throws Exception {	
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		int rs=mapper.add_config(bean);
		session.commit();
		session.close();
		return rs;
	}

	public static int update_config(AttachmentBean bean) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		int rs=mapper.update_config(bean);
		session.commit();
		session.close();
		
		return rs;
	}

	public static int del_config(List<String> list) throws Exception {
		
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		int rs=mapper.del_config(list);
		session.commit();
		session.close();
		return rs;
	}

	public static int count_config(Map<String, Object> map) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		int count=mapper.count_config(map);
		session.close();
		
		return count;
	}

	public static List<AttachmentBean> attachmentList_config(Map<String, Object> map) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		List<AttachmentBean> list=mapper.attachmentList_config(map);
		session.close();
		return list;
	}
	
	
	
	public static List<AttachmentBean> attachmentList_month_one(String time) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		List<AttachmentBean> list=mapper.attachmentList_month_one(time);
		session.close();
		return list;
	}

	public static int attachmentList_config_isexists(Map<String, Object> map) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		int count=mapper.attachmentList_config_isexists(map);
		session.close();
		
		return count;
	}
	
	public static int attachmentList_isexists(Map<String, Object> map) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		AttachmentMapper mapper=session.getMapper(AttachmentMapper.class);
		int count=mapper.attachmentList_isexists(map);
		session.close();
		
		return count;
	}

}
