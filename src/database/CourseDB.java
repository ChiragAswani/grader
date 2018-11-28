package database;

import DAO.CourseDAO;
import DAO.GradableDAO;
import DAO.StudentDAO;
import Student.Student;
import core.Course;
import grades.Gradable;
import grades.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseDB extends DBconn implements CourseDAO {
    @Override
    public List<Course> findAllCourse() throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from course";
        PreparedStatement stmt= conn.prepareStatement(sql);
        ResultSet rs=    stmt.executeQuery();
        List<Course> CourseList=new ArrayList<Course>();
        while(rs.next()) {
            StudentDB sDB=new StudentDB();
            GradableDB gDB=new GradableDB();
            List<Gradable> glist=gDB.findAllGradableInCourse(rs.getString("courseid"));
            List<Student> sList=sDB.findAllStudentInCourse(rs.getString("courseid"));
            Course c=new Course(
                    rs.getString("courseid"),
                    rs.getString("cname"),
                    sList,
                    glist
            );
            CourseList.add(c);
        }
        DBconn.closeAll(conn, stmt, rs);
        return CourseList;
    }

    @Override
    public Course findOneCourse(String courseID) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from course where courseid=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1,courseID);
        ResultSet rs=    stmt.executeQuery();
        Course course=null;
        if(rs.next()) {
            StudentDB sDB=new StudentDB();
            GradableDB gDB=new GradableDB();
            List<Gradable> glist=gDB.findAllGradableInCourse(rs.getString("courseid"));
            List<Student> sList=sDB.findAllStudentInCourse(rs.getString("courseid"));
            Course c=new Course(
                    rs.getString("courseid"),
                    rs.getString("cname"),
                    sList,
                    glist
            );
            course=c;
        }
        DBconn.closeAll(conn, stmt, rs);
        return course;
    }

    @Override
    public Course insertCourse(Course c) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="INSERT into course(courseid,cname) values (?,?)";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1,c.getCourseID());
        stmt.setString(2,c.getCourseName());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
        if (c.getStudentList()!=null && c.getStudentList().size()!=0) {
            for (Student s : c.getStudentList()) {
                StudentDAO sdb = new StudentDB();
                sdb.addStudentToCourse(s);
            }
        }
        if (c.getGradableList()!=null && c.getGradableList().size()!=0){
            for (int i=0;i<c.getGradableList().size();i++){
                Gradable g=c.getGradableList().get(i);
                GradableDAO gdb=new GradableDB();
                //  duplicate gradable may happen
                int gid=gdb.insertGradable(g);
                g.setgID(gid);
                c.getGradableList().get(i).setgID(gid);
                gdb.addGradableToOneCourse(g);
            }
        }
        return c;
    }

    @Override
    public void deleteCourse(Course c) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="Delete from course where courseid=?";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,c.getCourseID());
        stmt.executeUpdate();
    }

    @Override
    public void updateCourse(Course c) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="Update course set cname=? where=?";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,c.getCourseName());
        stmt.setString(2,c.getCourseID());
        stmt.executeUpdate();
    }
}
