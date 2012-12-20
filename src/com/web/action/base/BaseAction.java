package com.web.action.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import net.sf.json.*;  
import net.sf.json.processors.DefaultValueProcessor; 
import net.sf.json.util.JSONUtils;

import com.application.webserver.ApplicationConstants;
import com.opensymphony.xwork2.ActionSupport;
import com.web.common.ServiceReturn;
import com.web.form.administration.Unit;
import com.web.form.administration.UnitLevelReturn;
import com.web.form.administration.User;
import com.web.form.administration.UnitLevel;
import com.web.form.base.BaseForm;
import com.web.service.base.IPublicService;
import com.web.service.communication.CommunicationErrorcode;
import com.web.service.communication.CommunicationException;
import com.web.service.communication.CommunicationServiceReturn;
import com.web.service.communication.Datagram8583;
import com.web.service.communication.DatagramConfig;
import com.web.service.communication.ICommunicationService;
import com.web.service.communication.MessageSendNode;


public class BaseAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String AJAX_SUCCESS = "AJAX_SUCCESS";
	public static final String SYSTEMUNITTREELIST = "systemunittreelist";
	private String actionresult;
	protected IPublicService publicService;
	protected ICommunicationService communicationService;
	
	public String getActionresult() {
		return actionresult;
	}

	public void setActionresult(String actionresult) {
		this.actionresult = actionresult;
	} 
	
	public void setPublicService(IPublicService publicService) {
		this.publicService = publicService;
	}
	
	public static final JSONObject convertServiceReturnToJson(ServiceReturn ret) 
		throws Exception {
		
		JsonConfig conf = new JsonConfig();
		conf.registerDefaultValueProcessor(
			Integer.class,
		    new DefaultValueProcessor(){
		        public Object getDefaultValue(Class type){
		            return JSONNull.getInstance();
		        }
		    }
		);
		
		conf.registerDefaultValueProcessor(Short.class,
			    new DefaultValueProcessor(){
			        public Object getDefaultValue(Class type){
			            return JSONNull.getInstance();
			        }
			    });
		
		conf.registerDefaultValueProcessor(Double.class,
			    new DefaultValueProcessor(){
			        public Object getDefaultValue(Class type){
			            return JSONNull.getInstance();
			        }
			    });
		
		conf.registerDefaultValueProcessor(Long.class,
			    new DefaultValueProcessor(){
			        public Object getDefaultValue(Class type){
			            return JSONNull.getInstance();
			        }
			    });
		
		JSONObject jsonobj = new JSONObject();
		
		if (ret.getSuccess()) {//返回正确
			jsonobj.accumulate(ServiceReturn.RESULT, ret.getSuccess());
			jsonobj.accumulate(ServiceReturn.MESSAGE, ret.getErrmsg());
			
			Set<Entry<String,Object>> entrySet = ret.getResultMap().entrySet();
			for(Entry<String,Object> entry : entrySet){
				if(Iterable.class.isInstance(entry.getValue())){
					JSONArray array = JSONArray.fromObject(entry.getValue(), conf);
					jsonobj.accumulate(entry.getKey(), array);
				}else if(String.class.isInstance(entry.getValue())){
					jsonobj.accumulate(entry.getKey(), entry.getValue());
				}else{
					JSONObject obj = JSONObject.fromObject(entry.getValue());
					jsonobj.accumulate(entry.getKey(), obj);
				}
			}
		} else {//返回错误
			jsonobj.accumulate(ServiceReturn.RESULT, ret.getSuccess());
			jsonobj.accumulate(ServiceReturn.MESSAGE, ret.getErrmsg());
		}
		return jsonobj;
	}
	
	public static final String getJsonString() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = request.getReader();
			String str = "";
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if(reader != null){
					reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}
	
	protected static final User getLogonUser(boolean isBasic) throws Exception{
		if(!isBasic){
			return (User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		}else{
			User logonUser = (User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
			User user = new User();
			user.setUserid(logonUser.getUserid());
			user.setUnit(new Unit(logonUser.getUnit()));
			user.setUserid(logonUser.getUserid());
			user.setUsername(logonUser.getUsername());
			user.setUsertype(logonUser.getUsertype());
			return user;
		}
	}
	
	@SuppressWarnings("unchecked")
	protected static final UnitLevelReturn getUnitQueryCondition() throws Exception {
		User logonUser = getLogonUser(false);
		List<UnitLevel> level_list = (List<UnitLevel>)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.UNITLEVEL);
		Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
		Unit current_tree_node = unit_tree.findNodeById(logonUser.getUnit().getNodeid());
		
		Integer maxlevel = 0;
		UnitLevelReturn level_return = new UnitLevelReturn();
		for(UnitLevel level : level_list){
			if(level.getLevel() > maxlevel){
				maxlevel = level.getLevel();
			}
			if(level.getLevel() < logonUser.getUnit().getNodelevel()){
				Unit ancestor = current_tree_node.getAncestorAtLevel(level.getLevel());
				level_return.put(ancestor.getNodeid(), ancestor.getNodename());
			}else if(level.getLevel().equals(logonUser.getUnit().getNodelevel())){
				level_return.put(logonUser.getUnit().getNodeid(), logonUser.getUnit().getNodename());
			}else{
			}
		}
		level_return.setTotallevel(maxlevel);
		level_return.setCurrentlevel(logonUser.getUnit().getNodelevel());
		return level_return;
	}
	
	public static final List<Unit> getUnitTreeList() throws Exception{
		User logonUser = getLogonUser(false);
		Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
		Unit current_tree_node = unit_tree.findNodeById(logonUser.getUnit().getNodeid());
		List<Unit> unit_list = current_tree_node.getUnitTreeList(0);
		return unit_list;
	}
	
	public static final List<Unit> getUnitListAtLevel(int level) throws Exception{
		User logonUser = getLogonUser(false);
		Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
		Unit current_tree_node = unit_tree.findNodeById(logonUser.getUnit().getNodeid());
		List<Unit> unit_list = current_tree_node.getDescendantAtLevel(level);
		List<Unit> out_list = new ArrayList<Unit>();
		if(unit_list == null){
			return out_list;
		}
		for(Unit unit : unit_list){
			Unit temp = new Unit(unit);
			out_list.add(temp);
		}
		return out_list;
	}
	
	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD2, getLogonUser(true));
		JSONObject retObj = convertServiceReturnToJson(sRet);
		//System.out.println(retObj.toString());
		ServletActionContext.getRequest().setAttribute("actionresult", retObj.toString());
		return SUCCESS;
	}

	public String query() throws Exception{
		String jsonString = getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		BaseForm baseForm = (BaseForm)JSONObject.toBean(jsonObj, BaseForm.class);
		Class classForm = Class.forName(baseForm.getClassname());
		Object object = (Object)JSONObject.toBean(jsonObj, classForm);
		ServiceReturn sRet = publicService.queryPage(object, baseForm.getSqlMapNamespace()+".select", baseForm.getSqlMapNamespace()+".selectCount");
		JSONObject retObj = convertServiceReturnToJson(sRet);
		this.setActionresult(retObj.toString());
		return AJAX_SUCCESS;
	}

	
	public String add() throws Exception{
		String inputJsonStr = getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(inputJsonStr);
		BaseForm baseForm = (BaseForm)JSONObject.toBean(jsonObj, BaseForm.class);
		Class classForm = Class.forName(baseForm.getClassname());
		Object object = (BaseForm)JSONObject.toBean(jsonObj, classForm);
		ServiceReturn sRet = publicService.add_itransc(object, baseForm.getSqlMapNamespace()+".insert");
		JSONObject returnJson = convertServiceReturnToJson(sRet);
		setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	public String edit() throws Exception{
		String inputJsonStr = getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(inputJsonStr);
		BaseForm baseForm = (BaseForm)JSONObject.toBean(jsonObj, BaseForm.class);
		Class classForm = Class.forName(baseForm.getClassname());
		Object object = (BaseForm)JSONObject.toBean(jsonObj, classForm);
		ServiceReturn sRet = publicService.edit_itransc(object, baseForm.getSqlMapNamespace()+".update");
		JSONObject returnJson = convertServiceReturnToJson(sRet);
		setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String delete() throws Exception{
		String inputJsonStr = getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(BaseForm.class);
		List<Object> objectForms = (List<Object>) JSONArray.toCollection(jsonArray,config);
		BaseForm baseForm = (BaseForm)objectForms.get(objectForms.size()-1);
		config.setRootClass(Class.forName(baseForm.getClassname()));
		List<Object> objects = (List<Object>) JSONArray.toCollection(jsonArray,config);
		objects.remove(objectForms.size()-1);
		
		ServiceReturn ret = publicService.delete_itransc(objects,  baseForm.getSqlMapNamespace()+".delete");
		setActionresult(convertServiceReturnToJson(ret).toString());
		return AJAX_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String batchadd() throws Exception{
		String inputJsonStr = getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(BaseForm.class);
		List<Object> objectForms = (List<Object>) JSONArray.toCollection(jsonArray,config);
		BaseForm baseForm = (BaseForm)objectForms.get(objectForms.size()-1);
		config.setRootClass(Class.forName(baseForm.getClassname()));
		List<Object> objects = (List<Object>) JSONArray.toCollection(jsonArray,config);
		objects.remove(objectForms.size()-1);
		
		ServiceReturn ret = publicService.batchadd_itransc(objects,  baseForm.getSqlMapNamespace()+".insert");
		setActionresult(convertServiceReturnToJson(ret).toString());
		return AJAX_SUCCESS;
	}
	
	public String getRegSuq(boolean isJustNum) {
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
	
	public String getNowTime(){
		java.util.Calendar c = java.util.Calendar.getInstance();
		Date d1 = c.getTime();
		String todaystr = new SimpleDateFormat("yyyyMMddHHmmss").format(d1);
		return todaystr;
	}
	
	public static String leftZero(String s, Integer size) {//左补0
		String returns = s;
		int s_len = 0;
		if(s ==null )
		{
			returns = "";
		}else
		{
			s_len = s.length();
		}
		for (int i = 0; i < size - s_len; i++) {
			returns = "0" + returns;
		}
		return returns;
	}
	
	public byte[] communicateWithServer_offline(byte[] msg_tosend, MessageSendNode node) throws Exception {
		//取得Socket连接
		InetAddress netAddress = null;
		try {
			netAddress = InetAddress.getByName(node.getNodeIp());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			throw new CommunicationException(CommunicationErrorcode.HOSTIPERROR);//地址解析错误
		}
		
		InetSocketAddress netSocketAddress = new InetSocketAddress(netAddress,node.getNodePort());
		
		Socket socket = null;
		try{
			socket = new Socket();
			socket.connect(netSocketAddress, 5000);
		}catch(IOException ex){
			throw new CommunicationException(CommunicationErrorcode.SOCKETCONNFAILED);//通讯连接失败
		}
		
		try {
			socket.setSoTimeout(5000);
		} catch (SocketException ex) {
			// TODO Auto-generated catch block
			throw new CommunicationException(CommunicationErrorcode.PROTOCOLERROR);//通讯协议错误
		}
		
		//将报文发送给服务器
		BufferedOutputStream bufOutputStream = null;
		try {
			bufOutputStream = new BufferedOutputStream(socket.getOutputStream(),1024);
			bufOutputStream.write(msg_tosend, 0, msg_tosend.length);
			bufOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			socket.close();
			throw new CommunicationException(CommunicationErrorcode.SOCKETSENDERROR, msg_tosend);
		}
		
		
		//准备接收服务器的返回报文
		BufferedInputStream bufInputStream = null;
		try {
			bufInputStream = new BufferedInputStream(socket.getInputStream(),1024);
			//接收报文的前4个字节
			byte[] head_package = new byte[4];
			int bytesread = -1;
			while((bytesread = bufInputStream.read(head_package, 0, 4)) == -1)
			{
			}
			if(bytesread != 4)
			{
				bufInputStream.close();
				bufOutputStream.close();
				socket.close();
				throw new CommunicationException(CommunicationErrorcode.SOCKETRECVERROR, msg_tosend);
			}
			
			StringBuffer head_package_str_buf = new StringBuffer();
			for(byte b : head_package){
				head_package_str_buf.append((char)b);
			}
			
			//接收报文余下的字节
			int body_length = Integer.valueOf(head_package_str_buf.toString());
			byte[] body_package = new byte[body_length];
			bytesread = -1;
			while((bytesread = bufInputStream.read(body_package, 0, body_length)) == -1)
			{
			}
			if(bytesread != body_length){
				//如果读取的报文长度有误，则抛出异常
				bufInputStream.close();
				bufOutputStream.close();
				socket.close();
				throw new CommunicationException(CommunicationErrorcode.SOCKETRECVERROR, msg_tosend);
			}
				
			//将应答报文的两个部分合并后返回
			byte[] retBytes = new byte[head_package.length + body_package.length];
			for(int i=0;i<head_package.length;i++){
				retBytes[i] = head_package[i];
			}
			for(int i=0;i<body_package.length;i++){
				retBytes[i+head_package.length] = body_package[i];
			}
			
			bufInputStream.close();
			bufOutputStream.close();
			socket.close();
			return retBytes;
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			if(java.net.SocketTimeoutException.class.isInstance(ex)){
				throw new CommunicationException(CommunicationErrorcode.SOCKETNORESPONSE, msg_tosend);//后台无应答
			}else{
				throw ex;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	protected static final DatagramConfig getMessageConfig(String messagename) throws Exception{
		List<DatagramConfig> configs = (List<DatagramConfig>)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.DATAGRAMCONFIG);
		for(DatagramConfig config : configs){
			if(config.getName().equals(messagename)){
				return new DatagramConfig(config);
			}
		}
		return null;
	}
	

	public ICommunicationService getCommunicationService() {
		return communicationService;
	}

	public void setCommunicationService(ICommunicationService communicationService) {
		this.communicationService = communicationService;
	}

	public IPublicService getPublicService() {
		return publicService;
	}
}
