package org.scauhci.official.module.manage;

import org.scauhci.official.module.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
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
import org.scauhci.official.mvc.TokenFilter;
import org.scauhci.official.service.DepartmentService;
import org.scauhci.official.service.MemberService;
import org.scauhci.official.service.ProjectService;
import org.scauhci.official.util.ImageZip;
import org.scauhci.official.util.PrintImage;
import org.scauhci.official.util.TokenUtil;

@IocBean
@At("/manage/project")
@Filters(
@By(type = AuthFilter.class, args = {"/404.html"}))
public class ProjectManageModule {

    @Inject
    MemberService memberService;
    @Inject
    ProjectService projectService;
    @Inject
    DepartmentService departmentService;

    @GET
    @At("/add")
    @Ok("jsp:page.manage.project.add")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    public void toAdd(HttpServletRequest req , HttpSession session) {
        req.setAttribute("departmentList", departmentService.getAll());
        String token=TokenUtil.create(session);
        req.setAttribute("token", token);
    }

    @POST
    @At("/add")
    @Ok("json")
    @Auth(Member.ROLE_ADMIN)
    @Filters({@By(type=TokenFilter.class)})
    public Map<String, Object> add(@Param("::project.") final Project project) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            project.setState(Project.STATE_DEVELOPING);
            project.setStateDate(new java.sql.Date(new java.util.Date().getTime()));
            projectService.add(project);
            map.put("id", project.getId());
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
    @At("/?")
    @Ok("jsp:page.manage.project.edit")
    @Auth(Member.ROLE_ADMIN)
    public void toEidt(int id, HttpServletRequest req) {
        Project p = projectService.fetch(id);
        req.setAttribute("project", p);
        req.setAttribute("departmentList", departmentService.getAll());
    }

    @POST
    @At("/?")
    @Ok("json")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> eidt(final int id,
            @Param("::project.") final Project project) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            projectService.update(project);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "fail");
            map.put("message", "操作失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }

    @POST
    @At("/avatar/?")
    @Auth(Member.ROLE_ADMIN)
    @AdaptBy(type = UploadAdaptor.class, args = {"ioc:myUpload"})
    public void avatar(String id, @Param("successCode") String successCode, @Param("failCode") String failCode, @Param("errCode") String errCode,
            @Param("avatar") TempFile avatar, HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();
        if (avatar == null || id == null || "".equals(id)) {

            out.write("<script> " + failCode + "</script>");
            return;

        }
        File tempFile = avatar.getFile();
        File newFile = new File(Config.projectImagePath + "/" + id
                + avatar.getMeta().getFileExtension());
        try {

            Files.deleteFile(newFile);
            Files.move(tempFile, newFile);
            ImageZip.zipImageFile(newFile.getAbsolutePath(),
                    Config.projectImagePath, "thumb_" + id + ".jpg");

        } catch (IOException ex) {
            out.write("<script> " + errCode + "</script>");
            return;
        }

        out.write("<script> " + successCode + "</script>");
    }

    
    @At("/list")
    @Ok("jsp:page.manage.project.list")
    @Aop("pageInterceptor")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> list(@Param("..") ProjectCondition condition) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (condition.getPage() ==null || condition.getPage() < 1) {
            condition.setPage(1);
        }
        if(condition.getCount() ==null || condition.getCount()<1){
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

    @At("/members/?")
    @Ok("jsp:page.manage.project.projectMember")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> memberList(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        List pmList;
        map.put("project", projectService.getProjectWithDepartment(id));
        pmList = projectService.projectMembers(id, ProjectMember.TYPE_LEADER);
        if (pmList != null && pmList.size() != 0) {
            map.put("leader", pmList.get(0));
        } else {
            map.put("leader", null);
        }
        map.put("memberList", projectService.projectMembers(id, ProjectMember.TYPE_MEMBER));
        return map;
    }

    @POST
    @At("/leader/add")
    @Ok("json")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> addLeader(@Param("pid") int pid, @Param("mid") int mid, @Param("job") String job) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!projectService.exists(pid)) {
                map.put("state", "err");
                map.put("message", "项目不存在");
                return map;
            }
            if (projectService.isExistMemberType(pid, ProjectMember.TYPE_LEADER)) {
                map.put("state", "err");
                map.put("message", "项目已存在负责人");
                return map;
            }
            if (!memberService.isExistMember(mid)) {
                map.put("state", "err");
                map.put("message", "成员不存在");
                return map;
            }
            if (projectService.isExistMember(pid, mid)) {
                map.put("state", "err");
                map.put("message", "该成员已加进项目");
                return map;
            }
            ProjectMember pm = new ProjectMember();
            pm.setMemberId(mid);
            pm.setProjectId(pid);
            pm.setJob(job);
            pm.setType(ProjectMember.TYPE_LEADER);
            projectService.addProjectMember(pm);

        } catch (Exception e) {
            map.put("state", "err");
            map.put("message", "添加失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }
    
    
    @POST
    @At("/member/add")
    @Ok("json")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> addMember(@Param("pid") int pid, @Param("mid") int mid, @Param("job") String job) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (!projectService.exists(pid)) {
                map.put("state", "err");
                map.put("message", "项目不存在");
                return map;
            }
            if (!memberService.isExistMember(mid)) {
                map.put("state", "err");
                map.put("message", "成员不存在");
                return map;
            }
            if (projectService.isExistMember(pid, mid)) {
                map.put("state", "err");
                map.put("message", "该成员已加进项目");
                return map;
            }

            ProjectMember pm = new ProjectMember();
            pm.setMemberId(mid);
            pm.setProjectId(pid);
            pm.setJob(job);
            pm.setType(ProjectMember.TYPE_MEMBER);
            projectService.addProjectMember(pm);

        } catch (Exception e) {
            map.put("state", "err");
            map.put("message", "添加失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }

    @POST
    @At("/member")
    @Ok("json")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> updateMember(@Param("pmid") int pmid, @Param("mid") int mid, @Param("job") String job) {
        Map<String, Object> map = new HashMap<String, Object>();     
        try {
            ProjectMember pm = projectService.getProjectMember(pmid);
            if (pm == null) {
                map.put("state", "err");
                map.put("message", "项目成员不存在");
                return map;
            }
            if (!memberService.isExistMember(mid)) {
                map.put("state", "err");
                map.put("message", "成员不存在");
                return map;
            }
            if (projectService.isExistMember(pm.getProjectId(), mid) && pm.getMemberId() != mid) {
                map.put("state", "err");
                map.put("message", "该成员已加进项目");
                return map;
            }
            pm.setMemberId(mid);
            pm.setJob(job);
            projectService.updateProjectMember(pm);
        } catch (Exception e) {
            map.put("state", "err");
            map.put("message", "更新失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }
    
    @At("/member/remove/?")
    @Ok("json")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> removeMember(final int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            projectService.removeProjectMember(id);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "fail");
            map.put("message", "操作失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }

    @POST
    @At("/delete")
    @Ok("json")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> delete(@Param("id") int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            Project p = projectService.fetch(id);
            p.setState(Project.STATE_FAIL);
            p.setStateDate(new java.sql.Date(new java.util.Date().getTime()));
            projectService.update(p);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "fail");
            map.put("message", "操作失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }

    @POST
    @At("/finish")
    @Ok("json")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> finish(@Param("id") int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Project p = projectService.fetch(id);
            p.setState(Project.STATE_FINISHED);
            p.setStateDate(new java.sql.Date(new java.util.Date().getTime()));
            projectService.update(p);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "fail");
            map.put("message", "操作失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }

 

    @At("/projects/free/?")
    @Ok("jsp:page.manage.project.list")
    @Aop("pageInterceptor")
    public Map<String, Object> freeList(int page) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (page < 1) {
            page = 1;
        }
        map.put("list",
                projectService.listByFree(memberService.dao().createPager(page,
                Config.MANAGER_PAGE_SIZE)));
        map.put("count", projectService.countByFree());
        map.put("size", Config.MANAGER_PAGE_SIZE);
        map.put("page", page);
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
