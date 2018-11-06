package com.test;

import java.math.BigDecimal;

public class DataBean {
	private BigDecimal id;
	private String endtime2;
	private String endtime3;
	private String winstr;
	private String WinNumber;
	private String resetsec;
	
	
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getEndtime2() {
		return endtime2;
	}
	public void setEndtime2(String endtime2) {
		this.endtime2 = endtime2;
	}
	public String getEndtime3() {
		return endtime3;
	}
	public void setEndtime3(String endtime3) {
		this.endtime3 = endtime3;
	}
	public String getWinstr() {
		return winstr;
	}
	public void setWinstr(String winstr) {
		this.winstr = winstr;
	}
	public String getWinNumber() {
		return WinNumber;
	}
	public void setWinNumber(String winNumber) {
		WinNumber = winNumber;
	}
	public String getResetsec() {
		return resetsec;
	}
	public void setResetsec(String resetsec) {
		this.resetsec = resetsec;
	}
	@Override
	public String toString() {
		return "DataBean [id=" + id + ", endtime2=" + endtime2 + ", endtime3="
				+ endtime3 + ", winstr=" + winstr + ", WinNumber=" + WinNumber
				+ ", resetsec=" + resetsec + "]";
	}
	
	

}
