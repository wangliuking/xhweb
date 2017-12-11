package xh.org.listeners;

import java.util.regex.Pattern;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String fileName="100.doc";
		System.out.println(isInteger(fileName.split("\\.")[0]));
		
		
	}
	
	public static boolean isInteger(String str) {  
        /*Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");*/  
		Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();  
        
  }

}
