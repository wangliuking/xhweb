package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.Map;

import xh.mybatis.bean.CallListBean;

public interface CallListMapper {
	/**
	 * 呼叫列表
	 * @return
	 * @throws Exception
	 */
	public ArrayList<CallListBean>  selectCallList(Map<String,Object> map)throws Exception;
	
	/**
	 * 通话总数
	 * @return
	 * @throws Exception
	 */
	public int  CallListCount(Map<String,Object> map)throws Exception;
	
}
