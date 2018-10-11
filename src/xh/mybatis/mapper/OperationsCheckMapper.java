package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.CheckRoomEquBean;
import xh.mybatis.bean.OperationsCheckBean;
import xh.mybatis.bean.OperationsCheckDetailBean;

public interface OperationsCheckMapper {
	
	List<OperationsCheckBean> dataList(Map<String,Object> map)throws Exception;
	int count(Map<String,Object> map)throws Exception;
	
	int add(OperationsCheckBean bean)throws Exception;
	
	OperationsCheckDetailBean searchDetail(String time)throws Exception;
	
	int detailExists(String time)throws Exception;
	
	int addDetail(OperationsCheckDetailBean bean)throws Exception;
	
	int updateDetail(OperationsCheckDetailBean bean)throws Exception;
	
	int check2(OperationsCheckBean bean)throws Exception;
	int check3(OperationsCheckBean bean)throws Exception;
	int check4(OperationsCheckBean bean)throws Exception;
	
	
	
	
	/**
	 * 机房配套设备检查
	 * */
	List<Map<String,Object>> check_room_equ() throws Exception;
	/*<!-- 考核运维人员是否达到20人 -->*/
	int check_phone_book()throws Exception;
	/*<!-- 考核运办公场所 ,考核仪器仪表 ,考核运维车辆不足3辆 -->*/
	Map<String,Object> check_officeaddress() throws Exception;
	/*考核备品备件完好率低于80%*/
	List<Map<String,Object>> check_attachment() throws Exception;
	
	int insert_check_month_detail(CheckRoomEquBean bean)throws Exception;
	

	

}
