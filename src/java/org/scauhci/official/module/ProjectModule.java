package org.scauhci.official.module;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;
import org.scauhci.official.Config;
import org.scauhci.official.bean.Member;
import org.scauhci.official.bean.Project;
import org.scauhci.official.bean.ProjectMember;
import org.scauhci.official.bean.condition.ProjectCondition;
import org.scauhci.official.mvc.Auth;
import org.scauhci.official.mvc.AuthFilter;
import org.scauhci.official.service.DepartmentService;
import org.scauhci.official.service.MemberService;
import org.scauhci.official.service.ProjectService;
import org.scauhci.official.util.ImageZip;
import org.scauhci.official.util.PrintImage;

@IocBean
@Fail("redirect:/404.html")
@Filters(
@By(type = AuthFilter.class, args = {"/404.html"}))
public class ProjectModule {

    @Inject
    MemberService memberService;
    @Inject
    ProjectService projectService;
    @Inject
    DepartmentService departmentService;

    @GET
    @At("/project/?")
    @Ok("smart:page.project.details")
    @Auth({Member.ROLE_COMMON , Member.ROLE_ADMIN })
    public Map<String, Object> get(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        Project p = projectService.fetch(id);
        projectService.fetchProjectMember(p);
        map.put("project", p);
        map.put("department", departmentService.fetch(p.getDepartmentId()));      
        return map;
    }

    @GET
    @At("/project/avatar/?")
    @Auth({Member.ROLE_COMMON , Member.ROLE_ADMIN })
    public void avatar(String id, @Param("thumb") boolean thumb,
            HttpServletRequest req, HttpServletResponse res) {
        PrintImage.writePhoto(Config.projectImagePath, id, thumb,
                Config.defaultProjectImage, req, res);
    }

    @At("/project/list")
    @Ok("jsp:page.project.list")
    @Aop("pageInterceptor")
    @Auth({Member.ROLE_COMMON , Member.ROLE_ADMIN })
    public Map<String, Object> list(@Param("..") ProjectCondition condition) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (condition.getPage() == null || condition.getPage() < 1) {
            condition.setPage(1);
        }
        if (condition.getCount() == null || condition.getCount() < 1) {
            condition.setCount(Config.MANAGER_PAGE_SIZE);
        }
        List<Project> list = projectService.list(condition);
        projectService.fetchProjectMember(list);
        map.put("condition", condition);
        map.put("departmentList", departmentService.getAll());
        map.put("list", list);
        map.put("count", projectService.count(condition));
        map.put("size", condition.getCount());
        map.put("page", condition.getPage());
        return map;
    }


    @At("/project/search/")
    @Ok("jsp:page.manage.project.list")
    @Aop("pageInterceptor")
    public Map<String, Object> search(@Param("wd") String s, @Param("page") int page) {
        Map<String, Object> map = new HashMap<String, Object>();
        //map.put("list", projectLucene.searchIndex(s,memberService.dao().createPager(page,Config.MANAGER_PAGE_SIZE)));
        //map.put("count", projectLucene.countSearchIndex(s));
        map.put("size", Config.MANAGER_PAGE_SIZE);
        map.put("page", page);
        return map;
    }
}
