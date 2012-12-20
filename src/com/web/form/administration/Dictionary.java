/**
 * 
 */
package com.web.form.administration;

import java.io.Serializable;

import com.web.form.base.BaseForm;

public class Dictionary extends BaseForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String    dictType;

	private String    dictTypeDesc;

	private String   dictValue;

	private String    dictValueDesc;
	
	private String    locale;

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictTypeDesc() {
		return dictTypeDesc;
	}

	public void setDictTypeDesc(String dictTypeDesc) {
		this.dictTypeDesc = dictTypeDesc;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getDictValueDesc() {
		return dictValueDesc;
	}

	public void setDictValueDesc(String dictValueDesc) {
		this.dictValueDesc = dictValueDesc;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
}
