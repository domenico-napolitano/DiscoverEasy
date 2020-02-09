package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.OggettoDAO;

/**
 * Servlet implementation class EliminaAnnuncioNegozio
 */
@WebServlet("/EliminaAnnuncioNegozio")
public class EliminaAnnuncioNegozio extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger log;
	
	private PrintWriter out;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaAnnuncioNegozio() {
        super();
        // TODO Auto-generated constructor stub

       log = Logger.getGlobal();
       
       log.setLevel(Level.INFO);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.logp(Level.INFO, "EliminaAnnuncioNegozio", "doGet", "Entra nella servlet");
		
		out = response.getWriter();
		
		HttpSession sessione = request.getSession(true);
		
		Object accesso = sessione.getAttribute("accesso");
		
		if(accesso==null || (boolean)accesso == false) {

			log.logp(Level.WARNING, "EliminaAnnuncioNegozio", "doGet", "Utente tenta di eliminare un oggetto,"
					+ " mentre la sessione è null oppure ha effettuato il logout");
			
			String errore = "{\"status\" : 400, \"redirect\" : \"home.html\"}";
			
			out.println(errore);
			
			return;
		}		
		
		
		String email = (String) sessione.getAttribute("email");
		
		
		/*if(!((String)sessione.getAttribute("email")).trim().equals(email.trim()))
		{
			log.logp(Level.WARNING, "EliminaAnnuncioNegozio", "doGet", "L'email dell'utente è diversa dall'email"
					+ " dell'oggetto che tenta di eliminare");
			
			sendError(response);
			
			return;
		}*/
		
		String nome = (String) request.getParameter("nomeOggettoEliminato");
		
		int anno = Integer.parseInt((String) request.getParameter("annoOggettoEliminato"));

		/*Per forza meno 1 perchè in oggettoDAO nel metodo deletebykey al mese estratto da GregorianCalendar viene sommato 1 e 
		 * fatta query al database. E bisogna farlo nel metodo deletebykey perchè ci passi un GregorianCalendar il cui mese parte da 0
		 * invece che da 1 ed arriva a 11. quindi se dall'esterno del metodo metti in più 1 allora quando arrivi a 12 per la classe
		 * GregorianCalendar passi all'anno nuovo.*/
		
		int mese = Integer.parseInt((String) request.getParameter("meseOggettoEliminato"))-1;

		int giorno = Integer.parseInt((String) request.getParameter("giornoOggettoEliminato"));

		int ora = Integer.parseInt((String) request.getParameter("oraOggettoEliminato"));
		
		int minuti = Integer.parseInt((String) request.getParameter("minutiOggettoEliminato"));
		
		int secondi = Integer.parseInt((String) request.getParameter("secondiOggettoEliminato"));

		ManagerNegozio negozio = new ManagerNegozio();

		GregorianCalendar orario = new GregorianCalendar(anno, mese, giorno, ora, minuti, secondi);
		
		try {
			negozio.eliminaAnnuncio(nome, email, orario);
			
			out.println("{\"oggettoRisposta\" : 200}");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
			sendError(response);
			
			log.logp(Level.WARNING, "EliminaAnnuncioNegozio", "doGet", "L'annuncio non è stato eliminato,"
					+ "lanciata eccezzione SQLException");
			
			return;		
		}

	//	Logger.getGlobal().logp(Level.INFO, "EliminaAnnuncioNegozio", "doGet", "L'annuncio è stato eliminato");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void sendError(HttpServletResponse response) throws IOException {
		
		String errore = "{\"status\" : 400, \"redirect\" : \"Page404.html\"}";
		
		out.println(errore);
	}

}
