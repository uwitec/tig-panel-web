package com.web.service.communication;

import java.util.ArrayList;
import java.util.List;

public class DatagramConfig {
	public DatagramConfig(){
		
	}
	public DatagramConfig(DatagramConfig config){
		this.name = config.getName();
		this.classname = config.getClassname();
		this.fields = new ArrayList<DatagramField>();
		for(DatagramField field : config.getFields()){
			DatagramField field1 = new DatagramField();
			field1.setDefaultvalue(field.getDefaultvalue());
			field1.setFormat(field.getFormat());
			field1.setLength(field.getLength());
			field1.setProperty(field.getProperty());
			field1.setType(field.getType());
			field1.setPendingchar(field.getPendingchar());
			field1.setPendingdir(field.getPendingdir());
			this.fields.add(field1);
		}
	}
	
	public static enum Datagram_Type{
		ONLINE, OFFLINE
	}
	
	private String name;
	private String classname;
	private List<DatagramField> fields;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public List<DatagramField> getFields() {
		return fields;
	}
	public void setFields(List<DatagramField> fields) {
		this.fields = fields;
	}
}
