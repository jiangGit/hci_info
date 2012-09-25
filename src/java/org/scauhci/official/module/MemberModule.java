package org.scauhci.official.module;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
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
import org.scauhci.official.bean.DepartmentMember;
import org.scauhci.official.bean.Member;
import org.scauhci.official.bean.MemberExtend;
import org.scauhci.official.bean.Project;
import org.scauhci.official.bean.ProjectMember;
import org.scauhci.official.bean.condition.MemberCondition;
import org.scauhci.official.mvc.Auth;
import org.scauhci.official.mvc.AuthFilter;
import org.scauhci.official.service.DepartmentService;
import org.scauhci.official.service.MemberService;
import org.scauhci.official.util.GetInfoUtil;
import org.scauhci.official.util.ImageZip;
import org.scauhci.official.util.PrintImage;
import org.scauhci.official.util.Utils;

@IocBean
@Fail("redirect:/404.html")
@Filters(
@By(type = AuthFilter.class, args = {"/404.html"}))
public class MemberModule {

    @Inject
    MemberService memberService;
    @Inject
    DepartmentService departmentService;

//	MemberLucene memberLucene=MemberLucene.getInstance();
    @At("/member/?")
    @Ok("smart:page.member.details")
    @Auth({Member.ROLE_COMMON , Member.ROLE_ADMIN })
    public Map<String, Object> get(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        Member m = memberService.getMember(id);
        map.put("member", m);
        map.put("department", departmentService.getDepartmentByMember(id));
        map.put("developing", memberService.project(id, Project.STATE_DEVELOPING));
        map.put("finished", memberService.project(id, Project.STATE_FINISHED));
        return map;
    }

    @GET
    @At("/member/avatar/?")
    @Auth({Member.ROLE_COMMON , Member.ROLE_ADMIN })
    public void avatar(String id, @Param("thumb") boolean thumb,
            HttpServletRequest req, HttpServletResponse res) {
        PrintImage.writeAvatar(id, thumb, req, res);
    }

    @At("/member/all")
    @Ok("smart:page.manage.member.add")
    @Auth({Member.ROLE_COMMON , Member.ROLE_ADMIN })
    public Map<String, Object> getAll() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", memberService.listAll(null));
        return map;
    }

    @At("/member/list")
    @Ok("smart:page.member.list")
    @Aop("pageInterceptor")
    @Auth({Member.ROLE_COMMON , Member.ROLE_ADMIN })
    public Map<String, Object> list(@Param("..") MemberCondition condition) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (condition.getPage() == null || condition.getPage() < 1) {
            condition.setPage(1);
        }
        if (condition.getCount() == null || condition.getCount() < 1) {
            condition.setCount(Config.MANAGER_PAGE_SIZE);
        }
        map.put("condition", condition);
        map.put("departmentList", departmentService.getAll());
        map.put("list", memberService.list(condition));
        map.put("count", memberService.count(condition));
        map.put("size", condition.getCount());
        map.put("page", condition.getPage());
        return map;
    }
}
