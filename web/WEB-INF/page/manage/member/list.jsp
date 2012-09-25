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
        <script type="text/javascript" src="${base}/js/manager/member.js"></script>
        <script src="${base}/js/jquery.colorbox.js"></script>


    </head>

    <body>
        <%@include file="../../include/header.jsp" %>
        <div class="article">
            <%@include file="../../include/manager_left.jsp" %>
            <div class="information">
                <h3 class="infoTitle">成员列表</h3>
                <p class="path">
                    <a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                    ><a class="pathAnchor" href="#">管理中心</a>
                    ><a class="pathAnchor" href="#">成员列表</a>
                </p>
                </br>
                <div class="filter">
                    <form id="filter"  action="${base}/manage/members" method="post" >
                        <label >部门：</label>
                        <select name="departmentId" class="filterSelect">
                            <option value="0">请选择</option>
                            <c:forEach items="${obj.departmentList}" var="department">
                                <option value="${department.id}" ${obj.condition.departmentId== department.id ?"selected=\"selected\"":"" }>${department.name}</option>
                            </c:forEach>
                        </select>
                        <label >身份：</label>
                        <select name="type"  class="filterSelect">
                            <option value="0">请选择</option>
                            <option value="1" ${obj.condition.type== 1?"selected=\"selected\"":"" }>教师</option>
                            <option value="2" ${obj.condition.type== 2?"selected=\"selected\"":"" }>研究生</option>
                            <option value="3" ${obj.condition.type== 3?"selected=\"selected\"":"" }>本科生</option>
                            
                        </select>
                        <label >状态：</label>
                        <select name="state"  class="filterSelect">
                            <option value="0" >请选择</option>                           
                            <option value="1" ${obj.condition.state== 1?"selected=\"selected\"":"" }>在职</option>
                            <option value="2" ${obj.condition.state== 2?"selected=\"selected\"":"" }>已毕业</option>
                            <option value="3" ${obj.condition.state== 3?"selected=\"selected\"":"" }>见习生</option>
                        </select>
                        <input type ="hidden" name="page" value="0">
                        <input type="submit" class="button" value="筛选" />
                    </form>
                </div>
                <div class="mainContent" >
                    <div class="center">
                        <c:forEach items="${obj.list}" var="m">
                            <div class="person">
                                <a href="${base}/manage/member/${m.id}"><img src="${base}/member/avatar/${m.studentId }?thumb=true"/>
                                    <p>${m.name}</p></a>
                            </div>
                        </c:forEach>

                        <div class="clear"></div>
                    </div> 
                    <%@include file="../../include/paging.jsp" %>
                </div>
            </div>    
        </div>
        <%@include file="../../include/footer.jsp" %>
    </body>
</html>
