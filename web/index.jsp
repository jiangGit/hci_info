<%-- 
    Document   : index
    Created on : 2012-9-10, 1:27:09
    Author     : jiang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>HCI Meeting-Home</title>
        <script type="text/javascript" src="js/changePic.js"></script>
        <link href="css/index.css" type="text/css" rel="stylesheet" />
        <link rel = "Shortcut Icon" href="images/avicon.png" type="image/x-icon"/>
        <script type="text/javascript" src="js/jquery-1.3.2.js"></script>
        <script type="text/javascript" src="js/jquery.md5.js"></script>
        <script type="text/javascript" src="js/common.js"></script>
        <script type="text/javascript" src="js/index.js"></script>
        <script>
            $(function(){
                if($("#send").length>0)
                    $("#send").click(login);
            });       
            var base = "${base}";
        </script>
    </head>
    <body>
        <div id="container">
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
                    <div id="logo"><img src="images/logo.png" alt="logo"/></div>
                    <div id="title"><img src="images/HCI-SCAU.png" alt="hci"/></div>

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

            <div id="mainshow">
                <div id="middle">
                    <img src="images/show.png">
                </div>
            </div>
        </div>
        <div id="foot" class="footer" style="position: absolute;bottom: 0;" >
            <p>copyright: HCI Studio@ SCAU</p>
        </div>
    </body>
</html>
