package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.CommunicationSupportBean;


public interface CommunicationSupportMapper {
	/**
	 * 查询所有通信保障申请记录
	 * @return
	 * @throws Exception
	 */
	public List<CommunicationSupportBean> selectAll(Map<String, Object> map)throws Exception;
	
	/**
	 * 申请进度查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> applyProgress(int id)throws Exception;
	/**
	 * 总数
	 * @return
	 * @throws Exception
	 */
	public int dataCount(Map<String, Object> map)throws Exception;
	/**
	 * 保障申请
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int insertNet(CommunicationSupportBean bean)throws Exception;
	/**
	 * 管理方审核
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedOne(CommunicationSupportBean bean)throws Exception;
	/**
	 * 主管部门审核
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedTwo(CommunicationSupportBean bean)throws Exception;
	
	/**
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int uploadFile(CommunicationSupportBean bean)throws Exception;
	
	/**
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkFile(CommunicationSupportBean bean)throws Exception;
	/**
	 * 用户确认
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int sureFile(CommunicationSupportBean bean)throws Exception;
	
}
