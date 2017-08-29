package xh.org.socket;

import xh.func.plugin.Base64Util;
import cn.com.scca.signgw.api.SccaGwSDK;

public class test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(Base64Util.decode("MTIzM2RmZHNnc2Rnc2dzZ3NkZ2dzMjMy"));
		dd();
		
	}
	//11,42,52,16,19,25,26,56,48,34,17,51,32,22,30,30,76,74
	public static void dd(){
		
		int[] as={11,42,52,16,19,25,26,56,48,34,17,51,32,22,30,30,76,74};
		int  win=0;
		int lose=0;
		for(int j=0;j<as.length;j++){
			int r=as[j];
			if(r>80){
				float a=36;
				float b=0;
				for(int i=1;i<=29;i++){
					a=(float) (a*1.4);
					b+=a;
					//System.out.println("a="+a);
					
				}
				System.out.println("=====r="+r+"========="+j+"===============");
				
                lose+=b;
				
				System.out.println("亏损===="+b);
				
				
				
				
				
			}else{
				float a=36;
				float b=0;
				for(int i=1;i<=r;i++){
					a=(float) (a*1.4);
					b+=a;
					//System.out.println("a="+a);
					
				}
				System.out.println("=====r="+r+"========="+j+"===============");
				System.out.println("a=="+a);
				System.out.println(b);
				System.out.println("result=="+j+"="+(a*20-b));
				win+=(a*20-b);
				
				
			}
		}
		System.out.println("=============================================================");
		System.out.println("win===="+win);
		System.out.println("lose===="+lose);
		System.out.println("结果===="+(win-lose));
		
	/*	
		
		for(int i=1;i<=40;i++){
			a=(float) (a*1.2);
			b+=a;
			System.out.println("a="+a);
			
		}
		System.out.println("last="+(a*20-b));
		System.out.println(b);*/
	}
	

	public static String tt(String str) {
		int lastPos = 0;
		int a = 0;
		StringBuffer astr = new StringBuffer();
		if (str.length() == 0) {
			return "";
		}
		if (!str.startsWith("{")) {
			return "";
		}
		for (int i = 0; i < str.length(); i++) {
			if (String.valueOf(str.charAt(i)).equals("{")) {
				a++;
				/* System.out.println("point-> a++"+a); */
			} else if (String.valueOf(str.charAt(i)).equals("}")) {
				a--;
				/* System.out.println("point-> a--"+a); */
				if (a == 0) {
					astr.append(str.substring(lastPos, i + 1) + ";");
					lastPos = i + 1;
				}
			} else {

			}

		}
		if (lastPos < str.length()) {
			astr.append(str.substring(lastPos, str.length()));
		}
		System.out.println("a===>"+a);
		return astr.toString();
	}

}
