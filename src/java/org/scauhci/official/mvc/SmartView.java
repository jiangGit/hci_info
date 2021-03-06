package org.scauhci.official.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.nutz.json.JsonFormat;

import org.nutz.mvc.View;
import org.nutz.mvc.view.JspView;
import org.nutz.mvc.view.UTF8JsonView;
import org.nutz.web.ajax.Ajax;
import org.nutz.web.ajax.AjaxView;

/**智能视图,通过用户访问的URL的后缀来判断需要执行的视图*/
public class SmartView implements View {

	public SmartView(String viewValue) {
		this.viewValue = viewValue;
	}

	private String viewValue;

	@SuppressWarnings("rawtypes")
	public void render(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws Throwable {
		String uri = req.getRequestURI();
		if (uri.endsWith(".json")) {
                        new UTF8JsonView(new JsonFormat()).render(req, resp, obj);
//			if (obj instanceof Map && ((Map)obj).containsKey("error")) {
//				obj = Ajax.fail().setData(obj);
//			}
//			new AjaxView().render(req, resp, obj);
		}
		else
			new JspView(viewValue).render(req, resp, obj); //这里默认跑jsp
	}

}
