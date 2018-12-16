package database;

import DAO.CategoryDAO;
import grades.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDB extends DBconn implements CategoryDAO {
    @Override
    public void insertCategory(Category gc) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="INSERT INTO category(courseid,categoryName,weight_undergraduate,weight_graduate,displayOrder) VALUES(?,?,?,?,?)";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,gc.getCourseid());
        stmt.setString(2,gc.getCategoryName());
        stmt.setBigDecimal(3,gc.getWeight_ungrad());
        stmt.setBigDecimal(4,gc.getWeight_grad());
        stmt.setLong(5,System.currentTimeMillis());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }

    @Override
    public void deleteCategory(Category gc) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="delete from category where courseid=? and categoryName=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,gc.getCourseid());
        stmt.setString(2,gc.getCategoryName());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }

    @Override
    public void updateCategory(Category gc) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="UPDATE category set weight_undergraduate=?,weight_graduate=? where courseid=? and categoryName=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setBigDecimal(1,gc.getWeight_ungrad());
        stmt.setBigDecimal(2,gc.getWeight_grad());
        stmt.setInt(3,gc.getCourseid());
        stmt.setString(4,gc.getCategoryName());
        stmt.executeUpdate();
        DBconn.closeAll(conn,stmt);
    }

    @Override
    public List<Category> findAllCategoryInOneCourse(int courseid) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from category where courseid=? \n"+
                "ORDER BY displayOrder ASC";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,courseid);
        ResultSet rs= stmt.executeQuery();
        List<Category> CategoryList=new ArrayList<Category>();
        while(rs.next()) {
            Category gc=new Category(rs.getInt("courseid"),
                                                    rs.getString("categoryName"),
                                                    rs.getBigDecimal("weight_undergraduate"),
                                                    rs.getBigDecimal("weight_graduate"));

            CategoryList.add(gc);
        }
        DBconn.closeAll(conn, stmt, rs);
        return CategoryList;
    }

    @Override
    public Category findOneCategoryInOneCourse(int courseid, String categoryName) throws Exception {
        Connection conn=DBconn.getConnection();
        String sql="select * from category where courseid=? and categoryName=? \n"+
                "ORDER BY displayOrder ASC";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setInt(1,courseid);
        stmt.setString(2,categoryName);
        ResultSet rs= stmt.executeQuery();
        Category gc=null;
        if (rs.next()){
            Category category =new Category(
                    rs.getInt("courseid"),
                    rs.getString("categoryName"),
                    rs.getBigDecimal("weight_undergraduate"),
                    rs.getBigDecimal("weight_graduate")
            );
            gc= category;
        }
        DBconn.closeAll(conn, stmt, rs);
        return gc;
    }


}
