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
	 * 根据基站id查询对应动环信息
	 * @author wlk
	 */
	public List<HashMap<String,String>> selectAllEMHById(String bsId) throws Exception;

	
}
