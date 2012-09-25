<%-- 
    Document   : add
    Created on : 2012-9-10, 14:25:41
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>HCI Meeting-Home</title>
        <link href="${base}/css/index.css" rel="stylesheet" type="text/css" />
        <link rel = "Shortcut Icon" href="${base}/images/avicon.png" type="image/x-icon"/>
        <script type="text/javascript" src="${base}/js/jquery-1.3.2.js"></script>
        <script type="text/javascript" src="${base}/js/common.js"></script>
        <script type="text/javascript" src="${base}/js/manager/member.js"></script>

    </head>

    <body>
        <%@include file="../../include/header.jsp" %>
        <div id="article" class="article">
            <%@include file="../../include/manager_left.jsp" %>

            <div id="right_information" class="information">
                <h3  class="infoTitle">录入信息</h3>
                <p class="path">
			<a class="pathAnchor" href="#"><img src="${base}/images/home.png" alt="back to index" /></a>
                        ><a class="pathAnchor" href="#">管理中心</a>
                        ><a class="pathAnchor" href="#">添加成员</a>
		</p>
                
                <div id="modify_password" class="mainContent" >  
                    <div class="center">
                        <div class="message">
                            <h2>录入信息</h2>  
                            <div id="first_step" >
                                <p>学号：<input type="text" id="search_id" name="studentId"></p>   
                                <button class="button" onclick="searchInfo()" >查找</button> <a href="javascript:nextStep();" >直接填表</a>
                            </div>             
                            <div id="second_step" style="display: none;" >
                                <form id="add_member_form" >
                                    <div>
                                        <label>学号：</label> <input type="text" id="studentId" name="member.studentId">                                      
                                    </div>

                                    <div>
                                        <label>姓名：</label> <input type="text" id="name" name="member.name">
                                    </div>

                                    <div>
                                        <label>性别：</label>
                                        男<input type="radio" id="m_sex" name="extend.sex" value="0">&nbsp;
                                        女<input type="radio" id="f_sex" name="extend.sex" value="1">
                                    </div>                                  

                                    <div>
                                        <label>类型：</label> <select id="status" name="member.status">
                                            <option value="3">本科生</option>
                                            <option value="1">教师</option>
                                            <option value="2">研究生</option>
                                        </select>
                                    </div>

                                    <div>
                                        <label>状态：</label> <select id="state" name="member.state">
                                            <option value="3">见习生</option>
                                            <option value="1">在职</option>                                         
                                            <option value="2">已毕业</option>                                          
                                            <option value="4">退出</option>
                                        </select>
                                    </div>

                                    <div>
                                        <label>年级：</label> <input id="grade" type="text" name="extend.grade">
                                    </div>

                                    <div>
                                        <label>学院：</label> <input id="academy" type="text" name="extend.academy">
                                    </div>

                                    <div>
                                        <label>专业：</label> <input id="major" type="text" name="extend.major">
                                    </div>

                                    <div>
                                        <label>手机：</label> <input id="mobile" type="text" name="extend.mobile">
                                    </div>

                                    <div>
                                        <label>短号：</label> <input id="mobileshort" type="text" name="extend.mobileshort">
                                    </div>

                                    <div>
                                        <label>邮箱：</label> <input id="email" type="text" name="extend.email">
                                    </div>

                                    <div>
                                        <label>生日：</label> <input id="birthday" type="text" name="extend.birthday">
                                    </div>

                                    <div>
                                        <label>个人主页：</label> <input id="homepage" type="text" name="extend.homepage">
                                    </div>

                                    <div>
                                        <input type="hidden" id="familyaddress"  name="extend.familyaddress" >
                                        <input type="hidden" id="nativeplace" name="extend.nativeplace" >
                                        <input type="hidden" id="folk" name="extend.folk" >
                                        <input type="hidden" id="cardId" name="extend.cardId" >
                                        <input type="hidden" name="token" value="${token}">
                                        <input class="button" type="button" onclick="addMember()" value="添加" >
                                    </div>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            
        </div>
     <%@include file="../../include/footer.jsp" %>
    </body>
</html>
