package com.tcpServer;

import java.util.*;

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
	private static SearchBsByName searchBsByName;
	private static ReceiveOrder receiveOrder;
	private static GetOrderInfo getOrderInfo;
	private static GenOffCheck genOffCheck;
	private static ReceiveTable receiveTable;
	private static GetTableInfo getTableInfo;
	private static PushIPCStream pushIPCStream;
	private static StopIPCStream stopIPCStream;
	private static GetUnsentMessage getUnsentMessage;
	
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
				paramMap.put("status", "0");
				paramMap.put("break_order", "");
				Service.updateUserStatus(paramMap);
				//更新四方伟业库
				Map<String,Object> orderMap = OrderService.selectBySerialnumber(serialNum);
				ErrProTable bean = new ErrProTable();
				bean.setBsid(orderMap.get("bsid")+"");
				bean.setZbdldm(orderMap.get("zbdldm")+"");
				bean.setStatus("接单中");
				System.out.println(bean);
				OrderService.updateSfOrder(bean);
				map.put("returnMessage", "");
				return map;
			}else if("errcheck".equals(cmdtype)){
				errCheck = (ErrCheck) JSONObject.toBean(jsonObject, ErrCheck.class);
				if("1".equals(errCheck.getHungorder())){
					//app提交了挂单
					Map<String,String> paramMap = new HashMap<String,String>();
					paramMap.put("serialNum", errCheck.getSerialnumber());
					paramMap.put("status", "-2");
					paramMap.put("break_order", "1");
					Service.updateUserStatus(paramMap);

					ErrCheckAck bean = new ErrCheckAck();
					bean.setSerialnumber(errCheck.getSerialnumber());
					bean.setUserid(errCheck.getUserid());
					bean.setResult("0");
					//发送通知邮件通知网管组进行审核
					FunUtil.sendMsgToUserByGroupPowerWithoutReq("r_order",3,"派单审核","有挂单情况",errCheck.getUserid());
					map.put("returnMessage", Object2Json(bean));
				}else{
					String serialNum = errCheck.getSerialnumber();
					Map<String,String> paramMap = new HashMap<String,String>();
					paramMap.put("serialNum", serialNum);
					paramMap.put("status", "2");
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
				for (int i=0;i<list.size();i++) {
					String key = "returnMessage"+i;
					map.put(key, Object2Json(list.get(i)));
				}
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
				new GetBsInfoThread(getBsInfo).start();
				map.put("returnMessage", "");
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
				/*String prostate = genTableAck.getProstate();
				Map<String,Object> param = new HashMap<String,Object>();
				if("0".equals(prostate)){
					param.put("serialnumber",genTableAck.getSerialnumber());
					param.put("status",1);
					Service.updateGenTableStatus(param);
				}*/
				map.put("returnMessage", "");
				return map;
			}else if("gencheck".equals(cmdtype)){
				genCheck = (GenCheck) JSONObject.toBean(jsonObject, GenCheck.class);
				String serialnumber = genCheck.getSerialnumber();
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("serialnumber", serialnumber);
				paramMap.put("status", 2);
				Service.updateElecStatus(paramMap);
				//发送通知邮件通知网管组进行审核
				FunUtil.sendMsgToUserByGroupPowerWithoutReq("r_order",3,"发电审核","有发电审核，请查阅！",genCheck.getUserid());
				//GenCheckAck genCheckAck = Service.appGenCheckAck(genCheck);
				map.put("returnMessage", "");
				return map;
			}else if("getgenarg".equals(cmdtype)){
				getGenArg = (GetGenArg) JSONObject.toBean(jsonObject, GetGenArg.class);
				new GetGenArgThread(getGenArg).start();
				map.put("returnMessage", "");
				return map;
			}else if("getpowerontime".equals(cmdtype)){
				getPowerOnTime = (GetPowerOnTime) JSONObject.toBean(jsonObject, GetPowerOnTime.class);
				new GetPowerOnTimeThread(getPowerOnTime).start();
				map.put("returnMessage", "");
				return map;
			}else if("searchbsbyname".equals(cmdtype)){
				searchBsByName = (SearchBsByName) JSONObject.toBean(jsonObject, SearchBsByName.class);
				SearchBsByNameAck searchBsByNameAck = Service.appSearchBsByNameAck(searchBsByName);
				map.put("returnMessage", Object2Json(searchBsByNameAck));
				return map;
			}else if("receiveorder".equals(cmdtype)){
				receiveOrder = (ReceiveOrder) JSONObject.toBean(jsonObject, ReceiveOrder.class);
				ReceiveOrderAck receiveOrderAck = Service.appReceiveOrderAck(receiveOrder);
				String str = receiveOrderAck.getHandlepower();
				if("2".equals(str)){
					//该单号已接单
					ServerDemo demo=new ServerDemo();
					demo.startMessageThread(receiveOrder.getUserid(),receiveOrderAck);
				}else{
					List<String> list = Arrays.asList(str.split(","));
					if(list.size()>0){
						ServerDemo demo=new ServerDemo();
						for(String user : list){
							receiveOrderAck.setUserid(user);
							receiveOrderAck.setHandleusername(receiveOrder.getHandleusername());
							if(user.equals(receiveOrder.getUserid())){
								receiveOrderAck.setHandlepower("1");
							}else{
								receiveOrderAck.setHandlepower("2");
							}
							System.out.println(receiveOrderAck);
							demo.startMessageThread(user,receiveOrderAck);
						}
					}
				}
				map.put("returnMessage", "");
				return map;
			}else if("getorderinfo".equals(cmdtype)){
				getOrderInfo = (GetOrderInfo) JSONObject.toBean(jsonObject, GetOrderInfo.class);
				GetOrderInfoAck getOrderInfoAck = Service.appGetOrderInfoAck(getOrderInfo);
				map.put("returnMessage", Object2Json(getOrderInfoAck));
				return map;
			}else if("genoffcheck".equals(cmdtype)){
				genOffCheck = (GenOffCheck) JSONObject.toBean(jsonObject, GenOffCheck.class);
				String serialnumber = genOffCheck.getSerialnumber();
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("serialnumber", serialnumber);
				paramMap.put("status", 4);
				Service.updateElecStatus(paramMap);
				//发送通知邮件通知网管组进行审核
				FunUtil.sendMsgToUserByGroupPowerWithoutReq("r_order",3,"发电结束审核","有发电结束审核，请查阅！",genCheck.getUserid());
				//GenCheckAck genCheckAck = Service.appGenCheckAck(genCheck);
				map.put("returnMessage", "");
				return map;
			}else if("receivetable".equals(cmdtype)){
				receiveTable = (ReceiveTable) JSONObject.toBean(jsonObject, ReceiveTable.class);
				ReceiveTableAck receiveTableAck = Service.appReceiveTableAck(receiveTable);
				String str = receiveTableAck.getHandlepower();
				if("2".equals(str)){
					//该单号已接单
					ServerDemo demo=new ServerDemo();
					demo.startMessageThread(receiveTable.getUserid(),receiveTableAck);
				}else{
					List<String> list = Arrays.asList(str.split(","));
					if(list.size()>0){
						ServerDemo demo=new ServerDemo();
						for(String user : list){
							receiveTableAck.setUserid(user);
							receiveTableAck.setHandleusername(receiveTable.getHandleusername());
							if(user.equals(receiveTable.getUserid())){
								receiveTableAck.setHandlepower("1");
							}else{
								receiveTableAck.setHandlepower("2");
							}
							//System.out.println(receiveTableAck);
							demo.startMessageThread(user,receiveTableAck);
						}
					}
				}
				map.put("returnMessage", "");
				return map;
			} else if("gettableinfo".equals(cmdtype)){
				getTableInfo = (GetTableInfo) JSONObject.toBean(jsonObject, GetTableInfo.class);
				GetTableInfoAck getTableInfoAck = Service.appGetTableInfoAck(getTableInfo);
				map.put("returnMessage", Object2Json(getTableInfoAck));
				return map;
			}else if("pushIPCStream".equals(cmdtype)){
				pushIPCStream = (PushIPCStream) JSONObject.toBean(jsonObject, PushIPCStream.class);
				new WaitStreamThread(pushIPCStream).start();
				map.put("returnMessage", "");
				return map;
			}else if("stopIPCStream".equals(cmdtype)){
				stopIPCStream = (StopIPCStream) JSONObject.toBean(jsonObject, StopIPCStream.class);
				StopIPCStreamAck stopIPCStreamAck = Service.appStopIPCStreamAck(stopIPCStream);
				map.put("returnMessage", Object2Json(stopIPCStreamAck));
				return map;
			}else if("getunsentmessage".equals(cmdtype)){
				getUnsentMessage = (GetUnsentMessage) JSONObject.toBean(jsonObject, GetUnsentMessage.class);
				new SendUnsentMessageThread(getUnsentMessage).start();
				map.put("returnMessage", "");
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