package com.mvc.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mvc.bean.LoginBean;
import com.mvc.dao.LoginDao;

public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public LoginServlet() // default constructor
	{
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//username and password are the names which been given in the input box in Login.jsp page. Retrieving the values entered by the user and keeping in instance variables for further use.

		String userName = request.getParameter("username");
		String password = request.getParameter("password");

		LoginBean loginBean = new LoginBean(); //creating object for LoginBean class, which is a normal java class, contains just setters and getters. Bean classes are efficiently used in java to access user information wherever required in the application.

		loginBean.setUserName(userName); //setting the username and password through the loginBean object then we can get it in future.
		loginBean.setPassword(password);

		LoginDao loginDao = new LoginDao(); //creating object for LoginDao. This class contains main logic of the application.

		try
		{
			String userValidate = loginDao.authenticateUser(loginBean);

			if(userValidate.equals("Manager_Role"))
			{
				System.out.println("Manager's Home");

				HttpSession session = request.getSession(); //Creating a session
				session.setAttribute("Manager", userName); //setting session attribute
				request.setAttribute("userName", userName);

				request.getRequestDispatcher("Manager.jsp").forward(request, response);
			}
			else if(userValidate.equals("User_Role"))
			{
				System.out.println("User's Home");
				
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(10*60);
				session.setAttribute("User", userName);
				request.setAttribute("userName", userName);

				request.getRequestDispatcher("Home.jsp").forward(request, response);
			}
			else
			{
				System.out.println("Error message = "+userValidate);
				request.setAttribute("errMessage", userValidate);

				request.getRequestDispatcher("Login.jsp").forward(request, response);
			}
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		catch (Exception e2)
		{
			e2.printStackTrace();
		}
	} //End of doPost()
}
