package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface MenuMapper {
	
	
	/**
	 * 菜单列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	
	public List<Map<String,Object>> menuList(int roleId)throws Exception;
	
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
	public int menuExists(Map<String,Object> map) throws Exception;
	
	public int menuExistsByParentId(Map<String,Object> map) throws Exception;
	
	/**
	 * 删除菜单
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int deleteMenu(List<String> list) throws Exception;
	
	/**
	 * 添加菜单
	 * @return
	 * @throws Exception
	 */
	
	public int addMenu() throws Exception;
	public int addParentMenu(String roleId) throws Exception;

}
