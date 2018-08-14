package xh.mybatis.mapper;

import java.util.ArrayList;
import java.util.Map;

import xh.mybatis.bean.EventReportBean;
import xh.mybatis.bean.WorkBean;

public interface EventReportMapper {
	
	/**
	 * 事件上报列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ArrayList<EventReportBean> eventReportlist(Map<String,Object> map)throws Exception;
	/**
	 * 事件上报列表总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int count(Map<String,Object> map)throws Exception;
	
	/**
	 * 新增事件上报
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int addEventReport(EventReportBean bean)throws Exception;
	
	/**
	 * 签收事件上报
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int signEventReport(Map<String,Object> modeMap)throws Exception;

}
