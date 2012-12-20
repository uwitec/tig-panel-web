package com.application.webserver;

public class WlCommonUtil {

	//四位单证类型的获取
	public static final String getBillClass_Four(String insurance_no){
		return insurance_no.substring(0, 4);
	}
	
	//四位单证类型的对应的流水号的获取
	public static final long getBillNo_Four(String insurance_no){
		String billno_str =  insurance_no.substring(4, insurance_no.length());
		return Long.parseLong(billno_str);
	}
}
