package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.JoinNetBean_programingTemplate;


public interface JoinNetMapper {
	/**
	 * 查询所有入网申请记录
	 * @return
	 * @throws Exception
	 */
	public List<JoinNetBean> selectAll(Map<String, Object> map)throws Exception;
	
	/**
	 * 申请进度查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> applyProgress(int id)throws Exception;
	/**
	 * 查询该入网流程所添加的用户C_ID
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getUserCIDByID(int id)throws Exception;
	/**
	 * 总数
	 * @return
	 * @throws Exception
	 */
	public int dataCount(Map<String, Object> map)throws Exception;
	/**
	 * 查询最新一条有线接入ID
	 * @return
	 * @throws Exception
	 */
	public int YXMAXID()throws Exception;
	/**
	 * 入网申请
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int insertNet(JoinNetBean bean)throws Exception;
	/**
	 * 导入编程模板
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insertProgramingTemplate(Map<String, String> map)throws Exception;
	/**
	 * 管理方审核
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedOne(JoinNetBean bean)throws Exception;
	/**
	 * 主管部门审核
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedTwo(JoinNetBean bean)throws Exception;
	
	/**
	 * 上传编组方案
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int uploadFile(JoinNetBean bean)throws Exception;
	
	/**
	 * 上传公函
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int uploadFileGh(JoinNetBean bean)throws Exception;
	
	/**
	 * 上传通知函
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int uploadFileNote(JoinNetBean bean)throws Exception;
	
	/**
	 * 上传合同
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int uploadFileHT(JoinNetBean bean)throws Exception;
	
	/**
	 * 保存签署文档
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int uploadFileDoc(JoinNetBean bean)throws Exception;
	
	/**
	 * 更新流程
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int updateCheckById(JoinNetBean bean)throws Exception;
 
	/**
	 * 有线-主管部门审核
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int YXcheckedOne(JoinNetBean bean)throws Exception;
	
	/**
	 * 有线-应用接入
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int applicationAccess(JoinNetBean bean)throws Exception;


	/**
	 * 保存采购文档
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int uploadFileCG(JoinNetBean bean)throws Exception;
	
	/**
	 * 审核编组方案
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkFile(JoinNetBean bean)throws Exception;
	/**
	 * 用户确认编组方案
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int sureFile(JoinNetBean bean)throws Exception;
	/**
	 * 审核样机入网送检申请（合同附件）
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int sureHT(JoinNetBean bean)throws Exception;
	
	/**
	 * 用户签署协议
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int signFile(JoinNetBean bean)throws Exception;
	
	/**
	 * 经办人签署协议
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int signFileTwo(JoinNetBean bean)throws Exception;
	
	/**
	 * 通知培训
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int training(JoinNetBean bean)throws Exception;
	
	/**
	 * 退网
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int quitNet(JoinNetBean bean)throws Exception;
	
}
