package xh.springmvc.handlers;

import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import xh.func.plugin.DownLoadUtils;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.EmailBean;
import xh.mybatis.bean.EmergencyChangeBean;
import xh.mybatis.bean.WebLogBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.EmailService;
import xh.mybatis.service.EmergencyChangeService;
import xh.mybatis.service.EmergencyService;
import xh.mybatis.service.WebUserServices;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/emergencyChange")
public class EmergencyChangeController {

    private boolean success;
    private String message;
    private FunUtil funUtil = new FunUtil();
    protected final Log log = LogFactory.getLog(EmergencyChangeController.class);
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
        result.put("items", EmergencyChangeService.selectAll(map));
        result.put("totals", EmergencyChangeService.dataCount(map));
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
        result.put("items", EmergencyChangeService.applyProgress(id));
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
     * 发起应急演练
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/insertemergencyChange", method = RequestMethod.POST)
    public void insertemergencyChange(HttpServletRequest request,
                        HttpServletResponse response) {
        this.success = true;
        String userUnit = request.getParameter("userUnit");
        String note1 = request.getParameter("note1");
        String tel = request.getParameter("tel");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        EmergencyChangeBean bean = new EmergencyChangeBean();
        bean.setUserUnit(userUnit);
        bean.setTel(tel);
        bean.setFileName1(fileName);
        bean.setFilePath1(filePath);
        bean.setNote1(note1);
        bean.setUserName(funUtil.loginUser(request));
        bean.setRequestTime(FunUtil.nowDate());

        log.info("data==>" + bean.toString());
        int rst = EmergencyChangeService.insertemergencyChange(bean);

        if (rst == 1) {
            //通知运维负责人已上传应急演练方案
            FunUtil.sendMsgToUserByPower("o_check_emergency",3,"应急演练","已经上传应急演练方案，请查阅！",request);
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
     *创建演练组
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedOne", method = RequestMethod.POST)
    public void checkedOne(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        String emergencyGroupId = request.getParameter("emergencyGroupId");
        String emergencyGroupName = request.getParameter("emergencyGroupName");
        /*Map<String,Object> map = new HashMap<String,Object>();
        map.put("roleId",emergencyGroupId);
        map.put("role",emergencyGroupName);
        map.put("roleType",3);
        map.put("createTime",FunUtil.nowDate());*/

        //int res = EmergencyChangeService.createEmergencyGroup(map);

        WebRoleController webRoleController = new WebRoleController();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("role",emergencyGroupName);
        param.put("roleId",emergencyGroupId);
        param.put("roleType",3);
        param.put("parentRoleId","10003");
        param.put("loginUser",funUtil.loginUser(request));
        param.put("loginIp",funUtil.getIpAddr(request));
        Map<String,Object> resultObj = webRoleController.CreateRole(param);
        int res = 0;
        if("添加角色成功".equals(resultObj.get("message"))){
            res = 1;
            //为演练组增加临时权限
            EmergencyChangeService.insertDefaultPower(param);
        }
        if(res>0){
            int id = funUtil.StringToInt(request.getParameter("id"));
            //int checked = funUtil.StringToInt(request.getParameter("checked"));
            String note2 = request.getParameter("note2");
            EmergencyChangeBean bean = new EmergencyChangeBean();
            bean.setId(id);
            bean.setChecked(1);
            bean.setUser2(funUtil.loginUser(request));
            bean.setTime2(FunUtil.nowDate());
            bean.setNote2(note2);
            bean.setEmergencyGroupId(emergencyGroupId);
            EmergencyChangeService.checkedOne(bean);
            this.message = "创建演练组成功";
        }
        if (res == 1) {
            //通知运维负责人已创建演练组
            FunUtil.sendMsgToUserByPower("o_check_emergency",3,"应急演练","已经成功创建了演练组",request);
        }

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
     * 通知演练组进行演练
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedTwo", method = RequestMethod.POST)
    public void checkedTwo(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String prepareEmergencyGroupId = request.getParameter("prepareEmergencyGroupId");
        String note3 = request.getParameter("note3");
        EmergencyChangeBean bean = new EmergencyChangeBean();
        bean.setId(id);
        bean.setChecked(2);
        bean.setUser3(funUtil.loginUser(request));
        bean.setTime3(FunUtil.nowDate());
        bean.setNote3(note3);
        bean.setPrepareEmergencyGroupId(prepareEmergencyGroupId);

        int rst = EmergencyChangeService.checkedTwo(bean);
        this.message = "已通知"+prepareEmergencyGroupId+"演练组准备演练";
        if (rst == 1) {
            //通知演练组进行演练
            FunUtil.sendMsgToUserByGroupPower("r_emergency",3,"应急演练",prepareEmergencyGroupId+"演练组准备进行应急演练",request);
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
     * 实施演练
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedThree", method = RequestMethod.POST)
    public void checkedThree(HttpServletRequest request,
                             HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note4 = request.getParameter("note4");
        String preparedEmergencyGroupId = request.getParameter("preparedEmergencyGroupId");
        EmergencyChangeBean bean = new EmergencyChangeBean();
        bean.setId(id);
        bean.setChecked(3);
        bean.setUser4(funUtil.loginUser(request));
        bean.setTime4(FunUtil.nowDate());
        bean.setNote4(note4);
        bean.setPreparedEmergencyGroupId(preparedEmergencyGroupId);

        int rst = EmergencyChangeService.checkedThree(bean);
        this.message =preparedEmergencyGroupId+ "组准备开始演练！";
        if (rst == 1) {
            //通知运维负责人演练正在进行
            FunUtil.sendMsgToUserByPower("o_check_emergency",3,"应急演练","演练正在进行中",request);
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
     * 提交工作总结
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedFour", method = RequestMethod.POST)
    public void checkedFour(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));

        String note5 = request.getParameter("note5");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        EmergencyChangeBean bean = new EmergencyChangeBean();
        bean.setId(id);
        bean.setChecked(4);
        bean.setFileName2(fileName);
        bean.setFilePath2(filePath);
        bean.setUser5(funUtil.loginUser(request));
        bean.setTime5(FunUtil.nowDate());
        bean.setNote5(note5);

        int rst = EmergencyChangeService.checkedFour(bean);
        if (rst == 1) {
            //演练组通知运维负责人查阅工作总结
            FunUtil.sendMsgToUserByPower("o_check_emergency",3,"应急演练","应急演练工作总结已提交，请查阅！",request);
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
     * 向管理方发起报告
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedFive", method = RequestMethod.POST)
    public void checkedFive(HttpServletRequest request,
                             HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note6 = request.getParameter("note6");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        EmergencyChangeBean bean = new EmergencyChangeBean();
        bean.setId(id);
        bean.setChecked(5);
        bean.setFileName3(fileName);
        bean.setFilePath3(filePath);
        bean.setUser6(funUtil.loginUser(request));
        bean.setTime6(FunUtil.nowDate());
        bean.setNote6(note6);

        int rst = EmergencyChangeService.checkedFive(bean);
        if (rst == 1) {
            //通知管理方接受应急演练总结报告
            FunUtil.sendMsgToUserByPower("o_check_emergency",2,"应急演练","应急演练工作总结已提交，请查阅！",request);
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
    	

		// 获取当前时间
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
		String data = funUtil.MD5(sdf.format(d));
        String path = request.getSession().getServletContext()
                .getRealPath("")+"/Resources/upload/emergencyChange";
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
        result.put("filePath", "/Resources/upload/emergencyChange/"+fileName);
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
			emailBean.setTitle("优化整改");
			emailBean.setRecvUser(item.get("user").toString());
			emailBean.setSendUser(funUtil.loginUser(request));
			emailBean.setContent(content);
			emailBean.setTime(FunUtil.nowDate());
			EmailService.insertEmail(emailBean);
			// ----END
		}
	}

    @RequestMapping("/seal")
    public ModelAndView seal(HttpServletRequest request,
                             HttpServletResponse response) {
        PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
        String path=request.getParameter("path");
        int type=-1;
        if(request.getParameter("type")!=null){
            type=Integer.parseInt(request.getParameter("type"));
        }
        if(path.indexOf("/")==0){
            path=path.substring(1);
        }
        poCtrl.setServerPage(request.getContextPath() +"/poserver.zz");// 设置授权程序servlet
        poCtrl.addCustomToolButton("保存", "Save()", 1); // 添加自定义按钮
        poCtrl.addCustomToolButton("签字", "Seal1()", 2);
        poCtrl.addCustomToolButton("签章", "Seal2()", 2);
		/*poCtrl.addCustomToolButton("删除签字", "DeleteSeal1()", 21);
		poCtrl.addCustomToolButton("删除签章", "DeleteSeal2()", 21);*/

        poCtrl.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
        poCtrl.addCustomToolButton("关闭", "CloseFile()", 21);
        poCtrl.setJsFunction_AfterDocumentOpened("IsFullScreen()");
        poCtrl.setTitlebar(false); //隐藏标题栏
        String name="";
        try {
            name= URLEncoder.encode(path,"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        poCtrl.setSaveFilePage(request.getContextPath() +"/office/save_page?path="+name);
        poCtrl.webOpen(request.getSession().getServletContext().getRealPath("/")+"/"+path, OpenModeType.docNormalEdit, "");
        poCtrl.setTagId("PageOfficeCtrl1");
        ModelAndView mv = new ModelAndView("Views/jsp/emergencyChange");
        mv.addObject("sealName1",WebUserServices.sealName(FunUtil.loginUser(request),"2"));
        mv.addObject("sealName2",WebUserServices.sealName(FunUtil.loginUser(request),"1"));
        mv.addObject("type",type);
        mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
        return mv;
    }
}
