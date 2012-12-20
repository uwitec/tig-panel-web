package com.application.webserver;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * 系统配置文件(config.properties)
 * 
 * @author admin
 * 
 */
public final class Configure {
	private static final String BUNDLE = "quartzconfig.properties";

	private static Properties properties = null;

	/**
	 * 初试化系统设置
	 */
	public static void init() {
		InputStream is = null;

		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(BUNDLE);
			properties = new Properties();
			properties.load(is);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static {
		init();
	}

	public static void putConfig(String key, String value) {
		Properties props=System.getProperties(); //获得系统属性集    
		String osName = props.getProperty("os.name"); //操作系统名称  
		
		if(osName.indexOf("Windows")<0){
			key="lunix."+key;
		}
		properties.setProperty(key, value);
	}

	/**
	 * 取得设置
	 * 
	 * @param key
	 *            设置项的关键字
	 * @return 设置项的值
	 */
	public static String getConfig(String key) {
		try {
			Properties props=System.getProperties(); //获得系统属性集    
			String osName = props.getProperty("os.name"); //操作系统名称  
			
			if(osName.indexOf("Windows")<0){
				key="lunix."+key;
			}
			return new String(properties.getProperty(key).getBytes("utf-8"), "utf-8");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 取得设置
	 * 
	 * @param key
	 *            设置项的关键字
	 * @return 设置项的值
	 */
	public static String getConfig(String key, String def) {
		String val = getConfig(key);
		if (val == null) {
			return def;
		} else {
			return val;
		}
	}

	/**
	 * 取得设置
	 * 
	 * @param key
	 *            设置项的关键字
	 * @return 设置项的值
	 */
	public static int getConfigInt(String key, int def) {
		String val = getConfig(key);
		if (val == null) {
			return def;
		}

		val = val.trim();

		int ret = val.matches("\\d+") ? Integer.parseInt(val) : def;

		return ret;
	}

	/**
	 * 取得设置
	 * 
	 * @param key
	 *            设置项的关键字
	 * @return 设置项的值
	 */
	public static boolean getConfigBoolean(String key, boolean def) {
		String val = getConfig(key);
		if (val == null) {
			return def;
		}

		val = val.trim();

		return val.toLowerCase().equals("true");
	}
	
//	public static String getAttachmentItemId(String rk_file)
//	{
//		ServiceMediator serviceMediator = ServiceMediator.getInstance();
//		FindAttachmentItemRequest findAttachmentItemRequest = new FindAttachmentItemRequest();
//		findAttachmentItemRequest.setAttachmentId(Long.parseLong(rk_file));
//		FindAttachmentItemResponse findAttachmentItemResponse = serviceMediator.getSystemConfigureSrv().findAttachmentItem(findAttachmentItemRequest);
//		String file = getConfig("attachment.path") + File.separator
//		+ rk_file+File.separator+Long.toString(findAttachmentItemResponse.getFindAttachmentItemResponseList().get(findAttachmentItemResponse.getFindAttachmentItemResponseList().size()-1).getId());
//		
//		return file;
//	}
}