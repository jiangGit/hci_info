function searchInfo(){
    var id = $('#search_id').val();
    var a;
    if(id == ''){
        a = new Alert();
        a.show('请填写学号');
    }
    $.post('checkout/'+$('#search_id').val(), 
    {},
        function(data){
                
            if(data.state != "ok"){
                a = new Alert();
                a.show(data.message);
                return;
            }
                
            var member = data.member;
            var extend = data.extend;
            var isExist = data.isExist;               
            if(isExist){
                var a = new Alert();
                a.show("该成员已存在", function(){
                    window.location.href=data.id;
                });
                                     
            }
            $('#studentId').val(member.studentId);
            $('#name').val(member.name);
            if(extend.sex == 0){
                $('#m_sex').attr("checked", "checked");
            }else{
                $('#f_sex').attr("checked", "checked");
            }
            $('#type').val(member.type);
            $('#state').val(member.state);
            $('#grade').val(extend.grade);
            $('#academy').val(extend.academy);
            $('#major').val(extend.major);
            $('#mobile').val(extend.mobile);
            $('#mobileshort').val(extend.mobileshort);
            $('#email').val(extend.email);
            $('#birthday').val(extend.birthday);
            $('#homepage').val(extend.homepage);
            $('#familyaddress').val(extend.familyaddress);
            $('#nativeplace').val(extend.nativeplace);
            $('#folk').val(extend.folk);
            $('#cardId').val(extend.cardId);
                
            $('#first_step').hide();
            $('#second_step').show();
                
              
        },"json");
}

function nextStep(){
    $('#first_step').hide();
    $('#second_step').show();
}

function addMember(){
    var a ;
    $.ajax({
        url:"add",
        type: 'post',
        dataType : 'json',
        data: $('#add_member_form').serialize(),
        success:function(data){
            if(data.state == "ok"){
                a = new Alert();
                a.show("操作成功",function(){
                    window.location.href=data.id;
                });
                
            }else{
                a = new Alert();
                a.show(data.message);
            } 
        }
    });

}

function updateMember(){
    var a;
    $.post($('#memberId').val(),
        $('#update_member_form').serialize(), 
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


function toResetPs(){
    var str = "确定给"+$("#name").val()+"重置密码？";
    $.prompt(str,{
        callback: resetPs,
        buttons:{
            Ok:true,
            Cancel:false
        }
    });
}

function resetPs(v,m,f){
    if(v == false || v == "false"){
        return;
    }
    var a;
    $.post("password",
    {
        id:$("#memberId").val()
        }, 
    function(data){
        if(data.state == "ok"){
            a = new Alert();
            a.show("重置密码成功",function(){
                    
                });
                
        }else{
            a = new Alert();
            a.show(data.message);
        } 
          
    }, "json");
}