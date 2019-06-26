package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.RecordCommunicationBean;
import xh.mybatis.bean.RecordEmergencyBean;
import xh.mybatis.bean.UserNeedBean;

public interface UserNeedMapper {
	
	List<UserNeedBean> data_all(Map<String,Object> map) throws Exception;
	
	int data_all_count(Map<String, Object> map) throws Exception;
	
	int add(UserNeedBean bean) throws Exception;

	int update(UserNeedBean bean) throws Exception;
	
	int del(List<String> list) throws Exception;
	
	
	List<RecordCommunicationBean> communication_list(Map<String,Object> map) throws Exception;
	
	int communication_list_count(Map<String, Object> map) throws Exception;
	
	int add_communication(RecordCommunicationBean bean) throws Exception;

	int update_communication(RecordCommunicationBean bean) throws Exception;
	
	int upload_communication(RecordCommunicationBean bean) throws Exception;
	
	int del_communication(List<String> list) throws Exception;

	
    List<RecordEmergencyBean> emergency_list(Map<String,Object> map) throws Exception;
	
	int emergency_list_count(Map<String, Object> map) throws Exception;
	
	int add_emergency(RecordEmergencyBean bean) throws Exception;

	int update_emergency(RecordEmergencyBean bean) throws Exception;
	
	int upload_emergency(RecordEmergencyBean bean) throws Exception;
	
	int del_emergency(List<String> list) throws Exception;
	int addFile(List<Map<String,Object>> list)throws Exception;
	List<Map<String, Object>> searchFile(int id) throws Exception;
}
