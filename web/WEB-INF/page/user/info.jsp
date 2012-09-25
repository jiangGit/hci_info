<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>HCI Meeting-Home</title>
        <link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
        <link rel = "Shortcut Icon" href="${base}/images/avicon.png" type="image/x-icon"/>
        <script type="text/javascript" src="${base}/js/jquery-1.3.2.js"></script>
        <script type="text/javascript" src="${base}/js/common.js"></script>

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
                   
                </p>
                <div class="mainContent" >  
                    <img class="mainConImg" src="${base}/member/avatar/${obj.member.studentId }?thumb=true" alt="" />
                    <div class="center hasImg">
                        <div class="message">
                            <h2>基本信息</h2>
                            <p>姓名：${obj.member.name}</p>
                            <p>部门：${obj.department !=null ? obj.department.name :"无" }</p>
                            <p>年级：${obj.member.extend.grade}</p>
                            <p>专业：${obj.member.extend.major !=null ?obj.member.extend.major :""}</p>
                            <p>手机：${obj.member.extend.mobile != null ? obj.member.extend.mobile:"" }/${obj.member.extend.mobileshort != null ? obj.member.extend.mobileshort:"" }</p>
                            <p>邮箱：${obj.member.extend.email}</p>
                        </div>                        
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../include/footer.jsp" %>
    </body>
</html>
