<%-- 
    Document   : left
    Created on : 2012-9-10, 3:32:16
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>

<div id="left_nav" class="navSub">
    <h3 class="navSubTitle">个人中心</h3>
    <ul class="navSubContent">
      <li><a href="${base}/info">个人信息</a></li>
      <li><a href="${base}/password" >修改密码</a></li>
      <li><a href="${base}/projects/finished" >已完成项目</a></li>
      <li><a href="${base}/projects/developing" >未完成项目</a></li>
    </ul>
 </div>

