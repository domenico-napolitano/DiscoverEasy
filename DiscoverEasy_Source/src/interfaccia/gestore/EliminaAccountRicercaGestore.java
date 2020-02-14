package interfaccia.gestore;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manager.gestore.ManagerGestore;

/**
 * Servlet implementation class EliminaAccountRicercaGestore
 */
@WebServlet("/EliminaAccountRicercaGestore")
public class EliminaAccountRicercaGestore extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static Logger log;

	private PrintWriter out;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EliminaAccountRicercaGestore() {
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

		log.logp(Level.INFO, "EliminaAccountRicercaGestore", "doGet", "Entra nella servlet");

		out = response.getWriter();

		HttpSession sessione = request.getSession(true);

		Object accesso = sessione.getAttribute("accesso");

		if(accesso==null || (boolean)accesso == false || (int) sessione.getAttribute("amministratore") == 0) {

			log.logp(Level.WARNING, "EliminaAccountRicercaGestore", "doGet", "Utente tenta di eliminare un account,"
					+ " mentre la sessione è null oppure ha effettuato il logout oppure non è un amministratore");

			String errore = "{\"status\" : 400, \"redirect\" : \"home.html\"}";

			out.println(errore);

			return;

		}
		
		String emailBan = (String) request.getParameter("emailAccountBarraRicerca");

		String emailGestore = (String) sessione.getAttribute("email");
		
		try {
			new ManagerGestore().banAccount(emailGestore, emailBan);
			
			out.println("{\"oggettoRisposta\" : 200}");

			log.logp(Level.INFO, "EliminaAccountRicercaGestore", "doGet", "Account bannato");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
			sendError(response);
			
			return;
		};
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
