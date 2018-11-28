package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DAO.StudentDAO;
import Student.Student;
import grades.Grade;

public class StudentDB extends DBconn implements StudentDAO {
    @Override
    public List<Student> findAllStudentInCourse(int courseID) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from student s, register r where r.studentid=s.studentid and r.courseid=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,courseID);
        ResultSet rs=    stmt.executeQuery();
        List<Student> StudentList=new ArrayList<Student>();
        while(rs.next()) {
            GradeDB gDB=new GradeDB();
            List<Grade> glist=gDB.findOneStudentAllGradeInOneCourse(rs.getString("studentid"),courseID);
            Student s=new Student(
                    courseID,
                    rs.getString("studentid"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("customized"),
                    glist
            );
            StudentList.add(s);
        }
        DBconn.closeAll(conn, stmt, rs);
        return StudentList;
    }

    @Override
    public Student findOneStudentInCourse(int courseID, String studentID) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from student s, register r where r.studentid=s.studentid and r.courseid=? and r.studentid=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,courseID);
        stmt.setString(2,studentID);
        ResultSet rs=    stmt.executeQuery();
        Student student=null;
        if (rs.next()){
            GradeDB gDB=new GradeDB();
            List<Grade> glist=gDB.findOneStudentAllGradeInOneCourse(rs.getString("studentid"),courseID);
            Student s=new Student(
                    courseID,
                    rs.getString("studentid"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getInt("customized"),
                    glist
            );
            student=s;
        }
        DBconn.closeAll(conn, stmt, rs);
        return student;
    }


    @Override
    public boolean checkStudentInDB(Student s) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="select * from student where studentid=?";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1, s.getStudentID());
        ResultSet rs=  stmt.executeQuery();
        if (rs.next()){
            System.out.println("no student before");
            return true;
        }
        return false;
    }

    @Override
    public void addStudentToCourse(Student s) throws Exception{
        Connection conn=DBconn.getConnection();
        if (!checkStudentInDB(s)){
            System.out.println("insert student");
            insertStudent(s);
        }
        String sql="INSERT INTO register(studentid,courseid) values(?,?)";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,s.getStudentID());
        stmt.setInt(2,s.getCourseID());
        stmt.executeUpdate();
        List<Grade> glist=s.getGradeList();
        if (glist!=null){
            for (Grade g:glist){
                String sql2="Insert into grade(gradableid,studentid,weighting,sscore) values(?,?,?,?)";
                PreparedStatement stmt2=conn.prepareStatement(sql2);
                stmt2.setInt(1,g.getgID());
                stmt2.setString(2,g.getsID());
                stmt2.setBigDecimal(3,g.getWeighting());
                stmt2.setBigDecimal(4,g.getScore());
                // stmt2.setInt(3,t.gettID());
                stmt2.executeUpdate();
                DBconn.close(stmt2);
            }
        }
        DBconn.closeAll(conn,stmt);
    }

    @Override
    public void removeStudentFromCourse(Student s) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="Delete from register where studentid=? and courseid=? ";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,s.getStudentID());
        stmt.setInt(2,s.getCourseID());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }

    @Override
    public void insertStudent(Student s) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="INSERT INTO student(studentid,first_name,last_name,type,customized) VALUES(?,?,?,?,?)";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1,s.getStudentID());
        stmt.setString(2,s.getFirstName());
        stmt.setString(3,s.getLastName());
        stmt.setString(4,s.getType());
        stmt.setInt(5,s.getCustomized());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }
    @Override
    public void updateStudent(Student s) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="update Student set last_name=?, first_name=? , type=?, customized=? where  studentid=?";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,s.getLastName());
        stmt.setString(2,s.getFirstName());
        stmt.setString(3,s.getType());
        stmt.setInt(4,s.getCustomized());
        stmt.setString(5,s.getStudentID());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }
    @Override
    public void deleteStudent(Student s) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="delete from Studentwhere  studentid=?";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,s.getStudentID());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }

}