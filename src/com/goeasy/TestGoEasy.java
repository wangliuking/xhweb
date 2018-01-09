package com.goeasy;

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;

public class TestGoEasy {
	public static void main(String[] args) {
		GoEasy goEasy = new GoEasy("rest-hangzhou.goeasy.io",
				"BC-88e3350dfcc3428ba5abd490e095402e");
		goEasy.publish("alarmPush", "!", new PublishListener() {
			@Override
			public void onSuccess() {
				System.out.print("消息发布成功。");
			}

			@Override
			public void onFailed(GoEasyError error) {
				System.out.print("消息发布失败, 错误编码：" + error.getCode() + " 错误信息： "
						+ error.getContent());
			}
		});
		
	}
}
