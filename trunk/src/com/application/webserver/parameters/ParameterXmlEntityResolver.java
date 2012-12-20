package com.application.webserver.parameters;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class ParameterXmlEntityResolver implements EntityResolver {

	private static final String UNIT_LEVEL_DTD = "com/application/webserver/parameters/unitlevel.dtd";
	private static final String USER_LEVEL_DTD = "com/application/webserver/parameters/userlevel.dtd";
	private static final String SYSTEM_DICTIONARY_DTD = "com/application/webserver/parameters/systemdictionary.dtd";
	private static final String ROOT_PRIVILEGE_DTD = "com/application/webserver/parameters/rootprivilege.dtd";
	private static final String DATAGRAM_DTD = "com/application/webserver/parameters/datagram.dtd";

	private static final Map<String, String> doctypeMap = new HashMap<String, String>();

	static {
		doctypeMap.put("-//HUATENG//DTD Unit Level DTD//EN".toUpperCase(), UNIT_LEVEL_DTD);
		doctypeMap.put("http://www.huateng.com/Guatemala/unitlevel.dtd".toUpperCase(), UNIT_LEVEL_DTD);
		doctypeMap.put("-//HUATENG//DTD User Level DTD//EN".toUpperCase(), USER_LEVEL_DTD);
		doctypeMap.put("http://www.huateng.com/Guatemala/userlevel.dtd".toUpperCase(), USER_LEVEL_DTD);
		doctypeMap.put("-//HUATENG//DTD System Dictionary DTD//EN".toUpperCase(), SYSTEM_DICTIONARY_DTD);
		doctypeMap.put("http://www.huateng.com/Guatemala/systemdictionary.dtd".toUpperCase(), SYSTEM_DICTIONARY_DTD);
		doctypeMap.put("-//HUATENG//DTD Root Privilege DTD//EN".toUpperCase(), ROOT_PRIVILEGE_DTD);
		doctypeMap.put("http://www.huateng.com/Guatemala/rootprivilege.dtd".toUpperCase(), ROOT_PRIVILEGE_DTD);
		doctypeMap.put("-//HUATENG//DTD Datagram DTD//EN".toUpperCase(), DATAGRAM_DTD);
		doctypeMap.put("http://www.huateng.com/Guatemala/datagram.dtd".toUpperCase(), DATAGRAM_DTD);
	}

	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		// TODO Auto-generated method stub
		if (publicId != null) publicId = publicId.toUpperCase();
	    if (systemId != null) systemId = systemId.toUpperCase();

	    InputSource source = null;
	    try {
	      String path = (String) doctypeMap.get(publicId);
	      source = getInputSource(path, source);
	      if (source == null) {
	        path = (String) doctypeMap.get(systemId);
	        source = getInputSource(path, source);
	      }
	    } catch (Exception e) {
	      throw new SAXException(e.toString());
	    }
	    return source;
	}

	
	private InputSource getInputSource(String path, InputSource source) {
		if (path != null) {
			InputStream in = null;
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			if (loader != null)
				in = loader.getResourceAsStream(path);
			if (in == null)
				in = ClassLoader.getSystemResourceAsStream(path);
			if (in != null) 
				source = new InputSource(in);
		}
		return source;
	}
}
