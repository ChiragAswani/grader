package database;

import DAO.GradeDAO;
import grades.Gradable;
import grades.Grade;
import grades.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GradeDB extends DBconn implements GradeDAO {
    @Override
    public List<Grade> findOneStudentAllGradeInOneCourse(String studentid, String courseid) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from grade g, distribution d \n" +
                "where  g.gradableid=d.gradableid and d.courseid= ? and g.studentid=? ";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1,courseid);
        stmt.setString(2,studentid);
        //System.out.println(stmt);
        ResultSet rs=    stmt.executeQuery();
        List<Grade> GradeList=new ArrayList<Grade>();
        while(rs.next()) {
            TagDB tDB=new TagDB();
            List<Tag> tagList = tDB.findAllTagInOneGrade(studentid,rs.getInt("gradableid"));
            Grade g=new Grade(
                    rs.getString("studentid"),
                    rs.getInt("gradableid"),
                    rs.getBigDecimal("sscore"),
                    rs.getBigDecimal("weighting"),
                    tagList
            );
            GradeList.add(g);
        }
        DBconn.closeAll(conn, stmt, rs);
        return GradeList;
    }

    @Override
    public Grade findOneGrade(String studentid, int gradableid) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from grade \n" +
                "where  g.gradableid=? and g.studentid=? ";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,gradableid);
        stmt.setString(2,studentid);
        //System.out.println(stmt);
        ResultSet rs=    stmt.executeQuery();
        Grade grade=null;
        if (rs.next()){
            TagDB tDB=new TagDB();
            List<Tag> tagList = tDB.findAllTagInOneGrade(studentid,rs.getInt("gradableid"));
            Grade g=new Grade(
                    rs.getString("studentid"),
                    rs.getInt("gradableid"),
                    rs.getBigDecimal("sscore"),
                    rs.getBigDecimal("weighting"),
                    tagList
            );
            grade=g;
        }
        DBconn.closeAll(conn, stmt, rs);
        return grade;
    }

    @Override
    public void insertGrade(Grade g) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="INSERT INTO grade(gradableid,studentid,sscore,weighting) VALUES(?,?,?,?)";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,g.getgID());
        stmt.setString(2,g.getsID());
        stmt.setBigDecimal(3,g.getScore());
        stmt.setBigDecimal(4,g.getWeighting());
        stmt.executeUpdate();
        List<Tag> tlist=g.gettList();
        addTagsToOneGrade(tlist,g);
        DBconn.closeAll(conn,stmt);
    }

    @Override
    public void addTagsToOneGrade(List<Tag> tlist,Grade g) throws Exception{
        if (tlist!=null){
            for (Tag t:tlist){
                Connection conn=DBconn.getConnection();
                String sql2="Insert into grade_tag(gradableid,studentid,tagid) values(?,?,?)";
                PreparedStatement stmt2=conn.prepareStatement(sql2);
                stmt2.setInt(1,g.getgID());
                stmt2.setString(2,g.getsID());
                stmt2.setInt(3,t.gettID());
                stmt2.executeUpdate();
                DBconn.closeAll(conn,stmt2);
            }
        }
    }

    @Override
    public void updateGrade(Grade g) throws  Exception{
        Connection conn=DBconn.getConnection();
        String sql="update grade set sscore=?, weighting=? where  gradableid=? and studentid=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(3,g.getgID());
        stmt.setString(4,g.getsID());
        stmt.setBigDecimal(1,g.getScore());
        stmt.setBigDecimal(2,g.getWeighting());
        stmt.executeUpdate();
        String sql2="delete from grade_tag where gradableid=? and studentid=?";
        PreparedStatement stmt2= conn.prepareStatement(sql2);
        stmt2.setInt(1,g.getgID());
        stmt2.setString(2,g.getsID());
        stmt2.executeUpdate();
        List<Tag> tlist=g.gettList();
        addTagsToOneGrade(tlist,g);
        DBconn.close(stmt2);
        DBconn.closeAll(conn,stmt);
    }

    @Override
    public void deleteGrade(Grade g) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="delete from grade where  gradableid=? and studentid=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,g.getgID());
        stmt.setString(2,g.getsID());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }
}
