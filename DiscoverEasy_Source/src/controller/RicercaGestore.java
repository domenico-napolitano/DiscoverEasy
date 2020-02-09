package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.OggettoBean;

/**
 * Servlet implementation class RicercaGestore
 */
@WebServlet("/RicercaGestore")
public class RicercaGestore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RicercaGestore() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession sessione = request.getSession(true);

		Object accesso = sessione.getAttribute("accesso");

		if(accesso == null || (boolean)accesso == false || (int)sessione.getAttribute("amministratore") == 0) {

			response.sendRedirect("home.html");

			return;
		}

		String barraRicerca = (String) request.getParameter("barraRicerca");

		try {

			risultatiRicerca = new ManagerRicerca().caricaAnnunci(barraRicerca);

			request.setAttribute("risultatiRicerca", risultatiRicerca);

			System.out.println("arriva qui prova");
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/PaginaRicercaGestore.jsp");
			if(dispatcher != null)
			{
				dispatcher.forward(request, response);
			}

			
		} catch (SQLException e) {

			e.printStackTrace();

			sendError(response);

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

	private void sendError(HttpServletResponse response) throws IOException {
		response.setStatus(400);
		response.sendRedirect("Page404.html");
	}

	public ArrayList<OggettoBean> getRisultatiRicerca() {
		// TODO Auto-generated method stub
		return risultatiRicerca;
	}

	private ArrayList<OggettoBean> risultatiRicerca = null;
}
