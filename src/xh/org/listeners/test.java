package xh.org.listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.Timer;
import java.util.regex.Pattern;















import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;






import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.Cookie;  
import org.apache.commons.httpclient.cookie.CookiePolicy;







import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import com.sun.xml.xsom.impl.util.Uri;

import xh.func.plugin.DocConverter;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.service.EastComService;
import xh.mybatis.service.SqlServerService;
import xh.org.socket.MotoTcpClient;
import xh.org.socket.TcpKeepAliveClient;

public class test {
	private static BasicCookieStore cookieStore=new BasicCookieStore();

	public static void main(String[] args) {
		
		try {
			//http://xsj2800.com/Home/login/verify.html
			//doPostTestTwo();
			Login();
			jndw28();
			GameData();
			//bj16();
			//Code();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    
	}
	//获取验证码
	public static void Code() throws UnsupportedEncodingException {
		 
		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CookieStore cookieStore = new BasicCookieStore();
		String PHPSESSID = null;
		HttpGet getMethod=new HttpGet("http://xsj2800.com/Home/login/verify.html");
       try {
            HttpResponse response = httpClient.execute(getMethod, new BasicHttpContext());
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent(); 
            OutputStream outstream = new FileOutputStream(new File("D:/code","code.png"));
            //获得Cookies
            
         /*   List<org.apache.http.cookie.Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                if (cookies.get(i).getName().equals("PHPSESSID")) {
                	PHPSESSID = cookies.get(i).getValue();
                }
                if (cookies.get(i).getName().equals("cookie_user")) {
                    cookie_user = cookies.get(i).getValue();
                }
            }*/
            int l = -1;
            byte[] tmp = new byte[2048]; 
            while ((l = instream.read(tmp)) != -1) {
                outstream.write(tmp);
            } 
            outstream.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            getMethod.releaseConnection();
        }
       System.out.println("验证码获取结束");
       System.out.println("当前PHPSESSID："+PHPSESSID);
       
       /*String code = getImgContent("d:/code/code.png");
       //输出识别结果
       System.out.println("Ocr识别结果: \n" + code);*/
       
       
		
	}
	protected static String getImgContent(String imgUrl) {
		String content = "";
		File imageFile = new File(imgUrl);
		//读取图片数字
		ITesseract instance = new Tesseract();


		File tessDataFolder = LoadLibs.extractTessResources("tessdata");
		instance.setLanguage("eng");//英文库识别数字比较准确
		instance.setDatapath(tessDataFolder.getAbsolutePath());


		try {
		content = instance.doOCR(imageFile).replace("\n", "");
		System.out.println(content);
		} catch (TesseractException e) {
		System.err.println(e.getMessage());
		}
		return content;
		}
		
	public static void Login() throws UnsupportedEncodingException {
		//BasicCookieStore cookieStore  = new BasicCookieStore();
		//获取http客户端
		//CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpClient httpClient = HttpClients.custom()
	             .setDefaultCookieStore(cookieStore).build();

		RequestConfig requestConfig = RequestConfig.custom()  
		        .setConnectTimeout(5000).setConnectionRequestTimeout(1000)  
		        .setSocketTimeout(5000).build(); 
		//获取验证码
		HttpGet codeGet=new HttpGet("http://xsj2800.com/Home/login/verify.html");
		codeGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
		try {
			codeGet.setConfig(requestConfig);
		   HttpResponse response = httpClient.execute(codeGet, new BasicHttpContext());
		   HttpEntity entity = response.getEntity();
		   InputStream instream = entity.getContent(); 
		   OutputStream outstream = new FileOutputStream(new File("D:/code","code.png"));
		            int l = -1;
		            byte[] tmp = new byte[2048]; 
		            while ((l = instream.read(tmp)) != -1) {
		                outstream.write(tmp);
		            } 
		            outstream.close();
		        } catch (SocketTimeoutException e) {
		        	 System.out.println("连接超时");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					codeGet.releaseConnection();
		        }
		       System.out.println("验证码获取结束");
		
	
		
 
		// 创建Post请求http://183.221.117.37:5555/web/login
		HttpPost httpPost = new HttpPost("http://xsj2800.com/home/Login/logined");
		httpPost.setConfig(requestConfig);
		//HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/xhweb/web/login");
		
		
		
		//提醒用户并输入验证码
		System.out.println("请输入验证码：");
		String code="";
		Scanner in = new Scanner(System.in);
		code = in.next();
		in.close();
	
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("ltype", 1);
        paramMap.put("type", 0);
        paramMap.put("newview", 1);
        paramMap.put("account", "18100822165");
        paramMap.put("passwd","mw50113516");
        paramMap.put("pic_code", code);
		String jsonString = GsonUtil.object2Json(paramMap);
		System.out.println(jsonString);
		
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 遍历map，设置参数到list中
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        
        // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
        
     // 创建form表单对象
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");
        formEntity.setContentType("Content-Type:application/json");
 
		//StringEntity entity = new StringEntity(jsonString, "UTF-8");
	
		// post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
		httpPost.setEntity(formEntity);
 
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
	   // httpPost.setHeader("Referer", "http://xsj2800.com/home/Index/games/mapid/83");
	   // httpPost.setHeader("Cookie", "PHPSESSID=aqhncabto8h4j067g60fjd2hr4; p_mapid=83; auth=bc5148b94911e460554a1d40a269f851%3Aa3e90179967d2440e7db039c58822a3a");
	    httpPost.setHeader("Connection", "keep-alive");
	    httpPost.setHeader("Cache-Control", "no-cache");
	    httpPost.setHeader("X-Requested-With", "XMLHttpRequest");

 
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Post请求
			response = httpClient.execute(httpPost);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			
			// get all cookies
			
		    List<org.apache.http.cookie.Cookie> list = cookieStore.getCookies();
		    System.out.println("All Cookies:"+list.size());
		        for (org.apache.http.cookie.Cookie cookie : list) {
		        System.out.println(cookie);
		    }
		       
			String str="";
			System.out.println("响应状态为:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("响应内容长度为:" + responseEntity.getContentLength());
				InputStream instreams = responseEntity.getContent();
		        str = convertStreamToString(instreams);
			}
			System.out.println("字符：-》"+str);
			
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//GameData();
		
	}
	//http://xsj2800.com/home/game/indexInfo
	//获取数据
	public static void GameData() throws UnsupportedEncodingException {
		// 创建Post请求http://183.221.117.37:5555/web/login
		CloseableHttpClient httpClient = HttpClients.custom()
	             .setDefaultCookieStore(cookieStore).build();
		HttpPost httpPost = new HttpPost("http://xsj2800.com/home/game/indexInfo");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("gplay", 83);
        paramMap.put("page", 1);
        paramMap.put("gtype", 73);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 遍历map，设置参数到list中
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        
     // 创建form表单对象
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");
        formEntity.setContentType("Content-Type:application/x-www-form-urlencoded");
        
        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
 
	
		// post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
		httpPost.setEntity(formEntity);
 
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
	   // httpPost.setHeader("Referer", "http://xsj2800.com/home/Index/games/mapid/83");
		 //httpPost.setHeader("Cookie", "PHPSESSID=aqhncabto8h4j067g60fjd2hr4; p_mapid=83; auth=bc5148b94911e460554a1d40a269f851%3Aa3e90179967d2440e7db039c58822a3a");
		    httpPost.setHeader("Connection", "keep-alive");
	    httpPost.setHeader("Cache-Control", "no-cache");
	    httpPost.setHeader("X-Requested-With", "XMLHttpRequest");

 
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Post请求
			response = httpClient.execute(httpPost);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
 
			String str="";
			System.out.println("响应状态为:" + response.getStatusLine());
			if (responseEntity != null) {
				InputStream instreams = responseEntity.getContent();
		        str = convertStreamToString(instreams);
			}
			
			Map<String,Object> map=GsonUtil.json2Object(str, Map.class);
			System.out.println("字符：-》"+str);
			
			
			Map<String,Object> data=(Map<String, Object>) map.get("data");
			Map<String,Object> dayCount=(Map<String, Object>) data.get("dayCount");
			Map<String,Object> prevTimes=(Map<String, Object>) data.get("prevTimes");
			Map<String,Object> thisTimes=(Map<String, Object>) data.get("thisTimes");
			System.out.println("code：-》"+map.get("code"));
			System.out.println("dayCount：-》"+dayCount.get("win_num"));
			System.out.println("prevTimes：-》"+prevTimes.get("number"));
			System.out.println("thisTimes：-》"+thisTimes.get("number"));
			
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void bj16() throws UnsupportedEncodingException {
		 
		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		//CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpClient httpClient = HttpClients.custom()
	             .setDefaultCookieStore(cookieStore).build();
		BasicClientCookie cookie = new BasicClientCookie("p_mapid", "10"); 
        cookie.setVersion(0);  
        cookie.setDomain("xsj2800.com");   //设置范围
        cookie.setPath("/"); 
        cookieStore.addCookie(cookie);
		// 创建Post请求http://183.221.117.37:5555/web/login
        //http://xsj2800.com/home/Game/bettingSubmit
		HttpPost httpPost = new HttpPost("http://xsj2800.com/home/Game/bettingSubmit");
		//HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/xhweb/web/login");
		
		// 我这里利用阿里的fastjson，将Object转换为json字符串;
		// (需要导入com.alibaba.fastjson.JSON包)
		
		
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("mapid", 10);
        paramMap.put("orderid", 956833);
        paramMap.put("betting[f3]", 5);
        paramMap.put("betting[f4]", 15);
        paramMap.put("betting[f5]", 30);
        paramMap.put("betting[f6]", 50);
        paramMap.put("betting[f7]", 75);
        paramMap.put("betting[f8]", 105);
        paramMap.put("betting[f9]", 125);
        paramMap.put("betting[f10]", 135);
        paramMap.put("betting[f11]", 135);
        paramMap.put("betting[f12]", 125);
        paramMap.put("betting[f13]", 105);
        paramMap.put("betting[f14]", 75);
        paramMap.put("betting[f15]", 50);
        paramMap.put("betting[f16]", 30);
        paramMap.put("betting[f17]", 15);
        paramMap.put("betting[f18]", 5);
        /*paramMap.put("username", "muwei");
        paramMap.put("password", "123");*/
		String jsonString = GsonUtil.object2Json(paramMap);
		System.out.println(jsonString);
		
	
		
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 遍历map，设置参数到list中
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        
     // 创建form表单对象
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");
        formEntity.setContentType("Content-Type:application/x-www-form-urlencoded");
        
        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
	
		// post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        //httpPost.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
		httpPost.setEntity(formEntity);
         // httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
		//httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
	    //httpPost.setHeader("Referer", "http://xsj2800.com/home/Index/games/mapid/10");
		 //httpPost.setHeader("Cookie", "PHPSESSID=aqhncabto8h4j067g60fjd2hr4; p_mapid=83; auth=bc5148b94911e460554a1d40a269f851%3Aa3e90179967d2440e7db039c58822a3a");
		httpPost.setHeader("Connection", "keep-alive");
	    httpPost.setHeader("Cache-Control", "no-cache");
	    httpPost.setHeader("X-Requested-With", "XMLHttpRequest");

 
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Post请求
			response = httpClient.execute(httpPost);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
 
			String str="";
			System.out.println("响应状态为:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("响应内容长度为:" + responseEntity.getContentLength());
				InputStream instreams = responseEntity.getContent();
		        str = convertStreamToString(instreams);
			}
			System.out.println("字符：-》"+str);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void jndw28() throws UnsupportedEncodingException {
		 
		// 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
		//CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpClient httpClient = HttpClients.custom()
	             .setDefaultCookieStore(cookieStore).build();
		BasicClientCookie cookie = new BasicClientCookie("p_mapid", "83"); 
        cookie.setVersion(0);  
        cookie.setDomain("xsj2800.com");   //设置范围
        cookie.setPath("/"); 
        cookieStore.addCookie(cookie);
		// 创建Post请求http://183.221.117.37:5555/web/login
        //http://xsj2800.com/home/Game/bettingSubmit
		HttpPost httpPost = new HttpPost("http://xsj2800.com/home/Game/bettingSubmit");
		//HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/xhweb/web/login");
		
		// 我这里利用阿里的fastjson，将Object转换为json字符串;
		// (需要导入com.alibaba.fastjson.JSON包)
		
		
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("mapid", 83);
        paramMap.put("orderid", 2436572);
        paramMap.put("betting[f1]", 500);
        paramMap.put("betting[f7]", 500);
        /*paramMap.put("username", "muwei");
        paramMap.put("password", "123");*/
		String jsonString = GsonUtil.object2Json(paramMap);
		System.out.println(jsonString);
		
	
		
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 遍历map，设置参数到list中
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        
     // 创建form表单对象
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");
        formEntity.setContentType("Content-Type:application/x-www-form-urlencoded");
        
        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
	
		// post请求是将参数放在请求体里面传过去的;这里将entity放入post请求体中
        //httpPost.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
		httpPost.setEntity(formEntity);
         // httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
		//httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
	    //httpPost.setHeader("Referer", "http://xsj2800.com/home/Index/games/mapid/10");
		 //httpPost.setHeader("Cookie", "PHPSESSID=aqhncabto8h4j067g60fjd2hr4; p_mapid=83; auth=bc5148b94911e460554a1d40a269f851%3Aa3e90179967d2440e7db039c58822a3a");
		httpPost.setHeader("Connection", "keep-alive");
	    httpPost.setHeader("Cache-Control", "no-cache");
	    httpPost.setHeader("X-Requested-With", "XMLHttpRequest");

 
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Post请求
			response = httpClient.execute(httpPost);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
 
			String str="";
			System.out.println("响应状态为:" + response.getStatusLine());
			if (responseEntity != null) {
				System.out.println("响应内容长度为:" + responseEntity.getContentLength());
				InputStream instreams = responseEntity.getContent();
		        str = convertStreamToString(instreams);
			}
			System.out.println("字符：-》"+str);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void fun5() throws ClientProtocolException, IOException{
		//1、创建HttpClient
		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
		//2、创建get或post请求方法
		PostMethod method = new PostMethod("http://localhost:8080/itcast297/loginAction_login");
		//3、设置编码
		httpClient.getParams().setContentCharset("UTF-8");
		//4、设置请求消息头，为表单方式提交
		method.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		
		//5、设置参数
		method.setParameter("username", "cgx");
		method.setParameter("password", "123456");
		
//		6、执行提交
		httpClient.executeMethod(method);
		System.out.println(method.getStatusLine());
		System.out.println(method.getResponseBodyAsString());
		
		
	}
	
	public static String convertStreamToString(InputStream is)
	        throws IOException {

	    InputStreamReader ir = new InputStreamReader(is,"utf-8");

	    BufferedReader reader = new BufferedReader(ir);

	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {//.UnescapeDataString(line)
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return decode(sb.toString());
	}
	public static String decode(String unicodeStr) {
		if (unicodeStr == null) {
			return null;
		}
		StringBuffer retBuf = new StringBuffer();
		int maxLoop = unicodeStr.length();
		for (int i = 0; i < maxLoop; i++) {
			if (unicodeStr.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
								.charAt(i + 1) == 'U')))
					try {
						retBuf.append((char) Integer.parseInt(
								unicodeStr.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException localNumberFormatException) {
						retBuf.append(unicodeStr.charAt(i));
					}
				else
					retBuf.append(unicodeStr.charAt(i));
			} else {
				retBuf.append(unicodeStr.charAt(i));
			}
		}
		return retBuf.toString();
	}
	

	
}

