

import java.io.*;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Registerservlet
 */
@WebServlet("/Registerservlet")
public class Registerservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PreparedStatement pmt=null;
	private Connection con=null;
    /**
     * Default constructor. 
     */
    public Registerservlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project1","root","root");
			pmt=con.prepareStatement("insert into user values(?,?,?,?)");
		}
		catch(Exception e){
			System.out.println(e);
		}
		}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("uname");
		String pass=request.getParameter("pwd");
		String fullname=request.getParameter("fname");
		String mail=request.getParameter("email");
		PrintWriter pw=response.getWriter();
		response.setContentType("text/html");
		try {
			pmt.setString(1,username);
			pmt.setString(2,pass);
			pmt.setString(3,fullname);
			pmt.setString(4,mail);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i=0;
		try {
			i = pmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(i==1)
		{
			
			RequestDispatcher rd=request.getRequestDispatcher("index.html");
			rd.include(request, response);
			pw.println("<h2><b>Registration Successfull!!<b></h2>");
		}
		else
		{
			pw.println("<h3><i>Registration not completed!!Please Try Again<i></h3>");
			RequestDispatcher rd=request.getRequestDispatcher("register.html");
			rd.include(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
