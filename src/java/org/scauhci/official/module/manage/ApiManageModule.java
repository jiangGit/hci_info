/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scauhci.official.module.manage;

import java.util.Date;
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
import org.scauhci.official.bean.Api;
import org.scauhci.official.bean.Member;
import org.scauhci.official.mvc.Auth;
import org.scauhci.official.mvc.AuthFilter;
import org.scauhci.official.mvc.TokenFilter;
import org.scauhci.official.service.ApiService;
import org.scauhci.official.util.TokenUtil;
import org.scauhci.official.util.Utils;

/**
 *
 * @author Administrator
 */
@IocBean
@At("/manage/api")
@Filters(
@By(type = AuthFilter.class, args = {"/404.html"}))
public class ApiManageModule {

    @Inject
    ApiService apiService;

    @GET
    @At("/add")
    @Ok("jsp:page.manage.api.add")
    @Auth(Member.ROLE_ADMIN)
    public void toAdd(HttpServletRequest req , HttpSession session) {
        String token=TokenUtil.create(session);
        req.setAttribute("token", token);
    }
    
    @POST
    @At("/add")
    @Ok("json")
    @Aop("transactionInterceptor")
    @Auth(Member.ROLE_ADMIN)
    @Filters({@By(type=TokenFilter.class)})
    public Map<String, Object> add(@Param("::api.") Api api) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            api.setTime( new java.sql.Date(new Date().getTime()));
            api.setState(Api.STATE_OK);
            api.setApiKey(Utils.getMD5(api.getNote()+(new Date()).getTime()));
            apiService.add(api);
            map.put("id", api.getId());
        } catch (Exception e) {
            map.put("state", "fail");
            map.put("message", "操作失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }
    
    @GET
    @At("/?")
    @Ok("jsp:page.manage.api.edit")
    @Auth(Member.ROLE_ADMIN)
    public void toEidt(int id, HttpServletRequest req) {
        Api api = apiService.fetch(id);
        req.setAttribute("api", api);
    }

    @POST
    @At("/?")
    @Ok("json")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> update(int id , @Param("::api.") Api api) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            apiService.updateApi(api);
        } catch (Exception e) {
            map.put("state", "fail");
            map.put("message", "操作失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }

    @POST
    @At("/remove")
    @Ok("json")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> remove(@Param("id") int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            apiService.remove(id);
        } catch (Exception e) {
            map.put("state", "fail");
            map.put("message", "操作失败");
            return map;
        }
        map.put("state", "ok");
        return map;
    }

    @At("/list")
    @Ok("jsp:page.manage.api.list")
    @Auth(Member.ROLE_ADMIN)
    public Map<String, Object> list() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", apiService.list());
        return map;
    }
}
