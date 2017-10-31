package com.chinamobile.lscservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
	List<String> list = ThreadPoolTest.createList();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ThreadPoolTest t = new ThreadPoolTest();
		t.test();
	}

	public void test() {
		ExecutorService es = Executors.newFixedThreadPool(1000);
		for (int i = 0; i < 10; i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					String thr = Thread.currentThread().getName().substring(14);
					System.out.println( thr+ "  "
							+ list.get(Integer.parseInt(thr)-1));
				}
			});
		}
		es.shutdown();
	}

	public static List<String> createList() {
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		list.add("eee");
		list.add("fff");
		list.add("ggg");
		list.add("hhh");
		list.add("iii");
		list.add("jjj");
		return list;
	}

}
