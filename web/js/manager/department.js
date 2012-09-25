/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function addDepartment(){
    var a;
    $.post("add",
        $('#add_department_form').serialize(), 
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

function updateDepartment(){
    var a;
    $.post($("#departmentId").val(),
        $('#update_department_form').serialize(), 
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

