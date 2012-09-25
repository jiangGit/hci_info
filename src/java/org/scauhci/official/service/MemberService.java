package org.scauhci.official.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.impl.sql.callback.FetchEntityCallback;
import org.nutz.dao.impl.sql.callback.FetchIntegerCallback;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.scauhci.official.bean.Member;
import org.scauhci.official.bean.Project;
import org.scauhci.official.bean.ProjectMember;
import org.scauhci.official.bean.condition.MemberCondition;

@IocBean(args = {"refer:dao"})
public class MemberService extends BasicMysqlService<Member> {

    public MemberService(Dao dao) {
        super(dao);
    }

    public void insertMember(Member m) {
        this.insertEntity(m, "extend");
    }

    public void updateMember(Member m) {
        this.update(m);
    }

    public void updateAll(Member m) {
        this.updateEntity(m, "extend");
    }

    public void updatePart(Member m) {
        Sql sql = dao().sqls().create("member.update");
        sql.params().set("name", m.getName());
        sql.params().set("type", m.getType());
        sql.params().set("state", m.getState());
        sql.params().set("id", m.getId());
        dao().execute(sql);

        sql = dao().sqls().create("member.extend.update");
        sql.params().set("sex", m.getExtend().getSex());
        sql.params().set("grade", m.getExtend().getGrade());
        sql.params().set("academy", m.getExtend().getAcademy());
        sql.params().set("major", m.getExtend().getMajor());
        sql.params().set("mobile", m.getExtend().getMobile());
        sql.params().set("mobileshort", m.getExtend().getMobileshort());
        sql.params().set("email", m.getExtend().getEmail());
        sql.params().set("birthday", m.getExtend().getBirthday());
        sql.params().set("homepage", m.getExtend().getHomepage());
        sql.params().set("id", m.getExtend().getId());
        dao().execute(sql);
    }

    public void updateRole(int id, int role) {
        Sql sql = dao().sqls().create("member.update.role");
        sql.params().set("role", role);
        sql.params().set("id", id);
        dao().execute(sql);
    }

    public int count() {
        return dao().count(Member.class, Cnd.where("state", "!=", Member.STATE_LEAVE));
    }

    public int countByRole(int role) {
        return dao().count(Member.class, Cnd.where("state", "=", role));
    }

    public int countByFree() {
        Sql sql = dao().sqls().create("member.free");
        sql.setCallback(Sqls.callback.integer());
        dao().execute(sql);
        return sql.getInt();
    }
    
    public int count(MemberCondition mCnd){
        Sql sql = dao().sqls().create("member.list.count");
        Cnd cnd = Cnd.where("state", "!=", Member.STATE_LEAVE);
        if (mCnd.getDepartmentId() != null && mCnd.getDepartmentId() != 0) {
            cnd.and("md.department_id", "=", mCnd.getDepartmentId());
        }
        if (mCnd.getState() != null && mCnd.getState() != 0) {
            cnd.and("m.state", "=", mCnd.getState());
        }
        if (mCnd.getType() != null && mCnd.getType() != 0) {
            cnd.and("m.type", "=", mCnd.getType());
        }
        
        sql.vars().set("where", cnd.toString());
        sql.setCallback(Sqls.callback.integer());
        dao().execute(sql);
        return sql.getInt();
    }

    public List<Member> listAll(Pager pager) {
        return this.getList(Cnd.where("state", "!=", Member.STATE_LEAVE), pager);
    }

    public List<Member> list(MemberCondition mCnd) {
        Sql sql = dao().sqls().create("member.list");
        Cnd cnd = Cnd.where("state", "!=", Member.STATE_LEAVE);
        if (mCnd.getDepartmentId() != null && mCnd.getDepartmentId() != 0) {
            cnd.and("md.department_id", "=", mCnd.getDepartmentId());
        }
        if (mCnd.getState() != null && mCnd.getState() != 0) {
            cnd.and("m.state", "=", mCnd.getState());
        }
        if (mCnd.getType() != null && mCnd.getType() != 0) {
            cnd.and("m.type", "=", mCnd.getType());
        }
        if (mCnd.getAll() == null || !mCnd.getAll()) {
            sql.setPager(dao().createPager(mCnd.getPage(), mCnd.getCount()));
        } else {
        }
        sql.vars().set("where", cnd.toString());
        sql.setCallback(Sqls.callback.entities());
        sql.setEntity(dao().getEntity(Member.class));
        dao().execute(sql);
        return sql.getList(Member.class);
    }

    public List<Member> listAdmin() {
        return this.getList(Cnd.where("role", "=", Member.ROLE_ADMIN).and("state", "!=", Member.STATE_LEAVE), null);
    }

    public boolean isExist(String id) {
        return this.getEntity(Cnd.where("student_id", "=", id), null) != null;
    }

    public Member getMember(int id) {
        return this.getEntity(Cnd.where("id", "=", id), "extend");
    }

    public Member getMember(String id) {
        return this.getEntity(Cnd.where("student_id", "=", id), "extend");
    }

    public List<Member> find(String s) {
        return null;
    }

    public List<Member> listByFree(Pager pager) {
        Sql sql = dao().sqls().create("member.free");
        sql.setCallback(Sqls.callback.entities());
        sql.setPager(pager);
        sql.setEntity(dao().getEntity(Member.class));
        dao().execute(sql);
        return sql.getList(Member.class);
    }

    public List<Project> project(int memberId, int state) {
        Sql sql = dao().sqls().create("member.projects");
        sql.setCallback(Sqls.callback.entities());
        sql.params().set("state", state);
        sql.params().set("memberId", memberId);
        sql.setEntity(dao().getEntity(Project.class));
        dao().execute(sql);
        return sql.getList(Project.class);
    }
    
   
    public List<ProjectMember> projectMembers(int id, int type) {
        List<ProjectMember> list = this.dao().query(ProjectMember.class, Cnd.where("member_id", "=", id).and("type", "=", type));
        for (ProjectMember pm : list) {
            this.dao().fetchLinks(pm, "project");
        }
        return list;
    }

    public void addPassword(int memberId, String password) {
        Sql sql = dao().sqls().create("member.password.add");
        sql.params().set("password", password);
        sql.params().set("memberId", memberId);
        dao().execute(sql);
    }

    public void updatePassword(int memberId, String password) {
        Sql sql = dao().sqls().create("member.password.update");
        sql.params().set("password", password);
        sql.params().set("memberId", memberId);
        dao().execute(sql);
    }

    public String getPassword(int memberId) {
        Sql sql = dao().sqls().create("member.password.update");
        sql.setCallback(Sqls.callback.str());
        sql.params().set("memberId", memberId);
        dao().execute(sql);
        return sql.getString();
    }

    public boolean  authentication(String studentId, String password) {
        Sql sql = dao().sqls().create("member.authentication");
        sql.setCallback(new FetchIntegerCallback());
        sql.params().set("studentId", studentId).set("password", password);
        dao().execute(sql);
        return sql.getInt() ==1;
        
    }

    public boolean isExistMember(int id) {
        return dao().count(Member.class, Cnd.where("id", "=", id).and("state", "!=", Member.STATE_LEAVE)) != 0;
    }
    
    public boolean isExistMember(String studentId) {
        return dao().count(Member.class, Cnd.where("student_id", "=", studentId).and("state", "!=", Member.STATE_LEAVE)) != 0;
    }
}
