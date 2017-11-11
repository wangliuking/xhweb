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
	public static List<Map<String,Object>> menuChild(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		MenuMapper mapper=sqlSession.getMapper(MenuMapper.class);
		List<Map<String,Object>> list1=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list2=new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		try{
			//一级菜单
			list1=mapper.menuChild(0);
			for (Map<String, Object> map : list1) {
				Map<String,Object> mapOne=map;
				
				List<Map<String,Object>> listOne=mapper.menuChild(Integer.parseInt(map.get("id").toString()));
				List<Map<String,Object>> list3=new ArrayList<Map<String,Object>>();
				map.put("num",listOne.size());
				
				
				for (Map<String, Object> map2 : listOne) {
					Map<String,Object> mapTwo=map2;
					List<Map<String,Object>> listTwo=mapper.menuChild(Integer.parseInt(map2.get("id").toString()));
					mapTwo.put("child", listTwo);
					mapTwo.put("num",listTwo.size());
					
					list3.add(mapTwo);
				}
				mapOne.put("child", list3);
				
				list2.add(mapOne);
				
			/*	
				
				
				
				for (Map<String, Object> map2 : child) {
					//list2.add(map2);
					List<Map<String,Object>> child2=mapper.menuChild(Integer.parseInt(map2.get("pId").toString()));
					map2.put("child", child2);
				}
				map.put("child", child);*/
			}
			
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list2;
	}

}
