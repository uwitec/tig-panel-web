package com.application.webserver;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class TripleDES {

	private static final String Algorithm = "DESede"; //3DES加密

	/**
	 * 加密
	 * @param keybyte 密钥,填充后是24位
	 * @param src 数据,填充后是8的倍数
	 * @return
	 */
	public static byte[] encrypt(byte[] keybyte, byte[] src) {
		byte[] fillkeybyte = fill24bytes(keybyte);
		byte[] filldata = fillMul8bytes(src);
		try {
			SecretKey deskey = new SecretKeySpec(fillkeybyte, Algorithm);
			Cipher c1 = Cipher.getInstance("DESede/ECB/NoPadding");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(filldata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 解密
	 * @param keybyte 密钥,填充后是24位
	 * @param src 数据,填充后是8的倍数
	 * @return
	 */
	public static byte[] decrypt(byte[] keybyte, byte[] src) {
		byte[] fillkeybyte = fill24bytes(keybyte);
		byte[] filldata = fillMul8bytes(src);
		try {
			SecretKey deskey = new SecretKeySpec(fillkeybyte, Algorithm);
			Cipher c1 = Cipher.getInstance("DESede/ECB/NoPadding");
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(filldata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 讲密钥填充到24位
	 * @param byteKey
	 * @return
	 */
	public static byte[] fill24bytes(byte[] byteKey) {
		byte[] keys = null;
		if (byteKey.length == 16) {
			keys = new byte[24];
			System.arraycopy(byteKey, 0, keys, 0, 16);
			System.arraycopy(byteKey, 0, keys, 16, 8);
		} else {
			keys = byteKey;
		}
		return keys;
	}

	/**
	 * 填充加密内容为8的倍数
	 * @param data
	 * @return
	 */
	public static byte[] fillMul8bytes(byte[] data) {
		byte[] finalData = null;
		int length = data.length;
		int k = length % 8;
		if (k != 0) {
			int d = length / 8;
			finalData = new byte[8 * (d + 1)];
			System.arraycopy(data, 0, finalData, 0, length);
			for (int i = length; i < 8 * (d + 1); i++) {
				finalData[i] = (byte) 0x00;
			}
		} else {
			finalData = data;
		}
		return finalData;
	}

	public static void main(String[] args) {

//		String ip = "10.1.254.169chin";
//		byte[] keyBytes = ip.getBytes();
//		//System.out.println(keyBytes.length);
//
//		byte[] finalData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><REQ_401><PHONE>15070843529</PHONE></REQ_401>"
//				.getBytes();// 
//
//		byte[] encode = encrypt(keyBytes, finalData);
//		String encodeStr = StringHelper.bytesToHexString(encode, 1);
//		System.out.println("加密后的16进制字符串:" + encodeStr);
//
//		byte[] decode = decrypt(keyBytes, encode);
//		String decodeStr = StringHelper.bytesToHexString(decode, 1);
//		System.out.println("解密后的16进制字符串:" + decodeStr);
//		String realStr = new String(decode);
//		System.out.println("解密后的字符串:" + realStr);
		
		
//		byte[] b=TripleDES.encrypt("0020120606123456".getBytes(), "4051".getBytes());
//		System.out.println(b.length);
//		String encodeStr = StringHelper.bytesToHexString(b, 1);
//		System.out.println(encodeStr);
////		for(byte t:b){
////			System.out.print(t+"|");
////		}
//		byte[] c=TripleDES.decrypt("0020120606123456".getBytes(), b);
//		System.out.println(c.length);
//		String decodeStr = StringHelper.bytesToHexString(c, 1);
//		System.out.println(decodeStr);
////		for(byte t:c){
////			System.out.print(t+"|");
////		}
//		System.out.println(new String(c));

	}
}