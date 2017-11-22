package xh.mybatis.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<BsStatusBean> list = mapper.excelToBsStatus();
		session.close();
		return list;
	}

	/**
	 * 基站下的环控fsuId
	 * 
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public static String fsuIdBySiteId(int siteId) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		String fsuId = mapper.fsuIdBySiteId(siteId);
		session.close();
		return fsuId;
	}

	/**
	 * 基站下的环控状态
	 * 
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, Object> bsEmh(int siteId) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		/* List<EmhBean> list = mapper.bsEmh(fsuId); */

		String fsuId = null;
		HashMap<String, Object> result = new HashMap<String, Object>();

		fsuId = BsStatusService.fsuIdBySiteId(siteId);

		List<EmhBean> list = mapper.bsEmh(fsuId);
		;
		Map<String, Object> map = new HashMap<String, Object>();
		for (EmhBean emhBean : list) {
			map.put(emhBean.getSingleId(), emhBean.getSingleValue());
			map.put("time", emhBean.getUpdateTime());
		}
		DecimalFormat df = new DecimalFormat("#.0");

		if (list.size() > 0) {

			try {

				result.put("time", map.get("time").toString());
				// 开关量
				if (map.get("017001") != null) {
					result.put("water",
							Float.parseFloat(map.get("017001").toString()));// 水浸告警
				}
				if (map.get("017002") != null) {
					result.put("smoke",
							Float.parseFloat(map.get("017002").toString()));// 烟雾告警
				}
				if (map.get("017004") != null) {
					result.put("red",
							Float.parseFloat(map.get("017004").toString()));// 红外告警
				}
				if (map.get("017020") != null) {
					result.put("door",
							Float.parseFloat(map.get("017020").toString()));// 门碰告警
				}

				// 温湿度

				if (map.get("017301") != null) {
					result.put("temp", df.format(Float.parseFloat(map.get(
							"017301").toString())));// 温度XX ℃
				}
				if (map.get("017302") != null) {
					result.put("damp", df.format(Float.parseFloat(map.get(
							"017302").toString())));// 湿度XX %RH
				}

				// UPS
				if (map.get("008304") != null) {
					result.put("ups1",
							Float.parseFloat(map.get("008304").toString()));// 输入相电压Ua
				}
				if (map.get("008315") != null) {
					result.put("ups2",
							Float.parseFloat(map.get("008315").toString()));// 输出相电压Ua
				}
				if (map.get("008321") != null) {
					result.put("ups3",
							Float.parseFloat(map.get("008321").toString()));// 输出频率
				}
				if (map.get("008334") != null) {
					result.put("ups4",
							Float.parseFloat(map.get("008334").toString()));// 电池组电压
				}
				if (map.get("008408") != null) {
					result.put("ups5",
							Float.parseFloat(map.get("008408").toString()));// 电池方式工作状态

				}

				// FSU

				if (map.get("076002") != null) {
					result.put("fsu1",
							Float.parseFloat(map.get("076002").toString()));// 非智能设备采集器通信状态告警

				}
				if (map.get("076501") != null) {
					result.put("fsu2",
							Float.parseFloat(map.get("076501").toString()));// 开关电源通信状态告警

				}
				if (map.get("076507") != null) {
					result.put("fsu3",
							Float.parseFloat(map.get("076507").toString()));// UPS设备通信状态告警

				}
				if (map.get("076509") != null) {
					result.put("fsu4",
							Float.parseFloat(map.get("076509").toString()));// 电池方式工作状态

				}
				if (map.get("008408") != null) {
					result.put("fsu5",
							Float.parseFloat(map.get("008408").toString()));// 智能电表通信状态告警

				}
				// 智能电表
				if (map.get("092301") != null) {
					result.put("e1",
							Float.parseFloat(map.get("092301").toString()));// 电压--->相电压Ua

				}
				if (map.get("092304") != null) {
					result.put("e2",
							Float.parseFloat(map.get("092304").toString()));// 电流--->相电流Ia

				}
				if (map.get("092314") != null) {
					result.put("e3",
							Float.parseFloat(map.get("092314").toString()));// 功率因数--->总功率因数

				}
				if (map.get("092316") != null) {
					result.put("e4",
							Float.parseFloat(map.get("092316").toString()));// 电度度数-->正向有功电能

				}
				if (map.get("092315") != null) {
					result.put("e5",
							Float.parseFloat(map.get("092315").toString()));// 频率-->频率

				}

			} catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		session.close();
		return result;
	}

	/**
	 * 基站下的环控告警
	 * 
	 * @param fsuId
	 * @return
	 * @throws Exception
	 */
	public static List<EmhBean> bsEmhAlarm(int siteId) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		String fsuId = null;

		fsuId = BsStatusService.fsuIdBySiteId(siteId);
		List<EmhBean> list = mapper.bsEmhAlarm(fsuId);
		session.close();
		return list;
	}

	/**
	 * 基站下的bsc状态
	 * 
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> bsc(int bsId) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = mapper.bsc(bsId);
		session.close();
		return list;
	}

	/**
	 * 基站下的psm状态
	 * 
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> psm(int bsId) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = mapper.psm(bsId);
		session.close();
		return list;
	}

	/**
	 * 基站下的bsr状态
	 * 
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> bsr(int bsId) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = mapper.bsr(bsId);
		session.close();
		return list;
	}

	/**
	 * 基站下的dpx状态
	 * 
	 * @param bsId
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> dpx(int bsId) throws Exception {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		BsStatusMapper mapper = session.getMapper(BsStatusMapper.class);
		List<Map<String, Object>> list = mapper.dpx(bsId);
		session.close();
		return list;
	}

}
