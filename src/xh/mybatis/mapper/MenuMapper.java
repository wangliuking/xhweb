package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface MenuMapper {
	
	/**
	 * 获取菜单子项
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> menuChild(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 修改菜单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateMenu(Map<String,Object> map) throws Exception;
	
	public int updateMenuRoleId(int roleId) throws Exception;
	
	/**
	 * 根据roleId 判断菜单是否存在
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public int menuExists(int roleId) throws Exception;
	
	/**
	 * 添加菜单
	 * @return
	 * @throws Exception
	 */
	public int addMenu() throws Exception;

}
