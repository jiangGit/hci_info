/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function toAddAdmin(){
    $.get("/hci_official/member/all.json", 
    {},
        function(data){
            var str ="<select id='member_select' name='id'>"
                    +"<option label='请选择' value='0'> 请选择</option>";
             if(data.list){
                 for(var i in data.list){
                     str +="<option label='"+data.list[i].name + "'value ='"+data.list[i].id +"'> "+data.list[i].name + "</option>";
                 }
             }
             str += "</select><br>  ";   
            $.prompt(str,{callback: addAdmin});
        
        }, "json");
}
function addAdmin(v,m,f){
    if(v == true || v == "true"){
        var id = f.id;
        var a;
        $.post("add", 
            "id="+id,
            function(data){
               if(data.state == "ok"){
                a = new Alert();
                a.show("操作成功",function(){
                    window.location.href="/hci_official/manage/admin/list";
                });
                
            }else{
                a = new Alert();
                a.show(data.message);
            } 
            }, "json");
    }
}

function toRemoveAdmin(){
     var pDiv=$(this).parent();
    var id=pDiv.children("[name='id']").val();
    var name=pDiv.children("[name='name']").val();
    
    var str = "确定移除管理员："+name+" ？"
    +"<input type = 'hidden' name = 'id' value = '"+id+"' >";
    $.prompt(str,{
        callback: removeAdmin,
        buttons:{
            Ok:true,
            Cancel:false
        }
    });
}

function removeAdmin(v,m,f){
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
            a.show("移除管理员成功",function(){
                    location.reload();
                });        
        }else{
            a = new Alert();
            a.show(data.message);
        } 
          
    }, "json");
}


