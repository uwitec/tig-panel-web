/**
 * 
 */
package com.web.service.communication;

/**
 * @author David
 *
 */
public class CommunicationErrorcode {
	/**
	 * 通信错误
	 */
	public static final String SOCKETCONNFAILED  = "exception.commconnectionfailed";//通讯连接失败
	public static final String SOCKETNORESPONSE  = "exception.commnoresponse";//系统后台无应答
	public static final String SOCKETRECVERROR   = "exception.commrecvdatafailed";//数据接收失败
	public static final String DATAGRAMTYPEERROR = "exception.commdatagramtypeerror";//报文类型不正确
	public static final String RESPCODEERROR     = "exception.commrespcodeerror";//应答码错误
	public static final String HOSTIPERROR       = "exception.commaddressparsefailed";//通讯地址解析失败
	public static final String PROTOCOLERROR     = "exception.commprotocolerror";//通讯协议出错
	public static final String SOCKETSENDERROR   = "exception.commsenddatafailed";//数据发送失败
	public static final String CLOSEINPUTBUFERROR   = "exception.closeinputbuferror";//关闭输入流通道异常
	public static final String CLOSEOUTPUTBUFERROR   = "exception.closeoutputbuferror";//关闭输出流通道异常
	public static final String CLOSESOCKETERROR   = "exception.closesocketerror";//关闭SOCKET通道异常
	
	
	/**
	 * 报文解析错误
	 */
	public static final String CONFIGINCORRECT = "exception.commparse.configincorrect";//报文配置对象异常
	public static final String DATAGRAMCLASSNOTMATCH = "exception.commparse.datagramclassnotmatch";//报文对象类型不匹配
	public static final String DATAGRAMFIELDLENGTHERROR = "exception.commparse.datagramfieldlengtherror";//报文域长度错误
	public static final String DATAGRAMFIELDTYPEFORMATNOTMATCH = "exception.commparse.datagramfieldtypeformatnotmatch";//报文域的类型与格式不匹配
	public static final String ACTUALBYTESGREATERTHANSETBYTES = "exception.commparse.actualbytesgreaterthansetbytes";//实际字节数大于设定字节数
	public static final String DATAGRAMLENGTHERROR = "exception.commparse.datagramlengtherror";//实际字节数大于设定字节数
}
