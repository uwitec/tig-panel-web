package com.web.action.report.htreport.util;

import java.sql.*;
import java.text.*;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;
import java.util.GregorianCalendar;

import com.sun.org.apache.bcel.internal.generic.NEW;


/**
 * <p>Title: 获取时间的工具类</p>
 * <p>Description: 采用统一的时间获取类，目的是将时间进行统一</p>
 */
public class DateUtil {
  public DateUtil(){}


  private static String setMsgContent(String msg,String content){
	  StringBuffer buffer=new StringBuffer(msg);
	  buffer.append(content);
	  return buffer.substring(buffer.length()-msg.length(), buffer.length());  
  }
  
  
  /**
   * 功能：当天是当年的第几天
   */
  public static String getDayOfYear() {
	  Calendar cal=Calendar.getInstance(); 
	  int dayno=cal.get(Calendar.DAY_OF_YEAR);
	  return setMsgContent("000",""+(dayno-1));
  } 
  
  /**
   * 功能：计算某一天是当年的第几天 strdate"yyyyMMdd"
   * @throws Exception 
   */
  public static String getDayOfYear(String strdate) throws Exception{
	  Calendar cal=Calendar.getInstance(); 
	  strdate=strdate.substring(0,4)+"-"+strdate.substring(4,6)+"-"+strdate.substring(6,8);
	  try{
		cal.setTime(getDate(strdate));
	  }catch(Exception e) {
		System.out.println("getDayOfYear:"+e);
		throw new Exception();
	  }
	  int dayno=cal.get(Calendar.DAY_OF_YEAR);
	  return setMsgContent("000",""+(dayno-1));
  }
  
  
  
 /**
   * 功能：取应用服务器日期并以"yyyy-MM-dd HH:mm:ss"格式返回
   */
  public static String getDateTime() {
	  return getCurrentDateByFormat("yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 功能：取应用服务器日期并以"yyyy-MM-dd格式返回
   */
  public static String getDateStr() {
	  return getCurrentDateByFormat("yyyy-MM-dd");
  }
  
  /**
   * 功能：取应用服务器时间并以"yyyyMMddHHmmss"格式返回
   */
  public static String getDateTimeForLong() {
	  return getCurrentDateByFormat("yyyyMMddHHmmss");
  }

  /**
   * 功能：取应用服务器时间并以"yyyyMMdd"格式返回
   */
  public static String getDateForLong() {
	  return getCurrentDateByFormat("yyyyMMdd");
  }
  
  
  /**
   * 功能：取应用服务器时间并以"HHmmss"格式返回
   */
  public static String getTimeForLong() {
	  return getCurrentDateByFormat("HHmmss");
  }
  /**
   * 功能：取当前服务器时间并以参数指定的格式返回
   */
  public static String getCurrentDateByFormat(String formatStr){
	    long currentTime = System.currentTimeMillis();
	    java.util.Date date = new java.util.Date(currentTime);
	    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(formatStr);
	    return formatter.format(date);  
  }
  
  /**
   * 功能：转换long型为日期型字串并以"yyyy-MM-dd HH:mm:ss"格式返回
   */
  public static String getDateTime(long al_datetime) {
    java.util.Date date = new java.util.Date(al_datetime);
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss");
    return formatter.format(date);
  }

  
  public static String getYear() {
	    java.util.Date date = new java.util.Date();
	    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
	        "yyyy");
	    return formatter.format(date);
	  }

  /**
   * 功能：转换日期型为字串
   */
  public static String getDateString(java.util.Date inDate) {
    return inDate.toString();
  }

  /**
   * 功能：把给定日期与给定天数进行加减运算,返回一个新日期
   */
  public static java.util.Date getDateNDays(java.util.Date date, int days) {//
    if (days > 36500 || days < -36500) {
      System.out.println("请把日期限制在100年内");
      return null;
    }
    long l1 = 24, l2 = 60, l3 = 1000, l4 = days;
    long lDays = l1 * l2 * l2 * l3 * l4; //所改变天数的毫秒数
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    long lCurrentDate = calendar.getTimeInMillis(); //给定日期的毫秒日期
    long lUpdatedDate = lCurrentDate + lDays; //给定日期与给定天数运算后的毫秒日期
    java.util.Date dateNew = new java.util.Date(lUpdatedDate); //结果日期
    return dateNew;
  }
  
  /**
   * 功能：把给定日期与给定天数进行加减运算,返回一个yyyyMMdd的新日期
   */
  
  public static String getDateFromNDays(int days){
	  long currentTime = System.currentTimeMillis();
	  Date date=getDateNDays(new Date(currentTime),days);
	  java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
	  return formatter.format(date); 
  }
  
  
  /**
   * 功能：把给定日期与给定天数进行加减运算,返回一个yyyyMMdd的新日期
   */
  
  public static String getDateFromNYears(int years){
	  long currentTime = System.currentTimeMillis();
	  Date date=new Date(currentTime);
	  java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
	  int day=Integer.parseInt(formatter.format(date));
	  day+=years*10000;
	  return ""+day; 
  }
  
  
  
  /**
   * 把给定日期与给定月数进行运算,月数可以是负数.返回给定日期与给定日期的差或和
   * @param as_date:格式是yyyymm的日期,现支持yyyy-mm-dd型日期
   * @param months
   * @return
   * @throws Exception
   */
  public static Date getDateNMonths(String as_date, int months)throws Exception{
    String as_dateTem="";
    //yan_modi_20060708
    if( as_date.length()==10 ){//yyyy-mm-dd型日期
      as_dateTem= as_date.substring(0,7)+"-01";
    }else{//yyyymm型日期
      as_dateTem = as_date.substring(0,4) + "-" + as_date.substring(4,as_date.length()) + "-01"; //把yyyymm型的年月日期转换为yyyy-mm型的日期,在后面和-01相加,组成一个合法日期
    }
    Date ad_date = null;
    try {
      ad_date = getDate(as_dateTem);
    }  catch (Exception ex) {
      // ex.setClientMessage("日期处理出错！");
      //ex.setLogMessage("getDateNMonths(String as_date, int months) 出错！");
      throw ex;
    }
    ad_date=getDateNMonths(ad_date,months);

    //入参字符串是格式为yyyymm的日期
    return ad_date;
  }

 /**
   * 功能：把给定日期与给定月数进行运算,月数可以是负数.返回给定日期与给定日期的差或和
   * 	  若形成的新日期非法,则自动对新日期进行调整,例如:2004年1月31日加1个月为2004年2月31日,系统自动调整为2004年2月29日
   */
  public static Date getDateNMonths(java.util.Date date, int months) {
    if (months == 0) { //月数为零,直接返回给定日期
      return date;
    }
    if (months > 1200 || months < -1200) { //日期限制在100年以内
      System.out.println("请把日期限制在100年内");
      return null;
    }
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTime(date);
    gc.add(Calendar.MONTH, months);
    java.util.Date date2 = gc.getTime();
    return date2;
  }


  /**
   * 功能：功能：取得数据库系统日期，数据库类型可选，如为Oracle
   */
  public static Date getDateTime(Connection inConnection,String as_DBType)throws  Exception  {//取数据库系统日期
//    String sql = "select TO_CHAR(SYSDATE,'YYYY-MM-DD') d from dual ";//oracle
    String sql = "";
    if (as_DBType.equalsIgnoreCase("Oracle")){
      sql = "SELECT sysdate from dual";
    }else if (as_DBType.equalsIgnoreCase("Sybase")){
      sql="SELECT GetDate()";//sybase
    }else if (as_DBType.equalsIgnoreCase("Informix")){
      sql="SELECT GetDate()";//Informix
      Calendar date = Calendar.getInstance();
      Date sysDate = date.getTime();
      return sysDate;
    }

    Statement statement = null;
    ResultSet rs = null;
    try {
      statement = inConnection.createStatement();
      rs = statement.executeQuery(sql);

      while (rs.next()) {
        String date = rs.getString(1);      //add by wang_xm
//        try {
//          java.util.Date d = DateFormat.getDateInstance().parse(date);
//          return d;
//        } catch (ParseException e) {
//          throw new Exception(e, "日期数据转换失败");
//        }
        java.util.Date d = getDate(date);
        return d;
      }
      return null;
    } catch (SQLException e) {
      //throw new Exception(e, "取数据库服务器日期错", "取数据库服务器日期错");
    } finally {
       try {
         if (rs != null)  rs.close();
         if (statement != null) statement.close();
       } catch (Exception e) {
          e.printStackTrace();
       }
       rs = null;
       statement = null;
    }
	return null;
  }

  /*
   * 对日期按入参形式进行格式化
   */
  public static String formatDate(Date date,String format){
    if (date == null){
      return "";
    }
    if (format == null || format.trim().equals("")){
      format = "yyyy-MM-dd HH:mm:ss";
    }
    Locale locale = new Locale("en","US");
    SimpleDateFormat formatter = new SimpleDateFormat(format.trim(),locale);
    return formatter.format(date);
  }

  /**
   * 得到当前日期(java.sql.Date类型)，注意：没有时间，只有日期
   * @return 当前日期
   */
  public static java.sql.Date getDate() {
    Calendar oneCalendar = Calendar.getInstance();
    return getDate(oneCalendar.get(Calendar.YEAR),
                   oneCalendar.get(Calendar.MONTH) + 1,
                   oneCalendar.get(Calendar.DATE));
  }

  /**
   * 根据所给的起始,终止时间来计算间隔天数
   */
  public static int getIntervalDay(java.sql.Date startDate,java.sql.Date endDate) {//
    long startdate = startDate.getTime();    long enddate = endDate.getTime();
    long interval = enddate - startdate;
    int intervalday = (int) interval / (1000 * 60 * 60 * 24);
    return intervalday;
  }

  /**
   * 根据所给年、月、日，得到日期(java.sql.Date类型)，注意：没有时间，只有日期。
   * 年、月、日不合法，会抛IllegalArgumentException(不需要catch)
   */
  public static java.sql.Date getDate(int yyyy, int MM, int dd) {
    if (!verityDate(yyyy, MM, dd)) {
      throw new IllegalArgumentException("This is illegimate date!");
    }

    Calendar oneCalendar = Calendar.getInstance();
    oneCalendar.clear();
    oneCalendar.set(yyyy, MM - 1, dd);
    return new java.sql.Date(oneCalendar.getTime().getTime());
  }

  /**
   * 根据所给的起始,终止时间来计算间隔天数
   */
  public static int getIntervalDay2(Date startDate,Date endDate) {//
    long startdate = startDate.getTime();    long enddate = endDate.getTime();
    long interval = enddate - startdate;
    int intervalday = (int) interval / (1000 * 60 * 60 * 24);
    return intervalday;
  }
  
  
  /**
   * 根据所给年、月、日，检验是否为合法日期。
   */
  public static boolean verityDate(int yyyy, int MM, int dd) {//
    boolean flag = false;

    if (MM >= 1 && MM <= 12 && dd >= 1 && dd <= 31) {
      if (MM == 4 || MM == 6 || MM == 9 || MM == 11) {
        if (dd <= 30) {
          flag = true;
        }
      }  else if (MM == 2) {
        if (yyyy % 100 != 0 && yyyy % 4 == 0 || yyyy % 400 == 0) {
          if (dd <= 29) {
            flag = true;
          }
        } else if (dd <= 28) {
          flag = true;
        }
      } else {
        flag = true;
      }
    }
    return flag;
  }

  /**
   * 功能：根据所给的起始,终止时间来计算间隔月数；
   * @param 入参的格式是：yyyymm或yyyy-mm-dd
   */
  public static int getIntervalMonth(String as_startDate, String as_endDate) throws Exception{
    String ls_startD="",ls_endD="";
    Date ld_start = null; Date ld_end = null;
    if (as_startDate.length()==6){//yyyymm型
      ls_startD = as_startDate.substring(0,4) + "-" + as_startDate.substring(4,as_startDate.length()) + "-01"; //把yyyymm型的年月日期转换为yyyy-mm型的日期,在后面和-01相加,组成一个合法日期
      ls_endD = as_endDate.substring(0,4) + "-" + as_endDate.substring(4,as_endDate.length()) + "-01"; //把yyyymm型的年月日期转换为yyyy-mm型的日期,在后面和-01相加,组成一个合法日期
    }else{
      ls_startD = as_startDate;
      ls_endD = as_endDate;
    }
    //System.out.println("as_startD:"+as_startD);    //System.out.println("as_endD:"+as_endD);

    try {
      ld_start = getDate(ls_startD);
      ld_end = getDate(ls_endD);
    }catch (Exception ex) {
       ex.printStackTrace();
    }
    int interval=getIntervalMonth(ld_start,ld_end);
    return interval;
  }

  /**
   * 功能：根据所给的起始,终止时间来计算间隔月数；
   * @param 入参为Date
   */
  public static int getIntervalMonth(java.util.Date startDate, java.util.Date endDate) {
    java.text.SimpleDateFormat mmformatter = new java.text.SimpleDateFormat("MM");
    int monthstart = Integer.parseInt(mmformatter.format(startDate));
    int monthend = Integer.parseInt(mmformatter.format(endDate));
    java.text.SimpleDateFormat yyyyformatter = new java.text.SimpleDateFormat("yyyy");
    int yearstart = Integer.parseInt(yyyyformatter.format(startDate));
    int yearend = Integer.parseInt(yyyyformatter.format(endDate));
    return (yearend - yearstart) * 12 + (monthend - monthstart);
  }


  
  
  /**
   * 功能：将入参为"yyyy-mm-dd HH:mm:ss"或"yyyy-mm-dd"形式的字符串转换为Date并进行校验；
   */
  public static java.util.Date getDate(String strdate) throws Exception {
    int yyyy = Integer.parseInt(strdate.substring(0, 4));
    String temp = strdate.substring(5, strdate.length());
    int MM = Integer.parseInt(temp.substring(0, temp.indexOf("-")));
    temp = temp.substring(temp.indexOf("-") + 1, temp.length());
    int dd;
    if (temp.indexOf(" ") > 0) {
      dd = Integer.parseInt(temp.substring(0, temp.indexOf(" ")));
    } else {
      dd = Integer.parseInt(temp);
    }
    if (!verityDate(yyyy, MM, dd)) {
      throw new Exception("非法日期数据");
    }

    java.util.Date d;
    try {
      if (strdate.length() > 10) {
    	
    	//d = DateFormat.getDateTimeInstance().parse(strdate);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        d = formatter.parse(strdate.substring(0, 19));

      } else {
    	  java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
          d = formatter.parse(strdate);
      }
//      System.out.println(formatter.format(d));
      return d;
    } catch (ParseException e) {
      throw new Exception("日期数据转换错"+e.toString());
    }
  }
  
  
  
  /**
   * 功能：将入参为"yyyyMMdd"形式的字符串转换为Date并进行校验；
   */
  public static java.util.Date getDate2(String strdate) throws Exception {
      java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
      return formatter.parse(strdate);

  }
  
  public static String formatDate(String strdate,String inFomrat,String outFormat){
	  if(strdate==null||strdate.equals(""))return null;
	  java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(inFomrat);
	  Date date=null;
	  try{
		  date = formatter.parse(strdate);
	  }catch (ParseException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	  formatter = new java.text.SimpleDateFormat(outFormat);
	  return formatter.format(date);
  }
  
  
  /*得到了系统当前日期时间*/
  public static String getSysDate(){
       SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMddHHmmss");
       String s = simpledateformat.format(Calendar.getInstance().getTime());
       return s;
  }
  
  public static String getCurYearMonth() {
	    java.util.Date date = new java.util.Date();
	    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMM");
	    return formatter.format(date);
  }
  
  public static String getNewStr(String startdate,String enddate){
	  String curdate=DateUtil.getDateForLong();
	  if(StringUtil.isnull(enddate)||Integer.parseInt(enddate)<Integer.parseInt(curdate))return "";
	  if(!StringUtil.isnull(enddate)&&Integer.parseInt(startdate)>Integer.parseInt(curdate))return "";
	  return "<span class=\"newpic\" >&nbsp;</span>";
  }
  
  public static String getHotStr(String startdate,String enddate){
	  String curdate=DateUtil.getDateForLong();
	  if(StringUtil.isnull(enddate)||Integer.parseInt(enddate)<Integer.parseInt(curdate))return "";
	  if(!StringUtil.isnull(enddate)&&Integer.parseInt(startdate)>Integer.parseInt(curdate))return "";
	  return "<span class=\"hotpic\" >&nbsp;</span>";
  }
  
  public static java.sql.Timestamp formateSqlTimestamp(String formate){
	  if(formate==null||formate.equals(""))return null;
	  return java.sql.Timestamp.valueOf(formate+" 00:00:00");  
  }
  

	/**
	 * 获取指定日期的0点0分0秒的时间
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Timestamp getFirstTime(String date) {
		if (date == null||date.equals("")) {
			return null;
		}
		return java.sql.Timestamp.valueOf(date+" 00:00:00.0");
	}

	/**
	 * 获取指定日期的23点59分59秒的时间
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Timestamp getLastTime(String date) {
		if (date == null||date.equals("")) {
			return null;
		}
		return java.sql.Timestamp.valueOf(date+" 23:59:59.999");
	}
  
	
	/**
	 * 将时间转换为yyyy-MM-dd HH:mm:ss格式的字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTime(Timestamp time) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return myFormat.format(time);
	}

	/**
	 * 将时间转换为指定格式的字符串
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String formatTime(Timestamp time, String format) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat myFormat = new SimpleDateFormat(format);
		return myFormat.format(time);
	}
  
  /**
   * 功能：调试用main方法
   */
  public static void main(String[] args) throws Exception {
	  java.sql.Timestamp jt=java.sql.Timestamp.valueOf("2004-06-08 01:01:01.0");
	  System.out.println(DateUtil.formatTime(jt,"yyyy-MM-dd hh:mm:ss"));
	  Date date = getDate2("20091210");
	  System.out.println("date==>"+date);
	  java.sql.Date d  = new java.sql.Date(date.getTime());
	  

	  
	  Calendar cal = Calendar.getInstance(); 
	  cal.set(Calendar.YEAR,2009); 
	  cal.set(Calendar.MONTH, 8 - 1);//Java月份才0开始算 
	  int dateOfMonth = 
	  cal.getActualMaximum(Calendar.DATE); 
      System.out.println("dateOfMonth==>"+dateOfMonth);
	  

	  cal.setTime(date);
	  int week = cal.get(Calendar.DAY_OF_WEEK)-1;
	  System.out.println("week==>"+week);
	  System.out.println("date.getYear()==>"+date.getMonth());

  }
}
