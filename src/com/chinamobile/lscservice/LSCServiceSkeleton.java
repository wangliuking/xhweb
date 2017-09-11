/**
 * LSCServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.6  Built on : Jul 30, 2017 (09:08:31 BST)
 */
package com.chinamobile.lscservice;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * LSCServiceSkeleton java skeleton for the axisService
 */
public class LSCServiceSkeleton implements LSCServiceSkeletonInterface {

	/**
	 * Auto generated method signature
	 * 
	 * @param invoke0
	 * @return invokeResponse1
	 */

	public com.chinamobile.lscservice.InvokeResponse invoke(
			com.chinamobile.lscservice.Invoke invoke0) {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		System.out.println(time);
		System.out.println(invoke0.getXmlData());
		String xml = null;
		String xmlString = invoke0.getXmlData().getString();
		//xml = parseXml(xmlString);
		InvokeResponse response = new InvokeResponse();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		enc.setString("<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><PK_Type><Name>LOGIN_ACK</Name></PK_Type><Info><Result>1</Result><FailureCause>NULL</FailureCause></Info></Response>");
		response.setInvokeReturn(enc);
		System.out.println(response.getInvokeReturn());
		return response;
	}
	
	private static String parseXml(String xml){
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(getStringStream(xml));
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element root = document.getRootElement();
		String temp = root.element("PK_Type").element("Name").getText();
		//判断报文类型
		if("LOGIN".equals(temp)){
			//注册
			Element nameElem = root.element("Info");
			List<Element> list = nameElem.elements();
			Map<String,String> map = new HashMap<String,String>();
			for(int i=0;i<list.size();i++){
				Element e = (Element)list.get(i);
				map.put(e.getName(), e.getText());			
			}
			System.out.println(map);
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><PK_Type><Name>LOGIN_ACK</Name></PK_Type><Info><Result>1</Result><FailureCause>NULL</FailureCause></Info></Response>";
		}else if("SEND_DEV_CONF_DATA".equals(temp)){
			//上报动环设置配置
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><PK_Type><Name>SEND_DEV_CONF_DATA_ACK</Name></PK_Type><Info><Result>1</Result><FailureCause>NULL</FailureCause></Response>";
		}
		return null;
	}
	
	public static InputStream getStringStream(String sInputString){
		ByteArrayInputStream tInputStringStream = null;
		if(sInputString != null && !sInputString.trim().equals("")){
			tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
		}
		return tInputStringStream;
	}

}
