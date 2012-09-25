<%-- 
    Document   : modifyPassword
    Created on : 2012-9-21, 14:10:40
    Author     : Administrator
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>HCI Meeting-Home</title>
        <link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
        <link rel = "Shortcut Icon" href="${base}/images/avicon.png" type="image/x-icon"/>
        <script type="text/javascript" src="${base}/js/jquery-1.3.2.js"></script>
        <script type="text/javascript" src="js/jquery.md5.js"></script>
        <script type="text/javascript" src="${base}/js/common.js"></script>
        <script type="text/javascript" src="${base}/js/index.js"></script>
    </head>

    <body>
        <%@include file="../include/header.jsp" %>
        <div class="article">
            <%@include file="../include/left.jsp" %>

            <div class="information">
                <h3 class="infoTitle">个人中心</h3>
                <p class="path">
                    <a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                    ><a class="pathAnchor" href="#">个人中心</a>
                    ><a class="pathAnchor" href="#">修改密码</a>
                </p>
                <div class="mainContent" >  
                    <div class="center">                        
                        <div class="message">
                            <h2>修改密码</h2>
                            <p>输入密码：<input id="password" type="password" name="password" /></p>
                            <p>确认密码：<input id="confirmPassword" type="password" name="password" /></p>
                            <button class="button" onclick="modifyPassword()" >修改</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../include/footer.jsp" %>
    </body>
</html>