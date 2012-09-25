package org.scauhci.official.module.manage;

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
import org.scauhci.official.bean.condition.MemberCondition;
import org.scauhci.official.mvc.Auth;
import org.scauhci.official.mvc.AuthFilter;
import org.scauhci.official.mvc.TokenFilter;
import org.scauhci.official.service.DepartmentService;
import org.scauhci.official.service.MemberService;
import org.scauhci.official.util.GetInfoUtil;
import org.scauhci.official.util.GetPhotoUtil;
import org.scauhci.official.util.ImageZip;
import org.scauhci.official.util.TokenUtil;
import org.scauhci.official.util.Utils;

@IocBean
@Fail("json")
@At("/manage")
@Filters(
@By(type = AuthFilter.class, args = {"/404.html"}))
public class MemberManageModule {

    @Inject
    MemberService memberService;
    @Inject
    DepartmentService departmentService;

//	MemberLucene memberLucene=MemberLucene.getInstance();
    @GET
    @At("/member/add")
    @Ok("smart:page.manage.member.add")
    @Auth(Member.ROLE_ADMIN)
    public void toAdd(HttpServletRequest req , HttpSession session) {
        String token=TokenUtil.create(session);
        req.setAttribute("token", token);
    }

    @POST
    @At("/member/add")
    @Ok("json")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    @Filters({@By(type=TokenFilter.class)})
    public Map<String, Object> add(@Param("::member.") final Member member,
            @Param("::extend.") final MemberExtend me) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            member.setExtend(me);
            memberService.insertMember(member);
            memberService.addPassword(member.getId(), Utils.getMD5(Config.INIT_PASSWORD));
            map.put("id", member.getId());
            try{//获取头像结果不影响结果
                GetPhotoUtil.getPhoto(member.getStudentId());
            }catch(Exception ex){
                
            }
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
    @At("/member/checkout/?")
    @Ok("json")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> checkout(String id, HttpServletRequest req) {

        Map<String, Object> map = new HashMap<String, Object>();
        Member member;
        MemberExtend me;
        try {
            if (memberService.isExist(id)) {
                member = memberService.getMember(id);
                me = member.getExtend();
                map.put("isExist", true);
                map.put("id", member.getId());
            } else {
                member = GetInfoUtil.getMember(id);
                me = member.getExtend();
                map.put("isExist", false);
            }
            map.put("member", member);
            map.put("extend", me);
            map.put("state", "ok");
        } catch (Exception e) {
            map.put("state", "err");
            map.put("message", "获取数据失败");
            map.put("errMessage", e.getMessage());
        }

        return map;
    }

    @GET
    @At("/member/?")
    @Ok("jsp:page.manage.member.edit")
    @Auth(Member.ROLE_ADMIN)
    public void edit(int id, HttpServletRequest req) {
        Member member = memberService.getMember(id);
        MemberExtend me = member.getExtend();
        DepartmentMember dm = departmentService.getDepartmentMember(member.getId());
        if (dm == null) {
            dm = new DepartmentMember();
        }
        List departmentList = departmentService.getAll();
        req.setAttribute("member", member);
        req.setAttribute("extend", me);
        req.setAttribute("departmentList", departmentList);
        req.setAttribute("departmentMember", dm);
    }

    @POST
    @At("/member/?")
    @Ok("json")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> edit(int id, @Param("::member.") final Member member,
            @Param("::extend.") final MemberExtend me,
            @Param("::departmentMember.") final DepartmentMember dm,
            HttpServletRequest req) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            member.setExtend(me);
            member.setExtendId(me.getId());
            dm.setMemberId(member.getId());
            if(dm.getDepartmentId() == 0){            
                if(dm.getId() == 0){
                     //没有选择部门
                }else{
                    //从部门移除
                    departmentService.deleteMember(dm);
                }
            }else if (dm.getId() == 0) {
                departmentService.addMember(dm);
            } else {
                departmentService.updateMember(dm);
            }
            memberService.updatePart(member);

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
    @At("/member/avatar/?")
    @Auth(Member.ROLE_ADMIN)
    @AdaptBy(type = UploadAdaptor.class, args = {"ioc:myUpload"})
    public void avatar(String id,@Param("successCode") String successCode,@Param("failCode") String failCode, @Param("errCode") String errCode,
        @Param("avatar") TempFile avatar,HttpServletResponse res) throws IOException {
        PrintWriter out = res.getWriter();
        if (avatar == null || id == null || "".equals(id)) {

            out.write("<script>"+failCode+"</script>");
            return;

        }
        File tempFile = avatar.getFile();
        File newFile = new File(Config.avatarPath + "/" + id
                + avatar.getMeta().getFileExtension());
        try {

            Files.deleteFile(newFile);
            Files.move(tempFile, newFile);
            ImageZip.zipImageFile(newFile.getAbsolutePath(), Config.avatarPath, "thumb_" + id + ".jpg");

        } catch (IOException ex) {
            out.write("<script>"+errCode+"</script>");
            return;
        }
        out.write("<script>"+successCode+"</script>");
    }
   
    
    @At("/members")
    @Ok("jsp:page.manage.member.list")
    @Aop("pageInterceptor")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> list( @Param("..") MemberCondition condition) {
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (condition.getPage() ==null || condition.getPage() < 1) {
            condition.setPage(1);
        }
        if(condition.getCount() ==null || condition.getCount()<1){
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
    

    @At("/member/delete/?")
    @Ok("json")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> delete(final int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            Member m = memberService.fetch(id);
            m.setState(Member.STATE_LEAVE);
            memberService.update(m);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "fail");
            map.put("message", "删除失败");
            return map;
        }
        map.put("state", "ok");
        return map;

    }



    @At("/members/free")
    @Ok("jsp:page.manage.member.list")
    public Map<String, Object> freeList(@Param("page") int page) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (page < 1) {
            page = 1;
        }
        map.put("list",
                memberService.listByFree(memberService.dao().createPager(page,
                Config.MANAGER_PAGE_SIZE)));
        map.put("count", memberService.countByFree());
        map.put("size", Config.MANAGER_PAGE_SIZE);
        map.put("page", page);
        return map;
    }


    @POST
    @At("/member/password")
    @Ok("json")
    public Map<String, Object> resetPassword(@Param("id") int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            memberService.updatePassword(id, Utils.getMD5(Config.INIT_PASSWORD));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", "fail");
            map.put("message", "重置密码失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }


    @At("/member/search/")
    @Ok("jsp:page.manage.member.list")
    public Map<String, Object> search(@Param("wd") String s, @Param("page") int page) {
        Map<String, Object> map = new HashMap<String, Object>();
//		map.put("list", memberLucene.searchIndex(s,memberService.dao().createPager(page,Config.MANAGER_PAGE_SIZE)));
//		map.put("count", memberLucene.countSearchIndex(s));
        map.put("size", Config.MANAGER_PAGE_SIZE);
        map.put("page", page);
        return map;
    }
}
