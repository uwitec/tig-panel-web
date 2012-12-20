/**
 * 
 */
package com.web.service.communication;

import java.io.Serializable;

import com.web.form.base.BaseForm;

public class MessageSendNode extends BaseForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private String   nodeId;
	
	private String   nodeName;
	
	private String   nodeIp;
	
	private Integer  nodePort;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeIp() {
		return nodeIp;
	}

	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}

	public Integer getNodePort() {
		return nodePort;
	}

	public void setNodePort(Integer nodePort) {
		this.nodePort = nodePort;
	}
}
