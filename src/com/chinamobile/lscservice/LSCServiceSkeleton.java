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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.socket.TextMessage;

import com.chinamobile.fsuservice.ParseXml;
import com.goeasy.TestGoEasy;

import xh.mybatis.service.GosuncnService;
import xh.mybatis.tools.WebSocketPushHandler;
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
		//log.debug(invoke0.getXmlData());
		String xml = null;
		String xmlString = invoke0.getXmlData().getString();
		try{
		xml = parseXml(xmlString);
		} catch (Exception e1) {
			log.info("======================document cannot parse!!!check encode");
			e1.printStackTrace();
		}
		InvokeResponse response = new InvokeResponse();
		org.apache.axis2.databinding.types.soapencoding.String enc = new org.apache.axis2.databinding.types.soapencoding.String();
		enc.setString(xml);
		response.setInvokeReturn(enc);
		//log.info(response.getInvokeReturn());
		return response;
	}
	
	public static String parseXml(String xml) throws DocumentException {
		SAXReader reader = new SAXReader();
		reader.setEncoding("GBK");
		Document document = null;
		document = reader.read(getStringStream(xml));
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
			boolean result = GosuncnController.updateLogin(map);
			if(result){
				//log.info("啦啦啦一个FSU注册更新成功！");			
			}else{
				GosuncnController.insertLogin(map);
				//log.info("啦啦啦一个FSU注册添加成功！");
			}	
			//判断版本号为2.06的简阳基站，更新无线ip
			if("2.06".equals(map.get("FSUVER"))){
				GosuncnController.updataCameraIpByFSUID(map);
				//log.info("啦啦啦一个无线ip修改成功！");
			}
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><PK_Type><Name>LOGIN_ACK</Name></PK_Type><Info><Result>1</Result><FailureCause>NULL</FailureCause></Info></Response>";
		}else if("SEND_DEV_CONF_DATA".equals(temp)){
			//上报动环设置配置
			Element nameElem = root.element("Info").element("FSUID");
			String FSUID = nameElem.getText();
			List<Map<String, String>> configList;
			try {
				configList = ParseXml.getDevConf(xml, FSUID);
				GosuncnController.deleteConfigByFSUID(FSUID);//删除之前的配置信息，保持最新	
				GosuncnController.insertConfig(configList);//将配置信息入库
				//log.info("啦啦啦一个FSU已添加配置！");			
			} catch (DocumentException e) {
				e.printStackTrace();
			}		
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Response><PK_Type><Name>SEND_DEV_CONF_DATA_ACK</Name></PK_Type><Info><Result>1</Result><FailureCause>NULL</FailureCause></Response>";
		}else if("SEND_ALARM".equals(temp)){
			log.debug(xml);
			//上报告警信息
			Element nameElem = root.element("Info").element("FSUID");
			String FSUID = nameElem.getText();
			Element tAlarmList = root.element("Info").element("Values").element("TAlarmList");
			List<Element> alarmList = tAlarmList.elements();
			List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
			for(int i=0;i<alarmList.size();i++){
				Element tempList = (Element)alarmList.get(i);
				List<Element> list = tempList.elements();
				Map<String,String> map = new HashMap<String,String>();
				if(!"".equals(list) || list!=null){
					for(int j=0;j<list.size();j++){					
						Element e = (Element)list.get(j);
						map.put(e.getName(), e.getText());	
					}
					//查询是否有相同的流水号和FSUID，有则提取其开始时间同时删除该条记录
					String serialNo = map.get("SerialNo");
					Map<String,String> tempMap = new HashMap<String,String>();
					tempMap.put("serialNo", serialNo);
					tempMap.put("FSUID", FSUID);
					List<Map<String,Object>> serialList = GosuncnService.selectBySerialNo(tempMap);
					if(serialList.size()==0 || "".equals(serialList) || serialList==null){
						map.put("startTime", map.get("AlarmTime"));
						map.put("AlarmTime","0000-00-00 00:00:00");
					}else{
						Map<String,Object> testMap = serialList.get(0);
						//判断startTime是否为空，若为空则附加默认值
						Object tempObj = testMap.get("startTime");
						if(tempObj!=null){
							String startTime = tempObj.toString();
							if("".equals(startTime)){
								map.put("startTime","0000-00-00 00:00:00");
							}else{
								map.put("startTime", startTime);
							}
						}
						//判断AlarmTime是否为空，若为空则附加默认值
						if("".equals(map.get("AlarmTime")) || map.get("AlarmTime")==null){
							map.put("AlarmTime","0000-00-00 00:00:00");
						}
						GosuncnService.deleteBySerialNo(tempMap);
					}
					map.put("FSUID", FSUID);
					dataList.add(map);
				}
			}			
			//调用websocket推送至客户端2
			/*String alarmDesc = dataList.get(0).get("AlarmDesc").toString();
			TextMessage text = new TextMessage(alarmDesc);	
			WebSocketPushHandler webSocketPushHandler = new WebSocketPushHandler();
			webSocketPushHandler.sendMessagesToUsers(text);*/

			GosuncnController.insertAlarm(dataList);
			//log.info("啦啦啦一条告警信息已经添加！");
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
