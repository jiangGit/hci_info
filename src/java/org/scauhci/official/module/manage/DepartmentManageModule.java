package org.scauhci.official.module.manage;

import org.scauhci.official.module.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.scauhci.official.Config;
import org.scauhci.official.bean.Department;
import org.scauhci.official.bean.Member;
import org.scauhci.official.mvc.Auth;
import org.scauhci.official.mvc.AuthFilter;
import org.scauhci.official.mvc.TokenFilter;
import org.scauhci.official.service.DepartmentService;
import org.scauhci.official.service.MemberService;
import org.scauhci.official.service.ProjectService;
import org.scauhci.official.util.TokenUtil;

@IocBean
@At("/manage")
@Filters(
@By(type = AuthFilter.class, args = {"/404.html"}))
public class DepartmentManageModule {

    @Inject
    MemberService memberService;
    @Inject
    ProjectService projectService;
    @Inject
    DepartmentService departmentService;

    @GET
    @At("/department/add")
    @Ok("jsp:page.manage.department.add")
    @Auth(Member.ROLE_ADMIN)
    public void toAdd(HttpServletRequest req , HttpSession session) {
        String token=TokenUtil.create(session);
        req.setAttribute("token", token);
    }

    @POST
    @At("/department/add")
    @Ok("json")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    @Filters({@By(type=TokenFilter.class)})
    public Map<String, Object> add(@Param("::department.") Department department) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            departmentService.add(department);
            map.put("id", department.getId());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "fail");
            map.put("message", "操作失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }

    @GET
    @At("/department/?")
    @Ok("jsp:page.manage.department.edit")
    @Auth(Member.ROLE_ADMIN)
    public void toEidt(int id, HttpServletRequest req) {
        Department d = departmentService.fetch(id);
        req.setAttribute("department", d);
    }

    @POST
    @At("/department/?")
    @Ok("json")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> eidt(int id, @Param("::department.") final Department department) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            departmentService.update(department);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "fail");
            map.put("message", "操作失败");
            return map;
        }
        map.put("state", "ok");
        return map;

    }

    @GET
    @At("/department/delete/?")
    @Ok("json")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> delete(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "fail");
            map.put("message", "操作失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }



    @At("/departments")
    @Ok("jsp:page.manage.department.list")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> list() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", departmentService.getAll());
        return map;
    }


}
