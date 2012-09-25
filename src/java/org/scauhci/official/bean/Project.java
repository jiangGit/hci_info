package org.scauhci.official.bean;

import java.sql.Date;
import java.util.List;

import org.nutz.dao.entity.annotation.*;

@Table("project")
public class Project implements java.io.Serializable{
	
	//项目开发中
	public static int STATE_DEVELOPING=1;
	//项目结题
	public static int STATE_FINISHED=2;
	//项目难产
	public static int STATE_FAIL=3;
	
	
	
	@Id
	@Column("id")
	private int id;
	@Column("name")
	private String name;
	@Column("detail")
	private String detail;
	@Column("state_date")
	private Date stateDate;
	@Column("end_date")
	private Date endDate;
	@Column("state")
	private int state;
	@Column("is_public")
	private boolean isPublic;
	@Column("department_id")
	private int departmentId;



	@Many(target = ProjectMember.class, field = "projectId")
	private transient  List<ProjectMember> projectMembers;

	@ManyMany(target = Member.class, relation = "project_member", from = "project_id", to = "member_id")
	private List<Member> members;

	@One(target = Department.class, field = "departmentId")
	private Department department;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getStateDate() {
		return stateDate;
	}

	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}


	public List<ProjectMember> getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(List<ProjectMember> projectMembers) {
		this.projectMembers = projectMembers;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}