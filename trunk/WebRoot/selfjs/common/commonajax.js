/*function requestAjax(url, submitData, callbackfunc){
	Ext.Ajax.request({url:url, params:{requesttype : 'ajax'}, jsonData:submitData,
		callback : function(options,success,response){
			if(success){
				var rawObj = Ext.util.JSON.decode(response.responseText);
    			var jsonObj = Ext.util.JSON.decode(rawObj.actionresult);
    			if(jsonObj.result){
    				callbackfunc(jsonObj);
    			}else{
    				Ext.MessageBox.alert('',jsonObj.message);
    			}
			}else{
				
			}
		}
	});
}*/

function requestAjax(url, submitData, callbackfunc, callbackfunc2, callbackfunc3){
	Ext.Ajax.request({url:url, params:{requesttype : 'ajax'}, jsonData:submitData,
		callback : function(options,success,response){
			if(success){
				var rawObj = Ext.util.JSON.decode(response.responseText);
    			var jsonObj = Ext.util.JSON.decode(rawObj.actionresult);
    			if(jsonObj.result){
    				callbackfunc(jsonObj);
    			}else{
    				//Ext.MessageBox.alert('系统提示',jsonObj.message, function(id){
    				if(callbackfunc2 != undefined){
    					callbackfunc2(jsonObj);
    				}else{
    					Ext.MessageBox.alert('系统提示',jsonObj.message);
    				}
    				//});
    			}
			}else{
				if(callbackfunc3 != undefined){
					callbackfunc3();
				}else{
    				Ext.Msg.alert('系统提示','通讯超时!');return;
    			}
			}
		}
	});
}

function jsonStoreRequest(url){
	return new Ext.data.JsonStore({
		fields : ['dictValue','dictValueDesc'],
		url    : url,
		root   : 'field1', 
		autoLoad:true
	});
}

//add by wei.feng 2011-1-4 start
//补字符串
//输入:LorR,左补或者右补,0为左补,1为右补
//    str,需要补位的字符串
//    combo,补位时使用的符号
//    size,补位补足的位数
//返回:补位完成的字符串
function addStrByCombo(LorR,str,combo,size){
    var returnStr = str;
    var strLen = str.length;
    if(LorR==0){//左补
        for(var i=strLen;i<size;i++){
            returnStr = combo+returnStr;
        }
    }else if(LorR==1){//右补
        for(var i=strLen;i<size;i++){
            returnStr = returnStr+combo;
        }
    }
    return returnStr;
}
//add by wei.feng 2011-1-4 end

function queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,sortString){
	var query_obj = conditionPanel.getFields();
	if(sortString != undefined&&sortString != ''){
		query_obj['sortString'] = sortString;
	}
	query_obj['classname'] = classname;
	query_obj['sqlMapNamespace'] = sqlMapNamespace;
	pagequeryObj.queryPage(query_obj);
}

document.onkeydown = function(e){
	var code;   
    if (!e) var e = window.event;   
    if (e.keyCode) code = e.keyCode;   
    else if (e.which) code = e.which;   
    if (((event.keyCode == 8) &&                                                    //BackSpace    
         ((event.srcElement.type != "text" &&    
         event.srcElement.type != "textarea" &&    
         event.srcElement.type != "password") ||    
         event.srcElement.readOnly == true)) ||    
       ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) ||    //CtrlN,CtrlR    
       (event.keyCode == 116)) {                                                   //F5    
        event.keyCode = 0;    
       event.returnValue = false;    
   }
   return true;   
}


Ext.override(Ext.data.Store, {
	sort : function(fieldName,dir){
		var f = this.fields.get(fieldName);
	    if(!f){
	        return false;
	    }
	    if(!dir){
	        if(this.sortInfo && this.sortInfo.field == f.name){ // toggle sort dir
	            dir = (this.sortToggle[f.name] || 'ASC').toggle('ASC', 'DESC');
	        }else{
	            dir = f.sortDir;
	        }
	    }
	    var st = (this.sortToggle) ? this.sortToggle[f.name] : null;
	    var si = (this.sortInfo) ? this.sortInfo : null;
	
	    this.sortToggle[f.name] = dir;
	    this.sortInfo = {field: f.name, direction: dir};
	    if(!this.remoteSort){
	        //this.applySort();
	        this.fireEvent('datachanged', this);
	    }else{
	        if (!this.load(this.lastOptions)) {
	            if (st) {
	                this.sortToggle[f.name] = st;
	            }
	            if (si) {
	                this.sortInfo = si;
	            }
	        }
	    }
	},
	loadRecords : function(o, options, success){
        if (this.isDestroyed === true) {
            return;
        }
        if(!o || success === false){
            if(success !== false){
                this.fireEvent('load', this, [], options);
            }
            if(options.callback){
                options.callback.call(options.scope || this, [], options, false, o);
            }
            return;
        }
        var r = o.records, t = o.totalRecords || r.length;
        if(!options || options.add !== true){
            if(this.pruneModifiedRecords){
                this.modified = [];
            }
            for(var i = 0, len = r.length; i < len; i++){
                r[i].join(this);
            }
            if(this.snapshot){
                this.data = this.snapshot;
                delete this.snapshot;
            }
            this.clearData();
            this.data.addAll(r);
            this.totalLength = t;
            //this.applySort();
            this.fireEvent('datachanged', this);
        }else{
        	alert("this" + this);
            this.totalLength = Math.max(t, this.data.length+r.length);
            alert("commonajax.js");
            this.add(r);
        }
        this.fireEvent('load', this, r, options);
        if(options.callback){
            options.callback.call(options.scope || this, r, options, true);
        }
    }
});

