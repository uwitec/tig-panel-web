package com.application.webserver;

import org.w3c.dom.Node;

public interface INodeParser {
	public Object parse(Node node, Object...objects);
}
