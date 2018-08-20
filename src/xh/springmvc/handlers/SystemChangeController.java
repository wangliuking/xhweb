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
        //将json字符串转换成json对象
        JSONObject jsonobject = JSONObject.fromObject(beanStr);
        //将json对象转换成User实体对象
        SystemChangeSheet bean = (SystemChangeSheet)JSONObject.toBean(jsonobject, SystemChangeSheet.class);
        this.success = true;
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
     * 发起系统升级
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
            this.message = "系统升级方案已经成功提交";
            //----向项目经理发送通知邮件
            FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","系统升级方案已经提交",request);
        } else {
            this.message = "系统升级方案提交失败";
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
     * 项目经理审核系统升级方案
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
        int rst = SystemChangeService.checkedOne(bean);
        this.message = "项目经理已审核";
        if (rst == 1) {
            if(checked == 1){
                //项目经理审核通过
                //项目经理发送通知邮件给管理方负责人
                FunUtil.sendMsgToUserByPower("o_check_system_up",2,"系统升级","系统升级方案已经提交",request);
            }else if(checked == -1){
                //项目经理审核不通过
                //项目经理发送通知邮件给项目负责人
                FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","系统升级方案审核不通过",request);
            }else if (checked == -6){
                //项目经理已经取消流程
                FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","项目经理已取消流程",request);
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
     * 管理方负责人审核系统升级方案
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
        this.message = "管理方已审核";
        int rst = SystemChangeService.checkedTwo(bean);
        if (rst == 1) {
            if(checked == 2){
                //管理方负责人审核通过
                //管理方负责人发送通知邮件给项目负责人
                FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","管理方通过审核",request);
            }else if(checked == -1){
                //管理方负责人审核不通过
                //管理方负责人发送通知邮件给项目负责人
                FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","管理方审核不通过",request);
            }else if (checked == -6){
                //管理方负责人已经取消流程
                FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","管理方负责人已取消流程",request);
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
     * 提交系统升级申请
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
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(3);
        bean.setUser4(funUtil.loginUser(request));
        bean.setTime4(FunUtil.nowDate());
        bean.setNote4(note4);
        bean.setFileName2(fileName);
        bean.setFilePath2(filePath);

        int rst = SystemChangeService.checkedThree(bean);
        if (rst == 1) {
            this.message = "提交系统升级申请成功";
            FunUtil.sendMsgToUserByPower("o_check_system_up",2,"系统升级","项目负责人提交了系统升级申请，请确认",request);
        } else {
            this.message = "提交系统升级申请失败";
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
     * 管理方负责人审核系统升级申请
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedFour", method = RequestMethod.POST)
    public void checkedFour(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        String note5 = request.getParameter("note5");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(checked);
        bean.setUser5(funUtil.loginUser(request));
        bean.setTime5(FunUtil.nowDate());
        bean.setNote5(note5);
        this.message = "管理方已审核";
        int rst = SystemChangeService.checkedFour(bean);
        if (rst == 1) {
            if(checked == 4){
                //管理方负责人同意申请
                //管理方负责人发送通知邮件给项目负责人
                FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","管理方同意进行升级",request);
            }else if(checked == -3){
                //管理方负责人不同意升级
                //管理方负责人发送通知邮件给项目经理
                FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","管理方不同意进行升级",request);
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
     * 创建实施组然后更新状态
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedFive", method = RequestMethod.POST)
    public void checkedFive(HttpServletRequest request,
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
            String note6 = request.getParameter("note6");
            SystemChangeBean bean = new SystemChangeBean();
            bean.setId(id);
            bean.setChecked(5);
            bean.setUser6(funUtil.loginUser(request));
            bean.setTime6(FunUtil.nowDate());
            bean.setNote6(note6);
            bean.setImplId(ImplId);

            rst = SystemChangeService.checkedFive(bean);
            this.message ="已创建"+ImplId+ "组！";
        }

        if (rst == 1) {
            //通知项目负责人已经成功创建实施组
            FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","已成功创建了实施组",request);
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
     * 执行系统升级
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedSix", method = RequestMethod.POST)
    public void checkedSix(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        String ExcImplId = request.getParameter("ExcImplId");
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note7 = request.getParameter("note7");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(6);
        bean.setUser7(funUtil.loginUser(request));
        bean.setTime7(FunUtil.nowDate());
        bean.setNote7(note7);
        bean.setExcImplId(ExcImplId);

        int rst = SystemChangeService.checkedSix(bean);
        if(rst == 1){
            //通知实施组执行系统升级
            FunUtil.sendMsgToUserByGroupPower("r_system_up",3,"系统升级","准备进行系统升级！",request);
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
     * 反馈升级结果
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedSenven", method = RequestMethod.POST)
    public void checkedSenven(HttpServletRequest request,
                             HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        String note8 = request.getParameter("note8");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(checked);
        bean.setUser8(funUtil.loginUser(request));
        bean.setTime8(FunUtil.nowDate());
        bean.setNote8(note8);

        int rst = SystemChangeService.checkedSenven(bean);
        if(rst == 1){
            //通知项目负责人升级的结果
            FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","请查阅系统升级的结果",request);
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
     * 向项目负责人提交工作记录
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedEight", method = RequestMethod.POST)
    public void checkedEight(HttpServletRequest request,
                            HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note9 = request.getParameter("note9");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(8);
        bean.setUser9(funUtil.loginUser(request));
        bean.setTime9(FunUtil.nowDate());
        bean.setNote9(note9);
        bean.setFileName3(fileName);
        bean.setFilePath3(filePath);

        int rst = SystemChangeService.checkedEight(bean);
        if (rst == 1) {
            //向项目负责人发送通知邮件
            FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","实施组已提交工作记录，请查阅",request);
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
     * 向管理方负责人反馈结果
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedNine", method = RequestMethod.POST)
    public void checkedNine(HttpServletRequest request,
                           HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note10 = request.getParameter("note10");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(9);
        bean.setUser10(funUtil.loginUser(request));
        bean.setTime10(FunUtil.nowDate());
        bean.setNote10(note10);
        bean.setFileName4(fileName);
        bean.setFilePath4(filePath);

        int rst = SystemChangeService.checkedNine(bean);
        if (rst == 1) {
            //向管理方负责人发送通知邮件
            FunUtil.sendMsgToUserByPower("o_check_system_up",2,"系统升级","项目负责人已反馈系统升级结果，请查阅",request);
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
        if(rst == 1){
            //通知项目经理已重新拟制了升级方案
            FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","系统升级方案已重新拟制，请查阅",request);
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
     * 确认是否再次提交申请
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedNegThree", method = RequestMethod.POST)
    public void checkedNegThree(HttpServletRequest request,
                               HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        int checked = funUtil.StringToInt(request.getParameter("checked"));
        String noteNeg3 = request.getParameter("noteNeg3");
        SystemChangeBean bean = new SystemChangeBean();
        bean.setId(id);
        bean.setChecked(checked);
        bean.setUserNeg3(funUtil.loginUser(request));
        bean.setTimeNeg3(FunUtil.nowDate());
        bean.setNoteNeg3(noteNeg3);

        int rst = SystemChangeService.checkedNegThree(bean);
        if (rst == 1) {
           if(checked == 2){
               //项目经理同意继续提交申请
               FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","项目经理同意再次提交系统升级申请",request);
           }else if(checked == -6){
               //项目经理不同意继续提交申请，流程结束
               FunUtil.sendMsgToUserByPower("o_check_system_up",3,"系统升级","项目经理不同意继续提交系统升级申请，流程已结束",request);
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
        bean.setUserNeg4(funUtil.loginUser(request));
        bean.setTimeNeg4(FunUtil.nowDate());
        bean.setNoteNeg4(note9);

        int rst = SystemChangeService.checkedNegFour(bean);
        if (rst == 1) {
            //通知实施组执行回退操作
            FunUtil.sendMsgToUserByGroupPower("r_system_up",3,"系统升级","请迅速进行系统回退！",request);
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

}
