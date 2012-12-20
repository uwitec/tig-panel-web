package com.web.action.report.htreport.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.web.action.report.htreport.comm.CascadingSelect;
import com.web.action.report.htreport.comm.Constant;
import com.web.action.report.htreport.comm.SelectHelper;
import com.web.action.report.htreport.dao.BaseDao;
import com.web.action.report.htreport.dao.JDBCDao;
import com.web.action.report.htreport.domain.ReportRpBase;
import com.web.action.report.htreport.domain.ReportRpColumn;
import com.web.action.report.htreport.domain.ReportRpQuery;
import com.web.action.report.htreport.report.producer.ReportProducer;
import com.web.form.administration.Unit;
import com.web.service.communication.CommunicationService;


public class ReportServiceImpl implements ReportService{
	
	BaseDao baseDao;
	JDBCDao jdbcDao;
	private SelectHelper selectHelper;
	
	public SelectHelper getSelectHelper() {
		return selectHelper;
	}
	public void setSelectHelper(SelectHelper selectHelper) {
		this.selectHelper = selectHelper;
	}
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public JDBCDao getJdbcDao() {
		return jdbcDao;
	}
	public void setJdbcDao(JDBCDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

    /*
     * (non-Javadoc)
     * @生成报表
     */
	public String query(String reportId,List<StringBuffer> paras) {
		ReportRpBase reportRpBase=(ReportRpBase)baseDao.findObjectByValue("ReportRpBase.findById",reportId);
		Map<String, String> map=new HashMap<String, String>();
		map.put("reportId",reportId);
		List<ReportRpColumn> reportRpColumns=baseDao.findObjectsByCondition("ReportRpColumn.findByCondition",map);
		int i=0;
		String sql=reportRpBase.getSql();
		for(StringBuffer sb:paras){
			String str_tmp = sb.toString();
			int localsp1 = str_tmp.indexOf("&");
			int localsp2 = str_tmp.indexOf("&",localsp1+1);
			int kaishisp1 = str_tmp.indexOf("@");
			int kaishisp2 = str_tmp.indexOf("@",kaishisp1+1);
			int leijisp1 = str_tmp.indexOf("#");
			int leijisp2 = str_tmp.indexOf("#",leijisp1+1);
			int jiesuriqi = str_tmp.indexOf("*");
			if(localsp1 != -1){
				String localstr = str_tmp.substring(0, localsp1) + str_tmp.substring(localsp2+1, str_tmp.length());
				String spstr = str_tmp.substring(localsp1+1, localsp2);
				int ii = ++i;
				if(localsp1 != -1){
					sql=sql.replaceAll("&:"+ii,spstr);
					sql=sql.replaceAll("&", spstr.substring(spstr.length()-2, spstr.length()-1));
				}
				sql=sql.replace("?:"+ii,localstr);
			}else if(kaishisp1!=-1){
				String localstr = str_tmp.substring(0, kaishisp1) + str_tmp.substring(leijisp2+1, str_tmp.length());
				String spstr1 = str_tmp.substring(kaishisp1+1, kaishisp2);
				String spstr2 = str_tmp.substring(leijisp1+1, leijisp2);
				int ii = ++i;
				sql=sql.replaceAll("@@",spstr1);
				sql=sql.replaceAll("##",spstr2);
				sql=sql.replace("?:"+ii,localstr);
				sql=sql.replace("??"+ii,localstr);
			}else if(jiesuriqi!=-1){
				int j=++i;
				String str1=str_tmp.substring(jiesuriqi+1);
				String str2=str1.replaceAll(">", "");
				str2=str1.replaceAll("<", "");
				sql=sql.replace("?:"+j,str1);
				sql=sql.replace("*",str2);
			}else{
				int j=++i;
				sql=sql.replace("?:"+(j),sb.toString());
				sql=sql.replace("??"+(j),sb.toString());
			}
			

		}
		if(paras.size()==0){
			sql=sql.replace("?:1","");
			sql=sql.replace("&:1","");
			sql=sql.replace("??2","");
		}
		
		System.out.println(sql);
		String[][] data=jdbcDao.getTDArrayBySql(sql);
		if(data==null||data.length==0)
			if("1052".equals(reportId)){//部分卡类型记名信息查询
				String sql1="select t.crdpaccno,e.name from BIACTCARDACCTB t left join BICARDKINDTB e "
			           + "on (t.crdtype = e.CRDTYPE and e.mediatype = 1) where 1=1 "+paras.get(0).toString();
				String[][] data2=jdbcDao.getTDArrayBySql(sql1);
				String result="",result1="";
				if(data2==null||data2.length==0){
					result="此卡不存在,请确认是否输入正确的卡面号"+"\\r\\n";
					result1="此卡不存在,请确认是否输入正确的卡面号"+"\r\n";
				}else{
					for(int x=0; x<data2.length; x++){
						result="卡内号为"+data2[x][0]+",其卡类型为"+data2[x][1]+"\\r\\n";		
						result1="卡内号为"+data2[x][0]+",其卡类型为"+data2[x][1]+"\r\n";	
					}
				}
				result="不能查询此卡信息."+result1+"<script   language=javascript> alert('不能查询此卡信息."+result+"'); </script>";
				return result;
			}
				else
			return "查询结果为空！";
		
		//victor.xu
		//并去除由rollup 产生的NULL数据内的 未知
		try {
			int rownum = data.length;
			int colspan = data[0].length;
			for(int x=0; x<rownum; x++){
				int flag=0;
				for(int y=0; y<colspan; y++){
					if(data[x][y] == null || data[x][y].toString().equals("")){
						flag = 1;
						break;
					}
				}
				for(int yy=0; yy<colspan; yy++){
					if(data[x][yy] == null){
						continue;
					}
					if(data[x][yy].toString().equals("未知") && flag==1){
						if(x == (rownum-1)){
							data[x][yy] = null;
						}else{
							//data[x][yy] = "";
						}
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//end
	
		ReportProducer reportProducer=new ReportProducer(reportRpColumns,reportRpBase,data,"","");
		return reportProducer.getQueryResult();
	}
	
	
    /*
     * (non-Javadoc)
     * @生成打印预览
     */
	public Map printWatch(String reportId,List<StringBuffer> paras,String dateInfo,String parametersStr) {
		Map rtn=new HashMap();
		ReportRpBase reportRpBase=(ReportRpBase)baseDao.findObjectByValue("ReportRpBase.findById",reportId);
		Map map=new HashMap();
		map.put("reportId",reportId);
		List<ReportRpColumn> reportRpColumns=baseDao.findObjectsByCondition("ReportRpColumn.findByCondition",map);
		int i=0;
		String sql=reportRpBase.getSql();
		for(StringBuffer sb:paras){
			String str_tmp = sb.toString();
			int localsp1 = str_tmp.indexOf("&");
			int localsp2 = str_tmp.indexOf("&",localsp1+1);
			int kaishisp1 = str_tmp.indexOf("@");
			int kaishisp2 = str_tmp.indexOf("@",kaishisp1+1);
			int leijisp1 = str_tmp.indexOf("#");
			int leijisp2 = str_tmp.indexOf("#",leijisp1+1);
			int jiesuriqi = str_tmp.indexOf("*");
			if(localsp1 != -1){
				String localstr = str_tmp.substring(0, localsp1) + str_tmp.substring(localsp2+1, str_tmp.length());
				String spstr = str_tmp.substring(localsp1+1, localsp2);
				int ii = ++i;
				if(localsp1 != -1){
					sql=sql.replaceAll("&:"+ii,spstr);
					sql=sql.replaceAll("&", spstr.substring(spstr.length()-2, spstr.length()-1));
				}
				sql=sql.replace("?:"+ii,localstr);
				sql=sql.replace("??"+ii,localstr);
			}else if(kaishisp1!=-1){
				String localstr = str_tmp.substring(0, kaishisp1) + str_tmp.substring(leijisp2+1, str_tmp.length());
				String spstr1 = str_tmp.substring(kaishisp1+1, kaishisp2);
				String spstr2 = str_tmp.substring(leijisp1+1, leijisp2);
				int ii = ++i;
				sql=sql.replaceAll("@@",spstr1);
				sql=sql.replaceAll("##",spstr2);
				sql=sql.replace("?:"+ii,localstr);
				sql=sql.replace("??"+ii,localstr);
			}else if(jiesuriqi!=-1){
				int j=++i;
				String str1=str_tmp.substring(jiesuriqi+1);
				String str2=str1.replaceAll(">", "");
				str2=str1.replaceAll("<", "");
				sql=sql.replace("?:"+j,str1);
				sql=sql.replace("*",str2);
			}else{
				int j=++i;
				sql=sql.replace("?:"+(j),sb.toString());
				sql=sql.replace("??"+(j),sb.toString());
			}

		}
		if(paras.size()==0){
			sql=sql.replace("?:1","");
			sql=sql.replace("&:1","");
			sql=sql.replace("??2","");
		}
		for(StringBuffer sb:paras){
			sql=sql.replace("?:"+(++i),sb.toString());
			sql=sql.replace("??"+(++i),sb.toString());
		}
		if(paras.size()==0){
			sql=sql.replace("?:1","");
			sql=sql.replace("??2","");
		}
		String[][] data=jdbcDao.getTDArrayBySql(sql);
		if(data==null||data.length==0){
			rtn.put("report","查询结果为空！");
			rtn.put("reportRpBase",reportRpBase);
			return rtn;
		}
		
		//victor.xu
		//并去除由rollup 产生的NULL数据内的 未知
		try {
			int rownum = data.length;
			int colspan = data[0].length;
			for(int x=0; x<rownum; x++){
				int flag=0;
				for(int y=0; y<colspan; y++){
					if(data[x][y] == null || data[x][y].toString().equals("")){
						flag = 1;
						break;
					}
				}
				for(int yy=0; yy<colspan; yy++){
					if(data[x][yy] == null){
						continue;
					}
					if(data[x][yy].toString().equals("未知") && flag==1){
						if(x == (rownum-1)){
							data[x][yy] = null;
						}else{
							//data[x][yy] = "";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//end
		
		ReportProducer reportProducer=new ReportProducer(reportRpColumns,reportRpBase,data,dateInfo,parametersStr);
		rtn.put("report",reportProducer.getPrintWatchReport());
		rtn.put("reportRpBase",reportRpBase);
		return rtn;
	}
	
	public Map excel(String reportId,List<StringBuffer> paras,String dateInfo,String parametersStr) {
		Map rtn=new HashMap();
		ReportRpBase reportRpBase=(ReportRpBase)baseDao.findObjectByValue("ReportRpBase.findById",reportId);
		Map map=new HashMap();
		map.put("reportId",reportId);
		List<ReportRpColumn> reportRpColumns=baseDao.findObjectsByCondition("ReportRpColumn.findByCondition",map);
		int i=0;
		String sql=reportRpBase.getSql();
		for(StringBuffer sb:paras){
			String str_tmp = sb.toString();
			int localsp1 = str_tmp.indexOf("&");
			int localsp2 = str_tmp.indexOf("&",localsp1+1);
			int kaishisp1 = str_tmp.indexOf("@");
			int kaishisp2 = str_tmp.indexOf("@",kaishisp1+1);
			int leijisp1 = str_tmp.indexOf("#");
			int leijisp2 = str_tmp.indexOf("#",leijisp1+1);
			int jiesuriqi = str_tmp.indexOf("*");
			if(localsp1 != -1){
				String localstr = str_tmp.substring(0, localsp1) + str_tmp.substring(localsp2+1, str_tmp.length());
				String spstr = str_tmp.substring(localsp1+1, localsp2);
				int ii = ++i;
				if(localsp1 != -1){
					sql=sql.replaceAll("&:"+ii,spstr);
					sql=sql.replaceAll("&", spstr.substring(spstr.length()-2, spstr.length()-1));
				}
				sql=sql.replace("?:"+ii,localstr);
				sql=sql.replace("??"+ii,localstr);
			}else if(kaishisp1!=-1){
				String localstr = str_tmp.substring(0, kaishisp1) + str_tmp.substring(leijisp2+1, str_tmp.length());
				String spstr1 = str_tmp.substring(kaishisp1+1, kaishisp2);
				String spstr2 = str_tmp.substring(leijisp1+1, leijisp2);
				int ii = ++i;
				sql=sql.replaceAll("@@",spstr1);
				sql=sql.replaceAll("##",spstr2);
				sql=sql.replace("?:"+ii,localstr);
				sql=sql.replace("??"+ii,localstr);
			}else if(jiesuriqi!=-1){
				int j=++i;
				String str1=str_tmp.substring(jiesuriqi+1);
				String str2=str1.replaceAll(">", "");
				str2=str1.replaceAll("<", "");
				sql=sql.replace("?:"+j,str1);
				sql=sql.replace("*",str2);
			}else{
				int j=++i;
				sql=sql.replace("?:"+(j),sb.toString());
				sql=sql.replace("??"+(j),sb.toString());
			}

		}
		if(paras.size()==0){
			sql=sql.replace("?:1","");
			sql=sql.replace("&:1","");
			sql=sql.replace("??2","");
		}
		for(StringBuffer sb:paras){
			sql=sql.replace("?:"+(++i),sb.toString());
			sql=sql.replace("??"+(++i),sb.toString());
		}
		String[][] data=jdbcDao.getTDArrayBySql(sql);
		if(data==null||data.length==0){
			rtn.put("report","查询结果为空！");
			rtn.put("reportRpBase",reportRpBase);
			return rtn;
		}
		
		//victor.xu
		//并去除由rollup 产生的NULL数据内的 未知
		try {
			int rownum = data.length;
			int colspan = data[0].length;
			for(int x=0; x<rownum; x++){
				int flag=0;
				for(int y=0; y<colspan; y++){
					if(data[x][y] == null || data[x][y].toString().equals("")){
						flag = 1;
						break;
					}
				}
				for(int yy=0; yy<colspan; yy++){
					if(data[x][yy] == null){
						continue;
					}
					if(data[x][yy].toString().equals("未知") && flag==1){
						if(x == (rownum-1)){
							data[x][yy] = null;
						}else{
							//data[x][yy] = "";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//end
		
		ReportProducer reportProducer=new ReportProducer(reportRpColumns,reportRpBase,data,dateInfo,parametersStr);
		rtn.put("report",reportProducer.getExcelReport());
		rtn.put("reportRpBase",reportRpBase);
		return rtn;
	}
	
	public Map pdf(String reportId,List<StringBuffer> paras,String dateInfo) {
		Map rtn=new HashMap();
		ReportRpBase reportRpBase=(ReportRpBase)baseDao.findObjectByValue("ReportRpBase.findById",reportId);
		Map map=new HashMap();
		map.put("reportId",reportId);
		List<ReportRpColumn> reportRpColumns=baseDao.findObjectsByCondition("ReportRpColumn.findByCondition",map);
		int i=0;
		String sql=reportRpBase.getSql();
		for(StringBuffer sb:paras){
			sql=sql.replace("?:"+(++i),sb.toString());
		}
		String[][] data=jdbcDao.getTDArrayBySql(sql);
		if(data==null||data.length==0){
			rtn.put("report","查询结果为空！");
			rtn.put("reportRpBase",reportRpBase);
			return rtn;
		}
		ReportProducer reportProducer=new ReportProducer(reportRpColumns,reportRpBase,data,dateInfo,"");
		rtn.put("report",reportProducer.getPDFReport());
		rtn.put("reportRpBase",reportRpBase);
		return rtn;
	}
	
	/*
	 * 获取查询项
	 * 
	 */
	public Map proQuery(String reportId,Unit unit) {
		Map map=new HashMap();
		ReportRpBase reportRpBase=(ReportRpBase)baseDao.findObjectByValue("ReportRpBase.findById",reportId);
		if(reportRpBase==null){
			map.put(Constant.RTNSTATUS,96);
			map.put(Constant.RTNDESC,"您查询的报表不存在！");
			return map;
		}
		if(!reportRpBase.getStatus().equals(Constant.REPORTRPBASE_STATUS_START)){
			map.put(Constant.RTNSTATUS,96);
			map.put(Constant.RTNDESC,"您查询的报表状态不为【启用】！");
			return map;
		}
		List<ReportRpQuery> querys=baseDao.findObjectsByCondition("ReportRpQuery.findByReportId",reportId);
		
		for(int i=0;i<querys.size();i++){
			ReportRpQuery reportRpQuery=querys.get(i);
			if(reportRpQuery.getInputType().equals(Constant.INPUT_TYPE_SELECT)){
				reportRpQuery.setSpData(selectHelper.getSelectByDictEng(reportRpQuery.getOutKey(),reportRpQuery.getfEng()));
			}else if(reportRpQuery.getInputType().equals(Constant.INPUT_TYPE_SFW)){
				//changed by sx 20100720 增加外键select
				//reportRpQuery.setSpData(selectHelper.getGSelect(reportRpQuery.getfEng(),reportRpQuery.getSpData()));
				reportRpQuery.setSpData(selectHelper.getGSelect(reportRpQuery.getfEng(),reportRpQuery.getSpData(),reportRpQuery.getOutKey()));
				//changed end by sx 20100720
			}else if(reportRpQuery.getInputType().equals(Constant.INPUT_TYPE_CAS)){
				CascadingSelect cds=new CascadingSelect(reportRpQuery);
				String[][] level={{"0","中心"},{"1","单位"},{"2","网点"},{"3","充值点"}};
				cds.setLevel(level);
				cds.setJdbcDao(jdbcDao);
				//cds.setRight("1001", "d1001", "1");
				cds.createRpQuery();
				List<ReportRpQuery> rpqs = cds.getRpQuery();
				
				querys.remove(i);
				querys.addAll(i, rpqs);
				i=i+rpqs.size();
			}else if(reportRpQuery.getInputType().equals(Constant.INPUT_TYPE_MULTCAS)){
				String outKey=reportRpQuery.getOutKey();
				String checkRightSql=outKey.substring(outKey.indexOf("@")+1,outKey.length());
				outKey=outKey.substring(0, outKey.indexOf("@"));//去掉权限相关东西
				reportRpQuery.setOutKey(outKey);
				//if(isRightLevel(checkRightSql,unit.getUnitid())&&unit.getUnitlevel()!=0){//不为最高层
				if(isRightLevel(checkRightSql,unit.getNodeid())){//不为最高层
					reportRpQuery.setSpData(selectHelper.getRightSelect(reportRpQuery.getfEng(),reportRpQuery.getSpData(),reportRpQuery.getOutKey(),unit.getNodeid(),unit.getNodename()));
				}else{
					reportRpQuery.setSpData(selectHelper.getGSelect(reportRpQuery.getfEng(),reportRpQuery.getSpData(),reportRpQuery.getOutKey()));
				}
			}else if(reportRpQuery.getInputType().equals("15")){
				String outKey=reportRpQuery.getOutKey();
				String checkRightSql=outKey.substring(outKey.indexOf("@")+1,outKey.length());
				outKey=outKey.substring(0, outKey.indexOf("@"));//去掉权限相关东西
				reportRpQuery.setOutKey(outKey);
				//if(isRightLevel(checkRightSql,unit.getUnitid())&&unit.getUnitlevel()!=0){//不为最高层
				if(isRightLevel(checkRightSql,unit.getNodeid())){//不为最高层
					reportRpQuery.setSpData(selectHelper.getRightSelect(reportRpQuery.getfEng(),reportRpQuery.getSpData(),reportRpQuery.getOutKey(),unit.getNodeid(),unit.getNodename()));
				}else{
					reportRpQuery.setSpData(selectHelper.getGSelectEx(reportRpQuery.getfEng(),reportRpQuery.getSpData(),reportRpQuery.getOutKey()));
				}
			}else if(reportRpQuery.getInputType().equals(Constant.INPUT_TYPE_YEARMONTHDATE)){
				reportRpQuery.setSpData(selectHelper.getSelectByDictEng(reportRpQuery.getOutKey(),reportRpQuery.getfEng()));
			}
		}
		
		//测试结束
		map.put(Constant.RTNSTATUS,Constant.RTNSTATUS_SUCCESS);
		map.put("querys",querys);
		map.put("reportRpBase",reportRpBase);
		return map;
	}
	
	
	/**
	 * 判断权限是否在该查询项
	 * @param checkRightSql
	 * @param query
	 * @return
	 */
	private boolean isRightLevel(String checkRightSql,String query){
		String outKeySql="select outkeysql from T_REPORT_OUTKEYTB where outkeyname = '?'";
		String [][] isRightSql=jdbcDao.getTDArrayBySql(outKeySql.replaceAll("[?]",checkRightSql),1);
		String[][] data=null;
		if(isRightSql==null||isRightSql.length<1){
			return false;
		}else{
			data=jdbcDao.getTDArrayBySql(isRightSql[0][0].replaceAll("[?]",query));
		}
		
		if(data==null||data.length<1){
			return false;
		}
		
		return true;
	}

}
