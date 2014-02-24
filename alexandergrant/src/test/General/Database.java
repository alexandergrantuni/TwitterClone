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
	
	public static void createFollowing()
	 	{
	 		Connection conn = Database.getConnection();
	 		PreparedStatement query = null;
	 		try {
	 			query = conn.prepareStatement("DROP TABLE IF EXISTS `following`;");
	 
	 		query.executeUpdate();
	 		PreparedStatement q = null;
	 		q = conn.prepareStatement("CREATE TABLE `following` ("
	 +"`FollowId` int(11) NOT NULL auto_increment,"
	 +"`UserId` varchar(20) default NULL,"
	 +"`FollowingUserId` varchar(20) default NULL,"
	 +"PRIMARY KEY  (`FollowId`),"
	 +"KEY `UserId` (`UserId`),"
	 +"KEY `FollowingUserId` (`FollowingUserId`),"
	 +"CONSTRAINT `FollowingUserId` FOREIGN KEY (`FollowingUserId`) REFERENCES `users` (`Username`),"
	 +"CONSTRAINT `UserId` FOREIGN KEY (`UserId`) REFERENCES `users` (`Username`)"
	 +") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
	 		q.executeUpdate();
	 		} catch (SQLException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	 	}
	 	
	 	public static void createMessages()
	 	{
	 		
	 		Connection conn = Database.getConnection();
	 		try {
	 			PreparedStatement query = null;
	 			query = conn.prepareStatement("DROP TABLE IF EXISTS `message`;");
	 			query.executeUpdate();
	 			PreparedStatement query2 = null;
	 			query2 = conn.prepareStatement("CREATE TABLE `message` ("
	 					  +"`MessageId` int(11) NOT NULL auto_increment,"
	 					  +"`Text` varchar(140) default NULL,"
	 					  +"`Timestamp` int(11) default NULL,"
	 					  +"`Username` varchar(40) default NULL,"
	 					  +"PRIMARY KEY  (`MessageId`),"
	 					  +"KEY `Username` (`Username`),"
	 					  +"CONSTRAINT `Username` FOREIGN KEY (`Username`) REFERENCES `users` (`Username`)"
	 					+") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
	 			query2.executeUpdate();
	 		} catch (SQLException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 			return;
	 		}
	 		try {
	 			conn.close();
	 		} catch (SQLException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
			
	 	}
	 	
	 	public static void createUsers()
	 	{
	 		Connection conn = Database.getConnection();
	 		try {
	 			PreparedStatement query2 = null;
	 			query2 = conn.prepareStatement("DROP TABLE IF EXISTS `following`;");
	 			query2.executeUpdate();
	 			PreparedStatement query = null;
	 			query = conn.prepareStatement("DROP TABLE IF EXISTS `users`;");
	 			query.executeUpdate();
	 			PreparedStatement q = null;
	 			q = conn.prepareStatement("CREATE TABLE `users` ("
	   +"`Username` varchar(20) NOT NULL default '0',"
	   +"`EmailAddress` varchar(255) NOT NULL default '',"
	   +"`Password` varchar(50) default NULL,"
	   +"`FirstName` varchar(255) default NULL,"
	   +"`LastName` varchar(255) default NULL,"
	   +"`bio` varchar(160) default NULL,"
	   +"`ProfilePicture` varchar(255) default NULL,"
	   +"`IsAdministrator` int(11) default NULL,"
	   +"`AccountCreationTimestamp` int(11) default NULL,"
	   +"PRIMARY KEY  (`Username`,`EmailAddress`)"
	   +") ENGINE=InnoDB DEFAULT CHARSET=latin1;");
	 			q.executeUpdate();
	 		} catch (SQLException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	  		}
	  	}
}
