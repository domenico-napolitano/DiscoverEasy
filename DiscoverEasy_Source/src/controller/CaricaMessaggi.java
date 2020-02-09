package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.beans.MessaggioBean;
import model.beans.OggettoBean;

/**
 * Servlet implementation class caricaMessaggi
 */
@WebServlet("/CaricaMessaggi")
public class CaricaMessaggi extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CaricaMessaggi() {
		super();
		// TODO Auto-generated constructor stubù

		log = Logger.getGlobal();

		log.setLevel(Level.INFO);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession sessione = request.getSession(true);

		String email="";

		Object variabileSentinella = request.getParameter("variabileSentinella");

		PrintWriter out = response.getWriter();

		synchronized(sessione) {

			Object accesso = sessione.getAttribute("accesso");

			if(accesso==null || (boolean) accesso == false) {

				/*Controllo per vedere se sono arrivate altre notifiche*/
				if(variabileSentinella != null)
				{

					log.logp(Level.INFO, "CaricaMessaggi", "doGet", "effettua accesso per controllare se ricevuti messaggi");

					out.println("{\"nuoviMessaggi\": false}");

					return;
				}

				response.sendRedirect("home.html");
				return;
			}

			email = (String) sessione.getAttribute("email");
		}

		/*Controllo se ricevuti messaggi, se richiesta arriva da AJAX altrimenti la servlet esegue la funzione carica messaggi*/
		if(variabileSentinella != null)
		{
			try {

				log.logp(Level.INFO, "CaricaMessaggi", "doGet", "controllo se ricevuti messaggi");

				ricevutiMessaggi = new ManagerChat().notifica(email);

				if(ricevutiMessaggi == false) {

					out.println("{\"nuoviMessaggi\": false}");

					return;
				}
				else
				{
					out.println("{\"nuoviMessaggi\": true}");

					return;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				log.logp(Level.INFO, "CaricaMessaggi", "doGet", "fallito controllo messaggi ricevuto, verificata eccezione");

				out.println("{\"nuoviMessaggi\": false}");

				return;
			}
		}


		/*Carica messaggi*/
		
		String emailMittenteChat = (String) request.getParameter("emailMittente");

		String emailDestinatarioChat = (String) request.getParameter("emailDestinatario");

		/*Controllo sicurezza,altrimenti se l'utente cambia email mittente o destinatario su html facendo ispeziona elemento
		 può visualizzare messaggi di chiunque, quindi se la sua email non corrisponde all'email mittente o destinatario chat,
		 allora sarà rindirizzato alla pagina errore*/

		if(!(email.equals(emailMittenteChat)||email.equals(emailDestinatarioChat))) {
			response.sendRedirect("Page404.html");
			return;
		}

		String nomeAnnuncio = (String) request.getParameter("nomeAnnuncio");

		int anno = Integer.parseInt((String) request.getParameter("anno"));

		int mese = Integer.parseInt((String) request.getParameter("mese"))-1;

		int giorno = Integer.parseInt((String) request.getParameter("giorno"));

		int ora = Integer.parseInt((String) request.getParameter("ora"));

		int minuti = Integer.parseInt((String) request.getParameter("minuti"));

		int secondi = Integer.parseInt((String) request.getParameter("secondi"));

		GregorianCalendar dataOraAnnuncio = new GregorianCalendar(anno, mese, giorno, ora, minuti, secondi);

		try {

			log.logp(Level.INFO, "CaricaMessaggi", "doGet", "caricamento messaggi");

			listaMessaggi = new ManagerChat().caricaMessaggi(email, emailMittenteChat, emailDestinatarioChat, nomeAnnuncio, dataOraAnnuncio);

			log.logp(Level.INFO, "CaricaMessaggi", "doGet", "messaggi caricati");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			log.logp(Level.INFO, "CaricaMessaggi", "doGet", "messaggi non caricati, verificata eccezione");
		}

		String arrayJson = "[";

		int lunghezzaListaMessaggi = listaMessaggi.size();

		if(lunghezzaListaMessaggi > 0)
		{
			MessaggioBean messaggio;

			GregorianCalendar dataOra;

			for(int i=0; i < lunghezzaListaMessaggi; i++)
			{
				messaggio = listaMessaggi.get(i);

				dataOra = messaggio.getDataOraInvioMessaggio();

				arrayJson +="{"+


						"\"descrizione\":"+ "\"" + messaggio.getDescrizione() +"\","+

						"\"email_mittente_messaggio\":"+ "\"" + messaggio.getEmailMittenteMessaggio() +"\"";


				arrayJson += "},";

			}

			arrayJson += "{"+"\"emailUtente\":"+ "\"" + email +"\""+"}]";

			log.logp(Level.INFO, "CaricaMessaggi", "doGet","Contenuto lista messaggi JSON: "+arrayJson);


			out.println(arrayJson);

		}//if
		else
		{
			/*lista messaggi vuota, quindi è un array vuoto*/

			arrayJson += "]";

			out.println(arrayJson);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private ArrayList<MessaggioBean> listaMessaggi;
	
	private boolean ricevutiMessaggi;
	
	public ArrayList<MessaggioBean> getListaMessaggi(){
		
		return listaMessaggi;
	}

	public boolean getRicevutiMessaggi(){
		
		return ricevutiMessaggi;
	}
}
