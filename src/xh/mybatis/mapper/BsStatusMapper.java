package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.bean.EmhBean;

public interface BsStatusMapper {
	/**
	 * 查询所有告警信息
	 */
	public List<BsStatusBean> selectAllBsStatus() throws Exception;
	
	/**
	 * 导出现网基站的运行记录
	 * @return
	 * @throws Exception
	 */
	public List<BsStatusBean> excelToBsStatus() throws Exception;
	
	/**
	 * 基站下的环控状态
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public List<EmhBean> bsEmh(String fsuId) throws Exception;
	
	/**
	 * 基站下的环控告警
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public List<EmhBean> bsEmhAlarm(String fsuId) throws Exception;
	
	/**
	 * 基站下的环控fsuId 
	 * @param siteId
	 * @return
	 * @throws Exception
	 */
	public String fsuIdBySiteId(int siteId) throws Exception;
	
	/**
	 * 基站下的bsc状态
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> bsc(int bsId) throws Exception;
	/**
	 * 基站下的bs状态
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> bsr(int bsId) throws Exception;
	/**
	 * 基站下的dpx状态
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> dpx(int bsId) throws Exception;
	/**
	 * 基站下的psm状态
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> psm(int bsId) throws Exception;

}
