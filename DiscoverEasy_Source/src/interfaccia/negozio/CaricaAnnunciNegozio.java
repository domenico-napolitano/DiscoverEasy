package interfaccia.negozio;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import manager.ricerca.ManagerRicerca;
import model.beans.OggettoBean;
import model.dao.OggettoDAO;

/**
 * Servlet implementation class MioNegozio
 */
@WebServlet("/CaricaAnnunciNegozio")
public class CaricaAnnunciNegozio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger log;

	private ArrayList<OggettoBean> listaOggetti;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CaricaAnnunciNegozio() {
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

		PrintWriter out = response.getWriter();

		/* se getSession riceve come input true ritorna la sessione esistente o, se non esiste, ne crea 
		  una nuova altrimenti se prende false ritorna, se possibile, la sessione esistente, altrimenti 
		  ritorna null */

		HttpSession sessione = request.getSession(true);

		/*controlla se esiste la sessione altrimenti fa un rindirizzamento alla pagina home per accedervi.
		  Si verifica di solito quando rimane aperta la pagina di accesso e dopo tempo clicchi sul pulsante
		  mio negozio , che scaduta la sessione ovviamente non entra nella pagina mia negozio e quindi devi
		  rieffettuare l'accesso */		

		Object loginEffettuato = sessione.getAttribute("accesso");

		/*recupero l'attributo accesso dalla sessione. Se esite nella sessione allora significa che l'utente ha
	 effettuato il login, quindi è stata avviata la servlet login che ha messo nella sessione un attributo
	 accesso con valore true altrimenti, se l'utente non ha effettuato il login oppure ha effettuato il logout
	 oppure è scaduta la sessione quindi una volta acceduto NUOVAMENTE alla pagina si aprirà una nuova sessione
	 che non conterrà questo attributo accesso con valore true perchè la servlet login non è stata avviata
	 perchè l'utente non ha effettuato il login. Quindi se esiste attributo nella sessione non sarà eseguito
	 l'if se non esiste il metodo getAttribute restituirà null e quindi sarà eseguito l'if e l'utente 
	 sarà rindirizzato alla home */


		if(loginEffettuato == null || ((boolean)loginEffettuato) == false)
		{

			response.sendRedirect("home.html");

			return;
		}
		else
		{
			synchronized(sessione) {


				/*Il login è stato effettuato perchè non si è eseguito if sopra*/


				try {

					listaOggetti = new ManagerRicerca().caricaAnnunci((String)sessione.getAttribute("email"));

					request.setAttribute("listaOggetti", listaOggetti);

					request.setAttribute("isAmministratore", sessione.getAttribute("amministratore"));

					log.logp(Level.INFO, "MioNegozio", "doGet", "Tutti gli annunci da caricare:"+listaOggetti);

					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/MioNegozio.jsp");
					if(dispatcher != null)
					{
						dispatcher.forward(request, response);
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

					sendError(response);

					return;
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

	private void sendError(HttpServletResponse response) throws IOException {
		response.setStatus(400);
		response.sendRedirect("Page404.html");
	}

	public Object getListaOggetti() {
		// TODO Auto-generated method stub
		return listaOggetti;
	}
}
