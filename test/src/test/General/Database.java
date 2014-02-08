package test.General;

import java.sql.*;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.*;

//@Resource(name="jdbc/chitchat", type=javax.sql.DataSource.class)
public class Database {
	private static Connection databaseConnection;
	
	@Resource(name="jdbc/chitchat")
	private static DataSource ds;
	public static Connection getConnection()
	{
		  try {
				Context ctx = new InitialContext();
				ds = (DataSource)ctx.lookup("java:comp/env/jdbc/chitchat");
				databaseConnection = ds.getConnection();
				return databaseConnection;
			  } catch (NamingException ne) {
				ne.printStackTrace();
				return null;
			  } catch (SQLException se) {
				// TODO Auto-generated catch block
				se.printStackTrace();
				return null;
			}
	}
}
