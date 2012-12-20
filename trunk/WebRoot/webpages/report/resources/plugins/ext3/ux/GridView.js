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