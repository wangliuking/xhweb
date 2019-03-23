package xh.springmvc.handlers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xh.func.plugin.CaptchaUtil;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.FaultFourBean;
import xh.mybatis.bean.FaultOneBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.service.FaultLevelService;
import xh.mybatis.service.FaultService;

@Controller
public class FaultLevelController {
	private boolean success;
	private String message;
	private FunUtil funUtil = new FunUtil();
	private FlexJSON json = new FlexJSON();
	private WebLogBean webLogBean = new WebLogBean();

	protected final Log log=LogFactory.getLog(FaultLevelController.class);
	@RequestMapping(value = "/faultlevel/one_list", method = RequestMethod.GET)
	@ResponseBody
	public HashMap one_list(HttpServletRequest request,HttpServletResponse response){
		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));
		String time=request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time",time);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", FaultLevelService.one_list(map));
		result.put("totals",FaultLevelService.one_count(map));
		return result;		
	}
	@RequestMapping(value = "/faultlevel/three_list", method = RequestMethod.GET)
	@ResponseBody
	public HashMap three_list(HttpServletRequest request,HttpServletResponse response){
		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));
		String time=request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time",time);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", FaultLevelService.three_list(map));
		result.put("totals",FaultLevelService.three_count(map));
		return result;		
	}
	@RequestMapping(value = "/faultlevel/four_list", method = RequestMethod.GET)
	@ResponseBody
	public HashMap four_list(HttpServletRequest request,HttpServletResponse response){
		int start = funUtil.StringToInt(request.getParameter("start"));
		int limit = funUtil.StringToInt(request.getParameter("limit"));
		String time=request.getParameter("time");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("time",time);
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("items", FaultLevelService.four_list(map));
		result.put("totals",FaultLevelService.four_count(map));
		return result;		
	}
	@RequestMapping(value = "/faultlevel/one_add", method = RequestMethod.POST)
	@ResponseBody
	public HashMap one_add(@RequestBody FaultOneBean bean){
		int rs=FaultLevelService.one_add(bean);
		if(rs>0){
			this.success=true;
			this.message="添加成功";
		}else{
			this.success=false;
			this.message="添加失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		return result;		
	}
	@RequestMapping(value = "/faultlevel/four_add", method = RequestMethod.POST)
	@ResponseBody
	public HashMap four_add(@RequestBody FaultFourBean bean){
		int rs=FaultLevelService.four_add(bean);
		if(rs>0){
			this.success=true;
			this.message="添加成功";
		}else{
			this.success=false;
			this.message="添加失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		return result;		
	}
	@RequestMapping(value = "/faultlevel/one_update", method = RequestMethod.POST)
	@ResponseBody
	public HashMap one_update(@RequestBody FaultOneBean bean){
		int rs=FaultLevelService.one_update(bean);
		if(rs>0){
			this.success=true;
			this.message="修改成功";
		}else{
			this.success=false;
			this.message="修改失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		return result;		
	}
	@RequestMapping(value = "/faultlevel/four_update", method = RequestMethod.POST)
	@ResponseBody
	public HashMap four_update(@RequestBody FaultFourBean bean){
		int rs=FaultLevelService.four_update(bean);
		if(rs>0){
			this.success=true;
			this.message="修改成功";
		}else{
			this.success=false;
			this.message="修改失败";
		}
		HashMap result = new HashMap();
		result.put("success", success);
		result.put("message",message);
		return result;		
	}

}
