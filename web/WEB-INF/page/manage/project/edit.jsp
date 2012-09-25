<%-- 
    Document   : edit
    Created on : 2012-9-12, 0:58:53
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>HCI Meeting-Home</title>
        <link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
        <link href="${base}/css/colorbox.css" rel="stylesheet" type="text/css" />
        <link rel = "Shortcut Icon" href="${base}/images/avicon.png" type="image/x-icon"/>
        <script type="text/javascript" src="${base}/js/jquery-1.3.2.js"></script>
        <script type="text/javascript" src="${base}/js/common.js"></script>
        <script type="text/javascript" src="${base}/js/manager/project.js"></script>
        <script src="${base}/js/jquery.colorbox.js"></script>
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
                <h3 class="infoTitle">更新项目</h3>
                <p class="path">
                    <a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                    ><a class="pathAnchor" href="#">管理中心</a>
                    ><a class="pathAnchor" href="#">更新项目</a>
                </p>
                <div class="mainContent">

                    <div class="center">
                        <div class="message">
                            <h2>基本信息</h2>
                            <br/>
                            <div style="width: 110px;overflow-x: hidden; " >
                                <img id="avatar_img" alt="" src="avatar/${project.id }?thumb=true" width=120 height=120>
                                <form action="avatar/${project.id }" id="image_form" name="form" method="post" enctype="multipart/form-data" target="hidden_frame">
                                    <span class="file_input_tip">选择图片 </span> 
                                    <input type="file" id="image_file" name="avatar" style="" onchange="uploadAvatar()" >
                                    <input type="hidden" id="success_code" name="successCode" >
                                    <input type="hidden" id="fail_code" name="failCode" >
                                    <input type="hidden" id="err_code" name="errCode" >
                                    <iframe name="hidden_frame" id="hidden_frame" style="display: none"></iframe>
                                </form>                            
                            </div>
                            <div>
                                <form id="update_project_form">
                                    <input type="hidden" name="project.id" value="${project.id}">
                                    <p>项目名：<input type="text" name="project.name" value="${project.name}" ></p>
                                    <p>部门：
                                        <select name="project.departmentId">
                                            <option value="0">请选择部门</option>
                                            <c:forEach items="${departmentList}" var="department">
                                                <option value="${department.id}" ${project.departmentId== department.id ?"selected=\"selected\"":"" }>${department.name}</option>
                                            </c:forEach>
                                        </select>
                                    </p>
                                    <p>项目状态：
                                        <select name="project.state" disabled="disabled" >
                                            <option value="1" ${project.state== 1 ?"selected=\"selected\"":"" }>开发中</option>
                                            <option value="2" ${project.state== 2 ?"selected=\"selected\"":"" }>结题</option>
                                            <option value="3" ${project.state== 3 ?"selected=\"selected\"":"" }>难产</option>
                                        </select>
                                    </p>
                                    <p>开始日期：<input type="text" name="project.stateDate" value="${project.stateDate}" ></p>

                                    <p>结束日期：<input type="text" name="project.endDate" value="${project.endDate}" style="${project.state == 2 ? "display" : "none"}" ></p>

                                    <p>简介：</p>
                                    <textarea rows="4" cols="20" name="project.detail" style="width: 100%;">${project.detail}</textarea>
                                </form>
                            </div>
                        </div>
                        <button class="button" onclick="updateProject()" >保存</button>
                    </div>
                </div>
            </div>

        </div>
        <%@include file="../../include/footer.jsp" %>
    </body>
</html>
