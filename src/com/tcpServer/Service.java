package com.tcpServer;
 
import java.util.*;

import com.tcpBean.*;
import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.mapper.EventMapper;
import xh.mybatis.mapper.TcpMapper;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.SqlServerService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.tools.MoreDbTools;
import xh.org.listeners.SingLoginListener;

import com.tcpServer.ServerDemo.SocketThread;

public class Service {
	// 发送用对象
	private static LoginAck loginAck = new LoginAck();
	private static UserInfoAck userInfoAck = new UserInfoAck();
	private static ErrProTable errProTable = new ErrProTable();
	private static ErrProTableAck errProTableAck = new ErrProTableAck();
	private static ErrCheckAck errCheckAck = new ErrCheckAck();
	private static NetManagerTableAck netManagerTableAck = new NetManagerTableAck();
	private static DispatchTableAck dispatchTableAck = new DispatchTableAck();
	private static MovebsTableAck movebsTableAck = new MovebsTableAck();
	private static OwnbsTableAck ownbsTableAck = new OwnbsTableAck();
	private static GetMovebsInfoAck getMovebsInfoAck = new GetMovebsInfoAck();
	private static GetOwnbsInfoAck getOwnbsInfoAck = new GetOwnbsInfoAck();
	private static GetBsTypeAck getBsTypeAck = new GetBsTypeAck();
	private static GetBsInfoAck getBsInfoAck = new GetBsInfoAck();
	private static GetForGpsDstAck getForGpsDstAck = new GetForGpsDstAck();
	private static BsInspectTableAck bsInspectTableAck = new BsInspectTableAck();
	private static GetErrBsInfoAck getErrBsInfoAck = new GetErrBsInfoAck();
	private static GetInspectBsListAck getInspectBsListAck = new GetInspectBsListAck();
	private static GetUnInspectBsListAck getUnInspectBsListAck = new GetUnInspectBsListAck();
	private static GetTotalInfoAck getTotalInfoAck = new GetTotalInfoAck();
	
	private static FunUtil funUtil = new FunUtil();

	/**
	 * 登录处理
	 */
	public static LoginAck appLogin(UserLogin userLogin){
		Map<String,Object> map=new HashMap<String, Object>();
		map = WebUserServices.selectUserByRootAndPass(userLogin.getUserid(), funUtil.MD5(userLogin.getPasswd()));
		loginAck.setUserid(userLogin.getUserid());
		loginAck.setPasswd(userLogin.getPasswd());
		loginAck.setSerialnumber(userLogin.getSerialnumber());
		if (map!=null) {			
			loginAck.setAck("0");
		}else{
			loginAck.setAck("1");
		}
		return loginAck;	
	}
	
	/**
	 * 用户信息处理
	 */
	public static UserInfoAck appUserInfo(UserInfo userInfo){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		userInfoAck.setSerialnumber(userInfo.getSerialnumber());
		userInfoAck.setUserid(userInfo.getUserid());
		try{
			Map<String,String> map = mapper.selectUserName(userInfo.getUserid());
			userInfoAck.setUsername(map.get("userName"));
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userInfoAck;
	}

	/**
	 * 获取当前所有断站
	 */
	public static GetErrBsInfoAck appGetErrBsInfoAck(GetErrBsInfo getErrBsInfo){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		getErrBsInfoAck.setUserid(getErrBsInfo.getUserid());
		getErrBsInfoAck.setSerialnumber(getErrBsInfo.getSerialnumber());
		try {
			getErrBsInfoAck.setBslist(mapper.selectBreakBsInfo());
			getErrBsInfoAck.setStatus("1");
		}catch (Exception e){
			e.printStackTrace();
		}
		return getErrBsInfoAck;
	}

	/**
	 * 获取基站信息
	 */
	public static GetBsTypeAck appGetBsTypeAck(GetBsType getBsType){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		getBsTypeAck.setUserid(getBsType.getUserid());
		String bsId = getBsType.getBsid();
		getBsTypeAck.setBsid(bsId);
		try {
			if(!"".equals(bsId) && bsId!=null){
				Map<String,Object> map = mapper.selectByBsIdNew(bsId);
				if(map!=null){
					int bslevel = (Integer) map.get("bslevel");
					getBsTypeAck.setBslevel(String.valueOf(bslevel));
					getBsTypeAck.setBsname(map.get("bsname").toString());
					getBsTypeAck.setPeriod(map.get("period").toString());
					String bsType = map.get("bstype").toString();
					if("铁塔".equals(bsType) || "移动".equals(bsType) || "电信".equals(bsType)){
						getBsTypeAck.setBstype("运营商机房");
					}else{
						getBsTypeAck.setBstype(bsType+"机房");
					}
					getBsTypeAck.setAck("0");
				}else{
					getBsTypeAck.setAck("1");
				}
				sqlSession.close();
			}else{
				getBsTypeAck.setAck("1");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getBsTypeAck;
	}

	/**
	 * 获取当月已巡基站信息
	 */
	public static GetInspectBsListAck appGetInspectBsListAck(GetInspectBsList getInspectBsList){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		getInspectBsListAck.setUserid(getInspectBsList.getUserid());
		getInspectBsListAck.setSerialnumber(getInspectBsList.getSerialnumber());
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int month = cal.get(Calendar.MONTH);
			List<Map<String,Object>> list = mapper.selectInspectionBsList(month+1);
			getInspectBsListAck.setBslist(list);
			getInspectBsListAck.setStatus("1");
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getInspectBsListAck;
	}

	/**
	 * 获取当月未巡基站信息
	 */
	public static GetUnInspectBsListAck appGetUnInspectBsListAck(GetUnInspectBsList getUnInspectBsList){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		getUnInspectBsListAck.setUserid(getUnInspectBsList.getUserid());
		getUnInspectBsListAck.setSerialnumber(getUnInspectBsList.getSerialnumber());
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int month = cal.get(Calendar.MONTH);
			List<Map<String,Object>> list = mapper.selectNotInspectionBsList(month+1);
			getUnInspectBsListAck.setBslist(list);
			getUnInspectBsListAck.setStatus("1");
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getUnInspectBsListAck;
	}

	/**
	 * 获取统计信息
	 */
	public static GetTotalInfoAck appGetTotalInfoAck(GetTotalInfo getTotalInfo){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		getTotalInfoAck.setUserid(getTotalInfo.getUserid());
		getTotalInfoAck.setSerialnumber(getTotalInfo.getSerialnumber());
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int month = cal.get(Calendar.MONTH);
			getTotalInfoAck.setBsnum(mapper.selectForAllBsList().size()+"");
			getTotalInfoAck.setErrbsnum(mapper.selectBreakBsInfo().size()+"");
			getTotalInfoAck.setInspectbsnum(mapper.selectInspectionBsList(month+1).size()+"");
			getTotalInfoAck.setUninspectbsnum(mapper.selectNotInspectionBsList(month+1).size()+"");
			getTotalInfoAck.setAppnum(ServerDemo.mThreadList.size()+"");
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getTotalInfoAck;
	}

	public static void main(String[] args) {
		try {
			System.out.println(BsStatusService.bsEmh(185));
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * 获取基站详细信息
	 */
	public static GetBsInfoAck appGetBsInfoAck(GetBsInfo getBsInfo){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		getBsInfoAck.setUserid(getBsInfo.getUserid());
		String bsId = getBsInfo.getBsid();
		getBsInfoAck.setBsid(bsId);
		getBsInfoAck.setSerialnumber(getBsInfo.getSerialnumber());
		try {
			if(!"".equals(bsId) && bsId!=null){
				Map<String,Object> bsinfo = mapper.selectInfoByBsId(bsId);
				String bstype = bsinfo.get("bstype").toString();
				if("铁塔".equals(bstype) || "移动".equals(bstype) || "电信".equals(bstype)){
					bsinfo.put("bstype","运营商机房");
				}else{
					bsinfo.put("bstype",bstype+"机房");
				}
				String period = bsinfo.get("period").toString();
				getBsInfoAck.setPeriod(period);
				getBsInfoAck.setBsinfo(bsinfo);
				if("3".equals(period)){
					Map<String,Object> three = SqlServerService.bsmonitorList(Integer.parseInt(bsId));
					three.put("ups1",three.get("lv"));
					three.put("ups2",three.get("jv"));
					three.put("ups3",three.get("li"));
					three.put("ups4",three.get("ji"));
					three.put("e1","");
					three.put("e2","");
					three.put("e3","");
					three.put("e4","");
					three.put("e5","");
					three.remove("lv");
					three.remove("jv");
					three.remove("li");
					three.remove("ji");
					getBsInfoAck.setEmhinfo(three);
				}else if("4".equals(period)){
					Map<String,Object> four = BsStatusService.bsEmh(Integer.parseInt(bsId));
					four.remove("ups5");
					four.remove("fsu1");
					four.remove("fsu2");
					four.remove("fsu3");
					four.remove("fsu4");
					four.remove("time");
					getBsInfoAck.setEmhinfo(four);
				}
				List<Map<String,Object>> inspectlist = mapper.selectInspectListForSelfBs(bsId);

				List<BsInspectTable> finalinspectlist = new LinkedList<BsInspectTable>();
				for(int i=0;i<inspectlist.size();i++){
					Map<String,Object> map = inspectlist.get(i);
					BsInspectTable bsInspectTable = new BsInspectTable();
					bsInspectTable.setSerialnumber(map.get("serialnumber")+"");
					bsInspectTable.setUserid(map.get("userid")+"");
					bsInspectTable.setPeriod(map.get("period")+"");
					bsInspectTable.setBsname(map.get("bsname")+"");
					bsInspectTable.setBsid(map.get("bsid")+"");
					bsInspectTable.setBslevel(map.get("bslevel")+"");
					bsInspectTable.setCheckman(map.get("checkman")+"");
					bsInspectTable.setBstype(map.get("bstype")+"");
					bsInspectTable.setCommitdate(map.get("commitdate")+"");
					bsInspectTable.setAmmeternumber(map.get("ammeternumber")+"");
					bsInspectTable.setLongitude(map.get("longitude")+"");
					bsInspectTable.setLatitude(map.get("latitude")+"");
					bsInspectTable.setAddress(map.get("address")+"");
					bsInspectTable.setRemainwork(map.get("remainwork")+"");
					List<Map<String,String>> resultList = new LinkedList<Map<String,String>>();
					for(int j=1;j<29;j++){
						String result = map.get("d"+j)+"";
						String remarks = map.get("c"+j)+"";
						Map<String,String> temp = new HashMap<String,String>();
						temp.put("result",result);
						temp.put("remarks",remarks);
						resultList.add(temp);
						System.out.println(" result ======"+result+"=====remarks====="+remarks);
					}
					bsInspectTable.setMessage(resultList);
					finalinspectlist.add(bsInspectTable);
				}
				//System.out.println(inspectlist);
				getBsInfoAck.setInspectlist(finalinspectlist);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getBsInfoAck;
	}
	
	/**
	 * 获取移动基站信息
	 */
	public static GetMovebsInfoAck appGetMovebsInfoAck(GetMovebsInfo getMovebsInfo){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		getMovebsInfoAck.setUserid(getMovebsInfo.getUserid());
		String bsId = getMovebsInfo.getBsid();
		getMovebsInfoAck.setBsid(bsId);
		try {
			if(!"".equals(bsId) && bsId!=null){
				Map<String,Object> map = mapper.selectByBsId(bsId);
				if(map.size()>0){
					int bslevel = (Integer) map.get("bslevel");
					getMovebsInfoAck.setBslevel(String.valueOf(bslevel));
					getMovebsInfoAck.setBsname(map.get("bsname").toString());
					getMovebsInfoAck.setAck("0");
				}else{
					getMovebsInfoAck.setAck("1");
				}
				sqlSession.close();
			}else{
				getMovebsInfoAck.setAck("1");
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getMovebsInfoAck;
	}
	
	/**
	 * 获取自建基站信息
	 */
	public static GetOwnbsInfoAck appGetOwnbsInfoAck(GetOwnbsInfo getOwnbsInfo){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		getOwnbsInfoAck.setUserid(getOwnbsInfo.getUserid());
		String bsId = getOwnbsInfo.getBsid();
		getOwnbsInfoAck.setBsid(bsId);
		try {
			if(!"".equals(bsId) && bsId!=null){
				Map<String,Object> map = mapper.selectByBsId(bsId);
				if(map.size()>0){
					int bslevel = (Integer) map.get("bslevel");
					getOwnbsInfoAck.setBslevel(String.valueOf(bslevel));
					getOwnbsInfoAck.setBsname(map.get("bsname").toString());
					getOwnbsInfoAck.setAck("0");
				}else{
					getOwnbsInfoAck.setAck("1");
				}
				sqlSession.close();
			}else{
				getOwnbsInfoAck.setAck("1");
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getOwnbsInfoAck;
	}
	
	
	/**
	 * 更新派单状态为处理中
	 */
	public static void updateUserStatus(Map<String,String> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		try{
			mapper.updateUserStatus(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 更新发电状态
	 */
	public static void updateElecStatus(Map<String,Object> map){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		try{
			mapper.updateElecStatus(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 派单确认请求
	 */
	public static ErrCheckAck appErrCheck(ErrCheck errCheck){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		try {
			Map<String, String> map = mapper.selectOrderStatus(errCheck.getSerialnumber());
			System.out.println("map为："+map);
			String status=map.get("status");
			System.out.println("status为："+status);
			if("2".equals(status)){
				errCheckAck.setResult("0");
			}else{
				errCheckAck.setResult("1");
			}
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		errCheckAck.setSerialnumber(errCheck.getSerialnumber());
		errCheckAck.setUserid(errCheck.getUserid());		
		return errCheckAck;
	}
	
	/**
	 * 派单完结
	 */
	public static ErrProTableAck appProTableAck(ErrProTable errProTable){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		//需要入库
		Map<String,String> map = new HashMap<String,String>();
		map.put("bsid", errProTable.getBsid());
		map.put("bsname", errProTable.getBsname());
		map.put("dispatchtime", errProTable.getDispatchtime());
		map.put("dispatchman", errProTable.getDispatchman());
		map.put("errtype", errProTable.getErrtype());
		map.put("errlevel", errProTable.getErrlevel());
		map.put("errfoundtime", errProTable.getErrfoundtime());
		map.put("errslovetime", errProTable.getErrslovetime());
		map.put("progress", errProTable.getProgress());
		map.put("proresult", errProTable.getProresult());
		map.put("workman", errProTable.getWorkman());
		map.put("auditor", errProTable.getAuditor());
		map.put("longitude", errProTable.getLongitude());
		map.put("latitude", errProTable.getLatitude());
		map.put("address", errProTable.getAddress());
		map.put("serialnumber", errProTable.getSerialnumber());
		map.put("userid", errProTable.getUserid());	
		try {
			//更新派单
			int count = mapper.updateFaultOrder(map);
				
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		errProTableAck.setSerialnumber(errProTable.getSerialnumber());
		errProTableAck.setUserid(errProTable.getUserid());
		return errProTableAck;
	}

	/**
	 * 基站巡检表
	 */
	public static BsInspectTableAck appBsInspectTableAck(BsInspectTable bsInspectTable){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		//需要入库
		LinkedList<String> list = new LinkedList<String>();
		list.add(bsInspectTable.getSerialnumber());
		list.add(bsInspectTable.getPeriod());
		list.add(bsInspectTable.getBsid());
		list.add(bsInspectTable.getBsname());
		list.add(bsInspectTable.getBslevel());
		list.add(bsInspectTable.getBstype());
		list.add(bsInspectTable.getAmmeternumber());
		list.add(bsInspectTable.getLongitude());
		list.add(bsInspectTable.getLatitude());
		list.add(bsInspectTable.getAddress());
		list.add(bsInspectTable.getCheckman());
		list.add(bsInspectTable.getCommitdate());
		list.add(bsInspectTable.getRemainwork());
		List<String> picList = bsInspectTable.getPicurl();
		String pic = "";
		for(int i=0;i<picList.size();i++){
			String tempPic = picList.get(i);
			pic += tempPic + "|";
		}
		list.add(pic);
		list.add(bsInspectTable.getUserid());
		List<Map<String,String>> message = bsInspectTable.getMessage();
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);
			list.add(map.get("result"));
		}
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);
			list.add(map.get("remarks"));
		}
		System.out.println("list为："+list);
		try {
			mapper.insertBsTable(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bsInspectTableAck.setSerialnumber(bsInspectTable.getSerialnumber());
		bsInspectTableAck.setUserid(bsInspectTable.getUserid());
		bsInspectTableAck.setAck("0");
		return bsInspectTableAck;
	}
	
	/**
	 * 移动基站巡检表
	 */
	public static MovebsTableAck appMovebsTableAck(MovebsTable movebsTable){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		//需要入库
		LinkedList<String> list = new LinkedList<String>();
		list.add(movebsTable.getSerialnumber());
		list.add(movebsTable.getBsid());
		list.add(movebsTable.getBsname());
		list.add(movebsTable.getBslevel());
		list.add(movebsTable.getBstype());
		list.add(movebsTable.getLongitude());
		list.add(movebsTable.getLatitude());
		list.add(movebsTable.getAddress());
		list.add(movebsTable.getCheckman());
		list.add(movebsTable.getCommitdate());
		list.add(movebsTable.getRemainwork());
		list.add(movebsTable.getUserid());
		List<Map<String,String>> message = movebsTable.getMessage();
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);
			list.add(map.get("result"));
		}
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);			
			list.add(map.get("remarks"));;
		}
		System.out.println("list为："+list);
		try {
			mapper.insertMoveBsTable(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		movebsTableAck.setSerialnumber(movebsTable.getSerialnumber());
		movebsTableAck.setUserid(movebsTable.getUserid());
		movebsTableAck.setAck("0");
		return movebsTableAck;
	}
	
	/**
	 * 800M自建基站巡检表
	 */
	public static OwnbsTableAck appOwnbsTable(OwnbsTable ownbsTable){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		//需要入库
		LinkedList<String> list = new LinkedList<String>();
		list.add(ownbsTable.getSerialnumber());
		list.add(ownbsTable.getBsid());
		list.add(ownbsTable.getBsname());
		list.add(ownbsTable.getBslevel());
		list.add(ownbsTable.getBstype());
		list.add(ownbsTable.getAmmeternumber());
		list.add(ownbsTable.getLongitude());
		list.add(ownbsTable.getLatitude());
		list.add(ownbsTable.getAddress());
		list.add(ownbsTable.getCheckman());
		list.add(ownbsTable.getCommitdate());
		list.add(ownbsTable.getRemainwork());
		list.add(ownbsTable.getUserid());	
		List<Map<String,String>> message = ownbsTable.getMessage();
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);
			list.add(map.get("result"));
		}
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);			
			list.add(map.get("remarks"));;
		}
		System.out.println("list为："+list);
		try {
			mapper.insertOwnBsTable(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ownbsTableAck.setSerialnumber(ownbsTable.getSerialnumber());
		ownbsTableAck.setUserid(ownbsTable.getUserid());
		ownbsTableAck.setAck("0");
		return ownbsTableAck;
	}
	
	/**
	 * 调度台巡检作业表
	 */
	public static DispatchTableAck appDispatchTableAck(DispatchTable dispatchTable){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		//需要入库
		LinkedList<String> list = new LinkedList<String>();
		list.add(dispatchTable.getSerialnumber());
		list.add(dispatchTable.getDispatchname());
		list.add(dispatchTable.getDispatchplace());
		list.add(dispatchTable.getLongitude());
		list.add(dispatchTable.getLatitude());
		list.add(dispatchTable.getAddress());
		list.add(dispatchTable.getCheckman());
		list.add(dispatchTable.getCommitdate());
		list.add(dispatchTable.getUserid());		
		List<Map<String,String>> message = dispatchTable.getMessage();
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);
			list.add(map.get("comment"));
		}
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);			
			list.add(map.get("result"));;
		}
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);			
			list.add(map.get("description"));;
		}
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);			
			list.add(map.get("remarks"));;
		}
		System.out.println("list为："+list);
		try {
			mapper.insertDispatchTable(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		dispatchTableAck.setSerialnumber(dispatchTable.getSerialnumber());
		dispatchTableAck.setUserid(dispatchTable.getUserid());
		dispatchTableAck.setAck("0");
		return dispatchTableAck;
	}
	
	/**
	 * 网管巡检作业表
	 */
	public static NetManagerTableAck appNetManagerTableAck(NetManagerTable netManagerTable){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		//需要入库
		LinkedList<String> list = new LinkedList<String>();
		list.add(netManagerTable.getSerialnumber());
		list.add(netManagerTable.getManagername());
		list.add(netManagerTable.getManagerplace());
		list.add(netManagerTable.getLongitude());
		list.add(netManagerTable.getLatitude());
		list.add(netManagerTable.getAddress());
		list.add(netManagerTable.getCheckman());
		list.add(netManagerTable.getCommitdata());
		list.add(netManagerTable.getUserid());
		
		List<Map<String,String>> message = netManagerTable.getMessage();
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);
			list.add(map.get("comment"));
		}
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);			
			list.add(map.get("result"));;
		}
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);			
			list.add(map.get("description"));;
		}
		for(int i=0;i<message.size();i++){
			Map<String,String> map = message.get(i);			
			list.add(map.get("remarks"));;
		}
		System.out.println("list为："+list);
		try {
			mapper.insertNetTable(list);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		netManagerTableAck.setSerialnumber(netManagerTable.getSerialnumber());
		netManagerTableAck.setUserid(netManagerTable.getUserid());
		netManagerTableAck.setAck("0");
		return netManagerTableAck;
	}
	
	
	/**
	 * 手台的dstId和经纬度(测试用)
	 */
	public static GetForGpsDstAck appGetForGpsDstAck(GetForGpsDst getForGpsDst){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.gps_voice_slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		getForGpsDstAck.setUserid(getForGpsDst.getUserid());
		try{
			//获取当月
			Calendar cal = Calendar.getInstance();
			int temp = cal.get(Calendar.MONTH)+1;
			String currentMonth;
			if(temp<10){
				currentMonth="0"+temp;
			}else{
				currentMonth=Integer.toString(temp);
			}
			Map<String,Object> tempMap = new HashMap<String, Object>();
			currentMonth="xhgmnet_gpsinfo"+currentMonth;
			tempMap.put("currentMonth", currentMonth);
			
			List<Map<String,String>> list = mapper.selectForGpsDst(tempMap);
			getForGpsDstAck.setInfolist(list);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getForGpsDstAck;
	}
	
	/**
	 * 返回所有基站信息
	 */
	public static List<GetAllBsListAck> appGetAllBsListAck(GetAllBsList getAllBsList){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		List<GetAllBsListAck> finalList = new LinkedList<GetAllBsListAck>();
		try{						
			List<Map<String,String>> list = mapper.selectForAllBsList();
			//System.out.println("list长度为 : "+list.size());
			List<Map<String,String>> list1 = new LinkedList<Map<String,String>>();
			List<Map<String,String>> list2 = new LinkedList<Map<String,String>>();
			List<Map<String,String>> list3 = new LinkedList<Map<String,String>>();
			List<Map<String,String>> list4 = new LinkedList<Map<String,String>>();
			List<Map<String,String>> list5 = new LinkedList<Map<String,String>>();
			List<Map<String,String>> list6 = new LinkedList<Map<String,String>>();
			for(int i=0;i<list.size();i++){
				if(i>=0 && i<50){
					list1.add(list.get(i));
				}else if(i==50){
					GetAllBsListAck getAllBsListAck = new GetAllBsListAck();
					getAllBsListAck.setUserid(getAllBsList.getUserid());
					getAllBsListAck.setSerialnumber(getAllBsList.getSerialnumber());
					getAllBsListAck.setStatus("0");
					getAllBsListAck.setBslist(list1);
					finalList.add(getAllBsListAck);
					list2.add(list.get(i));
				}else if(i>50 && i<100){
					list2.add(list.get(i));
				}else if(i==100){
					GetAllBsListAck getAllBsListAck = new GetAllBsListAck();
					getAllBsListAck.setUserid(getAllBsList.getUserid());
					getAllBsListAck.setSerialnumber(getAllBsList.getSerialnumber());
					getAllBsListAck.setStatus("0");
					getAllBsListAck.setBslist(list2);
					finalList.add(getAllBsListAck);
					list3.add(list.get(i));
				}else if(i>100 && i<150){
					list3.add(list.get(i));
				}else if(i==150){
					GetAllBsListAck getAllBsListAck = new GetAllBsListAck();
					getAllBsListAck.setUserid(getAllBsList.getUserid());
					getAllBsListAck.setSerialnumber(getAllBsList.getSerialnumber());
					getAllBsListAck.setStatus("0");
					getAllBsListAck.setBslist(list3);
					finalList.add(getAllBsListAck);
					list4.add(list.get(i));
				}else if(i>150 && i<200){
					list4.add(list.get(i));
				}else if(i==200){
					GetAllBsListAck getAllBsListAck = new GetAllBsListAck();
					getAllBsListAck.setUserid(getAllBsList.getUserid());
					getAllBsListAck.setSerialnumber(getAllBsList.getSerialnumber());
					getAllBsListAck.setStatus("0");
					getAllBsListAck.setBslist(list4);
					finalList.add(getAllBsListAck);
					list5.add(list.get(i));
				}else if(i>200 && i<250){
					list5.add(list.get(i));
				}else if(i==250){
					GetAllBsListAck getAllBsListAck = new GetAllBsListAck();
					getAllBsListAck.setUserid(getAllBsList.getUserid());
					getAllBsListAck.setSerialnumber(getAllBsList.getSerialnumber());
					getAllBsListAck.setStatus("0");
					getAllBsListAck.setBslist(list5);
					finalList.add(getAllBsListAck);
					list6.add(list.get(i));
				}else if(i>250 && i<list.size()){
					list6.add(list.get(i));
				}
			}			
			
			GetAllBsListAck getAllBsListAck = new GetAllBsListAck();
			getAllBsListAck.setUserid(getAllBsList.getUserid());
			getAllBsListAck.setSerialnumber(getAllBsList.getSerialnumber());
			getAllBsListAck.setStatus("1");
			getAllBsListAck.setBslist(list6);
			finalList.add(getAllBsListAck);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return finalList;
	}
	
	/**
	 * 插入用户上传的位置信息
	 */
	public static void appInsertGpsInfoUp(GpsInfoUp gpsInfoUp){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		try{
			Map<String,String> map = new HashMap<String,String>();
			map.put("userId", gpsInfoUp.getUserid());
			map.put("name", gpsInfoUp.getName());
			map.put("lat", gpsInfoUp.getLatitude());
			map.put("lng", gpsInfoUp.getLongitude());
			map.put("address", gpsInfoUp.getAddress());
			mapper.appInsertGpsInfoUp(map);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回最新一次所有app位置信息
	 */
	public static GetAllAppListAck appGetAllAppListAck(GetAllAppList getAllAppList){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		GetAllAppListAck getAllAppListAck = new GetAllAppListAck();
		getAllAppListAck.setSerialnumber(getAllAppList.getSerialnumber());
		getAllAppListAck.setStatus("1");
		getAllAppListAck.setUserid(getAllAppList.getUserid());
		try{						
			List<String> userList = new ArrayList<String>();
			ArrayList<SocketThread> tempList = ServerDemo.mThreadList;
			for(SocketThread st : tempList){
				userList.add(st.userId);
			}
			List<Map<String,String>> list = mapper.selectForAllAppLocation(userList);
			getAllAppListAck.setApplist(list);
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getAllAppListAck;
	}

	/**
	 * 发电派单返回
	 */
	public static GenTableAck appGenTableAck(GenTable genTable){
		System.out.println("genTable开始为："+genTable);
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		GenTableAck genTableAck = new GenTableAck();
		genTableAck.setSerialnumber(genTable.getSerialnumber());
		genTableAck.setUserid(genTable.getUserid());
        List<String> genonpiclist = genTable.getGenonpiclist();
        if(genonpiclist!=null && genonpiclist.size()>0){
            String str = "";
            for(int i=0;i<genonpiclist.size();i++){
                String temp = genonpiclist.get(i);
                str+=temp+"|";
            }
            genTable.setGen_on_pic(str);
        }

        List<String> getGenoffpiclist = genTable.getGenoffpiclist();
        if(getGenoffpiclist!=null && getGenoffpiclist.size()>0){
            String str = "";
            for(int i=0;i<getGenoffpiclist.size();i++){
                String temp = getGenoffpiclist.get(i);
                str+=temp+"|";
            }
            genTable.setGen_off_pic(str);
        }
        System.out.println("genTable结束为："+genTable);
		try{
			mapper.updateForGenTable(genTable);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return genTableAck;
	}

	/**
	 * 更新发电派单状态
	 */
	public static void updateGenTableStatus(Map<String,Object> param){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		try{
			int count = mapper.updateGenTableStatus(param);
			System.out.println("param : "+param);
			System.out.println("更新后update记录为："+count);
			sqlSession.commit();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 发电校验请求
	 */
	public static GenCheckAck appGenCheckAck(GenCheck genCheck){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		GenCheckAck genCheckAck = new GenCheckAck();
		genCheckAck.setUserid(genCheck.getUserid());
		genCheckAck.setSerialnumber(genCheck.getSerialnumber());
		try{
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return genCheckAck;
	}

	/**
	 * 获取发电电流电压参数
	 */
	public static GetGenArgAck appGetGenArgAck(GetGenArg getGenArg){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		GetGenArgAck getGenArgAck = new GetGenArgAck();
		getGenArgAck.setUserid(getGenArg.getUserid());
		getGenArgAck.setSerialnumber(getGenArg.getSerialnumber());
		getGenArgAck.setBsid(getGenArg.getBsid());
		try{
			List<Map<String,Object>> list = mapper.selectForGenVI(getGenArg.getBsid());
			if(list!=null && list.size()>0){
				Map<String,Object> map = new HashMap<String,Object>();
				for(int i=0;i<list.size();i++){
					map.put(list.get(i).get("singleId")+"",list.get(i).get("singleValue")+"");
				}
				getGenArgAck.setGenv(map.get("092301")+"");
				getGenArgAck.setGeni(map.get("092304")+"");
				getGenArgAck.setAck("0");
			}else{
				getGenArgAck.setAck("1");
			}
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getGenArgAck;
	}

	/**
	 * 获取市电恢复时间
	 */
	public static GetPowerOnTimeAck appGetPowerOnTimeAck(GetPowerOnTime getPowerOnTime){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		GetPowerOnTimeAck getPowerOnTimeAck = new GetPowerOnTimeAck();
		getPowerOnTimeAck.setUserid(getPowerOnTime.getUserid());
		getPowerOnTimeAck.setSerialnumber(getPowerOnTime.getSerialnumber());
		try{
			List<Map<String,Object>> powerOnTimeList = mapper.selectForPowerOnTime(getPowerOnTime.getBsid());
			List<Map<String,Object>> genOffTimeList = mapper.selectForGenOffTime(getPowerOnTime.getBsid());
			if(powerOnTimeList != null && powerOnTimeList.size()>0 && genOffTimeList != null && genOffTimeList.size()>0){
				getPowerOnTimeAck.setPowerontime(powerOnTimeList.get(0).get("alarmTime")+"");
				getPowerOnTimeAck.setGenofftime(genOffTimeList.get(0).get("startTime")+"");
				getPowerOnTimeAck.setAck("0");
			}else{
				getPowerOnTimeAck.setAck("1");
			}
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getPowerOnTimeAck;
	}
	
}
