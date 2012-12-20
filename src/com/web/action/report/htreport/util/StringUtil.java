package com.web.action.report.htreport.util;

//@引用类
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;
//@业务组件类

import javax.servlet.jsp.JspWriter;


/**                                                  
* Comments:                                                                                                              
* Author：黄孟俊
* QQ: 240713484
* Create Date：  
* Modified By：                                            
* Modified Date:                                      
* Why & What is modified      
* Company:上海华腾系统软件有限公司                                         
*/
public class StringUtil {
  public StringUtil() {
  }

  /**
   * 功能：分割符型字符串转换为数组
   * @param		String类型， as_str,输入的字符串； String类型， as_separator，分割符
   * @return    String[]
   * input example as_str="a,,b,c,,d", ",",it can return string array splited by as_separator ','
   */
  public static String[] split(String as_str, String as_separator) {
    if(as_str == null)
            throw new NullPointerException("字符串不能为空!");
    if(as_separator == null)
            throw new NullPointerException("分隔符不能为空!");
    if(as_separator.length() == 0)
            throw new IllegalArgumentException("分隔符不能为空!");

    ArrayList lal_tmp = new ArrayList();
    int li_spos = 0;
    int li_separatorLen = as_separator.length();
    int li_epos = as_str.indexOf(as_separator);
    while(li_epos != -1) {
      lal_tmp.add(as_str.substring(li_spos, li_epos));
      li_spos = li_epos + li_separatorLen;
      li_epos = as_str.indexOf(as_separator, li_spos);
    }
    lal_tmp.add(as_str.substring(li_spos,as_str.length()));
    String[] ls_result = new String[lal_tmp.size()];
    lal_tmp.toArray(ls_result);
    return ls_result;
}

  
  /**
   * 功能：分割符型字符串转换为数组
   * @param		String类型， as_str,输入的字符串； String类型， as_separator，分割符
   * @return    ArrayList
   * input example as_str="a,,b,c,,d", ",",it can return string array splited by as_separator ','
   */
  public static ArrayList splitToArrayList(String as_str, String as_separator) {
    if(as_str == null)return null;
    if(as_separator == null)return null;
    if(as_separator.length() == 0)return null;
    
    ArrayList list=new ArrayList(); 
    StringTokenizer  stk=new StringTokenizer(as_str,as_separator);
    while(stk.hasMoreTokens()){
    	list.add((String)stk.nextToken());
    }
    
    return list;
}

  
  /**
   * 功能：分割符型字符串转换为HashMap
   * @param		String类型， as_str,输入的字符串； String类型， as_separator，分割符
   * @return    ArrayList
   * input example as_str="a,,b,c,,d", ",",it can return string array splited by as_separator ','
   */
  public static HashMap splitToHashMap(String as_str, String as_separator) {
    if(as_str == null)return null;
    if(as_separator == null)return null;
    if(as_separator.length() == 0)return null;
    
    HashMap hm=new HashMap(); 
    StringTokenizer  stk=new StringTokenizer(as_str,as_separator);
    while(stk.hasMoreTokens()){
    	String str=(String)stk.nextToken();
    	hm.put(str,str);
    }
    
    return hm;
}
  
  /**
   * 功能：分割符型字符串转换为二维数组
   * @param		String类型， as_str,输入的字符串； String类型， as_separator，分割符
   * @return    ArrayList
   * input example as_str="a,,b,c,,d", ",",it can return string array splited by as_separator ','
   */
  public static ArrayList splitToPlanarList(String as_str, String as_separator1,String as_separator2) {
    if(as_str == null)return null;
    if(as_separator1 == null)return null;
    if(as_separator1.length() == 0)return null;
    if(as_separator2 == null)return null;
    if(as_separator2.length() == 0)return null;
    
    ArrayList list=new ArrayList(); 
    ArrayList oneList=null;
    StringTokenizer  stk1=null;
    StringTokenizer  stk=new StringTokenizer(as_str,as_separator1);
    String oneStr=null;
    while(stk.hasMoreTokens()){
    	oneList=new ArrayList();
    	oneStr=(String)stk.nextToken();
    	stk1=new StringTokenizer(oneStr,as_separator2);
    	while(stk1.hasMoreTokens()){
    		oneList.add((String)stk1.nextToken());
    	} 	
    	list.add(oneList);
    }
    
    return list;
}
  

  
  
  /**
   * 功能：填充一定数量的字符
   * @param		String类型， as_pad,填充的字符串； int类型， ai_num，填充的数量
   * @return    String
   * repeat counted ai_num the string named as_pad,and return summar the value;
   */
  public static String pad(String as_pad,int ai_num){
    String ls_result= new String();

    for(int i=1;i<=ai_num;i++){
      ls_result += as_pad;
    }
    return ls_result;
  }

  
  
  public static int getUnicodeLength(String   str){   
      int  i,t=0;   
      byte[]  bt =str.getBytes();   
      for(i=1;i<=bt.length;i++){   
          if(bt[i-1]<0){t=t+2;i++;}   
          else t=t+1;   
      }   
      return t;   
  }  
  
  
  
  /**
   * 功能：判断字符串间的包含关系，类似于sql标准语句的in函数 入参as_InStrs为字符串,格式为形如"aa|bb"中包含"aa"返回真
   */
  public static boolean in(String as_SrcStr,String as_InStrs){
    boolean lb=false;
    String[] ls_InStrs=null;
    //若为空串,返回false
    if( as_SrcStr.equals("")|| as_InStrs.equals("") )  return false;

    ls_InStrs = split( as_InStrs,"|");

    for (int i=0;i<ls_InStrs.length;i++){
      if( as_SrcStr.indexOf(ls_InStrs[i]) >=0 ){
        lb=true;
        break;
      }
    }

    return lb;
  }

  /**
   * 功能：空值替换,类似于oracle的nvl函数，如果as_str为空，则替换为as_val
   */
  public static String nvl(String as_str,String as_val){//equal nvl function in oracle
    if(as_str==null) return as_val;
    if ( (as_str.trim()).length()==0 || as_str.equals("") ){

    }else{
      as_val = as_str;
    }
    return as_val;
  }//nvl(String as_str,String as_val)
  
  /**
   * 功能：返回首字母大写的字串
   */
 public static String FirstCharToUpper(String as_str){
   String ls_rtnstr="";
   int li_len=0;
   li_len=as_str.length();
   ls_rtnstr = as_str.substring(0,1).toUpperCase()+as_str.substring(1,li_len);

   return ls_rtnstr;
 }

 /**
  * 功能：空值替换,类似于oracle的nvl函数，如果Object--ao_obj1为空，则替换为as_val
  */
  public static String nvl(Object ao_obj1,String as_val1){
    if(ao_obj1==null) {
      return as_val1;
    }else{
      return ao_obj1.toString().trim();
    }
  }//nvl(String as_str,String as_val)

  /**
   * 功能：判断字符串是否为空
   */
  public static boolean isnull(String as_str){
    if ( as_str==null){
      return true;
    }
    if ( (as_str.trim()).length()==0 || as_str.equals("") ){
      return true;
    }
    return false;
  }

  /**
   * 功能：字符串替换，将oldStr中的searchStr替换为replaceStr
   */
  public static String replace(String oldStr, String searchStr, String replaceStr){
    String outStr = "";
    oldStr = nvl(oldStr, "");
    searchStr = nvl(searchStr, "");

    int iPos = 0;
    int iLen = searchStr.length();

    if (oldStr.equals("") || searchStr.equals("") || replaceStr == null)
      return oldStr;

    iPos = oldStr.indexOf(searchStr);

    while (iPos != -1) {
      outStr += oldStr.substring(0, iPos) + replaceStr;
      iPos += iLen;
      if (oldStr.length() > iPos) {
        oldStr = oldStr.substring(iPos);
        iPos = oldStr.indexOf(searchStr);
      } else {
        oldStr = "";
        iPos = -1;
      }
    }

    outStr += oldStr;
    return outStr;
  }

  /**
   * 功能：判断字符串是否在字符数组中
   */
  public static boolean isStrInArray(String as_Str,String[] strArr){
    boolean bl=false;
    for(int i=0;i<strArr.length;i++){
      if(as_Str.equals( strArr[i])) {
        bl = true;   return bl;
      }
    }
    return bl;
  }

  /**
   * 功能：判断字符串是否在字符数组中
   */
  public static boolean isArrayAllEqualsStr(String[] strArr,String as_Str){

    for(int i=0;i<strArr.length;i++){
      if(!as_Str.equals( strArr[i])) {
          return false;
      }
    }
    return true;
  }
 
  /**
   * 功能：判断字符串是否在字符数组中
   */
  public static boolean isArraysAllEqualsArray(String[][] strArr,String as_Str[]){
    if(strArr==null)return false;
    for(int i=0;i<strArr.length;i++){
      for(int j=0;j<as_Str.length;j++){
    	  if(!as_Str[j].equals( strArr[i][j])) {
              return false;
          }
      }
    }
    return true;
  }
  
  
  
  /**
   * 功能：对sql字符串解码，其中包含单引号的将其转换
   */
  public static String sqlEncode(String str){
    if (StringUtil.nvl(str,"").equals("")){
       return "";
    }else{
      str = StringUtil.replace(str, "'", "''");
    }
    return str;
  }

  /**
   * 功能：xml字符串解码，对其中包含双引号、<、>...的将其转换
   */
  public static String xmlEncode(String input) {
    StringBuffer filtered = null;
    input = nvl(input,"");
    filtered = new StringBuffer(input.length());

    char c;
    for(int i=0; i<input.length(); i++) {
      c = input.charAt(i);
      if (c == '<') {
        filtered.append("&lt;");
      } else if (c == '>') {
        filtered.append("&gt;");
      } else if (c == '"') {
        filtered.append("&quot;");
      } else if (c == '&') {
        filtered.append("&amp;");
      } else {
        filtered.append(c);
      }
    }
    return(filtered.toString());
  }

  /**
   * 功能：字符集解码，数据库查询取得字符串解码为中文GB2312字符串
   */
  public static String dbstringEncode(String as_srcStr,String as_dbtype,int flag)  throws Exception {
//          if (uiEngine.Const.SYSTEM_DATABASE_TYPE == uiEngine.Const.SECTION_DATABASE_ORACLE) {
//                  return sourceString;
//          } else if (uiEngine.Const.SYSTEM_DATABASE_TYPE == uiEngine.Const.SECTION_DATABASE_INFORMIX) {
   try{
     if(flag==1){//0来自数据库的字符串，1提交到数据库的字符串
       if(as_dbtype.equals("Informix")){//Informix数据库需要字符集转换
         return new String(as_srcStr.getBytes("GB2312"), "ISO8859-1");
       }
     }
     if(as_dbtype.equals("Oracle")){
       return as_srcStr;
     }else if(as_dbtype.equals("Informix")){
       return new String(as_srcStr.getBytes("ISO8859-1"), "GB2312");
     }else{
       return new String(as_srcStr.getBytes("ISO8859-1"), "GB2312");
     }

   } catch (Exception ex) {
     ex.printStackTrace();
   }
//          } else
//                  return new String(sourceString.getBytes("ISO8859-1"), "GB2312");
return as_dbtype;
  }//end

  /**
   * 功能：重载dbstringEncode方法，默认数据库类型从全局参数中取得
   */
  public static String dbstringEncode(String as_srcStr,int flag)  throws Exception {
    String ls_dbtype = "";//SysRuleing.getInstance().getPaval("DBTYPE");//取得数据库类型
    return dbstringEncode(as_srcStr,ls_dbtype,flag);
  }

  /**
   * 功能：截取标签右边的子串
   */
  public static String RightTag(String as_str,String as_tag){
    String ls_rightTag="";
    int li_pos= as_str.indexOf(as_tag);
    int li_taglen= as_tag.length();
    ls_rightTag= as_str.substring(li_pos+li_taglen,as_str.length());
    return ls_rightTag;
  }
  
  /**
   * 功能：截取标签左边的子串
   */
  public static String LeftTag(String as_str,String as_tag){
    String ls_leftTag="";
    int li_pos= as_str.indexOf(as_tag);
    ls_leftTag= as_str.substring(0,li_pos);
    return ls_leftTag;
  }

  /**
   * 功能：将字符串转换为数字
   */
  public static int Int(String as_str,int i){
      int t=0;
	  try{
    	  t=Integer.parseInt(as_str);
      }catch(Exception e){
    	  t=i;
      }
	  return t;
  }
 
  public static int Int(String as_str){
	  return Int(as_str,0);
  }
  
  /**
   * 功能：合并数组
   */
  public static String[][] concatArray(String[] a1,String[] a2){
	  String[][] ca=new String[a1.length*a2.length][2];
	  int t=0;
	  for(int i=0;i<a1.length;i++){
		  for(int j=0;j<a2.length;j++){
			  ca[t][0]=a1[i];
			  ca[t][1]=a2[j];
		  }
		  t++;
	  }
	  return ca;
  }
  
  
  /**
   * 功能：合并数组
   */
  public static String[][] addArrayToArrays(String[] a1,String[][] a2){
	  String[][] ca=new String[a2.length+1][a1.length];
	  ca[0]=a1;
	  for(int i=1;i<ca.length;i++){
		  ca[i]=a2[i-1];
	  }
	  return ca;
  }
  
  
  
  
  /**
   * 功能：请str中的根据分隔符改为数组
   */
  public static String[] strToArrayByDelim(String str,String stat){
	  StringTokenizer token=new StringTokenizer(str,stat);
	  String ar[]=new String[token.countTokens()];
	  int num=0;
	  while(token.hasMoreTokens()){
		  ar[num++]=(String)token.nextToken(); 
	  }
	  return ar;
  }
  
  
  /**
   * 功能：请hashMap中的根据str转化为字符,处理多主键查询语句的时候用。
   */
  public static String[][] hashMapToArrays(HashMap hm,String str){
	  String[][] ca=new String[1][2];
	  String[] strarray=strToArrayByDelim(str,",");
	  StringTokenizer token=new StringTokenizer((String)hm.get(strarray[0]),",");
	  ca=new String[strarray.length][token.countTokens()+1];
	  String ele="";
	  for(int i=0;i<strarray.length;i++){
		  ele=strarray[i];
		  ca[i][0]=ele;
		  String[] str1=strToArrayByDelim((String)hm.get(ele),",");
		  for(int j=0;j<str1.length;j++){
			  ca[i][j+1]=str1[j];
		  }
	  }	  	  
	  return ca;
  }
  
  public static String getFormatDouble(double d){
	 DecimalFormat FormatNum=new DecimalFormat("0.00");  
	 FormatNum.format(d);
	 return FormatNum.format(d);
 }
  
  /**
   * 功能：将java的二维数组String[n][m]转换成JavaScript 的数组array
   * @param     String[n][m]
   * @return    String;
   * @throws     
   * @exception 
   * @author 黄孟俊 2008-06-30
   */
  
  public static String TransStringArrayToJS(String[][] strarr){
	  StringBuffer buffer=new StringBuffer();
	  if(strarr.length==0)return "[]";
	  buffer.append("[");
	  for(int i=0;i<strarr.length;i++){
		buffer.append("[");
		for(int j=0;j<strarr.length;j++){
			buffer.append(strarr[i][j]+",");
		} 
		if(strarr.length!=0)buffer.deleteCharAt(buffer.length()-1);
		buffer.append("],");
	  }
	  buffer.deleteCharAt(buffer.length()-1);
	  buffer.append("]");
	  return buffer.toString();
  }
  
  
  public static String toField(String str){
	  if(str.equals(".00")){
		  return "0.00";
	  }
	  return str;
  }
  


  
  /**
   * 功能：调用改方法实现报文的字符串的处理
   * msg: 0000..n0
   */
  public static String setMsgContent(String msg,String content){
	  StringBuffer buffer=new StringBuffer(msg);
	  buffer.replace(buffer.length()-content.length(),buffer.length()+1,content);
	  return buffer.toString();  
  }
  
  
  
  public static String formnateDateStr(String date){
	  return date.substring(0,4)+"-"+date.substring(4, 6)+"-"+date.substring(6);
  }
  
  
  
  
  /*
   * 功能：合并ArrayList,用分隔符 
   * code: 0 返回少于200个字符
   *       
   */
  public static String concatArrayList(ArrayList<String> list,String str,int code){
	  StringBuffer buffer=new StringBuffer(list.get(0));
	  for(int i=1;i<list.size();i++){
		  buffer.append(str).append(list.get(i));
	  }
	  if(code<=0)return buffer.toString();
	  if(buffer.length()>=code){
		  buffer.delete(code, buffer.length());
		  buffer.append("...");
	  }
	  return buffer.toString();
  }
  
  /*
   * 功能：合并ArrayList,用分隔符 
   */
  public static String concatArrayList(ArrayList<String> list,String str){
	  
	  return concatArrayList(list,str,200);
  }
  
  public static void OutPrintStringEncode(String str,JspWriter out){
	  if(str!=null){	  
		try{
			out.print(new String(str.getBytes("ISO8859-1"), "GB2312"));
			out.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
	  }
  } 
  
  /*
   * 功能：
   */
  public static String getOrHsql(String[] values,String name){
	  StringBuilder sb=new StringBuilder("");
	  if(values!=null&&values.length!=0){
		  for(int i=0;i<values.length;i++){
			  String value=values[i];
			  if(i!=0){
				  sb.append(" or ");
			  }else{
				  sb.append(" and ( ");
			  }
			  sb.append(name).append("='").append(value).append("' ");
		  }
		  sb.append(" ) ");
	  }
	  return sb.toString();
  }
  
  public static String getAndHsql(String[] values,String name){
	  StringBuilder sb=new StringBuilder("");
	  if(values!=null&&values.length!=0){
		  for(int i=0;i<values.length;i++){
			  String value=values[i];
			  sb.append(" and ");
			  sb.append(name).append("='").append(value).append("' ");
		  }
	  }
	  return sb.toString();
  }
  
  public static String getDateAreaHsql(String startDate,String endDate,String name){
	  if(StringUtil.isnull(startDate)&&StringUtil.isnull(endDate)){
		  return "";
	  }
	  if(StringUtil.isnull(startDate)&&!StringUtil.isnull(endDate)){
		  return " and "+name+"<="+endDate;
	  }
	  if(!StringUtil.isnull(startDate)&&StringUtil.isnull(endDate)){
		  return " and "+name+">="+startDate;
	  }
	  return " and "+name+">="+startDate+" and "+name+"<="+endDate;
  }
  
  
  
  
  public static String getStrUtf8(String str){
	  
	  return "";
  }
 
  public static String getStrByLength(String str,int len){
	  if(str==null)return null;
	  if(str.length()<=len){
		  return str;
	  }
	  return str.substring(0,len);
  }
  
  /**
   * 功能：例如将userName 转换为user_name
   * @param     String[n][m]
   * @return    String;
   * @throws     
   * @exception 
   * @author 黄孟俊 2008-06-30
   */
  public static String formateBeanAttrToFieldName(String beanAttr){
	  StringBuffer fieldName=new StringBuffer();
	  char[] cs=beanAttr.toCharArray();
	  for(char c:cs){
		  if(c>='A'&&c<='Z'){
			  fieldName.append("_").append((char)(c+32));
		  }else{
			  fieldName.append(c);
		  }
	  }
	  
	  return fieldName.toString().replace("_dict","");
  }
  
  
  
  /**
   * 功能：调试用main方法
   */
  public static void main(String[] args)  {
    //StringUtil sStringUtil=new StringUtil();
	//System.out.println( sStringUtil.xmlEncode("sdfsdfsd\"") );
	//System.out.println( sStringUtil.FirstCharToUpper("asdfFds") );
	/*String ls_tmp="na_stud_depcode";
    String ls_resultName_pre = ls_tmp.substring(0,ls_tmp.lastIndexOf("_"));
    String ls_colName = ls_tmp.substring( ls_tmp.lastIndexOf("_")+1,ls_tmp.length());
    System.out.println( ls_resultName_pre );
    System.out.println( ls_colName );*/
    
    String test="rrr.ccc|nyyyy";
    String[] result = test.split("\\|");
    
    //String[] result=StringUtil.split(test, ".");
    System.out.println(StringUtil.setMsgContent("0000000","123456"));
    System.out.println(StringUtil.formateBeanAttrToFieldName("userNameSexUnit"));
  }

}
