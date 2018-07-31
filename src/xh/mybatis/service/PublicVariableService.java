package xh.mybatis.service;

public class PublicVariableService {
	//告警数目
	private static int voiceAlarmCount=0;

	public static int getVoiceAlarmCount() {
		return voiceAlarmCount;
	}

	public static void setVoiceAlarmCount(int voiceAlarmCount) {
		PublicVariableService.voiceAlarmCount = voiceAlarmCount;
	}
	

	
	
	
	

}
