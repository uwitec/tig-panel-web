PageQueryLite = function(divid, url, textconfig, params, changeclearcomps, batchQueryAgainVar){
	this.url = url;
	this.params = params;
	if(changeclearcomps != undefined ){
		if(this.changeclearcomps != ''){
			this.changeclearcomps = changeclearcomps;
		}
	} 
	this.rsM = new Ext.grid.RowSelectionModel({
        singleSelect: true
    });
	this.dataStore = new Ext.data.JsonStore({fields : ['id','name','reserved']});
	var columnModel = new Ext.grid.ColumnModel(
		[{dataIndex:'id'},{dataIndex : 'name'}]
	);
	this.selfPagingToolLite = new SelfPagingToolbarLite({
		store:this.dataStore, parentComp:this, jsonData:{}
	});
	this.pagingGrid = new Ext.grid.GridPanel({
		tbar   : this.selfPagingToolLite,
		width  : 320,
		height : 260,
	    cm     : columnModel,
		sm     : this.rsM,
		store  : this.dataStore,
		frame  : false,
		floating : true,
		renderTo : divid
	});

	this.pagingGrid.on('sortchange',function(field,sort){
		var sortStr = sort.field;
		if(columnSortFunc !== undefined){
			sortStr = columnSortFunc(sort);
		}
		if(this.submit_obj !== undefined){
			this.submit_obj['sortString'] = sortStr + ' ' + sort.direction;
			this.selfPagingTool.resetPageInfo();
			this.selfPagingTool.doLoad(1);
		}
	},this);
	
	this.pagingGrid.hide();
	this.pageingGridVisible = false;
	
	this.textFieldHidden = new Ext.form.Hidden({id:textconfig['hiddenId'], name:textconfig['hiddenName']});
	this.textField = new Ext.form.TextField(textconfig);
	
	this.textField.on('focus', function(comp){
		if(this.textDom === undefined){
			this.textDom = this.textField.getEl().dom;
			this.textDom.onclick = this.onTextFieldClicked.createDelegate(this,[comp]);
			this.textDom.ondblclick = this.onTextFieldDblClicked.createDelegate(this,[comp]);
			this.textDom.onkeyup = this.onTextFieldKeyup.createDelegate(this,[comp]);
		}
	},this);
	
	if(batchQueryAgainVar == undefined){
		this.pagingGrid.on(
			'dblclick', 
			function(e){
				var record = this.rsM.getSelected();
				if(record === undefined){
					this.textFieldHidden.setValue();
					this.textField.setValue();
				}else{
					this.textFieldHidden.setValue(record.get('id'));
					this.textField.setValue(record.get('name'));
				}
				this.textField.fireEvent('change',{field:this.textField});
				this.pagingGrid.hide();
				this.pageingGridVisible = false;
				this.resetOthers();
			},
			this
		);
	}else{
		this.pagingGrid.on('dblclick', function(e){
			var record = this.rsM.getSelected();
			if(record === undefined){
				this.textFieldHidden.setValue();
				this.textField.setValue();
			}else{
				this.textFieldHidden.setValue(record.get('id'));
				this.textField.setValue(record.get('name'));
			}
			this.textField.fireEvent('change',{field:this.textField});
			this.pagingGrid.hide();
			this.pageingGridVisible = false;
			this.resetOthers();

			Ext.getCmp(batchQueryAgainVar['gridid']).getSelectionModel().clearSelections();			
			var submitDate = {};
			
			submitDate[batchQueryAgainVar['queryrequirement']] = this.textFieldHidden.getValue();
			requestAjax(batchQueryAgainVar['url'],submitDate,function(sRet){

				var gridrecords = Ext.getCmp(batchQueryAgainVar['gridid']).getStore().getRange();
				
				t=sRet.field1;
				for(var i=0;i<gridrecords.length;i=i+1){
					for(var j=0;j<t.length;j=j+1){
					    if(batchQueryAgainVar['mediatypeout'] == undefined &&batchQueryAgainVar['mediatypein'] == undefined){
							if(gridrecords[i].get(batchQueryAgainVar['compareobject'])==t[j][batchQueryAgainVar['queryresult']]){
								Ext.getCmp(batchQueryAgainVar['gridid']).getSelectionModel().selectRows([i],true) ;
								break;
						}					
						}else{
							if(gridrecords[i].get(batchQueryAgainVar['compareobject'])==t[j][batchQueryAgainVar['queryresult']]
							 && gridrecords[i].get(batchQueryAgainVar['mediatypein'])==t[j][batchQueryAgainVar['mediatypeout']]){
								Ext.getCmp(batchQueryAgainVar['gridid']).getSelectionModel().selectRows([i],true) ;
							   	break;
							}
							
						}
					}
				}
			});
		},this);
	}
	
	if(PageQueryLite.objects == undefined){
		PageQueryLite.objects = new Array();
		document.onmousedown = PageQueryLite.onmousedown.createDelegate(PageQueryLite,[]);
	}
	PageQueryLite.objects.push(this);
}
PageQueryLite.onmousedown = function(){
	var e = window.event;
	if(e.button == 1){
		var clientX = e.clientX;
		var clientY = e.clientY;
		for(var i=0;i<PageQueryLite.objects.length;i=i+1){
			var arr = PageQueryLite.objects[i].pagingGrid.getPosition();
			var size = PageQueryLite.objects[i].pagingGrid.getSize();
			var arr1 = PageQueryLite.objects[i].textField.getPosition();
			var size1 = PageQueryLite.objects[i].textField.getSize();
			if(PageQueryLite.objects[i].pageingGridVisible
				&& (clientX < arr[0] || clientX > arr[0] + size.width
						|| clientY < arr[1] || clientY > arr[1] + size.height)
				&& (clientX < arr1[0] || clientX > arr1[0] + size1.width
						|| clientY < arr1[1] || clientY > arr1[1] + size1.height)){
				PageQueryLite.objects[i].onTextFieldDblClicked();
			}
		}
	}
}

PageQueryLite.prototype.getTextField = function(){
	return this.textField;
}
PageQueryLite.prototype.getTextHiddenField = function(){
	return this.textFieldHidden;
}
PageQueryLite.prototype.getValue = function(){
	return this.textFieldHidden.getValue();
}
PageQueryLite.prototype.getText = function(){
	return this.textField.getValue();
}
PageQueryLite.prototype.getReserved = function(){
	var val = this.textFieldHidden.getValue();
	var tmp;
	this.dataStore.each(function(record){
		if(record.get('id') == val){
			tmp = record.get('reserved');
			return false;
		}
	});
	return tmp;
}
PageQueryLite.prototype.setValue = function(value){
	this.textFieldHidden.setValue(value);
}
PageQueryLite.prototype.setText = function(value){
	this.textField.setValue(value);
}

PageQueryLite.prototype.onTextFieldClicked = function(comp){
	if(!this.pageingGridVisible){
		var position = comp.getPosition();
		var size = comp.getSize();
		this.pagingGrid.show();
		this.pagingGrid.setPosition(position[0], (position[1] + size.height));
		if(this.pagingGrid.getSize().width != this.textField.getSize().width){
			//this.pagingGrid.setWidth(this.textField.getSize().width);
			var colmodel = this.pagingGrid.getColumnModel();
			
			//colmodel.setColumnWidth(0, this.textField.getSize().width-5);
			colmodel.setColumnWidth(0, (this.pagingGrid.getWidth()-10) / 2);
		}
		this.pageingGridVisible = true;
		this.queryPage();
	}
}

PageQueryLite.prototype.onTextFieldDblClicked = function(comp){
	if(!this.textField.readOnly){
		this.textFieldHidden.setValue();
		this.textField.setValue();
	}
	this.pagingGrid.hide();
	this.pageingGridVisible = false;
	this.resetOthers();
}

PageQueryLite.prototype.resetOthers = function(){
	if(this.changeclearcomps != undefined){
	   if(this.changeclearcomps != ''){
		var len = this.changeclearcomps.length;
		for(var i=0;i<len;i=i+1){
			this.changeclearcomps[i].onTextFieldDblClicked();
		}
		}
	}
}

PageQueryLite.prototype.onTextFieldKeyup = function(comp){
	if(this.timer_id >= 0){
		window.clearTimeout(this.timer_id);
	}
	this.timer_id = window.setTimeout(this.keyupProcess,300, this);
}

PageQueryLite.prototype.keyupProcess = function(thisclass){
	thisclass.queryPage();
}
PageQueryLite.prototype.queryPage = function(){
	this.submit_obj = {};
	for(var i=0; i<this.params.length; i=i+1){
		var str = this.params[i];
		var index = str.indexOf('|');
		if(index < 0){
			this.submit_obj[str] = this.textField.getValue();
		}else{
			this.submit_obj[str.substring(index + 1, str.length)] = Ext.getCmp(str.substring(0,index)).getValue();
		}
	}
	this.selfPagingToolLite.resetPageInfo();
	this.selfPagingToolLite.doLoad(1);
}

var _sto = window.setTimeout;
window.setTimeout = function (fRef, mDelay) {
	if (typeof fRef == "function") {
		var argu = Array.prototype.slice.call(arguments, 2);
		var f = (function () {
			fRef.apply(null, argu);
		});
		return _sto(f, mDelay);
	}
	return _sto(fRef, mDelay);
};



SelfPagingToolbarLite = Ext.extend(Ext.Toolbar, {
    paramNames : {start: 'start', limit: 'limit', total: 'total', resultList:'field1', resultObject:'field2'},
    jsonData   : {}, parentComp : {},
    initComponent : function(){
        this.addEvents('change','beforechange');
        SelfPagingToolbarLite.superclass.initComponent.call(this);
        this.bind(this.store);
        this.jsonData[this.paramNames.start] = 1;
        this.jsonData[this.paramNames.limit] = 10;
    },

    onRender : function(ct, position){
        SelfPagingToolbarLite.superclass.onRender.call(this, ct, position);
        this.first = this.addButton({iconCls:"x-tbar-page-first", disabled:true,
            handler: this.onClick.createDelegate(this, ["first"])
        });
        this.prev = this.addButton({iconCls:"x-tbar-page-prev", disabled:true,
            handler: this.onClick.createDelegate(this, ["prev"])
        });
        this.field = new Ext.form.NumberField({
            cls: 'x-tbar-page-number',
            allowDecimals: false,
            allowNegative: false,
            enableKeyEvents: true,
            selectOnFocus: true,
            submitValue: false,
            disabled:true
        })
        this.field.on("keydown", this.onPagingKeydown, this);
        this.field.on("focus", function(){this.el.dom.select();});
        this.field.on("blur", this.onPagingBlur, this);
        this.field.setHeight(18);
        this.addField(this.field);
        //this.addSeparator();
        this.next = this.addButton({iconCls:"x-tbar-page-next", disabled:true,
            handler: this.onClick.createDelegate(this, ["next"])
        });
        this.last = this.addButton({iconCls:"x-tbar-page-last", disabled:true,
            handler: this.onClick.createDelegate(this, ["last"])
        });
        if(this.dsLoaded){this.onLoad.apply(this, this.dsLoaded);}
    },
    
    // private
    onLoad : function(store, r, o){
        if(!this.rendered){
            this.dsLoaded = [store, r, o];
            return;
        }
        if(this.jsonData[this.paramNames.start] == -1){
        	this.jsonData[this.paramNames.start] = this.jsonData[this.paramNames.total];
        }
        this.field.el.dom.value = this.jsonData[this.paramNames.start];
        this.first.setDisabled(this.jsonData[this.paramNames.start] == 1);
        this.prev.setDisabled(this.jsonData[this.paramNames.start] == 1);
        if(this.jsonData[this.paramNames.total] == 0){
        	var storeCount = this.store.getTotalCount(); 
        	if(storeCount < this.jsonData[this.paramNames.limit]){
        		this.next.setDisabled(true);
        		this.last.setDisabled(true);
        	}else{
        		this.next.setDisabled(false);
        		this.last.setDisabled(false);
        	}
        }else{
        	this.next.setDisabled(true);
        	this.last.setDisabled(true);
        }
        this.fireEvent('change', this);
    },

    // private
    onLoadError : function(){
        if(!this.rendered){return;}
    },

    // private
    readPage : function(d){
        var v = this.field.dom.value, pageNum;
        if (!v || isNaN(pageNum = parseInt(v, 10))) {
            this.field.dom.value = d.activePage;
            return false;
        }
        return pageNum;
    },

    //private
    onPagingBlur: function(e){
        this.field.dom.value = this.jsonData[this.paramNames.start];
    },

    // private
    onPagingKeydown : function(e){
        var k = e.getKey();
        if (k == e.RETURN) {
            e.stopEvent();
            var newvalue = this.field.dom.value;
            if(newvalue === '' || newvalue.search(/[^0-9]/) >= 0){
            	this.field.dom.value = this.jsonData[this.paramNames.start];
            }else{
            	var tmp = newvalue.toString().replace(/0*/,'');
            	this.doLoad(parseInt(tmp));
            }
        }
    },

    beforeLoad : function(){},

    doLoad : function(start){
    	this.jsonData[this.paramNames.start] = start;
		this.parentComp.submit_obj[this.paramNames.start] = this.jsonData[this.paramNames.start];
		this.parentComp.submit_obj[this.paramNames.limit] = this.jsonData[this.paramNames.limit];
        Ext.Ajax.request({
			url : this.parentComp.url, jsonData : this.parentComp.submit_obj, scope:this, params:{requesttype:'ajax'},
			callback : function(options, success, response){
        		if(success){
        			var rawObj = Ext.util.JSON.decode(response.responseText);
        			var jsonObj = Ext.util.JSON.decode(rawObj.actionresult);
        			if(jsonObj.result){
        				var resultList = jsonObj[this.paramNames.resultList];
        				var resultObject = jsonObj[this.paramNames.resultObject];
        				this.jsonData[this.paramNames.total] = resultObject[this.paramNames.total];
        				this.store.loadData(resultList,false);
        				this.field.el.dom.disabled = false;
        			}else{
        				Ext.MessageBox.alert('',jsonObj.message);
        			}
        		}else{//ajax error
        		}
        	}
		});
    },
     
    changePage: function(page){
        this.doLoad(((page-1) * this.pageSize).constrain(0, this.store.getTotalCount()));
    },

    // private
    onClick : function(which,num){
        var store = this.store;
        switch(which){
            case "first":
                this.doLoad(1);
            break;
            case "prev":
                this.doLoad(this.jsonData[this.paramNames.start] - 1);
            break;
            case "next":
                this.doLoad(this.jsonData[this.paramNames.start] + 1);
            break;
            case "last":
                this.doLoad(-1);
            break;
        }
    },
    
    resetPageInfo : function(){
    	this.field.el.dom.value = 1;
    },
    
    unbind : function(store){
        store = Ext.StoreMgr.lookup(store);
        store.un("beforeload", this.beforeLoad, this);
        store.un("load", this.onLoad, this);
        store.un("loadexception", this.onLoadError, this);
        this.store = undefined;
    },

    bind : function(store){
        store = Ext.StoreMgr.lookup(store);
        store.on("beforeload", this.beforeLoad, this);
        store.on("load", this.onLoad, this);
        store.on("loadexception", this.onLoadError, this);
        this.store = store;
    },

    onDestroy : function(){
        if(this.store){
            this.unbind(this.store);
        }
        SelfPagingToolbarLite.superclass.onDestroy.call(this);
    }
});
Ext.reg('selfpaginglite', SelfPagingToolbarLite);