package xh.mybatis.mapper;

import java.util.List;

import xh.mybatis.bean.EmhBean;

public interface EmhMapper {
	/**
	 * 查询基站环境监控实时状态
	 * @return
	 * @throws Exception
	 */
	public List<EmhBean> oneBsEmh(String code)throws Exception;

}
