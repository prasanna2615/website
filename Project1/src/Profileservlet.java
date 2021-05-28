

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Profileservlet
 */
@WebServlet("/Profileservlet")
public class Profileservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PreparedStatement pmt=null;
	private Connection con=null;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Profileservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Project1","root","root");
			pmt=con.prepareStatement("select * from user where username=? and password=?");
		}
		catch(Exception e){
			System.out.println(e);
		}
		PrintWriter pw=response.getWriter();
		response.setContentType("text/html");
		Cookie[] ck=request.getCookies();
		if(ck==null)
		{
			pw.println("<h3 ><b><i>Please Login First!!!</i></b><h3>");
			RequestDispatcher rd=request.getRequestDispatcher("login.html");
			rd.include(request, response);
		}
		else
		{
			RequestDispatcher rd=request.getRequestDispatcher("index.html");
			rd.include(request, response);
			String user=ck[0].getValue();
			pw.println("<b><i>Welcome "+ck[0].getValue()+"</i><b>");
			pw.println("<br>");
			try
			{
				pmt=con.prepareStatement("select *from user where username=?");
				pmt.setString(1, user);
				ResultSet rs=pmt.executeQuery();
				while(rs.next())
				{
					pw.println("<b><i>Full name is "+rs.getString(3)+"</i></b>");
					pw.println("<br>");
					pw.println("<b><i>Email : "+rs.getString(4)+"</i></b>");
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
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
