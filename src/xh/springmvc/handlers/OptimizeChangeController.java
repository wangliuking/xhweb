package xh.springmvc.handlers;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import xh.func.plugin.DownLoadUtils;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.*;
import xh.mybatis.service.*;

@Controller
@RequestMapping(value = "/optimizeChange")
public class OptimizeChangeController {

    private boolean success;
    private String message;
    private FunUtil funUtil = new FunUtil();
    protected final Log log = LogFactory.getLog(OptimizeChangeController.class);
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
        result.put("items", OptimizeChangeService.selectAll(map));
        result.put("totals", OptimizeChangeService.dataCount(map));
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
        result.put("items", OptimizeChangeService.applyProgress(id));
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
        result.put("items",OptimizeChangeService.sheetShow(map));
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
        //将json字符串转换成json对象
        JSONObject jsonobject = JSONObject.fromObject(beanStr);
        //将json对象转换成User实体对象
        OptimizeChangeSheet bean = (OptimizeChangeSheet)JSONObject.toBean(jsonobject, OptimizeChangeSheet.class);
        this.success = true;
        int res = OptimizeChangeService.sheetChange(bean);
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
     * 申请
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/insertOptimizeChange", method = RequestMethod.POST)
    public void insertOptimizeChange(HttpServletRequest request,
                        HttpServletResponse response) {
        this.success = true;
        String userUnit = request.getParameter("userUnit");
        String note1 = request.getParameter("note1");
        String tel = request.getParameter("tel");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        OptimizeChangeBean bean = new OptimizeChangeBean();
        bean.setUserUnit(userUnit);
        bean.setTel(tel);
        bean.setFileName1(fileName);
        bean.setFilePath1(filePath);
        bean.setNote1(note1);
        bean.setUserName(funUtil.loginUser(request));
        bean.setRequestTime(funUtil.nowDate());

        log.info("data==>" + bean.toString());
        int rst = OptimizeChangeService.insertOptimizeChange(bean);
        if (rst == 1) {
            //通知运维负责人已提交优化整改申请表
            FunUtil.sendMsgToUserByPower("o_check_netoptimize",3,"优化整改","已提交优化整改申请表，请查阅！",request);
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
     * 运维负责人审核优化整改
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedOne", method = RequestMethod.POST)
    public void checkedOne(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note2 = request.getParameter("note2");
        int checked = Integer.parseInt(request.getParameter("checked"));
        OptimizeChangeBean bean = new OptimizeChangeBean();
        bean.setId(id);
        bean.setChecked(checked);
        bean.setUser2(funUtil.loginUser(request));
        bean.setTime2(funUtil.nowDate());
        bean.setNote2(note2);
        int rst = OptimizeChangeService.checkedOne(bean);
        if (rst == 1) {
            if(checked == 1){
                //运维负责人审核通过，发送通知邮件给运维组
                FunUtil.sendMsgToUserByGroupPower("r_netoptimize",3,"优化整改","运维负责人通过了优化整改申请",request);
            }else if(checked == -1){
                //运维负责人审核不通过，发送通知邮件给运维组
                FunUtil.sendMsgToUserByGroupPower("r_netoptimize",3,"优化整改","运维负责人不通过优化整改申请",request);
            }
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
     * 运维负责人向管理方发起审批
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
        String user = request.getParameter("userName");
        String note3 = request.getParameter("note3");
        OptimizeChangeBean bean = new OptimizeChangeBean();
        bean.setId(id);
        bean.setChecked(2);
        bean.setFileName2(fileName);
        bean.setFilePath2(filePath);
        bean.setUser3(funUtil.loginUser(request));
        bean.setTime3(funUtil.nowDate());
        bean.setNote3(note3);

        int rst = OptimizeChangeService.checkedTwo(bean);
        if (rst == 1) {
            //通知管理方负责人
            FunUtil.sendMsgToUserByPower("o_check_netoptimize",2,"优化整改","已提交优化整改申请，请查阅！",request);
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
     * 管理方审批优化整改
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
        String user = request.getParameter("user2");
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        OptimizeChangeBean bean = new OptimizeChangeBean();
        bean.setId(id);
        bean.setChecked(checked);
        bean.setUser4(funUtil.loginUser(request));
        bean.setTime4(funUtil.nowDate());
        bean.setNote4(note4);
        int rst = OptimizeChangeService.checkedThree(bean);
        if (rst == 1) {
            if(checked == 3){
                //管理方审批通过，向运维负责人发送通知消息
                FunUtil.sendMsgToUserByPower("o_check_netoptimize",3,"优化整改","管理方同意进行优化整改",request);
            }else if(checked == -2){
                //管理方审批不通过，向运维负责人发送通知消息
                FunUtil.sendMsgToUserByPower("o_check_netoptimize",3,"优化整改","管理方拒绝了优化整改",request);
                FunUtil.sendMsgToUserByGroupPower("r_netoptimize",3,"优化整改","管理方拒绝了优化整改",request);
            }
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
     * 通知抢修组进行整改
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedFour", method = RequestMethod.POST)
    public void checkedFour(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String repairTeamId = request.getParameter("repairTeamId");
        String note5 = request.getParameter("note5");
        String user4 = request.getParameter("user4");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        OptimizeChangeBean bean = new OptimizeChangeBean();
        bean.setId(id);
        bean.setChecked(4);
        bean.setFileName3(fileName);
        bean.setFilePath3(filePath);
        bean.setUser5(funUtil.loginUser(request));
        bean.setTime5(funUtil.nowDate());
        bean.setNote5(note5);

        int rst = OptimizeChangeService.checkedFour(bean);
        if (rst == 1) {
            //通知运维组进行整改
            FunUtil.sendMsgToUserByGroupPower("r_netoptimize",3,"优化整改","运维组准备进行整改",request);
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
     * 运维组确认通知
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
        String user = request.getParameter("user2");
        OptimizeChangeBean bean = new OptimizeChangeBean();
        bean.setId(id);
        bean.setChecked(5);
        bean.setUser6(funUtil.loginUser(request));
        bean.setTime6(funUtil.nowDate());
        bean.setNote6(note6);
        int rst = OptimizeChangeService.checkedFive(bean);
        if (rst == 1) {
            //抢修组反馈运维负责人
            FunUtil.sendMsgToUserByPower("o_check_netoptimize",3,"优化整改","正在执行优化整改",request);
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
     * 抢修组上传完结报告
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
        String user = request.getParameter("user2");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        OptimizeChangeBean bean = new OptimizeChangeBean();
        bean.setId(id);
        bean.setChecked(6);
        bean.setUser7(funUtil.loginUser(request));
        bean.setTime7(funUtil.nowDate());
        bean.setNote7(note7);
        bean.setFileName4(fileName);
        bean.setFilePath4(filePath);
        int rst = OptimizeChangeService.checkedSix(bean);
        if (rst == 1) {
            //通知运维负责人已上传完结报告
            FunUtil.sendMsgToUserByPower("o_check_netoptimize",3,"优化整改","优化整改完结报告已提交",request);
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
                .getRealPath("")+"/Resources/upload/optimizeChange";
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
        result.put("filePath", "/Resources/upload/OptimizeChange/"+fileName);
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
		emailBean.setTime(funUtil.nowDate());
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
			emailBean.setTime(funUtil.nowDate());
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
        ModelAndView mv = new ModelAndView("Views/jsp/optimizeChange");
        mv.addObject("sealName1",WebUserServices.sealName(FunUtil.loginUser(request),"2"));
        mv.addObject("sealName2",WebUserServices.sealName(FunUtil.loginUser(request),"1"));
        mv.addObject("type",type);
        mv.addObject("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
        return mv;
    }
}
