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
import org.dom4j.DocumentException;
import org.xml.sax.InputSource;

public class Test {
	

	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		// 获取注册信息
		String GET_LOGININFO = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_LOGININFO</Name></PK_Type><Info><FSUID>09201704160085</FSUID></Info></Request>";
		
		
		// 获取FSU的FTP信息
		String GET_FTP = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_FTP</Name></PK_Type><Info><FSUID>09201704160085</FSUID></Info></Request>";
		// 获取FSU状态信息
		String GET_FSUINFO = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_FSUINFO</Name></PK_Type><Info><FSUID>09201704160085</FSUID></Info></Request>";
		getData("09201704160085");
		//getThreshold("09201704160085");
		//getLoginInfo("09201704160085");
		//getDevConf("09201704160085");
		//timeCheck("09201704160085");
		
	}
	
	/**
	 * 请求监控点数据
	 * @throws RemoteException 
	 * 
	 */
	public static List<String> getData(String FSUID) throws RemoteException{
		FSUServiceStub stub = new FSUServiceStub();
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		List<String> list = null;
		String GET_DATA = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_DATA</Name></PK_Type><Info><FSUID>"+FSUID+"</FSUID><DeviceList><Device></Device></DeviceList></Info></Request>";
		enc.setString(GET_DATA);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		System.out.println(resp);
		try {
			list = ParseXml.getData(resp.getString());
					
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 请求动环设备配置数据
	 * @throws RemoteException 
	 * 
	 */
	public static void getDevConf(String FSUID) throws RemoteException{
		FSUServiceStub stub = new FSUServiceStub();
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		List<String> list = getData(FSUID);
		String temp="";
		for(int i=0;i<list.size();i++){
			if(!"".equals(list.get(i))){
				temp=temp+"<DeviceID>"+list.get(i)+"</DeviceID>";
			}
		}		
		String GET_DEV_CONF = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_DEV_CONF</Name></PK_Type><Info><FSUID>09201704160085</FSUID>"+temp+"</Info></Request>";
		enc.setString(GET_DEV_CONF);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		try {
			Map<String,String> map = ParseXml.getDevConf(resp.getString());
			System.out.println(map);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 请求监控点门限数据
	 * 
	 */
	public static List<String> getThreshold(String FSUID) throws RemoteException{
		FSUServiceStub stub = new FSUServiceStub();
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		List<String> list = null;
		String GET_THRESHOLD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_THRESHOLD</Name></PK_Type><Info><FSUID>"+FSUID+"</FSUID><DeviceList><Device></Device></DeviceList></Info></Request>";
		enc.setString(GET_THRESHOLD);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		System.out.println(resp);
		/*try {
			list = ParseXml.getData(resp.getString());
					
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return list;
	}
	/**
	 * 获取FSU注册信息
	 * 
	 */
	public static List<String> getLoginInfo(String FSUID) throws RemoteException{
		FSUServiceStub stub = new FSUServiceStub();
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		List<String> list = null;
		String GET_LOGININFO = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>GET_LOGININFO</Name></PK_Type><Info><FSUID>"+FSUID+"</FSUID></Info></Request>";
		enc.setString(GET_LOGININFO);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		System.out.println(resp);
		/*try {
			list = ParseXml.getData(resp.getString());
					
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return list;
	}
	/**
	 * 时间同步
	 * @throws RemoteException 
	 */
	public static void timeCheck(String FSUID) throws RemoteException{
		Calendar now = Calendar.getInstance();  
        int year = now.get(Calendar.YEAR);  
        int month = now.get(Calendar.MONTH) + 1; 
        int day = now.get(Calendar.DAY_OF_MONTH);  
        int hour = now.get(Calendar.HOUR_OF_DAY);  
        int minute = now.get(Calendar.MINUTE); 
        int second = now.get(Calendar.SECOND); 
        System.out.println(year+month+day+hour+minute+second);
		FSUServiceStub stub = new FSUServiceStub();
		Invoke invoke = new Invoke();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		List<String> list = null;
		String TIME_CHECK = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><PK_Type><Name>TIME_CHECK</Name></PK_Type><Info><FSUID>"+FSUID+"</FSUID><Time><Year>"+year+"</Year><Month>"+month+"</Month><Day>"+day+"</Day><Hour>"+hour+"</Hour><Minute>"+minute+"</Minute><Second>"+second+"</Second></Time></Info></Request>";
		enc.setString(TIME_CHECK);
		invoke.setXmlData(enc);
		InvokeResponse response = stub.invoke(invoke);
		org.apache.axis2.databinding.types.soapencoding.String resp = response
				.getInvokeReturn();
		System.out.println(resp);
		/*try {
			list = ParseXml.getData(resp.getString());
					
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	


}
