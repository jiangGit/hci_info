package org.scauhci.official.service;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.scauhci.official.bean.Member;
import org.scauhci.official.bean.Project;
import org.scauhci.official.bean.ProjectMember;
import org.scauhci.official.bean.condition.ProjectCondition;

@IocBean(args = {"refer:dao"})
public class ProjectService extends BasicMysqlService<Project> {

    public ProjectService(Dao dao) {
        super(dao);
    }

    public Project getProjectWithDepartment(int id) {
        return this.getEntity(id, "department");
    }

    public void updatePart(Project project) {
    }

    public ProjectMember getProjectMember(int pId, int mId) {
        return this.dao().fetch(ProjectMember.class, Cnd.where("project_id", "=", pId).and("member_id", "=", mId));
    }

    public ProjectMember getProjectMember(int id) {
        return this.dao().fetch(ProjectMember.class, id);
    }

    public void addProjectMember(ProjectMember pm) {
        this.dao().insert(pm);
    }

    public void removeProjectMember(int id) {
        this.dao().delete(ProjectMember.class, id);
    }

    public void updateProjectMember(ProjectMember pm) {
        this.dao().update(pm);
    }

    public List<ProjectMember> projectMembers(int id) {
        List<ProjectMember> list = this.dao().query(ProjectMember.class, Cnd.where("project_id", "=", id));
        for (ProjectMember pm : list) {
            this.dao().fetchLinks(pm, "member");
        }
        return list;
    }

    public List<ProjectMember> projectMembers(int id, int type) {
        List<ProjectMember> list = this.dao().query(ProjectMember.class, Cnd.where("project_id", "=", id).and("type", "=", type));
        for (ProjectMember pm : list) {
            this.dao().fetchLinks(pm, "member");
        }
        return list;
    }

    public void clearMembers(int id, int type) {
        dao().clear(ProjectMember.class, Cnd.where("project_id", "=", id).and("type", "=", type));
    }

    public boolean isExistMember(int pid, int mid) {
        return dao().count(ProjectMember.class, Cnd.where("project_id", "=", pid).and("member_id", "=", mid)) > 0;
    }

    public boolean isExistMemberType(int pid, int type) {
        return dao().count(ProjectMember.class, Cnd.where("project_id", "=", pid).and("type", "=", type)) > 0;
    }

    public int countAll() {
        return dao().count(Project.class, null);
    }

    public List<Project> listAll(Pager pager) {
        return getList(null, pager);
    }

    public int countByFree() {
        Sql sql = dao().sqls().create("project.free.count");
        sql.setCallback(Sqls.callback.integer());
        dao().execute(sql);
        return sql.getInt();
    }

    public List<Project> listByFree(Pager pager) {
        Sql sql = dao().sqls().create("project.free");
        sql.setCallback(Sqls.callback.entities());
        sql.setPager(pager);
        sql.setEntity(dao().getEntity(Project.class));
        dao().execute(sql);
        return sql.getList(Project.class);
    }

    public List<Project> listByMember(int memberId, int state) {
        Sql sql = dao().sqls().create("project.list.by.member");
        sql.params().set("memberId", memberId);
        sql.params().set("state", state);
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(dao().getEntity(Project.class));
        dao().execute(sql);
        return sql.getList(Project.class);
    }

    public List<Project> list(ProjectCondition pCnd) {

        Cnd cnd = Cnd.where("state", "!=", Project.STATE_FAIL);
        Pager pager = null;
        if (pCnd.getDepartmentId() != null && pCnd.getDepartmentId() != 0) {
            cnd.and("department_id", "=", pCnd.getDepartmentId());
        }
        if (pCnd.getState() != null && pCnd.getState() != 0) {
            cnd.and("state", "=", pCnd.getState());
        }
        if (pCnd.getAll() == null || !pCnd.getAll()) {
            pager = dao().createPager(pCnd.getPage(), pCnd.getCount());
        }
        return this.getList(cnd, pager);
    }

    public int count(ProjectCondition pCnd) {
        Cnd cnd = Cnd.where("state", "!=", Project.STATE_FAIL);
        if (pCnd.getDepartmentId() != null && pCnd.getDepartmentId() != 0) {
            cnd.and("department_id", "=", pCnd.getDepartmentId());
        }
        if (pCnd.getState() != null && pCnd.getState() != 0) {
            cnd.and("state", "=", pCnd.getState());
        }
        return dao().count(Project.class, cnd);
    }

    public void fetchProjectMember(List<Project> list) {
        for (Project p : list) {
            this.dao().fetchLinks(p, "projectMembers");
            for (ProjectMember pm : p.getProjectMembers()) {
                this.dao().fetchLinks(pm, "member");
            }
        }
    }

    public void fetchProjectMember(Project p) {
        this.dao().fetchLinks(p, "projectMembers");
        for (ProjectMember pm : p.getProjectMembers()) {
            this.dao().fetchLinks(pm, "member");
        }
    }
}
