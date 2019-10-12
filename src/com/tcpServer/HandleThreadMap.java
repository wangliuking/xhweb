package com.tcpServer;

import cc.eguid.FFmpegCommandManager.test.TestFFmpegForWeb;
import com.tcpBean.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HandleThreadMap {
}
class GetPowerOnTimeThread extends Thread{
    private GetPowerOnTime getPowerOnTime;
    public GetPowerOnTimeThread(GetPowerOnTime getPowerOnTime){
        this.getPowerOnTime = getPowerOnTime;
    }
    @Override
    public void run() {
        GetPowerOnTimeAck getPowerOnTimeAck = Service.appGetPowerOnTimeAck(getPowerOnTime);
        ServerDemo.startMessageThread(getPowerOnTime.getUserid(),getPowerOnTimeAck);
    }
}
class GetGenArgThread extends Thread{
    private GetGenArg getGenArg;
    public GetGenArgThread(GetGenArg getGenArg){
        this.getGenArg = getGenArg;
    }
    @Override
    public void run() {
        GetGenArgAck getGenArgAck = Service.appGetGenArgAck(getGenArg);
        ServerDemo.startMessageThread(getGenArg.getUserid(),getGenArgAck);
    }
}

class GetBsInfoThread extends Thread{
    private GetBsInfo getBsInfo;
    public GetBsInfoThread(GetBsInfo getBsInfo){
        this.getBsInfo = getBsInfo;
    }
    @Override
    public void run() {
        LinkedList<GetBsInfoAck> list = Service.appGetBsInfoAck(getBsInfo);
        for (int i=0;i<list.size();i++) {
            ServerDemo.startMessageThread(getBsInfo.getUserid(),list.get(i));
        }
    }
}

class SendUnsentMessageThread extends Thread{
    private GetUnsentMessage getUnsentMessage;
    public SendUnsentMessageThread(GetUnsentMessage getUnsentMessage){
        this.getUnsentMessage = getUnsentMessage;
    }
    @Override
    public void run() {
        try {
            String userId = getUnsentMessage.getUserid();
            GetUnsentMessageAck getUnsentMessageAck = new GetUnsentMessageAck();
            getUnsentMessageAck.setUserid(userId);
            getUnsentMessageAck.setSerialnumber(getUnsentMessage.getSerialnumber());

            //查询此userId是否有未发送的消息
            if(ServerDemo.unsentMessageList.containsKey(userId)){
                getUnsentMessageAck.setAck("1");
                ServerDemo.startUnmessageThread(userId,Util.Object2Json(getUnsentMessageAck));
                LinkedList<String> list = ServerDemo.unsentMessageList.get(userId);
                for(int i=0;i<list.size();i++){
                    System.out.println("准备发送未收到的消息！！！！！ "+list.get(i));
                    ServerDemo.startUnmessageThread(userId,list.get(i));
                    Thread.sleep(200);
                }
                ServerDemo.unsentMessageList.remove(userId);
            }else{
                getUnsentMessageAck.setAck("0");
                ServerDemo.startUnmessageThread(userId,Util.Object2Json(getUnsentMessageAck));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class WaitStreamThread extends Thread{
    private PushIPCStream pushIPCStream;
    public WaitStreamThread(PushIPCStream pushIPCStream){
        this.pushIPCStream = pushIPCStream;
    }
    @Override
    public void run() {
        PushIPCStreamAck pushIPCStreamAck = Service.appPushIPCStreamAck(pushIPCStream);
        System.out.println("pushIPCStream : "+pushIPCStream);
        ServerDemo.startMessageThread(pushIPCStream.getUserid(),pushIPCStreamAck);
    }
}

class PushStreamThread extends Thread{
    private Map<String,Object> map;
    public PushStreamThread(Map<String,Object> map){
        this.map = map;
    }

    @Override
    public void run() {
        try {
            //设置标志位0-无基站流 1-有基站流
            int status = 0;
            List<Map<String, String>> appStreamList = TestFFmpegForWeb.appStreamList;
            System.out.println(" appStreamList : " + appStreamList);
            System.out.println("map为："+map);
            String bsId = map.get("bsId")+"";
            for (int i=0;i<appStreamList.size();i++) {
                Map<String,String> tMap = appStreamList.get(i);
                if (bsId.equals(tMap.get("bsId"))) {
                    status = 1;
                }
            }

            Map<String,String> param = new HashMap<String,String>();
            param.put("userId", map.get("userId")+"");
            param.put("bsId", map.get("bsId")+"");
            TestFFmpegForWeb.appStreamList.add(param);

            // 根据status的值判断是否需要推流
            if (status == 0) {
                System.out.println("准备开启基站视频流！！！");
                TestFFmpegForWeb.testApp(map);
            }else{
                //有流，把状态改为true
                Custom.streamStatus = true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class StopIPCStreamThread extends Thread{
    private String userId;
    public StopIPCStreamThread(String userId){
        this.userId = userId;
    }

    @Override
    public void run() {
        try {
            //设置标志位0-无用户使用 1-有用户使用
            int status = 0;
            List<Map<String, String>> appStreamList = TestFFmpegForWeb.appStreamList;
            System.out.println(" appStreamList : " + appStreamList);
            String bsId = "";
            int index = -1;
            for (int i=0;i<appStreamList.size();i++) {
                Map<String,String> map = appStreamList.get(i);
                if (userId.equals(map.get("userId"))) {
                    bsId = map.get("bsId");
                    index = i;
                }
            }
            for (Map<String, String> map : appStreamList) {
                if (bsId.equals(map.get("bsId")) && !userId.equals(map.get("userId"))) {
                    // 还有用户在使用此基站流
                    status = 1;
                }
            }
            System.out.println("查询的userId : "+userId+" 查询的bsId : "+bsId);
            // 根据status的值判断是否需要关闭推流
            if(index != -1){
                appStreamList.remove(index);
            }
            if (status == 0 && bsId != "") {
                System.out.println("准备关闭基站视频流！！！");
                TestFFmpegForWeb.stopApp(bsId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
