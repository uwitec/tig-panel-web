Ext.grid.DictionaryColumn = function(config){   
    Ext.apply(this, config);   
    if(!this.id){   
        this.id = Ext.id();   
    }   
    this.renderer = this.renderer.createDelegate(this);   
    this.valueField = this.valueField || 'id';   
    this.displayField = this.displayField || 'name';   
    this.mode = this.mode || 'remote';   
    this.editable = this.editable || false;   
       
    if (this.editable) {   
        this.editor = new Ext.form.ComboBox({   
            displayField:this.displayField,   
            valueField: this.valueField,   
            store: this.store,   
            mode: this.mode,   
            typeAhead: true,   
            triggerAction: 'all',   
            listClass: 'x-combo-list-small'  
        });   
    }   
    this.store.load();
};   
  
Ext.grid.DictionaryColumn.prototype ={   
    renderer : function(v, p, record){   
        var index = this.store.find(this.valueField, v); 
        if(index==-1)return v;
        return this.store.getAt(index).get(this.displayField);   
    }   
};  


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
