package com.web.form.administration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UnitLevelReturn implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer totallevel;
	private Integer currentlevel;
	private List<UnitLevelReturnValue> valuelist= new ArrayList<UnitLevelReturnValue>();
	
	public Integer getTotallevel() {
		return totallevel;
	}

	public void setTotallevel(Integer totallevel) {
		this.totallevel = totallevel;
	}

	public Integer getCurrentlevel() {
		return currentlevel;
	}

	public void setCurrentlevel(Integer currentlevel) {
		this.currentlevel = currentlevel;
	}

	public List<UnitLevelReturnValue> getValuelist() {
		return valuelist;
	}

	public void setValuelist(List<UnitLevelReturnValue> valuelist) {
		this.valuelist = valuelist;
	}

	public void put(String value, String name){
		UnitLevelReturnValue item = new UnitLevelReturnValue();
		item.setValue(value);
		item.setName(name);
		valuelist.add(item);
	}
	
	public class UnitLevelReturnValue{
		private String value;
		private String  name;
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
