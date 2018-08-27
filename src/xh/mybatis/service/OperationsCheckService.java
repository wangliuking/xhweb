package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.OperationsCheckBean;
import xh.mybatis.bean.OperationsCheckDetailBean;
import xh.mybatis.mapper.OperationsCheckMapper;
import xh.mybatis.tools.MoreDbTools;

public class OperationsCheckService {
	
	
	public static List<OperationsCheckBean> dataList(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper=session.getMapper(OperationsCheckMapper.class);
		List<OperationsCheckBean> list=new ArrayList<OperationsCheckBean>();		
		try {
			list=mapper.dataList(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static int count(Map<String,Object> map){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper=session.getMapper(OperationsCheckMapper.class);
		int count=0;	
		try {
			count=mapper.count(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public static int add(OperationsCheckBean checkBean){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper=session.getMapper(OperationsCheckMapper.class);
		//detailBean.setApplyId(checkBean.getApplyId());
		int count=0;
		int r=0;
		try {
			count=mapper.add(checkBean);
			session.commit();
			/*if(count>=1){
				if(detailExists(detailBean.getTime())>0){
					r=updateDetail(detailBean);
				}else{
					r=addDetail(detailBean);
				}
				if(r==0){
					session.rollback();
				}
			}else{
				session.rollback();
			}*/
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static OperationsCheckDetailBean searchDetail(String time){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper=session.getMapper(OperationsCheckMapper.class);
		OperationsCheckDetailBean bean=new OperationsCheckDetailBean();	
		try {
			bean=mapper.searchDetail(time);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	
	public static int detailExists(String time){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper=session.getMapper(OperationsCheckMapper.class);
		int count=0;	
		try {
			count=mapper.detailExists(time);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static int addDetail(OperationsCheckDetailBean bean){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper=session.getMapper(OperationsCheckMapper.class);
		int count=0;	
		try {
			count=mapper.addDetail(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static int updateDetail(OperationsCheckDetailBean bean){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper=session.getMapper(OperationsCheckMapper.class);
		int count=0;	
		try {
			count=mapper.updateDetail(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int check2(OperationsCheckBean checkBean){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper=session.getMapper(OperationsCheckMapper.class);
		int count=0;
		try {
			count=mapper.check2(checkBean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int check3(OperationsCheckBean checkBean,OperationsCheckDetailBean detailBean){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper=session.getMapper(OperationsCheckMapper.class);
		detailBean.setApplyId(checkBean.getApplyId());
		int count=0;
		int r=0;
		try {
			count=mapper.check3(checkBean);
			session.commit();
			if(count>=1){
				if(detailExists(detailBean.getTime())>0){
					r=updateDetail(detailBean);
				}else{
					r=addDetail(detailBean);
				}
				if(r==0){
					session.rollback();
				}
			}else{
				session.rollback();
			}
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int check4(OperationsCheckBean checkBean){
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper=session.getMapper(OperationsCheckMapper.class);
		int count=0;
		try {
			count=mapper.check4(checkBean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	

}
