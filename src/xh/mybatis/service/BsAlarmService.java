package xh.mybatis.service;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.BsAlarmBean;
import xh.mybatis.bean.BsAlarmExcelBean;
import xh.mybatis.bean.BsJiFourBean;
import xh.mybatis.mapper.BsAlarmMapper;
import xh.mybatis.mapper.UserStatusMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class BsAlarmService {
	
	/*实时添加基站断站记录 */
	public static int addBsFault(BsAlarmExcelBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		int code=-1;
		try {
			if(mapper.bsFaultIsHave(Integer.parseInt(bean.getBsId()))<1){
				code=mapper.addBsFault(bean);
				sqlSession.commit();
			}
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}
	public static void bsRestore(BsAlarmExcelBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		try {
			mapper.bsRestore(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询所有告警信息
	 */
	public List<BsAlarmBean> selectAllBsAlarm() throws Exception{
		SqlSession session=DbTools.getSession();
		BsAlarmMapper mapper=session.getMapper(BsAlarmMapper.class);
	        List<BsAlarmBean> BsAlarm=mapper.selectAllBsAlarm();
	        session.commit();
	        session.close();
	        return BsAlarm;   
	}
	
	/**
	 * 条件查询告警信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<BsAlarmBean> selectBsAlarmList(Map<String,Object> map) throws Exception{
		SqlSession session=DbTools.getSession();
		BsAlarmMapper mapper=session.getMapper(BsAlarmMapper.class);
	        List<BsAlarmBean> BsAlarm=mapper.selectBsAlarmList(map);
	        session.commit();
	        session.close();
	        return BsAlarm;   
	}
	
	public static List<Map<String,Object>> selectTop5(){
		SqlSession session=DbTools.getSession();
		BsAlarmMapper mapper=session.getMapper(BsAlarmMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.selectTop5();
			session.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 告警总数
	 * @return
	 */
	public static int BsAlarmCount(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		int count=0;
		try {
			count=mapper.BsAlarmCount(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 告警等级统计
	 * @return
	 */
	public static List<HashMap> bsAlarmLevelChart(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list = mapper.bsAlarmLevelChart(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 告警类型统计
	 * @return
	 */
	public static List<HashMap> bsAlarmTypeChart(Map<String,Object> map) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<HashMap> list = new ArrayList<HashMap>();
		try {
			list = mapper.bsAlarmTypeChart(map);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 确认告警信息
	 * @param map
	 */
	public static void identifyBsAlarmById(String id) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		try {
			mapper.identifyBsAlarmById(id);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void write_bs_emh_eps(BsJiFourBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		try {
			mapper.write_bs_emh_eps(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void del_bs_emh_eps(BsJiFourBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		try {
			mapper.del_bs_emh_eps(bean);
			sqlSession.commit();
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int bs_emh_eps(BsJiFourBean bean) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		int count=0;
		try {
			count=mapper.bs_emh_eps(bean);
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static void bs_ji_four() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<BsJiFourBean> list=new ArrayList<BsJiFourBean>();
		int eps=0;
		BsJiFourBean bean=new BsJiFourBean();
		try {
			list=mapper.bs_ji_four();
			
			for(int i=0,a=list.size();i<a;i++){
				bean=list.get(i);
				eps=bs_emh_eps(bean);
				if(bean.getSingleValue()==1){
					if(eps==0){
						 write_bs_emh_eps(bean);
					}
				}else{
					if(eps>0){
						del_bs_emh_eps(bean);
					}
				}
			}
			
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void bs_water_four() {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsAlarmMapper mapper = sqlSession.getMapper(BsAlarmMapper.class);
		List<BsJiFourBean> list=new ArrayList<BsJiFourBean>();
		int eps=0;
		BsJiFourBean bean=new BsJiFourBean();
		try {
			list=mapper.bs_water_four();
			
			for(int i=0,a=list.size();i<a;i++){
				bean=list.get(i);
				eps=bs_emh_eps(bean);
				if(bean.getSingleValue()==1){
					if(eps==0){
						 write_bs_emh_eps(bean);
					}
				}else{
					if(eps>0){
						del_bs_emh_eps(bean);
					}
				}
			}			
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
