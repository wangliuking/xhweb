package xh.mybatis.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.BsStatusBean;
import xh.mybatis.bean.EmhBean;
import xh.mybatis.mapper.BsStatusMapper;
import xh.mybatis.tools.DbTools;
import xh.mybatis.tools.MoreDbTools;

public class BsStatusService {

	public List<BsStatusBean> selectAllBsStatus() throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<BsStatusBean> BsStatus = mapper.selectAllBsStatus();
		session.commit();
		session.close();
		return BsStatus;
	}

	/**
	 * 导出现网基站的运行记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<BsStatusBean> excelToBsStatus() throws Exception {
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<BsStatusBean> list = mapper.excelToBsStatus();
		session.close();
		return list;
	}
	/**
	 * 基站下的环控fsuId 
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public static  String fsuIdBySiteId(int siteId) throws Exception {
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		String fsuId = mapper.fsuIdBySiteId(siteId);
		session.close();
		return fsuId;
	}
	/**
	 * 基站下的环控状态
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public static  List<EmhBean> bsEmh(String fsuId) throws Exception {
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<EmhBean> list = mapper.bsEmh(fsuId);
		session.close();
		return list;
	}

}
