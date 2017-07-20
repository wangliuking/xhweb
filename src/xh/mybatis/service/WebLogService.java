package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.mapper.WebLogMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;
import xh.springmvc.handlers.LoginController;

public class WebLogService {
	protected final static Log log=LogFactory.getLog(WebLogService.class);
	/**
	 * 添加日志
	 * @return
	 * @throws Exception
	 */
	public static void writeLog(WebLogBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		WebLogMapper mapper=sqlSession.getMapper(WebLogMapper.class);
	
		try{
			if (bean.getOperator()!=null && bean.getOperator()!="") {
				mapper.insertLog(bean);
				sqlSession.commit();
				sqlSession.close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 查询日志记录
	 * @param map
	 * @return
	 */
	public static List<WebLogBean> logInfo(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebLogMapper mapper=sqlSession.getMapper(WebLogMapper.class);
		List<WebLogBean> list=new ArrayList<WebLogBean>();
		try{
			list=mapper.logInfo(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 查询日志记录总数
	 * @param map
	 * @return
	 */
	public static int logInfoCount(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		WebLogMapper mapper=sqlSession.getMapper(WebLogMapper.class);
		int count=0;
		try{
			count=mapper.logInfoCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
