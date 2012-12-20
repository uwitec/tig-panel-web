package com.web.form.cchs.exchangeadj;

import com.web.form.base.BaseForm;

public class tdfhpossusdtltb extends BaseForm{
private static final long serialVersionUID = 1L;
	
	private	Integer	sussdate	;
	private	Integer	suscenseq	;
	private	Integer	sustxncode	;
	private	Integer	susinntype	;
	private	Integer	sustranstype	;
	private	String	suscardno	;
	private	Integer	suscardcity	;
	private	Integer	suscdkind	;
	private	Integer	suscdphykind	;
	private	Integer	susdptamt	;
	private	Integer	susvalid	;
	private	Integer	suscrdstat	;
	private	Integer	susunitid	;
	private	String	susnetid	;
	private	String	susposid	;
	private	String	sussamid	;
	private	Integer	susposseq	;
	private	Integer	susbatchno	;
	private	String	sustermid	;
	private	Integer	sustermseq	;
	private	String	susoprid	;
	private	Integer	susrdate	;
	private	Integer	susrtime	;
	private	Integer	suscendate	;
	private	Integer	suscentime	;
	private	Integer	sustxncount	;
	private	String  susbefbal	;
	private	String 	susaftbal	;
	private	String 	susamt	;
	private	Integer	susval	;
	private	Integer	sushandingcharger	;
	private	String	sustacbuf	;
	private	String	susspecinfo	;
	private	Integer	sustestflag	;
	private	Integer	suserr	;
	private	Integer	susinnerr	;
	private	Integer	suspkgid	;
	private	Integer	sustxnorder	;
	private	Integer	susadjflag	;
	private	Integer	susadjdate	;
	/**
	 * 数据字典
	 * @return
	 */
	private String susadjflagdesc;
	
	private String sustxncodedesc;
	
	private String susinntypedesc;
	
	private String suscdkinddesc;
	
	private String suscdphykinddesc;
	
	private String sustranstypedesc;
	
	private String suscrdstatdesc;
	
	private String susunitiddesc;
	
	private String susnetiddesc;
	
	private String sustestflagdesc;
	
	private String suserrdesc;
	
	private String susinnerrdesc;
	
	public String getSusadjflagdesc() {
		return susadjflagdesc;
	}
	public void setSusadjflagdesc(String susadjflagdesc) {
		this.susadjflagdesc = susadjflagdesc;
	}
	public Integer getSussdate() {
		return sussdate;
	}
	public void setSussdate(Integer sussdate) {
		this.sussdate = sussdate;
	}
	public Integer getSuscenseq() {
		return suscenseq;
	}
	public void setSuscenseq(Integer suscenseq) {
		this.suscenseq = suscenseq;
	}
	public Integer getSustxncode() {
		return sustxncode;
	}
	public void setSustxncode(Integer sustxncode) {
		this.sustxncode = sustxncode;
	}
	public Integer getSusinntype() {
		return susinntype;
	}
	public void setSusinntype(Integer susinntype) {
		this.susinntype = susinntype;
	}
	public Integer getSustranstype() {
		return sustranstype;
	}
	public void setSustranstype(Integer sustranstype) {
		this.sustranstype = sustranstype;
	}
	public String getSuscardno() {
		return suscardno;
	}
	public void setSuscardno(String suscardno) {
		this.suscardno = suscardno;
	}
	public Integer getSuscardcity() {
		return suscardcity;
	}
	public void setSuscardcity(Integer suscardcity) {
		this.suscardcity = suscardcity;
	}
	public Integer getSuscdkind() {
		return suscdkind;
	}
	public void setSuscdkind(Integer suscdkind) {
		this.suscdkind = suscdkind;
	}
	public Integer getSuscdphykind() {
		return suscdphykind;
	}
	public void setSuscdphykind(Integer suscdphykind) {
		this.suscdphykind = suscdphykind;
	}
	public Integer getSusdptamt() {
		return susdptamt;
	}
	public void setSusdptamt(Integer susdptamt) {
		this.susdptamt = susdptamt;
	}
	public Integer getSusvalid() {
		return susvalid;
	}
	public void setSusvalid(Integer susvalid) {
		this.susvalid = susvalid;
	}
	public Integer getSuscrdstat() {
		return suscrdstat;
	}
	public void setSuscrdstat(Integer suscrdstat) {
		this.suscrdstat = suscrdstat;
	}
	public Integer getSusunitid() {
		return susunitid;
	}
	public void setSusunitid(Integer susunitid) {
		this.susunitid = susunitid;
	}
	public String getSusnetid() {
		return susnetid;
	}
	public void setSusnetid(String susnetid) {
		this.susnetid = susnetid;
	}
	public String getSusposid() {
		return susposid;
	}
	public void setSusposid(String susposid) {
		this.susposid = susposid;
	}
	public String getSussamid() {
		return sussamid;
	}
	public void setSussamid(String sussamid) {
		this.sussamid = sussamid;
	}
	public Integer getSusposseq() {
		return susposseq;
	}
	public void setSusposseq(Integer susposseq) {
		this.susposseq = susposseq;
	}
	public Integer getSusbatchno() {
		return susbatchno;
	}
	public void setSusbatchno(Integer susbatchno) {
		this.susbatchno = susbatchno;
	}
	public String getSustermid() {
		return sustermid;
	}
	public void setSustermid(String sustermid) {
		this.sustermid = sustermid;
	}
	public Integer getSustermseq() {
		return sustermseq;
	}
	public void setSustermseq(Integer sustermseq) {
		this.sustermseq = sustermseq;
	}
	public String getSusoprid() {
		return susoprid;
	}
	public void setSusoprid(String susoprid) {
		this.susoprid = susoprid;
	}
	public Integer getSusrdate() {
		return susrdate;
	}
	public void setSusrdate(Integer susrdate) {
		this.susrdate = susrdate;
	}
	public Integer getSusrtime() {
		return susrtime;
	}
	public void setSusrtime(Integer susrtime) {
		this.susrtime = susrtime;
	}
	public Integer getSuscendate() {
		return suscendate;
	}
	public void setSuscendate(Integer suscendate) {
		this.suscendate = suscendate;
	}
	public Integer getSuscentime() {
		return suscentime;
	}
	public void setSuscentime(Integer suscentime) {
		this.suscentime = suscentime;
	}
	public Integer getSustxncount() {
		return sustxncount;
	}
	public void setSustxncount(Integer sustxncount) {
		this.sustxncount = sustxncount;
	}
	public String getSusbefbal() {
		return susbefbal;
	}
	public void setSusbefbal(String susbefbal) {
		this.susbefbal = susbefbal;
	}
	public String getSusaftbal() {
		return susaftbal;
	}
	public void setSusaftbal(String susaftbal) {
		this.susaftbal = susaftbal;
	}
	public String getSusamt() {
		return susamt;
	}
	public void setSusamt(String susamt) {
		this.susamt = susamt;
	}
	public Integer getSusval() {
		return susval;
	}
	public void setSusval(Integer susval) {
		this.susval = susval;
	}
	public Integer getSushandingcharger() {
		return sushandingcharger;
	}
	public void setSushandingcharger(Integer sushandingcharger) {
		this.sushandingcharger = sushandingcharger;
	}
	public String getSustacbuf() {
		return sustacbuf;
	}
	public void setSustacbuf(String sustacbuf) {
		this.sustacbuf = sustacbuf;
	}
	public String getSusspecinfo() {
		return susspecinfo;
	}
	public void setSusspecinfo(String susspecinfo) {
		this.susspecinfo = susspecinfo;
	}
	public Integer getSustestflag() {
		return sustestflag;
	}
	public void setSustestflag(Integer sustestflag) {
		this.sustestflag = sustestflag;
	}
	public Integer getSuserr() {
		return suserr;
	}
	public void setSuserr(Integer suserr) {
		this.suserr = suserr;
	}
	public Integer getSusinnerr() {
		return susinnerr;
	}
	public void setSusinnerr(Integer susinnerr) {
		this.susinnerr = susinnerr;
	}
	public Integer getSuspkgid() {
		return suspkgid;
	}
	public void setSuspkgid(Integer suspkgid) {
		this.suspkgid = suspkgid;
	}
	public Integer getSustxnorder() {
		return sustxnorder;
	}
	public void setSustxnorder(Integer sustxnorder) {
		this.sustxnorder = sustxnorder;
	}
	public Integer getSusadjflag() {
		return susadjflag;
	}
	public void setSusadjflag(Integer susadjflag) {
		this.susadjflag = susadjflag;
	}
	public Integer getSusadjdate() {
		return susadjdate;
	}
	public void setSusadjdate(Integer susadjdate) {
		this.susadjdate = susadjdate;
	}
	public Integer getSusadjamt() {
		return susadjamt;
	}
	public void setSusadjamt(Integer susadjamt) {
		this.susadjamt = susadjamt;
	}
	public Integer getSusadjval() {
		return susadjval;
	}
	public void setSusadjval(Integer susadjval) {
		this.susadjval = susadjval;
	}
	public Integer getSusadjseq() {
		return susadjseq;
	}
	public void setSusadjseq(Integer susadjseq) {
		this.susadjseq = susadjseq;
	}
	public String getSusrsvd() {
		return susrsvd;
	}
	public void setSusrsvd(String susrsvd) {
		this.susrsvd = susrsvd;
	}
	
	
	private	Integer	susadjamt	;
	private	Integer	susadjval	;
	private	Integer	susadjseq	;
	private	String	susrsvd	;

	public String getSustxncodedesc() {
		return sustxncodedesc;
	}
	public void setSustxncodedesc(String sustxncodedesc) {
		this.sustxncodedesc = sustxncodedesc;
	}
	public String getSusinntypedesc() {
		return susinntypedesc;
	}
	public void setSusinntypedesc(String susinntypedesc) {
		this.susinntypedesc = susinntypedesc;
	}
	public String getSuscdkinddesc() {
		return suscdkinddesc;
	}
	public void setSuscdkinddesc(String suscdkinddesc) {
		this.suscdkinddesc = suscdkinddesc;
	}
	public String getSuscdphykinddesc() {
		return suscdphykinddesc;
	}
	public void setSuscdphykinddesc(String suscdphykinddesc) {
		this.suscdphykinddesc = suscdphykinddesc;
	}
	public String getSustranstypedesc() {
		return sustranstypedesc;
	}
	public void setSustranstypedesc(String sustranstypedesc) {
		this.sustranstypedesc = sustranstypedesc;
	}
	public String getSuscrdstatdesc() {
		return suscrdstatdesc;
	}
	public void setSuscrdstatdesc(String suscrdstatdesc) {
		this.suscrdstatdesc = suscrdstatdesc;
	}
	public String getSusunitiddesc() {
		return susunitiddesc;
	}
	public void setSusunitiddesc(String susunitiddesc) {
		this.susunitiddesc = susunitiddesc;
	}
	public String getSusnetiddesc() {
		return susnetiddesc;
	}
	public void setSusnetiddesc(String susnetiddesc) {
		this.susnetiddesc = susnetiddesc;
	}
	public String getSustestflagdesc() {
		return sustestflagdesc;
	}
	public void setSustestflagdesc(String sustestflagdesc) {
		this.sustestflagdesc = sustestflagdesc;
	}
	public String getSuserrdesc() {
		return suserrdesc;
	}
	public void setSuserrdesc(String suserrdesc) {
		this.suserrdesc = suserrdesc;
	}
	public String getSusinnerrdesc() {
		return susinnerrdesc;
	}
	public void setSusinnerrdesc(String susinnerrdesc) {
		this.susinnerrdesc = susinnerrdesc;
	}
}
