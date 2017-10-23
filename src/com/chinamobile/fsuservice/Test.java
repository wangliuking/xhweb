package com.chinamobile.fsuservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.Timer;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.databinding.ADBException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.xml.sax.InputSource;

import xh.springmvc.handlers.GosuncnController;

import com.chinamobile.lscservice.LSCServiceSkeleton;

public class Test {
	protected static final Log log = LogFactory.getLog(Test.class);

	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		// 获取注册信息
		String GET_LOGININFO = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_LOGININFO</Name></PK_Type><Info><FSUID>09201704160085</FSUID></Info></Request>";
		// 获取FSU的FTP信息
		String GET_FTP = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_FTP</Name></PK_Type><Info><FSUID>09201704160085</FSUID></Info></Request>";
		// 获取FSU状态信息
		String GET_FSUINFO = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_FSUINFO</Name></PK_Type><Info><FSUID>09201704160085</FSUID></Info></Request>";
		// getData("09201704160085");
		//List<String> list = new ArrayList<String>();
		//list.add("170100000000001");// 温度
		//list.add("170200000000001");// 湿度
		//list.add("170300000000001");// 水浸
		//list.add("170400000000001");// 烟雾
		//list.add("170500000000001");// 红外
		//list.add("170700000000001");// 门碰
		//list.add("920100000000001");// 电表
		//list.add("080200000000001");// UPS
		//list.add("760300000000001");// FSU
		//getData("http://192.168.5.254:8080/services/FSUService","09201704160085");//获取所有监控点数据
		//getDataByList("http://192.168.5.254:8080/services/FSUService","09201704160085",list);// 查询监控点数据
		
		//setThreshold("http://192.168.5.254:8080/services/FSUService","09201704160085", list);// 写监控点门限数据
		//getThreshold("http://192.168.5.254:8080/services/FSUService","09201704160085",list);//查询监控点门限数据
		// getLoginInfo("http://192.168.5.254:8080/services/FSUService","09201704160085");//获取注册信息
		// getDevConf("http://192.168.5.254:8080/services/FSUService","09201704160085");//获取FSU配置信息
		// timeCheck("09201704160085");//时间确认
		
		List<Map<String,String>> list = GosuncnController.selectForGetLogin();
		for(int i=0;i<list.size();i++){
			Map<String,String> map = list.get(i);
			String FSUID = map.get("fsuId");
			String url = "http://"+map.get("fsuIp")+":8080/services/FSUService";
			try {
				List<Map<String,String>> configList = Test.getDevConf(url, FSUID);
				//String result = GosuncnController.deleteByFSUID(FSUID);//删除之前的配置信息，保持最新
				//if("success".equals(result)){
					GosuncnController.insertConfig(configList);//将最新的配置信息入库
				//}				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				
			}
		}		
		
	}

	/**
	 * 请求所有监控点数据
	 * 
	 * @throws RemoteException
	 * 
	 */
	public static List<String> getData(String url, String FSUID)
			throws RemoteException {
		FSUServiceStub stub = new FSUServiceStub(url);
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		List<String> list = null;
		String GET_DATA = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_DATA</Name></PK_Type><Info><FSUID>"
				+ FSUID
				+ "</FSUID><DeviceList><Device></Device></DeviceList></Info></Request>";
		enc.setString(GET_DATA);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		log.info(response.getInvokeReturn());
		try {
			list = ParseXml.getData(resp.getString());

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 请求指定监控点数据用于首页展示
	 * 
	 * @throws RemoteException
	 * 
	 */
	public static List<Map<String, String>> getDataByList(String url,
			String FSUID, List list) throws RemoteException {
		FSUServiceStub stub = new FSUServiceStub(url);
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		List<Map<String, String>> list1 = null;
		// 遍历list查出id并封装成报文
		String temp = "";
		for (int i = 0; i < list.size(); i++) {
			temp = temp + "<Device ID=\"" + list.get(i) + "\"></Device>";
		}
		String GET_DATA = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_DATA</Name></PK_Type><Info><FSUID>"
				+ FSUID
				+ "</FSUID><DeviceList>"
				+ temp
				+ "</DeviceList></Info></Request>";
		enc.setString(GET_DATA);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		log.info(response.getInvokeReturn());
		try {
			list1 = ParseXml.getDataByList(resp.getString());

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list1;
	}

	/**
	 * 请求指定监控点数据用于入库
	 * 
	 * @throws RemoteException
	 * 
	 */
	public static List<Map<String, String>> getDataForDB(String url,
			String FSUID, List list) throws RemoteException {
		FSUServiceStub stub = new FSUServiceStub(url);
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		List<Map<String, String>> list1 = null;
		// 遍历list查出id并封装成报文
		String temp = "";
		for (int i = 0; i < list.size(); i++) {
			temp = temp + "<Device ID=\"" + list.get(i) + "\"></Device>";
		}
		String GET_DATA = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_DATA</Name></PK_Type><Info><FSUID>"
				+ FSUID
				+ "</FSUID><DeviceList>"
				+ temp
				+ "</DeviceList></Info></Request>";
		enc.setString(GET_DATA);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		log.info(resp);
		try {
			list1 = ParseXml.getDataForDB(resp.getString());

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list1;
	}

	/**
	 * 请求动环设备配置数据
	 * 
	 * @throws RemoteException
	 * 
	 */
	public static List<Map<String, String>> getDevConf(String url, String FSUID)
			throws RemoteException {
		FSUServiceStub stub = new FSUServiceStub(url);
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		List<String> list = getData(url, FSUID);
		String temp = "";
		for (int i = 0; i < list.size(); i++) {
			if (!"".equals(list.get(i))) {
				temp = temp + "<DeviceID>" + list.get(i) + "</DeviceID>";
			}
		}
		String GET_DEV_CONF = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_DEV_CONF</Name></PK_Type><Info><FSUID>"
				+ FSUID + "</FSUID>" + temp + "</Info></Request>";
		enc.setString(GET_DEV_CONF);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		log.info(resp);
		List<Map<String, String>> configList = null;
		try {
			configList = ParseXml.getDevConf(resp.getString(), FSUID);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return configList;
	}

	/**
	 * 写监控点门限数据
	 * 
	 * @throws RemoteException
	 */
	public static void setThreshold(String url, String FSUID, List list)
			throws RemoteException {
		FSUServiceStub stub = new FSUServiceStub(url);
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		String SET_THRESHOLD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>SET_THRESHOLD</Name></PK_Type><Info><FSUID>"
				+ FSUID
				+ "</FSUID><Value><DeviceList><Device ID=\"170100000000001\"><TThreshold Type=\"3\" ID=\"017301\" SignalNumber=\"001\" Threshold=\"20.00000\" AlarmLevel=\"3\" NMAlarmID=\"99999999999999\"/></Device></DeviceList></Value></Info></Request>";
		enc.setString(SET_THRESHOLD);
		log.info(enc);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		log.info(resp);
	}

	/**
	 * 请求监控点门限数据
	 * 
	 */
	public static List<String> getThreshold(String url, String FSUID, List list)
			throws RemoteException {
		FSUServiceStub stub = new FSUServiceStub(url);
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		List<Map<String, String>> list1 = null;
		// 遍历list查出id并封装成报文
		String temp = "";
		for (int i = 0; i < list.size(); i++) {
			temp = temp + "<Device ID=\"" + list.get(i) + "\"></Device>";
		}
		String GET_THRESHOLD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_THRESHOLD</Name></PK_Type><Info><FSUID>"
				+ FSUID
				+ "</FSUID><DeviceList>"
				+ temp
				+ "</DeviceList></Info></Request>";
		enc.setString(GET_THRESHOLD);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		log.info(resp);
		/*
		 * try { list = ParseXml.getData(resp.getString());
		 * 
		 * } catch (DocumentException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		return list;
	}

	/**
	 * 获取FSU注册信息
	 * 
	 */
	public static List<String> getLoginInfo(String url, String FSUID)
			throws RemoteException {
		FSUServiceStub stub = new FSUServiceStub(url);
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		List<String> list = null;
		String GET_LOGININFO = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_LOGININFO</Name></PK_Type><Info><FSUID>"
				+ FSUID + "</FSUID></Info></Request>";
		enc.setString(GET_LOGININFO);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		log.info(resp);
		/*
		 * try { list = ParseXml.getData(resp.getString());
		 * 
		 * } catch (DocumentException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		return list;
	}

	/**
	 * 时间同步
	 * 
	 * @throws RemoteException
	 */
	public static void timeCheck(String url, String FSUID)
			throws RemoteException {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DAY_OF_MONTH);
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		int second = now.get(Calendar.SECOND);
		System.out.println(year + month + day + hour + minute + second);
		FSUServiceStub stub = new FSUServiceStub(url);
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		List<String> list = null;
		String TIME_CHECK = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>TIME_CHECK</Name></PK_Type><Info><FSUID>"
				+ FSUID
				+ "</FSUID><Time><Year>"
				+ year
				+ "</Year><Month>"
				+ month
				+ "</Month><Day>"
				+ day
				+ "</Day><Hour>"
				+ hour
				+ "</Hour><Minute>"
				+ minute
				+ "</Minute><Second>"
				+ second
				+ "</Second></Time></Info></Request>";
		enc.setString(TIME_CHECK);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		log.info(resp);
		/*
		 * try { list = ParseXml.getData(resp.getString());
		 * 
		 * } catch (DocumentException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

}
