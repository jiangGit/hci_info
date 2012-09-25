<%-- 
    Document   : newjsp
    Created on : 2012-9-15, 3:58:02
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<p class="page">
    <span>共${obj.count}条纪录，当前第${obj.page}页，每页${obj.size}条纪录,共${obj.pageCount}页</span>
    <c:if test="${obj.page!=1&& obj.page>1}">
        <a href="javascript:void();" page="1" >首页</a>
    </c:if>
    <c:if test="${obj.page>1}">
        <a href="javascript:void();" page="${obj.frontPage}">上一页</a>
    </c:if>
    <c:if test="${obj.pageCount>obj.page}">
        <a href="javascript:void();" page="${obj.nextPage}">下一页</a>
    </c:if>
    <c:if test="${obj.pageCount>1 && obj.page<obj.pageCount}">
        <a href="javascript:void();" page="${obj.lastPage}">尾页</a>			
    </c:if>
    <span>转到第</span>
    <span>
        <input id="go_page" name="page" type="text" size="5" />
    </span>页 
    <input id="go_page_button" type="button" size="5" value="跳转" onclick="goPage()" />
</p>
<script>  
    $(".page a").click(function (){
        var page = $(this).attr("page");
        $("#filter").children("[name='page']").val(page)
        $("#filter").submit();
    }); 
    function goPage(){
        var page = document.getElementById("go_page").value;
        if(!page) return;
        $("#filter").children("[name='page']").val(page)
        $("#filter").submit();
    }
    
</script>