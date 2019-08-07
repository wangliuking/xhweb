package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.AssetInfoBean;

public interface ChartsMapper {
	/**
	 * 终端在线状态统计
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> userStatusByChart()throws Exception;
	/**
	 * 基站状态统计图
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> bsStatusByChart()throws Exception;
	/**
	 * 资产状态图形统计分析
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> AssetStatusByChart()throws Exception;
	/**-基站分布图 -基站信息
	 * 资产状态图形统计分析
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> bsPostionInfo()throws Exception;
	/**-基站分布图 -经纬度
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> bsPostionLatLng()throws Exception;
	/**
	 * 当天呼叫时间统计
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> hourCallTime(Map<String,Object> map)throws Exception;
	
	public List<Map<String,Object>> excel_month_inspection(Map<String,Object> map)throws Exception;
	public List<Map<String,Object>> excel_month_inspection_bs(Map<String,Object> map)throws Exception;

}
