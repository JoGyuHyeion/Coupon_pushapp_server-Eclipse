package android_Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker{	
	public Connection makeConnection()throws ClassNotFoundException,SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://203.247.240.61:3306/3cs", "3cs","password");
		return c; 
	}
}
