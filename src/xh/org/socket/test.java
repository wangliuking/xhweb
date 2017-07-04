package xh.org.socket;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("point->"
				+ tt("{a:1,b:2,c:{ab:1,ac:2}}{a:1,b:2,c:{ab:1,ac:2}}{a:1"));
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
		return astr.toString();
	}

}
