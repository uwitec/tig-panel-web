
/*                                                 
* Comments:下拉树ComboBoxTree   
* @extend Ext.form.ComboBox
* @xtype 'combotree'                                                                              
* Author：黄孟俊
* QQ: 240713484
* Create Date：  
* Modified By：                                            
* Modified Date:                                      
* Why & What is modified      
* Company:上海华腾系统软件有限公司                                         
*/
 /*-------------------------------------------------*
	treecombo = {
            xtype:'combotree',
            fieldLabel:'所属部门',
            name:'department_id',
            allowUnLeafClick:false,
            treeHeight:200,
            url:'/myoa/department/getTrees',
            onSelect:function(id){
            }
	}
*-----------------------------------------------------*/

ComboBoxTree = Ext.extend(Ext.form.ComboBox, {
    treeHeight : 180,
    allowUnLeafClick:false,
    url:'',
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

    /**
     * 初始化
     * Init
     */
    initComponent : function() {
        ComboBoxTree.superclass.initComponent.call(this);
        this.tplId = Ext.id();
        this.tpl = '<div id="' + this.tplId + '" style="height:' + this.treeHeight + 'px;overflow:hidden;"></div>';

        var tree = new Ext.tree.TreePanel({
            root:new Ext.tree.AsyncTreeNode({id:'0',text:''}),
            loader:new Ext.tree.TreeLoader({dataUrl:this.url}),
            autoScroll:true,
            height:this.treeHeight,
            rootVisible:false,
            border:false
        });
        var combo = this;
        tree.on('beforeload',function(node){
        	var url=(combo.url.indexOf('?',0)>-1)?combo.url+'&':combo.url+'?';
            tree.loader.dataUrl = url+'parent_id='+node.id;
        });
        tree.on('click',function(node){
            if (combo.allowUnLeafClick == true){
            	var data={"id":node.id,"text":node.text};
                combo.setValue(data);
                combo.collapse();

            }else if (node.leaf == true){
            	var data={"id":node.id,"text":node.text};
                combo.setValue(data);
                combo.collapse();
            }
        });
        this.tree = tree;
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
                if (!this.tree.rendered && this.tplId) {
                    this.tree.render(this.tplId);
                    this.tree.root.expand();
                    this.tree.root.select();
                }
                this.tree.show();
            }
        },
        'render':{
            fn:function(){
                this.hiddenField = this.el.insertSibling({
                    tag:'input',
                    type:'hidden',
                    name:this.getName()
                },'before',true);
                this.el.dom.removeAttribute('name');
            }
        }
    },
    setValue : function(node){
    	if(!node.id){
    		node={id:node,text:node};
    	}
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
 * 将ComboBoxTree注册为Ext的组件,以便使用
 * Ext的延迟渲染机制，xtype:'combotree' 
 * ---------------------------------
 */
Ext.reg('combotree', ComboBoxTree);


