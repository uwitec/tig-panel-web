
package com.application.webserver;

import java.util.Map;



/**
 * Comments:	参数定义
 * Author:	 	
 *
 */
public class Constant{
	
	//时间的format
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYYMMDDSS = "yyyyMMddss";
	public static final String HH_MM = "HH:mm";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM_SS_MMM = "yyyy-MM-dd HH:mm:ss:mmm";
	
	//定时器中处理的事情
	public static final int DEBITCASHQUARTZ = 1;
	public static final int BUSSOCKET = 2;//二级控制台的socket
	public static final int CARDOVERSERVERQUARTZ = 3;//清算控制台的缴卡处理
	//public static final int CCHSSOCKET = 2;//清算控制台的sockt
	public static final int SENDADJQUARTZ = 4;//清算控制台的缴卡处理
	public static final int CENTERSOCKET = 5;//清算控制台的socket
	public static final int BUSUNCOLLECT = 6;//未采集车辆的处理
	public static final int BATTRADEDTLFLOAT = 7;//公交现金平衡表浮动
	public static final int INSURANCEDZPFPROCESS = 8;//保险单证派发审核处理
	
	//报文返回的错误
	public static final String Success = "00000";//正确
	public static final String MsgLengthError = "10001";//报文长度错
	public static final String MsgHeadError = "10002";//报文头错
	public static final String ServerSqlError = "10003";//Socket服务端操作数据库错误
	public static final String CheckCodeError = "00003";//校验码错
	public static final String CardActError = "00004";//卡账户错误,不存在或找到多条记录
	public static final String CardTypeError = "00005";//不为月票卡不能登记卡套损坏
	
	//缴卡中的领卡类型
	public static final int CARDOVERGETFLAG0 = 0;//未领取
	public static final int CARDOVERGETFLAG1 = 1;//正常领取
	public static final int CARDOVERGETFLAG2 = 2;//强制领取
	public static final int CARDOVERGETFLAG3 = 3;//取消登记
	

	
}
