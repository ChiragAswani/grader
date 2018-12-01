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
    public boolean checkUserInDB(String username, String password) throws Exception{
        Connection conn=DBconn.getConnection();
        String sql="select * from authentication where username=? and password=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1,username);
        stmt.setString(2,password);
        ResultSet rs=    stmt.executeQuery();
        if (rs.next()){
            DBconn.closeAll(conn,stmt,rs);
            return true;
        }
        DBconn.closeAll(conn, stmt, rs);
        return false;
    }
}
