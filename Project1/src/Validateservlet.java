

import java.io.*;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Validateservlet
 */
@WebServlet("/Validateservlet")
public class Validateservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PreparedStatement pmt=null;
	private Connection con=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Validateservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Project1","root","root");
			pmt=con.prepareStatement("select * from user where username=? and password=?");
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter pw=response.getWriter();
		String user=request.getParameter("uname");
		String pass=request.getParameter("pwd");
		try
		{
			pmt.setString(1, user);
			pmt.setString(2, pass);
			ResultSet rs=pmt.executeQuery();
			if(rs.next())
			{
				Cookie ck1=new Cookie("uname",user);
				response.addCookie(ck1);
				
				RequestDispatcher rd=request.getRequestDispatcher("index.html");
				rd.include(request,response);
				pw.println("<h2>Login Successful</h2>");
			}
			else
			{
				pw.println("<h3>Invalid username!!Try Again</h3>");
				RequestDispatcher rd=request.getRequestDispatcher("login.html");
				rd.include(request, response);
			}
		}
		catch(Exception e){
			System.out.println(e);
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
