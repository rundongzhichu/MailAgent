package MyMailAgent;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

/**
 * Servlet implementation class ChooseServer
 */
public class ChooseServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ChooseServer() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8"); 
    	response.setContentType("text/html;charset=utf-8"); 
    	response.setCharacterEncoding("utf-8");
    	
    	String sender = request.getParameter("sender");
    	String password = request.getParameter("password");
    	String server = request.getParameter("server");
    	
    	System.out.println("发送者："+sender);
    	System.out.println("密码："+password);
    	System.out.println("服务器："+server);
    	
    	request.getSession().setAttribute("sender", sender);
    	request.getSession().setAttribute("password", password);
    	request.getSession().setAttribute("server", server);
    	
		response.sendRedirect("index1.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
