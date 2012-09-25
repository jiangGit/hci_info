/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scauhci.official.bean;

import java.sql.Date;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 *
 * @author Administrator
 */
@Table("api")
public class Api {

    // 有效
    public static final int STATE_OK = 1;
    // 无效
    public static final int STATE_FAIL = 0;
    
    @Id
    @Column("id")
    private int id;
    @Column("api_key")
    private String apiKey;
    @Column("note")
    private String note;
    @Column("state")
    private int state;
    @Column("time")
    private Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
    
}
