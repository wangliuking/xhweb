package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.EmhBean;
import xh.mybatis.mapper.EmhMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.mybatis.tools.MoreDbTools.DataSourceEnvironment;

public class EmhService {
	/**
	 * 查询基站环境监控实时状态
	 * @param code
	 * @return
	 */
	public static List<EmhBean> oneBsEmh(String code) {
		SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.emh);
		EmhMapper mapper = sqlSession.getMapper(EmhMapper.class);
		List<EmhBean> list = new ArrayList<EmhBean>();
		try {
			list = mapper.oneBsEmh(code);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
