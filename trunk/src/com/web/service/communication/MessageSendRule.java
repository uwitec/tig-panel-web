/**
 * 
 */
package com.web.service.communication;

import java.io.Serializable;
import com.web.form.base.BaseForm;

public class MessageSendRule extends BaseForm implements Serializable{
	private static final long serialVersionUID = 1L;

	private String   ruleId;
	
	private Integer  txnCode;
	
	private Integer  nodeType;
	
	private MessageSendNode node;
	
	public MessageSendRule(){
		
	}
	
	public MessageSendRule(String ruleId, Integer txnCode, Integer nodeType, MessageSendNode node){
		this.ruleId = ruleId;
		this.txnCode = txnCode;
		this.nodeType = nodeType;
		this.node = node;
	}
	
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getTxnCode() {
		return txnCode;
	}

	public void setTxnCode(Integer txnCode) {
		this.txnCode = txnCode;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public MessageSendNode getNode() {
		return node;
	}

	public void setNode(MessageSendNode node) {
		this.node = node;
	}


}
