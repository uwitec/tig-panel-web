package com.web.action.report.htreport.comm;

import java.util.List;

public class PagingBean {
	private int total;
	private List entitys;
	
	public PagingBean(int total,List entitys){
		this.total=total;
		this.entitys=entitys;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	public List getEntitys() {
		return entitys;
	}
	public void setEntitys(List entitys) {
		this.entitys = entitys;
	}
	
}
