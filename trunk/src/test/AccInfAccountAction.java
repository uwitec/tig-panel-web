package test;


import net.sf.json.JSONObject;
import com.web.action.base.BaseAction;
import com.web.common.ServiceReturn;
import com.web.form.cchs.baseinfo.spmng.TAccInfAccount;



@SuppressWarnings("serial")
public class AccInfAccountAction extends BaseAction{


	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub	
		
		String inputJsonStr = getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(inputJsonStr);
		TAccInfAccount tAccInfAccount = (TAccInfAccount)JSONObject.toBean(jsonObj, TAccInfAccount.class);
		
		TAccInfAccount t_acc_inf_account = new TAccInfAccount();
		t_acc_inf_account.setMaxOutamt(tAccInfAccount.getMaxOutamt()); 
		t_acc_inf_account.setMaxDayOutamt(tAccInfAccount.getMaxDayOutamt());
		
		ServiceReturn sRet = publicService.edit_itransc(t_acc_inf_account,"t_acc_inf_account.update");
		JSONObject returnJson = convertServiceReturnToJson(sRet);
		setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
		
	
    
}
