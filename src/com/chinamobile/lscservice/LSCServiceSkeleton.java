/**
 * LSCServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.6  Built on : Jul 30, 2017 (09:08:31 BST)
 */
package com.chinamobile.lscservice;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.chinamobile.fsuservice.ParseXml;

import xh.springmvc.handlers.GosuncnController;

/**
 * LSCServiceSkeleton java skeleton for the axisService
 */
public class LSCServiceSkeleton implements LSCServiceSkeletonInterface {
	protected static final Log log = LogFactory.getLog(LSCServiceSkeleton.class);

	/**
	 * Auto generated method signature
	 * 
	 * @param invoke0
	 * @return invokeResponse1
	 */

	public com.chinamobile.lscservice.InvokeResponse invoke(
			com.chinamobile.lscservice.Invoke invoke0) {
		log.info(invoke0.getXmlData());
		/*try {
			log.info(new String(invoke0.getXmlData().getString().getBytes(),"GB2312"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String xml = null;
		String xmlString = invoke0.getXmlData().getString();
		xml = parseXml(xmlString);
		InvokeResponse response = new InvokeResponse();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		enc.setString(xml);
		response.setInvokeReturn(enc);
		log.info(response.getInvokeReturn());
		return response;
	}
	
	public static String parseXml(String xml){
		SAXReader reader = new SAXReader();
		reader.setEncoding("GBK");
		Document document = null;
		try {
			document = reader.read(getStringStream(xml));
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			log.info("======================document cannot parse!!!check encode");
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
			String result = GosuncnController.insertLogin(map);
			log.info(result);
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><PK_Type><Name>LOGIN_ACK</Name></PK_Type><Info><Result>1</Result><FailureCause>NULL</FailureCause></Info></Response>";
		}else if("SEND_DEV_CONF_DATA".equals(temp)){
			//上报动环设置配置
			Element nameElem = root.element("Info").element("FSUID");
			String FSUID = nameElem.getText();
			List<Map<String, String>> configList;
			try {
				configList = ParseXml.getDevConf(xml, FSUID);
				GosuncnController.deleteByFSUID(FSUID);//配置信息入库前，删除以前的
				String result = GosuncnController.insertConfig(configList);//将配置信息入库
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><PK_Type><Name>SEND_DEV_CONF_DATA_ACK</Name></PK_Type><Info><Result>1</Result><FailureCause>NULL</FailureCause></Response>";
		}else if("SEND_ALARM".equals(temp)){
			//上报告警信息
			Element nameElem = root.element("Info").element("FSUID");
			String FSUID = nameElem.getText();
			Element tAlarm = root.element("Info").element("Values").element("TAlarmList").element("TAlarm");//一条告警数据
			List<Element> list = tAlarm.elements();
			Map<String,String> map = new HashMap<String,String>();
			for(int i=0;i<list.size();i++){
				Element e = (Element)list.get(i);
				map.put(e.getName(), e.getText());			
			}
			map.put("FSUID", FSUID);
			String result = GosuncnController.insertAlarm(map);
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><PK_Type><Name>SEND_ALARM_ACK</Name></PK_Type><Info><Result>1</Result><FailureCause>NULL</FailureCause></Info></Response>";
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
