package com.web.action.report.htreport.web.tag;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import com.web.action.report.htreport.comm.Constant;
import com.web.action.report.htreport.dao.BaseDao;
import com.web.action.report.htreport.domain.ReportRightFunction;
import com.web.action.report.htreport.domain.ReportRightUser;
import com.web.action.report.htreport.util.SpringBeanUtil;






public class RightTag extends TagSupport{
	private static final long serialVersionUID = 6920029415044488731L;
	private String funId;

	public RightTag(){
		
    }

    public int doStartTag() throws JspTagException  {
	    try{
	    	//ReportRightUser user=(ReportRightUser)pageContext.getSession().getAttribute(Constant.REPORTRIGHTUSER);
	    	//if(user==null)return 0;
	    	BaseDao baseDao=(BaseDao)SpringBeanUtil.getBean("baseDao");
	    	StringBuffer sb=new StringBuffer("<script type=\"text/javascript\">\n");
	    	Map map=new HashMap();
	    	map.put("parentId",funId);
	    	map.put("funType",2);
	    	map.put("userId","1");
	    	List<ReportRightFunction> list=baseDao.findObjectsByCondition("ReportRightFunction.getTreesByUserId",map);
	    	sb.append("var right_btns=[");
	    	StringBuffer keySb=new StringBuffer("var right_keys=[");
	    	StringBuffer userRgihts=new StringBuffer();
	    	if(list!=null&&list.size()!=0){
	    		for(ReportRightFunction rf:list){
		    		sb.append(rf.getBtn()).append(",");
		    		keySb.append(rf.getNotes()).append(",");
		    		userRgihts.append("var right_").append(rf.getFunId()).append("=true;\n");
		    	}
	    		sb.deleteCharAt(sb.length()-1);
	    		keySb.deleteCharAt(keySb.length()-1);
	    	}
	    	sb.append("];\n");
	    	keySb.append("];\n");
	    	
	    	map.put("funType",3);
	    	List<ReportRightFunction> listCol=baseDao.findObjectsByCondition("ReportRightFunction.getTreesByUserId",map);
	    	if(listCol!=null&&listCol.size()!=0){
	    		keySb.append("var right_cols=").append(listCol.get(0).getBtn()).append(";\n");
	    	}
	    	
	    	sb.append(keySb.toString()).append(userRgihts.toString()).append("</script>\n");
			pageContext.getOut().write(sb.toString());
			pageContext.getOut().flush();
	    }catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return SKIP_BODY;//this.EVAL_BODY_INCLUDE
    }

  public int doEndTag() throws JspTagException  {
    return EVAL_PAGE;
  }

  public void release()    {
      super.release();
  }

	public String getFunId() {
		return funId;
	}
	
	public void setFunId(String funId) {
		this.funId = funId;
	}


}
