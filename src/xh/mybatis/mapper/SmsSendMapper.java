package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SmsSendMapper {
	/**
	 * 查询接收短信
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> recInfo(Map<String,Object> map)throws Exception;
	
	/**
	 * 短信总数
	 * @return
	 * @throws Exception
	 */
	public int  recCount(Map<String,Object> map)throws Exception;
	
	/**
	 * 根据id删除短信
	 * @return
	 * @throws Exception
	 */
	public void  deleteById(List<String> list)throws Exception;
	
}
