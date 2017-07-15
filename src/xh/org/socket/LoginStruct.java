package xh.org.socket;

import java.util.Map;

/*"account":"dev3",

"mcd":

{

“operate”：1

},

"name":null,

"password":"abcd1234",

"type":2,

"usertype":2*/
public class LoginStruct {
	private String account;
	private Map<String,Object> mcd;
	/*private String name;*/
	private String password;
	private int type=2;
	private int usertype=2;
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Map<String, Object> getMcd() {
		return mcd;
	}

	public void setMcd(Map<String, Object> mcd) {
		this.mcd = mcd;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	@Override
	public String toString() {
		return "LoginStruct [account=" + account + ", mcd=" + mcd
				+ ", password=" + password + ", type=" + type + ", usertype="
				+ usertype + "]";
	}
	
	
	
	

}
