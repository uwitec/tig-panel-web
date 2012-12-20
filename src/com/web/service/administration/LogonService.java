package com.web.service.administration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.application.exception.AppErrorcode;
import com.application.exception.AppException;
import com.application.webserver.MD5;
import com.web.common.ServiceReturn;
import com.web.form.administration.Module;
import com.web.form.administration.Role;
import com.web.form.administration.User;
import com.web.service.base.BaseService;

public class LogonService extends BaseService 
		implements ILogonService {

	public ServiceReturn getLogonUser(User user) throws Exception {
		
		// TODO Auto-generated method stub
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		
		//从数据库获取用户信息
		MD5 md5 = new MD5();
		user.setPassword(md5.getMD5String(user.getPassword())); // 对用户输入的密码进行加密
		User result_user = sqlDao_i.getRecord("SystemUser.selectLogonUser", user); // 用用户输入的信息到数据库中进行匹配
		if(result_user != null){
			
			if(result_user.getState().equals((short)2)){//用户禁用
				throw new AppException(AppErrorcode.USERPROHIBITED);
			}
			
			int role_size = result_user.getRoles().size(); 
			TreeNode tree = new TreeNode();
			int rootflag = 0; 
			int temp_size = 0; // 角色id为空计数器
			for(Role temp_role : result_user.getRoles()){
				
				if(temp_role.getRoleid() == null){
					temp_size++;
					continue;
				}
				
				// 从数据库获取某个角色的模块信息
				List<Module> result_modules 
					= sqlDao_i.getRecordList("SystemModule.selectModulesByRoleId", temp_role); 
				
				// 构建模块树结构
				if(result_modules != null && result_modules.size() != 0){
					
					for(Module temp_module : result_modules){
						
						TreeNode node = new TreeNode(temp_module);
						TreeNode parentNode = this.getParentTreeNode(tree,node); 
						if(parentNode == null){
							if(rootflag == 0){
								tree = node;
								rootflag ++;
							}
						}else{
							this.addTreeNode(parentNode, node);
						}
					} // end for
				} // end if
			} // end for
			
			if(temp_size == role_size){
				throw new AppException(AppErrorcode.USERNOPRIVILIDGE);
			}
			
			Set<Module> privileges = result_user.getCatalog_and_privileges();
			tranverseTree(tree,privileges);
			Set<Module> privileges1 = new java.util.LinkedHashSet<Module>();
			tranverseTree1(tree,privileges1);
			ret.put(ServiceReturn.FIELD1, result_user);
			ret.put(ServiceReturn.FIELD2, privileges1);
			return ret;
		} // end-if
		throw new AppException(AppErrorcode.INVALIDUSER);
	} // end-getLogonUser
	
	/**
	 * 函数名称 tranverseTree
	 * 函数描述 先根遍历整棵树形节点，将节点信息放入系统模块的Set
	 * 参数tree 树形结构的根节点
	 * 参数privileges 模块Set
	 * 返回值 void
	 * 调用者 本类的getLogonUser
	 */
	private void tranverseTree(TreeNode tree, Set<Module> privileges){
		if(tree.getChildNode() == null 
				&& (tree.getModule().getModuletype().equals((short)3) 
						|| tree.getModule().getModuletype().equals((short)4)
					)){
		}else{
			if(tree.getChildNode() == null){
				tree.getModule().setIsleaf(true);
			}
			privileges.add(tree.getModule());
			List<TreeNode> nodes = tree.getChildNode();
			if(nodes != null){
				Iterator<TreeNode> iter = nodes.iterator();
				while(iter.hasNext()){
					TreeNode node = iter.next();
					tranverseTree(node,privileges);
				}
			}
		}
	}
	
	private void tranverseTree1(TreeNode tree,Set<Module> privileges){
		privileges.add(tree.getModule());
		List<TreeNode> nodes = tree.getChildNode();
		if(nodes != null){
			Iterator<TreeNode> iter = nodes.iterator();
			while(iter.hasNext()){
				TreeNode node = iter.next();
				tranverseTree1(node,privileges);
			}
		}
	}
	
	
	/**
	 * 函数名称 addTreeNode
	 * 函数描述 向某个父节点添加一个子节点
	 * 参数parentNode 父节点
	 * 参数node 需要添加的子节点
	 * 返回值 void
	 * 调用者 本类的getLogonUser
	 */
	private void addTreeNode(TreeNode parentNode,TreeNode node){
		List<TreeNode> nodes = parentNode.getChildNode(); 
		if(nodes == null){
			parentNode.setChildNode(new ArrayList<TreeNode>());
			parentNode.getChildNode().add(node);
		}else{
			int size = nodes.size();
			for(int i=0;i<size;i++){
				if(node.getModule().getModuleorder() < nodes.get(i).getModule().getModuleorder()){
					nodes.add(i, node);
				}else if(node.getModule().getModuleorder() > nodes.get(i).getModule().getModuleorder()){
					if(i == size -1){
						nodes.add(node);
					}
				}else{
					break;
				}
			}
		}
	}
	
	
	/**
	 * 函数名称 tranverseTree
	 * 函数描述 先根遍历整棵树形节点，将节点信息放入系统模块的Set
	 * 参数tree 树形结构的根节点
	 * 参数privileges 模块Set
	 * 返回值 void
	 * 调用者 本类的getLogonUser
	 */
	private TreeNode getParentTreeNode(TreeNode tree,TreeNode node){
		if(tree.getModule() == null){
			return null;
		}else{
			if(tree.getModule().getModuleid().equals(node.getModule().getParentmoduleid())){
				return tree;
			}else{
				if(tree.getChildNode() != null){
					Iterator<TreeNode> iter = tree.getChildNode().iterator();
					while(iter.hasNext()){
						TreeNode temp = getParentTreeNode(iter.next(),node);
						if(temp != null){
							return temp;
						}
					}
					return null;
				}else{
					return null;
				}
			}
		}
	}
	
	
	/**
	 * @author David
	 * 类描述 系统模块树形结构的节点类型
	 * 
	 */
	private class TreeNode{
		public TreeNode(){
		}
		
		public TreeNode(Module module){
			this.module = module;
		}
		
		private Module module = null;
		private List<TreeNode> childNode = null;
		public Module getModule() {
			return module;
		}
		/*public void setModule(Module module) {
			this.module = module;
		}*/
		public List<TreeNode> getChildNode() {
			return childNode;
		}
		public void setChildNode(List<TreeNode> childNode) {
			this.childNode = childNode;
		}
	}

}
