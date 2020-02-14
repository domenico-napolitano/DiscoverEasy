package interfaccia.ricerca;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manager.ricerca.ManagerRicerca;
import model.beans.OggettoBean;
import model.beans.utenteBean;
import model.dao.OggettoDAO;
import model.dao.utenteDAO;

/**
 * Servlet implementation class Ricerca
 */
@WebServlet("/Ricerca")
public class Ricerca extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Ricerca() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

		HttpSession sessione = request.getSession(true);

		String barraRicerca = (String) request.getParameter("barraRicerca");

		String selectRegione = (String) request.getParameter("selectRegione");

		String selectCategoria = (String) request.getParameter("selectCategoria");

		risultatiRicerca = new ManagerRicerca().ricercaAnnunci(selectRegione, selectCategoria, barraRicerca);


		request.setAttribute("risultatiRicerca", risultatiRicerca);

		/*Se utente è loggato ed effettua una ricerca non deve mostrare il pulsante messaggia, agli annunci che ha pubblicato
		 quindi , gli invio email tramite request alla jsp che confronta l'email della request con email annuncio, e se sono 
		 uguali non deve mostrare pulsate*/

		synchronized(sessione){

			Object accesso = sessione.getAttribute("accesso");

			if(accesso!=null && (boolean) accesso == true) {

				String email = (String) sessione.getAttribute("email");

				request.setAttribute("email", email);
			}

		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/PaginaRicerca.jsp");
		if(dispatcher != null)
		{
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

	public ArrayList<OggettoBean> getRisultatiRicerca() {
		return risultatiRicerca;
	}

	private ArrayList<OggettoBean> risultatiRicerca;
}

