package com.web.action.report.htreport.util;

import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

/**
* <p>Title: RSA非对称型加密的公钥和私钥</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2003</p>
* <p>Company: </p>
* @author not attributable
* @version 1.0
*/

public class KeyRSA {
	
	private KeyPairGenerator kpg = null;
	private KeyPair kp = null;
	private PublicKey publicKey = null;
	private PrivateKey privateKey = null;

	/**
	  * 构造函数
	  * @param in 指定密匙长度（取值范围：512～2048）
	  * @throws NoSuchAlgorithmException 异常
	  */
	public KeyRSA(int in) throws NoSuchAlgorithmException,FileNotFoundException, IOException {
        kpg = KeyPairGenerator.getInstance("RSA"); //创建‘密匙对’生成器
        kpg.initialize(in); //指定密匙长度（取值范围：512～2048）
        kp = kpg.genKeyPair(); //生成‘密匙对’，其中包含着一个公匙和一个私匙的信息
        publicKey = kp.getPublic(); //获得公匙
        privateKey = kp.getPrivate(); //获得私匙
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void createPublicKeyFile(String fileName){
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream(fileName);
			oos= new ObjectOutputStream(fos);
			oos.writeObject(this.publicKey);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void createPrivateKeyFile(String fileName){
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream(fileName);
			oos= new ObjectOutputStream(fos);
			oos.writeObject(this.privateKey);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		try{
		      System.out.println("私匙和公匙保存到C盘下的文件中.");
		      KeyRSA keyRSA= new KeyRSA(512);
		      System.out.println(keyRSA.getPublicKey().toString());
		      System.out.println(keyRSA.getPrivateKey().toString());
		  }catch(IOException ex){
			  
		  }catch(NoSuchAlgorithmException ex){
			  
		  }
	}

}