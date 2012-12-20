/**
 * 
 */
package com.web.service.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dao.core.IHibernateGenericDao;
import com.dao.core.IbatisGenericDao;

/**
 * @author David
 *
 */
public abstract class BaseService implements IBaseService {
	protected IHibernateGenericDao sqlDao_h;
	protected IbatisGenericDao     sqlDao_i;

	public IHibernateGenericDao getSqlDao_h() {
		return sqlDao_h;
	}

	public void setSqlDao_h(IHibernateGenericDao sqlDao_h) {
		this.sqlDao_h = sqlDao_h;
	}

	public IbatisGenericDao getSqlDao_i() {
		return sqlDao_i;
	}

	public void setSqlDao_i(IbatisGenericDao sqlDao_i) {
		this.sqlDao_i = sqlDao_i;
	}
	
	/**************保险出入库管理-start****************/
	//获得ID
	public static String getId(boolean isJustNum) {
		// TODO Auto-generated method stub
		java.util.Calendar c = java.util.Calendar.getInstance();
		Date d1 = c.getTime();
		String todaystr = new SimpleDateFormat("yyyyMMddHHmmss").format(d1);
		
		java.util.Random r=new java.util.Random();
		String rad = "";
		
		for(int i=0;i<6;i++){
			int l = r.nextInt(2);
			if(isJustNum&&l==0){//产生一个随机数
				int x = r.nextInt(9);
				rad += Integer.toString(x);
			}else{//产生一个随机字母
				char d = (char)('A'+Math.random()*('Z'-'A'+1));
				rad += String.valueOf(d);
			}
		}
		
		return todaystr+rad;
	}
	
	//
	public String getNowTime(){
		java.util.Calendar c = java.util.Calendar.getInstance();
		Date d1 = c.getTime();
		String todaystr = new SimpleDateFormat("yyyyMMddHHmmss").format(d1);
		return todaystr;
	}
	
	/**************保险出入库管理-end****************/
}
