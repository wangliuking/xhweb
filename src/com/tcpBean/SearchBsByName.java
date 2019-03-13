package com.tcpBean;

public class SearchBsByName {
	 	private String cmdtype = "searchbsbyname";
	    private String userid;
	    private String bsname;
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
		public String getBsname() {
			return bsname;
		}
		public void setBsname(String bsname) {
			this.bsname = bsname;
		}
		@Override
		public String toString() {
			return "SearchBsByName [cmdtype=" + cmdtype + ", userid=" + userid
					+ ", bsname=" + bsname + "]";
		}
	    
	    
}
