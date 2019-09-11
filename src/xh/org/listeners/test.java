package xh.org.listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
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
import java.util.Comparator;
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

import com.spire.ms.System.Collections.Comparer;
import com.sun.xml.xsom.impl.util.Uri;

import xh.func.plugin.DocConverter;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.service.EastComService;
import xh.mybatis.service.SqlServerService;
import xh.org.socket.MotoTcpClient;
import xh.org.socket.TcpKeepAliveClient;

public class test {
    

	public static void main(String[] args) {

		String a="-1";
		System.out.println(Integer.parseInt(a));
		
		
		

	}
	public static List<Map<String,Object>> readFileTree(String path){
		File dir = new File(path);
		File[] files=dir.listFiles();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(files!=null){
			for(int i=0;i<files.length;i++){
				Map<String,Object> map=new HashMap<String, Object>();
				if(files[i].isDirectory()){
					map.put("isDir", true);
					map.put("file", files[i].getName());				
					map.put("children", readFileTree(files[i].getPath()));
				}else{
					map.put("isDir", false);
					map.put("file", files[i].getName());
					map.put("path", files[i].getPath());
				}
				list.add(map);
			}
		}else{
			System.out.println("当前目录为空");
		}
		return list;
		
	}
	
	

	
}

