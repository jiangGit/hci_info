/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scauhci.official.service;

import java.util.List;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.scauhci.official.bean.Api;

/**
 *
 * @author Administrator
 */
@IocBean(args = {"refer:dao"})
public class ApiService extends BasicMysqlService<Api> {

    public ApiService(Dao dao) {
        super(dao);
    }
    
    public List<String> keys(){
        Sql sql = dao().sqls().create("api.keys");
        sql.setCallback(Sqls.callback.strList());
        dao().execute(sql);
        return sql.getList(String.class);
    }
    
    public List<Api> list(){
        return dao().query(Api.class, Cnd.where("state", "=", Api.STATE_OK));
    }
    
    public void updateApi(Api api){
        Sql sql = dao().sqls().create("api.update");
        sql.params().set("id", api.getId());
        sql.params().set("note", api.getNote());
        dao().execute(sql);
    }
    
    public void remove(int id){
        Sql sql = dao().sqls().create("api.remove");
        sql.params().set("id", id);
        dao().execute(sql);
    }
    
}
