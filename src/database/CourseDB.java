package database;

import DAO.CourseDAO;
import DAO.GradableDAO;
import DAO.StudentDAO;
import Student.Student;
import core.Course;
import grades.Category;
import grades.Gradable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
            CategoryDB cDB=new CategoryDB();
            List<Category> cList=cDB.findAllCategoryInOneCourse(rs.getInt("courseid"));
            List<Gradable> glist=gDB.findAllGradableInCourse(rs.getInt("courseid"));
            List<Student> sList=sDB.findAllStudentInCourse(rs.getInt("courseid"));
            Course c=new Course(
                    rs.getInt("courseid"),
                    rs.getString("cname"),
                    sList,
                    glist,
                    cList
            );
            c.setArchived(rs.getInt("archived"));
            CourseList.add(c);
        }
        DBconn.closeAll(conn, stmt, rs);
        return CourseList;
    }

    @Override
    public List<Course> findAllUnArchivedCourse() throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from course where archived=0";
        PreparedStatement stmt= conn.prepareStatement(sql);
        ResultSet rs=    stmt.executeQuery();
        List<Course> CourseList=new ArrayList<Course>();
        while(rs.next()) {
            StudentDB sDB=new StudentDB();
            GradableDB gDB=new GradableDB();
            CategoryDB cDB=new CategoryDB();
            List<Category> cList=cDB.findAllCategoryInOneCourse(rs.getInt("courseid"));
            List<Gradable> glist=gDB.findAllGradableInCourse(rs.getInt("courseid"));
            List<Student> sList=sDB.findAllStudentInCourse(rs.getInt("courseid"));
            Course c=new Course(
                    rs.getInt("courseid"),
                    rs.getString("cname"),
                    sList,
                    glist,
                    cList
            );
            c.setArchived(rs.getInt("archived"));
            CourseList.add(c);
        }
        DBconn.closeAll(conn, stmt, rs);
        return CourseList;
    }

    @Override
    public List<Course> findAllArchivedCourse() throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from course where archived=1";
        PreparedStatement stmt= conn.prepareStatement(sql);
        ResultSet rs=    stmt.executeQuery();
        List<Course> CourseList=new ArrayList<Course>();
        while(rs.next()) {
            StudentDB sDB=new StudentDB();
            GradableDB gDB=new GradableDB();
            CategoryDB cDB=new CategoryDB();
            List<Category> cList=cDB.findAllCategoryInOneCourse(rs.getInt("courseid"));
            List<Gradable> glist=gDB.findAllGradableInCourse(rs.getInt("courseid"));
            List<Student> sList=sDB.findAllStudentInCourse(rs.getInt("courseid"));
            Course c=new Course(
                    rs.getInt("courseid"),
                    rs.getString("cname"),
                    sList,
                    glist,
                    cList
            );
            c.setArchived(rs.getInt("archived"));
            CourseList.add(c);
        }
        DBconn.closeAll(conn, stmt, rs);
        return CourseList;
    }

    @Override
    public Course findOneCourse(int courseID) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from course where courseid=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,courseID);
        ResultSet rs=    stmt.executeQuery();
        Course course=null;
        if(rs.next()) {
            StudentDB sDB=new StudentDB();
            GradableDB gDB=new GradableDB();
            CategoryDB cDB=new CategoryDB();
            List<Category> cList=cDB.findAllCategoryInOneCourse(rs.getInt("courseid"));
            List<Gradable> glist=gDB.findAllGradableInCourse(rs.getInt("courseid"));
            List<Student> sList=sDB.findAllStudentInCourse(rs.getInt("courseid"));
            Course c=new Course(
                    rs.getInt("courseid"),
                    rs.getString("cname"),
                    sList,
                    glist,
                    cList
            );
            c.setArchived(rs.getInt("archived"));
            course=c;
        }
        DBconn.closeAll(conn, stmt, rs);
        return course;
    }

    @Override
    public Course insertCourse(Course c) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="INSERT into course(cname,archived) values (?,?)";
        PreparedStatement stmt= conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1,c.getCourseName());
        stmt.setInt(2,c.getArchived());
        stmt.executeUpdate();
        ResultSet tableKeys = stmt.getGeneratedKeys();
        tableKeys.next();
        int autoGeneratedID = tableKeys.getInt(1);
        c.setCourseID(autoGeneratedID);
        DBconn.closeAll(conn,stmt);
        if (c.getStudentList()!=null && c.getStudentList().size()!=0) {
            for (Student s : c.getStudentList()) {
                StudentDAO sdb = new StudentDB();
                s.setCourseID(c.getCourseID());
                sdb.addStudentToCourse(s);
            }
        }
        if (c.getGradableList()!=null && c.getGradableList().size()!=0){
            for (int i=0;i<c.getGradableList().size();i++){
                Gradable g=c.getGradableList().get(i);
                g.setCourseID(c.getCourseID());
                GradableDAO gdb=new GradableDB();
                //  duplicate gradable may happen
                int gid=gdb.insertGradable(g);
                g.setgID(gid);
                c.getGradableList().get(i).setgID(gid);
                gdb.addGradableToOneCourse(g);
            }
        }
        if (c.getCategoryList()!=null && c.getCategoryList().size()!=0){
            for (Category gc:c.getCategoryList()){
                CategoryDB cdb = new CategoryDB();
                gc.setCourseid(c.getCourseID());
                cdb.insertCategory(gc);
            }
        }
        return c;
    }

    @Override
    public void deleteCourse(Course c) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="Delete from course where courseid=?";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setInt(1,c.getCourseID());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }

    @Override
    public void updateCourse(Course c) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="Update course set cname=?,archived=? where courseid=?";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,c.getCourseName());
        stmt.setInt(2,c.getArchived());
        stmt.setInt(3,c.getCourseID());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }
}
