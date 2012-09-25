<%-- 
    Document   : add
    Created on : 2012-9-24, 13:29:43
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
        <script type="text/javascript" src="${base}/js/manager/api.js"></script>


    </head>

    <body>
        <%@include file="../../include/header.jsp" %>
        <div class="article">
            <%@include file="../../include/manager_left.jsp" %>
            <div class="information">
                <h3 class="infoTitle">添加API key</h3>
                <p class="path">
                    <a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                    ><a class="pathAnchor" href="#">管理中心</a>
                    ><a class="pathAnchor" href="#">添加key</a>
                </p>
                <div class="mainContent">      		
                    <div class="center">
                        <div class="message">
                            <h2>基本信息</h2>
                            <form id="add_api_form">                           
                            <p>说明：</p>
                            <textarea rows="4" cols="20" name="api.note" style="width: 100%;" ></textarea>
                            <input type="hidden" name="token" value="${token}">
                            </form>
                        </div>
                        <button class="button" onclick="addApi()" >保存</button>
                    </div>
                </div>

            </div>
            
        </div>
              <%@include file="../../include/footer.jsp" %>
    </body>
</html>
