package com.web.form.report;
import com.web.form.base.BaseForm;

public class Batfhunitaccdaytb extends BaseForm implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Fields
	private Integer pacsetdate;//结算日期
	
	private Integer pacsetdate_start;//开始结算日期
	private Integer pacsetdate_end;//结束结算日期
	
	private Integer pacunitid;//单位编号
	private String pacunitname;//单位名称
	private Short acdacctxntype;//外部交易类型
	private String acdacctxntypestr;
	private Short pacaccknd;//内部交易类型
	private Short adjflag;//调整交易标志
	private Short crdtype;//卡类型
	private String crdtypestr;
	
	private Integer pacerrcode;//外部错误代码
	private Short pactestflag;//测试标志位
	private Integer pacsetmth;//结算月份
	private Short pacsetyear;//结算年份
	private Integer pacdate;//统计日期
	private Integer pacpurnum;//交易笔数
	private Long pacpuramt;//交易金额
	private Long pacorgamt;//应收金额
	private Double pachndamt1;//笔数手续费
	private Double pachndamt2;//金额手续费
	private String pacrsvd;
	
	
	public Integer getPacsetdate() {
		return pacsetdate;
	}
	public void setPacsetdate(Integer pacsetdate) {
		this.pacsetdate = pacsetdate;
	}
	public Integer getPacunitid() {
		return pacunitid;
	}
	public void setPacunitid(Integer pacunitid) {
		this.pacunitid = pacunitid;
	}
	public String getPacunitname() {
		return pacunitname;
	}
	public void setPacunitname(String pacunitname) {
		this.pacunitname = pacunitname;
	}
	public Short getAcdacctxntype() {
		return acdacctxntype;
	}
	public void setAcdacctxntype(Short acdacctxntype) {
		this.acdacctxntype = acdacctxntype;
	}
	public String getAcdacctxntypestr() {
		return acdacctxntypestr;
	}
	public void setAcdacctxntypestr(String acdacctxntypestr) {
		this.acdacctxntypestr = acdacctxntypestr;
	}
	public Short getPacaccknd() {
		return pacaccknd;
	}
	public void setPacaccknd(Short pacaccknd) {
		this.pacaccknd = pacaccknd;
	}
	public Short getAdjflag() {
		return adjflag;
	}
	public void setAdjflag(Short adjflag) {
		this.adjflag = adjflag;
	}
	public Short getCrdtype() {
		return crdtype;
	}
	public void setCrdtype(Short crdtype) {
		this.crdtype = crdtype;
	}
	public String getCrdtypestr() {
		return crdtypestr;
	}
	public void setCrdtypestr(String crdtypestr) {
		this.crdtypestr = crdtypestr;
	}
	public Integer getPacerrcode() {
		return pacerrcode;
	}
	public void setPacerrcode(Integer pacerrcode) {
		this.pacerrcode = pacerrcode;
	}
	public Short getPactestflag() {
		return pactestflag;
	}
	public void setPactestflag(Short pactestflag) {
		this.pactestflag = pactestflag;
	}
	public Integer getPacsetmth() {
		return pacsetmth;
	}
	public void setPacsetmth(Integer pacsetmth) {
		this.pacsetmth = pacsetmth;
	}
	public Short getPacsetyear() {
		return pacsetyear;
	}
	public void setPacsetyear(Short pacsetyear) {
		this.pacsetyear = pacsetyear;
	}
	public Integer getPacdate() {
		return pacdate;
	}
	public void setPacdate(Integer pacdate) {
		this.pacdate = pacdate;
	}
	public Integer getPacpurnum() {
		return pacpurnum;
	}
	public void setPacpurnum(Integer pacpurnum) {
		this.pacpurnum = pacpurnum;
	}
	public Long getPacpuramt() {
		return pacpuramt;
	}
	public void setPacpuramt(Long pacpuramt) {
		this.pacpuramt = pacpuramt;
	}
	public Long getPacorgamt() {
		return pacorgamt;
	}
	public void setPacorgamt(Long pacorgamt) {
		this.pacorgamt = pacorgamt;
	}
	public Double getPachndamt1() {
		return pachndamt1;
	}
	public void setPachndamt1(Double pachndamt1) {
		this.pachndamt1 = pachndamt1;
	}
	public Double getPachndamt2() {
		return pachndamt2;
	}
	public void setPachndamt2(Double pachndamt2) {
		this.pachndamt2 = pachndamt2;
	}
	public String getPacrsvd() {
		return pacrsvd;
	}
	public void setPacrsvd(String pacrsvd) {
		this.pacrsvd = pacrsvd;
	}
	public Integer getPacsetdate_start() {
		return pacsetdate_start;
	}
	public void setPacsetdate_start(Integer pacsetdateStart) {
		pacsetdate_start = pacsetdateStart;
	}
	public Integer getPacsetdate_end() {
		return pacsetdate_end;
	}
	public void setPacsetdate_end(Integer pacsetdateEnd) {
		pacsetdate_end = pacsetdateEnd;
	}
}