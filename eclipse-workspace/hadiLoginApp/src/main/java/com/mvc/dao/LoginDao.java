package com.mvc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.mvc.bean.LoginBean;
import com.mvc.util.DBConnection;

public class LoginDao {
	
	 public String authenticateUser(LoginBean loginBean)
	 {
		 String userName = loginBean.getUserName(); //Assign user entered values to temporary variables.
		 String password = loginBean.getPassword();

		 Connection con = null;
		 Statement statement = null;
		 ResultSet resultSet = null;

		 String userNameDB = "";
		 String passwordDB = "";
		 String roleDB = "";

		 try
		 {
			 con = DBConnection.createConnection(); //Fetch database connection object
			 statement = con.createStatement(); //Statement is used to write queries.
			 resultSet = statement.executeQuery("select username,password,role from users"); //the table name is users and userName,password,role are columns. Fetching all the records and storing in a resultSet.

			 while(resultSet.next()) // Until next row is present otherwise it return false
				 {
				  userNameDB = resultSet.getString("userName"); //fetch the values present in database
				  passwordDB = resultSet.getString("password");
				  roleDB = resultSet.getString("role");
				  
					if(userName.equals(userNameDB) && password.equals(passwordDB) && roleDB.equals("manager"))
						return "Manager_Role";
						
					else if(userName.equals(userNameDB) && password.equals(passwordDB) && roleDB.equals("user"))
						return "User_Role";		
				 }
		        	 
		 }
			  catch(SQLException e)
			  {
				  e.printStackTrace();
			  }
			  return "Invalid userid or password";
	 }
}

