/**
 * 
 */
package com.application.exception;

/**
 * @author David
 *
 */
public class AppErrorcode {
	/**
	 * 用户登录(10001——10010)
	 */
	public static final String INVALIDUSER = "exception.invaliduser";//10001用户名或密码错误
	public static final String DUPLICATEUSERCODE = "exception.duplicateusercode";//10002登录账号已存在
	public static final String USERLOGONTIMEOUT = "exception.userlogontimeout";//10003用户登录超时
	public static final String USERCODEALREADYEXIST = "exception.usercodealreadyexist";//10004用户账号或操作员号已存在
	public static final String USERPROHIBITED = "exception.userprohibited";//10005该用户已被禁用
	public static final String USERNOPRIVILIDGE = "exception.usernoprivilidge";//10006该用户没有任何权限
	public static final String DUPLICATEUSERID = "exception.duplicateuserid";//10007用户编号已被使用
	

	/**
	 * 权限错误(10011——10015)
	 */
	public static final String NOPRIVILEGE = "exception.noprivilege";//10011权限不足
	
	/**
	 * 分页查询错误(10016--10020)
	 */
	public static final String PARAMETEROBJECTISNULL = "exception.parameterobjectisnull";//10016分压查询的参数对象为空
	public static final String PARAMETEROBJECTWRONGTYPE = "exception.parameterobjectwrongtype";//10017分页查询的参数对象类型错误
	
	/**
	 * 消费调整错误(40001--40003)
	 */
	public static final String wrongsetdate = "exception.wrongsetdate";    //结算日期与系统不符
	public static final String wrongerrcode = "exception.wrongerrcode";    //外部错误代码不允许调整
	public static final String wrongsusadjflag = "exception.wrongsusadjflag"; //该调整状态不允许调整
	
	
}
