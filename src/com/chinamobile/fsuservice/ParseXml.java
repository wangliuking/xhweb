package com.chinamobile.fsuservice;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class ParseXml {
	/**
	 * 请求监控点数据解析
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
	 * 请求动环设备配置数据解析
	 * @param elem
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getDevConf(String xml) throws DocumentException{
		SAXReader reader = new SAXReader();
		Document document = reader.read(getStringStream(xml));
		Element root = document.getRootElement();
		Element nameElem = root.element("Info").element("Values");
		List<Element> list = nameElem.elements();
		Map<String,String> map = new HashMap<String,String>();
		for(int i=0;i<list.size();i++){
			Element temp = (Element)list.get(i);
			String DeviceID = temp.attributeValue("DeviceID");
			String DeviceName = temp.attributeValue("DeviceName");
			map.put(DeviceName, DeviceID);
		}
		return map;
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
