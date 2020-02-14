package interfaccia.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutHome
 */
@WebServlet("/LogoutHome")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logout() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

		HttpSession sessione = request.getSession(false);

		/* se la sessione è = null allora significa che la sessione è scaduta e faccio direttamente il 
		   sendRedirect ovvero rindirizzamento alla pagina Home altrimenti se non è null la sessione allora
		   modifico l'attributo accesso presente nella sessione e il suo valore lo cambio da true a false e
		   poi dopo faccio il sendRedirect cioè rindirizzamento alla pagina Home*/

		String url = ((String) request.getHeader("referer"));


		if(sessione == null && url.indexOf("http://localhost:8080/ProgettoTSW2/Ricerca") != -1)/*Quando fai logout solo dalla pagina ricerca*/
		{
			response.sendRedirect(url);
			return;
		}
		else
		{

			if(url.indexOf("http://localhost:8080/ProgettoTSW2/Ricerca") != -1)
			{
				sessione.setAttribute("accesso", false);
				accesso = false;
				sessione.setAttribute("email", null);
				
				response.sendRedirect(url);
				return;
			}
		}


		if(sessione == null) /*Quando fai logout solo dalla pagina home e mio negozio*/
		{
			response.sendRedirect("home.html");
			
			return;
		}
		else
		{
			sessione.setAttribute("accesso", false);
			accesso = false;
			sessione.setAttribute("email", null);

			response.sendRedirect("home.html");
			return;

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public boolean isAccesso() {
		return accesso;
	}

	private boolean accesso = true;
	
}
