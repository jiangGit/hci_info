/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scauhci.official.module.manage;

import java.util.HashMap;
import java.util.Map;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.scauhci.official.bean.Member;
import org.scauhci.official.mvc.Auth;
import org.scauhci.official.mvc.AuthFilter;
import org.scauhci.official.service.MemberService;

/**
 *
 * @author Administrator
 */
@IocBean
@At("/manage/admin")
@Filters(
@By(type = AuthFilter.class, args = {"/404.html"}))
public class AdminManageModule {

    @Inject
    MemberService memberService;

    @GET
    @At("/add")
    @Ok("jsp:page.manage.member.add")
    @Auth(Member.ROLE_ADMIN)
    public void toAdd() {
    }

    @POST
    @At("/add")
    @Ok("json")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> add(@Param("id") int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            memberService.updateRole(id, Member.ROLE_ADMIN);
            map.put("state", "ok");
        } catch (Exception e) {
            e.printStackTrace();;
            map.put("state", "err");
            map.put("message", "更新出错");
        }
        return map;
    }

    @POST
    @At("/remove")
    @Ok("json")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> remove(@Param("id") int id) {
         Map<String, Object> map = new HashMap<String, Object>();
        try {
            memberService.updateRole(id, Member.ROLE_COMMON);
            map.put("state", "ok");
        } catch (Exception e) {
            map.put("state", "err");
            map.put("message", "更新出错");
        }
        return map;
    }


    @At("/list")
    @Ok("smart:page.manage.admin.list")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> list() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", memberService.listAdmin());
        return map;
    }
}
