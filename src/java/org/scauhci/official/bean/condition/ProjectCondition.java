/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scauhci.official.bean.condition;

/**
 *
 * @author Administrator
 */
public class ProjectCondition {

    private Integer id;
    private Integer departmentId;
    private Integer state;
    private Integer type;
    private Integer count;
    private Integer page;
    private Boolean all;
    private String with;
    
    public Boolean getAll() {
        return all;
    }

    public void setAll(Boolean all) {
        this.all = all;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }
    
    
}
