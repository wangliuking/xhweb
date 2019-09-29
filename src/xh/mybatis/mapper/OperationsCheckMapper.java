package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.CheckMoneyBean;
import xh.mybatis.bean.CheckMonthBsBreakBean;
import xh.mybatis.bean.CheckRoomEquBean;
import xh.mybatis.bean.MoneyBean;
import xh.mybatis.bean.OperationsCheckBean;
import xh.mybatis.bean.OperationsCheckDetailBean;
import xh.mybatis.bean.OperationsCheckScoreBean;

public interface OperationsCheckMapper {
	
	List<OperationsCheckBean> dataList(Map<String,Object> map)throws Exception;
	int count(Map<String,Object> map)throws Exception;
	
	int add(OperationsCheckBean bean)throws Exception;
	
	int isExists(OperationsCheckBean bean)throws Exception;
	
	List<CheckMoneyBean> searchDetail(Map<String, Object> map)throws Exception;
	
	List<CheckMoneyBean> show_money_detail(Map<String, Object> map)throws Exception;
	
	List<OperationsCheckScoreBean> searchScore(Map<String, Object> map)throws Exception;
	
	List<OperationsCheckScoreBean> show_score_detail(Map<String,Object> map)throws Exception;
	
	int detailExists(String time)throws Exception;
	
	int addDetail(List<CheckMoneyBean> bean)throws Exception;
	
	int addScore(List<OperationsCheckScoreBean> list)throws Exception;
	
	int updateDetail(OperationsCheckDetailBean bean)throws Exception;
	
	int check2(OperationsCheckBean bean)throws Exception;
	int check3(OperationsCheckBean bean)throws Exception;
	int check4(OperationsCheckBean bean)throws Exception;
	int check5(OperationsCheckBean bean)throws Exception;
	
	int sealScoreMoneyComplete(OperationsCheckBean bean)throws Exception;
	
	int signMeet(OperationsCheckBean bean)throws Exception;
	
	int sealFile(int id)throws Exception;
	int delFile(Map<String, Object> map)throws Exception;
	
	
	int update_file_info(Map<String, Object> map)throws Exception;
	int up_score_money(OperationsCheckBean bean)throws Exception;
	int sure_score_money(OperationsCheckBean bean)throws Exception;
	
	int addFile(List<Map<String,Object>> list)throws Exception;
	List<Map<String,Object>> searchFile(String applyId) throws Exception;
	
	
	
	
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
	
	int insert_check_month_money_detail(CheckMoneyBean bean)throws Exception;
	
	int del_score(String time)throws Exception;
	int del_money(String time)throws Exception;
	
	/*<!--基站故障 -->*/
	List<Map<String,Object>> bs_error(String time) throws Exception;
	
	/**(特别)重大故障
	 */
	
	List<Map<String,Object>> tb_zd_fault(Map<String, Object> map)throws Exception;
	/*<!--基站故障 -->*/
	List<Map<String,Object>> bs_error_money(String time) throws Exception;
	
	List<Map<String,Object>> error_money_total(String time) throws Exception;
	
	/*<!-- 考核一级基站 -->*/
	List<Map<String,Object>> check_onelevel_bs() throws Exception;
	/*<!-- 考核二级基站 -->*/
	List<Map<String,Object>> check_twolevel_bs() throws Exception;
	/*<!-- 考核三级基站 -->*/
	List<Map<String,Object>> check_threelevel_bs() throws Exception;
	
	/*	<!-- 考核特别重大故障 -->*/
	List<Map<String,Object>> check_onelevel_fault() throws Exception;
	/*	<!-- 考核重大故障 -->*/
	List<Map<String,Object>> check_twolevel_fault() throws Exception;
	
	/*	查询考核扣分明细*/
	List<Map<String,Object>> search_score_detail(Map<String,Object> map) throws Exception;
	
	/*	查询考核扣分明细数量*/
	int search_score_detail_count(Map<String,Object> map) throws Exception;
	
	/*	查询考核扣款明细*/
	List<Map<String,Object>> search_money_detail(Map<String,Object> map) throws Exception;
	
	/*	查询考核扣款明细数量*/
	int search_money_detail_count(Map<String,Object> map) throws Exception;
	/*查询核减数量*/
	int search_checkcut_count(Map<String,Object> map) throws Exception;
	
	/*写入每月基站断站时长*/
	int insert_check_month_bs_break(CheckMonthBsBreakBean bean) throws Exception;
	
	/*查询统计年的断站时长*/
	List<Map<String,Object>> search_year_bs_break(Map<String,Object> map) throws Exception;
	
	/*查询统计月的断站时长*/
	List<Map<String,Object>> search_month_bs_break(Map<String,Object> map) throws Exception;
	

}
