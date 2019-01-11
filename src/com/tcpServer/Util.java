package com.tcpServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tcpBean.*;
import xh.func.plugin.FunUtil;
import xh.mybatis.service.OrderService;

import com.tcpServer.ServerDemo.SocketThread;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;





/**
 * 工具类，处理接收的数据
 */

public class Util {
	//接收用对象
	private static UserLogin userLogin;
	private static UserInfo userInfo;
	private static ErrProTable errProTable;
	private static ErrProTableAck errProTableAck;
	private static ErrCheck errCheck;
	private static NetManagerTable netManagerTable;
	private static DispatchTable dispatchTable;
	private static MovebsTable movebsTable;
	private static OwnbsTable ownbsTable;
	private static GetMovebsInfo getMovebsInfo;
	private static GetOwnbsInfo getOwnbsInfo;
	private static GetBsType getBsType;
	private static GetBsInfo getBsInfo;
	private static GetForGpsDst getForGpsDst;
	private static GetAllBsList getAllBsList;
	private static GetAllAppList getAllAppList;
	private static GpsInfoUp gpsInfoUp;
	private static BsInspectTable bsInspectTable;
	private static GetErrBsInfo getErrBsInfo;
	private static GetInspectBsList getInspectBsList;
	private static GetUnInspectBsList getUnInspectBsList;
	private static GetTotalInfo getTotalInfo;
	private static GenTable genTable;
	private static GenTableAck genTableAck;
	private static GenCheck genCheck;
	private static GetGenArg getGenArg;
	private static GetPowerOnTime getPowerOnTime;
	
	/**
	 * 测试用主方法
	 */
	public static void main(String[] args) {
		/*String objectStr="{\"cmdtype\":\"userlogin\",\"userid\":\"admin\",\"passwd\":\"12345\",\"serialnumber\":\"qwertyui123\"}"+"\n";
		JSONObject jsonObject=JSONObject.fromObject(objectStr);
		userLogin = (UserLogin) JSONObject.toBean(jsonObject, UserLogin.class);
		System.out.println(userLogin);*/
		/*Map<String,Object> orderMap = OrderService.selectBySerialnumber("tP4C79y6");
		ErrProTable bean = new ErrProTable();
		bean.setBsid(orderMap.get("bsid")+"");
		bean.setZbdldm(orderMap.get("zbdldm")+"");
		bean.setStatus("处理中");
		System.out.println(bean);*/
	}
	
	/**
	 * 根据消息对象构建Json对象
	 *
	 * @param message
	 * @return
	 */
	public static JSONObject initJsonObject(SocketMessage message) {
		JSONObject jsonObject = new JSONObject();
		try {
			if (message.getType() != Custom.MESSAGE_ACTIVE)
				jsonObject.put("message", message.getMessage());
			else
				jsonObject.put("message", "");
			jsonObject.put("type", message.getType());
			jsonObject.put("from", message.getFrom());
			jsonObject.put("to", message.getTo());
			jsonObject.put("userId", message.getUserId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 解析Json数据
	 *
	 * @param json
	 * @return
	 */
	public static Map<String,String> parseJson(String json) {
		Map<String,String> map = new HashMap<String,String>();
		try {		
			JSONObject jsonObject=JSONObject.fromObject(json);
			String cmdtype = (String) jsonObject.get("cmdtype");
			if("userlogin".equals(cmdtype)){
				userLogin = (UserLogin) JSONObject.toBean(jsonObject, UserLogin.class);
				LoginAck loginAck = Service.appLogin(userLogin);
				if("0".equals(loginAck.getAck())){
					map.put("userId", userLogin.getUserid());
				}			
				map.put("returnMessage", Object2Json(loginAck));
				return map;
			}else if("getuserinfo".equals(cmdtype)){
				userInfo = (UserInfo) JSONObject.toBean(jsonObject, UserInfo.class);
				UserInfoAck userInfoAck = Service.appUserInfo(userInfo);
				map.put("returnMessage", Object2Json(userInfoAck));
				return map;			
			}else if("errprotableack".equals(cmdtype)){
				errProTableAck = (ErrProTableAck) JSONObject.toBean(jsonObject, ErrProTableAck.class);
				String serialNum = errProTableAck.getSerialnumber();
				Map<String,String> paramMap = new HashMap<String,String>();
				paramMap.put("serialNum", serialNum);
				paramMap.put("status", "1");
				paramMap.put("break_order", "");
				Service.updateUserStatus(paramMap);
				//更新四方伟业库
				Map<String,Object> orderMap = OrderService.selectBySerialnumber(serialNum);
				ErrProTable bean = new ErrProTable();
				bean.setBsid(orderMap.get("bsid")+"");
				bean.setZbdldm(orderMap.get("zbdldm")+"");
				bean.setStatus("处理中");
				System.out.println(bean);
				OrderService.updateSfOrder(bean);
				map.put("returnMessage", "");
				return map;
			}else if("errcheck".equals(cmdtype)){
				errCheck = (ErrCheck) JSONObject.toBean(jsonObject, ErrCheck.class);
				if("是".equals(errCheck.getHungorder())){
					//app提交了挂单
					Map<String,String> paramMap = new HashMap<String,String>();
					paramMap.put("serialNum", errCheck.getSerialnumber());
					paramMap.put("status", "4");
					paramMap.put("break_order", "1");
					Service.updateUserStatus(paramMap);

					ErrCheckAck bean = new ErrCheckAck();
					bean.setSerialnumber(errCheck.getSerialnumber());
					bean.setUserid(errCheck.getUserid());
					bean.setResult("4");
					//发送通知邮件通知网管组进行审核
					FunUtil.sendMsgToUserByGroupPowerWithoutReq("r_order",3,"派单审核","有挂单情况",errCheck.getUserid());
					map.put("returnMessage", Object2Json(bean));
				}else{
					String serialNum = errCheck.getSerialnumber();
					Map<String,String> paramMap = new HashMap<String,String>();
					paramMap.put("serialNum", serialNum);
					paramMap.put("status", "3");
					paramMap.put("break_order", "");
					Service.updateUserStatus(paramMap);
					//发送通知邮件通知网管组进行审核
					FunUtil.sendMsgToUserByGroupPowerWithoutReq("r_order",3,"派单审核","有派单审核，请查阅！",errCheck.getUserid());
					//更新四方伟业库
					Map<String,Object> orderMap = OrderService.selectBySerialnumber(serialNum);
					ErrProTable bean = new ErrProTable();
					bean.setBsid(orderMap.get("bsid")+"");
					bean.setZbdldm(orderMap.get("zbdldm")+"");
					bean.setStatus("请求审核");
					System.out.println(bean);
					OrderService.updateSfOrder(bean);
					map.put("returnMessage", "");
				/*ErrCheckAck errCheckAck = Service.appErrCheck(errCheck);
				map.put("returnMessage", Object2Json(errCheckAck));*/
					return map;
				}
			}else if("errprotable".equals(cmdtype)){
				errProTable = (ErrProTable) JSONObject.toBean(jsonObject, ErrProTable.class);
				ErrProTableAck errProTableAck = Service.appProTableAck(errProTable);
				map.put("returnMessage", Object2Json(errProTableAck));
				return map;
			}else if("bsinspecttable".equals(cmdtype)){
				Map<String, Class> classMap = new HashMap<String, Class>();
				classMap.put("message", Map.class);

				bsInspectTable = (BsInspectTable) JSONObject.toBean(jsonObject, BsInspectTable.class, classMap);
				BsInspectTableAck bsInspectTableAck = Service.appBsInspectTableAck(bsInspectTable);
				map.put("returnMessage", Object2Json(bsInspectTableAck));
				return map;
			}else if("movebstable".equals(cmdtype)){
				Map<String, Class> classMap = new HashMap<String, Class>();
				classMap.put("message", Map.class);
				
				movebsTable = (MovebsTable) JSONObject.toBean(jsonObject, MovebsTable.class, classMap);
				MovebsTableAck movebsTableAck = Service.appMovebsTableAck(movebsTable);
				map.put("returnMessage", Object2Json(movebsTableAck));
				return map;
			}else if("ownbstable".equals(cmdtype)){
				Map<String, Class> classMap = new HashMap<String, Class>();
				classMap.put("message", Map.class);
				
				ownbsTable = (OwnbsTable) JSONObject.toBean(jsonObject, OwnbsTable.class, classMap);
				OwnbsTableAck ownbsTableAck = Service.appOwnbsTable(ownbsTable);
				map.put("returnMessage", Object2Json(ownbsTableAck));
				return map;
			}else if("dispatchtable".equals(cmdtype)){
				Map<String, Class> classMap = new HashMap<String, Class>();
				classMap.put("message", Map.class);
				
				dispatchTable = (DispatchTable) JSONObject.toBean(jsonObject, DispatchTable.class, classMap);
				DispatchTableAck dispatchTableAck = Service.appDispatchTableAck(dispatchTable);
				map.put("returnMessage", Object2Json(dispatchTableAck));
				return map;
			}else if("netmanagertable".equals(cmdtype)){
				Map<String, Class> classMap = new HashMap<String, Class>();
				classMap.put("message", Map.class);
				
				netManagerTable = (NetManagerTable) JSONObject.toBean(jsonObject, NetManagerTable.class, classMap);
				NetManagerTableAck netManagerTableAck = Service.appNetManagerTableAck(netManagerTable);
				map.put("returnMessage", Object2Json(netManagerTableAck));
				return map;
			}else if("getmovebsinfo".equals(cmdtype)){
				getMovebsInfo = (GetMovebsInfo) JSONObject.toBean(jsonObject, GetMovebsInfo.class);
				GetMovebsInfoAck getMovebsInfoAck = Service.appGetMovebsInfoAck(getMovebsInfo);
				map.put("returnMessage", Object2Json(getMovebsInfoAck));
				return map;
			}else if("getownbsinfo".equals(cmdtype)){
				getOwnbsInfo = (GetOwnbsInfo) JSONObject.toBean(jsonObject, GetOwnbsInfo.class);
				GetOwnbsInfoAck getOwnbsInfoAck = Service.appGetOwnbsInfoAck(getOwnbsInfo);
				map.put("returnMessage", Object2Json(getOwnbsInfoAck));
				return map;
			}else if("getbstype".equals(cmdtype)){
				getBsType = (GetBsType) JSONObject.toBean(jsonObject, GetBsType.class);
				GetBsTypeAck getBsTypeAck = Service.appGetBsTypeAck(getBsType);
				map.put("returnMessage", Object2Json(getBsTypeAck));
				return map;
			}else if("heartbeat".equals(cmdtype)){
				map.put("returnMessage", "{\"cmdtype\":\"heartbeat\"}");			
				return map;
			}else if("getforgpsdst".equals(cmdtype)){
				getForGpsDst = (GetForGpsDst) JSONObject.toBean(jsonObject, GetForGpsDst.class);
				GetForGpsDstAck getForGpsDstAck = Service.appGetForGpsDstAck(getForGpsDst);
				map.put("returnMessage", Object2Json(getForGpsDstAck));
				return map;
			}else if("getallbslist".equals(cmdtype)){
				getAllBsList = (GetAllBsList) JSONObject.toBean(jsonObject, GetAllBsList.class);
				List<GetAllBsListAck> list = Service.appGetAllBsListAck(getAllBsList);
				map.put("returnMessage0", Object2Json(list.get(0)));
				map.put("returnMessage1", Object2Json(list.get(1)));
				map.put("returnMessage2", Object2Json(list.get(2)));
				map.put("returnMessage3", Object2Json(list.get(3)));
				map.put("returnMessage4", Object2Json(list.get(4)));
				map.put("returnMessage5", Object2Json(list.get(5)));
				map.put("returnMessage", "for");
				return map;
			}else if("gpsinfoup".equals(cmdtype)){
				gpsInfoUp = (GpsInfoUp) JSONObject.toBean(jsonObject, GpsInfoUp.class);
				Service.appInsertGpsInfoUp(gpsInfoUp);		
				map.put("returnMessage", "");
				return map;
			}else if("getallapplist".equals(cmdtype)){
				getAllAppList = (GetAllAppList) JSONObject.toBean(jsonObject, GetAllAppList.class);
				GetAllAppListAck getAllAppListAck = Service.appGetAllAppListAck(getAllAppList);
				map.put("returnMessage", Object2Json(getAllAppListAck));
				return map;
			}else if("getbsinfo".equals(cmdtype)){
				getBsInfo = (GetBsInfo) JSONObject.toBean(jsonObject, GetBsInfo.class);
				GetBsInfoAck getBsInfoAck = Service.appGetBsInfoAck(getBsInfo);
				map.put("returnMessage", Object2Json(getBsInfoAck));
				return map;
			}else if("geterrbsinfo".equals(cmdtype)){
				getErrBsInfo = (GetErrBsInfo) JSONObject.toBean(jsonObject, GetErrBsInfo.class);
				GetErrBsInfoAck getErrBsInfoAck = Service.appGetErrBsInfoAck(getErrBsInfo);
				map.put("returnMessage", Object2Json(getErrBsInfoAck));
				return map;
			}else if("getinspectbslist".equals(cmdtype)){
				getInspectBsList = (GetInspectBsList) JSONObject.toBean(jsonObject, GetInspectBsList.class);
				GetInspectBsListAck getInspectBsListAck = Service.appGetInspectBsListAck(getInspectBsList);
				map.put("returnMessage", Object2Json(getInspectBsListAck));
				return map;
			}else if("getuninspectbslist".equals(cmdtype)){
				getUnInspectBsList = (GetUnInspectBsList) JSONObject.toBean(jsonObject, GetUnInspectBsList.class);
				GetUnInspectBsListAck getUnInspectBsListAck = Service.appGetUnInspectBsListAck(getUnInspectBsList);
				map.put("returnMessage", Object2Json(getUnInspectBsListAck));
				return map;
			}else if("gettotalinfo".equals(cmdtype)){
				getTotalInfo = (GetTotalInfo) JSONObject.toBean(jsonObject, GetTotalInfo.class);
				GetTotalInfoAck getTotalInfoAck = Service.appGetTotalInfoAck(getTotalInfo);
				map.put("returnMessage", Object2Json(getTotalInfoAck));
				return map;
			}else if("gentable".equals(cmdtype)){
				genTable = (GenTable) JSONObject.toBean(jsonObject, GenTable.class);
				GenTableAck genTableAck = Service.appGenTableAck(genTable);
				map.put("returnMessage", Object2Json(genTableAck));
				return map;
			}else if("gentableack".equals(cmdtype)){
				genTableAck = (GenTableAck) JSONObject.toBean(jsonObject, GenTableAck.class);
				//更新状态
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("serialnumber",genTableAck.getSerialnumber());
				param.put("status",1);
				Service.updateGenTableStatus(param);
				map.put("returnMessage", "");
				return map;
			}else if("gencheck".equals(cmdtype)){
				genCheck = (GenCheck) JSONObject.toBean(jsonObject, GenCheck.class);
				String serialNum = genCheck.getSerialnumber();
				Map<String,String> paramMap = new HashMap<String,String>();
				paramMap.put("serialNum", serialNum);
				paramMap.put("status", "");
				Service.updateElecStatus(paramMap);
				//发送通知邮件通知网管组进行审核
				FunUtil.sendMsgToUserByGroupPowerWithoutReq("r_order",3,"发电审核","有发电审核，请查阅！",genCheck.getUserid());
				//GenCheckAck genCheckAck = Service.appGenCheckAck(genCheck);
				map.put("returnMessage", "");
				return map;
			}else if("getgenarg".equals(cmdtype)){
				getGenArg = (GetGenArg) JSONObject.toBean(jsonObject, GetGenArg.class);
				GetGenArgAck getGenArgAck = Service.appGetGenArgAck(getGenArg);
				map.put("returnMessage", Object2Json(getGenArgAck));
				return map;
			}else if("getpowerontime".equals(cmdtype)){
				getPowerOnTime = (GetPowerOnTime) JSONObject.toBean(jsonObject, GetPowerOnTime.class);
				GetPowerOnTimeAck getPowerOnTimeAck = Service.appGetPowerOnTimeAck(getPowerOnTime);
				map.put("returnMessage", Object2Json(getPowerOnTimeAck));
				return map;
			}
						
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * java对象转json字符串
	 */
	public static  String Object2Json(Object obj){  
        JSONObject json = JSONObject.fromObject(obj);//将java对象转换为json对象  
        String str = json.toString();//将json对象转换为字符串          
        return str;  
    }  
	
}
