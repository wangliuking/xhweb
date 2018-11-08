package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.AttachmentBean;

public interface AttachmentMapper {
	int add(AttachmentBean bean) throws Exception;
	
	int update(AttachmentBean bean) throws Exception;
	
	int del(List<String> list)throws Exception;
	
	
	int add_config(AttachmentBean bean) throws Exception;
	
	int update_config(AttachmentBean bean) throws Exception;
	
	int del_config(List<String> list)throws Exception;
	
	
	
	int count(Map<String,Object> map) throws Exception;
	List<AttachmentBean> attachmentList(Map<String,Object> map)throws Exception;
	
	
	int count_config(Map<String,Object> map) throws Exception;	
	List<AttachmentBean> attachmentList_config(Map<String,Object> map)throws Exception;
	
	int attachmentList_config_isexists(Map<String,Object> map) throws Exception;
	int attachmentList_isexists(Map<String,Object> map) throws Exception;
	
	
	
	List<AttachmentBean> attachmentList_month_one(String time)throws Exception;

}
