package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.ChartsMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class ChartService {
	/**
	 * 终端在线状态统计
	 * @return
	 */
	public static List<HashMap> userStatusByChart() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ChartsMapper mapper = sqlSession.getMapper(ChartsMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list = mapper.userStatusByChart();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 基站状态统计图
	 * @return
	 */
	public static List<HashMap> statusByChart() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ChartsMapper mapper = sqlSession.getMapper(ChartsMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list = mapper.bsStatusByChart();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 资产状态图形统计分析
	 * @return
	 */
	public static List<HashMap> AssetStatusByChart(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ChartsMapper mapper = sqlSession.getMapper(ChartsMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list=mapper.AssetStatusByChart();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 基站分布图 -基站信息
	 * @return
	 */
	public static List<HashMap> bsPostionInfo(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ChartsMapper mapper = sqlSession.getMapper(ChartsMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list=mapper.bsPostionInfo();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 基站分布图 -经纬度
	 * @return
	 */
	public static List<HashMap> bsPostionLatLng(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ChartsMapper mapper = sqlSession.getMapper(ChartsMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list=mapper.bsPostionLatLng();
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 当天呼叫时间统计
	 * @return
	 */
	public static List<HashMap> hourCallTime(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ChartsMapper mapper = sqlSession.getMapper(ChartsMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list=mapper.hourCallTime(map);
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	public static List<Map<String,Object>>  excel_month_inspection(String time,int period){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		ChartsMapper mapper = sqlSession.getMapper(ChartsMapper.class);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		map.put("period", period);
		try {
			list=mapper.excel_month_inspection(map);
			sqlSession.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}

}
