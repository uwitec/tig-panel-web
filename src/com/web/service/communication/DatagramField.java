package com.web.service.communication;

public class DatagramField {
	public static enum Enum_type{
		B,A,N,S,AN,ANS,MMDD,YYYYMMDD,HHMMSS,YYYYMMDDHHMMSS,
		ALLVAR, NLLVAR, ANLLVAR, ANSLLVAR,
		ALLLVAR, NLLLVAR, ANLLLVAR, ANSLLLVAR,
		BLLVAR, BLLLVAR,
		CHINESE, CHINESELLVAR, CHINESELLLVAR,ANSLLVAROTHER,ANSLLLVAROTHER,ANSOTHER
	};
	public static enum Enum_pending_dir{LEADING, TRAILING};
	public static enum Enum_format{BCD,BINARY,ASCII,HEX,ASCIIOTHER};
	
	private String property;
	private Enum_type type;
	private Enum_pending_dir pendingdir;
	private Character pendingchar;
	private String length;
	private Enum_format format;
	private String defaultvalue;
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Enum_type getType() {
		return type;
	}
	public void setType(Enum_type type) {
		this.type = type;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public Enum_format getFormat() {
		return format;
	}
	public void setFormat(Enum_format format) {
		this.format = format;
	}
	public String getDefaultvalue() {
		return defaultvalue;
	}
	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}
	public Enum_pending_dir getPendingdir() {
		return pendingdir;
	}
	public void setPendingdir(Enum_pending_dir pendingdir) {
		this.pendingdir = pendingdir;
	}
	public Character getPendingchar() {
		return pendingchar;
	}
	public void setPendingchar(Character pendingchar) {
		this.pendingchar = pendingchar;
	}
}
