package com.web.service.base;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.application.exception.AppErrorcode;
import com.application.exception.AppException;
import com.web.common.ServiceReturn;
import com.web.form.base.BaseForm;

public class PublicService extends BaseService implements IPublicService {

	static Logger logger = Logger.getLogger(PublicService.class);

	// @Override
	public ServiceReturn queryPage(Object obj, String queryName,
			String queryNameForCount) throws Exception {
		// TODO Auto-generated method stub
		if (obj != null) {
			if (obj instanceof BaseForm) {
				BaseForm baseForm = (BaseForm) obj;
				if (!baseForm.getLimit().equals(0)) {// get records from
					// certain page
					java.util.Calendar c = java.util.Calendar.getInstance();
					Date d1 = c.getTime();
					int totCount = sqlDao_i.getRecord(queryNameForCount, obj);
					baseForm.setTotCount(totCount);// 总记录数
					Integer pageCount = (totCount % baseForm.getLimit() == 0) ? totCount
							/ baseForm.getLimit()
							: totCount / baseForm.getLimit() + 1;
					baseForm.setPageCount(pageCount);// 总页数
					if (baseForm.getStart() == -1) {// query last page
						Integer count = sqlDao_i.getRecord(queryNameForCount,
								obj);
						Integer page = (count % baseForm.getLimit() == 0) ? count
								/ baseForm.getLimit()
								: count / baseForm.getLimit() + 1;
						int rowStart = (page - 1) * baseForm.getLimit() + 1;
						baseForm.setRowStart(rowStart);
						int rowEnd = baseForm.getRowStart()
								+ (count % baseForm.getLimit() == 0 ? baseForm
										.getLimit() : count
										% baseForm.getLimit()) - 1;
						baseForm.setRowEnd(rowEnd);
						baseForm.setEndInPage(rowEnd);
						if (1 == rowStart && totCount > rowEnd) {// 是首页单不是末页
							baseForm.setDelareFirstEndPage(1);
							baseForm.setTotal(1);
						} else if (1 == rowStart && totCount <= rowEnd) {// 是首页又是末页
							baseForm.setDelareFirstEndPage(2);
							baseForm.setTotal(2);
						} else if (1 != rowStart && totCount <= rowEnd) {// 是末页不是首页
							baseForm.setDelareFirstEndPage(3);
							baseForm.setTotal(page);
						} else {// 不是首页不是末页
							baseForm.setDelareFirstEndPage(0);
							baseForm.setTotal(0);
						}
					} else {
						int rowStart = (baseForm.getStart() - 1)
								* baseForm.getLimit() + 1;
						baseForm.setRowStart(rowStart);
						int rowEnd = baseForm.getRowStart()
								+ baseForm.getLimit() - 1;
						baseForm.setRowEnd(rowEnd);
						int endInPage = 0;
						if (1 == rowStart && totCount > rowEnd) {// 是首页单不是末页
							endInPage = baseForm.getRowStart()
									+ baseForm.getLimit() - 1;
							baseForm.setDelareFirstEndPage(1);
							baseForm.setTotal(1);
						} else if (1 == rowStart && totCount <= rowEnd) {// 是首页又是末页
							endInPage = baseForm.getRowStart()
									+ (totCount % baseForm.getLimit() == 0 ? baseForm
											.getLimit()
											: totCount % baseForm.getLimit())
									- 1;
							baseForm.setDelareFirstEndPage(2);
							baseForm.setTotal(2);
						} else if (1 != rowStart && totCount <= rowEnd) {// 是末页不是首页
							endInPage = baseForm.getRowStart()
									+ (totCount % baseForm.getLimit() == 0 ? baseForm
											.getLimit()
											: totCount % baseForm.getLimit())
									- 1;
							baseForm.setDelareFirstEndPage(3);
							baseForm.setTotal(3);
						} else {// 不是首页不是末页
							endInPage = baseForm.getRowStart()
									+ baseForm.getLimit() - 1;
							baseForm.setDelareFirstEndPage(0);
							baseForm.setTotal(0);
						}
						baseForm.setEndInPage(endInPage);
						// baseForm.setTotal(0);
					}
					List<?> resultList = sqlDao_i.getRecordList(queryName, obj);
					java.util.Calendar c1 = java.util.Calendar.getInstance();
					Date d2 = c1.getTime();
					long diff = d2.getTime() - d1.getTime();
					double diffSecond = ((double) diff) / 1000;
					DecimalFormat df = new DecimalFormat("0.###");
					baseForm.setDiffSecond(Double
							.valueOf(df.format(diffSecond)));

					// List<?> resultList = sqlDao_i.getRecordList(queryName,
					// obj, baseForm.getRowStart() - 1, baseForm.getLimit());
					ServiceReturn ret = new ServiceReturn(
							ServiceReturn.SUCCESS, "");
					ret.put(ServiceReturn.FIELD1, resultList);
					BaseForm retPageInfo = new BaseForm(baseForm);
					ret.put(ServiceReturn.FIELD2, retPageInfo);
					return ret;
				} else {
					Integer count = sqlDao_i.getRecord(queryNameForCount, obj);
					baseForm.setRowStart(1);
					baseForm.setRowEnd(count);
					baseForm.setTotal(1);
					// List<?> resultList = sqlDao_i.getRecordList(queryName,
					// obj, 0, count);
					List<?> resultList = sqlDao_i.getRecordList(queryName, obj);
					ServiceReturn ret = new ServiceReturn(
							ServiceReturn.SUCCESS, "");
					ret.put(ServiceReturn.FIELD1, resultList);
					BaseForm retPageInfo = new BaseForm(baseForm);
					ret.put(ServiceReturn.FIELD2, retPageInfo);
					return ret;
				}
			} else {
				throw new AppException(AppErrorcode.PARAMETEROBJECTWRONGTYPE);
			}
		} else {
			// throws exception
			throw new AppException(AppErrorcode.PARAMETEROBJECTISNULL);
		}
	}

	// @Override
	public ServiceReturn add_itransc(Object object, String sqlMapFilename)
			throws Exception {
		sqlDao_i.insertRecord(sqlMapFilename, object);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return sRet;
	}

	// @Override
	public ServiceReturn edit_itransc(Object object, String sqlMapFilename)
			throws Exception {
		sqlDao_i.updateRecord(sqlMapFilename, object);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		return sRet;
	}

	// @Override
	public ServiceReturn delete_itransc(List<Object> objects,
			String sqlMapFilename) throws Exception {
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		for (Object object : objects) {
			sqlDao_i.deleteRecord(sqlMapFilename, object);
		}
		return ret;
	}

	// @Override
	public ServiceReturn batchadd_itransc(List<Object> objects,
			String sqlMapFilename) throws Exception {

		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		;
		for (Object object : objects) {
			try {
				sqlDao_i.insertRecord(sqlMapFilename, object);
			} catch (Exception e) {
				logger.debug("unique constraint violated");

			}
		}
		return ret;
	}

	
}
