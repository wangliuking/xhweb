package xh.springmvc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;

import net.sf.json.JSONObject;
import xh.func.plugin.DownLoadUtils;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FormToWord;
import xh.func.plugin.FunUtil;
import xh.func.plugin.GsonUtil;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.OrderManageBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.OrderManageService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;

//故障处理流程
@Controller
@RequestMapping(value = "/ordermanage")
public class OrderManageController {
    private boolean success;
    private String message;
    private FunUtil funUtil = new FunUtil();
    protected final Log log = LogFactory.getLog(OrderManageController.class);
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
        result.put("items", OrderManageService.selectAll(map));
        result.put("totals", OrderManageService.dataCount(map));
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /*
    *  故障处理进度
    * */
    @RequestMapping(value = "/applyProgress", method = RequestMethod.GET)
    public void applyProgress(HttpServletRequest request,
                              HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("items", OrderManageService.applyProgress(id));
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
     * 故障记录入库
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/insertOrderManage", method = RequestMethod.POST)
    public void insertOrderManage(HttpServletRequest request,
                          HttpServletResponse response) {
        this.success = true;
        String jsonData = request.getParameter("formData");

        OrderManageBean bean = GsonUtil.json2Object(jsonData, OrderManageBean.class);
        bean.setUserName(funUtil.loginUser(request));
        log.info("data==>" + bean.toString());

        int rst = OrderManageService.insertOrderManage(bean);
        if (rst == 1) {
            this.message = "故障入库信息已经成功提交";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(1);
            webLogBean.setContent("故障入库信息，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotify(bean.getUser_MainManager(), "故障入库信息已经成功提交,请审核。。。", request);
            //----END
        } else {
            this.message = "故障入库信息提交失败";
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
     *
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
        String result1 = request.getParameter("result");
        String note1 = request.getParameter("note1");
        String user = request.getParameter("user");
        OrderManageBean bean = new OrderManageBean();
        bean.setId(id);
        if(checked ==1 && result1.equals("成功")){
            bean.setChecked(-2);
            bean.setResult("值班人员发现故障后修复");
        }else if(checked == 1 && result1.equals("失败")){
            bean.setChecked(1);
            bean.setResult("值班人员未能修复");
        }else if(checked == -1){
            bean.setChecked(-1);
        }
        bean.setUser1(funUtil.loginUser(request));
        bean.setNote1(note1);
        bean.setTime1(funUtil.nowDate());
//        bean.setUser1(funUtil.loginUser(request));
//        bean.setTime1(funUtil.nowDate());
//        bean.setNote1(note1);
        log.info("data==>" + bean.toString());

        int rst = OrderManageService.checkedOne(bean);
        if (rst == 1) {
            this.message = "审核提交成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("审核故障处理，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotify(user, "审核故障处理，请值班人员审核并移交维护工程师。。。", request);
            //----END
        } else {
            this.message = "审核提交失败";
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
     *
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedTwo", method = RequestMethod.POST)
    public void checkedTwo(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note2 = request.getParameter("note2");
        String user = request.getParameter("user");
        String need_contact = request.getParameter("need_contact");
        OrderManageBean bean = new OrderManageBean();
        bean.setId(id);
        if(need_contact.equals("需要")){
            bean.setChecked(2);
            bean.setResult("维护工程师未能解决故障");
        }else if(need_contact.equals("不需要")){
            bean.setChecked(-3);
            bean.setResult("维护工程师赶赴现场完成修复");
        }
        bean.setNeed_contact(need_contact);
        bean.setUser2(funUtil.loginUser(request));
        bean.setNote2(note2);
        bean.setTime2(funUtil.nowDate());
//        bean.setUser2(funUtil.loginUser(request));
//        bean.setUser3(user3);
//        bean.setTime2(funUtil.nowDate());
//        bean.setNote2(note2);

        int rst = OrderManageService.checkedTwo(bean);
        if (rst == 1) {
            this.message = "维护工程师处理成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知维护工程师处理，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotify(user, "。。。", request);
            //----END
        } else {
            this.message = "通知维护工程师处理失败";
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
     * 管理方审核
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedThree", method = RequestMethod.POST)
    public void checkedThree(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note3 = request.getParameter("note2");
        String user = request.getParameter("user");
        OrderManageBean bean = new OrderManageBean();
        bean.setId(id);
        bean.setResult("运维负责人已通知厂家修复故障");
        bean.setChecked(3);
        bean.setNote3(note3);
        bean.setTime3(funUtil.nowDate());
//        bean.setUser2(funUtil.loginUser(request));
//        bean.setUser3(user3);
//        bean.setTime2(funUtil.nowDate());
//        bean.setNote2(note2);

        int rst = OrderManageService.checkedTwo(bean);
        if (rst == 1) {
            this.message = "通知运维负责人处理成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知运维负责人处理，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotify(user, "。。。", request);
            //----END
        } else {
            this.message = "通知运维负责人处理失败";
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
     * 确认
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sureFile", method = RequestMethod.POST)
    public void sureFile(HttpServletRequest request,
                         HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note4 = request.getParameter("note4");
        OrderManageBean bean = new OrderManageBean();
        bean.setId(id);
        //bean.setTime5(funUtil.nowDate());
        bean.setChecked(4);
        bean.setNote4(note4);
        bean.setTime4(funUtil.nowDate());
        int rst = OrderManageService.sureFile(bean);
        if (rst == 1) {
            this.message = "确认成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("确认，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            //sendNotify(bean.getUser_MainManager(), "已经成功提交,请审核。。。", request);
            //----END
        } else {
            this.message = "确认失败";
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
     * 发送邮件--工单管理--故障处理
     * @param recvUser	邮件接收者
     * @param content	邮件内容
     * @param request
     */
    public void sendNotify(String recvUser,String content,HttpServletRequest request){
        //----发送通知邮件
        EmailBean emailBean = new EmailBean();
        emailBean.setTitle("工单-故障处理");
        emailBean.setRecvUser(recvUser);
        emailBean.setSendUser(funUtil.loginUser(request));
        emailBean.setContent(content);
        emailBean.setTime(funUtil.nowDate());
        EmailService.insertEmail(emailBean);
        //----END
    }

}
