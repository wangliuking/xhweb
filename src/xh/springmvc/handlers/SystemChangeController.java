package xh.springmvc.handlers;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xh.func.plugin.DownLoadUtils;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.SystemChangeBean;
import xh.mybatis.bean.SystemChangeSheet;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.SystemChangeService;
import xh.mybatis.service.WebUserServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/systemChange")
public class SystemChangeController {

    private boolean success;
    private String message;
    private FunUtil funUtil = new FunUtil();
    protected final Log log = LogFactory.getLog(SystemChangeController.class);
    private FlexJSON json = new FlexJSON();
    //private WebLogBean webLogBean = new WebLogBean();
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
        result.put("items", SystemChangeService.selectAll(map));
        result.put("totals", SystemChangeService.dataCount(map));
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
        result.put("items", SystemChangeService.applyProgress(id));
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
     * 显示系统升级表格
     */
    @RequestMapping(value="/sheetShow",method = RequestMethod.GET)
    public void sheetShow(HttpServletRequest request,HttpServletResponse response){
        this.success = true;
        int id = Integer.parseInt(request.getParameter("id"));
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        HashMap result = new HashMap();
        result.put("success",success);
        result.put("items",SystemChangeService.sheetShow(map));
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 修改系统升级表格
     */
    @RequestMapping(value = "/sheetChange",method = RequestMethod.POST)
    @ResponseBody
    public void sheetChange(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "bean") String beanStr){
        System.out.println(" beanStr : "+beanStr);
        //将json字符串转换成json对象
        JSONObject jsonobject = JSONObject.fromObject(beanStr);
        //将json对象转换成User实体对象
        SystemChangeSheet bean = (SystemChangeSheet)JSONObject.toBean(jsonobject, SystemChangeSheet.class);
        this.success = true;
        System.out.println(" bean : "+bean);
        int res = SystemChangeService.sheetChange(bean);
        this.message = "保存成功";
        HashMap result = new HashMap();
        result.put("message",message);
        result.put("success",success);
        result.put("result",res);
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 发起应急演练
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/insertSystemChange", method = RequestMethod.POST)
    public void insertSystemChange(HttpServletRequest request,
                        HttpServletResponse response) {
        this.success = true;
        String userUnit = request.getParameter("userUnit");
        String note1 = request.getParameter("note1");
        String tel = request.getParameter("tel");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setUserUnit(userUnit);
        bean.setTel(tel);
        bean.setFileName1(fileName);
        bean.setFilePath1(filePath);
        bean.setNote1(note1);
        bean.setUserName(funUtil.loginUser(request));
        bean.setRequestTime(FunUtil.nowDate());

        log.info("data==>" + bean.toString());
        int rst = SystemChangeService.insertSystemChange(bean);

        if (rst == 1) {
            this.message = "应急演练申请信息已经成功提交";
            //----发送通知邮件
           sendNotifytoGroup("o_check_changeSystem",10003, "网络优化任务下达", request);
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
     *审核系统升级方案
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
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(checked);
        bean.setUser2(funUtil.loginUser(request));
        bean.setTime2(FunUtil.nowDate());
        bean.setNote2(note2);
        int res = SystemChangeService.checkedOne(bean);
        this.message = "项目经理已审核";
        /*if (rst == 1) {
            this.message = "审核提交成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("创建演练组，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotifytoSingle(user, "服务提供方已收到信息，我们将尽快上传优化方案", request);
            //----EN
        }*/

        HashMap result = new HashMap();
        result.put("success", success);
        result.put("result", res);
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
     * 管理方审核系统升级方案
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedTwo", method = RequestMethod.POST)
    public void checkedTwo(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        String note3 = request.getParameter("note3");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(checked);
        bean.setUser3(funUtil.loginUser(request));
        bean.setTime3(FunUtil.nowDate());
        bean.setNote3(note3);

        int rst = SystemChangeService.checkedTwo(bean);
        this.message = "管理方已审核";
        /*if (rst == 1) {
            this.message = "上传优化方案成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知演练组进行演练，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);
            //----发送通知邮件
            //sendNotifytoSingle(user, "请审核优化整改方案", request);
            //----EN
        } else {
            this.message = "上传优化方案失败";
        }*/
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
     * 创建实施组然后更新状态
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedThree", method = RequestMethod.POST)
    public void checkedThree(HttpServletRequest request,
                             HttpServletResponse response) {
        this.success = true;
        //创建实施组
        String ImplId = request.getParameter("ImplId");
        String ImplName = request.getParameter("ImplName");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("roleId",ImplId);
        map.put("role",ImplName);
        map.put("roleType",3);
        map.put("createTime",FunUtil.nowDate());

        int res = SystemChangeService.createSystemGroup(map);
        int rst = 0;
        if(res>0){
            int id = funUtil.StringToInt(request.getParameter("id"));
            String note4 = request.getParameter("note4");
            SystemChangeBean bean = new SystemChangeBean();
            bean.setId(id);
            bean.setChecked(3);
            bean.setUser4(funUtil.loginUser(request));
            bean.setTime4(FunUtil.nowDate());
            bean.setNote4(note4);
            bean.setImplId(ImplId);

            rst = SystemChangeService.checkedThree(bean);
            this.message ="已创建"+ImplId+ "组！";
        }

        /*if (rst == 1) {
            this.message = "优化方案审核成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("优化方案审核，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);
            //----发送通知邮件
            sendNotifytoSingle(user, "优化方案已经由管理方审批", request);
        } else {
            this.message = "审核优化方案失败";
        }*/
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
     * 执行系统升级
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedFour", method = RequestMethod.POST)
    public void checkedFour(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        String ExcImplId = request.getParameter("ExcImplId");
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note5 = request.getParameter("note5");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(4);
        bean.setUser5(funUtil.loginUser(request));
        bean.setTime5(FunUtil.nowDate());
        bean.setNote5(note5);
        bean.setExcImplId(ExcImplId);

        int rst = SystemChangeService.checkedFour(bean);
        /*if (rst == 1) {
            this.message = "上传总结报告成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("上传了总结报告，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);
            //----发送通知邮件通知抢修组
            //sendNotifytoGroup("o_check_changesystem",repairTeamId, "通知抢修组进行整改", request);
            //----END
        } else {
            this.message = "上传总结报告失败";
        }*/
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
     * 反馈升级结果
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedFive", method = RequestMethod.POST)
    public void checkedFive(HttpServletRequest request,
                             HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        String note6 = request.getParameter("note6");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(checked);
        bean.setUser6(funUtil.loginUser(request));
        bean.setTime6(FunUtil.nowDate());
        bean.setNote6(note6);

        int rst = SystemChangeService.checkedFive(bean);
        /*if (rst == 1) {
            this.message = "总结报告审核成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知服务管理方处理(总结审核消息)，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotifytoSingle(user, "总结报告已审核", request);
            //----END
        } else {
            this.message = "总结报告审核失败";
        }*/
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
     * 提交工作记录
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedSix", method = RequestMethod.POST)
    public void checkedSix(HttpServletRequest request,
                            HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note7 = request.getParameter("note7");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(6);
        bean.setUser7(funUtil.loginUser(request));
        bean.setTime7(FunUtil.nowDate());
        bean.setNote7(note7);
        bean.setFileName2(fileName);
        bean.setFilePath2(filePath);

        int rst = SystemChangeService.checkedSix(bean);
        /*if (rst == 1) {
            this.message = "总结报告审核成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知服务管理方处理(总结审核消息)，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotifytoSingle(user, "总结报告已审核", request);
            //----END
        } else {
            this.message = "总结报告审核失败";
        }*/
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
     * 向管理方反馈结果
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedSenven", method = RequestMethod.POST)
    public void checkedSenven(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note8 = request.getParameter("note8");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(7);
        bean.setUser8(funUtil.loginUser(request));
        bean.setTime8(FunUtil.nowDate());
        bean.setNote8(note8);
        bean.setFileName3(fileName);
        bean.setFilePath3(filePath);

        int rst = SystemChangeService.checkedSenven(bean);
        /*if (rst == 1) {
            this.message = "总结报告审核成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知服务管理方处理(总结审核消息)，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotifytoSingle(user, "总结报告已审核", request);
            //----END
        } else {
            this.message = "总结报告审核失败";
        }*/
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
     * 重新拟制升级方案
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedNegOne", method = RequestMethod.POST)
    public void checkedNegOne(HttpServletRequest request,
                              HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note1 = request.getParameter("note1");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(0);
        bean.setUser1(funUtil.loginUser(request));
        bean.setTime1(FunUtil.nowDate());
        bean.setNote1(note1);
        bean.setFileName1(fileName);
        bean.setFilePath1(filePath);

        int rst = SystemChangeService.checkedNegOne(bean);
        /*if (rst == 1) {
            this.message = "总结报告审核成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知服务管理方处理(总结审核消息)，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotifytoSingle(user, "总结报告已审核", request);
            //----END
        } else {
            this.message = "总结报告审核失败";
        }*/
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
     * 执行回退操作
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedNegFour", method = RequestMethod.POST)
    public void checkedNegFour(HttpServletRequest request,
                              HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        String note9 = request.getParameter("note9");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(checked);
        bean.setUser9(funUtil.loginUser(request));
        bean.setTime9(FunUtil.nowDate());
        bean.setNote9(note9);

        int rst = SystemChangeService.checkedNegFour(bean);
        /*if (rst == 1) {
            this.message = "总结报告审核成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知服务管理方处理(总结审核消息)，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotifytoSingle(user, "总结报告已审核", request);
            //----END
        } else {
            this.message = "总结报告审核失败";
        }*/
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
    	

		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
		String data = funUtil.MD5(sdf.format(d));
        String path = request.getSession().getServletContext()
                .getRealPath("")+"/Resources/upload/systemChange";
        String fileName = file.getOriginalFilename();
        //String fileName = new Date().getTime()+".jpg";
        log.info("path==>"+path);
        log.info("fileName==>"+fileName);;
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
        result.put("filePath", "/Resources/upload/systemChange/"+fileName);
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
        int type = FunUtil.StringToInt(request.getParameter("type"));
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
	 * 发送邮件(指定收件人)--优化整改
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
		emailBean.setTitle("优化整改申请");
		emailBean.setRecvUser(recvUser);
		emailBean.setSendUser(funUtil.loginUser(request));
		emailBean.setContent(content);
		emailBean.setTime(FunUtil.nowDate());
		EmailService.insertEmail(emailBean);
		// ----END
	}

	/**
	 * 发送邮件(指定权限)--优化整改
	 * 
	 * @param
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
			emailBean.setTitle("系统升级");
			emailBean.setRecvUser(item.get("user").toString());
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent(content);
			emailBean.setTime(FunUtil.nowDate());
			EmailService.insertEmail(emailBean);
			// ----END
		}
	}
}
