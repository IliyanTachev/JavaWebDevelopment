package org.elsys.servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	HashMap<String, String> map;
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
        map = new HashMap<String, String>();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		ServletOutputStream out = response.getOutputStream();
//		out.println();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<title>");
		out.println("Homework");
		out.println("</title>");
		out.println("</head>");
		out.println("<body>");
		out.println();
		
		for(Map.Entry<String, String> entry : map.entrySet()) {
			out.println("\"" + entry.getKey() + "\"" + " => " + entry.getValue());
			out.println("<br>\r\n");
		}
		
		out.println("<br>");
		out.println("<form action=\"\" method=\"post\">\r\n");
		out.println("Enter KEY:<br>\r\n");
		out.println("<input type=\"text\" name=\"key\">\r\n");
		out.println("<br>\r\n");
		out.println("<br>\r\n");
		out.println("Enter VALUE:<br>\r\n");
		out.println("<input type=\"text\" name=\"value\">\r\n");
		out.println("<br><br>\r\n");
		out.println("<input type=\"submit\" value=\"Submit\">\r\n");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()) {
			Object element1 = en.nextElement();
			String key = element1.toString();
			
			Object element2 = en.nextElement();
			String value = element2.toString();
			map.put(request.getParameter(key), request.getParameter(value));
		}
		
		response.sendRedirect("http://localhost:8080/Servlets/Servlet");
	}

}
