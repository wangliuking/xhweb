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
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.EmergencyBean;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.QualityCheckBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.EmergencyService;
import xh.mybatis.service.QualityCheckService;
import xh.mybatis.service.WebLogService;
import xh.mybatis.service.WebUserServices;

@Controller
@RequestMapping(value = "/qualitycheck")
public class QualityCheckController {

    private boolean success;
    private String message;
    private FunUtil funUtil = new FunUtil();
    protected final Log log = LogFactory.getLog(QualityCheckController.class);
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
        result.put("items", QualityCheckService.selectAll(map));
        result.put("totals", QualityCheckService.dataCount(map));
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
        result.put("items", QualityCheckService.applyProgress(id));
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
    @RequestMapping(value = "/insertQualityCheck", method = RequestMethod.POST)
    public void insertQualityCheck(HttpServletRequest request,
                        HttpServletResponse response) {
        this.success = true;
        //String jsonData = request.getParameter("formData");
        String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("path");
		String applicant = request.getParameter("applicant");
		String tel = request.getParameter("tel");
		
        //QualityCheckBean bean = GsonUtil.json2Object(jsonData, QualityCheckBean.class);
        //bean.setUserName(funUtil.loginUser(request));
        //bean.setRequestTime(funUtil.nowDate());
        //log.info("data==>" + bean.toString());
		QualityCheckBean bean = new QualityCheckBean();
		bean.setRequestTime(funUtil.nowDate());
		bean.setTel(tel);
		bean.setUserName(funUtil.loginUser(request));
		bean.setChecked(0);
		bean.setApplicant(applicant);
		bean.setFileName1(fileName);
		bean.setFilePath1(filePath);
        System.out.println("+++++++++++++++++"+filePath);
        System.out.println("+++++++++++++++++"+fileName);
        int rst = QualityCheckService.insertQualityCheck(bean);
        System.out.println(rst);
        WebLogBean webLogBean = new WebLogBean();
        if (rst == 1) {
            this.message = "运维质量抽检申请信息已经成功提交";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(1);
            webLogBean.setContent("运维质量抽检申请信息，data=" );
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            //sendNotify(bean.getUser_MainManager(), "运维质量抽检申请信息已经成功提交,请审核。。。", request);
            //----END
        } else {
            this.message = "运维质量抽检申请信息提交失败";
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
        String note1 = request.getParameter("note1");
        String user = request.getParameter("user");
        QualityCheckBean bean = new QualityCheckBean();
        bean.setId(id);
        if(checked ==1) {
            bean.setChecked(1);
        }else if(checked == -1) {
            bean.setChecked(-1);
        }

        bean.setUser1(funUtil.loginUser(request));
        bean.setTime1(funUtil.nowDate());
        bean.setNote1(note1);
        int rst = QualityCheckService.checkedOne(bean);
        if (rst == 1) {
            this.message = "审核提交成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("审核抽查申请信息，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotify(user, "运维质量抽查申请信息审核，请服务提供方方人员审核并尽快处理", request);
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
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        EmergencyBean bean = new EmergencyBean();
        bean.setId(id);
        bean.setChecked(2);
        bean.setFileName1(fileName);
        bean.setFilePath1(filePath);
        System.out.println("应急处置演练任务消息:" + fileName);

        int rst = EmergencyService.checkedTwo(bean);
        if (rst == 1) {
            this.message = "上传抽查记录成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("上传抽查记录，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);
        } else {
            this.message = "上传抽查结果失败";
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
        String note2 = request.getParameter("note2");
        String user = request.getParameter("user");
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        QualityCheckBean bean = new QualityCheckBean();
        bean.setId(id);
        if(checked == 3){
            bean.setChecked(3);
        }else if(checked == 1) {
            bean.setChecked(1);

        }
		bean.setUser2(funUtil.loginUser(request));
        bean.setTime2(funUtil.nowDate());
        bean.setNote2(note2);
        int rst = QualityCheckService.checkedThree(bean);
        if (rst == 1) {
            this.message = "通知服务管理方处理成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("通知服务管理方处理(运维质量抽查任务消息)，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);

            //----发送通知邮件
            sendNotify(user, "服务管理方请重新处理运维质量抽查任务消息。。。", request);
            //----END
        } else {
            this.message = "通知服务管理方处理运维质量抽查任务消息失败";
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
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        QualityCheckBean bean = new QualityCheckBean();
        bean.setId(id);
        bean.setChecked(4);
        bean.setFileName2(fileName);
        bean.setFilePath2(filePath);
        System.out.println("方案审核消息:" + fileName);

        int rst = QualityCheckService.checkedFour(bean);
        if (rst == 1) {
            this.message = "上传方案审核消息成功";
            webLogBean.setOperator(funUtil.loginUser(request));
            webLogBean.setOperatorIp(funUtil.getIpAddr(request));
            webLogBean.setStyle(5);
            webLogBean.setContent("上传方案审核消息，data=" + bean.toString());
            WebLogService.writeLog(webLogBean);
        } else {
            this.message = "上传方案审核消息失败";
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
    @RequestMapping(value = "/checkedFive", method = RequestMethod.POST)
    public void checkedFive(HttpServletRequest request,
                             HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note3 = request.getParameter("note3");
        String user = request.getParameter("user");
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        QualityCheckBean bean = new QualityCheckBean();
        bean.setId(id);
        if(checked == 5){
            bean.setChecked(5);
        }else if(checked == 3) {
            bean.setChecked(3);

        }
        bean.setUser3(funUtil.loginUser(request));
        bean.setTime3(funUtil.nowDate());
        bean.setNote3(note3);
        int rst = QualityCheckService.checkedFive(bean);
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
        String path = "";
        path = request.getSession().getServletContext().getRealPath("/Resources/upload");
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
        emailBean.setTitle("运维质量抽查");
        emailBean.setRecvUser(recvUser);
        emailBean.setSendUser(funUtil.loginUser(request));
        emailBean.setContent(content);
        emailBean.setTime(funUtil.nowDate());
        EmailService.insertEmail(emailBean);
        //----END
    }

}
