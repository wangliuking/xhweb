package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import xh.mybatis.mapper.MenuMapper;
import xh.mybatis.tools.MoreDbTools;

public class MenuService {
	protected final static Log log=LogFactory.getLog(MenuService.class);
	
	/**
	 * 获取菜单子项
	 * @param pId
	 * @return
	 */
	public static List<Map<String,Object>> menuChild(int roleId,int flag){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		MenuMapper mapper=sqlSession.getMapper(MenuMapper.class);
		List<Map<String,Object>> list1=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list2=new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		
		
		try{
			//一级菜单
			Map<String,Object> paraMap=new HashMap<String, Object>();
			paraMap.put("pId", 0);
			paraMap.put("flag", flag);
			paraMap.put("roleId", roleId);
			list1=mapper.menuChild(paraMap);
			for (Map<String, Object> map : list1) {
				Map<String,Object> mapOne=map;
				
				
				
				Map<String,Object> paraMap2=new HashMap<String, Object>();
				paraMap2.put("pId", Integer.parseInt(map.get("id").toString()));
				paraMap.put("flag", flag);
				paraMap2.put("roleId", roleId);
				
				
				
				List<Map<String,Object>> listOne=mapper.menuChild(paraMap2);
				List<Map<String,Object>> list3=new ArrayList<Map<String,Object>>();
				map.put("num",listOne.size());
				
				
				for (Map<String, Object> map2 : listOne) {
					Map<String,Object> mapTwo=map2;
					
					
					
					Map<String,Object> paraMap3=new HashMap<String, Object>();
					paraMap3.put("pId", Integer.parseInt(map2.get("id").toString()));
					paraMap.put("flag", flag);
					paraMap3.put("roleId", roleId);
					
					
					
					
					List<Map<String,Object>> listTwo=mapper.menuChild(paraMap3);
					mapTwo.put("children", listTwo);
					mapTwo.put("num",listTwo.size());
					
					list3.add(mapTwo);
				}
				mapOne.put("children", list3);
				
				list2.add(mapOne);
			}
			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list2;
	}
	/**
	 * 更新菜单
	 * @param map
	 * @return
	 */
	public static int updateMenu(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		MenuMapper mapper=sqlSession.getMapper(MenuMapper.class);
		int result=-1;
		try {
			result=mapper.updateMenu(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static int updateMenuRoleId(int roleId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		MenuMapper mapper=sqlSession.getMapper(MenuMapper.class);
		int result=-1;
		try {
			result=mapper.updateMenuRoleId(roleId);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 判断菜单是否存在
	 * @return
	 */
	public static int menuExists(int roleId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		MenuMapper mapper=sqlSession.getMapper(MenuMapper.class);
		int result=-1;
		try {
			result=mapper.menuExists(roleId);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 新增菜单
	 * @return
	 */
	public static int addMenu(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		MenuMapper mapper=sqlSession.getMapper(MenuMapper.class);
		int result=-1;
		try {
			result=mapper.addMenu();
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
