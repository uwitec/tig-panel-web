
/*                                                 
* Comments:下拉树ComboBoxTreeCheckbox  
 * @extend Ext.form.ComboBox
 * @xtype 'comboboxtreecheckbox'                                                                           
* Author：黄孟俊
* QQ: 240713484
* Create Date：2009-7-9  
* Modified By：                                            
* Modified Date:                                      
* Why & What is modified      
* Company:上海华腾系统软件有限公司                                         
*/
/**
 * ----------------------
 * Demo ComboBoxTreeCheckbox
 * ----------------------
 */
 /*-------------------------------------------------*
	treecombo = {
            xtype:'ComboBoxTreeCheckbox',
            fieldLabel:'所属部门',
            name:'department_id',
            allowUnLeafClick:false,
            treeHeight:200,
            url:'/myoa/department/getTrees',
            onSelect:function(id){
            }
	}
*-----------------------------------------------------*/

Ext.form.ComboBoxTreeCheckbox = Ext.extend(Ext.form.ComboBox, {
    treeHeight : 180,
    allowUnLeafClick:false,
    url:'',
    isAutoLoad:false,
    lazyInit : false, 
    setFieldValue:function(id,text){
        this.setValue(text);
        this.hiddenField.value = id;
    },
    onSelect:function(id){
        
    },

    store : new Ext.data.SimpleStore({
            fields : [],
            data : [[]]
    }),

    //Default
    editable : false, // 禁止手写及联想功能
    mode : 'local',
    triggerAction : 'all',
    maxHeight : 500,
    selectedClass : '',
    onSelect : Ext.emptyFn,
    emptyText : '请选择...',
    
    checkedData:{},  //选中的数据存放

    
    /**
     * 初始化
     * Init
     */
    initComponent : function() {
    	Ext.form.ComboBoxTreeCheckbox.superclass.initComponent.call(this);
        this.tplId = Ext.id();
        this.tpl = '<div id="' + this.tplId + '" style="height:' + this.treeHeight + 'px;overflow:hidden;"></div>';

        var tree = new Ext.tree.TreePanel({
            root:new Ext.tree.AsyncTreeNode({id:'0',text:''}),
            loader:new Ext.tree.TreeLoader({
            	dataUrl:this.url,
            	baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI}
            }), 
            onlyLeafCheckable: false,   
            autoScroll:true,
            height:this.treeHeight,
            rootVisible:false,
            border:false
        });
        var combo = this;
        this.tree = tree;
        tree.on('beforeload',function(node){
        	var url=(combo.url.indexOf('?',0)>-1)?combo.url+'&':combo.url+'?';
            tree.loader.dataUrl = url+'parent_id='+node.id;
        });
        tree.on('load',function(node){
        	var texts="";
        	var ids="";
            var checkedNodes=combo.tree.getChecked();
            for(var i=0;i<checkedNodes.length;i++){   
            	var node = checkedNodes[i];
            	combo.checkedData[node.id]=node.text;
            	texts+=node.text+",";
        		ids+=node.id+",";
            }
        	if(texts.length>0){
        		texts=texts.substring(0,texts.length-1);
        		ids=ids.substring(0,ids.length-1);
        	}
        	var data={"id":ids,"text":texts};
        	combo.setValue(data);
        	
        });
        
        
        tree.on('checkchange', function(node,checked){ 
        	node.expand(); 
        	if(checked){
        		combo.checkedData[node.id]=node.text;
        		combo.checkParentNode(node.parentNode,checked);
        	}else{
        		if(combo.checkedData[node.id]) delete combo.checkedData[node.id];
        	}
        	
        	var texts="";
        	var ids="";
        	for(var i in  combo.checkedData){
        		if(i=="0")continue;
        		texts+=combo.checkedData[i]+",";
        		ids+=i+",";
        	}
        	if(texts.length>0){
        		texts=texts.substring(0,texts.length-1);
        		ids=ids.substring(0,ids.length-1);
        	}
        	var data={"id":ids,"text":texts};
        	combo.setValue(data);
        }, tree);	
        

    },
    checkParentNode:function(node,checked){
    	this.checkedData[node.id]=node.text;
    	try{
    		node.getUI().checkbox.checked=checked; 
    	}catch(e){}
    	this.tree.fireEvent('check',node,checked);
    	if(node.id=="0")return ;
        this.checkParentNode(node.parentNode,checked)
    },

    /**
     * ------------------
     * 事件监听器 
     * Listener   
     * ------------------
     */
    listeners : {
        'expand' : {
            fn: function() {
		    	if(!this.isAutoLoad){
		            if(!this.tree.rendered && this.tplId) {
		                this.tree.render(this.tplId);
		                this.tree.root.expand();
		                this.tree.root.select();
		            }
		            this.tree.show();
				}
            }
        },
        'render':{
            fn:function(){

			this.hiddenField = this.el.insertSibling({
                tag:'input',
                type:'hidden',
                name:this.name,
                id:this.name
            },'before',true);

    		
            this.el.dom.removeAttribute('name');
            if(this.isAutoLoad){
                if(!this.tree.rendered && this.tplId) {
                    this.tree.render(this.tplId);
                    this.tree.expandAll();
                }
    		}
        		
            }
        }
    },
    setValue : function(node){
    	if(!node.id)return;
        var text = node.text;
        this.lastSelectionText = text;
        if(this.hiddenField){
            this.hiddenField.value = node.id;
        }
        Ext.form.ComboBox.superclass.setValue.call(this, text);
        this.value = node.id;
    },
    
    getValue : function(){
        return typeof this.value != 'undefined' ? this.value : '';
    }
});



/**
 * --------------------------------- 
 * 将ComboBoxTreeCheckbox注册为Ext的组件,以便使用
 * Ext的延迟渲染机制，xtype:'combotreecheckbox' 
 * ---------------------------------
 */
Ext.reg('combotreecheckbox',Ext.form.ComboBoxTreeCheckbox);








