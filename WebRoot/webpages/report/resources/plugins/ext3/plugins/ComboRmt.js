

/*!
 * Ext JS Library 3.0.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
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

