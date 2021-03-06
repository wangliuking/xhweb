package xh.springmvc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import net.sf.json.JSONObject;
import xh.func.plugin.DownLoadUtils;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.*;
import xh.mybatis.service.*;

@Controller
@RequestMapping(value = "/emergency")
public class EmergencyController {

    private boolean success;
    private String message;
    private FunUtil funUtil = new FunUtil();
    protected final Log log = LogFactory.getLog(EmergencyController.class);
    private FlexJSON json = new FlexJSON();
    private WebLogBean webLogBean = new WebLogBean();
    /**
     * 查询所有流程
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/selectAll", method = RequestMethod.GET)
    public void selectAll(HttpServletRequest request,
                          HttpServletResponse response) {
        this.success = true;
        int start = funUtil.StringToInt(request.getParameter("start"));
        int limit = funUtil.StringToInt(request.getParameter("limit"));
        String user=funUtil.loginUser(request);
        WebUserBean userbean=WebUserServices.selectUserByUser(user);
        int roleId=userbean.getRoleId();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("limit", limit);
        map.put("user", user);
        map.put("roleId", roleId);

        HashMap result = new HashMap();
        result.put("success", success);
        result.put("items", EmergencyService.selectAll(map));
        result.put("totals", EmergencyService.dataCount(map));
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/selectAll2", method = RequestMethod.GET)
    public void selectAll2(HttpServletRequest request,
                          HttpServletResponse response) {
        this.success = true;
        int start = funUtil.StringToInt(request.getParameter("start"));
        int limit = funUtil.StringToInt(request.getParameter("limit"));
        String user=funUtil.loginUser(request);
        WebUserBean userbean=WebUserServices.selectUserByUser(user);
        int roleId=userbean.getRoleId();
        start=(start-1)*limit;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("limit", limit);
        map.put("user", user);
        map.put("roleId", roleId);

        HashMap result = new HashMap();
        result.put("success", success);
        result.put("items", EmergencyService.selectAll(map));
        result.put("totals", EmergencyService.dataCount(map));
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    @RequestMapping(value = "/applyProgress", method = RequestMethod.GET)
    public void applyProgress(HttpServletRequest request,
                              HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("items", EmergencyService.applyProgress(id));
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        log.debug(jsonstr);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

//	@RequestMapping(value = "/demo", method = RequestMethod.GET)
//	public List<Integer> selectquitNumber(@RequestBody UserFormBean userFormBean) {
//		this.success = true;
//		String userName = request.getParameter("userName");
//		List<Integer> ids =  new ArrayList<>();
//		return quitNetService.selectquitNumber(userName);
//	}

    /**
     * 申请
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/insertEmergency", method = RequestMethod.POST)
    public void optimizeNet(HttpServletRequest request,
                        HttpServletResponse response) {
        this.success = true;
        String jsonData = request.getParameter("formData");
        EmergencyBean bean = GsonUtil.json2Object(jsonData, EmergencyBean.class);
        bean.setUserName(funUtil.loginUser(request));
        bean.setRequestTime(funUtil.nowDate());
        log.info("data==>" + bean.toString());

        int rst = EmergencyService.insertEmergency(bean);
        WebLogBean webLogBean = new WebLogBean();
        if (rst == 1) {
            this.message = "应急演练信息已经成功提交";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(1);
            webLogBean.setContent("应急演练申请信息，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            FunUtil.sendMsgToUserByPower("o_check_emergency", 3, "应急处置演练", "应急处置演练任务", request);
           // sendNotifytoGroup("o_check_emergency",10003, "应急演练任务", request);
            //----END
        } else {
            this.message = "应急演练申请信息提交失败";
        }
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("result", rst);
        result.put("message", message);
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        log.debug(jsonstr);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 服务提供方审核
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedOne", method = RequestMethod.POST)
    public void checkedOne(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        //int checked = funUtil.StringToInt(request.getParameter("checked"));
        String note2 = request.getParameter("note2");
        String user = request.getParameter("userName");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        EmergencyBean bean = new EmergencyBean();
        bean.setUser1(funUtil.loginUser(request));
        bean.setTime1(funUtil.nowDate());
        bean.setNote2(note2);
        bean.setId(id);
        bean.setChecked(1);
        bean.setFileName1(fileName);
        bean.setFilePath1(filePath);
        int rst = EmergencyService.checkedOne(bean);
        if (rst == 1) {
            this.message = "上传应急处置演练计划成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("上传应急处置演练计划，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);
            sendNotifytoSingle(user, "演练计划已上传，请审核！", request);
        } else {
            this.message = "上传应急处置演练计划失败";
        }
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("result", rst);
        result.put("message", message);
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        log.debug(jsonstr);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 管理方下达网络优化任务消息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedTwo", method = RequestMethod.POST)
    public void checkedTwo(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note3 = request.getParameter("note3");
        String user = request.getParameter("user1");
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        EmergencyBean bean = new EmergencyBean();
        bean.setId(id);
        if(checked == 2){
            bean.setChecked(2);
        }else if(checked == 0) {
            bean.setChecked(0);

        }
		bean.setUser2(funUtil.loginUser(request));
        bean.setTime2(funUtil.nowDate());
        bean.setNote3(note3);
        int rst = EmergencyService.checkedTwo(bean);
        if (rst == 1) {
            this.message = "通知服务管理方处理成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知服务管理方处理(应急处置演练计划)，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            if(checked==2){
            	//----发送通知邮件
                sendNotifytoSingle(user, "应急处置演练计划已经审核通过", request);
                //----END
            }else{
            	//----发送通知邮件
                sendNotifytoSingle(user, "应急处置演练计划审核不通过,请重新上传演练计划", request);
                //----END
            }
        } else {
            this.message = "通知服务管理方处理应急处置演练任务消息失败";
        }
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("result", rst);
        result.put("message", message);
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        log.debug(jsonstr);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 服务提供方审核
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedThree", method = RequestMethod.POST)
    public void checkedThree(HttpServletRequest request,
                             HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        String note3 = request.getParameter("note3");
        String user = request.getParameter("userName");
        EmergencyBean bean = new EmergencyBean();
        bean.setId(id);
        bean.setChecked(3);
        bean.setFileName2(fileName);
        bean.setFilePath2(filePath);
        bean.setNote3(note3);
        bean.setTime3(funUtil.nowDate());
        System.out.println("方案报告消息:" + fileName);
        System.out.println("id是:" + id);

        int rst = EmergencyService.checkedThree(bean);
        if (rst == 1) {
            this.message = "上传演练报告成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("上传演练报告，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);
          //----发送通知邮件
            sendNotifytoSingle(user, "应急处置演练报告已经上传", request);
            //----END
        } else {
            this.message = "上传演练报告失败";
        }
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("result", rst);
        result.put("message", message);
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        log.debug(jsonstr);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 服务提供方上传方案审核消息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedFour", method = RequestMethod.POST)
    public void checkedFour(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        //String note3 = request.getParameter("note3");
        String user = request.getParameter("user1");
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        int level = funUtil.StringToInt(request.getParameter("level"));
        String note4 = request.getParameter("note4");
        EmergencyBean bean = new EmergencyBean();
        bean.setId(id);
        bean.setChecked(4);
        bean.setUser3(funUtil.loginUser(request));
        bean.setNote4(note4);
        bean.setTime4(funUtil.nowDate());
        bean.setLevel(level);
        int rst = EmergencyService.checkedFour(bean);
        String ll="";
        if (rst == 1) {
            this.message = "通知服务方处理方案评审成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知服务管理方处理(方案评审消息)，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);
            
            if(bean.getLevel()==1){
            	ll="优";
            }else if(bean.getLevel()==2){
            	ll="良";
            }else if(bean.getLevel()==3){
            	ll="中";
            }else if(bean.getLevel()==4){
            	ll="差";
            }
            //----发送通知邮件
            sendNotifytoSingle(user, "应急处置演练报告已经查阅  评级：（"+ll+"）", request);
            //----END
        } else {
            this.message = "通知服务方处理方案评审消息失败";
        }
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("result", rst);
        result.put("message", message);
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        log.debug(jsonstr);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 管理方审核方案审核消息
     *
     * @param request
     * @param response
     */
/*    @RequestMapping(value = "/checkedFive", method = RequestMethod.POST)
    public void checkedFive(HttpServletRequest request,
                             HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        //String note3 = request.getParameter("note3");
        String user = request.getParameter("user");
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        int level = funUtil.StringToInt(request.getParameter("level"));
        EmergencyBean bean = new EmergencyBean();
        bean.setId(id);
        if(checked == 5){
            bean.setChecked(5);
        }else if(checked == 3) {
            bean.setChecked(3);

        }
        bean.setUser3(funUtil.loginUser(request));
        bean.setTime4(funUtil.nowDate());
        bean.setLevel(level);
        int rst = EmergencyService.checkedFive(bean);
        if (rst == 1) {
            this.message = "通知服务管理方处理方案审核消息成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知服务管理方处理(方案审核消息)，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotify(user, "服务管理方请重新处理方案审核消息。。。", request);
            //----END
        } else {
            this.message = "通知服务管理方处理方案审核消息失败";
        }
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("result", rst);
        result.put("message", message);
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        log.debug(jsonstr);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
/*
    *//**
     * 服务提供方上传总结审核消息
     *
     * @param request
     * @param response
     *//*
    @RequestMapping(value = "/checkedSix", method = RequestMethod.POST)
    public void checkedSix(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        EmergencyBean bean = new EmergencyBean();
        bean.setId(id);
        bean.setChecked(6);
        bean.setFileName3(fileName);
        bean.setFilePath3(filePath);
        System.out.println("总结审核消息请求:" + fileName);

        int rst = EmergencyService.checkedSix(bean);
        if (rst == 1) {
            this.message = "上传总结审核消息成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("上传总结审核消息，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);
        } else {
            this.message = "上传总结审核消息失败";
        }
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("result", rst);
        result.put("message", message);
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        log.debug(jsonstr);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    *//**
     * 管理方审核总结审核消息
     *
     * @param request
     * @param response
     *//*
    @RequestMapping(value = "/checkedSeven", method = RequestMethod.POST)
    public void checkedSeven(HttpServletRequest request,
                             HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note4 = request.getParameter("note4");
        String user = request.getParameter("user");
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        int level = funUtil.StringToInt(request.getParameter("level"));
        EmergencyBean bean = new EmergencyBean();
        bean.setId(id);
        if(checked == 7){
            bean.setChecked(7);
        }else if(checked == 5) {
            bean.setChecked(5);

        }
        bean.setUser4(funUtil.loginUser(request));
        bean.setTime4(funUtil.nowDate());
        bean.setNote4(note4);
        int rst = EmergencyService.checkedSeven(bean);
        if (rst == 1) {
            this.message = "通知服务管理方处理成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知服务管理方处理(总结审核消息)，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotify(user, "服务管理方请重新处理总结审核消息。。。", request);
            //----END
        } else {
            this.message = "通知服务管理方处理总结审核消息失败";
        }
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("result", rst);
        result.put("message", message);
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        log.debug(jsonstr);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    /**
     * 上传文件
     * @param file
     * @param request
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam("filePath") MultipartFile file,
                       HttpServletRequest request,HttpServletResponse response) {
    	
    	// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
		String data = funUtil.MD5(sdf.format(d));
        String path = request.getSession().getServletContext()
                .getRealPath("")+"/Resources/upload/emergency";
        String fileName = file.getOriginalFilename();
        //String fileName = new Date().getTime()+".jpg";
        log.info("path==>"+path);
        log.info("fileName==>"+fileName);
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        // 保存
        try {
            file.transferTo(targetFile);
            this.success=true;
            this.message="文件上传成功";
            fileName=data+"-"+fileName;
			File rename = new File(path,fileName);
			targetFile.renameTo(rename);

        } catch (Exception e) {
            e.printStackTrace();
            this.message="文件上传失败";
        }

        HashMap result = new HashMap();
        result.put("success", success);
        result.put("message", message);
        result.put("fileName", fileName);
        result.put("filePath", "/Resources/upload/emergency/"+fileName);
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        log.debug(jsonstr);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
        int type = funUtil.StringToInt(request.getParameter("type"));
        String path = "";
        if(type == 3){
            path = request.getSession().getServletContext().getRealPath("/Resources/outputDoc");
        }
        else{
            path = request.getSession().getServletContext().getRealPath("/Resources/upload");
        }
        String fileName=request.getParameter("fileName");
        fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8");
        String downPath=path+"/"+fileName;
        log.info(downPath);
        System.out.println(downPath);
        File file = new File(downPath);
        if(!file.exists()){
            this.success=false;
            this.message="文件不存在";
        }
        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + DownLoadUtils.getName(request.getHeader("user-agent"), fileName));
        //用于记录以完成的下载的数据量，单位是byte
        long downloadedLength = 0l;
        try {
            //打开本地文件流
            InputStream inputStream = new FileInputStream(downPath);
            //激活下载操作
            OutputStream os = response.getOutputStream();

            //循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
                downloadedLength += b.length;
            }

            // 这里主要关闭。
            os.close();
            inputStream.close();
        } catch (Exception e){
            throw e;
        }
        //存储记录
    }

    /**
	 * 发送邮件(指定收件人)--入网申请
	 * 
	 * @param recvUser
	 *            邮件接收者
	 * @param content
	 *            邮件内容
	 * @param request
	 */
	public void sendNotifytoSingle(String recvUser, String content,
			HttpServletRequest request) {
		// ----发送通知邮件
		EmailBean emailBean = new EmailBean();
		emailBean.setTitle("应急处置演练");
		emailBean.setRecvUser(recvUser);
		emailBean.setSendUser(funUtil.loginUser(request));
		emailBean.setContent(content);
		emailBean.setTime(funUtil.nowDate());
		EmailService.insertEmail(emailBean);
		// ----END
	}

	/**
	 * 发送邮件(指定权限)--入网申请
	 * 
	 * @param recvUser
	 *            邮件接收者
	 * @param content
	 *            邮件内容
	 * @param request
	 */
	public void sendNotifytoGroup(String powerstr, int roleId, String content,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("powerstr", powerstr);
		map.put("roleId", roleId);
		List<Map<String, Object>> items = WebUserServices
				.userlistByPowerAndRoleId(map);
		log.info("邮件发送：" + items);
		for (Map<String, Object> item : items) {
			// ----发送通知邮件
			EmailBean emailBean = new EmailBean();
			emailBean.setTitle("应急处置演练");
			emailBean.setRecvUser(item.get("user").toString());
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent(content);
			emailBean.setTime(funUtil.nowDate());
			EmailService.insertEmail(emailBean);
			// ----END
		}
	}

}
