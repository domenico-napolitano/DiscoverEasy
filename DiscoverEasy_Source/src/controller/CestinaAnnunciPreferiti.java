package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.OggettoBean;

/**
 * Servlet implementation class RimuoviOggettoSessionePreferiti
 */
@WebServlet("/CestinaAnnunciPreferiti")
public class CestinaAnnunciPreferiti extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log;

	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CestinaAnnunciPreferiti() {
		super();
		// TODO Auto-generated constructor stub
	
		log = Logger.getGlobal();

		log.setLevel(Level.INFO);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		response.setContentType("text/html");

		HttpSession sessione = request.getSession(true);

		synchronized(sessione) {

			sessione.setAttribute("listaOggettiPreferiti", new Hashtable<String, OggettoBean>());

			log.logp(Level.INFO, "CestinaAnnunciPreferiti", "doGet", "Tutti gli annunci sono stati eliminati dai preferiti in sessione");
			

			Object accesso = sessione.getAttribute("accesso");

			if(accesso != null && (boolean) accesso == true) {
				
				String email = (String) sessione.getAttribute("email");
				
				try {
					new ManagerRicerca().cestinaAnnunci(email);
					
					log.logp(Level.INFO, "CestinaAnnunciPreferiti", "doGet", "Tutti gli annunci sono stati eliminati dai preferiti nel database");

					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					log.logp(Level.INFO, "CestinaAnnunciPreferiti", "doGet", "Sorta eccezione, non è possibile eliminare tutti i preferiti dal database");

				}
			}
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

