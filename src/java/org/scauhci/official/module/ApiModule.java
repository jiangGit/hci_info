/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scauhci.official.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.scauhci.official.Config;
import org.scauhci.official.bean.Department;
import org.scauhci.official.bean.Member;
import org.scauhci.official.bean.Project;
import org.scauhci.official.bean.condition.MemberCondition;
import org.scauhci.official.bean.condition.ProjectCondition;
import org.scauhci.official.mvc.ApiAuthFilter;
import org.scauhci.official.service.DepartmentService;
import org.scauhci.official.service.MemberService;
import org.scauhci.official.service.ProjectService;

/**
 *
 * @author Administrator
 */
@IocBean
@Fail("json")
@Ok("json")
@At("/api")
@Filters(
@By(type = ApiAuthFilter.class))
public class ApiModule {

    @Inject
    MemberService memberService;
    @Inject
    ProjectService projectService;
    @Inject
    DepartmentService departmentService;

    @At("/member/list")
    @Aop("pageInterceptor")
    public Map<String, Object> memberList(@Param("..") MemberCondition condition) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (condition.getPage() == null || condition.getPage() < 1) {
            condition.setPage(1);
        }
        if (condition.getCount() == null || condition.getCount() < 1) {
            condition.setCount(Config.MANAGER_PAGE_SIZE);
        }
        if (condition.getAll() != null && condition.getAll()) {
            condition.setCount(memberService.count(condition));
        }
        List<Member> list = memberService.list(condition);
        if (condition.getWith() != null && !"".equals(condition.getWith())) {
            for (Member m : list) {
                memberService.dao().fetchLinks(m, condition.getWith());
            }
        }
        map.put("condition", condition);
        map.put("list", list);
        map.put("count", memberService.count(condition));
        map.put("size", condition.getCount());
        map.put("page", condition.getPage());
        return map;
    }

    @At("/member/get")
    public Map<String, Object> getMember(@Param("..") MemberCondition condition) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member m = null;
        if (condition.getId() != null && condition.getId() != 0) {
            m = memberService.getMember(condition.getId());
        } else if (condition.getStudentId() != null && !"".equals(condition.getStudentId())) {
            m = memberService.getMember(condition.getStudentId());
        } else {
            map.put("message", "id 和 studentId 两者必须有一个");
            return map;
        }
        if (m == null) { //查不到
            return map;
        }
        if (condition.getWith() != null && !"".equals(condition.getWith())) {
            memberService.dao().fetchLinks(m, condition.getWith());
        }
        map.put("member", m);
        return map;
    }

    @At("/project/list")
    @Aop("pageInterceptor")
    public Map<String, Object> projectList(@Param("..") ProjectCondition condition) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (condition.getPage() == null || condition.getPage() < 1) {
            condition.setPage(1);
        }
        if (condition.getCount() == null || condition.getCount() < 1) {
            condition.setCount(Config.MANAGER_PAGE_SIZE);
        }
        if (condition.getAll() != null && condition.getAll()) {
            condition.setCount(projectService.count(condition));
        }
        List<Project> list = projectService.list(condition);
        if (condition.getWith() != null && !"".equals(condition.getWith())) {
            for (Project p : list) {
                projectService.dao().fetchLinks(p, condition.getWith());
            }
        }
        map.put("condition", condition);
        map.put("list", list);
        map.put("count", projectService.count(condition));
        map.put("size", condition.getCount());
        map.put("page", condition.getPage());
        return map;
    }

    @At("/project/get")
    public Map<String, Object> getProject(@Param("..") ProjectCondition condition) {
        Map<String, Object> map = new HashMap<String, Object>();
        Project p = null;
        if (condition.getId() != null && condition.getId() != 0) {
            p = projectService.fetch(condition.getId());
        }else {
            map.put("message", "必须有id");
            return map;
        }
        if (p == null) { //查不到
            return map;
        }
        if (condition.getWith() != null && !"".equals(condition.getWith())) {
            projectService.dao().fetchLinks(p, condition.getWith());
        }
        map.put("project", p);
        return map;
    }

    @At("/department/list")
    public Map<String, Object> departmentList() {
         Map<String, Object> map = new HashMap<String, Object>();
         List<Department> list = departmentService.getAll();
         map.put("list", list);
         return map;
    }
    
    @At("/department/get")
    public Map<String, Object> getDepartment(@Param("id") Integer id ,@Param("with") String with){
        Map<String, Object> map = new HashMap<String, Object>();
        Department d = null;
        if (id != null && id != 0) {
            d = departmentService.fetch(id);
        }else {
            map.put("message", "必须有id");
            return map;
        }    
        if (d == null) { //查不到
            return map;
        } 
        if (with != null && !"".equals(with)) {
            departmentService.dao().fetchLinks(d, with);
        }     
        return map;
    }
}
