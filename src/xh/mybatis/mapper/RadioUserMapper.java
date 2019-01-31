package xh.mybatis.mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.RadioUserBean;

public interface RadioUserMapper {
	/**
	 * 查询无线用户信息
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,String>> radiouserById(Map<String,Object> map)throws Exception;
	
	/**
	 * 根据无线用户ID判断用户是否存在
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int  radioUserIsExists(int id)throws Exception;
	
	/**
	 * 无线用户总数
	 * @return
	 * @throws Exception
	 */
	public int  radiouserCount(Map<String,Object> map)throws Exception;
	
	/**
	 * 增加无线用户
	 * @return
	 * @throws Exception
	 */
	public int  insertRadioUser(RadioUserBean bean)throws Exception;
	/**
	 * 根据无线用户ID修改用户
	 * @return
	 * @throws Exception
	 */
	public int  updateByRadioUserId(Map<String,Object> map)throws Exception;
	/**
	 * 根据无线用户ID查找无线用户
	 * @return
	 * @throws Exception
	 */
	public int  selectByRadioUserId(int C_ID)throws Exception;
	/**
	 * 根据无线用户ID删除用户
	 * @return
	 * @throws Exception
	 */
	public int deleteBsByRadioUserId(List<String> list)throws Exception;
	/**
	 * 查询所有无线用户
	 * @return
	 * @throws Exception
	 */
	public List<HashMap>allRadioUser()throws Exception;
	/**
	 * 根据vpnId查询用户
	 * 
	 */
	public List<HashMap<String,String>> allByVpnId(Map<String,Object> map)throws Exception;
	/**
	 * 根据vpnId查询总数
	 */
	public int CountByVpnId(Map<String,Object> map)throws Exception;
	
	/**
	 * 根据vpnId查询所有无线用户id
	 */
	public List<String> selectCIdByVpnId(Map<String,Object> map)throws Exception;
	/**
	 * 标记moto手台
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int  update_moto(Map<String,Object> map)throws Exception;

}
