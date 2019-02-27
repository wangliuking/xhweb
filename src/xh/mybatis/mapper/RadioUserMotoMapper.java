package xh.mybatis.mapper;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.RadioBean;
import xh.mybatis.bean.RadioUserMotoBean;


public interface RadioUserMotoMapper {
	public List<Map<String,Object>> radiouserById(Map<String,Object> map)throws Exception;
	public List<Map<String,Object>> radioList()throws Exception;
	public RadioBean radio_one(String RadioID)throws Exception;
	public List<Map<String,Object>> securityGroupList()throws Exception;
	public int  radioUserIsExists(int id)throws Exception;	
	public int  count(Map<String,Object> map)throws Exception;	
	public int  insertRadioUser(RadioUserMotoBean bean)throws Exception;
	public int  updateByRadioUserId(RadioUserMotoBean bean)throws Exception;
	public int  deleteByRadioUserId(List<String> list)throws Exception;

}
