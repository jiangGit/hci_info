package org.scauhci.official.module;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.scauhci.official.Config;
import org.scauhci.official.bean.Department;
import org.scauhci.official.bean.Member;
import org.scauhci.official.service.DepartmentService;
import org.scauhci.official.service.MemberService;
import org.scauhci.official.service.ProjectService;


@IocBean
public class DepartmentModule {

	@Inject
	MemberService memberService;
	@Inject
	ProjectService projectService;
	@Inject
	DepartmentService departmentService;

	@At("/department/new")
	@Ok("jsp:page.manage.department.add")
	public void toAdd(HttpServletRequest req) {

	}

	@At("/department/edit/?")
	@Ok("jsp:page.manage.department.edit")
	public void toEidt(int id, HttpServletRequest req) {
		Department d = departmentService.fetch(id);
		req.setAttribute("department", d);
	}

	@POST
	@At("/department/?")
	@Ok("json")
	@Aop("transactionInterceptor")
	public Map<String, Object> eidt(int id, @Param("::department.") final Department department) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (id == 0) {
						departmentService.add(department);
			} else {
						departmentService.update(department);
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

	@GET
	@At("/department/delete/?")
	@Ok("json")
	@Aop("transactionInterceptor")
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

	@GET
	@At("/department/?")
	@Ok("json")
	public Map<String, Object> get(int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("department", departmentService.fetch(id));
		return map;
	}

	@At("/departments")
	@Ok("jsp:page.manage.department.list")
	public Map<String, Object> list() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", departmentService.getAll());
		return map;
	}




}
