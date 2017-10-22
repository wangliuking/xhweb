package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.RadioUserSeria;

public interface RadioUserSeriaMapper {
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> ById(Map<String,Object> map)throws Exception;
	
	/**
	 * 总数
	 * @return
	 * @throws Exception
	 */
	public int  Count(Map<String,Object> map)throws Exception;
	
	/**
	 * 增加
	 * @return
	 * @throws Exception
	 */
	public int  insert(Map<String,Object> map)throws Exception;
	/**
	 * 修改
	 * @return
	 * @throws Exception
	 */
	public int  update(Map<String,Object> map)throws Exception;
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	public void  delete(List<String> list)throws Exception;
}