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

    @Override
    public void checkDB(String dbName) throws Exception {
        Connection conn=DBconn.getConnection();
        ResultSet resultSet = conn.getMetaData().getCatalogs();
        Boolean existDB=false;
        while (resultSet.next()) {

            String databaseName = resultSet.getString(1);
            if(databaseName.equals(dbName)){
                System.out.println("database exist");
                DBconn.setUrl("jdbc:mysql://127.0.0.1:3306/"+dbName+"?serverTimezone=UTC");
                existDB=true;
            }
        }
        DBconn.close(resultSet);
        if (!existDB){
            String sql="create schema "+dbName;
            PreparedStatement stmt= conn.prepareStatement(sql);
            stmt.execute();
            DBconn.closeAll(conn,stmt);
            DBconn.setUrl("jdbc:mysql://127.0.0.1:3306/"+dbName+"?serverTimezone=UTC");
            Connection newConn=DBconn.getConnection();
            sql="CREATE TABLE `authentication` (\n" +
                    "  `username` varchar(45) NOT NULL,\n" +
                    "  `password` varchar(45) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`username`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            stmt=newConn.prepareStatement(sql);
            stmt.execute();
            sql="INSERT INTO `authentication` VALUES ('test', '');";
            stmt=newConn.prepareStatement(sql);
            stmt.execute();
            sql="CREATE TABLE `course` (\n" +
                    "  `courseid` int(40) NOT NULL AUTO_INCREMENT,\n" +
                    "  `cname` varchar(255) DEFAULT NULL,\n" +
                    "  `archived` int(11) DEFAULT '0',\n"+
                    "  PRIMARY KEY (`courseid`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;";
            stmt=newConn.prepareStatement(sql);
            stmt.execute();
            sql="CREATE TABLE `category` (\n" +
                    "  `courseid` int(11) NOT NULL,\n" +
                    "  `categoryName` varchar(255) NOT NULL,\n" +
                    "  `weight_undergraduate` decimal(10,2) DEFAULT NULL,\n" +
                    "  `weight_graduate` decimal(10,2) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`courseid`,`categoryName`),\n" +
                    "  KEY `categoryName` (`categoryName`),\n" +
                    "  CONSTRAINT `foreignkey10` FOREIGN KEY (`courseid`) REFERENCES `course` (`courseid`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            stmt=newConn.prepareStatement(sql);
            stmt.execute();
            sql="CREATE TABLE `gradable` (\n" +
                    "  `gradableid` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `gname` varchar(255) DEFAULT NULL,\n" +
                    "  `maxscore` decimal(11,3) DEFAULT NULL,\n" +
                    "  `type` varchar(45) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`gradableid`),\n" +
                    "  KEY `foreignkey12` (`type`),\n" +
                    "  CONSTRAINT `foreignkey12` FOREIGN KEY (`type`) REFERENCES `category` (`categoryName`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;";

            stmt=newConn.prepareStatement(sql);
            stmt.execute();
            sql= "CREATE TABLE `student` (\n" +
                    "  `studentid` varchar(40) NOT NULL,\n" +
                    "  `first_name` varchar(255) DEFAULT NULL,\n" +
                    "  `last_name` varchar(255) DEFAULT NULL,\n" +
                    "  `type` varchar(40) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`studentid`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";

            stmt=newConn.prepareStatement(sql);
            stmt.execute();

            sql="CREATE TABLE `register` (\n" +
                    "  `StudentId` varchar(10) NOT NULL,\n" +
                    "  `CourseId` int(40) NOT NULL,\n" +
                    "  `customized` int(11) unsigned zerofill DEFAULT '00000000000',\n" +
                    "  PRIMARY KEY (`StudentId`,`CourseId`),\n" +
                    "  KEY `foreignkey6` (`CourseId`),\n" +
                    "  CONSTRAINT `foreignkey5` FOREIGN KEY (`StudentId`) REFERENCES `student` (`studentid`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `foreignkey6` FOREIGN KEY (`CourseId`) REFERENCES `course` (`courseid`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            stmt=newConn.prepareStatement(sql);
            stmt.execute();
            sql="CREATE TABLE `distribution` (\n" +
                    "  `courseid` int(40) NOT NULL,\n" +
                    "  `gradableid` int(11) NOT NULL,\n" +
                    "  `weighting_undergraduate` decimal(10,3) DEFAULT NULL,\n" +
                    "  `weighting_graduate` decimal(10,3) DEFAULT NULL,\n" +
                    "  `customized` int(10) unsigned zerofill DEFAULT '0000000000',\n" +
                    "  PRIMARY KEY (`courseid`,`gradableid`),\n" +
                    "  KEY `foreignkey2` (`gradableid`),\n" +
                    "  CONSTRAINT `foreignkey1` FOREIGN KEY (`courseid`) REFERENCES `course` (`courseid`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `foreignkey2` FOREIGN KEY (`gradableid`) REFERENCES `gradable` (`gradableid`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            stmt=newConn.prepareStatement(sql);
            stmt.execute();
            sql="CREATE TABLE `tag` (\n" +
                    "  `tagid` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `tname` varchar(45) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`tagid`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;\n";
            stmt=newConn.prepareStatement(sql);
            stmt.execute();
            sql="CREATE TABLE `grade` (\n" +
                    "  `gradableid` int(11) NOT NULL,\n" +
                    "  `studentid` varchar(40) NOT NULL,\n" +
                    "  `weighting` decimal(10,3) DEFAULT NULL,\n" +
                    "  `sscore` decimal(11,3) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`gradableid`,`studentid`),\n" +
                    "  KEY `foreignkey3` (`studentid`),\n" +
                    "  KEY `gradableid` (`gradableid`),\n" +
                    "  CONSTRAINT `foreignkey3` FOREIGN KEY (`studentid`) REFERENCES `student` (`studentid`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `foreignkey4` FOREIGN KEY (`gradableid`) REFERENCES `gradable` (`gradableid`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            stmt=newConn.prepareStatement(sql);
            stmt.execute();
            sql="CREATE TABLE `grade_tag` (\n" +
                    "  `gradableid` int(11) NOT NULL,\n" +
                    "  `studentid` varchar(40) NOT NULL,\n" +
                    "  `tagid` int(11) NOT NULL,\n" +
                    "  PRIMARY KEY (`gradableid`,`studentid`,`tagid`),\n" +
                    "  KEY `foreignkey8` (`studentid`),\n" +
                    "  KEY `foreignkey9` (`tagid`),\n" +
                    "  CONSTRAINT `foreignkey7` FOREIGN KEY (`gradableid`) REFERENCES `grade` (`gradableid`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `foreignkey8` FOREIGN KEY (`studentid`) REFERENCES `grade` (`studentid`) ON DELETE CASCADE ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `foreignkey9` FOREIGN KEY (`tagid`) REFERENCES `tag` (`tagid`) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            stmt=newConn.prepareStatement(sql);
            stmt.execute();
            DBconn.closeAll(newConn,stmt);
        }else{
            DBconn.close(conn);
        }
    }

}
