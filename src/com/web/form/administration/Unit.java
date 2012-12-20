/**
 * 
 */
package com.web.form.administration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.web.form.base.BaseForm;

public class Unit extends BaseForm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Unit(){
	}
	
	public Unit(Unit unit){
		this.nodeid = unit.getNodeid();
		this.nodename = unit.getNodename();
		this.nodelevel = unit.getNodelevel();
		this.parentnodeid = unit.getParentnodeid();
	}
	
	private String        nodeid;
	
	private String        nodename;
	
	private Integer         nodelevel;
	
	private String       parentnodeid;

	
	private Unit parentNode = null;
	private List<Unit> childNodes = null;

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public Integer getNodelevel() {
		return nodelevel;
	}

	public void setNodelevel(Integer nodelevel) {
		this.nodelevel = nodelevel;
	}

	public String getParentnodeid() {
		return parentnodeid;
	}

	public void setParentnodeid(String parentnodeid) {
		this.parentnodeid = parentnodeid;
	}
	
	public List<Unit> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<Unit> childNodes) {
		this.childNodes = childNodes;
	}

	public Unit findNodeById(String id){
		if(this.nodeid.equals(id)){
			return this;
		}else{
			List<Unit> childList = this.childNodes;
			if(childList == null){
				return null;
			}else{
				for(Unit unit : childList){
					Unit temp_unit = unit.findNodeById(id);
					if(temp_unit != null){
						return temp_unit;
					}
				}
				return null;
			}
		}
	}
	
	public void addChildNode(Unit unit){
		if(this.childNodes == null){
			this.childNodes = new ArrayList<Unit>();
		}
		this.childNodes.add(unit);
		unit.parentNode = this;
	}
	
	public Unit getAncestorAtLevel(Integer level){
		if(this.parentNode.getNodelevel().equals(level)){
			return this.parentNode;
		}else if(this.parentNode.getNodelevel() > level){
			return this.parentNode.getAncestorAtLevel(level);
		}else{
			return null;
		}
	}
	
	public List<Unit> getDescendantAtLevel(Integer level){
		List<Unit> temp_list = new ArrayList<Unit>();
		if(this.childNodes == null){
			return null;
		}else{
			if(this.childNodes.get(0).getNodelevel().equals(level)){
				return this.childNodes;
			}else if(this.childNodes.get(0).getNodelevel() < level){
				for(Unit unit : this.childNodes){
					List<Unit> child_temp_list = unit.getDescendantAtLevel(level);
					if(child_temp_list != null){
						temp_list.addAll(child_temp_list);
					}
				}
				return temp_list;
			}else{
				return null;
			}
		}
	}
	
	public List<Unit> getUnitTreeList(int root_space_number){
		List<Unit> outList = new ArrayList<Unit>();
		Unit unit = new Unit(this);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<root_space_number;i++){
			//sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		sb.append(unit.getNodename());
		unit.setNodename(sb.toString());
		outList.add(unit);
		if(this.childNodes != null){
			for(Unit child_unit : this.childNodes){
				List<Unit> child_unit_list = child_unit.getUnitTreeList(root_space_number + 1);
				outList.addAll(child_unit_list);
			}
		}
		return outList;
	}
}
