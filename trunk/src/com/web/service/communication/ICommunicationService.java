/**
 * 
 */
package com.web.service.communication;
/**
 * @author David
 *
 */
public interface ICommunicationService {
	public static final Integer REDO = 1;
	public static final Integer REVERSE = 2;
	public static final Integer TERMINATE = 3;
	
	public static final String   OFFLINE_COMM_CORRECT = "00";
	public static final String   OFFLINE_DATAGRAM_INCORRECTTYPE = "4008";
	public static final String   OFFLINE_DATAGRAM_CORRECT = "00";
	public static final String   OFFLINE_TRANSAC_CORRECT = "00000";
	
	//联机账户报文
	public CommunicationServiceReturn sendDatagramAndRecieveResponse_onlineaccount(Datagram8583 datagram, MessageSendNode node, DatagramConfig config_sent, DatagramConfig config_received) throws Exception;
	public CommunicationServiceReturn sendDatagramAndRecieveResponse_onlineaccount(String datagram, MessageSendNode node, DatagramConfig config_sent, DatagramConfig config_received) throws Exception;
	public CommunicationServiceReturn processDatagramAfterException_onlineaccount(Exception ex);
	
	//脱机账户报文
	public CommunicationServiceReturn sendDatagramAndRecieveResponse_offlineaccount(DatagramOffline datagram, MessageSendNode node, DatagramConfig config_sent, DatagramConfig config_received) throws Exception;
	public CommunicationServiceReturn sendDatagramAndRecieveResponse_offlineaccount(String datagram, MessageSendNode node, DatagramConfig config_sent, DatagramConfig config_received) throws Exception;
	public CommunicationServiceReturn processDatagramAfterException_offlineaccount(Exception ex);
	
	public Integer getDatagramReverseTimes();
	
	//发送报文方法
	public byte[] communicateWithServer_offline(byte[] msg_tosend, MessageSendNode node) throws Exception;
}
