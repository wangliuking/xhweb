package xh.org.socket;

import xh.func.plugin.Base64Util;
import xh.func.plugin.FunUtil;
import xh.mybatis.service.UcmService;
import cn.com.scca.signgw.api.SccaGwSDK;

public class test {
	public static void main(String[] args) {

	}

	public void radioUser() {
		RadioUserStruct setRadioUser = new RadioUserStruct();
		setRadioUser.setOperation(1);

		setRadioUser.setId(105);
		setRadioUser.setName("100");
		setRadioUser.setAlias("100");
		setRadioUser.setMscId(1);
		setRadioUser.setVpnId(1);
		setRadioUser.setSn("159");
		setRadioUser.setCompany("123");
		setRadioUser.setType("0");
		setRadioUser.setEnabled(1);
		setRadioUser.setShortData(1);
		setRadioUser.setFullDuple(1);
		setRadioUser.setRadioType(1);
		setRadioUser.setAnycall(1);
		setRadioUser.setSaId(1);
		setRadioUser.setIaId(1);
		setRadioUser.setVaId(1);
		setRadioUser.setRugId(1);
		setRadioUser.setPacketData("25");
		setRadioUser.setIp("127.0.0.1");
		setRadioUser.setPrimaryTGId(1);
		setRadioUser.setAmbienceMonitoring("1");
		setRadioUser.setAmbienceInitiation("1");
		setRadioUser.setDirectDial("1");
		setRadioUser.setPstnAccess("1");
		setRadioUser.setPabxAccess("1");
		setRadioUser.setClir("1");
		setRadioUser.setClirOverride("1");
		setRadioUser.setKilled("1");
		setRadioUser.setMsType("1");
		UcmService.sendRadioUser(setRadioUser);
	}

	public void talkgroup() {
		TalkGroupStruct setTalkGroupData = new TalkGroupStruct();
		setTalkGroupData.setOperation(1);
		setTalkGroupData.setId(2000);
		setTalkGroupData.setName("2000");
		setTalkGroupData.setAlias("2000");
		setTalkGroupData.setMscId(1);
		setTalkGroupData.setVpnId(1);
		setTalkGroupData.setSaId(1);
		setTalkGroupData.setIaId(1);
		setTalkGroupData.setVaId(1);
		setTalkGroupData.setPreempt(1);
		setTalkGroupData.setRadioType(1);
		setTalkGroupData.setRegroupAble(1);
		setTalkGroupData.setEnabled(1);
		setTalkGroupData.setDirectDial("20569");
		UcmService.sendTalkGroupData(setTalkGroupData);
	}

	public void MultiGroup() {
		MultiGroupStruct setMultiGroup = new MultiGroupStruct();
		setMultiGroup.setOperation(1);
		setMultiGroup.setId(12586);
		setMultiGroup.setName("11");
		setMultiGroup.setAlias("11");
		setMultiGroup.setMscId(1);
		setMultiGroup.setVpnId(0);
		setMultiGroup.setSaId(0);
		setMultiGroup.setIaId(0);
		setMultiGroup.setVaId(0);
		setMultiGroup.setPreempt(0);
		setMultiGroup.setRadioType(0);
		setMultiGroup.setEnabled(0);
		setMultiGroup.setDirectDial("");
		setMultiGroup.setInterruptWait(0);
		setMultiGroup.setPdtType(0);
		setMultiGroup.setNpType(0);
		UcmService.sendMultiGroupData(setMultiGroup);
	}

	public void dispatchUser() {
		DispatchUserStruct struct = new DispatchUserStruct();
		struct.setOperation(1); // 数据处理方式
		struct.setId(122);
		struct.setName("22");
		struct.setAlias("2");
		struct.setMscId(0);
		struct.setVpnId(0);
		struct.setUserType(0);
		struct.setPassword("2");
		struct.setVaid(0);
		struct.setSaid(0);
		struct.setMasterId(0);
		struct.setFullDuplex(0);
		struct.setAmbienceInitiation(0);
		struct.setClir(0);
		struct.setClirOverride(0);

		// 新增
		struct.setSaName("");
		struct.setSupervisorStatus(0);
		struct.setDispatcherType(0);
		struct.setCode(0);
		struct.setDispatchNum(0);
		struct.setDialString("");
		UcmService.sendDispatchUserData(struct);
	}

	public void RadioUserAttr() {
		RadioUserAttrStruct setRadioUserAttr = new RadioUserAttrStruct();
		setRadioUserAttr.setOperation(1);
		setRadioUserAttr.setId(1);
		setRadioUserAttr.setName("1");
		setRadioUserAttr.setSsId(0);
		setRadioUserAttr.setSsName("1");
		setRadioUserAttr.setDispatchPriority(0);
		setRadioUserAttr.setPccEnabled(0);
		setRadioUserAttr.setTgEnabled(0);
		setRadioUserAttr.setMgEnabled(0);
		setRadioUserAttr.setMgEmergencyEnabled(0);
		setRadioUserAttr.setDispatchPreempt(0);
		setRadioUserAttr.setAllSitesAllowed(0);
		setRadioUserAttr.setCalledPreempt(0);
		setRadioUserAttr.setUserGroup(0);
		setRadioUserAttr.setEmergIndCallEnabled(0);
		setRadioUserAttr.setEmergGroupCallEnabled(0);
		UcmService.sendRadioUserAttrData(setRadioUserAttr);
	}

	public void talkGroupAttr() {
		TalkGroupAttrStruct setTalkGroupAttr = new TalkGroupAttrStruct();
		setTalkGroupAttr.setOperation(1);
		setTalkGroupAttr.setId(1);
		setTalkGroupAttr.setName("159");
		setTalkGroupAttr.setMessageTransmission(0);
		setTalkGroupAttr.setBusyOverride(0);
		setTalkGroupAttr.setEmergencyCall(0);
		setTalkGroupAttr.setEmergencyAtNVS(0);
		setTalkGroupAttr.setDispatchPriority(0);
		setTalkGroupAttr.setPriorityMonitor(0);
		setTalkGroupAttr.setTgDataPreempt(0);

		setTalkGroupAttr.setInactivityTime(0);
		setTalkGroupAttr.setCallsWithoutMGEG(0);
		setTalkGroupAttr.setAudioInterrupt(0);
		setTalkGroupAttr.setUserGroup(0);
		UcmService.sendTalkGroupAttrData(setTalkGroupAttr);
	}

	public void DispatchUserIA() {

		DispatchUserIAStruct struct = new DispatchUserIAStruct();
		struct.setOperation(1);
		struct.setId(1);
		struct.setName("sss");
		struct.setMonitorOn(0);
		struct.setPCPreempt(0);
		struct.setCallPriority(0);
		struct.setAllMute(0);
		struct.setAllMuteTimeout(0);
		struct.setPttPriority(0);

		// 新增
		struct.setProhibitTone(0);
		struct.setSideTone(0);
		struct.setPatchGroupNum(0);
		struct.setMSGroupNum(0);
		struct.setAPBNum(0);
		struct.setCalledPreempt(0);
		struct.setInboundCall(0);
		struct.setInboundPTT(0);
		struct.setInstantTransmit(0);
		struct.setPatchPC(0);
		UcmService.sendDispatchUserIAData(struct);
	}

	// 摇起
	public void Killa() {

		KillStruct kill = new KillStruct();
		kill.setOperation(1);

		kill.setUserId(2017017);
		kill.setMsId(1);
		kill.setKillCmd(0);
		UcmService.sendKilledData(kill);
	}

	// 摇晕
	public void Killb() {

		KillStruct kill = new KillStruct();
		kill.setOperation(3);
		kill.setUserId(2017017);
		kill.setMsId(1);
		kill.setKillCmd(1);
		UcmService.sendKilledData(kill);
	}
	//动态重组
	public void dgna() {

		addDgnaStruct struct = new addDgnaStruct();
		struct.setOpra(2);
		struct.setMscId(1);
		struct.setIssi(2017017);
		struct.setGssi(10010007);
		struct.setGroupname("亚光重组测试组");
		struct.setAttached(1);
		struct.setCou(4);
		struct.setOperation(0);
		struct.setStatus(0);

		UcmService.sendDgna(struct);
	}
	
}
