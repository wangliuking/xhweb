package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.ChartBean;

public interface BsstationMapper {
	/**
	 * 查询基站信息
	 * @return
	 * @throws Exception
	 */
	public List<BsstationBean> bsInfo(Map<String,Object> map)throws Exception;
	
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
	public int  selectByBsId(int bsId)throws Exception;
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
	public List<HashMap>allBsInfo()throws Exception;
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
	 * 查询top5话务量
	 * @author wlk
	 */
	public List<HashMap<String,String>> selectCalllist(String currentMonth) throws Exception;
	/**
	 * 查询top5排队数
	 * @author wlk
	 */
	public List<HashMap<String,String>> selectChannel() throws Exception;
	
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
	public List<BsstationBean> rectangle(Map<String,Object> map)throws Exception;
	
	/**
	 * 圈选基站总数
	 * @return
	 * @throws Exception
	 */
	public int  rectangleCount(Map<String,Object> map)throws Exception;
	
}
