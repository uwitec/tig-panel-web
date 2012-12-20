SelfFormWindow = function(divid, title, width, height, fieldwidth,cols, fields, buttons ){
	var items = new Array();
	for(var i=0;i<cols;i=i+1){
		var item = {columnWidth:1/cols, layout:'form', bodyStyle:'padding:5 8 0 8', labelAlign:'top'};
		item.items = new Array();
		for(var j=0;j<fields.length;j=j+1){
			if(fields[j].colIndex === i){
				if(fields[j].field.width === undefined){
					fields[j].field.width = fieldwidth;
				}
				if(fields[j].field.xtype === 'combo'){
					fields[j].field.mode = 'local';
					fields[j].field.triggerAction = 'all';
					fields[j].field.editable = false;
				}
				item.items.push(fields[j].field);
			}
		}
		items.push(item);
	}
	this.window = new Ext.Window({
		renderTo:divid, modal:true, height:height, width:width, title:title, closable:false,  
		items:[
	       {xtype:'form', height:height - 30, width:width - 8, monitorValid:true, buttonAlign:'right', layout:'column', frame:true,
	    	   items:items, autoScroll:true,
	    	   buttons:buttons
	       }
		]
	});
}

SelfFormWindow.prototype.open = function(){
	this.window.getComponent(0).getForm().reset();
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
	this.window.setPosition((clientWidth - this.window.getSize().width)/2, (clientHeight - this.window.getSize().height)/2);
	this.window.show();
}

SelfFormWindow.prototype.close = function(){
	this.window.hide();
}

SelfFormWindow.prototype.updateFields = function(record){
	this.window.getComponent(0).getForm().loadRecord(record);
}

SelfFormWindow.prototype.getFields = function(){
	var submitValues = this.window.getComponent(0).getForm().getValues(false);
	for (var param in submitValues) {   
		if(this.window.getComponent(0).getForm().findField(param) && this.window.getComponent(0).getForm().findField(param).emptyText == submitValues[param]) {   
			submitValues[param] = '';   
		} 
		if(submitValues[param] == 'undefined'){
			submitValues[param] = '';   
		}
	}
	return submitValues;
}
// add by qinliang 20120817
SelfFormWindow.prototype.reset = function(){
	return this.window.getComponent(0).getForm().reset();
}

SelfFormWindow.prototype.onlyopen = function(){
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
	this.window.setPosition((clientWidth - this.window.getSize().width)/2, (clientHeight - this.window.getSize().height)/2);
	this.window.show();
}

// 生成操作按钮和查询条件表单
SelfToolBarPanel = function(divid, title, fieldwidth, rows, fields, buttons, buttonClickFunc){
	var items = new Array();
	if(buttons !== undefined){
		var item = new Ext.Toolbar({height:24});
		for(var i=0;i<buttons.length;i=i+1){
			buttons[i].handler = buttonClickFunc.createDelegate(this, [i]);
			item.addButton(buttons[i]);
			if(i !== buttons.length - 1){
				item.addSeparator();
			}
		}
		items.push(item);
	}
	for(var i=0;i<rows;i=i+1){
		var item = {xtype:'toolbar', height:24};
		item.items = new Array();
		for(var j=0;j<fields.length;j=j+1){
			if(fields[j].rowIndex === i){
				item.items.push({xtype:'tbtext', text:fields[j].field.fieldLabel});
				fields[j].field.width = fieldwidth;
				if(fields[j].field.xtype === 'combo'){
					fields[j].field.mode = 'local';
					fields[j].field.triggerAction = 'all';
					fields[j].field.editable = false;
				}				
				item.items.push(fields[j].field);
				item.items.push({xtype:'tbspacer',width:20});
			}
		}
		items.push(item);
	}
	var height = 0;
	if(buttons !== undefined){
		height = 24	*(rows + 1) + 27;
	}else{
		height = 24	*rows + 27;
	}
	this.toolbarwindow = new Ext.form.FormPanel({
		region : 'north',
		renderTo : divid,
		items : items
	});
}

SelfToolBarPanel.prototype.open = function(){
	//var clientWidth = document.body.clientWidth;
	//var clientHeight = document.body.clientHeight;
	//this.toolbarwindow.setPosition((clientWidth - this.toolbarwindow.getSize().width)/2, (clientHeight - this.toolbarwindow.getSize().height)/2);
	this.toolbarwindow.show();
}

SelfToolBarPanel.prototype.close = function(){
	this.toolbarwindow.hide();
}

SelfToolBarPanel.prototype.getHeight = function(){
	return this.toolbarwindow.getHeight();
}

SelfToolBarPanel.prototype.getFields = function(){
	var submitValues = this.toolbarwindow.getForm().getValues(false);
	for(var param in submitValues) {   
		if(this.toolbarwindow.getForm().findField(param) && this.toolbarwindow.getForm().findField(param).emptyText == submitValues[param]) {   
			submitValues[param] = '';   
		}
		if(submitValues[param] == 'undefined'){
			submitValues[param] = '';   
		}
	}
	return submitValues;
	
}

SelfToolBarPanel.prototype.reset = function(){
	return this.toolbarwindow.getForm().reset();
}