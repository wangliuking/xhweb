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
	public int insertSupport(CommunicationSupportBean bean)throws Exception;
	/**
	 * 管理方审核
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedOne(CommunicationSupportBean bean)throws Exception;
	/**
	 *
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
	public int checkedThree(CommunicationSupportBean bean)throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedFour(CommunicationSupportBean bean)throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedFive(CommunicationSupportBean bean)throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedSix(CommunicationSupportBean bean)throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedSeven(CommunicationSupportBean bean)throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedEight(CommunicationSupportBean bean)throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedNine(CommunicationSupportBean bean)throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedTen(CommunicationSupportBean bean)throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedEvelen(CommunicationSupportBean bean)throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedTwelve(CommunicationSupportBean bean)throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedThirteen(CommunicationSupportBean bean)throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedFourteen(CommunicationSupportBean bean)throws Exception;

	/**
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int checkedFifteen(CommunicationSupportBean bean)throws Exception;

	/**
	 * 用户确认
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int sureFile(CommunicationSupportBean bean)throws Exception;
	
}
