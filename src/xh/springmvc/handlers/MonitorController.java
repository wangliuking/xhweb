package xh.springmvc.handlers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.AssetInfoBean;
import xh.mybatis.bean.BsstationBean;
import xh.mybatis.bean.ChartBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.bsLinkConfigBean;
import xh.mybatis.bean.bsrConfigBean;
import xh.mybatis.service.BsStatusService;
import xh.mybatis.service.BsstationService;
import xh.mybatis.service.CallListServices;
import xh.mybatis.service.DispatchStatusService;
import xh.mybatis.service.ServerStatusService;
import xh.mybatis.service.SqlServerService;
import xh.mybatis.service.WebLogService;
@Controller
@RequestMapping(value="/monitor")
public class MonitorController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(MonitorController.class);
	private FlexJSON json=new FlexJSON();
	private BsstationBean bsstationBean=new BsstationBean();
	private WebLogBean webLogBean=new WebLogBean();
	
	/**
	 * 查询基站信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/bsoffline",method = RequestMethod.GET)
	public void bsInfo(HttpServletRequest request, HttpServletResponse response){
		int emhstart=funUtil.StringToInt(request.getParameter("emhstart"));
		int emhlimit=funUtil.StringToInt(request.getParameter("emhlimit"));
		
		String[] type=request.getParameter("type").split(",");
		List<String> list=new ArrayList<String>();
		int door=0,smoke=0,red=0,water=0,temp=0,ups=0,i=0,fsu=0,period3=0,period4=0;
		for (String str : type) {
			if(str.equals("1")){door=1;}
			if(str.equals("2")){smoke=1;}
			if(str.equals("3")){red=1;}
			if(str.equals("4")){water=1;}
			if(str.equals("5")){temp=1;}
			if(str.equals("6")){ups=1;}
			if(str.equals("7")){i=1;}
			if(str.equals("8")){fsu=1;}
			if(str.equals("9")){period3=1;}
			if(str.equals("10")){period4=1;}
		}
		
		Map<String,Object> emhParamMap=new HashMap<String, Object>();
		emhParamMap.put("start", emhstart);
		emhParamMap.put("limit", emhlimit);
		emhParamMap.put("door", door);
		emhParamMap.put("smoke",smoke );
		emhParamMap.put("red", red);
		emhParamMap.put("water",water );
		emhParamMap.put("temp",temp );
		emhParamMap.put("ups",ups );
		emhParamMap.put("i",i );
		emhParamMap.put("fsu",fsu);
		
		
		List<Map<String,Object>>  bs=BsstationService.monitorBsofflineList();
		List<Map<String,Object>>  msc=ServerStatusService.unusualStatus(0);
		List<Map<String,Object>>  threeEmh=SqlServerService.EmhAlarmList(emhParamMap);
		List<Map<String,Object>>  fourEmh=BsStatusService.fourEmhAlarmList(emhParamMap);
		List<Map<String,Object>> emh=new ArrayList<Map<String,Object>>();
		if(period3==1){
			for (Map<String, Object> map : threeEmh) {
				emh.add(map);
			}
		}
		
		if(period4==1){
			for (Map<String, Object> map : fourEmh) {
				emh.add(map);
			}
		}
		
		Collections.sort(emh,new MapComparator());
		
		HashMap result = new HashMap();
		result.put("bsList", bs);
		result.put("bsListCount", bs.size());
		result.put("mscList", msc);
		result.put("mscCount", msc.size());
		result.put("emhList", emh);
		result.put("emhListCount", emh.size());
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
class MapComparator implements Comparator<Map<String,Object>> {
	 
    public int compare(Map<String,Object> o1, Map<String,Object> o2) {
        // TODO Auto-generated method stub
        String b1 = o1.get("time").toString();
        String b2 = o2.get("time").toString();
        if (b2 != null) {
            return b2.compareTo(b1);
        }
        return 0;
    }

}
