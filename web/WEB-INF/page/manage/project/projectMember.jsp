<%-- 
    Document   : projectMember
    Created on : 2012-9-13, 11:20:31
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>HCI Meeting-Home</title>
        <link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
        <link href="${base}/css/impromptu.css" rel="Stylesheet" type="text/css" />
        <link rel = "Shortcut Icon" href="${base}/images/avicon.png" type="image/x-icon"/>
        <script type="text/javascript" src="${base}/js/jquery-1.3.2.js"></script>
        <script type="text/javascript" language="javascript" src="${base}/js/impromptu.js" ></script>
        <script type="text/javascript" src="${base}/js/common.js"></script>       
        <script type="text/javascript" src="${base}/js/manager/project.js"></script>
        <script>
            $(document).ready(function(){
                $(".modify_member_a").click(toModifyMember);
                $(".delete_member_a").click(deleteMember);
            });                   
        </script>
    </head>

    <body>
        <%@include file="../../include/header.jsp" %>
        <div class="article">
            <%@include file="../../include/manager_left.jsp" %>
            <div class="information">
                <h3 class="infoTitle">项目成员管理</h3>
                <p class="path">
                    <a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                    ><a class="pathAnchor" href="#">管理中心</a>
                    ><a class="pathAnchor" href="#">项目成员</a>
                </p>
                <div class="mainContent">      		
                    <div class="center">
                        <div class="message">
                            <h2>基本信息</h2>                            
                            <p>项目名：${obj.project.name}</p>
                            <p>部门： ${obj.project.department.name}                                              
                            </p>
                            <p>项目状态：
                                <select name="project.state" disabled="disabled" >
                                    <option value="1" ${obj.project.state== 1 ?"selected=\"selected\"":"" }>开发中</option>
                                    <option value="2" ${obj.project.state== 2 ?"selected=\"selected\"":"" }>结题</option>
                                    <option value="3" ${obj.project.state== 3 ?"selected=\"selected\"":"" }>难产</option>
                                </select>
                            </p>
                            <p>开始日期：${obj.project.stateDate} </p>
                            <c:if test="${obj.project.endDate != null && obj.project.state== 2}">
                                <p>结束日期：<input ${obj.project.endDate}</p>
                                </c:if>
                            <input type="hidden" id="project_id" value="${obj.project.id}">
                            <p>项目负责人：</p>
                            <c:choose>
                                <c:when test="${obj.leader != null}">
                                    <div style="border: 2px solid #99C944; padding-left: 20px;" >
                                        <input type="hidden" name="pm_id" value="${obj.leader.id}">
                                        <input type="hidden" name="pid" value="${obj.leader.projectId}">
                                        <input type="hidden" name="mid" value="${obj.leader.memberId}">
                                        <input type="hidden" name="job" value="${obj.leader.job}">
                                        <input type="hidden" name="name" value="${obj.leader.member.name}">
                                        <p> ${obj.leader.member.name}  
                                            <a class="modify_member_a" href="javascript:void(0);"  >更改</a> 
                                            <a class="delete_member_a" href="javascript:void(0);"  >删除</a></p>
                                        <p> 职责：${obj.leader.job}</p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <a href="javascript:void(0);" onclick="toAddLeader()" >添加</a>
                                </c:otherwise>
                            </c:choose>                 

                            <p>项目成员：<a class="add_member_a" href="javascript:void(0);" onclick="toAddMember()" >添加</a> </p>
                            <c:forEach items="${obj.memberList}" var="pm" varStatus="status">
                                <div class="project_member_div" style="border: 2px solid #99C944; padding-left: 20px; margin-bottom: 2px;" >
                                    <input type="hidden" name="pm_id" value="${pm.id}">
                                    <input type="hidden" name="pid" value="${pm.projectId}">
                                    <input type="hidden" name="mid" value="${pm.memberId}">
                                    <input type="hidden" name="job" value="${pm.job}">
                                    <input type="hidden" name="name" value="${pm.member.name}">
                                    <p> ${pm.member.name}  
                                        <a class="modify_member_a" href="javascript:void(0);"  >更改</a> 
                                        <a class="delete_member_a" href="javascript:void(0);"  >删除</a></p>
                                    <p> 职责：${pm.job}</p>
                                </div>

                            </c:forEach>

                            <p>简介：</p>
                            <textarea rows="4" cols="20" name="project.detail" style="width: 100%;" disabled="disabled" >${obj.project.detail}</textarea>

                        </div>

                    </div>
                </div>

            </div>          
        </div>
        <%@include file="../../include/footer.jsp" %>
    </body>
</html>
