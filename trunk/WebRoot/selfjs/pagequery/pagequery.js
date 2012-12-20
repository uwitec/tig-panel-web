PageQuery = function(whetherpage, divid, url, fields, colM, pageToolText, columnSortFunc){
	
	this.divid = divid;
	this.url = url;
	this.fields = fields;
	//alert(colM.length);//
	for(var i=1;i<colM.length;i=i+1){
		var col = colM[i];
		if(col.translateField !== undefined){
			col.renderer = this.translatecolumn;
			
		}else{
			if(col.renderType !== undefined){
				col.renderer = this.rendertype;
			}
		}
		col.sortable = true;
	}
	this.rsM = new Ext.grid.RowSelectionModel({
        singleSelect: false
    });
	//colM[0] = this.rsM;
	this.colMData = colM;
	var columnModel = new Ext.grid.ColumnModel(colM);
	
	this.dataStore = new Ext.data.JsonStore({fields : fields});
	
	pageToolText = pageToolText.replace(/\<\$/g,'{');
	pageToolText = pageToolText.replace(/\$\>/g,'}');
	var pagingToolParamObj = Ext.util.JSON.decode(pageToolText);
	pagingToolParamObj.displayMsg = pagingToolParamObj.displayMsg.replace(/\<\%\$/g,'{');
	pagingToolParamObj.displayMsg = pagingToolParamObj.displayMsg.replace(/\$\%\>/g,'}');
	this.selfPagingTool = new SelfPagingToolbar({
	
		url : this.url,
		displayMsg : pagingToolParamObj['displayMsg'],
	    emptyMsg : pagingToolParamObj['emptyMsg'],
	    beforePageText : pagingToolParamObj['beforePageText'],
	    afterPageText : pagingToolParamObj['afterPageText'],
	    firstText : pagingToolParamObj['firstText'],
	    prevText : pagingToolParamObj['prevText'],
	    nextText : pagingToolParamObj['nextText'],
	    lastText : pagingToolParamObj['lastText'],
	    pageData : pagingToolParamObj['pageData'],
	    whetherpage : whetherpage,
	    addRecordText : pagingToolParamObj['addRecordText'],
	    deleteRecordText : pagingToolParamObj['deleteRecordText'],
		store : this.dataStore,
		displayInfo : true,
		parentComp  : this,
		//buttonsComp : buttons,
		jsonData : {}
		//,onButtonClicked : buttonClickFunction
	});
	var div = document.getElementById(this.divid);
	this.pagingGrid = new Ext.grid.GridPanel({
		region : 'center',
		bbar   : this.selfPagingTool,
	    cm     : columnModel,
		sm     : this.rsM,
		store  : this.dataStore,
		renderTo:this.divid,
		stripeRows : true
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
	
	this.buildRowDtl();
	this.pagingGrid.addListener('rowdblclick',this.showRowDtl);
	
	if(PageQuery.objects === undefined){
		PageQuery.objects = new Array();
	}
	PageQuery.objects.push(this);
}

PageQuery.TYPE = {};
PageQuery.TYPE.MONEY = 1;
PageQuery.TYPE.DATE = 2;
PageQuery.TYPE.TIME = 3;
PageQuery.TYPE.DATETIME = 4;
PageQuery.TYPE.MINUTE =5;
PageQuery.TYPE.OTHERMONEY =6;
PageQuery.TYPE.TJFHTXNMONEY =7;
PageQuery.TYPE.TJSVTXNMONEY =8;
PageQuery.TYPE.OTHERACCMONEY=9;



PageQuery.prototype.getSelectedObjects = function(fields){
	var records = this.rsM.getSelections();
	if(records.length === 0){
		return undefined;
	}else{
		//return records;
		var submitdata = new Array();
		for(var i=0;i<records.length;i=i+1){
			var data = {};
			for(var j=0; j<fields.length;j=j+1){
				data[fields[j]] = records[i].get(fields[j]);
			}
			submitdata.push(data);
		}
		return submitdata;
	}
}

PageQuery.prototype.getSelectedRecords = function(){
	var records = this.rsM.getSelections();
	if(records.length === 0){
		return undefined;
	}else{
		return records;
	}
}

PageQuery.prototype.translatecolumn = function(value, metadata, record, rowIndex, colIndex, store){
	if(value === null){
		return null;
	}
	var obj;
	for(var i=0;i<PageQuery.objects.length;i=i+1){
		if(PageQuery.objects[i].dataStore === store){
			obj = PageQuery.objects[i];
		}
	}
	var colm = obj.colMData;
	var col = colm[colIndex];
	return (record.get(col.translateField) === '') ? record.get(col.dataIndex) : record.get(col.translateField);
}

PageQuery.prototype.rendertype = function(value, metadata, record, rowIndex, colIndex, store){
	if(value === null){
		return null;
	}
	var obj;
	for(var i=0;i<PageQuery.objects.length;i=i+1){
		if(PageQuery.objects[i].dataStore === store){
			obj = PageQuery.objects[i];
		}
	}
	var colm = obj.colMData;
	var col = colm[colIndex];
	if(col.renderType === PageQuery.TYPE.MONEY){
	    if(value == 0){
			return (value / 100);
	    }else{
			return (value / 100).toFixed(2);
		}
	}else if(col.renderType === PageQuery.TYPE.DATE){
		var str = value.toString();
		// modify by han 2010-08-08 begin
		//return str.substring(0,4) + '-' + str.substring(4,6) + '-' + str.substring(6,8);
		var tmpStr = str.substring(0,4);
		if (str.substring(4,6) !== "")
		{
			tmpStr += '-' + str.substring(4,6);
			if (str.substring(6,8) !== "")
			{
				tmpStr += '-' + str.substring(6,8);
			}
		}
		return tmpStr;
		// modify by han 2010-08-08 end
	}else if(col.renderType === PageQuery.TYPE.TIME){
		var str = value.toString();
		while(str.length < 6){
			str = '0' + str;
		}
		return str.substring(0,2) + ':' + str.substring(2,4) + ':' + str.substring(4,6);
	}else if(col.renderType === PageQuery.TYPE.DATETIME){
	    if(value==undefined||value==""){
	        return "";
	    }
		var str = value.toString();
		// modify by han 2010-08-08 begin
		//return str.substring(0,4) + '-' + str.substring(4,6) + '-' + str.substring(6,8) + ' ' + str.substring(8,10) + ':' + str.substring(10,12) + ':' + str.substring(12,14);
		var tmpStr = str.substring(0,4);
		if (str.substring(4,6) !== "")
		{
			tmpStr += '-' + str.substring(4,6);
			if (str.substring(6,8) !== "")
			{
				tmpStr += '-' + str.substring(6,8);
				if (str.substring(8,10) !== "")
				{
					tmpStr += ' ' + str.substring(8,10);
					if (str.substring(10,12) !== "")
					{
						tmpStr +=  ':' + str.substring(10,12);
						if (str.substring(12,14) !== "")
						{
							tmpStr += ':' + str.substring(12,14);
						}
					}
				
				}
			}
		}
		return tmpStr;
		// modify by han 2010-08-08 end
	}else if(col.renderType === PageQuery.TYPE.MINUTE){
		return (value / 60).toString();
	}else if(col.renderType === PageQuery.TYPE.OTHERMONEY){
	//modify by gong 2010-08-10 begin
		if(record.get('dtlinntype').toString() == '2064' || 
		   record.get('dtlinntype').toString() == '2065' ||
		   record.get('dtlinntype').toString() == '2452' ||
		   record.get('dtlinntype').toString() == '2453' ||
		   record.get('dtlinntype').toString() == '2454' ||
		   record.get('dtlinntype').toString() == '8452' ||
		   record.get('dtlinntype').toString() == '8453' ||
		   record.get('dtlinntype').toString() == '8454' ){
			return value;
		}else{
		   		if(value == 0){
					return (value / 100);
	  		 	 }else{
				return (value / 100).toFixed(2);
				}
		}
	}else if(col.renderType === PageQuery.TYPE.TJFHTXNMONEY){
		if(record.get('tjrlinntype').toString() == '2064' || 
		   record.get('tjrlinntype').toString() == '2065'   ||
		   record.get('tjrlinntype').toString() == '2066' ||
		   record.get('tjrlinntype').toString() == '2452' ||
		   record.get('tjrlinntype').toString() == '2453' ||
		   record.get('tjrlinntype').toString() == '2454' ||
		   record.get('tjrlinntype').toString() == '8452' ||
		   record.get('tjrlinntype').toString() == '8453' ||
		   record.get('tjrlinntype').toString() == '8454' ){
			return value;
		}else{
		   		if(value == 0){
					return (value / 100);
	  		 	 }else{
				return (value / 100).toFixed(2);
				}
		}
	}else if(col.renderType === PageQuery.TYPE.TJSVTXNMONEY){
		if(record.get('tjrlinntype').toString() == '2064' || 
		   record.get('tjrlinntype').toString() == '2065' ||
		   record.get('tjrlinntype').toString() == '2066' ||
		   record.get('tjrlinntype').toString() == '2452' ||
		   record.get('tjrlinntype').toString() == '2453' ||
		   record.get('tjrlinntype').toString() == '2454' ||
		   record.get('tjrlinntype').toString() == '8452' ||
		   record.get('tjrlinntype').toString() == '8453' ||
		   record.get('tjrlinntype').toString() == '8454' ){
			return value;
		}else{
		   		if(value == 0){
					return (value / 100);
	  		 	 }else{
				return (value / 100).toFixed(2);
				}
		}
	}else if(col.renderType === PageQuery.TYPE.OTHERACCMONEY){
	//modify by gong 2010-08-17 begin
		if(record.get('crdacctype').toString() === '4'){
				if(value == 0){
					return (value / 100);
	  		 	 }else{
				return (value / 100).toFixed(2);
				}
		}else{
		  return value;
	}
	}
	return '';
}

PageQuery.prototype.queryPage = function(query_obj) {
	this.submit_obj = query_obj;
	this.selfPagingTool.resetPageInfo();
	this.selfPagingTool.doLoad(1);
}

PageQuery.prototype.setData = function(data){
	this.dataStore.loadData(data,false);
}

PageQuery.prototype.reset = function(data){
	this.selfPagingTool.resetPageInfo();
	this.dataStore.loadData({},false);
}


SelfPagingToolbar = Ext.extend(Ext.Toolbar, {
    
    //pageSize: 20,
    displayMsg : '正在显示第{0} - {1}条记录,共{2}条记录',
    emptyMsg : '对不起，没有您需要的数据',
    beforePageText : "第",
    afterPageText : "页",
    firstText : "第一页",
    prevText : "上一页",
    nextText : "下一页",
    lastText : "最后一页",
    refreshText : "Refresh",
    paramNames : {start: 'start', limit: 'limit',delareFirstEndPage:'delareFirstEndPage', total: 'total',rowStart:'rowStart',endInPage:'endInPage',rowEnd:'rowEnd',totCount: 'totCount',pageCount:'pageCount',diffSecond: 'diffSecond', resultList:'field1', resultObject:'field2'},
    pageData   : [[10,'每页10条记录'],[15,'每页15条记录'],[20,'每页20条记录']],
    jsonData   : {},
    addRecordText : '',
    deleteRecordText : '',
    url        : '',
    whetherpage : true,
    parentComp  : {},
    buttonsComp : [],
    // private
    initComponent : function(){
        this.addEvents('change', 'beforechange');
        SelfPagingToolbar.superclass.initComponent.call(this);
        this.bind(this.store);
        var tempPageData = new Array();
        for(var i=0;i<this.pageData.length; i=i+1){
        	if(this.whetherpage){
        		if(this.pageData[i][0] !== 0){
        			tempPageData.push(this.pageData[i]);
        		}
        	}else{
        		if(this.pageData[i][0] === 0){
        			tempPageData.push(this.pageData[i]);
        		}
        	}
        }
        this.pageData = tempPageData;
        this.pageDataStore = new Ext.data.SimpleStore({
        	fields : [{name:'value',type:'int'},{name:'name'}],
        	data   : this.pageData
        });
    },

    // private
    onRender : function(ct, position){
        SelfPagingToolbar.superclass.onRender.call(this, ct, position);
        this.first = this.addButton({
            tooltip: this.firstText,
            iconCls: "x-tbar-page-first",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["first"])
        });
        this.prev = this.addButton({
            tooltip: this.prevText,
            iconCls: "x-tbar-page-prev",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["prev"])
        });
        this.addSeparator();
        this.add(this.beforePageText);
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
        this.field.setWidth(50);
        this.addField(this.field);
        this.add(this.afterPageText);
        this.addSeparator();
        this.next = this.addButton({
            tooltip: this.nextText,
            iconCls: "x-tbar-page-next",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["next"])
        });
        this.last = this.addButton({
            tooltip: this.lastText,
            iconCls: "x-tbar-page-last",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["last"])
        });
        this.addSeparator();
        this.pageCombo = new Ext.form.ComboBox({
        	store :  this.pageDataStore,
        	displayField:'name',
        	valueField:'value',
        	value : this.pageData[0][0],
        	mode:'local',
        	editable : false,
        	triggerAction:'all',
        	width : 120
        });
        this.pageCombo.on('change',this.onComboChanged,this);
        if(!this.whetherpage){
        	this.pageCombo.disable();
        }
        this.addField(this.pageCombo);
        
        if(this.buttonsComp !== undefined){
        	for(var i=0;i<this.buttonsComp.length;i=i+1){
        		this.addSeparator();
        		var btnconfig = this.buttonsComp[i];
        		btnconfig.handler = this.onClick.createDelegate(this, ["buttonClick",i+1]);
        		this.addButton(btnconfig);
        	}
        }
        
        this.jsonData[this.paramNames.start] = 1;
        this.jsonData[this.paramNames.limit] = this.pageData[0][0];

        if(this.displayInfo){
            this.displayEl = Ext.fly(this.el.dom).createChild({cls:'x-paging-info'});
        }
        if(this.dsLoaded){
            this.onLoad.apply(this, this.dsLoaded);
        }
    },
    
    onComboChanged : function(field,newvalue,oldvalue){
    	this.jsonData[this.paramNames.limit] = newvalue;
    	this.doLoad(1);
    },

    // private
    updateInfo : function(){
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
        
        //if(this.jsonData[this.paramNames.total] == 0){
        //	var storeCount = this.store.getTotalCount(); 
        //	//alert(storeCount+' '+this.jsonData[this.paramNames.limit]);
        //	if(storeCount < this.jsonData[this.paramNames.limit]){
        //		this.next.setDisabled(true);
        //		this.last.setDisabled(true);
        //	}else{
        //		this.next.setDisabled(false);
        //		this.last.setDisabled(false);
        //	}
        //}else{
        //	this.next.setDisabled(true);
        //	this.last.setDisabled(true);
        //}
        
        //alert(this.jsonData[this.paramNames.total]);
        if(this.jsonData[this.paramNames.delareFirstEndPage] == 1){//是首页单不是末页
            this.first.setDisabled(true);
        	this.prev.setDisabled(true);
        	this.next.setDisabled(false);
        	this.last.setDisabled(false);
        }else if(this.jsonData[this.paramNames.delareFirstEndPage] == 2){//是首页又是末页
            this.first.setDisabled(true);
        	this.prev.setDisabled(true);
        	this.next.setDisabled(true);
        	this.last.setDisabled(true);
        }else if(this.jsonData[this.paramNames.delareFirstEndPage] == 0){//不是首页不是末页
            this.first.setDisabled(false);
        	this.prev.setDisabled(false);
        	this.next.setDisabled(false);
        	this.last.setDisabled(false);
        }else{//是末页不是首页,值为3或者页数的值
            this.first.setDisabled(false);
        	this.prev.setDisabled(false);
        	this.next.setDisabled(true);
        	this.last.setDisabled(true);
        }
        
        this.updateInfo();
        //this.fireEvent('change', this, d);
        this.fireEvent('change', this);
        
        if(this.displayEl){
            var count = this.store.getCount();
            var startrow = this.jsonData[this.paramNames.rowStart];
            var endrow   = this.jsonData[this.paramNames.rowEnd];
        	var totCount = this.jsonData[this.paramNames.totCount];
        	var pageCount = this.jsonData[this.paramNames.pageCount];
        	var diffSecond = this.jsonData[this.paramNames.diffSecond];
        	var endInPage = this.jsonData[this.paramNames.endInPage];
        	startrow = startrow;
            var msg = count == 0 ?
                this.emptyMsg :
                String.format(
                    this.displayMsg,
                    startrow, endInPage,pageCount,totCount,diffSecond);
            this.displayEl.update(msg);
        }
    },

    // private
    onLoadError : function(){
        if(!this.rendered){
            return;
        }
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
        this.field.el.dom.value = this.jsonData[this.paramNames.start];
    },

    // private
    onPagingKeydown : function(field,e){
        var k = e.getKey();
        if (k == e.RETURN) {
            e.stopEvent();
            var newvalue = this.field.el.dom.value;
            if(newvalue === '' || newvalue.search(/[^0-9]/) >= 0){
            	this.field.el.dom.value = this.jsonData[this.paramNames.start];
            }else{
            	var tmp = newvalue.toString().replace(/0*/,'');
            	this.doLoad(parseInt(tmp));
            }
        }
    },

    // private
    beforeLoad : function(){},

    // private
    doLoad : function(start){
    	this.jsonData[this.paramNames.start] = start;
		this.parentComp.submit_obj[this.paramNames.start] = this.jsonData[this.paramNames.start];
		this.parentComp.submit_obj[this.paramNames.limit] = this.jsonData[this.paramNames.limit];
		var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"请稍等...", removeMask:true});
		myMask.show();
        Ext.Ajax.request({
			url : this.url, jsonData : this.parentComp.submit_obj, scope:this, params:{requesttype:'ajax'},
			callback : function(options, success, response){
        		if(success){
        			var rawObj = Ext.util.JSON.decode(response.responseText);
        			var jsonObj = Ext.util.JSON.decode(rawObj.actionresult);
        			if(jsonObj.result){
        				var resultList = jsonObj[this.paramNames.resultList];
        				var resultObject = jsonObj[this.paramNames.resultObject];
        				this.jsonData[this.paramNames.total] = resultObject[this.paramNames.total];
        				this.jsonData[this.paramNames.totCount] = resultObject[this.paramNames.totCount];
        				this.jsonData[this.paramNames.rowStart] = resultObject[this.paramNames.rowStart];
        				this.jsonData[this.paramNames.rowEnd] = resultObject[this.paramNames.rowEnd];
        				this.jsonData[this.paramNames.pageCount] = resultObject[this.paramNames.pageCount];
        				this.jsonData[this.paramNames.diffSecond] = resultObject[this.paramNames.diffSecond];
        				this.jsonData[this.paramNames.endInPage] = resultObject[this.paramNames.endInPage];
        				this.jsonData[this.paramNames.delareFirstEndPage] = resultObject[this.paramNames.delareFirstEndPage];
        				this.store.loadData(resultList,false);
        				this.field.enable();
        				myMask.hide();
        			}else{
        				myMask.hide();
        				Ext.MessageBox.alert('',jsonObj.message);
        			}
        		}else{//ajax error
        			myMask.hide();
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
            case "buttonClick":
            	this.onButtonClicked(num);
            break;
        }
    },
    
    resetPageInfo : function(){
    	this.field.el.dom.value = 1;
    },
    
    onButtonClicked : function(which){
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
        SelfPagingToolbar.superclass.onDestroy.call(this);
    }
});
Ext.reg('selfpaging', SelfPagingToolbar);

PageQuery.prototype.buildRowDtl = function(){
	winDtl = new Ext.Window({
        title:'详细信息',
        layout:'fit',
        width:650,
        height:400,
        closeAction:'hide',
        plain: true,    
        items:new Ext.Panel({
              el: 'row-win',
              id:'row-win-panel',
              autoScroll:true,
              border:false
    	}),
        buttons: [{
            text: '关闭',
            handler: function(){
        		winDtl.hide();
            }
        }]
    });
	
	var table="<table cellspacing='1' cellpadding='2' class='tablemng' >";
	var count=this.pagingGrid.getColumnModel().getColumnCount();
	for(var i=1;i<count/2;i=i+1){
		table+="<tr>";
		table+="<th width='140' nowrap align='right'>"+this.pagingGrid.getColumnModel().getColumnHeader(2*i-1)+"</th>";
		table+="<td width='140' id='"+this.pagingGrid.getColumnModel().getDataIndex(2*i-1)+"_row' align='left' ></td>";
		table+="<th width='140' nowrap align='right'>"+this.pagingGrid.getColumnModel().getColumnHeader(2*i)+"</th>";
		table+="<td width='140' id='"+this.pagingGrid.getColumnModel().getDataIndex(2*i)+"_row' align='left' ></td>";
		table+="</tr>";
	}
	if(count%2!=1){
		table+="<tr>";
		table+="<th width='140' nowrap align='right'>"+this.pagingGrid.getColumnModel().getColumnHeader(count-1)+"</th>";
		table+="<td width='140' id='"+this.pagingGrid.getColumnModel().getDataIndex(count-1)+"_row' align='left' colspan=3 ></td>";
		table+="</tr>";
	}
	table+="</table>";
	document.getElementById('row-win').innerHTML=table;
}



PageQuery.prototype.showRowDtl = function(pagingGrid,rowIndex,e){
	winDtl.show();
	for(var i=1;i<pagingGrid.getColumnModel().getColumnCount();i=i+1){
		var renderer=pagingGrid.getColumnModel().getRenderer(i);
		var value=pagingGrid.getStore().getAt(rowIndex).get(pagingGrid.getColumnModel().getDataIndex(i));
		var record=pagingGrid.getStore().getAt(rowIndex);
		var colIndex=i;
		var store = pagingGrid.getStore();
		var showVar = renderer ? renderer(value, '', record, '', colIndex, store) : value;
		if(showVar === null || showVar == undefined){
			showVar = '';
		}else{
			showVar = showVar;
		}
		
		//document.getElementById(pagingGrid.getColumnModel().getDataIndex(i)+"_row").innerHTML=renderer(value, '', record, '', colIndex, store);
		//document.getElementById(grid.getColumnModel().getDataIndex(i)+"_row").innerHTML=renderer ? renderer(value,{},{}) : value;
		document.getElementById(pagingGrid.getColumnModel().getDataIndex(i)+"_row").innerHTML=showVar;
		
		
	}
}
