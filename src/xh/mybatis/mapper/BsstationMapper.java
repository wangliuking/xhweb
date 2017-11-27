package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.ChartBean;
import xh.mybatis.bean.bsLinkConfigBean;
import xh.mybatis.bean.bsrConfigBean;

public interface BsstationMapper {
	/**
	 * 查询基站信息
	 * @return
	 * @throws Exception
	 */
	public List<BsstationBean> bsInfo(Map<String,Object> map)throws Exception;
	/**
	 * 查询基站状态信息
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, Object>> bsstatusInfo(Map<String,Object> map)throws Exception;
	
	/**
	 * 根据基站ID,判断该基站相邻小区是否存在
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int neighborExists(Map<String,Object> map)throws Exception;
	
	/**
	 *  新增基站切换参数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int addBsHandover(Map<String,Object> map)throws Exception;
	
	/**
	 *  修改基站切换参数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateBsHandover(Map<String,Object> map)throws Exception;
	
	/**
	 * 新增基站相邻小区
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int addBsNeighbor(Map<String,Object> map)throws Exception;
	
	/**
	 * 删除基站相邻小区
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int delBsNeighbor(Map<String,Object> map)throws Exception;
	
	
	
	
	/**
	 * 根据基站ID,调单好判断该基站传输是否存在
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int linkconfigExists(Map<String,Object> map)throws Exception;
	
	/**
	 * 新增基站传输
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int addLinkconfig(bsLinkConfigBean bean)throws Exception;
	
	/**
	 * 删除基站传输
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int delLinkconfig(int id)throws Exception;
	
	
	/**
	 * 根据基站ID,bscId,bsrId判断该基站bsr是否存在 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int bsrconfigExists(Map<String,Object> map)throws Exception;
	
	/**
	 * 新增基站bsr
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int addBsrconfig(bsrConfigBean bean)throws Exception;
	
	/**
	 * 删除基站bsr
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int delBsrconfig(int id)throws Exception;
	
	/**
	 * 查询基站断站列表
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> bsOfflineList() throws Exception;
	
	/**
	 * 基站总数
	 * @return
	 * @throws Exception
	 */
	public int  bsCount(Map<String,Object> map)throws Exception;
	
	/**
	 * 增加基站
	 * @return
	 * @throws Exception
	 */
	public int  insertBs(BsstationBean bean)throws Exception;
	/**
	 * 根据基站ID修改基站
	 * @return
	 * @throws Exception
	 */
	public int  updateByBsId(BsstationBean bean)throws Exception;
	/**
	 * 根据基站ID查找基站
	 * @return
	 * @throws Exception
	 */
	public int  selectByBsId(String bsId)throws Exception;
	
	
	/**
	 * 根据基站ID查找基站相邻小区
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>>  neighborByBsId(int bsId)throws Exception;
	/**
	 * 根据基站ID查找基站切换参数
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>>  handoverByBsId(int bsId)throws Exception;
	/**
	 * 根据基站ID查找基站BSR配置信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>>  bsrconfigByBsId(int bsId)throws Exception;
	/**
	 * 根据基站ID查找基站传输配置信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>>  linkconfigByBsId(int bsId)throws Exception;
	/**
	 * 根据基站ID删除基站
	 * @return
	 * @throws Exception
	 */
	public void  deleteBsByBsId(List<String> list)throws Exception;
	/**
	 * 查询所有基站
	 * @return
	 * @throws Exception
	 */
	public List<HashMap>allBsInfo(Map<String,Object> map)throws Exception;

	/**
	 * 根据所选区域查询所有基站
	 * @author wlk
	 */
	public List<HashMap<String,String>> bsByArea(List<String> zone) throws Exception;
	/**
	 * 根据所选级别查询所有基站
	 * @author wlk
	 */
	public List<HashMap<String,String>> bsByLevel(String level) throws Exception;
	/**
	 * 查询所有基站区域
	 * @author wlk
	 */
	public List<HashMap<String,String>> selectAllArea() throws Exception;
	/**
	 * 查询所有基站级别
	 * @author wlk
	 */
	public List<HashMap<String,String>> selectLevel() throws Exception;
	/**
	 * 查询所有基站位置信息
	 * @author wlk
	 */
	public List<HashMap<String,String>> selectAllBsStation() throws Exception;
	/**
	 * 根据id查询对应基站信息
	 * @author wlk
	 */
	public List<HashMap<String,String>> selectBsStationById(int bsId) throws Exception;
	/**
	 * 查询单个基站话务量及其他业务
	 * @author wlk
	 */
	public List<HashMap<String,String>> selectCalllistById(String currentMonth) throws Exception;
	/**
	 * 查询单个基站排队数及其他业务
	 * @author wlk
	 */
	public List<HashMap<String,String>> selectChannelById() throws Exception;
	
	/**
	 * 路测数据查询
	 * @author wlk
	 */
	public List<HashMap<String,String>> selectRoadTest() throws Exception;
	
	/**
	 * 圈选基站查询
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> rectangle(Map<String,Object> map)throws Exception;
	
	/**
	 * 圈选基站总数
	 * @return
	 * @throws Exception
	 */
	public int  rectangleCount(Map<String,Object> map)throws Exception;
	
}