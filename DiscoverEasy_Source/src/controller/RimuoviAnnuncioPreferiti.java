package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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
@WebServlet("/RimuoviAnnuncioPreferiti")
public class RimuoviAnnuncioPreferiti extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RimuoviAnnuncioPreferiti() {
		super();
		// TODO Auto-generated constructor stub

		log = Logger.getGlobal();

		log.setLevel(Level.INFO);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		response.setContentType("text/html");

		HttpSession sessione = request.getSession(true);

		synchronized(sessione) {

			String nome = ((String) request.getParameter("nome")).trim();

			String email_annuncio = ((String) request.getParameter("email")).trim();

			int anno = Integer.parseInt((String) request.getParameter("anno"));

			int mese = Integer.parseInt((String) request.getParameter("mese"))-1;

			int giorno = Integer.parseInt((String) request.getParameter("giorno"));

			int ora = Integer.parseInt((String) request.getParameter("ora"));

			int minuti = Integer.parseInt((String) request.getParameter("minuti"));

			int secondi = Integer.parseInt((String) request.getParameter("secondi"));

			GregorianCalendar dataOra = new GregorianCalendar(anno, mese, giorno, ora, minuti, secondi);

			Hashtable<String, OggettoBean> listaOggettiPreferiti = (Hashtable<String, OggettoBean>) sessione.getAttribute("listaOggettiPreferiti");

			Object accesso = sessione.getAttribute("accesso");

			if(accesso != null && (boolean) accesso == true) {

				String email_utente = (String) sessione.getAttribute("email");

				try {
					new ManagerRicerca().eliminaAnnuncio(email_utente, email_annuncio, nome, dataOra);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					log.logp(Level.INFO, "RimuoviAnnuncioPreferiti", "doGet", "Eccezione l'annuncio nel database non è stato eliminato");
				}

				log.logp(Level.INFO, "RimuoviAnnuncioPreferiti", "doGet", "L'annuncio nel database è stato eliminato");

			}

			/*Elimina da sessione annuncio*/
			
			System.out.println(nome);
			System.out.println(email_annuncio);
			System.out.println(nome+email_annuncio+anno+mese+giorno+ora+minuti+secondi);
			

			if(listaOggettiPreferiti.remove(nome+email_annuncio+anno+mese+giorno+ora+minuti+secondi) == null){

				log.logp(Level.INFO, "RimuoviAnnuncioPreferiti", "doGet", "L'annuncio non è stato eliminato dalla sessione");
			}
			else
			{
				log.logp(Level.INFO, "RimuoviAnnuncioPreferiti", "doGet", "L'annuncio è stato eliminato dalla sessione");
			}

			sessione.setAttribute("listaOggettiPreferiti", listaOggettiPreferiti);
			
			if(listaOggettiPreferiti.size() == 0) {
				
				/*Variabile sentinella  per controllo grafica*/
				
				response.getWriter().println("{\"vuoto\": true}");
		
			}
			else
			{
				response.getWriter().println("{\"vuoto\": false}");

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