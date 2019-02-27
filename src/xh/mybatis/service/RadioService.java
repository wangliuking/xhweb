package xh.mybatis.service;
 
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.bean.RadioBean;
import xh.mybatis.mapper.RadioMapper;
import xh.mybatis.tools.MoreDbTools;

public class RadioService implements RadioMapper {
	protected  static  Log log=LogFactory.getLog(RadioService.class);
	@Override
	public List<RadioBean> list(Map<String, Object> map) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioMapper mapper=session.getMapper(RadioMapper.class);
		List<RadioBean> list=new ArrayList<RadioBean>();
		try {
			list=mapper.list(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public int count() throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioMapper mapper=session.getMapper(RadioMapper.class);
		int count=0;
		try {
			count=mapper.count();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public int add(RadioBean bean) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioMapper mapper=session.getMapper(RadioMapper.class);
		int rs=0;
		try {
			rs=mapper.add(bean);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	@Override
	public int update(RadioBean bean) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioMapper mapper=session.getMapper(RadioMapper.class);
		int rs=0;
		try {
			rs=mapper.update(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	@Override
	public int delete(List<String> list) throws Exception {
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		RadioMapper mapper=session.getMapper(RadioMapper.class);
		int rs=0;
		try {
			rs=mapper.delete(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}
