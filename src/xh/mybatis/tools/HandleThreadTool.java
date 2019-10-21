package xh.mybatis.tools;

import cc.eguid.FFmpegCommandManager.test.TestFFmpegForWeb;

public class HandleThreadTool {
}

class StopStreamTaskThread extends Thread{
    private String bsId;
    public StopStreamTaskThread(String bsId){
        this.bsId = bsId;
    }

    @Override
    public void run() {
        try {
            TestFFmpegForWeb.stop(bsId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
