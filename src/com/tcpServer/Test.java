package com.tcpServer;

import java.util.List;

import com.tcpBean.ErrProTable;
import com.tcpBean.GetAllBsList;
import com.tcpBean.GetAllBsListAck;
import com.tcpBean.GpsInfoUp;
import com.tcpServer.ServerDemo.TestServer;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GpsInfoUp gpsInfoUp = new GpsInfoUp();
		gpsInfoUp.setLatitude("34.6666");
		gpsInfoUp.setLongitude("109.7899");
		gpsInfoUp.setName("张三");
		gpsInfoUp.setUserid("admin");
		Service.appInsertGpsInfoUp(gpsInfoUp);
	}

}
