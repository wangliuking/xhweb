package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.EmailBean;
import xh.mybatis.mapper.EmailMapper;
import xh.mybatis.mapper.WebRoleMapper;
import xh.mybatis.tools.MoreDbTools;

public class EmailService {
	protected final static Log log=LogFactory.getLog(EmailService.class);
	/**
	 * 写邮件
	 * @return
	 * @throws Exception
	 */
	public static void insertEmail(EmailBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		EmailMapper mapper=sqlSession.getMapper(EmailMapper.class);
		log.info(bean.toString());
	
		try{
			mapper.insertEmail(bean);
			sqlSession.commit();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 查询邮件
	 * @param map
	 * @return
	 */
	public static List<HashMap> emailInfo(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		EmailMapper mapper=sqlSession.getMapper(EmailMapper.class);
		List<HashMap> list=new ArrayList<HashMap>();
		try{
			list=mapper.emailInfo(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 邮件总数
	 * @param map
	 * @return
	 */
	public static int emailCount(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		EmailMapper mapper=sqlSession.getMapper(EmailMapper.class);
		int count=0;
		try{
			count=mapper.emailCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 未读邮件总数
	 * @param map
	 * @return
	 */
	public static int noReadEmailCount(Map<String, String> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		EmailMapper mapper=sqlSession.getMapper(EmailMapper.class);
		int count=0;
		try{
			count=mapper.noReadEmailCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int updateById(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		EmailMapper mapper=sqlSession.getMapper(EmailMapper.class);
		int result=-1;
		try {
			result=mapper.updateById(list);
			sqlSession.commit();		
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除邮件
	 * @param list
	 * @return
	 */
	public static int deleteById(List<String> list){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		EmailMapper mapper=sqlSession.getMapper(EmailMapper.class);
		int result=-1;
		try {
			result=mapper.deleteById(list);
			sqlSession.commit();		
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static int noVoiceEmailCount(Map<String, Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		EmailMapper mapper=sqlSession.getMapper(EmailMapper.class);
		int count=0;
		try{
			count=mapper.noVoiceEmailCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int updateVoice(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		EmailMapper mapper=sqlSession.getMapper(EmailMapper.class);
		int count=0;
		try{
			count=mapper.updateVoice();
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
