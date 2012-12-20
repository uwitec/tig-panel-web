/**
 * 
 */
package com.web.service.base;

import java.util.List;

import com.application.exception.AppErrorcode;
import com.application.exception.AppException;
import com.web.common.ServiceReturn;
import com.web.form.base.BaseForm;

/**
 * @author David
 *
 */
public class PageQueryService extends BaseService implements
		IPageQueryService {
	//protected String    formBeanName;
	//protected String    queryNameForCount;
	//protected String    queryName;
	//protected String    queryName2;
	//protected String    formBeanName2;
	
	public ServiceReturn queryPage(Object obj, String queryName, String queryNameForCount) throws Exception {
		// TODO Auto-generated method stub
		if(obj != null){
			if (obj instanceof BaseForm) {
				BaseForm baseForm = (BaseForm)obj;
				if(!baseForm.getLimit().equals(0)){//get records from certain page
					if (baseForm.getStart() == -1) {//query last page
						Integer count = sqlDao_i.getRecord(queryNameForCount, obj);
						Integer page = (count % baseForm.getLimit() == 0) ? 
								count / baseForm.getLimit() : count / baseForm.getLimit() + 1;
						baseForm.setRowStart((page - 1)* baseForm.getLimit() + 1);
						baseForm.setRowEnd(baseForm.getRowStart()
								+ (count % baseForm.getLimit() == 0 ? baseForm.getLimit() : count % baseForm.getLimit())
								- 1);
						baseForm.setTotal(page);
					} else {
//						baseForm.setRowStart((baseForm.getStart() - 1) * baseForm.getLimit() + 1);
//						baseForm.setRowEnd(baseForm.getRowStart() + baseForm.getLimit() - 1);
						
						baseForm.setRowStart((baseForm.getStart() - 1) * baseForm.getLimit());
						baseForm.setRowEnd(baseForm.getRowStart() + baseForm.getLimit() - 1);
						baseForm.setTotal(0);
					}
					List<?> resultList = sqlDao_i.getRecordList(queryName, obj);
					//List<?> resultList = sqlDao_i.getRecordList(queryName, obj, baseForm.getRowStart() - 1, baseForm.getLimit());
					ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
					ret.put(ServiceReturn.FIELD1, resultList);
					BaseForm retPageInfo = new BaseForm(baseForm);
					ret.put(ServiceReturn.FIELD2, retPageInfo);
					return ret;
				}else{
					Integer count = sqlDao_i.getRecord(queryNameForCount, obj);
					baseForm.setRowStart(1);
					baseForm.setRowEnd(count);
					baseForm.setTotal(1);
					//List<?> resultList = sqlDao_i.getRecordList(queryName, obj, 0, count);
					List<?> resultList = sqlDao_i.getRecordList(queryName, obj);
					ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
					ret.put(ServiceReturn.FIELD1, resultList);
					BaseForm retPageInfo = new BaseForm(baseForm);
					ret.put(ServiceReturn.FIELD2, retPageInfo);
					return ret;
				}
			}else{
				throw new AppException(AppErrorcode.PARAMETEROBJECTWRONGTYPE);
			}
		}else{
			//throws exception
			throw new AppException(AppErrorcode.PARAMETEROBJECTISNULL);
		}
	}
}
