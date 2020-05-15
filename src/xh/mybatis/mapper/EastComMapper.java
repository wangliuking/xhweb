package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.EastBsCallDataBean;
import xh.mybatis.bean.EastDsCallBean;
import xh.mybatis.bean.EastGroupCallBean;
import xh.mybatis.bean.EastMscCallBean;
import xh.mybatis.bean.EastMscCallDetailBean;
import xh.mybatis.bean.EastMscDayBean;
import xh.mybatis.bean.EastVpnCallBean;

public interface EastComMapper {
	
	/*基站信道排队top5*/
	public List<Map<String,Object>> queueTop5(String time)throws Exception;
	
	public List<Map<String,Object>> queueTopBsName(List<String> list)throws Exception;
	
	public List<EastBsCallDataBean> get_bs_call_data(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean> get_bs_now_call_data(String time)throws Exception;
	public int write_bs_call_data(List<EastBsCallDataBean> list)throws Exception;
	public int write_bs_now_call_data(List<EastBsCallDataBean> list)throws Exception;
	
	public List<EastMscCallBean> get_msc_call_data(Map<String, Object> map)throws Exception;
	public int write_msc_call_data(List<EastMscCallBean> list)throws Exception;
	
	public List<EastVpnCallBean> get_vpn_call_data(Map<String, Object> map)throws Exception;
	public int write_vpn_call_data(List<EastVpnCallBean> list)throws Exception;
	
	public List<EastMscCallDetailBean> get_msc_call_detail_data(Map<String, Object> map)throws Exception;
	public int write_msc_call_detail_data(List<EastMscCallDetailBean> list)throws Exception;
	
	
	
	
	
	public List<EastBsCallDataBean>chart_bs_call(Map<String, Object> map)throws Exception;
	public List<EastVpnCallBean>chart_vpn_call(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean>chart_bs_level_call(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean>chart_bs_area_call(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean>chart_bs_zone_call(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean>chart_bs_zone_top10_call(Map<String, Object> map)throws Exception;
	public List<EastDsCallBean>chart_ds_call(Map<String, Object> map)throws Exception;
	public List<EastGroupCallBean>chart_vpn_group_call(Map<String, Object> map)throws Exception;
	
	
	public List<EastBsCallDataBean>chart_bs_call_top10(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean>chart_bs_userreg_top10(Map<String, Object> map)throws Exception;
	public List<EastBsCallDataBean>chart_bs_queue_top10(Map<String, Object> map)throws Exception;
	public List<EastVpnCallBean>chart_vpn_call_top10(Map<String, Object> map)throws Exception;
	
	
	
	public EastMscDayBean chart_msc_call(Map<String, Object> map)throws Exception;
	public EastMscDayBean chart_month_msc(Map<String, Object> map)throws Exception;
	
	
	public int del_data(String time)throws Exception;
	
	
	
	
	

}
