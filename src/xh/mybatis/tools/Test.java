package xh.mybatis.tools;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import xh.mybatis.service.WebLogService;

public class Test {
	
	public static void main(String[] args) {
		List<Map<String, Object>> list=WebLogService.test();
		
		System.out.println("list:"+Arrays.toString(list.toArray()));
	}

}
