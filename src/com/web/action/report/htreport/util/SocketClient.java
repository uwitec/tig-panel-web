package com.web.action.report.htreport.util;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;




public class SocketClient{
	public SocketClient(){
		
	}
	
	public byte[] sendRequest(String serverIp,int serverPort,byte[] buffer,int responseLen){
		return sendRequest(serverIp,serverPort,buffer,450000,responseLen);
	}

	public byte[] sendRequest(String serverIp,int serverPort,byte[] buffer,int timeout,int responseLen){
		if(serverIp==null || serverPort<=0 || buffer==null || buffer.length>9999) return null;
		
		if(timeout<=450000) timeout=450000;
		SocketChannel sc=null;
		try{
			sc=SocketChannel.open();
			sc.configureBlocking(true);
			sc.socket().setSoTimeout(timeout);
			System.out.println("serverIp:" + serverIp);
			System.out.println("serverPort:" + serverPort);
			sc.connect(new InetSocketAddress(serverIp,serverPort));
			sc.finishConnect();
			
			sc.write(ByteBuffer.wrap(buffer));
			ByteBuffer bfBody=ByteBuffer.allocate(responseLen);
			
			int length=sc.read(bfBody);
			System.out.println(length);
			byte[] bt=bfBody.array();
			byte[] bb=new byte[length];
			for(int i=0;i<length;i++){
				bb[i]=bt[i];
			}
			sc.close();	
			return bb;
		}catch(Exception e){
			System.err.println("error e :"+e.getMessage());
			System.out.println("sc..." + sc.toString());
			try { if(sc!=null && sc.isOpen()) sc.close(); } catch(Exception ex) {
				System.err.println("error ex :"+ex.getMessage());
			}
			System.out.println("sc2..." + sc.toString());
			return null;
		}
	}

	
	public byte[] sendRequest1(String serverIp,int serverPort,byte[] buffer,int timeout){
	    if(timeout<=450000) timeout=450000;
	    SocketChannel sc=null;
		try{
            sc=SocketChannel.open();
			sc.configureBlocking(true);
			sc.socket().setSoTimeout(timeout);
			sc.connect(new InetSocketAddress(serverIp,serverPort));
			sc.finishConnect();

			sc.write(ByteBuffer.wrap(buffer));

			ByteBuffer bfBody=ByteBuffer.allocate(buffer.length);
			sc.close();

			return bfBody.array();
		}catch(Exception e){
			System.err.println("error e :"+e.getMessage());
			System.out.println("sc..." + sc.toString());
			try{ if(sc!=null && sc.isOpen()) sc.close(); } catch(Exception ex) {
				System.err.println("error ex :"+ex.getMessage());
			}
			System.out.println("sc2..." + sc.toString());
			return null;
		}
	}

	public static void main(String[] args){
		byte[] buffer="FhBlackFile#20080620".getBytes();
		SocketClient c1=new SocketClient();
		byte[] rb=c1.sendRequest("192.168.1.178", 5555, buffer,buffer.length);
		System.out.print(new String(rb));
	}
}
