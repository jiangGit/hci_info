package org.scauhci.official.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nutz.aop.InterceptorChain;
import org.nutz.aop.MethodInterceptor;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Param;
import org.scauhci.official.Config;

@SuppressWarnings("unchecked")
public class PageInterceptor implements MethodInterceptor {

    @Override
    public void filter(InterceptorChain chain) throws Throwable {

        chain.doChain();
        Map<String, Object> map = (Map<String, Object>) chain.getReturn();

        String url = this.getUrl(chain);
        int page, size, count, pageCount;
        if (map.get("size") == null) {
            map.put("size", Config.MANAGER_PAGE_SIZE);
        }
        if (map.get("page") == null) {
            map.put("page", this.getPage(chain));
        }
        page = (Integer) map.get("page");
        size = (Integer) map.get("size");
        count = (Integer) map.get("count");
        pageCount = count % size == 0 ? count / size : count / size + 1;
        map.put("url", url);
        map.put("frontPage", page-1);
        map.put("nextPage", page+1);
        map.put("lastPage", pageCount);
        map.put("pageCount", pageCount);
    }

    private String getUrl(InterceptorChain chain) {

        Object[] args = chain.getArgs();
        Method m = chain.getCallingMethod();
        String name =chain.getCallingObj().toString();
        name = name.substring(0, name.indexOf("$"));
        Class o = null;
        try {
            System.out.println("call object:"+name);
            o = Class.forName(name);
        } catch (ClassNotFoundException ex) {
            
        }

        StringBuffer sb = new StringBuffer();
        At oAt = (At) o.getAnnotation(At.class);
        At mAt = m.getAnnotation(At.class);

        //这里at不能设置成多个路径，默认使用第一个
        if (oAt != null) {
            sb.append(oAt.value()[0]);
        }
       // System.out.println(o.getAnnotations().length);
        
        if (mAt != null) {
            sb.append(mAt.value()[0]);
        }

        for (Object a : args) {
            if (sb.indexOf("?") != -1) {
                sb.replace(sb.indexOf("?"), sb.indexOf("?") + 1, a.toString());
            } else {
                break;
            }
        }
        return sb.toString();
    }

    @SuppressWarnings("unused")
    private int getPage(InterceptorChain chain) {
        Annotation[][] annotation = chain.getCallingMethod().getParameterAnnotations();
        Object[] args = chain.getArgs();
        ForArgs:
        for (int i = 0; i < args.length; i++) {
            ForAnnotation:
            for (Annotation a : annotation[i]) {
                if (a instanceof Param) {
                    if (((Param) a).value().equals("page")) {
                        return (Integer) args[i];
                    } else {
                        continue ForArgs;
                    }
                }
            }
        }
        return 1;
    }
}
