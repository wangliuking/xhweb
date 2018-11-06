package com.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import xh.func.plugin.GsonUtil;

public class UrlData {
	
	public static void main(String[] args) {
		//http://vip.xsl280.com/AJAX/game/gameindex.ashx?action=getpage&gameid=13&page=1&botid=130919101
		 String url="http://vip.xsl280.com/AJAX/game/gameindex.ashx?action=getpage&gameid=13&page=1&botid=130919201";
	        try {
				String urldata=getData(url);
				System.out.println("接收数据>原始数据："+urldata);
				ArrayList<Map<String,Object>> list=GsonUtil.json2Object(urldata, ArrayList.class);
				/*JSONObject json= (JSONObject) JSONObject.parse(urldata);
				System.out.println("接收数据>json："+json);*/
				
				
				int i=0;
				for (Map<String, Object> map : list) {
					Double d=Double.parseDouble(map.get("id").toString());
					BigDecimal b=new BigDecimal(d);
					DataBean bean=new DataBean();
					bean.setId(b);
					bean.setEndtime2(map.get("endtime2")==null?"":map.get("endtime2").toString());
					bean.setEndtime3(map.get("endtime3")==null?"":map.get("endtime3").toString());
					bean.setWinNumber(map.get("WinNumber")==null?"":map.get("WinNumber").toString());
					bean.setWinstr(map.get("winstr")==null?"":map.get("winstr").toString());
					bean.setResetsec(map.get("restsec")==null?"":map.get("restsec").toString());
					
					
					
					System.out.println("接收数据1："+bean);
					System.out.println("接收数据1："+(i++));
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public static String getData(String urlString) throws Exception {
        String res = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            URLConnection conn = (URLConnection)url.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            System.out.println("创建url!");
            String line;
           
            while ((line = reader.readLine()) != null) {
                //System.out.println("分别获取每行内容！："+line);
                res += line;
            }
            reader.close();
        } catch (Exception e) {
            e.getMessage();
        }
       // System.out.println(res);
        return res;
	}

}
