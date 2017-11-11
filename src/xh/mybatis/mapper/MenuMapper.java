package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface MenuMapper {
	
	/**
	 * 获取菜单子项
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> menuChild(int pId)throws Exception;

}
