/**
 * 
 */
package com.web.service.communication;

/**
 * @author David
 *
 */
public class DatagramOffline {
	private String packlength;//包长度本消息报文的长度（不包括本字段本身长度）	N4
	private String synchmsg="0000";//同步信息由客户端定义的用于匹配请求消息的数据块	AN4
	private String compactflag = "0";//压缩标志报文内容压缩标志	N1
	private String encryptflag = "0";//加密算法加密算法标志	N1
	private String datagramversion = "01";//消息报文版本号N2
	private String datagrammsgtype = "5000";//消息报文类型  AN4

	private String transcversion = "10";//N2	交易版本号	M	M
	private String transcmessage;//N4	交易类型	M	M
	private String messagedatetime;//	YYYYMMDDhhmmss	报文发送时间	M	M
	private String mac;//H8	通讯押码	M	全0
	private String respcodecomm="00";//	H2	通讯应答码	全0	M
	private String retsign1="\r\n";//	S2	0x0d，0x0a	M	M

	private String respmessage;//报文返回的信息
	private String communicationkey;//通讯密钥
	
	public String getPacklength() {
		return packlength;
	}
	public void setPacklength(String packlength) {
		this.packlength = packlength;
	}
	public String getSynchmsg() {
		return synchmsg;
	}
	public void setSynchmsg(String synchmsg) {
		this.synchmsg = synchmsg;
	}
	public String getCompactflag() {
		return compactflag;
	}
	public void setCompactflag(String compactflag) {
		this.compactflag = compactflag;
	}
	public String getEncryptflag() {
		return encryptflag;
	}
	public void setEncryptflag(String encryptflag) {
		this.encryptflag = encryptflag;
	}
	public String getDatagramversion() {
		return datagramversion;
	}
	public void setDatagramversion(String datagramversion) {
		this.datagramversion = datagramversion;
	}
	public String getDatagrammsgtype() {
		return datagrammsgtype;
	}
	public void setDatagrammsgtype(String datagrammsgtype) {
		this.datagrammsgtype = datagrammsgtype;
	}
	public String getTranscversion() {
		return transcversion;
	}
	public void setTranscversion(String transcversion) {
		this.transcversion = transcversion;
	}
	public String getTranscmessage() {
		return transcmessage;
	}
	public void setTranscmessage(String transcmessage) {
		this.transcmessage = transcmessage;
	}
	public String getMessagedatetime() {
		return messagedatetime;
	}
	public void setMessagedatetime(String messagedatetime) {
		this.messagedatetime = messagedatetime;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getRespcodecomm() {
		return respcodecomm;
	}
	public void setRespcodecomm(String respcodecomm) {
		this.respcodecomm = respcodecomm;
	}
	public String getRetsign1() {
		return retsign1;
	}
	public void setRetsign1(String retsign1) {
		this.retsign1 = retsign1;
	}
	public String getRespmessage() {
		return respmessage;
	}
	public void setRespmessage(String respmessage) {
		this.respmessage = respmessage;
	}
	public String getCommunicationkey() {
		return communicationkey;
	}
	public void setCommunicationkey(String communicationkey) {
		this.communicationkey = communicationkey;
	}
}
