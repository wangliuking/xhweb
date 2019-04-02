package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.JoinNetBean_programingTemplate;
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
	public static List<Map<String, Object>> net_db(int id){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try {
			list = mapper.net_db(id);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<Map<String, Object>> programingTemplateList(Map<String, Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try {
			list = mapper.programingTemplateList(map);
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
	 * 查询该入网流程所添加的用户C_ID
	 * @param id
	 * @return
	 */
	public static List<Integer> getUserCIDByID(int id){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		List<Integer> list=new ArrayList<Integer>();
		try {
			list = mapper.getUserCIDByID(id);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
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
	 * 查询最新一条有线接入ID
	 * @return
	 */
	public static int YXMAXID(){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int MAXID=0;
		try {
			MAXID=mapper.YXMAXID();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MAXID;
	}
	/**
	 * 通知培训
	 * @return
	 */
	public static int training(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.training(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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
	 * 导入编程模板
	 * @param map
	 * @return
	 */
	public static int existsProgramingTemplate(Map<String, String> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.existsProgramingTemplate(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static int insertProgramingTemplate(Map<String, String> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			if(existsProgramingTemplate(map)==0){
				result=mapper.insertProgramingTemplate(map);
				sqlSession.commit();
			}			
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
	 * 上传公函
	 * @param bean
	 * @return
	 */
	public static int uploadFileGh(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.uploadFileGh(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 上传通知函
	 * @param bean
	 * @return
	 */
	public static int uploadFileNote(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.uploadFileNote(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 管理方评估技术方案 
	 * @param bean
	 * @return
	 */
	public static int uploadFile100(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.uploadFile100(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 上传合同
	 * @param bean
	 * @return
	 */
	public static int uploadFileHT(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.uploadFileHT(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 上传doc
	 * @param bean
	 * @return
	 */
	public static int uploadFileDoc(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.uploadFileDoc(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 上传CG
	 * @param bean
	 * @return
	 */
	public static int uploadFileCG(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.uploadFileCG(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 上传CG
	 * @param bean
	 * @return
	 */
	public static int updateCheckById(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.updateCheckById(bean);
			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 经办人交付终端
	 * @param bean
	 * @return
	 */
	public static int updateCheck10(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.updateCheck10(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 用户确认接收终端
	 * @param bean
	 * @return
	 */
	public static int updateCheck11(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.updateCheck11(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * 经办人通知培训完成
	 * @param bean
	 * @return
	 */
	public static int updateCheck12(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.updateCheck12(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 用户确认培训是否完成
	 * @param bean
	 * @return
	 */
	public static int updateCheck13(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.updateCheck13(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	/**
	 * 有线-主管部门审核
	 * @param bean
	 * @return
	 */
	public static int YXcheckedOne(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.YXcheckedOne(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 有线-应用接入 
	 * @param bean
	 * @return
	 */
	public static int applicationAccess(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.applicationAccess(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 通知服务提供方开始应用接入
	 */
	public static int applicationAccess8(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.applicationAccess8(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 服务提供方完成应用接入
	 */
	public static int applicationAccess9(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.applicationAccess9(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 管理方审核应用接入
	 */
	public static int applicationAccess10(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.applicationAccess10(bean);
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
	 * 经办人上传资源配置文件
	 * @param bean
	 * @return
	 */
	public static int uploadFile4(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.uploadFile4(bean);
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
	 * 领导审核资源配置方案 
	 * @param bean
	 * @return
	 */
	public static int checkFile5(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.checkFile5(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 用户确认资源配置方案
	 * @param bean
	 * @return
	 */
	public static int checkFile7(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.checkFile7(bean);
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
	 * 审核样机入网送检申请（合同附件）
	 * @param bean
	 * @return
	 */
	public static int sureHT(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.sureHT(bean);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 领导通知经办人上传资源配置方案
	 * @param bean
	 * @return
	 */
	public static int checked100(JoinNetBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		JoinNetMapper mapper = sqlSession.getMapper(JoinNetMapper.class);
		int result=0;
		try {
			result=mapper.checked100(bean);
			sqlSession.commit();
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
