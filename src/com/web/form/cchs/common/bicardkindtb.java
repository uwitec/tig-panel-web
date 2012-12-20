package com.web.form.cchs.common;

import java.io.Serializable;

import com.web.form.base.BaseForm;

public class bicardkindtb extends BaseForm implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int crdtype;
	
	private String name;
	
	private int mediatype;

	public int getCrdtype() {
		return crdtype;
	}

	public void setCrdtype(int crdtype) {
		this.crdtype = crdtype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	private String mediatypedesc;

	public String getMediatypedesc() {
		return mediatypedesc;
	}

	public void setMediatypedesc(String mediatypedesc) {
		this.mediatypedesc = mediatypedesc;
	}

	public int getMediatype() {
		return mediatype;
	}

	public void setMediatype(int mediatype) {
		this.mediatype = mediatype;
	}
	
	
}
