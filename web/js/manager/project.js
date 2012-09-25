/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var base ="/hci_official"

function addProject(){
    var a;
    $.post("add",
        $('#add_project_form').serialize(), 
        function(data){
            if(data.state == "ok"){
                a = new Alert();
                a.show("操作成功",function(){
                    window.location.href=data.id;
                });
                
            }else{
                a = new Alert();
                a.show(data.message);
            } 
          
        }, "json");
}

function updateProject(){
    var a;
    $.post($('#projectId').val(),
        $('#update_project_form').serialize(), 
        function(data){
            if(data.state == "ok"){
                a = new Alert();
                a.show("操作成功",function(){
                    
                    });
                
            }else{
                a = new Alert();
                a.show(data.message);
            } 
          
        }, "json");
}

function uploadAvatar(){
    $('#success_code').val("window.parent.uploadSuccess ()");
    $('#fail_code').val("window.parent.uploadFail ()");
    $('#err_code').val("window.parent.uploadFail ()");
    $('#image_form').submit(); 
}

function uploadSuccess (){
    var src = $('#avatar_img').attr("src")
    $('#avatar_img').attr("src", src+"&"+Math.random());
}

function uploadFail(){
    var a = new Alert();
    a.show("更新头像失败");
}


function toAddLeader(){
    $.get(base+"/member/all.json", 
    {},
        function(data){
            var str ="<select id='member_select' name='id'>"
            +"<option label='请选择' value='0'> 请选择</option>";
            if(data.list){
                for(var i in data.list){
                    str +="<option label='"+data.list[i].name + "'value ='"+data.list[i].id +"' > "+data.list[i].name + "</option>";
                }
            }
            str += "</select><br>  "
            +"<p>职责：</p>"
            +"<textarea rows='4' cols='20 'name = 'job' style='width:100%;' ></textarea>";   
            $.prompt(str,{
                callback: addLeader,
                buttons:{
                    Ok:true,
                    Cancel:false
                }
            });
        
        }, "json");
}

function addLeader(v,m,f){
    if(v == true || v == "true"){
        var id = f.id;
        var pid = $("#project_id").val();
        var a;
        $.post(base+"/manage/project/leader/add", 
            "pid="+pid+"&mid="+id+"&job="+f.job,
            function(data){
                if(data.state == "ok"){
                    a = new Alert();
                    a.show("操作成功",function(){
                        window.location.href=base+"/manage/project/members/"+pid;
                    });
                
                }else{
                    a = new Alert();
                    a.show(data.message);
                } 
            }, "json");
    }
}

function toAddMember(){
    $.get(base+"/member/all.json", 
    {},
        function(data){
            var str ="<select id='member_select' name='id'>"
            +"<option label='请选择' value='0'> 请选择</option>";
            if(data.list){
                for(var i in data.list){
                    str +="<option label='"+data.list[i].name + "'value ='"+data.list[i].id +"' > "+data.list[i].name + "</option>";
                }
            }
            str += "</select><br>  "
            +"<p>职责：</p>"
            +"<textarea rows='4' cols='20 'name = 'job' style='width:100%;' ></textarea>";   
            $.prompt(str,{
                callback: addMember,
                buttons:{
                    Ok:true,
                    Cancel:false
                }
            });
        
        }, "json");
}

function addMember(v,m,f){
    if(v == true || v == "true"){
        var id = f.id;
        var pid = $("#project_id").val();
        var a;
        $.post(base+"/manage/project/member/add", 
            "pid="+pid+"&mid="+id+"&job="+f.job,
            function(data){
                if(data.state == "ok"){
                    a = new Alert();
                    a.show("操作成功",function(){
                        window.location.href=base+"/manage/project/members/"+pid;
                    });
                
                }else{
                    a = new Alert();
                    a.show(data.message);
                } 
            }, "json");
    }
}

function toModifyMember(){
    var pDiv=$(this).parent().parent();
    var pmid=pDiv.children("[name='pm_id']").val();
    var mid=pDiv.children("[name='mid']").val();
    var pid=pDiv.children("[name='pid']").val();
    var job=pDiv.children("[name='job']").val();
    $.get(base+"/member/all.json", 
    {},
        function(data){
            var str ="<select id='member_select' name='id'>"
            +"<option label='请选择' value='0'> 请选择</option>";
            if(data.list){
                for(var i in data.list){
                    str +="<option label='"+data.list[i].name + "'value ='"+data.list[i].id +"'"+ (mid==data.list[i].id ? "selected='selected'":"") +" > "+data.list[i].name + "</option>";
                }
            }
            str += "</select><br>  "
            +"<p>职责：</p>"
            +"<textarea rows='4' cols='20 'name = 'job' style='width:100%;' >"+job+"</textarea>"
            +"<input type='hidden' name = 'pmid' value = '"+pmid+"'>"
            +"<input type='hidden' name = 'pid' value = '"+pid+"'>";   
            $.prompt(str,{
                callback: modifyMember,
                buttons:{
                    Ok:true,
                    Cancel:false
                }
            });
        
        }, "json");
}

function modifyMember(v,m,f){
    if(v == true || v == "true"){
        var a;
        $.post(base+"/manage/project/member", 
            "pmid="+f.pmid+"&mid="+f.id+"&job="+f.job,
            function(data){
                if(data.state == "ok"){
                    a = new Alert();
                    a.show("操作成功",function(){
                        window.location.href=base+"/manage/project/members/"+f.pid;
                    });
                
                }else{
                    a = new Alert();
                    a.show(data.message);
                } 
            }, "json");
    }
}

function deleteMember(){
    var pDiv=$(this).parent().parent();
    var pmid=pDiv.children("[name='pm_id']").val();
    var name=pDiv.children("[name='name']").val();
    var str = "确定删除项目成员 ："+name+" ?";
    $.prompt(str,{
        callback: function(v,m,f){
            if(v == false || v == "false"){
                return;
            }
            $.post(base+"/manage/project/member/remove/"+pmid,
            {},
                function(data){
                    if(data.state == "ok"){                    
                        a = new Alert();
                        a.show("操作成功",function(){
                            window.location.href=base+"/manage/project/members/"+$("#project_id").val();;
                        });
                
                    }else{
                        a = new Alert();
                        a.show(data.message);
                    } 
                },"json");
        },
        buttons:{
            Ok:true,
            Cancel:false
        }
    });
}

function toFinishedProject(){
    var pDiv=$(this).parent();
    var id=pDiv.children("[name='id']").val();
    var name=pDiv.children("[name='name']").val();
    
    var str = "确定给项目"+name+"进行结题？"
    +"<input type = 'hidden' name = 'id' value = '"+id+"' >";
    $.prompt(str,{
        callback: finishedProject,
        buttons:{
            Ok:true,
            Cancel:false
        }
    });
}

function finishedProject(v,m,f){
    if(v == false || v == "false"){
        return;
    }
    var id = f.id;
    var a;
    $.post("finish",
    {id:id}, 
    function(data){
        if(data.state == "ok"){
            a = new Alert();
            a.show("项目结题成功",function(){
                    $("#filter").submit();
                });
                
        }else{
            a = new Alert();
            a.show(data.message);
        } 
          
    }, "json");
}

function toRemoveProjct(){
    var pDiv=$(this).parent();
    var id=pDiv.children("[name='id']").val();
    var name=pDiv.children("[name='name']").val();
    
    var str = "确定给项目"+name+"进行删除？"
    +"<input type = 'hidden' name = 'id' value = '"+id+"' >";
    $.prompt(str,{
        callback: removeProject,
        buttons:{
            Ok:true,
            Cancel:false
        }
    });
}

function removeProject(v,m,f){
    if(v == false || v == "false"){
        return;
    }
    var id = f.id;
    var a;
    $.post("delete",
    {id:id}, 
    function(data){
        if(data.state == "ok"){
            a = new Alert();
            a.show("移除项目成功",function(){
                    $("#filter").submit();
                });
                
        }else{
            a = new Alert();
            a.show(data.message);
        } 
          
    }, "json");
}

