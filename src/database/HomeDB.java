package database;

import DAO.HomeDAO;
import Student.Student;
import grades.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class HomeDB extends DBconn implements HomeDAO {
    @Override
    public boolean checkUserInDB(String password) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="select * from authentication where password=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1,password);
        ResultSet rs=    stmt.executeQuery();
        if (rs.next()){
            DBconn.closeAll(conn,stmt,rs);
            return true;
        }
        DBconn.closeAll(conn, stmt, rs);
        return false;
    }

    @Override
    public boolean checkPasswordExist() throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="select * from authentication where username='test'";
        PreparedStatement stmt= conn.prepareStatement(sql);
        ResultSet rs=    stmt.executeQuery();
        if (rs.next()){
            String password=rs.getString("password");
            DBconn.closeAll(conn,stmt,rs);
            return !password.equals("");
        }
        DBconn.closeAll(conn, stmt, rs);
        return false;
    }

    @Override
    public void changePassword(String newPassword) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="update authentication set password=? where username='test'";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1,newPassword);
        stmt.execute();
        DBconn.closeAll(conn, stmt);
    }
}
