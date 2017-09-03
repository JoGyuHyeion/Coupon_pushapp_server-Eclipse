package android_Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConn {
	
	String Drv = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/3cs";
    String user = "3cs";
    String password = "password";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    public Connection getConn() {
        try {
            Class.forName(Drv);
            conn = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Err1:"+ex.getCause());
        } catch (SQLException ex) {
             System.out.println("Err2:"+ex.getErrorCode());
        }
        return conn;
    }
     public Statement getStmt() {
        try {
            conn =getConn();
            stmt=conn.createStatement();
        } catch (SQLException ex) {
             System.out.println("Err1:"+ex.getErrorCode());
        }
        return stmt;
    }
      public ResultSet getRs(String sql) {
        try {
           stmt=getStmt();
           rs=stmt.executeQuery(sql);
        } catch (SQLException ex) {
             System.out.println("Err1:"+ex.getErrorCode());
        }
        return rs;
    }
}