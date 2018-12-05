package database;

//import com.mysql.cj.protocol.Resultset;

import java.sql.*;

public class DBconn {
    private static String driver="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql://127.0.0.1:3306/" +"?serverTimezone=UTC";
    private static String user="root";
    private static String password= CredentialConstants.dbPass;
    
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        if(rs!=null) {
            rs.close();
        }
        if(stmt!=null) {
            stmt.close();
        }
        if(conn!=null) {
            conn.close();
        }
    }


    public static void closeAll(Connection conn, Statement stmt) throws SQLException {
        if(stmt!=null) {
            stmt.close();
        }
        if(conn!=null) {
            conn.close();
        }
    }


    public static void close(Statement stmt) throws SQLException {
        if(stmt!=null) {
            stmt.close();
        }
    }

    public static void close(ResultSet rs) throws SQLException {
        if(rs!=null) {
            rs.close();
        }
    }

    public static void close(Connection conn)throws SQLException {
        if (conn!=null){
            conn.close();
        }
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DBconn.url = url;
    }

    public int executeSQL(String preparedSql, Object[] param) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection(); // connect database
            pstmt = conn.prepareStatement(preparedSql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setObject(i + 1, param[i]); // fill sql
                }
            }
            ResultSet num = pstmt.executeQuery(); // execute sql
        } catch (SQLException e) {
            e.printStackTrace(); // handle SQLException
        } finally {
            try {
                DBconn.closeAll(conn, pstmt, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

}