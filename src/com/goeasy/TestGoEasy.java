package com.goeasy;

import io.goeasy.GoEasy;

public class TestGoEasy {
	public static void main(String[] args) {
		GoEasy goEasy = new GoEasy("rest-hangzhou.goeasy.io", "BC-88e3350dfcc3428ba5abd490e095402e");
		goEasy.publish("alarmPush", "无敌了!");
	}
}
