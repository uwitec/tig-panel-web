package com.web.action.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.struts2.ServletActionContext;

import com.application.webserver.ApplicationConstants;
import com.web.common.ServiceReturn;
import com.web.form.administration.Dictionary;
import com.web.form.administration.Unit;


public class CommonAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	public String getSystemDictionaryItem() throws Exception{
		
		String item_id = ServletActionContext.getRequest().getParameter("item_id");
		List<Dictionary> dictList = new ArrayList<Dictionary>();
		Map<String,Map<String,String>> systemDict 
			= (Map<String, Map<String, String>>)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMDICTIONARY);
		Map<String,String> item = systemDict.get(item_id);
		if(item != null){
			Set<Entry<String, String>> entrySet = item.entrySet();
			for(Entry<String, String> entry : entrySet){
				Dictionary dict = new Dictionary();
				dict.setDictType(item_id);
				dict.setDictValue(entry.getKey());
				dict.setDictValueDesc(super.getText(entry.getValue()));
				dictList.add(dict);
			}
		}
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, dictList);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
		return null;
	}
	
	public String getUnitTreeList1() throws Exception{
		List<Unit> unit_list = super.getUnitTreeList();
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, unit_list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
		return null;
	}
	
	public String getUnitListAtLevel1() throws Exception{
		String level = ServletActionContext.getRequest().getParameter("level");
		List<Unit> unit_list = super.getUnitListAtLevel(Integer.parseInt(level));
		ServiceReturn ret = new ServiceReturn(true, "");
		ret.put(ServiceReturn.FIELD1, unit_list);
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().print(super.convertServiceReturnToJson(ret));
		return null;
	}
	

}
