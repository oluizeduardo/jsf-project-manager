package web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.pojo.Pessoa;

@WebFilter(urlPatterns = {"/professor/*", "/aluno/*"})
public class FilterAccess implements Filter {

		
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
				
		// Pega o objeto do usu�rio logado pela requisi��o vinda ao servidor.
		Pessoa usuario = getUserByRequest(req);

		// Verifica se h� um usu�rio logado.
		if(usuario == null){			
			System.err.println("=> Acesso negado!!! Nenhum usu�rio logado. <=");
			
			// Redireciona o usu�rio para a p�gina de login.			
			resp.sendRedirect("../login.xhtml");
		}else{
			
			String uri = req.getRequestURI();
			
			if(uri.contains("aluno")){
				//Professor tentando acessar p�gina de aluno.
				if(!usuario.getPapel().equals("Aluno")){
					// Redireciona para a home do aluno.			
					resp.sendRedirect("../professor/home.xhtml");
				}else{
					// Continua o fluxo de acesso.
					chain.doFilter(request, response);
				}
			}else{
				if(uri.contains("professor")){
					//Aluno tentando acessar p�gina de professor.
					if(!usuario.getPapel().equals("Professor")){
						// Redireciona para a home do aluno.			
						resp.sendRedirect("../aluno/home.xhtml");
					}else{
						// Continua o fluxo de acesso.
						chain.doFilter(request, response);
					}
				}
			}
		}		
	}

	
	

	private Pessoa getUserByRequest(HttpServletRequest req) {
		return (Pessoa) req.getSession().getAttribute(SessionUtil.KEY_SESSION);
	}


	public void init(FilterConfig arg0) throws ServletException { }
	
	public void destroy() { }
	
}
