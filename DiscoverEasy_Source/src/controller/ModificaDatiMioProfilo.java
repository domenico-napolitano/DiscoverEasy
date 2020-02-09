package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ModificaDatiMioProfilo
 */
@WebServlet("/ModificaDatiMioProfilo")
public class ModificaDatiMioProfilo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaDatiMioProfilo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession sessione = request.getSession(true);

		Object accesso = sessione.getAttribute("accesso");
		
		if(accesso == null || (boolean)accesso == false) {
			
			response.sendRedirect("home.html");
			
			return;
		}
		
		String email = (String) sessione.getAttribute("email");
		
		/*Serve solo per capire se l'utenet ha effettuato o meno azione elimina account*/
		String variabileSentinellaEliminaAccount = (String) request.getParameter("eliminaAccount");
		
		/*If elimina account*/
		
		if(variabileSentinellaEliminaAccount != null) {

		
			try {
				
				/*Questo metodo elimina tutti i preferiti, setta tutti gli annunci a eliminato e l'account a eliminato, dell'utente
				  con email che è uguale al parametro in input email*/
				
				new ManagerUtente().eliminaAccount(email);
								
				sessione.setAttribute("accesso", false);
				sessione.setAttribute("email", null);
				sessione.setAttribute("amministratore", 0);

				response.sendRedirect("home.html");

				return;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				sendError(response);
				
				return;
			}
		}
		
		String password = (String) request.getParameter("password");
				
		try {
			
			System.out.println("Email modifica è:"+ email+"\n la password è: "+password);
			new ManagerUtente().modificaPassword(email, password);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		
			sendError(response);
			
			return;
			
		} catch(IllegalArgumentException e1) {
			
			sendError(response);
			
			return;
		}
		
		/*Serve solo per caricare label password modificata in MioProfilo.jsp*/
		request.setAttribute("passwordModificata", "variabile sentinella");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("CaricaMioProfilo");
		if(dispatcher != null) {
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void sendError(HttpServletResponse response) throws IOException {
		response.setStatus(400);
		response.sendRedirect("Page404.html");
	}
}
