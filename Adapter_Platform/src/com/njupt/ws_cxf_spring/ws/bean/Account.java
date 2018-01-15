package com.njupt.ws_cxf_spring.ws.bean;

public class Account {
	String consume;
	String savetime;
	public Account(String consume, String savetime) {
		super();
		this.consume = consume;
		this.savetime = savetime;
	}
	public String getConsume() {
		return consume;
	}
	public void setConsume(String consume) {
		this.consume = consume;
	}
	public String getSavetime() {
		return savetime;
	}
	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}
	@Override
	public String toString() {
		return "Account [consume=" + consume + ", savetime=" + savetime + "]";
	}
	
}
