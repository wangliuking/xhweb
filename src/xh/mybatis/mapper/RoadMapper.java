package xh.mybatis.mapper;


import java.util.List;
import java.util.Map;

public interface RoadMapper {
	/**
	 * 查询路测信息
	 * @return
	 * @throws Exception
	 */
	public List roadInfo(Map<String,Object> map)throws Exception;
	
	/**
	 * 路测信息总数
	 * @return
	 * @throws Exception
	 */
	public int roadCount(Map<String,Object> map)throws Exception;

}
