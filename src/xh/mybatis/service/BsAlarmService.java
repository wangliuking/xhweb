package xh.mybatis.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.BsAlarmBean;
import xh.mybatis.mapper.BsAlarmMapper;
import xh.mybatis.tools.DbTools;

public class BsAlarmService {

	public List<BsAlarmBean> selectAllBsAlarm() throws Exception{
		SqlSession session=DbTools.getSession();
		BsAlarmMapper mapper=session.getMapper(BsAlarmMapper.class);
	        List<BsAlarmBean> BsAlarm=mapper.selectAllBsAlarm();
	        session.commit();
	        session.close();
	        return BsAlarm;   
	}

}
