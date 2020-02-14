package interfaccia.ricerca;

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

import manager.ricerca.ManagerRicerca;
import model.beans.OggettoBean;

/**
 * Servlet implementation class AggiungiPreferitiRicerca
 */
@WebServlet("/AggiungiPreferitiRicerca")
public class AggiungiPreferitiRicerca extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AggiungiPreferitiRicerca() {
		super();
		// TODO Auto-generated constructor stub

		log = Logger.getGlobal();

		log.setLevel(Level.INFO);
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.logp(Level.INFO, "AggiungiPreferitiRicerca", "doGet", "entra nella classe");

		response.setContentType("text/html");

		HttpSession sessione = request.getSession(true);

		synchronized(sessione) {

			Hashtable<String, OggettoBean> listaOggettiPreferiti = (Hashtable<String, OggettoBean>) sessione.getAttribute("listaOggettiPreferiti");

			/*Serve la dimensione dell'oggetto listaOggettiPreferiti perchè essendo che è un insieme, se aggiungi di nuovo lo stesso
			  elemento nell'insieme, allora non sarà aggiunto e quindi non bisogna fare query al database di salvare annuncio se
			  la dimensione dell'insieme è uguale dopo al metodo add() sull'insieme listaOggettiPreferiti*/

			String nome = (String) request.getParameter("nomeP");

			//	String descrizione = (String) request.getParameter("descrizioneP");

			//String regione = (String) request.getParameter("regioneP");

			String email = (String) request.getParameter("emailP");

			//	String numeroTelefono = (String) request.getParameter("numeroP");

			//	String immagine = (String) request.getParameter("urlImmagineP");			

			//	String prezzoS = (String) request.getParameter("prezzoP");

			int anno = Integer.parseInt((String) request.getParameter("anno"));

			int mese = Integer.parseInt((String) request.getParameter("mese"));

			int giorno = Integer.parseInt((String) request.getParameter("giorno"));

			int ora = Integer.parseInt((String) request.getParameter("ora"));

			int minuti = Integer.parseInt((String) request.getParameter("minuti"));

			int secondi = Integer.parseInt((String) request.getParameter("secondi"));

			/*	prezzoS = prezzoS.trim();
			prezzoS = prezzoS.replaceAll("€", "");

			int prezzo = Integer.parseInt(prezzoS);

			OggettoBean oggetto = new OggettoBean();

			oggetto.setNome(nome);

			oggetto.setDescrizione(descrizione);

			oggetto.setRegione(regione);

			oggetto.setEmail(email);

			oggetto.setImmagine(immagine);

			oggetto.setPrezzo(prezzo);

			oggetto.setNumero_proprietario(numeroTelefono);*/

			if(!listaOggettiPreferiti.containsKey(nome+email+anno+(mese-1)+giorno+ora+minuti+secondi) == true) {


				log.logp(Level.INFO, "AggiungiPreferitiRicerca", "doGet", "annuncio non presente in sessione, da salvare");
			}
			else
			{
				log.logp(Level.INFO, "AggiungiPreferitiRicerca", "doGet", "annuncio non salvato in sessione, perchè già presente");

				return;
			}




			GregorianCalendar dataOra = new GregorianCalendar(anno, mese-1,
					giorno, ora, minuti, secondi);


			OggettoBean oggetto = null;
			try {
				oggetto = new ManagerRicerca().ricercaAnnuncioPerChiave(nome, email, dataOra);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

				log.logp(Level.INFO, "AggiungiPreferitiRicerca", "doGet", "non aggiunto annuncio ai preferiti, verificata eccezione");

			}


			//oggetto.setDataOra(dataOra);

			if(oggetto == null) {

				log.logp(Level.INFO, "AggiungiPreferitiRicerca", "doGet", "non aggiunto annuncio ai preferiti, non presente sul database");

				response.sendRedirect("Page404.html");

				return;
			}

			listaOggettiPreferiti.put(oggetto.getNome()+oggetto.getEmail()+oggetto.getDataOra().get(GregorianCalendar.YEAR)+
					oggetto.getDataOra().get(GregorianCalendar.MONTH)+oggetto.getDataOra().get(GregorianCalendar.DAY_OF_MONTH)+
					oggetto.getDataOra().get(GregorianCalendar.HOUR_OF_DAY)+oggetto.getDataOra().get(GregorianCalendar.MINUTE)+
					oggetto.getDataOra().get(GregorianCalendar.SECOND),oggetto);

			log.logp(Level.INFO, "AggiungiPreferitiRicerca", "doGet", "salvato annuncio in sessione");

			log.logp(Level.INFO, "AggiungiPreferitiRicerca", "doGet", oggetto.getNome()+oggetto.getEmail()+oggetto.getDataOra().get(GregorianCalendar.YEAR)+
					oggetto.getDataOra().get(GregorianCalendar.MONTH)+oggetto.getDataOra().get(GregorianCalendar.DAY_OF_MONTH)+
					oggetto.getDataOra().get(GregorianCalendar.HOUR_OF_DAY)+oggetto.getDataOra().get(GregorianCalendar.MINUTE)+
					oggetto.getDataOra().get(GregorianCalendar.SECOND));

			sessione.setAttribute("listaOggettiPreferiti", listaOggettiPreferiti);


			/*Se l'utente è loggato allora l'annuncio sarà salvato anche sul database*/

			if(sessione.getAttribute("accesso") != null && (boolean)sessione.getAttribute("accesso") == true) {

				try {
					new ManagerRicerca().salvaAnnuncioAiPreferiti((String) sessione.getAttribute("email"), oggetto);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					log.logp(Level.INFO, "AggiungiPreferitiRicerca", "doGet", "Lanciata eccezzione per mezzo del metodo salvaAnnuncioAiPreferiti,"
							+ "l'annuncio è stato salvato solo nella sessione anzichè, anche nel database");
				}
			}

			log.logp(Level.INFO, "AggiungiPreferitiRicerca", "doGet", "lista preferiti : "+listaOggettiPreferiti);

			/*mi mette lista oggetti preferiti in sessione cosi una volta ritornato alla funzione di callback di ajax quella funzione mi ricarica la pagina
			  e cosi  si avvia la funzione caricasessione() che mi aggiorna i preferiti*/

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
