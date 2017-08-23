package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.LendBean;
import xh.mybatis.mapper.LendMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.mybatis.tools.MoreDbTools.DataSourceEnvironment;

public class LendService {
	/**
	 * 申请租借列表
	 * @param map
	 * @return
	 */
	public static List<Map<String,Object>> lendlist(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.lendlist(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 数量
	 * @return
	 */
	public static int lendlistCount(){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		int count=0;
		try {
			count=mapper.lendlistCount();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * 租借
	 * @param bean
	 * @return
	 */
	public static int lend(LendBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		int result=0;
		try {
			result=mapper.lend(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 管理方审核
	 * @param bean
	 * @return
	 */
	public static int checkedOne(LendBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		int result=0;
		try {
			result=mapper.checkedOne(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 提交至领导审核租借清单
	 * @param bean
	 * @return
	 */
	public static int checkedTwo(LendBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		int result=0;
		try {
			result=mapper.checkedTwo(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 领导审核租借清单
	 * @param bean
	 * @return
	 */
	public static int checkedOrder(LendBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		int result=0;
		try {
			result=mapper.checkedOrder(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 用户租借清单
	 * @param bean
	 * @return
	 */
	public static int sureOrder(LendBean bean){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		int result=0;
		try {
			result=mapper.sureOrder(bean);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 判断租借清单中是否存在该条记录
	 * @param map
	 * @return
	 */
	public static int isExtisSerialNumberInfo(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		int result=0;
		try {
			result=mapper.isExtisSerialNumberInfo(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 添加设备清单
	 * @param map
	 * @return
	 */
	public static int addOrder(List<Map<String,Object>> list){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		int result=0;
		try {
			result=mapper.addOrder(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 更新资产租借状态
	 * @param map
	 * @return
	 */
	public static int updateAssetStatus(List<Map<String,Object>> list){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		int result=0;
		try {
			result=mapper.updateAssetStatus(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除设备清单中的设备
	 * @param map
	 * @return
	 */
	public static int deleteLendOrderE(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		int result=0;
		try {
			result=mapper.deleteLendOrderE(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 租借清单列表
	 * @param lendId
	 * @return
	 */
	public static List<Map<String,Object>> lendInfoList(Map<String,Object> map){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.lendInfoList(map);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 查询待审核 归还借设备清单 
	 * @param map
	 * @return
	 */
	public static List<Map<String,Object>> checkLendOrderCount(){
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
		LendMapper mapper = sqlSession.getMapper(LendMapper.class);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.checkLendOrderCount();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
