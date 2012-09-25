<%-- 
    Document   : edit
    Created on : 2012-9-12, 2:36:29
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
        <script type="text/javascript" src="${base}/js/manager/department.js"></script>
        <script src="${base}/js/jquery.colorbox.js"></script>


    </head>

    <body>
        <%@include file="../../include/header.jsp" %>
        <div class="article">
            <%@include file="../../include/manager_left.jsp" %>
            <div class="information">
                <h3 class="infoTitle">更新部门</h3>
                <p class="path">
                    <a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                    ><a class="pathAnchor" href="#">管理中心</a>
                    ><a class="pathAnchor" href="#">更新部门</a>
                </p>
                <div class="mainContent">      		
                    <div class="center">
                        <div class="message">
                            <h2>基本信息</h2>
                            <form id="update_department_form">
                                <input id="departmentId" type="hidden" name="department.id" value="${department.id}">
                                <p>部门名：<input type="text" name="department.name" value="${department.name}" ></p>                            
                            <p>简介：</p>
                            <textarea rows="4" cols="20" name="department.detail" style="width: 100%;" >${department.detail}</textarea>
                            </form>
                        </div>
                        <button class="button" onclick="updateDepartment()" >更新</button>
                    </div>
                </div>

            </div>
            
        </div>
              <%@include file="../../include/footer.jsp" %>
    </body>
</html>
