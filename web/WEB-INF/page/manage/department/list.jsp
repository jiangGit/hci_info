<%-- 
    Document   : list
    Created on : 2012-9-12, 2:36:39
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
                <h3 class="infoTitle">部门列表</h3>
                <p class="path">
                    <a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                    ><a class="pathAnchor" href="#">管理中心</a>
                    ><a class="pathAnchor" href="#">部门列表</a>
                </p>

                <div class="mainContent">
                    <div class="center">
                        <c:forEach items="${obj.list}" var="department" varStatus="status">
                            <div class="project">
                                <div class="list">
                                    <p>部门名称：${department.name}</p>
                                    <p>部门负责人：XX</p>
                                    <p>简介：${department.detail}</p>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </c:forEach>
                        <a  href="${base}/manage/department/add">
                            <button class="button">添加</button>
                        </a>
                    </div>
                </div>
            </div>

        </div>
        <%@include file="../../include/footer.jsp" %>
    </body>
</html>
