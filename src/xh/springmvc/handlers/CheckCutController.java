package xh.springmvc.handlers;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import Decoder.BASE64Decoder;

import com.oreilly.servlet.Base64Decoder;

import xh.func.plugin.*;
import xh.mybatis.bean.CheckCutBean;
import xh.mybatis.bean.CheckCutElecBean;
import xh.mybatis.bean.WebUserBean;
import xh.mybatis.service.BsAlarmService;
import xh.mybatis.service.CheckCutService;
import xh.mybatis.service.WebUserServices;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        String bsPeriod = request.getParameter("bsPeriod");
        String bsRules = request.getParameter("bsRules");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String checkCutType = request.getParameter("checkCutType");
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
        map.put("bsPeriod", bsPeriod);
        map.put("bsRules", bsRules);
        map.put("checkCutType", checkCutType);

        if(startTime == null || "".equals(startTime)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //获取当前月第一天：
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
            String first = format.format(c.getTime());
            //System.out.println("===============first:"+first);

            //获取当前月最后一天
            Calendar ca = Calendar.getInstance();
            ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
            String last = format.format(ca.getTime());
            //System.out.println("===============last:"+last);

            map.put("startTime", first+" 00:00:00");
            map.put("endTime", last+" 23:59:59");
        }else{
            map.put("startTime", startTime);
            map.put("endTime", endTime);
        }

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
        CheckCutService.updateFaultWhenDel(id);
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
     * 更新打印状态
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updatePrintStatusById", method = RequestMethod.GET)
    public void updatePrintStatusById(HttpServletRequest request,
                               HttpServletResponse response) {
        this.success = true;
        int id = Integer.parseInt(request.getParameter("id"));

        CheckCutService.updatePrintStatusById(id);

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

    public static void main(String[] args) throws Exception {
        /*List<String> list = new LinkedList<String>();
        list.add("C:\\down\\郫县犀浦镇2019-07-111729100test.doc");
        list.add("C:\\down\\郫县犀浦镇2019-07-111729100.doc");
        String zipPath = "E:\\xh\\xhweb\\out\\artifacts\\xhweb_Web_exploded\\Resources\\upload\\check\\2019\\07\\4\\故障核减申请书.zip";
        zipFiles(list,zipPath);*/
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-07-12 15:15:15");
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        System.out.println(ca.get(Calendar.YEAR));
        System.out.println(ca.get(Calendar.MONTH)+1);
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
     *  发起核减的位置自动填充表格(基站)
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
        List<Map<String,Object>> list = CheckCutService.selectBsInformationById(selectMap);
        Map<String,Object> res = list.get(0);
        bean.setHometype("该基站建设于"+res.get("hometype")+"机房");//机房类型
        //判断传输类型
        if("已开通".equals(list.get(0).get("is_open")) && "已开通".equals(list.get(1).get("is_open"))){
            bean.setTransfer("双传输链路");
        }else if("已开通".equals(list.get(0).get("is_open")) || "已开通".equals(list.get(1).get("is_open"))){
            bean.setTransfer("单传输链路");
        }else{
            bean.setTransfer("无传输链路");
        }
        //判断是否同一运营商
        String transferOne = list.get(0).get("operator")+"";
        String transferTwo = list.get(1).get("operator")+"";
        /*if(transferOne.equals(transferTwo) && !"".equals(transferOne) && transferOne != null){
            bean.setTransferCompare("相同运营商");
        }else if(!transferOne.equals(transferTwo) && !"".equals(transferOne) && transferOne != null){
            bean.setTransferCompare("不同运营商");
        }*/
        if(transferOne != null && !"".equals(transferOne)){
            bean.setTransferOne("第一条传输链路运营商为"+transferOne);
        }else{
            bean.setTransferOne("无第一条传输链路");
        }
        if(transferTwo != null && !"".equals(transferTwo)){
            bean.setTransferTwo("第二条传输链路运营商为"+transferTwo);
        }else{
            bean.setTransferTwo("无第二条传输链路");
        }


        //bean.setPowerOne("基站主设备供电类型为"+res.get("bs_supply_electricity_type")+"，基站主设备由"+res.get("bs_power_down_type")+"供电");
        bean.setPowerOne("基站主设备为"+res.get("bs_supply_electricity_type")+"供电");
        bean.setPowerTimeOne(res.get("bs_xh_fact_time")+"小时");
        //bean.setPowerTwo("网络柜供电类型为"+res.get("transfer_supply_electricity_type")+"，传输设备由"+res.get("transfer_power_down_type")+"供电");
        bean.setPowerTwo("传输、环控等网络设备为"+res.get("transfer_supply_electricity_type")+"供电");
        bean.setPowerTimeTwo(res.get("transfer_fact_time")+"小时");
        bean.setMaintainTime(res.get("to_bs_date")+"分钟");
        if("是".equals(res.get("is_allow_generation"))){
            bean.setIsPower("基站允许发电");
            bean.setIsPowerTime("发电时间:"+res.get("generation_date"));
        }else if("否".equals(res.get("is_allow_generation"))){
            bean.setIsPower("基站不允许发电");
            bean.setIsPowerTime("发电时间:无");
        }
        String tempPeriod = res.get("period")+"";
        String period = "";
        if("3".equals(tempPeriod)){
            period = "三";
        }else if("4".equals(tempPeriod)){
            period = "四";
        }
        bean.setPeriod("成都市应急指挥调度无线通信网"+period+"期项目部");
        bean.setFirstDesc("《基站信息表-"+cal.get(cal.YEAR)+"年"+(cal.get(cal.MONTH)+1)+"月》");
        bean.setApplyTime(cal.get(cal.YEAR)+"年 "+(cal.get(cal.MONTH)+1)+"月 "+cal.get(cal.DATE)+"日 "+dayForWeek(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())));
        //System.out.println(" bean : "+bean);
        bean.setCheckCutType("1");
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
     *  发起核减的位置自动填充表格(调度台)
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/createCheckSheetDispatch", method = RequestMethod.GET)
    public void createCheckSheetDispatch(HttpServletRequest request,
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
        List<Map<String,Object>> list = CheckCutService.selectDispathInformationById(selectMap);
        Map<String,Object> res = list.get(0);
        //判断传输类型
        String transferType = res.get("transfer")+"";
        if("1".equals(transferType)){
            bean.setTransfer("单传输");
        }else if("2".equals(transferType)){
            bean.setTransfer("双传输");
        }
        //判断是否同一运营商
        String transferOne = res.get("transferOne")+"";
        String transferTwo = res.get("transferTwo")+"";
        if(transferOne.equals(transferTwo) && !"".equals(transferOne) && transferOne != null){
            bean.setTransferCompare("相同运营商");
        }else if(!transferOne.equals(transferTwo) && !"".equals(transferOne) && transferOne != null){
            bean.setTransferCompare("不同运营商");
        }

        if(transferOne != null && !"".equals(transferOne) && !"null".equals(transferOne)){
            bean.setTransferOne("第一传输运营商"+transferOne);
        }else{
            bean.setTransferOne("");
        }
        if(transferTwo != null && !"".equals(transferTwo) && !"null".equals(transferTwo)){
            bean.setTransferTwo("第二传输运营商"+transferTwo);
        }else {
            bean.setTransferTwo("");
        }

        String tempPeriod = res.get("period")+"";
        String period = "";
        if("3".equals(tempPeriod)){
            period = "三";
        }else if("4".equals(tempPeriod)){
            period = "四";
        }
        bean.setPeriod("成都市应急指挥调度无线通信网"+period+"期项目部");

        bean.setApplyTime(cal.get(cal.YEAR)+"年 "+(cal.get(cal.MONTH)+1)+"月 "+cal.get(cal.DATE)+"日 "+dayForWeek(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())));
        //System.out.println(" bean : "+bean);
        bean.setCheckCutType("2");
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
     *  故障核减自动填充(基站)
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
        Calendar cal = Calendar.getInstance();
        Map<String,Object> selectMap = new HashMap<String,Object>();
        selectMap.put("bsId",Integer.parseInt(bsId));
        List<Map<String,Object>> list = CheckCutService.selectBsInformationById(selectMap);
        Map<String,Object> res = list.get(0);
        bean.setHometype("该基站建设于"+res.get("hometype")+"机房");//机房类型
        //判断传输类型
        if("已开通".equals(list.get(0).get("is_open")) && "已开通".equals(list.get(1).get("is_open"))){
            bean.setTransfer("双传输链路");
        }else if("已开通".equals(list.get(0).get("is_open")) || "已开通".equals(list.get(1).get("is_open"))){
            bean.setTransfer("单传输链路");
        }else{
            bean.setTransfer("无传输链路");
        }
        //判断是否同一运营商
        String transferOne = list.get(0).get("operator")+"";
        String transferTwo = list.get(1).get("operator")+"";
        /*if(transferOne.equals(transferTwo) && !"".equals(transferOne) && transferOne != null){
            bean.setTransferCompare("相同运营商");
        }else if(!transferOne.equals(transferTwo) && !"".equals(transferOne) && transferOne != null){
            bean.setTransferCompare("不同运营商");
        }*/
        if(transferOne != null && !"".equals(transferOne)){
            bean.setTransferOne("第一条传输链路运营商为"+transferOne);
        }else{
            bean.setTransferOne("无第一条传输链路");
        }
        if(transferTwo != null && !"".equals(transferTwo)){
            bean.setTransferTwo("第二条传输链路运营商为"+transferTwo);
        }else{
            bean.setTransferTwo("无第二条传输链路");
        }


        //bean.setPowerOne("基站主设备供电类型为"+res.get("bs_supply_electricity_type")+"，基站主设备由"+res.get("bs_power_down_type")+"供电");
        bean.setPowerOne("基站主设备为"+res.get("bs_supply_electricity_type")+"供电");
        bean.setPowerTimeOne(res.get("bs_xh_fact_time")+"小时");
        //bean.setPowerTwo("网络柜供电类型为"+res.get("transfer_supply_electricity_type")+"，传输设备由"+res.get("transfer_power_down_type")+"供电");
        bean.setPowerTwo("传输、环控等网络设备为"+res.get("transfer_supply_electricity_type")+"供电");
        bean.setPowerTimeTwo(res.get("transfer_fact_time")+"小时");
        bean.setMaintainTime(res.get("to_bs_date")+"分钟");
        if("是".equals(res.get("is_allow_generation"))){
            bean.setIsPower("基站允许发电");
            bean.setIsPowerTime("发电时间:"+res.get("generation_date"));
        }else if("否".equals(res.get("is_allow_generation"))){
            bean.setIsPower("基站不允许发电");
            bean.setIsPowerTime("发电时间:无");
        }
        String tempPeriod = res.get("period")+"";
        String period = "";
        if("3".equals(tempPeriod)){
            period = "三";
        }else if("4".equals(tempPeriod)){
            period = "四";
        }
        bean.setPeriod("成都市应急指挥调度无线通信网"+period+"期项目部");
        bean.setFirstDesc("《基站信息表-"+cal.get(cal.YEAR)+"年"+(cal.get(cal.MONTH)+1)+"月》");
        bean.setApplyTime(cal.get(cal.YEAR)+"年 "+(cal.get(cal.MONTH)+1)+"月 "+cal.get(cal.DATE)+"日 "+dayForWeek(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())));
        //System.out.println(" bean : "+bean);
        bean.setCheckCutType("1");
        //填充申请表部分数据end

        log.info("data==>" + bean.toString());
        int rst = CheckCutService.createCheckCut(bean);
        this.message = "核减信息已生成，请进入核减流程发起申请";

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
     *  故障核减自动填充(调度台)
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/createCheckCutDispatch", method = RequestMethod.POST)
    public void createCheckCutDispatch(HttpServletRequest request,
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
        Calendar cal = Calendar.getInstance();
        Map<String,Object> selectMap = new HashMap<String,Object>();
        selectMap.put("bsId",Integer.parseInt(bsId));
        List<Map<String,Object>> list = CheckCutService.selectDispathInformationById(selectMap);
        Map<String,Object> res = list.get(0);
        //判断传输类型
        String transferType = res.get("transfer")+"";
        if("1".equals(transferType)){
            bean.setTransfer("单传输");
        }else if("2".equals(transferType)){
            bean.setTransfer("双传输");
        }
        //判断是否同一运营商
        String transferOne = res.get("transferOne")+"";
        String transferTwo = res.get("transferTwo")+"";
        if(transferOne.equals(transferTwo) && !"".equals(transferOne) && transferOne != null){
            bean.setTransferCompare("相同运营商");
        }else if(!transferOne.equals(transferTwo) && !"".equals(transferOne) && transferOne != null){
            bean.setTransferCompare("不同运营商");
        }

        if(transferOne != null && !"".equals(transferOne) && !"null".equals(transferOne)){
            bean.setTransferOne("第一传输运营商"+transferOne);
        }else{
            bean.setTransferOne("");
        }
        if(transferTwo != null && !"".equals(transferTwo) && !"null".equals(transferTwo)){
            bean.setTransferTwo("第二传输运营商"+transferTwo);
        }else {
            bean.setTransferTwo("");
        }

        String tempPeriod = res.get("period")+"";
        String period = "";
        if("3".equals(tempPeriod)){
            period = "三";
        }else if("4".equals(tempPeriod)){
            period = "四";
        }
        bean.setPeriod("成都市应急指挥调度无线通信网"+period+"期项目部");

        bean.setApplyTime(cal.get(cal.YEAR)+"年 "+(cal.get(cal.MONTH)+1)+"月 "+cal.get(cal.DATE)+"日 "+dayForWeek(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())));
        //System.out.println(" bean : "+bean);
        bean.setCheckCutType("2");
        //填充申请表部分数据end

        log.info("data==>" + bean.toString());
        int rst = CheckCutService.createCheckCut(bean);
        this.message = "核减信息已生成，请进入核减流程发起申请";

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
        bean.setPersion2("sign/"+funUtil.loginUser(request)+".png");
        int rst = CheckCutService.checkedOne(bean);
        if (checked == 1)
            this.message = "审核通过，已自动保存";
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
        bean.setPersion3("sign/"+funUtil.loginUser(request)+".png");
        int rst = CheckCutService.checkedTwo(bean);
        if (checked == 2)
            this.message = "审核通过，已自动保存";
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
     * @param files
     * @param request
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam("filePath") MultipartFile[] files,
                       HttpServletRequest request,HttpServletResponse response) {
        String fileNames = "";
        String filePaths = "";
        try {
            for(MultipartFile file:files){
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
                file.transferTo(targetFile);
                this.success=true;
                this.message="文件上传成功";
                fileName=data+"-"+fileName;
                fileNames+=fileName+";";
                filePaths+="/Resources/upload/CheckCut/"+fileName+";";
                File rename = new File(path,fileName);
                targetFile.renameTo(rename);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.message="文件上传失败";
        }

        HashMap result = new HashMap();
        result.put("success", success);
        result.put("message", message);
        result.put("fileName", fileNames);
        result.put("filePath", filePaths);
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
     * 测试word压缩导出
     */
    @RequestMapping(value = "/downLoadZipFile")
    public void downLoadZipFile(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String bsId = request.getParameter("bsId");
        String name = request.getParameter("bsName");
        String status = request.getParameter("checked");
        String bsPeriod = request.getParameter("bsPeriod");
        String bsRules = request.getParameter("bsRules");
        String checkCutType = request.getParameter("checkCutType");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("bsId", bsId);
        param.put("name", name);
        param.put("status", status);
        param.put("bsPeriod", bsPeriod);
        param.put("bsRules", bsRules);
        param.put("checkCutType", checkCutType);
        param.put("startTime",startTime);
        param.put("endTime",endTime);
        List<Map<String,Object>> list = CheckCutService.exportWordByTime(param);
        System.out.println(param);
        System.out.println(list);
        String zipName = "checkcut.zip";
        WordTest wordTest = new WordTest();
        List<String> fileList = wordTest.run(list);//查询数据库中记录

        //自动生成start
        //String zipPath = "E:\\xh\\xhweb\\out\\artifacts\\xhweb_Web_exploded\\Resources\\upload\\check\\2019\\07\\4\\故障核减申请书.zip";
        for(int i=3;i<5;i++){
            List<String> filePeriodList = WordTest.searchPeriodFile(i+"");
            if(filePeriodList.size()>0){
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
                Calendar ca = Calendar.getInstance();
                ca.setTime(date);
                int year = ca.get(Calendar.YEAR);
                int tempMonth = ca.get(Calendar.MONTH)+1;
                String month = "";
                if(tempMonth<10){
                    month = "0"+ tempMonth;
                }else {
                    month = "" + tempMonth;
                }
                String zipPath = this.getClass().getResource("/").getPath();
                String[] temp = zipPath.split("WEB-INF");
                String finalPath = temp[0]+"Resources/upload/check/"+year+"/"+month+"/"+i+"/"+"故障核减申请书.zip";
                zipFiles(filePeriodList,finalPath);
            }
        }
        //自动生成end

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename="+zipName);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try {
            for(Iterator<String> it = fileList.iterator(); it.hasNext();){
                String str = it.next();
                ZipUtils.doCompress(str, out);
                response.flushBuffer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            out.close();
        }
    }

    public static void zipFiles(List<String> fileList, String zipPath) {
        List<File> srcFiles = new LinkedList<File>();
        for(int i=0;i<fileList.size();i++){
            File file = new File(fileList.get(i));
            srcFiles.add(file);
        }
        File zipFile = new File(zipPath);
        File parentFile = zipFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        // 判断压缩后的文件存在不，不存在则创建
        if (!zipFile.exists()) {
            try {
                zipFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 创建 FileOutputStream 对象
        FileOutputStream fileOutputStream = null;
        // 创建 ZipOutputStream
        ZipOutputStream zipOutputStream = null;
        // 创建 FileInputStream 对象
        FileInputStream fileInputStream = null;

        try {
            // 实例化 FileOutputStream 对象
            fileOutputStream = new FileOutputStream(zipFile);
            // 实例化 ZipOutputStream 对象
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            // 创建 ZipEntry 对象
            ZipEntry zipEntry = null;
            // 遍历源文件数组
            for (int i = 0; i < srcFiles.size(); i++) {
                // 将源文件数组中的当前文件读入 FileInputStream 流中
                fileInputStream = new FileInputStream(srcFiles.get(i));
                // 实例化 ZipEntry 对象，源文件数组中的当前文件
                zipEntry = new ZipEntry(srcFiles.get(i).getName());
                zipOutputStream.putNextEntry(zipEntry);
                // 该变量记录每次真正读的字节个数
                int len;
                // 定义每次读取的字节数组
                byte[] buffer = new byte[1024];
                while ((len = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
            zipOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析base64并保存
     */
    @RequestMapping(value = "/uploadImg",method = RequestMethod.POST)
    public void uploadImg(HttpServletRequest req,HttpServletResponse resp) {
        String imageFiles = req.getParameter("imgStr");
        BASE64Decoder decoder=new BASE64Decoder();
        byte[] imageByte = null;
        try {
            imageByte = decoder.decodeBuffer(imageFiles);
            for (int i = 0; i < imageByte.length; ++i) {
                if (imageByte[i] < 0) {
                    imageByte[i] += 256;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String files = funUtil.loginUser(req) + ".png";

        String path = this.getClass().getResource("/").getPath();
        String[] temp = path.split("WEB-INF");
        String filename = temp[0]+"Views/business/sign/"+files;
        System.out.println(filename);

        try {
            File imageFile = new File(filename);
            imageFile.createNewFile();
            if (!imageFile.exists()) {
                imageFile.createNewFile();
            }else{
                imageFile.delete();
                imageFile.createNewFile();
            }
            OutputStream imageStream = new FileOutputStream(imageFile);
            imageStream.write(imageByte);
            imageStream.flush();
            imageStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap result = new HashMap();
        result.put("success", success);
        result.put("message", message);
        resp.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        log.debug(jsonstr);
        try {
            resp.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 发起签名
     */
    @RequestMapping(value = "/signNow",method = RequestMethod.GET)
    public void signNow(HttpServletRequest req,HttpServletResponse resp) {
        HashMap result = new HashMap();
        String files = funUtil.loginUser(req) + ".png";

        String path = this.getClass().getResource("/").getPath();
        String[] temp = path.split("WEB-INF");
        String filename = temp[0]+"Views/business/sign/"+files;
        System.out.println(filename);

        try {
            File imageFile = new File(filename);
            if (!imageFile.exists()) {
                result.put("success", false);
                result.put("message", "该用户未进行电子签名录入，请先录入电子签名！");
            }else{
                result.put("success", true);
                result.put("message", "签名成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        log.debug(jsonstr);
        try {
            resp.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 修改核减依据
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updateCheckContent", method = RequestMethod.POST)
    public void updateCheckContent(HttpServletRequest request,
                               HttpServletResponse response) {
        this.success = true;
        String id = request.getParameter("id");
        String fileName = request.getParameter("fileName");

        String fileName1 = "";
        String fileName2 = "";
        String fileName3 = "";
        String[] fileArr = fileName.split(";");
        if(fileArr.length<5){
            fileName1 = fileName;
        }else if (fileArr.length>4 && fileArr.length<9){
            fileName1 = fileArr[0]+";"+fileArr[1]+";"+fileArr[2]+";"+fileArr[3]+";";
            for(int i=4;i<fileArr.length;i++){
                fileName2 += fileArr[i]+";";
            }
        }else if (fileArr.length>8 && fileArr.length<13){
            fileName1 = fileArr[0]+";"+fileArr[1]+";"+fileArr[2]+";"+fileArr[3]+";";
            fileName2 = fileArr[4]+";"+fileArr[5]+";"+fileArr[6]+";"+fileArr[7]+";";
            for(int i=8;i<fileArr.length;i++){
                fileName3 += fileArr[i]+";";
            }
        }

        String note1 = request.getParameter("note1");
        CheckCutBean bean = new CheckCutBean();
        bean.setId(Integer.parseInt(id));
        bean.setFileName1(fileName1);
        bean.setFileName2(fileName2);
        bean.setFileName3(fileName3);
        bean.setNote1(note1);

        log.info("data==>" + bean.toString());
        int rst = CheckCutService.updateCheckContent(bean);

        HashMap result = new HashMap();
        result.put("success", success);
        result.put("result", rst);
        result.put("message", "修改成功");
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
    @RequestMapping(value = "/selectCheckTimeForStatus", method = RequestMethod.GET)
    public void selectCheckTimeForStatus(HttpServletRequest request,
                             HttpServletResponse response) {
        this.success = true;
        String checkCreateTime = CheckCutService.selectCheckTimeForStatus();
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("result", checkCreateTime);
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
     * 更新发电说明
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/replaceCheckContent", method = RequestMethod.POST)
    public void replaceCheckContent(HttpServletRequest request,
                                   HttpServletResponse response) {
        this.success = true;
        String bsId = request.getParameter("bsId");
        String name = request.getParameter("name");
        String to_bs_time = request.getParameter("to_bs_time");
        String to_bs_level = request.getParameter("to_bs_level");
        String isPower = request.getParameter("isPower");
        String power_time = request.getParameter("power_time");
        String fileName = request.getParameter("fileName");
        String suggest = request.getParameter("suggest");

        CheckCutElecBean bean = new CheckCutElecBean();
        bean.setBsId(Integer.parseInt(bsId));
        bean.setName(name);
        bean.setTo_bs_time(to_bs_time);
        bean.setTo_bs_level(to_bs_level);
        bean.setIsPower(isPower);
        bean.setPower_time(power_time);
        bean.setReason(fileName);
        bean.setSuggest(suggest);

        log.info("bean : "+bean);
        int rst = CheckCutService.replaceCheckContent(bean);

        HashMap result = new HashMap();
        result.put("success", success);
        result.put("result", rst);
        result.put("message", "保存成功");
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
     * 删除发电记录
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/delElec", method = RequestMethod.GET)
    public void delElec(HttpServletRequest request,
                                    HttpServletResponse response) {
        this.success = true;
        String bsId = request.getParameter("bsId");
        int rst = CheckCutService.delElec(bsId);
        HashMap result = new HashMap();
        result.put("success", success);
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
     * 根据id查询发电记录
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/selectCheckCutElecById", method = RequestMethod.GET)
    public void selectCheckCutElecById(HttpServletRequest request,
                          HttpServletResponse response) {
        this.success = true;
        String bsId = request.getParameter("bsId");
        HashMap result = new HashMap();
        result.put("success", success);
        result.put("items", CheckCutService.selectCheckCutElecById(Integer.parseInt(bsId)));
        response.setContentType("application/json;charset=utf-8");
        String jsonstr = json.Encode(result);
        try {
            response.getWriter().write(jsonstr);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
