package xh.springmvc.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.chinamobile.fsuservice.Test;
import xh.func.plugin.FlexJSON;
import xh.mybatis.service.GosuncnService;
import cc.eguid.FFmpegCommandManager.test.TestFFmpegForWeb;
/**
 * 数据获取测试类
 * @author 12878
 *
 */
@Controller
@RequestMapping(value = "/camera")
public class ADataController {
	private boolean success;
	private String message;
	public static String id;
	protected final Log log = LogFactory.getLog(ADataController.class);
	private FlexJSON json=new FlexJSON();	
	private Timer timer = new Timer();
	private Timer timer1 = new Timer();	
	private Timer timer2 = new Timer();	
	
	/**
	 * 开启视频流
	 */
	@RequestMapping("/startById")
	public void oneBsInfo(HttpServletRequest request, HttpServletResponse response){
		this.success=true;
		String bsId = request.getParameter("bsId");
		String window = request.getParameter("window");
		Map<String, Object> map = new HashMap<String,Object>();
		Map<String, Object> cameraMap = new HashMap<String,Object>();
		if(Integer.parseInt(bsId)<2000){
			map.put("bsId", bsId);
			List<Map<String,Object>> listMap = GosuncnService.selectCameraIpByBsId(map);
			cameraMap = listMap.get(0);
			cameraMap.put("window", window);
		}else{
			map.put("bsId", bsId);
			List<Map<String,Object>> listMap = GosuncnService.selectCameraIpByVpn(map);
			cameraMap = listMap.get(0);
			cameraMap.put("window", window);
		}				
		try {
			TestFFmpegForWeb.test1(cameraMap);
			//timer.schedule(new timerTaskForStop(), 3*60*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询环控状态
	 */
	@RequestMapping("/getEMHStatus")
	public void getEMHStatus(HttpServletRequest request, HttpServletResponse response){
		String bsId = request.getParameter("bsId");
		this.success=true;
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("bsId",bsId);
		List<Map<String,Object>> list = GosuncnService.getEMHStatus(param);

		response.setContentType("application/json;charset=utf-8");
		String jsonstr = json.Encode(list);
		try {
			response.getWriter().write(jsonstr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

/**
 * 维持心跳
 */
class timerTaskForStop extends TimerTask {
	@Override
	public void run() {
		String id = ADataController.id;
		// TODO Auto-generated method stub
		try {
			TestFFmpegForWeb.stop(id);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

