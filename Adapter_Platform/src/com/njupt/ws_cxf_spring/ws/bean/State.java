package com.njupt.ws_cxf_spring.ws.bean;

public class State {
	String relationid;
	String openstate;
	String savetime;
	public State(String relationid, String openstate, String savetime) {
		super();
		this.relationid = relationid;
		this.openstate = openstate;
		this.savetime = savetime;
	}
	public String getRelationid() {
		return relationid;
	}
	public void setRelationid(String relationid) {
		this.relationid = relationid;
	}
	public String getOpenstate() {
		return openstate;
	}
	public void setOpenstate(String openstate) {
		this.openstate = openstate;
	}
	public String getSavetime() {
		return savetime;
	}
	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}
	@Override
	public String toString() {
		return "State [relationid=" + relationid + ", openstate=" + openstate + ", savetime=" + savetime + "]";
	}
	
}
