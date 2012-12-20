package com.web.action.report.htreport.comm;

import java.util.LinkedList;
import java.util.List;


import com.web.action.report.htreport.dao.JDBCDao;
import com.web.action.report.htreport.domain.ReportRpQuery;

public class CascadingSelect {
	
	private JDBCDao jdbcDao;	
	
	public JDBCDao getJdbcDao() {
		return jdbcDao;
	}
	public void setJdbcDao(JDBCDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public static String SQL_IN_VALUE="SELECT u.UNITID FROM BIUNITINFOTB u START WITH u.UNITID = '?' CONNECT BY u.PARENTUNITID = PRIOR u.UNITID";
	
	private ReportRpQuery  forefather;
	
	private String selectName;
	
	private String outKeyName;
	
	private String outKeyMap;
	
	private String noRightLevel;
	
	private String[][] level;
	
	private String casKey;
	
	private String noRightValue;
	
	private String noRightText;
	
	private String valueKeySql;
	
	private List<ReportRpQuery> rpQuery=new LinkedList<ReportRpQuery>();
	
	
	public CascadingSelect(ReportRpQuery forefather){
		this.forefather=forefather;
		splitOutKey(forefather.getOutKey());
	}
	
	private void splitOutKey(String outKey){
		if(outKey==null){
			return;
		}
//		outKey=outKey.replace("_cas_", "");
		String[] casArray=outKey.split("[|]");
		
		this.selectName=casArray[0];
		this.outKeyName=casArray[1];
		this.outKeyMap=casArray[2];
		this.valueKeySql=casArray[3];
		this.casKey=this.selectName+"|"+outKeyName+"|"+outKeyMap;
		
		
	}
	
	public void setRight(String noRightValue,String noRightText,String noRightLevel){
		this.noRightText=noRightText;
		this.noRightValue=noRightValue;
		this.noRightLevel=noRightLevel;
	}
	
	
	public void createRpQuery(){
		
		String fatherName=null;
		boolean isStart=false;
		for(int i=0;i<level.length;i++){
			if(noRightLevel!=null&&!noRightLevel.equals("")){
				if(!level[i][0].equals(noRightLevel)&&!isStart){
					continue;
				}
			}
			
			ReportRpQuery rp=this.forefather.copy();
			rp.setInputType(Constant.INPUT_TYPE_CAS);
			rp.setfChn(level[i][1]);
			rp.setfEng("_cas_"+rp.getfEng()+i);
			rp.setSpData("casKey");
			
		//	rp.setSpData(getHeadSelect(rp.getfEng(),rp.getSpData(),casKey,noRightValue,noRightText));
			if(isStart){
				this.valueKeySql=null;
			}
			
			rp.setSpData(getCasSelect(rp.getfEng(),rp.getSpData(),casKey,fatherName,noRightValue,noRightText,this.valueKeySql));
			
			fatherName=rp.getfEng();
			this.noRightValue=null;
			this.noRightText=null;
			this.rpQuery.add(rp);
			isStart=true;
		}
		
	}
	
	
	/**
	 * 
	 * @param selectName    unitid
	 * @param stat    casKey
	 * @param casKey    
	 * @param fatherName    _cas_unitid?
	 * @param noRightValue    
	 * @param noRightText
	 * @return
	 */
	private String getCasSelect(String selectName,String stat,String casKey,String fatherName,String noRightValue,String noRightText,String valueKeySql  ){

		
		
		String actionUrl="../webpages/report/sfw_handleRequest?stat="+stat+"&casKey="+casKey;
		
		
		//处理级联查询时的相关匹配
		if(fatherName!=null){
			actionUrl+="&fathername="+fatherName;
		}
		
		StringBuffer sb=new StringBuffer("<input type='text' name='sfw_"+selectName+"'  stat='"+selectName+"' size=\"30\" columns=\"2\" capture=\"1\" action=\""+actionUrl+"\" ");
		if(fatherName!=null){
			sb.append("fathername='sfw_"+fatherName+"' ");
		}
		
		if(noRightValue!=null){
			sb.append(" norightvalue='"+noRightValue+"' ");
			sb.append(" norighttext='"+noRightText+"' ");
			
		}
		
		
		sb.append(" > ");
		sb.append("<input type='text' name='"+selectName+"'   readonly > ");
		if(valueKeySql!=null){
			sb.append("<input name='valueKeySql' type='text' style='display:none' value='"+valueKeySql+"'>");
		}
		
		return sb.toString();
	}
	
	public String[][] getLevel() {
		return level;
	}
	public void setLevel(String[][] level) {
		this.level = level;
	}
	public List<ReportRpQuery> getRpQuery() {
		return rpQuery;
	}
	public void setRpQuery(List<ReportRpQuery> rpQuery) {
		this.rpQuery = rpQuery;
	}
	
	
	
}
