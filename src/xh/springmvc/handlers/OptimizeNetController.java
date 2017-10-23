package xh.springmvc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
@RequestMapping(value = "/optimizenet")
public class OptimizeNetController {

    private boolean success;
    private String message;
    private FunUtil funUtil = new FunUtil();
    protected final Log log = LogFactory.getLog(OptimizeNetController.class);
    private FlexJSON json = new FlexJSON();
    private WebLogBean webLogBean = new WebLogBean();
    private String unit;
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
        //unit = WebUserServices.selectUnitByUser(user);
        WebUserBean userbean=WebUserServices.selectUserByUser(user);
        int roleId=userbean.getRoleId();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("limit", limit);
        map.put("user", user);
        map.put("roleId", roleId);

        HashMap result = new HashMap();
        result.put("success", success);
        result.put("items", OptimizeNetService.selectAll(map));
        result.put("totals", OptimizeNetService.dataCount(map));
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
        result.put("items", OptimizeNetService.applyProgress(id));
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
    @RequestMapping(value = "/insertOptimizeNet", method = RequestMethod.POST)
    public void insertOptimizeNet(HttpServletRequest request,
                        HttpServletResponse response) {
        this.success = true;
        String jsonData = request.getParameter("formData");
//        String note1 = request.getParameter("note1");
//        String userUnit = request.getParameter("userUnit");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        OptimizeNetBean bean = GsonUtil.json2Object(jsonData, OptimizeNetBean.class);
        bean.setFileName1(fileName);
        bean.setFilePath1(filePath);
        bean.setUserName(funUtil.loginUser(request));
        bean.setRequestTime(funUtil.nowDate());
//        bean.setNote1(note1);
//        bean.setUserUnit(userUnit);
        log.info("data==>" + bean.toString());
        int rst = OptimizeNetService.insertOptimizeNet(bean);
        WebLogBean webLogBean = new WebLogBean();
        if (rst == 1) {
            this.message = "网络优化申请信息已经成功提交";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(1);
            webLogBean.setContent("网络优化申请信息，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);
//            //----发送通知邮件
//            sendNotify(bean.getUser_MainManager(), "网络优化申请信息已经成功提交,请审核。。。", request);
//            //----END
        } else {
            this.message = "网络优化申请信息提交失败";
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
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        String note2 = request.getParameter("note2");
        String user = request.getParameter("user");
        OptimizeNetBean bean = new OptimizeNetBean();
        bean.setId(id);
        if(checked ==1) {
            bean.setChecked(1);
        }else if(checked == -1) {
            bean.setChecked(-1);
        }
        bean.setUser2(funUtil.loginUser(request));
        bean.setTime2(funUtil.nowDate());
        bean.setNote2(note2);
        int rst = OptimizeNetService.checkedOne(bean);
        if (rst == 1) {
            this.message = "审核提交成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("审核网络优化信息，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotify(user, "网络优化信息审核，请服务提供方方人员审核并尽快处理", request);
            //----EN
        }
        log.info("data==>" + bean.toString());

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
     * 管理方上传方案总结消息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedTwo", method = RequestMethod.POST)
    public void checkedTwo(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        OptimizeNetBean bean = new OptimizeNetBean();
        bean.setId(id);
        bean.setChecked(2);
        bean.setFileName2(fileName);
        bean.setFilePath2(filePath);
        System.out.println("方案总结消息消息:" + fileName);

        int rst = OptimizeNetService.checkedTwo(bean);
        if (rst == 1) {
            this.message = "上传方案总结消息成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("上传网络优化任务消息，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);
        } else {
            this.message = "上传方案总结消息失败";
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
     * 服务提供方审核方案总结消息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedThree", method = RequestMethod.POST)
    public void checkedThree(HttpServletRequest request,
                             HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note3 = request.getParameter("note3");
        String user = request.getParameter("user");
        int drop = funUtil.StringToInt(request.getParameter("drop"));
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        OptimizeNetBean bean = new OptimizeNetBean();
        bean.setId(id);
        bean.setDrop(drop);
        if(checked == 3 && drop != 1){
            bean.setChecked(3);
        }else if(drop == 1) {
            bean.setChecked(-2);
        }else if(checked ==1){
            bean.setChecked(1);
        }
		bean.setUser3(funUtil.loginUser(request));
        bean.setTime3(funUtil.nowDate());
        bean.setNote3(note3);
        int rst = OptimizeNetService.checkedThree(bean);
        if (rst == 1) {
            this.message = "通知服务管理方处理成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知服务管理方处理(方案总结消息)，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotify(user, "服务管理方请重新处理方案总结消息。。。", request);
            //----END
        } else {
            this.message = "通知服务管理方处理方案总结消息失败";
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
     * 服务提供方上传总结审核消息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedFour", method = RequestMethod.POST)
    public void checkedFour(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        OptimizeNetBean bean = new OptimizeNetBean();
        bean.setId(id);
        bean.setChecked(4);
        bean.setFileName3(fileName);
        bean.setFilePath3(filePath);
        System.out.println("总结审核消息:" + fileName);

        int rst = OptimizeNetService.checkedFour(bean);
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
    /**
     * 管理方审核总结审核消息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedFive", method = RequestMethod.POST)
    public void checkedFive(HttpServletRequest request,
                             HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note4 = request.getParameter("note4");
        String user = request.getParameter("user");
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        OptimizeNetBean bean = new OptimizeNetBean();
        bean.setId(id);
        if(checked == 5){
            bean.setChecked(5);
        }else if(checked == 3) {
            bean.setChecked(3);
        }
        bean.setUser4(funUtil.loginUser(request));
        bean.setTime4(funUtil.nowDate());
        bean.setNote4(note4);
        int rst = OptimizeNetService.checkedFive(bean);
        if (rst == 1) {
            this.message = "通知服务管理方处理总结审核消息成功";
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
    }

    /**
     * 上传文件
     * @param file
     * @param request
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam("filePath") MultipartFile file,
                       HttpServletRequest request,HttpServletResponse response) {
        String path = request.getSession().getServletContext()
                .getRealPath("")+"/Resources/upload";
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

        } catch (Exception e) {
            e.printStackTrace();
            this.message="文件上传失败";
        }

        HashMap result = new HashMap();
        result.put("success", success);
        result.put("message", message);
        result.put("fileName", fileName);
        result.put("filePath", path+"/"+fileName);
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
     * 发送邮件
     * @param recvUser	邮件接收者
     * @param content	邮件内容
     * @param request
     */
    public void sendNotify(String recvUser,String content,HttpServletRequest request){
        //----发送通知邮件
        EmailBean emailBean = new EmailBean();
        emailBean.setTitle("网络优化");
        emailBean.setRecvUser(recvUser);
        emailBean.setSendUser(funUtil.loginUser(request));
        emailBean.setContent(content);
        emailBean.setTime(funUtil.nowDate());
        EmailService.insertEmail(emailBean);
        //----END
    }

}
