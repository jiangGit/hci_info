<%-- 
    Document   : header
    Created on : 2012-9-10, 3:30:12
    Author     : Administrator
--%>

<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<div id="" class="topBackground">
    <div id="" class="forSearch">
        <c:choose>
            <c:when test="${user != null}">
                <div class="loginIn">
                    <p>欢迎您！${user.name} |<a href="${base}/info">个人中心<a/>|<a href="${base}/logout">退出</a></p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="search">
                    <form id="login_form" action="/login" method="post" >
                        <input id="username" name="username" class="inputCss" type="text"/>
                        <input id="password" name="password" class="inputCss" type="password"/>
                        <input type="button" id="send"  />
                    </form>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
</div>

<div id="top2">
    <div id="topcontent">
        <div id="logo"><img src="${base}/images/logo.png" /></div>
        <div id="title"><img src="${base}/images/HCI-SCAU.png" /></div>

        <div id="navigation">
            <ul class="nav">
                <li class="thread"><a href="${base}">Home</a></li>          
                <c:choose>
                    <c:when test="${user != null && user.role ==1 }">
                        <li class="thread"><a href="${base}">About</a></li>
                        <li class="thread"><a href="${base}/member/list">Members</a></li>
                        <li class="thread"><a href="${base}/project/list">Projects</a></li>        
                        <li><a href="${base}/managerCenter">Manage</a></li>
                    </c:when>
                    <c:when test="${user != null}">
                        <li class="thread"><a href="${base}">About</a></li>
                         <li class="thread"><a href="${base}/member/list">Members</a></li>
                         <li ><a href="${base}/project/list">Projects</a></li>   
                    </c:when>    
                    <c:otherwise>
                         <li><a href="${base}">About</a></li>
                    </c:otherwise>
                </c:choose>


            </ul>    

        </div>
    </div>
</div>