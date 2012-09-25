/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function login(){
    var username = $("#username").val();
    var password = $("#password").val();
    var a;
    if(username.length<1 ){
        a = new Alert();
        a.show("请填写用户名");
        return;
    }
    if(username.length<1 ){
        a = new Alert();
        a.show("请填写用密码");
        return;
    }
    password=$.md5(password);
    $.post('login', 
    {
        username:username,
        password:password
    },
    function(data){
        if(data.state=="ok"){
            window.location.href=base;
        }else{
            a = new Alert();
            a.show(data.message);
            return;
        }
    },"json");
    
}

function modifyPassword(){
    var password = $("#password").val();
    var cp=$("#confirmPassword").val();
    var a;
    if(password.length < 5){
        a = new Alert();
        a.show("密码必须大于5为");
        return;
    }
    if(password != cp){
        a = new Alert();
        a.show("两次密码不一致");
        return;
    }
    password=$.md5(password);
    $.post('password', 
    {
        password:password
    },
    function(data){
        if(data.state=="ok"){
            $("#password").val("");
            $("#confirmPassword").val("");
            a = new Alert();
            a.show("修改成功");
        }else{
            a = new Alert();
            a.show(data.message);
            return;
        }
    },"json");
    
}

