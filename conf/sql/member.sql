/* member.free */
select * from member as m where m.id not in (select pm.member_id from project_member as pm ,project as p where p.id= pm.project_id and p.state =1) and m.state=1 and m.type=3

/* member.free.count */
select count(m.id) from member as m where m.id not in (select pm.member_id from project_member as pm ,project as p where p.id= pm.project_id and p.state =1) and m.state=1 and m.type=3

/* member.department.state */
select * from member where id not in(select member_id from department_member where department_id = @departmentId ) and state = @state

/* member.department.state.count */
select count(id) from member where id not in(select member_id from department_member where department_id = @departmentId ) and state = @state

/* member.projects */
select * from project where id in (select pm.project_id from project_member as pm where pm.member_id= @memberId ) and state= @state

/* member.password.add */
INSERT INTO member_password (member_id, password) VALUES (@memberId , @password)

/* member.password.update */
update member_password set password = @password where member_id = @memberId 

/* member.password.get*/
select password from member_password where member_id = @memberId 

/* member.authentication */
select count(m.id) FROM member m  left join member_password mp on m.id = mp.member_id WHERE m.student_id=@studentId AND mp.password=@password and m.state != 4

/* member.update */
update member set name = @name ,type = @type,state = @state  where id = @id

/* member.update.role */
update member set role = @role where id = @id

/* member.extend.update */
update member_extend set sex =@sex, grade = @grade, academy = @academy, major = @major, mobile = @mobile, mobileshort = @mobileshort, email = @email, birthday = @birthday, homepage = @homepage where id = @id

/* member.list */
select m.* from member m left join department_member md on m.id = md.member_id  $where

/* member.list.count */
select count(m.id) from member m left join department_member md on m.id = md.member_id  $where