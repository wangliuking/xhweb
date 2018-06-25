package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.ContactsBean;
import xh.mybatis.bean.OndutyBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.mapper.ContactsMapper;
import xh.mybatis.mapper.OndutyMapper;
import xh.mybatis.mapper.WebLogMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;
import xh.springmvc.handlers.LoginController;

public class ContactsService {
	protected final static Log log=LogFactory.getLog(ContactsService.class);
	public static List<ContactsBean> phone_list(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ContactsMapper mapper=sqlSession.getMapper(ContactsMapper.class);
		List<ContactsBean> list=new ArrayList<ContactsBean>();
		try{
			list=mapper.phone_list();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static int phone_info_by_namenum(ContactsBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ContactsMapper mapper=sqlSession.getMapper(ContactsMapper.class);
		int count=0;
		try{
			count=mapper.phone_info_by_namenum(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static int phone_update(ContactsBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		ContactsMapper mapper=sqlSession.getMapper(ContactsMapper.class);
		int count=0;
		try{
			count=mapper.phone_update(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int phone_write(ContactsBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		ContactsMapper mapper=sqlSession.getMapper(ContactsMapper.class);
		int result=0;
		try{
			result=mapper.phone_write(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static int phone_del(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		ContactsMapper mapper=sqlSession.getMapper(ContactsMapper.class);
		int result=0;
		try{
			result=mapper.phone_del(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


}
