/**
 * 
 */
package com.web.service.communication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author David
 *
 */
public class CommunicationService implements
		ICommunicationService {
	private final static Logger logger = Logger.getLogger(CommunicationService.class);
	
	private String datagramEncoding;
	private Integer onlineCommunicationTimeout;
	private Integer offlineCommunicationTimeout;
	private Integer reverseTimes;
	public void setDatagramEncoding(String datagramEncoding) {
		this.datagramEncoding = datagramEncoding;
	}
	public void setOnlineCommunicationTimeout(Integer onlineCommunicationTimeout) {
		this.onlineCommunicationTimeout = onlineCommunicationTimeout;
	}
	public void setOfflineCommunicationTimeout(Integer offlineCommunicationTimeout) {
		this.offlineCommunicationTimeout = offlineCommunicationTimeout;
	}
	public void setReverseTimes(Integer reverseTimes) {
		this.reverseTimes = reverseTimes;
	}
	
	@Override
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
		BufferedOutputStream bufOutputStream = null;
		BufferedInputStream bufInputStream = null;
		try {
			try{
				socket = new Socket();
				socket.connect(netSocketAddress, this.offlineCommunicationTimeout);
			}catch(IOException ex){
				throw new CommunicationException(CommunicationErrorcode.SOCKETCONNFAILED);//通讯连接失败
			}
			
			try {
				socket.setSoTimeout(this.offlineCommunicationTimeout);
			} catch (SocketException ex) {
				// TODO Auto-generated catch block
				throw new CommunicationException(CommunicationErrorcode.PROTOCOLERROR);//通讯协议错误
			}
			
			//将报文发送给服务器
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			if(bufInputStream != null){
				try {
					bufInputStream.close();
				} catch (Exception e) {
					throw new CommunicationException(CommunicationErrorcode.CLOSEINPUTBUFERROR, msg_tosend);
				}
			}
			if(bufOutputStream != null){
				try {
					bufOutputStream.close();
				} catch (Exception e) {
					throw new CommunicationException(CommunicationErrorcode.CLOSEOUTPUTBUFERROR, msg_tosend);
				}
			}
			if(socket != null){
				try {
					socket.close();
				} catch (Exception e) {
					throw new CommunicationException(CommunicationErrorcode.CLOSESOCKETERROR, msg_tosend);
				}
			}
		}
	}
	
	private byte[] communicateWithServer_online(byte[] msg_tosend, MessageSendNode node) throws Exception {
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
			socket.connect(netSocketAddress, this.onlineCommunicationTimeout);
		}catch(IOException ex){
			throw new CommunicationException(CommunicationErrorcode.SOCKETCONNFAILED);//通讯连接失败
		}
		
		try {
			socket.setSoTimeout(this.onlineCommunicationTimeout);
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
			throw new CommunicationException(CommunicationErrorcode.SOCKETSENDERROR);
		}
		
		
		//准备接收服务器的返回报文
		BufferedInputStream bufInputStream = null;
		try {
			bufInputStream = new BufferedInputStream(socket.getInputStream(),1024);
			//接收报文的前2个字节
			byte[] head_package = new byte[2];
			int bytesread = -1;
			while((bytesread = bufInputStream.read(head_package, 0, 2)) == -1)
			{
			}
			if(bytesread != 2)
			{
				bufInputStream.close();
				bufOutputStream.close();
				socket.close();
				throw new CommunicationException(CommunicationErrorcode.SOCKETRECVERROR);
			}
		
			String hex_str = EncodingConverter.hexStringFromBytes(head_package);
			//接收报文余下的字节
			int body_length = Integer.decode("0x" + hex_str);
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
				throw new CommunicationException(CommunicationErrorcode.SOCKETRECVERROR);
			}
				
			bufInputStream.close();
			bufOutputStream.close();
			socket.close();
			return body_package;
			//return retBytes;
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			if(java.net.SocketTimeoutException.class.isInstance(ex)){
				throw new CommunicationException(CommunicationErrorcode.SOCKETNORESPONSE);//后台无应答
			}else{
				throw ex;
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private byte[] encodeDatagram_online(Object datagramObj, DatagramConfig config) throws Exception{
		if(config != null){
			List<Byte> result = new ArrayList<Byte>();
			Class datagram_class = Class.forName(config.getClassname());
			if(datagram_class.isInstance(datagramObj)){
				List<DatagramField> fieldList = config.getFields();
				for(DatagramField df : fieldList){
					Field class_field = datagram_class.getDeclaredField(df.getProperty());
					Method class_method = datagram_class.getDeclaredMethod("get" + class_field.getName().substring(0, 1).toUpperCase() + class_field.getName().substring(1));
					String value = (String)class_method.invoke(datagramObj);
					if(value == null)
						value = df.getDefaultvalue();
					
					//调用converter进行转换
					List<Byte> temp_bytes = FieldConverter.convert(df, value, this.datagramEncoding);
					logger.debug(class_field.getName() + ": " + EncodingConverter.hexStringFromBytes(temp_bytes));
					result.addAll(temp_bytes);
				}
			}else{
				//报文对象类型不匹配
				throw new CommunicationException(CommunicationErrorcode.DATAGRAMCLASSNOTMATCH);
			}
			byte[] ret = new byte[result.size() + 2];
			List<Byte> bytes_length = EncodingConverter.byteListForHex(result.size(), 2, EncodingConverter.LEADING, (byte)0);
			int i=0;
			for(byte b : bytes_length){
				ret[i] = b;
				i++;
			}
			i=2;
			for(byte b : result){
				ret[i] = b;
				i++;
			}
			return ret;
		}else{
			//配置对象为空异常
			throw new CommunicationException(CommunicationErrorcode.CONFIGINCORRECT);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Object decodeDatagram_online(byte[] retDatagram, DatagramConfig config) throws Exception{
		if(config != null){
			Class datagram_class = Class.forName(config.getClassname());
			Object datagramObj = datagram_class.newInstance();
			List<DatagramField> fieldList = config.getFields();
			String txntype = config.getName();
			logger.debug(EncodingConverter.hexStringFromBytes(retDatagram));
			String bitmap = "";
			//先解析头，到bitmap为止
			int byte_index = 0;
			for(DatagramField df : fieldList){
				Field class_field = datagram_class.getDeclaredField(df.getProperty());
				Method class_method = datagram_class.getDeclaredMethod("set" + class_field.getName().substring(0, 1).toUpperCase() + class_field.getName().substring(1), java.lang.String.class);
				
				ParseResult parseResult = FieldConverter.convertFromBytes(df, retDatagram, byte_index, this.datagramEncoding);
				logger.debug(class_field.getName() + ": " + parseResult.result);
				byte_index = parseResult.next_position;
				if(class_field.getName().equals("txntype")){
					if(!(Integer.parseInt(parseResult.result) == Integer.parseInt(txntype))){
						//throw 报文交易类型不匹配
						throw new CommunicationException(CommunicationErrorcode.DATAGRAMTYPEERROR);
					}
					class_method.invoke(datagramObj, parseResult.result);
				}else if(class_field.getName().equals("bitmap")){
					class_method.invoke(datagramObj, bitmap = parseResult.result);
					break;
				}else{
					class_method.invoke(datagramObj, parseResult.result);
				}
			}
			int bitmap_length = bitmap.length();
			for(int i=1;i<=bitmap_length;i++){
				if(bitmap.charAt(i-1) == '1'){
					String fieldname = "field" + String.valueOf(i);
					for(DatagramField df : fieldList){
						if(df.getProperty().equals(fieldname)){
							Field class_field = datagram_class.getDeclaredField(df.getProperty());
							Method class_method = datagram_class.getDeclaredMethod("set" + class_field.getName().substring(0, 1).toUpperCase() + class_field.getName().substring(1), java.lang.String.class);
							ParseResult parseResult = FieldConverter.convertFromBytes(df, retDatagram, byte_index, this.datagramEncoding);
							logger.debug(class_field.getName() + ": " + parseResult.result);
							byte_index = parseResult.next_position;
							class_method.invoke(datagramObj, parseResult.result);
							break;
						}
					}
				}
			}
			if(byte_index != retDatagram.length){
				throw new CommunicationException(CommunicationErrorcode.DATAGRAMLENGTHERROR);
			}
			return datagramObj;
		}else{
			//配置对象为空异常
			throw new CommunicationException(CommunicationErrorcode.CONFIGINCORRECT);
		}
	}
	
	@SuppressWarnings("unchecked")
	private byte[] encodeDatagram_offline(Object datagramObj, DatagramConfig config) throws Exception{
		if(config != null){
			List<Byte> result = new ArrayList<Byte>();
			Class datagram_class = Class.forName(config.getClassname());
			if(datagram_class.isInstance(datagramObj)){
				List<DatagramField> fieldList = config.getFields();
				for(DatagramField df : fieldList){
					Field class_field = datagram_class.getDeclaredField(df.getProperty());
					Method class_method = datagram_class.getDeclaredMethod("get" + class_field.getName().substring(0, 1).toUpperCase() + class_field.getName().substring(1));
					String value = (String)class_method.invoke(datagramObj);
					if(value == null)
						value = df.getDefaultvalue();
					
					//调用converter进行转换
					result.addAll(FieldConverter.convert(df, value, this.datagramEncoding));
				}
			}else{
				//报文对象类型不匹配
				throw new CommunicationException(CommunicationErrorcode.DATAGRAMCLASSNOTMATCH);
			}
			byte[] ret = new byte[result.size()];
			int i=0;
			for(byte b : result){
				ret[i] = b;
				i++;
			}
			return ret;
		}else{
			//配置对象为空异常
			throw new CommunicationException(CommunicationErrorcode.CONFIGINCORRECT);
		}
	}
	
	@SuppressWarnings("unchecked")
	private Object decodeDatagram_offline(byte[] retDatagram, DatagramConfig config) throws Exception{
		if(config != null){
			Class datagram_class = Class.forName(config.getClassname());
			Object datagramObj = datagram_class.newInstance();
			List<DatagramField> fieldList = config.getFields();
			
			int byte_index = 0;
			for(DatagramField df : fieldList){
				Field class_field = datagram_class.getDeclaredField(df.getProperty());
				Method class_method = datagram_class.getDeclaredMethod("set" + class_field.getName().substring(0, 1).toUpperCase() + class_field.getName().substring(1), java.lang.String.class);
				
				ParseResult parseResult = FieldConverter.convertFromBytes(df, retDatagram, byte_index, this.datagramEncoding);
				byte_index = parseResult.next_position;
				class_method.invoke(datagramObj, parseResult.result);
			}
			return datagramObj;
		}else{
			//配置对象为空异常
			throw new CommunicationException(CommunicationErrorcode.CONFIGINCORRECT);
		}
	}
	

	@Override
	public CommunicationServiceReturn processDatagramAfterException_offlineaccount(
			Exception ex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommunicationServiceReturn processDatagramAfterException_onlineaccount(
			Exception ex) {
		// TODO Auto-generated method stub
		CommunicationServiceReturn sRet = new CommunicationServiceReturn(false,"");
		Integer result = 0;
		if(IOException.class.isInstance(ex)){//说明通讯的io有问题
			result = TERMINATE;
		}else if(CommunicationException.class.isInstance(ex)){
			if(((CommunicationException)ex).getErrorMessage().equals(CommunicationErrorcode.SOCKETCONNFAILED)){//通讯连接失败
				result = REDO;
			}else if(((CommunicationException)ex).getErrorMessage().equals(CommunicationErrorcode.SOCKETNORESPONSE)){//系统后台无应答
				result = REVERSE;
			}else if(((CommunicationException)ex).getErrorMessage().equals(CommunicationErrorcode.SOCKETRECVERROR)){//数据接收失败
				result = REVERSE;
			}else if(((CommunicationException)ex).getErrorMessage().equals(CommunicationErrorcode.DATAGRAMTYPEERROR)){//报文类型不正确
				result = TERMINATE;
			}else if(((CommunicationException)ex).getErrorMessage().equals(CommunicationErrorcode.RESPCODEERROR)){//应答码错误
				result = TERMINATE;
			}else if(((CommunicationException)ex).getErrorMessage().equals(CommunicationErrorcode.HOSTIPERROR)){//通讯地址解析失败
				result = TERMINATE;
			}else if(((CommunicationException)ex).getErrorMessage().equals(CommunicationErrorcode.PROTOCOLERROR)){//通讯协议出错
				result = TERMINATE;
			}else if(((CommunicationException)ex).getErrorMessage().equals(CommunicationErrorcode.SOCKETSENDERROR)){//数据发送失败
				result = REDO;
			}else{
				result = TERMINATE;
			}
		}else{
			result = TERMINATE;
		}
		
		sRet.put(CommunicationServiceReturn.FIELD1, result);
		sRet.put(CommunicationServiceReturn.FIELD2, ex);
		return sRet;
	}

	@Override
	public CommunicationServiceReturn sendDatagramAndRecieveResponse_offlineaccount(
			DatagramOffline datagram, MessageSendNode node, DatagramConfig config_sent, DatagramConfig config_received) throws Exception {
		// TODO Auto-generated method stub
		byte[] bytessent = this.encodeDatagram_offline(datagram, config_sent);
		byte[] bytesReceived = this.communicateWithServer_offline(bytessent,node);
		DatagramOffline retDatagram = (DatagramOffline)decodeDatagram_offline(bytesReceived, config_received);
		
		if(!retDatagram.getDatagrammsgtype().equals(OFFLINE_DATAGRAM_INCORRECTTYPE)){
			throw new CommunicationException("exception.offlinerespcodedatagram.4008");
		}
		
		if(!retDatagram.getRespcodecomm().equals(OFFLINE_COMM_CORRECT)){
			throw new CommunicationException("exception.offlinerespcodecommunication."
					+ retDatagram.getRespcodecomm());
		}
		
		CommunicationServiceReturn sRet = new CommunicationServiceReturn(true,"");
		sRet.put(CommunicationServiceReturn.FIELD1, retDatagram);
		return sRet;
	}

	@Override
	public CommunicationServiceReturn sendDatagramAndRecieveResponse_offlineaccount(
			String datagram, MessageSendNode node, DatagramConfig config_sent, DatagramConfig config_received) throws Exception {
		// TODO Auto-generated method stub
		byte[] bytesSent = datagram.getBytes(Charset.forName(this.datagramEncoding));
		byte[] bytesReceived = this.communicateWithServer_offline(bytesSent,node);
		DatagramOffline retDatagram = (DatagramOffline)decodeDatagram_offline(bytesReceived, config_received);
		
		if(!retDatagram.getDatagrammsgtype().equals(OFFLINE_DATAGRAM_INCORRECTTYPE)){
			throw new CommunicationException("exception.offlinerespcodedatagram.4008");
		}
		
		if(!retDatagram.getRespcodecomm().equals(OFFLINE_COMM_CORRECT)){
			throw new CommunicationException("exception.offlinerespcodecommunication."
					+ retDatagram.getRespcodecomm());
		}
		
		CommunicationServiceReturn sRet = new CommunicationServiceReturn(true,"");
		sRet.put(CommunicationServiceReturn.FIELD1, retDatagram);
		return sRet;
	}

	@Override
	public CommunicationServiceReturn sendDatagramAndRecieveResponse_onlineaccount(
			Datagram8583 datagram, MessageSendNode node, DatagramConfig config_sent, DatagramConfig config_received) throws Exception {
		// TODO Auto-generated method stub
		byte[] bytesSent = this.encodeDatagram_online(datagram, config_sent);
		//System.out.println(EncodingConverter.hexStringFromBytes(bytesSent));
		byte[] bytesReceived = this.communicateWithServer_online(bytesSent,node);
		Datagram8583 retDatagram = (Datagram8583)decodeDatagram_online(bytesReceived, config_received);
		
		CommunicationServiceReturn sRet = new CommunicationServiceReturn(true,"");
		sRet.put(CommunicationServiceReturn.FIELD1, retDatagram);
		return sRet;
	}

	@Override
	public CommunicationServiceReturn sendDatagramAndRecieveResponse_onlineaccount(
			String datagram, MessageSendNode node, DatagramConfig config_sent, DatagramConfig config_received) throws Exception {
		// TODO Auto-generated method stub
		List<Byte> byteList = EncodingConverter.byteListForHexBCD(datagram, datagram.length()/2, EncodingConverter.TRAILING, '0');
		byte[] bytesSent = new byte[byteList.size()];
		int i=0;
		for(byte b : byteList){
			bytesSent[i] = b;
			i++;
		}
		byte[] bytesReceived = this.communicateWithServer_online(bytesSent,node);
		Datagram8583 retDatagram = (Datagram8583)decodeDatagram_online(bytesReceived, config_received);
		
		CommunicationServiceReturn sRet = new CommunicationServiceReturn(true,"");
		sRet.put(CommunicationServiceReturn.FIELD1, retDatagram);
		return sRet;
	}
	
	public Integer getDatagramReverseTimes(){
		return this.reverseTimes;
	}
	
	/*public static void main(String[] args){
		Datagram8583 dg8583 = new Datagram8583();
		DatagramConfig config = new DatagramConfig();
		config.setName("0810");config.setClassname("com.web.service.communication.Datagram8583");
		List<DatagramField> fieldList = new java.util.ArrayList<DatagramField>();
		DatagramField field = new DatagramField();
		field.setProperty("tpdu");field.setType(DatagramField.Enum_type.N);field.setLength("10");field.setFormat(DatagramField.Enum_format.BCD);field.setDefaultvalue("6000000000");
		fieldList.add(field);
		field = new DatagramField();
		field.setProperty("header");field.setType(DatagramField.Enum_type.N);field.setLength("12");field.setFormat(DatagramField.Enum_format.BCD);field.setDefaultvalue("612200000000");
		fieldList.add(field);
		field = new DatagramField();
		field.setProperty("txntype");field.setType(DatagramField.Enum_type.N);field.setLength("4");field.setFormat(DatagramField.Enum_format.BCD);field.setDefaultvalue("0810");
		fieldList.add(field);
		field = new DatagramField();
		field.setProperty("bitmap");field.setType(DatagramField.Enum_type.B);field.setLength("64");field.setFormat(DatagramField.Enum_format.BINARY);field.setDefaultvalue("1100000000000000000000000000000000000000000000000000000000000000");
		fieldList.add(field);
		field = new DatagramField();
		field.setProperty("field1");field.setType(DatagramField.Enum_type.CHINESELLVAR);field.setLength("10");field.setFormat(DatagramField.Enum_format.ASCII);
		fieldList.add(field);
		field = new DatagramField();
		field.setProperty("field2");field.setType(DatagramField.Enum_type.N);field.setLength("5");field.setFormat(DatagramField.Enum_format.BCD);
		fieldList.add(field);
		config.setFields(fieldList);
		
		dg8583.setField1("圣达菲sdfsadfjiwe按时间覅我");
		dg8583.setField2("12345");
		dg8583.setField13("0910");
		dg8583.setField32("234125223");
		dg8583.setField37("sdfs1231fw");
		dg8583.setField39("00");
		dg8583.setField41("2137123412");
		dg8583.setField42("sjiw92837412");
		dg8583.setField60("000000990001");
		dg8583.setField63("777");
		
		//DatagramOffline offlinedg = new DatagramOffline();
		try {
			CommunicationService service = new CommunicationService();
			service.setDatagramEncoding("gbk");
			byte[] bytesent = service.encodeDatagram_offline(dg8583, config);
			System.out.println(EncodingConverter.hexStringFromBytes(bytesent));
			Datagram8583 retdg = (Datagram8583)service.decodeDatagram_offline(bytesent, config);
			int a = 0;
			int b = a;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
