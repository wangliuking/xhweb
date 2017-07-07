package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.JoinNetBean;


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
	 * 总数
	 * @return
	 * @throws Exception
	 */
	public int dataCount(Map<String, Object> map)throws Exception;
	/**
	 * 入网申请
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int insertNet(JoinNetBean bean)throws Exception;
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

}
