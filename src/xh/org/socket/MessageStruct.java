package xh.org.socket;

import java.util.Map;

public class MessageStruct {
	private  short commandHeader=(short) 0xC4D7;//commandHeader	2	命令开始字段  
	private  byte segNum=1;            //segNum	1	分片总数
	private  byte segFlag=1;           //segFlag	1	当前分片序号
	private  short length=12;           //length	2	后接数据长度
	private  short commandId;        //commandId	2	命令ID    
	private  short protocolNo=0;       //protocolNo	2	协议号
	private  int businessSN=0;         //businessSN	4	业务流水号
	private  byte srcDevice=6;         //srcDevice	1	源设备类型
	private  byte dstDevice=6;         //dstDevice	1	目标设备类型
	//结构体
	
	private  short checksum=(short) 0x0000;	 //checksum	2	校验码       //18
	
	public MessageStruct(){}
	

	public short getCommandHeader() {
		return commandHeader;
	}

	public void setCommandHeader(short commandHeader) {
		this.commandHeader = commandHeader;
	}

	public byte getSegNum() {
		return segNum;
	}

	public void setSegNum(byte segNum) {
		this.segNum = segNum;
	}

	public byte getSegFlag() {
		return segFlag;
	}

	public void setSegFlag(byte segFlag) {
		this.segFlag = segFlag;
	}

	public short getLength() {
		return length;
	}

	public void setLength(short length) {
		this.length = length;
	}

	public short getCommandId() {
		return commandId;
	}

	public void setCommandId(short commandId) {
		this.commandId = commandId;
	}

	public short getProtocolNo() {
		return protocolNo;
	}

	public void setProtocolNo(short protocolNo) {
		this.protocolNo = protocolNo;
	}

	public int getBusinessSN() {
		return businessSN;
	}

	public void setBusinessSN(int businessSN) {
		this.businessSN = businessSN;
	}

	public byte getSrcDevice() {
		return srcDevice;
	}

	public void setSrcDevice(byte srcDevice) {
		this.srcDevice = srcDevice;
	}

	public byte getDstDevice() {
		return dstDevice;
	}

	public void setDstDevice(byte dstDevice) {
		this.dstDevice = dstDevice;
	}

	public short getChecksum() {
		return checksum;
	}

	public void setChecksum(short checksum) {
		this.checksum = checksum;
	}
	
	
}
