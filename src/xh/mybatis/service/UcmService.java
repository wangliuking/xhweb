package xh.mybatis.service;

import java.io.IOException;

import xh.org.socket.DispatchUserIAStruct;
import xh.org.socket.DispatchUserStruct;
import xh.org.socket.KillStruct;
import xh.org.socket.MessageStruct;
import xh.org.socket.MultiGroupStruct;
import xh.org.socket.RadioUserAttrStruct;
import xh.org.socket.RadioUserStruct;
import xh.org.socket.SendData;
import xh.org.socket.TalkGroupAttrStruct;
import xh.org.socket.TalkGroupStruct;
import xh.org.socket.savalidsiteStruct;
import xh.org.socket.statusSetStruct;
import xh.org.socket.statusSetUnitStruct;
import xh.org.socket.validregionStruct;

public class UcmService {
	
	/**
	 * 发送终端用户数据
	 * @param radiouser
	 */
	public static void sendRadioUser(RadioUserStruct radiouser){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)518);   ////四期：长度486+=32  +16
		header.setCommandId((short)153);
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendRadioUserData(header, radiouser);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送通话组数据包
	 * @param talkgroup
	 */
	public static void sendTalkGroupData(TalkGroupStruct talkgroup){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)208);//四期：长度+=32
		header.setCommandId((short)159);
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendTalkGroupData(header, talkgroup);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送通播组数据包
	 * @param talkgroup
	 */
	public static void  sendMultiGroupData(MultiGroupStruct multiGroupStruct){
		MessageStruct header=new MessageStruct();
		
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)1723);//1776+5   //四期：长度+=32
		header.setCommandId((short)161);
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendMultiGroupData(header, multiGroupStruct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送调度用户数据包
	 * @param talkgroup
	 */
	public static void  sendDispatchUserData(DispatchUserStruct dispatchUserStruct){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)195);  //91+48  //四期：长度+=16
		header.setCommandId((short)167);
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendDispatchUserData(header, dispatchUserStruct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送状态集数据包
	 * @param talkgroup
	 */
	public static void  sendStatusSetData(statusSetStruct struct){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)62);
		header.setCommandId((short)165);
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendStatusSetData(header, struct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送状态集数据包
	 * @param talkgroup
	 */
	public static void sendStatusSetUnitData(statusSetUnitStruct struct){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)98);
		header.setCommandId((short)171);
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendStatusSetUnitData(header, struct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送终端用户业务属性数据包
	 * @param talkgroup
	 */
	public static void  sendRadioUserAttrData(RadioUserAttrStruct radioUserAttrStruct){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)225);//145+16   //四期：长度+=32
		header.setCommandId((short)155);
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendRadioUserAttrData(header, radioUserAttrStruct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送通话组/通播组业务属性数据包
	 * @param talkgroup
	 */
	public static void  sendTalkGroupAttrData(TalkGroupAttrStruct talkGroupAttrStruct){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)182); //  131+4
		header.setCommandId((short)163);
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendTalkGroupAttrData(header,talkGroupAttrStruct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送调度用户属性数据包
	 * @param talkgroup
	 */
	public static void  sendDispatchUserIAData(DispatchUserIAStruct dispatchUserIAStruct){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)94);//40+22;
		header.setCommandId((short)169);//169+22
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendDispatchUserIAData(header, dispatchUserIAStruct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送遥毙数据包
	 * @param talkgroup
	 */
	public static void  sendKilledData(KillStruct kill){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)62);//40+22;
		header.setCommandId((short)169);//169+22
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendKilledData(header, kill);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * //发送终端用户业务属性有效站点数据包
	 * @param talkgroup
	 */
	public static void  sendRadioUserSavalidSiteData(savalidsiteStruct savalidsiteStruct){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)176);
		header.setCommandId((short)177);
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendRadioUserSavalidSiteData(header, savalidsiteStruct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * //发送通话组业务属性有效站点数据包
	 * @param talkgroup
	 */
	public static void  sendTalkGroupSavalidSiteData(savalidsiteStruct savalidsiteStruct){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)177);
		header.setCommandId((short)181);
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendTalkGroupSavalidSiteData(header, savalidsiteStruct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送终端用户有效地域数据包
	 * @param talkgroup
	 */
	public static void  sendRadioUserSavalidRegionData(validregionStruct validregionStruct){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)132);
		header.setCommandId((short)179);
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendRadioUserSavalidRegionData(header, validregionStruct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送终端用户有效地域数据包
	 * @param talkgroup
	 */
	public static void  sendTalkGroupSavalidRegionData(validregionStruct validregionStruct){
		MessageStruct header=new MessageStruct();
		header.setSegNum((byte)1);
		header.setSegFlag((byte)1);
		header.setLength((short)132);
		header.setCommandId((short)183);
		header.setSrcDevice((byte)6);
		header.setDstDevice((byte)6);
		try {
			SendData.sendTalkGroupSavalidRegionData(header, validregionStruct);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
