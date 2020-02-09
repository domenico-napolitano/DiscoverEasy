package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InviaMessaggio
 */
@WebServlet("/InviaMessaggio")
public class InviaMessaggio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InviaMessaggio() {
		super();
		// TODO Auto-generated constructor stub

		log = Logger.getGlobal();

		log.setLevel(Level.INFO);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession sessione = request.getSession(true);

		String emailMittenteMessaggio="";

		synchronized(sessione) {

			Object accesso = sessione.getAttribute("accesso");

			if(accesso==null || (boolean) accesso == false) {
				response.sendRedirect("home.html");
				return;
			}

			emailMittenteMessaggio = (String) sessione.getAttribute("email");
		}

		String emailMittenteChat = (String) request.getParameter("emailMittenteChat");

		String emailDestinatarioChat = (String) request.getParameter("emailDestinatarioChat");

		/*Controllo sicurezza,altrimenti se l'utente cambia email mittente o destinatario su html facendo ispeziona elemento
		 può visualizzare messaggi di chiunque, quindi se la sua email non corrisponde all'email mittente o destinatario chat,
		 allora sarà rindirizzato alla pagina errore*/

		if(!(emailMittenteMessaggio.equals(emailMittenteChat)||emailMittenteMessaggio.equals(emailDestinatarioChat))) {
			response.sendRedirect("Page404.html");
			return;
		}

		String descrizione = (String) request.getParameter("descrizione");
		String nomeAnnuncio = (String) request.getParameter("nomeAnnuncio");

		int annoAnnuncio = Integer.parseInt((String) request.getParameter("annoAnnuncio"));
		int meseAnnuncio = Integer.parseInt((String) request.getParameter("meseAnnuncio"));
		int giornoAnnuncio = Integer.parseInt((String) request.getParameter("giornoAnnuncio"));
		int oraAnnuncio = Integer.parseInt((String) request.getParameter("oraAnnuncio"));
		int minutiAnnuncio = Integer.parseInt((String) request.getParameter("minutiAnnuncio"));
		int secondiAnnuncio = Integer.parseInt((String) request.getParameter("secondiAnnuncio"));

		GregorianCalendar dataOraAnnuncio = new GregorianCalendar(annoAnnuncio, meseAnnuncio, giornoAnnuncio,
				oraAnnuncio, minutiAnnuncio, secondiAnnuncio);
		
		try {
			
			log.logp(Level.INFO, "InviaMessaggio", "doGet", "invio messaggio");
			
			new ManagerChat().inviaMessaggio(descrizione, emailMittenteMessaggio, emailMittenteChat,
					emailDestinatarioChat, nomeAnnuncio, dataOraAnnuncio);
			
			log.logp(Level.INFO, "InviaMessaggio", "doGet", "messaggio inviato");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			log.logp(Level.INFO, "InviaMessaggio", "doGet", "messaggio non inviato, verificata eccezione");

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
