package xh.springmvc.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.InfoDeviceBean;
import xh.mybatis.service.InfoDeviceService;
import xh.mybatis.service.InspectionServices;
import xh.org.listeners.SingLoginListener;

@Controller
@RequestMapping("/bs")
public class InfoDeviceController {
	private boolean success;
	private String message;
	private FunUtil funUtil=new FunUtil();
	protected final Log log = LogFactory.getLog(InfoDeviceController.class);
	private FlexJSON json=new FlexJSON();
	
	@RequestMapping("/device/list")
	public void bsInfoDeviceList(HttpServletRequest request, HttpServletResponse response){
		int bsId=Integer.parseInt(request.getParameter("bsId"));	

		HashMap result = new HashMap();
		result.put("items",InfoDeviceService.bsInfoDeviceList(bsId));
		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(result);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@RequestMapping("/device/update")
	public void updateBsDevice(HttpServletRequest request,HttpServletResponse response){
		String formData=request.getParameter("formData");
		
		InfoDeviceBean bean=GsonUtil.json2Object(formData, InfoDeviceBean.class);
		
		
		log.info(bean.toString());
		
		int count=InfoDeviceService.bsInfoDeviceListCount(Integer.parseInt(bean.getBsId()));
		int rlt=0;
		log.info("count==>"+count);
		if(count==0){
			rlt=InfoDeviceService.insertBsDevice(bean);
		}else{
			rlt=InfoDeviceService.updateBsDevice(bean);
		}
		
		if(rlt==1){
			this.message="更新成功";
			this.success=true;
		}else{
          this.success=false;
          this.message="更新失败";
		}

		HashMap result = new HashMap();
		result.put("success",success);
		result.put("message",message);
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
