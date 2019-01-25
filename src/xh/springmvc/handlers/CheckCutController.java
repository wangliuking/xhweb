package xh.springmvc.handlers;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import xh.func.plugin.DownLoadUtils;
import xh.func.plugin.FlexJSON;
import xh.func.plugin.FunUtil;
import xh.mybatis.bean.CheckCutBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.BsAlarmService;
import xh.mybatis.service.CheckCutService;
import xh.mybatis.service.WebUserServices;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/checkCut")
public class CheckCutController {

    private boolean success;
    private String message;
    private FunUtil funUtil = new FunUtil();
    protected final Log log = LogFactory.getLog(CheckCutController.class);
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
        String bsId = request.getParameter("bsId");
        String name = request.getParameter("bsName");
        String status = request.getParameter("checked");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String user=funUtil.loginUser(request);
        //unit = WebUserServices.selectUnitByUser(user);
        WebUserBean userbean=WebUserServices.selectUserByUser(user);
        int roleId=userbean.getRoleId();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("limit", limit);
        map.put("user", user);
        map.put("roleId", roleId);
        System.out.println("user:"+user);
        System.out.println("roleId:"+roleId);
        map.put("bsId", bsId);
        map.put("name", name);
        map.put("status", status);
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        HashMap result = new HashMap();
        result.put("success", success);
        result.put("items", CheckCutService.selectAll(map));
        result.put("totals", CheckCutService.dataCount(map));
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
        result.put("items", CheckCutService.applyProgress(id));
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
     * 显示核减申请表
     */
    @RequestMapping(value="/sheetShow",method = RequestMethod.GET)
    public void sheetShow(HttpServletRequest request,HttpServletResponse response){
        this.success = true;
        int id = Integer.parseInt(request.getParameter("id"));
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        HashMap result = new HashMap();
        result.put("success",success);
        result.put("items",CheckCutService.sheetShow(map));
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
     * 修改核减申请表
     */
    @RequestMapping(value = "/sheetChange",method = RequestMethod.POST)
    @ResponseBody
    public void sheetChange(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "bean") String beanStr){
        //将json字符串转换成json对象
        JSONObject jsonobject = JSONObject.fromObject(beanStr);
        //将json对象转换成User实体对象
        CheckCutBean bean = (CheckCutBean)JSONObject.toBean(jsonobject, CheckCutBean.class);
        //System.out.println("bean : "+bean);
        this.success = true;
        int res = CheckCutService.sheetChange(bean);
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
     * 运维人员重新发起核减申请
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkedNegTwo", method = RequestMethod.POST)
    public void checkedNegTwo(HttpServletRequest request,
                              HttpServletResponse response) {
        this.success = true;
        int id = funUtil.StringToInt(request.getParameter("id"));
        String note1 = request.getParameter("note1");
        String fileName1 = request.getParameter("fileName");
        String filePath1 = request.getParameter("path");
        CheckCutBean bean = new CheckCutBean();
        bean.setId(id);
        bean.setFileName1(fileName1);
        bean.setFilePath1(filePath1);
        bean.setNote1(note1);
        bean.setUser1(funUtil.loginUser(request));
        bean.setTime1(FunUtil.nowDate());
        bean.setChecked(0);

        log.info("data==>" + bean.toString());
        int rst = CheckCutService.checkedNegTwo(bean);
        if (rst == 1) {
            this.message = "新的核减申请信息已经成功提交";
            //----给运维负责人发送通知邮件
            FunUtil.sendMsgToUserByGroupPower("r_cut",3,"核减流程","重新提交了核减申请，请查阅！",request);
            //----END
        } else {
            this.message = "新的核减申请信息未成功提交";
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
     * 根据id删除核减记录
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/deleteCheckCutById", method = RequestMethod.GET)
    public void deleteCheckCutById(HttpServletRequest request,
                               HttpServletResponse response) {
        this.success = true;
        int id = Integer.parseInt(request.getParameter("id"));
        int res = CheckCutService.deleteCheckCutById(id);
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("message", res);
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
     * 更新bsfault派单状态
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updateCheckTag", method = RequestMethod.POST)
    public void updateCheckTag(HttpServletRequest request,
                               HttpServletResponse response) {
        this.success = true;
        int id = Integer.parseInt(request.getParameter("id"));
        BsAlarmService.updateCkeckTag(id,1);
        HashMap result = new HashMap();
        result.put("success", success);
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

    public static void main(String[] args) {
        String s = "73";
        int id = Integer.parseInt(s);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("bsId",id);
        Map<String,Object> res = CheckCutService.selectBsInformationById(map);
        System.out.println(" powerTime : "+res.get("powerTime"));
    }

    /**
     * 判断当前日期是星期几<br>
     * <br>
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */
    public static String dayForWeek(String pTime){
        String[] week = {"星期","星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        }catch (Exception e){
            e.printStackTrace();
        }
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return week[dayForWeek];
    }

    /**
     *  发起核减的位置自动填充表格
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/createCheckSheet", method = RequestMethod.GET)
    public void createCheckSheet(HttpServletRequest request,
                               HttpServletResponse response) {
        this.success = true;
        String bsId = request.getParameter("bsId");
        int id=FunUtil.StringToInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String breakTime = request.getParameter("breakTime");
        String restoreTime = request.getParameter("restoreTime");
        CheckCutBean bean = new CheckCutBean();
        bean.setBsId(bsId);
        bean.setId(id);
        bean.setName(name);
        bean.setBreakTime(breakTime);
        bean.setRestoreTime(restoreTime);

        //填充申请表部分数据start
        Calendar cal = Calendar.getInstance();
        Map<String,Object> selectMap = new HashMap<String,Object>();
        selectMap.put("bsId",Integer.parseInt(bsId));
        Map<String,Object> res = CheckCutService.selectBsInformationById(selectMap);
        bean.setHometype("基站所在机房归属"+res.get("roomType"));//机房类型
        //判断传输类型
        if("已开通".equals(res.get("transferOneIsOpen")) && "已开通".equals(res.get("transferTwoIsOpen"))){
            bean.setTransfer("双传输");
        }else if("已开通".equals(res.get("transferOneIsOpen")) || "已开通".equals(res.get("transferTwoIsOpen"))){
            bean.setTransfer("单传输");
        }else{
            bean.setTransfer("无传输");
        }
        String isSameOperator = res.get("isSameOperator")+"";
        //判断是否同一运营商
        if("0".equals(isSameOperator)){
            bean.setTransferCompare("不同运营商");
        }else if("1".equals(isSameOperator)){
            bean.setTransferCompare("相同运营商");
        }
        bean.setTransferOne("第一传输运营商"+res.get("transferOneOperator"));
        bean.setTransferTwo("第二传输运营商"+res.get("transferTwoOperator"));
        bean.setPowerOne("基站由"+res.get("bsDevicePowerMode")+"供电");
        bean.setPowerTimeOne(res.get("bsDeviceNowTime")+"小时");
        bean.setPowerTwo(res.get("transferDevicePowerMode")+"供电");
        bean.setPowerTimeTwo(res.get("transferDeviceNowTime")+"小时");
        bean.setMaintainTime(res.get("toBsTime")+"分钟");
        if("是".equals(res.get("isPermitTempPower"))){
            bean.setIsPower("基站允许发电");
            bean.setIsPowerTime("发电时间:"+res.get("powerTime"));
        }else if("否".equals(res.get("isPermitTempPower"))){
            bean.setIsPower("基站不允许发电");
        }
        bean.setPeriod("成都市应急指挥调度无线通信网"+res.get("period")+"项目部");
        bean.setFirstDesc("《基站信息表-"+cal.get(cal.YEAR)+"年"+(cal.get(cal.MONTH)+1)+"月》");
        bean.setApplyTime(cal.get(cal.YEAR)+"年 "+(cal.get(cal.MONTH)+1)+"月 "+cal.get(cal.DATE)+"日 "+dayForWeek(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())));
        //System.out.println(" bean : "+bean);
        //填充申请表部分数据end

        HashMap result = new HashMap();
        result.put("items", bean);
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
     *  故障核减自动填充
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/createCheckCut", method = RequestMethod.POST)
    public void createCheckCut(HttpServletRequest request,
                               HttpServletResponse response) {
        this.success = true;
        String note7 = request.getParameter("note7");
        String fileName4 = request.getParameter("fileName");
        String filePath4 = request.getParameter("path");
        String bsId = request.getParameter("bsId");
        String name = request.getParameter("name");
        String breakTime = request.getParameter("breakTime");
        String restoreTime = request.getParameter("restoreTime");
        String desc = request.getParameter("desc");
        String situation = request.getParameter("situation");
        CheckCutBean bean = new CheckCutBean();
        int id=FunUtil.StringToInt(request.getParameter("id"));
        bean.setFileName4(fileName4);
        bean.setFilePath4(filePath4);
        bean.setNote7(note7);
        bean.setChecked(7);

        bean.setBsId(bsId);
        bean.setName(name);
        bean.setBreakTime(breakTime);
        bean.setRestoreTime(restoreTime);
        bean.setDesc(desc);
        bean.setSituation(situation);
        bean.setId(id);
        System.out.println(" bean : "+bean);
        //填充申请表部分数据start
        Map<String,Object> selectMap = new HashMap<String,Object>();
        selectMap.put("bsId",Integer.parseInt(bsId));
        Map<String,Object> res = CheckCutService.selectBsInformationById(selectMap);
        bean.setHometype("基站所在机房归属"+res.get("roomType"));//机房类型
        //判断传输类型
        if("已开通".equals(res.get("transferOneIsOpen")) && "已开通".equals(res.get("transferTwoIsOpen"))){
            bean.setTransfer("双传输");
        }else if("已开通".equals(res.get("transferOneIsOpen")) || "已开通".equals(res.get("transferTwoIsOpen"))){
            bean.setTransfer("单传输");
        }else{
            bean.setTransfer("无传输");
        }
        String isSameOperator = res.get("isSameOperator")+"";
        //判断是否同一运营商
        if("0".equals(isSameOperator)){
            bean.setTransferCompare("不同运营商");
        }else if("1".equals(isSameOperator)){
            bean.setTransferCompare("相同运营商");
        }
        bean.setTransferOne("第一传输运营商"+res.get("transferOneOperator"));
        bean.setTransferTwo("第二传输运营商"+res.get("transferOneOperator"));
        bean.setPowerOne("基站由"+res.get("bsDevicePowerMode")+"供电");
        bean.setPowerTimeOne(res.get("bsDeviceNowTime")+"小时");
        bean.setPowerTwo(res.get("transferDevicePowerMode")+"供电");
        bean.setPowerTimeTwo(res.get("transferDeviceNowTime")+"小时");
        bean.setMaintainTime(res.get("toBsTime")+"分钟");
        if("是".equals(res.get("isPermitTempPower"))){
            bean.setIsPower("基站允许发电");
            bean.setIsPowerTime("发电时间:"+res.get("powerTime"));
        }else if("否".equals(res.get("isPermitTempPower"))){
            bean.setIsPower("基站不允许发电");
        }
        bean.setPeriod("成都市应急调度指挥无线通信网"+res.get("period")+"项目部");
        Calendar cal = Calendar.getInstance();
        bean.setApplyTime(cal.get(cal.YEAR)+"年 "+(cal.get(cal.MONTH)+1)+"月 "+cal.get(cal.DATE)+"日 "+dayForWeek(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())));
        //System.out.println(" bean : "+bean);
        //填充申请表部分数据end

        log.info("data==>" + bean.toString());
        int rst = CheckCutService.createCheckCut(bean);

        if (rst == 1) {
            this.message = "核减信息已生成，请进入核减流程发起申请";
            //----给运维负责人发送通知邮件
            FunUtil.sendMsgToUserByGroupPower("r_cut",3,"核减流程","有核减申请，请查阅！",request);
            //----END
        } else {
            this.message = "核减申请生成失败";
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
     * 运维人员发起核减申请
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/insertCheckCut", method = RequestMethod.POST)
    public void insertCheckCut(HttpServletRequest request,
                        HttpServletResponse response) {
        this.success = true;
        String id = request.getParameter("id");
        String userUnit = request.getParameter("userUnit");
        String note1 = request.getParameter("note1");
        String tel = request.getParameter("tel");
        String fileName = request.getParameter("fileName");
        String filePath = request.getParameter("path");
        CheckCutBean bean = new CheckCutBean();
        bean.setId(Integer.parseInt(id));
        bean.setUserUnit(userUnit);
        bean.setTel(tel);
        bean.setFileName1(fileName);
        bean.setFilePath1(filePath);
        bean.setNote1(note1);
        bean.setUserName(funUtil.loginUser(request));
        bean.setRequestTime(FunUtil.nowDate());
        bean.setChecked(0);

        log.info("data==>" + bean.toString());
        int rst = CheckCutService.insertCheckCut(bean);

        if (rst == 1) {
            this.message = "核减申请信息已经成功提交";
            //----给运维负责人发送通知邮件
            FunUtil.sendMsgToUserByGroupPower("r_cut",3,"核减流程","有核减申请，请查阅！",request);
           //----END
        } else {
            this.message = "核减申请信息未成功提交";
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
     * 运维负责人审核核减申请
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
        CheckCutBean bean = new CheckCutBean();
        bean.setId(id);
        bean.setChecked(checked);
        bean.setUser2(funUtil.loginUser(request));
        bean.setTime2(FunUtil.nowDate());
        bean.setNote2(note2);
        int rst = CheckCutService.checkedOne(bean);
        if (checked == 1)
            this.message = "审核通过";
        else
            this.message = "审核不通过";
        if (checked == 1) {
            //----给管理方发送通知邮件
            FunUtil.sendMsgToUserByGroupPower("r_cut",2,"核减流程","有核减申请，请查阅!",request);
            //----END
        }else{
            //通知网管组审核不通过
            FunUtil.sendMsgToUserByGroupPower("r_cut",3,"核减流程","核减申请不通过",request);
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
     * 管理方审核核减申请
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
        CheckCutBean bean = new CheckCutBean();
        bean.setId(id);
        bean.setChecked(checked);
        bean.setUser3(funUtil.loginUser(request));
        bean.setTime3(FunUtil.nowDate());
        bean.setNote3(note3);
        int rst = CheckCutService.checkedTwo(bean);
        if (checked == 2)
            this.message = "审核通过";
        else
            this.message = "审核不通过";
        if (rst == 1) {
            //----给运维人员发送通知邮件
            FunUtil.sendMsgToUserByGroupPower("r_cut",3,"核减流程","管理方已审核核减申请",request);
            //----END
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
     * 运维人员提交相关资料
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
        String note4 = request.getParameter("note4");
        CheckCutBean bean = new CheckCutBean();
        bean.setId(id);
        bean.setChecked(3);
        bean.setUser4(funUtil.loginUser(request));
        bean.setTime4(FunUtil.nowDate());
        bean.setNote4(note4);
        bean.setFileName3(fileName);
        bean.setFilePath3(filePath);

        int rst = CheckCutService.checkedThree(bean);
        this.message = "已提交核减相关资料";
        if (rst == 1) {
            //----给管理方发送通知邮件
            FunUtil.sendMsgToUserByGroupPower("r_cut",2,"核减流程","运维人员已提交相关核减资料",request);
            //----END
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
     * 运维人员结束流程
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
        CheckCutBean bean = new CheckCutBean();
        bean.setId(id);
        bean.setChecked(4);
        bean.setUser5(funUtil.loginUser(request));
        bean.setTime5(FunUtil.nowDate());
        bean.setNote5(note5);

        int rst = CheckCutService.checkedFour(bean);
        if (rst == 1) {
            //----给管理方发送通知邮件
            FunUtil.sendMsgToUserByGroupPower("r_cut",3,"核减流程","核减流程已经结束",request);
            //----END
        }
        this.message ="核减结束";
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
                .getRealPath("")+"/Resources/upload/CheckCut";
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
        result.put("filePath", "/Resources/upload/CheckCut/"+fileName);
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
