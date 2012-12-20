Ext.data.Connection=function(config){Ext.apply(this,config);this.addEvents("beforerequest","requestcomplete","requestexception");Ext.data.Connection.superclass.constructor.call(this)};Ext.extend(Ext.data.Connection,Ext.util.Observable,{timeout:30000,autoAbort:false,disableCaching:true,request:function(o){if(this.fireEvent("beforerequest",this,o)!==false){var p=o.params;if(typeof p=="function"){p=p.call(o.scope||window,o)}
if(typeof p=="object"){p=Ext.urlEncode(p)}
if(this.extraParams){var extras=Ext.urlEncode(this.extraParams);p=p?(p+'&'+extras):extras}
var url=o.url||this.url;if(typeof url=='function'){url=url.call(o.scope||window,o)}
if(o.form){var form=Ext.getDom(o.form);url=url||form.action;var enctype=form.getAttribute("enctype");if(o.isUpload||(enctype&&enctype.toLowerCase()=='multipart/form-data')){return this.doFormUpload(o,p,url)}
var f=Ext.lib.Ajax.serializeForm(form);p=p?(p+'&'+f):f}
var hs=o.headers;if(this.defaultHeaders){hs=Ext.apply(hs||{},this.defaultHeaders);if(!o.headers){o.headers=hs}}
var cb={success:this.handleResponse,failure:this.handleFailure,scope:this,argument:{options:o},timeout:o.timeout||this.timeout};var method=o.method||this.method||(p?"POST":"GET");if(method=='GET'&&(this.disableCaching&&o.disableCaching!==false)||o.disableCaching===true){url+=(url.indexOf('?')!=-1?'&':'?')+'_dc='+(new Date().getTime())}
if(typeof o.autoAbort=='boolean'){if(o.autoAbort){this.abort()}}else if(this.autoAbort!==false){this.abort()}
if((method=='GET'&&p)||o.xmlData||o.jsonData){url+=(url.indexOf('?')!=-1?'&':'?')+p;p=''}
this.transId=Ext.lib.Ajax.request(method,url,cb,p,o);return this.transId}else{Ext.callback(o.callback,o.scope,[o,null,null]);return null}},isLoading:function(transId){if(transId){return Ext.lib.Ajax.isCallInProgress(transId)}else{return this.transId?true:false}},abort:function(transId){if(transId||this.isLoading()){Ext.lib.Ajax.abort(transId||this.transId)}},handleResponse:function(response){this.transId=false;var options=response.argument.options;response.argument=options?options.argument:null;this.fireEvent("requestcomplete",this,response,options);Ext.callback(options.success,options.scope,[response,options]);Ext.callback(options.callback,options.scope,[options,true,response])},handleFailure:function(response,e){this.transId=false;var options=response.argument.options;response.argument=options?options.argument:null;this.fireEvent("requestexception",this,response,options,e);Ext.callback(options.failure,options.scope,[response,options]);Ext.callback(options.callback,options.scope,[options,false,response])},doFormUpload:function(o,ps,url){var id=Ext.id();var frame=document.createElement('iframe');frame.id=id;frame.name=id;frame.className='x-hidden';if(Ext.isIE){frame.src=Ext.SSL_SECURE_URL}
document.body.appendChild(frame);if(Ext.isIE){document.frames[id].name=id}
var form=Ext.getDom(o.form);form.target=id;form.method='POST';form.enctype=form.encoding='multipart/form-data';if(url){form.action=url}
var hiddens,hd;if(ps){hiddens=[];ps=Ext.urlDecode(ps,false);for(var k in ps){if(ps.hasOwnProperty(k)){hd=document.createElement('input');hd.type='hidden';hd.name=k;hd.value=ps[k];form.appendChild(hd);hiddens.push(hd)}}}
function cb(){var r={responseText:'',responseXML:null};r.argument=o?o.argument:null;try{var doc;if(Ext.isIE){doc=frame.contentWindow.document}else{doc=(frame.contentDocument||window.frames[id].document)}
if(doc&&doc.body){r.responseText=doc.body.innerHTML}
if(doc&&doc.XMLDocument){r.responseXML=doc.XMLDocument}else{r.responseXML=doc}}catch(e){}
Ext.EventManager.removeListener(frame,'load',cb,this);this.fireEvent("requestcomplete",this,r,o);Ext.callback(o.success,o.scope,[r,o]);Ext.callback(o.callback,o.scope,[o,true,r]);setTimeout(function(){Ext.removeNode(frame)},100)}
Ext.EventManager.on(frame,'load',cb,this);form.submit();if(hiddens){for(var i=0,len=hiddens.length;i<len;i++){Ext.removeNode(hiddens[i])}}}});Ext.Ajax=new Ext.data.Connection({autoAbort:false,serializeForm:function(form){return Ext.lib.Ajax.serializeForm(form)}});


Ext.override(Ext.data.Connection,{
	win:false,
	panel:false,
	showWin:function(msg,msgDesc){
		var self=this;
		var btn2=new Ext.Button({
			text:'确认',
			handler: function(){
				self.win.hide();
        	}
		});
		if(!this.win){
			this.panel= new Ext.Panel({
                el: 'message-panel-common',
                autoScroll:true,
                border:false
            });
			this.win = new Ext.Window({
                el:'message-win-common',
                title:'消息窗口',
                layout:'fit',
                width:500,
                height:300,
                closeAction:'hide',
                plain: true,
                
                items:self.panel,

                buttons: [btn2,{
                    text: '关闭',
                    handler: function(){
                        self.win.hide();
                    }
                }]
            });
		}
		this.win.show();
		self.panel.body.update(msgDesc);
	},
    serail : 0,
    request : Ext.data.Connection.prototype.request.createSequence(function(){ 
        this.serail++;
        Ext.get('load-ing-common').show();
    }),
    handleResponse : Ext.data.Connection.prototype.handleResponse.createInterceptor(function(response){ 
        if(this.serail==1)Ext.get('load-ing-common').hide();
        this.serail--;
        if(typeof response.getResponseHeader('rtnStatus')!='undefined'){
        	var rtnStatus=parseInt(response.getResponseHeader('rtnStatus'));
			switch(rtnStatus){
			    case 99:
			    	alert(response.responseText);
			    	window.location.href=window.location.href;
				    break;
			    case 98:
			    	this.showWin('后台异常错误！',response.responseText);
				    break;
			    default:
				    break;
			}
			return false
        }
        return true;           
    }),
    doFormUpload : Ext.data.Connection.prototype.doFormUpload.createSequence(function(){ 
        if(this.serail==1)Ext.get('load-ing-common').hide();
        this.serail--;
    }),
    handleFailure : Ext.data.Connection.prototype.handleFailure.createSequence(function(){ 
        Ext.DomHelper.overwrite(Ext.get('load-ing-common'),'加载出错!')
    this.serail=this.serail-100;
    })
});


Ext.ux.GridView=Ext.extend(   
    Ext.grid.GridView,{   
        getRowClass:function(record,index){   
            if(index%2==1)   
                return 'jui_grid_row_single';   
            else  
                return 'jui_grid_row_double';   
        }   
    }   
)  

/*
 * author:黄孟俊
 * QQ:240713484
 * date:2009-11-3
 * company:www.huateng.com
 */
Ext.override(Ext.form.BasicForm, {
  setReadOnly: function(bReadOnly){
    this.items.each(function(f){
      if (f.isFormField){
        f.getEl().dom.readOnly = bReadOnly;
      }
    });    
  }
});

/*
 * author:黄孟俊
 * QQ:240713484
 * date:2009-11-3
 * company:www.huateng.com
 */
Ext.ns('Ext.ux.form');


Ext.ux.form.ComboRmt = Ext.extend(Ext.form.ComboBox, {
	constructor: function(config){
		config = config || {};
		config = Ext.apply(config || {}, {
			emptyText:'请选择...',
			hiddenName:config.name,
			store:new Ext.data.Store({
			    proxy:new Ext.data.HttpProxy({url:config.url}),
			    reader:new Ext.data.JsonReader({}, ['id','name']),
			    autoLoad:config.autoLoad || false
			}),
			displayField:'name',
			valueField:'id',
			forceSelection: true,
			selectOnFocus: true,
			editable: false,
			triggerAction: 'all',
			mode: 'remote'
		});

		Ext.ux.form.ComboRmt.superclass.constructor.apply(this, arguments);
	}

});

Ext.reg('combormt', Ext.ux.form.ComboRmt);

//backwards compat
Ext.ux.ComboRmt = Ext.ux.form.ComboRmt;



Ext.PagingToolbarEx = Ext.extend(Ext.PagingToolbar, {
	constructor: function(config){
		config = config || {};
		var self = this;
		config = Ext.apply(config || {}, {
			items:[ 
			   '&nbsp;&nbsp;&nbsp;&nbsp;每页显示', 
			   new Ext.form.ComboBox({ 
				   store:new Ext.data.SimpleStore({ 
					   fields: ['value', 'text'], 
					   data : [[10,10],[15,15],[20,20],[50,50],[100,100]] 
			       }),
			       width:50,   
                   displayField:'text',   
                   typeAhead: true,   
                   mode: 'local',   
                   value:config.pageSize,   
                   triggerAction: 'all',   
                   selectOnFocus:true,
                   valueField:'value',
			       listeners:{ 
					   change:{ 
						   fn:function(box,value,oldValue){
				   			   self.pageSize=parseInt(value);
				   			   config.store.load({params:{start:0,limit:self.pageSize}});   
				   			   config.store.reload();   
					       }
					   }, 
					   select:{ 
						   fn:function(combo,record,index){					   	   
						       self.pageSize=parseInt(record.data.value);
						       config.store.load({params:{start:0,limit:self.pageSize}});   
						       config.store.reload();   
						   } 
					   }  
				   } 
				}),
			 '条'
			]
		});

		Ext.PagingToolbarEx.superclass.constructor.apply(this, arguments);
	}

});

//设置
Ext.MessageBox.minWidth=200;


//下拉多选树获取选择的参数
var checkParentNode=function(node,params){
	if(node.parentNode){
		params[node.parentNode.id]=node.parentNode.id;
		checkParentNode(node.parentNode,params);
	}
}

var getTreeCheckIds = function(tree,fieldName){
	var selNodes = tree.getChecked();
	var params=new Object();
	var ids="";
	Ext.each(selNodes, function(node){
		params[node.id]=node.id;
		checkParentNode(node,params);
    });
	for(var i in params){
		ids+="&"+fieldName+"="+params[i];
	}
	return ids;
};



/*
 * author:黄孟俊
 * 下拉选择表格框
 * QQ:240713484
 * company:www.huateng.com
 */
Ext.ns('Ext.ux.form.ComboBoxGrid');
/** 
 * 下拉表格ComboBoxGrid 
 * @extend Ext.form.ComboBox 
 * 
 * @author achui 2009/11/16
 */ 
ComboBoxGrid = function(cfg) { 
	//前台传入grid组件
	this.grid = cfg.grid;
    Ext.apply(this, cfg); 
    ComboBoxGrid.superclass.constructor.call(this, { 
        editable :false, // 禁止手写及联想功能 
        mode : 'local', 
        triggerAction : 'all', 
        store : new Ext.data.SimpleStore({   
            fields : [],   
            data : [[]]   
     }),  
        maxHeight : 500, 
        selectedClass : '', 
        onSelect : Ext.emptyFn, 
        emptyText : '请选择...', 
        gridWidth:600, 
        gridHeight : 300, 
        listAlign : 'tr-br', 
        listWidth : 600, 
        resizable : false
    }); 
}; 
Ext.extend(ComboBoxGrid, Ext.form.ComboBox, { 
    gridClk : function(grid, rowIndex, e) { 
        var record = grid.getSelectionModel().getSelected();
        var idValue = record.get(this.valueField); 
        var displayValue = record.get(this.displayField); 
        this.setRawValue(displayValue); 
        this.setValue(idValue); 
        this.collapse(); 
        //this.fireEvent('gridselected', grid.getRecord(rowIndex)); 
    }, 
    initLayout : function() { 
        this.grid.autoScroll = true; 
        this.grid.height = this.gridHeight; 
        this.grid.containerScroll = false; 
        this.grid.border = false; 
        this.listWidth = this.grid.width + 5; 
    }, 
    initComponent : function() { 
        ComboBoxGrid.superclass.initComponent.call(this); 
        this.initLayout(); 
        this.tplId = Ext.id(); 
        this.tpl = '<div id="' + this.tplId + '" style="height:' + (this.gridHeight || 180) 
                + '";overflow:hidden;"></div>'; 
        //Add Event 
       // this.addEvents('gridselected'); 
    }, 
    listeners : { 
        'expand' : { 
            fn : function() {
             var pageSize = this.grid.getBottomToolbar().pageSize;
                if ( this.tplId) { 
                    this.initLayout(); 
                    this.grid.render(this.tplId); 
                   
                    this.grid.getStore().load({
                     params:{
                      start:0,
                      limit:pageSize
                     }
                    })
                    //设置ComboBox的store使得设置的时候能够取到真实的Value
                    this.store = this.grid.store;
                    //this.store.reload();
                    //this.store.reload(); 
                    if (this.store.getCount() == 0) { 
                        this.store.add(new Ext.data.Record([{}])); 
                    } 
                    this.grid.on('rowclick', this.gridClk, this); 
                } 
                this.grid.show(); 
            } 
        }, 
        'render' : { 
            fn : function() { 
            } 
        }, 
        'beforedestroy' : { 
            fn : function(cmp) { 
                this.purgeListeners(); 
                this.grid.purgeListeners(); 
            } 
        }, 
        'collapse' : { 
            fn : function(cmp) { 
                /** 
                 *  防止当store的记录为0时不出现下拉的状况 
                 */ 
                if (this.grid.store.getCount() == 0) { 
                    this.store.add(new Ext.data.Record([{}])); 
                } 
            } 
        } 
    } 
}); 

Ext.reg('combogrid',Ext.ux.form.ComboBoxGrid);


/*
 * author:黄孟俊
 * 修改Ext.form.TextField验证规则，扩展为汉字算两字符长度
 * QQ:240713484
 * company:www.huateng.com
 */
String.prototype.codeLength=function(){
	 var len=0;
	 if(this==null||this.length==0)
	  return 0;
	 var str=this.replace(/(^\s*)|(\s*$)/g,"");//去掉空格
	 for(i=0;i<str.length;i++)
	  if(str.charCodeAt(i)>0&&str.charCodeAt(i)<128)
	   len++;
	  else 
	   len+=2;
	 return len;
} 

Ext.form.TextField.prototype.validateValue=function(value){
	if(Ext.isFunction(this.validator)){
        var msg = this.validator(value);
        if(msg !== true){
            this.markInvalid(msg);
            return false;
        }
    }
    if(value.length < 1 || value === this.emptyText){ // if it's blank
        if(this.allowBlank){
            this.clearInvalid();
            return true;
        }else{
            this.markInvalid(this.blankText);
            return false;
        }
   }
    if(this.vtype){
        var vt = Ext.form.VTypes;
        if(!vt[this.vtype](value, this)){
            this.markInvalid(this.vtypeText || vt[this.vtype +'Text']);
            return false;
        }
    }
    if(this.regex && !this.regex.test(value)){
        this.markInvalid(this.regexText);
        return false;
    }
    if(value.codeLength() < this.minLength){
        this.markInvalid(String.format(this.minLengthText, this.minLength));
        return false;
    }
    if(value.codeLength() > this.maxLength){
        this.markInvalid(String.format(this.maxLengthText, this.maxLength));
        return false;
    }
    return true;
}