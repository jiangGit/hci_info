<%-- 
    Document   : list
    Created on : 2012-9-13, 0:55:17
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
        <link href="${base}/css/impromptu.css" rel="Stylesheet" type="text/css" />
        <link rel = "Shortcut Icon" href="${base}/images/avicon.png" type="image/x-icon"/>
        <script type="text/javascript" src="${base}/js/jquery-1.3.2.js"></script>
        <script type="text/javascript" src="${base}/js/common.js"></script>
        <script language="javascript" type="text/javascript" src="${base}/js/impromptu.js" ></script>
        <script type="text/javascript" src="${base}/js/manager/admin.js"></script>
        <script src="${base}/js/jquery.colorbox.js"></script>


    </head>

    <body>
        <%@include file="../../include/header.jsp" %>
        <div class="article">
            <%@include file="../../include/manager_left.jsp" %>
            <div class="information">
                <h3 class="infoTitle">管理员列表</h3>
                <p class="path">
                    <a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                    ><a class="pathAnchor" href="#">管理中心</a>
                    ><a class="pathAnchor" href="#">管理员列表</a>
                </p>
                <div class="mainContent">
                    <div class="center">
                        <c:forEach items="${obj.list}" var="m">
                            <div class="person manage">
                                <a href="${m.id}">
                                    <img src="${base}/member/avatar/${m.studentId }?thumb=true"/>
                                    <p>${m.name}</p>
                                </a>
                                <input type="hidden" name="id" value="${m.id }">
                                <input type="hidden" name="name" value="${m.name }">
                                <a class="exit" href="javascript:void(0);">X</a>
                            </div>
                        </c:forEach>

                        <div class="clear"></div>
                         <button class="button" onclick="toAddAdmin()"  >添加</button>                        
                    </div>           
                </div>
               
            </div>
        </div>
        <%@include file="../../include/footer.jsp" %>
        <script>
            $(function(){
                $(".exit").click(toRemoveAdmin);
            });
        </script>
    </body>
</html>
