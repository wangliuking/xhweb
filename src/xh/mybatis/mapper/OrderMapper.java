package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.tcpBean.ErrProTable;

public interface OrderMapper {
	
	//派单列表
	public List<Map<String,Object>> orderList(Map<String,Object> map) throws Exception;
	//派单列表总数
	public int orderListCount(Map<String,Object> map) throws Exception;
	//新增派单
	public int addOrder(ErrProTable bean) throws Exception;
	//修改确认派单
	public int updateOrder(Map<String,Object> map) throws Exception;
	public int updateSfOrder(ErrProTable bean) throws Exception;
	
	//更新基站故障状态记录
	public int updateBsFault(Map<String,Object> map) throws Exception;
	
	//运维用户列表
	public List<Map<String,Object>> userList() throws Exception;

}
