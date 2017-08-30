package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.mapper.JoinNetMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.mybatis.tools.MoreDbTools.DataSourceEnvironment;

public class JoinNetService {
	/**
	 * 入网申请记录
	 * @return
	 */
	public static List<JoinNetBean> selectAll(Map<String, Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		List<JoinNetBean> list=new ArrayList<JoinNetBean>();
		try {
			list = mapper.selectAll(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 申请进度查询
	 * @param id
	 * @return
	 */
	public static Map<String,Object> applyProgress(int id){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			map = mapper.applyProgress(id);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 总数
	 * @return
	 */
	public static int dataCount(Map<String, Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int count=0;
		try {
			count=mapper.dataCount(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 入网申请
	 * @param bean
	 * @return
	 */
	public static int insertNet(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.insertNet(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 管理方审核
	 * @param bean
	 * @return
	 */
	public static int checkedOne(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.checkedOne(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 主管部门审核
	 * @param bean
	 * @return
	 */
	public static int checkedTwo(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.checkedTwo(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 上传公函或通知函
	 * @param bean
	 * @return
	 */
	public static int uploadFileGhorNote(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.uploadFileGhorNote(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 上传编组方案
	 * @param bean
	 * @return
	 */
	public static int uploadFile(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.uploadFile(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 审核编组方案
	 * @param bean
	 * @return
	 */
	public static int checkFile(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.checkFile(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 用户确认编组方案
	 * @param bean
	 * @return
	 */
	public static int sureFile(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.sureFile(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 用户签署协议
	 * @param bean
	 * @return
	 */
	public static int signFile(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.signFile(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 经办人签署协议
	 * @param bean
	 * @return
	 */
	public static int signFileTwo(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.signFile(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 退网
	 * @param bean
	 * @return
	 */
	public static int quitNet(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.quitNet(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
