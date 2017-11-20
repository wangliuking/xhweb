package xh.mybatis.mapper;

import java.util.Map;

import xh.mybatis.bean.InfoDeviceBean;

public interface InfoDeviceMapper {
	
	/**
	 * 获取基站下的设备信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> bsInfoDeviceList(int bsId) throws Exception;
	
	/**
	 * 获取基站下的设备信息记录总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int bsInfoDeviceListCount(int bsId) throws Exception;
	
	/**
	 * 新增基站设备记录
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int insertBsDevice(InfoDeviceBean bean) throws Exception;
	
	/**
	 * 修改设备记录
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int updateBsDevice(InfoDeviceBean bean) throws Exception;

}
