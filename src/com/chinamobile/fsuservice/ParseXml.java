package com.chinamobile.fsuservice;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class ParseXml {
	/**
	 * 请求所有监控点数据解析
	 * 
	 * @param elem
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getData(String xml) throws DocumentException{	
		SAXReader reader = new SAXReader();
		Document document = reader.read(getStringStream(xml));
		Element root = document.getRootElement();
		Element nameElem = root.element("Info").element("Values").element("DeviceList");
		List<Element> list = nameElem.elements();
		List<String> l = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			Element temp = (Element)list.get(i);
			String ID = temp.attributeValue("ID");
			l.add(ID);
		}
		return l;
	}
	/**
	 * 请求指定监控点数据解析用于首页展示
	 * 
	 * @param elem
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,String>> getDataByList(String xml) throws DocumentException{	
		SAXReader reader = new SAXReader();
		Document document = reader.read(getStringStream(xml));
		Element root = document.getRootElement();
		Element nameElem = root.element("Info").element("Values").element("DeviceList");
		List<Element> list = nameElem.elements();		
		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		for(int i=0;i<list.size();i++){
			Element temp = (Element)list.get(i);
			List<Element> list1 = temp.elements();
			Map<String,String> map = new HashMap<String, String>();
			for(int j=0;j<list1.size();j++){
				Element temp1 = (Element)list1.get(j);
				String ID = temp1.attributeValue("ID");
				String MeasuredVal = temp1.attributeValue("MeasuredVal");
				map.put(ID, MeasuredVal);		
			}
			l.add(map);
		}
		return l;
	}
	
	/**
	 * 请求指定监控点数据解析用于入库
	 * 
	 * @param elem
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,String>> getDataForDB(String xml) throws DocumentException{	
		SAXReader reader = new SAXReader();
		Document document = reader.read(getStringStream(xml));
		Element root = document.getRootElement();
		String FSUID = root.element("Info").element("FSUID").getText();
		Element nameElem = root.element("Info").element("Values").element("DeviceList");
		List<Element> list = nameElem.elements();		
		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		for(int i=0;i<list.size();i++){
			Element temp = (Element)list.get(i);
			String deviceId = temp.attributeValue("ID");
			List<Element> list1 = temp.elements();
			if(!"".equals(list1) || list1!=null){
				for(int j=0;j<list1.size();j++){
					Map<String,String> map = new HashMap<String, String>();
					Element temp1 = (Element)list1.get(j);
					String ID = temp1.attributeValue("ID");
					String Type = temp1.attributeValue("Type");
					String MeasuredVal = temp1.attributeValue("MeasuredVal");
					String Status = temp1.attributeValue("Status");
					String Time = temp1.attributeValue("Time");
					map.put("bsId", "110");
					map.put("FSUID", FSUID);
					map.put("deviceId", deviceId);
					map.put("ID", ID);	
					map.put("Type", Type);
					map.put("MeasuredVal", MeasuredVal);
					map.put("Status", Status);
					map.put("Time", Time);
					l.add(map);
				}		
			}	
		}
		return l;
	}
	
	/**
	 * 请求动环设备配置数据解析
	 * @param elem
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,String>> getDevConf(String xml,String FSUID) throws DocumentException{
		SAXReader reader = new SAXReader();
		Document document = reader.read(getStringStream(xml));
		Element root = document.getRootElement();
		Element nameElem = root.element("Info").element("Values");
		List<Element> list = nameElem.elements();
		List<Map<String,String>> configList = new ArrayList<Map<String,String>>();
		for(int i=0;i<list.size();i++){
			Map<String,String> map = new HashMap<String,String>();
			Element temp = (Element)list.get(i);
			String DeviceID = temp.attributeValue("DeviceID");
			String DeviceName = temp.attributeValue("DeviceName");
			map.put("fsuId", FSUID);
			map.put("deviceId", DeviceID);
			map.put("deviceName", DeviceName);
			configList.add(map);
		}
		return configList;
	}

	
	
	// 递归查询节点函数,输出节点名称
	private static void getChildNodes(Element elem) {
		System.out.println(elem.getName());
		Iterator<Node> it = elem.nodeIterator();
		while (it.hasNext()) {
			Node node = it.next();
			if (node instanceof Element) {
				Element e1 = (Element) node;
				getChildNodes(e1);
			}

		}
	}
	
	public static InputStream getStringStream(String sInputString){
		ByteArrayInputStream tInputStringStream = null;
		if(sInputString != null && !sInputString.trim().equals("")){
			tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
		}
		return tInputStringStream;
	}
}
