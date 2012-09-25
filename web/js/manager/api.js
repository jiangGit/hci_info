/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function addApi(){
    var a;
    $.post("add",
        $('#add_api_form').serialize(), 
        function(data){
            if(data.state == "ok"){
                a = new Alert(0,0,false);
                a.show("操作成功",function(){
                    window.location.href=data.id;
                    });
                
            }else{
                a = new Alert();
                a.show(data.message);
            } 
        }, "json");
}

function updateApi(){
    var a;
    $.post($("#apiId").val(),
        $('#update_api_form').serialize(), 
        function(data){
            if(data.state == "ok"){
                a = new Alert();
                a.show("操作成功");   
            }else{
                a = new Alert();
                a.show(data.message);
            } 
        }, "json");
}

function toRemoveApi(){
     var pDiv=$(this).parent();
    var id=pDiv.children("[name='id']").val();
    var note=pDiv.children("[name='note']").val();
    
    var str = "确定移除key ？"
    +"<p>说明：</p>"
    +"<p>"+note+"</p>"
    +"<input type = 'hidden' name = 'id' value = '"+id+"' >";
    $.prompt(str,{
        callback: removeApi,
        buttons:{
            Ok:true,
            Cancel:false
        }
    });
}

function removeApi(v,m,f){
    if(v == false || v == "false"){
        return;
    }
    var id = f.id;
    var a;
    $.post("remove",
    {id:id}, 
    function(data){
        if(data.state == "ok"){
            a = new Alert();
            a.show("移除api成功",function(){
                    location.reload();
                });        
        }else{
            a = new Alert();
            a.show(data.message);
        } 
          
    }, "json");
}
