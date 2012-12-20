package com.web.action.report.htreport.domain;


public class ReportDictCode extends BaseBean{ 

	private String dictId    ;
	private String codeValue ;
	private String codeDesc  ;
	private String priority  ;
	
	public ReportDictCode(){
		
	}
	
	public ReportDictCode(String codeValue,String codeDesc,String priority){
		this.codeValue=codeValue;
		this.codeDesc=codeDesc;
		this.priority=priority;
	}
	
	public String getCodeValue() {
		return codeValue;
	}
	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}
	public String getCodeDesc() {
		return codeDesc;
	}
	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}

}
