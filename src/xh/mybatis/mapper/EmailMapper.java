package xh.mybatis.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xh.mybatis.bean.EmailBean;

public interface EmailMapper {
	/**
	 * 写邮件
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public void insertEmail(EmailBean bean)throws Exception;
	/**
	 * 查询邮件
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<HashMap> emailInfo(Map<String, Object> map)throws Exception;
	/**
	 * 邮件总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int emailCount()throws Exception;
	/**
	 * 未读邮件总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int noReadEmailCount()throws Exception;
	/**
	 * 标记已读
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int updateById(List<String> list)throws Exception;
	/**
	 * 删除邮件
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int deleteById(List<String> list)throws Exception;

}
