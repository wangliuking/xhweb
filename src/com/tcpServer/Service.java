package com.tcpServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.func.plugin.FunUtil;
import xh.mybatis.mapper.EventMapper;
import xh.mybatis.mapper.TcpMapper;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;
import xh.mybatis.tools.MoreDbTools;
import xh.org.listeners.SingLoginListener;

import com.tcpBean.DispatchTableAck;
import com.tcpBean.ErrCheck;
import com.tcpBean.ErrCheckAck;
import com.tcpBean.ErrProTable;
import com.tcpBean.ErrProTableAck;
import com.tcpBean.LoginAck;
import com.tcpBean.MovebsTableAck;
import com.tcpBean.NetManagerTableAck;
import com.tcpBean.OwnbsTableAck;
import com.tcpBean.UserInfo;
import com.tcpBean.UserInfoAck;
import com.tcpBean.UserLogin;

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
	
	private static FunUtil funUtil = new FunUtil();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(loginAck);
	}

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
		userInfoAck.setUsername("测试用名称");
		try{
			userInfoAck.setBslist(mapper.selectByAppUser(userInfo.getUserid()));
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userInfoAck;
	}
	
	
	/**
	 * 更新派单状态为处理中
	 */
	public static void updateUserStatus(String userId){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
		TcpMapper mapper=sqlSession.getMapper(TcpMapper.class);
		try{
			mapper.updateUserStatus(userId);
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
		errCheckAck.setSerialnumber(errCheck.getSerialnumber());
		errCheckAck.setUserid(errCheck.getUserid());
		errCheckAck.setResult("0");
		return errCheckAck;
	}
	
	/**
	 * 派单完结
	 */
	public static ErrProTableAck appProTableAck(ErrProTable errProTable){
		errProTableAck.setSerialnumber(errProTable.getSerialnumber());
		errProTableAck.setUserid(errProTable.getUserid());
		return errProTableAck;
	}
	
	
}
