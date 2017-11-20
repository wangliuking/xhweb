package xh.mybatis.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.InfoDeviceBean;
import xh.mybatis.mapper.InfoDeviceMapper;
import xh.mybatis.tools.MoreDbTools;

public class InfoDeviceService {
	
	/**
	 * 基站下的设备信息
	 * @param map
	 * @return
	 */
	public static Map<String,Object> bsInfoDeviceList(int bsId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		InfoDeviceMapper mapper=sqlSession.getMapper(InfoDeviceMapper.class);
		Map<String,Object> list=new HashMap<String, Object>() ;
		try {
			list=mapper.bsInfoDeviceList(bsId);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  list;
	}
	/**
	 * 基站下的设备总数
	 * @param map
	 * @return
	 */
	public static int bsInfoDeviceListCount(int bsId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		InfoDeviceMapper mapper=sqlSession.getMapper(InfoDeviceMapper.class);
		int count=0;
		try {
			count=mapper.bsInfoDeviceListCount(bsId);
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 新增基站设备信息
	 * @param bean
	 * @return
	 */
	public static int insertBsDevice(InfoDeviceBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		InfoDeviceMapper mapper=sqlSession.getMapper(InfoDeviceMapper.class);
		int count=0;
		try {
			count=mapper.insertBsDevice(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 修改基站设备信息
	 * @param bean
	 * @return
	 */
	public static int updateBsDevice(InfoDeviceBean bean){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		InfoDeviceMapper mapper=sqlSession.getMapper(InfoDeviceMapper.class);
		int count=0;
		try {
			count=mapper.updateBsDevice(bean);
			sqlSession.commit();
			sqlSession.close();					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

}
