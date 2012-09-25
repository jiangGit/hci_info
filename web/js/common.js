/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function Alert(width,height,autoClose,time){
    this.width=width||250;
    this.height=height||80;
    if( (typeof autoClose)=="boolean" ){
        this.isAutoClose=autoClose;
    }else{
        this.isAutoClose=true;
    }
    this.AlertDiv=null;
    this.closeButton=null;
    this.time=time||2000;
}

Alert.prototype.show=function(msg,callback){
    var self=this;
    this.create(msg);
    if(this.isAutoClose){
        window.setTimeout(function(){
            self.close(callback);
        },this.time);
    }
    this.closeButton.onclick=function(){
        self.close(callback);
    }
}

Alert.prototype.create=function(msg){
    var Obj=document.createElement("div");
    Obj.setAttribute("class","alertDiv");
    
    Obj.style.position="fixed";
    Obj.style.width=this.width+"px";
    Obj.style.height=this.height+"px"
    Obj.style.display="block";
    Obj.style.top="50%";
    Obj.style.left = "50%";
    Obj.style.marginLeft =-this.width/2+ "px";
    Obj.style.marginTop =-this.height/2+ "px";
    Obj.style.zIndex=300002;

    document.body.appendChild(Obj);
    this.AlertDiv=Obj;

    this.creatCloseButton(this);

    var contentDiv=document.createElement("div");
    var innerHtml="<div class='alertContent'>"+msg+"</div>";
    contentDiv.innerHTML = innerHtml;
    this.AlertDiv.appendChild(contentDiv);
  
}

Alert.prototype.creatCloseButton=function(){
    var closeDiv=document.createElement("div");
    closeDiv.style.position="relative";
    closeDiv.style.zIndex=1;
    var innerHtml="<div class='alertClose' ></div>";
    closeDiv.innerHTML = innerHtml;
    this.AlertDiv.appendChild(closeDiv);
    this.closeButton=closeDiv;
   
}

Alert.prototype.close=function(callback){
    if(this.AlertDiv!=null){
        document.body.removeChild(this.AlertDiv);
        this.AlertDiv=null;
    }
    if(callback && typeof callback == "function"){
        callback();
    }
}
