package web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.pojo.Pessoa;


public class FilterOfflineUser implements Filter {

	
	
	private final String OFFLINE = "<offline>";
	
	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		
		String user = getUser(req);
		
		if(user.equals(OFFLINE)){
			
			System.out.println("=> Access Denied!! You need to be logged in. <=");
			HttpServletResponse resp = (HttpServletResponse) response;

			resp.sendRedirect("login.html");
		}else{
			System.out.println("User "+user+" accessing: "+uri);
			
			chain.doFilter(request, response);	
		}		
	}

	
	
	/**
	 * @param req An object HttpServletRequest.
	 * @return The name of the user online.
	 */
	private String getUser(HttpServletRequest req) {
		Pessoa user = (Pessoa) req.getSession().getAttribute("user.online");
		if(user == null) return OFFLINE;
		return user.getNome();
	}


	public void init(FilterConfig arg0) throws ServletException { }
	
	public void destroy() { }
	
}
