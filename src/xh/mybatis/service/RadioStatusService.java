package xh.mybatis.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.RadioStatusBean;
import xh.mybatis.mapper.RadioStatusMapper;
import xh.mybatis.tools.MoreDbTools;

public class RadioStatusService {

	public List<RadioStatusBean> selectAllRadioStatus() throws Exception{
		SqlSession session=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		RadioStatusMapper mapper=session.getMapper(RadioStatusMapper.class);
	        List<RadioStatusBean> RadioStatus=mapper.selectAllRadioStatus();
	        session.commit();  
	        session.close();
	        return RadioStatus;   
	}

}
