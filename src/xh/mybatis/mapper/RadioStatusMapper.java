package xh.mybatis.mapper;

import java.util.List;
import xh.mybatis.bean.RadioStatusBean;

public interface RadioStatusMapper {
	/**
	 * 查询所有终端状态
	 */
	public List<RadioStatusBean> selectAllRadioStatus() throws Exception;

}
