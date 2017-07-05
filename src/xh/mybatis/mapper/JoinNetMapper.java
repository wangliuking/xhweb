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
	 * 总数
	 * @return
	 * @throws Exception
	 */
	public int dataCount()throws Exception;
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

}
