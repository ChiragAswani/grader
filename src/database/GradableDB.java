package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DAO.GradableDAO;
import DAO.GradeDAO;
import grades.Category;
import grades.Gradable;


public class GradableDB extends DBconn implements GradableDAO {
    @Override
    public List<Gradable> findAllGradableInCourse(int courseID) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from gradable g, distribution d \n" +
                "where g.gradableid=d.gradableid and d.courseid= ? ";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,courseID);
        ResultSet rs=    stmt.executeQuery();
        List<Gradable> GradableList=new ArrayList<Gradable>();
        while(rs.next()) {
            Gradable g=new Gradable(
                    rs.getInt("courseid"),
                    rs.getInt("gradableid"),
                    rs.getString("gname"),
                    rs.getBigDecimal("maxscore"),
                    rs.getBigDecimal("weighting_undergraduate"),
                    rs.getBigDecimal("weighting_graduate"),
                    rs.getInt("customized"),
                    rs.getString("type")
            );
            GradableList.add(g);
        }
        DBconn.closeAll(conn, stmt, rs);
        return GradableList;
    }

    @Override
    public Gradable findOneGradableInCourse(int courseID,int gradableid) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from gradable g, distribution d \n" +
                "where g.gradableid=d.gradableid and d.courseid= ? and d.gradableid=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,courseID);
        stmt.setInt(2,gradableid);
        ResultSet rs=    stmt.executeQuery();
        Gradable gradableOut = null;
        if (rs.next()){
            Gradable gradable=new Gradable();
            gradable.setgID( rs.getInt("gradableid"));
            gradable.setAssignmentName(rs.getString("gname"));
            gradable.setCustomized(rs.getInt("customized"));
            gradable.setMaxScore(rs.getBigDecimal("maxscore"));
            gradable.setWeight_grad(rs.getBigDecimal("weighting_graduate"));
            gradable.setWeight_ungrad(rs.getBigDecimal("weighting_undergraduate"));
            gradable.setCourseID(rs.getInt("courseid"));
            gradable.setType(rs.getString("type"));
            gradableOut=gradable;
        }
        DBconn.closeAll(conn, stmt, rs);
        return gradableOut;
    }

    @Override
    public Gradable findOneGradableByCategoryNameAndGradableName(int courseid, String gname, String cname) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from gradable g, distribution d \n" +
                "where g.gradableid=d.gradableid and d.courseid= ? and g.gname=? and g.type=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,courseid);
        stmt.setString(2,gname);
        stmt.setString(3,cname);
        ResultSet rs=    stmt.executeQuery();
        Gradable gradableOut = null;
        if (rs.next()){
            Gradable gradable=new Gradable();
            gradable.setgID( rs.getInt("gradableid"));
            gradable.setAssignmentName(rs.getString("gname"));
            gradable.setCustomized(rs.getInt("customized"));
            gradable.setMaxScore(rs.getBigDecimal("maxscore"));
            gradable.setWeight_grad(rs.getBigDecimal("weighting_graduate"));
            gradable.setWeight_ungrad(rs.getBigDecimal("weighting_undergraduate"));
            gradable.setCourseID(rs.getInt("courseid"));
            gradable.setType(rs.getString("type"));
            gradableOut=gradable;
        }
        DBconn.closeAll(conn, stmt, rs);
        return gradableOut;
    }

    @Override
    public List<Gradable> getAllGradableInOneCategory(Category c) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select d.courseid, g.gname, g.gradableid, g.maxscore, g.type, d.weighting_graduate,d.weighting_undergraduate, d.customized from gradable g, distribution d, category c \n"+
                " where g.gradableid=d.gradableid and d.courseid=c.courseid and c.categoryName=g.type and d.courseid= ? and c.categoryName=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,c.getCourseid());
        stmt.setString(2,c.getCategoryName());
        ResultSet rs=    stmt.executeQuery();
        List<Gradable> GradableList=new ArrayList<Gradable>();
        while(rs.next()) {
            Gradable g=new Gradable(
                    rs.getInt("courseid"),
                    rs.getInt("gradableid"),
                    rs.getString("gname"),
                    rs.getBigDecimal("maxscore"),
                    rs.getBigDecimal("weighting_undergraduate"),
                    rs.getBigDecimal("weighting_graduate"),
                    rs.getInt("customized"),
                    rs.getString("type")
            );
            GradableList.add(g);
        }
        DBconn.closeAll(conn, stmt, rs);
        return GradableList;
    }

    @Override
    public Gradable addGradableToOneCourse(Gradable g) throws Exception{
        int gid=insertGradable(g);
        String test="test";
        g.setgID(gid);
        Connection conn=DBconn.getConnection();
        String sql="insert into distribution(courseid,gradableid,weighting_undergraduate,weighting_graduate,customized) values(?,?,?,?,?)";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,g.getCourseID());
        stmt.setInt(2,g.getgID());
        stmt.setBigDecimal(3,g.getWeight_ungrad());
        stmt.setBigDecimal(4,g.getWeight_grad());
        stmt.setInt(5,g.getCustomized());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
        return g;
    }

    @Override
    public void removeGradableFromOneCourse(Gradable g) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="delete from distribution where gradableid=? and courseid=?";
        PreparedStatement stmt =conn.prepareStatement(sql);
        stmt.setInt(1,g.getgID());
        stmt.setInt(2,g.getCourseID());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }

    @Override
    public int insertGradable(Gradable g) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="INSERT INTO gradable(gname,maxscore,type) VALUES(?,?,?)";
        PreparedStatement stmt= conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1,g.getAssignmentName());
        stmt.setBigDecimal(2,g.getMaxScore());
        stmt.setString(3,g.getType());
        stmt.executeUpdate();
        ResultSet tableKeys = stmt.getGeneratedKeys();
        tableKeys.next();
        int autoGeneratedID = tableKeys.getInt(1);
        DBconn.closeAll(conn,stmt,tableKeys);
        return autoGeneratedID;
    }

    @Override
    public void deleteGradable(Gradable g)throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="delete from gradable where gradableid=?";
        PreparedStatement stmt =conn.prepareStatement(sql);
        stmt.setInt(1,g.getgID());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }
    @Override
    public void updateGradable(Gradable g)throws Exception {
        Connection conn= DBconn.getConnection();
        String sql="update gradable \n"+
                    "set gname=?, maxscore=?, type=?  \n"+
                    "where gradableid=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1,g.getAssignmentName());
        stmt.setBigDecimal(2,g.getMaxScore());
        stmt.setString(3,g.getType());
        stmt.setInt(4,g.getgID());
        stmt.executeUpdate();

        String sql2="update distribution \n"+
                "set weighting_undergraduate=?,weighting_graduate=?,customized=? \n"+
                "where courseid=? and gradableid=?";

        PreparedStatement stmt2=conn.prepareStatement(sql2);
        stmt2.setBigDecimal(1,g.getWeight_ungrad());
        stmt2.setBigDecimal(2,g.getWeight_grad());
        stmt2.setInt(3,g.getCustomized());
        stmt2.setInt(4,g.getCourseID());
        stmt2.setInt(5,g.getgID());
        stmt2.executeUpdate();
        DBconn.close(stmt2);
        DBconn.closeAll(conn,stmt);
    }
}
