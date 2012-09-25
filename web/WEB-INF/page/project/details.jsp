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
                <h3 class="infoTitle">项目信息</h3>
                <p class="path">
                    <a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                    ><a class="pathAnchor" href="#">项目列表</a>
                    ><a class="pathAnchor" href="#">项目信息</a>
                </p>
                <div class="mainContent" >  
                    <img class="mainConImg" src="${base}/project/avatar/${obj.project.id }?thumb=true" alt="" />
                    <div class="center hasImg">
                        <div class="message">
                            <h2>基本信息</h2>
                            <p>项目名：${obj.project.name}</p>
                            <p>部门：${obj.department !=null ? obj.department.name :"无" }</p>
                            <p>项目状态：
                                <select name="project.state" disabled="disabled" >
                                    <option value="1" ${obj.project.state== 1 ?"selected=\"selected\"":"" }>开发中</option>
                                    <option value="2" ${obj.project.state== 2 ?"selected=\"selected\"":"" }>结题</option>
                                    <option value="3" ${obj.project.state== 3 ?"selected=\"selected\"":"" }>难产</option>
                                </select>
                            </p>
                            <p>项目开始时间：${obj.project.stateDate}</p>
                            <c:if test="${obj.project.state == 2}">
                                <p>项目结束时间：${obj.project.stateDate}</p>
                            </c:if>
                            <p>项目负责人：
                                <c:forEach items="${obj.project.projectMembers}" var="pm" varStatus="status">
                                    <c:if test="${pm.type == 2}">
                                        <font>${pm.member.name}</font>
                                    </c:if>                                           
                                </c:forEach>
                            </p>
                            <p>项目成员：
                                <c:forEach items="${obj.project.projectMembers}" var="pm" varStatus="status">
                                    <c:if test="${pm.type == 1}">
                                        <font>${pm.member.name}</font>
                                    </c:if>                                           
                                </c:forEach>
                            </p>
                        </div>
                        <div class="message">
                            <h2>简介</h2>
                            <p>${obj.project.detail}</p>       
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>
        <%@include file="../include/footer.jsp" %>
    </body>
</html>
