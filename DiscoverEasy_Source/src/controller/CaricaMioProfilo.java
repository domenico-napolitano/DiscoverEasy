package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.utenteBean;
import model.dao.utenteDAO;

/**
 * Servlet implementation class CaricaMioProfilo
 */
@WebServlet("/CaricaMioProfilo")
public class CaricaMioProfilo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CaricaMioProfilo() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private static utenteBean utente;
	
	
    public static utenteBean getUtente() {
		return utente;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession sessione = request.getSession(true);

		Object accesso = sessione.getAttribute("accesso");
		
		if(accesso == null || (boolean)accesso == false) {
			
			response.sendRedirect("home.html");
			
			return;
		}
		
		String email = (String)sessione.getAttribute("email");
		
		utente = new ManagerUtente().caricaMioProfilo(email);
		
		request.setAttribute("utente", utente);
		
		request.setAttribute("isAmministratore", sessione.getAttribute("amministratore"));
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/MioProfilo.jsp");
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

}
