<%-- 
    Document   : add
    Created on : 2012-9-10, 14:25:41
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>HCI Meeting-Home</title>
        <link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
        <link href="${base}/css/impromptu.css" rel="Stylesheet" type="text/css" />
        <link rel = "Shortcut Icon" href="${base}/images/avicon.png" type="image/x-icon"/>
        <script type="text/javascript" src="${base}/js/jquery-1.3.2.js"></script>
        <script language="javascript" type="text/javascript" src="${base}/js/impromptu.js" ></script>
        <script type="text/javascript" src="${base}/js/common.js"></script>
        <script type="text/javascript" src="${base}/js/manager/member.js"></script>
        <style>
            .file_input_tip {
                margin-top: 10px;
                width: 80px;
                height: 20px;
                display: block;
                border: 1px #AAAAAA solid;
                -moz-border-radius: 10px;
                -webkit-border-radius: 10px;
                -khtml-border-radius: 10px;
                -border-radius: 10px;
            }

            #image_file {
                width: 80px;
                height: 20px;
                opacity: 0;
                margin-top: -20px;
                margin-bottom: 10px;
            }

        </style>
    </head>

    <body>
        <%@include file="../../include/header.jsp" %>
        <div class="article">
            <%@include file="../../include/manager_left.jsp" %>

            <div class="information">
                <h3 class="infoTitle">更新信息</h3>
                <p class="path">
                    <a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                    ><a class="pathAnchor" href="#">管理中心</a>
                    ><a class="pathAnchor" href="#">更新信息</a>
                </p>
                <div class="mainContent" >  
                    <div class="center">
                        <div class="message">
                            <h2>更新头像</h2>
                            <div style="width: 110px;overflow-x: hidden;" >
                                <img id="avatar_img" alt="" src="${base}/member/avatar/${member.studentId }?thumb=true" width=120 height=120>
                                <form action="avatar/${member.studentId }" id="image_form" name="form" method="post" enctype="multipart/form-data" target="hidden_frame">
                                    <span class="file_input_tip">选择图片 </span> 
                                    <input type="file" id="image_file" name="avatar" style="" onchange="uploadAvatar()" >
                                    <input type="hidden" id="success_code" name="successCode" >
                                    <input type="hidden" id="fail_code" name="failCode" >
                                    <input type="hidden" id="err_code" name="errCode" >
                                    <iframe name="hidden_frame" id="hidden_frame" style="display: none"></iframe>
                                </form>                            
                            </div>
                        </div>         
                        <div class="message">
                            <h2>更新信息</h2>          
                            <div>
                                <form id="update_member_form" method="post" >
                                    <div>
                                        <label>学号：</label> <input type="text" id="studentId" name="member.studentId" value="${member.studentId}" disabled="disabled" >                                      
                                    </div>

                                    <div>
                                        <label>姓名：</label> <input type="text" id="name" name="member.name" value="${member.name}" >
                                    </div>
                                    <div>
                                        <input type="hidden" value="${departmentMember.id }" name="departmentMember.id" >
                                        <label>部门：</label>
                                        <select name="departmentMember.departmentId">
                                            <option value="0">请选择部门</option>
                                            <c:forEach items="${departmentList}" var="department">
                                                <option value="${department.id}" ${departmentMember.departmentId== department.id ?"selected=\"selected\"":"" }>${department.name}</option>
                                            </c:forEach>
                                        </select> <select name="departmentMember.type">
                                            <option value="1" ${departmentMember.type== 1?"selected=\"selected\"":"" }>部门成员</option>
                                            <option value="2" ${departmentMember.type== 2?"selected=\"selected\"":"" }>部门负责人</option>
                                        </select>
                                    </div>
                                    <div>
                                        <label>性别：</label>
                                        男<input type="radio" id="m_sex" name="extend.sex" value="0"  ${extend.sex == 0 ? "checked=\"checked\"" :""} >&nbsp;
                                        女<input type="radio" id="f_sex" name="extend.sex" value="1" ${extend.sex == 1 ? "checked=\"checked\"" :""} >
                                    </div>                                  

                                    <div>
                                        <label>类型：</label> <select id="type" name="member.type">
                                            <option value="3" ${member.type == 3 ? "selected=\"selected\"":"" } >本科生</option>
                                            <option value="1" ${member.type == 1 ? "selected=\"selected\"":"" } >教师</option>
                                            <option value="2" ${member.type == 2 ? "selected=\"selected\"":"" } >研究生</option>
                                        </select>
                                    </div>

                                    <div>
                                        <label>状态：</label> <select id="state" name="member.state">
                                            <option value="3" ${member.state == 3 ? "selected=\"selected\"":"" } >见习生</option>
                                            <option value="1" ${member.state == 1 ? "selected=\"selected\"":"" } >在职</option>                                         
                                            <option value="2" ${member.state == 2 ? "selected=\"selected\"":"" } >已毕业</option>                                          
                                            <option value="4" ${member.state == 4 ? "selected=\"selected\"":"" } >退出</option>
                                        </select>
                                    </div>
                                    <div>
                                        <label>年级：</label> <input id="grade" type="text" name="extend.grade" value="${extend.grade}" >
                                    </div>

                                    <div>
                                        <label>学院：</label> <input id="academy" type="text" name="extend.academy" value="${extend.academy}" >
                                    </div>

                                    <div>
                                        <label>专业：</label> <input id="major" type="text" name="extend.major" value="${extend.major}" >
                                    </div>

                                    <div>
                                        <label>手机：</label> <input id="mobile" type="text" name="extend.mobile" value="${extend.mobile}" >
                                    </div>

                                    <div>
                                        <label>短号：</label> <input id="mobileshort" type="text" name="extend.mobileshort" value="${extend.mobileshort}" >
                                    </div>

                                    <div>
                                        <label>邮箱：</label> <input id="email" type="text" name="extend.email" value="${extend.email}" >
                                    </div>

                                    <div>
                                        <label>生日：</label> <input id="birthday" type="text" name="extend.birthday" value="${extend.birthday}" >
                                    </div>

                                    <div>
                                        <label>个人主页：</label> <input id="homepage" type="text" name="extend.homepage" value="${extend.homepage}" >
                                    </div>

                                    <div>

                                        <input type="hidden" id="memberId"  name="member.id" value="${member.id}" > 
                                        <input class="button" type="button"  onclick="updateMember()" value="更新" >                                       
                                    </div>

                                </form>
                            </div>

                        </div>
                        <div class="message">
                                <h2>重置密码</h2>
                                <button class="button" onclick="toResetPs()" >重置</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../../include/footer.jsp" %>
    </body>
</html>
