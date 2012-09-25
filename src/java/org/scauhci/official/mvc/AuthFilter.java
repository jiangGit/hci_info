/**
 * 
 */
package org.scauhci.official.mvc;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.ServerRedirectView;
import org.scauhci.official.bean.Member;

/**
 * 权限验证的过滤器
 * 
 * @author clarenceau
 *
 */
public class AuthFilter implements ActionFilter {


    private String path;
    
    public AuthFilter( String path) {
        this.path = path;
    }

    public View match(ActionContext actionContext) {
        Method method = actionContext.getMethod();
        Auth auth = method.getAnnotation(Auth.class);
        if (auth == null) {
            return null;
        }
        HttpServletRequest req = actionContext.getRequest();
        Member m = (Member) req.getSession().getAttribute("user");
        if (m != null) {

            int[] tmp = auth.value();

            for (int roleId : tmp) {
                if (m.getRole() == roleId) {
                    return null;
                }
            }
            return new ServerRedirectView(path);
        } else {
            return new ServerRedirectView(path);
        }
    }
}
