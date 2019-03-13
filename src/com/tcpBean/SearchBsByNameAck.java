package com.tcpBean;

import java.util.LinkedList;
import java.util.Map;

public class SearchBsByNameAck {
	private String cmdtype = "searchbsbynameack";
    private String userid;
    private LinkedList<Map<String,String>> bslist;
	public String getCmdtype() {
		return cmdtype;
	}
	public void setCmdtype(String cmdtype) {
		this.cmdtype = cmdtype;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public LinkedList<Map<String, String>> getBslist() {
		return bslist;
	}
	public void setBslist(LinkedList<Map<String, String>> bslist) {
		this.bslist = bslist;
	}
	@Override
	public String toString() {
		return "SearchBsByNameAck [cmdtype=" + cmdtype + ", userid=" + userid
				+ ", bslist=" + bslist + "]";
	}
    
    
}
