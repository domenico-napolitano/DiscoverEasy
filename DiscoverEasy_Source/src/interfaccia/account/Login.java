package interfaccia.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manager.utente.ManagerUtente;
import model.beans.utenteBean;
import model.dao.utenteDAO;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();

		log = Logger.getGlobal();

		log.setLevel(Level.INFO);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");


		/*Per vedere se la richiesta è fatta da AJAX con jquery allora l'header della request deve avere un parametro 
		 x-requested-with con valore XMLHttpRequest*/

		String headerRequest = request.getHeader("x-requested-with");


		if(headerRequest != null && headerRequest.equalsIgnoreCase("XMLHttpRequest")) {

			HttpSession sessione = request.getSession(true);

			PrintWriter out = response.getWriter();

			/*CONTROLLO AUTENTICAZIONE*/

			/*if serve per verificare se l'accesso è stato effettutato o meno per mostrare o meno la barra
			  di navigazione con relativi pulsanti mio negozio esci chat */

			if(request.getParameter("verificaAccesso") != null) {

				Object accesso = sessione.getAttribute("accesso");

				if(accesso == null || (boolean)accesso == false) {
					
					out.println("{\"accessoEffettuato\": false, \"accessoGestore\": false}");

					return;
				}
				else
				{
					/*L'utente è autenticato, ma non da gestore*/
					
					if((int)sessione.getAttribute("amministratore") == 1) {

						out.println("{\"accessoEffettuato\": true, \"accessoGestore\": true}");

						return;
					}
					else
					{
						/*L'utente è autenticato, da gestore*/
						
						out.println("{\"accessoEffettuato\": true, \"accessoGestore\": false}");
						
						return;
					}
				}
			}


			/*ORA INIZIA L'AUTENTICAZIONE*/			


			String email = request.getParameter("email");

			String password = request.getParameter("password");


			//controllo pass e email

			utenteBean datiUtente = new ManagerUtente().autentica(email, password);

			/*Compongo un oggetto JSON che contiene il risultato dell'autenticazione e che serve 
			  per la gestione dell'interfaccia grafica*/

			String validitaDati ="{\"validita\":";


			if(datiUtente != null)
			{
				validitaDati += "true}";

				/*Inserisco nella sessione un attributo di nome accesso con valore true in modo che dalla sessione 
				 posso capire se l'utente ha effettuato l'accesso e quindi mostrare pulsanti giusti nella barra navigazione,
				 e se ricarico la pagina entro un certo tempo ovvero che nella sessione è presente ancora l'attributo 
				 accesso=true e quindi l'utente non ha effettuato il logout, saranno visualizzate sempre le informazioni
				 dell'utente che ha effettuato accesso esempio mostrare pulsanti giusti nella barra di navigazione */

				synchronized(sessione) {

					sessione.setAttribute("accesso", true);
					sessione.setAttribute("email", email);
					
					accesso = true;
					
					/*Memorizza nella sessione se questo account è o meno un gestore*/

					sessione.setAttribute("amministratore", datiUtente.getAmministratore());

				}

			}
			else
			{
				validitaDati += "false}";
			}

			/*invia risultato autenticazione*/

			out.println(validitaDati);


		}// fine if , se la request viene inviata con AJAX

		/*RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/okok.jsp");
		dispatcher.forward(request, response);*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private boolean accesso = false;

	public boolean isAccesso() {
		 return accesso;
	}
}
