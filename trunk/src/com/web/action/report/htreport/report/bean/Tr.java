package com.web.action.report.htreport.report.bean;

public class Tr {
	private Td[] tds;
	
	public Tr(int size){
		tds=new Td[size];
	}
	
	public Td[] getTds() {
		return tds;
	}
	public void setTds(Td[] tds) {
		this.tds = tds;
	}
}
