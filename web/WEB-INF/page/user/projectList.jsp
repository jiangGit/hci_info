
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
    </head>

    <body>
        <%@include file="../include/header.jsp" %>
        <div class="article">
            <%@include file="../include/left.jsp" %>
            <div class="information">
                <h3 class="infoTitle">项目列表</h3>
                <p class="path">
                    <a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                    ><a class="pathAnchor" href="#">个人中心</a>
                    ><a class="pathAnchor" href="#">项目列表</a>
                </p>
               
                <div class="mainContent">
                    <div class="center">
                        <c:forEach items="${obj.list}" var="project" varStatus="status">
                            <div class="project">
                                <img width="100px" src="${base}/project/avatar/${project.id }?thumb=true" />
                                <div class="list">
                                    <p>项目名字：${project.name}</p>
                                    <p>项目负责人：
                                        <c:forEach items="${project.projectMembers}" var="pm" varStatus="status">
                                            <c:if test="${pm.type == 2}">
                                                <font>${pm.member.name}</font>
                                            </c:if>                                           
                                        </c:forEach>
                                    </p>
                                    <p>项目成员：
                                        <c:forEach items="${project.projectMembers}" var="pm" varStatus="status">
                                            <c:if test="${pm.type == 1}">
                                                <font>${pm.member.name}</font>
                                            </c:if>                                           
                                        </c:forEach>
                                    </p>
                                </div>
                                <div class="controls">
                                    <a href="${base}/project/${project.id}">项目详情</a> 
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

        </div>
        <%@include file="../include/footer.jsp" %>
    </body>
</html>
