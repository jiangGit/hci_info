<%-- 
    Document   : list
    Created on : 2012-9-24, 13:29:32
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
        <script language="javascript" type="text/javascript" src="${base}/js/impromptu.js" ></script>
        <script type="text/javascript" src="${base}/js/common.js"></script>
        <script type="text/javascript" src="${base}/js/manager/api.js"></script>
        <script src="${base}/js/jquery.colorbox.js"></script>


    </head>

    <body>
        <%@include file="../../include/header.jsp" %>
        <div class="article">
            <%@include file="../../include/manager_left.jsp" %>
            <div class="information">
                <h3 class="infoTitle">API列表</h3>
                <p class="path">
                    <a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                    ><a class="pathAnchor" href="#">管理中心</a>
                    ><a class="pathAnchor" href="#">API列表</a>
                </p>

                <div class="mainContent">
                    <div class="center">
                        <c:forEach items="${obj.list}" var="api" varStatus="status">
                            <div class="project">
                                <div class="list">
                                    <p>key：${api.apiKey}</p>
                                    <p>创建时间：${api.time}</p>
                                    <p>说明：${api.note}</p>
                                </div>
                                <div class="controls">
                                    <input type="hidden" name="id" value="${api.id }">
                                    <input type="hidden" name="note" value="${api.note}">
                                    <a href="${api.id}">编辑</a> |                                   
                                    <a href="javascript:void(0);" class="remove_api" >移除</a>
                                </div>
                            </div>
                        </c:forEach>
                        <a href="${base}/manage/api/add">
                            <button class="button" >添加</button>
                        </a>
                    </div>
                </div>
            </div>

        </div>
        <%@include file="../../include/footer.jsp" %>
        <script>
            $(function(){
                $(".remove_api").click(toRemoveApi);
            });
        </script>
    </body>
</html>
