package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.ContactsBean;
import xh.mybatis.bean.OndutyBean;
import xh.mybatis.bean.WebLogBean;

public interface ContactsMapper {
	public List<ContactsBean> phone_list()throws Exception;
	
	public int phone_info_by_namenum(ContactsBean bean)throws Exception;
	
	public int phone_update(ContactsBean bean)throws Exception;
	
	public int phone_write(ContactsBean bean)throws Exception;
	public int phone_del(List<String> list)throws Exception;


}
