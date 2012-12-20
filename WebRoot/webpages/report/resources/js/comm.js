var agt = navigator.userAgent.toLowerCase();
var is_ie = ((agt.indexOf("msie") != -1) && (agt.indexOf("opera") == -1));
var is_gecko= (navigator.product == "Gecko");
function getObj(id){ return document.getElementById(id);}
function ietruebody(){	return (document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body;}
function IsElement(id){return document.getElementById(id)!=null ? true : false;}


function PwMenu(){
	this.pid  = null;
	this.obj  = null;
	this.w	  = null;
	this.h	  = null;
	this.t	  = 0;
	this.menu = null;
	this.init();
}

function PwMenu(){
	this.pid  = null;
	this.obj  = null;
	this.w	  = null;
	this.h	  = null;
	this.t	  = 0;
	this.menu = null;
	this.init();
}

PwMenu.prototype = {

	init : function(){
		this.menu = document.createElement('div');
		try{
			document.body.insertBefore(this.menu,document.body.firstChild);
		}catch(e){}
	},

	guide : function(){
		menu_read.menu.innerHTML = '<div style="padding:13px 30px"><img src="'+imgpath+'/loading.gif" align="absbottom" /> 正在加载数据...</div>';
		menu_read.menu.className = 'menu';
		menu_read.menupz(menu_read.obj,1);
	},

	close : function(){
		menu_read.t = setTimeout("closep();",100);
	},

	move : function(e){
		if(is_ie){
			document.body.onselectstart = function(){return false;}
		}
		var e  = is_ie ? window.event : e;
		var o  = menu_read.menu;
		var x  = e.clientX;
		var y  = e.clientY;
		menu_read.w = e.clientX - parseInt(o.offsetLeft);
		menu_read.h = e.clientY - parseInt(o.offsetTop);
		document.onmousemove = menu_read.moving;
		document.onmouseup   = menu_read.moved;
	},

	moving : function(e){
		var e  = is_ie ? window.event : e;
		var x  = e.clientX;
		var y  = e.clientY;
		menu_read.menu.style.left = x - menu_read.w + 'px';
		menu_read.menu.style.top  = y - menu_read.h + 'px';
	},

	moved : function(){
		if(is_ie){
			document.body.onselectstart = function(){return true;}
		}
		document.onmousemove = '';
		document.onmouseup   = '';
	},

	open : function(idName,object,type){
		clearTimeout(menu_read.t);
		if(typeof type == "undefined"){
			type = 1;
		}
		menu_read.menu.innerHTML = getObj(idName).innerHTML;
		menu_read.menu.className = getObj(idName).className;
		menu_read.menupz(object,type);
		if(type!=2){
			getObj(object).onmouseout = function(){
				menu_read.close();
				getObj(object).onmouseout = '';
			}
			menu_read.menu.onmouseout = menu_read.close;
			menu_read.menu.onmouseover = function(){
				clearTimeout(menu_read.t);
			}
		}
	},

	menupz : function(obj,type){
		menu_read.menu.onmouseout = '';
		menu_read.menu.style.display = '';
		menu_read.menu.style.cssText = 'FILTER:Alpha(opacity=95);opacity:0.95;left:-500px;z-index:3000';
		if(typeof obj == 'string'){
			obj = getObj(obj);
		}
		if(obj == null){
			menu_read.menu.style.top  = (ietruebody().clientHeight - menu_read.menu.offsetHeight)/2 + ietruebody().scrollTop + 'px';
			menu_read.menu.style.left = (ietruebody().clientWidth - menu_read.menu.offsetWidth)/2 + 'px';
		} else{
			var top  = findPosY(obj);
			var left = findPosX(obj);
			
			if(top < ietruebody().clientHeight/2 || type>3){
				top += ietruebody().scrollTop + obj.offsetHeight;
			} else{
				top += ietruebody().scrollTop - menu_read.menu.offsetHeight;
			}
			if(left > (ietruebody().clientWidth)*3/5){
				left -= menu_read.menu.offsetWidth - obj.offsetWidth;
			}
			menu_read.menu.style.top  = top  + 'px';
			menu_read.menu.style.left = left + 'px';
		}
	},

	InitMenu : function(){
		function setopen(a,b){
			if(getObj(a)){
				getObj(a).onmouseover = function(){menu_read.open(b,a);}
			}
		}
		for(var i in openmenu)
			setopen(i,openmenu[i]);
	}
}
var menu_read = new PwMenu();

function closep(){
	menu_read.menu.style.display = 'none';
}
function findPosX(obj){
	var curleft = 0;
	if(obj.offsetParent){
		while(obj.offsetParent){
			curleft += obj.offsetLeft
			obj = obj.offsetParent;
		}
	} else if(obj.x){
		curleft += obj.x;
	}
	return curleft - ietruebody().scrollLeft;
}
function findPosY(obj){
	var curtop = 0;
	if(obj.offsetParent){
		while(obj.offsetParent){
			curtop += obj.offsetTop
			obj = obj.offsetParent;
		}
	} else if(obj.y){
		curtop += obj.y;
	}
	return curtop - ietruebody().scrollTop;
}
function in_array(str,a){
	for(var i=0;i<a.length;i++){
		if(str == a[i])	return true;
	}
	return false;
}
function loadjs(path){
	var header = document.getElementsByTagName("head")[0];
	var s = document.createElement("script");
	s.src = path;
	header.appendChild(s);
}
function ck(o){
	var nowtime	 = new Date().getTime();
	o.src = 'ck.php?nowtime=' + nowtime;
}
function keyCodes(e){
	if(menu_read.menu.style.display == '' && e.keyCode==27){
		menu_read.close();
	}
}