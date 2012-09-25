/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scauhci.official.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.scauhci.official.bean.Member;
import org.scauhci.official.bean.Project;
import org.scauhci.official.mvc.Auth;
import org.scauhci.official.mvc.AuthFilter;
import org.scauhci.official.service.MemberService;
import org.scauhci.official.service.ProjectService;

/**
 *
 * @author Administrator
 */
@IocBean
@Fail("redirect:/404.html")
@Filters(
@By(type = AuthFilter.class, args = {"/index.jsp"}))
public class UserModule {

    @Inject
    MemberService memberService;
    @Inject
    ProjectService projectService;


    @POST
    @At("/login")
    @Ok("json")
    public Map<String, Object> login(@Param("username") String account, @Param("password") String password, HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (!memberService.isExistMember(account)) {
            result.put("state", "fail");
            result.put("message", "用户不存在！");
            return result;
        }
        if (!memberService.authentication(account, password)) {
            result.put("state", "fail");
            result.put("message", "密码错误！");
            return result;
        }
        Member member = memberService.getMember(account);
        session.setAttribute("user", member);
        result.put("state", "ok");
        return result;
    }
    
    @At("/logout")
    @Ok("redirect:/index.jsp")
    public void logout(HttpSession session){
        session.removeAttribute("user");
    }

    @At("/info")
    @Ok("jsp:page.user.info")
    @Auth({Member.ROLE_COMMON , Member.ROLE_ADMIN })
    public Map<String, Object> info(HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member m = (Member) session.getAttribute("user");
        memberService.dao().fetchLinks(m, "extend");
        map.put("member", m);
        return map;
    }
    
    @GET
    @At("/password")
    @Ok("jsp:page.user.modifyPassword")
    @Auth({Member.ROLE_COMMON , Member.ROLE_ADMIN })
    public void updatePassword(){        
    }
    
    @POST
    @At("/password")
    @Ok("json")
    @Auth({Member.ROLE_COMMON , Member.ROLE_ADMIN })
    public Map<String, Object> updatePassword(@Param("password") String password, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member m = (Member) session.getAttribute("user");
        int id = m.getId();
        try {
            memberService.updatePassword(id, password);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "fail");
            map.put("message", "更新密码失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }

    @At("/projects/finished")
    @Ok("jsp:page.user.projectList")
    @Auth({Member.ROLE_COMMON , Member.ROLE_ADMIN })
    public Map<String, Object> finishedProjectList(HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member m = (Member) session.getAttribute("user");
        int id = m.getId();
        map.put("list", memberService.project(id, Project.STATE_FINISHED));
        return map;
    }

    @At("/projects/developing")
    @Ok("jsp:page.user.projectList")
    @Auth({Member.ROLE_COMMON , Member.ROLE_ADMIN })
    public Map<String, Object> developingProjectList(HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member m = (Member) session.getAttribute("user");
        int id = m.getId();
        List<Project> list =memberService.project(id, Project.STATE_DEVELOPING);
        projectService.fetchProjectMember(list);
        map.put("list",list );
        return map;
    }
    
    @At("/managerCenter")
    @Ok("jsp:page.manage.index")
    @Auth({ Member.ROLE_ADMIN })
    public Map<String, Object> managerCenter(HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member m = (Member) session.getAttribute("user");
        memberService.dao().fetchLinks(m, "extend");
        map.put("member", m);
        return map;
    }
    
}
