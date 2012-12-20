package com.application.webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class DocumentParser {
	private Map<String, INodeParser> letMap = new HashMap<String, INodeParser>();
	
	public static Properties parseAttributes(Node n, Properties variables) {
		Properties attributes = new Properties();
		NamedNodeMap attributeNodes = n.getAttributes();
		for (int i = 0; i < attributeNodes.getLength(); i++) {
			Node attribute = attributeNodes.item(i);
			attributes.put(attribute.getNodeName(), attribute.getNodeValue());
		}
		return attributes;
	}
	
	public void addNodeLet(String xpath ,INodeParser nodeParser){
		letMap.put(xpath, nodeParser);
	}
	
	public Object processNodelet(Node node, String pathString, Object...objects) {
		INodeParser nodelet = (INodeParser) letMap.get(pathString);
		if (nodelet != null) {
			return nodelet.parse(node, objects);
		}
		return null;
	}
}
