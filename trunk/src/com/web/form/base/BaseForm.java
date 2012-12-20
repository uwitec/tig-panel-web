/**
 * 
 */
package com.web.form.base;

import com.web.form.administration.User;

/**
 * @author David
 * @name BaseForm
 * @describe：所有页面Form的基类,其属性不参与Hibernate的对象关系映射
 */
public class BaseForm {
	public BaseForm(){
	}
	
	public BaseForm(BaseForm form){
		this.start = form.getStart();
		this.limit = form.getLimit();
		this.total = form.getTotal();
		this.rowStart = form.getRowStart();
		this.rowEnd = form.getRowEnd();
		this.sortString = form.getSortString();
		this.totCount = form.getTotCount();
		this.pageCount = form.getPageCount();
		this.delareFirstEndPage = form.getDelareFirstEndPage();
		this.diffSecond = form.getDiffSecond();
		this.endInPage = form.getEndInPage();
	}
	
	/**
	 *	在很多sqlmap组装的sql语句中需要使用的登录用户信息
	 */
	private User    logonUser = null;
	
	/**
	 *	分页时用到的变量
	 *	start       : 当前页页码，与前台SelfPagingToolbar组件的start属性对应。当查询最后一页的数据时，前台组件相对应的该值设为-1
	 *	limit       : 每页显示的记录数，与前台SelfPagingToolbar组件的limit属性对应
	 *	total       : 总页数，只有当用户查询最后一页数据时由系统自己填入。查询其它页时，total的值应为0
	 *	rowStart    : 起始行号
	 *	rowEnd      : 结束行号 
	 */
	private Integer start = 1;
	private Integer limit = 20;
	private Integer total = 0;//第几页
	private Integer rowStart;
	private Integer rowEnd;
	private Integer totCount = 0;//记录总数
	private Integer pageCount;//总页数
	private Integer delareFirstEndPage = 0;//判断是首页还是尾页还是都不是
	private Double diffSecond;//查询页面所用的时间
	private Integer endInPage;//在当页显示的最后一条记录
	
	private String locale;
	
	private String sortString;
	
	private String classname;
	private String sqlMapNamespace;
	
	private String socketResponseCode;
	
	public User getLogonUser() {
		return logonUser;
	}
	public void setLogonUser(User logonUser) {
		this.logonUser = logonUser;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getRowStart() {
		return rowStart;
	}
	public void setRowStart(Integer rowStart) {
		this.rowStart = rowStart;
	}
	public Integer getRowEnd() {
		return rowEnd;
	}
	public void setRowEnd(Integer rowEnd) {
		this.rowEnd = rowEnd;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getSortString() {
		return sortString;
	}
	public void setSortString(String sortString) {
		this.sortString = sortString;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getSqlMapNamespace() {
		return sqlMapNamespace;
	}
	public void setSqlMapNamespace(String sqlMapNamespace) {
		this.sqlMapNamespace = sqlMapNamespace;
	}

	public Integer getTotCount() {
		return totCount;
	}

	public void setTotCount(Integer totCount) {
		this.totCount = totCount;
	}

	public Integer getDelareFirstEndPage() {
		return delareFirstEndPage;
	}

	public void setDelareFirstEndPage(Integer delareFirstEndPage) {
		this.delareFirstEndPage = delareFirstEndPage;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Double getDiffSecond() {
		return diffSecond;
	}

	public void setDiffSecond(Double diffSecond) {
		this.diffSecond = diffSecond;
	}

	public Integer getEndInPage() {
		return endInPage;
	}

	public void setEndInPage(Integer endInPage) {
		this.endInPage = endInPage;
	}

	public String getSocketResponseCode() {
		return socketResponseCode;
	}

	public void setSocketResponseCode(String socketResponseCode) {
		this.socketResponseCode = socketResponseCode;
	}
	
}
