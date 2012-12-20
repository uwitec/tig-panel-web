package com.web.action.report.htreport.util;

import java.util.List;
import java.util.Map;

import com.web.action.report.htreport.comm.Constant;
import com.web.action.report.htreport.domain.BaseBean;



public class DictInfoUtil {
	
	public static String getDictCacheByCode(String dictEng,String codeValue){
		if(codeValue==null)return null;
		if(Constant.COMBOX_CODE_HM==null)return codeValue;
		Map dictCode=Constant.COMBOX_CODE_HM.get(dictEng);
		if(dictCode==null)return codeValue;
		String codeDesc=(String)dictCode.get(codeValue);
		if(codeDesc==null)return codeValue;
		return codeDesc;
	}
	
	public static String getComboCacheByDictEng(String dictEng){
		if(Constant.COMBOX_STR_HM==null)return "[]";
		if(dictEng==null||dictEng.equals(""))return "[]";
		String str=Constant.COMBOX_STR_HM.get(dictEng);
		str=(str==null)?"[]":str;
		return str;
	}
	
	public static void setListTransAble(List<BaseBean> list){
		for(BaseBean baseBean:list){
			baseBean.transAble=true;
		}
	}
	
	public static void setBeanTransAble(BaseBean baseBean){
		baseBean.transAble=true;
	}
	
}
