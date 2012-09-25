/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scauhci.official.mvc;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.nutz.json.JsonFormat;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.View;
import org.nutz.mvc.view.UTF8JsonView;

/**
 *
 * @author Administrator
 */
public class ApiAuthFilter implements ActionFilter{

    public View match(ActionContext ac) {
        List<String> list =(List<String>) ac.getServletContext().getAttribute("API_KEYS");
         HttpServletRequest req = ac.getRequest();
         String key = req.getParameter("key");
         for (String s : list) {
            if(s.equals(key)){
                return null;
            }
        }
        return new UTF8JsonView(new JsonFormat());
    }
    
}
